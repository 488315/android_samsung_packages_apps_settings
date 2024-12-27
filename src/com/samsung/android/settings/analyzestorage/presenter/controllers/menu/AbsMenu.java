package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbsMenu {
    public final AbsPageController controller;
    public final ListItemHandler listItemInterface;
    public final PageInfo pageInfo;

    public AbsMenu(AbsPageController controller) {
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.controller = controller;
        new ArrayList();
        new ArrayList();
        new ArrayList();
        ListItemHandler listItemHandler = controller.mListItemHandler;
        Intrinsics.checkNotNullExpressionValue(listItemHandler, "getListItemHandler(...)");
        this.listItemInterface = listItemHandler;
        this.pageInfo = controller.mPageInfo;
    }

    public abstract ExecutionParams getParams();

    public abstract void result(MenuExecuteManager.Result result);
}
