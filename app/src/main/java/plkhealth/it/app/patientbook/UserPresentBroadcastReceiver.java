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
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {

            String media_count = Prefs.getString("media_count","0");
            int num = Integer.parseInt(media_count);
            if (num == 0) {
                num = -1;
            }
            myGlobal.setBadge(context, num);


        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {


        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {


        }

        /*Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {

        }
    }

}