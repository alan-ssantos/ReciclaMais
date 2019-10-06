package br.com.fiap.reciclamais.retrofit;

import br.com.fiap.reciclamais.service.CadastroService;
import br.com.fiap.reciclamais.service.LoginService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public final Retrofit retrofit;

    public RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.109:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public CadastroService getCadastroService(){
        return this.retrofit.create(CadastroService.class);
    }

    public LoginService getLoginService(){
        return this.retrofit.create(LoginService.class);
    }
}
