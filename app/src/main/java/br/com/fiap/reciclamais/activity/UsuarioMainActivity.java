package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;
import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.GenericResponse;
import br.com.fiap.reciclamais.model.LoginResult;
import br.com.fiap.reciclamais.model.Usuario;
import br.com.fiap.reciclamais.model.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class UsuarioMainActivity extends AppCompatActivity {

    TableLayout registroPontos;
    TextView txtNome;
    TextView txtPontuacao;

    String cpf;
    GenericResponse<UsuarioResult> usuarioResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);

        registroPontos = findViewById(R.id.tableLayout);
        registroPontos.setStretchAllColumns(true);
        registroPontos.bringToFront();

        txtNome = findViewById(R.id.txtUsuarioNome);
        txtPontuacao = findViewById(R.id.txtUsuarioPontucao);

        cpf = this.getIntent().getExtras().getString("cpf");

        criarUsuario();
    }

    public void criarUsuario() {
        Call<GenericResponse<UsuarioResult>> call = new RetrofitConfig().getUsuarioService().buscar(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioResult>>() {

            @Override
            public void onResponse(Call<GenericResponse<UsuarioResult>> call, Response<GenericResponse<UsuarioResult>> response) {
                if(response.isSuccessful()){
                    usuarioResponse = response.body();

                    txtNome.setText(usuarioResponse.getResults().getNome());
                    txtPontuacao.setText(usuarioResponse.getResults().getPontuacaoTotal().toString());

                    criarUsuario();

                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(UsuarioMainActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
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

//    public void criarInfoTabela() {
//        for(int i = 0; i < 4; i++){
//            TableRow tr =  new TableRow(this);
//            TextView c1 = new TextView(this);
//            c1.setText(usuarioTO[i].getName());
//            TextView c2 = new TextView(this);
//            c2.setText(String.valueOf(usuarioTO[i].getPrice()));
//            tr.addView(c1);
//            tr.addView(c2);
//            registroPontos.addView(tr);
//        }
//    }
}
