package co.josiahcampbell.doodles.gallery;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
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


    public static List<String> getPhotoLocationss(Activity activity) {
        String camera = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath();
        String pictures = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getAbsolutePath();
        final String[] projection = {MediaStore.Images.Media.DATA};
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {camera};
        final Cursor cursor = activity.getContentResolver().query(EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);

        if (cursor == null) {
            return new ArrayList<>();
        }

        List<String> results = new ArrayList<>();

        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                results.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

}
