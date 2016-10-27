package moviegossip.incresol.com.moviegossip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class MoviesGridFragment extends Fragment {
private final String LOG_TAG=MoviesGridFragment.class.getSimpleName();
    GridView gridView_movies;
    public static CustomGridAdapter customGridAdapter;
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView!=null){
            rootView=null;
        }
        rootView= inflater.inflate(R.layout.fragment_movies_grid, container, false);
        gridView_movies= (GridView)rootView.findViewById(R.id.gridview_movies);
        customGridAdapter=new CustomGridAdapter(MoviesGridFragment.this.getContext(),MainActivity.bitmapArrayList);
        gridView_movies.setAdapter(customGridAdapter);
        gridView_movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 customGridAdapter.getItem(position);
                Log.i(LOG_TAG,"object=====*****//>>>");
                /*Intent intent_movie=new Intent(getActivity(),MovieDetailsActivity.class).putExtra("Bitmap_Movie",item);
                startActivity(intent_movie);*/

            }
        });
        return rootView;
    }

}
