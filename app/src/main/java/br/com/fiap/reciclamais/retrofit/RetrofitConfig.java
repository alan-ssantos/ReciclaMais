package br.com.fiap.reciclamais.retrofit;

import br.com.fiap.reciclamais.service.CadastroService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public final Retrofit retrofit;

    public RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.59.211:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public CadastroService getCadastroService(){
        return this.retrofit.create(CadastroService.class);
    }

}
