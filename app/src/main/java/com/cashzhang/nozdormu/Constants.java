package com.cashzhang.nozdormu;

import android.content.res.Resources;
import android.widget.ListView;
import com.cashzhang.nozdormu.fragment.FeedsFragment;
import com.cashzhang.nozdormu.fragment.StreamsFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * Created by cz21 on 2018/2/6.
 */

public class Constants {

    public static final String BASE_URL = "https://cloud.feedly.com";
    public static final String AUTH_URL = "/v3/auth/auth";
    public static final String TOKEN_URL = "/v3/auth/token";
    public static final String PROFILE = "/v3/profile";
    public static final String CATEGORIES = "/v3/categories?sort=feedly";
    public static final String SUBSCRIPTIONS = "/v3/subscriptions";
    public static final String RESPONSE_TYPE = "?response_type=code";
    public static final String CLIENT_ID = "&client_id=feedly";
    public static final String REDIRECT_URI = "&redirect_uri=https://cloud.feedly.com/feedly.html";
    public static final String SCOPE = "&scope=https://cloud.feedly.com/subscriptions";


    public static MainActivity s_activity;
    public static Resources s_resources;
    public static StreamsFragment s_fragmentFeeds;
    public static FeedsFragment s_fragmentSecCateg;
    public static FragmentManager s_fragmentManager;
    public static ListView s_listView;
    public static SwipeRefreshLayout s_swipeSLayout;
    public static SwipeRefreshLayout s_swipeCLayout;
    public static SwipeRefreshLayout s_swipeMLayout;

    static void saveInitialConstants(MainActivity activity) {
        s_activity = activity;
        s_resources = activity.getResources();
//        s_listView = activity.findViewById(R.id.l_list);
        s_fragmentManager = activity.getSupportFragmentManager();
    }

    static public void getFragmentView(StreamsFragment streamsFragment) {
        s_fragmentFeeds = streamsFragment;
        s_swipeMLayout = s_fragmentFeeds.getView().findViewById(R.id.swipe_refresh);
    }

    static public void getSecCatesFragmentView(FeedsFragment feedsFragment) {
        s_fragmentSecCateg = feedsFragment;
        s_swipeSLayout = s_fragmentSecCateg.getView().findViewById(R.id.swipe_refresh);
    }

    public static XmlPullParser createXmlParser(CharSequence urlString) throws IOException, XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        URL url = new URL(urlString.toString());
        InputStream inputStream = url.openStream();
        parser.setInput(inputStream, null);
        return parser;
    }

    public static void tmpWrite(String fileName, String response) {
        String currentUserDir = "data/data/com.cashzhang.nozdormu/files/";
        File file = new File(currentUserDir, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(response.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}