package br.ufc.qx.adaptercervejas;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anibal on 24/10/2017.
 */

public class AdapterCervejas extends BaseAdapter {

    private final List<Cerveja> cervejas;
    private final Activity activity;

    public AdapterCervejas(List<Cerveja> cervejas, Activity activity) {
        this.cervejas = cervejas;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return cervejas.size();
    }

    @Override
    public Object getItem(int position) {
        return cervejas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cervejas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lista_item_cerveja, parent, false);

        Cerveja cerveja = cervejas.get(position);

        //pegando as referências das Views
        TextView nome = view.findViewById(R.id.cerveja_nome);
        TextView marca = view.findViewById(R.id.cerveja_marca);
        ImageView imagem = view.findViewById(R.id.cerveja_imagem);

        //populando as Views
        nome.setText(cerveja.getNome());
        marca.setText(cerveja.getMarca());
        imagem.setImageResource(cerveja.getImagem());
        return view;

    }
}
