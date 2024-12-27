package com.samsung.android.settings.languagepack.apkverifier;

import android.util.Pair;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.viewpager2.widget.ViewPager2$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.CustomDeviceManager;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ApkSignatureV2V3Verifier {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class PlatformNotSupportedException extends Exception {
        public PlatformNotSupportedException(String str) {
            super(str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SignatureInfo {
        public final ByteBuffer signatureBlock;

        public SignatureInfo(ByteBuffer byteBuffer) {
            this.signatureBlock = byteBuffer;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class SignatureNotFoundException extends Exception {
        private static final long serialVersionUID = 1;

        public SignatureNotFoundException(String str) {
            super(str);
        }
    }

    public static SignatureInfo findSignature(RandomAccessFile randomAccessFile, int i) {
        Pair findZipEndOfCentralDirectoryRecord;
        int i2 = 0;
        if (randomAccessFile.length() < 22) {
            findZipEndOfCentralDirectoryRecord = null;
        } else {
            findZipEndOfCentralDirectoryRecord =
                    ZipUtils.findZipEndOfCentralDirectoryRecord(randomAccessFile, 0);
            if (findZipEndOfCentralDirectoryRecord == null) {
                findZipEndOfCentralDirectoryRecord =
                        ZipUtils.findZipEndOfCentralDirectoryRecord(
                                randomAccessFile, CustomDeviceManager.QUICK_PANEL_ALL);
            }
        }
        if (findZipEndOfCentralDirectoryRecord == null) {
            throw new SignatureNotFoundException(
                    "Not an APK file: ZIP End of Central Directory record not found");
        }
        ByteBuffer byteBuffer = (ByteBuffer) findZipEndOfCentralDirectoryRecord.first;
        long longValue = ((Long) findZipEndOfCentralDirectoryRecord.second).longValue();
        long j = longValue - 20;
        if (j >= 0) {
            randomAccessFile.seek(j);
            if (randomAccessFile.readInt() == 1347094023) {
                throw new SignatureNotFoundException("ZIP64 APK not supported");
            }
        }
        ByteOrder order = byteBuffer.order();
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        if (order != byteOrder) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
        long j2 = byteBuffer.getInt(byteBuffer.position() + 16) & 4294967295L;
        if (j2 > longValue) {
            StringBuilder m =
                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                            j2,
                            "ZIP Central Directory offset out of range: ",
                            ". ZIP End of Central Directory offset: ");
            m.append(longValue);
            throw new SignatureNotFoundException(m.toString());
        }
        if (byteBuffer.order() != byteOrder) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
        if ((byteBuffer.getInt(byteBuffer.position() + 12) & 4294967295L) + j2 != longValue) {
            throw new SignatureNotFoundException(
                    "ZIP Central Directory is not immediately followed by End of Central"
                        + " Directory");
        }
        if (j2 < 32) {
            throw new SignatureNotFoundException(
                    ViewPager2$$ExternalSyntheticOutline0.m(
                            j2,
                            "APK too small for APK Signing Block. ZIP Central Directory offset: "));
        }
        ByteBuffer allocate = ByteBuffer.allocate(24);
        allocate.order(byteOrder);
        randomAccessFile.seek(j2 - allocate.capacity());
        randomAccessFile.readFully(allocate.array(), allocate.arrayOffset(), allocate.capacity());
        if (allocate.getLong(8) != 2334950737559900225L
                || allocate.getLong(16) != 3617552046287187010L) {
            throw new SignatureNotFoundException(
                    "No APK Signing Block before ZIP Central Directory");
        }
        long j3 = allocate.getLong(0);
        if (j3 < allocate.capacity() || j3 > 2147483639) {
            throw new SignatureNotFoundException(
                    ViewPager2$$ExternalSyntheticOutline0.m(
                            j3, "APK Signing Block size out of range: "));
        }
        int i3 = (int) (8 + j3);
        long j4 = j2 - i3;
        if (j4 < 0) {
            throw new SignatureNotFoundException(
                    ViewPager2$$ExternalSyntheticOutline0.m(
                            j4, "APK Signing Block offset out of range: "));
        }
        ByteBuffer allocate2 = ByteBuffer.allocate(i3);
        allocate2.order(byteOrder);
        randomAccessFile.seek(j4);
        randomAccessFile.readFully(
                allocate2.array(), allocate2.arrayOffset(), allocate2.capacity());
        long j5 = allocate2.getLong(0);
        if (j5 != j3) {
            StringBuilder m2 =
                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                            j5,
                            "APK Signing Block sizes in header and footer do not match: ",
                            " vs ");
            m2.append(j3);
            throw new SignatureNotFoundException(m2.toString());
        }
        Pair create = Pair.create(allocate2, Long.valueOf(j4));
        ByteBuffer byteBuffer2 = (ByteBuffer) create.first;
        ((Long) create.second).getClass();
        if (byteBuffer2.order() != byteOrder) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
        int capacity = byteBuffer2.capacity() - 24;
        if (capacity < 8) {
            throw new IllegalArgumentException(
                    LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                            capacity, "end < start: ", " < 8"));
        }
        int capacity2 = byteBuffer2.capacity();
        if (capacity > byteBuffer2.capacity()) {
            throw new IllegalArgumentException(
                    HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                            capacity, capacity2, "end > capacity: ", " > "));
        }
        int limit = byteBuffer2.limit();
        int position = byteBuffer2.position();
        try {
            byteBuffer2.position(0);
            byteBuffer2.limit(capacity);
            byteBuffer2.position(8);
            ByteBuffer slice = byteBuffer2.slice();
            slice.order(byteBuffer2.order());
            while (slice.hasRemaining()) {
                i2++;
                if (slice.remaining() < 8) {
                    throw new SignatureNotFoundException(
                            SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                    i2,
                                    "Insufficient data to read size of APK Signing Block entry #"));
                }
                long j6 = slice.getLong();
                if (j6 < 4 || j6 > 2147483647L) {
                    throw new SignatureNotFoundException(
                            "APK Signing Block entry #" + i2 + " size out of range: " + j6);
                }
                int i4 = (int) j6;
                int position2 = slice.position() + i4;
                if (i4 > slice.remaining()) {
                    StringBuilder m3 =
                            RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                    "APK Signing Block entry #",
                                    " size out of range: ",
                                    i2,
                                    i4,
                                    ", available: ");
                    m3.append(slice.remaining());
                    throw new SignatureNotFoundException(m3.toString());
                }
                if (slice.getInt() == i) {
                    return new SignatureInfo(ApkSigningBlockUtils.getByteBuffer(i4 - 4, slice));
                }
                slice.position(position2);
            }
            throw new SignatureNotFoundException(
                    LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                            i, "No block with ID ", " in APK Signing Block."));
        } finally {
            byteBuffer2.position(0);
            byteBuffer2.limit(limit);
            byteBuffer2.position(position);
        }
    }

    public static boolean hasSignature(int i, String str) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            try {
                findSignature(randomAccessFile, i);
                randomAccessFile.close();
                return true;
            } finally {
            }
        } catch (SignatureNotFoundException unused) {
            return false;
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0027: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]) (LINE:40), block:B:50:0x0027 */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readContentDigest(int r5, java.lang.String r6) {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.String r2 = "r"
            r1.<init>(r6, r2)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            com.samsung.android.settings.languagepack.apkverifier.ApkSignatureV2V3Verifier$SignatureInfo r6 = findSignature(r1, r5)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.nio.ByteBuffer r6 = r6.signatureBlock     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29 java.io.IOException -> L6a
            java.nio.ByteBuffer r6 = com.samsung.android.settings.languagepack.apkverifier.ApkSigningBlockUtils.getLengthPrefixedSlice(r6)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29 java.io.IOException -> L6a
            r2 = 0
            r3 = r0
        L15:
            boolean r4 = r6.hasRemaining()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            if (r4 == 0) goto L48
            int r2 = r2 + 1
            java.nio.ByteBuffer r3 = com.samsung.android.settings.languagepack.apkverifier.ApkSigningBlockUtils.getLengthPrefixedSlice(r6)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29 java.lang.Throwable -> L2b
            java.lang.String r3 = verifyContentDigest(r5, r3)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29 java.lang.Throwable -> L2b
            goto L15
        L26:
            r5 = move-exception
            r0 = r1
            goto L85
        L29:
            r5 = move-exception
            goto L77
        L2b:
            r5 = move-exception
            java.lang.SecurityException r6 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            r3.<init>()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.String r4 = "Failed to parse/verify signer #"
            r3.append(r4)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            r3.append(r2)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.String r2 = " block"
            r3.append(r2)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            r6.<init>(r2, r5)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            throw r6     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
        L48:
            r5 = 1
            if (r2 < r5) goto L62
            boolean r5 = r3.isEmpty()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            if (r5 != 0) goto L5a
            r1.close()     // Catch: java.io.IOException -> L55
            goto L59
        L55:
            r5 = move-exception
            r5.printStackTrace()
        L59:
            return r3
        L5a:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.String r6 = "No content digests found"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            throw r5     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
        L62:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.String r6 = "No signers found"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            throw r5     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
        L6a:
            r5 = move-exception
            java.lang.SecurityException r6 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            java.lang.String r2 = "Failed to read list of signers"
            r6.<init>(r2, r5)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            throw r6     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
        L73:
            r5 = move-exception
            goto L85
        L75:
            r5 = move-exception
            r1 = r0
        L77:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L26
            if (r1 == 0) goto L84
            r1.close()     // Catch: java.io.IOException -> L80
            goto L84
        L80:
            r5 = move-exception
            r5.printStackTrace()
        L84:
            return r0
        L85:
            if (r0 == 0) goto L8f
            r0.close()     // Catch: java.io.IOException -> L8b
            goto L8f
        L8b:
            r6 = move-exception
            r6.printStackTrace()
        L8f:
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.languagepack.apkverifier.ApkSignatureV2V3Verifier.readContentDigest(int,"
                    + " java.lang.String):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00db, code lost:

       r5 = com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile.KEY_TYPE_EC;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0099, code lost:

       if (r4 != (-1)) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x009b, code lost:

       if (r5 != 0) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a4, code lost:

       throw new java.lang.SecurityException("No signatures found");
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00ac, code lost:

       throw new java.lang.SecurityException("No supported signatures found");
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00af, code lost:

       if (r4 == 513) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b1, code lost:

       if (r4 == 514) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b3, code lost:

       if (r4 == 769) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b5, code lost:

       if (r4 == 1057) goto L49;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b7, code lost:

       if (r4 == 1059) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b9, code lost:

       if (r4 == 1061) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00bb, code lost:

       switch(r4) {
           case 257: goto L49;
           case 258: goto L49;
           case 259: goto L49;
           case 260: goto L49;
           default: goto L47;
       };
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d4, code lost:

       throw new java.lang.IllegalArgumentException("Unknown signature algorithm: 0x" + java.lang.Long.toHexString(r4));
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d5, code lost:

       r5 = "RSA";
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00dd, code lost:

       if (r4 == 513) goto L67;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00df, code lost:

       if (r4 == 514) goto L66;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e1, code lost:

       if (r4 == 769) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e3, code lost:

       if (r4 == 1057) goto L64;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e5, code lost:

       if (r4 == 1059) goto L67;
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e7, code lost:

       if (r4 == 1061) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e9, code lost:

       switch(r4) {
           case 257: goto L63;
           case 258: goto L62;
           case 259: goto L64;
           case 260: goto L61;
           default: goto L59;
       };
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0102, code lost:

       throw new java.lang.IllegalArgumentException("Unknown signature algorithm: 0x" + java.lang.Long.toHexString(r4));
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0103, code lost:

       r3 = android.util.Pair.create("SHA512withRSA", null);
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0151, code lost:

       r6 = (java.lang.String) r3.first;
       r3 = (java.security.spec.AlgorithmParameterSpec) r3.second;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0159, code lost:

       r2 = java.security.KeyFactory.getInstance(r5).generatePublic(new java.security.spec.X509EncodedKeySpec(r2));
       r5 = java.security.Signature.getInstance(r6);
       r5.initVerify(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x016d, code lost:

       if (r3 == null) goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x016f, code lost:

       r5.setParameter(r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0176, code lost:

       r5.update(r0);
       android.util.Log.d("sig", "signedData = " + r0.toString());
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0199, code lost:

       if (r5.verify(r1) == false) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x019b, code lost:

       r0.clear();
       r0 = com.samsung.android.settings.languagepack.apkverifier.ApkSigningBlockUtils.getLengthPrefixedSlice(r0);
       r1 = new java.util.ArrayList();
       r2 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01ac, code lost:

       if (r0.hasRemaining() == false) goto L113;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01ae, code lost:

       r2 = r2 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01b0, code lost:

       r3 = com.samsung.android.settings.languagepack.apkverifier.ApkSigningBlockUtils.getLengthPrefixedSlice(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01ba, code lost:

       if (r3.remaining() < 8) goto L112;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x01bc, code lost:

       r5 = r3.getInt();
       r1.add(java.lang.Integer.valueOf(r5));
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01c7, code lost:

       if (r5 != r4) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01c9, code lost:

       r15 = com.samsung.android.settings.languagepack.apkverifier.ApkSigningBlockUtils.readLengthPrefixedByteArray(r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01d8, code lost:

       throw new java.io.IOException("Record too short");
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01cf, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01e4, code lost:

       throw new java.io.IOException(androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0.m(r2, "Failed to parse digest record #"), r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01ef, code lost:

       return new java.lang.String(android.util.Base64.encode(r15, 2));
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01fb, code lost:

       throw new java.lang.SecurityException(androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r6, " signature did not verify"));
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0173, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0209, code lost:

       throw new java.lang.SecurityException(androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0.m("Failed to verify ", r6, " signature"), r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x010a, code lost:

       r3 = android.util.Pair.create("SHA512withRSA/PSS", new java.security.spec.PSSParameterSpec("SHA-512", "MGF1", java.security.spec.MGF1ParameterSpec.SHA512, 64, 1));
    */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0120, code lost:

       r3 = android.util.Pair.create("SHA256withRSA/PSS", new java.security.spec.PSSParameterSpec("SHA-256", "MGF1", java.security.spec.MGF1ParameterSpec.SHA256, 32, 1));
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0136, code lost:

       r3 = android.util.Pair.create("SHA256withRSA", null);
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x013d, code lost:

       r3 = android.util.Pair.create("SHA256withDSA", null);
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0144, code lost:

       r3 = android.util.Pair.create("SHA512withECDSA", null);
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x014b, code lost:

       r3 = android.util.Pair.create("SHA256withECDSA", null);
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00d8, code lost:

       r5 = "DSA";
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String verifyContentDigest(int r16, java.nio.ByteBuffer r17) {
        /*
            Method dump skipped, instructions count: 558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.languagepack.apkverifier.ApkSignatureV2V3Verifier.verifyContentDigest(int,"
                    + " java.nio.ByteBuffer):java.lang.String");
    }
}
