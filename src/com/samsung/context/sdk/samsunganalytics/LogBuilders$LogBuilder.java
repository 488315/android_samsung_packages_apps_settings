package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class LogBuilders$LogBuilder {
    public final Map logs = new HashMap();

    public abstract LogBuilders$LogBuilder getThis();

    public final void set(String str, String str2) {
        ((HashMap) this.logs).put(str, str2);
    }

    public final void setDimension(Map map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (TextUtils.isEmpty(str)) {
                Debug.LogENG("cd key is empty");
            } else {
                if (str.length() > 40) {
                    Debug.LogENG("cd key length over:".concat(str));
                    str = str.substring(0, 40);
                }
                if (str2 != null && str2.length() > 1024) {
                    Debug.LogENG("cd value length over:".concat(str2));
                    str2 = str2.substring(0, 1024);
                }
                hashMap.put(str, str2);
            }
        }
        set("cd", Delimiter.makeDelimiterString(hashMap, Delimiter.Depth.TWO_DEPTH));
    }

    public final LogBuilders$LogBuilder setScreenView(String str) {
        if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to build logs [PropertyBuilder] : Key cannot be null.");
        } else {
            set("pn", str);
        }
        return getThis();
    }
}
