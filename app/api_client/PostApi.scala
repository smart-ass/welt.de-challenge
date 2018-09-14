package api_client

import conf.AppConf
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsArray, JsObject}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostApi @Inject()(wsClient: WSClient, appConf: AppConf) extends JsonApi {

  private val apiUrl = s"${appConf.userApiUrl}posts"

  def getAllPosts(implicit ec: ExecutionContext): Future[Option[JsArray]] = {
    wsClient
      .url(apiUrl)
      .withRequestTimeout(appConf.userApiTimeout)
      .get()
      .map(parseJsonBody[JsArray])
  }

  def getUserPosts(userId: Int)(implicit ec: ExecutionContext): Future[Option[JsArray]] = {
    val url = s"$apiUrl?userId=$userId"
    wsClient
      .url(url)
      .withRequestTimeout(appConf.userApiTimeout)
      .get()
      .map(parseJsonBody[JsArray])
  }

  def createPost(post: JsObject): Future[JsObject] = {
    ???
  }

  def updatePost(post: JsObject): Future[JsObject] = {
    ???
  }

  def deletePost(id: Int): Future[Boolean] = {
    ???
  }

}
