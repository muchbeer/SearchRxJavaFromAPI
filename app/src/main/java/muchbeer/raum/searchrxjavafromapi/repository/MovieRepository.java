package muchbeer.raum.searchrxjavafromapi.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import muchbeer.raum.searchrxjavafromapi.R;
import muchbeer.raum.searchrxjavafromapi.model.Movie;
import muchbeer.raum.searchrxjavafromapi.model.MovieResponse;
import muchbeer.raum.searchrxjavafromapi.service.DataService;
import muchbeer.raum.searchrxjavafromapi.service.RetroInstance;
import muchbeer.raum.searchrxjavafromapi.view.MainActivity;

public class MovieRepository {

    private Application application;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private MutableLiveData<List<Movie>> moviesLiveData=new MutableLiveData<>();
    private ArrayList<Movie> movies;
    private Observable<MovieResponse> movieResponseObservable;
    private static final String LOG_TAG = MovieRepository.class.getSimpleName();

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getPopularMovieWithRxJava() {

        movies = new ArrayList<>();
        DataService getMoviesDataService = RetroInstance.getService();
        movieResponseObservable = getMoviesDataService.getPopularMoviesWithRx(application.getString(R.string.api_key));

        compositeDisposable.add(movieResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<MovieResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieResponse movieResponse) throws Exception {
                        Log.d(LOG_TAG, "tHE original data fetched are: " + movieResponse);
                        return Observable.fromArray(movieResponse.getMovies().toArray(new Movie[0]));
                    }
                })
                .filter(new Predicate<Movie>() {
                    @Override
                    public boolean test(Movie movie) throws Exception {
                        return movie.getVoteAverage()>3.0;
                        //  return false;
                    }
                })
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        movies.add(movie);
                        Log.d(LOG_TAG, "The movie are here: "+ movie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG, "The movie contain a bug which is: "+
                                e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        moviesLiveData.postValue(movies);
                        Log.d(LOG_TAG, "The value movies are2: " + movies);
                    }
                }));

        return moviesLiveData;
    }
    public void clear(){
        compositeDisposable.clear();
    }

}
