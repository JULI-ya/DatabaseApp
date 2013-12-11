package com.database.app.activities.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.database.app.R;
import com.database.app.activities.adapters.ProductsListAdapter;
import com.database.app.activities.database.DatabaseHandler;

public class MyActivity extends Activity implements View.OnClickListener {

    ListView mList;
    DatabaseHandler mDatabaseHandler;
    private ProductsListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mList = (ListView) findViewById(R.id.products);
        TextView textView = (TextView) findViewById(R.id.empty);
        mList.setEmptyView(textView);
        mDatabaseHandler = new DatabaseHandler(this);
        mAdapter = new ProductsListAdapter(this, R.id.name, mDatabaseHandler.getProducts());
        mList.setAdapter(mAdapter);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showAddingDialog();

    }

    private void showAddingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText price = (EditText) view.findViewById(R.id.price);
        final EditText amount = (EditText) view.findViewById(R.id.amount);
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        builder.setView(view);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (name.getText().toString().trim().equals(""))
                    name.setError("Заполните!");
                else {
                    mDatabaseHandler.addProduct(name.getText().toString(), price.getText().toString(), amount.getText().toString(), fId.getText().toString());
                    mAdapter.setProducts(mDatabaseHandler.getProducts());
                    mAdapter.notifyDataSetChanged();
                    dialog.cancel();
                }
            }
        })

                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
