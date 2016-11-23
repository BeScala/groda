package controllers

import javax.inject.{Singleton}

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

@Singleton
class RaffleController (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  def index() = Action {
    Ok(views.html.index())
  }

  def test = Action {
    Ok("Hello")
  }
}