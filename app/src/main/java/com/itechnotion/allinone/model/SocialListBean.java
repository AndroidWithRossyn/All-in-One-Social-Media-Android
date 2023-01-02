package com.itechnotion.allinone.model;

/**
 * Created by Viral on 4/29/2018.
 */

public class SocialListBean {
    String name;
    int image;
    int iconimg;
    boolean isShow;
    public SocialListBean(String name, int image, boolean isShow,int iconimg) {
        this.name = name;
        this.image = image;
        this.iconimg=iconimg;
        this.isShow = isShow;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getIconimg() {
        return iconimg;
    }

    public void setIconimg(int iconimg) {
        this.iconimg = iconimg;
    }

    @Override
    public String toString() {
        return "SocialListBean{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", image=" + iconimg +
                ", isShow=" + isShow +
                '}';
    }
}
