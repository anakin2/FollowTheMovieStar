package tw.ggmlab.followthemoviestar;

import android.app.Application;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by ggm on 4/11/15.
 */
public class FTMSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xE663KkYxrGlPhGiOurXseiFUZajrTM7CjFS3XFb",
                "zHAJv2fCp3o0iya9YKJBYPFKQKrC4KYUC9OwZckr");

        setInitData();
    }

    private void setInitData() {
        ParseQuery<ParseObject> query = new ParseQuery<>("MovieInfo");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ParseObject.pinAllInBackground(parseObjects);
            }
        });
    }
}