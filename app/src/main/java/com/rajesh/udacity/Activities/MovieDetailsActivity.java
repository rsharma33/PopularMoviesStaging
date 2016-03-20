package com.rajesh.udacity.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajesh.udacity.MainActivity;
import com.rajesh.udacity.Model.Movie;
import com.rajesh.udacity.connection.API;
import com.rajesh.udacity.R;
import com.squareup.picasso.Picasso;


/**
 * Created by Rajesh Sharma on 20/March/16.
 */
public class MovieDetailsActivity extends ActionBarActivity {



    public static Movie movie;
    public static Intent intent;
    public static TextView movie_title, movie_year,movie_rate,movie_overview, tagline;
    public static ImageView movie_poster;
    public static View divisor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
            WindowManager wm = (WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            initComponents(rootView);
            setValues(rootView);
            return rootView;
        }

        public void initComponents(View rootView){
            movie = new Movie();
            MovieDetailsActivity.intent = getActivity().getIntent();
            int movie_id = intent.getIntExtra("movie_id",0);
            int movie_position = intent.getIntExtra("movie_position",0);
            movie = MainActivity.moviesList.get(movie_position);
            movie_title = (TextView)rootView.findViewById(R.id.movie_title);
            movie_year = (TextView)rootView.findViewById(R.id.movie_year);
            movie_rate = (TextView)rootView.findViewById(R.id.movie_rating);
            movie_poster = (ImageView)rootView.findViewById(R.id.movie_poster);
            movie_overview = (TextView)rootView.findViewById(R.id.movie_overview);
        }

        public static void setValues(View rootView){
            movie_title.setText(movie.getOriginal_title());
            movie_year.setText("Release date: ["+movie.getRelease_date()+"]");//movie.getRelease_date().substring(0,4)
            movie_title.setVisibility(View.VISIBLE);
            movie_rate.setText("User Rating: ["+movie.getVote_average() + "/10]");
            movie_overview.setText(movie.getOverview());
            String movie_poster_url;
            if (movie.getPoster_path() == API.IMAGE_NOT_FOUND) {
                movie_poster_url = API.IMAGE_NOT_FOUND;
            }else {
                movie_poster_url = API.IMAGE_URL + API.IMAGE_SIZE_185 + "/" + movie.getPoster_path();
            }
            Picasso.with(rootView.getContext()).load(movie_poster_url).into(movie_poster);
            movie_poster.setVisibility(View.VISIBLE);

        }
    }
}
