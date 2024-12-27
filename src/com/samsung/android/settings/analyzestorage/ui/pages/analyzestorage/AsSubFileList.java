package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsSubFileList extends AnalyzeStorageSubList {
    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final MutableLiveData getListItemData() {
        ListItemHandler listItemHandler;
        SubFileListController subFileListController = (SubFileListController) this.controller;
        MutableLiveData mutableLiveData =
                (subFileListController == null
                                || (listItemHandler = subFileListController.mListItemHandler)
                                        == null)
                        ? null
                        : listItemHandler.mListItems;
        if (mutableLiveData instanceof LiveData) {
            return mutableLiveData;
        }
        return null;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final MutableLiveData getListItemTotalSizeData() {
        SubFileListController subFileListController = (SubFileListController) this.controller;
        if (subFileListController != null) {
            return subFileListController.mListItemTotalSizeData;
        }
        return null;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final MutableLiveData getListLoading() {
        SubFileListController subFileListController = (SubFileListController) this.controller;
        if (subFileListController != null) {
            return subFileListController.mLoading;
        }
        return null;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final void initLayout() {
        super.initLayout();
        View view = this.rootView;
        if (view != null) {
            view.setOnClickListener(
                    new View.OnClickListener() { // from class:
                        // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubFileList$initLayout$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            AsSubFileList asSubFileList = AsSubFileList.this;
                            SubFileListController subFileListController =
                                    (SubFileListController) asSubFileList.controller;
                            if (subFileListController != null) {
                                final Intent intent =
                                        new Intent(
                                                "com.sec.android.app.myfiles.VIEW_ANALYZE_STORAGE_SUB_LIST");
                                intent.setPackage("com.sec.android.app.myfiles");
                                intent.putExtra("asType", subFileListController.mSaType);
                                Context context = ActivityInfoStore.context;
                                Optional.ofNullable(
                                                ActivityInfoStore.Companion.getInstance(
                                                                subFileListController.mInstanceId)
                                                        .getActivityResultLauncher())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        ((ActivityResultLauncher) obj)
                                                                .launch(intent);
                                                    }
                                                });
                                subFileListController.mNeedUpdate = false;
                                LoggingHelper.insertEventLogging(
                                        PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(),
                                        asSubFileList.getAsSubList().getSAEvent());
                            }
                        }
                    });
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final void refresh() {
        SubFileListController subFileListController = (SubFileListController) this.controller;
        if (subFileListController != null) {
            subFileListController.refresh(true);
        }
    }
}
