package com.samsung.android.settings.languagepack.appsstub;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.languagepack.logger.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeviceConfig {
    public final Context mContext;

    public DeviceConfig(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static String getHash(String str) {
        byte[] digest =
                MessageDigest.getInstance("SHA-256")
                        .digest(
                                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                                str, "kjk3syk6wkj5")
                                        .getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getPrimaryUniqueId(Context context) {
        String string;
        String str = ApnSettings.MVNO_NONE;
        if (TextUtils.isEmpty(ApnSettings.MVNO_NONE)) {
            synchronized (DeviceConfig.class) {
                SharedPreferences sharedPreferences =
                        context.getSharedPreferences("PREF_UNIQUE_ID", 0);
                string = sharedPreferences.getString("PREF_UNIQUE_ID", null);
                if (string == null) {
                    string = UUID.randomUUID().toString();
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("PREF_UNIQUE_ID", string);
                    edit.apply();
                }
            }
            str = string;
        }
        Log.i("DeviceConfig", "[getPrimaryUniqueId] unique id = " + str);
        try {
            return getHash(str);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
