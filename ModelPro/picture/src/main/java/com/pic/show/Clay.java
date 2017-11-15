package com.pic.show;

import java.io.Serializable;

/**
 * Created by wanghaofei on 17/11/13.
 */

public class Clay implements Serializable{
    /*文件路径*/
    public String data;
    /*文件 id*/
    public long id = 0l;
    /*文件所在组 id*/
    public long parentId = 0l;
    /*文件名*/
    public String name;
    /*文件所在组名*/
    public String parentName;
    /*文件加入时间戳*/
    public long addDate = 0l;
    /*文件修改时间戳*/
    public long modifyDate = 0l;
    /*图片文件生成时间戳*/
    public long takenDate = 0l;
    /*文件标题*/
    public String title;
    /*文件类型*/
    public String mimeType;
    /*文件大小*/
    public long size = 0l;
    /*图片文件宽度*/
    public int width = 0;
    /*图片文件高度*/
    public int height = 0;
    /*图片文件方向*/
    public int orientation = 0;
    /*作为Album时文件个数*/
    public int count = 0;
    /*文件是否被选中*/
    public boolean check = false;

    public Clay(String name,long id,int count,String data) {
        this.name = name;
        this.data = data;
        this.id = id;
        this.count = count;
    }

    public Clay(String name, String data, Long id, boolean check) {
        this.name = name;
        this.data = data;
        this.id = id;
        this.check = check;
    }


    public Clay(String data, long id, long parentId, String name, String parentName, long addDate, long modifyDate, long takenDate, String title, String mimeType, long size, int width, int height, int orientation) {
        this.data = data;
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.parentName = parentName;
        this.addDate = addDate;
        this.modifyDate = modifyDate;
        this.takenDate = takenDate;
        this.title = title;
        this.mimeType = mimeType;
        this.size = size;
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    public Clay(String data, Long id, Long parentId, String name, String parentName, Long addDate, Long modifyDate, Long takenDate, String title, String mimeType, Long size, int width, int height, int orientation, int count, boolean check) {
        this.data = data;
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.parentName = parentName;
        this.addDate = addDate;
        this.modifyDate = modifyDate;
        this.takenDate = takenDate;
        this.title = title;
        this.mimeType = mimeType;
        this.size = size;
        this.width = width;
        this.height = height;
        this.orientation = orientation;
        this.count = count;
        this.check = check;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getAddDate() {
        return addDate;
    }

    public void setAddDate(Long addDate) {
        this.addDate = addDate;
    }

    public Long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Long takenDate) {
        this.takenDate = takenDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Clay{" +
                "data='" + data + '\'' +
                ", id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", addDate=" + addDate +
                ", modifyDate=" + modifyDate +
                ", takenDate=" + takenDate +
                ", title='" + title + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", size=" + size +
                ", width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                ", count=" + count +
                ", check=" + check +
                '}';
    }
}
