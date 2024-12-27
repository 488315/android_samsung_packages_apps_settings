package com.samsung.android.settings.wifi.intelligent;

import android.content.Context;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiBadgeUtils {
    public static JSONArray favoriteNetworkArray;

    public static String getABTestParam(Context context) {
        String string =
                Settings.Global.getString(
                        context.getContentResolver(), "sem_auto_wifi_abtest_param");
        return TextUtils.isEmpty(string) ? SignalSeverity.NONE : string;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.json.JSONObject getBadgeJSONObject(android.content.Context r1) {
        /*
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r0 = "sem_auto_wifi_added_removed_list"
            java.lang.String r1 = android.provider.Settings.Global.getString(r1, r0)
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 != 0) goto L17
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: org.json.JSONException -> L17
            r0.<init>(r1)     // Catch: org.json.JSONException -> L17
            goto L18
        L17:
            r0 = 0
        L18:
            if (r0 == 0) goto L1b
            goto L20
        L1b:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
        L20:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.intelligent.WifiBadgeUtils.getBadgeJSONObject(android.content.Context):org.json.JSONObject");
    }

    public static boolean hasNewFavoriteNetwork(Context context) {
        JSONArray jSONArray;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        boolean z =
                Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
        boolean isSimCardReady = SemWifiUtils.isSimCardReady(telephonyManager);
        try {
            jSONArray = getBadgeJSONObject(context).getJSONArray("added");
        } catch (JSONException unused) {
            jSONArray = null;
        }
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        favoriteNetworkArray = jSONArray;
        return !z && isSimCardReady && jSONArray.length() > 0;
    }

    public static boolean isAbTestCurrentlyAvailable(Context context) {
        return !(Settings.Global.getInt(
                                context.getContentResolver(), "sem_auto_wifi_abtest_report", 0)
                        == 1)
                && (getABTestParam(context).equals(SignalSeverity.NONE) ^ true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ab, code lost:

       if ("CCT".equals(com.android.internal.telephony.TelephonyFeatures.getSalesCode()) == false) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0118, code lost:

       if (r10 == null) goto L58;
    */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e5 A[Catch: all -> 0x0107, Exception -> 0x0118, TRY_LEAVE, TryCatch #1 {all -> 0x0107, blocks: (B:28:0x00db, B:30:0x00e5, B:33:0x00e9, B:37:0x00f2, B:40:0x0101), top: B:27:0x00db }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNewItemForWips(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.intelligent.WifiBadgeUtils.isNewItemForWips(android.content.Context):boolean");
    }
}
