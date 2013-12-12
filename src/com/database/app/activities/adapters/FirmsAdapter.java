package com.database.app.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.database.app.R;
import com.database.app.activities.entities.Firma;

import java.util.ArrayList;

/**
 * Created by y.klimko on 12.12.13.
 */
public class FirmsAdapter extends ArrayAdapter<Firma> {

    private Context context;
    private ArrayList<Firma> firms;

    public FirmsAdapter(Context context, int textViewResourceId, ArrayList<Firma> firms) {
        super(context, textViewResourceId);
        this.firms = firms;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.firma_item, null);
        }

        Firma firma = firms.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(firma.getName());
        TextView sity = (TextView) convertView.findViewById(R.id.sity);
        sity.setText(firma.getSity());
        TextView id = (TextView) convertView.findViewById(R.id.id);
        id.setText("Идентификатор: " + firma.getId());

        return convertView;
    }

    @Override
    public int getCount() {
        return firms.size();
    }

    @Override
    public Firma getItem(int position) {
        return firms.get(position);
    }

    public void setFirms(ArrayList<Firma> firms) {
        this.firms = firms;
    }

}
