package tw.ggmlab.followthemoviestar;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
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
                _movieInfo.put(filename, query.find().get(0));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        return _movieInfo.get(filename);
    }
}
