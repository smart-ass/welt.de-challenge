package controllers

import javax.inject._
import play.api.libs.json.{JsObject, Json}
import play.api.mvc._

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getUserData(id: Int): Action[AnyContent] = Action {
    Ok(Json.toJson(JsObject.empty))
  }

}