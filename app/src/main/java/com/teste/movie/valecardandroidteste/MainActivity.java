package com.teste.movie.valecardandroidteste;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teste.movie.valecardandroidteste.api.MovieService;
import com.teste.movie.valecardandroidteste.model.Filme;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private Retrofit imageRetrofit;
    private EditText searchView;
    private Button buttonBuscar;
    private ImageView imagem;
    private TextView textoTitulo;
    private TextView textoAno;
    private final static String API_KEY = "a968df4c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchView.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Por favor digite o nome de um filme", Toast.LENGTH_LONG).show();
                }else {
                    recuperarFilme();
                }

            }
        });
    }

    private void recuperarFilme(){
        MovieService movieService = retrofit.create(MovieService.class);
        Call<Filme> call = movieService.recuperarFilme(API_KEY, searchView.getText().toString());

        call.enqueue(new Callback<Filme>() {
            @Override
            public void onResponse(Call<Filme> call, Response<Filme> response) {
                if(response.isSuccessful()){
                    Filme filme = response.body();
                    if(filme != null){
                        textoTitulo.setText(filme.getTitle());
                        textoAno.setText(filme.getYear());
                        Glide.with(getApplicationContext()).load(filme.getPoster()).into(imagem);
                    }
                }
            }

            @Override
            public void onFailure(Call<Filme> call, Throwable t) {

            }
        });
    }



    private void initViews() {

        searchView = findViewById(R.id.searchView);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        textoTitulo = findViewById(R.id.textoTitulo);
        textoAno = findViewById(R.id.textoAno);
        imagem = findViewById(R.id.image);
    }
}
