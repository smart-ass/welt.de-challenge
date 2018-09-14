package api_client

import conf.AppConf
import javax.inject.{Inject, Singleton}
import play.api.http.Status
import play.api.libs.json.{JsArray, JsObject, JsValue, Reads}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserApi @Inject()(wsClient: WSClient, appConf: AppConf) {

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

  private def parseJsonBody[T: Reads](response: WSResponse): Option[T] = {
    response.status match {
      case Status.OK => response.body[JsValue].asOpt[T]
      case Status.NOT_FOUND => None
      case _ => throw new RuntimeException(s"Unexpected response: status=${response.status}; body=${response.body}")
    }
  }
}
