package com.example.training;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText descriptionEditText;
    Button saveButton;
    DataBaseAccessor db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        db = new DataBaseAccessor(this);

        nameEditText = findViewById(R.id.nameEdit);
        descriptionEditText = findViewById(R.id.descriptionEdit);
        saveButton = findViewById(R.id.saveButton);

        //получение данных из прошлой активити
        String itemName = getIntent().getStringExtra("itemName");
        String itemDescription = getIntent().getStringExtra("itemDescription");
        String id = getIntent().getStringExtra("itemid");
        //установка новых данных в нужные поля
        nameEditText.setText(itemName);
        descriptionEditText.setText(itemDescription);

        //нажатии на кнопку для сохранения
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //получаемя отредактированные данные
                String editedName = nameEditText.getText().toString();
                String editedDescription = descriptionEditText.getText().toString();
                //обновляем их в базе данных
                db.updateData(Integer.parseInt(id), editedName, editedDescription);
                finish();
            }
        });
    }
}

