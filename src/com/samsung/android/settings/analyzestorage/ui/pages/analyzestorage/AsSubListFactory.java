package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0007R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n"
                + "\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "saType",
            ImsProfile.TIMER_NAME_I,
            "getSaType",
            "()I",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public abstract class AsSubListFactory {
    public static final /* synthetic */ AsSubListFactory[] $VALUES;
    public static final Companion Companion;
    private final int saType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bÆ\u0001\u0018\u00002\u00020\u0001¨\u0006\u0002"
            },
            d2 = {
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory$APP_CACHE;",
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public static final class APP_CACHE extends AsSubListFactory {
        @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory
        public final AsSubListInterface createASList() {
            return new AsAppCache();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bÆ\u0001\u0018\u00002\u00020\u0001¨\u0006\u0002"
            },
            d2 = {
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory$DUPLICATED_FILES;",
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public static final class DUPLICATED_FILES extends AsSubListFactory {
        @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory
        public final AsSubListInterface createASList() {
            return new AsDuplicatedFiles();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bÆ\u0001\u0018\u00002\u00020\u0001¨\u0006\u0002"
            },
            d2 = {
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory$LARGE_FILES;",
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public static final class LARGE_FILES extends AsSubListFactory {
        @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory
        public final AsSubListInterface createASList() {
            return new AsLargeFiles();
        }

        @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory
        public final boolean isFileType() {
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bÆ\u0001\u0018\u00002\u00020\u0001¨\u0006\u0002"
            },
            d2 = {
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory$TRASH_APPS;",
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public static final class TRASH_APPS extends AsSubListFactory {
        @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory
        public final AsSubListInterface createASList() {
            return new AsTrashApps();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bÆ\u0001\u0018\u00002\u00020\u0001¨\u0006\u0002"
            },
            d2 = {
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory$UNUSED_APPS;",
                "Lcom/samsung/android/settings/analyzestorage/ui/pages/analyzestorage/AsSubListFactory;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public static final class UNUSED_APPS extends AsSubListFactory {
        @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubListFactory
        public final AsSubListInterface createASList() {
            return new AsUnusedApps();
        }
    }

    static {
        AsSubListFactory[] asSubListFactoryArr = {
            new LARGE_FILES("LARGE_FILES", 0, 0),
            new DUPLICATED_FILES("DUPLICATED_FILES", 1, 1),
            new TRASH_APPS("TRASH_APPS", 2, 3),
            new UNUSED_APPS("UNUSED_APPS", 3, 4),
            new APP_CACHE("APP_CACHE", 4, 5)
        };
        $VALUES = asSubListFactoryArr;
        EnumEntriesKt.enumEntries(asSubListFactoryArr);
        Companion = new Companion();
    }

    public AsSubListFactory(String str, int i, int i2) {
        this.saType = i2;
    }

    public static AsSubListFactory valueOf(String str) {
        return (AsSubListFactory) Enum.valueOf(AsSubListFactory.class, str);
    }

    public static AsSubListFactory[] values() {
        return (AsSubListFactory[]) $VALUES.clone();
    }

    public abstract AsSubListInterface createASList();

    public final int getSaType() {
        return this.saType;
    }

    public boolean isFileType() {
        return this instanceof DUPLICATED_FILES;
    }
}
