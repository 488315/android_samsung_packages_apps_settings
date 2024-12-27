package com.samsung.android.settings.wifi.mobileap;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticOutline0;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSmartSwitchBackupRestore extends BroadcastReceiver {
    public static boolean mIsCanceled;
    public SemWifiManager mSemWifiManager;
    public String mSsidBeforeRestoring;
    public byte[] salt;
    public boolean support5G;
    public boolean support6G;
    public String xmlConf;
    public String xmlModel;
    public String xmlOtherConf;

    public WifiApSmartSwitchBackupRestore() {
        StringBuilder sb = Utils.sBuilder;
        this.support5G = Utils.SUPPORT_MOBILEAP_5G_BASED_ON_COUNTRY;
        this.support6G = Utils.SUPPORT_MOBILEAP_6G_BASED_ON_COUNTRY;
    }

    public static SecretKeySpec generateSHA256SecretKey(String str) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }

    public static String getModelName() {
        String str = Build.MODEL;
        int length = str.length();
        if (length > 8 && "SAMSUNG-".equals(str.substring(0, 8))) {
            str = str.substring(8, length);
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "model name..", str, "WifiApSmartSwitchBackupRestore");
        return str;
    }

    public static int getSamsungAccountCount(Context context) {
        return ((AccountManager) context.getSystemService("account"))
                .getAccountsByType("com.osp.app.signin")
                .length;
    }

    public static int getWifiSharing(Context context) {
        int i = Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_wifi_sharing", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, " getWifiSharing ", "WifiApSmartSwitchBackupRestore");
        return i;
    }

    public static void sendConfigChangedBroadcastToSmartTethering(Context context) {
        Log.i(
                "WifiApSmartSwitchBackupRestore",
                "sendConfigChangedBroadcastToSmartTethering() - Sending Broadcast from"
                    + " BackupreStore");
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.server.wifi.softap.smarttethering.ssid_changed");
        context.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
    }

    public static void sendResponse(
            int i, int i2, Context context, String str, String str2, String str3) {
        if (mIsCanceled) {
            Log.i("WifiApSmartSwitchBackupRestore", "sendResponse cancelled");
            return;
        }
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i, "sendResponse,action:", str, ",result:", ",err_code:");
        m.append(i2);
        m.append(",source:");
        m.append(str2);
        Log.i("WifiApSmartSwitchBackupRestore", m.toString());
        Intent intent = new Intent(str);
        intent.putExtra("RESULT", i);
        intent.putExtra("ERR_CODE", i2);
        intent.putExtra("REQ_SIZE", 0);
        intent.putExtra("SOURCE", str2);
        intent.putExtra("EXPORT_SESSION_TIME ", str3);
        context.sendBroadcast(intent, "com.wssnps.permission.COM_WSSNPS");
    }

    public static void set5gPreferChecked(Context context, int i) {
        Log.d("WifiApSmartSwitchBackupRestore", " set5gPreferChecked " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_5G_checked", i);
    }

    public static void setPMFChecked(Context context, int i) {
        Log.d("WifiApSmartSwitchBackupRestore", " setPMFChecked " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_pmf_checked", i);
    }

    public static void setWIFISharing(Context context, int i) {
        Log.d("WifiApSmartSwitchBackupRestore", " setWIFISharing " + i);
        Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_wifi_sharing", i);
    }

    public final int decryptForRestore(int i, String str, String str2, String str3) {
        InputStream inputStream;
        FileInputStream fileInputStream;
        int i2 = -1;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    inputStream = decryptStream(fileInputStream, i, str3);
                    try {
                        try {
                            FileOutputStream fileOutputStream2 = new FileOutputStream(str2);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int read = inputStream.read(bArr, 0, 1024);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream2.write(bArr, 0, read);
                                }
                                fileOutputStream2.close();
                                inputStream.close();
                                try {
                                    fileInputStream.close();
                                } catch (Exception unused) {
                                }
                                i2 = 0;
                            } catch (Exception e) {
                                e = e;
                                fileOutputStream = fileOutputStream2;
                                Log.e("WifiApSmartSwitchBackupRestore", "error : " + e);
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception unused2) {
                                    }
                                }
                                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                        i2,
                                        "decryptForRestore Everything OK?0:1 - ret",
                                        "WifiApSmartSwitchBackupRestore");
                                return i2;
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e2) {
                                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                                                "ioexception ",
                                                e2,
                                                "WifiApSmartSwitchBackupRestore");
                                        throw th;
                                    }
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception unused3) {
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                } catch (Exception e4) {
                    e = e4;
                    inputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = null;
                }
            } catch (Exception e5) {
                e = e5;
                inputStream = null;
                fileInputStream = null;
            } catch (Throwable th4) {
                th = th4;
                inputStream = null;
                fileInputStream = null;
            }
        } catch (IOException e6) {
            QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                    "ioexception ", e6, "WifiApSmartSwitchBackupRestore");
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i2, "decryptForRestore Everything OK?0:1 - ret", "WifiApSmartSwitchBackupRestore");
        return i2;
    }

    public final InputStream decryptStream(InputStream inputStream, int i, String str) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bArr = new byte[cipher.getBlockSize()];
        if (inputStream.read(bArr) <= 0) {
            Log.d("WifiApSmartSwitchBackupRestore", "Error reading bytes into iv");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            this.salt = bArr2;
            if (inputStream.read(bArr2) <= 0) {
                Log.d("WifiApSmartSwitchBackupRestore", "Error reading bytes into salt");
            }
            generateSHA256SecretKey = generatePBKDF2SecretKey(str);
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey(str);
        }
        cipher.init(2, generateSHA256SecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, cipher);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r7v5 */
    public final int encryptForBackup(int i, String str, String str2, String str3) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        int i2 = -1;
        OutputStream outputStream = null;
        try {
        } catch (IOException e) {
            QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                    "ioexception ", e, "WifiApSmartSwitchBackupRestore");
        }
        try {
            try {
                fileInputStream = new FileInputStream((String) str);
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                str = 0;
                fileInputStream = null;
            }
            try {
                fileOutputStream = new FileOutputStream(str2);
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                str = 0;
                if (0 != 0) {
                    try {
                        outputStream.close();
                    } catch (IOException e4) {
                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                                "ioexception ", e4, "WifiApSmartSwitchBackupRestore");
                        throw th;
                    }
                }
                if (str != 0) {
                    str.close();
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
            try {
                outputStream = encryptStream(i, fileOutputStream, str3);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr, 0, 1024);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, read);
                }
                outputStream.close();
                fileOutputStream.close();
                try {
                    fileInputStream.close();
                } catch (Exception unused2) {
                }
                i2 = 0;
            } catch (Exception e5) {
                e = e5;
                Log.e("WifiApSmartSwitchBackupRestore", "error : " + e);
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused3) {
                    }
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2, "Everything OK?0:1 - ret", "WifiApSmartSwitchBackupRestore");
                return i2;
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i2, "Everything OK?0:1 - ret", "WifiApSmartSwitchBackupRestore");
            return i2;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final OutputStream encryptStream(int i, OutputStream outputStream, String str) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bArr = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        if (i == 1) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] bArr2 = new byte[16];
            this.salt = bArr2;
            secureRandom.nextBytes(bArr2);
            outputStream.write(this.salt);
            generateSHA256SecretKey = generatePBKDF2SecretKey(str);
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey(str);
        }
        cipher.init(1, generateSHA256SecretKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, cipher);
    }

    public final SecretKeySpec generatePBKDF2SecretKey(String str) {
        return new SecretKeySpec(
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                        .generateSecret(new PBEKeySpec(str.toCharArray(), this.salt, 1000, 256))
                        .getEncoded(),
                "AES");
    }

    public final int get5gPreferChecked(Context context) {
        SemWifiManager semWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mSemWifiManager = semWifiManager;
        semWifiManager.getSoftApConfiguration();
        int[] bandArray = WifiApSoftApUtils.getBandArray(context);
        return (bandArray.length == 1 && bandArray[0] == 2) ? 1 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0714 A[Catch: IOException -> 0x070f, TRY_LEAVE, TryCatch #22 {IOException -> 0x070f, blocks: (B:131:0x070b, B:120:0x0714), top: B:130:0x070b }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x070b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x073f A[Catch: IOException -> 0x073a, TRY_LEAVE, TryCatch #0 {IOException -> 0x073a, blocks: (B:143:0x0736, B:137:0x073f), top: B:142:0x0736 }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0736 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x06e8 A[Catch: IOException -> 0x06e3, TRY_LEAVE, TryCatch #25 {IOException -> 0x06e3, blocks: (B:155:0x06df, B:149:0x06e8), top: B:154:0x06df }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x06df A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean loadOtherConf(android.content.Context r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 1899
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.loadOtherConf(android.content.Context,"
                    + " java.lang.String):boolean");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        Utils.initMHSFeature(context);
        if (action != null) {
            final String stringExtra = intent.getStringExtra("SAVE_PATH");
            final String stringExtra2 = intent.getStringExtra("SOURCE");
            final String stringExtra3 = intent.getStringExtra("SESSION_KEY");
            final String stringExtra4 = intent.getStringExtra("EXPORT_SESSION_TIME");
            int intExtra = intent.getIntExtra("ACTION", 0);
            final int intExtra2 = intent.getIntExtra("SECURITY_LEVEL", 0);
            intent.getStringArrayListExtra("EXTRA_BACKUP_ITEM");
            if (action.equals("com.samsung.android.intent.action.REQUEST_BACKUP_HOTSPOT_SETTING")) {
                if (intExtra == 2) {
                    mIsCanceled = true;
                    return;
                }
                if (intExtra != 0) {
                    Log.e("WifiApSmartSwitchBackupRestore", "Sending Response FAIL");
                    sendResponse(
                            1,
                            3,
                            context,
                            "com.samsung.android.intent.action.RESPONSE_BACKUP_HOTSPOT_SETTING",
                            stringExtra2,
                            stringExtra4);
                    return;
                } else {
                    mIsCanceled = false;
                    final int i = 0;
                    new Thread(
                                    new Runnable(this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.1
                                        public final /* synthetic */ WifiApSmartSwitchBackupRestore
                                                this$0;

                                        {
                                            this.this$0 = this;
                                        }

                                        /* JADX WARN: Multi-variable type inference failed */
                                        /* JADX WARN: Removed duplicated region for block: B:106:0x0091 A[Catch: all -> 0x005b, TryCatch #6 {all -> 0x005b, blocks: (B:6:0x0036, B:8:0x0054, B:10:0x0072, B:12:0x007d, B:14:0x0083, B:15:0x00a5, B:17:0x00da, B:18:0x00fa, B:20:0x010f, B:95:0x00e0, B:97:0x00e6, B:99:0x00ee, B:102:0x00f7, B:103:0x0087, B:105:0x008d, B:106:0x0091, B:108:0x0097, B:109:0x009b, B:111:0x009e, B:112:0x00a2, B:113:0x005f, B:116:0x0068, B:118:0x006e), top: B:5:0x0036, outer: #5 }] */
                                        /* JADX WARN: Removed duplicated region for block: B:12:0x007d A[Catch: all -> 0x005b, TryCatch #6 {all -> 0x005b, blocks: (B:6:0x0036, B:8:0x0054, B:10:0x0072, B:12:0x007d, B:14:0x0083, B:15:0x00a5, B:17:0x00da, B:18:0x00fa, B:20:0x010f, B:95:0x00e0, B:97:0x00e6, B:99:0x00ee, B:102:0x00f7, B:103:0x0087, B:105:0x008d, B:106:0x0091, B:108:0x0097, B:109:0x009b, B:111:0x009e, B:112:0x00a2, B:113:0x005f, B:116:0x0068, B:118:0x006e), top: B:5:0x0036, outer: #5 }] */
                                        /* JADX WARN: Removed duplicated region for block: B:17:0x00da A[Catch: all -> 0x005b, TryCatch #6 {all -> 0x005b, blocks: (B:6:0x0036, B:8:0x0054, B:10:0x0072, B:12:0x007d, B:14:0x0083, B:15:0x00a5, B:17:0x00da, B:18:0x00fa, B:20:0x010f, B:95:0x00e0, B:97:0x00e6, B:99:0x00ee, B:102:0x00f7, B:103:0x0087, B:105:0x008d, B:106:0x0091, B:108:0x0097, B:109:0x009b, B:111:0x009e, B:112:0x00a2, B:113:0x005f, B:116:0x0068, B:118:0x006e), top: B:5:0x0036, outer: #5 }] */
                                        /* JADX WARN: Removed duplicated region for block: B:20:0x010f A[Catch: all -> 0x005b, TRY_LEAVE, TryCatch #6 {all -> 0x005b, blocks: (B:6:0x0036, B:8:0x0054, B:10:0x0072, B:12:0x007d, B:14:0x0083, B:15:0x00a5, B:17:0x00da, B:18:0x00fa, B:20:0x010f, B:95:0x00e0, B:97:0x00e6, B:99:0x00ee, B:102:0x00f7, B:103:0x0087, B:105:0x008d, B:106:0x0091, B:108:0x0097, B:109:0x009b, B:111:0x009e, B:112:0x00a2, B:113:0x005f, B:116:0x0068, B:118:0x006e), top: B:5:0x0036, outer: #5 }] */
                                        /* JADX WARN: Removed duplicated region for block: B:26:0x0150  */
                                        /* JADX WARN: Removed duplicated region for block: B:29:0x015e A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                        /* JADX WARN: Removed duplicated region for block: B:94:0x00de  */
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        private final void
                                                run$com$samsung$android$settings$wifi$mobileap$WifiApSmartSwitchBackupRestore$1() {
                                            /*
                                                Method dump skipped, instructions count: 716
                                                To view this dump change 'Code comments level' option to 'DEBUG'
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.AnonymousClass1.run$com$samsung$android$settings$wifi$mobileap$WifiApSmartSwitchBackupRestore$1():void");
                                        }

                                        /* JADX WARN: Code restructure failed: missing block: B:58:0x0157, code lost:

                                           if ("model".equalsIgnoreCase(r0) != false) goto L62;
                                        */
                                        /* JADX WARN: Removed duplicated region for block: B:139:0x0489 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                        /* JADX WARN: Removed duplicated region for block: B:146:? A[SYNTHETIC] */
                                        /* JADX WARN: Removed duplicated region for block: B:214:0x0495 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                        /* JADX WARN: Removed duplicated region for block: B:241:0x0546 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                        /* JADX WARN: Removed duplicated region for block: B:248:? A[SYNTHETIC] */
                                        @Override // java.lang.Runnable
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        public final void run() {
                                            /*
                                                Method dump skipped, instructions count: 1370
                                                To view this dump change 'Code comments level' option to 'DEBUG'
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.AnonymousClass1.run():void");
                                        }
                                    })
                            .start();
                    return;
                }
            }
            if (action.equals(
                    "com.samsung.android.intent.action.REQUEST_RESTORE_HOTSPOT_SETTING")) {
                if (intExtra == 2) {
                    mIsCanceled = true;
                    return;
                }
                if (intExtra != 0) {
                    Log.e("WifiApSmartSwitchBackupRestore", "Sending Response FAIL");
                    sendResponse(
                            1,
                            3,
                            context,
                            "com.samsung.android.intent.action.RESPONSE_RESTORE_HOTSPOT_SETTING",
                            stringExtra2,
                            stringExtra4);
                } else {
                    mIsCanceled = false;
                    final int i2 = 1;
                    new Thread(
                                    new Runnable(this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.1
                                        public final /* synthetic */ WifiApSmartSwitchBackupRestore
                                                this$0;

                                        {
                                            this.this$0 = this;
                                        }

                                        private final void
                                                run$com$samsung$android$settings$wifi$mobileap$WifiApSmartSwitchBackupRestore$1() {
                                            /*
                                                Method dump skipped, instructions count: 716
                                                To view this dump change 'Code comments level' option to 'DEBUG'
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.AnonymousClass1.run$com$samsung$android$settings$wifi$mobileap$WifiApSmartSwitchBackupRestore$1():void");
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            /*
                                                Method dump skipped, instructions count: 1370
                                                To view this dump change 'Code comments level' option to 'DEBUG'
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.AnonymousClass1.run():void");
                                        }
                                    })
                            .start();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0459 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x046b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeOtherConf(android.content.Context r21) {
        /*
            Method dump skipped, instructions count: 1153
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApSmartSwitchBackupRestore.writeOtherConf(android.content.Context):void");
    }
}
