import com.google.inject.{AbstractModule, Provides}
import conf.AppConf
import play.api.Configuration

import scala.concurrent.duration.Duration

class Module extends AbstractModule {

  override def configure(): Unit = {
  }

  @Provides
  def provideAppConf(configuration: Configuration): AppConf = {
    AppConf(
      userApiUrl = configuration.get[String]("app.userApiUrl"),
      userApiTimeout = configuration.get[Duration]("app.userApiTimeout")
    )
  }
}
