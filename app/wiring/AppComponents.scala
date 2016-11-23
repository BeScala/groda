package wiring

import com.softwaremill.macwire._
import controllers.Assets
import controllers.RaffleController
import play.api._
import play.api.i18n.{DefaultLangs, DefaultMessagesApi}
import play.api.routing.Router
import router.Routes

trait AppComponents extends BuiltInComponents {

  lazy val assets: Assets = wire[Assets]
  lazy val prefix: String = "/"

  lazy val router: Router = {
    val context = ""
    wire[Routes] withPrefix configuration.getString("play.http.context").getOrElse("/werf")
  }

  lazy val raffleController = wire[RaffleController]
  lazy val messagesApi = wire[DefaultMessagesApi]
  lazy val langs = wire[DefaultLangs]
}
