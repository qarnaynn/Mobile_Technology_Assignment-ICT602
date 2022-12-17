package com.example.zakatpayment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edWeight, edPrice;
    private Spinner spinner;
    private Button btnCalculate;
    private TextView tvAboutUs;

    private final int X_WEAR = 200;
    private final int X_KEEP = 85;

    private final String KEEP = "Keep";
    private final String WEAR = "Wear";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String getWeight = sharedPref.getString("weight", "");
        String getPrice = sharedPref.getString("price", "");

        edWeight = findViewById(R.id.edWeight);
        edPrice = findViewById(R.id.edPrice);

        edWeight.setText(getWeight);
        edPrice.setText(getPrice);

        spinner = findViewById(R.id.spinner);

        btnCalculate = findViewById(R.id.btnCalculate);

        tvAboutUs = findViewById(R.id.tvAboutUs);
        tvAboutUs.setOnClickListener(view -> {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        });

        String[] items = new String[]{KEEP, WEAR};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        btnCalculate.setOnClickListener(view -> {

            if (edWeight.getText().toString().length() == 0) {
                edWeight.setError("Please input weight in number.");
                Toast.makeText(this, "Missing input", Toast.LENGTH_SHORT).show();
                return;
            }

            if (edPrice.getText().toString().length() == 0) {
                edPrice.setError("Please input price in number");
                Toast.makeText(this, "Missing input", Toast.LENGTH_SHORT).show();
                return;
            }


            double weight = Double.parseDouble(edWeight.getText().toString());
            double price = Double.parseDouble(edPrice.getText().toString());

            int xValue;
            String spinValue = spinner.getSelectedItem().toString();
            if (spinValue.equals(KEEP)) {
                xValue = X_KEEP;
            } else if (spinValue.equals(WEAR)) {
                xValue = X_WEAR;
            } else {
                xValue = 0;
            }

            double totalValueOfGold = weight * price;
            double uruf = weight - xValue;
            double zakatPayable = uruf <= 0 ? 0 : price * uruf;
            double totalZakat = zakatPayable * 0.025;


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Result");
            builder.setMessage("" +
                    "Total Value of Gold :RM " + totalValueOfGold +
                    "\nZakat Payable :RM " + zakatPayable +
                    "\nUruf :RM " + uruf +
                    "\nTotal Zakat :RM " + totalZakat);

            builder.setPositiveButton("Continue", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("weight", String.valueOf(weight));
            editor.putString("price", String.valueOf(price));
            editor.apply();

        });


    }

    public void btnOnclick2(View view) {
        EditText edit1 = findViewById(R.id.edWeight);
        EditText edit2 = findViewById(R.id.edPrice);
                 edit1.getText().clear();
                 edit2.getText().clear();
    }

}