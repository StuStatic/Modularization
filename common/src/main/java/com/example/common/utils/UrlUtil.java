package com.example.common.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.HashMap;

public class UrlUtil {
    //	保存URL参数的容器
    public HashMap<String,String> strUrlParas = new HashMap<>();
    private String startUrl="";

    public UrlUtil(String url,Context context){
        parser(url);
        appendChannelId(context);
    }
    /**
     * @description 解析日志url
     * @param url 需要解析的单条日志内容
     */
    private void parser(String url){
        strUrlParas.clear();
//		传递的URL参数
        String strUrl = "";
        String strUrlParams = "";


//		解析访问地址
        if(url.contains("?")){
            String[] strUrlPatten = url.split("\\?");
            strUrl = strUrlPatten[0];
            strUrlParams = strUrlPatten[1];

        }else{
            strUrl = url;
            strUrlParams = url;
        }
        startUrl=strUrl;
//		解析参数
        String[] params;

        if(strUrlParams.contains("&")){
            params = strUrlParams.split("&");
        }else{
            params = new String[] {strUrlParams};
        }

//		保存参数到参数容器
        for(String p:params){
            if(p.contains("=")) {
                String[] param = p.split("=");
                if(param.length==1){
                    strUrlParas.put(param[0],"");
                }else{

                    String key = param[0];
                    String value = param[1];

                    strUrlParas.put(key, value);
                }
            }
        }
    }

    public String getUrl(){
        if(TextUtils.isEmpty(startUrl)){
            return "";
        }
        StringBuilder builder=new StringBuilder(startUrl);
        if(strUrlParas.size()>0){
            builder.append("?");
        }
        for(String key:strUrlParas.keySet()){
            builder.append(key);
            builder.append("=");
            builder.append(strUrlParas.get(key));
            builder.append("&");
        }
        String returnUrl=builder.toString();
        if(returnUrl.endsWith("&")){
            returnUrl=returnUrl.substring(0,returnUrl.length()-1);
        }
        return returnUrl;
    }

    private void appendChannelId(Context context){
        String channelId=strUrlParas.get("channelId");
        if(TextUtils.isEmpty(channelId)){
            strUrlParas.put("channelId",AppUtils.getChannel(context));
        }
    }
    public static JSONObject parseScheme(String scheme) {

        JSONObject object = new JSONObject();
        try {
            scheme= URLDecoder.decode(scheme,"UTF-8");
            Uri uri = Uri.parse(scheme);
            for (String name : uri.getQueryParameterNames()) {
                object.put(name, uri.getQueryParameter(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
