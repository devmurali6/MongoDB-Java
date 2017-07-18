package com.mongodb.M101J.FinalExam;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class CleanUpUtil {

	public static void main(String[] args) {
        
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db=mongoClient.getDatabase("photos");
		MongoCollection<Document> albumColl=db.getCollection("albums");
		MongoCollection<Document> imageColl=db.getCollection("images");
		
		MongoCursor<Document> imageCursor=imageColl.find().iterator();   
		
		
		try{
     	   while(imageCursor.hasNext()){
     		   Document cur=imageCursor.next();
     		  // printJson(cur);
     		   int image_id=cur.getInteger("_id");
     		   Bson filter = new Document("images", image_id);
     		   Bson removefilter = new Document("_id", image_id);

     		  List<Document> all = albumColl.find(filter).into(new ArrayList<Document>());
     		  
     		  if(all.isEmpty()){
     			  
     			  imageColl.deleteOne(removefilter);
     		  }
  
     	   }
        }finally{
        	imageCursor.close();
        	
        }
		
	
	}
	
   

}
