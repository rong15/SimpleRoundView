package com.zou.simpleroundview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zou.roundviewlibrary.CircleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleView = findViewById(R.id.circleView);
        circleView.setUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=233386756,608033436&fm=27&gp=0.jpg");

    }
}
