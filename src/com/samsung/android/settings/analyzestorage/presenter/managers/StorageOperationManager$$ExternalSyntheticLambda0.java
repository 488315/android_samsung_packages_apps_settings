package com.samsung.android.settings.analyzestorage.presenter.managers;

import com.samsung.android.settings.analyzestorage.ui.menu.MenuIdType;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class StorageOperationManager$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        OverView overView = (OverView) obj;
        if (overView.activity.semIsResumed()) {
            overView.controller.mMenuDirector.execute(
                    MenuIdType.FORMAT.getMenuType(), new PrepareMenu());
        }
    }
}
