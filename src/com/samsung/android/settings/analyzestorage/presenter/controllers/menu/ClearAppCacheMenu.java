package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.DataInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.FileOperationArgs;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.ProgressListener;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.ui.dialog.SpinnerProgressDialogFragment;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ClearAppCacheMenu extends AbsMenu {
    public final PrepareMenu menuExecute;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClearAppCacheMenu(AbsPageController controller, PrepareMenu prepareMenu) {
        super(controller);
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.menuExecute = prepareMenu;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu
    public final ExecutionParams getParams() {
        AbsPageController absPageController = this.controller;
        ExecutionParams executionParams = new ExecutionParams(absPageController.mContext);
        executionParams.mPageInfo = new PageInfo(this.pageInfo);
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
                                FileType.CHM,
                                absPageController.mInstanceId,
                                absPageController.mListItemHandler)
                        : null;
        executionParams.mProgressListener =
                new ProgressListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.presenter.controllers.menu.ClearAppCacheMenu$getParams$1
                    public final SpinnerProgressDialogFragment dialog;

                    {
                        PrepareMenu prepareMenu2 = this.menuExecute;
                        this.dialog =
                                prepareMenu2 != null
                                        ? prepareMenu2.getProgressDialog(FileType.CHM)
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
        Intrinsics.checkNotNullExpressionValue(
                result.mPackageSuccessList, "getPackageSuccessList(...)");
        if (!((ArrayList) r0).isEmpty()) {
            AbsPageController absPageController = this.controller;
            ListItemHandler listItemHandler = absPageController.mListItemHandler;
            final List list = result.mPackageSuccessList;
            StringBuilder sb = new StringBuilder("mListItems + ");
            MutableLiveData mutableLiveData = listItemHandler.mListItems;
            sb.append(mutableLiveData);
            Log.d("ListItemHandler", sb.toString());
            Log.d("ListItemHandler", "mAppDataList + " + listItemHandler.mAppDataList);
            List list2 = (List) mutableLiveData.getValue();
            Objects.requireNonNull(list2);
            final int i = 0;
            List list3 =
                    (List)
                            list2.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    int i2 = i;
                                                    List list4 = list;
                                                    DataInfo dataInfo = (DataInfo) obj;
                                                    switch (i2) {
                                                    }
                                                    return !list4.contains(dataInfo.getUniqueId());
                                                }
                                            })
                                    .collect(Collectors.toList());
            final int i2 = 1;
            List list4 =
                    (List)
                            listItemHandler.mAppDataList.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    int i22 = i2;
                                                    List list42 = list;
                                                    DataInfo dataInfo = (DataInfo) obj;
                                                    switch (i22) {
                                                    }
                                                    return !list42.contains(dataInfo.getUniqueId());
                                                }
                                            })
                                    .collect(Collectors.toList());
            ((ArrayList) listItemHandler.mAppDataList).clear();
            ((ArrayList) listItemHandler.mAppDataList).addAll(list4);
            Log.d("ListItemHandler", "cloneItemList + " + list3);
            Log.d("ListItemHandler", "cloneAppDataList + " + list4);
            listItemHandler.setListItems(list3);
            absPageController.mListItemHandler.resetCheckedItemInfo();
        }
    }
}
