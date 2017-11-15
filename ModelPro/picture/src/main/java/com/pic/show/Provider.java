package com.pic.show;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaofei on 17/11/13.
 */

public class Provider {

    private Provider() {

    }

    private static Provider provider = null;

    private static ContentResolver resolver;

    public static Provider getInstance(Context context) {

        resolver = context.getContentResolver();
        if (null == provider) {
            synchronized (Provider.class) {
                if (null == provider) {
                    provider = new Provider();
                }
            }
        }
        return provider;
    }


    public List<Clay> obtainAllList() {
        List<Clay> arrayList = new ArrayList<Clay>();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.BUCKET_ID,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DATE_ADDED,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.DATE_TAKEN,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT,
                        MediaStore.Images.ImageColumns.MIME_TYPE,
                        MediaStore.Images.ImageColumns.TITLE,
                        MediaStore.Images.ImageColumns.ORIENTATION
                },
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_TAKEN
        );
        if (null != cursor && cursor.moveToNext()) {
            cursor.moveToLast();
            do {
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                if (new File(path).exists() &&  10 <= (size >> 10)) {
                    arrayList.add(new Clay(path,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.TITLE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE)),
                            size,
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT)),
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION))
                            ,0
                            ,false
                    ));
                }
            } while (cursor.moveToPrevious());
                cursor.close();
        }
        return arrayList;
    }



    public List<Clay> obtainAlbumList(){
        List<Clay> arrayList = new ArrayList<Clay>();
        Map hashMap = new HashMap<String,Clay>();

        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.ImageColumns.BUCKET_ID,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.SIZE
                },
                null,
                null,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
        );
        if (null != cursor && cursor.moveToNext()) {
            Clay pandora = new Clay(
                   "全部图片",
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                    0l,
                    true
            );
            arrayList.add(pandora);
            cursor.moveToFirst();
            do {
                pandora.count ++;
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
                if (hashMap.keySet().contains(name)) {
                    ((Clay)hashMap.get(name)).count++;
                } else {
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                    if (new File(path).exists() && 10 <= (size >> 10)) {
                        Clay album = new Clay(
                                name = name,
                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID)),
                                1,
                               path
                        );
                        hashMap.put(name, album);
                        arrayList.add(album);
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return arrayList;
    }

    public List<Clay> obtainPandoraWithAlbumName(long parentId){
        List<Clay> arrayList = new ArrayList<Clay>();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.BUCKET_ID,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DATE_ADDED,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.DATE_TAKEN,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT,
                        MediaStore.Images.ImageColumns.MIME_TYPE,
                        MediaStore.Images.ImageColumns.TITLE,
                        MediaStore.Images.ImageColumns.ORIENTATION
                },
                "${MediaStore.Images.ImageColumns.BUCKET_ID} = ?",
                new String[]{String.valueOf(parentId)},
                MediaStore.Images.ImageColumns.DATE_ADDED
        );
        if (null != cursor && cursor.moveToNext()) {
            cursor.moveToLast();
            do {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));
                if (new File(path).exists() && 10 <= (size >> 10)) {
                    arrayList.add(new Clay(
                            path,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.TITLE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE)),
                            size = size,
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT)),
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION))
                    ));
                }
            } while (cursor.moveToPrevious());
            cursor.close();
        }
        return arrayList;

    }





}
