package com.ultimate.infits;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ultimate.infits.databinding.FragmentDietBreakfastBinding;
import com.ultimate.infits.model.DietChartModal;

import java.util.ArrayList;

public class DietBreakfastFragment extends Fragment {
    FragmentDietBreakfastBinding binding;
    DietChartAdapter adapter;
    ArrayList<DietChartModal> list;
    public DietBreakfastFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDietBreakfastBinding.inflate(getLayoutInflater());
        list=new ArrayList<>();
        adapter=new DietChartAdapter(list,getContext());
        binding.dietRecycle.setHasFixedSize(true);
        binding.AfterRecycle.setHasFixedSize(true);
        LinearLayoutManager linearlayout=new LinearLayoutManager(getContext());
        linearlayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.dietRecycle.setLayoutManager(linearlayout);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.AfterRecycle.setLayoutManager(linearLayoutManager);
        binding.dietRecycle.setAdapter(adapter);
        binding.AfterRecycle.setAdapter(adapter);
        list.add(new DietChartModal(R.drawable.plus));
        list.add(new DietChartModal(R.drawable.plus));
        list.add(new DietChartModal(R.drawable.plus));
        list.add(new DietChartModal(R.drawable.plus));






        return binding.getRoot();
    }
}