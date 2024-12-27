package com.google.zxing.aztec.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.ReaderException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Decoder {
    public AztecDetectorResult ddata;
    public static final String[] UPPER_TABLE = {
        "CTRL_PS",
        " ",
        ImsProfile.TIMER_NAME_A,
        ImsProfile.TIMER_NAME_B,
        ImsProfile.TIMER_NAME_C,
        ImsProfile.TIMER_NAME_D,
        ImsProfile.TIMER_NAME_E,
        ImsProfile.TIMER_NAME_F,
        ImsProfile.TIMER_NAME_G,
        ImsProfile.TIMER_NAME_H,
        ImsProfile.TIMER_NAME_I,
        ImsProfile.TIMER_NAME_J,
        ImsProfile.TIMER_NAME_K,
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z",
        "CTRL_LL",
        "CTRL_ML",
        "CTRL_DL",
        "CTRL_BS"
    };
    public static final String[] LOWER_TABLE = {
        "CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
        "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL",
        "CTRL_BS"
    };
    public static final String[] MIXED_TABLE = {
        "CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b",
        "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@",
        "\\", "^", "_", "`", "|", "~", "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"
    };
    public static final String[] PUNCT_TABLE = {
        "FLG(n)", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*",
        "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"
    };
    public static final String[] DIGIT_TABLE = {
        "CTRL_PS",
        " ",
        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN,
        "1",
        "2",
        DATA.DM_FIELD_INDEX.PUBLIC_USER_ID,
        "4",
        DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE,
        DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE,
        DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB,
        DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER,
        DATA.DM_FIELD_INDEX.SMS_FORMAT,
        ",",
        ".",
        "CTRL_UL",
        "CTRL_US"
    };
    public static final Charset DEFAULT_ENCODING = StandardCharsets.ISO_8859_1;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Table {
        public static final /* synthetic */ Table[] $VALUES;
        public static final Table BINARY;
        public static final Table DIGIT;
        public static final Table LOWER;
        public static final Table MIXED;
        public static final Table PUNCT;
        public static final Table UPPER;

        static {
            Table table = new Table("UPPER", 0);
            UPPER = table;
            Table table2 = new Table("LOWER", 1);
            LOWER = table2;
            Table table3 = new Table("MIXED", 2);
            MIXED = table3;
            Table table4 = new Table("DIGIT", 3);
            DIGIT = table4;
            Table table5 = new Table("PUNCT", 4);
            PUNCT = table5;
            Table table6 = new Table("BINARY", 5);
            BINARY = table6;
            $VALUES = new Table[] {table, table2, table3, table4, table5, table6};
        }

        public static Table valueOf(String str) {
            return (Table) Enum.valueOf(Table.class, str);
        }

        public static Table[] values() {
            return (Table[]) $VALUES.clone();
        }
    }

    public static int readCode(boolean[] zArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = i; i4 < i + i2; i4++) {
            i3 <<= 1;
            if (zArr[i4]) {
                i3 |= 1;
            }
        }
        return i3;
    }

    public final DecoderResult decode(AztecDetectorResult aztecDetectorResult) {
        GenericGF genericGF;
        int i;
        Table table;
        String str;
        char c;
        AztecDetectorResult aztecDetectorResult2 = aztecDetectorResult;
        int i2 = 2;
        int i3 = 1;
        this.ddata = aztecDetectorResult2;
        boolean z = aztecDetectorResult2.compact;
        int i4 = z ? 11 : 14;
        int i5 = aztecDetectorResult2.nbLayers;
        int i6 = (i5 * 4) + i4;
        int[] iArr = new int[i6];
        int i7 = ((i5 * 16) + (z ? 88 : 112)) * i5;
        boolean[] zArr = new boolean[i7];
        if (z) {
            for (int i8 = 0; i8 < i6; i8++) {
                iArr[i8] = i8;
            }
        } else {
            int i9 = i6 / 2;
            int i10 = ((((i9 - 1) / 15) * 2) + (i6 + 1)) / 2;
            for (int i11 = 0; i11 < i9; i11++) {
                iArr[(i9 - i11) - 1] = (i10 - r20) - 1;
                iArr[i9 + i11] = i10 + (i11 / 15) + i11 + 1;
            }
        }
        int i12 = 0;
        int i13 = 0;
        while (true) {
            if (i12 >= i5) {
                break;
            }
            int i14 = ((i5 - i12) * 4) + (z ? 9 : 12);
            int i15 = i12 * 2;
            int i16 = (i6 - 1) - i15;
            int i17 = 0;
            while (i17 < i14) {
                int i18 = i17 * 2;
                int i19 = 0;
                while (i19 < i2) {
                    int i20 = i15 + i19;
                    int i21 = iArr[i20];
                    int i22 = i15 + i17;
                    int i23 = iArr[i22];
                    BitMatrix bitMatrix = aztecDetectorResult2.bits;
                    zArr[i13 + i18 + i19] = bitMatrix.get(i21, i23);
                    int i24 = i16 - i19;
                    zArr[(i14 * 2) + i13 + i18 + i19] = bitMatrix.get(iArr[i22], iArr[i24]);
                    int i25 = i16 - i17;
                    zArr[(i14 * 4) + i13 + i18 + i19] = bitMatrix.get(iArr[i24], iArr[i25]);
                    zArr[(i14 * 6) + i13 + i18 + i19] = bitMatrix.get(iArr[i25], iArr[i20]);
                    i19++;
                    aztecDetectorResult2 = aztecDetectorResult;
                    z = z;
                    i2 = 2;
                }
                i17++;
                i3 = 1;
                i2 = 2;
                aztecDetectorResult2 = aztecDetectorResult;
            }
            i13 += i14 * 8;
            i12 += i3;
            i2 = 2;
            aztecDetectorResult2 = aztecDetectorResult;
        }
        AztecDetectorResult aztecDetectorResult3 = this.ddata;
        int i26 = aztecDetectorResult3.nbLayers;
        if (i26 <= 2) {
            genericGF = GenericGF.AZTEC_DATA_6;
            i = 6;
        } else if (i26 <= 8) {
            genericGF = GenericGF.AZTEC_DATA_8;
            i = 8;
        } else if (i26 <= 22) {
            genericGF = GenericGF.AZTEC_DATA_10;
            i = 10;
        } else {
            genericGF = GenericGF.AZTEC_DATA_12;
            i = 12;
        }
        int i27 = i7 / i;
        int i28 = aztecDetectorResult3.nbDatablocks;
        if (i27 < i28) {
            throw FormatException.getFormatInstance();
        }
        int i29 = i7 % i;
        int[] iArr2 = new int[i27];
        int i30 = 0;
        while (i30 < i27) {
            iArr2[i30] = readCode(zArr, i29, i);
            i30++;
            i29 += i;
        }
        try {
            ReedSolomonDecoder reedSolomonDecoder = new ReedSolomonDecoder(genericGF);
            int i31 = i27 - i28;
            int decodeWithECCount = reedSolomonDecoder.decodeWithECCount(i31, iArr2);
            int i32 = 1 << i;
            int i33 = i32 - 1;
            int i34 = 0;
            for (int i35 = 0; i35 < i28; i35++) {
                int i36 = iArr2[i35];
                if (i36 == 0 || i36 == i33) {
                    throw FormatException.getFormatInstance();
                }
                if (i36 == 1 || i36 == i32 - 2) {
                    i34++;
                }
            }
            int i37 = (i28 * i) - i34;
            boolean[] zArr2 = new boolean[i37];
            int i38 = 0;
            for (int i39 = 0; i39 < i28; i39++) {
                int i40 = iArr2[i39];
                if (i40 == 1 || i40 == i32 - 2) {
                    Arrays.fill(zArr2, i38, (i38 + i) - 1, i40 > 1);
                    i38 = (i - 1) + i38;
                } else {
                    int i41 = i - 1;
                    while (i41 >= 0) {
                        int i42 = i38 + 1;
                        zArr2[i38] = ((1 << i41) & i40) != 0;
                        i41--;
                        i38 = i42;
                    }
                }
            }
            int i43 = (i31 * 100) / i27;
            int i44 = 8;
            int i45 = (i37 + 7) / 8;
            byte[] bArr = new byte[i45];
            int i46 = 0;
            while (i46 < i45) {
                int i47 = i46 * 8;
                int i48 = i37 - i47;
                bArr[i46] =
                        i48 >= i44
                                ? (byte) readCode(zArr2, i47, i44)
                                : (byte) (readCode(zArr2, i47, i48) << (8 - i48));
                i46++;
                i44 = 8;
            }
            Table table2 = Table.UPPER;
            int i49 = 5;
            StringBuilder sb = new StringBuilder((i37 - 5) / 4);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Charset charset = DEFAULT_ENCODING;
            Table table3 = table2;
            Table table4 = table3;
            int i50 = 0;
            loop9:
            while (i50 < i37) {
                Table table5 = Table.BINARY;
                if (table3 != table5) {
                    Table table6 = Table.DIGIT;
                    int i51 = table3 == table6 ? 4 : 5;
                    if (i37 - i50 >= i51) {
                        int readCode = readCode(zArr2, i50, i51);
                        i50 += i51;
                        int ordinal = table3.ordinal();
                        if (ordinal != 0) {
                            table = table2;
                            if (ordinal == 1) {
                                str = LOWER_TABLE[readCode];
                            } else if (ordinal == 2) {
                                str = MIXED_TABLE[readCode];
                            } else if (ordinal == 3) {
                                str = DIGIT_TABLE[readCode];
                            } else {
                                if (ordinal != 4) {
                                    throw new IllegalStateException("Bad table");
                                }
                                str = PUNCT_TABLE[readCode];
                            }
                        } else {
                            table = table2;
                            str = UPPER_TABLE[readCode];
                        }
                        if (!"FLG(n)".equals(str)) {
                            Table table7 = table3;
                            if (str.startsWith("CTRL_")) {
                                char charAt = str.charAt(5);
                                if (charAt != 'B') {
                                    table5 =
                                            charAt != 'D'
                                                    ? charAt != 'P'
                                                            ? charAt != 'L'
                                                                    ? charAt != 'M'
                                                                            ? table
                                                                            : Table.MIXED
                                                                    : Table.LOWER
                                                            : Table.PUNCT
                                                    : table6;
                                }
                                table3 = table5;
                                table4 = str.charAt(6) == 'L' ? table3 : table7;
                            } else {
                                byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
                                byteArrayOutputStream.write(bytes, 0, bytes.length);
                                table3 = table4;
                            }
                        } else if (i37 - i50 >= 3) {
                            int readCode2 = readCode(zArr2, i50, 3);
                            i50 += 3;
                            try {
                                sb.append(byteArrayOutputStream.toString(charset.name()));
                                byteArrayOutputStream.reset();
                                if (readCode2 == 0) {
                                    c = 2;
                                    sb.append((char) 29);
                                } else {
                                    if (readCode2 == 7) {
                                        throw FormatException.getFormatInstance();
                                    }
                                    int i52 = 4;
                                    if (i37 - i50 >= readCode2 * 4) {
                                        int i53 = 0;
                                        while (true) {
                                            int i54 = readCode2 - 1;
                                            if (readCode2 > 0) {
                                                int readCode3 = readCode(zArr2, i50, i52);
                                                i50 += i52;
                                                if (readCode3 < 2 || readCode3 > 11) {
                                                    break loop9;
                                                }
                                                i53 = (i53 * 10) + (readCode3 - 2);
                                                readCode2 = i54;
                                                i52 = 4;
                                            } else {
                                                c = 2;
                                                CharacterSetECI characterSetECIByValue =
                                                        CharacterSetECI.getCharacterSetECIByValue(
                                                                i53);
                                                if (characterSetECIByValue == null) {
                                                    throw FormatException.getFormatInstance();
                                                }
                                                charset =
                                                        Charset.forName(
                                                                characterSetECIByValue.name());
                                            }
                                        }
                                        throw FormatException.getFormatInstance();
                                    }
                                    c = 2;
                                }
                                table3 = table4;
                            } catch (UnsupportedEncodingException e) {
                                throw new IllegalStateException(e);
                            }
                        }
                        i49 = 5;
                        table2 = table;
                    }
                } else if (i37 - i50 >= i49) {
                    int readCode4 = readCode(zArr2, i50, i49);
                    int i55 = i50 + 5;
                    if (readCode4 == 0) {
                        if (i37 - i55 >= 11) {
                            readCode4 = readCode(zArr2, i55, 11) + 31;
                            i55 = i50 + 16;
                        }
                    }
                    int i56 = 0;
                    while (true) {
                        if (i56 >= readCode4) {
                            i50 = i55;
                            break;
                        }
                        if (i37 - i55 < 8) {
                            i50 = i37;
                            break;
                        }
                        byteArrayOutputStream.write((byte) readCode(zArr2, i55, 8));
                        i55 += 8;
                        i56++;
                    }
                    table3 = table4;
                    i49 = 5;
                }
            }
            try {
                sb.append(byteArrayOutputStream.toString(charset.name()));
                DecoderResult decoderResult =
                        new DecoderResult(
                                sb.toString(), bArr, String.format("%d%%", Integer.valueOf(i43)));
                decoderResult.errorsCorrected = Integer.valueOf(decodeWithECCount);
                return decoderResult;
            } catch (UnsupportedEncodingException e2) {
                throw new IllegalStateException(e2);
            }
        } catch (ReedSolomonException e3) {
            FormatException formatException = FormatException.INSTANCE;
            if (ReaderException.isStackTrace) {
                throw new FormatException(e3);
            }
            throw FormatException.INSTANCE;
        }
    }
}
