package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FormatMenu extends AbsMenu {
    public final PrepareMenu menuExecute;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FormatMenu(AbsPageController controller, PrepareMenu prepareMenu) {
        super(controller);
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.menuExecute = prepareMenu;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final ExecutionParams getParams() {
        AbsPageController absPageController = this.controller;
        ExecutionParams executionParams = new ExecutionParams(absPageController.mContext);
        executionParams.mPageInfo = absPageController.mPageInfo;
        PrepareMenu prepareMenu = this.menuExecute;
        executionParams.mDialog =
                prepareMenu != null
                        ? prepareMenu.getDialog(
                                400,
                                absPageController.mInstanceId,
                                absPageController.mListItemHandler)
                        : null;
        return executionParams;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final void result(MenuExecuteManager.Result result) {}
}
