package com.example.traintable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        buttonStation = (Button) view;
        buttonStation.setOnClickListener(this);

        Intent intent;
        intent = new Intent(ChooseActivity.this, MainActivity.class);
        intent.putExtra("station", buttonStation.getText().toString());
        setResult(RESULT_OK, intent);

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}