package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.LoginRequest;
import br.com.fiap.reciclamais.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<LoginResponse> login (@Body LoginRequest request);

}
