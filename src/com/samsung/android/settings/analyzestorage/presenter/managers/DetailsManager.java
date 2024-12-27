package com.samsung.android.settings.analyzestorage.presenter.managers;

import com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.AbsDetailsInfoLoader$LoadHandler;
import com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DetailsManager {
    public static DetailsManager instance;
    public final CheckedItemsDetailsLoader checkedItemLoader;

    public DetailsManager() {
        CheckedItemsDetailsLoader checkedItemsDetailsLoader = new CheckedItemsDetailsLoader();
        this.checkedItemLoader = checkedItemsDetailsLoader;
        checkedItemsDetailsLoader.mHandlerThread.start();
        checkedItemsDetailsLoader.mLoadHandler =
                new AbsDetailsInfoLoader$LoadHandler(
                        checkedItemsDetailsLoader,
                        checkedItemsDetailsLoader.mHandlerThread.getLooper(),
                        0);
    }
}
