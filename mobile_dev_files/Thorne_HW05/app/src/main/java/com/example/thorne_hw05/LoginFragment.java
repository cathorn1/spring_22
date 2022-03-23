package com.example.thorne_hw05;

import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.thorne_hw05.databinding.FragmentLoginBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    final String TAG = "demo";
    FragmentLoginBinding binding;
    private OkHttpClient client = new OkHttpClient();

    EditText etLoginEmail;
    EditText etLoginPassword;
    Button btnLogin;
    Button btnCreateNewAcct;
    String userEmail;
    String userPassword;

    LoginFragment.ILoginFragment listener;
    interface ILoginFragment{
        void goToPosts(UserCreds userCreds);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof LoginFragment.ILoginFragment){
            listener = (LoginFragment.ILoginFragment) context;
        }else{
            throw new RuntimeException("HEY, implement ILoginFragment");
        }
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        client = new OkHttpClient();
        etLoginEmail = (EditText) binding.etLoginEmail;
        etLoginPassword = binding.etLoginPassword;
        btnLogin = binding.btnLogin;
        btnCreateNewAcct = binding.btnCreateAccount;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etLoginEmail.toString() != "" && etLoginPassword.toString() !=""){
                    userEmail = etLoginEmail.getText().toString();
                    userPassword = etLoginPassword.getText().toString();
                } else {
                    Toast.makeText(getContext(), "FILL OUT FIELDS", Toast.LENGTH_SHORT).show();
                }

//                HttpUrl.Builder loginBuilder = new HttpUrl.Builder();
//                HttpUrl loginUrl = loginBuilder.scheme("https")
//                        .host("www.theappsdr.com")
//                        .addPathSegment("posts/login")
////                        .addQueryParameter("email", userEmail)
////                        .addQueryParameter("password", userPassword)
//                        .build();

//                Log.d(TAG, "onCreateView: email: " + userEmail + " pass: " + userPassword+ " login url: " + loginUrl);

                RequestBody formBody =new FormBody.Builder()
                        .add("email", userEmail)
                        .add("password", userPassword)
                        .build();
                Log.d(TAG, "onClick: form body" + formBody.toString());
                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/posts/login")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        Log.d(TAG, "onResponse: try login " + Thread.currentThread().getId());

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            UserCreds userCreds = gson.fromJson(response.body().charStream(), UserCreds.class);
                            Log.d(TAG, "get User Creds: " + userCreds);

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(getString(R.string.shared_pref_token_key), String.valueOf(userCreds.token));
                            Log.d(TAG, "onResponse: shared prefs: " + String.valueOf(userCreds.token));
                            editor.apply();

                            listener.goToPosts(userCreds);

                        }else{
                            ResponseBody responseBody = response.body();
                            String body = responseBody.string();
                            Gson gson = new Gson();
//                            UserCreds userCredError = gson.fromJson(response.body().charStream(), UserCreds.class);
//
//                            String message = userCredError.getMessage();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                    builder.setMessage(body)
//                                            .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    // START THE GAME!
//                                                }
//                                            });
////                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
////                                        public void onClick(DialogInterface dialog, int id) {
////                                            // User cancelled the dialog
////                                        }
////                                    });
//                                    // Create the AlertDialog object and return it
//                                    builder.create();
                                }
                            });

                        }
                    }
                });
            }
        });


        return binding.getRoot();
    }
}