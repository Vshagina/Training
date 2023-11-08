package com.example.training;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBaseAccessor db;
    EditText id,name,description;
    Button add,viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        add = findViewById(R.id.add_button);
        viewList = findViewById(R.id.view_button);

        db = new DataBaseAccessor(this);
        ADD();
        VIEW();
    }
    private void ADD() {
        description = findViewById(R.id.description);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Точно добавляем?Может потренируемся ещё?)").setCancelable(false)
                        .setPositiveButton("Хватит,добавляй!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean isInserted = db.insertData(
                                        name.getText().toString(),
                                        description.getText().toString()
                                );

                                if (isInserted) {
                                    Toast.makeText(MainActivity.this, "Добавление данных", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Ошибка в добавление данных", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("Хорошо,уговорил,потренируюсь ещё", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Добавление");
                alertDialog.show();
            }
        });
    }
    private void VIEW() {
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.viewData();
                if (res.getCount() == 0){
                    ShowMessage("Ошибка", "Данные не найдены");
                    return;
                }

                ArrayList<String> dataList = new ArrayList<>();
                while (res.moveToNext()){
                    dataList.add( res.getString(0) + "\n" + res.getString(1) + "\n" + res.getString(2));
                }
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putStringArrayListExtra("dataList", dataList);
                startActivity(intent);
            }
        });
    }
    public void ShowMessage (String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}