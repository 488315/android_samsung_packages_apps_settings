package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.FileListObserver;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FileListController extends DataListPageController {
    public BroadcastListeners mBroadcastListener;
    public FileListObserver mFileListObserver;
    public AnonymousClass1 mRefreshHandler;

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController
    public final boolean isFileListController() {
        return true;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.DataListPageController, com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public void onCleared() {
        removeMessages(0);
        this.mFileListObserver.removeObserver();
        BroadcastListeners broadcastListeners = this.mBroadcastListener;
        broadcastListeners.getClass();
        SparseArray sparseArray = BroadcastReceiveCenter.sInstanceMap;
        BroadcastReceiveCenter broadcastReceiveCenter =
                BroadcastReceiveCenter.InstanceHolder.INSTANCE;
        broadcastReceiveCenter.removeDynamicListener(
                BroadcastType.MEDIA_MOUNTED, broadcastListeners.mStorageChangeListener);
        BroadcastType broadcastType = BroadcastType.MEDIA_UNMOUNTED;
        BroadcastListeners.AnonymousClass1 anonymousClass1 =
                broadcastListeners.mStorageEjectListener;
        broadcastReceiveCenter.removeDynamicListener(broadcastType, anonymousClass1);
        broadcastReceiveCenter.removeDynamicListener(BroadcastType.MEDIA_EJECTED, anonymousClass1);
        broadcastReceiveCenter.removeDynamicListener(BroadcastType.MEDIA_REMOVED, anonymousClass1);
        broadcastReceiveCenter.removeListener(
                BroadcastType.TIMEZONE_CHANGED, broadcastListeners.mTimeSetBroadcastReceiver);
        broadcastReceiveCenter.removeListener(
                BroadcastType.LOCALE_CHANGED, broadcastListeners.mLocaleChangedBroadcastReceiver);
        broadcastReceiveCenter.removeListener(
                BroadcastType.CACHED_FILES_CHANGED, broadcastListeners.mCachedFilesChanged);
        broadcastReceiveCenter.removeListener(
                BroadcastType.NEED_REFRESH, broadcastListeners.mNeedRefreshListener);
        MenuExecuteManager.sCallbackListener.remove(this);
        super.onCleared();
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.DataListControllerInterface
    public final void refresh$1(boolean z) {
        AnonymousClass1 anonymousClass1 = this.mRefreshHandler;
        if (anonymousClass1.hasMessages(0)) {
            anonymousClass1.removeMessages(0);
        }
        anonymousClass1.sendMessageDelayed(
                anonymousClass1.obtainMessage(0, Boolean.valueOf(z)), 200);
    }
}
