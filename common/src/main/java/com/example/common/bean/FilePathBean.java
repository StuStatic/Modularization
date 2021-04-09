package com.example.common.bean;

/**
 * fileName：FilePathBean
 *
 * @author ：qyh
 * date    ：2019-12-22 19:07
 * describe：
 */
public class FilePathBean {

    private String name;

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FilePathBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
