package com.database.app.activities.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.database.app.R;
import com.database.app.activities.database.DatabaseHandler;
import com.database.app.activities.entities.Firma;
import com.database.app.activities.entities.Product;

import java.util.ArrayList;

/**
 * Created by y.klimko on 13.12.13.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                showSearchDialog("Поиск фирмы с минимальной ценой продукта");
                break;
            case R.id.button1:
                showSearchDialog1("Выбор продукта по минимальной цене");
                break;
            case R.id.button2:
                showSearchDialog2("Сортировка по алфавиту");
                break;
        }

    }

    private void showSearchDialog2(String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        name.setVisibility(View.GONE);
        final EditText price = (EditText) view.findViewById(R.id.price);
        price.setVisibility(View.GONE);
        final EditText amount = (EditText) view.findViewById(R.id.amount);
        amount.setVisibility(View.GONE);
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        fId.setVisibility(View.GONE);
        fId.setHint("Введите колличество продукта");
        builder.setView(view);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                DatabaseHandler mDatabaseHandler = new DatabaseHandler(SearchActivity.this);
                ArrayList<String> result = mDatabaseHandler.searchType3();
                if(result != null) {
                    showResultDialog(result);
                } else {
                    Toast.makeText(SearchActivity.this, "Ничего не найдено!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.create().show();
    }


    private void showSearchDialog(String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        name.setVisibility(View.GONE);
        final EditText price = (EditText) view.findViewById(R.id.price);
        price.setVisibility(View.GONE);
        final EditText amount = (EditText) view.findViewById(R.id.amount);
        amount.setVisibility(View.GONE);
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        fId.setHint("Введите колличество продукта");
        builder.setView(view);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                DatabaseHandler mDatabaseHandler = new DatabaseHandler(SearchActivity.this);
                String result = mDatabaseHandler.searchType1(fId.getText().toString());
                if(result != null) {
                    ArrayList<String> r = new ArrayList<String>();
                    r.add(result);
                    showResultDialog(r);
                } else {
                    Toast.makeText(SearchActivity.this, "Ничего не найдено!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.create().show();
    }

    private void showSearchDialog1(String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.adding_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        name.setVisibility(View.GONE);
        final EditText price = (EditText) view.findViewById(R.id.price);
        price.setVisibility(View.GONE);
        final EditText amount = (EditText) view.findViewById(R.id.amount);
        amount.setVisibility(View.GONE);
        final EditText fId = (EditText) view.findViewById(R.id.firm_id);
        fId.setVisibility(View.GONE);
        fId.setHint("Введите колличество продукта");
        builder.setView(view);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                DatabaseHandler mDatabaseHandler = new DatabaseHandler(SearchActivity.this);
                ArrayList<String> result = mDatabaseHandler.searchType2();
                if(result != null) {
                    showResultDialog(result);
                } else {
                    Toast.makeText(SearchActivity.this, "Ничего не найдено!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.create().show();
    }
    private void showResultDialog(ArrayList<String> result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Результат");
        StringBuilder builder1 = new StringBuilder();
        for (String s :result){
            builder1.append(s+"\n");
        }
        builder.setMessage(builder1.toString());
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.create().show();
    }
}
