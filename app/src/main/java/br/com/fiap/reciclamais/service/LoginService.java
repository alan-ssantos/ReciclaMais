package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.LoginRequest;
import br.com.fiap.reciclamais.model.GenericResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("login")
    Call<GenericResponse> login (@Body LoginRequest request);

}
