package moviegossip.incresol.com.moviegossip;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    public String[] url_path=null;
    public static ArrayList<Bitmap> bitmapArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmapArrayList=new ArrayList<Bitmap>();
        progressDialog=new ProgressDialog(this);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new MoviesGridFragment())
                .commit();

        /*FetchMoviesDetailsTask fetchMoviesDetailsTask=new FetchMoviesDetailsTask("popular");
        fetchMoviesDetailsTask.execute();*/
    }

    public void MainAsyncTaskExecution(String category){
        progressDialog.setTitle("Loading....");
        progressDialog.setMessage(category+" Movies are loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FetchMoviesDetailsTask fetchMoviesDetailsTask=new FetchMoviesDetailsTask(category);
        fetchMoviesDetailsTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        String category=null;
        if(id==R.id.action_top_rated){
            category="top_rated";
            bitmapArrayList.clear();
            if(MoviesGridFragment.customGridAdapter!=null){
                MoviesGridFragment.customGridAdapter=null;
            }
            MainAsyncTaskExecution(category);

        }if(id==R.id.action_popular){
            category="popular";
            bitmapArrayList.clear();
            if(MoviesGridFragment.customGridAdapter!=null){
                MoviesGridFragment.customGridAdapter=null;
            }
            MainAsyncTaskExecution(category);
            return true;
        }
        return true;
    }

    public void getImageFromPath(String[] imagePath){

       for (int i=0;i<imagePath.length;i++){
           FetchImageFromURL fetchImageFromURL=new FetchImageFromURL();
           fetchImageFromURL.execute(imagePath[i]);
       }
       Log.i("getImageFromPath","************************************************* ");
        /*if(getSupportFragmentManager().getFragments()!=null) {
            getSupportFragmentManager().getFragments().clear();
        }*/
        /*getSupportFragmentManager()
               .beginTransaction()
               .add(R.id.container, new MoviesGridFragment())
               .commit();*/
       if (progressDialog.isShowing())
       progressDialog.cancel();


   }

    public class FetchImageFromURL extends AsyncTask<String,Void,Void>{
        private final String LOG_TAG=FetchImageFromURL.class.getSimpleName();
        Bitmap bitmap=null;
        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try {
                Log.i("=============>>>>>>>",params[0]);
                //Read the input stream into a string.
            String one="http://image.tmdb.org/t/p/w185/"+params[0];

                Log.i("=============>>>>>>>",one);
                InputStream inputStream = new URL(one).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
                bitmapArrayList.add(bitmap);


            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "MalformedURLException", e);
                return null;
            } catch (IOException ioe) {
                Log.e(LOG_TAG, "IOException", ioe);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return null;
            }

        }


    }


    public class FetchMoviesDetailsTask extends AsyncTask<Void,Void,String[]> {

        private final String LOG_TAG=FetchMoviesDetailsTask.class.getSimpleName();
        private String Category=null;
        FetchMoviesDetailsTask(String category){
            Category=category;
        }

        private String[] getMoviesDataFromJson(String moviesJsonStr) throws JSONException {

            final String MDB_RESULT="results";

            JSONObject moviesJson=new JSONObject(moviesJsonStr);
            JSONArray resultsArray=moviesJson.getJSONArray(MDB_RESULT);

            String[] resultStr=new String[10000];

            for(int i=0;i<resultsArray.length();i++){

                String poster_path;
               JSONObject jsonObject= resultsArray.getJSONObject(i);
                poster_path=  jsonObject.getString("poster_path");
                resultStr[i]=poster_path;
            }
            return resultStr;

        }

        @Override
        protected String[] doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String FORECAST_BASE_URL=null;
            String moviesJsonStr = null;
            String apiKey="e706c59c065652414490513b2545802e";
            try {
if(Category.equalsIgnoreCase("top_rated")){
    FORECAST_BASE_URL="https://api.themoviedb.org/3/movie/top_rated?";
}else if(Category.equalsIgnoreCase("popular")){
    FORECAST_BASE_URL="https://api.themoviedb.org/3/movie/popular?";
}

                final String API_KEY="api_key";

                Uri builtUri= Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY,apiKey)
                        .build();

                URL url=new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a string.

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                if (stringBuffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = stringBuffer.toString();
                Log.i("moviesJsonStr=====>>>",moviesJsonStr);

            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "MalformedURLException", e);
                return null;
            } catch (IOException ioe) {
                Log.e(LOG_TAG, "IOException", ioe);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if(result!=null){
                url_path=result;
                getImageFromPath(url_path);
                /*for (int i=0;i<url_path.length;i++) {
                    Log.i(LOG_TAG, "=====****>>>>" + url_path[0]);
                }*/

            }

        }
    }


}
