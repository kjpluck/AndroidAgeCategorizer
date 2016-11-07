package com.kevpluck.agecategorizer;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Kevin Pluck on 02-Nov-16.
 */
public class CategoriesAdapter extends BaseAdapter {
    private static final int maxAge = 100;
    private final boolean[] ageCategoryFlags;
    private final CategoriesSelectorActivity context;

    public CategoriesAdapter(CategoriesSelectorActivity categoriesSelectorActivity, ArrayList<Integer> ageCategories) {

        ageCategoryFlags = parseAgeCategories(ageCategories);
        context = categoriesSelectorActivity;
        enableOkButton(ageCategories);
    }

    private boolean[] parseAgeCategories(ArrayList<Integer> ageCategories) {
        boolean[] ageCategoryFlags;
        ageCategoryFlags = new boolean[maxAge];
        for (int ageCategory : ageCategories) {
            ageCategoryFlags[ageCategory - 1] = true;
        }
        return ageCategoryFlags;
    }

    private void updateAgeCategories()
    {
        ArrayList<Integer> ages = new ArrayList<>();
        for (int i = 0; i < maxAge; i++) {
            if(ageCategoryFlags[i])
                ages.add(i+1);
        }

        enableOkButton(ages);

        context.ageCategories = ages;
    }

    private void enableOkButton(ArrayList<Integer> ages) {
        Button okButton = (Button) context.findViewById(R.id.okButton);
        okButton.setEnabled(ages.size() >= 2);
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
    public View getView(final int ageCategoryIndex, View view, ViewGroup viewGroup) {
        Button button;
        if(view == null)
            button = new Button(context);
        else
            button = (Button) view;

        button.setText("<" + (ageCategoryIndex + 1));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCategory(ageCategoryIndex);
            }
        });

        if(ageCategoryFlags[ageCategoryIndex])
            button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        else
            button.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);


        return button;
    }

    private void selectCategory(int ageCategoryIndex) {
        ageCategoryFlags[ageCategoryIndex] = !ageCategoryFlags[ageCategoryIndex];
        updateAgeCategories();
        CategoriesAdapter.this.notifyDataSetChanged();
    }

}
