package com.example.thorne_hw05;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thorne_hw05.databinding.FragmentLoginBinding;
import com.example.thorne_hw05.databinding.FragmentPostListBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostListFragment extends Fragment {

    private static final String ARG_PARAM_USER_CREDS = "ARG_PARAM_USER_CREDS";
    FragmentPostListBinding binding;
    private final String TAG = "demo";
    private String token;
    private String userID;
    private String userFullName;
    private String status;
    UserCreds tempUserCreds;
    OkHttpClient client;
    String pageNum = "1";
    ArrayList<Post> currentPosts = new ArrayList<>();
    ArrayList<Integer> pageNums = new ArrayList<>();
    PostRecyclerAdapter postRecyclerAdapter;
    PageRecyclerAdapter pageRecyclerAdapter;
    RecyclerView recyclerViewPostList;
    RecyclerView recyclerViewPageCount;
    LinearLayoutManager layoutManager1;
    LinearLayoutManager layoutManager2;
    int pageCount;

    public PostListFragment() {
        // Required empty public constructor
    }

    public static PostListFragment newInstance(UserCreds userCreds) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER_CREDS, userCreds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tempUserCreds = (UserCreds) getArguments().getSerializable(ARG_PARAM_USER_CREDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        token = tempUserCreds.getToken();
        userID = tempUserCreds.getUser_id();
        userFullName = tempUserCreds.getUser_fullname();
        status = tempUserCreds.getStatus();
        client = new OkHttpClient();

        recyclerViewPostList = binding.recyclerViewPostList;
        recyclerViewPostList.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext());
        recyclerViewPageCount = binding.recyclerViewPageCount;
        recyclerViewPageCount.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        getPosts(pageNum);

        return binding.getRoot();
    }

    void getPosts(String pageNum){
        HttpUrl.Builder apiBuilder = new HttpUrl.Builder();
        HttpUrl apiUrl = apiBuilder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("posts")
                .addQueryParameter("page", "1")
                .build();

        Log.d(TAG, "onCreateView: apiURL: " + apiUrl);

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "BEARER " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d(TAG, "onResponse: getPosts " + Thread.currentThread().getId());

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    PostList postList = gson.fromJson(response.body().charStream(), PostList.class);
                    Log.d(TAG, "onResponse: postList: " + postList);

                    for (Post post : postList.posts){
                        currentPosts.add(post);
                    }

                    pageCount = (int) Math.ceil(postList.totalCount/10);
                    pageNums = new ArrayList<>();
                    for(int i = 0; i < pageCount; i++){
                        pageNums.add(i+1);
                    }

                    Log.d(TAG, "onResponse: post array list " + currentPosts);
                    //make sure to update textviews, etc. on UI thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            recyclerViewPostList.setLayoutManager(layoutManager1);
                            recyclerViewPageCount.setLayoutManager(layoutManager2);

                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewPostList.getContext(),
                                    layoutManager1.getOrientation());
                            recyclerViewPostList.addItemDecoration(dividerItemDecoration);

                            postRecyclerAdapter = new PostRecyclerAdapter(currentPosts);
                            pageRecyclerAdapter = new PageRecyclerAdapter(pageNums);
                            recyclerViewPostList.setAdapter(postRecyclerAdapter);
                            recyclerViewPageCount.setAdapter(pageRecyclerAdapter);
                        }
                    });
                }else{
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(TAG, "onResponse: getPosts else fork: " + body);
                }
            }
        });

    }
}