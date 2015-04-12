package tw.ggmlab.followthemoviestar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToResultActivity();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void goToResultActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
        startActivity(intent);
    }

    public void clickSearch(View view) {

        setListViewData();
    }

    public void setListViewData() {

        String movieNames[] = new String[]{"食神", "唐伯虎點秋香"};
        String subtitles[] = new String[]{"先生 你額有朝天骨 眼中有靈光", "我終於搶到唐伯虎的墨寶了", };


        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < movieNames.length; i++){
            HashMap<String, String> item = new HashMap<>();
            item.put("movieName", movieNames[i]);
            item.put("subtitle", subtitles[i]);
            data.add(item);
        }

        String[] from = {"movieName", "subtitle"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
