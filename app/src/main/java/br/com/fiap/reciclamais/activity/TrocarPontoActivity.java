package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;
import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.Usuario;
import br.com.fiap.reciclamais.model.request.TrocaRequest;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioLoginResult;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class TrocarPontoActivity extends AppCompatActivity {

    TextView txtNome;
    TextView txtPontuacao;

    String cpf;
    GenericResponse<UsuarioLoginResult> usuarioResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_ponto);

        txtNome = findViewById(R.id.txtUsuarioNome);
        txtPontuacao = findViewById(R.id.txtUsuarioPontuacao);

        cpf = this.getIntent().getExtras().getString("cpf");

        buscarUsuario();
    }

    public void buscarUsuario() {
        Call<GenericResponse<UsuarioLoginResult>> call = new RetrofitConfig().getUsuarioService().buscarPontuacao(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioLoginResult>>() {

            @Override
            public void onResponse(Call<GenericResponse<UsuarioLoginResult>> call, Response<GenericResponse<UsuarioLoginResult>> response) {
                if(response.isSuccessful()){
                    usuarioResponse = response.body();
                    txtNome.setText(usuarioResponse.getResults().getNome());
                    txtPontuacao.setText(String.valueOf(usuarioResponse.getResults().getPontuacaoTotal()));
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrocarPontoActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioLoginResult>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    public void trocarPontos(View view) {
        String voucher = "10";
        TrocaRequest trocaRequest = new TrocaRequest();
        trocaRequest.setCpf(cpf);
        trocaRequest.setVoucher(voucher);

        Call<GenericResponse<String>> call = new RetrofitConfig().getPontuacaoService().trocar(trocaRequest);
        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                if(response.isSuccessful()){
                    String result = response.body().getResults();
                    Toast.makeText(TrocarPontoActivity.this, result, Toast.LENGTH_SHORT).show();
                    buscarUsuario();
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(TrocarPontoActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });

    }

}
