package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView titel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        titel = (TextView) findViewById(R.id.tv_detaitActivity_Title) ;
        String mtitle = getIntent().getStringExtra(String.valueOf(R.string.INFO_TITLE));
        Intent intent = getIntent();
    }
}
