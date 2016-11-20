package josiahcampbell.customoptions;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class SerifButton extends Button {

    public SerifButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(SerifTypeface.get());
    }
}
