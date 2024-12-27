package com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus;

import android.os.SystemProperties;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.sec.ims.configuration.DATA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KddiHttpsUrlConnection {
    public boolean mIsSucceed;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class RequestBodyPart {
        /* JADX WARN: Code restructure failed: missing block: B:24:0x002d, code lost:

           if (r3 != null) goto L35;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static byte[] build(android.content.Context r9) {
            /*
                java.lang.String r0 = "body part written : "
                java.lang.String r1 = "type of request : "
                java.lang.String r2 = "KddiHttpsUrlConnection"
                java.lang.String r3 = "prepare HTTP Request body part"
                android.util.Log.d(r2, r3)
                android.content.res.Resources r3 = r9.getResources()
                r4 = 2131951706(0x7f13005a, float:1.9539834E38)
                java.io.InputStream r3 = r3.openRawResource(r4)
                r4 = 0
                int r5 = r3.available()     // Catch: java.lang.Throwable -> L26 java.io.IOException -> L29
                byte[] r4 = new byte[r5]     // Catch: java.lang.Throwable -> L26 java.io.IOException -> L29
                r3.read(r4)     // Catch: java.lang.Throwable -> L26 java.io.IOException -> L29
            L22:
                r3.close()     // Catch: java.io.IOException -> L2f
                goto L2f
            L26:
                r9 = move-exception
                goto Ld0
            L29:
                r5 = move-exception
                r5.printStackTrace()     // Catch: java.lang.Throwable -> L26
                if (r3 != 0) goto L22
            L2f:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r5 = "dummy data length : "
                r3.<init>(r5)
                int r5 = r4.length
                androidx.preference.Preference$$ExternalSyntheticOutline0.m(r3, r5, r2)
                int r3 = r4.length
                int r3 = r3 + 22
                short r3 = (short) r3
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                java.lang.String r6 = "expected message length : "
                r5.<init>(r6)
                r5.append(r3)
                java.lang.String r5 = r5.toString()
                android.util.Log.d(r2, r5)
                java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream
                r5.<init>()
                java.lang.String r6 = "message type : 16"
                android.util.Log.d(r2, r6)
                r6 = 16
                r5.write(r6)
                r6 = 2
                java.nio.ByteBuffer r7 = java.nio.ByteBuffer.allocate(r6)     // Catch: java.io.IOException -> Lc7
                java.nio.ByteBuffer r3 = r7.putShort(r3)     // Catch: java.io.IOException -> Lc7
                byte[] r3 = r3.array()     // Catch: java.io.IOException -> Lc7
                r5.write(r3)     // Catch: java.io.IOException -> Lc7
                java.lang.String r3 = "2"
                java.lang.String r7 = "persist.sys.kddi_sim_lock"
                java.lang.String r7 = android.os.SystemProperties.get(r7)     // Catch: java.io.IOException -> Lc7
                boolean r3 = r3.equals(r7)     // Catch: java.io.IOException -> Lc7
                r7 = 0
                if (r3 == 0) goto L80
                r3 = r7
                goto L82
            L80:
                r3 = 34
            L82:
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.io.IOException -> Lc7
                r8.<init>(r1)     // Catch: java.io.IOException -> Lc7
                r8.append(r3)     // Catch: java.io.IOException -> Lc7
                java.lang.String r1 = r8.toString()     // Catch: java.io.IOException -> Lc7
                android.util.Log.d(r2, r1)     // Catch: java.io.IOException -> Lc7
                r5.write(r3)     // Catch: java.io.IOException -> Lc7
                byte[] r9 = com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatusUtils.getImei(r9)     // Catch: java.io.IOException -> Lc7
                r5.write(r9)     // Catch: java.io.IOException -> Lc7
                java.lang.String r9 = "pki key id : 0"
                android.util.Log.d(r2, r9)     // Catch: java.io.IOException -> Lc7
                java.nio.ByteBuffer r9 = java.nio.ByteBuffer.allocate(r6)     // Catch: java.io.IOException -> Lc7
                java.nio.ByteBuffer r9 = r9.putShort(r7)     // Catch: java.io.IOException -> Lc7
                byte[] r9 = r9.array()     // Catch: java.io.IOException -> Lc7
                r5.write(r9)     // Catch: java.io.IOException -> Lc7
                r5.write(r4)     // Catch: java.io.IOException -> Lc7
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.io.IOException -> Lc7
                r9.<init>(r0)     // Catch: java.io.IOException -> Lc7
                int r0 = r5.size()     // Catch: java.io.IOException -> Lc7
                r9.append(r0)     // Catch: java.io.IOException -> Lc7
                java.lang.String r9 = r9.toString()     // Catch: java.io.IOException -> Lc7
                android.util.Log.d(r2, r9)     // Catch: java.io.IOException -> Lc7
                goto Lcb
            Lc7:
                r9 = move-exception
                r9.printStackTrace()
            Lcb:
                byte[] r9 = r5.toByteArray()
                return r9
            Ld0:
                if (r3 != 0) goto Ld3
                goto Ld6
            Ld3:
                r3.close()     // Catch: java.io.IOException -> Ld6
            Ld6:
                throw r9
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.KddiHttpsUrlConnection.RequestBodyPart.build(android.content.Context):byte[]");
        }
    }

    public static String getAuthorizationValue() {
        return "Basic "
                + Base64.encodeToString(
                        ("1".equals(SystemProperties.get("persist.sys.kddi_sim_lock"))
                                        ? "aagq3b1NBs:"
                                        : "aHgq8b2NBs:")
                                .getBytes(),
                        2);
    }

    public static ByteBuffer processResponseBodyPart(ByteBuffer byteBuffer) {
        Log.d("KddiHttpsUrlConnection", "process return body part()");
        if (DATA.DM_FIELD_INDEX.PUBLIC_USER_ID.equals(
                SystemProperties.get("persist.sys.kddi_sim_lock"))) {
            Log.e("KddiHttpsUrlConnection", "no response body part available property is 3");
            return null;
        }
        if (byteBuffer == null || !byteBuffer.hasRemaining()) {
            Log.e("KddiHttpsUrlConnection", "no response body part available");
            return null;
        }
        Log.d("KddiHttpsUrlConnection", "message type : " + ((int) byteBuffer.get()));
        short s = byteBuffer.getShort();
        Log.d("KddiHttpsUrlConnection", "entire message length : " + ((int) s));
        Log.d("KddiHttpsUrlConnection", "server status code : " + byteBuffer.getInt());
        int i = byteBuffer.getShort();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "server text length : ", "KddiHttpsUrlConnection");
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        Log.d("KddiHttpsUrlConnection", "server text : ".concat(new String(bArr)));
        int i2 = (s - 9) - i;
        byte[] bArr2 = new byte[i2];
        Log.d("KddiHttpsUrlConnection", "new sim lock policy file length : " + i2);
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream(byteBuffer.remaining());
        byteArrayOutputStream.write(
                byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
        ByteBuffer wrap = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused) {
        }
        return wrap;
    }

    public static ByteBuffer readInputStream(InputStream inputStream) {
        Log.d("KddiHttpsUrlConnection", "read input stream");
        byte[] bArr = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException unused) {
                    }
                    throw th;
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    byteArrayOutputStream.close();
                    return null;
                } catch (IOException unused2) {
                    return null;
                }
            }
        }
        ByteBuffer wrap = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        Log.d("KddiHttpsUrlConnection", "byte buffer read : " + wrap.capacity());
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused3) {
        }
        return wrap;
    }
}
