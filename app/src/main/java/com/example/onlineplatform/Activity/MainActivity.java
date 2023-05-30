package com.example.onlineplatform.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineplatform.Adapter.AdapterCleaner;
import com.example.onlineplatform.Entity.Cleaner;
import com.example.onlineplatform.Entity.Cleaners;
import com.example.onlineplatform.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private ArrayList<Cleaner> mainList = new ArrayList<>();
    private float minPrice;
    private float maxPrice;
    private String selectedDay;
    private boolean isActive = false;
    private RelativeLayout research_relativity_layout;
    private LinearLayout filtering_linear_layout;
    private EditText min_price_edit_text;
    private EditText max_price_edit_text;
    private TextView total_result_text_view;
    private RecyclerView recycler_view_cleaners;
    private ImageView background_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        research_relativity_layout = findViewById(R.id.research_relativity_layout);
        filtering_linear_layout = findViewById(R.id.filtering_linear_layout);
        min_price_edit_text = findViewById(R.id.min_price_edit_text);
        max_price_edit_text = findViewById(R.id.max_price_edit_text);
        Button select_location_button = findViewById(R.id.select_location_button);
        ImageButton refresh_button = findViewById(R.id.refresh_button);
        total_result_text_view = findViewById(R.id.total_result_text_view);
        RadioGroup radio_group_price = findViewById(R.id.radio_group_price);
        RadioGroup radio_group_day = findViewById(R.id.radio_group_day);
        recycler_view_cleaners = findViewById(R.id.recycler_view_cleaners);
        background_image_view = findViewById(R.id.background_image_view);

        recycler_view_cleaners.setVisibility(View.GONE);

        //radio grupta bulunan tüm seçimlerin ne yapacağı
        radio_group_price.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_price_1:
                        minPrice = 0;
                        maxPrice = 250;
                        break;
                    case R.id.radio_price_2:
                        minPrice = 250;
                        maxPrice = 500;
                        break;
                    case R.id.radio_price_3:
                        minPrice = 500;
                        maxPrice = 1000;
                        break;
                    case R.id.radio_price_4:
                        minPrice = 1000;
                        maxPrice = 2000;
                        break;
                    case R.id.radio_price_5:
                        minPrice = 2000;
                        maxPrice = 3000;
                        break;
                    case R.id.radio_price_6:
                        minPrice = 3000;
                        maxPrice = 10000;
                        break;
                    default:
                        minPrice = 0;
                        maxPrice = 100000;
                        break;
                }
            }
        });

        radio_group_day.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_day_1:
                        selectedDay = "Monday";
                        break;
                    case R.id.radio_day_2:
                        selectedDay = "Tuesday";
                        break;
                    case R.id.radio_day_3:
                        selectedDay = "Wednesday";
                        break;
                    case R.id.radio_day_4:
                        selectedDay = "Thursday";
                        break;
                    case R.id.radio_day_5:
                        selectedDay = "Friday";
                        break;
                    case R.id.radio_day_6:
                        selectedDay = "Saturday";
                        break;
                    case R.id.radio_day_7:
                        selectedDay = "Sunday";
                        break;
                    default:
                        selectedDay = null;
                        break;
                }
            }
        });

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isActive){
                    String strMin = String.valueOf(min_price_edit_text.getText());
                    if (!strMin.equals("")) {
                        minPrice = Float.parseFloat(strMin);

                        min_price_edit_text.getText().clear();
                        min_price_edit_text.clearFocus();
                    }

                    String strMax = String.valueOf(max_price_edit_text.getText());
                    if (!strMax.equals("")) {
                        maxPrice = Float.parseFloat(strMax);

                        max_price_edit_text.getText().clear();
                        max_price_edit_text.clearFocus();
                    }

                    mainList.clear();

                    filtering(readLocalData());

                    if (!mainList.isEmpty()) {
                        research_relativity_layout.setVisibility(View.GONE);
                        filtering_linear_layout.setVisibility(View.GONE);
                        background_image_view.setVisibility(View.GONE);
                        setAdapterForCleaners(mainList);

                        String str = "Total number of results: " + mainList.size();
                        total_result_text_view.setText(str);

                        refresh_button.setImageResource(R.drawable.baseline_keyboard_arrow_down);

                        isActive = true;
                    } else {
                        recycler_view_cleaners.setVisibility(View.GONE);
                        research_relativity_layout.setVisibility(View.VISIBLE);
                        filtering_linear_layout.setVisibility(View.VISIBLE);
                        background_image_view.setVisibility(View.VISIBLE);

                        String str = "No results found!";
                        total_result_text_view.setText(str);

                        isActive = false;
                    }

                } else{
                    research_relativity_layout.setVisibility(View.VISIBLE);
                    filtering_linear_layout.setVisibility(View.VISIBLE);

                    refresh_button.setImageResource(R.drawable.baseline_person_search);

                    isActive = false;
                }
            }
        });

        select_location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Your location has been detected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Cleaner> readLocalData() {

        Cleaners cleanersList = null;

        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.cleaners);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            String responseBody = stringBuilder.toString();
            cleanersList = new Gson().fromJson(responseBody, Cleaners.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert cleanersList != null;
        return cleanersList.getCleaners();
    }

    private void filtering(ArrayList<Cleaner> cleanerArrayList) {

        for (Cleaner cleaner : cleanerArrayList) {

            if (cleaner.getRateMultiplier() * 1000 >= minPrice && cleaner.getRateMultiplier() * 1000 <= maxPrice) {

                for (String day : cleaner.getAvailability()) {

                    if (day.equals(selectedDay)) {

                        mainList.add(cleaner);
                        break;
                    }
                }

            }
        }
    }

    private void setAdapterForCleaners(ArrayList<Cleaner> resultForCleaners) {

        background_image_view.setVisibility(View.GONE);
        recycler_view_cleaners.setVisibility(View.VISIBLE);

        AdapterCleaner adapterCleaner = new AdapterCleaner(resultForCleaners);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view_cleaners.setLayoutManager(mLayoutManager);
        recycler_view_cleaners.setAdapter(adapterCleaner);
    }

    public ArrayList<Cleaner> getMainList() {
        return mainList;
    }

    public void setMainList(ArrayList<Cleaner> mainList) {
        this.mainList = mainList;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}