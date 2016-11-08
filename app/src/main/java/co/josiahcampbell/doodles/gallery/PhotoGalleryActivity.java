package co.josiahcampbell.doodles.gallery;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.josiahcampbell.doodles.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static java.lang.String.format;

@RuntimePermissions
public class PhotoGalleryActivity extends AppCompatActivity {

    PhotoView picassoImage;
    PhotoView contentImage;

    List<String> fileLocations;
    List<String> contentLocations;

    int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        picassoImage = (PhotoView) findViewById(R.id.picasso_image);
        contentImage = (PhotoView) findViewById(R.id.content_image);
        TextView dcimText = (TextView) findViewById(R.id.directory_text);
        TextView picturesText = (TextView) findViewById(R.id.pictures_text);

        dcimText.setText(format("DCIM: %s",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()));

        picturesText.setText(format("Pictures: %s",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()));


        contentLocations = new ArrayList<>();
        fileLocations = new ArrayList<>();

        currentIndex = 0;


        String readable = isExternalStorageReadable()
                ? "readable (this is good.)" : "not readable. :/ something's wrong...";
        Toast.makeText(this, format("External media is %s", readable), Toast.LENGTH_LONG).show();
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PhotoGalleryActivityPermissionsDispatcher.refreshImagesWithCheck(this);
    }

    @NeedsPermission(READ_EXTERNAL_STORAGE)
    public void refreshImages() {
        contentLocations.addAll(PhotoLocator.getContentLocations(this));
        fileLocations.addAll(PhotoLocator.getPhotoLocations(this));
        if (fileLocations.isEmpty()) return;
        setImages();
    }

    private void setImages() {
        picassoImage.bind(fileLocations.get(currentIndex));
        contentImage.bind(contentLocations.get(currentIndex));
    }

    public void setPreviousImage(View view) {
        if (fileLocations.isEmpty()) {
            return;
        }
        if (currentIndex == 0) {
            currentIndex = fileLocations.size() - 1;
        } else {
            currentIndex--;
        }
        setImages();
    }


    public void setNextImage(View view) {
        if (fileLocations.isEmpty()) {
            return;
        }
        if (currentIndex == fileLocations.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        setImages();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoGalleryActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
