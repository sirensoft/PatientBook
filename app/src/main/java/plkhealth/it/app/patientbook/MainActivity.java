package plkhealth.it.app.patientbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.readystatesoftware.viewbadger.BadgeView;

import java.util.List;


public class MainActivity extends AppCompatActivity {



    ProgressDialog progress;
    SharedPreferences pref ;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View viewParent = findViewById(R.id.myCoordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(getResources().getText(R.string.app_title));
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setBadge(getApplicationContext(),2);

        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("patient_activated",true);
        editor.apply();


        View btn_appointment = findViewById(R.id.btn_appointment);
        BadgeView badge1 = new BadgeView(this, btn_appointment);
        badge1.setText("3");
        badge1.show();

        View btn_promotion = findViewById(R.id.btn_promotion);
        BadgeView badge2 = new BadgeView(this,btn_promotion);
        badge2.setText("N");
        badge2.show();

        ImageButton btn_talk_doctor = (ImageButton)findViewById(R.id.btn_talk_doctor);
        BadgeView badge3 = new BadgeView(this,btn_talk_doctor);
        badge3.setText("N");
        badge3.show();

        View btn_lab_result = findViewById(R.id.btn_lab_result);
        BadgeView badge4 = new BadgeView(this,btn_lab_result);
        badge4.setText("N");
        badge4.show();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean patient_activated =  pref.getBoolean("patient_activated",false);
                if(!patient_activated){
                    Snackbar snackbar = Snackbar.make(viewParent, "ไม่ได้รับอนุญาต", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                progress = ProgressDialog.show(MainActivity.this, "โปรดรอสักครู่",
                        "เรียกข้อมูลใหม่...", true);
                progress.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        // do the thing that takes a long time
                       try{
                           Thread.sleep(10000);
                       }catch (Exception e){

                       }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                progress.dismiss();
                                String name = pref.getString("name", "");
                                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,AdminLoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void btnPersonalInfo_Click(View view){
        Intent intent = new Intent(this, PersonalInfoActivity.class);
        startActivity(intent);
    }

}
