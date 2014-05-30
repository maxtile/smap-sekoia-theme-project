package com.example.sekoia.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Maxi on 27-05-14.
 */
    public class CustomGridAdapter extends BaseAdapter {
        private Activity mContext;

        // Keep all Images in array
        public List<Bitmap> bitmaps;

        // Constructor
        public CustomGridAdapter(PicturesActivity pActivity, List<Bitmap> items) {
            this.mContext = pActivity;
            this.bitmaps = items;
        }

        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return bitmaps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageBitmap(bitmaps.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
            return imageView;
        }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}

