package josiahcampbell.customoptions;

import android.graphics.Typeface;

public class SerifTypeface {

    public static Typeface get() {
        return Typeface.createFromAsset(CustomOptionsApplication.getAppContext().getAssets(), "pt_serif.ttf");
    }
}
