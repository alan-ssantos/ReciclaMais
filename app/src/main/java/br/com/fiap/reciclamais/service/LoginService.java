package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.request.LoginRequest;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.LoginResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<GenericResponse<LoginResult>> login (@Body LoginRequest request);

}
