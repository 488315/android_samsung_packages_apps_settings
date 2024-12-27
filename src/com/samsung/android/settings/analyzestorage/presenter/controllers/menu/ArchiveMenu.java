package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.FileOperationArgs;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.ProgressListener;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;
import com.samsung.android.settings.analyzestorage.ui.dialog.SpinnerProgressDialogFragment;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ArchiveMenu extends UninstallMenu {
    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.UninstallMenu, com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final ExecutionParams getParams() {
        AbsDialog absDialog;
        ExecutionParams params = super.getParams();
        PrepareMenu prepareMenu = this.menuExecute;
        if (prepareMenu != null) {
            AbsPageController absPageController = this.controller;
            absDialog =
                    prepareMenu.getDialog(
                            750, absPageController.mInstanceId, absPageController.mListItemHandler);
        } else {
            absDialog = null;
        }
        params.mDialog = absDialog;
        params.mProgressListener =
                new ProgressListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.ArchiveMenu$getParams$1
                    public final SpinnerProgressDialogFragment dialog;

                    {
                        PrepareMenu prepareMenu2 = this.menuExecute;
                        this.dialog =
                                prepareMenu2 != null ? prepareMenu2.getProgressDialog(750) : null;
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
        return params;
    }
}
