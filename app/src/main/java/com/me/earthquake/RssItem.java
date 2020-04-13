package com.me.earthquake;

class RssItem {
    String name;
    String desc;
    String link;
    String date;

    public RssItem() {
    }
    public RssItem(String name, String desc, String link, String date, String cat, String lat, String lng) {
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.date = date;
        this.cat = cat;
        this.lat = lat;
        this.lng = lng;
    }

    String cat;
    String lat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    String lng;
}
