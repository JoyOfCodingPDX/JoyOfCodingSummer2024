package edu.pdx.cs.joy.whitlock;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

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
        finish();
    }

    public void computeSum(View view) {
        EditText left = findViewById(R.id.left);
        EditText right = findViewById(R.id.right);
        TextView sum = findViewById(R.id.sum);

        String leftString = left.getText().toString();
        String rightString = right.getText().toString();

        int leftValue = Integer.parseInt(leftString);
        int rightValue = Integer.parseInt(rightString);
        int sumValue = leftValue + rightValue;

        sum.setText(String.valueOf(sumValue));
    }
}