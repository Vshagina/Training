package com.example.training;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class EditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if (savedInstanceState == null) {
            // получение данных из Intent
            Bundle dataBundle = getIntent().getExtras();

            // создание и передача данных в EditFragment
            EditFragment editFragment = EditFragment.newInstance(dataBundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, editFragment)
                    .commit();
        }
    }

    // метод вызывается фрагментом при завершении редактирования
    public void onBackData(Bundle resultBundle) {
        // получение отредактированных данных из Bundle
        String editedName = resultBundle.getString("editedName");
        String editedDescription = resultBundle.getString("editedDescription");
        finish();
    }

    // метод для обновления данных в базе данных
    public void updateDatabase(int id, String editedName, String editedDescription) {
        DataBaseAccessor db = new DataBaseAccessor(this);
        db.updateData(id, editedName, editedDescription);
    }
}




