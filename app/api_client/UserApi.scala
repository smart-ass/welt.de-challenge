package api_client

import conf.AppConf
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsArray, JsObject}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserApi @Inject()(wsClient: WSClient, appConf: AppConf) extends JsonApi  {

  private val apiUrl = s"${appConf.userApiUrl}users"

  def getAllUsers(implicit ec: ExecutionContext): Future[Option[JsArray]] = {
    wsClient
      .url(apiUrl)
      .withRequestTimeout(appConf.userApiTimeout)
      .get()
      .map(parseJsonBody[JsArray])
  }

  def getUser(id: Int)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    val url = s"$apiUrl/$id"
    wsClient
      .url(url)
      .withRequestTimeout(appConf.userApiTimeout)
      .get()
      .map(parseJsonBody[JsObject])
  }

  def createUser(user: JsObject): Future[JsObject] = {
    ???
  }

  def updateUser(user: JsObject): Future[JsObject] = {
    ???
  }

  def deleteUser(id: Int): Future[Boolean] = {
    ???
  }

}
