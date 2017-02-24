package service

import org.mindrot.jbcrypt.BCrypt

class HashingService {
  def hashpw(secret:String) = BCrypt.hashpw(secret, BCrypt.gensalt())
  def checkpw(candidate:String, hashed:String) = BCrypt.checkpw(candidate,hashed)
}
