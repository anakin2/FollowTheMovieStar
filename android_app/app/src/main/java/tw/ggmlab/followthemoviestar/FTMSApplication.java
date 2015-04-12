package tw.ggmlab.followthemoviestar;

import android.app.Application;

import com.parse.Parse;

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

    }
}
