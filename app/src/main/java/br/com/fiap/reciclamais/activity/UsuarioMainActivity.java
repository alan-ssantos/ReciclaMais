package br.com.fiap.reciclamais.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.HistoricoResult;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioMainActivity extends AppCompatActivity {

    TableLayout registroPontos;
    TextView txtNome;
    TextView txtPontuacao;

    String cpf;
    GenericResponse<UsuarioResult> usuarioResponse;
    GenericResponse<List<HistoricoResult>> historicoResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);

        registroPontos = findViewById(R.id.tableLayout);
        registroPontos.setStretchAllColumns(true);
        registroPontos.bringToFront();

        txtNome = findViewById(R.id.txtUsuarioNome);
        txtPontuacao = findViewById(R.id.txtUsuarioPontuacao);

        cpf = this.getIntent().getExtras().getString("cpf");

        buscarUsuario();
        buscaHistorico();
    }

    public void buscarUsuario() {
        Call<GenericResponse<UsuarioResult>> call = new RetrofitConfig().getUsuarioService().buscar(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioResult>>() {

            @Override
            public void onResponse(Call<GenericResponse<UsuarioResult>> call, Response<GenericResponse<UsuarioResult>> response) {
                if(response.isSuccessful()){
                    usuarioResponse = response.body();

                    txtNome.setText(usuarioResponse.getResults().getNome());
                    txtPontuacao.setText(usuarioResponse.getResults().getPontuacaoTotal().toString());

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


    public void buscaHistorico(){
        Call<GenericResponse<List<HistoricoResult>>> call = new RetrofitConfig().getHistoricoService().buscar(cpf);
        call.enqueue(new Callback<GenericResponse<List<HistoricoResult>>>() {

            @Override
            public void onResponse(Call<GenericResponse<List<HistoricoResult>>> call, Response<GenericResponse<List<HistoricoResult>>> response) {
                if(response.isSuccessful()){
                    historicoResponse = response.body();
                    preencherTabela();
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
            public void onFailure(Call<GenericResponse<List<HistoricoResult>>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    public void preencherTabela() {
        for(int i = 0; i < 5; i++){
            TableRow tr =  new TableRow(this);
            tr.setPadding(8, 12, 8, 12);

            if (i%2 == 1) tr.setBackgroundColor(Color.WHITE);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime localDateTime = LocalDateTime.parse(historicoResponse.getResults().get(i).getData());
            String data = localDateTime.format(formatter);

            TextView txtData = new TextView(this);
            txtData.setPadding(8, 0, 8,0);
            txtData.setText(String.valueOf(data));

            TextView txtPonto = new TextView(this);
            txtPonto.setText(String.valueOf(historicoResponse.getResults().get(i).getPonto()));

            tr.addView(txtData);
            tr.addView(txtPonto);
            registroPontos.addView(tr);
        }
    }

}
