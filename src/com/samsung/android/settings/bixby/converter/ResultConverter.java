package com.samsung.android.settings.bixby.converter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ResultConverter {
    public HashMap mMap;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008c, code lost:

       if (r14.equals("-1") == false) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d8, code lost:

       if (r14.equals("2") == false) goto L67;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String convertSpecificActionResult(
            java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.converter.ResultConverter.convertSpecificActionResult(java.lang.String,"
                    + " java.lang.String):java.lang.String");
    }

    public final String convert(Bundle bundle, String str) {
        JSONObject jSONObject = new JSONObject();
        for (String str2 : bundle.keySet()) {
            String string = bundle.getString(str2);
            if (TextUtils.equals(str2, "result")) {
                if (!this.mMap.containsKey(string)) {
                    str.getClass();
                    switch (str) {
                        case "bixby.settingsApp.GetOnOff_NightTheme":
                        case "bixby.settingsApp.GetOnOff_AutoBrightness":
                        case "bixby.settingsApp.SetOnOff_AutoBrightness":
                        case "bixby.settingsApp.SetOnOff_NightTheme":
                        case "bixby.settingsApp.Set_ScreenTimeOut":
                        case "bixby.settingsApp.GetOnOff_BlueLight":
                            string = convertSpecificActionResult(str, string);
                            break;
                    }
                } else {
                    string = (String) this.mMap.get(string);
                }
            }
            try {
                jSONObject.put(str2, string);
            } catch (JSONException unused) {
                Log.e(
                        "ResultConverter",
                        "result convert failed / " + str2 + " : , value : " + string);
            }
        }
        return jSONObject.toString();
    }

    public static String convert(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", str);
        } catch (JSONException e) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "createJSONResult() failed / result : ", str, "\n");
            m.append(e.toString());
            Log.e("ResultConverter", m.toString());
        }
        Log.i("ResultConverter", "converted result : " + jSONObject.toString());
        return jSONObject.toString();
    }
}
