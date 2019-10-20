package br.com.fiap.reciclamais.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.HistoricoResult;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity {

    TableLayout registroPontos;
    TextView txtNome;
    TextView txtPontuacao;
    TextView txtDescricao;

    String cpf;
    GenericResponse<UsuarioResult> usuarioResponse;
    GenericResponse<List<HistoricoResult>> historicoResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        registroPontos = findViewById(R.id.tableLayout);
        registroPontos.setStretchAllColumns(true);
        registroPontos.bringToFront();

        txtNome = findViewById(R.id.txtUsuarioNome);
        txtPontuacao = findViewById(R.id.txtUsuarioPontuacao);
        txtDescricao = findViewById(R.id.txtUsuarioMsg);

        cpf = this.getIntent().getExtras().getString("cpf");

        buscarUsuario();
        buscaHistorico();
    }

    public void buscarUsuario() {
        Call<GenericResponse<UsuarioResult>> call = new RetrofitConfig().getUsuarioService().buscarPontuacao(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioResult>>() {

            @Override
            public void onResponse(Call<GenericResponse<UsuarioResult>> call, Response<GenericResponse<UsuarioResult>> response) {
                if(response.isSuccessful()){
                    usuarioResponse = response.body();
                    txtNome.setText(usuarioResponse.getResults().getNome());
                    txtPontuacao.setText(usuarioResponse.getResults().getPontuacaoTotal().toString());
                    txtDescricao.setText("Você participou de " + String.valueOf(usuarioResponse.getResults().getReciclagemTotal()) + " coletas");

                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(UsuarioActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
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


    public void buscaHistorico(){
        Call<GenericResponse<List<HistoricoResult>>> call = new RetrofitConfig().getHistoricoService().buscar(cpf);
        call.enqueue(new Callback<GenericResponse<List<HistoricoResult>>>() {

            @Override
            public void onResponse(Call<GenericResponse<List<HistoricoResult>>> call, Response<GenericResponse<List<HistoricoResult>>> response) {
                if(response.isSuccessful()){
                    historicoResponse = response.body();
                    preencherTabela(historicoResponse.getResults());
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(UsuarioActivity.this, jsonError.getString("descricao"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<HistoricoResult>>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    public void preencherTabela(List<HistoricoResult> historicoResults) {

        if (historicoResults.isEmpty()){
            TableRow tr = new TableRow(this);
            tr.setPadding(8, 12, 8, 12);

            TextView txtMsg = new TextView(this);
            txtMsg.setPadding(8, 0, 8,0);
            txtMsg.setText("Nenhuma participação registrada");

            tr.addView(txtMsg);
            registroPontos.addView(tr);
        } else {

            int size;
            if (historicoResults.size() > 5){
                size = 5;
            }else {
                size = historicoResults.size();
            }

            for(int i = 0; i < size; i++){
                TableRow tr =  new TableRow(this);
                tr.setPadding(8, 12, 8, 12);

                if (i%2 == 1) tr.setBackgroundColor(Color.WHITE);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime localDateTime = LocalDateTime.parse(historicoResults.get(i).getData());
                String data = localDateTime.format(formatter);

                TextView txtData = new TextView(this);
                txtData.setPadding(8, 0, 8,0);
                txtData.setText(String.valueOf(data));

                TextView txtPonto = new TextView(this);
                txtPonto.setPadding(8, 0, 8,0);
                txtPonto.setText(String.valueOf(historicoResults.get(i).getPonto()));

                tr.addView(txtData);
                tr.addView(txtPonto);
                registroPontos.addView(tr);
            }
        }
    }

    public void abrirEditarUsuario(View view) {
        Intent intent = new Intent(UsuarioActivity.this, EditarUsuarioActivity.class);
        intent.putExtra("cpf", cpf);
        startActivity(intent);
    }
}
