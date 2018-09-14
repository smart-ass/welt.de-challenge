package service

import api_client.UserApi
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsArray, JsObject}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserService @Inject()(userApi: UserApi) {

  def getUsersData: Future[Option[JsArray]] = {
    val usersFuture = userApi.getAllUsers
    for {
      mayBeUsers <- usersFuture
    } yield mayBeUsers
  }

  def getUserData(userId: Int): Future[Option[JsObject]] = {
    val userFuture = userApi.getUser(userId)
    for {
      mayBeUser <- userFuture
    } yield mayBeUser
  }
}
