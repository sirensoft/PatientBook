package plkhealth.it.app.patientbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    MyGlobals myGlobal;

    @Override
    public void onReceive(Context context, Intent intent) {
        myGlobal = new MyGlobals(context);
        /*Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            myGlobal.setBadge(context,2);
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