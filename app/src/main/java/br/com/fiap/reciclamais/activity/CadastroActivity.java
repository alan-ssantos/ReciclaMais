package br.com.fiap.reciclamais.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.request.CadastroRequest;
import br.com.fiap.reciclamais.model.response.CadastroResponse;
import br.com.fiap.reciclamais.model.response.EnderecoResponse;
import br.com.fiap.reciclamais.model.response.GenericResponse;
import br.com.fiap.reciclamais.model.result.UsuarioResult;
import br.com.fiap.reciclamais.retrofit.RetrofitConfig;
import br.com.fiap.reciclamais.retrofit.ViaCep;
import br.com.fiap.reciclamais.utils.mask.MaskEditUtil;
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

    ViewFlipper viewFlipper;

    CadastroResponse cadastroResponse;
    EnderecoResponse enderecoResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        edtCpf = findViewById(R.id.edtCpf);
        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));

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

        viewFlipper = findViewById(R.id.viewFlipper);
    }

    public void cadastrar(View view) {
        CadastroRequest request = setCadastroRequest();

        if (request == null){
            return;
        }

        Call<CadastroResponse> call = new RetrofitConfig().getCadastroService().cadastrar(request);
        call.enqueue(new Callback<CadastroResponse>() {
            @Override
            public void onResponse(Call<CadastroResponse> call, Response<CadastroResponse> response) {
                if(response.isSuccessful()){
                    cadastroResponse = response.body();
                    exibeToast(cadastroResponse.getResults());
                    finish();
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
            public void onFailure(Call<CadastroResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private CadastroRequest setCadastroRequest(){
        CadastroRequest cadastroRequest = new CadastroRequest();

        if (validaDadosPessoais() && validaEndereco()){
            cadastroRequest.setNome(edtNome.getText().toString().trim());
            cadastroRequest.setEmail(edtEmail.getText().toString().trim());
            cadastroRequest.setSenha(edtSenha.getText().toString());
            cadastroRequest.setCpf(MaskEditUtil.unmask(edtCpf.getText().toString().trim()));
            cadastroRequest.setCep(MaskEditUtil.unmask(edtCep.getText().toString().trim()));
            cadastroRequest.setRua(edtRua.getText().toString().trim());
            int numero;
            if (edtNumero.getText().toString().trim().isEmpty()){
                numero = 0;
            } else {
                numero = Integer.parseInt(edtNumero.getText().toString().trim());
            }
            cadastroRequest.setNumero(numero);
            cadastroRequest.setEstado(edtEstado.getText().toString().trim());
            cadastroRequest.setCidade(edtCidade.getText().toString().trim());

            return cadastroRequest;
        }

        return null;
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

    public void finalizar(View view) {
        finish();
    }

    public void flipperAnterior(View view) {
        viewFlipper.showPrevious();
    }

    public void flipperProximo(View view) {
        if (validaDadosPessoais()){
            viewFlipper.showNext();
        }
    }

    private void exibeToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
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

        //Validação de CPF
        String cpf = edtCpf.getText().toString().trim();
        if (cpf.length() == 14){
            String expressao = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}";
            Pattern pattern = Pattern.compile(expressao);
            Matcher matcher = pattern.matcher(cpf);
            if (!matcher.matches()){
                exibeToast("Insira um CPF válido");
                return false;
            }
        } else {
            exibeToast("Insira um CPF válido");
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
        if (estado.isEmpty() || estado.length() < 4){
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
}
