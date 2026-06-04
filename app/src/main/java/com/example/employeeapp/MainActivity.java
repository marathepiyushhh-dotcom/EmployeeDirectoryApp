package com.example.employeeapp;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonObjectRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;

    ArrayList<Employee> employeeList;

    EmployeeAdapter adapter;

    String url = "https://aamras.com/dummy/EmployeeDetails.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeList = new ArrayList<>();

        adapter = new EmployeeAdapter(this, employeeList);

        recyclerView.setAdapter(adapter);

        // Search Employee
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });

        loadData();
    }

    private void loadData() {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    try {

                        JSONArray employeeArray =
                                response.getJSONArray("employees");

                        for (int i = 0; i < employeeArray.length(); i++) {

                            JSONObject object =
                                    employeeArray.getJSONObject(i);

                            String name =
                                    object.getString("name");

                            String age =
                                    object.getString("age");

                            String salary =
                                    object.getString("salary");

                            employeeList.add(
                                    new Employee(
                                            name,
                                            age,
                                            salary
                                    )
                            );
                        }

                        adapter.setFullList(employeeList);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {

                        Toast.makeText(
                                MainActivity.this,
                                e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }

                },

                error -> Toast.makeText(
                        MainActivity.this,
                        error.toString(),
                        Toast.LENGTH_LONG
                ).show()

        );

        RequestQueue queue =
                Volley.newRequestQueue(this);

        queue.add(request);
    }
}