package com.example.training;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditFragment extends Fragment {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Button saveAndExitButton;


    public EditFragment() {
    }
    // метод для создания нового экземпляра EditFragment с переданными данными
    public static EditFragment newInstance(Bundle dataBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(dataBundle);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        nameEditText = view.findViewById(R.id.nameEdit);
        descriptionEditText = view.findViewById(R.id.descriptionEdit);
        saveAndExitButton = view.findViewById(R.id.SaveAndExit);

        // получение данных из переданного Bundle
        Bundle args = getArguments();
        if (args != null) {
            // получение имени и описания из Bundle
            String itemName = args.getString("itemName");
            String itemDescription = args.getString("itemDescription");
            nameEditText.setText(itemName);
            descriptionEditText.setText(itemDescription);
        }
        saveAndExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveAndExitClicked();
            }
        });

        return view;
    }

    private void onSaveAndExitClicked() {
        String editedName = nameEditText.getText().toString();
        String editedDescription = descriptionEditText.getText().toString();
        // получение данных из переданного Bundle
        Bundle args = getArguments();
        if (args != null) {
            // получение идентификатора элемента из Bundle
            int id = Integer.parseInt(args.getString("itemid"));

            // вызов метода обновления базы данных в EditActivity
            ((EditActivity) requireActivity()).updateDatabase(id, editedName, editedDescription);

            // передача отредактированных данных в EditActivity
            if (getActivity() instanceof EditActivity) {
                Bundle resultBundle = new Bundle();
                resultBundle.putString("editedName", editedName);
                resultBundle.putString("editedDescription", editedDescription);
                ((EditActivity) getActivity()).onBackData(resultBundle);
            }
        }
    }
}




