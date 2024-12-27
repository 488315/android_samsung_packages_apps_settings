package com.samsung.android.settings.knox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.SemPersonaManager;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxSamsungAnalyticsLogger {
    public static HashMap addEvent(int i, int i2, Object obj) {
        HashMap hashMap = new HashMap();
        LottieColorUtils$$ExternalSyntheticOutline0.m(i, hashMap, "viewID", i2, "eventID");
        hashMap.put("detail", obj);
        hashMap.put("type", IMSParameter.CALL.EVENT);
        return hashMap;
    }

    public static HashMap addStatus(int i, Object obj) {
        HashMap hashMap = new HashMap();
        hashMap.put("statusID", Integer.valueOf(i));
        hashMap.put("detail", obj);
        hashMap.put("type", IMSParameter.CALL.STATUS);
        return hashMap;
    }

    public static void send(Context context, ArrayList arrayList, int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            Log.d("KKG::KnoxSamsungAnalyticsLogger", "Sending SA logging event");
            Intent intent = new Intent("com.samsung.knox.securefolder.salogging");
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.knox.securefolder",
                            "com.samsung.knox.securefolder.common.util.logging.LoggingReceiver"));
            intent.putExtra("knoxEventList", arrayList);
            context.sendBroadcastAsUser(intent, new UserHandle(i));
        }
    }
}
