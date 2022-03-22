package com.example.thorne_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginFragment {

    Context context;
    SharedPreferences sharedPref;
    String token="";
    final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sharedPref = context.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoginFragment())
                .commit();

    }

    @Override
    public void goToPosts(UserCreds userCreds) {
        Log.d(TAG, "goToPosts: " + userCreds);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, PostListFragment.newInstance(userCreds))
                .addToBackStack(null)
                .commit();
    }
}