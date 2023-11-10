package com.example.training;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    //используем адаптер для отображения данных в списке
    ArrayAdapter<String> adapter;
    //список данных,которые передавали из предыдущей активности
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        dataList = getIntent().getStringArrayListExtra("dataList"); //получение этих данных
        ListView listView = findViewById(R.id.listView);

        //создаётся список с именем и описанием
        ArrayList<String> displayList = new ArrayList<>();
        for (String data : dataList) {
            String[] itemParts = data.split("\n");
            if (itemParts.length >= 2) {
                displayList.add(itemParts[1] + "\n" + itemParts[2]);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        //обрабатывает одно нажатие на элемент списка
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

        //обрабатывет долгое нажатие на элемент
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteWindow(position);
                return true;
            }
        });
    }
    //метод который выводит дополнительное окно,чтобы удостовериться в долгом нажатии
    private void deleteWindow(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Удалить запись?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    //метод который удаляет элемент и из бд из списка
    private void deleteItem(int position) {
        String selectedItem = dataList.get(position);
        String[] itemParts = selectedItem.split("\n");
        String id = itemParts[0];

        DataBaseAccessor db = new DataBaseAccessor(ListActivity.this);
        db.deleteData(Integer.parseInt(id));

        dataList.remove(position);
        adapter.remove(selectedItem);
        adapter.notifyDataSetChanged();
    }
    //метод для перехода в окно для редактирования
    private void openEditActivity(String id, String itemName, String itemDescription) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemDescription", itemDescription);
        intent.putExtra("itemid", id);
        NotesLauncher.launch(intent);
    }
}

