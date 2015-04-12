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

                HashMap<String, String> item =
                        (HashMap<String, String>) parent.getAdapter().getItem(position);

                goToResultActivity(item);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void goToResultActivity(HashMap<String, String> item) {
        Bundle extras = new Bundle();
        extras.putSerializable("data",item);

        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
        intent.putExtras(extras);
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

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("SubtitleData");
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

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < parseObjects.size(); i++){
            ParseObject object = parseObjects.get(i);

            String movieName =
                    Utils.getMovieInfo(object.getString("movie")).getString("movieName");

            HashMap<String, String> item = new HashMap<>();
            item.put("text1", object.getString("subtitle"));
            item.put("text2", movieName + ": " +
                    object.getString("timeStart") + " ~ " +
                    object.getString("timeEnd"));

            item.put("subtitle", object.getString("subtitle"));
            item.put("movieName", object.getString("movie"));
            item.put("timeStart", object.getString("timeStart"));
            item.put("timeEnd", object.getString("timeEnd"));

            data.add(item);
        }

        String[] from = {"text1", "text2"};
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
