package plkhealth.it.app.patientbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MediaActivity extends AppCompatActivity {
    RecyclerView listViewMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("หมอแนะนำ");
        final List<MediaModel> data = new ArrayList<>();

        MediaModel model = new MediaModel();
        model.mTitle = "ข่าว 1";
        model.mDate = "xxxx";
        model.mDesc = "kkkk";
        model.mRead = "อ่านแล้ว";
        data.add(model);

        MediaModel model2 = new MediaModel();
        model2.mTitle = "ข่าว 1";
        model2.mDate = "xxxx";
        model2.mDesc = "kkkk";
        //model.mRead = "อ่านแล้ว";
        model2.mType = 1;
        model2.mId = "m001";
        data.add(model2);

        listViewMedia = (RecyclerView) findViewById(R.id.listViewMedia);
        MediaAdapter adapter = new MediaAdapter(getApplicationContext(), data);

        listViewMedia.setLayoutManager(new LinearLayoutManager(MediaActivity.this));
        listViewMedia.setItemAnimator(new DefaultItemAnimator());
        listViewMedia.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        listViewMedia.setAdapter(adapter);
        listViewMedia.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listViewMedia, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
               MediaModel media = data.get(position);
                Toast.makeText(getApplicationContext(), media.mId + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


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
