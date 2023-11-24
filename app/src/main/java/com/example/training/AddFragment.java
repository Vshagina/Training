package com.example.training;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddFragment extends Fragment {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Button addButton;
    private Button viewListButton;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        // Создание нового экземпляра фрагмента
        return new AddFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        nameEditText = view.findViewById(R.id.name);
        descriptionEditText = view.findViewById(R.id.description);
        addButton = view.findViewById(R.id.add_button);
        viewListButton = view.findViewById(R.id.view_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                // создание Bundle для хранения данных
                Bundle resultBundle = new Bundle();
                resultBundle.putString("name", name);
                resultBundle.putString("description", description);

                // передача данных обратно в AddActivity
                if (getActivity() instanceof AddActivity) {
                    ((AddActivity) getActivity()).onAddButtonClick(resultBundle);
                }
            }
        });

        viewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                // создание Bundle для хранения данных
                Bundle resultBundle = new Bundle();
                resultBundle.putString("name", name);
                resultBundle.putString("description", description);

                // передача Bundle обратно в AddActivity для открытия ListActivity
                if (getActivity() instanceof AddActivity) {
                    ((AddActivity) getActivity()).onViewListButtonClick(resultBundle);
                }
            }
        });

        return view;
    }
}




