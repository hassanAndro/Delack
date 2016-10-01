package com.delack.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Hassan on 9/22/2016.
 */

public class Constants {
    public static String token;
    public static String base = "scope=files:write:user&client_id=2332962332.82641665893&client_secret=090fd070956aba92297e649f660a06a2&redirect_uri=https://onebyte.slack.com";
    public static String base2 = "client_id=2332962332.82641665893&client_secret=090fd070956aba92297e649f660a06a2&redirect_uri=https://onebyte.slack.com";
    public static String singInUrl = "https://slack.com/oauth/authorize?" + base;
    public static String tokenUrl = "https://slack.com/api/oauth.access?" + base2 + "&code=";

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }
}
