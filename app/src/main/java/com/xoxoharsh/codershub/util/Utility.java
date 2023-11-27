package com.xoxoharsh.codershub.util;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
