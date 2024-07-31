package edu.pdx.cs.joy.whitlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    public static final String SUM_VALUE = "SUM";
    private int sumValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backToMain(View view) {
        Intent intent = new Intent();
        intent.putExtra(SUM_VALUE, this.sumValue);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void computeSum(View view) {
        EditText left = findViewById(R.id.left);
        EditText right = findViewById(R.id.right);
        TextView sum = findViewById(R.id.sum);

        String leftString = left.getText().toString();
        if (isEmpty(leftString)) {
            toast("Enter value for left operand");
            return;
        }

        String rightString = right.getText().toString();
        if (isEmpty(rightString)) {
            toast("Enter value for right operand");
            return;
        }

        int leftValue;
        try {
            leftValue = Integer.parseInt(leftString);
        } catch (NumberFormatException ex) {
            toast("Left value \"" + leftString + "\" is not an integer");
            return;
        }
        int rightValue;
        try {
            rightValue = Integer.parseInt(rightString);
        } catch (NumberFormatException ex) {
            toast("Right value \"" + rightString + "\" is not an integer");
            return;
        }

        this.sumValue = leftValue + rightValue;

        sum.setText(String.valueOf(sumValue));
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}