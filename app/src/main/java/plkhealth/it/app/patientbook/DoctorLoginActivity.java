package plkhealth.it.app.patientbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText txt_doctor_user,txt_doctor_pass;

    private  void bind_widget(){
        txt_doctor_user = (EditText)findViewById(R.id.txt_doctor_user);
        txt_doctor_pass = (EditText)findViewById(R.id.txt_doctor_pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Doctor Login");

        bind_widget();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void btnCancelLogin_Click(View view){
        finish();
    }

    public void btnOkLogin_Click(View view){
        String var_doctor_user = txt_doctor_user.getText().toString();
        String var_doctor_pass = txt_doctor_pass.getText().toString();
        if(var_doctor_user.equals("doctor") && var_doctor_pass.equals("112233")){
            Intent intent = new Intent(this,HomeVisitActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
