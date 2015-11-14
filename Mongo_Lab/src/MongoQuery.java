/**
 * Created by Bharat on 11/13/2015.
 */
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import org.json.simple.JSONObject;


public class MongoQuery {
	public static MongoClient mongoClient;
    public static DBCollection dbcollection;

    public static void createDBCollection() {

        if(mongoClient == null){
            MongoClientURI mongoClientUri = new MongoClientURI("mongodb://bharat:bharat@ds053954.mongolab.com:53954/twitter_tweets");
            mongoClient = new MongoClient(mongoClientUri);
            DB db = mongoClient.getDB(mongoClientUri.getDatabase());
            dbcollection = db.getCollection("tweets");
        }
        else
            return;
    }

    public static void queryTotalCount()
    {	
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
  
    }
    
    public static void queryWithSentimentCount()
    {
    	BasicDBObject andQuery = new BasicDBObject();
    	JSONObject json = new JSONObject();
    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	DBCursor cursor = dbcollection.find(andQuery);
    	System.out.println("Republican with Negative Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Republican with Positive Sentiment" + cursor.count());
    	
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Republican"));
    	obj.add(new BasicDBObject("sentiment",2));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Republican with Neutral Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Democratic with Negative Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Democratic with Positive Sentiment" + cursor.count());
    	obj.clear();
    	
    	obj.add(new BasicDBObject("party", "Democratic"));
    	obj.add(new BasicDBObject("sentiment",2));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Democratic with Neutral Sentiment" + cursor.count());
    	obj.clear();
    }
    
    public static void queryWithUserSentimentCount(String username)
    {
    	BasicDBObject andQuery = new BasicDBObject();
    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    	
    	obj.add(new BasicDBObject("hashtag", username));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$lt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());
    	
    	DBCursor cursor = dbcollection.find(andQuery);
    	System.out.println("Username " + username+" with Negative Sentiment " + cursor.count());
    	obj.clear();
    	
    	
    	obj.add(new BasicDBObject("hashtag", username));
    	obj.add(new BasicDBObject("sentiment",new BasicDBObject("$gt",2)));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());	
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Username " + username+" with Positive Sentiment " + cursor.count());
    	obj.clear();
    	
    	
    	obj.add(new BasicDBObject("hashtag", username));
    	obj.add(new BasicDBObject("sentiment",2));
    	andQuery.put("$and", obj);
    	System.out.println(andQuery.toString());	
    	cursor = dbcollection.find(andQuery);
    	System.out.println("Username " + username+" with Positive Sentiment " + cursor.count());
    	obj.clear();
    	
    	
    	
    }
    
    
    
    public static void main(String args[]) throws Exception
    {
    	MongoQuery.createDBCollection();
    	MongoQuery.queryTotalCount();
    	MongoQuery.queryWithSentimentCount();
    	String name="HillaryClinton";
    	MongoQuery.queryWithUserSentimentCount(name);
    }

}
