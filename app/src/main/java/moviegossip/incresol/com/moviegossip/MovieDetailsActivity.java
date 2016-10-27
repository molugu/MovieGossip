package moviegossip.incresol.com.moviegossip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Incresol-078 on 29-09-2016.
 */

public class MovieDetailsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Movie Details");
        setContentView(R.layout.activity_movie_details);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new MoviesDetailsFragment()).commit();
        }
    }

    public static class MoviesDetailsFragment extends Fragment{
        public Bitmap bitmap_movie;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Intent intent=getActivity().getIntent();
            View rootView=inflater.inflate(R.layout.fragment_movie_details,container,false);
            if(intent!=null&& intent.hasExtra("Bitmap_movie")){
                bitmap_movie=intent.getParcelableExtra("Bitmap_movie");
                ((ImageView)rootView.findViewById(R.id.movie_image)).setImageBitmap(bitmap_movie);
            }

            return rootView;
        }
    }

}
