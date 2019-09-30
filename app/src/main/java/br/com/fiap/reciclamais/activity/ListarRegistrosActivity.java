package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.resource.ScannerResource;
import br.com.fiap.reciclamais.service.ScannerService;

public class ListarRegistrosActivity extends AppCompatActivity {

    List<String> registros;
    ScannerResource resource;
    ListView lstRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_registros);

        lstRegistros = findViewById(R.id.lstRegistros);
        resource = new ScannerResource(this);

        registros = resource.listar();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registros);
        lstRegistros.setAdapter(adapter);
    }

    public void salvar(View view) {
        finish();
    }
}
