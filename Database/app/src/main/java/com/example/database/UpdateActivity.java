package com.example.database;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//обновить
public class UpdateActivity extends AppCompatActivity {

    EditText nominal_input, year_input, price_input;
    Button update_button, delete_button;

    String id, nominal, year, price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nominal_input = findViewById(R.id.nominal_input2);
        year_input = findViewById(R.id.year_input2);
        price_input = findViewById(R.id.price_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //1) Сначала собираем и устанавливаем данные
        getAndSetIntentData();


        //установить заголовок после getAndSetIntentData()
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(nominal);
        }
        update_button.setOnClickListener(v -> {

            //2) Потом их обновляем
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            nominal = nominal_input.getText().toString().trim();
            year = year_input.getText().toString().trim();
            price = price_input.getText().toString().trim();
            myDB.updateData(id, nominal, year, price);
        });

        delete_button.setOnClickListener(v -> {
            confirmDialog();
        });



    }

    //получение и установка данных из интента
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("nominal") && getIntent().hasExtra("year") && getIntent().hasExtra("price")){

            //взять данные из интента
            id = getIntent().getStringExtra("id");
            nominal = getIntent().getStringExtra("nominal");
            year = getIntent().getStringExtra("year");
            price = getIntent().getStringExtra("price");

            //установить данные из интента
            nominal_input.setText(nominal);
            year_input.setText(year);
            price_input.setText(price);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    //диалоговое окно подтверждения удаления
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить "+"\""+nominal+"\""+" ?");
        builder.setMessage("Вы уверены, что хотите удалить " + "\""+nominal +"\""+ " ?");
        builder.setPositiveButton("Да", (dialog, which) -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);
            //плавный переход без черных экранов
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        });
        builder.setNegativeButton("Нет", (dialog, which) -> { });
        builder.create().show();

        /*
        или так, но с лямбдой проще
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
         */
    }
}