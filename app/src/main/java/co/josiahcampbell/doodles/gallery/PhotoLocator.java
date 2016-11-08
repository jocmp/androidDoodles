package co.josiahcampbell.doodles.gallery;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static java.lang.String.format;

public class PhotoLocator {

    public static List<String> getPhotoLocations(@NonNull Activity activity) {

        String[] projection = new String[]{
                MediaStore.Images.Media.DATA,
        };

        Uri imageUri = EXTERNAL_CONTENT_URI;

        String ordering = format("%s DESC", MediaStore.Images.Media.DATE_TAKEN);
        Cursor cur = activity.getContentResolver().query(imageUri,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                ordering    // Ordering
        );

        if (cur == null) {
            return new ArrayList<>();
        }

        List<String> fileLocations = new ArrayList<>();

        cur.moveToFirst();
        try {
            do {
                String filePath = cur.getString(cur.getColumnIndex(projection[0]));
                fileLocations.add("file://" + filePath);
            } while (cur.moveToNext());
        } finally {
            cur.close();
        }

        return fileLocations;
    }


}
