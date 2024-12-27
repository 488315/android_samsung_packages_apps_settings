package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.EventListener;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbsPageController extends ViewModel
        implements PageControllerInterface, MenuExecuteManager.ResultListener {
    public final Context mContext;
    public final int mInstanceId;
    public PageInfo mPageInfo;
    public String mTag = "AbsPageController";
    public EventListener mListener = new EventListener();
    public boolean mIsCleared = false;
    public final ListItemHandler mListItemHandler = createListItemHandler();

    public AbsPageController(Context context, int i) {
        this.mContext = context;
        this.mInstanceId = i;
    }

    public ListItemHandler createListItemHandler() {
        return new ListItemHandler();
    }

    public boolean isFileListController() {
        return false;
    }

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        Log.d(this.mTag, "onCleared");
        if (this.mListener != null) {
            this.mListener = null;
        }
        this.mIsCleared = true;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager.ResultListener
    public void onResult(MenuExecuteManager.Result result) {}
}
