package com.example.getobj.FragmentPage;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.getobj.Adapter.PostAdapter;

import com.example.getobj.Client.ApiClient;
import com.example.getobj.GsonData;
import com.example.getobj.Interface.PostInterface;
import com.example.getobj.R;
import com.example.getobj.databinding.FragmentRecycleListBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentRecycleList extends Fragment implements PostAdapter.OnItemClickListener {

    FragmentRecycleListBinding binding;
    PostAdapter adapter;
    Bundle bundle = new Bundle();

    String header = "t9jSctzaGt2JiiOqw5QGGAfG6he6f7H1";
    int count = 10;
    int page = 1;
    int type = 6;


    //各項資料Array List
    ArrayList<String> imageUrl = new ArrayList<>();
    ArrayList<String> profilePic = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> postContent = new ArrayList<>();
    ArrayList<String> createDate = new ArrayList<>();
    ArrayList<String> loveNumber = new ArrayList<>();
    ArrayList<String> chatNumber = new ArrayList<>();
    ArrayList<String> isVideo = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRecycleListBinding.inflate(inflater, container, false);
        recycleViewList();

        //底部加載
        binding.progressLoading.setBackgroundColor(Color.WHITE);
        binding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){

                    count+=10;
                    binding.progressLoading.setVisibility(View.VISIBLE);
                    recycleViewList();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.recycle.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.recycle.setHasFixedSize(true);


        System.out.println(imageUrl);
    }

    private void recycleViewList() {
//        int pos = imageUrl.size();
        PostInterface postInterface = ApiClient.getClientData().create(PostInterface.class);
        Call<GsonData> call = postInterface.getData(header, count, page, type);
        call.enqueue(new Callback<GsonData>() {
            @Override
            public void onResponse(Call<GsonData> call, Response<GsonData> response) {
                List<GsonData.DataDTO> str = response.body().getData();
                for (int i = imageUrl.size(); i < str.size(); i++) {
                    imageUrl.add(str.get(i).getUrl());
                    profilePic.add(str.get(i).getProfilePic());
                    name.add(str.get(i).getName());
                    postContent.add(str.get(i).getContent());
                    createDate.add(str.get(i).getCreateDt());
                    loveNumber.add(str.get(i).getLove());
                    chatNumber.add(str.get(i).getCommentNum());
                    isVideo.add(str.get(i).getType());
                }

                adapter = new PostAdapter(getActivity(), imageUrl, isVideo);
                binding.recycle.setAdapter(adapter);
//                adapter.notifyItemChanged(pos);
                adapter.submitList(str);

                adapter.setOnItemClickListener(FragmentRecycleList.this);
                binding.progressLoading.setVisibility(View.INVISIBLE);

                //頂部刷新
                binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        adapter.notifyDataSetChanged();
                        binding.refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call<GsonData> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        goToFragmentInnerPage(position);

    }

    //bundle資料依position位置打包
    private void goToFragmentInnerPage(int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment inner = new FragmentInnerPage();
        bundle.putInt("position", position);
        bundle.putString("imageUrl", imageUrl.get(position));
        bundle.putString("profilePic", profilePic.get(position));
        bundle.putString("name", name.get(position));
        bundle.putString("postContent", postContent.get(position));
        bundle.putString("createDate", createDate.get(position));
        bundle.putString("loveNumber", loveNumber.get(position));
        bundle.putString("chatNumber", chatNumber.get(position));
        inner.setArguments(bundle);

        fragmentTransaction.add(R.id.fragmentContainerView, inner, "Inner");
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.pop_out,
                R.anim.pop_enter,
                R.anim.slide_out_right);
        fragmentTransaction.addToBackStack("recycle");
        fragmentTransaction.show(inner);
        fragmentTransaction.commit();

    }
}

