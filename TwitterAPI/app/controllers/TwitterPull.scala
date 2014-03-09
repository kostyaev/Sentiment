package controllers

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.commons.io.IOUtils


object TwitterPull {

  val AccessToken = "hF6seC55tmLhwVmf769Mw";
  val AccessSecret = "yLSuNWGtVSlkgpolD7F185cVWBsE0UCWHQKjPdSb4U";
  val ConsumerKey = "2163469069-HVGKOzDEnSbuEBw6lWpXRIp97zzreBNd7CEp3oq";
  val ConsumerSecret = "zxfJlHr6wQOYHUpCaAU0Fp9OcuVQt06UVtBxdBNQZiDMW";


  def main(args: Array[String]) {

    val consumer = new CommonsHttpOAuthConsumer(ConsumerKey,ConsumerSecret);
    consumer.setTokenWithSecret(AccessToken, AccessSecret);

    val request = new HttpGet("http://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=josdirksen");
    consumer.sign(request);
    val client = new DefaultHttpClient();
    val response = client.execute(request);

    println(response.getStatusLine().getStatusCode());
    println(IOUtils.toString(response.getEntity().getContent()));
  }
}