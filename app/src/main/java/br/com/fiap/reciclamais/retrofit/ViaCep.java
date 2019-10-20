package br.com.fiap.reciclamais.retrofit;

import br.com.fiap.reciclamais.service.ViaCepService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViaCep {

    public final Retrofit retrofit;

    public ViaCep() {
        this.retrofit =  new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ViaCepService getViaCepService(){
        return this.retrofit.create(ViaCepService.class);
    }

}
