package plkhealth.it.app.patientbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Created by utehn on 1/5/2559.
 */
public class MyGlobals {
    Context mContext;
    SharedPreferences pref ;
    // constructor
    public MyGlobals(Context context){
        this.mContext = context;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getPatientCid(){
        return pref.getString("patient_cid","");
    }
    public boolean getIsActivated(String cid){
        String patient_cid = getPatientCid();

        return false;
    }



}
