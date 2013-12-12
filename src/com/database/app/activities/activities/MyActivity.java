package com.database.app.activities.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.database.app.R;
import com.database.app.activities.adapters.ProductsListAdapter;
import com.database.app.activities.database.DatabaseHandler;
import com.database.app.activities.entities.Firma;
import com.database.app.activities.entities.Product;

public class MyActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

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
        registerForContextMenu(mList);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        mList.setOnItemClickListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Посмотреть фирмы");
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.products) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(mAdapter.getItem(info.position).getName());
            menu.add("Edit");
            menu.add("Delete");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int menuItemIndex = item.getItemId();
        Product product = mAdapter.getItem(info.position);

        if (item.getTitle().equals("Edit")) {
            showEditDialog(product);
        } else if (item.getTitle().equals("Delete")) {
            mDatabaseHandler.deleteProduct(product.getName());
            mAdapter.setProducts(mDatabaseHandler.getProducts());
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MyActivity.this, FirmsActivity.class));
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product product = mAdapter.getItem(position);
        Firma firma = mDatabaseHandler.getFirmForId(product.getFirmaId());
        if (firma != null) {
            showFirmDialog(product, firma);
        } else {
            Toast.makeText(MyActivity.this, "Фирмы у этого продукта не существует!", Toast.LENGTH_LONG).show();
        }
    }

    private void showFirmDialog(Product product, Firma firma) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(product.getName() + ", Фирма id: " + product.getFirmaId());
        builder.setMessage("Фирма: " + firma.getName() + "\n Город: " + firma.getSity());
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.create().show();
    }

    private void showEditDialog(final Product product) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(product.getName());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        name.setVisibility(View.GONE);
        final EditText price = (EditText) view.findViewById(R.id.price);
        price.setText(product.getPrice());
        final EditText amount = (EditText) view.findViewById(R.id.amount);
        amount.setText(product.getAmount());
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        fId.setText(product.getFirmaId());
        builder.setView(view);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                mDatabaseHandler.editProduct(product.getName(), price.getText().toString(), amount.getText().toString(), fId.getText().toString());
                mAdapter.setProducts(mDatabaseHandler.getProducts());
                mAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

        builder.create().show();
    }
}
