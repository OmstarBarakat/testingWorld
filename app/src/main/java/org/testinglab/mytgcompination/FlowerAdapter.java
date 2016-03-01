package org.testinglab.mytgcompination;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.testinglab.mytgcompination.Models.dataItems;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class FlowerAdapter extends ArrayAdapter<dataItems> {

    private Context context;
    private List<dataItems> flowerList;

    public FlowerAdapter(Context context, int resource, List<dataItems> objects) {
        super(context, resource, objects);
        this.context = context;
        this.flowerList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower, parent, false);

        //Display flower name in the TextView widget
        dataItems flower = flowerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.tv_item_name);
        tv.setText(flower.getProductNm());

        if (flower.getProductPhoto() != null) {
            Log.i("tst", "photo NOT null");//
            ImageView imageV = (ImageView) view.findViewById(R.id.imageview_flower);
            imageV.setImageBitmap(flower.getProductPhoto());
        } else {
            Log.i("tst", "photo is null");//
            FlowerSavedView container = new FlowerSavedView();
            container.flower = flower;
            container.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);

        }

        return view;
    }

    class FlowerSavedView {

        public dataItems flower;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<FlowerSavedView, Void, FlowerSavedView> {

        @Override
        protected FlowerSavedView doInBackground(FlowerSavedView... params) {

            FlowerSavedView container = params[0];
            dataItems flower = container.flower;

            try {
                Log.i("tst", "trying set img");//
                String imageUrl = FeedNewsActivity.PHOTOS_BASE_URL + flower.getProductImg();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                flower.setProductPhoto(bitmap);
                in.close();
                container.bitmap = bitmap;
                return container;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(FlowerSavedView result) {
            ImageView image = (ImageView) result.view.findViewById(R.id.imageview_flower);
            image.setImageBitmap(result.bitmap);
            result.flower.setProductPhoto(result.bitmap);
        }
    }
}
