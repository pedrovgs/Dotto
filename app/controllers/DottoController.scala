package controllers

import javax.inject._

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
    Ok(views.html.index.render())
  }

}
