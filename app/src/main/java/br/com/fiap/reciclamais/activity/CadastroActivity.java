package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.CadastroResponse;
import br.com.fiap.reciclamais.model.CadastroRequest;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtSenha;
    EditText edtCpf;
    EditText edtCep;
    EditText edtRua;
    EditText edtNumero;
    EditText edtEstado;
    EditText edtCidade;

    CadastroResponse cadastroResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCpf = findViewById(R.id.edtCpf);
        edtCep = findViewById(R.id.edtCep);
        edtRua = findViewById(R.id.edtRua);
        edtNumero = findViewById(R.id.edtNumero);
        edtEstado = findViewById(R.id.edtEstado);
        edtCidade = findViewById(R.id.edtCidade);

    }

    public void cadastrar(View view) {

        CadastroRequest request = setCadastroRequest();

        Call<CadastroResponse> call = new RetrofitConfig().getCadastroService().cadastrar(request);
        call.enqueue(new Callback<CadastroResponse>() {
            @Override
            public void onResponse(Call<CadastroResponse> call, Response<CadastroResponse> response) {
                if(response.isSuccessful()){
                    cadastroResponse = response.body();
                    Toast.makeText(CadastroActivity.this, cadastroResponse.getResults(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(CadastroActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CadastroResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private CadastroRequest setCadastroRequest(){
        CadastroRequest cadastroRequest = new CadastroRequest();

        cadastroRequest.setNome(edtNome.getText().toString().trim());
        cadastroRequest.setEmail(edtEmail.getText().toString().trim());
        cadastroRequest.setSenha(edtSenha.getText().toString());
        cadastroRequest.setCpf(edtCpf.getText().toString().trim());
        cadastroRequest.setCep(edtCep.getText().toString().trim());
        cadastroRequest.setRua(edtRua.getText().toString().trim());
        cadastroRequest.setNumero(Integer.parseInt(edtNumero.getText().toString().trim()));
        cadastroRequest.setEstado(edtEstado.getText().toString().trim());
        cadastroRequest.setCidade(edtCidade.getText().toString().trim());

        return cadastroRequest;
    }

}
