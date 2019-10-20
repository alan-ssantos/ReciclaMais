package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.Usuario;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("usuario/{cpf}")
    Call<GenericResponse<Usuario>> buscarUsuario(@Path("cpf") String request);

    @GET("pontuacao/{cpf}")
    Call<GenericResponse<UsuarioResult>> buscarPontuacao(@Path("cpf") String request);

    @POST("cadastrar")
    Call<GenericResponse<String>> cadastrar(@Body Usuario request);


    @PATCH("alterar/{cpf}")
    Call<GenericResponse<String>> alterar(Usuario usuario);

    @DELETE("deletar/{cpf}")
    Call<GenericResponse<String>> deletar(@Path("cpf") String request);

}
