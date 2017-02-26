package service

import generated.tables.records.UserRecord

class GreetingService {
  def greet(users:Array[UserRecord]):String =
    users.foldRight("")((record,acc)=>s"Hi ${record.getFirstname} \n")
  def greet(user:UserRecord):String =
    s"Hi ${user.getFirstname} \n"
}
