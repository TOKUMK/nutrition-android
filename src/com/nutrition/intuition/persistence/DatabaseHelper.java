package com.nutrition.intuition.persistence;

import com.nutrition.intuition.model.Food;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String 	DATABASE_NAME 			= "NutrtionIntuition";
    private static final int 		DATABASE_VERSION 		= 33;
    
    
	private static final String USER_TABLE      			= "UserTable";
	//public static final String 	KEY_ROWID        			= "_id";
	//public static final String 	KEY_USERNAME     			= "username";
	public static final String 	KEY_AGE     				= "age";
	public static final String 	KEY_GENDER     				= "gender";
	public static final String 	KEY_WEIGHT     				= "weight";
	public static final String 	KEY_HEIGHT     				= "height";
	public static final String 	KEY_DIET    				= "diet";
	public static final String 	KEY_EXERCISE    			= "exercise";
	
	
	private static final String FOOD_TABLE      			= "FoodTable";
	public static final String 	KEY_ROWID        			= "_id";
	public static final String 	KEY_FOODNAME     			= "foodname";
	public static final String 	KEY_EAN     				= "ean";
	public static final String  KEY_CAT						= "cat";
	public static final String 	KEY_SERVINGSIZE     		= "serving_size";
	public static final String 	KEY_CALORIES     			= "calories";
	public static final String 	KEY_TOTALFAT     			= "total_fat";
	public static final String 	KEY_SATFAT     				= "sat_fat";
	public static final String 	KEY_TRANSFAT     			= "trans_fat";
	public static final String 	KEY_CHOLESTERAL     		= "cholesteral";
	public static final String 	KEY_SODIUM    				= "sodium";
	public static final String 	KEY_TOTALCARBS    			= "total_carbs";
	public static final String 	KEY_FIBRECARBS    			= "fibre_carbs";
	public static final String 	KEY_SUGARCARBS    			= "sugar_carbs";
	public static final String 	KEY_PROTEIN    				= "protein";
	public static final String 	KEY_CALCIUM					= "calcium";
	public static final String 	KEY_IRON					= "iron";
	public static final String 	KEY_VITA					= "vit_a";
	public static final String 	KEY_VITB					= "vit_b";
	public static final String 	KEY_VITC					= "vit_c";
	public static final String 	KEY_VITD					= "vit_d";
	public static final String 	KEY_VITE					= "vit_e";
	public static final String 	KEY_VITK					= "vit_k";
	public static final String 	KEY_POTASSIUM				= "potassium";
	public static final String 	KEY_PHOSPHORUS				= "phosphorus";
	public static final String 	KEY_THAIMIN					= "thaimin";
	public static final String 	KEY_RIBOFLAVIN				= "riboflavin";
	public static final String 	KEY_NIACIN					= "niacin";
	public static final String 	KEY_MAGNESIUM				= "magnesium";
	public static final String 	KEY_MANGANESE				= "manganese";
	public static final String 	KEY_ZINC					= "zinc";
	public static final String 	KEY_COPPER					= "copper";
	//public static final String 	KEY_USERNAME     			= "username";

	private static final String SYSTEM_FOOD_TABLE      		= "SystemFoodTable";
	//public static final String 	KEY_ROWID        			= "_id";
	//public static final String 	KEY_FOODNAME     			= "foodname";
	//public static final String 	KEY_EAN     				= "ean";
	//public static final String  KEY_CAT						= "cat";
	//public static final String 	KEY_SERVINGSIZE     		= "serving_size";
	//public static final String 	KEY_CALORIES     			= "calories";
	//public static final String 	KEY_TOTALFAT     			= "total_fat";
	//public static final String 	KEY_SATFAT     				= "sat_fat";
	//public static final String 	KEY_TRANSFAT     			= "trans_fat";
	//public static final String 	KEY_CHOLESTERAL     		= "cholesteral";
	//public static final String 	KEY_SODIUM    				= "sodium";
	//public static final String 	KEY_TOTALCARBS    			= "total_carbs";
	//public static final String 	KEY_FIBRECARBS    			= "fibre_carbs";
	//public static final String 	KEY_SUGARCARBS    			= "sugar_carbs";
	//public static final String 	KEY_PROTEIN    				= "protein";
	//public static final String 	KEY_CALCIUM					= "calcium";
	//public static final String 	KEY_IRON					= "iron";
	//public static final String 	KEY_VITA					= "vit_a";
	//public static final String 	KEY_VITB					= "vit_b";
	//public static final String 	KEY_VITC					= "vit_c";
	//public static final String 	KEY_VITD					= "vit_d";
	//public static final String 	KEY_VITE					= "vit_e";
	//public static final String 	KEY_VITK					= "vit_k";
	//public static final String 	KEY_POTASSIUM				= "potassium";
	//public static final String 	KEY_PHOSPHORUS				= "phosphorus";
	//public static final String 	KEY_THAIMIN					= "thaimin";
	//public static final String 	KEY_RIBOFLAVIN				= "riboflavin";
	//public static final String 	KEY_NIACIN					= "niacin";
	//public static final String 	KEY_MAGNESIUM				= "magnesium";
	//public static final String 	KEY_MANGANESE				= "manganese";
	//public static final String 	KEY_ZINC					= "zinc";
	//public static final String 	KEY_COPPER					= "copper";


	// Food Journal Table
	private static final String FOOD_JOURNAL_TABLE      	= "JournalTable";
	//public static final String 	KEY_ROWID        		= "_id";
	public static final String 	KEY_USERNAME     			= "username";
	//public static final String 	KEY_FOODNAME     		= "foodname";
	public static final String 	KEY_DATE   					= "date";
	//public static final String 	KEY_SERVINGSIZE   			= "serving_size";
	public static final String 	KEY_TIMESTAMP   			= "timestamp";

	// NutriCart table
	private static final String NUTRI_CART_TABLE      		= "NutriCartTable";
	//public static final String 	KEY_ROWID        		= "_id";
	//public static final String 	KEY_USERNAME     			= "username";
	//public static final String 	KEY_FOODNAME     		= "foodname";
	//public static final String 	KEY_SERVINGSIZE   		= "serving_size";

	
    public static final String 		USER_TABLE_CREATE		= "CREATE TABLE " + USER_TABLE + "("
            + KEY_ROWID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT," 
            + KEY_AGE 	+ " INTEGER," + KEY_GENDER + " TEXT,"
            + KEY_WEIGHT + " INTEGER," + KEY_HEIGHT + " INTEGER," 
            + KEY_DIET + " TEXT," + KEY_EXERCISE + " TEXT" + ")";
	
	
	/*// Nutrtion Advice Table
	private static final String NUTRITION_ADVICE_TABLE      	= "AdviceTable";
	//public static final String 	KEY_ROWID        		= "_id";
	//public static final String 	KEY_FOODNAME     			= "foodname";
	public static final String 	KEY_FUNCTION     			= "nutrientFuction";
	public static final String 	KEY_SOURCES     			= "sources";
	
	// Dietary Guidelines Table
	private static final String GUIDELINES_TABLE      			= "GuidelinesTable";
	//public static final String 	KEY_ROWID        			= "_id";
	public static final String 	KEY_DIET_TYPE  					= "diet";
	public static final String 	KEY_INFO     					= "info";*/

    public static final String 		FOOD_TABLE_CREATE	= "CREATE TABLE " + FOOD_TABLE + "("
            + KEY_ROWID + " INTEGER PRIMARY KEY," + KEY_FOODNAME + " TEXT," + KEY_EAN + " STRING," 
            + KEY_SERVINGSIZE 	+ " INTEGER," + KEY_CALORIES + " INTEGER,"
            + KEY_TOTALFAT 	+ " INTEGER," + KEY_SATFAT 	+ " INTEGER,"  
            + KEY_TRANSFAT + " INTEGER," + KEY_CHOLESTERAL + " INTEGER," 
            + KEY_SODIUM + " INTEGER,"
            + KEY_TOTALCARBS + " INTEGER," + KEY_FIBRECARBS + " INTEGER,"
            + KEY_SUGARCARBS + " INTEGER,"  + KEY_PROTEIN + " INTEGER," 
            + KEY_CALCIUM + " INTEGER,"  + KEY_IRON + " INTEGER,"
            + KEY_VITA + " INTEGER," + KEY_VITB + " INTEGER,"
            + KEY_VITC + " INTEGER," + KEY_VITD + " INTEGER,"
            + KEY_VITE + " INTEGER," + KEY_VITK + " INTEGER,"
            + KEY_POTASSIUM + " INTEGER," 	+ KEY_PHOSPHORUS + " INTEGER,"
            + KEY_THAIMIN 	+ " INTEGER," 	+ KEY_RIBOFLAVIN + " INTEGER,"
            + KEY_NIACIN 	+ " INTEGER,"
            + KEY_MAGNESIUM + " INTEGER," 	+ KEY_MANGANESE  + " INTEGER,"
            + KEY_ZINC		+ " INTEGER,"	+ KEY_COPPER	 + " INTEGER," 
            + KEY_CAT + " STRING," + KEY_USERNAME + " STRING" +")";
    
    public static final String 		FOOD_JOURNAL_CREATE	= "CREATE TABLE " + FOOD_JOURNAL_TABLE + "("
            + KEY_ROWID + " INTEGER PRIMARY KEY," + KEY_USERNAME+ " STRING," + KEY_FOODNAME + " STRING," 
            + KEY_SERVINGSIZE 	+ " INTEGER," + KEY_DATE + " STRING," + KEY_TIMESTAMP + " STRING" +  ")";


    public static final String 		NUTRI_CART_CREATE = "CREATE TABLE " + NUTRI_CART_TABLE + "("
            + KEY_ROWID + " INTEGER PRIMARY KEY," + KEY_FOODNAME + " STRING," + KEY_SERVINGSIZE  + " INTEGER," + KEY_USERNAME + " STRING" + ")";
     
    public static final String 		SYSTEM_FOOD_TABLE_CREATE	= "CREATE TABLE " + SYSTEM_FOOD_TABLE + "("
            + KEY_ROWID + " INTEGER PRIMARY KEY," + KEY_FOODNAME + " TEXT," + KEY_EAN + " STRING," 
            + KEY_SERVINGSIZE 	+ " INTEGER," + KEY_CALORIES + " INTEGER,"
            + KEY_TOTALFAT 	+ " INTEGER," + KEY_SATFAT 	+ " INTEGER,"  
            + KEY_TRANSFAT + " INTEGER," + KEY_CHOLESTERAL + " INTEGER," 
            + KEY_SODIUM + " INTEGER,"
            + KEY_TOTALCARBS + " INTEGER," + KEY_FIBRECARBS + " INTEGER,"
            + KEY_SUGARCARBS + " INTEGER,"  + KEY_PROTEIN + " INTEGER," 
            + KEY_CALCIUM + " INTEGER,"  + KEY_IRON + " INTEGER,"
            + KEY_VITA + " INTEGER," + KEY_VITB + " INTEGER,"
            + KEY_VITC + " INTEGER," + KEY_VITD + " INTEGER,"
            + KEY_VITE + " INTEGER," + KEY_VITK + " INTEGER,"
            + KEY_POTASSIUM + " INTEGER," 	+ KEY_PHOSPHORUS + " INTEGER,"
            + KEY_THAIMIN 	+ " INTEGER," 	+ KEY_RIBOFLAVIN + " INTEGER,"
            + KEY_NIACIN 	+ " INTEGER,"
            + KEY_MAGNESIUM + " INTEGER," 	+ KEY_MANGANESE  + " INTEGER,"
            + KEY_ZINC		+ " INTEGER,"	+ KEY_COPPER	 + " INTEGER," 
            + KEY_CAT + " STRING" +")";
     

    public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    

    // Method is called during creation of the database. 
    @Override
    public void onCreate(SQLiteDatabase database) {

                database.execSQL(FOOD_TABLE_CREATE);
                database.execSQL(FOOD_JOURNAL_CREATE);
                database.execSQL(NUTRI_CART_CREATE);
                database.execSQL(SYSTEM_FOOD_TABLE_CREATE);
                database.execSQL(USER_TABLE_CREATE);


                                
                Log.v("DEBUG","Tables now created");
                
                // add foods to system data table.
                Log.v("DEBUG","Calling INIT.");

                initSystemFoods(database);
                preLoadVegtables(database);

    }
    
    public void initSystemFoods(SQLiteDatabase database){
    	//TODO Insert test food into the db..
    	/*process: scan food -> found?
    			N: Not Found! 	Add Manually? -? add and insert to System table AND foodTable
    			Y: Found! 		Return and display data to the user. Give option to add to library.*/
    	
    	//database.();

		ContentValues values = new ContentValues();
		Log.v("DEBUG", "Test Food to be Inserted to DB is Harbios!" );	
		values.put(KEY_FOODNAME,	"Haribos");
		values.put(KEY_EAN,			"4002450223356");
		values.put(KEY_CAT,			"Other");
		values.put(KEY_SERVINGSIZE, 200);
		values.put(KEY_CALORIES, 	710);
		values.put(KEY_PROTEIN, 	0);
		values.put(KEY_TOTALFAT, 	1);
		values.put(KEY_TRANSFAT, 	0);
		values.put(KEY_SATFAT, 		2);
		values.put(KEY_SODIUM,		0);
		values.put(KEY_CHOLESTERAL, 0);
		values.put(KEY_TOTALCARBS, 	168);
		values.put(KEY_FIBRECARBS,	130);
		values.put(KEY_SUGARCARBS,	38);
		values.put(KEY_IRON, 		0);
		values.put(KEY_CALCIUM,		0);
		values.put(KEY_VITA, 		0);
		values.put(KEY_VITB, 		0);
		values.put(KEY_VITC, 		0);
		values.put(KEY_VITD, 		0);
		values.put(KEY_VITE, 		0);
		values.put(KEY_VITK, 		0);
		values.put(KEY_POTASSIUM, 	0);
		values.put(KEY_PHOSPHORUS, 	0);
		values.put(KEY_THAIMIN, 	0);
		values.put(KEY_RIBOFLAVIN, 	0);
		values.put(KEY_NIACIN, 		0);
		values.put(KEY_MAGNESIUM, 	0);
		values.put(KEY_MANGANESE, 	0);
		values.put(KEY_ZINC, 		0);
		values.put(KEY_COPPER, 		0);
		values.put(KEY_CAT, 		"Other");
				
		Log.v("DEBUG", "Inserting new Food Item to SystemFoodTable ");
		database.insert(SYSTEM_FOOD_TABLE, null, values);	
    	
    }
    

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
                   	Log.v("DEBUG", "Upgrading..");
                   	
                    database.execSQL("DROP TABLE IF EXISTS FoodTable");      
                    database.execSQL("DROP TABLE IF EXISTS JournalTable");                   	
                    database.execSQL("DROP TABLE IF EXISTS NutriCartTable");                   	
                    database.execSQL("DROP TABLE IF EXISTS SystemFoodTable");    
                    database.execSQL("DROP TABLE IF EXISTS UserTable");    

                    onCreate(database);
    }
    
    
    	/*
    	 *  System Initialising Transactions
    	 * 
    	 * 
    	 */
    	
    	public long preLoadVegtables(SQLiteDatabase database){
    		
    		ContentValues values = new ContentValues();
    		values.put(KEY_FOODNAME,	"mango");
    		values.put(KEY_EAN,			"N/A");
    		values.put(KEY_CAT,			"Fruit and Vegatables");
    		values.put(KEY_SERVINGSIZE, 165);
    		values.put(KEY_CALORIES, 	107);
    		values.put(KEY_PROTEIN, 	1);
    		values.put(KEY_TOTALFAT, 	0);
    		values.put(KEY_TRANSFAT, 	0);
    		values.put(KEY_SATFAT, 		0);
    		values.put(KEY_SODIUM,		3);
    		values.put(KEY_CHOLESTERAL, 0);
    		values.put(KEY_TOTALCARBS, 	28);
    		values.put(KEY_FIBRECARBS,	3);
    		values.put(KEY_SUGARCARBS,	24);
    		values.put(KEY_IRON, 		1);
    		values.put(KEY_CALCIUM,		2);
    		values.put(KEY_VITA, 		1);
    		values.put(KEY_VITB, 		0);
    		values.put(KEY_VITC, 		1);
    		values.put(KEY_VITD, 		0);
    		values.put(KEY_VITE, 		0);
    		values.put(KEY_VITK, 		0);
    		values.put(KEY_POTASSIUM, 	0);
    		values.put(KEY_PHOSPHORUS, 	0);
    		values.put(KEY_THAIMIN, 	0);
    		values.put(KEY_RIBOFLAVIN, 	0);
    		values.put(KEY_NIACIN, 		0);
    		values.put(KEY_MAGNESIUM, 	0);
    		values.put(KEY_MANGANESE, 	0);
    		values.put(KEY_ZINC, 		0);
    		values.put(KEY_COPPER, 		0);
    				
    		Log.v("DEBUG ENTRY", "Inserting new Food Item to FoodTable Mango");
    		return database.insert(FOOD_TABLE, null, values);	
    		
    	
    		
    }

}