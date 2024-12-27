package com.google.zxing.oned;

import com.google.zxing.ChecksumException;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Code93Reader extends OneDReader {
    public static final char[] ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
    public static final int ASTERISK_ENCODING;
    public static final int[] CHARACTER_ENCODINGS;
    public final StringBuilder decodeRowResult = new StringBuilder(20);
    public final int[] counters = new int[6];

    static {
        int[] iArr = {
            IKnoxCustomManager.Stub.TRANSACTION_setForcedDisplaySizeDensity,
            FileType.POTX,
            FileType.PPT,
            FileType.XLTX,
            IKnoxCustomManager.Stub.TRANSACTION_setApplicationRestrictionsInternal,
            IKnoxCustomManager.Stub.TRANSACTION_startTcpDump,
            IKnoxCustomManager.Stub.TRANSACTION_getBsoh,
            FileType.SDOCX,
            IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal,
            266,
            424,
            VolteConstants.ErrorCode.BAD_EXTENSION,
            418,
            404,
            402,
            394,
            360,
            356,
            354,
            308,
            IKnoxCustomManager.Stub.TRANSACTION_setShuttingDownAnimationSub,
            FileType.XDW,
            332,
            FileType.PPTM,
            300,
            IKnoxCustomManager.Stub.TRANSACTION_startSmartView,
            436,
            FileType.PEM,
            428,
            422,
            VolteConstants.ErrorCode.NOT_ACCEPTABLE,
            410,
            364,
            358,
            FileType.PPS,
            FileType.DOC,
            302,
            468,
            466,
            458,
            366,
            374,
            430,
            IKnoxCustomManager.Stub.TRANSACTION_getTcpDump,
            474,
            470,
            306,
            FileType.CHM
        };
        CHARACTER_ENCODINGS = iArr;
        ASTERISK_ENCODING = iArr[47];
    }

    public static void checkOneChecksum(int i, int i2, CharSequence charSequence) {
        int i3 = 0;
        int i4 = 1;
        for (int i5 = i - 1; i5 >= 0; i5--) {
            i3 +=
                    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*"
                                    .indexOf(charSequence.charAt(i5))
                            * i4;
            i4++;
            if (i4 > i2) {
                i4 = 1;
            }
        }
        if (charSequence.charAt(i) != ALPHABET[i3 % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    public static int toPattern(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        int length = iArr.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int round = Math.round((iArr[i4] * 9.0f) / i);
            if (round < 1 || round > 4) {
                return -1;
            }
            if ((i4 & 1) == 0) {
                for (int i5 = 0; i5 < round; i5++) {
                    i3 = (i3 << 1) | 1;
                }
            } else {
                i3 <<= round;
            }
        }
        return i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0132, code lost:

       if (r5 < 'X') goto L128;
    */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0134, code lost:

       if (r5 > 'Z') goto L129;
    */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0136, code lost:

       r4 = 127;
    */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x013d, code lost:

       throw com.google.zxing.FormatException.getFormatInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x013e, code lost:

       if (r5 < 'A') goto L130;
    */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0140, code lost:

       if (r5 > 'Z') goto L131;
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0142, code lost:

       r5 = r5 - '@';
    */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x014d, code lost:

       throw com.google.zxing.FormatException.getFormatInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0152, code lost:

       throw com.google.zxing.FormatException.getFormatInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0153, code lost:

       r2.append(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0159, code lost:

       r12 = r12;
       r14 = new com.google.zxing.Result(r2.toString(), null, new com.google.zxing.ResultPoint[]{new com.google.zxing.ResultPoint((r14[1] + r14[0]) / 2.0f, r12), new com.google.zxing.ResultPoint((r7 / 2.0f) + r1, r12)}, com.google.zxing.BarcodeFormat.CODE_93);
       r14.putMetadata(com.google.zxing.ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]G0");
    */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0188, code lost:

       return r14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x018d, code lost:

       throw com.google.zxing.NotFoundException.getNotFoundInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0192, code lost:

       throw com.google.zxing.NotFoundException.getNotFoundInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0054, code lost:

       r4 = com.google.zxing.oned.Code93Reader.ALPHABET[r5];
       r11.append(r4);
       r5 = r2.length;
       r6 = 0;
       r7 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005e, code lost:

       if (r6 >= r5) goto L125;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0060, code lost:

       r7 = r7 + r2[r6];
       r6 = r6 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0066, code lost:

       r5 = r13.getNextSet(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006c, code lost:

       if (r4 != '*') goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006e, code lost:

       r11.deleteCharAt(r11.length() - 1);
       r4 = r2.length;
       r6 = 0;
       r7 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0079, code lost:

       if (r6 >= r4) goto L126;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:

       r7 = r7 + r2[r6];
       r6 = r6 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0081, code lost:

       if (r5 == r3) goto L103;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0087, code lost:

       if (r13.get(r5) == false) goto L103;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008d, code lost:

       if (r11.length() < 2) goto L101;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008f, code lost:

       r13 = r11.length();
       checkOneChecksum(r13 - 2, 20, r11);
       checkOneChecksum(r13 - 1, 15, r11);
       r11.setLength(r11.length() - 2);
       r13 = r11.length();
       r2 = new java.lang.StringBuilder(r13);
       r3 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b2, code lost:

       if (r3 >= r13) goto L127;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b4, code lost:

       r4 = r11.charAt(r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ba, code lost:

       if (r4 < 'a') goto L97;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00be, code lost:

       if (r4 > 'd') goto L97;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c2, code lost:

       if (r3 >= (r13 - 1)) goto L132;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c4, code lost:

       r3 = r3 + 1;
       r5 = r11.charAt(r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d0, code lost:

       switch(r4) {
           case 97: goto L89;
           case 98: goto L56;
           case 99: goto L49;
           case 100: goto L43;
           default: goto L42;
       };
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d3, code lost:

       r4 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0145, code lost:

       r2.append(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0156, code lost:

       r3 = r3 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d6, code lost:

       if (r5 < 'A') goto L133;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d8, code lost:

       if (r5 > 'Z') goto L134;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00da, code lost:

       r5 = r5 + ' ';
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00dc, code lost:

       r4 = (char) r5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00e3, code lost:

       throw com.google.zxing.FormatException.getFormatInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e4, code lost:

       if (r5 < 'A') goto L52;
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e6, code lost:

       if (r5 > 'O') goto L52;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00e8, code lost:

       r5 = r5 - ' ';
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00eb, code lost:

       if (r5 != 'Z') goto L135;
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00ed, code lost:

       r4 = ':';
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00f4, code lost:

       throw com.google.zxing.FormatException.getFormatInstance();
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00f5, code lost:

       if (r5 < 'A') goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f9, code lost:

       if (r5 > 'E') goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00fb, code lost:

       r5 = r5 - '&';
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0100, code lost:

       if (r5 < 'F') goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0104, code lost:

       if (r5 > 'J') goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0106, code lost:

       r5 = r5 - 11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x010b, code lost:

       if (r5 < 'K') goto L69;
    */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x010d, code lost:

       if (r5 > 'O') goto L69;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x010f, code lost:

       r5 = r5 + 16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0114, code lost:

       if (r5 < 'P') goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0118, code lost:

       if (r5 > 'T') goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x011a, code lost:

       r5 = r5 + '+';
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x011f, code lost:

       if (r5 != 'U') goto L77;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0124, code lost:

       if (r5 != 'V') goto L80;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0126, code lost:

       r4 = '@';
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x012b, code lost:

       if (r5 != 'W') goto L83;
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x012d, code lost:

       r4 = '`';
    */
    @Override // com.google.zxing.oned.OneDReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result decodeRow(
            int r12, com.google.zxing.common.BitArray r13, java.util.Map r14) {
        /*
            Method dump skipped, instructions count: 466
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.zxing.oned.Code93Reader.decodeRow(int,"
                    + " com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
