package com.example.thorne_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragment,
        PostRecyclerAdapter.IPostRecycler, PageRecyclerAdapter.IPageRecycler {

    SharedPreferences sharedPref;
    UserCreds tempCreds;
    String uId;
    String token="";
    final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);

        Log.d(TAG, "onCreate: " + sharedPref.toString());

        if(sharedPref == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rootView, new LoginFragment())
                    .commit();
        }
        else{

        }

        try {

        } catch(Exception e){
            Log.d(TAG, "onCreate: catch: " + e.getMessage());
        }


    }

    @Override
    public void goToPosts(UserCreds userCreds) {
        Log.d(TAG, "goToPosts: " + userCreds);
        tempCreds = userCreds;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, PostListFragment.newInstance(userCreds))
                .addToBackStack(null)
                .commit();
    }
}