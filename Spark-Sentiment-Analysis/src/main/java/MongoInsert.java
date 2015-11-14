/**
 * Created by Bharat on 11/13/2015.
 */
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoInsert {
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

    public static void insertTweet(BasicDBObject basicdbobject) {
        System.out.println("Entering a new record into mongodatabase");
        dbcollection.insert(basicdbobject);
    }

}
