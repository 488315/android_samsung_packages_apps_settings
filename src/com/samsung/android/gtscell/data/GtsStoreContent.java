package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u00004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b6\u0018\u00002\u00020\u0001:\u0006\r"
                + "\u000e\u000f\u0010\u0011\u0012B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0007\u0010\bR\u0012\u0010"
                + "\t\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\n"
                + "\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000b\u0010\f\u0082\u0001\u0006\u0013\u0014\u0015\u0016\u0017\u0018¨\u0006\u0019"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
            ApnSettings.MVNO_NONE,
            "type",
            "Lcom/samsung/android/gtscell/data/GtsStoreType;",
            "(Lcom/samsung/android/gtscell/data/GtsStoreType;)V",
            "contentType",
            ApnSettings.MVNO_NONE,
            "getContentType",
            "()Ljava/lang/String;",
            "packageName",
            "getPackageName",
            "getType",
            "()Lcom/samsung/android/gtscell/data/GtsStoreType;",
            "GalaxyOpenTheme",
            "GalaxyStoreApp",
            "GalaxyStoreSticker",
            "GalaxyThemeIconPack",
            "GalaxyThemeWallpaper",
            "PlayStoreApp",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent$PlayStoreApp;",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyStoreApp;",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyStoreSticker;",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyOpenTheme;",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyThemeWallpaper;",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyThemeIconPack;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public abstract class GtsStoreContent {
    private final String contentType;
    private final GtsStoreType type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyOpenTheme;",
                "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
                "packageName",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;)V",
                "getPackageName",
                "()Ljava/lang/String;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class GalaxyOpenTheme extends GtsStoreContent {
        private final String packageName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GalaxyOpenTheme(String packageName) {
            super(GtsStoreType.GALAXY_OPEN_THEME, null);
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            this.packageName = packageName;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getPackageName() {
            return this.packageName;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyStoreApp;",
                "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
                "packageName",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;)V",
                "getPackageName",
                "()Ljava/lang/String;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class GalaxyStoreApp extends GtsStoreContent {
        private final String packageName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GalaxyStoreApp(String packageName) {
            super(GtsStoreType.GALAXY_STORE_APP, null);
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            this.packageName = packageName;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getPackageName() {
            return this.packageName;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyStoreSticker;",
                "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
                "packageName",
                ApnSettings.MVNO_NONE,
                "contentType",
                "(Ljava/lang/String;Ljava/lang/String;)V",
                "getContentType",
                "()Ljava/lang/String;",
                "getPackageName",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class GalaxyStoreSticker extends GtsStoreContent {
        private final String contentType;
        private final String packageName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GalaxyStoreSticker(String packageName, String contentType) {
            super(GtsStoreType.GALAXY_STORE_STICKER, null);
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            Intrinsics.checkParameterIsNotNull(contentType, "contentType");
            this.packageName = packageName;
            this.contentType = contentType;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getContentType() {
            return this.contentType;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getPackageName() {
            return this.packageName;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyThemeIconPack;",
                "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
                "packageName",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;)V",
                "getPackageName",
                "()Ljava/lang/String;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class GalaxyThemeIconPack extends GtsStoreContent {
        private final String packageName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GalaxyThemeIconPack(String packageName) {
            super(GtsStoreType.GALAXY_THEME_ICON_PACK, null);
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            this.packageName = packageName;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getPackageName() {
            return this.packageName;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsStoreContent$GalaxyThemeWallpaper;",
                "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
                "packageName",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;)V",
                "getPackageName",
                "()Ljava/lang/String;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class GalaxyThemeWallpaper extends GtsStoreContent {
        private final String packageName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GalaxyThemeWallpaper(String packageName) {
            super(GtsStoreType.GALAXY_THEME_WALLPAPER, null);
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            this.packageName = packageName;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getPackageName() {
            return this.packageName;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0012\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsStoreContent$PlayStoreApp;",
                "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
                "packageName",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;)V",
                "getPackageName",
                "()Ljava/lang/String;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class PlayStoreApp extends GtsStoreContent {
        private final String packageName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PlayStoreApp(String packageName) {
            super(GtsStoreType.PLAY_STORE_APP, null);
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            this.packageName = packageName;
        }

        @Override // com.samsung.android.gtscell.data.GtsStoreContent
        public String getPackageName() {
            return this.packageName;
        }
    }

    private GtsStoreContent(GtsStoreType gtsStoreType) {
        this.type = gtsStoreType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public abstract String getPackageName();

    public final GtsStoreType getType() {
        return this.type;
    }

    public /* synthetic */ GtsStoreContent(
            GtsStoreType gtsStoreType, DefaultConstructorMarker defaultConstructorMarker) {
        this(gtsStoreType);
    }
}
