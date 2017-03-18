package controllers


import javax.inject.{Singleton,Inject}

import models.User
import play.api.cache.CacheApi
import play.api.mvc.{Action, Controller}
import service.GreetingService

@Singleton
class Hello @Inject()(greetingService:GreetingService, cache: CacheApi) extends Controller {

  def greet = Action {
    val id = 1
    cache.get[User](id.toString) match {
      case Some(user) =>
        Ok(greetingService.greet(user))
      case None =>
        User.find(id) match {
          case Some(u) =>
            cache.set(id.toString, u)
            Ok(greetingService.greet(u))
          case None => BadRequest(s"no such user: $id")
        }
    }
  }
}