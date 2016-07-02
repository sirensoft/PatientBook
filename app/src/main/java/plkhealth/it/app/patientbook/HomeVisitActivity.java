package plkhealth.it.app.patientbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HomeVisitActivity extends AppCompatActivity {

    EditText txt_bw,txt_bh,txt_waist,txt_bps,txt_bpd,txt_pulse,txt_sugar,txt_note1;

    public void add_data(){

        final String url_input = Prefs.getString("api_url", "") + "frontend/web/patient/home-visit-input";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()

                        .add("cid", Prefs.getString("patient_cid", ""))
                        .add("weight",txt_bw.getText().toString())
                        .add("height",txt_bh.getText().toString())
                        .add("waist",txt_waist.getText().toString())
                        .add("bps",txt_bps.getText().toString())
                        .add("bpd",txt_bpd.getText().toString())
                        .add("pulse",txt_pulse.getText().toString())
                        .add("sugar",txt_sugar.getText().toString())
                        .add("note1",txt_note1.getText().toString())
                        .add("note2","doctor")

                        .build();

                Request request = new Request.Builder()
                        .url(url_input)
                        .post(body)
                        .build();

                try {
                    client.newCall(request).execute();
                    Log.d("homevisit input","ok");

                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_input_add);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("บันทึกการเยี่ยม");
        txt_bw = (EditText)findViewById(R.id.txt_bw);
        txt_bh = (EditText)findViewById(R.id.txt_bh);
        txt_waist = (EditText)findViewById(R.id.txt_waist);
        txt_bps = (EditText)findViewById(R.id.txt_bps);
        txt_bpd = (EditText)findViewById(R.id.txt_bpd);
        txt_pulse = (EditText)findViewById(R.id.txt_pulse);
        txt_sugar = (EditText)findViewById(R.id.txt_sugar);
        txt_note1 = (EditText)findViewById(R.id.txt_note1);

        String c_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txt_note1.setText("วันที่ "+dateThai(c_date)+"  ");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_save, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        if (menuItem.getItemId() == R.id.action_input_save) {
            add_data();
            //Toast.makeText(getApplicationContext(),"บันทึกสำเร็จ",Toast.LENGTH_SHORT).toString();

        }

        return super.onOptionsItemSelected(menuItem);
    }
}
