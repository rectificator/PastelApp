package com.dixketl.pastelapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by juan on 4/01/18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c){
        mContext=c;
    }

    @Override
    public int getCount() {
        return mThumbsIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Crea una nueva ImageView por cada Item(elemento) referenciado por el Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView==null){
            //Si no está reciclada, inicializa algunos atributos
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500,500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }
        else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbsIds[position]);
        return imageView;
    }

    //referencias a nuestras imagenes
    private Integer[] mThumbsIds = {R.drawable.pastel0, R.drawable.pastel1, R.drawable.pastel2,
            R.drawable.pastel3, R.drawable.pastel4};
}
