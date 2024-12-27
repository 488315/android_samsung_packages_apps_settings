package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UnmountMenu extends AbsMenu {
    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final ExecutionParams getParams() {
        AbsPageController absPageController = this.controller;
        ExecutionParams executionParams = new ExecutionParams(absPageController.mContext);
        executionParams.mPageInfo = absPageController.mPageInfo;
        return executionParams;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final void result(MenuExecuteManager.Result result) {}
}
