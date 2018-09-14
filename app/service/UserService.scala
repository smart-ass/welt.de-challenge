package service

import api_client.{PostApi, UserApi}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsArray, JsObject, JsValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserService @Inject()(userApi: UserApi, postApi: PostApi) {

  def getUsersWithPosts: Future[Option[JsArray]] = {
    val usersFuture = userApi.getAllUsers
    val postsFuture = postApi.getAllPosts
    for {
      mayBeUsers <- usersFuture
      mayBePosts <- postsFuture
    } yield {
      for {
        users <- mayBeUsers
        posts <- mayBePosts
      } yield {
        val postsByUserId = posts.value.groupBy { post =>
          (post \ "userId").as[Int]
        }
        val usersWithPosts = users.value.map { user =>
          val userId = (user \ "id").as[Int]
          val userPosts = postsByUserId.getOrElse(userId, Seq.empty)
          mergeUserAndPosts(user, JsArray(userPosts))
        }
        JsArray(usersWithPosts)
      }
    }
  }

  def getUserWithPosts(userId: Int): Future[Option[JsObject]] = {
    val userFuture = userApi.getUser(userId)
    val userPostsFuture = postApi.getUserPosts(userId)
    for {
      mayBeUser <- userFuture
      mayBeUserPosts <- userPostsFuture
    } yield {
      for {
        user <- mayBeUser
        posts <- mayBeUserPosts
      } yield mergeUserAndPosts(user, posts)
    }
  }

  private def mergeUserAndPosts(user: JsValue, posts: JsArray): JsObject = {
    val postsWithoutUserId = JsArray(posts.value.map { _.as[JsObject] - "userId" })
    user.as[JsObject] + ("posts" -> postsWithoutUserId)
  }
}
