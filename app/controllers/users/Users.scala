package controllers.users

import javax.inject.{Singleton,Inject}

import com.github.tototoshi.play2.json4s.native._
import models.User
import org.json4s._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraints._

@Singleton
class Users @Inject() (json4s: Json4s) extends Controller {

  import json4s._
  implicit val formats = DefaultFormats

  def all = Action {
    Ok(Extraction.decompose(User.findAll))
  }

  case class UserForm(email: String, password: String, firstname:String, lastname:String)

  private val userForm = Form(
    mapping(
      "email" -> text.verifying(nonEmpty),
      "password" -> text.verifying(nonEmpty),
      "firstname" -> text.verifying(nonEmpty),
      "lastname" -> text.verifying(nonEmpty)
    )(UserForm.apply)(UserForm.unapply)
  )

  def create = Action { implicit req =>
    userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(s"invalid parameters: $formWithErrors"),
      form => {
        val user = User.create(email = form.email, password = form.password, firstname=form.firstname, lastname=form.lastname)
        Ok(s"created user with id: ${user.id}")
      }
    )
  }
}
