package com.samsung.android.settings.analyzestorage.presenter.controllers.filelist;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.DataListControllerInterface;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastType;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BroadcastListeners {
    public final BroadcastListeners$$ExternalSyntheticLambda0 mCachedFilesChanged;
    public final WeakReference mContext;
    public final WeakReference mControllerWeakReference;
    public final BroadcastListeners$$ExternalSyntheticLambda0 mLocaleChangedBroadcastReceiver;
    public final BroadcastListeners$$ExternalSyntheticLambda0 mNeedRefreshListener;
    public PageInfo mPageInfo;
    public final BroadcastListeners$$ExternalSyntheticLambda0 mStorageChangeListener;
    public final AnonymousClass1 mStorageEjectListener =
            new BroadcastListener() { // from class:
                                      // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners.1
                @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                    String string = bundle.getString("path");
                    int i = bundle.getInt("domainType", -1);
                    Log.d(
                            "mStorageEjectListener",
                            "onReceive() ] ejectedStorageRootPath : "
                                    + Log.getEncodedMsg(string)
                                    + " , domainType : "
                                    + i);
                    if (TextUtils.isEmpty(string)) {
                        return;
                    }
                    BroadcastType broadcastType2 = BroadcastType.MEDIA_EJECTED;
                    BroadcastListeners broadcastListeners = BroadcastListeners.this;
                    if (broadcastType2 != broadcastType
                            && BroadcastType.MEDIA_REMOVED != broadcastType) {
                        if (BroadcastType.MEDIA_UNMOUNTED == broadcastType) {
                            StorageOperationManager.StorageOperationManagerHolder.INSTANCE
                                    .showToast((Context) broadcastListeners.mContext.get(), i, 1);
                            return;
                        }
                        return;
                    }
                    String path = broadcastListeners.getPageInfo().getPath();
                    int lastIndexOf = string.lastIndexOf(File.separatorChar);
                    if (!path.startsWith(string)) {
                        if (!path.contains(File.separator + string.substring(lastIndexOf + 1))) {
                            return;
                        }
                    }
                    if (-1 == i || StorageVolumeManager.connected(i)) {
                        return;
                    }
                    StorageOperationManager.StorageOperationManagerHolder.INSTANCE.mOperationList
                            .remove(Integer.valueOf(i));
                }
            };
    public final BroadcastListeners$$ExternalSyntheticLambda0 mTimeSetBroadcastReceiver;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0] */
    public BroadcastListeners(
            Context context, DataListControllerInterface dataListControllerInterface) {
        final int i = 0;
        this.mStorageChangeListener =
                new BroadcastListener(this) { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0
                    public final /* synthetic */ BroadcastListeners f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        boolean z;
                        switch (i) {
                            case 0:
                                BroadcastListeners broadcastListeners = this.f$0;
                                PageInfo pageInfo = broadcastListeners.getPageInfo();
                                int i2 = bundle.getInt("domainType", 0);
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                i2,
                                                "mStorageChangeListener - onReceive() ] USB"
                                                    + " mounted. domainType : ",
                                                " , pageType : ");
                                m.append(pageInfo.mPageType.name());
                                Log.d("Listener", m.toString());
                                if (DomainType.isUsb(i2) || DomainType.isSd(i2)) {
                                    if (i2 == StoragePathUtils.getDomainType(pageInfo.getPath())
                                            || RestrictionPolicy.EXTERNAL_STORAGE_PATH_SD.equals(
                                                    pageInfo.getPath())) {
                                        StorageOperationManager.StorageOperationManagerHolder
                                                .INSTANCE
                                                .showToast(
                                                        (Context) broadcastListeners.mContext.get(),
                                                        i2,
                                                        0);
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                final int i3 = 2;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i3) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 2:
                                final int i4 = 1;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i4) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 3:
                                final BroadcastListeners broadcastListeners2 = this.f$0;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners2.mControllerWeakReference
                                                                .get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda8
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        PageType pageType =
                                                                BroadcastListeners.this
                                                                        .getPageInfo()
                                                                        .mPageType;
                                                        pageType.getClass();
                                                        if (pageType
                                                                == PageType
                                                                        .ANALYZE_STORAGE_CACHED_FILES) {
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            ((AbsPageController)
                                                                            dataListControllerInterface2)
                                                                    .mListItemHandler
                                                                            .setAllItemChecked(
                                                                                    false);
                                                        }
                                                    }
                                                });
                                break;
                            default:
                                BroadcastListeners broadcastListeners3 = this.f$0;
                                Optional ofNullable =
                                        Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners3.mControllerWeakReference
                                                                .get());
                                if (bundle != null) {
                                    String string = bundle.getString("path");
                                    final int i5 = 0;
                                    final int i6 = 1;
                                    String str =
                                            (String)
                                                    Optional.ofNullable(
                                                                    (PageInfo)
                                                                            ofNullable
                                                                                    .map(
                                                                                            new Function() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                                                @Override // java.util.function.Function
                                                                                                public
                                                                                                final
                                                                                                Object
                                                                                                        apply(
                                                                                                                Object
                                                                                                                        obj) {
                                                                                                    switch (i5) {
                                                                                                        case 0:
                                                                                                            return ((AbsPageController)
                                                                                                                            ((DataListControllerInterface)
                                                                                                                                    obj))
                                                                                                                    .mPageInfo;
                                                                                                        default:
                                                                                                            return ((PageInfo)
                                                                                                                            obj)
                                                                                                                    .getPath();
                                                                                                    }
                                                                                                }
                                                                                            })
                                                                                    .orElse(null))
                                                            .map(
                                                                    new Function() { // from class:
                                                                        // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            switch (i6) {
                                                                                case 0:
                                                                                    return ((AbsPageController)
                                                                                                    ((DataListControllerInterface)
                                                                                                            obj))
                                                                                            .mPageInfo;
                                                                                default:
                                                                                    return ((PageInfo)
                                                                                                    obj)
                                                                                            .getPath();
                                                                            }
                                                                        }
                                                                    })
                                                            .orElse(ApnSettings.MVNO_NONE);
                                    z = str.equals(string);
                                    Log.i(
                                            "Listener",
                                            "mNeedRefreshListener - onReceive() ] scannedPath : "
                                                    + Log.getEncodedMsg(string)
                                                    + " , currentPath : "
                                                    + Log.getEncodedMsg(str)
                                                    + " , needRefresh : "
                                                    + z
                                                    + " , controller : "
                                                    + broadcastListeners3.mControllerWeakReference
                                                            .get());
                                } else {
                                    z = true;
                                }
                                if (z) {
                                    final int i7 = 0;
                                    ofNullable.ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    DataListControllerInterface
                                                            dataListControllerInterface2 =
                                                                    (DataListControllerInterface)
                                                                            obj;
                                                    switch (i7) {
                                                        case 0:
                                                            dataListControllerInterface2.refresh(
                                                                    true);
                                                            break;
                                                        case 1:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                        default:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                    }
                                                }
                                            });
                                    Log.v("Listener", "NeedRefreshListener - refresh");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mTimeSetBroadcastReceiver =
                new BroadcastListener(this) { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0
                    public final /* synthetic */ BroadcastListeners f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        boolean z;
                        switch (i2) {
                            case 0:
                                BroadcastListeners broadcastListeners = this.f$0;
                                PageInfo pageInfo = broadcastListeners.getPageInfo();
                                int i22 = bundle.getInt("domainType", 0);
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "mStorageChangeListener - onReceive() ] USB"
                                                    + " mounted. domainType : ",
                                                " , pageType : ");
                                m.append(pageInfo.mPageType.name());
                                Log.d("Listener", m.toString());
                                if (DomainType.isUsb(i22) || DomainType.isSd(i22)) {
                                    if (i22 == StoragePathUtils.getDomainType(pageInfo.getPath())
                                            || RestrictionPolicy.EXTERNAL_STORAGE_PATH_SD.equals(
                                                    pageInfo.getPath())) {
                                        StorageOperationManager.StorageOperationManagerHolder
                                                .INSTANCE
                                                .showToast(
                                                        (Context) broadcastListeners.mContext.get(),
                                                        i22,
                                                        0);
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                final int i3 = 2;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i3) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 2:
                                final int i4 = 1;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i4) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 3:
                                final BroadcastListeners broadcastListeners2 = this.f$0;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners2.mControllerWeakReference
                                                                .get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda8
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        PageType pageType =
                                                                BroadcastListeners.this
                                                                        .getPageInfo()
                                                                        .mPageType;
                                                        pageType.getClass();
                                                        if (pageType
                                                                == PageType
                                                                        .ANALYZE_STORAGE_CACHED_FILES) {
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            ((AbsPageController)
                                                                            dataListControllerInterface2)
                                                                    .mListItemHandler
                                                                            .setAllItemChecked(
                                                                                    false);
                                                        }
                                                    }
                                                });
                                break;
                            default:
                                BroadcastListeners broadcastListeners3 = this.f$0;
                                Optional ofNullable =
                                        Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners3.mControllerWeakReference
                                                                .get());
                                if (bundle != null) {
                                    String string = bundle.getString("path");
                                    final int i5 = 0;
                                    final int i6 = 1;
                                    String str =
                                            (String)
                                                    Optional.ofNullable(
                                                                    (PageInfo)
                                                                            ofNullable
                                                                                    .map(
                                                                                            new Function() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                                                @Override // java.util.function.Function
                                                                                                public
                                                                                                final
                                                                                                Object
                                                                                                        apply(
                                                                                                                Object
                                                                                                                        obj) {
                                                                                                    switch (i5) {
                                                                                                        case 0:
                                                                                                            return ((AbsPageController)
                                                                                                                            ((DataListControllerInterface)
                                                                                                                                    obj))
                                                                                                                    .mPageInfo;
                                                                                                        default:
                                                                                                            return ((PageInfo)
                                                                                                                            obj)
                                                                                                                    .getPath();
                                                                                                    }
                                                                                                }
                                                                                            })
                                                                                    .orElse(null))
                                                            .map(
                                                                    new Function() { // from class:
                                                                        // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            switch (i6) {
                                                                                case 0:
                                                                                    return ((AbsPageController)
                                                                                                    ((DataListControllerInterface)
                                                                                                            obj))
                                                                                            .mPageInfo;
                                                                                default:
                                                                                    return ((PageInfo)
                                                                                                    obj)
                                                                                            .getPath();
                                                                            }
                                                                        }
                                                                    })
                                                            .orElse(ApnSettings.MVNO_NONE);
                                    z = str.equals(string);
                                    Log.i(
                                            "Listener",
                                            "mNeedRefreshListener - onReceive() ] scannedPath : "
                                                    + Log.getEncodedMsg(string)
                                                    + " , currentPath : "
                                                    + Log.getEncodedMsg(str)
                                                    + " , needRefresh : "
                                                    + z
                                                    + " , controller : "
                                                    + broadcastListeners3.mControllerWeakReference
                                                            .get());
                                } else {
                                    z = true;
                                }
                                if (z) {
                                    final int i7 = 0;
                                    ofNullable.ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    DataListControllerInterface
                                                            dataListControllerInterface2 =
                                                                    (DataListControllerInterface)
                                                                            obj;
                                                    switch (i7) {
                                                        case 0:
                                                            dataListControllerInterface2.refresh(
                                                                    true);
                                                            break;
                                                        case 1:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                        default:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                    }
                                                }
                                            });
                                    Log.v("Listener", "NeedRefreshListener - refresh");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i3 = 2;
        this.mLocaleChangedBroadcastReceiver =
                new BroadcastListener(this) { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0
                    public final /* synthetic */ BroadcastListeners f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        boolean z;
                        switch (i3) {
                            case 0:
                                BroadcastListeners broadcastListeners = this.f$0;
                                PageInfo pageInfo = broadcastListeners.getPageInfo();
                                int i22 = bundle.getInt("domainType", 0);
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "mStorageChangeListener - onReceive() ] USB"
                                                    + " mounted. domainType : ",
                                                " , pageType : ");
                                m.append(pageInfo.mPageType.name());
                                Log.d("Listener", m.toString());
                                if (DomainType.isUsb(i22) || DomainType.isSd(i22)) {
                                    if (i22 == StoragePathUtils.getDomainType(pageInfo.getPath())
                                            || RestrictionPolicy.EXTERNAL_STORAGE_PATH_SD.equals(
                                                    pageInfo.getPath())) {
                                        StorageOperationManager.StorageOperationManagerHolder
                                                .INSTANCE
                                                .showToast(
                                                        (Context) broadcastListeners.mContext.get(),
                                                        i22,
                                                        0);
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                final int i32 = 2;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i32) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 2:
                                final int i4 = 1;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i4) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 3:
                                final BroadcastListeners broadcastListeners2 = this.f$0;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners2.mControllerWeakReference
                                                                .get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda8
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        PageType pageType =
                                                                BroadcastListeners.this
                                                                        .getPageInfo()
                                                                        .mPageType;
                                                        pageType.getClass();
                                                        if (pageType
                                                                == PageType
                                                                        .ANALYZE_STORAGE_CACHED_FILES) {
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            ((AbsPageController)
                                                                            dataListControllerInterface2)
                                                                    .mListItemHandler
                                                                            .setAllItemChecked(
                                                                                    false);
                                                        }
                                                    }
                                                });
                                break;
                            default:
                                BroadcastListeners broadcastListeners3 = this.f$0;
                                Optional ofNullable =
                                        Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners3.mControllerWeakReference
                                                                .get());
                                if (bundle != null) {
                                    String string = bundle.getString("path");
                                    final int i5 = 0;
                                    final int i6 = 1;
                                    String str =
                                            (String)
                                                    Optional.ofNullable(
                                                                    (PageInfo)
                                                                            ofNullable
                                                                                    .map(
                                                                                            new Function() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                                                @Override // java.util.function.Function
                                                                                                public
                                                                                                final
                                                                                                Object
                                                                                                        apply(
                                                                                                                Object
                                                                                                                        obj) {
                                                                                                    switch (i5) {
                                                                                                        case 0:
                                                                                                            return ((AbsPageController)
                                                                                                                            ((DataListControllerInterface)
                                                                                                                                    obj))
                                                                                                                    .mPageInfo;
                                                                                                        default:
                                                                                                            return ((PageInfo)
                                                                                                                            obj)
                                                                                                                    .getPath();
                                                                                                    }
                                                                                                }
                                                                                            })
                                                                                    .orElse(null))
                                                            .map(
                                                                    new Function() { // from class:
                                                                        // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            switch (i6) {
                                                                                case 0:
                                                                                    return ((AbsPageController)
                                                                                                    ((DataListControllerInterface)
                                                                                                            obj))
                                                                                            .mPageInfo;
                                                                                default:
                                                                                    return ((PageInfo)
                                                                                                    obj)
                                                                                            .getPath();
                                                                            }
                                                                        }
                                                                    })
                                                            .orElse(ApnSettings.MVNO_NONE);
                                    z = str.equals(string);
                                    Log.i(
                                            "Listener",
                                            "mNeedRefreshListener - onReceive() ] scannedPath : "
                                                    + Log.getEncodedMsg(string)
                                                    + " , currentPath : "
                                                    + Log.getEncodedMsg(str)
                                                    + " , needRefresh : "
                                                    + z
                                                    + " , controller : "
                                                    + broadcastListeners3.mControllerWeakReference
                                                            .get());
                                } else {
                                    z = true;
                                }
                                if (z) {
                                    final int i7 = 0;
                                    ofNullable.ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    DataListControllerInterface
                                                            dataListControllerInterface2 =
                                                                    (DataListControllerInterface)
                                                                            obj;
                                                    switch (i7) {
                                                        case 0:
                                                            dataListControllerInterface2.refresh(
                                                                    true);
                                                            break;
                                                        case 1:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                        default:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                    }
                                                }
                                            });
                                    Log.v("Listener", "NeedRefreshListener - refresh");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i4 = 3;
        this.mCachedFilesChanged =
                new BroadcastListener(this) { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0
                    public final /* synthetic */ BroadcastListeners f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        boolean z;
                        switch (i4) {
                            case 0:
                                BroadcastListeners broadcastListeners = this.f$0;
                                PageInfo pageInfo = broadcastListeners.getPageInfo();
                                int i22 = bundle.getInt("domainType", 0);
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "mStorageChangeListener - onReceive() ] USB"
                                                    + " mounted. domainType : ",
                                                " , pageType : ");
                                m.append(pageInfo.mPageType.name());
                                Log.d("Listener", m.toString());
                                if (DomainType.isUsb(i22) || DomainType.isSd(i22)) {
                                    if (i22 == StoragePathUtils.getDomainType(pageInfo.getPath())
                                            || RestrictionPolicy.EXTERNAL_STORAGE_PATH_SD.equals(
                                                    pageInfo.getPath())) {
                                        StorageOperationManager.StorageOperationManagerHolder
                                                .INSTANCE
                                                .showToast(
                                                        (Context) broadcastListeners.mContext.get(),
                                                        i22,
                                                        0);
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                final int i32 = 2;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i32) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 2:
                                final int i42 = 1;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i42) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 3:
                                final BroadcastListeners broadcastListeners2 = this.f$0;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners2.mControllerWeakReference
                                                                .get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda8
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        PageType pageType =
                                                                BroadcastListeners.this
                                                                        .getPageInfo()
                                                                        .mPageType;
                                                        pageType.getClass();
                                                        if (pageType
                                                                == PageType
                                                                        .ANALYZE_STORAGE_CACHED_FILES) {
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            ((AbsPageController)
                                                                            dataListControllerInterface2)
                                                                    .mListItemHandler
                                                                            .setAllItemChecked(
                                                                                    false);
                                                        }
                                                    }
                                                });
                                break;
                            default:
                                BroadcastListeners broadcastListeners3 = this.f$0;
                                Optional ofNullable =
                                        Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners3.mControllerWeakReference
                                                                .get());
                                if (bundle != null) {
                                    String string = bundle.getString("path");
                                    final int i5 = 0;
                                    final int i6 = 1;
                                    String str =
                                            (String)
                                                    Optional.ofNullable(
                                                                    (PageInfo)
                                                                            ofNullable
                                                                                    .map(
                                                                                            new Function() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                                                @Override // java.util.function.Function
                                                                                                public
                                                                                                final
                                                                                                Object
                                                                                                        apply(
                                                                                                                Object
                                                                                                                        obj) {
                                                                                                    switch (i5) {
                                                                                                        case 0:
                                                                                                            return ((AbsPageController)
                                                                                                                            ((DataListControllerInterface)
                                                                                                                                    obj))
                                                                                                                    .mPageInfo;
                                                                                                        default:
                                                                                                            return ((PageInfo)
                                                                                                                            obj)
                                                                                                                    .getPath();
                                                                                                    }
                                                                                                }
                                                                                            })
                                                                                    .orElse(null))
                                                            .map(
                                                                    new Function() { // from class:
                                                                        // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            switch (i6) {
                                                                                case 0:
                                                                                    return ((AbsPageController)
                                                                                                    ((DataListControllerInterface)
                                                                                                            obj))
                                                                                            .mPageInfo;
                                                                                default:
                                                                                    return ((PageInfo)
                                                                                                    obj)
                                                                                            .getPath();
                                                                            }
                                                                        }
                                                                    })
                                                            .orElse(ApnSettings.MVNO_NONE);
                                    z = str.equals(string);
                                    Log.i(
                                            "Listener",
                                            "mNeedRefreshListener - onReceive() ] scannedPath : "
                                                    + Log.getEncodedMsg(string)
                                                    + " , currentPath : "
                                                    + Log.getEncodedMsg(str)
                                                    + " , needRefresh : "
                                                    + z
                                                    + " , controller : "
                                                    + broadcastListeners3.mControllerWeakReference
                                                            .get());
                                } else {
                                    z = true;
                                }
                                if (z) {
                                    final int i7 = 0;
                                    ofNullable.ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    DataListControllerInterface
                                                            dataListControllerInterface2 =
                                                                    (DataListControllerInterface)
                                                                            obj;
                                                    switch (i7) {
                                                        case 0:
                                                            dataListControllerInterface2.refresh(
                                                                    true);
                                                            break;
                                                        case 1:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                        default:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                    }
                                                }
                                            });
                                    Log.v("Listener", "NeedRefreshListener - refresh");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i5 = 4;
        this.mNeedRefreshListener =
                new BroadcastListener(this) { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda0
                    public final /* synthetic */ BroadcastListeners f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        boolean z;
                        switch (i5) {
                            case 0:
                                BroadcastListeners broadcastListeners = this.f$0;
                                PageInfo pageInfo = broadcastListeners.getPageInfo();
                                int i22 = bundle.getInt("domainType", 0);
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "mStorageChangeListener - onReceive() ] USB"
                                                    + " mounted. domainType : ",
                                                " , pageType : ");
                                m.append(pageInfo.mPageType.name());
                                Log.d("Listener", m.toString());
                                if (DomainType.isUsb(i22) || DomainType.isSd(i22)) {
                                    if (i22 == StoragePathUtils.getDomainType(pageInfo.getPath())
                                            || RestrictionPolicy.EXTERNAL_STORAGE_PATH_SD.equals(
                                                    pageInfo.getPath())) {
                                        StorageOperationManager.StorageOperationManagerHolder
                                                .INSTANCE
                                                .showToast(
                                                        (Context) broadcastListeners.mContext.get(),
                                                        i22,
                                                        0);
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                final int i32 = 2;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i32) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 2:
                                final int i42 = 1;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        this.f$0.mControllerWeakReference.get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        switch (i42) {
                                                            case 0:
                                                                dataListControllerInterface2
                                                                        .refresh(true);
                                                                break;
                                                            case 1:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                            default:
                                                                dataListControllerInterface2
                                                                        .refresh$1(true);
                                                                break;
                                                        }
                                                    }
                                                });
                                break;
                            case 3:
                                final BroadcastListeners broadcastListeners2 = this.f$0;
                                Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners2.mControllerWeakReference
                                                                .get())
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda8
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        DataListControllerInterface
                                                                dataListControllerInterface2 =
                                                                        (DataListControllerInterface)
                                                                                obj;
                                                        PageType pageType =
                                                                BroadcastListeners.this
                                                                        .getPageInfo()
                                                                        .mPageType;
                                                        pageType.getClass();
                                                        if (pageType
                                                                == PageType
                                                                        .ANALYZE_STORAGE_CACHED_FILES) {
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            ((AbsPageController)
                                                                            dataListControllerInterface2)
                                                                    .mListItemHandler
                                                                            .setAllItemChecked(
                                                                                    false);
                                                        }
                                                    }
                                                });
                                break;
                            default:
                                BroadcastListeners broadcastListeners3 = this.f$0;
                                Optional ofNullable =
                                        Optional.ofNullable(
                                                (DataListControllerInterface)
                                                        broadcastListeners3.mControllerWeakReference
                                                                .get());
                                if (bundle != null) {
                                    String string = bundle.getString("path");
                                    final int i52 = 0;
                                    final int i6 = 1;
                                    String str =
                                            (String)
                                                    Optional.ofNullable(
                                                                    (PageInfo)
                                                                            ofNullable
                                                                                    .map(
                                                                                            new Function() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                                                @Override // java.util.function.Function
                                                                                                public
                                                                                                final
                                                                                                Object
                                                                                                        apply(
                                                                                                                Object
                                                                                                                        obj) {
                                                                                                    switch (i52) {
                                                                                                        case 0:
                                                                                                            return ((AbsPageController)
                                                                                                                            ((DataListControllerInterface)
                                                                                                                                    obj))
                                                                                                                    .mPageInfo;
                                                                                                        default:
                                                                                                            return ((PageInfo)
                                                                                                                            obj)
                                                                                                                    .getPath();
                                                                                                    }
                                                                                                }
                                                                                            })
                                                                                    .orElse(null))
                                                            .map(
                                                                    new Function() { // from class:
                                                                        // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda5
                                                                        @Override // java.util.function.Function
                                                                        public final Object apply(
                                                                                Object obj) {
                                                                            switch (i6) {
                                                                                case 0:
                                                                                    return ((AbsPageController)
                                                                                                    ((DataListControllerInterface)
                                                                                                            obj))
                                                                                            .mPageInfo;
                                                                                default:
                                                                                    return ((PageInfo)
                                                                                                    obj)
                                                                                            .getPath();
                                                                            }
                                                                        }
                                                                    })
                                                            .orElse(ApnSettings.MVNO_NONE);
                                    z = str.equals(string);
                                    Log.i(
                                            "Listener",
                                            "mNeedRefreshListener - onReceive() ] scannedPath : "
                                                    + Log.getEncodedMsg(string)
                                                    + " , currentPath : "
                                                    + Log.getEncodedMsg(str)
                                                    + " , needRefresh : "
                                                    + z
                                                    + " , controller : "
                                                    + broadcastListeners3.mControllerWeakReference
                                                            .get());
                                } else {
                                    z = true;
                                }
                                if (z) {
                                    final int i7 = 0;
                                    ofNullable.ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.BroadcastListeners$$ExternalSyntheticLambda7
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    DataListControllerInterface
                                                            dataListControllerInterface2 =
                                                                    (DataListControllerInterface)
                                                                            obj;
                                                    switch (i7) {
                                                        case 0:
                                                            dataListControllerInterface2.refresh(
                                                                    true);
                                                            break;
                                                        case 1:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                        default:
                                                            dataListControllerInterface2.refresh$1(
                                                                    true);
                                                            break;
                                                    }
                                                }
                                            });
                                    Log.v("Listener", "NeedRefreshListener - refresh");
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mContext = new WeakReference(context);
        this.mControllerWeakReference = new WeakReference(dataListControllerInterface);
    }

    public final PageInfo getPageInfo() {
        Object obj = (DataListControllerInterface) this.mControllerWeakReference.get();
        if (this.mPageInfo == null && obj != null) {
            this.mPageInfo = ((AbsPageController) obj).mPageInfo;
        }
        return this.mPageInfo;
    }
}
