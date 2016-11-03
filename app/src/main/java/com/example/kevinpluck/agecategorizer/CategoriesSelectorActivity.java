package com.example.kevinpluck.agecategorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesSelectorActivity extends AppCompatActivity {

    public ArrayList<Integer> foo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        foo = i.getIntegerArrayListExtra("categories");

        GridView gridview = (GridView) findViewById(R.id.categoriesSelectorGridView);
        gridview.setAdapter(new CategoriesAdapter(this, foo));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CategoriesSelectorActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onOkClick(View v){
        Intent intent=new Intent();
        intent.putExtra("categories",foo);
        setResult(RESULT_OK ,intent);
        finish();

    }
}
