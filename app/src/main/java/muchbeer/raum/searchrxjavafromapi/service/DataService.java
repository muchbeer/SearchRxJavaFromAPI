package muchbeer.raum.searchrxjavafromapi.service;

import io.reactivex.Observable;
import muchbeer.raum.searchrxjavafromapi.model.MovieResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMoviesWithRx(@Query("api_key") String apiKey);

}
