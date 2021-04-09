package com.example.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.R;

/**
 * xiangfenr
 * Glide加载图
 */
public class GlideUtil {


    /**
     * 加载原图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void GlideImage(Context context, String url, ImageView imageView) {
        if (null != context) {
            Glide.with(context).load(url)
                    .placeholder(R.mipmap.default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 加载显示圆角的图
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void GlideCorners(Context context, String url, ImageView imageView, int width, int height) {
        GlideCorners(context, url, imageView, width, height, R.mipmap.default_img);
    }

    /**
     * 加载显示圆角的图
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     * @param placeholderId
     */
    public static void GlideCorners(Context context, String url, ImageView imageView, int width, int height, int placeholderId) {

        GlideCorners(context, url, imageView, width, height, placeholderId, placeholderId);
    }

    /**
     * 加载显示圆角的图
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     * @param placeholderId
     * @param errorId
     */
    public static void GlideCorners(Context context, String url, ImageView imageView, int width, int height, int placeholderId, int errorId) {
        if (null != context) {
            RoundedCorners corners = new RoundedCorners(25);
            Glide.with(context).load(url)
                    .placeholder(placeholderId)
                    .error(errorId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.bitmapTransform(corners).override(width, height))
                    .into(imageView);
        }
    }

    /**
     * 加载显示圆图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void GlideCircle(Context context, String url, ImageView imageView, int size) {

        if (null != context) {
            //显示头像
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.default_touxiang)
                    .dontAnimate()
                    .apply(RequestOptions.circleCropTransform())
                    .override(size, size)
                    .into(imageView);
        }
    }
    /**
     * 加载原图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void GlideImageWithCallback(Context context, String url,int size, ImageView imageView, RequestListener<Drawable> listener) {
        if (null != context) {
            Glide.with(context).load(url)
                    .listener(listener)
                    .dontAnimate()
                    .override(size, size)
                    .into(imageView);
        }
    }

    /**
     * 加载显示圆图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void GlideCircleWithCallback(Context context, String url, ImageView imageView, int size,RequestListener<Drawable> listener) {

        if (null != context) {
            //显示头像
            Glide.with(context)
                    .load(url)
                    .listener(listener)
                    .placeholder(R.mipmap.default_touxiang)
                    .dontAnimate()
                    .apply(RequestOptions.circleCropTransform())
                    .override(size, size)
                    .into(imageView);
        }
    }
    /**
     * 加载显示圆图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void GlideMiniWithCallback(Context context, String url, ImageView imageView,RequestListener<Drawable> listener) {

        if (null != context) {
            //显示头像
            Glide.with(context)
                    .load(url)
                    .listener(listener)
                    .dontAnimate()
                    .into(imageView);
        }
    }

}
