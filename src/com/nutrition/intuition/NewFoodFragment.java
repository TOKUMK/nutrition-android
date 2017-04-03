package com.nutrition.intuition;


import com.nutrition.intuition.barscan.IntentIntegrator;
import com.nutrition.intuition.barscan.IntentResult;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class NewFoodFragment extends DialogFragment {
	
		String scanContent = "";		
		String scanFormat  = "";
	
	public NewFoodFragment(){}
	
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	//Intent intent;
        return new AlertDialog.Builder(getActivity())
                .setMessage("\t\t\t\t\t\t Select Your Entry Method").setPositiveButton("Barscan", 
                		new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                    	
            			IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
            			scanIntegrator.initiateScan();
                    }
                })
                .setNegativeButton("Manual Input", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                    	
            			Intent intent = new Intent (getActivity(), NewFoodManualEntry.class);
            			startActivity(intent);
                    }		
                    }).create();
    }


}
    
    

