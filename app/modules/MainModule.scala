package modules

import helpers.{Contexts, DatabaseHelper}
import scaldi.Module
import service.{GreetingService, HashingService}

class MainModule extends Module {
  binding to new Contexts
  binding to new DatabaseHelper
  binding to new GreetingService
  binding to new HashingService
}