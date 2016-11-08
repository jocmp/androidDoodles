package co.josiahcampbell.doodles.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.josiahcampbell.doodles.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

@RuntimePermissions
public class PhotoGalleryActivity extends AppCompatActivity {

    PhotoView picassoImage;

    List<String> fileLocations;

    int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        picassoImage = (PhotoView) findViewById(R.id.picasso_image);

        fileLocations = new ArrayList<>();
        currentIndex = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PhotoGalleryActivityPermissionsDispatcher.refreshImagesWithCheck(this);
    }

    @NeedsPermission(READ_EXTERNAL_STORAGE)
    public void refreshImages() {
        fileLocations.addAll(PhotoLocator.getPhotoLocations(this));
        if (fileLocations.isEmpty()) return;
        setImages(fileLocations.get(currentIndex));
    }

    private void setImages(String fileLocation) {
        picassoImage.bind(fileLocation);
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
        setImages(fileLocations.get(currentIndex));
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
        setImages(fileLocations.get(currentIndex));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoGalleryActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
