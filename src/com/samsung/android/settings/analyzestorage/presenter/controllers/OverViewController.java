package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.util.SparseLongArray;

import androidx.lifecycle.MutableLiveData;

import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.data.model.OverViewItemInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.presenter.account.QuotaInfo;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;
import com.samsung.android.settings.analyzestorage.presenter.controllers.menu.MenuDirector;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastReceiveCenter$$ExternalSyntheticLambda2;
import com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastType;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.utils.CategoryType;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class OverViewController extends AbsPageController {
    public static final Uri CLOUD_ACCOUNT_URI =
            Uri.parse("content://myfiles").buildUpon().appendPath("cloud_account_info").build();
    public final Map UiItemTypeMap;
    public final Handler delayHandler;
    public final OverViewController$$ExternalSyntheticLambda0 mCachedFilesChangeListener;
    public final AnonymousClass1 mCloudContentObserver;
    public final MutableLiveData mExternalSdUsageData;
    public final MutableLiveData mGoogleCloudUsageData;
    public final Handler mHandler;
    public final MutableLiveData mInternalAppCloneUsageData;
    public final MutableLiveData mInternalKnoxUsageData;
    public final MutableLiveData mInternalSecureFolderUsageData;
    public final MutableLiveData mInternalUsageData;
    public final Map mIsLoading;
    public final MutableLiveData mIsLoadingData;
    public final MenuDirector mMenuDirector;
    public final MutableLiveData mOneDriveUsageData;
    public final MutableLiveData mQuotaStorageList;
    public final OverViewController$$ExternalSyntheticLambda0 mStorageChangeListener;
    public final MutableLiveData mStorageUsedSize;
    public final ArrayList mSupportedStorageList;
    public final OverViewController$$ExternalSyntheticLambda0 mTrashChangeListener;

    /* JADX WARN: Type inference failed for: r1v14, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$1] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda0] */
    public OverViewController(Context context, int i) {
        super(context, i);
        this.mStorageUsedSize = new MutableLiveData();
        this.mInternalUsageData = new MutableLiveData();
        this.mExternalSdUsageData = new MutableLiveData();
        this.mInternalAppCloneUsageData = new MutableLiveData();
        this.mInternalKnoxUsageData = new MutableLiveData();
        this.mInternalSecureFolderUsageData = new MutableLiveData();
        this.mGoogleCloudUsageData = new MutableLiveData();
        this.mOneDriveUsageData = new MutableLiveData();
        this.mIsLoadingData = new MutableLiveData();
        this.mIsLoading = new HashMap();
        this.UiItemTypeMap = new HashMap();
        this.mSupportedStorageList = new ArrayList();
        this.mQuotaStorageList = new MutableLiveData();
        final int i2 = 0;
        this.mStorageChangeListener =
                new BroadcastListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda0
                    public final /* synthetic */ OverViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        int i3 = i2;
                        OverViewController overViewController = this.f$0;
                        overViewController.getClass();
                        switch (i3) {
                            case 0:
                                int i4 = bundle.getInt("domainType", -1);
                                if (DomainType.isUsb(i4) || DomainType.isSd(i4)) {
                                    StorageOperationManager storageOperationManager =
                                            StorageOperationManager.StorageOperationManagerHolder
                                                    .INSTANCE;
                                    if (BroadcastType.MEDIA_MOUNTED == broadcastType) {
                                        storageOperationManager.showToast(
                                                overViewController.mContext, i4, 0);
                                    } else if (BroadcastType.MEDIA_EJECTED == broadcastType) {
                                        if (!StorageVolumeManager.connected(i4)) {
                                            storageOperationManager.mOperationList.remove(
                                                    Integer.valueOf(i4));
                                        }
                                    } else if (BroadcastType.MEDIA_UNMOUNTED == broadcastType) {
                                        storageOperationManager.showToast(
                                                overViewController.mContext, i4, 1);
                                    }
                                }
                                overViewController.checkSupportedStorageList();
                                return;
                            case 1:
                                throw null;
                            default:
                                throw null;
                        }
                    }
                };
        this.mCloudContentObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        Log.d(OverViewController.this.mTag, "CloudContentObserver onChange");
                        OverViewController.this.checkSupportedStorageList();
                    }
                };
        final int i3 = 1;
        this.mTrashChangeListener =
                new BroadcastListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda0
                    public final /* synthetic */ OverViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        int i32 = i3;
                        OverViewController overViewController = this.f$0;
                        overViewController.getClass();
                        switch (i32) {
                            case 0:
                                int i4 = bundle.getInt("domainType", -1);
                                if (DomainType.isUsb(i4) || DomainType.isSd(i4)) {
                                    StorageOperationManager storageOperationManager =
                                            StorageOperationManager.StorageOperationManagerHolder
                                                    .INSTANCE;
                                    if (BroadcastType.MEDIA_MOUNTED == broadcastType) {
                                        storageOperationManager.showToast(
                                                overViewController.mContext, i4, 0);
                                    } else if (BroadcastType.MEDIA_EJECTED == broadcastType) {
                                        if (!StorageVolumeManager.connected(i4)) {
                                            storageOperationManager.mOperationList.remove(
                                                    Integer.valueOf(i4));
                                        }
                                    } else if (BroadcastType.MEDIA_UNMOUNTED == broadcastType) {
                                        storageOperationManager.showToast(
                                                overViewController.mContext, i4, 1);
                                    }
                                }
                                overViewController.checkSupportedStorageList();
                                return;
                            case 1:
                                throw null;
                            default:
                                throw null;
                        }
                    }
                };
        final int i4 = 2;
        this.mCachedFilesChangeListener =
                new BroadcastListener(
                        this) { // from class:
                                // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda0
                    public final /* synthetic */ OverViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.samsung.android.settings.analyzestorage.presenter.managers.broadcast.BroadcastListener
                    public final void onReceive(BroadcastType broadcastType, Bundle bundle) {
                        int i32 = i4;
                        OverViewController overViewController = this.f$0;
                        overViewController.getClass();
                        switch (i32) {
                            case 0:
                                int i42 = bundle.getInt("domainType", -1);
                                if (DomainType.isUsb(i42) || DomainType.isSd(i42)) {
                                    StorageOperationManager storageOperationManager =
                                            StorageOperationManager.StorageOperationManagerHolder
                                                    .INSTANCE;
                                    if (BroadcastType.MEDIA_MOUNTED == broadcastType) {
                                        storageOperationManager.showToast(
                                                overViewController.mContext, i42, 0);
                                    } else if (BroadcastType.MEDIA_EJECTED == broadcastType) {
                                        if (!StorageVolumeManager.connected(i42)) {
                                            storageOperationManager.mOperationList.remove(
                                                    Integer.valueOf(i42));
                                        }
                                    } else if (BroadcastType.MEDIA_UNMOUNTED == broadcastType) {
                                        storageOperationManager.showToast(
                                                overViewController.mContext, i42, 1);
                                    }
                                }
                                overViewController.checkSupportedStorageList();
                                return;
                            case 1:
                                throw null;
                            default:
                                throw null;
                        }
                    }
                };
        this.delayHandler = new Handler(Looper.getMainLooper());
        this.mHandler =
                new Handler(
                        new Handler
                                .Callback() { // from class:
                                              // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda3
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                OverViewController overViewController = OverViewController.this;
                                Log.v(
                                        overViewController.mTag,
                                        "handler update info " + message.what);
                                int i5 = message.what;
                                if (i5 == 0) {
                                    overViewController.updateUsageData(
                                            message.arg1, (ArrayList) message.obj);
                                } else if (i5 == 1) {
                                    overViewController.mStorageUsedSize.setValue(
                                            (SparseLongArray) message.obj);
                                }
                                return true;
                            }
                        });
        this.mTag = "OverViewController";
        this.mMenuDirector = new MenuDirector(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x014a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0117 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkSupportedStorageList() {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController.checkSupportedStorageList():void");
    }

    public final ArrayList createCloudDetail(Cursor cursor) {
        long j = cursor.getLong(2);
        long j2 = cursor.getLong(3);
        long[] jArr = {cursor.getLong(3), cursor.getLong(4)};
        QuotaInfo quotaInfo = new QuotaInfo();
        quotaInfo.totalSize = j;
        quotaInfo.usedSize = j2;
        quotaInfo.additionalUsageInfo = jArr;
        CloudConstants$CloudType cloudType = CloudConstants$CloudType.valueOf(cursor.getString(0));
        if (SettingsCloudAccountManager.sInstance == null) {
            synchronized (SettingsCloudAccountManager.class) {
                if (SettingsCloudAccountManager.sInstance == null) {
                    SettingsCloudAccountManager settingsCloudAccountManager =
                            new SettingsCloudAccountManager();
                    settingsCloudAccountManager.accountInfoMap =
                            new EnumMap(CloudConstants$CloudType.class);
                    SettingsCloudAccountManager.sInstance = settingsCloudAccountManager;
                }
            }
        }
        SettingsCloudAccountManager settingsCloudAccountManager2 =
                SettingsCloudAccountManager.sInstance;
        String string = cursor.getString(1);
        Intrinsics.checkNotNullParameter(cloudType, "cloudType");
        SettingsCloudAccountManager.AccountInfo accountInfo =
                new SettingsCloudAccountManager.AccountInfo();
        accountInfo.cloudType = cloudType;
        accountInfo.accountId = string;
        accountInfo.quotaInfo = quotaInfo;
        settingsCloudAccountManager2.getClass();
        settingsCloudAccountManager2.accountInfoMap.put(cloudType, accountInfo);
        return formatCloudDetailData(jArr, cloudType, j);
    }

    public final ArrayList formatCloudDetailData(
            long[] jArr, CloudConstants$CloudType cloudConstants$CloudType, long j) {
        ArrayList arrayList = new ArrayList();
        if (jArr != null && jArr.length != 0) {
            ArrayList arrayList2 = new ArrayList();
            Bundle bundle = null;
            try {
                bundle =
                        this.mContext
                                .getContentResolver()
                                .call(
                                        "myfiles",
                                        "CLOUD_QUOTA_DETAIL",
                                        cloudConstants$CloudType.name(),
                                        (Bundle) null);
            } catch (RuntimeException e) {
                Log.w("ContentResolverWrapper", e.getMessage());
            }
            if (bundle != null) {
                long[] longArray = bundle.getLongArray(cloudConstants$CloudType.name());
                int i = 0;
                for (CategoryType categoryType : CategoryType.values()) {
                    if (categoryType != CategoryType.NONE
                            && categoryType != CategoryType.DOWNLOADS) {
                        AnalyzeStorageConstants$UiItemType overViewItem =
                                categoryType.getOverViewItem();
                        Log.d(
                                this.mTag,
                                "getUsageDetailDataByCloudCategory() ] - usage detail data : ("
                                        + overViewItem.name()
                                        + ", "
                                        + longArray[i]
                                        + ")");
                        arrayList2.add(
                                new OverViewItemInfo(overViewItem, Long.valueOf(longArray[i])));
                        i++;
                    }
                }
            }
            long sum =
                    jArr[0]
                            - arrayList2.stream()
                                    .map(new OverViewController$$ExternalSyntheticLambda14())
                                    .mapToLong(new OverViewController$$ExternalSyntheticLambda15())
                                    .sum();
            arrayList.addAll(arrayList2);
            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType =
                    AnalyzeStorageConstants$UiItemType.OTHER_FILES;
            if (sum < 0) {
                sum = Long.MIN_VALUE;
            }
            arrayList.add(
                    new OverViewItemInfo(analyzeStorageConstants$UiItemType, Long.valueOf(sum)));
            if (cloudConstants$CloudType == CloudConstants$CloudType.GOOGLE_DRIVE) {
                arrayList.add(
                        new OverViewItemInfo(
                                AnalyzeStorageConstants$UiItemType.GOOGLE_APPS,
                                Long.valueOf(jArr[1])));
            }
            arrayList.add(
                    new OverViewItemInfo(
                            AnalyzeStorageConstants$UiItemType.TOTAL, Long.valueOf(j)));
        }
        return arrayList;
    }

    public final void getCloudInfoByProviderQueryWithContentObserver() {
        if (SettingsCloudAccountManager.sInstance == null) {
            synchronized (SettingsCloudAccountManager.class) {
                if (SettingsCloudAccountManager.sInstance == null) {
                    SettingsCloudAccountManager settingsCloudAccountManager =
                            new SettingsCloudAccountManager();
                    settingsCloudAccountManager.accountInfoMap =
                            new EnumMap(CloudConstants$CloudType.class);
                    SettingsCloudAccountManager.sInstance = settingsCloudAccountManager;
                }
            }
        }
        SettingsCloudAccountManager settingsCloudAccountManager2 =
                SettingsCloudAccountManager.sInstance;
        settingsCloudAccountManager2.getClass();
        settingsCloudAccountManager2.accountInfoMap = new EnumMap(CloudConstants$CloudType.class);
        Cursor query =
                this.mContext.getContentResolver().query(CLOUD_ACCOUNT_URI, null, null, null);
        while (query != null) {
            try {
                if (!query.moveToNext()) {
                    break;
                }
                Log.d(
                        this.mTag,
                        "getCloudInfoByProviderQueryWithContentObserver() ] - "
                                + query.getString(0)
                                + " "
                                + Log.getEncodedMsg(query.getString(1))
                                + " "
                                + query.getLong(2)
                                + " "
                                + query.getLong(3)
                                + " "
                                + query.getLong(4)
                                + " "
                                + CloudConstants$CloudType.valueOf(query.getString(0)));
                updateUsageData(
                        CloudConstants$CloudType.valueOf(query.getString(0)).getValue(),
                        createCloudDetail(query));
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
    }

    public final MutableLiveData getUsageDetailData(int i) {
        Log.d(this.mTag, "getUsageDetailData() - domainType : " + i);
        if (i == 0) {
            return this.mInternalUsageData;
        }
        if (i == 1) {
            return this.mExternalSdUsageData;
        }
        if (i == 2) {
            return this.mInternalAppCloneUsageData;
        }
        if (i == 3) {
            return this.mInternalKnoxUsageData;
        }
        if (i == 4) {
            return this.mInternalSecureFolderUsageData;
        }
        if (i == 101) {
            return this.mGoogleCloudUsageData;
        }
        if (i != 102) {
            return null;
        }
        return this.mOneDriveUsageData;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public final void onCleared() {
        SparseArray sparseArray = BroadcastReceiveCenter.sInstanceMap;
        BroadcastReceiveCenter broadcastReceiveCenter =
                BroadcastReceiveCenter.InstanceHolder.INSTANCE;
        BroadcastType broadcastType = BroadcastType.MEDIA_MOUNTED;
        OverViewController$$ExternalSyntheticLambda0 overViewController$$ExternalSyntheticLambda0 =
                this.mStorageChangeListener;
        broadcastReceiveCenter.removeDynamicListener(
                broadcastType, overViewController$$ExternalSyntheticLambda0);
        broadcastReceiveCenter.removeDynamicListener(
                BroadcastType.MEDIA_UNMOUNTED, overViewController$$ExternalSyntheticLambda0);
        broadcastReceiveCenter.removeDynamicListener(
                BroadcastType.MEDIA_EJECTED, overViewController$$ExternalSyntheticLambda0);
        broadcastReceiveCenter.removeListener(
                BroadcastType.TRASH_CHANGED, this.mTrashChangeListener);
        broadcastReceiveCenter.removeListener(
                BroadcastType.CACHED_FILES_CHANGED, this.mCachedFilesChangeListener);
        Log.d(this.mTag, "removeCloudListener");
        this.mContext.getContentResolver().unregisterContentObserver(this.mCloudContentObserver);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onCleared();
    }

    public final void onCreate(PageInfo pageInfo) {
        this.mPageInfo = pageInfo;
        SparseArray sparseArray = BroadcastReceiveCenter.sInstanceMap;
        BroadcastReceiveCenter broadcastReceiveCenter =
                BroadcastReceiveCenter.InstanceHolder.INSTANCE;
        BroadcastType broadcastType = BroadcastType.MEDIA_MOUNTED;
        OverViewController$$ExternalSyntheticLambda0 overViewController$$ExternalSyntheticLambda0 =
                this.mStorageChangeListener;
        broadcastReceiveCenter.addDynamicListener(
                broadcastType, overViewController$$ExternalSyntheticLambda0);
        broadcastReceiveCenter.addDynamicListener(
                BroadcastType.MEDIA_UNMOUNTED, overViewController$$ExternalSyntheticLambda0);
        broadcastReceiveCenter.addDynamicListener(
                BroadcastType.MEDIA_EJECTED, overViewController$$ExternalSyntheticLambda0);
        ((List)
                        broadcastReceiveCenter.mListenerMap.computeIfAbsent(
                                BroadcastType.TRASH_CHANGED,
                                new BroadcastReceiveCenter$$ExternalSyntheticLambda2(0)))
                .add(this.mTrashChangeListener);
        ((List)
                        broadcastReceiveCenter.mListenerMap.computeIfAbsent(
                                BroadcastType.CACHED_FILES_CHANGED,
                                new BroadcastReceiveCenter$$ExternalSyntheticLambda2(0)))
                .add(this.mCachedFilesChangeListener);
        Log.d(this.mTag, "addCloudListener");
        this.mContext
                .getContentResolver()
                .registerContentObserver(CLOUD_ACCOUNT_URI, false, this.mCloudContentObserver);
    }

    public final void setIsLoading(int i, boolean z) {
        if (z) {
            if (((HashMap) this.mIsLoading).containsKey(Integer.valueOf(i))) {
                ((HashMap) this.mIsLoading).remove(Integer.valueOf(i));
            }
            ((HashMap) this.mIsLoading).put(Integer.valueOf(i), 2);
        } else {
            if (((HashMap) this.mIsLoading).containsKey(Integer.valueOf(i))) {
                return;
            }
            ((HashMap) this.mIsLoading).put(Integer.valueOf(i), 0);
            this.delayHandler.postDelayed(
                    new OverViewController$$ExternalSyntheticLambda16(this, i, 0), 1000L);
        }
        this.mIsLoadingData.postValue(this.mIsLoading);
    }

    public final void updateUsageData(int i, ArrayList arrayList) {
        Log.d(this.mTag, "updateUsageData() ] domainType : " + i);
        MutableLiveData usageDetailData = getUsageDetailData(i);
        if (usageDetailData != null) {
            usageDetailData.setValue(arrayList);
            if (arrayList.stream()
                            .filter(new OverViewController$$ExternalSyntheticLambda12())
                            .count()
                    == 0) {
                setIsLoading(i, true);
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList.forEach(new OverViewController$$ExternalSyntheticLambda11(1, arrayList2));
            ((HashMap) this.UiItemTypeMap).put(Integer.valueOf(i), arrayList2);
        }
    }

    public final void updateUsageInfo(final boolean z, boolean z2) {
        Log.d(this.mTag, "updateUsageInfo() ] needDetails : " + z + ", isTrashChange : " + z2);
        ThreadExecutor.runOnWorkThread(
                new Runnable() { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        final OverViewController overViewController = OverViewController.this;
                        final boolean z3 = z;
                        overViewController.getClass();
                        final SparseLongArray sparseLongArray = new SparseLongArray();
                        Optional.ofNullable(
                                        (ArrayList) overViewController.mQuotaStorageList.getValue())
                                .ifPresent(
                                        new Consumer() { // from class:
                                            // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda9
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                OverViewController overViewController2 =
                                                        OverViewController.this;
                                                SparseLongArray sparseLongArray2 = sparseLongArray;
                                                boolean z4 = z3;
                                                overViewController2.getClass();
                                                Iterator it = ((ArrayList) obj).iterator();
                                                while (it.hasNext()) {
                                                    int intValue = ((Integer) it.next()).intValue();
                                                    sparseLongArray2.put(
                                                            intValue,
                                                            StorageUsageManager.getStorageUsedSize(
                                                                    overViewController2.mContext,
                                                                    intValue));
                                                    if (z4) {
                                                        Log.d(
                                                                overViewController2.mTag,
                                                                "getUsageDetailData() - domainType"
                                                                    + " : "
                                                                        + intValue);
                                                        ThreadExecutor.runOnWorkThread(
                                                                new OverViewController$$ExternalSyntheticLambda16(
                                                                        overViewController2,
                                                                        intValue,
                                                                        1));
                                                    }
                                                }
                                            }
                                        });
                        Handler handler = overViewController.mHandler;
                        Message obtainMessage = handler.obtainMessage();
                        obtainMessage.what = 1;
                        obtainMessage.obj = sparseLongArray;
                        handler.sendMessage(obtainMessage);
                    }
                });
    }
}
