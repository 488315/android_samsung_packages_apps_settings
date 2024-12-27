package com.samsung.android.sdk.scs.base.utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FeatureHelper {
    public static FeatureConfig getFeatureConfig(String str) {
        JSONObject jSONObject = new JSONObject(str);
        String optString =
                jSONObject.optString(FeatureConfig.JSON_KEY_APP_VERSION, ApnSettings.MVNO_NONE);
        JSONObject optJSONObject = jSONObject.optJSONObject(FeatureConfig.JSON_KEY_FEATURES);
        HashMap hashMap = new HashMap();
        if (optJSONObject != null) {
            Iterator<String> keys = optJSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, Integer.valueOf(optJSONObject.optInt(next, 0)));
            }
        }
        return new FeatureConfig(optString, hashMap);
    }
}
