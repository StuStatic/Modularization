package com.example.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {

    private String id;  // 483700310884487168,
    private String username;  // "",
    private String nickname;  // "",
    private String password;  // "",
    private String head;  // "",
    private int sex;  // DEFAULT,
    private long birthday;  // 0,
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String mobile;  // 17611719616,
    private String country;  // 86,
    private String type;  // NORMAL,
    private String status;  // ACTIVE,
    private String sendId;  // 0,
    private String firstOrderSendId;  // 0,
    private String channel;  // 1,
    private String joinDate;  // 1595489606462,
    private String pageOrigin;  // "",
    private String pageOriginId;  // 0,
    private String subscribe;  // DEFAULT,
    private String userNum;  // 110574,
    private String basePainting;  // DEFAULT,
    private String sensorsId;  // OL5sX0A6X48aG2KQ0uyh8A==,
    private String teamId;  // 0,
    private String lastTeacherId;  // 0,
    private String lastTeamId;  // 0,
    private String importTime;  // 0,
    private String importRemark;  // "",
    private String exportRemark;  // "",
    private String mobileProvince;  // 北京,
    private String mobileCity;  // 北京市,
    private String user1v1Id;  // 0,
    private String wallBackground;  // "",
    private String birthdayActivity;  // "",
    private String spreadCode;  // "",
    private String cityId;  // 110100,
    private String provinceId;  // 110000,
    private String addedWechat;  // 0
    private String accessToken;

    protected UserBean(Parcel in) {
        id = in.readString();
        username = in.readString();
        nickname = in.readString();
        password = in.readString();
        head = in.readString();
        sex = in.readInt();
        birthday = in.readLong();
        mobile = in.readString();
        country = in.readString();
        type = in.readString();
        status = in.readString();
        sendId = in.readString();
        firstOrderSendId = in.readString();
        channel = in.readString();
        joinDate = in.readString();
        pageOrigin = in.readString();
        pageOriginId = in.readString();
        subscribe = in.readString();
        userNum = in.readString();
        basePainting = in.readString();
        sensorsId = in.readString();
        teamId = in.readString();
        lastTeacherId = in.readString();
        lastTeamId = in.readString();
        importTime = in.readString();
        importRemark = in.readString();
        exportRemark = in.readString();
        mobileProvince = in.readString();
        mobileCity = in.readString();
        user1v1Id = in.readString();
        wallBackground = in.readString();
        birthdayActivity = in.readString();
        spreadCode = in.readString();
        cityId = in.readString();
        provinceId = in.readString();
        addedWechat = in.readString();
        accessToken = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getFirstOrderSendId() {
        return firstOrderSendId;
    }

    public void setFirstOrderSendId(String firstOrderSendId) {
        this.firstOrderSendId = firstOrderSendId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPageOrigin() {
        return pageOrigin;
    }

    public void setPageOrigin(String pageOrigin) {
        this.pageOrigin = pageOrigin;
    }

    public String getPageOriginId() {
        return pageOriginId;
    }

    public void setPageOriginId(String pageOriginId) {
        this.pageOriginId = pageOriginId;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getBasePainting() {
        return basePainting;
    }

    public void setBasePainting(String basePainting) {
        this.basePainting = basePainting;
    }

    public String getSensorsId() {
        return sensorsId;
    }

    public void setSensorsId(String sensorsId) {
        this.sensorsId = sensorsId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getLastTeacherId() {
        return lastTeacherId;
    }

    public void setLastTeacherId(String lastTeacherId) {
        this.lastTeacherId = lastTeacherId;
    }

    public String getLastTeamId() {
        return lastTeamId;
    }

    public void setLastTeamId(String lastTeamId) {
        this.lastTeamId = lastTeamId;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getImportRemark() {
        return importRemark;
    }

    public void setImportRemark(String importRemark) {
        this.importRemark = importRemark;
    }

    public String getExportRemark() {
        return exportRemark;
    }

    public void setExportRemark(String exportRemark) {
        this.exportRemark = exportRemark;
    }

    public String getMobileProvince() {
        return mobileProvince;
    }

    public void setMobileProvince(String mobileProvince) {
        this.mobileProvince = mobileProvince;
    }

    public String getMobileCity() {
        return mobileCity;
    }

    public void setMobileCity(String mobileCity) {
        this.mobileCity = mobileCity;
    }

    public String getUser1v1Id() {
        return user1v1Id;
    }

    public void setUser1v1Id(String user1v1Id) {
        this.user1v1Id = user1v1Id;
    }

    public String getWallBackground() {
        return wallBackground;
    }

    public void setWallBackground(String wallBackground) {
        this.wallBackground = wallBackground;
    }

    public String getBirthdayActivity() {
        return birthdayActivity;
    }

    public void setBirthdayActivity(String birthdayActivity) {
        this.birthdayActivity = birthdayActivity;
    }

    public String getSpreadCode() {
        return spreadCode;
    }

    public void setSpreadCode(String spreadCode) {
        this.spreadCode = spreadCode;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getAddedWechat() {
        return addedWechat;
    }

    public void setAddedWechat(String addedWechat) {
        this.addedWechat = addedWechat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeString(password);
        dest.writeString(head);
        dest.writeInt(sex);
        dest.writeLong(birthday);
        dest.writeString(mobile);
        dest.writeString(country);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeString(sendId);
        dest.writeString(firstOrderSendId);
        dest.writeString(channel);
        dest.writeString(joinDate);
        dest.writeString(pageOrigin);
        dest.writeString(pageOriginId);
        dest.writeString(subscribe);
        dest.writeString(userNum);
        dest.writeString(basePainting);
        dest.writeString(sensorsId);
        dest.writeString(teamId);
        dest.writeString(lastTeacherId);
        dest.writeString(lastTeamId);
        dest.writeString(importTime);
        dest.writeString(importRemark);
        dest.writeString(exportRemark);
        dest.writeString(mobileProvince);
        dest.writeString(mobileCity);
        dest.writeString(user1v1Id);
        dest.writeString(wallBackground);
        dest.writeString(birthdayActivity);
        dest.writeString(spreadCode);
        dest.writeString(cityId);
        dest.writeString(provinceId);
        dest.writeString(addedWechat);
        dest.writeString(accessToken);
    }
}
