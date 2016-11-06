package com.example.kevinpluck.agecategorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesSelectorActivity extends AppCompatActivity {

    public ArrayList<Integer> ageCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        ageCategories = i.getIntegerArrayListExtra("categories");

        GridView gridview = (GridView) findViewById(R.id.categoriesSelectorGridView);
        gridview.setAdapter(new CategoriesAdapter(this, ageCategories));
    }



    public void onOkClick(View v){
        Intent intent=new Intent();
        intent.putExtra("categories", ageCategories);
        setResult(RESULT_OK ,intent);
        finish();

    }
}
