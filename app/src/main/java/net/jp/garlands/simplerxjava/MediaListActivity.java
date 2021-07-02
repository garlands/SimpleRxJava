package net.jp.garlands.simplerxjava;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MediaListActivity extends AppCompatActivity {

    private static String[] PERMISSIONS_STOREGE = { Manifest.permission.READ_EXTERNAL_STORAGE };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    GridView gridview;

    private static Activity me = null;
    private List<MediaListData> medias = new ArrayList<>();

    private List<Integer> imgList = new ArrayList<>();
    private MediaListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        me = this;
        setContentView(R.layout.media_list_activity);
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MediaListFragment.newInstance())
                    .commitNow();
        }

         */

        gridview = this.findViewById(R.id.album_grid_view);

        if (checkPermission() == true) {
            getMediaList();
        }
        adapter = new MediaListAdapter(getApplicationContext(), R.layout.layout_media_listitem, medias);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {
                MediaListData mediaListData = medias.get(position);
            }
        });
        gridview.requestFocus();
    }

    public void getMediaList() {
        ContentResolver resolver = me.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] {
                        MediaStore.Images.Media.TITLE,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media._ID,
                },
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            MediaListData media = new MediaListData(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE))
            );
            medias.add(media);
        }
        if (cursor != null) {
            cursor.close();
        }
        reflesh();
    }

    public void reflesh() {
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.updateItems(medias);
                adapter.notifyDataSetChanged();
                gridview.invalidate();
            }
        });
    }

    private static boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(me, Manifest.permission.MEDIA_CONTENT_CONTROL);
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    me,
//                    PERMISSIONS_STOREGE,
//                    REQUEST_EXTERNAL_STORAGE);
//            return false;
            return true;
        }
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }
}