package com.ultimate.infits.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ultimate.infits.fragments.Directions;
import com.ultimate.infits.fragments.FinalRecipeDirectionsFragment;
import com.ultimate.infits.fragments.FinalRecipeIngredientsFragment;
import com.ultimate.infits.fragments.Ingredients;
import com.ultimate.infits.fragments.RecipeDetails;

public class FinalRecipeAdapter  extends FragmentStateAdapter {

    public FinalRecipeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FinalRecipeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public FinalRecipeAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==1){
            return new FinalRecipeDirectionsFragment();
        }else{
            return new FinalRecipeIngredientsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }



}
