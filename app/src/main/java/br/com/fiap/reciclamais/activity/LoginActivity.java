package br.com.fiap.reciclamais.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.request.LoginRequest;
import br.com.fiap.reciclamais.model.result.LoginResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import br.com.fiap.reciclamais.utils.enums.PerfilEnum;
import br.com.fiap.reciclamais.utils.mask.MaskEditUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtCPF;
    EditText edtSenha;
    Button btnEntrar;

    GenericResponse<LoginResult> loginResponse;
    Activity activity = LoginActivity.this;
    LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtCPF = findViewById(R.id.edtLoCPF);
        edtCPF.addTextChangedListener(MaskEditUtil.mask(edtCPF, MaskEditUtil.FORMAT_CPF));
        edtSenha = findViewById(R.id.edtLoSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
    }

    public void logar(View view) {
        btnEntrar.setEnabled(false);

        loginRequest = setLoginRequest();

        if (loginRequest.getCpf().isEmpty() || loginRequest.getSenha().isEmpty()){
            Toast.makeText(this, "Insira CPF e Senha", Toast.LENGTH_SHORT).show();
            btnEntrar.setEnabled(true);
            return;
        }

        Call<GenericResponse<LoginResult>> call = new RetrofitConfig().getLoginService().login(loginRequest);
        call.enqueue(new Callback<GenericResponse<LoginResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<LoginResult>> call, Response<GenericResponse<LoginResult>> response) {
                if(response.isSuccessful()){
                    loginResponse = response.body();
                    abrirPerfil(loginResponse.getResults().getPerfil());
                } else {
                    try {
                        btnEntrar.setEnabled(true);
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
                btnEntrar.setEnabled(true);
            }
        });
    }

    private LoginRequest setLoginRequest(){
        LoginRequest request = new LoginRequest();

        request.setCpf(MaskEditUtil.unmask(edtCPF.getText().toString().trim()));
        request.setSenha(edtSenha.getText().toString());

        return request;
    }

    private void abrirPerfil(PerfilEnum perfil){
        Intent intent;

        switch (perfil){
            case CLIENTE: intent = new Intent(activity, UsuarioActivity.class); break;
            case FUNCIONARIO: intent =  new Intent(activity, FuncionarioActivity.class); break;
            default: throw new IllegalStateException("Unexpected value: " + perfil);
        }

        intent.putExtra("cpf", loginRequest.getCpf());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void abrirCadastro(View view) {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }
}
