package br.com.fiap.reciclamais.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.com.fiap.reciclamais.R;

public class ScannerActivity extends AppCompatActivity {

    private TextView txtResult;
    private String lastTxt;
    private Activity activity = this;
    private DecoratedBarcodeView barcodeView;
    public static final int MY_PERMISSIONS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        txtResult =  findViewById(R.id.txtResult);
        barcodeView = this.findViewById(R.id.barcode_scanner);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                mostraAlerta();

            } else {
                solicitaPermissao();
            }
        } else {
            initScanner();
        }
    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            // Evita Scans duplicados
            if (result.getText() == null || result.getText().equals(lastTxt)) {
                return;
            }

            lastTxt = result.getText();
            txtResult.setText("Ultimo: " + lastTxt);

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    private void initScanner(){
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getIntent());
        barcodeView.decodeContinuous(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void mostraAlerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Precisamos usa a camera").setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                solicitaPermissao();
            }
        })
        .setNegativeButton("Negar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                exibeToast("Não foi possivel iniciar a camera");
            }
        });
        builder.create();
        builder.show();
    }

    public void solicitaPermissao(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initScanner();
                } else {
                    exibeToast("Não foi possivel iniciar a camera");
                }
                return;
            }
        }
    }

    public void exibeToast(String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
