package com.android.settings.datausage;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.datausage.lib.AppDataUsageRepository;
import com.android.settingslib.AppItem;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.net.UidDetailProvider;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/datausage/AppDataUsagePreference;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DataUsageListAppsController$update$1$apps$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Integer $carrierId;
    final /* synthetic */ long $endTime;
    final /* synthetic */ long $startTime;
    int label;
    final /* synthetic */ DataUsageListAppsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageListAppsController$update$1$apps$1(
            DataUsageListAppsController dataUsageListAppsController,
            Integer num,
            long j,
            long j2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsageListAppsController;
        this.$carrierId = num;
        this.$startTime = j;
        this.$endTime = j2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataUsageListAppsController$update$1$apps$1(
                this.this$0, this.$carrierId, this.$startTime, this.$endTime, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataUsageListAppsController$update$1$apps$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AppDataUsageRepository appDataUsageRepository;
        Context context;
        UidDetailProvider uidDetailProvider;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        appDataUsageRepository = this.this$0.repository;
        if (appDataUsageRepository == null) {
            Intrinsics.throwUninitializedPropertyAccessException("repository");
            throw null;
        }
        Integer num = this.$carrierId;
        List queryBuckets =
                appDataUsageRepository.networkStatsRepository.queryBuckets(
                        this.$startTime, this.$endTime);
        appDataUsageRepository.secureusage = 0L;
        List<Pair<AppItem, Integer>> appPercent =
                appDataUsageRepository.getAppPercent(num, queryBuckets);
        final DataUsageListAppsController dataUsageListAppsController = this.this$0;
        final long j = this.$endTime;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(appPercent, 10));
        Iterator<T> it = appPercent.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            final AppItem appItem = (AppItem) pair.getFirst();
            int intValue = ((Number) pair.getSecond()).intValue();
            context = ((AbstractPreferenceController) dataUsageListAppsController).mContext;
            uidDetailProvider = dataUsageListAppsController.uidDetailProvider;
            AppDataUsagePreference appDataUsagePreference =
                    new AppDataUsagePreference(context, appItem, intValue, uidDetailProvider);
            appDataUsagePreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.datausage.DataUsageListAppsController$update$1$apps$1$1$1$1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            DataUsageListAppsController.this.startAppDataUsage(appItem, j);
                            return true;
                        }
                    });
            arrayList.add(appDataUsagePreference);
        }
        return arrayList;
    }
}
