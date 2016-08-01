package plkhealth.it.app.patientbook;

/**
 * Created by utehn on 27/5/2559.
 */
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        String cid= "5650201060154";
        //String token = firebase_token;
        String url = Prefs.getString("api_url","")+"frontend/web/index.php/media/update-token?cid="+cid+"&token="+token;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)L
                //Log.d("api","eeeee");
                e.printStackTrace();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

    }
}