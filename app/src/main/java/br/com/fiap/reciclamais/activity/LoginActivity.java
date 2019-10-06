package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.LoginRequest;
import br.com.fiap.reciclamais.model.CadastroResponse;
import br.com.fiap.reciclamais.model.LoginResponse;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtCPF;
    EditText edtSenha;

    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtCPF = findViewById(R.id.edtLoCPF);
        edtSenha = findViewById(R.id.edtLoSenha);

    }


    public void logar(View view) {
        LoginRequest loginRequest = setLoginRequest();

        Call<LoginResponse> call = new RetrofitConfig().getLoginService().login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    loginResponse = response.body();
                    Toast.makeText(LoginActivity.this, loginResponse.getResults().getNome(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
}
