package com.samsung.android.settings.vpn.smartswitch;

import android.security.LegacyVpnProfileStore;

import com.android.internal.net.VpnProfile;

import com.samsung.android.util.SemLog;

import org.json.JSONArray;
import org.json.JSONException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class VpnProfileUtils {
    public static String getVpnProfileData() {
        JSONArray jSONArray = new JSONArray();
        for (String str : LegacyVpnProfileStore.list("VPN_")) {
            VpnProfile decode = VpnProfile.decode(str, LegacyVpnProfileStore.get("VPN_" + str));
            if (decode != null) {
                VpnProfile.decrypt(decode);
                jSONArray.put(new String(decode.encode(), StandardCharsets.UTF_8));
            }
        }
        if (jSONArray.length() > 0) {
            return jSONArray.toString();
        }
        return null;
    }

    public static boolean putVpnProfileData(String str) {
        HashSet hashSet = new HashSet(Arrays.asList(LegacyVpnProfileStore.list("VPN_")));
        try {
            JSONArray jSONArray = new JSONArray(str);
            int i = 0;
            while (true) {
                boolean z = true;
                if (i >= jSONArray.length()) {
                    return true;
                }
                String string = jSONArray.getString(i);
                long currentTimeMillis = System.currentTimeMillis();
                while (hashSet.contains(Long.toHexString(currentTimeMillis))) {
                    currentTimeMillis++;
                }
                VpnProfile decode =
                        VpnProfile.decode(
                                Long.toHexString(currentTimeMillis),
                                string.getBytes(StandardCharsets.UTF_8));
                if (decode != null) {
                    if (decode.isIpsecSecretIvParams.isEmpty()
                            && decode.isPasswordIvParams.isEmpty()) {
                        z = false;
                    }
                    if (LegacyVpnProfileStore.put("VPN_" + decode.key, decode.encode(z))) {
                        hashSet.add(decode.key);
                    } else {
                        SemLog.e(
                                "VpnProfileUtils",
                                "putVpnProfileData: failed to put profile: " + decode.name);
                    }
                }
                i++;
            }
        } catch (JSONException e) {
            SemLog.e("VpnProfileUtils", "putVpnProfileData: invalid data: " + e);
            return false;
        }
    }
}
