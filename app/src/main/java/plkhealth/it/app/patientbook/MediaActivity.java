package plkhealth.it.app.patientbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        setTitle("รายการแนะนำจากหมอ");
        List<MediaModel> data=new ArrayList<>();

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
        data.add(model2);

        listViewMedia = (RecyclerView) findViewById(R.id.listViewMedia);
        MediaAdapter adapter = new MediaAdapter(getApplicationContext(),data);
        listViewMedia.setAdapter(adapter);
        listViewMedia.setLayoutManager(new LinearLayoutManager(MediaActivity.this));



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
