package com.pic.show;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pic.show.compress.Luban;
import com.pic.show.compress.OnCompressListener;
import com.pic.show.display.ClayAdapter;
import com.pic.show.display.ClaysAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by wanghaofei on 17/11/14.
 */

public class Pandora extends AppCompatActivity implements ClayAdapter.BlockInterface, ClaysAdapter.SingleBlock {


    private int _requestCodeShot = 1000;
    private int _requestCodePermissionCamera = 2000;
    private int _requestCodePermissionReadExternalStorage = 3000;
    private List<Clay> clayList = new ArrayList<Clay>();
    private String _timeFormat = "yyyyMMdd_HHmmss";

    private long currentId = 0l;

    private ClayAdapter clayAdapter;
    private ClaysAdapter claysAdapter;
    /**
     * 原图路径
     */
    private String originalPath;
    /**
     * 压缩图路径
     */
    private String compressedPath;

    private String MaxCountKey = "count";

    public static OnHookListener onHookListener;

    public static int maxCount;

    //返回按钮
    private ImageView backView;
    //拍照按钮
    private ImageView cameraView;
    //确定按钮
    private TextView sureView;

    //标题
    private TextView titleContent;


    RecyclerView recyclerViewOne;

    RecyclerView recyclerViewTwo;

    FrameLayout barLayout;

    LinearLayout rTwoLayout;

    GridLayoutManager layoutManager;

    public static void open(Context context, int maxCounts, OnHookListener onHookListeners) {
        maxCount = maxCounts;
        onHookListener = onHookListeners;
        context.startActivity(new Intent(context, Pandora.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_pandora_activity_pandora);

        if(isGrantExternalRW(this)){
            initView();
        }else {
            finish();
        }

    }


    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }


    private void initView() {
        backView = (ImageView) this.findViewById(R.id.lib_pandora_activity_pandora_navigation_icon_view);
        cameraView = (ImageView) this.findViewById(R.id.lib_pandora_activity_pandora_shot_button_view);
        titleContent = (TextView) this.findViewById(R.id.lib_pandora_activity_pandora_album_button);
        sureView = (TextView) this.findViewById(R.id.lib_pandora_activity_pandora_sure_button_view);
        recyclerViewOne = (RecyclerView) this.findViewById(R.id.lib_pandora_activity_pandora_clay_recycler_view);
        recyclerViewTwo = (RecyclerView) this.findViewById(R.id.lib_pandora_activity_pandora_clays_recycler_view);
        barLayout = (FrameLayout) this.findViewById(R.id.lib_pandora_activity_progress_bar);
        rTwoLayout = (LinearLayout) this.findViewById(R.id.lib_pandora_activity_pandora_extra);

        layoutManager = new GridLayoutManager(this, PandoraConfig.countInLine);

        clayAdapter = new ClayAdapter(this,maxCount);
        claysAdapter = new ClaysAdapter(this);
        clayAdapter.setBlockInterface(this);

        recyclerViewOne.setLayoutManager(layoutManager);
        recyclerViewOne.setAdapter(clayAdapter);

        scanClayData();
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
                    Toast.makeText(Pandora.this, R.string.lib_pandora_check_camera, Toast.LENGTH_SHORT).show();
                }
                initShotPath();

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(Pandora.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Pandora.this, Manifest.permission.CAMERA)) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, _requestCodePermissionCamera);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, _requestCodePermissionCamera);
                        }
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }

            }
        });


        sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> temList = new ArrayList<>();

                if(clayList.size() > 0){
                    for (Clay clay : clayList){
                        temList.add(clay.data);
                    }
                    onHookListener.onHook(temList);
                }
                finish();
            }
        });

    }


    private void openCamera() {
        File file = new File(originalPath);

        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, PandoraConfig.hostPackageName + ".FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, _requestCodeShot);
    }


    /**
     * 初始化拍照路径
     */
    private void initShotPath() {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            long var2 = System.currentTimeMillis();
            String timeTip = (new SimpleDateFormat(this._timeFormat, Locale.CHINA)).format(Long.valueOf(var2));

            File parentFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + '/' + PandoraConfig.hostSubName);

            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            this.originalPath = "" + parentFile + "/IMG_" + timeTip + ".jpg";
            compressedPath = getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
        } else {
            Toast.makeText(this, R.string.lib_pandora_check_external_storage, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (_requestCodeShot == requestCode) {
            if (Activity.RESULT_OK == resultCode) {

                Luban.with(this)
                        .load(originalPath)
                        .setTargetDir(compressedPath)
                        .setCompressListener(new OnCompressListener(){

                            @Override
                            public void onStart() {
                                barLayout.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onSuccess(File file) {
                                barLayout.setVisibility(View.GONE);
                                List<String> dataList = new ArrayList<>();
                                dataList.add(file.getAbsolutePath());
                                onHookListener.onHook(dataList);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {
                                barLayout.setVisibility(View.GONE);
                                onHookListener.onError(e);
                                finish();
                            }
                        }).launch();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            if (_requestCodePermissionCamera == requestCode) {
                openCamera();
            } else if (_requestCodePermissionReadExternalStorage == requestCode) {
                scanClayData();
                scanClaysData();
            }
        } else {
            if (_requestCodePermissionCamera == requestCode) {

            } else if (_requestCodePermissionReadExternalStorage == requestCode) {
                finish();
            }
        }
    }

    //    onRequestPermissionsResult

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        release();
    }


    private void scanClaysData() {
        List<Clay> datas = Provider.getInstance(this).obtainAlbumList();
//        claysAdapter.addData(datas);
    }

    private void scanClayData() {

        List<Clay> datas = Provider.getInstance(this).obtainAllList();
        clayAdapter.addData(datas);

    }


    @Override
    public void removeBlock(Clay clay) {

        if(clayList.size()>0){
            clayList.remove(clay);
        }

    }

    @Override
    public void addBlock(Clay clay) {
        clayList.add(clay);
    }

    @Override
    public void countBlock(Clay clay) {
        if(0 == clayList.size()){
           sureView.setVisibility(View.GONE);
        }else {
            sureView.setVisibility(View.VISIBLE);
            sureView.setText(String.format(Locale.CHINA, getResources().getString(R.string.lib_pandora_sure), clayList.size(), maxCount));
        }
    }

    @Override
    public void block(Clay clay) {

    }


    private void release() {
        if (null != rTwoLayout && rTwoLayout.isShown()) {
            rTwoLayout.setVisibility(View.GONE);
        } else {
            if (null != barLayout && !barLayout.isShown()) {
                onHookListener.onCancel();
                super.onBackPressed();
            }
        }
    }

}
