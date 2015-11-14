package com.umkc.twitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class TwitterTweetCollection {
	
	private static String CONSUMER_KEY = "Va6EyvdYI3JC3U2GadAVg2eW1";
	private static String CONSUMER_KEY_SECRET = "yqaufHGGB7rbsQs9HTnShhOwNbLAhZ2Rdc1kp7hHxFpGfQXlZK";
	private static String ACCESS_TOKEN = "149520055-OFjHM4XO08YHs0Kt47YhvDpLpD3kS127LbpjCaAo";
	private static String ACCESS_TOKEN_SECRET = "3aj6ZC2tQ4vk5VEqswXZfPFKwWDO4vACQxHx9MK5yZOsF";
	private static int count =10;
	
	/*****
	 * 
	 * Main method for starting execution of the program and it is starting point of our application
	 * @param args
	 * @throws TwitterException 
	 * @throws ParseException 
	 * 
	 */
	public static void main(String[] args) throws TwitterException, ParseException {
		
		
		startCollectingTweets();
		
	}
	
	/***
	 * Method will start collecting tweets 
	 * @throws TwitterException 
	 * @throws ParseException 
	 */
	public static void startCollectingTweets() throws TwitterException, ParseException{
		
		//Creating Twitter object from twitter  
		Twitter twitterObject = new TwitterFactory().getInstance(); 
		
        twitterObject.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        twitter4j.auth.AccessToken oathAccessToken = new twitter4j.auth.AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
       twitterObject.setOAuthAccessToken(oathAccessToken);
       Query query = new Query("@realDonaldTrump");
//       Date date = new SimpleDateFormat("yyyymmdd").parse("20151110");
//       Date date1 = new SimpleDateFormat("yyyymmdd").parse("20150101");
//       String modifiedDate= new SimpleDateFormat("yyyymmdd").format(date);
      // System.out.println("input date"+modifiedDate);
       //query.setSince("2015-01-01");
       query.setUntil("2015-11-10");
       QueryResult result;
           do {
           result = twitterObject.search(query);
           List<Status> tweets = result.getTweets(); 
           
           if(tweets.size() > 100){
        	   break;
           }
      else{
        	   int a =10;
           for (Status tweet : tweets) {
        	   
              // System.out.println("@"+tweet.getUser().getScreenName() + "|" + tweet.getText()+"|"+ tweet.isRetweeted() );
        	   if (count > a) {
				break;
			}
        	   a++;
        	   System.out.println(tweet);
                }
          }
             } while ((query = result.nextQuery()) != null); 
	}

}
