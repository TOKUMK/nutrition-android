package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.List;

import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.UserProfile;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class UserReg extends Activity {
	
	private TextView userName, userAge, userHeight, userWeight;
	private Spinner spinnerGender, spinnerDiet, spinnerExercise;
	private UserProfile user;
	private DatabaseManager db;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_reg);
       
		ActionBar actionBar = getActionBar();
        actionBar.show();
        setFonts();
        mapWidgets();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.new_user_actions, menu);
	    return true;
	 } 
	
	 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    	Intent intent;
	    	db = new DatabaseManager(this);
	    	
	        // Handle item selection
	        switch (item.getItemId()) {
	       
	        case R.id.done:
	        	
	        	if(validate() == false){
	        		validate();
	        	}else{
		        	getValues();

	        		Log.v("DEBUG", "Registering the user with name " + user.getName());

	        		
	        	if(db.searchUsername(user.getName())){
		            	Toast.makeText(this," The user name" + user.getName() + " is already taken..", Toast.LENGTH_SHORT).show();
		        	}else{
		        		
	        			//Log.v("DEBUG", "REGISTER " + user.getName());

			        	db.registerProfile(user);
			        	// TODO: close database connection.
			        	

			        	intent = new Intent(this, MainActivity.class);
			        	String nametoken = user.getName();
						intent.putExtra("Username", nametoken );
						
		            	Toast.makeText(this," You are now Registered as" + user.getName() + " !", Toast.LENGTH_SHORT).show();
				 		
		            	MainActivity.callingActivity ="Reg";

			        	startActivity(intent);
			        	
			        	
		        	}
	        	}
	        	
	        	
	        	
	        	
	        	//Log.v("DEBUG", user.toString());
	        	// todo	Validate, database transaction.       	
	            return true;        
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	
	public void mapWidgets(){
		
		userName 	= (TextView) findViewById(R.id.userName);
		userAge 	= (TextView) findViewById(R.id.userAge);
		userHeight  = (TextView) findViewById(R.id.userHeight);
		userWeight  = (TextView) findViewById(R.id.userWeight);
	
		spinnerGender 	= (Spinner) findViewById(R.id.spinner1);
		spinnerDiet 	= (Spinner) findViewById(R.id.spinner2);
		spinnerExercise = (Spinner) findViewById(R.id.spinner3);
	}
	 
	 
	public void getValues(){

		user = new UserProfile();	
		user.setName(userName.getText().toString().toLowerCase());
		user.setGender(spinnerGender.getSelectedItem().toString());
 
		user.setAge(Integer.parseInt(userAge.getText().toString())); // string to int..
		user.setWeight(Integer.parseInt(userWeight.getText().toString())); 
		user.setHeight(Integer.parseInt(userHeight.getText().toString()));
		user.setDiet(spinnerDiet.getSelectedItem().toString());
		user.setExercise(spinnerExercise.getSelectedItem().toString());
    }
	
	public boolean validate(){			
		int i = 0;
		
		if(userName.getText().toString().equals("")){
			Toast.makeText(this, "Please enter a User Name", Toast.LENGTH_SHORT).show();
			i++;
		}
		else if(userAge.getText().toString().equals("")){
			Toast.makeText(this, "Please enter your Age", Toast.LENGTH_SHORT).show();
			i++;
		}else if(userHeight.getText().toString().equals("")){
			Toast.makeText(this, "Please enter your Height", Toast.LENGTH_SHORT).show();
			i++;
		}else if(userWeight.getText().toString().equals("")){
			Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show();
			i++;
		}else if(spinnerGender.getSelectedItem().toString().equals("")){
			Toast.makeText(this, "Please enter your Gender", Toast.LENGTH_SHORT).show();
			i++;
		}else if(spinnerExercise.getSelectedItem().toString().equals("")){
			Toast.makeText(this, "Please enter a Exercise routine", Toast.LENGTH_SHORT).show();
			i++;
		}else  if(spinnerDiet.getSelectedItem().toString().equals("")){
			Toast.makeText(this, "Please enter a Diet type", Toast.LENGTH_SHORT).show();
			i++;
		}
			
		if(i > 0)
			{return false;}
		else
			{return true;}
	}
	
	
	public void setFonts(){
		// Set font
		TextView tvHeader = (TextView) findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/QuaverSans.otf");	
				//"fonts/LeagueGothicRegular.otf");
	    tvHeader.setTypeface(tf);	    
	}
}