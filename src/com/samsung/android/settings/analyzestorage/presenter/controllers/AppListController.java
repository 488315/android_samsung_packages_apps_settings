package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import com.samsung.android.settings.analyzestorage.data.database.repository.RepositoryFactory;
import com.samsung.android.settings.analyzestorage.data.database.repository.comparator.ComparatorFactory;
import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.domain.utils.CollectionUtils;
import com.samsung.android.settings.analyzestorage.external.database.repository.RepositoryFactoryImpl;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.constant.MenuType;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler$$ExternalSyntheticLambda0;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListLoading;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ManageStorageListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.controllers.menu.AbsMenu;
import com.samsung.android.settings.analyzestorage.presenter.controllers.menu.MenuDirector;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoaderParams;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.LoadResult;
import com.samsung.android.settings.analyzestorage.presenter.exception.ExceptionListener;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.ConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.preference.SettingStatusPreferenceUtils;
import com.samsung.android.settings.analyzestorage.ui.exception.ExceptionHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListController extends AbsPageController implements DataLoader.IDataLoaderCallback, DataListControllerInterface {
    public final MutableLiveData mAppListLoading;
    public final MutableLiveData mAppListTotalSizeData;
    public long mFreedUpSize;
    public final MutableLiveData mListItems;
    public final ListLoading mListLoading;
    public final MenuDirector mMenuDirector;
    public boolean mNeedLoading;
    public final AnonymousClass1 mPackageBroadcastReceiver;
    public final AnonymousClass2 mRefreshHandler;
    public int mSaAppsType;
    public final Set selectedAppPackageNameList;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.content.BroadcastReceiver, com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$2] */
    public AppListController(Application application, int i, int i2, PageInfo pageInfo) {
        super(application, i);
        this.mNeedLoading = false;
        this.mAppListLoading = new MutableLiveData();
        this.mAppListTotalSizeData = new MutableLiveData();
        this.mListItems = new MutableLiveData();
        this.selectedAppPackageNameList = new HashSet();
        ?? r2 = new BroadcastReceiver() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Uri data = intent.getData();
                if (data == null) {
                    Log.e(AppListController.this.mTag, "package remove receiver -  data is null");
                    return;
                }
                final String encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart();
                if (TextUtils.isEmpty(encodedSchemeSpecificPart)) {
                    Log.e(AppListController.this.mTag, "package remove receiver -  package name is empty");
                } else if (AppListController.this.mListItemHandler.mAppDataList.stream().anyMatch(new Predicate() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return encodedSchemeSpecificPart.equalsIgnoreCase(((CommonAppInfo) ((AppInfo) obj)).getPackageName());
                    }
                })) {
                    AppListController.this.refresh(true);
                }
            }
        };
        this.mPackageBroadcastReceiver = r2;
        this.mRefreshHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    AppListController.this.loadListItem(((Boolean) message.obj).booleanValue());
                }
            }
        };
        this.mSaAppsType = i2;
        this.mListLoading = new ListLoading(this.mListItemHandler);
        if (pageInfo != null) {
            setPageInfo(pageInfo);
        } else {
            this.mMenuDirector = new MenuDirector(this);
        }
        Context context = this.mContext;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(r2, intentFilter);
        this.mTag = "AppListController";
    }

    public final void applySort(int i, boolean z) {
        Log.d(this.mTag, "applySort() ] sortType : " + i + ", isAscending : " + z);
        ListItemHandler listItemHandler = this.mListItemHandler;
        List list = listItemHandler.mAppDataList;
        if (CollectionUtils.isEmpty(list)) {
            Log.d(this.mTag, "updateSort() ] originalListItems is null or empty");
            return;
        }
        ArrayList arrayList = new ArrayList(list);
        arrayList.sort(ComparatorFactory.getAppSortComparator(i, z));
        ((ArrayList) listItemHandler.mAppDataList).clear();
        ((ArrayList) listItemHandler.mAppDataList).addAll(arrayList);
        listItemHandler.setListItems(listItemHandler.mAppDataList);
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController
    public final ListItemHandler createListItemHandler() {
        Log.d(this.mTag, "createListItemHandler() ] ManageStorageListItemHandler ");
        return new ManageStorageListItemHandler();
    }

    public final void loadListItem(boolean z) {
        Log.d(this.mTag, "loadListItem, mSaAppsType : " + this.mSaAppsType);
        if (this.mSaAppsType == 3) {
            ThreadExecutor.runOnWorkThread(new AppListController$$ExternalSyntheticLambda0(this));
            return;
        }
        ListLoading listLoading = this.mListLoading;
        if (listLoading != null) {
            setLoadingData(true);
            PageInfo pageInfo = this.mPageInfo;
            Intrinsics.checkNotNullParameter(pageInfo, "pageInfo");
            DataLoaderParams dataLoaderParams = new DataLoaderParams();
            dataLoaderParams.pageInfo = pageInfo;
            PageType pageType = this.mPageInfo.mPageType;
            RepositoryFactory.getGenerator().getClass();
            listLoading.loadListItem(RepositoryFactoryImpl.getAppRepository(pageType), dataLoaderParams, this);
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public final void onCleared() {
        Context context = ActivityInfoStore.context;
        FragmentActivity fragmentActivity = ActivityInfoStore.Companion.getInstance(this.mInstanceId).getFragmentActivity();
        MenuExecuteManager.sCallbackListener.remove(this);
        this.mListLoading.clear();
        ListItemHandler listItemHandler = this.mListItemHandler;
        listItemHandler.mListItems.removeObservers(fragmentActivity);
        listItemHandler.mCheckedItemCount.removeObservers(fragmentActivity);
        removeMessages(0);
        this.mContext.unregisterReceiver(this.mPackageBroadcastReceiver);
        super.onCleared();
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader.IDataLoaderCallback
    public final void onLoadFinished(LoadResult loadResult) {
        int i;
        boolean z;
        ListLoading listLoading = this.mListLoading;
        if (listLoading.mCancelLoad) {
            return;
        }
        if (!loadResult.success && this.mListener.mExceptionListener != null) {
            listLoading.turnOffLoading();
            Log.e(this.mTag, "handleExceptionOnLoadFinished() ] to display an error message. mErrorType : " + loadResult.errorType);
            ExceptionListener exceptionListener = this.mListener.mExceptionListener;
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
        boolean z2 = listLoading.mCancelLoad;
        ListItemHandler listItemHandler = this.mListItemHandler;
        if (z2) {
            listLoading.finishLoadingState();
        } else if (loadResult.loadExecutionId != listLoading.mLoadExecutionId) {
            Log.d(this.mTag, "checkConditionAppDataLoading ] result.mLoadExecutionId : " + loadResult.loadExecutionId + " mListLoading.getLoadExecutionId() : " + listLoading.mLoadExecutionId);
        } else if (loadResult.groupInfo == null) {
            Log.i(this.mTag, "loadAppDataList() ] Empty data");
            listLoading.setEmptyList(true);
            listLoading.finishLoadingState();
            ((ArrayList) listItemHandler.mAppDataList).clear();
            listItemHandler.mGroupInfo.setValue(new Bundle());
            listItemHandler.setListItems(new ArrayList());
        } else {
            List appDataList = loadResult.getAppDataList();
            listItemHandler.mGroupInfo.setValue(loadResult.groupInfo);
            if (!CollectionUtils.isEmpty(appDataList)) {
                ((ArrayList) listItemHandler.mAppDataList).clear();
                ((ArrayList) listItemHandler.mAppDataList).addAll(appDataList);
            }
            listItemHandler.setListItems(listItemHandler.mAppDataList);
            listLoading.finishLoadingState();
            listLoading.setEmptyList(false);
            if (this.mPageInfo.mExtra.getString("selected_path") != null) {
                this.mPageInfo.mExtra.putString("selected_path", null);
            }
        }
        List list = listItemHandler.mAppDataList;
        long j = 0;
        for (AppInfo appInfo : CollectionUtils.getEmptyListIfNull(list)) {
            if (appInfo != null) {
                j = ((CommonAppInfo) appInfo).getSize() + j;
            }
        }
        int i2 = this.mSaAppsType;
        if (i2 == 4) {
            String str2 = (j / 1000000) + "MB";
            int size2 = CollectionUtils.getEmptyListIfNull(list).size();
            Log.d(this.mTag, "setListItemSize ] unused app size : " + str2 + ", count : " + size2);
            SettingStatusPreferenceUtils.setInternalUnusedApplicationInfo(this.mContext, size2, str2);
        } else if (i2 == 5) {
            int size3 = CollectionUtils.getEmptyListIfNull(list).size();
            String str3 = this.mTag;
            Log.d(str3, "setListItemSize ] app cache size : " + ((j / 1000000) + "MB") + ", count : " + size3);
        }
        setLoadingData(false);
        this.mAppListTotalSizeData.setValue(Long.valueOf(j));
        if (CollectionUtils.isEmpty(loadResult.getAppDataList())) {
            listItemHandler.resetCheckedItemInfo();
            return;
        }
        if (!CollectionUtils.isEmpty(listItemHandler.mCheckedItemList)) {
            List list2 = (List) listItemHandler.mCheckedItemList.stream().filter(new ListItemHandler$$ExternalSyntheticLambda0(1, new HashSet(listItemHandler.mAppDataList))).collect(Collectors.toList());
            ((ArrayList) listItemHandler.mCheckedItemList).clear();
            ((ArrayList) listItemHandler.mCheckedItemList).addAll(list2);
        }
        listItemHandler.mCheckedItemCount.setValue(Integer.valueOf(((ArrayList) listItemHandler.mCheckedItemList).size()));
        listItemHandler.updateCheckedAll(((HashSet) listItemHandler.mCheckableItemList).size());
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager.ResultListener
    public final void onResult(MenuExecuteManager.Result result) {
        if (this.mIsCleared) {
            return;
        }
        int i = result.mMenuType;
        Log.i(this.mTag, "onResult() ] " + MenuType.getMenuName(i) + " , " + result.mIsSuccess + " , " + result.mNeedRefresh + " , false , 0 , this : " + this);
        MenuDirector menuDirector = this.mMenuDirector;
        AbsMenu absMenu = menuDirector.menu;
        if (absMenu != null) {
            absMenu.result(result);
        }
        menuDirector.showOpResult(result, (ExceptionHandler) this.mListener.mExceptionListener);
        Bundle bundle = result.mExtra;
        if (i == 340) {
            long j = bundle.getLong("unused_app_freed_up_size", 0L);
            if (j > 0) {
                this.mFreedUpSize += j;
            }
            String str = this.mTag;
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m(j, "handleFreedUpSize() ] FreedUpSize By Uninstalling Unused Apps : ", " , Cumulative Size : ");
            m.append(this.mFreedUpSize);
            Log.d(str, m.toString());
            return;
        }
        if (i != 350) {
            Log.d(this.mTag, "handleFreedUpSize() ] " + MenuType.getMenuName(i) + " does not support FreedUpSize.");
            return;
        }
        long j2 = bundle.getLong("app_cache_freed_up_size", 0L);
        if (j2 > 0) {
            this.mFreedUpSize += j2;
        }
        String str2 = this.mTag;
        StringBuilder m2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m(j2, "handleFreedUpSize() ] FreedUpSize By Clearing App Cache : ", " , Cumulative Size : ");
        m2.append(this.mFreedUpSize);
        Log.d(str2, m2.toString());
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.PageControllerInterface
    public final void refresh(boolean z) {
        refresh$1(z);
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.DataListControllerInterface
    public final void refresh$1(boolean z) {
        AnonymousClass2 anonymousClass2 = this.mRefreshHandler;
        if (anonymousClass2.hasMessages(0)) {
            anonymousClass2.removeMessages(0);
        }
        anonymousClass2.sendMessageDelayed(anonymousClass2.obtainMessage(0, Boolean.valueOf(z)), 200);
    }

    public final void setLoadingData(boolean z) {
        Log.d(this.mTag, "setLoadingData, saType : " + this.mSaAppsType + ", value : " + z);
        this.mNeedLoading = z;
        this.mAppListLoading.setValue(Boolean.valueOf(z));
    }

    public final void setPageInfo(PageInfo pageInfo) {
        Log.d(this.mTag, "setPageInfo() ] mSaAppsType : " + this.mSaAppsType + " , pageInfo : " + pageInfo);
        int i = this.mSaAppsType;
        if (i == -1) {
            this.mPageInfo = pageInfo;
            return;
        }
        PageInfo pageInfo2 = (PageInfo) ConvertManager.sSaTypeToPageInfo.get(i, new PageInfo(PageType.ANALYZE_STORAGE_HOME));
        this.mPageInfo = pageInfo2;
        pageInfo2.mNavigationMode = pageInfo.mNavigationMode;
        pageInfo2.mActivityType = pageInfo.mActivityType;
        pageInfo2.putExtra(this.mSaAppsType, "asType");
        this.mPageInfo.putExtra(this.mInstanceId, "instanceId");
        this.mPageInfo.mExtra.putBoolean("manageStorageSubPageInitialEntry", true);
    }
}
