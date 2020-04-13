package com.me.earthquake;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssFeedProvider extends AsyncTask<String,Void,List<RssItem>> {

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String PUB_DATE = "pubDate";
    static final String LINK = "link";
    static final String CATEGORY = "category";
    static final String LAT = "geo:lat";
    static final String LNG = "geo:long";
    static final String ITEM = "item";





    @Override
    protected List<RssItem> doInBackground(String... strings) {
        List<RssItem> list = new ArrayList<RssItem>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setReadTimeout(10000 );
        connection.setConnectTimeout(15000);
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setDoInput(true);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            stream = connection.getInputStream();

            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            RssItem item = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                            item = new RssItem();
                        } else if (item != null) {
                            if (name.equalsIgnoreCase(LINK)) {
                                Log.i("Attribute", "setLink");
                                item.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                Log.i("Attribute", "description");
                                item.setDesc(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                Log.i("Attribute", "date");
                                item.setDate(parser.nextText());
                            } else if (name.equalsIgnoreCase(CATEGORY)) {
                                Log.i("Attribute", "title");
                                item.setCat(parser.nextText().trim());
                            }
                            else if (name.equalsIgnoreCase(LAT)) {
                                Log.i("Attribute", "title");
                                item.setLat(parser.nextText().trim());
                            }
                            else if (name.equalsIgnoreCase(LNG)) {
                                Log.i("Attribute", "title");
                                item.setLng(parser.nextText().trim());
                            }
                            else if (name.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "title");
                                item.setName(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase(ITEM) && item != null) {
                            Log.i("Added", item.toString());
                            list.add(item);
                        } /*else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }*/
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
