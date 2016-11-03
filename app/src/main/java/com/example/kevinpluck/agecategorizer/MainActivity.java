package com.example.kevinpluck.agecategorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout yearButtonLayout;
    private LinearLayout monthButtonLayout;
    private LinearLayout dayButtonLayout;
    private ArrayList<Integer> ageCategoriesArray = new ArrayList<Integer>(Arrays.asList(9,11,14,16,20));
    private TextView categoriesTextView;
    private TextView categoryOutputTextView;
    private Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        yearButtonLayout = (LinearLayout) findViewById(R.id.yearButtonLayout);
        monthButtonLayout = (LinearLayout) findViewById(R.id.monthButtonLayout);
        dayButtonLayout = (LinearLayout) findViewById(R.id.dayButtonLayout);

        categoriesTextView = (TextView) findViewById(R.id.categoriesTextView);
        categoryOutputTextView = (TextView) findViewById(R.id.categoryOutputTextView);

        date = Calendar.getInstance();
        generateButtons();
    }

    private String joinAgeArray(){
        String result = "";
        for(int age: ageCategoriesArray){
            result += "<" + age + " ";
        }

        return result;
    }

    private void generateButtons() {

        categoriesTextView.setText(joinAgeArray());

        yearButtonLayout.removeAllViews();


        int theYear = date.get(Calendar.YEAR);
        int numberOfCategories = ageCategoriesArray.size();

        if(numberOfCategories <= 1) return;

        int oldestCategory = ageCategoriesArray.get(numberOfCategories - 1);
        int yearForButton = theYear - oldestCategory;

        addDefiniteYearButton("-" + (yearForButton-1),numberOfCategories);
        addYearButton(String.valueOf(yearForButton), numberOfCategories - 1);

        String years = "";
        for(int i = numberOfCategories - 2; i >= 0; i--){

            while(yearForButton <= (theYear - ageCategoriesArray.get(i) - 1)) {

                if (i >= 0 && yearForButton == (theYear - ageCategoriesArray.get(i) - 1)) {
                    addDefiniteYearButton(years, i + 1);
                    years = "";
                }

                yearForButton++;
                years += yearForButton + "\n";
            }
            addYearButton(years, i);
            years="";
        }
        addDefiniteYearButton((yearForButton+1) + "+",0);
    }

    private void addYearButton(String years, final int categoryIndex) {
        Button yearButton = new Button(this);
        yearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearDay();
                showMonth(categoryIndex);
            }
        });
        yearButton.setText(years.trim());
        yearButtonLayout.addView(yearButton);
    }

    private void showMonth(final int categoryIndex) {
        categoryOutputTextView.setText("--");
        monthButtonLayout.removeAllViews();

        int theMonth = date.get(Calendar.MONTH) + 1;
        MakeMinMonthButton(categoryIndex, theMonth);
        MakeShowDayButton(categoryIndex, theMonth);
        MakeMaxMonthButton(categoryIndex, theMonth);
    }

    private void MakeMaxMonthButton(final int categoryIndex, int theMonth) {
        monthButtonLayout.addView(makeMaxButton(new View.OnClickListener(){
            public void onClick(View v){
                clearDay();
                showCategory(categoryIndex);
            }
        }, theMonth, 12));
    }

    private View makeMaxButton(View.OnClickListener onClick, int value, int maxValue) {
        if(maxValue==31)
            maxValue = date.getActualMaximum(Calendar.DAY_OF_MONTH);

        if(value == maxValue)
            return makeButton(noClick(), "");

        if(value == maxValue-1)
            return makeButton(onClick, String.valueOf(maxValue));

        return makeButton(onClick, (value + 1) + " - " +maxValue);
    }

    private void MakeShowDayButton(final int categoryIndex, int theMonth) {
        monthButtonLayout.addView(makeButton(new View.OnClickListener(){
            public void onClick(View view) {
                showDay(categoryIndex);
            }
        }, String.valueOf(theMonth)));
    }

    private void showDay(int categoryIndex) {
        categoryOutputTextView.setText("--");
        dayButtonLayout.removeAllViews();

        int theDay = date.get(Calendar.DAY_OF_MONTH);

        MakeMinDayButton(categoryIndex, theDay);
        MakeDayButton(categoryIndex, theDay);
        MakeMaxDayButton(categoryIndex, theDay);
    }

    private void MakeMaxDayButton(final int categoryIndex, int theDay) {
        dayButtonLayout.addView(makeMaxButton(new View.OnClickListener(){
            public void onClick(View v){
                showCategory(categoryIndex);
            }
        }, theDay, 31));
    }

    private void MakeDayButton(final int categoryIndex, int theDay) {
        dayButtonLayout.addView(makeButton(new View.OnClickListener(){
            public void onClick(View v){
                showCategory(categoryIndex + 1);
            }
        }, String.valueOf(theDay)));
    }

    private void MakeMinDayButton(final int categoryIndex, int theDay) {
        dayButtonLayout.addView(makeMinButton(new View.OnClickListener(){
            public void onClick(View v){
                showCategory(categoryIndex + 1);
            }
        },theDay));
    }

    private void MakeMinMonthButton(final int categoryIndex, int theMonth) {
        monthButtonLayout.addView(makeMinButton(new View.OnClickListener(){
            public void onClick(View v){
                clearDay();
                showCategory(categoryIndex + 1);
            }
        },theMonth));
    }

    private View makeMinButton(View.OnClickListener onClickListener, int value) {
        if(value == 1)
            return makeButton(noClick(), "");

        if(value == 2)
            return makeButton(onClickListener, "1");

        return makeButton(onClickListener, "1 - " + String.valueOf(value - 1));
    }

    @NonNull
    private View.OnClickListener noClick() {
        return new View.OnClickListener(){public void onClick(View v){}};
    }

    @NonNull
    private View makeButton(View.OnClickListener onClickListener, String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(onClickListener);
        if(text == "") button.setVisibility(View.INVISIBLE);
        return button;
    }

    private void addDefiniteYearButton(String years, final int categoryIndex) {
        Button yearButton = new Button(this);
        yearButton.setText(years.trim());
        yearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearMonthAndDay();
                showCategory(categoryIndex);
            }
        });
        yearButtonLayout.addView(yearButton);
    }

    private void showCategory(int categoryIndex) {
        String result;
        if(categoryIndex >= ageCategoriesArray.size())
            result = ageCategoriesArray.get(categoryIndex - 1) + " +";
        else
            result = "<" + ageCategoriesArray.get(categoryIndex);

        categoryOutputTextView.setText(result);
    }

    private void clearMonthAndDay() {
        monthButtonLayout.removeAllViews();
        clearDay();
    }

    private void clearDay() {
        dayButtonLayout.removeAllViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showCategoriesSelector(View v){
        Intent i = new Intent(getApplicationContext(), CategoriesSelectorActivity.class);
        i.putExtra("categories", ageCategoriesArray);
        startActivityForResult(i, 666);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        if(resultCode == RESULT_OK){
            Bundle result = Data.getExtras();
            ageCategoriesArray = result.getIntegerArrayList("categories");
            generateButtons();
        }
    }
}
