package controllers

import javax.inject._

import akka.actor.ActorSystem
import com.github.pedrovgs.dotto.core.DottoApp._
import com.github.pedrovgs.dotto.interpreter.DottoInterpreter
import com.github.pedrovgs.dotto.interpreter.actors.ShowMessageActor
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
  private val interpreter = new DottoInterpreter(actorSystem.actorOf(ShowMessageActor.props))

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
        val program = enqueueMessageProgram(messagePosted)
        val resultMessage = program.foldMap(interpreter)
        Created(views.html.index(result = resultMessage))
      }
    )
  }

}
