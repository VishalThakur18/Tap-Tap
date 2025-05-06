package com.app.tap.tap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import java.util.List;
import com.app.tap.tap.classes.RetrofitAPI;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;


    private com.app.tap.tap.classes.RetrofitAPI RetrofitAPI;

    String cok;

    String idToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI = retrofit.create(RetrofitAPI.class);




        showingData();




    }
    private void showingData() {

        if (user != null) {


            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        idToken = task.getResult().getToken();
                        LoginonNodeapp(idToken);

                    } else {
                    }
                }
            });



        } else {

            Intent n = new Intent(SplashScreen.this, IntroActivity.class);
            startActivity(n);
            finish();

        }
    }


    private void LoginonNodeapp(String idToken) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{ \"idToken\" : \"" + idToken + "\"}");

                Request request = new Request.Builder()
                        .url(RetrofitAPI.BASE_URL + "auth/login")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Cookie", "session=" + idToken)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (response.code() == 200) {
                        List<String> sessiosMap = response.headers("Set-Cookie");
                        String cok = sessiosMap.get(0).split(";")[0];

                        if (SplashScreen.this != null) {
                            SharedPreferences sharedPreferences = getSharedPreferences("cookie", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("cookie", cok);
                            myEdit.commit();

                            SharedPreferences prefs1 = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                            prefs1.edit().clear().apply(); // Clear all stored values


                        }


                         

                            Intent n = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(n);
                            finish();


                    } else {
//                        errordialog("Something went wrong. Please try again later.");
                    }
                } catch (IOException e) {
//                    errordialog(e.getMessage());
                }
            }
        });
        thread.start();
    }
}