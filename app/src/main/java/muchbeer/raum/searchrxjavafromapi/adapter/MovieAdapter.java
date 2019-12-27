package muchbeer.raum.searchrxjavafromapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import muchbeer.raum.searchrxjavafromapi.R;
import muchbeer.raum.searchrxjavafromapi.model.Movie;
import muchbeer.raum.searchrxjavafromapi.view.MovieActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    public static String SELECTED_MOVIE = "movieSelected";
    private Context context;
    private List<Movie> filterMovies;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
        this.filterMovies = movies;
    }

    public MovieAdapter() {
    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item,
                        parent,
                        false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getOriginalTitle());
        holder.rate.setText(Double.toString(movies.get(position).getVoteAverage()));

        String posterPath="https://image.tmdb.org/t/p/w500"+ movies.get(position).getPosterPath();

        Glide.with(context)
                .load(posterPath)
                .placeholder(R.drawable.loading)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return filterMovies.size();
    }

    @Override
    public Filter getFilter() {

        return  new Filter() {
           @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {
                    filterMovies = movies;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie movie : movies) {

                        if (movie.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }

                    filterMovies = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterMovies;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterMovies = (ArrayList<Movie>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView movieTitle,rate;
        public ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieTitle=itemView.findViewById(R.id.tvTitle);
            rate=itemView.findViewById(R.id.tvRating);
            movieImage=itemView.findViewById(R.id.ivMovie);

itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();

        if (position != RecyclerView.NO_POSITION){
            Movie selectedMovie = movies.get(position);

            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra(SELECTED_MOVIE, selectedMovie );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String movieTitle=selectedMovie.getOriginalTitle();
            Toast.makeText(view.getContext(),  movieTitle, Toast.LENGTH_LONG).show();

            context.startActivity(intent);
        }
    }
});
        }
    }
}
