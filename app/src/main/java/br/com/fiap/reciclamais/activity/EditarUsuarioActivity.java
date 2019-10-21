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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.Usuario;
import br.com.fiap.reciclamais.model.response.EnderecoResponse;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import br.com.fiap.reciclamais.retrofit.ViaCep;
import br.com.fiap.reciclamais.utils.enums.PerfilEnum;
import br.com.fiap.reciclamais.utils.mask.MaskEditUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarUsuarioActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtSenha;
    EditText edtCep;
    EditText edtRua;
    EditText edtNumero;
    EditText edtEstado;
    EditText edtCidade;

    String cpf;

    Activity activity = EditarUsuarioActivity.this;

    GenericResponse<UsuarioResult> usuarioResponse;
    EnderecoResponse enderecoResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCep = findViewById(R.id.edtCep);
        edtCep.addTextChangedListener(MaskEditUtil.mask(edtCep, MaskEditUtil.FORMAT_CEP));
        edtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) buscaEndereco();
            }
        });
        edtRua = findViewById(R.id.edtRua);
        edtNumero = findViewById(R.id.edtNumero);
        edtEstado = findViewById(R.id.edtEstado);
        edtCidade = findViewById(R.id.edtCidade);

        cpf = this.getIntent().getExtras().getString("cpf");

        buscaUsuario();
    }

    private void buscaUsuario() {
        Call<GenericResponse<UsuarioResult>> call = new RetrofitConfig().getUsuarioService().buscarUsuario(cpf);
        call.enqueue(new Callback<GenericResponse<UsuarioResult>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioResult>> call, Response<GenericResponse<UsuarioResult>> response) {
                if (response.isSuccessful()){
                    usuarioResponse = response.body();
                    preencherInputs(usuarioResponse.getResults());
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
            public void onFailure(Call<GenericResponse<UsuarioResult>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }


    private void preencherInputs(UsuarioResult usuario){
       edtNome.setText(usuario.getNome());
       edtEmail.setText(usuario.getEmail());
       edtSenha.setText(usuario.getSenha());
       edtCep.setText(usuario.getEndereco().getCep());
       edtRua.setText(usuario.getEndereco().getRua());
       edtNumero.setText(String.valueOf(usuario.getEndereco().getNumero()));
       edtEstado.setText(usuario.getEndereco().getEstado());
       edtCidade.setText(usuario.getEndereco().getCidade());
    }

    public void salvarAlteracoes(View view) {
        Usuario request = setUsuarioRequest();
        if (request == null) return;

        Call<GenericResponse<String>> call = new RetrofitConfig().getUsuarioService().atualizar(request);
        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                if (response.isSuccessful()){
                    finalizar();
                } else{
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
                Log.d("err", t.getMessage());
            }
        });
    }

    private Usuario setUsuarioRequest() {
        Usuario usuario = new Usuario();

        if (validaDadosPessoais() && validaEndereco()){
            usuario.setNome(edtNome.getText().toString().trim());
            usuario.setEmail(edtEmail.getText().toString().trim());
            usuario.setCpf(cpf);
            usuario.setSenha(edtSenha.getText().toString());
            usuario.setCep(MaskEditUtil.unmask(edtCep.getText().toString().trim()));
            usuario.setRua(edtRua.getText().toString().trim());
            usuario.setNumero(Integer.parseInt(edtNumero.getText().toString().trim()));
            usuario.setEstado(edtEstado.getText().toString().trim());
            usuario.setCidade(edtCidade.getText().toString().trim());

            return usuario;
        }

        return null;
    }

    private boolean validaDadosPessoais(){
        //Validação do nome
        String nome = edtNome.getText().toString().trim();
        if (nome.isEmpty() || nome.length() <= 1){
            exibeToast("Insira um nome válido");
            return false;
        }

        //Validação do e-mail
        String email = edtEmail.getText().toString().trim();
        if (!email.isEmpty() && email.length() > 5) {
            String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                exibeToast("Insira um e-mail válido");
                return false;
            }
        } else {
            exibeToast("Insira um e-mail válido");
            return false;
        }

        //Validação de Senha
        String senha = edtSenha.getText().toString();
        if (senha.isEmpty()){
            exibeToast("Insira uma senha");
            return false;
        }

        return true;
    }

    private boolean validaEndereco(){
        //Validação de CEP
        String cep = edtCep.getText().toString().trim();
        if (cep.isEmpty() || cep.length() < 9){
            exibeToast("Insira um CEP válido");
            return false;
        }

        //Validação de Rua
        String rua = edtRua.getText().toString().trim();
        if (rua.isEmpty() || rua.length() < 4){
            exibeToast("Insira uma Rua válida");
            return false;
        }

        //Validação de Estado
        String estado = edtEstado.getText().toString().trim();
        if (estado.isEmpty() || estado.length() < 2){
            exibeToast("Insira um Estado válido");
            return false;
        }

        //Validação de Cidade
        String cidade = edtCidade.getText().toString().trim();
        if (cidade.isEmpty() || cidade.length() < 4){
            exibeToast("Insira uma Cidade válida");
            return false;
        }

        return true;
    }

    private void buscaEndereco() {
        String cep = MaskEditUtil.unmask(edtCep.getText().toString().trim());

        Call<EnderecoResponse> call = new ViaCep().getViaCepService().buscar(cep);
        call.enqueue(new Callback<EnderecoResponse>() {
            @Override
            public void onResponse(Call<EnderecoResponse> call, Response<EnderecoResponse> response) {
                if(response.isSuccessful()){
                    enderecoResponse = response.body();
                    if (enderecoResponse != null) {
                        preencheEndereco(enderecoResponse);
                    }
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
            public void onFailure(Call<EnderecoResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void preencheEndereco(EnderecoResponse response) {
        edtRua.setText(response.getLogradouro());
        edtEstado.setText(response.getUf());
        edtCidade.setText(response.getLocalidade());
    }

    private void abrirPerfil(PerfilEnum perfil){
        Intent intent;
        switch (perfil){
            case CLIENTE: intent = new Intent(activity, UsuarioActivity.class); break;
            case FUNCIONARIO: intent =  new Intent(activity, FuncionarioActivity.class); break;
            default: throw new IllegalStateException("Unexpected value: " + perfil);
        }

        intent.putExtra("cpf", cpf);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void deletarConta(View view){
        Call<GenericResponse<String>> call = new RetrofitConfig().getUsuarioService().deletar(cpf);
        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                String result = response.body().getResults();
                exibeToast(result);
                finalizar();
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void finalizar(){
        Intent intent = new Intent(EditarUsuarioActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }

    private void exibeToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

}
