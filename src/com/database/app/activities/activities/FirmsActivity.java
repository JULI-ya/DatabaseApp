package com.database.app.activities.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.database.app.R;
import com.database.app.activities.adapters.FirmsAdapter;
import com.database.app.activities.adapters.ProductsListAdapter;
import com.database.app.activities.database.DatabaseHandler;
import com.database.app.activities.entities.Firma;
import com.database.app.activities.entities.Product;

import java.util.ArrayList;

/**
 * Created by y.klimko on 12.12.13.
 */
public class FirmsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mList;
    private DatabaseHandler mDatabaseHandler;
    private FirmsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mList = (ListView) findViewById(R.id.products);
        TextView textView = (TextView) findViewById(R.id.empty);
        mList.setEmptyView(textView);
        mDatabaseHandler = new DatabaseHandler(this);
        mAdapter = new FirmsAdapter(this, R.id.name, mDatabaseHandler.getFirms());
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        registerForContextMenu(mList);

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
        final EditText fid = (EditText) view.findViewById(R.id.price);
        fid.setHint("Введите id");
        final EditText sity = (EditText) view.findViewById(R.id.amount);
        sity.setHint("Введите город");
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        fId.setVisibility(View.GONE);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (name.getText().toString().trim().equals(""))
                    name.setError("Заполните!");
                else {
                    mDatabaseHandler.addFirm(name.getText().toString(), fid.getText().toString(), sity.getText().toString());
                    mAdapter.setFirms(mDatabaseHandler.getFirms());
                    mAdapter.notifyDataSetChanged();
                    dialog.cancel();
                }
            }
        })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Firma firma = mAdapter.getItem(position);
        ArrayList<Product> products = mDatabaseHandler.getProductsForFirmId(firma.getId());
        if (products.size() != 0) {
            showProductsDialog(products, firma);
        } else {
            Toast.makeText(FirmsActivity.this, "У этой фирмы нет продуктов!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.products) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Id: " + mAdapter.getItem(info.position).getId());
            menu.add("Edit");
            menu.add("Delete");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int menuItemIndex = item.getItemId();
        Firma firma = mAdapter.getItem(info.position);

        if (item.getTitle().equals("Edit")) {
            showEditDialog(firma);
        } else if (item.getTitle().equals("Delete")) {
            mDatabaseHandler.deleteFirm(firma.getId());
            mAdapter.setFirms(mDatabaseHandler.getFirms());
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    private void showEditDialog(final Firma firma) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ID фирмы: " + firma.getId());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        name.setHint("Введите имя");
        name.setText(firma.getName());
        final EditText fid = (EditText) view.findViewById(R.id.price);
        fid.setVisibility(View.GONE);
        final EditText sity = (EditText) view.findViewById(R.id.amount);
        sity.setHint("Введите город");
        sity.setText(firma.getSity());
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        fId.setVisibility(View.GONE);
        builder.setView(view);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                mDatabaseHandler.editFirma(name.getText().toString(), firma.getId(), sity.getText().toString());
                mAdapter.setFirms(mDatabaseHandler.getFirms());
                mAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    private void showProductsDialog(ArrayList<Product> products, Firma firma) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(firma.getName() + ", Фирма id: " + firma.getId());
        StringBuilder builder1 = new StringBuilder();
        for(Product product:products){
            builder1.append(product.getName() + "\n");
        }
        builder1.append("Всего: " + products.size() + " пр.");
        builder.setMessage(builder1.toString());
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.create().show();
    }
}
