package plkhealth.it.app.patientbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.List;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    MyGlobals myGlobal;
    Context mContext;



    @Override
    public void onReceive(final Context context, Intent intent) {
        myGlobal = new MyGlobals(context);
        this.mContext = context;
        /*Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            //
            String url = Prefs.getString("api_url","")+"frontend/web/media/check-media?cid="+Prefs.getString("patient_cid","");
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("check media",response);
                            int num = Integer.parseInt(response);
                            if(num==0){ num = -1;}
                            myGlobal.setBadge(context,num);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });

            Volley.newRequestQueue(mContext).add(stringRequest);



        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

        }

        /*Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

        }
    }

}