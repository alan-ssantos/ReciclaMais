package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.Resultado;
import br.com.fiap.reciclamais.model.UsuarioRequest;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import br.com.fiap.reciclamais.utils.enums.StatusResponseEnum;
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

    Resultado resultado;

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

        UsuarioRequest usuarioRequest = setUsuarioRequest();

        Call<Resultado> call = new RetrofitConfig().getCadastroService().cadastrar(usuarioRequest);
        call.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                if(response.isSuccessful()){
                    resultado = response.body();
                    Toast.makeText(CadastroActivity.this, resultado.getResults(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<Resultado> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private UsuarioRequest setUsuarioRequest(){
        UsuarioRequest usuarioRequest = new UsuarioRequest();

        usuarioRequest.setNome(edtNome.getText().toString().trim());
        usuarioRequest.setEmail(edtEmail.getText().toString().trim());
        usuarioRequest.setSenha(edtSenha.getText().toString());
        usuarioRequest.setCpf(edtCpf.getText().toString().trim());
        usuarioRequest.setCep(edtCep.getText().toString().trim());
        usuarioRequest.setRua(edtRua.getText().toString().trim());
        usuarioRequest.setNumero(Integer.parseInt(edtNumero.getText().toString().trim()));
        usuarioRequest.setEstado(edtEstado.getText().toString().trim());
        usuarioRequest.setCidade(edtCidade.getText().toString().trim());

        return usuarioRequest;
    }

}
