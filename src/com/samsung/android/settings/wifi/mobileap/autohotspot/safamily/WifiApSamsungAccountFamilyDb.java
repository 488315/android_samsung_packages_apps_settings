package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSamsungAccountFamilyDb extends BroadcastReceiver {
    public static String currentGuid;
    public static int newFamilyCount;
    public static String newFamilyGuids;
    public static String newFamilyId;
    public static String newFamilyUserNames;
    public static String newGroupOwnerId;
    public static Long newHashFamilyId;
    public static int oldFamilyCount;
    public static String oldFamilyGuids;
    public static String oldFamilyId;
    public static String oldFamilyUserNames;
    public static String oldGroupOwnerId;
    public static Long oldHashFamilyId;
    public static final Uri FAMILY_GROUP_INFO_URI =
            Uri.parse(
                    "content://com.samsung.android.samsungaccount.familyGroupProvider/family_group_info");
    public static final Uri FAMILY_GROUP_MEMBER_INFO_URI =
            Uri.parse(
                    "content://com.samsung.android.samsungaccount.familyGroupProvider/family_group_member_info");
    public static final Object mylock = new Object();

    /* JADX WARN: Removed duplicated region for block: B:25:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void saveMemberIcon(android.content.Context r8, int r9, java.lang.String r10) {
        /*
            r0 = 0
            if (r10 == 0) goto L8b
            boolean r1 = r10.isEmpty()
            if (r1 != 0) goto L8b
            com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.bmForURL = r0
            com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil$1 r1 = new com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil$1
            r1.<init>()
            com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.mURLThread = r1
            r1.start()
            com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil$1 r10 = com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.mURLThread     // Catch: java.lang.InterruptedException -> L1f
            if (r10 == 0) goto L23
            r1 = 3000(0xbb8, double:1.482E-320)
            r10.join(r1)     // Catch: java.lang.InterruptedException -> L1f
            goto L23
        L1f:
            r10 = move-exception
            r10.printStackTrace()
        L23:
            com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil$1 r10 = com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.mURLThread
            if (r10 == 0) goto L32
            boolean r10 = r10.isAlive()
            if (r10 == 0) goto L32
            com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil$1 r10 = com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.mURLThread
            r10.interrupt()
        L32:
            r10 = 0
            android.graphics.Bitmap r1 = com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.bmForURL     // Catch: java.lang.Exception -> L80
            if (r1 == 0) goto L84
            r2 = 89
            r3 = 1
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createScaledBitmap(r1, r2, r2, r3)     // Catch: java.lang.Exception -> L80
            int r2 = r1.getWidth()     // Catch: java.lang.Exception -> L80
            int r4 = r1.getHeight()     // Catch: java.lang.Exception -> L80
            if (r2 > r4) goto L49
            goto L4a
        L49:
            r2 = r4
        L4a:
            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Exception -> L80
            android.graphics.Bitmap r4 = android.graphics.Bitmap.createBitmap(r2, r2, r4)     // Catch: java.lang.Exception -> L80
            android.graphics.Canvas r5 = new android.graphics.Canvas     // Catch: java.lang.Exception -> L80
            r5.<init>(r4)     // Catch: java.lang.Exception -> L80
            android.graphics.BitmapShader r6 = new android.graphics.BitmapShader     // Catch: java.lang.Exception -> L80
            android.graphics.Shader$TileMode r7 = android.graphics.Shader.TileMode.CLAMP     // Catch: java.lang.Exception -> L80
            r6.<init>(r1, r7, r7)     // Catch: java.lang.Exception -> L80
            android.graphics.Paint r1 = new android.graphics.Paint     // Catch: java.lang.Exception -> L80
            r1.<init>()     // Catch: java.lang.Exception -> L80
            r1.setAntiAlias(r3)     // Catch: java.lang.Exception -> L80
            r1.setShader(r6)     // Catch: java.lang.Exception -> L80
            int r2 = r2 >> r3
            float r2 = (float) r2     // Catch: java.lang.Exception -> L80
            r5.drawCircle(r2, r2, r2, r1)     // Catch: java.lang.Exception -> L80
            android.graphics.Bitmap r1 = com.samsung.android.settings.wifi.mobileap.utils.PhotoUtil.bmForURL     // Catch: java.lang.Exception -> L80
            r1.recycle()     // Catch: java.lang.Exception -> L80
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Exception -> L80
            r1.<init>()     // Catch: java.lang.Exception -> L80
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.PNG     // Catch: java.lang.Exception -> L80
            r4.compress(r2, r10, r1)     // Catch: java.lang.Exception -> L80
            byte[] r1 = r1.toByteArray()     // Catch: java.lang.Exception -> L80
            goto L85
        L80:
            r1 = move-exception
            r1.printStackTrace()
        L84:
            r1 = r0
        L85:
            if (r1 == 0) goto L8b
            java.lang.String r0 = android.util.Base64.encodeToString(r1, r10)
        L8b:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r1 = "smart_tethering_family_icons_"
            r10.<init>(r1)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            com.samsung.android.wifi.SemWifiApContentProviderHelper.insert(r8, r9, r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSamsungAccountFamilyDb.saveMemberIcon(android.content.Context,"
                    + " int, java.lang.String):void");
    }

    public static void setFamilyGroupInfoValues(
            Context context, String str, String str2, int i, String str3, String str4) {
        long valueOf;
        if (TextUtils.isEmpty(str)) {
            valueOf = -1L;
        } else {
            long j = 0;
            for (int i2 = 0; i2 < str.length(); i2++) {
                j = ((31 * j) + str.charAt(i2)) % 9223372036854775806L;
            }
            if (j < 0) {
                j *= -1;
            }
            valueOf = Long.valueOf(j);
        }
        newHashFamilyId = valueOf;
        newFamilyId = SaFamilyUtils.getEncryptedString(context, str);
        newGroupOwnerId = SaFamilyUtils.getEncryptedString(context, str2);
        newFamilyUserNames =
                i > 0 ? SaFamilyUtils.getEncryptedString(context, str3) : ApnSettings.MVNO_NONE;
        newFamilyGuids =
                i > 0 ? SaFamilyUtils.getEncryptedString(context, str4) : ApnSettings.MVNO_NONE;
        newFamilyCount = i;
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        "[[[[[[[[[[ ============\nnewHashFamilyId : " + newHashFamilyId,
                        "\nnewFamilyCount : ");
        m.append(newFamilyCount);
        StringBuilder m2 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m.toString(), "\nnewFamilyUserNames : ");
        m2.append(newFamilyUserNames);
        StringBuilder m3 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m2.toString(), "\nnewFamilyGuids : ");
        m3.append(newFamilyGuids);
        StringBuilder m4 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m3.toString(), "\nnewFamilyLEaderGuid : ");
        m4.append(newGroupOwnerId);
        StringBuilder m5 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m4.toString(), "\nnewFamilyId : ");
        m5.append(newFamilyId);
        Log.d("WifiApSamsungAccountFamilyDb", m5.toString() + "\n============ ]]]]]]]]]]");
        SemWifiApContentProviderHelper.insert(
                context, "hash_value_based_on_familyid", newHashFamilyId + ApnSettings.MVNO_NONE);
        SemWifiApContentProviderHelper.insert(
                context, "smart_tethering_family_count", newFamilyCount + ApnSettings.MVNO_NONE);
        SemWifiApContentProviderHelper.insert(context, "smart_tethering_ownerid", newGroupOwnerId);
        SemWifiApContentProviderHelper.insert(
                context, "smart_tethering_family_guids", newFamilyGuids);
        SemWifiApContentProviderHelper.insert(context, "smart_tethering_familyid", newFamilyId);
        SemWifiApContentProviderHelper.insert(
                context, "smart_tethering_family_user_names", newFamilyUserNames);
        String str5 = oldFamilyId;
        if (str5 == null || str5.equals(newFamilyId)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.server.wifi.softap.smarttethering.familyid");
        context.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
    }

    public static void setOldDbValues(Context context) {
        String str = SemWifiApContentProviderHelper.get(context, "hash_value_based_on_familyid");
        if (TextUtils.isEmpty(str)) {
            oldHashFamilyId = -1L;
        } else {
            oldHashFamilyId = Long.valueOf(Long.parseLong(str));
        }
        String str2 = SemWifiApContentProviderHelper.get(context, "smart_tethering_family_count");
        if (TextUtils.isEmpty(str2)) {
            oldFamilyCount = 0;
        } else {
            oldFamilyCount = Integer.parseInt(str2);
        }
        oldGroupOwnerId = SemWifiApContentProviderHelper.get(context, "smart_tethering_ownerid");
        oldFamilyUserNames =
                SemWifiApContentProviderHelper.get(context, "smart_tethering_family_user_names");
        oldFamilyGuids =
                SemWifiApContentProviderHelper.get(context, "smart_tethering_family_guids");
        oldFamilyId = SemWifiApContentProviderHelper.get(context, "smart_tethering_familyid");
        newHashFamilyId = -1L;
        newFamilyId = ApnSettings.MVNO_NONE;
        newFamilyUserNames = ApnSettings.MVNO_NONE;
        newFamilyGuids = ApnSettings.MVNO_NONE;
        newGroupOwnerId = ApnSettings.MVNO_NONE;
        newFamilyCount = 0;
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        "[[[[[[[[[[  ============\noldHashFamilyId : " + oldHashFamilyId,
                        "\noldFamilyCount : ");
        m.append(oldFamilyCount);
        StringBuilder m2 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m.toString(), "\noldFamilyUserNames : ");
        m2.append(oldFamilyUserNames);
        StringBuilder m3 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m2.toString(), "\noldFamilyGuids : ");
        m3.append(oldFamilyGuids);
        StringBuilder m4 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m3.toString(), "\noldFamilyLEaderGuid : ");
        m4.append(oldGroupOwnerId);
        StringBuilder m5 =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        m4.toString(), "\noldFamilyId : ");
        m5.append(oldFamilyId);
        Log.d("WifiApSamsungAccountFamilyDb", m5.toString() + "\n============ ]]]]]]]]]]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0186 A[Catch: all -> 0x017d, Exception -> 0x0180, TRY_LEAVE, TryCatch #15 {all -> 0x017d, blocks: (B:19:0x0046, B:22:0x0083, B:188:0x017c, B:187:0x0179, B:198:0x018d, B:37:0x0198, B:114:0x03ac, B:113:0x03a9, B:142:0x03b9, B:32:0x03cd, B:33:0x03de, B:42:0x03b1, B:30:0x03c3, B:28:0x0186), top: B:18:0x0046, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x03c3 A[Catch: all -> 0x017d, TryCatch #15 {all -> 0x017d, blocks: (B:19:0x0046, B:22:0x0083, B:188:0x017c, B:187:0x0179, B:198:0x018d, B:37:0x0198, B:114:0x03ac, B:113:0x03a9, B:142:0x03b9, B:32:0x03cd, B:33:0x03de, B:42:0x03b1, B:30:0x03c3, B:28:0x0186), top: B:18:0x0046, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0198 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x03b1 A[Catch: all -> 0x017d, Exception -> 0x03ad, TRY_LEAVE, TryCatch #0 {Exception -> 0x03ad, blocks: (B:114:0x03ac, B:113:0x03a9, B:42:0x03b1), top: B:38:0x01a3 }] */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v27 */
    /* JADX WARN: Type inference failed for: r10v28 */
    /* JADX WARN: Type inference failed for: r10v29 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void updateSAFamilyGroupInfo(android.content.Context r19) {
        /*
            Method dump skipped, instructions count: 1003
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSamsungAccountFamilyDb.updateSAFamilyGroupInfo(android.content.Context):void");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        boolean isSAFamilySupportedInSAFamilyDB =
                WifiApFeatureUtils.isSAFamilySupportedInSAFamilyDB(context);
        Log.i(
                "WifiApSamsungAccountFamilyDb",
                "rcvd:" + intent.getAction() + ",supported:" + isSAFamilySupportedInSAFamilyDB);
        if (isSAFamilySupportedInSAFamilyDB) {
            updateSAFamilyGroupInfo(context);
        }
    }
}
