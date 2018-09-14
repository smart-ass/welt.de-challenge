package conf

import javax.inject.Singleton

import scala.concurrent.duration.Duration

@Singleton
case class AppConf(userApiUrl: String, userApiTimeout: Duration)
