package com.samsung.android.settings.share.common;

import android.content.ComponentName;
import android.util.Log;
import android.util.Pair;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.share.structure.ShareComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ShareUtil {
    public static List parseJsonArray(String str) {
        if (str == null) {
            Log.e("ShareUtil", "JSONArrayStr is null");
            return Collections.emptyList();
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String string = jSONObject.getString("packageName");
                    String string2 = jSONObject.getString("activityName");
                    String string3 = jSONObject.getString("resolvedLabel");
                    String string4 = jSONObject.getString("applicationLabel");
                    int i2 = jSONObject.getInt(NetworkAnalyticsConstants.DataPoints.UID);
                    ComponentName componentName = new ComponentName(string, string2);
                    if (string4.equals(string3)) {
                        string3 = ApnSettings.MVNO_NONE;
                    }
                    arrayList.add(
                            new ShareComponent(componentName, i2, Pair.create(string4, string3)));
                } catch (JSONException e) {
                    Log.e("ShareUtil", "JSONException occurred " + e.getMessage());
                }
            }
            return arrayList;
        } catch (JSONException unused) {
            Log.e("ShareUtil", "JSONException occurred with ".concat(str));
            return Collections.emptyList();
        }
    }
}
