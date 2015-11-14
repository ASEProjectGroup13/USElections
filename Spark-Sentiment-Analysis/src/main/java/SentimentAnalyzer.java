/**
 * Created by Bharat on 13-Nov-15.
 * Reference : https://github.com/shekhargulati/day20-stanford-sentiment-analysis-demo
 */

import com.mongodb.BasicDBObject;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;


public class SentimentAnalyzer {

    static MongoInsert mongoInsert = new MongoInsert();
    static StanfordCoreNLP pipeline;
    static Properties props;


    public SentimentAnalyzer()
    {
        mongoInsert.createDBCollection();
        createPipeLine();
    }

    public static void createPipeLine()
    {
        if(props==null){
            props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
            pipeline = new StanfordCoreNLP(props);
        }

    }

    public TweetWithSentiment findSentiment(String line, Date date,String hashtag,String party,String loc){

        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }

        TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));

        BasicDBObject tweetObject= new BasicDBObject();
        tweetObject.put("tweet",line);
        tweetObject.put("date",date);
        tweetObject.put("sentiment",mainSentiment);
        tweetObject.put("hashtag",hashtag);
        tweetObject.put("loc",loc);
        tweetObject.put("party",party);
        mongoInsert.insertTweet(tweetObject);

      //  insertintoMongoDB(line,date,mainSentiment,hashtag);
        return tweetWithSentiment;

    }

    public void insertintoMongoDB(String text, Date date, int sentiment,String hashtag)
    {
        try {
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost("https://api.mongolab.com/api/1/databases/twitter_tweets/collections/tweets?apiKey=sGre-i4_owEQssV2EMp19ufegqcZEJ4N");

            JSONObject json = new JSONObject();

        //    json.put("tweet", text);

            String d=date.toString();
            int month = date.getMonth();
            int year = date.getYear();
            String monthYear = month +"-"+year;
            json.put("date", monthYear);

            json.put("sentiment",sentiment);

            json.put("hashtag", hashtag);

            StringEntity input = new StringEntity(json.toString());
            System.out.println(json.toString());

            post.setEntity(input);

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while ((line = rd.readLine()) != null) {

                System.out.println(line);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public int findSentiment(String line,int i) {
    Properties props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            int mainSentiment = 0;
            if (line != null && line.length() > 0) {
                int longest = 0;
                Annotation annotation = pipeline.process(line);
                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                    String partText = sentence.toString();
                    if (partText.length() > longest) {
                        mainSentiment = sentiment;
                        longest = partText.length();
                    }

                }
            }
            if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
                return -1;
            }

            return mainSentiment;


    }

    private String toCss(int sentiment) {
        switch (sentiment) {
            case 0:
                return "sentiment : very negative";
            case 1:
                return "sentiment : negative";
            case 2:
                return "sentiment : neutral";
            case 3:
                return "sentiment : positive";
            case 4:
                return "sentiment : very positive";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
       // SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
     //   TweetWithSentiment tweetWithSentiment = sentimentAnalyzer
                //.findSentiment("click here for your Sachin Tendulkar personalized digital autograph.");
       // System.out.println(tweetWithSentiment);
    }
}
