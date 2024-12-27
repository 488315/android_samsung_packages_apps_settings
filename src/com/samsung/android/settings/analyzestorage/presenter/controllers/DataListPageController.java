package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.settings.analyzestorage.data.database.repository.RepositoryFactory;
import com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IDataInfoRepository;
import com.samsung.android.settings.analyzestorage.domain.utils.CollectionUtils;
import com.samsung.android.settings.analyzestorage.external.database.datasource.MediaProviderDataSource;
import com.samsung.android.settings.analyzestorage.external.database.repository.AnalyzeStorageFileInfoRepositoryImpl;
import com.samsung.android.settings.analyzestorage.external.database.repository.RepositoryFactoryImpl;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.constant.MenuType;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListLoading;
import com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu;
import com.samsung.android.settings.analyzestorage.presenter.controllers.menu.MenuDirector;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoaderParams;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.LoadResult;
import com.samsung.android.settings.analyzestorage.presenter.exception.ExceptionListener;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.ui.exception.ExceptionHandler;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DataListPageController extends AbsPageController
        implements DataLoader.IDataLoaderCallback, DataListControllerInterface {
    public ListLoading mListLoading;
    public MenuDirector mMenuDirector;

    public final void loadListItem(boolean z) {
        SubFileListController subFileListController = (SubFileListController) this;
        subFileListController.setLoadingData(true);
        DataLoaderParams dataLoaderParams = new DataLoaderParams();
        dataLoaderParams.sessionId = subFileListController.mListLoading.mSessionId;
        PageInfo pageInfo = subFileListController.mPageInfo;
        Intrinsics.checkNotNullParameter(pageInfo, "<set-?>");
        dataLoaderParams.pageInfo = pageInfo;
        dataLoaderParams.forceUpdate = z;
        SparseArray sparseArray = new SparseArray();
        RepositoryFactory.getGenerator().getClass();
        sparseArray.put(
                0,
                AnalyzeStorageFileInfoRepositoryImpl.getInstance(
                        RepositoryFactoryImpl.sContext,
                        new MediaProviderDataSource(RepositoryFactoryImpl.sContext)));
        this.mListLoading.loadListItem(
                (IDataInfoRepository) sparseArray.get(0), dataLoaderParams, this);
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public void onCleared() {
        Context context = ActivityInfoStore.context;
        FragmentActivity fragmentActivity =
                ActivityInfoStore.Companion.getInstance(this.mInstanceId).getFragmentActivity();
        MenuExecuteManager.sCallbackListener.remove(this);
        this.mListLoading.clear();
        ListItemHandler listItemHandler = this.mListItemHandler;
        listItemHandler.mListItems.removeObservers(fragmentActivity);
        listItemHandler.mCheckedItemCount.removeObservers(fragmentActivity);
        super.onCleared();
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader.IDataLoaderCallback
    public final void onLoadFinished(LoadResult loadResult) {
        int i;
        boolean z;
        FileListController fileListController = (FileListController) this;
        ListLoading listLoading = fileListController.mListLoading;
        if (listLoading.mCancelLoad) {
            return;
        }
        if (!loadResult.success && fileListController.mListener.mExceptionListener != null) {
            listLoading.turnOffLoading();
            Log.e(
                    fileListController.mTag,
                    "handleExceptionOnLoadFinished() ] to display an error message. mErrorType : "
                            + loadResult.errorType);
            ExceptionListener exceptionListener = fileListController.mListener.mExceptionListener;
            AbsMyFilesException.ErrorType errorType = loadResult.errorType;
            ((ExceptionHandler) exceptionListener).getClass();
            Intrinsics.checkNotNullParameter(errorType, "errorType");
        }
        Bundle bundle = loadResult.groupInfo;
        int size = loadResult.getAppDataList().size();
        if (bundle != null) {
            i = bundle.getInt("childCount", 0);
            z = true;
        } else {
            i = 0;
            z = false;
        }
        String str = this.mTag;
        StringBuilder sb = new StringBuilder("onLoadFinished() ] mSuccess : ");
        sb.append(loadResult.success);
        sb.append(" , existGroup : ");
        sb.append(z);
        sb.append(" , total items count : ");
        if (i != 0) {
            size = i;
        }
        sb.append(size);
        sb.append(" , this : ");
        sb.append(this);
        Log.i(str, sb.toString());
        if (listLoading.mCancelLoad) {
            listLoading.finishLoadingState();
            return;
        }
        if (loadResult.loadExecutionId != listLoading.mLoadExecutionId) {
            Log.d(
                    fileListController.mTag,
                    "loadGroupListFiles ] result.mLoadExecutionId : "
                            + loadResult.loadExecutionId
                            + " mListLoading.getLoadExecutionId() : "
                            + listLoading.mLoadExecutionId);
            return;
        }
        Bundle bundle2 = loadResult.groupInfo;
        ListItemHandler listItemHandler = fileListController.mListItemHandler;
        if (bundle2 == null) {
            Log.i(fileListController.mTag, "loadExpandableListGroupAllFiles - empty data");
            listLoading.setEmptyList(true);
            listLoading.finishLoadingState();
            ((ArrayList) listItemHandler.mAppDataList).clear();
            listItemHandler.mGroupInfo.setValue(new Bundle());
            listItemHandler.setListItems(new ArrayList());
            return;
        }
        List appDataList = loadResult.getAppDataList();
        listItemHandler.mGroupInfo.setValue(loadResult.groupInfo);
        if (!CollectionUtils.isEmpty(appDataList)) {
            ((ArrayList) listItemHandler.mAppDataList).clear();
            ((ArrayList) listItemHandler.mAppDataList).addAll(appDataList);
        }
        SubFileListController subFileListController = (SubFileListController) fileListController;
        ListItemHandler listItemHandler2 = subFileListController.mListItemHandler;
        listItemHandler2.setListItems(listItemHandler2.mAppDataList);
        subFileListController.mListLoading.finishLoadingState();
        Bundle bundle3 = (Bundle) listItemHandler2.mGroupInfo.getValue();
        if (bundle3 != null) {
            subFileListController.setLoadingData(false);
            subFileListController.mListItemTotalSizeData.setValue(
                    Long.valueOf(bundle3.getLong("size", 0L)));
        }
        listLoading.setEmptyList(false);
        if (fileListController.mPageInfo.mExtra.getString("selected_path") != null) {
            fileListController.mPageInfo.mExtra.putString("selected_path", null);
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager.ResultListener
    public final void onResult(MenuExecuteManager.Result result) {
        if (this.mIsCleared) {
            return;
        }
        Log.i(
                this.mTag,
                "onResult() ] : "
                        + MenuType.getMenuName(result.mMenuType)
                        + " , "
                        + result.mIsSuccess
                        + " , "
                        + result.mNeedRefresh
                        + " , false , 0 , this : "
                        + this);
        MenuDirector menuDirector = this.mMenuDirector;
        AbsMenu absMenu = menuDirector.menu;
        if (absMenu != null) {
            absMenu.result(result);
        }
        menuDirector.showOpResult(result, (ExceptionHandler) this.mListener.mExceptionListener);
    }
}
