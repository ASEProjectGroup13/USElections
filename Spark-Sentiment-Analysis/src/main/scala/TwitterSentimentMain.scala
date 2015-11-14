import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by Bharat on 13-Nov-15.
 */

object TwitterSentimentMain {

  def main(args: Array[String]) {

    val filters = Array("#hillaryclinton","#Martinomalley","#BernieSanders","#DonaldTrump", "#JebBush","#BenCarson","#ChrsiChristie","#jindal","@HillaryClinton","@MartinOMalley","@SenSanders","@realDonaldTrump",
                        "@JebBush","@RealBenCarson","@ChrisChristie","@GrahamBlog","@RickSantorum","@BobbyJindal","@RickSantorum", "@marcorubio","#MarcoRubio")

    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials

    System.setProperty("twitter4j.oauth.consumerKey", "t0yuE3t1kmJ2rMEEaSTn7w8iK")
    System.setProperty("twitter4j.oauth.consumerSecret", "TICdoM3voJGu2MCDAqnYWumQTuV5IP27eoOPsMRDen9i78vgIv")
    System.setProperty("twitter4j.oauth.accessToken", "136941445-aTIo9pxFUzuwtJPdEmlf1Ie3al9AOUMVBOqxsVxv")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "DAFkGQTet4XkFLQ0PLn9Jz7YYHMhY4mQFw01EbMoNjXNj")

    //Create a spark configuration with a custom name and master
    // For more master configuration see  https://spark.apache.org/docs/1.2.0/submitting-applications.html#master-urls
    val sparkConf = new SparkConf().setAppName("STweetsApp").setMaster("local[*]")
    //Create a Streaming COntext with 2 second window
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    //Using the streaming context, open a twitter stream (By the way you can also use filters)
    //Stream generates a series of random tweets
    val stream = TwitterUtils.createStream(ssc, None, filters)


    val sentiment:DStream[TweetWithSentiment]=stream.map{Status=> {
      val st = Status.getText()
      val date = Status.getCreatedAt()
      val location = Status.getGeoLocation();
      var loc="";
      if(location!=null) {
        val lat=location.getLatitude()
        val long = location.getLongitude()
        loc = lat.toString() + long.toString();
    }
      /*
      val place = Status.getPlace()
      if (place != null) {
      val name = place.getCountry()
      val name2 = place.getName()
      print(name)
      print(name2)
    }
*/
      //place.
     // print(place);
     // print(location);
    //  print(st);
    //  print(date);

      var hashtag="";
      var party="";

      if(st.contains("#hillaryclinton") || st.contains("@HillaryClinton")) {
        hashtag = "HillaryClinton";
        party = "Democratic";
      }
      else if(st.contains("#Martinomalley") || st.contains("@Martinomalley")) {
        hashtag = "Martinomalley";
        party = "Democratic";
      }
      else if(st.contains("#BernieSanders") || st.contains("@SenSanders")) {
        hashtag = "BernieSanders";
        party = "Democratic";
      }
      else if(st.contains("#DonaldTrump") || st.contains("@realDonaldTrump")) {
        hashtag = "DonaldTrump";
        party = "Republican";
      }
      else if(st.contains("#JebBush") || st.contains("@JebBush")) {
        hashtag = "JebBush";
        party = "Republican";
      }
      else if(st.contains("#BenCarson") || st.contains("@RealBenCarson")) {
        hashtag = "BenCarson";
        party = "Republican";
      }
      else if(st.contains("#ChrsiChristie") || st.contains("@ChrisChristie")) {
        hashtag = "ChrisChristie";
        party = "Republican";
      }
      else if(st.contains("#jindal") || st.contains("@BobbyJindal")) {
        hashtag = "Jindal";
        party = "Republican";
      }
      else if(st.contains("@GrahamBlog")) {
        hashtag = "GrahamBlog";
        party = "Republican";
      }
      else if(st.contains("@RickSantorum")) {
        hashtag = "RickSantorum";
        party = "Republican";
      }
      else if(st.contains("@marcorubio") || st.contains("#MarcoRubio")) {
        hashtag = "MarcoRubio";
        party = "Republican";
      }
      //print(hashtag);

      //val hash=Status.getHashtagEntities();
      val sa= new SentimentAnalyzer()
      val tw=sa.findSentiment(st,date,hashtag,party,loc);
      tw
    }}

   sentiment.foreachRDD{
      rdd=>rdd.foreach{
        tw=> {
        //  if(tw!=null)
          //     println(tw.getLine+"      "+tw.getCssClass)
        }}}

    ssc.start()

    ssc.awaitTermination()
  }

}
