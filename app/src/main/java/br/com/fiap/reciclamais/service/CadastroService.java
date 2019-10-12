package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.response.CadastroResponse;
import br.com.fiap.reciclamais.model.request.CadastroRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CadastroService  {

    @POST("cadastrar")
    Call<CadastroResponse> cadastrar(@Body CadastroRequest request);

}
