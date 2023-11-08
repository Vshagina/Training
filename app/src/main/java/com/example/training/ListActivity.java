package com.example.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> NotesLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    finish();
                }
            });
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        ArrayList<String> dataList = getIntent().getStringArrayListExtra("dataList");
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = dataList.get(position);
                String[] itemParts = selectedItem.split("\n");
                Toast.makeText(ListActivity.this, itemParts[1], Toast.LENGTH_LONG).show();
                if (itemParts.length >= 2) {
                    String itemName = itemParts[1];
                    String itemDescription = itemParts[2];
                    openEditActivity(itemParts[0], itemName, itemDescription);
                }
            }
        });
    }

    private void openEditActivity(String id, String itemName, String itemDescription) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemDescription", itemDescription);
        intent.putExtra("itemid", id);
        NotesLauncher.launch(intent);
    }
}

