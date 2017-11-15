package com.pic.show.pupil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pic.show.Clay;
import com.pic.show.R;

import java.util.List;

/**
 * Created by wanghaofei on 17/11/15.
 */

public class PupilFragment extends Fragment {


//    private static PupilFragment pupilFragment;

    ImageView pupilView;

//    public static Fragment getInstance() {
//        if (pupilFragment == null) {
//            pupilFragment = new PupilFragment();
//        }
//        return pupilFragment;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.lib_pandora_fragment_pupil, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        pupilView = (ImageView) view.findViewById(R.id.lib_pandora_fragment_pupil_image_view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString("url");

        Glide.with(getActivity()).load(url).apply(new RequestOptions().fitCenter().placeholder(R.color.lib_pandora_817F7F)).thumbnail(0.1f).into(pupilView);

    }
}
