package br.com.fiap.reciclamais.service;

import br.com.fiap.reciclamais.model.response.EnderecoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {

    @GET("{cep}/json")
    Call<EnderecoResponse> buscar(@Path("cep") String request);

}
