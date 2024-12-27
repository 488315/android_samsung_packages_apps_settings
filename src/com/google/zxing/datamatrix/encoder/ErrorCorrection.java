package com.google.zxing.datamatrix.encoder;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ErrorCorrection {
    public static final int[] FACTOR_SETS = {
        5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68
    };
    public static final int[][] FACTORS = {
        new int[] {228, 48, 15, 111, 62},
        new int[] {23, 68, 144, 134, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp, 92, 254},
        new int[] {
            28,
            24,
            185,
            166,
            223,
            IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut,
            116,
            255,
            110,
            61
        },
        new int[] {
            175,
            138,
            205,
            12,
            194,
            168,
            39,
            IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
            60,
            97,
            120
        },
        new int[] {
            41,
            153,
            158,
            91,
            61,
            42,
            142,
            213,
            97,
            178,
            100,
            IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState
        },
        new int[] {
            156,
            97,
            192,
            IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList,
            95,
            9,
            157,
            119,
            138,
            45,
            18,
            186,
            83,
            185
        },
        new int[] {
            83,
            195,
            100,
            39,
            188,
            75,
            66,
            61,
            IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState,
            213,
            109,
            129,
            94,
            254,
            225,
            48,
            90,
            188
        },
        new int[] {
            15,
            195,
            IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode,
            9,
            IKnoxCustomManager.Stub.TRANSACTION_removeWidget,
            71,
            168,
            2,
            188,
            160,
            153,
            145,
            IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList,
            79,
            108,
            82,
            27,
            174,
            186,
            172
        },
        new int[] {
            52,
            190,
            88,
            205,
            109,
            39,
            176,
            21,
            155,
            197,
            IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut,
            223,
            155,
            21,
            5,
            172,
            254,
            124,
            12,
            181,
            184,
            96,
            50,
            193
        },
        new int[] {
            211,
            IKnoxCustomManager.Stub.TRANSACTION_removeShortcut,
            43,
            97,
            71,
            96,
            103,
            174,
            37,
            151,
            170,
            53,
            75,
            34,
            IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut,
            121,
            17,
            138,
            110,
            213,
            141,
            136,
            120,
            151,
            IKnoxCustomManager.Stub.TRANSACTION_removeWidget,
            168,
            93,
            255
        },
        new int[] {
            IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
            127,
            IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState,
            218,
            130,
            IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend,
            162,
            181,
            102,
            120,
            84,
            179,
            220,
            IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut,
            80,
            182,
            IKnoxCustomManager.Stub.TRANSACTION_setBrightness,
            18,
            2,
            4,
            68,
            33,
            101,
            137,
            95,
            119,
            115,
            44,
            175,
            184,
            59,
            25,
            225,
            98,
            81,
            112
        },
        new int[] {
            77,
            193,
            137,
            31,
            19,
            38,
            22,
            153,
            IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut,
            105,
            122,
            2,
            IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
            133,
            IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState,
            8,
            175,
            95,
            100,
            9,
            167,
            105,
            214,
            111,
            57,
            121,
            21,
            1,
            IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList,
            57,
            54,
            101,
            IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut,
            202,
            69,
            50,
            150,
            177,
            226,
            5,
            9,
            5
        },
        new int[] {
            IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
            132,
            172,
            223,
            96,
            32,
            117,
            22,
            IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp,
            133,
            IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp,
            IKnoxCustomManager.Stub.TRANSACTION_removeShortcut,
            205,
            188,
            IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp,
            87,
            191,
            106,
            16,
            147,
            118,
            23,
            37,
            90,
            170,
            205,
            131,
            88,
            120,
            100,
            66,
            138,
            186,
            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
            82,
            44,
            176,
            87,
            187,
            147,
            160,
            175,
            69,
            213,
            92,
            IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList,
            225,
            19
        },
        new int[] {
            175,
            9,
            223,
            IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp,
            12,
            17,
            220,
            208,
            100,
            29,
            175,
            170,
            230,
            192,
            215,
            IKnoxCustomManager.Stub.TRANSACTION_setAppsButtonState,
            150,
            159,
            36,
            223,
            38,
            200,
            132,
            54,
            228,
            146,
            218,
            IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage,
            117,
            203,
            29,
            IKnoxCustomManager.Stub.TRANSACTION_addWidget,
            144,
            IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp,
            22,
            150,
            201,
            117,
            62,
            207,
            164,
            13,
            137,
            IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
            127,
            67,
            IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut,
            28,
            155,
            43,
            203,
            107,
            IKnoxCustomManager.Stub.TRANSACTION_removeWidget,
            53,
            143,
            46
        },
        new int[] {
            IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState,
            93,
            169,
            50,
            144,
            210,
            39,
            118,
            202,
            188,
            201,
            189,
            143,
            108,
            196,
            37,
            185,
            112,
            134,
            230,
            IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
            63,
            197,
            190,
            IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend,
            106,
            185,
            221,
            175,
            64,
            114,
            71,
            161,
            44,
            147,
            6,
            27,
            218,
            51,
            63,
            87,
            10,
            40,
            130,
            188,
            17,
            163,
            31,
            176,
            170,
            4,
            107,
            IKnoxCustomManager.Stub.TRANSACTION_addWidget,
            7,
            94,
            166,
            224,
            124,
            86,
            47,
            11,
            204
        },
        new int[] {
            220,
            228,
            173,
            89,
            IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut,
            149,
            159,
            56,
            89,
            33,
            147,
            IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode,
            154,
            36,
            73,
            127,
            213,
            136,
            IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut,
            180,
            IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage,
            197,
            158,
            177,
            68,
            122,
            93,
            213,
            15,
            160,
            227,
            IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState,
            66,
            139,
            153,
            185,
            202,
            167,
            179,
            25,
            220,
            IKnoxCustomManager.Stub.TRANSACTION_addWidget,
            96,
            210,
            IKnoxCustomManager.Stub.TRANSACTION_removeShortcut,
            136,
            223,
            IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount,
            181,
            IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState,
            59,
            52,
            172,
            25,
            49,
            IKnoxCustomManager.Stub.TRANSACTION_addWidget,
            211,
            189,
            64,
            54,
            108,
            153,
            132,
            63,
            96,
            103,
            82,
            186
        }
    };
    public static final int[] LOG = new int[256];
    public static final int[] ALOG = new int[255];

    static {
        int i = 1;
        for (int i2 = 0; i2 < 255; i2++) {
            ALOG[i2] = i;
            LOG[i] = i2;
            i *= 2;
            if (i >= 256) {
                i ^= 301;
            }
        }
    }

    public static String createECCBlock(int i, CharSequence charSequence) {
        int[] iArr;
        int[] iArr2;
        int i2;
        int i3;
        int i4 = 0;
        while (true) {
            if (i4 >= 16) {
                i4 = -1;
                break;
            }
            if (FACTOR_SETS[i4] == i) {
                break;
            }
            i4++;
        }
        if (i4 < 0) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "Illegal number of error correction codewords specified: "));
        }
        int[] iArr3 = FACTORS[i4];
        char[] cArr = new char[i];
        for (int i5 = 0; i5 < i; i5++) {
            cArr[i5] = 0;
        }
        int i6 = 0;
        while (true) {
            String str = (String) charSequence;
            if (i6 >= str.length()) {
                break;
            }
            int i7 = i - 1;
            int charAt = str.charAt(i6) ^ cArr[i7];
            while (true) {
                iArr = LOG;
                iArr2 = ALOG;
                if (i7 <= 0) {
                    break;
                }
                if (charAt == 0 || (i3 = iArr3[i7]) == 0) {
                    cArr[i7] = cArr[i7 - 1];
                } else {
                    cArr[i7] = (char) (iArr2[(iArr[charAt] + iArr[i3]) % 255] ^ cArr[i7 - 1]);
                }
                i7--;
            }
            if (charAt == 0 || (i2 = iArr3[0]) == 0) {
                cArr[0] = 0;
            } else {
                cArr[0] = (char) iArr2[(iArr[charAt] + iArr[i2]) % 255];
            }
            i6++;
        }
        char[] cArr2 = new char[i];
        for (int i8 = 0; i8 < i; i8++) {
            cArr2[i8] = cArr[(i - i8) - 1];
        }
        return String.valueOf(cArr2);
    }
}
