package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.EnumMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SamsungAnalyticsConvertManager {
    public static final SparseArray sCardConfirmFolderToSAEventId;
    public static final SparseArray sCardConfirmToSAEventId;
    public static final SparseArray sCardFolderToSAEventId;
    public static final SparseArray sCardNotNowFolderToSAEventId;
    public static final SparseArray sCardNotNowToSAEventId;
    public static final SparseArray sCardToSAEventId;
    public static final SparseArray sOverViewGoogleCloudCollapseToSAEventId;
    public static final EnumMap sOverViewGoogleCloudExpandToSAEventId;
    public static final SparseArray sOverViewInternalCollapseToSAEventId;
    public static final EnumMap sOverViewInternalExpandToSAEventId;
    public static final SparseArray sOverViewOnedriveCloudCollapseToSAEventId;
    public static final EnumMap sOverViewOnedriveCloudExpandToSAEventId;
    public static final SparseArray sOverViewSdCardCollapseToSAEventId;
    public static final EnumMap sOverViewSdCardExpandToSAEventId;

    static {
        EnumMap enumMap = new EnumMap(AnalyzeStorageConstants$UiItemType.class);
        sOverViewInternalExpandToSAEventId = enumMap;
        SparseArray sparseArray = new SparseArray();
        sOverViewInternalCollapseToSAEventId = sparseArray;
        EnumMap enumMap2 = new EnumMap(AnalyzeStorageConstants$UiItemType.class);
        sOverViewSdCardExpandToSAEventId = enumMap2;
        SparseArray sparseArray2 = new SparseArray();
        sOverViewSdCardCollapseToSAEventId = sparseArray2;
        EnumMap enumMap3 = new EnumMap(AnalyzeStorageConstants$UiItemType.class);
        sOverViewGoogleCloudExpandToSAEventId = enumMap3;
        SparseArray sparseArray3 = new SparseArray();
        sOverViewGoogleCloudCollapseToSAEventId = sparseArray3;
        EnumMap enumMap4 = new EnumMap(AnalyzeStorageConstants$UiItemType.class);
        sOverViewOnedriveCloudExpandToSAEventId = enumMap4;
        SparseArray sparseArray4 = new SparseArray();
        sOverViewOnedriveCloudCollapseToSAEventId = sparseArray4;
        SparseArray sparseArray5 = new SparseArray();
        sCardNotNowToSAEventId = sparseArray5;
        SparseArray sparseArray6 = new SparseArray();
        sCardNotNowFolderToSAEventId = sparseArray6;
        SparseArray sparseArray7 = new SparseArray();
        sCardConfirmToSAEventId = sparseArray7;
        SparseArray sparseArray8 = new SparseArray();
        sCardConfirmFolderToSAEventId = sparseArray8;
        SparseArray sparseArray9 = new SparseArray();
        sCardToSAEventId = sparseArray9;
        SparseArray sparseArray10 = new SparseArray();
        sCardFolderToSAEventId = sparseArray10;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType =
                AnalyzeStorageConstants$UiItemType.IMAGES;
        enumMap.put(
                (EnumMap) analyzeStorageConstants$UiItemType,
                (AnalyzeStorageConstants$UiItemType) 8830);
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType2 =
                AnalyzeStorageConstants$UiItemType.VIDEOS;
        enumMap.put(
                (EnumMap) analyzeStorageConstants$UiItemType2,
                (AnalyzeStorageConstants$UiItemType) 8831);
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType3 =
                AnalyzeStorageConstants$UiItemType.AUDIO;
        enumMap.put(
                (EnumMap) analyzeStorageConstants$UiItemType3,
                (AnalyzeStorageConstants$UiItemType) 8832);
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType4 =
                AnalyzeStorageConstants$UiItemType.DOCUMENTS;
        enumMap.put(
                (EnumMap) analyzeStorageConstants$UiItemType4,
                (AnalyzeStorageConstants$UiItemType) 8833);
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType5 =
                AnalyzeStorageConstants$UiItemType.APK;
        enumMap.put(
                (EnumMap) analyzeStorageConstants$UiItemType5,
                (AnalyzeStorageConstants$UiItemType) 8834);
        enumMap.put(
                (EnumMap) AnalyzeStorageConstants$UiItemType.APPS,
                (AnalyzeStorageConstants$UiItemType) 8835);
        enumMap.put(
                (EnumMap) AnalyzeStorageConstants$UiItemType.SECURE_FOLDER,
                (AnalyzeStorageConstants$UiItemType) 8836);
        enumMap.put(
                (EnumMap) AnalyzeStorageConstants$UiItemType.WORK_PROFILE,
                (AnalyzeStorageConstants$UiItemType) 8837);
        sparseArray.put(0, 8811);
        sparseArray.put(1, 8812);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8813, sparseArray, 2, 8824, 3);
        enumMap2.put(
                (EnumMap) analyzeStorageConstants$UiItemType,
                (AnalyzeStorageConstants$UiItemType) 8845);
        enumMap2.put(
                (EnumMap) analyzeStorageConstants$UiItemType2,
                (AnalyzeStorageConstants$UiItemType) 8846);
        enumMap2.put(
                (EnumMap) analyzeStorageConstants$UiItemType3,
                (AnalyzeStorageConstants$UiItemType) 8847);
        enumMap2.put(
                (EnumMap) analyzeStorageConstants$UiItemType4,
                (AnalyzeStorageConstants$UiItemType) 8848);
        enumMap2.put(
                (EnumMap) analyzeStorageConstants$UiItemType5,
                (AnalyzeStorageConstants$UiItemType) 8849);
        sparseArray2.put(0, 8841);
        sparseArray2.put(1, 8842);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8843, sparseArray2, 2, 8854, 3);
        enumMap3.put(
                (EnumMap) analyzeStorageConstants$UiItemType,
                (AnalyzeStorageConstants$UiItemType) 8885);
        enumMap3.put(
                (EnumMap) analyzeStorageConstants$UiItemType2,
                (AnalyzeStorageConstants$UiItemType) 8886);
        enumMap3.put(
                (EnumMap) analyzeStorageConstants$UiItemType3,
                (AnalyzeStorageConstants$UiItemType) 8887);
        enumMap3.put(
                (EnumMap) analyzeStorageConstants$UiItemType4,
                (AnalyzeStorageConstants$UiItemType) 8888);
        enumMap3.put(
                (EnumMap) analyzeStorageConstants$UiItemType5,
                (AnalyzeStorageConstants$UiItemType) 8889);
        sparseArray3.put(0, 8881);
        sparseArray3.put(1, 8882);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8883, sparseArray3, 2, 8897, 3);
        enumMap4.put(
                (EnumMap) analyzeStorageConstants$UiItemType,
                (AnalyzeStorageConstants$UiItemType) 8865);
        enumMap4.put(
                (EnumMap) analyzeStorageConstants$UiItemType2,
                (AnalyzeStorageConstants$UiItemType) 8866);
        enumMap4.put(
                (EnumMap) analyzeStorageConstants$UiItemType3,
                (AnalyzeStorageConstants$UiItemType) 8867);
        enumMap4.put(
                (EnumMap) analyzeStorageConstants$UiItemType4,
                (AnalyzeStorageConstants$UiItemType) 8868);
        enumMap4.put(
                (EnumMap) analyzeStorageConstants$UiItemType5,
                (AnalyzeStorageConstants$UiItemType) 8869);
        sparseArray4.put(0, 8861);
        sparseArray4.put(1, 8862);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8863, sparseArray4, 2, 8871, 3);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8904, sparseArray5, 11, 8916, 12);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8910, sparseArray5, 13, 8928, 21);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8922, sparseArray5, 22, 8934, 2);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8945, sparseArray5, 5, 8950, 6);
        sparseArray5.put(4, 8940);
        sparseArray7.put(11, 8905);
        sparseArray7.put(12, 8917);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8911, sparseArray7, 13, 8929, 21);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8923, sparseArray7, 22, 8935, 2);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8951, sparseArray7, 6, 8941, 4);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8901, sparseArray6, 11, 8913, 12);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8907, sparseArray6, 13, 8925, 21);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8919, sparseArray6, 22, 8931, 2);
        sparseArray6.put(3, 8937);
        sparseArray8.put(11, 8902);
        sparseArray8.put(12, 8914);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8908, sparseArray8, 13, 8926, 21);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8920, sparseArray8, 22, 8932, 2);
        sparseArray8.put(3, 8938);
        sparseArray9.put(11, 8903);
        sparseArray9.put(12, 8915);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8909, sparseArray9, 13, 8927, 21);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8921, sparseArray9, 22, 8933, 2);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(8942, sparseArray9, 5, 8946, 6);
        sparseArray9.put(4, 8939);
        sparseArray10.put(11, 8900);
        sparseArray10.put(12, 8912);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8906, sparseArray10, 13, 8924, 21);
        SamsungAnalyticsConvertManager$$ExternalSyntheticOutline0.m(
                8918, sparseArray10, 22, 8930, 2);
        sparseArray10.put(3, 8936);
    }

    public static Integer convertCardToSAEventId(int i, int i2, boolean z, int i3) {
        if (i == 0 || i == 1) {
            i =
                    i2 != 1
                            ? i2 != 2
                                    ? i2 != 3
                                            ? (i2 < 410 || i2 > FileType.LAST_ARCHIVE_FILE_TYPE)
                                                    ? 22
                                                    : 21
                                            : 13
                                    : 12
                            : 11;
        }
        return i3 != 0
                ? i3 != 1
                        ? z
                                ? (Integer) sCardConfirmToSAEventId.get(i)
                                : (Integer) sCardConfirmFolderToSAEventId.get(i)
                        : z
                                ? (Integer) sCardNotNowToSAEventId.get(i)
                                : (Integer) sCardNotNowFolderToSAEventId.get(i)
                : z ? (Integer) sCardToSAEventId.get(i) : (Integer) sCardFolderToSAEventId.get(i);
    }
}
