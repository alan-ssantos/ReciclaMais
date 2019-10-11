package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.LoginRequest;
import br.com.fiap.reciclamais.model.GenericResponse;
import br.com.fiap.reciclamais.model.LoginResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<GenericResponse<LoginResult>> login (@Body LoginRequest request);

}
