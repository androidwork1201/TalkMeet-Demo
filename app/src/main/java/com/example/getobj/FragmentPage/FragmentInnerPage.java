package com.example.getobj.FragmentPage;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getobj.R;
import com.example.getobj.databinding.FragmentInnerPageBinding;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class FragmentInnerPage extends Fragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    Handler handler = new Handler();
    FragmentInnerPageBinding binding;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;
    private MyHandler myHandler = new MyHandler();
    public static String VIDEO_URL = "";    //Json image or video解析Url
    int value;  //設置Progress更新的計次累加
    int current = 0;
    int duration = 0;
    boolean RUN = true; //影片播放中的進行布林值
    RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.ic_baseline_loop_24)
            .error(R.drawable.ic_baseline_error_24);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInnerPageBinding.inflate(inflater, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        RUN = false;    //設定false以跳出while loop避免crash
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Fragment recycleList = new FragmentRecycleList();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(
                                        R.anim.slide_out_right,
                                        R.anim.pop_enter,
                                        R.anim.pop_out,
                                        R.anim.slide_in_left)
                                .replace(R.id.fragmentContainerView, recycleList)
                                .addToBackStack("recycle")
                                .commit();
                    }
                });
            }
        });
        //檢舉功能複製
        binding.toolbar.inflateMenu(R.menu.toolbar_item);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.info) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("您要檢舉影像嗎?")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getActivity(), "檢舉已送出!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setCancelable(false);
                    builder.show();
                }
                return true;
            }
        });
        // Bundle傳入的資料包
        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt("position", 0);
            String url = bundle.getString("imageUrl");
            String profilePic = bundle.getString("profilePic");
            String name = bundle.getString("name");
            String postContent = bundle.getString("postContent");
            String createDate = bundle.getString("createDate");
            String loveNumber = bundle.getString("loveNumber");
            String chatNumber = bundle.getString("chatNumber");
            showPosterInfo(
                    url,
                    profilePic,
                    name,
                    postContent,
                    createDate,
                    loveNumber,
                    chatNumber);

            Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }

    //內頁資料呈現
    private void showPosterInfo(String url,
                                String profilePic,
                                String name,
                                String postContent,
                                String createDate,
                                String loveNumber,
                                String chatNumber) {
        binding.txtName.setText(name);
        if (!postContent.equals("")) {
            binding.textPost.setText(postContent);
        } else {
            binding.textPost.setText("這裡沒留下些什麼...");
        }
        binding.textDate.setText("‧ " + createDate);
        binding.textFavorite.setText(loveNumber);
        binding.textChat.setText(chatNumber);

        //影片或圖片的判斷
        if (url.contains(".mp4")) {
            videoPost(url);
        } else {
            binding.surfaceView.setVisibility(View.INVISIBLE);
            binding.imagePlay.setVisibility(View.INVISIBLE);
            binding.imagePost.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(url)
                    .apply(requestOptions)
                    .into(binding.imagePost);
        }
        //user頭貼顯示
        Picasso.get()
                .load(profilePic)
                .fit()
                .transform(new RoundedTransformationBuilder()
                        .cornerRadius(50)
                        .borderWidth(3)
                        .borderColor(Color.WHITE)
                        .build())
                .into(binding.imageUserPics);
    }

    private void videoPost(String url) {
        binding.surfaceView.setVisibility(View.VISIBLE);
        binding.imagePost.setVisibility(View.INVISIBLE);
        VIDEO_URL = url;
        surfaceHolder = binding.surfaceView.getHolder();
        surfaceHolder.addCallback(FragmentInnerPage.this);
    }

    //自訂surface整合mediaPlayer
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(surfaceHolder);
        try {
            mediaPlayer.setDataSource(VIDEO_URL);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(FragmentInnerPage.this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    //內頁離開影片占用資源釋放
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseMediaPlayer();
    }

    //應該是用不太到這段，畢竟只做Fragment切換還不到要銷毀
    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();

    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Tag", "Close Video");
    }

    //影片播放暫停邏輯設定
    //進度條設定
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        binding.progressBarVideoTime.setProgress(0);
        binding.progressBarVideoTime.setMax(100);
        binding.progressBarVideoTime.getProgressDrawable().setColorFilter(
                Color.DKGRAY, PorterDuff.Mode.SRC_IN);

        binding.progressBarVideoTime.setVisibility(View.VISIBLE);
        current = mediaPlayer.getCurrentPosition();
        duration = mediaPlayer.getDuration();
        if (value == 0) {
            new MyThread().start();
        }
        mediaPlayer.start();

        binding.surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                binding.imagePlay.setVisibility(View.VISIBLE);
            }
        });

        binding.imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imagePlay.setVisibility(View.INVISIBLE);
                mediaPlayer.start();
                new MyThread().start();
            }
        });
    }

    //進度條執行續
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    value++;
                    binding.progressBarVideoTime.setProgress(value);
                    break;
            }
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    int gap = (mediaPlayer.getDuration() / 100);
                    Thread.sleep(gap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (value > 99) {
                    value = 0;
                    binding.progressBarVideoTime.setProgress(value);
                    break;
                }
                if (!RUN) {
                    break;
                }
                Message msg = new Message();
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }
    }
}
