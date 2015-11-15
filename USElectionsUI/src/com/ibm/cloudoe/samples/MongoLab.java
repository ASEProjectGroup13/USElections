package com.ibm.cloudoe.samples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.umkc.dao.MongoQuery;

@Path("/mongo")
public class MongoLab {

	@GET
	@Path("byTweets")
	public String byTweets()
	{
		MongoQuery mongoQuery = new MongoQuery();
		return mongoQuery.queryTotalCount();
		
	}
	
	@GET
	@Path("byparty")
	public String senimentsByParty()
	{
		
		MongoQuery mongoQuery = new MongoQuery();
		return mongoQuery.queryWithSentimentCount();
	}
	
	@GET
	@Path("byName")
	public String sentimentByCandidate(String username)
	{
		MongoQuery mongoQuery = new MongoQuery();
		return mongoQuery.queryWithUserSentimentCount(username);
	}
}
