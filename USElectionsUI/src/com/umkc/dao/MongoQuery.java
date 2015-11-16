package com.umkc.dao;
/**
 * Created by Bharat on 11/13/2015.
 */
import java.util.ArrayList;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class MongoQuery {
	public static MongoClient mongoClient;
    public static DBCollection dbcollection;

    public static DBCollection createDBCollection() {

        if(mongoClient == null){
            MongoClientURI mongoClientUri = new MongoClientURI("mongodb://bharat:bharat@ds053954.mongolab.com:53954/twitter_tweets");
            mongoClient = new MongoClient(mongoClientUri);
            DB db = mongoClient.getDB(mongoClientUri.getDatabase());
            dbcollection = db.getCollection("tweets");
            
            return dbcollection;
        }
        else
            return dbcollection;
    }

    public String queryTotalCount()
    {	dbcollection = createDBCollection();
    	JSONObject json = new JSONObject();
    	BasicDBObject whereQuery = new BasicDBObject();
    	whereQuery.put("party", "Republican");
    	DBCursor cursor = dbcollection.find(whereQuery);
    	cursor.count();
    	json.put("Republican", cursor.count());
    	System.out.println("Republican Tweet Count:" + cursor.count());
    	whereQuery.put("party","Democratic");
    	cursor=dbcollection.find(whereQuery);
    	System.out.println("Democratic Tweet Count:" + cursor.count());
    	json.put("Democratic", cursor.count());
    	System.out.println(json.toString());
    	
    	return json.toString();
  
    }
    
    public  String queryWithSentimentCount()
    {
    	dbcollection = createDBCollection();
    	BasicDBObject andQuery = new BasicDBObject();
    	JSONObject json = new JSONObject();
    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	DBCursor cursor = dbcollection.find(andQuery);
    	int republicanNegativeCount=cursor.count();
    	System.out.println("Republican with Negative Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int republicanPositiveCount=cursor.count();
    	System.out.println("Republican with Positive Sentiment" + cursor.count());
    	
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",2));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int republicanNeutralCount=cursor.count();
    	System.out.println("Republican with Neutral Sentiment" + cursor.count());
    	obj.clear();
    	
    	JSONObject sentiment = new JSONObject();
    	sentiment.put("party", "Republican");
    	sentiment.put("positive",republicanPositiveCount);
    	sentiment.put("Negative",republicanNegativeCount);
    	sentiment.put("Neutral",republicanNeutralCount);
    
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int democraticNegativeCount = cursor.count();
    	System.out.println("Democratic with Negative Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int democraticPositiveCount =cursor.count();
    	System.out.println("Democratic with Positive Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",2));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int democraticNeutralCount=cursor.count();
    	System.out.println("Democratic with Neutral Sentiment" + cursor.count());
    	obj.clear();
    	JSONObject sentiment1 = new JSONObject();
    	sentiment1.put("party", "Democratic");
    	sentiment1.put("positive",democraticPositiveCount);
    	sentiment1.put("Negative",democraticNegativeCount);
    	sentiment1.put("Neutral",democraticNeutralCount);
    	
    	JSONArray ja = new JSONArray();
    	ja.add(sentiment);
    	ja.add(sentiment1);
    //	mongoClient.close();	
    	
    /*	JSONObject s1 = new JSONObject();
    	s1.put("party", "Republican");
    	JSONObject s2 = new JSONObject();
    	s2.put("value",republicanPositiveCount);
    	JSONObject s3 = new JSONObject();
    	s3.put("value",republicanNegativeCount);
    	JSONObject s4 = new JSONObject();
    	s4.put("value",republicanNeutralCount);
    	
    	JSONObject s5 = new JSONObject();
    	s5.put("party", "Democratic");
    	JSONObject s6 = new JSONObject();
    	s6.put("value",democraticPositiveCount);
    	JSONObject s7 = new JSONObject();
    	s7.put("value",democraticNegativeCount);
    	JSONObject s8 = new JSONObject();
    	s8.put("value",democraticNeutralCount);
    	
    	JSONObject ps = new JSONObject();
    	ps.put("seriesname", "Positive");
    	JSONArray positive = new JSONArray();
    	positive.add(s2);
    	positive.add(s6);
    	ps.put("data", positive);
    	
    	JSONObject ne = new JSONObject();
    	ne.put("seriesname", "Negative");
    	JSONArray Negative = new JSONArray();
    	Negative.add(s3);
    	Negative.add(s7);
    	ne.put("data", Negative);
    	
    	JSONObject nee = new JSONObject();
    	nee.put("seriesname", "Neutral");
    	JSONArray Neutral = new JSONArray();
    	Neutral.add(s4);
    	Neutral.add(s8);
    	nee.put("data", Neutral);
    	
    
      JSONArray result = new JSONArray();
       result.add(ps);
       result.add(ne);
       result.add(nee);
    	
    	
    	return result.toString();
    	
    	*/
    	return ja.toString();
    }
    
    public  String queryWithUserSentimentCount(String username)
    {
    	dbcollection = MongoQuery.createDBCollection();
    	BasicDBObject andQuery = new BasicDBObject();
    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    	
    	obj.add(new BasicDBObject("hashtag", username.toLowerCase()));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	
    	DBCursor cursor = dbcollection.find(andQuery);
    	int NegativeCount = cursor.count();
    	System.out.println("Username " + username+" with Negative Sentiment " + cursor.count());
    	obj.clear();
    	
    	
    	obj.add(new BasicDBObject("hashtag", username.toLowerCase()));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());	
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Username " + username+" with Positive Sentiment " + cursor.count());
    	int PositiveCount = cursor.count();
    	obj.clear();
    	
    	
    	obj.add(new BasicDBObject("hashtag", username.toLowerCase()));
    	obj.add(new BasicDBObject("sentiment",2));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());	
    	cursor = dbcollection.find(andQuery);
    	int NeutralCount = cursor.count();
    	System.out.println("Username " + username+" with Neutral Sentiment " + cursor.count());
    	obj.clear();
    	
    	
    	
    	int total = PositiveCount + NegativeCount + NeutralCount;
    	
    	double ps = ((double)PositiveCount/total)*100;
    	double ns = ((double)NegativeCount/total)* 100;
    	 double nes = ((double)NeutralCount/total) * 100;
    	 System.out.println(total);
    	 System.out.println(ps);
    	 System.out.println(ns);
    	 System.out.println(nes);
    	 
    	 JSONObject result = new JSONObject();
     	result.put("value", ps);
     	JSONObject result2 = new JSONObject();
     	result2.put("value", ns);
     	JSONObject result3 = new JSONObject();
     	result3.put("value", nes);
     	
    	
    	JSONArray finalResult = new JSONArray();
    	finalResult.add(result);
    	finalResult.add(result2);
    	finalResult.add(result3);
    	
    	return finalResult.toString();
    	
    //	return result.toString();
    }
    
    public static void queryByDateforParty()
    {
    	BasicDBObject andQuery = new BasicDBObject();
    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	obj.add(new BasicDBObject("date", "Nov-13-2015"));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	DBCursor cursor = dbcollection.find(andQuery);
    	int r13P=cursor.count();
    	System.out.println("Republican with Positive Sentiment on Nov 13" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	obj.add(new BasicDBObject("date", "Nov-14-2015"));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int r14P=cursor.count();
    	
    	System.out.println("Republican with Positive Sentiment on Nov 14" + cursor.count());
    	obj.clear();
    	
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	obj.add(new BasicDBObject("date", "Nov-13-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int r13N=cursor.count();
    	
    	System.out.println("Republican with Negative Sentiment on Nov 13" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	obj.add(new BasicDBObject("date", "Nov-14-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int r14N=cursor.count();
    	
    	System.out.println("Republican with Negative Sentiment on Nov 14" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",2));
    	obj.add(new BasicDBObject("date", "Nov-13-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int r13Ne=cursor.count();
    	
    	System.out.println("Republican with Neutral Sentiment on Nov 13" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",2));
    	obj.add(new BasicDBObject("date", "Nov-14-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int r14Ne=cursor.count();
    	
    	System.out.println("Republican with Neutral Sentiment on Nov 14" + cursor.count());
    	obj.clear();
    	
    	
    	//Democratic
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	obj.add(new BasicDBObject("date", "Nov-13-2015"));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	 cursor = dbcollection.find(andQuery);
    	int d13P=cursor.count();
    	System.out.println("Democratic with Positive Sentiment on Nov 13" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	obj.add(new BasicDBObject("date", "Nov-14-2015"));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int d14P=cursor.count();
    	
    	System.out.println("Democratic with Positive Sentiment on Nov 14" + cursor.count());
    	obj.clear();
    	
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	obj.add(new BasicDBObject("date", "Nov-13-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int d13N=cursor.count();
    	
    	System.out.println("Democratic with Negative Sentiment on Nov 13" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	obj.add(new BasicDBObject("date", "Nov-14-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int d14N=cursor.count();
    	
    	System.out.println("Democratic with Negative Sentiment on Nov 14" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",2));
    	obj.add(new BasicDBObject("date", "Nov-13-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int d13Ne=cursor.count();
    	
    	System.out.println("Democratic with Neutral Sentiment on Nov 13" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",2));
    	obj.add(new BasicDBObject("date", "Nov-14-2015"));
		
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	int d14Ne=cursor.count();
    	
    	System.out.println("Democratic with Neutral Sentiment on Nov 14" + cursor.count());
    	obj.clear();   	
    }
    
    
    
    	
    
    public static void main(String args[]) throws Exception
    {
    	MongoQuery.createDBCollection();
    	//MongoQuery.queryTotalCount();
    //	MongoQuery.queryWithSentimentCount();
    	String name="HillaryClinton";
    	MongoQuery mongoQuery = new MongoQuery();
    System.out.println(mongoQuery.queryWithUserSentimentCount(name)); 
    	//MongoQuery.queryByDateforParty();
    }

}
