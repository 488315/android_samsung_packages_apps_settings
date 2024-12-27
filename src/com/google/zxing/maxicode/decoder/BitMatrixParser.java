package com.google.zxing.maxicode.decoder;

import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureImpl;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.volte2.data.QuantumSecurityInfo;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BitMatrixParser {
    public static final int[][] BITNR;

    static {
        int[] iArr = new int[30];
        // fill-array-data instruction
        iArr[0] = 419;
        iArr[1] = 418;
        iArr[2] = 425;
        iArr[3] = 424;
        iArr[4] = 431;
        iArr[5] = 430;
        iArr[6] = 107;
        iArr[7] = 106;
        iArr[8] = 59;
        iArr[9] = 58;
        iArr[10] = -3;
        iArr[11] = -3;
        iArr[12] = -3;
        iArr[13] = -3;
        iArr[14] = -3;
        iArr[15] = -3;
        iArr[16] = -3;
        iArr[17] = -3;
        iArr[18] = -3;
        iArr[19] = 23;
        iArr[20] = 89;
        iArr[21] = 88;
        iArr[22] = 437;
        iArr[23] = 436;
        iArr[24] = 443;
        iArr[25] = 442;
        iArr[26] = 449;
        iArr[27] = 448;
        iArr[28] = 836;
        iArr[29] = 835;
        BITNR =
                new int[][] {
                    new int[] {
                        121, 120, 127, 126, 133, 132, 139, 138, 145, 144, 151, 150, 157, 156, 163,
                        162, 169, 168, 175, 174, 181, 180, 187, 186, 193, 192, 199, 198, -2, -2
                    },
                    new int[] {
                        123, 122, 129, 128, 135, 134, 141, 140, 147, 146, 153, 152, 159, 158, 165,
                        164, 171, 170, 177, 176, 183, 182, 189, 188, 195, 194, 201, 200, 816, -3
                    },
                    new int[] {
                        125, 124, 131, 130, 137, 136, 143, 142, 149, 148, 155, 154, 161, 160, 167,
                        166, 173, 172, 179, 178, 185, 184, 191, 190, 197, 196, 203, 202, 818, 817
                    },
                    new int[] {
                        IKnoxCustomManager.Stub.TRANSACTION_getLoadingLogoPath,
                        IKnoxCustomManager.Stub.TRANSACTION_setShuttingDownAnimationSub,
                        IKnoxCustomManager.Stub.TRANSACTION_clearForcedDisplaySizeDensity,
                        IKnoxCustomManager.Stub.TRANSACTION_setForcedDisplaySizeDensity,
                        271,
                        270,
                        265,
                        264,
                        259,
                        258,
                        IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList,
                        IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList,
                        IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut,
                        IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode,
                        IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState,
                        IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
                        IKnoxCustomManager.Stub.TRANSACTION_setAppsButtonState,
                        IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage,
                        IKnoxCustomManager.Stub.TRANSACTION_setBrightness,
                        228,
                        223,
                        222,
                        217,
                        216,
                        211,
                        210,
                        205,
                        204,
                        819,
                        -3
                    },
                    new int[] {
                        IKnoxCustomManager.Stub.TRANSACTION_startProKioskMode,
                        IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback,
                        IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView,
                        IKnoxCustomManager.Stub.TRANSACTION_startSmartView,
                        IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal,
                        272,
                        267,
                        266,
                        261,
                        260,
                        255,
                        254,
                        IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut,
                        IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut,
                        IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode,
                        IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState,
                        IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp,
                        IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState,
                        IKnoxCustomManager.Stub.TRANSACTION_removeShortcut,
                        230,
                        225,
                        224,
                        219,
                        218,
                        213,
                        212,
                        207,
                        206,
                        821,
                        820
                    },
                    new int[] {
                        IKnoxCustomManager.Stub.TRANSACTION_stayInDexForegroundMode,
                        IKnoxCustomManager.Stub.TRANSACTION_stopProKioskMode,
                        IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub,
                        IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView,
                        IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentBroadcast,
                        IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal,
                        269,
                        268,
                        263,
                        262,
                        257,
                        256,
                        IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut,
                        IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend,
                        IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
                        IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode,
                        IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount,
                        IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp,
                        IKnoxCustomManager.Stub.TRANSACTION_removeWidget,
                        IKnoxCustomManager.Stub.TRANSACTION_addWidget,
                        227,
                        226,
                        221,
                        220,
                        215,
                        214,
                        209,
                        208,
                        822,
                        -3
                    },
                    new int[] {
                        IKnoxCustomManager.Stub.TRANSACTION_setAsoc,
                        IKnoxCustomManager.Stub.TRANSACTION_getAsoc,
                        IKnoxCustomManager.Stub.TRANSACTION_readFile,
                        IKnoxCustomManager.Stub.TRANSACTION_getTcpDump,
                        301,
                        300,
                        307,
                        306,
                        FileType.RTF,
                        FileType.PDF,
                        FileType.XLS,
                        FileType.DOTX,
                        FileType.PPTX,
                        FileType.PPT,
                        331,
                        FileType.TXT,
                        FileType.SHOW,
                        FileType.SDOCX,
                        FileType.TSV,
                        FileType.PRN,
                        FileType.MOBI,
                        FileType.ACSM,
                        355,
                        354,
                        361,
                        360,
                        367,
                        366,
                        824,
                        823
                    },
                    new int[] {
                        IKnoxCustomManager.Stub.TRANSACTION_getBsohUnbiased,
                        IKnoxCustomManager.Stub.TRANSACTION_getBsoh,
                        IKnoxCustomManager.Stub.TRANSACTION_getApplicationRestrictionsInternal,
                        IKnoxCustomManager.Stub.TRANSACTION_setApplicationRestrictionsInternal,
                        303,
                        302,
                        FileType.ASC,
                        308,
                        FileType.DOCX,
                        FileType.DOC,
                        FileType.XLT,
                        FileType.XLSX,
                        FileType.POT,
                        FileType.PPTM,
                        FileType.MEMO,
                        332,
                        FileType.XLTM,
                        FileType.XLSB,
                        FileType.ODS,
                        FileType.XDW,
                        351,
                        FileType.CHM,
                        357,
                        356,
                        363,
                        362,
                        369,
                        368,
                        825,
                        -3
                    },
                    new int[] {
                        IKnoxCustomManager.Stub.TRANSACTION_stopTcpDump,
                        IKnoxCustomManager.Stub.TRANSACTION_startTcpDump,
                        IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions,
                        IKnoxCustomManager.Stub.TRANSACTION_setKeyedAppStatesReport,
                        305,
                        304,
                        FileType.CSV,
                        FileType.PPS,
                        FileType.DOT,
                        FileType.DOCM,
                        FileType.XLSM,
                        FileType.XLTX,
                        FileType.PPSX,
                        FileType.POTX,
                        FileType.SDOC,
                        FileType.XML,
                        FileType.HCDT,
                        FileType.CELL,
                        FileType.EPUB,
                        FileType.XMIND,
                        QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_FAIL_TO_GET_KEY,
                        QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_EXCEPTION,
                        359,
                        358,
                        365,
                        364,
                        371,
                        370,
                        827,
                        826
                    },
                    new int[] {
                        409,
                        VolteConstants.ErrorCode.REQUEST_TIMEOUT,
                        403,
                        402,
                        397,
                        396,
                        391,
                        390,
                        79,
                        78,
                        -2,
                        -2,
                        13,
                        12,
                        37,
                        36,
                        2,
                        -1,
                        44,
                        43,
                        109,
                        108,
                        385,
                        384,
                        379,
                        378,
                        373,
                        372,
                        828,
                        -3
                    },
                    new int[] {
                        FileType.RAR,
                        410,
                        405,
                        404,
                        399,
                        398,
                        393,
                        392,
                        81,
                        80,
                        40,
                        -2,
                        15,
                        14,
                        39,
                        38,
                        3,
                        -1,
                        -1,
                        45,
                        111,
                        110,
                        387,
                        386,
                        VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY,
                        VolteConstants.ErrorCode.ALTERNATIVE_SERVICES,
                        375,
                        374,
                        830,
                        829
                    },
                    new int[] {
                        FileType.LAST_ARCHIVE_FILE_TYPE,
                        FileType.SEVEN_Z,
                        407,
                        VolteConstants.ErrorCode.NOT_ACCEPTABLE,
                        401,
                        400,
                        395,
                        394,
                        83,
                        82,
                        41,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        5,
                        4,
                        47,
                        46,
                        113,
                        112,
                        389,
                        388,
                        383,
                        VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB,
                        377,
                        376,
                        831,
                        -3
                    },
                    new int[] {
                        VolteConstants.ErrorCode.UNSUPPORTED_MEDIA_TYPE,
                        414,
                        FileType.SM4,
                        VolteConstants.ErrorCode.BAD_EXTENSION,
                        427,
                        426,
                        103,
                        102,
                        55,
                        54,
                        16,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        20,
                        19,
                        85,
                        84,
                        FileType.DER,
                        FileType.CRT,
                        439,
                        438,
                        445,
                        444,
                        833,
                        832
                    },
                    new int[] {
                        417,
                        VolteConstants.ErrorCode.UNSUPPORTED_URI_SCHEME,
                        VolteConstants.ErrorCode.INTERVAL_TOO_BRIEF,
                        422,
                        429,
                        428,
                        105,
                        104,
                        57,
                        56,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        22,
                        21,
                        87,
                        86,
                        FileType.CER,
                        FileType.PEM,
                        CustomDeviceManager.MULTI_WINDOW_FIXED_STATE,
                        440,
                        447,
                        446,
                        834,
                        -3
                    },
                    iArr,
                    new int[] {
                        481,
                        VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE,
                        475,
                        474,
                        469,
                        468,
                        48,
                        -2,
                        30,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        0,
                        53,
                        52,
                        463,
                        462,
                        457,
                        456,
                        451,
                        450,
                        837,
                        -3
                    },
                    new int[] {
                        483, 482, 477, 476, 471, 470, 49, -1, -2, -3, -3, -3, -3, -3, -3, -3, -3,
                        -3, -3, -3, -2, -1, 465, 464, 459, 458, 453, 452, 839, 838
                    },
                    new int[] {
                        485,
                        VolteConstants.ErrorCode.ADDRESS_INCOMPLETE,
                        479,
                        478,
                        473,
                        472,
                        51,
                        50,
                        31,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        1,
                        -2,
                        42,
                        467,
                        466,
                        461,
                        460,
                        455,
                        454,
                        840,
                        -3
                    },
                    new int[] {
                        VolteConstants.ErrorCode.REQUEST_TERMINATED,
                        VolteConstants.ErrorCode.BUSY_HERE,
                        493,
                        492,
                        499,
                        498,
                        97,
                        96,
                        61,
                        60,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        26,
                        91,
                        90,
                        505,
                        504,
                        FileType.SASF,
                        FileType.EML,
                        FileType.SOL,
                        FileType.SCC_SCRAP,
                        842,
                        841
                    },
                    new int[] {
                        489,
                        VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE,
                        495,
                        494,
                        501,
                        500,
                        99,
                        98,
                        63,
                        62,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        28,
                        27,
                        93,
                        92,
                        FileType.VNT,
                        FileType.VCF,
                        FileType.SSF,
                        512,
                        FileType.ENC,
                        518,
                        843,
                        -3
                    },
                    new int[] {
                        491,
                        490,
                        497,
                        496,
                        503,
                        502,
                        101,
                        100,
                        65,
                        64,
                        17,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        18,
                        29,
                        95,
                        94,
                        FileType.XHTML,
                        FileType.VTS,
                        FileType.SCC,
                        FileType.SFF,
                        FileType.LA,
                        FileType.LOC,
                        845,
                        844
                    },
                    new int[] {
                        559,
                        558,
                        553,
                        552,
                        547,
                        546,
                        541,
                        540,
                        73,
                        72,
                        32,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        -3,
                        10,
                        67,
                        66,
                        115,
                        114,
                        535,
                        534,
                        FileType.JSON,
                        FileType.PNK,
                        FileType.PAGES,
                        FileType.GPX,
                        846,
                        -3
                    },
                    new int[] {
                        561,
                        560,
                        555,
                        554,
                        549,
                        548,
                        543,
                        542,
                        75,
                        74,
                        -2,
                        -1,
                        7,
                        6,
                        35,
                        34,
                        11,
                        -2,
                        69,
                        68,
                        117,
                        116,
                        537,
                        536,
                        FileType.EMC,
                        FileType.GLTF,
                        FileType.NUMBERS,
                        FileType.KEY,
                        848,
                        847
                    },
                    new int[] {
                        563,
                        562,
                        557,
                        556,
                        551,
                        SfpsEnrollmentFeatureImpl.HELP_ANIMATOR_DURATION,
                        545,
                        544,
                        77,
                        76,
                        -2,
                        33,
                        9,
                        8,
                        25,
                        24,
                        -1,
                        -2,
                        71,
                        70,
                        119,
                        118,
                        539,
                        538,
                        533,
                        532,
                        FileType.TORRENT,
                        FileType.PASSBOOK,
                        849,
                        -3
                    },
                    new int[] {
                        565,
                        564,
                        571,
                        570,
                        577,
                        576,
                        583,
                        582,
                        589,
                        588,
                        595,
                        594,
                        601,
                        600,
                        607,
                        VolteConstants.ErrorCode.NOT_ACCEPTABLE2,
                        613,
                        612,
                        619,
                        618,
                        625,
                        624,
                        631,
                        630,
                        637,
                        636,
                        643,
                        642,
                        851,
                        850
                    },
                    new int[] {
                        567,
                        566,
                        573,
                        572,
                        579,
                        578,
                        585,
                        584,
                        591,
                        590,
                        597,
                        596,
                        VolteConstants.ErrorCode.DECLINE,
                        602,
                        609,
                        608,
                        615,
                        614,
                        621,
                        620,
                        627,
                        626,
                        633,
                        632,
                        639,
                        638,
                        645,
                        644,
                        852,
                        -3
                    },
                    new int[] {
                        569,
                        568,
                        575,
                        574,
                        581,
                        VolteConstants.ErrorCode.PRECONDITION_FAILURE,
                        587,
                        586,
                        593,
                        592,
                        599,
                        598,
                        605,
                        VolteConstants.ErrorCode.DOES_NOT_EXIST_ANYWHERE,
                        611,
                        610,
                        617,
                        616,
                        623,
                        622,
                        629,
                        628,
                        635,
                        634,
                        641,
                        640,
                        647,
                        646,
                        854,
                        853
                    },
                    new int[] {
                        727,
                        726,
                        721,
                        720,
                        715,
                        714,
                        709,
                        708,
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_ACTIVATION_NOT_FOUND,
                        702,
                        697,
                        696,
                        691,
                        690,
                        685,
                        684,
                        679,
                        678,
                        673,
                        672,
                        667,
                        666,
                        661,
                        660,
                        655,
                        654,
                        649,
                        648,
                        855,
                        -3
                    },
                    new int[] {
                        729,
                        728,
                        723,
                        722,
                        717,
                        716,
                        711,
                        710,
                        705,
                        KnoxEnterpriseLicenseManager
                                .ERROR_LICENSE_QUANTITY_EXHAUSTED_ON_AUTO_RELEASE,
                        699,
                        698,
                        693,
                        692,
                        687,
                        686,
                        681,
                        680,
                        675,
                        674,
                        669,
                        668,
                        663,
                        662,
                        657,
                        656,
                        651,
                        650,
                        857,
                        856
                    },
                    new int[] {
                        731,
                        730,
                        725,
                        724,
                        719,
                        718,
                        713,
                        712,
                        707,
                        706,
                        701,
                        KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED,
                        695,
                        694,
                        689,
                        688,
                        683,
                        682,
                        677,
                        676,
                        671,
                        670,
                        665,
                        664,
                        659,
                        658,
                        653,
                        652,
                        858,
                        -3
                    },
                    new int[] {
                        733,
                        732,
                        739,
                        738,
                        745,
                        744,
                        751,
                        750,
                        757,
                        756,
                        763,
                        762,
                        769,
                        768,
                        775,
                        774,
                        781,
                        780,
                        787,
                        786,
                        793,
                        792,
                        799,
                        798,
                        VpnErrorValues.ERROR_INVALID_IPV6_CONFIGURATION,
                        VpnErrorValues.ERROR_INVALID_PROXY_CONFIGURATION,
                        811,
                        810,
                        860,
                        859
                    },
                    new int[] {
                        735, 734, 741, 740, 747, 746, 753, 752, 759, 758, 765, 764, 771, 770, 777,
                        776, 783, 782, 789, 788, 795, 794, 801, 800, 807, 806, 813, 812, 861, -3
                    },
                    new int[] {
                        737,
                        736,
                        743,
                        742,
                        749,
                        748,
                        755,
                        754,
                        761,
                        760,
                        767,
                        766,
                        773,
                        772,
                        779,
                        778,
                        785,
                        784,
                        791,
                        790,
                        797,
                        796,
                        VpnErrorValues.ERROR_STORING_PROXY_PASSWORD,
                        802,
                        809,
                        808,
                        815,
                        814,
                        863,
                        862
                    }
                };
    }
}
