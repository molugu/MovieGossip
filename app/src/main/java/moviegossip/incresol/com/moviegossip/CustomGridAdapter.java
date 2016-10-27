package moviegossip.incresol.com.moviegossip;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Incresol-078 on 26-09-2016.
 */

public class CustomGridAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<Bitmap> icon_movie;
    CustomGridAdapter(Context context,ArrayList<Bitmap> icon_movie){
            this.mContext=context;
            this.icon_movie=icon_movie;
    }

    @Override
    public int getCount() {
        return icon_movie.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("getView===>",""+convertView);
        View grid;
        grid=new View(mContext);
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            grid=inflater.inflate(R.layout.grid_item_movie,null);
            ImageView imageView=(ImageView)grid.findViewById(R.id.icon_movie);
            imageView.setImageBitmap(icon_movie.get(position));
        }else{
            grid=(View) convertView;
        }
        return grid;
    }
}
