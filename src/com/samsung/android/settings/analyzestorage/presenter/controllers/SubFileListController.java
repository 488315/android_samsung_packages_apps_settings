package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.app.Application;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.lifecycle.MutableLiveData;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.FileListObserver;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListLoading;
import com.samsung.android.settings.analyzestorage.presenter.controllers.menu.MenuDirector;
import com.samsung.android.settings.analyzestorage.presenter.managers.ConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SubFileListController extends FileListController implements CheckedItemsDetailsLoader.DetailsResultListener {
    public final AnonymousClass1 mContentObserver;
    public final MutableLiveData mListItemTotalSizeData;
    public final MutableLiveData mLoading;
    public boolean mNeedUpdate;
    public PageInfo mSAHomePageInfo;
    public int mSaType;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.database.ContentObserver, com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.FileListController$1] */
    public SubFileListController(Application application, int i, int i2, PageInfo pageInfo) {
        super(application, i);
        this.mTag = "DataListPageController";
        this.mListLoading = new ListLoading(this.mListItemHandler);
        this.mMenuDirector = new MenuDirector(this);
        this.mRefreshHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.FileListController.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    FileListController.this.loadListItem(((Boolean) message.obj).booleanValue());
                }
            }
        };
        this.mTag = "FileListController";
        this.mFileListObserver = new FileListObserver(application, this);
        this.mBroadcastListener = new BroadcastListeners(application, this);
        this.mNeedUpdate = true;
        this.mListItemTotalSizeData = new MutableLiveData();
        this.mLoading = new MutableLiveData();
        ?? r2 = new ContentObserver(new Handler()) { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                super.onChange(z);
                SubFileListController subFileListController = SubFileListController.this;
                if (subFileListController.mNeedUpdate) {
                    Log.d(subFileListController.mTag, "onChange");
                    SubFileListController.this.refresh(false);
                }
            }
        };
        this.mContentObserver = r2;
        this.mTag = "SubFileListController";
        this.mSaType = i2;
        setPageInfo(pageInfo);
        this.mContext.getContentResolver().registerContentObserver(Uri.parse("content://myfiles").buildUpon().appendPath("setting").appendPath(Integer.toString(this.mSaType)).build(), false, r2);
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.FileListController, com.samsung.android.settings.analyzestorage.presenter.controllers.DataListPageController, com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public final void onCleared() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        super.onCleared();
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader.DetailsResultListener
    public final void onResult(CheckedItemsDetailsLoader.DirInfo dirInfo) {
        this.mListItemTotalSizeData.setValue(Long.valueOf(dirInfo.mSize));
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.PageControllerInterface
    public final void refresh(boolean z) {
        this.mListItemTotalSizeData.setValue(0L);
        loadListItem(z);
    }

    public final void setLoadingData(boolean z) {
        Log.d(this.mTag, "setLoadingData, saType : " + this.mSaType + ", value : " + z);
        this.mLoading.setValue(Boolean.valueOf(z));
    }

    public final void setPageInfo(PageInfo pageInfo) {
        this.mSAHomePageInfo = pageInfo;
        PageInfo pageInfo2 = (PageInfo) ConvertManager.sSaTypeToPageInfo.get(this.mSaType, new PageInfo(PageType.ANALYZE_STORAGE_HOME));
        this.mPageInfo = pageInfo2;
        pageInfo2.mActivityType = this.mSAHomePageInfo.mActivityType;
        pageInfo2.putExtra(this.mSaType, "asType");
        this.mPageInfo.putExtra(1, "instanceId");
    }
}
