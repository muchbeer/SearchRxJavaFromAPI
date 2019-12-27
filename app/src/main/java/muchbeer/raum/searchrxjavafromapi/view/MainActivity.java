package muchbeer.raum.searchrxjavafromapi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import muchbeer.raum.searchrxjavafromapi.R;
import muchbeer.raum.searchrxjavafromapi.adapter.MovieAdapter;
import muchbeer.raum.searchrxjavafromapi.model.Movie;
import muchbeer.raum.searchrxjavafromapi.model.MovieResponse;
import muchbeer.raum.searchrxjavafromapi.service.DataService;
import muchbeer.raum.searchrxjavafromapi.service.RetroInstance;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeContainer;
    private MainActivityViewModel mainActivityViewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
EditText searchEditText = findViewById(R.id.movie_search);
        getSupportActionBar().setTitle(" The movie team");
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        getPopularMovieWithRxJava();
        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovieWithRxJava();
            }
        });

   /*     searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            movieAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
       compositeDisposable.add(RxTextView.textChangeEvents(searchEditText)
                .skipInitialValue()
                .debounce(300, TimeUnit.MICROSECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        movieAdapter.getFilter().filter(textViewTextChangeEvent.getText());
                        Log.d(LOG_TAG, "The value seached "+ textViewTextChangeEvent);
                     //   movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG, "The error gotten from search: "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

  }

    private void getPopularMovieWithRxJava() {

        mainActivityViewModel.getAllMovies().observe(this, listofMovies->{
            movies=(ArrayList<Movie>) listofMovies;
            init();
        });
    }


    private void init() {
        recyclerView = findViewById(R.id.rView);
        movieAdapter = new MovieAdapter(this, movies);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
