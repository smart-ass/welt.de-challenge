package service

import api_client.{PostApi, UserApi}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsArray, JsObject}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserService @Inject()(userApi: UserApi, postApi: PostApi) {

  def getUsersData: Future[Option[JsArray]] = {
    val usersFuture = userApi.getAllUsers
    for {
      mayBeUsers <- usersFuture
    } yield mayBeUsers
  }

  def getUserData(userId: Int): Future[Option[JsObject]] = {
    val userFuture = userApi.getUser(userId)
    val userPostsFuture = postApi.getUserPosts(userId)
    for {
      mayBeUser <- userFuture
      userPosts <- userPostsFuture
    } yield {
      mayBeUser.flatMap { user =>
        userPosts.map { posts =>
          user + ("posts" -> posts)
        }
      }
    }
  }
}
