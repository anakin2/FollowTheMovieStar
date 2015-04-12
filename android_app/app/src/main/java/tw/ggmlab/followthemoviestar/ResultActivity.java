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
import android.widget.VideoView;


public class ResultActivity extends ActionBarActivity {

    private VideoView videoView;

    // mesec
    private int playStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        playStart = getPlayStart();

        videoView = (VideoView) findViewById(R.id.videoView);
//        videoView.setVideoURI(Uri.parse("http://vultr.dm4.tw/mp4/hitcon.mp4"));
        videoView.setVideoURI(Uri.parse("http://vultr.dm4.tw/mp4/god.mp4"));

//        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                //mock data or error
                if (playStart == -1) {
                    mp.seekTo(1500);
                } else {
                    mp.seekTo(playStart);
                }

            }
        });
        videoView.start();

    }

    private int getPlayStart() {
        Intent intent = getIntent();
        if (intent == null) return -1;
        return intent.getIntExtra("playStart", -1);
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
