package com.samsung.android.settings.analyzestorage.presenter.controllers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.analyzestorage.data.model.RecommendCardState;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.utils.CollectionUtils;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType;
import com.samsung.android.settings.analyzestorage.presenter.constant.CloudConstants$CloudType$Companion$realCloudList$1;
import com.samsung.android.settings.analyzestorage.presenter.managers.AsRecommendCardInfoManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.application.AppDataInterface$AppData;
import com.samsung.android.settings.analyzestorage.presenter.managers.application.AppDataManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.application.AppDataManager$getTopInstalledApp$$inlined$sortedByDescending$1;
import com.samsung.android.settings.analyzestorage.presenter.utils.CloudUtils$Companion;
import com.samsung.android.settings.analyzestorage.presenter.utils.ExtensionFunctionsKt;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.RecommendCardView;

import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecommendCardController extends AbsPageController {
    public final Uri CLOUD_ACCOUNT_URI;
    public final ArrayList archiveAppNameList;
    public final HashMap bucketIdCard;
    public RecommendCardView chooseAccountListener;
    public final HashMap fileSizeCard;
    public final HashMap fileTypeCard;
    public final MutableLiveData isLoading;
    public final HashMap nameCard;
    public int numberApps;
    public final HashMap pathCard;
    public final MutableLiveData recommendCardState;
    public ArrayList supportedRecommendCardListTemp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecommendCardController(Context context, int i) {
        super(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.supportedRecommendCardListTemp = new ArrayList();
        this.recommendCardState = new MutableLiveData();
        this.fileSizeCard = new HashMap();
        this.pathCard = new HashMap();
        this.fileTypeCard = new HashMap();
        this.nameCard = new HashMap();
        this.bucketIdCard = new HashMap();
        this.isLoading = new MutableLiveData();
        this.archiveAppNameList = new ArrayList();
        Uri build =
                Uri.parse("content://myfiles").buildUpon().appendPath("cloud_account_info").build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        this.CLOUD_ACCOUNT_URI = build;
        this.mTag = "RecommendCardController";
    }

    public final void getCloudStatus() {
        Set set;
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
        if (settingsCloudAccountManager2 != null) {
            Context mContext = this.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            if (CloudUtils$Companion.isSupportCloud(mContext)) {
                Context mContext2 = this.mContext;
                Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
                Set set2 = EmptySet.INSTANCE;
                try {
                    Bundle call =
                            mContext2
                                    .getContentResolver()
                                    .call(
                                            "myfiles",
                                            "GET_VISIBILITY_HOME_CLOUD",
                                            (String) null,
                                            AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                                    "calling_package",
                                                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG));
                    int[] intArray = call != null ? call.getIntArray("visibilityHomeCloud") : null;
                    Intrinsics.checkNotNull(intArray);
                    if (!(intArray.length == 0)) {
                        int length = intArray.length;
                        if (length != 0) {
                            if (length != 1) {
                                set =
                                        new LinkedHashSet(
                                                MapsKt__MapsKt.mapCapacity(intArray.length));
                                for (int i : intArray) {
                                    set.add(Integer.valueOf(i));
                                }
                            } else {
                                set = SetsKt.setOf(Integer.valueOf(intArray[0]));
                            }
                        } else {
                            set = EmptySet.INSTANCE;
                        }
                        set2 = set;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (CollectionUtils.isEmpty(set2)) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                if (set2.size() > 1) {
                    CloudConstants$CloudType.Companion.getClass();
                    Object collect =
                            Arrays.stream(CloudConstants$CloudType.values())
                                    .filter(
                                            CloudConstants$CloudType$Companion$realCloudList$1
                                                    .INSTANCE)
                                    .collect(Collectors.toList());
                    Intrinsics.checkNotNullExpressionValue(collect, "collect(...)");
                    for (CloudConstants$CloudType cloudConstants$CloudType : (List) collect) {
                        if (set2.contains(Integer.valueOf(cloudConstants$CloudType.getValue()))
                                && !settingsCloudAccountManager2.isSignedIn(
                                        cloudConstants$CloudType)) {
                            arrayList.add(cloudConstants$CloudType.toString());
                        }
                    }
                    if (arrayList.size() > 1) {
                        AsRecommendCardInfoManager.cloudList = arrayList;
                        this.supportedRecommendCardListTemp.add(5);
                    }
                }
            }
        }
    }

    public final void getTopInstalledAppSize() {
        ConcurrentHashMap concurrentHashMap = AppDataManager.appsDataMap;
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        AppDataInterface$AppData appDataInterface$AppData = AppDataInterface$AppData.TOTAL;
        long currentTimeMillis = System.currentTimeMillis();
        AppDataManager.getAppsSize(mContext);
        Log.d(
                "AppDataManager",
                "getTopInstalledApp() ] load app size time = "
                        + (System.currentTimeMillis() - currentTimeMillis)
                        + "ms");
        Collection values = new HashMap(AppDataManager.appsDataMap).values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        List take =
                CollectionsKt___CollectionsKt.take(
                        CollectionsKt___CollectionsKt.sortedWith(
                                values,
                                new AppDataManager$getTopInstalledApp$$inlined$sortedByDescending$1()),
                        3);
        ArrayList arrayList = new ArrayList();
        Iterator it = take.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppDataManager.AppDataInfo) it.next()).packageName);
        }
        AsRecommendCardInfoManager.appList = arrayList;
        if (!take.isEmpty()) {
            this.supportedRecommendCardListTemp.add(6);
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController, androidx.lifecycle.ViewModel
    public final void onCleared() {
        super.onCleared();
        AsRecommendCardInfoManager.Companion companion = AsRecommendCardInfoManager.Companion;
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        companion.getInstance(mContext);
        AsRecommendCardInfoManager.cloudList = null;
        AsRecommendCardInfoManager.appList = null;
    }

    public final void removeCard(int i) {
        ArrayList arrayList;
        Log.d(this.mTag, "removeCard - cardType: " + i);
        int indexOf = this.supportedRecommendCardListTemp.indexOf(Integer.valueOf(i));
        this.supportedRecommendCardListTemp.remove(Integer.valueOf(i));
        RecommendCardState recommendCardState =
                new RecommendCardState(1, this.supportedRecommendCardListTemp, indexOf, 0, 8);
        MutableLiveData mutableLiveData = this.recommendCardState;
        ExtensionFunctionsKt.updateValue(mutableLiveData, recommendCardState);
        String str = this.mTag;
        RecommendCardState recommendCardState2 = (RecommendCardState) mutableLiveData.getValue();
        Log.d(
                str,
                "removeCard - mSupportedRecommendCardList size: "
                        + ((recommendCardState2 == null
                                        || (arrayList = recommendCardState2.supportedCardList)
                                                == null)
                                ? null
                                : Integer.valueOf(arrayList.size())));
    }

    public final void setLoadingData$1(boolean z) {
        Log.d(this.mTag, "setLoadingData()] value: " + z);
        ExtensionFunctionsKt.updateValue(this.isLoading, Boolean.valueOf(z));
    }

    public final void updateOnlyCard(int i) {
        if (i == 5) {
            Cursor query =
                    this.mContext
                            .getContentResolver()
                            .query(this.CLOUD_ACCOUNT_URI, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        do {
                            Log.d(
                                    this.mTag,
                                    "checkAccountStatusByProviderQueryWithContentObserver() ]"
                                        + " account : "
                                            + Log.getEncodedMsg(query.getString(1)));
                            String string = query.getString(1);
                            if (string != null && string.length() != 0) {
                                CloseableKt.closeFinally(query, null);
                            }
                        } while (query.moveToNext());
                    }
                    CloseableKt.closeFinally(query, null);
                    return;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(query, th);
                        throw th2;
                    }
                }
            }
            return;
        }
        setLoadingData$1(true);
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this),
                Dispatchers.IO,
                null,
                new RecommendCardController$updateOnlyCard$1(this, i, null),
                2);
    }

    public final void updateSupportedRecommendCardList() {
        setLoadingData$1(true);
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this),
                Dispatchers.IO,
                null,
                new RecommendCardController$updateSupportedRecommendCardList$1(this, null),
                2);
    }
}
