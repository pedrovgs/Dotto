package controllers

import javax.inject._

import play.api.data.Forms._
import play.api.data._
import play.api.mvc._

/**
  * Main application controller. Returns the main page in plain HTTP and declares and endpoint to translate a string
  * message into morse.
  */
@Singleton
class DottoController @Inject() extends Controller {

  /**
    * Main application endpoint. All the requests to the root domain path will arrive here.
    *
    * @return the main page ready to start sending messages from a text box.
    */
  def index = Action {
    Ok(views.html.index("", ""))
  }

  /**
    * Enqueue a message to be translated to morse and be shown in the led.
    *
    * @return 201 if the message has been enqueued.
    */
  def toMorse = Action { implicit request =>
    Form("message" -> nonEmptyText).bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(errorMessage = "The request sent hasn't the correct structure."))
      },
      message => {
        Created(views.html.index(message = message))
      }
    )
  }

}
