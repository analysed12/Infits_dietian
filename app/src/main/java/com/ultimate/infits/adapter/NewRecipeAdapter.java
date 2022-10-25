package com.ultimate.infits.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ultimate.infits.fragments.Directions;
import com.ultimate.infits.fragments.Ingredients;
import com.ultimate.infits.fragments.RecipeDetails;

public class NewRecipeAdapter extends FragmentStateAdapter {

    public NewRecipeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public NewRecipeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public NewRecipeAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==1){
            return new Ingredients();
        }else if(position==2){
            return new Directions();
        }else{
            return new RecipeDetails();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}