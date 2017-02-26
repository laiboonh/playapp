package controllers

import generated.Tables._
import generated.tables.records.UserRecord
import helpers.DatabaseHelper
import org.jooq.DSLContext
import play.api.cache.CacheApi
import play.api.mvc.{Action, Controller, Result}
import scaldi.{Injectable, Injector}
import service.GreetingService

import scala.concurrent.Future

class Hello(implicit inj: Injector) extends Controller with Injectable {
  val db = inject[DatabaseHelper]
  val greetingService = inject[GreetingService]
  val cacheApi = inject[CacheApi]

  def greet = Action.async {

    import scala.concurrent.ExecutionContext.Implicits.global
    val id = "1"

    val temp = cacheApi.get[UserRecord](id)

    cacheApi.get[UserRecord](id) match {
      case Some(user) =>
        Future.successful(Ok(greetingService.greet(user)))
      case None =>
        db.query[Option[UserRecord]] {
          (context: DSLContext) =>
            Option(context.selectFrom[UserRecord](USER).where(USER.ID.equal(id.toLong)).fetchOne())
        }.map {
          userOpt =>
            userOpt match {
              case Some(u) =>
                cacheApi.set(u.getId.toString, u)
                Ok(greetingService.greet(u))
              case None => BadRequest(s"no such user: $id")
            }
        }
    }
  }
}