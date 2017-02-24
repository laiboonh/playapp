package controllers

import generated.tables.records.UserRecord
import org.jooq.{DSLContext, SQLDialect}
import org.jooq.impl.DSL
import play.api.db.Database
import play.api.mvc.{Action, Controller}
import service.GreetingService
import generated.Tables._
import scaldi.{Injectable, Injector}

class Hello (implicit inj:Injector) extends Controller with Injectable {
  val db = inject[Database]
  val greetingService = inject[GreetingService]
  def greet = Action {
    db.withConnection{
      connection =>
        val context:DSLContext = DSL.using(connection, SQLDialect.POSTGRES_9_4)
        val users:Array[UserRecord] = context.selectFrom[UserRecord](USER).fetchArray()
        Ok(greetingService.greet(users))
    }
  }
}