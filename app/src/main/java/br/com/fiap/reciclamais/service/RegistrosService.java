package br.com.fiap.reciclamais.service;

import java.util.List;

import br.com.fiap.reciclamais.model.GenericResponse;
import br.com.fiap.reciclamais.model.Registro;
import br.com.fiap.reciclamais.model.RegistroResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrosService  {

    @POST("pontuacao/salvar")
    Call<GenericResponse<RegistroResult>> registrar(@Body List<Registro> request);
}
