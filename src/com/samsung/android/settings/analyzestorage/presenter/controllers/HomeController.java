package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.external.injection.ControllerFactory;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.FileListObserver;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastHandler$1;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.StorageBroadcastHandler;
import com.samsung.android.settings.analyzestorage.presenter.observer.IContentObserver;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ReverseOrderComparator;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HomeController extends AbsPageController implements IContentObserver {
    public final HashMap appListController;
    public final Application application;
    public final FileListObserver listObserver;
    public final MutableLiveData mThirdBigSpace;
    public OverViewController overViewController;
    public final ViewModelStoreOwner owner;
    public RecommendCardController recommendCardController;
    public final HashMap subFileListController;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeController(ViewModelStoreOwner owner, Application application, int i) {
        super(application, i);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(application, "application");
        this.owner = owner;
        this.application = application;
        this.subFileListController = new HashMap();
        this.appListController = new HashMap();
        this.mThirdBigSpace = new MutableLiveData();
        new ArrayList();
        this.listObserver = new FileListObserver(this.mContext, this);
    }

    public static final void access$updateThirdBigSize(HomeController homeController) {
        MutableLiveData mutableLiveData;
        ArrayList arrayList = new ArrayList();
        Iterator it = homeController.subFileListController.entrySet().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            mutableLiveData = homeController.mThirdBigSpace;
            if (!hasNext) {
                Iterator it2 = homeController.appListController.entrySet().iterator();
                while (it2.hasNext()) {
                    AppListController appListController =
                            (AppListController) ((Map.Entry) it2.next()).getValue();
                    if (Intrinsics.areEqual(
                            appListController.mAppListLoading.getValue(), Boolean.FALSE)) {
                        MutableLiveData mutableLiveData2 = appListController.mAppListTotalSizeData;
                        if ((mutableLiveData2 != null ? (Long) mutableLiveData2.getValue() : null)
                                != null) {
                            Long l = (Long) mutableLiveData2.getValue();
                            if (l != null) {
                                arrayList.add(l);
                            }
                        }
                    }
                    mutableLiveData.setValue(1125899906842624L);
                    return;
                }
                CollectionsKt__MutableCollectionsJVMKt.sortWith(
                        arrayList, ReverseOrderComparator.INSTANCE);
                mutableLiveData.setValue(arrayList.get(2));
                return;
            }
            SubFileListController subFileListController =
                    (SubFileListController) ((Map.Entry) it.next()).getValue();
            if (!Intrinsics.areEqual(subFileListController.mLoading.getValue(), Boolean.FALSE)) {
                break;
            }
            MutableLiveData mutableLiveData3 = subFileListController.mListItemTotalSizeData;
            if ((mutableLiveData3 != null ? (Long) mutableLiveData3.getValue() : null) == null) {
                break;
            }
            Long l2 = (Long) mutableLiveData3.getValue();
            if (l2 != null) {
                arrayList.add(l2);
            }
        }
        mutableLiveData.setValue(1125899906842624L);
    }

    public final AbsPageController getController(int i) {
        ViewModelStoreOwner owner = this.owner;
        int i2 = this.mInstanceId;
        if (i <= 1) {
            if (this.subFileListController.get(Integer.valueOf(i)) == null) {
                Context context = this.mContext;
                Intrinsics.checkNotNull(
                        context, "null cannot be cast to non-null type android.app.Application");
                PageInfo pageInfo = this.mPageInfo;
                ControllerFactory controllerFactory =
                        new ControllerFactory((Application) context, i2, i, pageInfo);
                Log.d(
                        "HomeController",
                        "getController() ] Before SubFileListController - saType : "
                                + i
                                + ", pageInfo : "
                                + pageInfo
                                + ", pageType : "
                                + (pageInfo != null ? pageInfo.mPageType : null)
                                + " , factory : "
                                + controllerFactory);
                Intrinsics.checkNotNullParameter(owner, "owner");
                ViewModelStore store = owner.getViewModelStore();
                CreationExtras defaultCreationExtras =
                        owner instanceof HasDefaultViewModelProviderFactory
                                ? ((HasDefaultViewModelProviderFactory) owner)
                                        .getDefaultViewModelCreationExtras()
                                : CreationExtras.Empty.INSTANCE;
                Intrinsics.checkNotNullParameter(store, "store");
                Intrinsics.checkNotNullParameter(defaultCreationExtras, "defaultCreationExtras");
                ViewModelProviderImpl viewModelProviderImpl =
                        new ViewModelProviderImpl(store, controllerFactory, defaultCreationExtras);
                String key = String.valueOf(i);
                Intrinsics.checkNotNullParameter(key, "key");
                SubFileListController subFileListController =
                        (SubFileListController)
                                viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(
                                        JvmClassMappingKt.getKotlinClass(
                                                SubFileListController.class),
                                        key);
                subFileListController.mSaType = i;
                subFileListController.setPageInfo(this.mPageInfo);
                int i3 = subFileListController.mSaType;
                PageInfo pageInfo2 = this.mPageInfo;
                StringBuilder m =
                        RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                "getController() ] After SubFileListController - saType : ",
                                " , controller.mSaType : ",
                                i,
                                i3,
                                ", pageInfo : ");
                m.append(pageInfo2);
                m.append(", factory : ");
                m.append(controllerFactory);
                Log.d("HomeController", m.toString());
                this.subFileListController.put(Integer.valueOf(i), subFileListController);
            }
            Object obj = this.subFileListController.get(Integer.valueOf(i));
            Intrinsics.checkNotNull(
                    obj,
                    "null cannot be cast to non-null type"
                        + " com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController");
            return (SubFileListController) obj;
        }
        if (this.appListController.get(Integer.valueOf(i)) == null) {
            Context context2 = this.mContext;
            Intrinsics.checkNotNull(
                    context2, "null cannot be cast to non-null type android.app.Application");
            PageInfo pageInfo3 = this.mPageInfo;
            ControllerFactory controllerFactory2 =
                    new ControllerFactory((Application) context2, i2, i, pageInfo3);
            Log.d(
                    "HomeController",
                    "getController() ] Before AppListController - saType : "
                            + i
                            + ", pageInfo : "
                            + pageInfo3
                            + ", pageType : "
                            + (pageInfo3 != null ? pageInfo3.mPageType : null)
                            + ", factory : "
                            + controllerFactory2);
            Intrinsics.checkNotNullParameter(owner, "owner");
            ViewModelStore store2 = owner.getViewModelStore();
            CreationExtras defaultCreationExtras2 =
                    owner instanceof HasDefaultViewModelProviderFactory
                            ? ((HasDefaultViewModelProviderFactory) owner)
                                    .getDefaultViewModelCreationExtras()
                            : CreationExtras.Empty.INSTANCE;
            Intrinsics.checkNotNullParameter(store2, "store");
            Intrinsics.checkNotNullParameter(defaultCreationExtras2, "defaultCreationExtras");
            ViewModelProviderImpl viewModelProviderImpl2 =
                    new ViewModelProviderImpl(store2, controllerFactory2, defaultCreationExtras2);
            String key2 = String.valueOf(i);
            Intrinsics.checkNotNullParameter(key2, "key");
            AppListController appListController =
                    (AppListController)
                            viewModelProviderImpl2.getViewModel$lifecycle_viewmodel_release(
                                    JvmClassMappingKt.getKotlinClass(AppListController.class),
                                    key2);
            appListController.mSaAppsType = i;
            appListController.setPageInfo(this.mPageInfo);
            int i4 = appListController.mSaAppsType;
            PageInfo pageInfo4 = this.mPageInfo;
            StringBuilder m2 =
                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                            "getController() ] After AppListController - saType : ",
                            ", controller.mSaType : ",
                            i,
                            i4,
                            ", pageInfo : ");
            m2.append(pageInfo4);
            m2.append(", factory : ");
            m2.append(controllerFactory2);
            Log.d("HomeController", m2.toString());
            this.appListController.put(Integer.valueOf(i), appListController);
        }
        Object obj2 = this.appListController.get(Integer.valueOf(i));
        Intrinsics.checkNotNull(
                obj2,
                "null cannot be cast to non-null type"
                    + " com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController");
        return (AppListController) obj2;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public final void onCleared() {
        OverViewController overViewController = this.overViewController;
        if (overViewController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overViewController");
            throw null;
        }
        overViewController.onCleared();
        RecommendCardController recommendCardController = this.recommendCardController;
        if (recommendCardController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recommendCardController");
            throw null;
        }
        recommendCardController.onCleared();
        this.appListController.clear();
        this.subFileListController.clear();
        Context applicationContext = this.application.getApplicationContext();
        BroadcastReceiveCenter broadcastReceiveCenter =
                BroadcastReceiveCenter.InstanceHolder.INSTANCE;
        StorageBroadcastHandler storageBroadcastHandler =
                broadcastReceiveCenter.mStorageBroadcastHandler;
        try {
            if (storageBroadcastHandler.mBroadcastReceiver == null) {
                storageBroadcastHandler.mBroadcastReceiver =
                        new BroadcastHandler$1(storageBroadcastHandler);
            }
            applicationContext.unregisterReceiver(storageBroadcastHandler.mBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.e("BroadcastReceiveCenter", "unregisterReceiver() ] IllegalArgumentException:" + e);
        }
        storageBroadcastHandler.mNotifyBroadcastListener = null;
        ((EnumMap) broadcastReceiveCenter.mDynamicListenerMap).clear();
        ((EnumMap) broadcastReceiveCenter.mListenerMap).clear();
        this.listObserver.removeObserver();
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.observer.IContentObserver
    public final void onContentChanged() {
        Log.d(this.mTag, "onContentChanged");
        OverViewController overViewController = this.overViewController;
        if (overViewController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overViewController");
            throw null;
        }
        overViewController.updateUsageInfo(true, false);
        Iterator it = this.appListController.entrySet().iterator();
        while (it.hasNext()) {
            AppListController appListController =
                    (AppListController) ((Map.Entry) it.next()).getValue();
            appListController.getClass();
            ThreadExecutor.runOnWorkThread(
                    new AppListController$$ExternalSyntheticLambda0(appListController));
        }
        Handler handler = new Handler();
        for (final Map.Entry entry : this.subFileListController.entrySet()) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.analyzestorage.presenter.controllers.HomeController$onContentChanged$2$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((SubFileListController) entry.getValue()).refresh(true);
                        }
                    });
        }
    }
}
