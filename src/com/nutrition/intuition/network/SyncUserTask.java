package com.nutrition.intuition.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.nutrition.intuition.MainActivity;
import com.nutrition.intuition.model.UserProfile;
import com.nutrition.intuition.model.UserReport;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SyncUserTask extends AsyncTask {

	private Exception exception;
	private DatabaseManager db;
	private UserReport data;
	
	
	// network components.
	private 			ObjectOutputStream 	oos;
	private 			ObjectInputStream 	ois;
	private final int 	PORT 				= 3000;
	
	public void setUserData(UserReport data){
		this.data = data;
	}
	    
	@Override
	protected Object doInBackground(Object... params) {	        	
	  /*
	   * Set up socket and Object stream conections.
	   * The socket connects to the IP address of my home router
	   * and the pre-defined port number 3000.
	   * 
	   */		
	     try{		
	    	 
	    	 Log.v("DEBUG","Creating socket connection and data streams...");
	    	 Socket socket 	= new Socket("192.168.192.46", PORT);
	    	 // Socket socket 	= new Socket(" 10.0.2.2 ", PORT);
	    	 Log.v("DEBUG"," Socket instance created...");

	         oos 			= new ObjectOutputStream(socket.getOutputStream());
	         ois 			= new ObjectInputStream(socket.getInputStream());	
	         		
	     }catch(UnknownHostException e){
	    	 e.printStackTrace();
	     }catch(IOException e){
	         e.printStackTrace();
	     }

         	
         	if(oos != null && ois !=null){
	   			 try{
	   				 
	   				// TODO: Build User Profile here.
	   				Log.v("DEBUG", "Search user data " + MainActivity.currentUser);
	   				
	   					   				 
	   				if(data!=null){
		             Log.v("DEBUG", "User Data found");
	   				}
	   				 	   				 	              	
	      			// Write user details to server and flush stream
	      			oos.writeObject(data);
	      			oos.flush();
	      			
        			String s = null;
        			
        			do{
        				s = (String) ois.readObject();
        			}while(s == null);	
        			
	      			Log.v("DEBUG", "Recieved message " + s );	
	      			
	      			// TODO On success: Display toast here.	      			
	      			oos.close();
	      			ois.close();			// Close the Object streams.
	      			
	      			//publishProgress(1);
	      			
	            } catch(Exception e){
	          	  e.printStackTrace();
	            } 
         	}	        
			return null;
	    }
	
	

	
	
}