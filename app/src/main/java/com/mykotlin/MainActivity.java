package com.mykotlin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mykotlin.ben.KotlinTest2;
import com.mykotlin.test.TestAiYi;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sendKotlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                TestAiYi.getData();
            }
        });
        ((TextView) findViewById(R.id.sendKotlin)).setText("" + new KotlinTest2().tt);

    }
}
