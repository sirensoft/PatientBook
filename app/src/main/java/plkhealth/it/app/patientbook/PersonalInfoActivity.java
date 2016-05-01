package plkhealth.it.app.patientbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class PersonalInfoActivity extends AppCompatActivity {

    //SharedPreferences pref ;
    Toolbar toolbar;
    MyGlobals myGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ข้อมูลส่วนตัว");
        myGlobal = new MyGlobals(getApplicationContext());

        //pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //String patient_cid = pref.getString("patient_cid", "");
        String patient_cid =myGlobal.getPatientCid();
        Toast.makeText(getApplicationContext(),patient_cid,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
