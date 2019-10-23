package br.com.fiap.reciclamais.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioLoginResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioActivity extends AppCompatActivity {

    GenericResponse<UsuarioLoginResult> usuarioResponse;
    TextView txtNome;
    TextView txtMensagem;

    RelativeLayout spinner;

    String cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        txtNome = findViewById(R.id.txtFuncNome);
        txtMensagem = findViewById(R.id.txtFuncDescricao);

        spinner = findViewById(R.id.progressBar1);

        cpf = this.getIntent().getExtras().getString("cpf");
        carregaDados();
    }

    private void carregaDados() {
        spinner.setVisibility(View.VISIBLE);

        Call<GenericResponse<UsuarioLoginResult>> call = new RetrofitConfig().getUsuarioService().buscarPontuacao(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioLoginResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioLoginResult>> call, Response<GenericResponse<UsuarioLoginResult>> response) {
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
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioLoginResult>> call, Throwable t) {
                Toast.makeText(FuncionarioActivity.this, "Tente novamente", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.GONE);
                Log.d("failure", t.getMessage());
            }
        });
    }

    public void abrirScanner(View view) {
        startActivity(new Intent(FuncionarioActivity.this, ScannerActivity.class));
    }

    public void abrirLista(View view) {
        startActivity(new Intent(FuncionarioActivity.this, ListarRegistrosActivity.class));
    }

    public void abrirEditarFuncinario(View view) {
        Intent intent = new Intent(FuncionarioActivity.this, EditarUsuarioActivity.class);
        intent.putExtra("cpf", cpf);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaDados();
    }
}
