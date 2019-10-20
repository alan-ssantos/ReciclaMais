package br.com.fiap.reciclamais.retrofit;

import br.com.fiap.reciclamais.service.CadastroService;
import br.com.fiap.reciclamais.service.HistoricoService;
import br.com.fiap.reciclamais.service.LoginService;
import br.com.fiap.reciclamais.service.RegistrosService;
import br.com.fiap.reciclamais.service.UsuarioService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public final Retrofit retrofit;

    public RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.108:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public CadastroService getCadastroService(){
        return this.retrofit.create(CadastroService.class);
    }

    public LoginService getLoginService(){
        return this.retrofit.create(LoginService.class);
    }

    public RegistrosService getRegistroService(){
        return this.retrofit.create(RegistrosService.class);
    }

    public UsuarioService getUsuarioService(){
        return this.retrofit.create(UsuarioService.class);
    }

    public HistoricoService getHistoricoService(){
        return this.retrofit.create(HistoricoService.class);
    }

}
