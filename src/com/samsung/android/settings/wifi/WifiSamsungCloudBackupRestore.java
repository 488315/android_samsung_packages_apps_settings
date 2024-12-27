package com.samsung.android.settings.wifi;

import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticOutline0;

import com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient;
import com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper;
import com.samsung.android.scloud.oem.lib.utils.FileTool;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiSamsungCloudBackupRestore implements ISCloudQBNRClient {
    public boolean newVersion;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiSamsungCloudBackupRestore$1, reason: invalid class name */
    public final class AnonymousClass1 implements FileTool.PDMProgressListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531
                val$listener;

        public /* synthetic */ AnonymousClass1(
                QBNRClientHelper.AnonymousClass2.AnonymousClass1.C00531 c00531, int i) {
            this.$r8$classId = i;
            this.val$listener = c00531;
        }

        @Override // com.samsung.android.scloud.oem.lib.utils.FileTool.PDMProgressListener
        public final void transferred(long j, long j2) {
            switch (this.$r8$classId) {
                case 0:
                    this.val$listener.onProgress(j, j2);
                    Log.d("WifiSamsungCloudBackupRestore", "transferred 1");
                    break;
                default:
                    this.val$listener.onProgress(j, j2);
                    break;
            }
        }
    }

    public static void createBnRFile(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                    "createBnRFile exception: ", e, "WifiSamsungCloudBackupRestore");
        }
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            byte[] bArr2 = null;
            try {
                int read = inputStream.read(bArr);
                if (-1 == read) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:127:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x029d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:164:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x03b1  */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.io.FileWriter] */
    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void backup(
            android.content.Context r24,
            android.os.ParcelFileDescriptor r25,
            com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper.AnonymousClass2.AnonymousClass1
                            .C00531
                    r26) {
        /*
            Method dump skipped, instructions count: 1020
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiSamsungCloudBackupRestore.backup(android.content.Context,"
                    + " android.os.ParcelFileDescriptor,"
                    + " com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2$1$1):void");
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final String getDescription(Context context) {
        return context.getResources().getString(R.string.wifi_settings_title);
    }

    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    public final String getLabel(Context context) {
        return context.getResources().getString(R.string.wifi_settings_title);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v17 */
    /* JADX WARN: Type inference failed for: r14v18 */
    /* JADX WARN: Type inference failed for: r14v19 */
    /* JADX WARN: Type inference failed for: r14v20 */
    /* JADX WARN: Type inference failed for: r14v21 */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v24 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6, types: [java.io.DataInputStream] */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8, types: [java.io.DataInputStream] */
    /* JADX WARN: Type inference failed for: r14v9, types: [java.io.DataInputStream] */
    /* JADX WARN: Type inference failed for: r15v0, types: [com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2$1$1] */
    /* JADX WARN: Type inference failed for: r15v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.samsung.android.scloud.oem.lib.utils.FileTool$PDMProgressListener, com.samsung.android.settings.wifi.WifiSamsungCloudBackupRestore$1] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.io.FileInputStream] */
    @Override // com.samsung.android.scloud.oem.lib.qbnr.ISCloudQBNRClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restore(
            android.content.Context r13,
            android.os.ParcelFileDescriptor r14,
            com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper.AnonymousClass2.AnonymousClass1
                            .C00531
                    r15) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiSamsungCloudBackupRestore.restore(android.content.Context,"
                    + " android.os.ParcelFileDescriptor,"
                    + " com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2$1$1):void");
    }

    public final void throwAwayOldStyle() {
        Log.d("WifiSamsungCloudBackupRestore", "throwAwayOldStyle");
        FileWriter fileWriter = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader =
                            new BufferedReader(
                                    new FileReader("/data/misc/wifi_share_profile/restore.conf"));
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            if (!bufferedReader.ready()) {
                                break;
                            }
                            String readLine = bufferedReader.readLine();
                            if (readLine != null
                                    && readLine.startsWith("New_Version_Style_From_R")) {
                                this.newVersion = true;
                                break;
                            } else {
                                sb.append(readLine);
                                sb.append("\n");
                            }
                        }
                        if (this.newVersion) {
                            sb = new StringBuilder();
                            while (bufferedReader.ready()) {
                                sb.append(bufferedReader.readLine());
                                sb.append("\n");
                            }
                        }
                        FileWriter fileWriter2 =
                                new FileWriter("/data/misc/wifi_share_profile/restore.conf");
                        try {
                            fileWriter2.write(sb.toString());
                            fileWriter2.flush();
                            try {
                                bufferedReader.close();
                                fileWriter2.close();
                            } catch (IOException e) {
                                e = e;
                                fileWriter = fileWriter2;
                                Log.e(
                                        "WifiSamsungCloudBackupRestore",
                                        "throwAwayOldStyle IOException " + e);
                                if (fileWriter != null) {
                                    fileWriter.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                fileWriter = fileWriter2;
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e2) {
                                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                                                "throwAwayOldStyle try IOException ",
                                                e2,
                                                "WifiSamsungCloudBackupRestore");
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            fileWriter = fileWriter2;
                            th = th2;
                            try {
                                bufferedReader.close();
                                throw th;
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                                throw th;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (IOException e4) {
            QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                    "throwAwayOldStyle try IOException ", e4, "WifiSamsungCloudBackupRestore");
        }
    }
}
