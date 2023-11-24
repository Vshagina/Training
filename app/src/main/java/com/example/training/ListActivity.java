package com.example.training;

import android.annotation.SuppressLint;
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

    ActivityResultLauncher<Intent> NotesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    finish();
                }
            });

    ArrayAdapter<String> adapter;

    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // получение списка данных из предыдущей активности
        dataList = getIntent().getStringArrayListExtra("dataList");

        // создание фрагмента списка и передача данных
        ListFragment listFragment = ListFragment.newInstance(dataList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container1, listFragment)
                .commit();

        // установка обработчика кликов на элементах списка
        listFragment.setOnListItemClickListener(new ListFragment.OnListItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                handleItemClick(position);
            }

            @Override
            public void onItemLongClicked(int position) {
                deleteWindow(position);
            }
        });
    }

    // обработка клика на элементе списка
    private void handleItemClick(int position) {
        String selectedItem = dataList.get(position);
        String[] itemParts = selectedItem.split("\n");
        Toast.makeText(ListActivity.this, itemParts[1], Toast.LENGTH_LONG).show();
        if (itemParts.length >= 2) {
            String itemName = itemParts[1];
            String itemDescription = itemParts[2];
            openEditActivity(itemParts[0], itemName, itemDescription);
        }
    }

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

    // удаление элемента из списка и базы данных
    private void deleteItem(int position) {
        String selectedItem = dataList.get(position);
        String[] itemParts = selectedItem.split("\n");
        String id = itemParts[0];

        // создание экземпляра базы данных и удаление данных
        DataBaseAccessor db = new DataBaseAccessor(ListActivity.this);
        db.deleteData(Integer.parseInt(id));

        // удаление элемента из списка и обновление адаптера
        dataList.remove(position);
        adapter.remove(selectedItem);
        adapter.notifyDataSetChanged();
    }

    // открытие EditActivity для редактирования элемента
    private void openEditActivity(String id, String itemName, String itemDescription) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemDescription", itemDescription);
        intent.putExtra("itemid", id);
        NotesLauncher.launch(intent);
    }
}