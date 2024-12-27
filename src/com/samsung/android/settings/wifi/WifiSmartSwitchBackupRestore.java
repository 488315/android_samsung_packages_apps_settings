package com.samsung.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.BitSet;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiSmartSwitchBackupRestore extends BroadcastReceiver {
    public static boolean mIsCanceled;
    public Cipher cipher;
    public String mExportSessionTime;
    public boolean mFastTrack;
    public int mSecurityLevel;
    public String mSessionKey;
    public String mSource;
    public byte[] salt;
    public SecretKeySpec secretKey;
    public String xmlConf;
    public String xmlModel;
    public String xmlNewConf;
    public String xmlOldConf;
    public String xmlSemConfJson;
    public static final String[] mPermissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static final String[] actionStrings = {"START", "FINISH", "CANCEL"};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiBRthread implements Runnable {
        public final Context mContext;
        public final Intent mIntent;
        public final SemWifiManager mSemWifiManager;
        public final WifiManager mWifiManager;

        public WifiBRthread(Context context, Intent intent) {
            this.mContext = context;
            this.mIntent = intent;
            this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        }

        public static void createBnRFile(String str) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
            } catch (IOException e) {
                QrCamera$DecodingTask$$ExternalSyntheticOutline0.m("createBnRFile exception: ", e, "WifiSmartSwitchBackupRestore");
            }
        }

        public static byte[] inputStreamToByteArray(InputStream inputStream) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                byte[] bArr2 = null;
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        bArr2 = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        return bArr2;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                } catch (IOException e) {
                    e.printStackTrace();
                    return bArr2;
                }
            }
        }

        public static String makeString(BitSet bitSet, String[] strArr) {
            StringBuilder sb = new StringBuilder();
            BitSet bitSet2 = bitSet.get(0, strArr.length);
            int i = -1;
            while (true) {
                i = bitSet2.nextSetBit(i + 1);
                if (i == -1) {
                    break;
                }
                sb.append(strArr[i].replace('_', '-'));
                sb.append(' ');
            }
            if (bitSet2.cardinality() > 0) {
                sb.setLength(sb.length() - 1);
            }
            return sb.toString();
        }

        public static String readSemConfFile() {
            BufferedReader newBufferedReader = Files.newBufferedReader(Paths.get("/data/misc/wifi_share_profile/semconfigurations_restore.json", new String[0]), StandardCharsets.UTF_8);
            String str = ApnSettings.MVNO_NONE;
            while (true) {
                try {
                    String readLine = newBufferedReader.readLine();
                    if (readLine == null) {
                        newBufferedReader.close();
                        return str;
                    }
                    str = str + readLine;
                } catch (Throwable th) {
                    if (newBufferedReader != null) {
                        try {
                            newBufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        public static void throwAwayOldStyle() {
            String readLine;
            Log.d("WifiSmartSwitchBackupRestore", "throwAwayOldStyle");
            FileWriter fileWriter = null;
            try {
                try {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader("/data/misc/wifi_share_profile/restore.conf"));
                        String str = ApnSettings.MVNO_NONE;
                        while (bufferedReader.ready() && ((readLine = bufferedReader.readLine()) == null || !readLine.startsWith("New_Version_Style"))) {
                            try {
                            } catch (Throwable th) {
                                th = th;
                            }
                        }
                        while (bufferedReader.ready()) {
                            str = str + bufferedReader.readLine() + "\n";
                        }
                        FileWriter fileWriter2 = new FileWriter("/data/misc/wifi_share_profile/restore.conf");
                        try {
                            fileWriter2.write(str);
                            fileWriter2.flush();
                            try {
                                bufferedReader.close();
                                fileWriter2.close();
                            } catch (IOException e) {
                                e = e;
                                fileWriter = fileWriter2;
                                Log.e("WifiSmartSwitchBackupRestore", "throwAwayOldStyle IOException " + e);
                                if (fileWriter != null) {
                                    fileWriter.close();
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                fileWriter = fileWriter2;
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e2) {
                                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m("throwAwayOldStyle try IOException ", e2, "WifiSmartSwitchBackupRestore");
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fileWriter = fileWriter2;
                            try {
                                bufferedReader.close();
                            } catch (Throwable th4) {
                                th.addSuppressed(th4);
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m("throwAwayOldStyle try IOException ", e3, "WifiSmartSwitchBackupRestore");
                    }
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        }

        public final void backupSemWifiConfiguration() {
            String retrieveSemWifiConfigsBackupData = this.mSemWifiManager.retrieveSemWifiConfigsBackupData();
            if (retrieveSemWifiConfigsBackupData == null) {
                Log.e("WifiSmartSwitchBackupRestore", "SemWifiConfiguration is null");
                return;
            }
            Log.d("WifiSmartSwitchBackupRestore", "/data/misc/wifi_share_profile/tmp_semconfigurations.json Size: " + retrieveSemWifiConfigsBackupData.length());
            createBnRFile("/data/misc/wifi_share_profile/tmp_semconfigurations.json");
            Path path = Paths.get("/data/misc/wifi_share_profile/tmp_semconfigurations.json", new String[0]);
            try {
                BufferedWriter newBufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                try {
                    newBufferedWriter.write(retrieveSemWifiConfigsBackupData);
                    newBufferedWriter.close();
                } finally {
                }
            } catch (IOException e) {
                QrCamera$DecodingTask$$ExternalSyntheticOutline0.m("backup file IO exception ", e, "WifiSmartSwitchBackupRestore");
            }
            if (encrypt(0, "/data/misc/wifi_share_profile/tmp_semconfigurations.json", "/data/misc/wifi_share_profile/semconfigurations.json") == -1) {
                Log.e("WifiSmartSwitchBackupRestore", "encrypt new file fail");
                sendResponse(0, 1, 1);
            }
            try {
                Files.delete(path);
            } catch (IOException e2) {
                Log.e("WifiSmartSwitchBackupRestore", e2.getMessage());
            }
        }

        public final InputStream decryptStream(InputStream inputStream) {
            WifiSmartSwitchBackupRestore.this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bArr = new byte[WifiSmartSwitchBackupRestore.this.cipher.getBlockSize()];
            inputStream.read(bArr);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
            WifiSmartSwitchBackupRestore wifiSmartSwitchBackupRestore = WifiSmartSwitchBackupRestore.this;
            if (wifiSmartSwitchBackupRestore.mSecurityLevel == 1) {
                byte[] bArr2 = new byte[16];
                wifiSmartSwitchBackupRestore.salt = bArr2;
                inputStream.read(bArr2);
                WifiSmartSwitchBackupRestore.this.secretKey = generatePBKDF2SecretKey();
            } else {
                wifiSmartSwitchBackupRestore.secretKey = generateSHA256SecretKey();
            }
            WifiSmartSwitchBackupRestore wifiSmartSwitchBackupRestore2 = WifiSmartSwitchBackupRestore.this;
            wifiSmartSwitchBackupRestore2.cipher.init(2, wifiSmartSwitchBackupRestore2.secretKey, ivParameterSpec);
            return new CipherInputStream(inputStream, WifiSmartSwitchBackupRestore.this.cipher);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:16:0x007d A[Catch: all -> 0x0087, Exception -> 0x0090, TryCatch #24 {Exception -> 0x0090, all -> 0x0087, blocks: (B:13:0x0078, B:16:0x007d, B:18:0x0083), top: B:12:0x0078 }] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00ba A[Catch: IOException -> 0x00be, TRY_ENTER, TRY_LEAVE, TryCatch #21 {IOException -> 0x00be, blocks: (B:24:0x00ba, B:53:0x00f9), top: B:2:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00af A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0099 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0097 A[EDGE_INSN: B:44:0x0097->B:20:0x0097 BREAK  A[LOOP:0: B:14:0x007a->B:18:0x0083], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00f9 A[Catch: IOException -> 0x00be, TRY_ENTER, TRY_LEAVE, TryCatch #21 {IOException -> 0x00be, blocks: (B:24:0x00ba, B:53:0x00f9), top: B:2:0x000c }] */
        /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x00e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x00db A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0121 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:86:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:87:0x0117 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:92:0x010d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0103 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r11v0, types: [com.samsung.android.settings.wifi.WifiSmartSwitchBackupRestore$WifiBRthread] */
        /* JADX WARN: Type inference failed for: r11v16, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v14 */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v19 */
        /* JADX WARN: Type inference failed for: r12v21 */
        /* JADX WARN: Type inference failed for: r12v8, types: [java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r14v1 */
        /* JADX WARN: Type inference failed for: r14v16 */
        /* JADX WARN: Type inference failed for: r14v18 */
        /* JADX WARN: Type inference failed for: r14v19 */
        /* JADX WARN: Type inference failed for: r14v2 */
        /* JADX WARN: Type inference failed for: r14v20 */
        /* JADX WARN: Type inference failed for: r14v22 */
        /* JADX WARN: Type inference failed for: r14v3, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r14v6, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r14v7 */
        /* JADX WARN: Type inference failed for: r14v8 */
        /* JADX WARN: Type inference failed for: r14v9 */
        /* JADX WARN: Type inference failed for: r6v19 */
        /* JADX WARN: Type inference failed for: r6v21 */
        /* JADX WARN: Type inference failed for: r6v23 */
        /* JADX WARN: Type inference failed for: r6v27 */
        /* JADX WARN: Type inference failed for: r6v28 */
        /* JADX WARN: Type inference failed for: r6v4, types: [java.io.OutputStream] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int encrypt(int r12, java.lang.String r13, java.lang.String r14) {
            /*
                Method dump skipped, instructions count: 298
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.wifi.WifiSmartSwitchBackupRestore.WifiBRthread.encrypt(int, java.lang.String, java.lang.String):int");
        }

        public final OutputStream encryptStream(OutputStream outputStream) {
            WifiSmartSwitchBackupRestore.this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bArr = new byte[WifiSmartSwitchBackupRestore.this.cipher.getBlockSize()];
            new SecureRandom().nextBytes(bArr);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
            outputStream.write(bArr);
            WifiSmartSwitchBackupRestore wifiSmartSwitchBackupRestore = WifiSmartSwitchBackupRestore.this;
            if (wifiSmartSwitchBackupRestore.mSecurityLevel == 1) {
                byte[] bArr2 = new byte[16];
                new SecureRandom().nextBytes(bArr2);
                wifiSmartSwitchBackupRestore.salt = bArr2;
                outputStream.write(WifiSmartSwitchBackupRestore.this.salt);
                WifiSmartSwitchBackupRestore.this.secretKey = generatePBKDF2SecretKey();
            } else {
                wifiSmartSwitchBackupRestore.secretKey = generateSHA256SecretKey();
            }
            WifiSmartSwitchBackupRestore wifiSmartSwitchBackupRestore2 = WifiSmartSwitchBackupRestore.this;
            wifiSmartSwitchBackupRestore2.cipher.init(1, wifiSmartSwitchBackupRestore2.secretKey, ivParameterSpec);
            return new CipherOutputStream(outputStream, WifiSmartSwitchBackupRestore.this.cipher);
        }

        public final SecretKeySpec generatePBKDF2SecretKey() {
            return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(WifiSmartSwitchBackupRestore.this.mSessionKey.toCharArray(), WifiSmartSwitchBackupRestore.this.salt, 1000, 256)).getEncoded(), "AES");
        }

        public final SecretKeySpec generateSHA256SecretKey() {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(WifiSmartSwitchBackupRestore.this.mSessionKey.getBytes("UTF-8"));
            byte[] bArr = new byte[16];
            System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
            return new SecretKeySpec(bArr, "AES");
        }

        /* JADX WARN: Removed duplicated region for block: B:68:0x02a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:81:0x026a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:89:0x02b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:96:? A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void makeOldStyle(java.lang.String r21) {
            /*
                Method dump skipped, instructions count: 714
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.wifi.WifiSmartSwitchBackupRestore.WifiBRthread.makeOldStyle(java.lang.String):void");
        }

        public final int makeXML() {
            Throwable th;
            Log.d("WifiSmartSwitchBackupRestore", "makeXML");
            FileWriter fileWriter = null;
            try {
                try {
                    try {
                        XmlSerializer newSerializer = Xml.newSerializer();
                        FileWriter fileWriter2 = new FileWriter(new File("/data/misc/wifi_share_profile/wifi_wpaconf.xml"));
                        try {
                            newSerializer.setOutput(fileWriter2);
                            newSerializer.startDocument("UTF-8", Boolean.TRUE);
                            newSerializer.startTag(ApnSettings.MVNO_NONE, "Wi-Fi");
                            newSerializer.startTag(ApnSettings.MVNO_NONE, "model");
                            String str = Build.MODEL;
                            int length = str.length();
                            if (length > 8 && "SAMSUNG-".equals(str.substring(0, 8))) {
                                str = str.substring(8, length);
                            }
                            newSerializer.text(str);
                            newSerializer.endTag(ApnSettings.MVNO_NONE, "model");
                            newSerializer.startTag(ApnSettings.MVNO_NONE, "conf");
                            newSerializer.text("wpa_supplicant.conf");
                            newSerializer.endTag(ApnSettings.MVNO_NONE, "conf");
                            newSerializer.startTag(ApnSettings.MVNO_NONE, "newConf");
                            newSerializer.text("WifiConfigStore.xml");
                            newSerializer.endTag(ApnSettings.MVNO_NONE, "newConf");
                            if (this.mSemWifiManager.isSupportedAutoWifi()) {
                                newSerializer.startTag(ApnSettings.MVNO_NONE, "semConfJson");
                                newSerializer.text("semconfigurations.json");
                                newSerializer.endTag(ApnSettings.MVNO_NONE, "semConfJson");
                            }
                            newSerializer.endTag(ApnSettings.MVNO_NONE, "Wi-Fi");
                            newSerializer.endDocument();
                            fileWriter2.close();
                            return 0;
                        } catch (Exception e) {
                            e = e;
                            fileWriter = fileWriter2;
                            Log.e("WifiSmartSwitchBackupRestore", "makeXML exception " + e);
                            if (fileWriter != null) {
                                fileWriter.close();
                            }
                            return -1;
                        } catch (Throwable th2) {
                            th = th2;
                            fileWriter = fileWriter2;
                            if (fileWriter == null) {
                                throw th;
                            }
                            try {
                                fileWriter.close();
                                throw th;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                return -1;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v10 */
        /* JADX WARN: Type inference failed for: r3v35 */
        /* JADX WARN: Type inference failed for: r3v36 */
        public final int readXML() {
            FileReader fileReader;
            ?? r3;
            int i = -1;
            FileReader fileReader2 = null;
            FileReader fileReader3 = null;
            FileReader fileReader4 = null;
            try {
                try {
                    XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
                    newInstance.setNamespaceAware(true);
                    XmlPullParser newPullParser = newInstance.newPullParser();
                    fileReader = new FileReader(new File("/data/misc/wifi_share_profile/wifi_wpaconf.xml"));
                    try {
                        newPullParser.setInput(fileReader);
                        int eventType = newPullParser.getEventType();
                        String str = ApnSettings.MVNO_NONE;
                        boolean z = false;
                        r3 = eventType;
                        while (r3 != 1) {
                            if (r3 == 2) {
                                String name = newPullParser.getName();
                                if (!"model".equals(name) && !"conf".equals(name) && !"newConf".equals(name) && !"semConfJson".equals(name)) {
                                    str = name;
                                }
                                str = name;
                                z = true;
                            } else if (r3 == 4) {
                                if (z && "model".equals(str)) {
                                    WifiSmartSwitchBackupRestore.this.xmlModel = newPullParser.getText();
                                    Log.d("WifiSmartSwitchBackupRestore", "[readXML] model : " + WifiSmartSwitchBackupRestore.this.xmlModel);
                                } else if (z && "conf".equals(str)) {
                                    WifiSmartSwitchBackupRestore.this.xmlOldConf = newPullParser.getText();
                                    Log.d("WifiSmartSwitchBackupRestore", "[readXML] wpa_supplicant.conf : " + WifiSmartSwitchBackupRestore.this.xmlOldConf);
                                } else if (z && "newConf".equals(str)) {
                                    WifiSmartSwitchBackupRestore.this.xmlNewConf = newPullParser.getText();
                                    Log.d("WifiSmartSwitchBackupRestore", "[readXML] WifiConfigStore.xml : " + WifiSmartSwitchBackupRestore.this.xmlNewConf);
                                } else if (z && "semConfJson".equals(str) && this.mSemWifiManager.isSupportedAutoWifi()) {
                                    WifiSmartSwitchBackupRestore.this.xmlSemConfJson = newPullParser.getText();
                                    Log.d("WifiSmartSwitchBackupRestore", "[readXML] semConfJson.json : " + WifiSmartSwitchBackupRestore.this.xmlSemConfJson);
                                }
                            } else if (r3 == 3) {
                                String name2 = newPullParser.getName();
                                if (z && ("model".equalsIgnoreCase(name2) || "conf".equalsIgnoreCase(name2) || "semConfJson".equalsIgnoreCase(name2) || "newConf".equalsIgnoreCase(name2))) {
                                    z = false;
                                }
                            }
                            r3 = newPullParser.next();
                            z = z;
                        }
                    } catch (RuntimeException e) {
                        e = e;
                        fileReader3 = fileReader;
                        Log.e("WifiSmartSwitchBackupRestore", "runtimeException " + e);
                        fileReader2 = fileReader3;
                        if (fileReader3 != null) {
                            try {
                                fileReader3.close();
                                fileReader2 = fileReader3;
                            } catch (Exception unused) {
                            }
                        }
                        return i;
                    } catch (Exception e2) {
                        e = e2;
                        fileReader4 = fileReader;
                        Log.e("WifiSmartSwitchBackupRestore", "read XML Error " + e);
                        fileReader2 = fileReader4;
                        if (fileReader4 != null) {
                            try {
                                fileReader4.close();
                                fileReader2 = fileReader4;
                            } catch (Exception unused2) {
                                return -1;
                            }
                        }
                        return i;
                    } catch (Throwable th) {
                        th = th;
                        fileReader2 = fileReader;
                        if (fileReader2 != null) {
                            try {
                                fileReader2.close();
                            } catch (Exception unused3) {
                                return 0;
                            }
                        }
                        throw th;
                    }
                } catch (RuntimeException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                }
                try {
                    fileReader.close();
                    i = 0;
                    fileReader2 = r3;
                    return i;
                } catch (Exception unused4) {
                    return 0;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        public final void restoreSemConf() {
            if (TextUtils.isEmpty(WifiSmartSwitchBackupRestore.this.xmlSemConfJson)) {
                Log.e("WifiSmartSwitchBackupRestore", "xmlSemConfJson is empty");
                return;
            }
            if (encrypt(1, "/data/misc/wifi_share_profile//" + WifiSmartSwitchBackupRestore.this.xmlSemConfJson, "/data/misc/wifi_share_profile/semconfigurations_restore.json") == -1) {
                Log.e("WifiSmartSwitchBackupRestore", "decrypt fail");
                sendResponse(1, 1, 1);
                return;
            }
            try {
                this.mSemWifiManager.restoreSemConfigurationsBackupData(readSemConfFile());
                Files.delete(Paths.get("/data/misc/wifi_share_profile/semconfigurations_restore.json", new String[0]));
            } catch (IOException e) {
                Log.e("WifiSmartSwitchBackupRestore", e.getMessage());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:166:0x051d  */
        /* JADX WARN: Removed duplicated region for block: B:180:0x0511 A[Catch: IOException -> 0x050d, TRY_LEAVE, TryCatch #23 {IOException -> 0x050d, blocks: (B:190:0x0509, B:180:0x0511), top: B:189:0x0509 }] */
        /* JADX WARN: Removed duplicated region for block: B:189:0x0509 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:197:0x04d6 A[Catch: IOException -> 0x04d2, TRY_LEAVE, TryCatch #21 {IOException -> 0x04d2, blocks: (B:203:0x04ce, B:197:0x04d6), top: B:202:0x04ce }] */
        /* JADX WARN: Removed duplicated region for block: B:202:0x04ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:209:0x0536 A[Catch: IOException -> 0x0532, TRY_LEAVE, TryCatch #5 {IOException -> 0x0532, blocks: (B:218:0x052e, B:209:0x0536), top: B:217:0x052e }] */
        /* JADX WARN: Removed duplicated region for block: B:216:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:217:0x052e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r10v14, types: [java.io.DataInputStream] */
        /* JADX WARN: Type inference failed for: r10v20 */
        /* JADX WARN: Type inference failed for: r10v21 */
        /* JADX WARN: Type inference failed for: r10v22 */
        /* JADX WARN: Type inference failed for: r10v23 */
        /* JADX WARN: Type inference failed for: r10v6 */
        /* JADX WARN: Type inference failed for: r10v7 */
        /* JADX WARN: Type inference failed for: r10v8 */
        /* JADX WARN: Type inference failed for: r10v9 */
        /* JADX WARN: Type inference failed for: r11v10 */
        /* JADX WARN: Type inference failed for: r11v11 */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13 */
        /* JADX WARN: Type inference failed for: r11v14 */
        /* JADX WARN: Type inference failed for: r11v15 */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        /* JADX WARN: Type inference failed for: r11v18 */
        /* JADX WARN: Type inference failed for: r11v19 */
        /* JADX WARN: Type inference failed for: r11v20 */
        /* JADX WARN: Type inference failed for: r11v22 */
        /* JADX WARN: Type inference failed for: r11v23 */
        /* JADX WARN: Type inference failed for: r11v24 */
        /* JADX WARN: Type inference failed for: r11v25 */
        /* JADX WARN: Type inference failed for: r11v26 */
        /* JADX WARN: Type inference failed for: r11v27 */
        /* JADX WARN: Type inference failed for: r11v28 */
        /* JADX WARN: Type inference failed for: r11v29 */
        /* JADX WARN: Type inference failed for: r11v30 */
        /* JADX WARN: Type inference failed for: r11v31 */
        /* JADX WARN: Type inference failed for: r11v32 */
        /* JADX WARN: Type inference failed for: r11v33 */
        /* JADX WARN: Type inference failed for: r11v34 */
        /* JADX WARN: Type inference failed for: r11v35 */
        /* JADX WARN: Type inference failed for: r11v36 */
        /* JADX WARN: Type inference failed for: r11v37 */
        /* JADX WARN: Type inference failed for: r11v5 */
        /* JADX WARN: Type inference failed for: r11v6 */
        /* JADX WARN: Type inference failed for: r11v7 */
        /* JADX WARN: Type inference failed for: r11v8 */
        /* JADX WARN: Type inference failed for: r11v9 */
        /* JADX WARN: Type inference failed for: r17v0, types: [com.samsung.android.settings.wifi.WifiSmartSwitchBackupRestore$WifiBRthread] */
        /* JADX WARN: Type inference failed for: r5v19, types: [int] */
        /* JADX WARN: Type inference failed for: r5v20 */
        /* JADX WARN: Type inference failed for: r5v21, types: [java.io.DataInputStream] */
        /* JADX WARN: Type inference failed for: r5v25, types: [java.io.DataInputStream] */
        /* JADX WARN: Type inference failed for: r5v26 */
        /* JADX WARN: Type inference failed for: r5v27, types: [java.io.DataInputStream] */
        /* JADX WARN: Type inference failed for: r5v28 */
        /* JADX WARN: Type inference failed for: r5v29 */
        /* JADX WARN: Type inference failed for: r5v30 */
        /* JADX WARN: Type inference failed for: r5v31 */
        /* JADX WARN: Type inference failed for: r5v32 */
        /* JADX WARN: Type inference failed for: r5v35 */
        /* JADX WARN: Type inference failed for: r5v37 */
        /* JADX WARN: Type inference failed for: r5v38 */
        /* JADX WARN: Type inference failed for: r5v40 */
        /* JADX WARN: Type inference failed for: r5v42 */
        /* JADX WARN: Type inference failed for: r5v44, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r5v50 */
        /* JADX WARN: Type inference failed for: r5v75 */
        /* JADX WARN: Type inference failed for: r5v76 */
        /* JADX WARN: Type inference failed for: r5v77 */
        /* JADX WARN: Type inference failed for: r5v78 */
        /* JADX WARN: Type inference failed for: r5v79 */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        /* JADX WARN: Type inference failed for: r9v19 */
        /* JADX WARN: Type inference failed for: r9v20 */
        /* JADX WARN: Type inference failed for: r9v22, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r9v23 */
        /* JADX WARN: Type inference failed for: r9v24, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r9v25 */
        /* JADX WARN: Type inference failed for: r9v26 */
        /* JADX WARN: Type inference failed for: r9v27 */
        /* JADX WARN: Type inference failed for: r9v37 */
        /* JADX WARN: Type inference failed for: r9v49 */
        /* JADX WARN: Type inference failed for: r9v50 */
        /* JADX WARN: Type inference failed for: r9v51 */
        /* JADX WARN: Type inference failed for: r9v52 */
        /* JADX WARN: Type inference failed for: r9v54 */
        /* JADX WARN: Type inference failed for: r9v55 */
        /* JADX WARN: Type inference failed for: r9v56 */
        /* JADX WARN: Type inference failed for: r9v58 */
        /* JADX WARN: Type inference failed for: r9v59 */
        /* JADX WARN: Type inference failed for: r9v60 */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 1372
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.wifi.WifiSmartSwitchBackupRestore.WifiBRthread.run():void");
        }

        public final void sendResponse(int i, int i2, int i3) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m("r:", ", e:", i2, i3, ",rs:0, isCanceled:"), WifiSmartSwitchBackupRestore.mIsCanceled, "WifiSmartSwitchBackupRestore");
            if (WifiSmartSwitchBackupRestore.mIsCanceled) {
                return;
            }
            Intent intent = new Intent();
            if (i == 0) {
                intent.setAction("com.sec.android.intent.action.RESPONSE_BACKUP_WIFIWPACONF");
                intent.putExtra("EXPORT_SESSION_TIME", WifiSmartSwitchBackupRestore.this.mExportSessionTime);
            } else if (i == 1) {
                intent.setAction("com.sec.android.intent.action.RESPONSE_RESTORE_WIFIWPACONF");
            }
            intent.putExtra("RESULT", i2);
            intent.putExtra("ERR_CODE", i3);
            intent.putExtra("REQ_SIZE", 0);
            intent.putExtra("SOURCE", WifiSmartSwitchBackupRestore.this.mSource);
            if ("SmartSwitch".equals(WifiSmartSwitchBackupRestore.this.mSource) || "SmartSwitchAgent".equals(WifiSmartSwitchBackupRestore.this.mSource)) {
                this.mContext.sendBroadcast(intent, "com.wssnps.permission.COM_WSSNPS");
            } else {
                this.mContext.sendBroadcast(intent);
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (intent.getIntExtra("ACTION", 0) == 2) {
            mIsCanceled = true;
            return;
        }
        mIsCanceled = false;
        if ("com.sec.android.intent.action.REQUEST_BACKUP_WIFIWPACONF".equals(action)) {
            new Thread(new WifiBRthread(context, intent)).start();
        } else if ("com.sec.android.intent.action.REQUEST_RESTORE_WIFIWPACONF".equals(action)) {
            new Thread(new WifiBRthread(context, intent)).start();
        }
    }
}
