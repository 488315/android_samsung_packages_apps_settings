package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Message;
import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.data.database.repository.AnalyzeStorageFileInfoRepository;
import com.samsung.android.settings.analyzestorage.data.database.repository.RepositoryFactory;
import com.samsung.android.settings.analyzestorage.data.database.repository.abstraction.AbsFileRepository$QueryParams;
import com.samsung.android.settings.analyzestorage.data.model.OverViewItemInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IDataInfoRepository;
import com.samsung.android.settings.analyzestorage.external.database.datasource.MediaProviderDataSource;
import com.samsung.android.settings.analyzestorage.external.database.repository.AnalyzeStorageFileInfoRepositoryImpl;
import com.samsung.android.settings.analyzestorage.external.database.repository.RepositoryFactoryImpl;
import com.samsung.android.settings.analyzestorage.presenter.account.QuotaInfo;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class OverViewController$$ExternalSyntheticLambda16
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OverViewController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ OverViewController$$ExternalSyntheticLambda16(
            OverViewController overViewController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = overViewController;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType;
        switch (this.$r8$classId) {
            case 0:
                OverViewController overViewController = this.f$0;
                int i = this.f$1;
                if (((HashMap) overViewController.mIsLoading).containsKey(Integer.valueOf(i))) {
                    if (((Integer)
                                            ((HashMap) overViewController.mIsLoading)
                                                    .get(Integer.valueOf(i)))
                                    .intValue()
                            == 2) {
                        return;
                    }
                }
                if (((HashMap) overViewController.mIsLoading).containsKey(Integer.valueOf(i))) {
                    ((HashMap) overViewController.mIsLoading).remove(Integer.valueOf(i));
                }
                ((HashMap) overViewController.mIsLoading).put(Integer.valueOf(i), 1);
                overViewController.mIsLoadingData.postValue(overViewController.mIsLoading);
                return;
            default:
                OverViewController overViewController2 = this.f$0;
                int i2 = this.f$1;
                overViewController2.getClass();
                ArrayList arrayList = new ArrayList();
                if (DomainType.isLocalStorage(i2)) {
                    ArrayList arrayList2 = new ArrayList();
                    SparseArray sparseArray = new SparseArray();
                    RepositoryFactory.getGenerator().getClass();
                    sparseArray.put(
                            0,
                            AnalyzeStorageFileInfoRepositoryImpl.getInstance(
                                    RepositoryFactoryImpl.sContext,
                                    new MediaProviderDataSource(RepositoryFactoryImpl.sContext)));
                    AnalyzeStorageFileInfoRepository analyzeStorageFileInfoRepository =
                            (AnalyzeStorageFileInfoRepository)
                                    ((IDataInfoRepository) sparseArray.get(0));
                    if (analyzeStorageFileInfoRepository != null) {
                        AbsFileRepository$QueryParams absFileRepository$QueryParams =
                                new AbsFileRepository$QueryParams();
                        Bundle bundle = absFileRepository$QueryParams.mExtras;
                        bundle.putString("strCommand", "detailedSize");
                        bundle.putInt("domainType", i2);
                        Cursor query =
                                analyzeStorageFileInfoRepository.query(
                                        absFileRepository$QueryParams);
                        try {
                            Log.d(
                                    overViewController2.mTag,
                                    "getUsageDetailDataByCategory() - domainType : " + i2);
                            if (query != null && query.moveToFirst()) {
                                do {
                                    AnalyzeStorageConstants$UiItemType.Companion companion =
                                            AnalyzeStorageConstants$UiItemType.Companion;
                                    MatrixCursor matrixCursor = (MatrixCursor) query;
                                    int i3 = matrixCursor.getInt(0);
                                    companion.getClass();
                                    AnalyzeStorageConstants$UiItemType[] values =
                                            AnalyzeStorageConstants$UiItemType.values();
                                    int length = values.length;
                                    int i4 = 0;
                                    while (true) {
                                        if (i4 < length) {
                                            analyzeStorageConstants$UiItemType = values[i4];
                                            if (i3
                                                    != analyzeStorageConstants$UiItemType
                                                            .getType()) {
                                                i4++;
                                            }
                                        } else {
                                            analyzeStorageConstants$UiItemType = null;
                                        }
                                    }
                                    if (analyzeStorageConstants$UiItemType == null) {
                                        analyzeStorageConstants$UiItemType =
                                                AnalyzeStorageConstants$UiItemType.INVALID_ITEM;
                                    }
                                    Log.d(
                                            overViewController2.mTag,
                                            "usage detail data : ("
                                                    + analyzeStorageConstants$UiItemType.name()
                                                    + ", "
                                                    + matrixCursor.getLong(1)
                                                    + ")");
                                    arrayList2.add(
                                            new OverViewItemInfo(
                                                    analyzeStorageConstants$UiItemType,
                                                    Long.valueOf(matrixCursor.getLong(1))));
                                } while (query.moveToNext());
                            }
                            if (query != null) {
                                query.close();
                            }
                        } catch (Throwable th) {
                            if (query != null) {
                                try {
                                    query.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    }
                    arrayList2.add(
                            new OverViewItemInfo(
                                    AnalyzeStorageConstants$UiItemType.TOTAL,
                                    Long.valueOf(StorageUsageManager.getStorageTotalSize(i2))));
                    arrayList = arrayList2;
                } else if (DomainType.isCloud(i2)) {
                    CloudConstants$CloudType.Companion.getClass();
                    CloudConstants$CloudType fromDomainType =
                            CloudConstants$CloudType.Companion.fromDomainType(i2);
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
                    SettingsCloudAccountManager.AccountInfo accountInfo =
                            (SettingsCloudAccountManager.AccountInfo)
                                    ((EnumMap) SettingsCloudAccountManager.sInstance.accountInfoMap)
                                            .get(fromDomainType);
                    if (accountInfo != null) {
                        QuotaInfo quotaInfo = accountInfo.quotaInfo;
                        arrayList =
                                overViewController2.formatCloudDetailData(
                                        quotaInfo.additionalUsageInfo,
                                        fromDomainType,
                                        quotaInfo.totalSize);
                    } else {
                        Log.e(
                                overViewController2.mTag,
                                "getCloudUsageDetailData() ] - accountInfo is null "
                                        + fromDomainType);
                        arrayList = new ArrayList();
                    }
                }
                Message obtainMessage = overViewController2.mHandler.obtainMessage();
                obtainMessage.what = 0;
                obtainMessage.arg1 = i2;
                obtainMessage.obj = arrayList;
                overViewController2.mHandler.sendMessage(obtainMessage);
                return;
        }
    }
}
