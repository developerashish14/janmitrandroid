package com.example.janmitr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragmentMember extends BottomSheetDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view1 =  inflater.inflate(R.layout.layout_member_bottom, container, false);


        //AutoCompleteTextView et = (AutoCompleteTextView)view1.findViewById(R.id.flag);

        return view1;
    }

}
