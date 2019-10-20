package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.Usuario;

public class EditarUsuarioActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtSenha;
    EditText edtCep;
    EditText edtRua;
    EditText edtNumero;
    EditText edtEstado;
    EditText edtCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCep = findViewById(R.id.edtCep);
        edtRua = findViewById(R.id.edtRua);
        edtNumero = findViewById(R.id.edtNumero);
        edtEstado = findViewById(R.id.edtEstado);
        edtCidade = findViewById(R.id.edtCidade);

        Usuario user = new Usuario();
        user.setNome("Felipe Nadroga");
        user.setEmail("nadroga@email.com");
        user.setSenha("123");
        user.setCep("08072155");
        user.setRua("Belo Horizonte");
        user.setNumero(1);
        user.setEstado("São Paulo");
        user.setCidade("Guarulhos");

        preencherInputs(user);
    }

    public void preencherInputs(Usuario usuario){
       edtNome.setText(usuario.getNome());
       edtEmail.setText(usuario.getEmail());
       edtSenha.setText(usuario.getSenha());
       edtCep.setText(usuario.getCep());
       edtRua.setText(usuario.getRua());
       edtNumero.setText(usuario.getNumero().toString());
       edtEstado.setText(usuario.getEstado());
       edtCidade.setText(usuario.getCidade());
    }

}
