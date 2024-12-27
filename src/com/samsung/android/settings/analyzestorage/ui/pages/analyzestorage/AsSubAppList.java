package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.managers.TrashManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsSubAppList extends AnalyzeStorageSubList {
    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final MutableLiveData getListItemData() {
        AppListController appListController = (AppListController) this.controller;
        if (appListController != null) {
            return appListController.mListItems;
        }
        return null;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final MutableLiveData getListItemTotalSizeData() {
        AppListController appListController = (AppListController) this.controller;
        if (appListController != null) {
            return appListController.mAppListTotalSizeData;
        }
        return null;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final MutableLiveData getListLoading() {
        AppListController appListController = (AppListController) this.controller;
        if (appListController != null) {
            return appListController.mAppListLoading;
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
                        // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubAppList$initLayout$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            final Intent intent;
                            AsSubAppList asSubAppList = AsSubAppList.this;
                            AppListController appListController =
                                    (AppListController) asSubAppList.controller;
                            if (appListController != null) {
                                int i = appListController.mInstanceId;
                                int i2 = asSubAppList.type;
                                if (i2 == 3) {
                                    intent =
                                            new Intent(
                                                    "com.sec.android.app.myfiles.VIEW_ANALYZE_STORAGE_SUB_LIST");
                                    intent.setPackage("com.sec.android.app.myfiles");
                                    intent.putExtra("asType", i2);
                                } else {
                                    intent =
                                            new Intent(
                                                    i2 == 4
                                                            ? "com.samsung.android.settings.analyzestorage.ENTER_UNUSED_APPS"
                                                            : "com.samsung.android.settings.analyzestorage.ENTER_APP_CACHE");
                                    intent.putExtra("instanceId", i);
                                }
                                Context context = ActivityInfoStore.context;
                                Optional.ofNullable(
                                                ActivityInfoStore.Companion.getInstance(i)
                                                        .getActivityResultLauncher())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        ((ActivityResultLauncher) obj)
                                                                .launch(intent);
                                                    }
                                                });
                            }
                            AsSubAppList asSubAppList2 = AsSubAppList.this;
                            asSubAppList2.getClass();
                            LoggingHelper.insertEventLogging(
                                    PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(),
                                    asSubAppList2.getAsSubList().getSAEvent());
                        }
                    });
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final void observeTotalSize() {
        if (this.type == 4) {
            super.observeTotalSize();
            return;
        }
        MutableLiveData listItemTotalSizeData = getListItemTotalSizeData();
        if (listItemTotalSizeData != null) {
            listItemTotalSizeData.observe(
                    this.lifecycleOwner,
                    new Observer() { // from class:
                                     // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AsSubAppList$observeTotalSize$1
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            Long l = (Long) obj;
                            boolean z = false;
                            AsSubAppList asSubAppList = AsSubAppList.this;
                            if (l != null) {
                                long longValue = l.longValue();
                                TextView textView = asSubAppList.headerSize;
                                if (textView != null) {
                                    textView.setText(
                                            l.longValue() > 0
                                                    ? StringConverter.formatFileSize(
                                                            0, longValue, asSubAppList.context)
                                                    : ApnSettings.MVNO_NONE);
                                }
                            }
                            AnalyzeStorageSubList.SizeInfo sizeInfo = asSubAppList.sizeInfo;
                            if (l != null && l.longValue() >= 0) {
                                z = true;
                            }
                            sizeInfo.supportSize.setValue(Boolean.valueOf(z));
                        }
                    });
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final void onResume() {
        if (this.type == 3) {
            refresh();
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList
    public final void refresh() {
        AppListController appListController = (AppListController) this.controller;
        if (appListController != null) {
            TrashManager.isLoadCompleted.setValue(Boolean.FALSE);
            appListController.mAppListTotalSizeData.setValue(0L);
            appListController.setLoadingData(true);
            appListController.loadListItem(true);
        }
    }
}
