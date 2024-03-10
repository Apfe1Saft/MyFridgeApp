package com.mobilestudy.myfridge;


import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button removeButton;
    private ListView listView;
    private TextView textView;
    private ArrayList<Product> mainList;
    private ArrayAdapter<Product> mainListAdapter;
    private TextView  currentDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);
        currentDateView = findViewById(R.id.currentDateView);


        mainList = new ArrayList<>();
        mainListAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, mainList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Product product = getItem(position);


                textView.setTextColor(Color.WHITE);
                if (product != null && isCurrentDateGreaterThanEndDate(product.getDate().getEndExpirationDate())) {
                    view.setBackgroundColor(Color.RED);
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                return view;
            }
        };
        listView.setAdapter(mainListAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected product from the mainList
                Product selectedProduct = mainList.get(position);

                // Create and show a dialog with information about the selected product
                showProductDetailsDialog(selectedProduct);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        currentDateView.setText("Current Date: " + formattedDate);
    }

    private void showProductDetailsDialog(Product product) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText("Details: \n" + product.toStringAll());

        Button closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainList.remove(product);
                mainListAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showAddDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add_item);
        ListView dialogListView = dialog.findViewById(R.id.dialogListView);
        Button closeButtonDialog = dialog.findViewById(R.id.closeButtonDialog);

        ArrayList<String> dialogList = new ArrayList<>();
        dialogList.add("Milk");
        dialogList.add("Eggs");
        dialogList.add("Butter");
        dialogList.add("Cheese");
        dialogList.add("Yogurt");
        dialogList.add("Salad");
        dialogList.add("Tofu");
        dialogList.add("Fish");
        dialogList.add("Chicken");
        dialogList.add("Beaf");

        ArrayAdapter<String> dialogListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dialogList);
        dialogListView.setAdapter(dialogListAdapter);

        dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();

                String selectedItem = dialogList.get(position);
                showDateInputDialog(selectedItem);
            }
        });
        closeButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void showDateInputDialog(String selectedItem) {
        final Dialog dateInputDialog = new Dialog(MainActivity.this);
        dateInputDialog.setContentView(R.layout.dialog_date_input);

        EditText startDateInput = dateInputDialog.findViewById(R.id.startDateInput);
        EditText endDateInput = dateInputDialog.findViewById(R.id.endDateInput);
        Button submitButton = dateInputDialog.findViewById(R.id.submitButton);

        TextView selectedItemTextView = dateInputDialog.findViewById(R.id.selectedItemTextView);
        selectedItemTextView.setText(selectedItem);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDateString = startDateInput.getText().toString();
                String endDateString = endDateInput.getText().toString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    Date startDate = dateFormat.parse(startDateString);
                    Date endDate = dateFormat.parse(endDateString);

                    ExpirationDate expirationDate = new ExpirationDate(startDate, endDate);
                    Product newProduct = new Product(selectedItem, expirationDate);
                    mainList.add(newProduct);
                    mainListAdapter.notifyDataSetChanged();
                    dateInputDialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        dateInputDialog.show();
    }

    private boolean isCurrentDateGreaterThanEndDate(Date endDate) {
        Date currentDate = new Date();
        return currentDate.after(endDate);
    }
}