package plkhealth.it.app.patientbook;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MediaActivity extends AppCompatActivity {
    RecyclerView listViewMedia;
    final List<MediaModel> listData = new ArrayList<>();
    MediaAdapter adapter;
    String req_url;
    private SwipeRefreshLayout swipe_refresh_media;

    public  void watchYoutubeVideo(String youtube_id){
        Log.d("youtube",youtube_id);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtube_id));
            intent.putExtra("force_fullscreen",true);
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + youtube_id));
            intent.putExtra("force_fullscreen",true);
            startActivity(intent);
        }
    }

    private void makeRequest(String url) {
        Log.d("Volley", url);


        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("volley res", response.toString());
                listData.clear();

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject js_obj = (JSONObject) response.get(i);

                        MediaModel model = new MediaModel();
                        model.mId = js_obj.getString("media_id");
                        model.mTitle = js_obj.getString("media_name");
                        model.mDate = js_obj.getString("datetime_send");
                        model.mDesc = js_obj.getString("media_message");
                        model.mRead = js_obj.getString("media_read").equals("1")?"อ่านแล้ว":"";
                        model.mCid = js_obj.getString("cid");
                        model.mType = js_obj.getInt("media_type");
                        model.mUrl = js_obj.getString("media_url");
                        listData.add(model);

                        Log.d("send_date",model.mDate);

                    }

                    adapter.notifyDataSetChanged();
                    swipe_refresh_media.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    swipe_refresh_media.setRefreshing(false);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RetryPolicy policy = new DefaultRetryPolicy(30000//milli-sec
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("หมอแนะนำ");
        swipe_refresh_media = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_media) ;


        req_url = Prefs.getString("api_url", "") + "frontend/web/media/list-media?cid=" + Prefs.getString("patient_cid", "");
        makeRequest(req_url);

        listViewMedia = (RecyclerView) findViewById(R.id.listViewMedia);
        adapter = new MediaAdapter(getApplicationContext(), listData);
        listViewMedia.setAdapter(adapter);

        listViewMedia.setLayoutManager(new LinearLayoutManager(MediaActivity.this));
        listViewMedia.setItemAnimator(new DefaultItemAnimator());
        listViewMedia.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        listViewMedia.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listViewMedia, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //MediaModel media = data.get(position);
                final String cid = listData.get(position).mCid;
                final String mdate = listData.get(position).mDate;
                final String mid = listData.get(position).mId;
                if (listData.get(position).mType == 0) {

                    watchYoutubeVideo(listData.get(position).mUrl);
                }
                if(listData.get(position).mType == 1){
                    Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
                    intent.putExtra("url",listData.get(position).mUrl);
                    intent.putExtra("title",listData.get(position).mTitle);
                    Log.d("URL",listData.get(position).mUrl);
                    startActivity(intent);
                }

                //update read
                final String url_update_read = Prefs.getString("api_url", "") + "frontend/web/media/read";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("cid", cid)
                                .add("mdate", mdate)
                                .add("mid",mid)
                                .build();

                        okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(url_update_read)
                                .post(body)
                                .build();

                        try {
                            client.newCall(request).execute();
                            Log.d("update read","ok");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipe_refresh_media.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeRequest(req_url);
            }
        });


    }

    @Override
    public void  onResume(){

        Log.d("onResume","onResume");
        super.onResume();



    }


    @Override
    public void onDestroy(){

        String url = Prefs.getString("api_url","")+"frontend/web/media/check-media?cid="+Prefs.getString("patient_cid","");
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("destroy media",response);
                        Prefs.putString("media_count",response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }
    // click

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MediaActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MediaActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}
