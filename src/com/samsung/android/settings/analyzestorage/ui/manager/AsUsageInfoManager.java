package com.samsung.android.settings.analyzestorage.ui.manager;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.SparseArray;
import android.util.SparseIntArray;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.StorageManageHelper;
import com.samsung.android.settings.analyzestorage.presenter.feature.SepFeatures$FloatingFeature;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsUsageInfoManager {
    public static final Companion Companion = new Companion();
    public static final AnalyzeStorageConstants$UiItemType[] MEDIA_TYPE_ITEM_LIST = {
        AnalyzeStorageConstants$UiItemType.IMAGES,
        AnalyzeStorageConstants$UiItemType.VIDEOS,
        AnalyzeStorageConstants$UiItemType.AUDIO,
        AnalyzeStorageConstants$UiItemType.DOCUMENTS,
        AnalyzeStorageConstants$UiItemType.APK,
        AnalyzeStorageConstants$UiItemType.COMPRESSED
    };
    public static volatile AsUsageInfoManager instance;
    public final Context context;
    public final SparseArray usageInfoMap = new SparseArray();
    public final SparseIntArray dividerPosition = new SparseIntArray();
    public final Lazy clickableTypeSet$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.manager.AsUsageInfoManager$clickableTypeSet$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            AsUsageInfoManager.this.getClass();
                            EnumSet of =
                                    EnumSet.of(
                                            AnalyzeStorageConstants$UiItemType.IMAGES,
                                            AnalyzeStorageConstants$UiItemType.VIDEOS,
                                            AnalyzeStorageConstants$UiItemType.AUDIO,
                                            AnalyzeStorageConstants$UiItemType.DOCUMENTS,
                                            AnalyzeStorageConstants$UiItemType.COMPRESSED,
                                            AnalyzeStorageConstants$UiItemType.APK,
                                            AnalyzeStorageConstants$UiItemType.APPS,
                                            AnalyzeStorageConstants$UiItemType.SECURE_FOLDER,
                                            AnalyzeStorageConstants$UiItemType.WORK_PROFILE);
                            Intrinsics.checkNotNullExpressionValue(of, "of(...)");
                            return of;
                        }
                    });
    public final int externalItemCount = 1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public final AsUsageInfoManager getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            AsUsageInfoManager asUsageInfoManager = AsUsageInfoManager.instance;
            if (asUsageInfoManager == null) {
                synchronized (this) {
                    asUsageInfoManager = AsUsageInfoManager.instance;
                    if (asUsageInfoManager == null) {
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(
                                applicationContext, "getApplicationContext(...)");
                        asUsageInfoManager = new AsUsageInfoManager(applicationContext);
                        AsUsageInfoManager.instance = asUsageInfoManager;
                    }
                }
            }
            return asUsageInfoManager;
        }
    }

    public AsUsageInfoManager(Context context) {
        this.context = context;
    }

    public final List getUsageInfoList(int i) {
        List list = (List) this.usageInfoMap.get(i);
        if (!DomainType.isInternalStorage(i) && list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        AnalyzeStorageConstants$UiItemType[] analyzeStorageConstants$UiItemTypeArr =
                MEDIA_TYPE_ITEM_LIST;
        if (i == 0) {
            Collections.addAll(
                    arrayList,
                    AnalyzeStorageConstants$UiItemType.APPS,
                    AnalyzeStorageConstants$UiItemType.SYSTEM,
                    AnalyzeStorageConstants$UiItemType.OTHER_FILES);
            if (SepFeatures$FloatingFeature.SUPPORT_TRASH) {
                arrayList.add(AnalyzeStorageConstants$UiItemType.TRASH);
            }
            if (StorageManageHelper.isSupportCapacityOfSubUsers(i)) {
                Context context = this.context;
                Intrinsics.checkNotNullParameter(context, "context");
                UserInfoCheckerImpl userInfoCheckerImpl = UserInfoManager.sUserInfoChecker;
                if (userInfoCheckerImpl == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
                    throw null;
                }
                List userInfoList = userInfoCheckerImpl.getUserInfoList(context);
                if (userInfoList.size() > 1) {
                    Context context2 = this.context;
                    Intrinsics.checkNotNullParameter(context2, "context");
                    if (UserInfoManager.sUserInfoChecker == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
                        throw null;
                    }
                    Object systemService = context2.getSystemService("user");
                    Intrinsics.checkNotNull(
                            systemService,
                            "null cannot be cast to non-null type android.os.UserManager");
                    List<UserHandle> userProfiles = ((UserManager) systemService).getUserProfiles();
                    Intrinsics.checkNotNullExpressionValue(userProfiles, "getUserProfiles(...)");
                    this.dividerPosition.put(
                            i, arrayList.size() + analyzeStorageConstants$UiItemTypeArr.length);
                    if (UserInfoManager.sUserInfoChecker == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
                        throw null;
                    }
                    List<UserHandle> list2 = userProfiles;
                    ArrayList arrayList2 =
                            new ArrayList(
                                    CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(
                                Integer.valueOf(((UserHandle) it.next()).semGetIdentifier()));
                    }
                    if (!arrayList2.isEmpty()) {
                        Iterator it2 = arrayList2.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            if (SemPersonaManager.isSecureFolderId(
                                    ((Number) it2.next()).intValue())) {
                                arrayList.add(AnalyzeStorageConstants$UiItemType.SECURE_FOLDER);
                                break;
                            }
                        }
                    }
                    Context context3 = this.context;
                    Intrinsics.checkNotNullParameter(context3, "context");
                    UserInfoCheckerImpl userInfoCheckerImpl2 = UserInfoManager.sUserInfoChecker;
                    if (userInfoCheckerImpl2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
                        throw null;
                    }
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        Iterator<T> it3 = list2.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            if (((UserHandle) it3.next()).semGetIdentifier()
                                    == userInfoCheckerImpl2.getWorkProfileUserId(context3)) {
                                arrayList.add(AnalyzeStorageConstants$UiItemType.WORK_PROFILE);
                                break;
                            }
                        }
                    }
                    Context context4 = this.context;
                    Intrinsics.checkNotNullParameter(context4, "context");
                    UserInfoCheckerImpl userInfoCheckerImpl3 = UserInfoManager.sUserInfoChecker;
                    if (userInfoCheckerImpl3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
                        throw null;
                    }
                    List list3 = userInfoList;
                    ArrayList arrayList3 =
                            new ArrayList(
                                    CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    Iterator it4 = list3.iterator();
                    while (it4.hasNext()) {
                        arrayList3.add(
                                Integer.valueOf(((UserHandle) it4.next()).semGetIdentifier()));
                    }
                    if (!arrayList3.isEmpty()) {
                        Iterator it5 = arrayList3.iterator();
                        while (true) {
                            if (!it5.hasNext()) {
                                break;
                            }
                            int intValue = ((Number) it5.next()).intValue();
                            if (userInfoCheckerImpl3.getMyUserId() != intValue
                                    && intValue
                                            != userInfoCheckerImpl3.getWorkProfileUserId(context4)
                                    && !SemPersonaManager.isSecureFolderId(intValue)) {
                                arrayList.add(AnalyzeStorageConstants$UiItemType.OTHER_PROFILE);
                                break;
                            }
                        }
                    }
                }
            }
        } else if (i == 1) {
            Collections.addAll(arrayList, AnalyzeStorageConstants$UiItemType.OTHER_FILES);
            if (SepFeatures$FloatingFeature.SUPPORT_TRASH) {
                arrayList.add(AnalyzeStorageConstants$UiItemType.TRASH);
            }
        } else if (i == 2 || i == 3 || i == 4) {
            Collections.addAll(
                    arrayList,
                    AnalyzeStorageConstants$UiItemType.APPS,
                    AnalyzeStorageConstants$UiItemType.OTHER_FILES);
            if (SepFeatures$FloatingFeature.SUPPORT_TRASH) {
                arrayList.add(AnalyzeStorageConstants$UiItemType.TRASH);
            }
        } else if (i == 101) {
            Collections.addAll(
                    arrayList,
                    AnalyzeStorageConstants$UiItemType.OTHER_FILES,
                    AnalyzeStorageConstants$UiItemType.GOOGLE_APPS);
            this.dividerPosition.put(
                    i, analyzeStorageConstants$UiItemTypeArr.length + this.externalItemCount);
        } else {
            if (i != 102) {
                throw new IllegalStateException(
                        LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                i,
                                "AsUsageInfoManager : createUsageInfoList()] unsupported"
                                    + " domainType(",
                                ")"));
            }
            Collections.addAll(arrayList, AnalyzeStorageConstants$UiItemType.OTHER_FILES);
        }
        List mutableListOf =
                CollectionsKt__CollectionsKt.mutableListOf(
                        Arrays.copyOf(
                                analyzeStorageConstants$UiItemTypeArr,
                                analyzeStorageConstants$UiItemTypeArr.length));
        mutableListOf.addAll(arrayList);
        this.usageInfoMap.put(i, mutableListOf);
        return mutableListOf;
    }
}
