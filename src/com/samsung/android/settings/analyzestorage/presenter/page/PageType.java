package com.samsung.android.settings.analyzestorage.presenter.page;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum PageType {
    /* JADX INFO: Fake field, exist only in values array */
    HOME(DATA.DM_FIELD_INDEX.UT_APN_NAME),
    /* JADX INFO: Fake field, exist only in values array */
    BLANK(DATA.DM_FIELD_INDEX.UT_APN_NAME),
    IMAGES(DATA.DM_FIELD_INDEX.EHRPD_ENABLED),
    AUDIO(DATA.DM_FIELD_INDEX.IMS_ENABLED),
    VIDEOS(DATA.DM_FIELD_INDEX.VZW_EAB_MENU_SHOW),
    DOCUMENTS(DATA.DM_FIELD_INDEX.LVC_SUPPORTED),
    DOWNLOADS("119"),
    /* JADX INFO: Fake field, exist only in values array */
    VIEW_DOWNLOADS("233"),
    APK(DATA.DM_FIELD_INDEX.VOLTE_SUPPORTED),
    LOCAL_INTERNAL(DATA.DM_FIELD_INDEX.DEFAULT_BIT_RATE),
    LOCAL_APP_CLONE("138"),
    LOCAL_SDCARD(DATA.DM_FIELD_INDEX.VWF_ENABLED),
    LOCAL_SDCARD_UNMOUNT(DATA.DM_FIELD_INDEX.SPR_IMS_TPDCS),
    LOCAL_USB("135"),
    LOCAL_USB_UNMOUNT(DATA.DM_FIELD_INDEX.SPR_IMS_TPVP),
    GOOGLE_DRIVE(DATA.DM_FIELD_INDEX.SPR_IMS_PUID3),
    ONE_DRIVE(DATA.DM_FIELD_INDEX.SPR_IMS_ALPHA_ID),
    /* JADX INFO: Fake field, exist only in values array */
    SEARCH_SETTINGS(DATA.DM_FIELD_INDEX.SPR_IMS_TPSCA),
    SEARCH(DATA.DM_FIELD_INDEX.SPR_IMS_PCSCF_ADDR_TYPE),
    ANALYZE_STORAGE_HOME("8801"),
    ANALYZE_STORAGE_DUPLICATED_FILES("200"),
    ANALYZE_STORAGE_LARGE_FILES("203"),
    ANALYZE_STORAGE_CACHED_FILES("205"),
    ANALYZE_STORAGE_UNUSED_APPS("8803"),
    ANALYZE_STORAGE_APP_CACHE("8802"),
    ANALYZE_STORAGE_SUB_USER_USAGE_DETAIL(ApnSettings.MVNO_NONE),
    ANALYZE_STORAGE_DEVICE_CARE("241"),
    /* JADX INFO: Fake field, exist only in values array */
    SEARCH_SETTINGS(ApnSettings.MVNO_NONE),
    NONE(ApnSettings.MVNO_NONE);

    private final String mScreenIdForSA;
    private final String mSelectionScreenIdForSA;

    PageType(String str) {
        this.mScreenIdForSA = str;
        this.mSelectionScreenIdForSA =
                !str.isEmpty() ? String.valueOf(Integer.parseInt(str) + 1) : ApnSettings.MVNO_NONE;
    }

    public final String getScreenIdForSA() {
        return this.mScreenIdForSA;
    }
}
