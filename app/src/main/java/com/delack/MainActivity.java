package com.delack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.delack.Authentication.AuthenticationView;
import com.delack.Network.NetworkCalls;

public class MainActivity extends AppCompatActivity {
    ImageButton signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = (ImageButton) findViewById(R.id.singIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AuthenticationView.class);
                startActivity(i);
//                NetworkCalls.slackLogin(MainActivity.this);
            }
        });
    }
}
