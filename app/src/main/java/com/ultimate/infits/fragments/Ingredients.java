package com.ultimate.infits.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ultimate.infits.R;
import com.ultimate.infits.adapter.IngredientAdapter;
import com.ultimate.infits.interfaces.FragmentCallbacks;
import com.ultimate.infits.model.MyIngredients;

import java.util.ArrayList;


public class Ingredients extends Fragment {

    RecyclerView recyclerView;
    LinearLayout ll_addingIngred;
    TextView nextBtn;

    ArrayList<MyIngredients> myIngreds;
    private FragmentCallbacks callBack;
    IngredientAdapter ingredientAdapter;

     String iName;
     String iQuant;
    public static ArrayList<String> allIngredsName=new ArrayList<>();
    public static ArrayList<String> allIngredsQuant=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ingredients, container, false);
        ids(view);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage();
            }
        });

        ll_addingIngred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.add_ingred_dialog);
                dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.alert_bg));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.findViewById(R.id.nextBtn_Ingred).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText ingredNames =dialog.findViewById(R.id.ingredName);
                        EditText ingredQuants =dialog.findViewById(R.id.ingredQuantity);
                        iName =ingredNames.getText().toString();
                        allIngredsName.add(iName);
                        iQuant =ingredQuants.getText().toString();
                        allIngredsQuant.add(iQuant);
                        if(iName.equals("")){
                            Toast.makeText(getActivity(),"Please enter ingredient's name",Toast.LENGTH_LONG).show();
                            return;
                        }else if(iQuant.equals("")){
                            Toast.makeText(getActivity(),"Please enter ingredient's quantity",Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            passData(iName,iQuant);
                        }
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        myIngreds = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBack = (FragmentCallbacks) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void nextPage() {
        if (callBack != null) {
            callBack.
                    goTo(2);
        }
    }

    private void passData(String name,String quant) {
//        ll_addingIngred.setVisibility(View.INVISIBLE);
        MyIngredients obj =new MyIngredients();
        obj.setItemName(name);
        obj.setItemQuant(quant);
        myIngreds.add(obj);
        ingredientAdapter =new IngredientAdapter(this.getActivity(),myIngreds);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(ingredientAdapter);
    }

    private void ids(View view) {
        ll_addingIngred =view.findViewById(R.id.ll_addingIngred);
        recyclerView =view.findViewById(R.id.recycleIngred);
        nextBtn=view.findViewById(R.id.nextBtn);
    }

}