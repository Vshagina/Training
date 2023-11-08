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

        String itemName = getIntent().getStringExtra("itemName");
        String itemDescription = getIntent().getStringExtra("itemDescription");
        String id = getIntent().getStringExtra("itemid");
        nameEditText.setText(itemName);
        descriptionEditText.setText(itemDescription);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editedName = nameEditText.getText().toString();
                String editedDescription = descriptionEditText.getText().toString();

                db.updateData(Integer.parseInt(id), editedName, editedDescription);

                finish();
            }
        });
    }


    private int getItemIdFromDatabase(String itemName) {
        return db.getItemIdFromDatabase(itemName);
    }
}

