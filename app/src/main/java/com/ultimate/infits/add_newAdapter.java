package com.ultimate.infits;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class add_newAdapter  {

//    Context ct;
//    List<add_new> obj;
//
//    ArrayList<String> nameArr = new ArrayList<>();
//    ArrayList<String> amountArr = new ArrayList<>();
//    ArrayList<String> unitArr = new ArrayList<>();
//
//    public add_newAdapter(Context ct, List<add_new> obj) {
//        this.ct = ct;
//        this.obj = obj;
//    }

//    @NonNull
//    @Override
//    public add_newHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(ct);
//        View view = layoutInflater.inflate(R.layout.add_new2, parent, false);
//        return new add_newHolder(view).linkAdpter(this);
//    }

//    @Override
//    public void onBindViewHolder(@NonNull add_newHolder holder, int position) {
//        add_new pos = obj.get(position);
//
////            obj.get(position).name.equals("ingredient");
//        holder.name.setHint(pos.getName());
//        holder.units.setHint(pos.getQuantity());
//        holder.amount.setHint(pos.getAmount());
//        nameArr.add("");
//        unitArr.add("");
//        amountArr.add("");
//        holder.name.setText(nameArr.get(position));
//        holder.amount.setText(amountArr.get(position));
//        holder.units.setText(unitArr.get(position));
//
//        holder.name.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                FoodDetails2.ingName.set(position, s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //blank
//            }
//        });
//        holder.units.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                FoodDetails2.ingUnits.set(position,s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //blank
//            }
//        });
//        holder.amount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                FoodDetails2.ingAmount.set(position,s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //blank
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return obj.size();
//    }

    public class add_newHolder extends RecyclerView.ViewHolder {
        EditText name, units, amount;
        ImageView removeItem;
        add_newAdapter add;

        public add_newHolder(View view) {
            super(view);
            name = view.findViewById(R.id.food_name);
            units = view.findViewById(R.id.units);
            amount = view.findViewById(R.id.food_amount);
            removeItem = view.findViewById(R.id.remove_item);
            removeItem.setOnClickListener(v -> {
//                FoodDetails2.ingName.remove(getAbsoluteAdapterPosition());
//                FoodDetails2.ingUnits.remove(getAbsoluteAdapterPosition());
//                FoodDetails2.ingAmount.remove(getAbsoluteAdapterPosition());
//                add.obj.remove(getAbsoluteAdapterPosition());
//                add.notifyItemRemoved(getAbsoluteAdapterPosition());
            });
        }

        public add_newHolder linkAdpter(add_newAdapter add) {
            this.add = add;
            return this;
        }
    }
}