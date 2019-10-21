package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.adapter.RegistrosAdapter;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.Registro;
import br.com.fiap.reciclamais.model.result.RegistroResult;
import br.com.fiap.reciclamais.repository.ScannerRepository;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarRegistrosActivity extends AppCompatActivity {

    List<Registro> registros;
    ScannerRepository repository;
    ListView lstRegistros;
    GenericResponse<RegistroResult> registroResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_registros);

        lstRegistros = findViewById(R.id.lstRegistros);
        repository = new ScannerRepository(this);

        atualizaLista();
    }

    public void salvar(View view) {

        if (registros == null){
            exibeToast("Nenhuma registro a ser salvo");
            return;
        }

        Call<GenericResponse<RegistroResult>> call = new RetrofitConfig().getPontuacaoService().registrar(registros);

        call.enqueue(new Callback<GenericResponse<RegistroResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<RegistroResult>> call, Response<GenericResponse<RegistroResult>> response) {
                if(response.isSuccessful()){
                    registroResponse = response.body();
                    exibeToast(registroResponse.getResults().getDescricao());
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        exibeToast(jsonError.getString("descricao"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                limparListar();
            }

            @Override
            public void onFailure(Call<GenericResponse<RegistroResult>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void atualizaLista(){
        registros = repository.listar();
        RegistrosAdapter registrosAdapter = new RegistrosAdapter(this, registros);
        lstRegistros.setAdapter(registrosAdapter);
    }

    private void limparListar(){
        repository.limpar();
        atualizaLista();
    }

    private void exibeToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

}
