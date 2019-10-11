package br.com.fiap.reciclamais.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.GenericResponse;
import br.com.fiap.reciclamais.model.LoginRequest;
import br.com.fiap.reciclamais.model.LoginResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtCPF;
    EditText edtSenha;

    GenericResponse<LoginResult> loginResponse;

    Activity activity = LoginActivity.this;
    LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtCPF = findViewById(R.id.edtLoCPF);
        edtSenha = findViewById(R.id.edtLoSenha);

    }


    public void logar(View view) {
         loginRequest = setLoginRequest();

        if (loginRequest.getCpf().isEmpty() || loginRequest.getSenha().isEmpty()){
            Toast.makeText(this, "Insira CPF e Senha", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<GenericResponse<LoginResult>> call = new RetrofitConfig().getLoginService().login(loginRequest);
        call.enqueue(new Callback<GenericResponse<LoginResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<LoginResult>> call, Response<GenericResponse<LoginResult>> response) {
                if(response.isSuccessful()){
                    loginResponse = response.body();
                    Toast.makeText(LoginActivity.this, loginResponse.getResults().getNome(), Toast.LENGTH_SHORT).show();

                    abrirUsuarioMain();

                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<LoginResult>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private LoginRequest setLoginRequest(){
        LoginRequest request = new LoginRequest();

        request.setCpf(edtCPF.getText().toString().trim());
        request.setSenha(edtSenha.getText().toString());

        return request;
    }

    private void abrirUsuarioMain(){
        Intent intent = new Intent(activity, UsuarioMainActivity.class);
        intent.putExtra("cpf", loginRequest.getCpf());
        startActivity(intent);

    }

}
