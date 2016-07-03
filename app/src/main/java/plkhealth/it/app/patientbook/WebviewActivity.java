package plkhealth.it.app.patientbook;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

public class WebviewActivity extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("หมอแจ้งข่าว");

        webview =(WebView)findViewById(R.id.mywebview);

        Bundle b = getIntent().getExtras();
        Log.d("fcm data",b.getString("desc"));
        String msg = b.getString("desc");

        webview.loadData("<h3>"+msg+"</h3>", "text/html; charset=utf-8", "UTF-8");



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }


}
