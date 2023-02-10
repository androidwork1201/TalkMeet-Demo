package com.example.getobj.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import com.example.getobj.GsonData;
import com.example.getobj.R;

import java.util.ArrayList;
import java.util.Objects;

public class PostAdapter extends ListAdapter<GsonData.DataDTO, PostAdapter.ViewHolder> {

    Context context;
    ArrayList<String> list;
    ArrayList<String> isVideo;
    private OnItemClickListener mlistener;  //點擊接面

    public PostAdapter(Context context, ArrayList<String> list, ArrayList<String> isVideo) {
        super(Diff_Callback);
        this.context = context;
        this.list = list;
        this.isVideo = isVideo;

    }
    public static final DiffUtil.ItemCallback<GsonData.DataDTO> Diff_Callback = new DiffUtil.ItemCallback<GsonData.DataDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull GsonData.DataDTO oldItem, @NonNull GsonData.DataDTO newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull GsonData.DataDTO oldItem, @NonNull GsonData.DataDTO newItem) {
//            return Objects.equals(oldItem.getUrl(), newItem.getUrl()) &&
//                    Objects.equals(oldItem.getType(), newItem.getType());
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

//    public PostAdapter(Context context, ArrayList<String> list, ArrayList<String> isVideo) {
//        this.context = context;
//        this.list = list;
//        this.isVideo = isVideo;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = list.get(position);
        String videoIcon = isVideo.get(position);
        holder.showImageAndVideo(url, videoIcon);

    }

//    @Override
//    public int getItemCount() {
//        return list.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView postImageView;
        public ImageView isVideoIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImageView = itemView.findViewById(R.id.image_post);
            isVideoIcon = itemView.findViewById(R.id.image_is_video);

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

        //貼文圖片加載以及依照傳入type判斷是否需要加入video cam
        void showImageAndVideo(String url, String isVideo) {
            if (Objects.equals(isVideo, "4")) {
                isVideoIcon.setVisibility(View.VISIBLE);
            }
            Glide.with(context)
                    .load(url)
                    .into(postImageView);
        }
    }

}
