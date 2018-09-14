package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import service.UserService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               userService: UserService) extends AbstractController(cc) {

  def getUser(id: Int): Action[AnyContent] = Action.async {
    userService.getUserWithPosts(id).map {
      case Some(user)=> Ok(Json.toJson(user))
      case None => NotFound
    }
  }

  def getAllUsers: Action[AnyContent] = Action.async {
    userService.getUsersWithPosts.map {
      case Some(usersWithPosts)=> Ok(Json.toJson(usersWithPosts))
      case None => NotFound
    }
  }
}