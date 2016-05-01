package plkhealth.it.app.patientbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
