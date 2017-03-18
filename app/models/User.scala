package models

import scalikejdbc._

case class User(
  id: Long,
  email: String,
  password: String,
  firstname: String,
  lastname: String) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Int = User.destroy(this)(session)

}


object User extends SQLSyntaxSupport[User] {

  override val tableName = "users"

  override val columns = Seq("id", "email", "password", "firstname", "lastname")

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = apply(u.resultName)(rs)
  def apply(u: ResultName[User])(rs: WrappedResultSet): User = new User(
    id = rs.get(u.id),
    email = rs.get(u.email),
    password = rs.get(u.password),
    firstname = rs.get(u.firstname),
    lastname = rs.get(u.lastname)
  )

  val u = User.syntax("u")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.id, id)
    }.map(User(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(User as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(User as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    email: String,
    password: String,
    firstname: String,
    lastname: String)(implicit session: DBSession = autoSession): User = {
    val generatedKey = withSQL {
      insert.into(User).namedValues(
        column.email -> email,
        column.password -> password,
        column.firstname -> firstname,
        column.lastname -> lastname
      )
    }.updateAndReturnGeneratedKey.apply()

    User(
      id = generatedKey,
      email = email,
      password = password,
      firstname = firstname,
      lastname = lastname)
  }

  def batchInsert(entities: Seq[User])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'email -> entity.email,
        'password -> entity.password,
        'firstname -> entity.firstname,
        'lastname -> entity.lastname))
        SQL("""insert into users(
        email,
        password,
        firstname,
        lastname
      ) values (
        {email},
        {password},
        {firstname},
        {lastname}
      )""").batchByName(params: _*).apply[List]()
    }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.id -> entity.id,
        column.email -> entity.email,
        column.password -> entity.password,
        column.firstname -> entity.firstname,
        column.lastname -> entity.lastname
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(User).where.eq(column.id, entity.id) }.update.apply()
  }

}
