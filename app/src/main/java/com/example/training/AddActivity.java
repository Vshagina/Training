package com.example.training;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    DataBaseAccessor db;
    EditText name;
    Button viewList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.name);
        viewList = findViewById(R.id.view_button);
        db = new DataBaseAccessor(this);

        if (savedInstanceState == null) {
            // настройка фрагментов
            setupAddFragment();
        }
    }

    private void setupAddFragment() {
        AddFragment addFragment = AddFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container2, addFragment)
                .commit();
    }

    public void onViewListButtonClick(Bundle resultBundle) {
        // запрос к базе данных
        Cursor res = db.viewData();
        if (res.getCount() == 0) {
            ShowMessage("Ошибка", "Данные не найдены");
            return;
        }

        // обработка данных
        ArrayList<String> dataList = new ArrayList<>();
        while (res.moveToNext()) {
            dataList.add(res.getString(0) + "\n" + res.getString(1) + "\n" + res.getString(2));
        }

        /**
         * создание Bundle для хранения данных
         */
        resultBundle.putStringArrayList("dataList", dataList);
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtras(resultBundle);
        startActivity(intent);
    }

    /**
     * обработка данных из фрагмента
     */
    public void onAddButtonClick(Bundle resultBundle) {
        String name = resultBundle.getString("name");
        String description = resultBundle.getString("description");
        //диалоговое окно
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setMessage("Точно добавляем? Может потренируемся ещё?)").setCancelable(false)
                .setPositiveButton("Хватит, добавляй!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Вставка данных в базу данных
                        boolean isInserted = db.insertData(name, description);
                        if (isInserted) {
                            Toast.makeText(AddActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddActivity.this, "Проверьте введённые данные", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Хорошо, уговорил, потренируюсь ещё", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Ура! Добавилось!");
        alertDialog.show();
    }

    public void ShowMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}