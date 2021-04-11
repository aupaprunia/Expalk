package com.bitcamp.expalk.live.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bitcamp.expalk.live.Constants;

public class PrefManager {
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }
}
