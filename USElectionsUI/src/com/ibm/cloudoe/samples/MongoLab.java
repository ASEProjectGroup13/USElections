package com.ibm.cloudoe.samples;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.umkc.dao.MongoQuery;

@Path("/mongo")
public class MongoLab {

	@GET
	@Path("bytweets")
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
	@Path("byname/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String sentimentByCandidate(@PathParam("username") String username)
	{
		MongoQuery mongoQuery = new MongoQuery();
		return mongoQuery.queryWithUserSentimentCount(username);
	}
	
	
	
	
}
