package com.example.getobj.FragmentPage;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getobj.R;
import com.example.getobj.databinding.FragmentFocusInnerPageBinding;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;


public class FragmentFocusInnerPage extends Fragment {

    private FragmentFocusInnerPageBinding binding;
    Handler handler = new Handler();
    public static String VIDEO_URL = "";    //Json image or video解析Url
    RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.ic_baseline_loop_24)
            .error(R.drawable.ic_baseline_error_24);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFocusInnerPageBinding.inflate(inflater, container, false);

        binding.focusToolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);
        binding.focusToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Fragment recycleList = new FragmentFocus();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(
                                        R.anim.slide_out_right,
                                        R.anim.pop_enter,
                                        R.anim.pop_out,
                                        R.anim.slide_in_left)
                                .replace(R.id.fragmentContainerView, recycleList)
                                .addToBackStack("focus")
                                .commit();
                    }
                });
            }
        });
        //檢舉功能複製
        binding.focusToolbar.inflateMenu(R.menu.toolbar_item);
        binding.focusToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
        binding.txtFocusName.setText(name);
        if (!postContent.equals("")) {
            binding.textFocusPost.setText(postContent);
        } else {
            binding.textFocusPost.setText("這裡沒留下些什麼...");
        }
        binding.textFocusDate.setText("‧ " + createDate);
        binding.textFocusFavorite.setText(loveNumber);
        binding.textFocusChat.setText(chatNumber);

        //貼文圖片
        Glide.with(getActivity())
                .load(url)
                .apply(requestOptions)
                .into(binding.imageFocusPost);

        //user頭貼顯示
        Picasso.get()
                .load(profilePic)
                .fit()
                .transform(new RoundedTransformationBuilder()
                        .cornerRadius(50)
                        .borderWidth(3)
                        .borderColor(Color.WHITE)
                        .build())
                .into(binding.imageFocusUserPics);
    }
}