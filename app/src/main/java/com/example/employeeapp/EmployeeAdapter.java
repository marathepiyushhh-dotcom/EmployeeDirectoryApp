package com.example.employeeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>
        implements Filterable {

    Context context;

    ArrayList<Employee> employeeList;
    ArrayList<Employee> employeeListFull;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList) {

        this.context = context;
        this.employeeList = employeeList;


    }

    public void setFullList(ArrayList<Employee> list) {

        employeeListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_employee, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        Employee employee = employeeList.get(position);

        holder.txtName.setText(employee.getName());
        holder.txtEmail.setText("Age: " + employee.getAge());



        // Item Click
        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(context, DetailActivity.class);

            intent.putExtra("name",
                    employee.getName());

            intent.putExtra("age",
                    employee.getAge());

            intent.putExtra("salary",
                    employee.getSalary());

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    // SEARCH FILTER
    @Override
    public Filter getFilter() {
        return employeeFilter;
    }

    private final Filter employeeFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Employee> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(employeeListFull);

            } else {

                String filterPattern =
                        constraint.toString()
                                .toLowerCase()
                                .trim();

                for (Employee item : employeeListFull) {

                    if (item.getName()
                            .toLowerCase()
                            .contains(filterPattern)) {

                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            employeeList.clear();

            employeeList.addAll((ArrayList<Employee>) results.values);

            notifyDataSetChanged();
        }
    };

    // VIEW HOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }
}