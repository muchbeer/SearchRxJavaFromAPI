package muchbeer.raum.searchrxjavafromapi.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import muchbeer.raum.searchrxjavafromapi.model.Movie;
import muchbeer.raum.searchrxjavafromapi.repository.MovieRepository;

public class MainActivityViewModel extends AndroidViewModel {

    MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movieRepository.getPopularMovieWithRxJava();
    }

    public void clear(){movieRepository.clear(); }
}
