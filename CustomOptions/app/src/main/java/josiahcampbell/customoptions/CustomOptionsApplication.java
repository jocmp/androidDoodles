package josiahcampbell.customoptions;

import android.app.Application;
import android.content.Context;

public class CustomOptionsApplication extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
