package com.example.onlineplatform.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineplatform.Entity.Cleaner;
import com.example.onlineplatform.Activity.CleanerDetailActivity;
import com.example.onlineplatform.R;

import java.util.ArrayList;

public class AdapterCleaner extends RecyclerView.Adapter<AdapterCleaner.ViewHolder> {

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

    private ArrayList<Cleaner> cleanerList;

    public AdapterCleaner(ArrayList<Cleaner> cleanerList) {
        this.setCleanerList(cleanerList);
    }

    @Override
    public AdapterCleaner.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cleaner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCleaner.ViewHolder holder, final int position) {
        Cleaner cleaner = getCleanerList().get(position);

        Glide.with(holder.itemView)
                .load(cleanerImageId[Integer.parseInt(cleaner.getPhoto())])
                .centerCrop()
                .into(holder.cleaner_image_view);

        String str = cleaner.getFirstName() + " " + cleaner.getLastName();
        holder.cleaner_title_text_view.setText(str);

        str = cleaner.getGender() + " - ";
        holder.gender_text_view.setText(str);

        str = cleaner.getAge() + " years old";
        holder.age_text_view.setText(str);

        str = "$" + cleaner.getRateMultiplier() * 1000;
        holder.price_text_view.setText(str);

        int id;
        if (cleaner.getSuccessScore() >= 0 && cleaner.getSuccessScore() < 1) {
            id = 0;
        } else if (cleaner.getSuccessScore() >= 1 && cleaner.getSuccessScore() < 2) {
            id = 1;
        } else if (cleaner.getSuccessScore() >= 2 && cleaner.getSuccessScore() < 3) {
            id = 2;
        } else if (cleaner.getSuccessScore() >= 3 && cleaner.getSuccessScore() < 4) {
            id = 3;
        } else if (cleaner.getSuccessScore() >= 4 && cleaner.getSuccessScore() <= 5) {
            id = 4;
        } else {
            id = 5;
        }

        Glide.with(holder.itemView)
                .load(scoreImage[id])
                .centerCrop()
                .into(holder.score_image_view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cleaner'a tıklanıldığında yapılacak işlemler
                // Örneğin, detay sayfasına yönlendirme yapılabilir
                Intent intent = new Intent(v.getContext(), CleanerDetailActivity.class);
                intent.putExtra("cleanerId", cleaner.getPhoto());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getCleanerList().size();
    }

    public ArrayList<Cleaner> getCleanerList() {
        return cleanerList;
    }

    public void setCleanerList(ArrayList<Cleaner> cleanerList) {
        this.cleanerList = cleanerList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cleaner_image_view;
        private final TextView cleaner_title_text_view;
        private final TextView gender_text_view;
        private final TextView age_text_view;
        private final TextView price_text_view;
        private final ImageView score_image_view;

        public ViewHolder(View itemView) {
            super(itemView);
            cleaner_image_view = itemView.findViewById(R.id.cleaner_image_view);
            cleaner_title_text_view = itemView.findViewById(R.id.cleaner_title_text_view);
            gender_text_view = itemView.findViewById(R.id.gender_text_view);
            age_text_view = itemView.findViewById(R.id.age_text_view);
            price_text_view = itemView.findViewById(R.id.price_text_view);
            score_image_view = itemView.findViewById(R.id.score_image_view);
        }
    }
}