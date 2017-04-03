package com.nutrition.intuition.persistence;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.JournalEntry;
import com.nutrition.intuition.model.UserProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager{  

	private DatabaseHelper 		dbHelper;  
	private SQLiteDatabase 		database;  
	
	private static final String USER_TABLE      		= "UserTable";
	public static final String 	KEY_ROWID        		= "_id";
	public static final String 	KEY_USERNAME     		= "username";
	public static final String 	KEY_AGE     			= "age";
	public static final String 	KEY_GENDER     			= "gender";
	public static final String 	KEY_WEIGHT     			= "weight";
	public static final String 	KEY_HEIGHT     			= "height";
	public static final String 	KEY_DIET    			= "diet";
	public static final String 	KEY_EXERCISE    		= "exercise";
	
	private static final String FOOD_TABLE      			= "FoodTable";
	//public static final String 	KEY_ROWID        		= "_id";
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
	
	private static final String SYSTEM_FOOD_TABLE      		= "SystemFoodTable";
	//public static final String 	KEY_ROWID        			= "_id";
	//public static final String 	KEY_FOODNAME     			= "foodname";
	//public static final String 	KEY_EAN     				= "ean";
	//public static final String    KEY_CAT						= "cat";
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
	//public static final String 	KEY_USERNAME     			= "username";
	public static final String 	KEY_DATE   					= "date";
	//public static final String 	KEY_SERVINGSIZE   			= "serving_size";
	public static final String 	KEY_TIMESTAMP   			= "timestamp";

	// NutriCart table
	private static final String NUTRI_CART_TABLE      		= "NutriCartTable";
	//public static final String 	KEY_USERNAME     			= "username";
	//public static final String 	KEY_ROWID        		= "_id";
	//public static final String 	KEY_FOODNAME     			= "foodname";
	//public static final String 	KEY_SERVINGSIZE   			= "serving_size";
	


	public DatabaseManager(Context context){  
		dbHelper = new DatabaseHelper(context);  
		database = dbHelper.getWritableDatabase();  
	}

	
	/*
	 * 	FOOD TABLE DATABASE METHODS
	 * 		1. addNewFood: 			Adds new food item to table/
	 * 		2. Search EAN: 			Returns true ? false based on Value found.
	 * 		3. selectFood: 			selects food (Based on the EAN).
	 * 		4. selectFood: 			selects food (Based on the Name & Serving Size).
	 * 					   			this is used to prevent duplicate entries.
	 * 		5. selectFoodsFromCat:	searchs for food item by category.
	 */
	public long addNewFoodItem(Food f, String username){
		Food food 	= new Food();
		food 		= f;
		ContentValues values = new ContentValues();
		Log.v("DEBUG", "Food to be Inserted to DB is " + food.toString());	
		values.put(KEY_FOODNAME,	food.getName().toLowerCase());
		values.put(KEY_EAN,			food.getEan());
		values.put(KEY_CAT,			food.getCategory());
		values.put(KEY_SERVINGSIZE, food.getServingSize());
		values.put(KEY_CALORIES, 	food.getCalories());
		values.put(KEY_PROTEIN, 	food.getProtein());
		values.put(KEY_TOTALFAT, 	food.getTotalFat());
		values.put(KEY_TRANSFAT, 	food.getSatFat());
		values.put(KEY_SATFAT, 		food.getTransFat());
		values.put(KEY_SODIUM,		food.getSodium());
		values.put(KEY_CHOLESTERAL, food.getCholesteral());
		values.put(KEY_TOTALCARBS, 	food.getTotalCarbs());
		values.put(KEY_FIBRECARBS,	food.getDietaryFibre());
		values.put(KEY_SUGARCARBS,	food.getSugars());
		values.put(KEY_IRON, 		food.getIron());
		values.put(KEY_CALCIUM,		food.getCalcium());
		values.put(KEY_VITA, 		food.getVitA());
		values.put(KEY_VITB, 		food.getVitB());
		values.put(KEY_VITC, 		food.getVitC());
		values.put(KEY_VITD, 		food.getVitD());
		values.put(KEY_VITE, 		food.getVitE());
		values.put(KEY_VITK, 		food.getVitK());
		values.put(KEY_POTASSIUM, 	food.getPotassium());
		values.put(KEY_PHOSPHORUS, 	food.getPhosphorus());
		values.put(KEY_THAIMIN, 	food.getThaimin());
		values.put(KEY_RIBOFLAVIN, 	food.getRiboflavin());
		values.put(KEY_NIACIN, 		food.getNiacin());
		values.put(KEY_MAGNESIUM, 	food.getMagnesium());
		values.put(KEY_MANGANESE, 	food.getManganese());
		values.put(KEY_ZINC, 		food.getZinc());
		values.put(KEY_COPPER, 		food.getCopper());
		values.put(KEY_USERNAME,    username);
				
		Log.v("DEBUG ", "Inserting new Food Item to FoodTable " + food.getName());
		return database.insert(FOOD_TABLE, null, values);	
	}
	
	// Search Database for a specific ean number
	// EAN must be of string for query
	// if found the string is returned to its original type: long.
	public boolean searchEAN(String ean){
		
		//String sEan = String.valueOf(ean);
		String sEan = "";
		//Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_TABLE + " WHERE " + KEY_EAN + " = ?", new String[]{ean});
		
		Cursor cursor = database.rawQuery("SELECT * FROM " + SYSTEM_FOOD_TABLE + " WHERE " + KEY_EAN + " = ?", new String[]{ean});
		
		Food food = new Food();

		if(cursor.moveToFirst()){
			do{
				food.setName(cursor.getString(1));
				sEan = cursor.getString(2);;
				
			}while(cursor.moveToNext());
		} 
		boolean flag;
		if(sEan.equals(ean)){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	// Search SYSTEM_FOOD_TABLE for food based on ean number
	public Food selectFood(String ean){			
		Cursor cursor = database.rawQuery("SELECT * FROM " + SYSTEM_FOOD_TABLE + " WHERE " + KEY_EAN + " = ?", new String[]{ean});		
		Food food = new Food();
		
		if(cursor.moveToFirst()){
			do{
				//Log.v("DEBUG", "DATABASE: Cursor has data.");
			    // assuming row id = getString(0)
				food.setName(cursor.getString(1));				
				//Log.v("DEBUG", "DATABASE: Object name in Cursor" + food.getName() );

				
				food.setEan(cursor.getString(2));
				//Log.v("DEBUG", "DATABASE: Object EAN" + food.getEan() );

				food.setServingSize(Integer.parseInt(cursor.getString(3)));
				//Log.v("DEBUG", "DATABASE: Object sSize" + food.getServingSize() );

				food.setCalories(Integer.parseInt(cursor.getString(4)));
				//Log.v("DEBUG", "DATABASE: Object calories" + food.getCalories() );

				food.setTotalFat(Integer.parseInt(cursor.getString(5)));
				//Log.v("DEBUG", "DATABASE: Object total fat" + food.getTotalFat() );

				food.setSatFat(Integer.parseInt(cursor.getString(6)));
				//Log.v("DEBUG", "DATABASE: Object sat fat" + food.getSatFat() );

				food.setTransFat(Integer.parseInt(cursor.getString(7)));
				//Log.v("DEBUG", "DATABASE: Object trans fat" + food.getTransFat() );

				food.setCholesteral(Integer.parseInt(cursor.getString(8)));
				//Log.v("DEBUG", "DATABASE: Object cholesteral" + food.getCholesteral() );

				food.setSodium(Integer.parseInt(cursor.getString(9)));
				//Log.v("DEBUG", "DATABASE: Object sodium" + food.getSodium() );

				food.setTotalCarbs(Integer.parseInt(cursor.getString(10)));
				//Log.v("DEBUG", "DATABASE: Object total carbs" + food.getTotalCarbs() );

				food.setDietaryFibre(Integer.parseInt(cursor.getString(11)));
				//Log.v("DEBUG", "DATABASE: Object fibre" + food.getDietaryFibre() );
				
				

				food.setSugars(Integer.parseInt(cursor.getString(12)));
				food.setProtein(Integer.parseInt(cursor.getString(13)));
				food.setCalcium(Integer.parseInt(cursor.getString(14)));
				food.setIron(Integer.parseInt(cursor.getString(15)));
				food.setVitA(Integer.parseInt(cursor.getString(16)));
				food.setVitB(Integer.parseInt(cursor.getString(17)));
				food.setVitC(Integer.parseInt(cursor.getString(18)));
				food.setVitD(Integer.parseInt(cursor.getString(19)));
				food.setVitE(Integer.parseInt(cursor.getString(20)));
				food.setVitK(Integer.parseInt(cursor.getString(21)));

				food.setPotassium(Integer.parseInt(cursor.getString(22)));
				food.setPhosphorus(Integer.parseInt(cursor.getString(23)));
				food.setThaimin(Integer.parseInt(cursor.getString(24)));
				food.setRiboflavin(Integer.parseInt(cursor.getString(25)));
				food.setNiacin(Integer.parseInt(cursor.getString(26)));
				food.setMagnesium(Integer.parseInt(cursor.getString(27)));
				food.setManganese(Integer.parseInt(cursor.getString(28)));
				food.setZinc(Integer.parseInt(cursor.getString(29)));
				food.setCopper(Integer.parseInt(cursor.getString(30)));	
				food.setCategory(cursor.getString(31));
			}while(cursor.moveToNext());
		} 
		
		//food.setEan(Long.parseLong(sEan));

		
	//	Return food 
		return food;
		
	}
	
	
	// Search Database for food based on FoodName and ServingSize
	public Food selectFood(String foodName, int sSize){
		int servingSize = sSize; 
		Log.v("DEBUG", "DATABASE:  Searching by name ssize");
		// todo query with sSize.
		Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_TABLE + " WHERE " + 
        		KEY_FOODNAME + " = ? AND  " + KEY_SERVINGSIZE + " = ? ", new String[] { foodName.toLowerCase(), Integer.toString(servingSize)});
		
		Food food = new Food();
		
		if(cursor.moveToFirst()){
			do{
			    // row id = getString(0)
				food.setName(cursor.getString(1));				
				food.setEan(cursor.getString(2));
				food.setServingSize(Integer.parseInt(cursor.getString(3)));
				food.setCalories(Integer.parseInt(cursor.getString(4)));
				food.setTotalFat(Integer.parseInt(cursor.getString(5)));
				food.setSatFat(Integer.parseInt(cursor.getString(6)));
				food.setTransFat(Integer.parseInt(cursor.getString(7)));
				food.setCholesteral(Integer.parseInt(cursor.getString(8)));
				food.setSodium(Integer.parseInt(cursor.getString(9)));
				food.setTotalCarbs(Integer.parseInt(cursor.getString(10)));
				food.setDietaryFibre(Integer.parseInt(cursor.getString(11)));				
				food.setSugars(Integer.parseInt(cursor.getString(12)));
				food.setProtein(Integer.parseInt(cursor.getString(13)));
				food.setCalcium(Integer.parseInt(cursor.getString(14)));
				food.setIron(Integer.parseInt(cursor.getString(15)));
				food.setVitA(Integer.parseInt(cursor.getString(16)));
				food.setVitB(Integer.parseInt(cursor.getString(17)));
				food.setVitC(Integer.parseInt(cursor.getString(18)));
				food.setVitD(Integer.parseInt(cursor.getString(19)));
				food.setVitE(Integer.parseInt(cursor.getString(20)));
				food.setVitK(Integer.parseInt(cursor.getString(21)));
				food.setPotassium(Integer.parseInt(cursor.getString(22)));
				food.setPhosphorus(Integer.parseInt(cursor.getString(23)));
				food.setThaimin(Integer.parseInt(cursor.getString(24)));
				food.setRiboflavin(Integer.parseInt(cursor.getString(25)));
				food.setNiacin(Integer.parseInt(cursor.getString(26)));
				food.setMagnesium(Integer.parseInt(cursor.getString(27)));
				food.setManganese(Integer.parseInt(cursor.getString(28)));
				food.setZinc(Integer.parseInt(cursor.getString(29)));
				food.setCopper(Integer.parseInt(cursor.getString(30)));	
				food.setCategory(cursor.getString(31));
			}while(cursor.moveToNext());
		} 	
		return food;
		
	}
	
	// Checks for food entry based on <FoodName, ServingSize>
	public boolean checkForDuplicates(String foodName, int sSize){
		
		int servingSize = sSize; 
		boolean found;
		
		Log.v("DEBUG", "DATABASE:  Searching by name ssize");
		
		// todo query with sSize.
		Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_TABLE + " WHERE " + 
        		KEY_FOODNAME + " = ? AND  " + KEY_SERVINGSIZE + " = ? ", new String[] { foodName.toLowerCase(), Integer.toString(servingSize)});

		if(cursor.moveToFirst()){
			found = true;
		}else{
			found = false;
		}
		return found;
	}
	
    // Returns  list of food items that are associated with Category.
	public ArrayList<Food> selectFoodCat(String catType, String uname){
			
			Food food;
			Cursor cursor = null;
			ArrayList<Food> list = new ArrayList<Food>();
			
			Log.v("DEBUG", "SelectFood() with CAT" + catType);
			
			cursor = database.rawQuery("SELECT * FROM " + FOOD_TABLE + " WHERE " + KEY_CAT + " = ? AND " + KEY_USERNAME + " = ?", new String[]{catType, uname});
			
			if(cursor.moveToFirst()){
				do{
					food = new Food();
					food.setName(cursor.getString(1));
					food.setServingSize(Integer.parseInt(cursor.getString(3)));
					list.add(food);
				}while(cursor.moveToNext());
			} 
			return list; 
	}
	
	
	/*
	 *  Food Journal Database methods:
	 * 			1. Journal entry: JOURNAL TABLE <Name, Serving Size, Time>
	 * 			2. Remove an Entry:
	 * 			3. Return a Food Data object from Journal 
	 * 			4. Return number of entries in Journal
	 */
	
	public long addFoodToJournal(Food f, String date, String time, String uname){
		
		String fName = f.getName();
		int    sSize = f.getServingSize();
		String sTime = time;
		String sName = uname;
		
    	Log.v("DEBUG JOURNAL", "DATABASE ENTRY " + fName + " " + sSize + " " + sTime + " " + sName);

		ContentValues values = new ContentValues();	
		Log.v("DEBUG", "Inserting new Food Item to Food Journal");
		values.put(KEY_FOODNAME ,	fName.toLowerCase());
		values.put(KEY_DATE ,		date);
		values.put(KEY_SERVINGSIZE, sSize);
		values.put(KEY_TIMESTAMP, 	sTime);
		values.put(KEY_USERNAME, 	sName);

		
		Log.v("DEBUG JOURNAL", "COMPLETING DATABASE TRANSACTION FOR NEW JOURNAL ENTRY");

		return database.insert(FOOD_JOURNAL_TABLE, null, values);
	}
	
    // Return Journal Entry object that was selected on list view in food journal
	public ArrayList<JournalEntry> selectFromJournal(String date, String uname){
			
			JournalEntry entry;
			ArrayList<JournalEntry> list = new ArrayList<JournalEntry>();
			
			Log.v("DEBUG", "Searching foods by Date - " + date);
			Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_JOURNAL_TABLE + " WHERE " + KEY_DATE + " = ? AND "
					+ KEY_USERNAME + " = ? ", new String[]{date, uname });
						
/*			Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_JOURNAL_TABLE + " WHERE " + KEY_DATE + " = ? " , new String[]{date});
*/			if(cursor.moveToFirst()){
				
				do{
					entry = new JournalEntry();

					entry.setFoodName(cursor.getString(2));
					Log.v("DEBUG", "FOOD NAME IS " + cursor.getString(2));
					entry.setServingSize(cursor.getInt(3));
					Log.v("DEBUG", "FOOD NAME IS " + entry.getServingSize());
					entry.setTime(cursor.getString(5));
					Log.v("DEBUG", "FOOD NAME IS " + entry.getTime());
					list.add(entry);
				}while(cursor.moveToNext());
			} 

			return list; 
	}
	
	public long deleteFromJournal(String name, int servingSize, String time) {
					
		return database.delete(FOOD_JOURNAL_TABLE, KEY_FOODNAME + " = ? AND " + KEY_SERVINGSIZE + " = ? AND "+ KEY_TIMESTAMP + " = ?" , 
			            new String[] {name.toLowerCase(), Integer.toString(servingSize), time});
	}
	
	
	// Return all food objects entered into <Date> of Journal
	// Used to calculate totals for nutrition reporting
	// Return food item object that was selected on listedview in food journal
	public ArrayList<Food> selectFoodsForReport(String date, String uname){
				
				ArrayList<Food>  		 foodList	= new ArrayList<Food>();
				ArrayList<JournalEntry>	 entries 	= new ArrayList<JournalEntry>();
				Food 			food;
				JournalEntry 	entry;
				
				Log.v("DEBUG JOURNAL", "Searching for foods entered in Journal on date " + date);
				
				// Get list of all journal entry objects for <Date>
				Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_JOURNAL_TABLE + " WHERE " + KEY_DATE + " = ? AND " + KEY_USERNAME + " = ?", new String[]{date, uname});
				
				int z = 0;
				// for each row in cursor
				// construct the Journal Entry Object.
				if(cursor.moveToFirst()){
					
					Log.v("DEBUG JOURNAL", "Retrieving Journal entry object ");
					
					
					do{
						entry = new JournalEntry();	
						entry.setFoodName(cursor.getString(2));
						entry.setServingSize(cursor.getInt(3));
						entries.add(entry);
						Log.v("DEBUG JOURNAL", " Journal Entry added to the List is  " + entries.get(z).toString());
						z++;
						}while(cursor.moveToNext());
				} 
				
							
				
				// Query the Food Table for Food items
				// that have <Name, ServingSize> matching each journal entry.
				Log.v("DEBUG", "Retrieving Food data relating to Journal Entries...");
				Log.v("DEBUG", "size of Journal Entries..." + entries.size());

				for(int i = 0; i < entries.size(); i++){
					// The following code will query the FoodTable, for food items
					// matching <Name, Date> of each entry in the Journal Entries List.
					String foodName = entries.get(i).getFoodName();
					int servingSize = entries.get(i).getServingSize();
					
					Log.v("DEBUG", "RawQuery uses..." + foodName + servingSize);
	
					cursor = database.rawQuery("SELECT * FROM " + FOOD_TABLE + " WHERE " + 
			        		KEY_FOODNAME + " = ? AND " + KEY_SERVINGSIZE + " = ? ", new String[] { foodName, Integer.toString(servingSize)});
					
					if(cursor.moveToFirst()){
						do{
							food = new Food();
						    // row id = getString(0)
							food.setName(cursor.getString(1));				
							food.setEan(cursor.getString(2));
							food.setServingSize(Integer.parseInt(cursor.getString(3)));
							food.setCalories(Integer.parseInt(cursor.getString(4)));
							food.setTotalFat(Integer.parseInt(cursor.getString(5)));
							food.setSatFat(Integer.parseInt(cursor.getString(6)));
							food.setTransFat(Integer.parseInt(cursor.getString(7)));
							food.setCholesteral(Integer.parseInt(cursor.getString(8)));
							food.setSodium(Integer.parseInt(cursor.getString(9)));
							food.setTotalCarbs(Integer.parseInt(cursor.getString(10)));
							food.setDietaryFibre(Integer.parseInt(cursor.getString(11)));				
							food.setSugars(Integer.parseInt(cursor.getString(12)));
							food.setProtein(Integer.parseInt(cursor.getString(13)));
							food.setCalcium(Integer.parseInt(cursor.getString(14)));
							food.setIron(Integer.parseInt(cursor.getString(15)));
							food.setVitA(Integer.parseInt(cursor.getString(16)));
							food.setVitB(Integer.parseInt(cursor.getString(17)));
							food.setVitC(Integer.parseInt(cursor.getString(18)));
							food.setVitD(Integer.parseInt(cursor.getString(19)));
							food.setVitE(Integer.parseInt(cursor.getString(20)));
							food.setVitK(Integer.parseInt(cursor.getString(21)));
							food.setPotassium(Integer.parseInt(cursor.getString(22)));
							food.setPhosphorus(Integer.parseInt(cursor.getString(23)));
							food.setThaimin(Integer.parseInt(cursor.getString(24)));
							food.setRiboflavin(Integer.parseInt(cursor.getString(25)));
							food.setNiacin(Integer.parseInt(cursor.getString(26)));
							food.setMagnesium(Integer.parseInt(cursor.getString(27)));
							food.setManganese(Integer.parseInt(cursor.getString(28)));
							food.setZinc(Integer.parseInt(cursor.getString(29)));
							food.setCopper(Integer.parseInt(cursor.getString(30)));	
							food.setCategory(cursor.getString(31));	
							foodList.add(food);
						}while(cursor.moveToNext());
	
					}
				}
				
				
				return foodList;	 
		}
	
	public int getSizeJournal(String uname){
		int size;
		Cursor cursor = database.rawQuery("SELECT * FROM " + FOOD_JOURNAL_TABLE  + " WHERE " + KEY_USERNAME + " = ?", new String[]{uname}, null);			
		size = cursor.getCount();
		Log.v("DEBUG NUTRI", "Journal Cursor count is " + size);
		return size;
	}
		
		/*
		 * 	NUTRI-CART DATABASE METHODS
		 * 		1.  addToCart: 			Adds new food item to the NutriCart Table
		 * 		2. loadNutriCart: 		Returns all foods with <foodName, servingSize> from nutriCart.
		 * 		3. getSizeNutriCart		Return number of items in cart.
		 */
		
		public long addToNutriCart(String foodName, int servingSize, String uname){
			String fName = foodName;
			int    sSize = servingSize;
			String sTime = "Test";

			ContentValues values = new ContentValues();	
			Log.v("DEBUG", "Inserting new Food Item to Food Journal");
			values.put(KEY_FOODNAME ,	fName.toLowerCase());
			values.put(KEY_SERVINGSIZE, sSize);
			values.put(KEY_USERNAME, uname.toLowerCase());
			
			return database.insert(NUTRI_CART_TABLE, null, values);
		}
		
		
	public ArrayList<Food> loadNutriCart(String uname){
			ArrayList<Food>  		 foodList	= new ArrayList<Food>();
			Food 			food;
			
			Log.v("DEBUG", "Searching for foods in nutricart ");
			
			// Get list of all journal entry objects for <Date>
			//Cursor cursor = database.rawQuery("SELECT * FROM " + NUTRI_CART_TABLE , null);
			
			Cursor cursor = database.rawQuery("SELECT * FROM " + NUTRI_CART_TABLE + " WHERE " + 
	        		KEY_USERNAME + " = ? ", new String[]{uname});
			
			// for each row in cursor
			// construct the Journal Entry Object.
			if(cursor.moveToFirst()){
				Log.v("DEBUG CART", "Getting NutriCartEntries");
				do{
					food = new Food();
					food.setName(cursor.getString(1));
					food.setServingSize(Integer.parseInt(cursor.getString(2)));
					foodList.add(food);
					}while(cursor.moveToNext());
			} 
			return foodList;
	}
	
	public int getSizeNutriCart(String uname){
		
		int size;
		Cursor cursor = database.rawQuery("SELECT * FROM " + NUTRI_CART_TABLE + " WHERE " + 
        		KEY_USERNAME + " = ?", new String[]{uname});			
		// for each row in cursor
		// construct the Journal Entry Object.
		
			size = cursor.getCount();
		
		Log.v("DEBUG NUTRI", "Cursor count / table size is " + size);
		return size;
	}
		
	// Deletes a food item from nutri cart
	public long deleteFromCart(String name, int servingSize) {
			
		return database.delete(NUTRI_CART_TABLE, KEY_FOODNAME + " = ? AND " + KEY_SERVINGSIZE + " = ?", 
			            new String[] {name.toLowerCase(), Integer.toString(servingSize)});
	}
	
	/*
	 *   User Profile Database methods:
	 * 			1. Register user to system:
	 * 			2. check if username exists:
	 * 			3. return a data model of user profile
	 */
	
	
	public long registerProfile(UserProfile p){
		UserProfile user = new UserProfile();
		user = p;
		ContentValues values = new ContentValues();
		Log.v("DEBUG WEIGHT", "Inserting new UserProfile with the Weight.." + user.getWeight());
		
		values.put(KEY_USERNAME, user.getName());
		values.put(KEY_AGE, user.getAge());
		values.put(KEY_GENDER, user.getGender());
		values.put(KEY_WEIGHT, user.getWeight());
		values.put(KEY_HEIGHT, user.getHeight());
		values.put(KEY_DIET, user.getDiet());
		values.put(KEY_EXERCISE, user.getExercise());
		
		return database.insert(USER_TABLE, null, values);
		
	}
	
	//Check if username exists in the database already
	public boolean searchUsername(String name){  

		Log.v("DEBUG LOGIN","Value at" + name );
		Cursor cursor = database.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + KEY_USERNAME + " = ?", new String[]{name});
		
		//TODO Exception handling if login name returns a null cursor.
		Log.v("DEBUG LOGIN","cursor count" + cursor.getCount() );
			
		if(cursor.getCount() == 0) {
			Log.v("DEBUG LOGIN","Value at cursor is null " );
			return false;
		}else return true; // if username found and cursor has data return true.
	} 
	
	// searches username and returns a data model with user data
	public UserProfile getUserDetails(String username){
		Log.v("DEBUG", "building user profile ");
		String name = username;
		UserProfile profile = new UserProfile();
				
		Log.v("DEBUG", "USERNAME SEARCHING FOR IS  " + name);			
		Cursor cursor = database.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + KEY_USERNAME + " = ?", new String[]{name});			
				
		if(cursor.moveToFirst()){
			do{
						
				Log.v("DEBUG", "building user profile ");
				profile.setName(cursor.getString(1) );
				profile.setAge(Integer.parseInt(cursor.getString(2)));
				profile.setGender(cursor.getString(3));
				profile.setHeight(Integer.parseInt(cursor.getString(5)));
				profile.setWeight(Integer.parseInt(cursor.getString(4)));
				profile.setDiet(cursor.getString(6));
				profile.setExercise(cursor.getString(7));

			}while(cursor.moveToNext());
		} 
		
		Log.v("DEBUG WEIGHT", "RETURNING PROFILE WEIGHT AS" + profile.getWeight());
						
		// Return food list
		return profile;		
	}
	
	// adds food item to system table.
	// System table is used for storing preloaded food data, and
	// creating a collection of user entered data permanently
	// although not always visible to the user.
	public long addToSystemTable(Food f){
		Food food 	= new Food();
		food 		= f;
		ContentValues values = new ContentValues();
		Log.v("DEBUG", "Food to be Inserted to DB is " + food.toString());	
		values.put(KEY_FOODNAME,	food.getName().toLowerCase());
		values.put(KEY_EAN,			food.getEan());
		values.put(KEY_CAT,			food.getCategory());
		values.put(KEY_SERVINGSIZE, food.getServingSize());
		values.put(KEY_CALORIES, 	food.getCalories());
		values.put(KEY_PROTEIN, 	food.getProtein());
		values.put(KEY_TOTALFAT, 	food.getTotalFat());
		values.put(KEY_TRANSFAT, 	food.getSatFat());
		values.put(KEY_SATFAT, 		food.getTransFat());
		values.put(KEY_SODIUM,		food.getSodium());
		values.put(KEY_CHOLESTERAL, food.getCholesteral());
		values.put(KEY_TOTALCARBS, 	food.getTotalCarbs());
		values.put(KEY_FIBRECARBS,	food.getDietaryFibre());
		values.put(KEY_SUGARCARBS,	food.getSugars());
		values.put(KEY_IRON, 		food.getIron());
		values.put(KEY_CALCIUM,		food.getCalcium());
		values.put(KEY_VITA, 		food.getVitA());
		values.put(KEY_VITB, 		food.getVitB());
		values.put(KEY_VITC, 		food.getVitC());
		values.put(KEY_VITD, 		food.getVitD());
		values.put(KEY_VITE, 		food.getVitE());
		values.put(KEY_VITK, 		food.getVitK());
		values.put(KEY_POTASSIUM, 	food.getPotassium());
		values.put(KEY_PHOSPHORUS, 	food.getPhosphorus());
		values.put(KEY_THAIMIN, 	food.getThaimin());
		values.put(KEY_RIBOFLAVIN, 	food.getRiboflavin());
		values.put(KEY_NIACIN, 		food.getNiacin());
		values.put(KEY_MAGNESIUM, 	food.getMagnesium());
		values.put(KEY_MANGANESE, 	food.getManganese());
		values.put(KEY_ZINC, 		food.getZinc());
		values.put(KEY_COPPER, 		food.getCopper());
				
		Log.v("DEBUG ", "Inserting new Food Item to FoodTable " + food.getName());
		return database.insert(SYSTEM_FOOD_TABLE, null, values);	
	}
	
	// Checks for food entry in the system table based on <FoodName, ServingSize>
	public boolean checkForSystemTableDuplicates(String foodName, int sSize){
			
			int servingSize = sSize; 
			boolean found;
			
			Log.v("DEBUG", "DATABASE:  Searching by name ssize");
			
			// todo query with sSize.
			Cursor cursor = database.rawQuery("SELECT * FROM " + SYSTEM_FOOD_TABLE + " WHERE " + 
	        		KEY_FOODNAME + " = ? AND  " + KEY_SERVINGSIZE + " = ? ", new String[] { foodName.toLowerCase(), Integer.toString(servingSize)});

			if(cursor.moveToFirst()){
				found = true;
			}else{
				found = false;
			}
			return found;
		}
	
	
	

} // end class.