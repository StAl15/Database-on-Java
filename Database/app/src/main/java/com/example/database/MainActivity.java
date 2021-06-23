package com.example.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//главная активность
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageView;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> moneta_id, nominal_coin, year_coin, price_coin ;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView); //список отображаемого
        add_button = findViewById(R.id.add_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.noData);

        //переход на активность "добавление в бд"
        add_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        moneta_id = new ArrayList<>();
        nominal_coin = new ArrayList<>();
        year_coin = new ArrayList<>();
        price_coin = new ArrayList<>();

        viewDataInArrays();

        //то что корректно выводит данные в recyclerView
        customAdapter = new CustomAdapter(MainActivity.this,this, moneta_id, nominal_coin, year_coin, price_coin);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    //если обновлены данные, то и главная активность тоже обновляется
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    //вывод данных в массив
    void viewDataInArrays(){
       Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                moneta_id.add(cursor.getString(0));
                nominal_coin.add(cursor.getString(1));
                year_coin.add(cursor.getString(2));
                price_coin.add(cursor.getString(3));
            }
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);

        }
    }

    //показать кнопку deleteAll
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //что будет при нажатии на deleteAll
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            confirmDialog();
        }

        return super.onOptionsItemSelected(item);

    }
    //диалоговое окно подтверждения удаления
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить всё ?");
        builder.setMessage("Вы уверены, что хотите удалить все данные ?");
        builder.setPositiveButton("Да", (dialog, which) -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(this);
            myDB.deleteAllData();
            //чтобы выводилось плавно без чёрных экранов, пересоздаём активность
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Нет", (dialog, which) -> {

        });
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