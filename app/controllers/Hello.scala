package controllers

import generated.Tables._
import generated.tables.records.UserRecord
import helpers.DatabaseHelper
import org.jooq.DSLContext
import play.api.mvc.{Action, Controller}
import scaldi.{Injectable, Injector}
import service.GreetingService


class Hello (implicit inj:Injector) extends Controller with Injectable {
  val db = inject[DatabaseHelper]
  val greetingService = inject[GreetingService]

  def greet = Action.async {

    import scala.concurrent.ExecutionContext.Implicits.global

    db.query[Array[UserRecord]]{
      (context:DSLContext) =>
        context.selectFrom[UserRecord](USER).fetchArray()
    }.map{
      users => Ok(greetingService.greet(users))
    }
  }
}