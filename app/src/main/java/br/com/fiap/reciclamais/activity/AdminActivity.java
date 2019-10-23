package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioLoginResult;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import br.com.fiap.reciclamais.utils.mask.MaskEditUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    TextView txtNome;
    EditText edtBuscarUsuario;
    String adminCPF;

    TextView txtUsuarioNome;
    TextView txtUsuarioEmail;
    String usuarioCpf;

    RelativeLayout spinner;
    LinearLayout linearResp;

    GenericResponse<UsuarioLoginResult> adminResponse;
    GenericResponse<UsuarioResult> usuarioResponse;
    GenericResponse<String> alterarResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminCPF = this.getIntent().getExtras().getString("cpf");

        txtNome = findViewById(R.id.txtNome);
        edtBuscarUsuario = findViewById(R.id.edtBuscarUsuario);
        edtBuscarUsuario.addTextChangedListener(MaskEditUtil.mask(edtBuscarUsuario, MaskEditUtil.FORMAT_CPF));

        txtUsuarioNome = findViewById(R.id.txtUsuarioNome);
        txtUsuarioEmail = findViewById(R.id.txtUsuarioEmail);

        spinner = findViewById(R.id.progressBar1);
        linearResp = findViewById(R.id.linearResp);

        carregarDados();
    }

    public void carregarDados() {
        spinner.setVisibility(View.VISIBLE);

        Call<GenericResponse<UsuarioLoginResult>> call = new RetrofitConfig().getUsuarioService().buscarPontuacao(adminCPF);
        call.enqueue(new Callback<GenericResponse<UsuarioLoginResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioLoginResult>> call, Response<GenericResponse<UsuarioLoginResult>> response) {
                if(response.isSuccessful()){
                    adminResponse = response.body();
                    txtNome.setText(adminResponse.getResults().getNome());
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        exibeToast(jsonError.getString("descricao"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioLoginResult>> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                exibeToast("Tente Novamente");
                Log.d("failure", t.getMessage());
            }
        });
    }

    public void buscarUsuario(View view) {
        spinner.setVisibility(View.VISIBLE);

        final String uCpf = edtBuscarUsuario.getText().toString().trim();

        Call<GenericResponse<UsuarioResult>> call = new RetrofitConfig().getUsuarioService().buscarUsuario(uCpf);
        call.enqueue(new Callback<GenericResponse<UsuarioResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioResult>> call, Response<GenericResponse<UsuarioResult>> response) {
                if (response.isSuccessful()){
                    usuarioResponse = response.body();
                    linearResp.setVisibility(View.VISIBLE);
                    txtUsuarioNome.setText(usuarioResponse.getResults().getNome());
                    txtUsuarioEmail.setText(usuarioResponse.getResults().getEmail());

                    usuarioCpf = uCpf;
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        exibeToast(jsonError.getString("descricao"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                spinner.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<GenericResponse<UsuarioResult>> call, Throwable t) {
                Log.d("err", t.getMessage());
                spinner.setVisibility(View.GONE);
                exibeToast("Tente Novamente");
            }
        });
    }

    public void alterarFuncionario(View view) {
        Call<GenericResponse<String>> call = new RetrofitConfig().getUsuarioService().alterarPerfil(usuarioCpf);
        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                if (response.isSuccessful()){
                    alterarResponse = response.body();

                    exibeToast(alterarResponse.getResults());
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        exibeToast(jsonError.getString("descricao"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                exibeToast("Tente novamente");
                Log.d("failure", t.getMessage());
            }
        });
    }

    private void exibeToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

}
