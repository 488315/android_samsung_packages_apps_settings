package com.samsung.android.settings.wifi.mobileap;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.SoftApConfiguration;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient;
import com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper;
import com.samsung.android.scloud.oem.lib.utils.FileTool;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.samsung.android.wifi.SemWifiApSmartWhiteList;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApCloudBackupRestore implements ISCloudQBNRClient {
    public final String TAG = "WifiApCloudBackupRestore";
    public boolean support5G = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApCloudBackupRestore$1, reason: invalid class name */
    public final class AnonymousClass1 implements FileTool.PDMProgressListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531
                val$quickBackupListener;

        public /* synthetic */ AnonymousClass1(
                QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531, int i) {
            this.$r8$classId = i;
            this.val$quickBackupListener = c00531;
        }

        @Override // com.samsung.android.scloud.oem.lib.utils.FileTool.PDMProgressListener
        public final void transferred(long j, long j2) {
            switch (this.$r8$classId) {
                case 0:
                    this.val$quickBackupListener.onProgress(j, j2);
                    break;
                default:
                    this.val$quickBackupListener.onProgress(j, j2);
                    break;
            }
        }
    }

    public WifiApCloudBackupRestore() {
        StringBuilder sb = Utils.sBuilder;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0369 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x013a A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0196 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0219 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0222 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x027d A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x02e9 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0301 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0310 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0333 A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x033f A[Catch: JSONException -> 0x007d, TryCatch #6 {JSONException -> 0x007d, blocks: (B:3:0x0033, B:5:0x005a, B:7:0x0060, B:8:0x0080, B:11:0x0091, B:12:0x0098, B:15:0x00a6, B:16:0x00f6, B:19:0x0100, B:21:0x010c, B:23:0x0137, B:25:0x013a, B:28:0x0155, B:29:0x0149, B:31:0x014f, B:33:0x015a, B:36:0x017c, B:38:0x0182, B:39:0x018c, B:41:0x0196, B:42:0x01a5, B:44:0x01ab, B:46:0x01cb, B:47:0x01dd, B:49:0x0219, B:50:0x021c, B:52:0x0222, B:53:0x0225, B:55:0x027d, B:56:0x029f, B:58:0x02e9, B:59:0x02fb, B:61:0x0301, B:62:0x030a, B:64:0x0310, B:65:0x0319, B:67:0x0333, B:69:0x033f, B:70:0x0349, B:122:0x0186, B:123:0x0112, B:125:0x0119, B:126:0x011d, B:128:0x0124, B:129:0x0129, B:131:0x012d, B:132:0x0132, B:134:0x00af, B:136:0x00bc, B:139:0x00ca, B:143:0x00db, B:145:0x00f0, B:147:0x0095), top: B:2:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x03ab  */
    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void backup(
            android.content.Context r17,
            android.os.ParcelFileDescriptor r18,
            com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper.AnonymousClass2.AnonymousClass1
                            .C00531
                    r19) {
        /*
            Method dump skipped, instructions count: 1015
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApCloudBackupRestore.backup(android.content.Context,"
                    + " android.os.ParcelFileDescriptor,"
                    + " com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2$1$1):void");
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final String getDescription(Context context) {
        return context.getResources().getString(R.string.mobileap);
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final String getLabel(Context context) {
        return context.getResources().getString(R.string.mobileap);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0071 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String readJSON() {
        /*
            r5 = this;
            java.lang.String r0 = "/data/misc/wifi_hostapd/restore.conf"
            java.lang.String r5 = r5.TAG
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.io.IOException -> L4d java.io.FileNotFoundException -> L50
            r2.<init>(r0)     // Catch: java.io.IOException -> L4d java.io.FileNotFoundException -> L50
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L42
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L42
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L42
            r0.<init>(r3)     // Catch: java.lang.Throwable -> L42
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L37
            r3.<init>()     // Catch: java.lang.Throwable -> L37
        L19:
            java.lang.String r4 = r0.readLine()     // Catch: java.lang.Throwable -> L28
            if (r4 == 0) goto L2a
            r3.append(r4)     // Catch: java.lang.Throwable -> L28
            r4 = 10
            r3.append(r4)     // Catch: java.lang.Throwable -> L28
            goto L19
        L28:
            r4 = move-exception
            goto L39
        L2a:
            r0.close()     // Catch: java.lang.Throwable -> L35
            r2.close()     // Catch: java.io.IOException -> L31 java.io.FileNotFoundException -> L33
            goto L6a
        L31:
            r0 = move-exception
            goto L53
        L33:
            r0 = move-exception
            goto L59
        L35:
            r0 = move-exception
            goto L44
        L37:
            r4 = move-exception
            r3 = r1
        L39:
            r0.close()     // Catch: java.lang.Throwable -> L3d
            goto L41
        L3d:
            r0 = move-exception
            r4.addSuppressed(r0)     // Catch: java.lang.Throwable -> L35
        L41:
            throw r4     // Catch: java.lang.Throwable -> L35
        L42:
            r0 = move-exception
            r3 = r1
        L44:
            r2.close()     // Catch: java.lang.Throwable -> L48
            goto L4c
        L48:
            r2 = move-exception
            r0.addSuppressed(r2)     // Catch: java.io.IOException -> L31 java.io.FileNotFoundException -> L33
        L4c:
            throw r0     // Catch: java.io.IOException -> L31 java.io.FileNotFoundException -> L33
        L4d:
            r0 = move-exception
            r3 = r1
            goto L53
        L50:
            r0 = move-exception
            r3 = r1
            goto L59
        L53:
            java.lang.String r2 = "readJSON IOEx.."
            com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(r2, r0, r5)
            goto L6a
        L59:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "readJSON exception.."
            r2.<init>(r4)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Log.e(r5, r0)
        L6a:
            if (r3 == 0) goto L71
            java.lang.String r5 = r3.toString()
            return r5
        L71:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApCloudBackupRestore.readJSON():java.lang.String");
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final void restore(
            Context context,
            ParcelFileDescriptor parcelFileDescriptor,
            QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531) {
        int i;
        WifiApCloudBackupRestore wifiApCloudBackupRestore;
        boolean z;
        Context context2;
        String str = this.TAG;
        Log.i(str, "Creating restore");
        SemWifiManager semWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        Utils.initMHSFeature(context);
        this.support5G = Utils.SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY;
        File file = new File("/data/misc/wifi_hostapd/restore.conf");
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                FileTool.writeToFile(
                        fileInputStream,
                        parcelFileDescriptor.getStatSize(),
                        file.getAbsolutePath(),
                        new AnonymousClass1(c00531, 1));
                fileInputStream.close();
                c00531.complete(true);
                String readJSON = readJSON();
                if (readJSON != null) {
                    try {
                        try {
                            JSONObject jSONObject = new JSONObject(readJSON);
                            SoftApConfiguration.Builder builder =
                                    new SoftApConfiguration.Builder(
                                            semWifiManager.getSoftApConfiguration());
                            jSONObject.getInt(FieldName.VERSION);
                            JSONObject jSONObject2 = jSONObject.getJSONObject("hotspot");
                            StringBuilder sb = new StringBuilder("softAp ==> ");
                            boolean z2 =
                                    jSONObject2.has("isDefaultSSID")
                                            ? jSONObject2.getBoolean("isDefaultSSID")
                                            : false;
                            sb.append("isDefaultSSID: " + z2);
                            String obj = jSONObject2.get("SSID").toString();
                            if (z2) {
                                Log.i(str, "Skipping Restoring SSID: " + obj);
                            } else {
                                builder.setSsid(obj);
                                sb.append(", SSID: " + obj);
                            }
                            boolean convertIntToBoolean =
                                    WifiApSettingsUtils.convertIntToBoolean(
                                            jSONObject2.getInt("hidden"));
                            builder.setHiddenSsid(convertIntToBoolean);
                            sb.append(", isHiddenSSID: " + convertIntToBoolean);
                            int i2 =
                                    jSONObject2.has("channel") ? jSONObject2.getInt("channel") : -1;
                            if (i2 < 0) {
                                i2 = 0;
                            }
                            sb.append(", channel: " + i2);
                            try {
                                i = jSONObject2.getInt("band");
                            } catch (JSONException unused) {
                                i = -1;
                            }
                            sb.append(", band: " + i);
                            Log.i(str, "Restoring ==> " + sb.toString());
                            if (i == -1) {
                                Log.i(str, "mBand is -1,apChannel:" + i2);
                                if (i2 == 149) {
                                    wifiApCloudBackupRestore = this;
                                    if (wifiApCloudBackupRestore.support5G) {
                                        builder.setChannels(new AnonymousClass3((char) 0, 0));
                                        z = false;
                                    }
                                } else {
                                    wifiApCloudBackupRestore = this;
                                }
                                if (i2 > 0 && i2 <= 11) {
                                    builder.setChannels(new AnonymousClass3(i2, 6));
                                    Settings.Secure.putInt(
                                            context.getContentResolver(),
                                            "wifi_ap_last_2g_channel",
                                            i2);
                                    z = true;
                                }
                                builder.setChannels(new AnonymousClass3((char) 0, 5));
                                z = false;
                            } else {
                                wifiApCloudBackupRestore = this;
                                if (i == 0) {
                                    if (i2 == 0) {
                                        builder.setChannels(new AnonymousClass3((char) 0, 7));
                                    } else {
                                        builder.setChannels(new AnonymousClass3(i2, 8));
                                        Settings.Secure.putInt(
                                                context.getContentResolver(),
                                                "wifi_ap_last_2g_channel",
                                                i2);
                                        z = true;
                                    }
                                } else if (i == 1 && wifiApCloudBackupRestore.support5G) {
                                    if (i2 == 0) {
                                        builder.setChannels(new AnonymousClass3(i2, 9));
                                    } else {
                                        builder.setChannels(new AnonymousClass3((char) 0, 10));
                                    }
                                } else if (i != 2) {
                                    boolean z3 = Utils.SPF_SupportMobileApDualAp;
                                    if (z3 && i == 3) {
                                        builder.setChannels(new AnonymousClass3((char) 0, 3));
                                    } else if (z3 && i == 4) {
                                        builder.setChannels(new AnonymousClass3((char) 0, 4));
                                    }
                                } else if (i2 == 0) {
                                    builder.setChannels(new AnonymousClass3((char) 0, 1));
                                } else {
                                    builder.setChannels(new AnonymousClass3((char) 0, 2));
                                }
                                z = false;
                            }
                            int i3 = jSONObject2.getInt("use_security");
                            String string = jSONObject2.getString("passphrase");
                            if (i3 == 0) {
                                builder.setPassphrase((String) null, 0);
                            } else if (i3 == 1) {
                                builder.setPassphrase(string, 1);
                            } else if (i3 == 2) {
                                builder.setPassphrase(string, 2);
                            } else if (WifiApFrameworkUtils.getSemWifiManager(context)
                                            .isWifiApWpa3Supported()
                                    && i3 == 3) {
                                builder.setPassphrase(string, 3);
                            } else if (i3 == 5) {
                                builder.setPassphrase((String) null, 5);
                            }
                            Settings.Secure.putInt(
                                    context.getContentResolver(),
                                    "wifi_ap_timeout_setting",
                                    jSONObject2.getInt("timeout"));
                            if (jSONObject2.has("datalimite")) {
                                String string2 = jSONObject2.getString("datalimite");
                                Log.i(str, "Restore data limite value : " + string2);
                                if (string2.equals("unlimited")) {
                                    Settings.Secure.putInt(
                                            context.getContentResolver(),
                                            "wifi_ap_mobile_data_limit",
                                            0);
                                    Settings.Secure.putString(
                                            context.getContentResolver(),
                                            "wifi_ap_mobile_data_limit_value",
                                            null);
                                } else {
                                    Settings.Secure.putInt(
                                            context.getContentResolver(),
                                            "wifi_ap_mobile_data_limit",
                                            1);
                                    Settings.Secure.putString(
                                            context.getContentResolver(),
                                            "wifi_ap_mobile_data_limit_value",
                                            string2);
                                }
                            }
                            if (jSONObject2.has("smartap_allowed_devices_name")) {
                                JSONArray jSONArray =
                                        jSONObject2.getJSONArray("smartap_allowed_devices_name");
                                JSONArray jSONArray2 =
                                        jSONObject2.getJSONArray("smartap_allowed_devices");
                                JSONArray jSONArray3 =
                                        jSONObject2.has("smartap_allowed_devicestype")
                                                ? jSONObject2.getJSONArray(
                                                        "smartap_allowed_devicestype")
                                                : null;
                                SemWifiApSmartWhiteList semWifiApSmartWhiteList =
                                        SemWifiApSmartWhiteList.getInstance();
                                if (jSONArray != null) {
                                    int i4 = 0;
                                    for (int i5 = 0; i5 < jSONArray.length(); i5++) {
                                        String obj2 = jSONArray.get(i5).toString();
                                        String obj3 = jSONArray2.get(i5).toString();
                                        if (jSONArray3 != null) {
                                            i4 = Integer.parseInt(jSONArray3.get(i5).toString());
                                        }
                                        if (semWifiApSmartWhiteList.addWhiteList(obj3, obj2, i4)
                                                == 3) {
                                            Log.i(str, "Whitelist MAC invalid. QUIT!!");
                                            return;
                                        }
                                    }
                                }
                            }
                            if (!WifiApFrameworkUtils.canAutoHotspotBeEnabled(context)) {
                                context2 = context;
                            } else if (jSONObject2.has("autoHotspot_switch_bnr_state")) {
                                boolean z4 = jSONObject2.getBoolean("autoHotspot_switch_bnr_state");
                                Log.i(str, "Restoring autohotspot value: " + z4);
                                context2 = context;
                                WifiApFrameworkUtils.setAutoHotspotDB(context2, z4);
                            } else {
                                context2 = context;
                                Log.i(str, "No autoHotspot_switch_bnr_state");
                            }
                            if (jSONObject2.has("family_sharing_bnr_state")) {
                                boolean z5 = jSONObject2.getBoolean("family_sharing_bnr_state");
                                Log.i(str, "Restoring family sharing value: " + z5);
                                if (z5) {
                                    Settings.Secure.putInt(
                                            context.getContentResolver(),
                                            "wifi_ap_smart_tethering_settings_with_family_restoring_required",
                                            1);
                                    if (WifiApFrameworkUtils.canFamilySharingBeEnabled(context)) {
                                        WifiApFrameworkUtils.setFamilySharingDB(context2, true);
                                    }
                                } else {
                                    WifiApFrameworkUtils.setFamilySharingDB(context2, false);
                                }
                            } else {
                                Log.i(str, "No family_sharing_bnr_state");
                            }
                            if (jSONObject2.has("smart_tethering_d2d_Wifimac")) {
                                SemWifiApContentProviderHelper.insert(
                                        context2,
                                        "smart_tethering_d2d_Wifimac",
                                        jSONObject2.get("smart_tethering_d2d_Wifimac").toString());
                            }
                            if (jSONObject2.has("smart_tethering_d2dfamilyid")) {
                                SemWifiApContentProviderHelper.insert(
                                        context2,
                                        "smart_tethering_d2dfamilyid",
                                        jSONObject2.get("smart_tethering_d2dfamilyid").toString());
                            }
                            if (jSONObject2.has("PMFChecked")) {
                                int i6 = jSONObject2.getInt("PMFChecked");
                                Log.i(wifiApCloudBackupRestore.TAG, " setPMFChecked " + i6);
                                Settings.Secure.putInt(
                                        context.getContentResolver(), "wifi_ap_pmf_checked", i6);
                            } else {
                                Log.w(str, "device dont have PMF");
                                Log.i(wifiApCloudBackupRestore.TAG, " setPMFChecked 0");
                                Settings.Secure.putInt(
                                        context.getContentResolver(), "wifi_ap_pmf_checked", 0);
                            }
                            if (jSONObject2.has("PSChecked")) {
                                int i7 = jSONObject2.getInt("PSChecked");
                                Log.i(wifiApCloudBackupRestore.TAG, "setPSChecked:" + i7);
                                Settings.Secure.putInt(
                                        context.getContentResolver(),
                                        "wifi_ap_powersave_mode_checked",
                                        i7);
                            } else {
                                Log.w(str, "device does't have PS");
                                Log.i(wifiApCloudBackupRestore.TAG, "setPSChecked:0");
                                Settings.Secure.putInt(
                                        context.getContentResolver(),
                                        "wifi_ap_powersave_mode_checked",
                                        0);
                            }
                            if (Utils.SUPPORT_MOBILEAP_WIFISHARING
                                    && jSONObject2.has("WifiSharing")) {
                                wifiApCloudBackupRestore.setWIFISharing(
                                        context2, jSONObject2.getInt("WifiSharing"));
                            }
                            if (jSONObject2.has("last2gChannel") && !z) {
                                Settings.Secure.putInt(
                                        context.getContentResolver(),
                                        "wifi_ap_last_2g_channel",
                                        jSONObject2.getInt("last2gChannel"));
                            }
                            if (jSONObject2.has("DisableRandomMac")) {
                                wifiApCloudBackupRestore.setDisableRandomMac(
                                        context2, jSONObject2.getInt("DisableRandomMac"));
                            }
                            if (jSONObject2.has("11axWifi6")) {
                                wifiApCloudBackupRestore.set11axWifi6(
                                        context2, jSONObject2.getInt("11axWifi6"));
                            }
                            SoftApConfiguration build = builder.build();
                            Settings.Secure.putInt(
                                    context.getContentResolver(),
                                    "wifi_ap_settings_cloud_backup_restoring",
                                    1);
                            semWifiManager.setSoftApConfiguration(build);
                            Log.i(str, "restored:" + build);
                            sendConfigChangedBroadcastToSmartTethering(context);
                            if (WifiApFeatureUtils.isMobileDataUsageSupported(context)) {
                                if (jSONObject2.has("clientsDataUsageSettings")) {
                                    semWifiManager.wifiApRestoreClientDataUsageSettingsInfo(
                                            jSONObject2.getString("clientsDataUsageSettings"));
                                    Log.i(str, "restored client data usage settings");
                                }
                                if (jSONObject2.has("dailyHotspotDataLimit")) {
                                    semWifiManager.wifiApRestoreDailyHotspotDataLimit(
                                            jSONObject2.getLong("dailyHotspotDataLimit"));
                                    Log.i(str, "restored hotspot daily data limit");
                                }
                            }
                            if (WifiApFeatureUtils.isOneTimePasswordSupported(context)
                                    && jSONObject2.has("isOneTimePasswordEnabled")) {
                                WifiApFrameworkUtils.setOtpPasswordEnabled(
                                        context2,
                                        jSONObject2.getInt("isOneTimePasswordEnabled") == 1);
                            }
                            if (WifiApFeatureUtils.isMobileDataUsageSupported(context)
                                    && jSONObject2.has(
                                            "key_cloud_prioritize_real_time_traffic_state")) {
                                WifiApFrameworkUtils.setPrioritizeRealTimeTrafficDB(
                                        context2,
                                        jSONObject2.getBoolean(
                                                "key_cloud_prioritize_real_time_traffic_state"));
                            }
                            if (jSONObject2.has("auto_connect_settings")) {
                                int i8 = jSONObject2.getInt("auto_connect_settings");
                                Log.i(str, "restore auto_connect_settings:" + i8);
                                semWifiManager.setAdvancedAutohotspotConnectSettings(i8);
                            }
                            if (jSONObject2.has("auto_connect_lcd_off_settings")) {
                                int i9 = jSONObject2.getInt("auto_connect_lcd_off_settings");
                                Log.i(str, "restore auto_connect_lcd_off_settings:" + i9);
                                semWifiManager.setAdvancedAutohotspotLCDSettings(i9);
                            }
                        } catch (NullPointerException e) {
                            Log.e(str, "Nullpointer EXCEeption enountered.." + e);
                        }
                    } catch (JSONException e2) {
                        Log.e(str, "JSON EXCEeption  enountered.." + e2);
                    }
                }
            } finally {
            }
        } catch (IOException unused2) {
            c00531.complete(false);
        }
    }

    public final void sendConfigChangedBroadcastToSmartTethering(Context context) {
        Log.i(
                this.TAG,
                "sendConfigChangedBroadcastToSmartTethering() - Sending Broadcast from"
                    + " BackupreStore");
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.server.wifi.softap.smarttethering.ssid_changed");
        context.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
    }

    public final void set11axWifi6(Context context, int i) {
        Log.i(this.TAG, " 11axWifi6 " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_11ax_mode_checked", i);
    }

    public final void setDisableRandomMac(Context context, int i) {
        Log.i(this.TAG, " DisableRandomMac " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_disable_random_mac", i);
    }

    public final void setWIFISharing(Context context, int i) {
        Log.i(this.TAG, " setWIFISharing " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_wifi_sharing", i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApCloudBackupRestore$3, reason: invalid class name */
    public final class AnonymousClass3 extends SparseIntArray {
        public AnonymousClass3(char c, int i) {
            switch (i) {
                case 1:
                    put(4, 0);
                    break;
                case 2:
                    put(4, 0);
                    break;
                case 3:
                    put(1, 0);
                    put(2, 149);
                    break;
                case 4:
                    put(1, 0);
                    put(4, 0);
                    break;
                case 5:
                    put(1, 0);
                    break;
                case 6:
                case 8:
                case 9:
                default:
                    put(2, 149);
                    break;
                case 7:
                    put(1, 0);
                    break;
                case 10:
                    put(2, 149);
                    break;
            }
        }

        public AnonymousClass3(int i, int i2) {
            switch (i2) {
                case 8:
                    put(1, i);
                    break;
                case 9:
                    put(2, i);
                    break;
                default:
                    put(1, i);
                    break;
            }
        }
    }
}
