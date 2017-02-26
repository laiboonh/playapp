package helpers

import org.jooq.{DSLContext, SQLDialect}
import org.jooq.impl.DSL
import play.api.db.Database
import scaldi.{Injectable, Injector}

import scala.concurrent.Future

class DatabaseHelper(implicit inj:Injector) extends Injectable {
  val db = inject[Database]
  val contexts = inject[Contexts]
  def query[A](block: DSLContext => A):Future[A] = Future {
    db.withConnection {
      connection =>
        val sql = DSL.using(connection, SQLDialect.POSTGRES_9_4)
        block(sql)
    }
  }(contexts.database)
}
