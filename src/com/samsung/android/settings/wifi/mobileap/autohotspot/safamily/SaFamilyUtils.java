package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SaFamilyUtils {
    public static String getEncryptedString(Context context, String str) {
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        String str2 = accountsByType.length > 0 ? accountsByType[0].name : null;
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(), "wifiap_autohotspot_aes_encryption", 0);
        if (i == 0) {
            if (str2 == null) {
                return null;
            }
            try {
                AES_ECB.setKey(str2);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(1, AES_ECB.secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        if (i != 1 || str == null || str2 == null) {
            return null;
        }
        try {
            SecretKeySpec key = AES_GCM.setKey(str2);
            if (key == null) {
                return null;
            }
            byte[] bArr = new byte[12];
            for (int i2 = 0; i2 < 12; i2++) {
                bArr[i2] = 1;
            }
            byte[] generateHashKey = AES_GCM.generateHashKey(str2);
            if (generateHashKey != null) {
                int length = generateHashKey.length;
                for (int i3 = 0; i3 < 12 && i3 < length; i3++) {
                    bArr[i3] = generateHashKey[i3];
                }
            }
            Cipher cipher2 = Cipher.getInstance("AES/GCM/NoPadding");
            cipher2.init(
                    1, new SecretKeySpec(key.getEncoded(), "AES"), new GCMParameterSpec(128, bArr));
            return Base64.getEncoder()
                    .encodeToString(cipher2.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidAlgorithmParameterException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getFamilyGroupId(Context context) {
        Log.d("SaFamilyUtils", "getFamilyGroupId");
        String str = SemWifiApContentProviderHelper.get(context, "smart_tethering_familyid");
        if (TextUtils.isEmpty(str)) {
            return ApnSettings.MVNO_NONE;
        }
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        String str2 = accountsByType.length > 0 ? accountsByType[0].name : null;
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(), "wifiap_autohotspot_aes_encryption", 0);
        if (i == 0) {
            if (str2 == null) {
                return null;
            }
            try {
                AES_ECB.setKey(str2);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(2, AES_ECB.secretKey);
                return new String(
                        cipher.doFinal(Base64.getDecoder().decode(str)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        if (i != 1 || str == null || str2 == null) {
            return null;
        }
        try {
            SecretKeySpec key = AES_GCM.setKey(str2);
            if (key == null) {
                return null;
            }
            byte[] bArr = new byte[12];
            for (int i2 = 0; i2 < 12; i2++) {
                bArr[i2] = 1;
            }
            byte[] generateHashKey = AES_GCM.generateHashKey(str2);
            if (generateHashKey != null) {
                int length = generateHashKey.length;
                for (int i3 = 0; i3 < 12 && i3 < length; i3++) {
                    bArr[i3] = generateHashKey[i3];
                }
            }
            byte[] decode = Base64.getDecoder().decode(str);
            Cipher cipher2 = Cipher.getInstance("AES/GCM/NoPadding");
            cipher2.init(
                    2, new SecretKeySpec(key.getEncoded(), "AES"), new GCMParameterSpec(128, bArr));
            return new String(cipher2.doFinal(decode), StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean isSupportFamilyService(Context context, String str) {
        try {
            Bundle call =
                    context.getContentResolver()
                            .call(
                                    Uri.parse(
                                            "content://com.samsung.android.samsungaccount.accountmanagerprovider"),
                                    "getFamilyServiceInfo",
                                    str,
                                    (Bundle) null);
            if (call != null) {
                if (call.getInt("result_code", 1) == 0) {
                    return call.getBundle("result_bundle")
                            .getBoolean("isSupportFamilyService", false);
                }
                call.getString("result_message", ApnSettings.MVNO_NONE);
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void setFamilySharingDB(Context context, boolean z) {
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(),
                        "wifi_ap_smart_tethering_settings_with_family",
                        0);
        Log.i("SaFamilyUtils", "Setting(DB) FamilySharing: " + z + ",existing_value:" + i);
        if (z == i) {
            return;
        }
        Settings.Secure.putInt(
                context.getContentResolver(),
                "wifi_ap_smart_tethering_settings_with_family",
                z ? 1 : 0);
    }
}
