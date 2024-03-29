package co.josiahcampbell.doodles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.josiahcampbell.doodles.gallery.PhotoGalleryActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPhotoGallery(View view) {
        startActivity(new Intent(this, PhotoGalleryActivity.class));
    }
}
