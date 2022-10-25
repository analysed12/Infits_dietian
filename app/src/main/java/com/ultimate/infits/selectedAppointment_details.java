package com.ultimate.infits;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link selectedAppointment_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class selectedAppointment_details extends Fragment {

    TextView selt_time,selt_loc,selt_title,selt_link,selt_name;
    ImageView selt_img;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public selectedAppointment_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment selectedAppointment_details.
     */
    // TODO: Rename and change types and number of parameters
    public static selectedAppointment_details newInstance(String param1, String param2) {
        selectedAppointment_details fragment = new selectedAppointment_details();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_selected_appointment_details, container, false);
        selt_time=v.findViewById(R.id.selected_apt_time);
        selt_loc=v.findViewById(R.id.selected_apt_loc);
        selt_title=v.findViewById(R.id.selected_apt_title);
        selt_link=v.findViewById(R.id.selected_apt_link);
        selt_name=v.findViewById(R.id.selected_apt_name);
        selt_img=v.findViewById(R.id.selected_apt_img);

//       String time111=getArguments().getString("appointment_time")+"(Duration: "+getArguments().getString("appointment_duration")+")";
//        selt_time.setText(time111);
//        selt_loc.setText(getArguments().getString("appointment_location"));
//        selt_title.setText(getArguments().getString("appointment_title"));
//        selt_link.setText(getArguments().getString("appointment_link"));
//        selt_name.setText(getArguments().getString("appointment_client_name"));
        return v;
    }
}