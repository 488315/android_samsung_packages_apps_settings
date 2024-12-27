package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.presenter.managers.TrashManager;
import com.samsung.android.settings.analyzestorage.presenter.utils.ContentResolverWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AppListController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AppListController f$0;

    public /* synthetic */ AppListController$$ExternalSyntheticLambda0(
            AppListController appListController) {
        this.f$0 = appListController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final AppListController appListController = this.f$0;
        appListController.getClass();
        Bundle bundle = new Bundle();
        bundle.putString("calling_package", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        bundle.putInt("asType", appListController.mSaAppsType);
        Log.d(
                appListController.mTag,
                "loadListItem()] call type : " + appListController.mSaAppsType);
        try {
            final Bundle call =
                    ContentResolverWrapper.call(
                            appListController.mContext,
                            Uri.parse("content://myfiles/"),
                            "GET_AS_SIZE",
                            bundle);
            ThreadExecutor.runOnMainThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppListController appListController2 = AppListController.this;
                            Bundle bundle2 = call;
                            if (bundle2 == null || appListController2.mSaAppsType != 3) {
                                Log.e(
                                        appListController2.mTag,
                                        "updateAppCapacity() ] bundle is null");
                                appListController2.setLoadingData(false);
                                return;
                            }
                            long j = bundle2.getLong("TRASH_INTERNAL_CAPACITY", 0L);
                            long j2 = bundle2.getLong("TRASH_SDCARD_CAPACITY", 0L);
                            long j3 = bundle2.getLong("TRASH_INTERNAL_APP_CLONE_CAPACITY", 0L);
                            long j4 = j + j2 + j3;
                            Log.d(
                                    appListController2.mTag,
                                    "loadListItem()] type : "
                                            + appListController2.mSaAppsType
                                            + ", size : "
                                            + j4);
                            boolean z =
                                    (TrashManager.appTrashInternalCapacity == j
                                                    && TrashManager.appTrashExternalSDCapacity == j2
                                                    && TrashManager.appTrashAppCloneCapacity == j3)
                                            ? false
                                            : true;
                            TrashManager.appTrashInternalCapacity = j;
                            TrashManager.appTrashExternalSDCapacity = j2;
                            TrashManager.appTrashAppCloneCapacity = j3;
                            TrashManager.isLoadCompleted.setValue(Boolean.valueOf(z));
                            Log.d(
                                    "TrashManager",
                                    "setAppTrashCapacity() ] isLoaded : "
                                            + z
                                            + " internal trash: "
                                            + j);
                            TrashManager.appDataCapacity =
                                    bundle2.getLong("TRASH_APP_DATA_CAPACITY", 0L);
                            appListController2.setLoadingData(false);
                            appListController2.mAppListTotalSizeData.setValue(Long.valueOf(j4));
                        }
                    });
        } catch (SQLiteException e) {
            Log.e(appListController.mTag, "updateAppCapacity() ] Exception e : " + e.getMessage());
        }
    }
}
