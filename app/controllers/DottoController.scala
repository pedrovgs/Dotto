package controllers

import javax.inject._

import com.github.pedrovgs.dotto.actors.ShowMessageActor
import com.github.pedrovgs.dotto.actors.ShowMessageActor.ShowMessage
import akka.actor.ActorSystem
import play.api.Logger
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

/**
  * Main application controller. Returns the main page in plain HTTP and declares and endpoint to translate a string
  * message into morse.
  */
@Singleton
class DottoController @Inject()(val messagesApi: MessagesApi, val actorSystem: ActorSystem) extends Controller with I18nSupport {

  private val messageForm = Form("message" -> nonEmptyText)
  private val showMessageActor = actorSystem.actorOf(ShowMessageActor.props)

  /**
    * Main application endpoint. All the requests to the root domain path will arrive here.
    *
    * @return the main page ready to start sending messages from a text box.
    */
  def index = Action { implicit request =>
    Logger.debug("GET request received at /" + request)
    Ok(views.html.index())
  }

  /**
    * Enqueue a message to be translated to morse and be shown in the led.
    *
    * @return 201 if the message has been enqueued.
    */
  def toMorse = Action { implicit request =>
    Logger.debug("POST request received at / " + request)
    messageForm.bindFromRequest.fold(
      formWithErrors => {
        Logger.debug("Error translating request " + request)
        BadRequest(views.html.index(error = "The message can't be empty ¯\\_(ツ)_/¯"))
      },
      messagePosted => {
        showMessageActor ! ShowMessage(messagePosted)
        Logger.debug("Message enqueued to be translated " + request)
        Created(views.html.index(result = messagePosted))
      }
    )
  }

}
