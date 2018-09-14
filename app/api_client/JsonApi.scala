package api_client

import play.api.http.Status
import play.api.libs.json.{JsValue, Reads}
import play.api.libs.ws.WSResponse

trait JsonApi {

  protected def parseJsonBody[T: Reads](response: WSResponse): Option[T] = {
    response.status match {
      case Status.OK => response.body[JsValue].asOpt[T]
      case Status.NOT_FOUND => None
      case _ => throw new RuntimeException(s"Unexpected response: status=${response.status}; body=${response.body}")
    }
  }
}
