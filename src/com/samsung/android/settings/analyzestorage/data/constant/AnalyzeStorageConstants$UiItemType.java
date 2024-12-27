package com.samsung.android.settings.analyzestorage.data.constant;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\n"
                + "\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fR\u0017\u0010\u0004\u001a\u00020\u00038\u0006¢\u0006\f\n"
                + "\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00038\u0006¢\u0006\f\n"
                + "\u0004\b\b\u0010\u0005\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n"
                + "\u001a\u00020\u00038\u0006¢\u0006\f\n"
                + "\u0004\b\n"
                + "\u0010\u0005\u001a\u0004\b\u000b\u0010\u0007¨\u0006\r"
        },
        d2 = {
            "com/samsung/android/settings/analyzestorage/data/constant/AnalyzeStorageConstants$UiItemType",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/settings/analyzestorage/data/constant/AnalyzeStorageConstants$UiItemType;",
            ApnSettings.MVNO_NONE,
            "type",
            ImsProfile.TIMER_NAME_I,
            "getType",
            "()I",
            "titleResId",
            "getTitleResId",
            "colorResId",
            "getColorResId",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class AnalyzeStorageConstants$UiItemType {
    public static final /* synthetic */ AnalyzeStorageConstants$UiItemType[] $VALUES;
    public static final AnalyzeStorageConstants$UiItemType APK;
    public static final AnalyzeStorageConstants$UiItemType APPS;
    public static final AnalyzeStorageConstants$UiItemType AUDIO;
    public static final AnalyzeStorageConstants$UiItemType COMPRESSED;
    public static final Companion Companion;
    public static final AnalyzeStorageConstants$UiItemType DOCUMENTS;
    public static final AnalyzeStorageConstants$UiItemType GOOGLE_APPS;
    public static final AnalyzeStorageConstants$UiItemType IMAGES;
    public static final AnalyzeStorageConstants$UiItemType INVALID_ITEM;
    public static final AnalyzeStorageConstants$UiItemType OTHER_FILES;
    public static final AnalyzeStorageConstants$UiItemType OTHER_PROFILE;
    public static final AnalyzeStorageConstants$UiItemType SECURE_FOLDER;
    public static final AnalyzeStorageConstants$UiItemType SYSTEM;
    public static final AnalyzeStorageConstants$UiItemType TOTAL;
    public static final AnalyzeStorageConstants$UiItemType TRASH;
    public static final AnalyzeStorageConstants$UiItemType VIDEOS;
    public static final AnalyzeStorageConstants$UiItemType WORK_PROFILE;
    private final int colorResId;
    private final int titleResId;
    private final int type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    static {
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType =
                new AnalyzeStorageConstants$UiItemType(
                        0, 0, R.string.as_images, R.color.as_image, "IMAGES");
        IMAGES = analyzeStorageConstants$UiItemType;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType2 =
                new AnalyzeStorageConstants$UiItemType(
                        1, 1, R.string.as_videos, R.color.as_video, "VIDEOS");
        VIDEOS = analyzeStorageConstants$UiItemType2;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType3 =
                new AnalyzeStorageConstants$UiItemType(
                        2, 2, R.string.as_audio, R.color.as_music, "AUDIO");
        AUDIO = analyzeStorageConstants$UiItemType3;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType4 =
                new AnalyzeStorageConstants$UiItemType(
                        3, 3, R.string.as_documents, R.color.as_document, "DOCUMENTS");
        DOCUMENTS = analyzeStorageConstants$UiItemType4;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType5 =
                new AnalyzeStorageConstants$UiItemType(
                        4, 4, R.string.as_installation_files, R.color.as_installation_files, "APK");
        APK = analyzeStorageConstants$UiItemType5;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType6 =
                new AnalyzeStorageConstants$UiItemType(
                        5, 5, R.string.as_compressed_files, R.color.as_compressed, "COMPRESSED");
        COMPRESSED = analyzeStorageConstants$UiItemType6;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType7 =
                new AnalyzeStorageConstants$UiItemType(
                        6, 10, R.string.as_menu_apps, R.color.as_apps, "APPS");
        APPS = analyzeStorageConstants$UiItemType7;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType8 =
                new AnalyzeStorageConstants$UiItemType(
                        7, 11, R.string.as_menu_system, R.color.as_system, "SYSTEM");
        SYSTEM = analyzeStorageConstants$UiItemType8;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType9 =
                new AnalyzeStorageConstants$UiItemType(
                        8, 12, R.string.as_others_files, R.color.as_other, "OTHER_FILES");
        OTHER_FILES = analyzeStorageConstants$UiItemType9;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType10 =
                new AnalyzeStorageConstants$UiItemType(
                        9, 13, R.string.as_menu_trash, R.color.as_trash, "TRASH");
        TRASH = analyzeStorageConstants$UiItemType10;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType11 =
                new AnalyzeStorageConstants$UiItemType(
                        10,
                        20,
                        R.string.as_secure_folder,
                        R.color.as_secure_folder,
                        "SECURE_FOLDER");
        SECURE_FOLDER = analyzeStorageConstants$UiItemType11;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType12 =
                new AnalyzeStorageConstants$UiItemType(
                        11, 21, R.string.as_work_profile, R.color.as_work_profile, "WORK_PROFILE");
        WORK_PROFILE = analyzeStorageConstants$UiItemType12;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType13 =
                new AnalyzeStorageConstants$UiItemType(
                        12,
                        22,
                        R.string.as_other_profiles,
                        R.color.as_other_profiles,
                        "OTHER_PROFILE");
        OTHER_PROFILE = analyzeStorageConstants$UiItemType13;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType14 =
                new AnalyzeStorageConstants$UiItemType(
                        13, 30, R.string.as_gmail_google_photo, R.color.as_back_up, "GOOGLE_APPS");
        GOOGLE_APPS = analyzeStorageConstants$UiItemType14;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType15 =
                new AnalyzeStorageConstants$UiItemType(14, 100, -1, -1, "TOTAL");
        TOTAL = analyzeStorageConstants$UiItemType15;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType16 =
                new AnalyzeStorageConstants$UiItemType(15, 101, -1, -1, "OTHERS_SUMMARY");
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType17 =
                new AnalyzeStorageConstants$UiItemType(16, -1, -1, -1, "INVALID_ITEM");
        INVALID_ITEM = analyzeStorageConstants$UiItemType17;
        AnalyzeStorageConstants$UiItemType[] analyzeStorageConstants$UiItemTypeArr = {
            analyzeStorageConstants$UiItemType,
            analyzeStorageConstants$UiItemType2,
            analyzeStorageConstants$UiItemType3,
            analyzeStorageConstants$UiItemType4,
            analyzeStorageConstants$UiItemType5,
            analyzeStorageConstants$UiItemType6,
            analyzeStorageConstants$UiItemType7,
            analyzeStorageConstants$UiItemType8,
            analyzeStorageConstants$UiItemType9,
            analyzeStorageConstants$UiItemType10,
            analyzeStorageConstants$UiItemType11,
            analyzeStorageConstants$UiItemType12,
            analyzeStorageConstants$UiItemType13,
            analyzeStorageConstants$UiItemType14,
            analyzeStorageConstants$UiItemType15,
            analyzeStorageConstants$UiItemType16,
            analyzeStorageConstants$UiItemType17
        };
        $VALUES = analyzeStorageConstants$UiItemTypeArr;
        EnumEntriesKt.enumEntries(analyzeStorageConstants$UiItemTypeArr);
        Companion = new Companion();
    }

    public AnalyzeStorageConstants$UiItemType(int i, int i2, int i3, int i4, String str) {
        this.type = i2;
        this.titleResId = i3;
        this.colorResId = i4;
    }

    public static AnalyzeStorageConstants$UiItemType valueOf(String str) {
        return (AnalyzeStorageConstants$UiItemType)
                Enum.valueOf(AnalyzeStorageConstants$UiItemType.class, str);
    }

    public static AnalyzeStorageConstants$UiItemType[] values() {
        return (AnalyzeStorageConstants$UiItemType[]) $VALUES.clone();
    }

    public final int getColorResId() {
        return this.colorResId;
    }

    public final int getTitleResId() {
        return this.titleResId;
    }

    public final int getType() {
        return this.type;
    }
}
