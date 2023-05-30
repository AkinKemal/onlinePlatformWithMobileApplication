package com.example.onlineplatform.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineplatform.R;
import com.example.onlineplatform.Entity.Rating;

import java.util.ArrayList;
public class AdapterReviewsAndRating extends RecyclerView.Adapter<AdapterReviewsAndRating.ViewHolder> {

    Integer[] scoreImage = {
            R.drawable.score1,
            R.drawable.score2,
            R.drawable.score3,
            R.drawable.score4,
            R.drawable.score5,
            R.drawable.baseline_question_mark,
    };

    Integer[] commentImage = {
            R.drawable.comment1,
            R.drawable.comment2,
            R.drawable.comment3,
            R.drawable.comment4,
            R.drawable.comment5,
            R.drawable.comment6,
            R.drawable.comment7,
            R.drawable.comment8,
            R.drawable.comment9,
            R.drawable.comment10,
            R.drawable.baseline_question_mark,
    };

    private int like = 0;
    private int dislike = 0;
    private boolean isLike = false;
    private boolean isDislike = false;
    private ArrayList<Rating> ratingList;

    public AdapterReviewsAndRating(ArrayList<Rating> ratingList) {
        this.setRatingList(ratingList);
    }

    @Override
    public AdapterReviewsAndRating.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_reviews_and_rating, parent, false);
        return new AdapterReviewsAndRating.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterReviewsAndRating.ViewHolder holder, final int position) {
        Rating rating = getRatingList().get(position);

        holder.user_title_text_view.setText(rating.getUser());

        holder.date_text_view.setText(rating.getDate());

        int id;
        if (rating.getRating() >= 0 && rating.getRating() < 1) {
            id = 0;
        } else if (rating.getRating() >= 1 && rating.getRating() < 2) {
            id = 1;
        } else if (rating.getRating() >= 2 && rating.getRating() < 3) {
            id = 2;
        } else if (rating.getRating() >= 3 && rating.getRating() < 4) {
            id = 3;
        } else if (rating.getRating() >= 4 && rating.getRating() <= 5) {
            id = 4;
        } else {
            id = 5;
        }

        Glide.with(holder.itemView)
                .load(scoreImage[id])
                .centerCrop()
                .into(holder.score_image_view);

        holder.comment_text_view.setText(rating.getComment());

        Glide.with(holder.itemView)
                .load(commentImage[Integer.parseInt(rating.getPhotos().get(0))])
                .centerCrop()
                .into(holder.rating_1_image_view);

        Glide.with(holder.itemView)
                .load(commentImage[Integer.parseInt(rating.getPhotos().get(1))])
                .centerCrop()
                .into(holder.rating_2_image_view);

        like = rating.getLikes();
        holder.mood_text_view.setText(String.valueOf(like));

        dislike = rating.getDislikes();
        holder.bad_mood_text_view.setText(String.valueOf(dislike));

        holder.mood_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isDislike){
                    dislike = dislike - 1;
                    holder.bad_mood_text_view.setText(String.valueOf(dislike));
                    isDislike = false;
                }

                if(!isLike){
                    like = like + 1;
                    holder.mood_text_view.setText(String.valueOf(like));
                    isLike = true;
                }

                holder.mood_button.setImageResource(R.drawable.baseline_mood_active);
                holder.bad_mood_button.setImageResource(R.drawable.baseline_mood_bad);
            }
        });

        holder.bad_mood_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isDislike){
                    dislike = dislike + 1;
                    holder.bad_mood_text_view.setText(String.valueOf(dislike));
                    isDislike = true;
                }

                if(isLike){
                    like = like - 1;
                    holder.mood_text_view.setText(String.valueOf(like));
                    isLike = false;
                }

                holder.mood_button.setImageResource(R.drawable.baseline_mood);
                holder.bad_mood_button.setImageResource(R.drawable.baseline_mood_bad_active);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return getRatingList().size();
    }

    public ArrayList<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(ArrayList<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isDislike() {
        return isDislike;
    }

    public void setDislike(boolean dislike) {
        isDislike = dislike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView user_title_text_view;
        private final TextView date_text_view;
        private final ImageView score_image_view;
        private final TextView comment_text_view;
        private final ImageView rating_1_image_view;
        private final ImageView rating_2_image_view;
        private final ImageButton mood_button;
        private final TextView mood_text_view;
        private final ImageButton bad_mood_button;
        private final TextView bad_mood_text_view;

        public ViewHolder(View itemView) {
            super(itemView);

            user_title_text_view = itemView.findViewById(R.id.user_title_text_view);
            date_text_view = itemView.findViewById(R.id.date_text_view);
            score_image_view = itemView.findViewById(R.id.score_image_view);
            comment_text_view = itemView.findViewById(R.id.comment_text_view);
            rating_1_image_view = itemView.findViewById(R.id.rating_1_image_view);
            rating_2_image_view = itemView.findViewById(R.id.rating_2_image_view);
            mood_button = itemView.findViewById(R.id.mood_button);
            mood_text_view = itemView.findViewById(R.id.mood_text_view);
            bad_mood_button = itemView.findViewById(R.id.bad_mood_button);
            bad_mood_text_view = itemView.findViewById(R.id.bad_mood_text_view);
        }
    }
}