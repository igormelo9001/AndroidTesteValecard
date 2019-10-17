package com.teste.movie.valecardandroidteste.api;

import com.teste.movie.valecardandroidteste.model.Filme;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET(".")
    Call<Filme> recuperarFilme(@Query("apikey") String apikey, @Query("t") String query);

}
