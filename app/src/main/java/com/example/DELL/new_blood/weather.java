package com.example.DELL.new_blood;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sikandar on 4/18/2017.
 */

//public class weather {
//    public String icon;
//    public String title;
//    public weather(){
//        super();
//    }
//
//    public weather(String name, String pic) {
//        super();
//        this.icon = pic;
//        this.title =name;
//    }
//
//    public String get_name(){
//        return this.title;
//    }
//
//    public String get_pic(){
//        return this.icon;
//    }
//}

/*
public class weather{
    String name;
    int price;
    String pic;
    boolean box;


    weather(String _describe, int _price, String _image, boolean _box) {
        name = _describe;
        price = _price;
        this.pic = _image;
        box = _box;
    }

    public String get_name(){
        return  this.name;
    }

    public String get_pic(){
        return  this.pic;
    }
}*/

public class weather implements Parcelable {

    String name;
  String msg;
  String img;
  String address;
  String ID;
  String date_time;


    weather(String name, String msg, String img, String address, String ID,String date_time) {
        this.name = name;
        this.date_time = date_time;
        this.msg = msg;
        this.img = img;
        this.address = address;
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
    }

public String getName(){
        return this.name;

}
public String getMsg(){
    return this.msg;
}
public String getImg(){
    return this.img;
}
public String getAddress(){
    return this.address;
}
public String getDate_time(){
    return this.date_time;
}

    protected weather(Parcel in) {
        name = in.readString();
        msg = in.readString();
        img = in.readString();
        address = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(msg);
        dest.writeString(img);
        dest.writeString((address));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<weather> CREATOR = new Parcelable.Creator<weather>() {
        @Override
        public weather createFromParcel(Parcel in) {
            return new weather(in);
        }

        @Override
        public weather[] newArray(int size) {
            return new weather[size];
        }
    };
}
