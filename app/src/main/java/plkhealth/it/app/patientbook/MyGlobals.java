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

    public String getApiUrl(){
        return  pref.getString("api_url","");
    }

    public boolean getIsActivated(String cid){
        String patient_cid = getPatientCid();

        return false;
    }


    public  void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public  String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }



}
