package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText resultEditText;
    private double value1 = Double.NaN;
    private double value2;
    private String action;

    private static final String ADD = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";
    private static final String EQUAL = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEditText = findViewById(R.id.result);

        // Set button click listeners
        setNumericButtonListeners();
        setOperatorButtonListeners();
    }

    private void setNumericButtonListeners() {
        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            resultEditText.append(button.getText().toString());
        };

        int[] numericButtons = {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> performOperation(ADD));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> performOperation(SUBTRACT));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> performOperation(MULTIPLY));
        findViewById(R.id.btnDivide).setOnClickListener(v -> performOperation(DIVIDE));
        findViewById(R.id.btnEqual).setOnClickListener(v -> performOperation(EQUAL));
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
    }

    private void performOperation(String operator) {
        if (!Double.isNaN(value1)) {
            value2 = Double.parseDouble(resultEditText.getText().toString());
            resultEditText.setText(null);

            switch (action) {
                case ADD:
                    value1 += value2;
                    break;
                case SUBTRACT:
                    value1 -= value2;
                    break;
                case MULTIPLY:
                    value1 *= value2;
                    break;
                case DIVIDE:
                    value1 /= value2;
                    break;
            }

            if (operator.equals(EQUAL)) {
                resultEditText.setText(String.valueOf(value1));
                value1 = Double.NaN;
                action = "";
            } else {
                action = operator;
            }
        } else {
            value1 = Double.parseDouble(resultEditText.getText().toString());
            action = operator;
            resultEditText.setText(null);
        }
    }

    private void clear() {
        value1 = Double.NaN;
        value2 = Double.NaN;
        resultEditText.setText(null);
    }
}
