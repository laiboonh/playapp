package modules

import generated.Tables._
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import play.api.db.Database
import scaldi.{Injectable, Injector, Module}
import service.HashingService

class Fixtures (implicit inj:Injector) extends Injectable {
  val db = inject[Database]
  val hashingService = inject[HashingService]
  db.withTransaction {
    connection =>
      val sql = DSL.using(connection, SQLDialect.POSTGRES_9_4)
      if (sql.fetchCount(USER) == 0) {
        val hashedPassword = hashingService.hashpw("secret")
        sql
          .insertInto(USER)
          .columns(USER.EMAIL, USER.FIRSTNAME, USER.LASTNAME, USER.PASSWORD)
          .values("bob@marley.org", "Bob", "Marley", hashedPassword)
          .execute()
      }
  }
}


class FixturesModule extends Module {
  binding to new Fixtures
}
