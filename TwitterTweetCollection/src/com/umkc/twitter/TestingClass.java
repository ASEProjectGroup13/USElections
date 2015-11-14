package com.umkc.twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TestingClass {
	
	private static String CONSUMER_KEY = "Va6EyvdYI3JC3U2GadAVg2eW1";
	private static String CONSUMER_KEY_SECRET = "yqaufHGGB7rbsQs9HTnShhOwNbLAhZ2Rdc1kp7hHxFpGfQXlZK";
	private static String ACCESS_TOKEN = "149520055-OFjHM4XO08YHs0Kt47YhvDpLpD3kS127LbpjCaAo";
	private static String ACCESS_TOKEN_SECRET = "3aj6ZC2tQ4vk5VEqswXZfPFKwWDO4vACQxHx9MK5yZOsF";
	
	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
		cb.setOAuthAccessToken(ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		int pageno = 1;
		String user = "realDonaldTrump";
		List<Status> statuses = new ArrayList<Status>();

		while (true) {

		  try {

		    int size = statuses.size(); 
		    Paging page = new Paging(pageno++, 10);
		    statuses.addAll(twitter.getUserTimeline(user, page));
		    if (statuses.size() == size)
		      break;
		  }
		  catch(TwitterException e) {

		    e.printStackTrace();
		  }
		}

		System.out.println("Total: "+statuses.size());
		
		//System.out.println(statuses);
	}

}
