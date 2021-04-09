package com.example.common.bean;

public class BottomListItemBean {
    private String content;
    private String tag;
    private int code;

    public BottomListItemBean(String content){
        this(content,"",-1);
    }

    public BottomListItemBean(String content,String tag){
        this(content,tag,-1);
    }

    public BottomListItemBean(String content,String tag,int code){
        this.content=content;
        this.tag=tag;
        this.code=code;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
