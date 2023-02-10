package com.example.getobj.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import com.example.getobj.GsonData;
import com.example.getobj.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class FocusAdapter extends ListAdapter<GsonData.DataDTO, FocusAdapter.ViewHolder> {

    Context context;
    ArrayList<String> list;
    ArrayList<String> userPicsList;
    ArrayList<String> userName;
    ArrayList<String> postContent;
    ArrayList<String> love;
    ArrayList<String> chat;

    private OnItemClickListener mlistener;  //點擊接面

    public FocusAdapter(Context context,
                        ArrayList<String> list,
                        ArrayList<String> userPicsList,
                        ArrayList<String> userName,
                        ArrayList<String> postContent,
                        ArrayList<String> love,
                        ArrayList<String> chat) {
        super(Diff_Callback);
        this.context = context;
        this.list = list;
        this.userPicsList = userPicsList;
        this.userName = userName;
        this.postContent = postContent;
        this.love = love;
        this.chat = chat;
    }

    public static final DiffUtil.ItemCallback<GsonData.DataDTO> Diff_Callback = new DiffUtil.ItemCallback<GsonData.DataDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull GsonData.DataDTO oldItem, @NonNull GsonData.DataDTO newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull GsonData.DataDTO oldItem, @NonNull GsonData.DataDTO newItem) {
            return oldItem == newItem;
        }
    };

    //點擊接面
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.focus_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = list.get(position);
        String userPics = userPicsList.get(position);
        String name = userName.get(position);
        String postContentText = postContent.get(position);
        String loveNumber = love.get(position);
        String chatNumber = chat.get(position);
        holder.showFocusPost(url, userPics, name, postContentText, loveNumber, chatNumber);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView postImageView;
        public RoundedImageView imageViewFocusUserPics;
        public RoundedImageView imageFocusUserPicsMin;
        public ImageView imageErrorReport;
        public TextView textViewFocusUserName;
        public TextView textFocusFavorite;
        public TextView textFocusChat;
        public TextView textFocusPostText;
        public TextView textFocus;
        public int press = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImageView = itemView.findViewById(R.id.image_view_focus_photo);
            imageViewFocusUserPics = itemView.findViewById(R.id.image_view_focus_user_pics);
            imageFocusUserPicsMin = itemView.findViewById(R.id.image_focus_user_pics_min);
            textViewFocusUserName = itemView.findViewById(R.id.text_view_focus_user_name);
            textFocusFavorite = itemView.findViewById(R.id.text_focus_favorite);
            textFocusChat = itemView.findViewById(R.id.text_focus_chat);
            textFocusPostText = itemView.findViewById(R.id.text_focus_post_text);
            textFocus = itemView.findViewById(R.id.text_focus);
            imageErrorReport = itemView.findViewById(R.id.image_error_report);


            //item點擊監聽
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void showFocusPost(String url,
                                  String userPics,
                                  String name,
                                  String postContent,
                                  String loveNumber,
                                  String chatNumber) {
            Glide.with(context)
                    .load(userPics)
                    .into(imageViewFocusUserPics);
            Glide.with(context)
                    .load(url)
                    .into(postImageView);
            Picasso.get()
                    .load(userPics)
                    .fit()
                    .transform(new RoundedTransformationBuilder()
                            .cornerRadius(50)
                            .borderWidth(3)
                            .borderColor(Color.WHITE)
                            .build())
                    .into(imageFocusUserPicsMin);
            textViewFocusUserName.setText(String.valueOf(name));
            textFocusFavorite.setText(String.valueOf(loveNumber));
            textFocusChat.setText(String.valueOf(chatNumber));
            textFocusPostText.setText(String.valueOf(postContent));
            imageErrorReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("您要檢舉影像嗎?")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                            .setTitle("你確定?")
                                            .setNegativeButton("取消", null)
                                            .setPositiveButton("對啦", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Toast.makeText(context, "檢舉已送出!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    builder.setCancelable(false);
                                    builder.show();
                                }
                            });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
            textFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (press){
                        case 0:
                            press = 1;
                            textFocus.setText("已關注");
                            textFocus.setTextColor(Color.parseColor("#8A8A8A"));
                            textFocus.setBackgroundResource(R.drawable.focus_text_press);
                            break;
                        case 1:
                            press = 0;
                            textFocus.setText("+關注");
                            textFocus.setTextColor(Color.parseColor("#E11258"));
                            textFocus.setBackgroundResource(R.drawable.focus_text);
                            break;
                    }
                }
            });
        }
    }
}
