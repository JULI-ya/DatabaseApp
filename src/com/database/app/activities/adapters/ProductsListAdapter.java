package com.database.app.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.database.app.R;
import com.database.app.activities.entities.Product;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by y.klimko on 11.12.13.
 */
public class ProductsListAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> products;

    public ProductsListAdapter(Context context, int textViewResourceId, ArrayList<Product> products) {
        super(context, textViewResourceId);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.product_item, null);
        }

        Product product = products.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(product.getName());
        TextView price = (TextView) convertView.findViewById(R.id.price);
        price.setText(product.getPrice() + " руб.");
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        amount.setText(product.getAmount() + " шт.");

        return convertView;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
