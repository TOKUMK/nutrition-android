package com.nutrition.intuition;

import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private Button 			login;
	private Button 			createProfile;
	private	EditText		username;
	private DatabaseManager db;
	private String			nametoken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		getActionBar().hide();
		db = new DatabaseManager(this);
		
		login 			= (Button) findViewById(R.id.login);
		login.setOnClickListener(this);
		createProfile 	= (Button) findViewById(R.id.userReg);
		createProfile.setOnClickListener(this);	
		username		= (EditText) findViewById(R.id.username);

		
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}*/
	

	public boolean checkLogin(String username){
//		String uname = username.getText().toString().toLowerCase();
		
		
		String uname= username;
	  	Log.v("DEBUG LOGIN","checkong db for uname" + uname);
	  	
	  	if(!db.searchUsername(uname)){
		  	Log.v("DEBUG LOGIN","username does not exist" + uname);
			Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
			return false;

	  	}else nametoken = uname;
		  	Log.v("DEBUG LOGIN","username does exist and continue login with this name passed intently." + uname);
	  		return true;
	}
	
	@Override
	public void onClick(View view) {
		
		Intent intent;
		
		 switch(view.getId()){
		  case R.id.login: /** Start a new Activity LoginActivity.java */
			  	String uname = username.getText().toString().toLowerCase();
			  	if(checkLogin(uname) == false){
			  		checkLogin(uname);
			  	}else{
			 		Log.v("DEBUG NAME","Name token value is Home Page " + nametoken);

			 		MainActivity.callingActivity ="Login";
				  	intent = new Intent(this, MainActivity.class);
					intent.putExtra("Username", nametoken );
				  	this.startActivity(intent); 
			  	}

		      break;

		  case R.id.userReg: /** Start a new Activity About.java */
			  
			  

			  
			  intent = new Intent(this, UserReg.class);
			  this.startActivity(intent);
		      break;
		      

		  } 
		
	}

	
}