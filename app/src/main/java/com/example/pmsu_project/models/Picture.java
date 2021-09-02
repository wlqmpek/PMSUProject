package com.example.pmsu_project.models;


public class Picture {
    private String data;
    private String mime_type;
    private String name;

    public Picture(String data, String mime_type, String name) {
        this.data = data;
        this.mime_type = mime_type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "data='" + data + '\'' +
                ", mime_type='" + mime_type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
