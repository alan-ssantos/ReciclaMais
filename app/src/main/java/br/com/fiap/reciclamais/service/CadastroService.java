package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.Resultado;
import br.com.fiap.reciclamais.model.UsuarioRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CadastroService  {

    @POST("cadastrar")
    Call<Resultado> cadastrar(@Body UsuarioRequest request);

}
