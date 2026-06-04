package com.example.employeeapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView txtName, txtAge, txtSalary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtName = findViewById(R.id.txtDetailName);
        txtAge = findViewById(R.id.txtDetailAge);
        txtSalary = findViewById(R.id.txtDetailSalary);

        //detailImage = findViewById(R.id.detailImage);

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        String salary = getIntent().getStringExtra("salary");


        txtName.setText(name);
        txtAge.setText(age + " Years");
        txtSalary.setText("₹ " + salary);


        Toast.makeText(this, salary, Toast.LENGTH_LONG).show();

    }
}