package br.com.fiap.reciclamais.retrofit;

import br.com.fiap.reciclamais.service.PontuacaoService;
import br.com.fiap.reciclamais.service.UsuarioService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public final Retrofit retrofit;

    public RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api-reciclamais.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public UsuarioService getUsuarioService(){
        return this.retrofit.create(UsuarioService.class);
    }

    public PontuacaoService getPontuacaoService(){
        return this.retrofit.create(PontuacaoService.class);
    }

}
