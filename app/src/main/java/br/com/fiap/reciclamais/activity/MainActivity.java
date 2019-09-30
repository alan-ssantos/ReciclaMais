package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.resource.ScannerResource;

public class MainActivity extends AppCompatActivity {

    ScannerResource resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        resource = new ScannerResource(this);
    }


    public void abrirScanner(View view) {
        startActivity(new Intent(MainActivity.this, ScannerActivity.class));
    }

    public void abrirLista(View view) {
        startActivity(new Intent(MainActivity.this, ListarRegistrosActivity.class));
    }
}
