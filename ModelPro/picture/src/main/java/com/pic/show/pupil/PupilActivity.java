package com.pic.show.pupil;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pic.show.Clay;
import com.pic.show.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wanghaofei on 17/11/14.
 */

public class PupilActivity extends FragmentActivity {


    private ImageView backView;
    private TextView titleView;
    private ViewPager viewPager;
    List<Clay> arrayList;
    List<Fragment> fragmentList = new ArrayList<>();
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_pandora_activity_pupil);

        Bundle bundle = getIntent().getExtras();

        position = bundle.getInt("position");
        arrayList = (List<Clay>) bundle.getSerializable("arrayList");

        if (arrayList == null) {
            //没有传递进来图片信息,退出页面
            finish();
            return;
        }

        for (int i = 0; i < arrayList.size(); i++) {

            Clay clay = arrayList.get(i);
            PupilFragment pupilFragment = new PupilFragment();
            Bundle bundleOne = new Bundle();
            bundleOne.putString("url",clay.data);
            pupilFragment.setArguments(bundleOne);

            fragmentList.add(pupilFragment);
        }

        initView();
    }


    private void initView() {
        backView = (ImageView) this.findViewById(R.id.lib_pandora_tool_bar_navigation_button);
        titleView = (TextView) this.findViewById(R.id.lib_pandora_tool_bar_title_view);
        viewPager = (ViewPager) this.findViewById(R.id.lib_pandora_pupil_view_pager);

        viewPager.setAdapter(new PupilViewPagerAdapter(getSupportFragmentManager(), PupilActivity.this, fragmentList));

        int curp = position > arrayList.size() ? 0 : position;
        viewPager.setCurrentItem(curp);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                String titleName = String.format(Locale.CHINA, getResources().getString(R.string.lib_pandora_pupil_indicator), position + 1, arrayList.size());
                titleView.setText(titleName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (position >= arrayList.size()) {
            titleView.setText(String.format(Locale.CHINA, getResources().getString(R.string.lib_pandora_pupil_indicator), 1, arrayList.size()));
        } else {
            titleView.setText(String.format(Locale.CHINA, getResources().getString(R.string.lib_pandora_pupil_indicator), position + 1, arrayList.size()));
        }


    }


}
