package br.com.fiap.reciclamais.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioActivity extends AppCompatActivity {

    GenericResponse<UsuarioResult> usuarioResponse;
    TextView txtNome;
    TextView txtMensagem;
    String cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        txtNome = findViewById(R.id.txtFuncNome);
        txtMensagem = findViewById(R.id.txtFuncDescricao);

        cpf = this.getIntent().getExtras().getString("cpf");
        carregaDados();
    }

    private void carregaDados() {
        Call<GenericResponse<UsuarioResult>> call = new RetrofitConfig().getUsuarioService().buscarPontuacao(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioResult>> call, Response<GenericResponse<UsuarioResult>> response) {
                if (response.isSuccessful()){
                    usuarioResponse = response.body();
                    txtNome.setText(usuarioResponse.getResults().getNome());
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(FuncionarioActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioResult>> call, Throwable t) {

            }
        });
    }

    public void abrirScanner(View view) {
        startActivity(new Intent(FuncionarioActivity.this, ScannerActivity.class));
    }

    public void abrirLista(View view) {
        startActivity(new Intent(FuncionarioActivity.this, ListarRegistrosActivity.class));
    }

}
