package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.GenericResponse;
import br.com.fiap.reciclamais.model.UsuarioResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("pontuacao/{cpf}")
    Call<GenericResponse<UsuarioResult>> buscar(@Path("cpf") String request);

}
