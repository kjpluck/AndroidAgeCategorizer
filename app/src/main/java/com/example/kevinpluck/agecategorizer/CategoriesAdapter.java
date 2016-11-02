package com.example.kevinpluck.agecategorizer;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import java.lang.reflect.Array;

/**
 * Created by Kevin Pluck on 02-Nov-16.
 */
public class CategoriesAdapter extends BaseAdapter {
    private static final int maxAge = 100;
    private final boolean[] categories;
    private final CategoriesSelectorActivity context;
    public CategoriesAdapter(CategoriesSelectorActivity categoriesSelectorActivity, int[] foo) {

        categories = new boolean[maxAge];
        for (int aFoo : foo) {
            categories[aFoo - 1] = true;
        }
        context = categoriesSelectorActivity;
    }

    @Override
    public int getCount() {
        return maxAge;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button button;
        if(view == null)
            button = new Button(context);
        else
            button = (Button) view;

        button.setText("<" + (i + 1));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categories[i] = !categories[i];
                CategoriesAdapter.this.notifyDataSetChanged();
            }
        });

        if(categories[i])
            button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        else
            button.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);


        return button;
    }

}
