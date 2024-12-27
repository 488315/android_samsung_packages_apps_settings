package com.android.settings.spa.network;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.lifecycle.viewmodel.compose.ViewModelKt;

import com.android.settings.R;
import com.android.settings.network.SubscriptionInfoListViewModel;
import com.android.settings.network.telephony.DataSubscriptionRepository;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverFlowKt;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkCellularGroupProvider implements SettingsPageProvider {
    public int defaultDataSubId;
    public int defaultSmsSubId;
    public int defaultVoiceSubId;
    public int nonDds;

    public final void OtherSection(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1057112412);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProvider$OtherSection$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            NetworkCellularGroupProvider.this.OtherSection(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r2v11, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v13, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v5, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v7, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v9, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.settings.spa.network.NetworkCellularGroupProvider$Page$4, kotlin.jvm.internal.Lambda] */
    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1681868764);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element =
                RememberSaveableKt.rememberSaveable(
                        new Object[0],
                        null,
                        new Function0() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$callsSelectedId$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return SnapshotIntStateKt.mutableIntStateOf(-1);
                            }
                        },
                        composerImpl,
                        3080,
                        6);
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        ref$ObjectRef2.element =
                RememberSaveableKt.rememberSaveable(
                        new Object[0],
                        null,
                        new Function0() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$textsSelectedId$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return SnapshotIntStateKt.mutableIntStateOf(-1);
                            }
                        },
                        composerImpl,
                        3080,
                        6);
        final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        ref$ObjectRef3.element =
                RememberSaveableKt.rememberSaveable(
                        new Object[0],
                        null,
                        new Function0() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$mobileDataSelectedId$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return SnapshotIntStateKt.mutableIntStateOf(-1);
                            }
                        },
                        composerImpl,
                        3080,
                        6);
        final Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
        ref$ObjectRef4.element =
                RememberSaveableKt.rememberSaveable(
                        new Object[0],
                        null,
                        new Function0() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$nonDdsRemember$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return SnapshotIntStateKt.mutableIntStateOf(-1);
                            }
                        },
                        composerImpl,
                        3080,
                        6);
        final Ref$ObjectRef ref$ObjectRef5 = new Ref$ObjectRef();
        ref$ObjectRef5.element =
                RememberSaveableKt.rememberSaveable(
                        new Object[0],
                        null,
                        new Function0() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$showMobileDataSection$1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return SnapshotStateKt.mutableStateOf(
                                        Boolean.FALSE, StructuralEqualityPolicy.INSTANCE);
                            }
                        },
                        composerImpl,
                        3080,
                        6);
        composerImpl.startReplaceableGroup(1729797275);
        ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
        if (current == null) {
            throw new IllegalStateException(
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        ViewModel viewModel =
                ViewModelKt.viewModel(
                        Reflection.factory.getOrCreateKotlinClass(
                                SubscriptionInfoListViewModel.class),
                        current,
                        null,
                        current instanceof HasDefaultViewModelProviderFactory
                                ? ((HasDefaultViewModelProviderFactory) current)
                                        .getDefaultViewModelCreationExtras()
                                : CreationExtras.Empty.INSTANCE,
                        composerImpl);
        composerImpl.end(false);
        final SubscriptionInfoListViewModel subscriptionInfoListViewModel =
                (SubscriptionInfoListViewModel) viewModel;
        NetworkCellularGroupProviderKt.CollectAirplaneModeAndFinishIfOn(composerImpl, 0);
        composerImpl.startReplaceGroup(358362641);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        ReadonlyStateFlow flow = subscriptionInfoListViewModel.selectableSubscriptionInfoListFlow;
        if (rememberedValue == composer$Companion$Empty$1) {
            Flow buffer$default =
                    FlowKt.buffer$default(
                            new NetworkCellularGroupProviderKt$defaultSmsSubscriptionFlow$$inlined$map$1(
                                    FlowKt.merge(
                                            new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(
                                                    null),
                                            BroadcastReceiverFlowKt.broadcastReceiverFlow(
                                                    context,
                                                    new IntentFilter(
                                                            "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED"))),
                                    2),
                            -1);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            Flow flow2 = FlowKt.flowOn(buffer$default, defaultScheduler);
            Flow flow3 =
                    FlowKt.flowOn(
                            FlowKt.buffer$default(
                                    new NetworkCellularGroupProviderKt$defaultSmsSubscriptionFlow$$inlined$map$1(
                                            FlowKt.merge(
                                                    new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(
                                                            null),
                                                    BroadcastReceiverFlowKt.broadcastReceiverFlow(
                                                            context,
                                                            new IntentFilter(
                                                                    "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED"))),
                                            0),
                                    -1),
                            defaultScheduler);
            Flow flow4 = new DataSubscriptionRepository(context).defaultDataSubscriptionIdFlow();
            NetworkCellularGroupProvider$allOfFlows$1 networkCellularGroupProvider$allOfFlows$1 =
                    new NetworkCellularGroupProvider$allOfFlows$1(
                            5,
                            this,
                            NetworkCellularGroupProvider.class,
                            "refreshUiStates",
                            "refreshUiStates(Ljava/util/List;III)V",
                            4);
            Intrinsics.checkNotNullParameter(flow, "flow");
            Intrinsics.checkNotNullParameter(flow2, "flow2");
            Intrinsics.checkNotNullParameter(flow3, "flow3");
            Intrinsics.checkNotNullParameter(flow4, "flow4");
            Flow[] flowArr = {flow, flow2, flow3, flow4};
            z = true;
            rememberedValue =
                    FlowKt.flowOn(
                            new FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1(
                                    flowArr, networkCellularGroupProvider$allOfFlows$1, 1),
                            defaultScheduler);
            composerImpl.updateRememberedValue(rememberedValue);
        } else {
            z = true;
        }
        composerImpl.end(false);
        FlowsKt.collectLatestWithLifecycle(
                (Flow) rememberedValue,
                (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner),
                Lifecycle.State.STARTED,
                new NetworkCellularGroupProvider$Page$2(
                        ref$ObjectRef, this, ref$ObjectRef2, ref$ObjectRef3, ref$ObjectRef4, null));
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(flow, EmptyList.INSTANCE, composerImpl, 56);
        MutableState mutableState = (MutableState) ref$ObjectRef5.element;
        List list = (List) collectAsStateWithLifecycle.getValue();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((SubscriptionInfo) obj).getSimSlotIndex() > -1) {
                arrayList.add(obj);
            }
        }
        mutableState.setValue(Boolean.valueOf(arrayList.size() > 0 ? z : false));
        RegularScaffoldKt.RegularScaffold(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.provider_network_settings_title),
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        1612148905,
                        new Function2() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$4
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Composer composer2 = (Composer) obj2;
                                if ((((Number) obj3).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                SimsSectionKt.SimsSection(
                                        (List) collectAsStateWithLifecycle.getValue(),
                                        composer2,
                                        8);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(-94200347);
                                if (((Boolean) Ref$ObjectRef.this.element.getValue())
                                        .booleanValue()) {
                                    NetworkCellularGroupProviderKt.MobileDataSectionImpl(
                                            ref$ObjectRef3.element,
                                            ref$ObjectRef4.element,
                                            composerImpl3,
                                            0);
                                }
                                composerImpl3.end(false);
                                NetworkCellularGroupProviderKt.PrimarySimSectionImpl(
                                        subscriptionInfoListViewModel
                                                .selectableSubscriptionInfoListFlow,
                                        ref$ObjectRef.element,
                                        ref$ObjectRef2.element,
                                        ref$ObjectRef3.element,
                                        composerImpl3,
                                        8);
                                this.OtherSection(composerImpl3, 8);
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                384,
                2);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProvider$Page$5
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            NetworkCellularGroupProvider.this.Page(
                                    bundle,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final int getMetricsCategory() {
        return 1627;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "NetworkCellularGroupProvider";
    }
}
