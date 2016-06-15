package plkhealth.it.app.patientbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MediaActivity extends AppCompatActivity {
    ListView listViewMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("รายการแนะนำจากหมอ");
        listViewMedia = (ListView) findViewById(R.id.listViewMedia);

        String[] list_items = new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.media_list, R.id.title, list_items);
        listViewMedia.setAdapter(adapter);

        listViewMedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
                startActivity(intent);

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
