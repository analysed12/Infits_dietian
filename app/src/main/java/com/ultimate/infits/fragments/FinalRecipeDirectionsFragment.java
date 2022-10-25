package com.ultimate.infits.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ultimate.infits.R;
import com.ultimate.infits.adapter.DirectionAdapter;
import com.ultimate.infits.adapter.FinalDirectionAdapter;
import com.ultimate.infits.model.MyDirections;

import java.util.ArrayList;
import java.util.List;


public class FinalRecipeDirectionsFragment extends Fragment {

    RecyclerView recyclerViewFinalDirection;
    FinalDirectionAdapter finalDirectionAdapter;
    ArrayList<MyDirections> dirnList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void ids(View view) {
        recyclerViewFinalDirection=view.findViewById(R.id.recyclerViewFinalDirection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_final_recipe_directions, container, false);
        ids(view);
        recyclerViewFinalDirection.setHasFixedSize(true);
        recyclerViewFinalDirection.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        dirnList =new ArrayList<>();
        finalDirectionAdapter=new FinalDirectionAdapter(this,dirnList);

        recyclerViewFinalDirection.setAdapter(finalDirectionAdapter);

        return view;
    }
}