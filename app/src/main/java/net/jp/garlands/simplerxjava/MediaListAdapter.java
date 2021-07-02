package net.jp.garlands.simplerxjava;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class MediaListAdapter extends BaseAdapter {

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    private List<Integer> imageList = new ArrayList<>();
    private List<MediaListData> medialist = new ArrayList<>();

    private List<String> names;
    private LayoutInflater inflater;
    private int layoutId;
    private RequestManager manager;

    MediaListAdapter(Context context, int layoutId, List<MediaListData> medias) {
        super();
        this.inflater = (LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        this.layoutId = layoutId;
        manager = Glide.with(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
            holder = new ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MediaListData media = medialist.get(position);

        try {
            Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uri = Uri.withAppendedPath(uriExternal, "" + media.getImage_id());

            Glide.with(convertView)
                    .load(uri)
                    .override(400, 400)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.imageView);
        } catch (Exception e) {
            Log.d("TEST", e.getLocalizedMessage());
        }
        return convertView;
    }

    public void onViewRecycled(final ViewHolder viewHolder) {
        Glide.with(viewHolder.imageView).clear(viewHolder.imageView);
    }

    @Override
    public int getCount() { return medialist.size(); }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) { return position; }

    public void updateItems(List<MediaListData> medias) { medialist = medias; }
}
