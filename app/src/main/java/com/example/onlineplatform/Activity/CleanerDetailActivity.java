package com.example.onlineplatform.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onlineplatform.Adapter.AdapterReviewsAndRating;
import com.example.onlineplatform.Entity.Cleaner;
import com.example.onlineplatform.Entity.Cleaners;
import com.example.onlineplatform.Entity.Rating;
import com.example.onlineplatform.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CleanerDetailActivity extends AppCompatActivity {

    Integer[] cleanerImageId = {
            R.drawable.cleaner2,
            R.drawable.cleaner3,
            R.drawable.cleaner4,
            R.drawable.cleaner5,
            R.drawable.cleaner6,
            R.drawable.cleaner7,
            R.drawable.cleaner8,
            R.drawable.cleaner9,
            R.drawable.cleaner10,
            R.drawable.cleaner1,
            R.drawable.cleaner11,
            R.drawable.cleaner12,
            R.drawable.cleaner13,
            R.drawable.cleaner14,
            R.drawable.cleaner15,
            R.drawable.cleaner16,
            R.drawable.cleaner17,
            R.drawable.cleaner18,
            R.drawable.cleaner19,
            R.drawable.cleaner20,
    };

    Integer[] scoreImage = {
            R.drawable.score1,
            R.drawable.score2,
            R.drawable.score3,
            R.drawable.score4,
            R.drawable.score5,
            R.drawable.baseline_question_mark,
    };

    private String cleanerId;
    private float totalPrice = 0;
    private float personalPrice = 1000;
    private float housePrice = 0;
    private float methodPrice = 0;
    private String houseType;
    StringBuilder selectedOptions;
    Cleaner cleaner;
    private ImageView cleaner_in_detail_image_view;
    private ImageView score_in_detail_image_view;
    private TextView insurance_in_detail_text_view;
    private TextView personal_price_in_detail_text_view;
    private TextView cleaner_name_in_detail_text_view;
    private TextView gender_in_detail_text_view;
    private TextView age_in_detail_text_view;
    private TextView introduction_in_detail_text_view;
    private TextView cleaning_methods_in_detail_text_view;
    private TextView phone_in_detail_text_view;
    private TextView mail_in_detail_text_view;
    private TextView house_price_in_detail_text_view;
    private TextView method_price_in_detail_text_view;
    private TextView total_comment_text_view;
    private TextView total_price_in_detail_text_view;
    private CheckBox checkbox_option1;
    private CheckBox checkbox_option2;
    private CheckBox checkbox_option3;
    private CheckBox checkbox_option4;
    private CheckBox checkbox_option5;
    private CheckBox checkbox_option6;
    private CheckBox checkbox_option7;
    private RecyclerView comment_in_detail_recycler_view;
    private Dialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_detail);

        cleaner_in_detail_image_view = findViewById(R.id.cleaner_in_detail_image_view);
        score_in_detail_image_view = findViewById(R.id.score_in_detail_image_view);
        insurance_in_detail_text_view = findViewById(R.id.insurance_in_detail_text_view);
        personal_price_in_detail_text_view = findViewById(R.id.personal_price_in_detail_text_view);
        cleaner_name_in_detail_text_view = findViewById(R.id.cleaner_name_in_detail_text_view);
        gender_in_detail_text_view = findViewById(R.id.gender_in_detail_text_view);
        age_in_detail_text_view = findViewById(R.id.age_in_detail_text_view);
        introduction_in_detail_text_view = findViewById(R.id.introduction_in_detail_text_view);
        cleaning_methods_in_detail_text_view = findViewById(R.id.cleaning_methods_in_detail_text_view);
        phone_in_detail_text_view = findViewById(R.id.phone_in_detail_text_view);
        mail_in_detail_text_view = findViewById(R.id.mail_in_detail_text_view);
        house_price_in_detail_text_view = findViewById(R.id.house_price_in_detail_text_view);
        method_price_in_detail_text_view = findViewById(R.id.method_price_in_detail_text_view);
        total_comment_text_view = findViewById(R.id.total_comment_text_view);
        total_price_in_detail_text_view = findViewById(R.id.total_price_in_detail_text_view);
        RadioGroup house_radio_group = findViewById(R.id.house_radio_group);
        checkbox_option1 = findViewById(R.id.checkbox_option1);
        checkbox_option2 = findViewById(R.id.checkbox_option2);
        checkbox_option3 = findViewById(R.id.checkbox_option3);
        checkbox_option4 = findViewById(R.id.checkbox_option4);
        checkbox_option5 = findViewById(R.id.checkbox_option5);
        checkbox_option6 = findViewById(R.id.checkbox_option6);
        checkbox_option7 = findViewById(R.id.checkbox_option7);
        comment_in_detail_recycler_view = findViewById(R.id.comment_in_detail_recycler_view);
        Button booking_in_detail_button = findViewById(R.id.booking_in_detail_button);

        house_price_in_detail_text_view.setVisibility(View.GONE);
        method_price_in_detail_text_view.setVisibility(View.GONE);
        comment_in_detail_recycler_view.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setCleanerId(bundle.getString("cleanerId"));

            if (showDetail()) {
                System.out.println("Success");
            }
        }

        //radio grupta bulunan tüm seçimlerin ne yapacağı
        house_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.house_1:
                        housePrice = 250;
                        houseType = getResources().getString(R.string._1_1);
                        break;
                    case R.id.house_2:
                        housePrice = 500;
                        houseType = getResources().getString(R.string._2_1);
                        break;
                    case R.id.house_3:
                        housePrice = 750;
                        houseType = getResources().getString(R.string._3_1);
                        break;
                    case R.id.house_4:
                        housePrice = 1000;
                        houseType = getResources().getString(R.string._3_2);
                        break;
                    case R.id.house_5:
                        housePrice = 1250;
                        houseType = getResources().getString(R.string._4_1);
                        break;
                    case R.id.house_6:
                        housePrice = 1350;
                        houseType = getResources().getString(R.string._4_2);
                        break;
                    case R.id.house_7:
                        housePrice = 1450;
                        houseType = getResources().getString(R.string._4_3);
                        break;
                    case R.id.house_8:
                        housePrice = 1550;
                        houseType = getResources().getString(R.string._5_1);
                        break;
                    case R.id.house_9:
                        housePrice = 1650;
                        houseType = getResources().getString(R.string._6_1);
                        break;
                    case R.id.house_10:
                        housePrice = 2000;
                        houseType = getResources().getString(R.string.xl);
                        break;
                    default:
                        break;
                }

                house_price_in_detail_text_view.setVisibility(View.VISIBLE);

                String str = "$" + housePrice;
                house_price_in_detail_text_view.setText(str);

                updateTotalPrice();
            }
        });

        checkbox_option1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 250;
            } else {
                methodPrice -= 250;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        checkbox_option2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 500;
            } else {
                methodPrice -= 500;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        checkbox_option3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 100;
            } else {
                methodPrice -= 100;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        checkbox_option4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 100;
            } else {
                methodPrice -= 100;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        checkbox_option5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 200;
            } else {
                methodPrice -= 200;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        checkbox_option6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 500;
            } else {
                methodPrice -= 500;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        checkbox_option7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                methodPrice += 500;
            } else {
                methodPrice -= 500;
            }

            method_price_in_detail_text_view.setVisibility(View.VISIBLE);
            String str = "$" + methodPrice;
            method_price_in_detail_text_view.setText(str);

            updateTotalPrice();
        });

        booking_in_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(houseType != null){
                    onButtonClick();
                    getReservation();
                } else{
                    Toast.makeText(getApplicationContext(), "Please select the house type!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateTotalPrice();
    }

    public void onButtonClick() {
        selectedOptions = new StringBuilder();
        if (checkbox_option1.isChecked()) {
            selectedOptions.append("General Cleaning\n");
        }

        if (checkbox_option2.isChecked()) {
            selectedOptions.append("Deep Cleaning\n");
        }

        if (checkbox_option3.isChecked()) {
            selectedOptions.append("Laundry and/or Ironing\n");
        }

        if (checkbox_option4.isChecked()) {
            selectedOptions.append("Organizing and Decluttering\n");
        }

        if (checkbox_option5.isChecked()) {
            selectedOptions.append("Window Cleaning\n");
        }

        if (checkbox_option6.isChecked()) {
            selectedOptions.append("Special Requests\n");
        }

        if (checkbox_option7.isChecked()) {
            selectedOptions.append("Pet-related Cleaning\n");
        }

        if (selectedOptions.length() <= 0) {
            selectedOptions.append("No options selected\n");
        }
    }

    private boolean showDetail() {

        cleaner = readLocalData();

        if (cleaner == null) {
            return false;
        }

        Glide.with(this)
                .load(cleanerImageId[Integer.parseInt(cleaner.getPhoto())])
                .centerCrop()
                .into(cleaner_in_detail_image_view);

        Glide.with(this)
                .load(scoreImage[getScore(cleaner.getSuccessScore())])
                .centerCrop()
                .into(score_in_detail_image_view);

        insurance_in_detail_text_view.setText(cleaner.getInsuranceInfo());

        personalPrice = (float) (personalPrice * cleaner.getRateMultiplier());
        String str = "$" + String.valueOf(personalPrice);
        personal_price_in_detail_text_view.setText(str);

        str = cleaner.getFirstName() + " " + cleaner.getLastName();
        cleaner_name_in_detail_text_view.setText(str);

        str = cleaner.getGender() + " - ";
        gender_in_detail_text_view.setText(str);

        str = cleaner.getAge() + " years old";
        age_in_detail_text_view.setText(str);

        introduction_in_detail_text_view.setText(cleaner.getIntroduction());

        cleaning_methods_in_detail_text_view.setText(cleaner.getCleaningMethods());

        phone_in_detail_text_view.setText(cleaner.getPhoneNumber());

        mail_in_detail_text_view.setText(cleaner.getEmail());
        mail_in_detail_text_view.setSelected(true);

        str = "Total Comment: " + cleaner.getTotalRatings();
        total_comment_text_view.setText(str);

        setAdapterForComment(cleaner.getRatings());

        updateTotalPrice();

        return true;
    }

    private void updateTotalPrice() {
        totalPrice = personalPrice + housePrice + methodPrice;
        String str = "$" + totalPrice;
        total_price_in_detail_text_view.setText(str);
    }

    private Cleaner readLocalData() {

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
        return cleanersList.getCleaners().get(Integer.parseInt(getCleanerId()));
    }

    private int getScore(double score) {
        int id;

        if (score >= 0 && score < 1) {
            id = 0;
        } else if (score >= 1 && score < 2) {
            id = 1;
        } else if (score >= 2 && score < 3) {
            id = 2;
        } else if (score >= 3 && score < 4) {
            id = 3;
        } else if (score >= 4 && score <= 5) {
            id = 4;
        } else {
            id = 5;
        }

        return id;
    }

    private void setAdapterForComment(ArrayList<Rating> resultForComments) {

        comment_in_detail_recycler_view.setVisibility(View.VISIBLE);

        AdapterReviewsAndRating adapterComment = new AdapterReviewsAndRating(resultForComments);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        comment_in_detail_recycler_view.setLayoutManager(mLayoutManager);
        comment_in_detail_recycler_view.setAdapter(adapterComment);
    }

    public void getReservation() {

        dialog = new Dialog(CleanerDetailActivity.this, R.style.Dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#808080")));
        dialog.getWindow().setGravity(Gravity.CENTER);

        RadioButton monday_button = dialog.findViewById(R.id.monday_button);
        RadioButton tuesday_button = dialog.findViewById(R.id.tuesday_button);
        RadioButton wednesday_button = dialog.findViewById(R.id.wednesday_button);
        RadioButton thursday_button = dialog.findViewById(R.id.thursday_button);
        RadioButton friday_button = dialog.findViewById(R.id.friday_button);
        RadioButton saturday_button = dialog.findViewById(R.id.saturday_button);
        RadioButton sunday_button = dialog.findViewById(R.id.sunday_button);
        TextView cleaner_name_text_view = dialog.findViewById(R.id.cleaner_name_text_view);
        TextView gender_text_view = dialog.findViewById(R.id.gender_text_view);
        TextView age_text_view = dialog.findViewById(R.id.age_text_view);
        TextView house_text_view = dialog.findViewById(R.id.house_text_view);
        TextView methods_text_view = dialog.findViewById(R.id.methods_text_view);
        TextView price_text_view = dialog.findViewById(R.id.price_text_view);
        Button booking_button = dialog.findViewById(R.id.booking_button);
        Button update_button = dialog.findViewById(R.id.update_button);
        Button cancel_button = dialog.findViewById(R.id.cancel_button);

        monday_button.setEnabled(false);
        monday_button.setText("X");
        tuesday_button.setEnabled(false);
        tuesday_button.setText("X");
        wednesday_button.setEnabled(false);
        wednesday_button.setText("X");
        thursday_button.setEnabled(false);
        thursday_button.setText("X");
        friday_button.setEnabled(false);
        friday_button.setText("X");
        saturday_button.setEnabled(false);
        saturday_button.setText("X");
        sunday_button.setEnabled(false);
        sunday_button.setText("X");

        for (String day : cleaner.getAvailability()) {

            switch (day) {
                case "Monday":
                    monday_button.setEnabled(true);
                    monday_button.setText(getResources().getString(R.string.monday));

                    break;
                case "Tuesday":
                    tuesday_button.setEnabled(true);
                    tuesday_button.setText(getResources().getString(R.string.tuesday));

                    break;
                case "Wednesday":
                    wednesday_button.setEnabled(true);
                    wednesday_button.setText(getResources().getString(R.string.wednesday));

                    break;
                case "Thursday":
                    thursday_button.setEnabled(true);
                    thursday_button.setText(getResources().getString(R.string.thursday));

                    break;
                case "Friday":
                    friday_button.setEnabled(true);
                    friday_button.setText(getResources().getString(R.string.friday));

                    break;
                case "Saturday":
                    saturday_button.setEnabled(true);
                    saturday_button.setText(getResources().getString(R.string.saturday));

                    break;
                case "Sunday":
                    sunday_button.setEnabled(true);
                    sunday_button.setText(getResources().getString(R.string.sunday));

                    break;
            }
        }

        String str = cleaner.getFirstName() + " " + cleaner.getLastName();
        cleaner_name_text_view.setText(str);

        str = cleaner.getGender() + " - ";
        gender_text_view.setText(str);

        str = String.valueOf(cleaner.getAge());
        age_text_view.setText(str);

        house_text_view.setText(houseType);

        methods_text_view.setText(selectedOptions);

        str = "$" + totalPrice;
        price_text_view.setText(str);

        booking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                Toast.makeText(getApplicationContext(), "Your reservation is successful!", Toast.LENGTH_SHORT).show();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                Toast.makeText(getApplicationContext(), "Your reservation has been cancelled!", Toast.LENGTH_SHORT).show();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });

        // Geri tuşuyla kapatılması engelleniyor
        //updateDialog.setCancelable(false);

        // Popup dışına tıklanarak kapatılması engelleniyor
        //updateDialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    public String getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
    }

    public float getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(float housePrice) {
        this.housePrice = housePrice;
    }

    public float getMethodPrice() {
        return methodPrice;
    }

    public void setMethodPrice(float methodPrice) {
        this.methodPrice = methodPrice;
    }

    public float getPersonalPrice() {
        return personalPrice;
    }

    public void setPersonalPrice(float personalPrice) {
        this.personalPrice = personalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
}