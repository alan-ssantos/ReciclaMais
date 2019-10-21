package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.Usuario;
import br.com.fiap.reciclamais.model.request.LoginRequest;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.LoginResult;
import br.com.fiap.reciclamais.model.result.UsuarioLoginResult;
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
    Call<GenericResponse<UsuarioResult>> buscarUsuario(@Path("cpf") String request);

    @GET("pontuacao/{cpf}")
    Call<GenericResponse<UsuarioLoginResult>> buscarPontuacao(@Path("cpf") String request);

    @POST("usuario/cadastrar")
    Call<GenericResponse<String>> cadastrar(@Body Usuario request);

    @POST("usuario/login")
    Call<GenericResponse<LoginResult>> login (@Body LoginRequest request);

    @PATCH("usuario/atualizar")
    Call<GenericResponse<String>> atualizar(@Body Usuario usuario);

    @DELETE("usuario/{cpf}")
    Call<GenericResponse<String>> deletar(@Path("cpf") String request);

}
