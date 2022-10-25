package com.ultimate.infits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingMain extends Fragment {

    TextView name;
    DataFromDatabase dataFromDatabase;
    ImageView profilePic;
    CardView gotoAccount,achievements,notifications,resetpwd,aboutUs,help;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingMain() {
        // Required empty public constructor
    }

    public static SettingMain newInstance(String param1, String param2) {
        SettingMain fragment = new SettingMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);
        gotoAccount = view.findViewById(R.id.dieitician_gotoAccount);
        notifications = view.findViewById(R.id.notification);
        resetpwd=view.findViewById(R.id.resetpassword);
        profilePic=view.findViewById(R.id.settings_dietician_profile_icon);
        profilePic.setImageBitmap(dataFromDatabase.profile);
        name = view.findViewById(R.id.settings_dietician_name);
        name.setText(dataFromDatabase.name);
        gotoAccount.setOnClickListener(v->{
//            FragmentTransaction ftset1= getActivity().getSupportFragmentManager().beginTransaction();
//            ftset1.replace(R.id.FrameContainer,new BlankFragment());
//            ftset1.add(R.id.FrameContainer,new Account());
//            ftset1.addToBackStack("settings_account");
//            ftset1.commit();
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,new Account()).commit();
            Navigation.findNavController(v).navigate(R.id.action_settingMain_to_account);
        });
        achievements=view.findViewById(R.id.dieitician_achievements_settings_view);
        achievements.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_settingMain_to_achievement_card);
       });
        notifications.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_settingMain_to_notification);
       });
        resetpwd.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_settingMain_to_passwordReset);
        });

        return view;
    }
}