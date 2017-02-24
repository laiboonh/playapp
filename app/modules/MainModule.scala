package modules

import scaldi.Module
import service.{GreetingService, HashingService}

class MainModule extends Module {
  binding to new GreetingService
  binding to new HashingService
}