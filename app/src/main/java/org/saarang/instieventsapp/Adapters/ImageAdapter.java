package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.saarang.instieventsapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MIHIR on 28-08-2015.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    public Integer[] mThumbIds = {
            R.drawable.play_store_icon, R.drawable.play_store_icon,
            R.drawable.play_store_icon, R.drawable.play_store_icon,
            R.drawable.play_store_icon, R.drawable.play_store_icon,

    };

    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
}
