package tw.ggmlab.followthemoviestar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.parse.ParseObject;

import java.util.HashMap;


public class ResultActivity extends ActionBarActivity {

    private final static int playPeriod = 7 * 1000;
    private final static int playBuffer = 1 * 1000;


    private String timeStart;
    private String timeEnd;
    private String subtitle;

    private TextView textView;
    private VideoView videoView;
    private ParseObject movieInfo;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        textView = (TextView) findViewById(R.id.subtitle);
        textView.setText(subtitle);

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(movieInfo.getString("url")));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progressDialog.dismiss();

                int playStartMecs = getPlayStartMecs();

                if (playStartMecs == -1) {
                    Toast.makeText(ResultActivity.this,
                            "error start time !", Toast.LENGTH_SHORT).show();
                } else {
                    mp.seekTo(playStartMecs);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            videoView.pause();
                            Utils.createDialog(ResultActivity.this, videoView).show();
                        }
                    }, playPeriod);
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
                ((int) movieInfo.getDouble("offset") * 1000) - playBuffer;
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
