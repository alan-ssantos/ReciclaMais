package br.com.fiap.reciclamais.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.fiap.reciclamais.R;
import br.com.fiap.reciclamais.model.Registro;

public class RegistrosAdapter extends BaseAdapter {

    private Context context;
    private List<Registro> registros;

    public RegistrosAdapter(Context context, List<Registro> registros) {
        this.context = context;
        this.registros = registros;
    }

    @Override
    public int getCount() {
        return this.registros.size();
    }

    @Override
    public Object getItem(int i) {
        return this.registros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        Registro registro = this.registros.get(i);

        View v = layoutInflater.inflate(R.layout.listview_registros, null);
        TextView txtData = v.findViewById(R.id.txtData);
        TextView txtUuid = v.findViewById(R.id.txtUuid);

        txtData.setText(registro.getData());
        txtUuid.setText(registro.getUuid());

        return v;
    }
}
