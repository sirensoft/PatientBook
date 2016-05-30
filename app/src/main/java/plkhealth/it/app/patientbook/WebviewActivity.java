package plkhealth.it.app.patientbook;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {

    WebView youtube;

    private void bindWebView() {
        youtube = (WebView) findViewById(R.id.youtube);
        //youtube.setBackgroundColor(Color.TRANSPARENT);
        //youtube.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        youtube.setWebViewClient(new MyWebViewClient());
        youtube.getSettings().setJavaScriptEnabled(true);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_webview);
        bindWebView();
        youtube.loadUrl("http://ftp2.plkhealth.go.th/patientbookapi/frontend/web/patient/youtube");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, final String url) {


        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {


        }


    }
}
