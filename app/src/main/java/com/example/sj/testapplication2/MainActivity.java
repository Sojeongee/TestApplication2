package com.example.sj.testapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton searchBtn;
    private EditText inputKeyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        inputKeyword = (EditText) findViewById(R.id.search);
        searchBtn = (ImageButton) findViewById(R.id.magnifierbtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // continueApp();
                Intent i=new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("name",inputKeyword.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }
}


