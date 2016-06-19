package plkhealth.it.app.patientbook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pixplicity.easyprefs.library.Prefs;

public class PatientInputListActivity extends AppCompatActivity {

    View coord ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_input_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("บันทึกตนเอง");
        coord = (View)findViewById(R.id.layout_pt_input1);//for snackbar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_pt_input);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        if (menuItem.getItemId() == R.id.action_chart) {
            Intent itent = new Intent(this,ChartActivity.class);
            itent.putExtra("cid", Prefs.getString("patient_cid",""));
            startActivity(itent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
