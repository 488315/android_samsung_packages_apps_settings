package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ConvertManager {
    public static final SparseArray sSaTypeToPageInfo;

    static {
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        sSaTypeToPageInfo = sparseArray2;
        sparseArray.append(0, PageType.LOCAL_INTERNAL);
        sparseArray.append(2, PageType.LOCAL_APP_CLONE);
        sparseArray.append(1, PageType.LOCAL_SDCARD);
        PageType pageType = PageType.LOCAL_USB;
        sparseArray.append(10, pageType);
        sparseArray.append(11, pageType);
        sparseArray.append(12, pageType);
        sparseArray.append(13, pageType);
        sparseArray.append(14, pageType);
        sparseArray.append(15, pageType);
        sparseArray.append(101, PageType.GOOGLE_DRIVE);
        sparseArray.append(102, PageType.ONE_DRIVE);
        sparseArray.append(306, PageType.ANALYZE_STORAGE_HOME);
        sparseArray2.append(0, new PageInfo(PageType.ANALYZE_STORAGE_LARGE_FILES));
        sparseArray2.append(1, new PageInfo(PageType.ANALYZE_STORAGE_DUPLICATED_FILES));
        sparseArray2.append(2, new PageInfo(PageType.ANALYZE_STORAGE_CACHED_FILES));
        sparseArray2.append(4, new PageInfo(PageType.ANALYZE_STORAGE_UNUSED_APPS));
        sparseArray2.append(5, new PageInfo(PageType.ANALYZE_STORAGE_APP_CACHE));
    }
}
