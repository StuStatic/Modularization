package com.example.common.bean;

import com.example.common.utils.ShareUtil;

import java.util.HashMap;
import java.util.Map;

public class ShareBean {
    private String url;
    private String title;
    private String desc;
    private String cover;
    private int localUrl;
    private String wxMiniUrl; // wx 小程序地址
    private Map<String,Object> wxMiniParams;
    private String pyqTitle;
    private String pyqDesc;
    private int pyqLocalUrl; //朋友圈默认图片
    private String miniQrCode; //小程序二维码图片
    private ShareUtil.ShareFrom shareFrom;
    private String userHeadUrl; //用户头像地址

    public Map<String, Object> getWxMiniParams() {
        return wxMiniParams;
    }

    public void setWxMiniParams(Map<String, Object> wxMiniParams) {
        this.wxMiniParams = wxMiniParams;
    }


    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public ShareUtil.ShareFrom getShareFrom() {
        return shareFrom;
    }

    public void setShareFrom(ShareUtil.ShareFrom shareFrom) {
        this.shareFrom = shareFrom;
    }

    public String getPyqTitle() {
        return pyqTitle;
    }

    public void setPyqTitle(String pyqTitle) {
        this.pyqTitle = pyqTitle;
    }

    public String getPyqDesc() {
        return pyqDesc;
    }

    public void setPyqDesc(String pyqDesc) {
        this.pyqDesc = pyqDesc;
    }

    public int getPyqLocalUrl() {
        return pyqLocalUrl;
    }

    public void setPyqLocalUrl(int pyqLocalUrl) {
        this.pyqLocalUrl = pyqLocalUrl;
    }


    public String getMiniQrCode() {
        return miniQrCode;
    }

    public void setMiniQrCode(String miniQrCode) {
        this.miniQrCode = miniQrCode;
    }

    public int getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(int localUrl) {
        this.localUrl = localUrl;
    }



    public String getWxMiniUrl() {
        return wxMiniUrl;
    }
    public void setWxMiniUrl(String wxMiniUrl) {
        this.wxMiniUrl = wxMiniUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public void addParams(String key,Object value){
        if(wxMiniParams==null){
            wxMiniParams=new HashMap<>();
        }
        wxMiniParams.put(key,value);
    }

}
