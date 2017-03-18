package service

import models.User


class GreetingService {
  def greet(users:Array[User]):String =
    users.foldRight("")((record,acc)=>s"Hi ${record.firstname} \n")
  def greet(user:User):String =
    s"Hi ${user.firstname} \n"
}
