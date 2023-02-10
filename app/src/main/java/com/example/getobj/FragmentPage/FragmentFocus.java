package com.example.getobj.FragmentPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.getobj.Adapter.FocusAdapter;
import com.example.getobj.Client.ApiClient;
import com.example.getobj.GsonData;
import com.example.getobj.Interface.PostInterface;
import com.example.getobj.R;
import com.example.getobj.databinding.FragmentFocusBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFocus extends Fragment implements FocusAdapter.OnItemClickListener {

    private FragmentFocusBinding binding;
    FocusAdapter adapter;
    Bundle bundle = new Bundle();

    String header = "t9jSctzaGt2JiiOqw5QGGAfG6he6f7H1";
    int count = 10;
    int page = 1;
    int type = 6;
    ArrayList<String> profilePic = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> imageUrl = new ArrayList<>();
    ArrayList<String> postContent = new ArrayList<>();
    ArrayList<String> createDate = new ArrayList<>();
    ArrayList<String> loveNumber = new ArrayList<>();
    ArrayList<String> chatNumber = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentFocusBinding.inflate(inflater, container, false);
        recycleViewList();

        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();
        binding.focusRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.focusRecycle.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void recycleViewList() {
        PostInterface postInterface = ApiClient.getClientData().create(PostInterface.class);
        Call<GsonData> call = postInterface.getData(header, count, page, type);
        call.enqueue(new Callback<GsonData>() {
            @Override
            public void onResponse(Call<GsonData> call, Response<GsonData> response) {
                List<GsonData.DataDTO> str = response.body().getData();
                for (int i = 0; i < str.size(); i++) {
                    profilePic.add(str.get(i).getProfilePic());
                    name.add(str.get(i).getName());
                    imageUrl.add(str.get(i).getUrl());
                    postContent.add(str.get(i).getContent());
                    createDate.add(str.get(i).getCreateDt());
                    loveNumber.add(str.get(i).getLove());
                    chatNumber.add(str.get(i).getCommentNum());
                }
                adapter = new FocusAdapter(getActivity(),
                                            imageUrl,
                                            profilePic,
                                            name,
                                            postContent,
                                            loveNumber,
                                            chatNumber);
                binding.focusRecycle.setAdapter(adapter);
                adapter.submitList(str);

                adapter.setOnItemClickListener(FragmentFocus.this);

                binding.focusRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        adapter.notifyDataSetChanged();
                        binding.focusRefreshLayout.setRefreshing(false);


                    }
                });
            }
            @Override
            public void onFailure(Call<GsonData> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        goToFragmentInnerPage(position);
    }

    private void goToFragmentInnerPage(int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment focus = new FragmentFocusInnerPage();
        bundle.putInt("position", position);
        bundle.putString("profilePic", profilePic.get(position));
        bundle.putString("name", name.get(position));
        bundle.putString("imageUrl", imageUrl.get(position));
        bundle.putString("postContent", postContent.get(position));
        bundle.putString("createDate", createDate.get(position));
        bundle.putString("loveNumber", loveNumber.get(position));
        bundle.putString("chatNumber", chatNumber.get(position));
        focus.setArguments(bundle);
        fragmentTransaction.add(R.id.fragmentContainerView, focus, "focus");
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.pop_out,
                R.anim.pop_enter,
                R.anim.slide_out_right);
        fragmentTransaction.addToBackStack("focus");
        fragmentTransaction.show(focus);
        fragmentTransaction.commit();
    }
}