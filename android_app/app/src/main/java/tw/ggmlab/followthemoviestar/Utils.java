package tw.ggmlab.followthemoviestar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.VideoView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ggm on 4/11/15.
 */
public class Utils {

    //01:30:16,859

    private static Map<String, ParseObject> _movieInfo  = new HashMap<>();

    public static int convertTimeString(String time) {

        time = time.trim();
        if (time.length() != 12) return -1;

        int h = Integer.valueOf(time.substring(0, 2));
        int m = Integer.valueOf(time.substring(3, 5));
        int s = Integer.valueOf(time.substring(6, 8));
        int mecs = Integer.valueOf(time.substring(9, 12));

        return mecs + s * 1000 + m * 60 * 1000 + h * 60 * 60 * 1000;
    }

    public static ParseObject getMovieInfo(String filename) {

        if (_movieInfo.containsKey(filename) == false) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("MovieInfo");
            query.fromLocalDatastore();
            query.whereEqualTo("filename", filename);

            try {
                List<ParseObject> result = query.find();

                if (result.size() == 0) {
                    Log.e("Utils.java", "result size = 0. filename = " + filename);
                    return null;
                } else {
                    _movieInfo.put(filename, result.get(0));
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        return _movieInfo.get(filename);
    }

    public static Dialog createDialog(Activity activity, final VideoView videoView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("想看更多？")
                .setPositiveButton("前往購買", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("繼續播放", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        int position = videoView.getCurrentPosition();
                        videoView.seekTo(position);
                        videoView.start();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }
}
