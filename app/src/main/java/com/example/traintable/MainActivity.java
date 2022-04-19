package com.example.traintable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonFrom;
    private Button buttonTo;
    private String buttonFromText;
    private String buttonToText;
    private ConstraintLayout titlePictureStart;

    private final List<Object> viewItems = new ArrayList<>();

    private static final String TAG = "MainActivity";
    private static String STATIONS_ID = "no_way";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFrom = (Button) findViewById(R.id.buttonFrom);
        buttonFrom.setOnClickListener(this);

        buttonTo = (Button) findViewById(R.id.buttonTo);
        buttonTo.setOnClickListener(this);

        titlePictureStart = (ConstraintLayout) findViewById(R.id.titlePictureStart);
        titlePictureStart.setVisibility(View.VISIBLE);
    }

    private void recyclerUpdate() {
        getStationsId();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter = new RecyclerAdapter(this, viewItems);
        mRecyclerView.setAdapter(mAdapter);

        addItemsFromJSON();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ChooseActivity.class);

        switch (view.getId()) {
            case R.id.buttonFrom:
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.buttonTo:
                startActivityForResult(intent, 2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (requestCode == 1) {
            buttonFromText = data.getStringExtra("station");
            buttonFrom.setText(buttonFromText);
        }

        if (requestCode == 2) {
            buttonToText = data.getStringExtra("station");
            buttonTo.setText(buttonToText);
        }

        recyclerUpdate();
    }

    private void getStationsId() {
        if (buttonFromText != null && buttonToText != null) {
            titlePictureStart.setVisibility(View.GONE);

            ConstraintLayout titlePictureNoWays = (ConstraintLayout) findViewById(R.id.titlePictureNoWays);

            LinearLayout timetableLayout = (LinearLayout) findViewById(R.id.timetableLayout);

            if (buttonFromText.equals("Казанский вокзал") && buttonToText.equals("Раменское")) STATIONS_ID = "kaz_ram";
            if (buttonFromText.equals("Раменское") && buttonToText.equals("Казанский вокзал")) STATIONS_ID = "ram_kaz";

            if (buttonFromText.equals("Казанский вокзал") && buttonToText.equals("Рязань 1")) STATIONS_ID = "kaz_rzn";
            if (buttonFromText.equals("Рязань 1") && buttonToText.equals("Казанский вокзал")) STATIONS_ID = "rzn_kaz";

            if (buttonFromText.equals("Раменское") && buttonToText.equals("Рязань 1")) STATIONS_ID = "ram_rzn";
            if (buttonFromText.equals("Рязань 1") && buttonToText.equals("Раменское")) STATIONS_ID = "rzn_ram";

            if (buttonFromText.equals("Ярославский вокзал") && buttonToText.equals("Щелково")) STATIONS_ID = "yar_sch";
            if (buttonFromText.equals("Щелково") && buttonToText.equals("Ярославский вокзал")) STATIONS_ID = "sch_yar";

            if (buttonFromText.equals("Ярославский вокзал") && buttonToText.equals("Пушкино")) STATIONS_ID = "yar_pus";
            if (buttonFromText.equals("Пушкино") && buttonToText.equals("Ярославский вокзал")) STATIONS_ID = "pus_yar";

            if (buttonFromText.equals(buttonToText)) STATIONS_ID = "no_way";

            if (STATIONS_ID.equals("no_way")) {
                timetableLayout.setVisibility(View.GONE);
                titlePictureNoWays.setVisibility(View.VISIBLE);
            }
            else {
                timetableLayout.setVisibility(View.VISIBLE);
                titlePictureNoWays.setVisibility(View.GONE);
            }
            viewItems.clear();
        }
    }

    private void addItemsFromJSON() {
        try {
            String jsonDataString = readJSONDataFromFile();

            JSONObject jsonObject = new JSONObject(jsonDataString);

            JSONArray jsonArray = jsonObject.getJSONArray(STATIONS_ID);

            for (int i=0; i<jsonArray.length(); ++i) {

                JSONObject itemObj = jsonArray.getJSONObject(i);

                String timeStart = itemObj.getString("time_from");
                String timeEnd = itemObj.getString("time_to");
                String timeDuration = itemObj.getString("time_all");
                String stationStart = itemObj.getString("station_from");
                String stationEnd = itemObj.getString("station_to");
                String price = itemObj.getString("total_price");

                Timetable timetable = new Timetable(timeStart, timeEnd, timeDuration, stationStart, stationEnd, price);
                viewItems.add(timetable);

                STATIONS_ID = "no_way";
            }

        } catch (JSONException | IOException e) {
            Log.d(TAG, "addItemsFromJSON: ", e);
        }
    }

    private String readJSONDataFromFile() throws IOException{
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {

            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.timetable);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
}