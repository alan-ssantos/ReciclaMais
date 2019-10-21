package br.com.fiap.reciclamais.service;

import java.util.List;

import br.com.fiap.reciclamais.model.Registro;
import br.com.fiap.reciclamais.model.request.TrocaRequest;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.HistoricoResult;
import br.com.fiap.reciclamais.model.result.RegistroResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface PontuacaoService {

    @GET("pontuacao/{cpf}/historico")
    Call<GenericResponse<List<HistoricoResult>>> buscar(@Path("cpf") String request);

    @POST("pontuacao/salvar")
    Call<GenericResponse<RegistroResult>> registrar(@Body List<Registro> request);

    @POST("pontuacap/trocar")
    Call<GenericResponse<String>> trocar(@Body TrocaRequest request);

}