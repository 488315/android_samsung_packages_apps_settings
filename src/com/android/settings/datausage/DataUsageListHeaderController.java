package com.android.settings.datausage;

import android.content.Context;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.util.Range;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.lib.NetworkUsageData;
import com.android.settingslib.spa.framework.util.FlowsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.datausage.SecBillingCycleSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataUsageListHeaderController {
    public final View configureButton;
    public final Context context;
    public final CycleAdapter cycleAdapter;
    public final DataUsageListHeaderController$cycleListener$1 cycleListener;
    public final Spinner cycleSpinner;
    public List cycles;
    public boolean isRoaming;
    public final Function1 updateSelectedCycle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.DataUsageListHeaderController$2, reason: invalid class name */
    public final class AnonymousClass2 extends View.AccessibilityDelegate {
        @Override // android.view.View.AccessibilityDelegate
        public final void sendAccessibilityEvent(View host, int i) {
            Intrinsics.checkNotNullParameter(host, "host");
            if (i == 4) {
                return;
            }
            super.sendAccessibilityEvent(host, i);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0010\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010 \n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"
            },
            d2 = {
                "<anonymous>",
                ApnSettings.MVNO_NONE,
                "it",
                ApnSettings.MVNO_NONE,
                "Lcom/android/settings/datausage/lib/NetworkUsageData;"
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.datausage.DataUsageListHeaderController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 =
                    DataUsageListHeaderController.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass3 anonymousClass3 =
                    (AnonymousClass3) create((List) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass3.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            DataUsageListHeaderController dataUsageListHeaderController =
                    DataUsageListHeaderController.this;
            dataUsageListHeaderController.cycles = list;
            List<NetworkUsageData> list2 = list;
            ArrayList arrayList =
                    new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (NetworkUsageData networkUsageData : list2) {
                arrayList.add(
                        new Range(
                                Long.valueOf(networkUsageData.startTime),
                                Long.valueOf(networkUsageData.endTime)));
            }
            dataUsageListHeaderController.cycleAdapter.updateCycleList(arrayList);
            dataUsageListHeaderController.cycleSpinner.setVisibility(0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.datausage.DataUsageListHeaderController$cycleListener$1] */
    public DataUsageListHeaderController(
            View view,
            final NetworkTemplate template,
            final int i,
            LifecycleOwner lifecycleOwner,
            final int i2,
            Flow cyclesFlow,
            Function1 function1) {
        Intrinsics.checkNotNullParameter(template, "template");
        Intrinsics.checkNotNullParameter(cyclesFlow, "cyclesFlow");
        this.updateSelectedCycle = function1;
        Context context = view.getContext();
        this.context = context;
        View requireViewById = view.requireViewById(R.id.filter_settings);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        this.configureButton = requireViewById;
        View requireViewById2 = view.requireViewById(R.id.filter_spinner);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        Spinner spinner = (Spinner) requireViewById2;
        this.cycleSpinner = spinner;
        this.cycleListener =
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.android.settings.datausage.DataUsageListHeaderController$cycleListener$1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view2, int i3, long j) {
                        DataUsageListHeaderController dataUsageListHeaderController =
                                DataUsageListHeaderController.this;
                        NetworkUsageData networkUsageData =
                                (NetworkUsageData)
                                        CollectionsKt___CollectionsKt.getOrNull(
                                                i3, dataUsageListHeaderController.cycles);
                        if (networkUsageData != null) {
                            dataUsageListHeaderController.updateSelectedCycle.invoke(
                                    networkUsageData);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {}
                };
        this.cycleAdapter =
                new CycleAdapter(
                        context,
                        new CycleAdapter
                                .SpinnerInterface() { // from class:
                                                      // com.android.settings.datausage.DataUsageListHeaderController$cycleAdapter$1
                            @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
                            public final Object getSelectedItem() {
                                return DataUsageListHeaderController.this.cycleSpinner
                                        .getSelectedItem();
                            }

                            @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
                            public final void setAdapter(CycleAdapter cycleAdapter) {
                                Intrinsics.checkNotNullParameter(cycleAdapter, "cycleAdapter");
                                DataUsageListHeaderController.this.cycleSpinner.setAdapter(
                                        (SpinnerAdapter) cycleAdapter);
                            }

                            @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
                            public final void setOnItemSelectedListener(
                                    AdapterView.OnItemSelectedListener onItemSelectedListener) {
                                DataUsageListHeaderController.this.cycleSpinner
                                        .setOnItemSelectedListener(onItemSelectedListener);
                            }

                            @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
                            public final void setSelection(int i3) {
                                DataUsageListHeaderController dataUsageListHeaderController =
                                        DataUsageListHeaderController.this;
                                dataUsageListHeaderController.cycleSpinner.setSelection(i3);
                                if (dataUsageListHeaderController.cycleSpinner
                                                .getOnItemSelectedListener()
                                        == null) {
                                    dataUsageListHeaderController.cycleSpinner
                                            .setOnItemSelectedListener(
                                                    dataUsageListHeaderController.cycleListener);
                                    return;
                                }
                                NetworkUsageData networkUsageData =
                                        (NetworkUsageData)
                                                CollectionsKt___CollectionsKt.getOrNull(
                                                        i3, dataUsageListHeaderController.cycles);
                                if (networkUsageData != null) {
                                    dataUsageListHeaderController.updateSelectedCycle.invoke(
                                            networkUsageData);
                                }
                            }
                        });
        this.cycles = EmptyList.INSTANCE;
        view.semSetRoundedCorners(15);
        view.semSetRoundedCornerColor(
                15, context.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        requireViewById.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.datausage.DataUsageListHeaderController.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        Bundle bundle = new Bundle();
                        NetworkTemplate networkTemplate = template;
                        int i3 = i2;
                        bundle.putParcelable("network_template", networkTemplate);
                        bundle.putInt("subscriptionid", i3);
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(DataUsageListHeaderController.this.context);
                        int i4 = i;
                        String name = SecBillingCycleSettings.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        subSettingLauncher.setTitleRes(R.string.billing_cycle, null);
                        launchRequest.mSourceMetricsCategory = i4;
                        launchRequest.mArguments = bundle;
                        subSettingLauncher.launch();
                    }
                });
        spinner.setVisibility(8);
        spinner.setAccessibilityDelegate(new AnonymousClass2());
        FlowsKt.collectLatestWithLifecycle(
                cyclesFlow, lifecycleOwner, Lifecycle.State.RESUMED, new AnonymousClass3(null));
    }
}
