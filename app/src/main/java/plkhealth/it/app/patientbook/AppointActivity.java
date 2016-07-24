package plkhealth.it.app.patientbook;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppointActivity extends AppCompatActivity {

    View view_appoint;
    TextView tv_app_date,tv_app_time,tv_app_hos,tv_app_clinic,tv_app_note,tv_app_count_down;

    void bindWidget(){
        tv_app_date = (TextView)findViewById(R.id.tv_app_date);
        tv_app_time = (TextView)findViewById(R.id.tv_app_time);
        tv_app_hos = (TextView)findViewById(R.id.tv_app_hos);
        tv_app_clinic = (TextView)findViewById(R.id.tv_app_clinic);
        tv_app_note = (TextView)findViewById(R.id.tv_app_note);
        tv_app_count_down = (TextView)findViewById(R.id.tv_app_count_down);
    }

    public static String dateThai(String strDate)
    {
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return String.format("%s %s %s", day,Months[month],year+543);
    }

    void get_appoint(){
        // Request data
        String cid = Prefs.getString("patient_cid","");
        String url = Prefs.getString("api_url", "") + "frontend/web/patient/appoint?cid="+cid;
        Log.d("Url",url);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("volley res", response.toString());

                try {

                    //for (int i = 0; i < response.length(); i++) {

                    JSONObject js_obj = (JSONObject) response.get(0);


                    tv_app_date.setText(dateThai(js_obj.getString("mdate")));

                    tv_app_time.setText(js_obj.getString("mtime"));
                    tv_app_clinic.setText(js_obj.getString("mclinic"));
                    tv_app_hos.setText(js_obj.getString("hospcode"));
                    tv_app_note.setText(js_obj.getString("mnote"));
                    String mcount = js_obj.getString("mcount");
                    if(!mcount.equals("0")) {
                        tv_app_count_down.setText("อีก " + mcount + " วันจะถึงวันนัด");
                    }


                    if (js_obj.getString("cid").equals("null")) {
                        view_appoint.setVisibility(View.GONE);
                    }else{
                        view_appoint.setVisibility(View.VISIBLE);
                    }


                    //}


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        Volley.newRequestQueue(this).add(request);

        // end request

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);
        final View viewParent = findViewById(R.id.layout_appoint);
        bindWidget();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("หมอนัด");
        view_appoint = (View) findViewById(R.id.view_appoint);


        get_appoint();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_appoint);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // do the thing that takes a long time
                        try {
                            //Thread.sleep(5000);
                            get_appoint();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
