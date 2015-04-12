package tw.ggmlab.followthemoviestar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private EditText editText;
    private ProgressDialog progress;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xE663KkYxrGlPhGiOurXseiFUZajrTM7CjFS3XFb",
                "zHAJv2fCp3o0iya9YKJBYPFKQKrC4KYUC9OwZckr");

        progress = new ProgressDialog(this);

        button = (Button) findViewById(R.id.searchButton);

        editText = (EditText) findViewById(R.id.editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("debug", "keyCode:" + keyCode);
                // disable break line
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });

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

        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "input can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setTitle("Loading... ");
        progress.show();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TestClass");
        query.whereContains("subtitle", text);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e==null && parseObjects !=null && parseObjects.size()!=0) {
                    setListViewData(parseObjects);
                }
                progress.dismiss();
            }
        });
    }

    public void setListViewData(List<ParseObject> parseObjects) {

/*
        String movieNames[] = new String[]{"食神", "唐伯虎點秋香"};
        String subtitles[] = new String[]{"先生 你額有朝天骨 眼中有靈光", "我終於搶到唐伯虎的墨寶了", };
*/
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < parseObjects.size(); i++){
            HashMap<String, String> item = new HashMap<>();
            item.put("movieName", parseObjects.get(i).getString("movie"));
            item.put("subtitle", parseObjects.get(i).getString("subtitle"));
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
