package controllers

import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.JsArray
import play.api.test.FakeRequest
import play.api.test.Helpers._

class UserControllerSpec extends PlaySpec with SpecWithPlayApp with MustMatchers {

  private val controller = injector.instanceOf[UserController]

  "UserController#getUser" when {
    "user exists" should {
      "respond with user+posts data" in {
        val existingUserId = 1
        val result = call(
          controller.getUser(existingUserId),
          FakeRequest(method = "GET", path = s"/users/$existingUserId")
        )

        status(result) mustBe Status.OK
        val json = contentAsJson(result)
        (json \ "id").as[Int] mustBe existingUserId
        (json \ "posts").as[JsArray].value.size mustBe 10
      }
    }

    "user doesn't exist" should {
      "respond with 404" in {
        val nonExistingUserId = 100
        val result = call(
          controller.getUser(nonExistingUserId),
          FakeRequest(method = "GET", path = s"/users/$nonExistingUserId")
        )

        status(result) mustBe Status.NOT_FOUND
      }
    }
  }
}
