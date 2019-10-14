package br.com.fiap.reciclamais.service;

import java.util.List;

import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.HistoricoResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HistoricoService {

    @GET("/pontuacao/{cpf}/historico")
    Call<GenericResponse<List<HistoricoResult>>> buscar(@Path("cpf") String request);

}
