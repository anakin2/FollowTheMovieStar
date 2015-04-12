package tw.ggmlab.followthemoviestar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.parse.ParseObject;

import java.util.HashMap;


public class ResultActivity extends ActionBarActivity {

    private String timeStart;
    private String timeEnd;
    private String subtitle;

    private TextView textView;
    private VideoView videoView;
    private ParseObject movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();

        textView = (TextView) findViewById(R.id.subtitle);
        textView.setText(subtitle);

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(movieInfo.getString("url")));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                int playStartMecs = getPlayStartMecs();

                if (playStartMecs == -1) {
                    Toast.makeText(ResultActivity.this,
                            "error start time !", Toast.LENGTH_SHORT).show();
                } else {
                    mp.seekTo(playStartMecs);
                }
            }
        });

        videoView.start();

    }

    private void init() {
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        HashMap<String, String> item =
                (HashMap<String, String>) bundle.getSerializable("data");

        timeStart = item.get("timeStart");
        timeEnd = item.get("itemEnd");
        subtitle = item.get("subtitle");

        String filename = item.get("filename");
        movieInfo = Utils.getMovieInfo(filename);
    }

    private int getPlayStartMecs() {
        return Utils.convertTimeString(timeStart) +
                ((int) movieInfo.getDouble("offset") * 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
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
