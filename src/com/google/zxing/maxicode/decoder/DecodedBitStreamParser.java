package com.google.zxing.maxicode.decoder;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.att.iqi.lib.metrics.mm.MM05;

import java.text.DecimalFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class DecodedBitStreamParser {
    public static final byte[] COUNTRY_BYTES = {53, 54, 43, 44, 45, 46, 47, 48, 37, 38};
    public static final byte[] SERVICE_CLASS_BYTES = {55, 56, 57, 58, 59, 60, 49, 50, 51, 52};
    public static final byte[] POSTCODE_2_LENGTH_BYTES = {
        39, 40, 41, 42, 31, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED
    };
    public static final byte[] POSTCODE_2_BYTES = {
        33,
        34,
        35,
        36,
        25,
        26,
        27,
        28,
        29,
        30,
        19,
        20,
        21,
        22,
        23,
        24,
        13,
        14,
        15,
        HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED,
        17,
        18,
        7,
        8,
        9,
        10,
        MM05.IQ_SIP_CALL_STATE_DISCONNECTING,
        12,
        1,
        2
    };
    public static final byte[][] POSTCODE_3_BYTES = {
        new byte[] {39, 40, 41, 42, 31, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED},
        new byte[] {33, 34, 35, 36, 25, 26},
        new byte[] {27, 28, 29, 30, 19, 20},
        new byte[] {21, 22, 23, 24, 13, 14},
        new byte[] {15, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 17, 18, 7, 8},
        new byte[] {9, 10, MM05.IQ_SIP_CALL_STATE_DISCONNECTING, 12, 1, 2}
    };
    public static final String[] SETS = {
        "\r"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ\ufffa\u001c\u001d\u001e\ufffb"
            + " ￼\"#$%&'()*+,-./0123456789:\ufff1\ufff2\ufff3\ufff4\ufff8",
        "`abcdefghijklmnopqrstuvwxyz\ufffa\u001c\u001d\u001e\ufffb{￼}~\u007f;<=>?[\\]^_"
            + " ,./:@!|￼\ufff5\ufff6￼\ufff0\ufff2\ufff3\ufff4\ufff7",
        "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚ\ufffa\u001c\u001d\u001e\ufffbÛÜÝÞßª¬±²³µ¹º¼½¾\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\ufff7"
            + " \ufff9\ufff3\ufff4\ufff8",
        "àáâãäåæçèéêëìíîïðñòóôõö÷øùú\ufffa\u001c\u001d\u001e\ufffbûüýþÿ¡¨«¯°´·¸»¿\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\ufff7"
            + " \ufff2\ufff9\ufff4\ufff8",
        "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n"
            + "\u000b\f\r"
            + "\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\ufffa￼￼\u001b\ufffb\u001c\u001d\u001e\u001f\u009f"
            + " ¢£¤¥¦§©\u00ad®¶\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\ufff7"
            + " \ufff2\ufff3\ufff9\ufff8"
    };

    public static int getInt(byte[] bArr, byte[] bArr2) {
        int i = 0;
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            int i3 = bArr2[i2] - 1;
            i += (((1 << (5 - (i3 % 6))) & bArr[i3 / 6]) == 0 ? 0 : 1) << ((bArr2.length - i2) - 1);
        }
        return i;
    }

    public static String getMessage(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        int i3 = i;
        int i4 = -1;
        int i5 = 0;
        int i6 = 0;
        while (i3 < i + i2) {
            char charAt = SETS[i5].charAt(bArr[i3]);
            switch (charAt) {
                case 65520:
                case 65521:
                case 65522:
                case 65523:
                case 65524:
                    i6 = i5;
                    i5 = charAt - 65520;
                    i4 = 1;
                    break;
                case 65525:
                    i4 = 2;
                    i6 = i5;
                    i5 = 0;
                    break;
                case 65526:
                    i4 = 3;
                    i6 = i5;
                    i5 = 0;
                    break;
                case 65527:
                    i4 = -1;
                    i5 = 0;
                    break;
                case 65528:
                    i4 = -1;
                    i5 = 1;
                    break;
                case 65529:
                    i4 = -1;
                    break;
                case 65530:
                default:
                    sb.append(charAt);
                    break;
                case 65531:
                    int i7 =
                            (bArr[i3 + 1] << 24)
                                    + (bArr[i3 + 2] << 18)
                                    + (bArr[i3 + 3] << 12)
                                    + (bArr[i3 + 4] << 6);
                    i3 += 5;
                    sb.append(new DecimalFormat("000000000").format(i7 + bArr[i3]));
                    break;
            }
            int i8 = i4 - 1;
            if (i4 == 0) {
                i5 = i6;
            }
            i3++;
            i4 = i8;
        }
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == 65532) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
