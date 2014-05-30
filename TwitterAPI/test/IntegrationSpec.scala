package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.Logger
import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import org.fluentlenium.adapter.IsolatedTest

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends Specification {
  
  "Application" should {
    "work from within a browser" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/")
        browser.pageSource must contain("Streaming sentiment analysis")
      }
    }
  }

  "Application" should {
    "connect to twitter api with keyword and retrieve stream" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/")
        browser.find("#keywordsInput").text("movie")
        browser.$("#runKeywordsButton").click()
        browser.wait((3 seconds).inMilliseconds)
        val size1 = browser.$("#list").size()
        browser.wait((3 seconds).inMilliseconds)
        val size2 = browser.$("#list").size()
        size2 must be greaterThan size1
      }
    }
  }
  
}