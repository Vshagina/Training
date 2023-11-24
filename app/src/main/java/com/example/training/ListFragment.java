package com.example.training;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    private OnListItemClickListener itemClickListener;

    public interface OnListItemClickListener {
        void onItemClicked(int position);

        void onItemLongClicked(int position);
    }

    // метод для установки слушателя событий нажатия на элемент списка
    public void setOnListItemClickListener(OnListItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public ListFragment() {
    }

    // создание нового экземпляра фрагмента с передачей данных
    public static ListFragment newInstance(ArrayList<String> dataList) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("dataList", dataList);
        fragment.setArguments(args);
        return fragment;
    }

    // создание и настройка представления фрагмента
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // заполнение макета фрагмента
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // получение данных из аргументов
        dataList = getArguments().getStringArrayList("dataList");

        // получение ссылки на ListView из макета
        ListView listView = view.findViewById(R.id.listView);

        // создание списка для отображения в ListView
        ArrayList<String> displayList = new ArrayList<>();
        for (String data : dataList) {
            String[] itemParts = data.split("\n");
            if (itemParts.length >= 2) {
                displayList.add(itemParts[1] + "\n" + itemParts[2]);
            }
        }

        // создание адаптера для связывания данных с ListView
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        // установка слушателя для обработки кликов на элементах списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(position);
                }
            }
        });

        // установка слушателя для обработки долгих кликов на элементах списка
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (itemClickListener != null) {
                    itemClickListener.onItemLongClicked(position);
                }
                // возвращаем true, чтобы предотвратить дальнейшую обработку короткого клика
                return true;
            }
        });
        return view;
    }
}