package co.josiahcampbell.doodles.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.josiahcampbell.doodles.R;

public class PhotoView extends FrameLayout {

    TextView locationText;
    ImageView imageView;

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.photo_item, this);

        locationText = (TextView) findViewById(R.id.location_text);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void bind(String fileLocation) {
        Picasso.with(getContext())
                .load(fileLocation)
                .config(Bitmap.Config.RGB_565)
                .error(R.drawable.ic_block_black)
                .into(imageView);
        locationText.setText(fileLocation);
    }
}
