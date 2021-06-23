package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//добавить данные
public class AddActivity extends AppCompatActivity {

    EditText nominal_input, year_input, price_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        nominal_input = findViewById(R.id.nominal_input);
        year_input = findViewById(R.id.year_input);
        price_input = findViewById(R.id.price_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);

            myDB.add_data(nominal_input.getText().toString().trim(),
                    year_input.getText().toString().trim(),
                    price_input.getText().toString().trim());
        });
    }
}