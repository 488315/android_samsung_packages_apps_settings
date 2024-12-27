package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.FileOperationArgs;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.ProgressListener;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager;
import com.samsung.android.settings.analyzestorage.ui.dialog.SpinnerProgressDialogFragment;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UninstallMenu extends AbsMenu {
    public final PrepareMenu menuExecute;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UninstallMenu(AbsPageController controller, PrepareMenu prepareMenu) {
        super(controller);
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.menuExecute = prepareMenu;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public ExecutionParams getParams() {
        AbsPageController absPageController = this.controller;
        ExecutionParams executionParams = new ExecutionParams(absPageController.mContext);
        executionParams.mPageInfo = this.pageInfo;
        List list = this.listItemInterface.mCheckedItemList;
        Intrinsics.checkNotNullExpressionValue(list, "getCheckedItemList(...)");
        if (((ArrayList) list).isEmpty()) {
            SparseArray sparseArray = KeyboardMouseManager.sEventContextMap;
            KeyboardMouseManager.KeyboardMouseManagerHolder.INSTANCE.getClass();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof AppInfo) {
                arrayList.add(next);
            }
        }
        if (!arrayList.isEmpty()) {
            FileOperationArgs fileOperationArgs = executionParams.mFileOperationArgs;
            if (fileOperationArgs == null && fileOperationArgs == null) {
                executionParams.mFileOperationArgs = new FileOperationArgs();
            }
            executionParams.mFileOperationArgs.mSelectedApps = arrayList;
        }
        PrepareMenu prepareMenu = this.menuExecute;
        executionParams.mDialog =
                prepareMenu != null
                        ? prepareMenu.getDialog(
                                FileType.CELL,
                                absPageController.mInstanceId,
                                absPageController.mListItemHandler)
                        : null;
        executionParams.mProgressListener =
                new ProgressListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.UninstallMenu$getParams$1
                    public final SpinnerProgressDialogFragment dialog;

                    {
                        PrepareMenu prepareMenu2 = this.menuExecute;
                        this.dialog =
                                prepareMenu2 != null
                                        ? prepareMenu2.getProgressDialog(FileType.CELL)
                                        : null;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.ProgressListener
                    public final void onFinishProgress() {
                        SpinnerProgressDialogFragment spinnerProgressDialogFragment = this.dialog;
                        if (spinnerProgressDialogFragment == null
                                || spinnerProgressDialogFragment.baseFragmentManager == null) {
                            return;
                        }
                        spinnerProgressDialogFragment.dismissAllowingStateLoss();
                    }

                    @Override // com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.ProgressListener
                    public final void onPrepareProgress(FileOperationArgs args) {
                        Intrinsics.checkNotNullParameter(args, "args");
                        SpinnerProgressDialogFragment spinnerProgressDialogFragment = this.dialog;
                        if (spinnerProgressDialogFragment != null) {
                            spinnerProgressDialogFragment.onPrepareProgress(args);
                        }
                    }
                };
        return executionParams;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final void result(MenuExecuteManager.Result result) {
        if (result.mNeedRefresh) {
            AbsPageController absPageController = this.controller;
            absPageController.refresh(true);
            absPageController.mListItemHandler.resetCheckedItemInfo();
        }
    }
}
