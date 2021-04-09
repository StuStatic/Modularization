package com.example.common.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class DailyBean implements Parcelable {
    private String name;
    private String itemId;
    private String day;
    private String reportType; //	类型 0-A课日报 1-B课日报 2-周报 3-月报
    private String userId;
    private String uri;
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return TextUtils.isEmpty(startTime) ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return TextUtils.isEmpty(endTime) ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemId() {
        return TextUtils.isEmpty(itemId) ? "" : itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDay() {
        return TextUtils.isEmpty(day) ? "" : day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public static Creator<DailyBean> getCREATOR() {
        return CREATOR;
    }

    protected DailyBean(Parcel in) {
        name = in.readString();
        itemId = in.readString();
        day = in.readString();
        reportType = in.readString();
        userId = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        uri = in.readString();
    }


    public static final Creator<DailyBean> CREATOR = new Creator<DailyBean>() {
        @Override
        public DailyBean createFromParcel(Parcel in) {
            return new DailyBean(in);
        }

        @Override
        public DailyBean[] newArray(int size) {
            return new DailyBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(itemId);
        dest.writeString(day);
        dest.writeString(reportType);
        dest.writeString(userId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(uri);
    }
}
