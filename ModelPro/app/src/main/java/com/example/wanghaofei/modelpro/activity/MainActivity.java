package com.example.wanghaofei.modelpro.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanghaofei.modelpro.R;
import com.pic.show.OnHookListener;
import com.pic.show.Pandora;

import java.util.List;

/**
 * 测试
 */
public class MainActivity extends AppCompatActivity {


    private TextView addressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressView = (TextView) this.findViewById(R.id.address_val);

    }


    public void jumPhoto(View view) {

        Pandora.open(this, 9, new OnHookListener() {
            @Override
            public void onHook(List<String> clayList) {

                StringBuffer stringBuffer = new StringBuffer();

                for (String ss : clayList) {
                    stringBuffer.append(ss + "/n");
                }

                addressView.setText(Html.fromHtml(stringBuffer.toString()));

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onCancel() {

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
