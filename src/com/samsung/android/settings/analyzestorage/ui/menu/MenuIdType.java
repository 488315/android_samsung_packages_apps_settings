package com.samsung.android.settings.analyzestorage.ui.menu;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
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
                + "\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001"
                + "\tR\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n"
                + "\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006¢\u0006\f\n"
                + "\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006¨\u0006\n"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/menu/MenuIdType;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "menuId",
            ImsProfile.TIMER_NAME_I,
            "getMenuId",
            "()I",
            "menuType",
            "getMenuType",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class MenuIdType {
    public static final /* synthetic */ MenuIdType[] $VALUES;
    public static final MenuIdType CANCEL;
    public static final MenuIdType CLEAR_APP_CACHE;
    public static final Companion Companion;
    public static final MenuIdType FORMAT;
    public static final MenuIdType NONE;
    public static final MenuIdType UNINSTALL;
    private final int menuId;
    private final int menuType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    static {
        MenuIdType menuIdType = new MenuIdType(0, R.id.uninstall, FileType.CELL, "UNINSTALL");
        UNINSTALL = menuIdType;
        MenuIdType menuIdType2 =
                new MenuIdType(1, R.id.clear_app_cache, FileType.CHM, "CLEAR_APP_CACHE");
        CLEAR_APP_CACHE = menuIdType2;
        MenuIdType menuIdType3 = new MenuIdType(2, R.id.unmount, 390, "UNMOUNT");
        MenuIdType menuIdType4 = new MenuIdType(3, R.id.format, 400, "FORMAT");
        FORMAT = menuIdType4;
        MenuIdType menuIdType5 = new MenuIdType(4, R.id.cancel, 210, "CANCEL");
        CANCEL = menuIdType5;
        MenuIdType menuIdType6 = new MenuIdType(5, 0, -1, "NONE");
        NONE = menuIdType6;
        MenuIdType[] menuIdTypeArr = {
            menuIdType, menuIdType2, menuIdType3, menuIdType4, menuIdType5, menuIdType6
        };
        $VALUES = menuIdTypeArr;
        EnumEntriesKt.enumEntries(menuIdTypeArr);
        Companion = new Companion();
    }

    public MenuIdType(int i, int i2, int i3, String str) {
        this.menuId = i2;
        this.menuType = i3;
    }

    public static MenuIdType valueOf(String str) {
        return (MenuIdType) Enum.valueOf(MenuIdType.class, str);
    }

    public static MenuIdType[] values() {
        return (MenuIdType[]) $VALUES.clone();
    }

    public final int getMenuId() {
        return this.menuId;
    }

    public final int getMenuType() {
        return this.menuType;
    }
}
