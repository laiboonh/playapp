package helpers

import akka.actor.ActorSystem
import scaldi.{Injectable, Injector}

import scala.concurrent.ExecutionContext

class Contexts(implicit inj:Injector) extends Injectable {
  val system = inject[ActorSystem]
  val database: ExecutionContext =
    system.dispatchers.lookup("contexts.database")
}