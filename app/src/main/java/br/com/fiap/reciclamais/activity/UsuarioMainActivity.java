package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;
import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.Usuario;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UsuarioMainActivity extends AppCompatActivity {

    TableLayout registroPontos;
    Usuario usuarioTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);

        registroPontos = findViewById(R.id.tableLayout);
        registroPontos.setStretchAllColumns(true);
        registroPontos.bringToFront();

        criarInfoTabela();
    }

    public void criarUsuario() {

    }

    public void criarInfoTabela() {
        for(int i = 0; i < 4; i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(usuarioTO[i].getName());
            TextView c2 = new TextView(this);
            c2.setText(String.valueOf(usuarioTO[i].getPrice()));
            tr.addView(c1);
            tr.addView(c2);
            registroPontos.addView(tr);
        }
    }
}
