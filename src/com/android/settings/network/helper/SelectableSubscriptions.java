package com.android.settings.network.helper;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Keep;

import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SelectableSubscriptions implements Callable<List<SubscriptionAnnotation>> {
    public final Context mContext;
    public final Predicate mFilter = new SelectableSubscriptions$$ExternalSyntheticLambda4();
    public final SelectableSubscriptions$$ExternalSyntheticLambda5 mFinisher =
            new SelectableSubscriptions$$ExternalSyntheticLambda5();
    public final Supplier mSubscriptions;

    public SelectableSubscriptions(final Context context) {
        this.mContext = context;
        this.mSubscriptions =
                new Supplier() { // from class:
                                 // com.android.settings.network.helper.SelectableSubscriptions$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        SelectableSubscriptions selectableSubscriptions =
                                SelectableSubscriptions.this;
                        Context context2 = context;
                        selectableSubscriptions.getClass();
                        SubscriptionManager createForAllUserProfiles =
                                ((SubscriptionManager)
                                                context2.getSystemService(
                                                        SubscriptionManager.class))
                                        .createForAllUserProfiles();
                        List availableSubscriptionInfoList =
                                createForAllUserProfiles == null
                                        ? null
                                        : createForAllUserProfiles
                                                .getAvailableSubscriptionInfoList();
                        return availableSubscriptionInfoList == null
                                ? Collections.emptyList()
                                : availableSubscriptionInfoList;
                    }
                };
    }

    @Keep
    public static List<Integer> atomicToList(final AtomicIntegerArray atomicIntegerArray) {
        return atomicIntegerArray == null
                ? Collections.emptyList()
                : (List)
                        IntStream.range(0, atomicIntegerArray.length())
                                .map(
                                        new IntUnaryOperator() { // from class:
                                                                 // com.android.settings.network.helper.SelectableSubscriptions$$ExternalSyntheticLambda0
                                            @Override // java.util.function.IntUnaryOperator
                                            public final int applyAsInt(int i) {
                                                return atomicIntegerArray.get(i);
                                            }
                                        })
                                .boxed()
                                .collect(Collectors.toList());
    }

    @Override // java.util.concurrent.Callable
    public final List<SubscriptionAnnotation> call() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        try {
            QuerySimSlotIndex querySimSlotIndex = new QuerySimSlotIndex();
            querySimSlotIndex.mTelephonyManager = telephonyManager;
            querySimSlotIndex.mDisabledSlotsIncluded = false;
            querySimSlotIndex.mOnlySlotWithSim = true;
            ListenableFuture submit =
                    ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                            .submit((Callable) querySimSlotIndex);
            final List list = (List) this.mSubscriptions.get();
            final List<Integer> atomicToList = atomicToList((AtomicIntegerArray) submit.get());
            return (List)
                    IntStream.range(0, list.size())
                            .mapToObj(
                                    new IntFunction() { // from class:
                                                        // com.android.settings.network.helper.SelectableSubscriptions$$ExternalSyntheticLambda1
                                        @Override // java.util.function.IntFunction
                                        public final Object apply(int i) {
                                            return new SubscriptionAnnotation.Builder(i, list);
                                        }
                                    })
                            .map(
                                    new Function() { // from class:
                                                     // com.android.settings.network.helper.SelectableSubscriptions$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            SelectableSubscriptions selectableSubscriptions =
                                                    SelectableSubscriptions.this;
                                            List list2 = atomicToList;
                                            SubscriptionAnnotation.Builder builder =
                                                    (SubscriptionAnnotation.Builder) obj;
                                            return new SubscriptionAnnotation(
                                                    builder.mSubInfoList,
                                                    builder.mIndexWithinList,
                                                    selectableSubscriptions.mContext,
                                                    list2);
                                        }
                                    })
                            .filter(this.mFilter)
                            .collect(
                                    Collectors.collectingAndThen(
                                            Collectors.toList(), this.mFinisher));
        } catch (Exception e) {
            Log.w("SelectableSubscriptions", "Fail to request subIdList", e);
            return Collections.emptyList();
        }
    }
}
