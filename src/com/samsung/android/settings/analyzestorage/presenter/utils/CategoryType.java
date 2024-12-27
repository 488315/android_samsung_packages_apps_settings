package com.samsung.android.settings.analyzestorage.presenter.utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0011R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n"
                + "\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006¢\u0006\f\n"
                + "\u0004\b\b\u0010\t\u001a\u0004\b\n"
                + "\u0010\u000bR\u0017\u0010\r"
                + "\u001a\u00020\f8\u0006¢\u0006\f\n"
                + "\u0004\b\r"
                + "\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0012"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/presenter/utils/CategoryType;",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/settings/analyzestorage/presenter/page/PageType;",
            "pageType",
            "Lcom/samsung/android/settings/analyzestorage/presenter/page/PageType;",
            "getPageType",
            "()Lcom/samsung/android/settings/analyzestorage/presenter/page/PageType;",
            ApnSettings.MVNO_NONE,
            "path",
            "Ljava/lang/String;",
            "getPath",
            "()Ljava/lang/String;",
            "Lcom/samsung/android/settings/analyzestorage/data/constant/AnalyzeStorageConstants$UiItemType;",
            "overViewItem",
            "Lcom/samsung/android/settings/analyzestorage/data/constant/AnalyzeStorageConstants$UiItemType;",
            "getOverViewItem",
            "()Lcom/samsung/android/settings/analyzestorage/data/constant/AnalyzeStorageConstants$UiItemType;",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class CategoryType {
    public static final /* synthetic */ CategoryType[] $VALUES;
    public static final CategoryType APK;
    public static final CategoryType AUDIO;
    public static final CategoryType COMPRESSED;
    public static final Companion Companion;
    public static final CategoryType DOCUMENTS;
    public static final CategoryType DOWNLOADS;
    public static final CategoryType IMAGES;
    public static final CategoryType NONE;
    public static final CategoryType VIDEOS;
    private final AnalyzeStorageConstants$UiItemType overViewItem;
    private final PageType pageType;
    private final String path;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    static {
        CategoryType categoryType =
                new CategoryType(
                        "IMAGES",
                        0,
                        PageType.IMAGES,
                        "/Image",
                        AnalyzeStorageConstants$UiItemType.IMAGES);
        IMAGES = categoryType;
        CategoryType categoryType2 =
                new CategoryType(
                        "AUDIO",
                        1,
                        PageType.AUDIO,
                        "/Audio",
                        AnalyzeStorageConstants$UiItemType.AUDIO);
        AUDIO = categoryType2;
        CategoryType categoryType3 =
                new CategoryType(
                        "VIDEOS",
                        2,
                        PageType.VIDEOS,
                        "/Video",
                        AnalyzeStorageConstants$UiItemType.VIDEOS);
        VIDEOS = categoryType3;
        CategoryType categoryType4 =
                new CategoryType(
                        "DOCUMENTS",
                        3,
                        PageType.DOCUMENTS,
                        "/Document",
                        AnalyzeStorageConstants$UiItemType.DOCUMENTS);
        DOCUMENTS = categoryType4;
        PageType pageType = PageType.DOWNLOADS;
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType =
                AnalyzeStorageConstants$UiItemType.INVALID_ITEM;
        CategoryType categoryType5 =
                new CategoryType(
                        "DOWNLOADS", 4, pageType, "/Downloads", analyzeStorageConstants$UiItemType);
        DOWNLOADS = categoryType5;
        CategoryType categoryType6 =
                new CategoryType(
                        "APK", 5, PageType.APK, "/Apk", AnalyzeStorageConstants$UiItemType.APK);
        APK = categoryType6;
        PageType pageType2 = PageType.NONE;
        CategoryType categoryType7 =
                new CategoryType(
                        "COMPRESSED",
                        6,
                        pageType2,
                        "/Compressed",
                        AnalyzeStorageConstants$UiItemType.COMPRESSED);
        COMPRESSED = categoryType7;
        CategoryType categoryType8 =
                new CategoryType(
                        "NONE", 7, pageType2, "/Blank", analyzeStorageConstants$UiItemType);
        NONE = categoryType8;
        CategoryType[] categoryTypeArr = {
            categoryType,
            categoryType2,
            categoryType3,
            categoryType4,
            categoryType5,
            categoryType6,
            categoryType7,
            categoryType8
        };
        $VALUES = categoryTypeArr;
        EnumEntriesKt.enumEntries(categoryTypeArr);
        Companion = new Companion();
    }

    public CategoryType(
            String str,
            int i,
            PageType pageType,
            String str2,
            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType) {
        this.pageType = pageType;
        this.path = str2;
        this.overViewItem = analyzeStorageConstants$UiItemType;
    }

    public static final CategoryType getTypeFromOverViewItem(
            AnalyzeStorageConstants$UiItemType item) {
        Companion.getClass();
        Intrinsics.checkNotNullParameter(item, "item");
        int ordinal = item.ordinal();
        return ordinal != 0
                ? ordinal != 1
                        ? ordinal != 2
                                ? ordinal != 3
                                        ? ordinal != 4 ? ordinal != 5 ? NONE : COMPRESSED : APK
                                        : DOCUMENTS
                                : AUDIO
                        : VIDEOS
                : IMAGES;
    }

    public static CategoryType valueOf(String str) {
        return (CategoryType) Enum.valueOf(CategoryType.class, str);
    }

    public static CategoryType[] values() {
        return (CategoryType[]) $VALUES.clone();
    }

    public final AnalyzeStorageConstants$UiItemType getOverViewItem() {
        return this.overViewItem;
    }
}
