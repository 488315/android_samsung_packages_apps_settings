package com.android.settings.spa.network;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.material.icons.automirrored.outlined.MessageKt;
import androidx.compose.material.icons.outlined.DataUsageKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.PathNode;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.res.VectorResources_androidKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;

import com.android.settings.R;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.network.telephony.TelephonyRepository;
import com.android.settings.network.telephony.TelephonyRepositoryKt;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spa.widget.ui.CategoryKt;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanDelegate;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalChangeFlowKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.internal.ContextScope;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NetworkCellularGroupProviderKt {
    public static final void CollectAirplaneModeAndFinishIfOn(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-550225950);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            KProperty[] kPropertyArr = SettingsGlobalBooleanKt.$$delegatedProperties;
            Intrinsics.checkNotNullParameter(context, "<this>");
            FlowsKt.collectLatestWithLifecycle(
                    FlowKt.distinctUntilChanged(
                            new SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1(
                                    SettingsGlobalChangeFlowKt.settingsGlobalChangeFlow(
                                            context, "airplane_mode_on", true),
                                    new SettingsGlobalBooleanDelegate(
                                            context, "airplane_mode_on", false))),
                    (LifecycleOwner)
                            composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner),
                    Lifecycle.State.STARTED,
                    new NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1(
                            context, null));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            NetworkCellularGroupProviderKt.CollectAirplaneModeAndFinishIfOn(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1, kotlin.jvm.internal.Lambda] */
    public static final void MobileDataSectionImpl(
            final MutableIntState mobileDataSelectedId,
            final MutableIntState nonDds,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(mobileDataSelectedId, "mobileDataSelectedId");
        Intrinsics.checkNotNullParameter(nonDds, "nonDds");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1331320722);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(mobileDataSelectedId) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(nonDds) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            final WifiPickerTrackerHelper wifiPickerTrackerHelper =
                    new WifiPickerTrackerHelper(
                            new LifecycleRegistry(
                                    (LifecycleOwner)
                                            composerImpl.consume(
                                                    LocalLifecycleOwnerKt.LocalLifecycleOwner)),
                            context,
                            null);
            final SubscriptionManager subscriptionManager =
                    (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
            CategoryKt.Category(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.mobile_data_settings_title),
                    ComposableLambdaKt.rememberComposableLambda(
                            240961938,
                            new Function3() { // from class:
                                // com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    ColumnScope Category = (ColumnScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(Category, "$this$Category");
                                    if ((intValue & 81) == 16) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    int intValue2 =
                                            ((SnapshotMutableIntStateImpl) MutableIntState.this)
                                                    .getIntValue();
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(-2060392795);
                                    boolean changed = composerImpl3.changed(intValue2);
                                    Context context2 = context;
                                    MutableIntState mutableIntState = MutableIntState.this;
                                    Object rememberedValue = composerImpl3.rememberedValue();
                                    Composer$Companion$Empty$1 composer$Companion$Empty$1 =
                                            Composer.Companion.Empty;
                                    if (changed || rememberedValue == composer$Companion$Empty$1) {
                                        final Flow subscriptionsChangedFlow =
                                                SubscriptionRepositoryKt.subscriptionsChangedFlow(
                                                        context2);
                                        Intrinsics.checkNotNullParameter(
                                                subscriptionsChangedFlow,
                                                "subscriptionsChangedFlow");
                                        final int intValue3 =
                                                ((SnapshotMutableIntStateImpl) mutableIntState)
                                                        .getIntValue();
                                        if (SubscriptionManager.isValidSubscriptionId(intValue3)) {
                                            final TelephonyManager telephonyManager =
                                                    TelephonyRepositoryKt.telephonyManager(
                                                            context2, intValue3);
                                            rememberedValue =
                                                    FlowKt.flowOn(
                                                            FlowKt.buffer$default(
                                                                    new Flow() { // from class:
                                                                        // com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1
                                                                        public final /* synthetic */
                                                                        int $policy$inlined = 3;

                                                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                                        /* renamed from: com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1$2, reason: invalid class name */
                                                                        public final
                                                                        class AnonymousClass2
                                                                                implements FlowCollector {
                                                                            public
                                                                            final /* synthetic */
                                                                            int $policy$inlined;
                                                                            public
                                                                            final /* synthetic */
                                                                            int $subId$inlined;
                                                                            public
                                                                            final /* synthetic */
                                                                            TelephonyManager
                                                                                    $telephonyManager$inlined;
                                                                            public
                                                                            final /* synthetic */
                                                                            FlowCollector
                                                                                    $this_unsafeFlow;

                                                                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                                            @Metadata(
                                                                                    k = 3,
                                                                                    mv = {1, 9, 0},
                                                                                    xi = 48)
                                                                            /* renamed from: com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1$2$1, reason: invalid class name */
                                                                            public final
                                                                            class AnonymousClass1
                                                                                    extends ContinuationImpl {
                                                                                Object L$0;
                                                                                int label;
                                                                                /* synthetic */ Object
                                                                                        result;

                                                                                public
                                                                                AnonymousClass1(
                                                                                        Continuation
                                                                                                continuation) {
                                                                                    super(
                                                                                            continuation);
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Object
                                                                                        invokeSuspend(
                                                                                                Object
                                                                                                        obj) {
                                                                                    this.result =
                                                                                            obj;
                                                                                    this.label |=
                                                                                            Integer
                                                                                                    .MIN_VALUE;
                                                                                    return AnonymousClass2
                                                                                            .this
                                                                                            .emit(
                                                                                                    null,
                                                                                                    this);
                                                                                }
                                                                            }

                                                                            public AnonymousClass2(
                                                                                    FlowCollector
                                                                                            flowCollector,
                                                                                    TelephonyManager
                                                                                            telephonyManager,
                                                                                    int i,
                                                                                    int i2) {
                                                                                this
                                                                                                .$this_unsafeFlow =
                                                                                        flowCollector;
                                                                                this
                                                                                                .$telephonyManager$inlined =
                                                                                        telephonyManager;
                                                                                this
                                                                                                .$policy$inlined =
                                                                                        i;
                                                                                this
                                                                                                .$subId$inlined =
                                                                                        i2;
                                                                            }

                                                                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                                                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                                                            @Override // kotlinx.coroutines.flow.FlowCollector
                                                                            /*
                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                                            */
                                                                            public final java.lang
                                                                                            .Object
                                                                                    emit(
                                                                                            java
                                                                                                            .lang
                                                                                                            .Object
                                                                                                    r7,
                                                                                            kotlin
                                                                                                            .coroutines
                                                                                                            .Continuation
                                                                                                    r8) {
                                                                                /*
                                                                                    r6 = this;
                                                                                    boolean r0 = r8 instanceof com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                                                    if (r0 == 0) goto L13
                                                                                    r0 = r8
                                                                                    com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                                                    int r1 = r0.label
                                                                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                                                    r3 = r1 & r2
                                                                                    if (r3 == 0) goto L13
                                                                                    int r1 = r1 - r2
                                                                                    r0.label = r1
                                                                                    goto L18
                                                                                L13:
                                                                                    com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1$2$1
                                                                                    r0.<init>(r8)
                                                                                L18:
                                                                                    java.lang.Object r8 = r0.result
                                                                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                                                    int r2 = r0.label
                                                                                    r3 = 1
                                                                                    if (r2 == 0) goto L2f
                                                                                    if (r2 != r3) goto L27
                                                                                    kotlin.ResultKt.throwOnFailure(r8)
                                                                                    goto L69
                                                                                L27:
                                                                                    java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                                                                    java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                                                                    r6.<init>(r7)
                                                                                    throw r6
                                                                                L2f:
                                                                                    kotlin.ResultKt.throwOnFailure(r8)
                                                                                    kotlin.Unit r7 = (kotlin.Unit) r7
                                                                                    android.telephony.TelephonyManager r7 = r6.$telephonyManager$inlined
                                                                                    int r8 = r6.$policy$inlined
                                                                                    boolean r7 = r7.isMobileDataPolicyEnabled(r8)
                                                                                    java.lang.Boolean r2 = java.lang.Boolean.valueOf(r7)
                                                                                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                                                                                    java.lang.String r5 = "["
                                                                                    r4.<init>(r5)
                                                                                    int r5 = r6.$subId$inlined
                                                                                    r4.append(r5)
                                                                                    java.lang.String r5 = "] isMobileDataPolicyEnabled("
                                                                                    r4.append(r5)
                                                                                    r4.append(r8)
                                                                                    java.lang.String r8 = "): "
                                                                                    r4.append(r8)
                                                                                    java.lang.String r8 = "TelephonyRepository"
                                                                                    androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0.m(r4, r7, r8)
                                                                                    r0.label = r3
                                                                                    kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                                                                    java.lang.Object r6 = r6.emit(r2, r0)
                                                                                    if (r6 != r1) goto L69
                                                                                    return r1
                                                                                L69:
                                                                                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                                                                    return r6
                                                                                */
                                                                                throw new UnsupportedOperationException(
                                                                                        "Method not"
                                                                                            + " decompiled:"
                                                                                            + " com.android.settings.network.telephony.TelephonyRepository$isMobileDataPolicyEnabledFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                                                            + " kotlin.coroutines.Continuation):java.lang.Object");
                                                                            }
                                                                        }

                                                                        @Override // kotlinx.coroutines.flow.Flow
                                                                        public final Object collect(
                                                                                FlowCollector
                                                                                        flowCollector,
                                                                                Continuation
                                                                                        continuation) {
                                                                            Object collect =
                                                                                    Flow.this
                                                                                            .collect(
                                                                                                    new AnonymousClass2(
                                                                                                            flowCollector,
                                                                                                            telephonyManager,
                                                                                                            this
                                                                                                                    .$policy$inlined,
                                                                                                            intValue3),
                                                                                                    continuation);
                                                                            return collect
                                                                                            == CoroutineSingletons
                                                                                                    .COROUTINE_SUSPENDED
                                                                                    ? collect
                                                                                    : Unit.INSTANCE;
                                                                        }
                                                                    },
                                                                    -1),
                                                            Dispatchers.Default);
                                        } else {
                                            rememberedValue =
                                                    new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(
                                                            Boolean.FALSE);
                                        }
                                        composerImpl3.updateRememberedValue(rememberedValue);
                                    }
                                    composerImpl3.end(false);
                                    final MutableState collectAsStateWithLifecycle =
                                            FlowExtKt.collectAsStateWithLifecycle(
                                                    (Flow) rememberedValue,
                                                    null,
                                                    composerImpl3,
                                                    56);
                                    int intValue4 =
                                            ((SnapshotMutableIntStateImpl) mobileDataSelectedId)
                                                    .getIntValue();
                                    composerImpl3.startReplaceGroup(-2060392464);
                                    boolean changed2 = composerImpl3.changed(intValue4);
                                    Context context3 = context;
                                    MutableIntState mutableIntState2 = mobileDataSelectedId;
                                    Object rememberedValue2 = composerImpl3.rememberedValue();
                                    if (changed2
                                            || rememberedValue2 == composer$Companion$Empty$1) {
                                        rememberedValue2 =
                                                new TelephonyRepository(context3)
                                                        .isDataEnabledFlow(
                                                                ((SnapshotMutableIntStateImpl)
                                                                                mutableIntState2)
                                                                        .getIntValue());
                                        composerImpl3.updateRememberedValue(rememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    final MutableState collectAsStateWithLifecycle2 =
                                            FlowExtKt.collectAsStateWithLifecycle(
                                                    (Flow) rememberedValue2,
                                                    Boolean.FALSE,
                                                    composerImpl3,
                                                    56);
                                    Object rememberedValue3 = composerImpl3.rememberedValue();
                                    if (rememberedValue3 == composer$Companion$Empty$1) {
                                        rememberedValue3 =
                                                PredictiveBackHandlerKt$$ExternalSyntheticOutline0
                                                        .m(
                                                                EffectsKt
                                                                        .createCompositionCoroutineScope(
                                                                                EmptyCoroutineContext
                                                                                        .INSTANCE,
                                                                                composerImpl3),
                                                                composerImpl3);
                                    }
                                    CoroutineScope coroutineScope =
                                            ((CompositionScopedCoroutineScopeCanceller)
                                                            rememberedValue3)
                                                    .coroutineScope;
                                    composerImpl3.startReplaceGroup(-2060392144);
                                    boolean changed3 =
                                            composerImpl3.changed(collectAsStateWithLifecycle2);
                                    Object rememberedValue4 = composerImpl3.rememberedValue();
                                    if (changed3
                                            || rememberedValue4 == composer$Companion$Empty$1) {
                                        rememberedValue4 =
                                                new Function0() { // from class:
                                                    // com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        Boolean bool =
                                                                (Boolean)
                                                                        collectAsStateWithLifecycle2
                                                                                .getValue();
                                                        bool.booleanValue();
                                                        return bool;
                                                    }
                                                };
                                        composerImpl3.updateRememberedValue(rememberedValue4);
                                    }
                                    composerImpl3.end(false);
                                    final Context context4 = context;
                                    final SubscriptionManager subscriptionManager2 =
                                            subscriptionManager;
                                    final WifiPickerTrackerHelper wifiPickerTrackerHelper2 =
                                            wifiPickerTrackerHelper;
                                    final MutableIntState mutableIntState3 = mobileDataSelectedId;
                                    final ContextScope contextScope = (ContextScope) coroutineScope;
                                    MobileDataSwitchingPreferenceKt.MobileDataSwitchingPreference(
                                            (Function0) rememberedValue4,
                                            new Function1() { // from class:
                                                // com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1.2

                                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                @Metadata(
                                                        d1 = {
                                                            "\u0000\n\n"
                                                                + "\u0000\n"
                                                                + "\u0002\u0010\u0002\n"
                                                                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                                                        },
                                                        d2 = {
                                                            "<anonymous>",
                                                            ApnSettings.MVNO_NONE,
                                                            "Lkotlinx/coroutines/CoroutineScope;"
                                                        },
                                                        k = 3,
                                                        mv = {1, 9, 0},
                                                        xi = 48)
                                                /* renamed from: com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1$2$1, reason: invalid class name */
                                                final class AnonymousClass1 extends SuspendLambda
                                                        implements Function2 {
                                                    final /* synthetic */ Context $context;
                                                    final /* synthetic */ MutableIntState
                                                            $mobileDataSelectedId;
                                                    final /* synthetic */ boolean $newEnabled;
                                                    final /* synthetic */ SubscriptionManager
                                                            $subscriptionManager;
                                                    final /* synthetic */ WifiPickerTrackerHelper
                                                            $wifiPickerTrackerHelper;
                                                    int label;

                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    public AnonymousClass1(
                                                            Context context,
                                                            SubscriptionManager subscriptionManager,
                                                            WifiPickerTrackerHelper
                                                                    wifiPickerTrackerHelper,
                                                            MutableIntState mutableIntState,
                                                            boolean z,
                                                            Continuation continuation) {
                                                        super(2, continuation);
                                                        this.$context = context;
                                                        this.$subscriptionManager =
                                                                subscriptionManager;
                                                        this.$wifiPickerTrackerHelper =
                                                                wifiPickerTrackerHelper;
                                                        this.$mobileDataSelectedId =
                                                                mutableIntState;
                                                        this.$newEnabled = z;
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Continuation create(
                                                            Object obj, Continuation continuation) {
                                                        return new AnonymousClass1(
                                                                this.$context,
                                                                this.$subscriptionManager,
                                                                this.$wifiPickerTrackerHelper,
                                                                this.$mobileDataSelectedId,
                                                                this.$newEnabled,
                                                                continuation);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(
                                                            Object obj, Object obj2) {
                                                        return ((AnonymousClass1)
                                                                        create(
                                                                                (CoroutineScope)
                                                                                        obj,
                                                                                (Continuation)
                                                                                        obj2))
                                                                .invokeSuspend(Unit.INSTANCE);
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Object invokeSuspend(Object obj) {
                                                        CoroutineSingletons coroutineSingletons =
                                                                CoroutineSingletons
                                                                        .COROUTINE_SUSPENDED;
                                                        int i = this.label;
                                                        Unit unit = Unit.INSTANCE;
                                                        if (i == 0) {
                                                            ResultKt.throwOnFailure(obj);
                                                            Context context = this.$context;
                                                            SubscriptionManager
                                                                    subscriptionManager =
                                                                            this
                                                                                    .$subscriptionManager;
                                                            WifiPickerTrackerHelper
                                                                    wifiPickerTrackerHelper =
                                                                            this
                                                                                    .$wifiPickerTrackerHelper;
                                                            int intValue =
                                                                    ((SnapshotMutableIntStateImpl)
                                                                                    this
                                                                                            .$mobileDataSelectedId)
                                                                            .getIntValue();
                                                            boolean z = this.$newEnabled;
                                                            this.label = 1;
                                                            Object withContext =
                                                                    BuildersKt.withContext(
                                                                            Dispatchers.Default,
                                                                            new NetworkCellularGroupProviderKt$setMobileData$2(
                                                                                    intValue,
                                                                                    z,
                                                                                    subscriptionManager,
                                                                                    context,
                                                                                    wifiPickerTrackerHelper,
                                                                                    null),
                                                                            this);
                                                            if (withContext
                                                                    != coroutineSingletons) {
                                                                withContext = unit;
                                                            }
                                                            if (withContext
                                                                    == coroutineSingletons) {
                                                                return coroutineSingletons;
                                                            }
                                                        } else {
                                                            if (i != 1) {
                                                                throw new IllegalStateException(
                                                                        "call to 'resume' before"
                                                                            + " 'invoke' with"
                                                                            + " coroutine");
                                                            }
                                                            ResultKt.throwOnFailure(obj);
                                                        }
                                                        return unit;
                                                    }
                                                }

                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj4) {
                                                    BuildersKt.launch$default(
                                                            contextScope,
                                                            null,
                                                            null,
                                                            new AnonymousClass1(
                                                                    context4,
                                                                    subscriptionManager2,
                                                                    wifiPickerTrackerHelper2,
                                                                    mutableIntState3,
                                                                    ((Boolean) obj4).booleanValue(),
                                                                    null),
                                                            3);
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl3,
                                            0);
                                    if (((SnapshotMutableIntStateImpl) MutableIntState.this)
                                                    .getIntValue()
                                            != -1) {
                                        composerImpl3.startReplaceGroup(-2060391549);
                                        boolean changed4 =
                                                composerImpl3.changed(collectAsStateWithLifecycle);
                                        Object rememberedValue5 = composerImpl3.rememberedValue();
                                        if (changed4
                                                || rememberedValue5 == composer$Companion$Empty$1) {
                                            rememberedValue5 =
                                                    new Function0() { // from class:
                                                        // com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1$3$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        /* renamed from: invoke */
                                                        public final Object mo1068invoke() {
                                                            return (Boolean)
                                                                    collectAsStateWithLifecycle
                                                                            .getValue();
                                                        }
                                                    };
                                            composerImpl3.updateRememberedValue(rememberedValue5);
                                        }
                                        composerImpl3.end(false);
                                        final Context context5 = context;
                                        final MutableIntState mutableIntState4 =
                                                MutableIntState.this;
                                        AutomaticDataSwitchingPreferenceKt
                                                .AutomaticDataSwitchingPreference(
                                                        (Function0) rememberedValue5,
                                                        new Function1() { // from class:
                                                            // com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$1.4
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            public final Object invoke(
                                                                    Object obj4) {
                                                                boolean booleanValue =
                                                                        ((Boolean) obj4)
                                                                                .booleanValue();
                                                                Context context6 = context5;
                                                                Flow subscriptionsChangedFlow2 =
                                                                        SubscriptionRepositoryKt
                                                                                .subscriptionsChangedFlow(
                                                                                        context6);
                                                                Intrinsics.checkNotNullParameter(
                                                                        subscriptionsChangedFlow2,
                                                                        "subscriptionsChangedFlow");
                                                                int intValue5 =
                                                                        ((SnapshotMutableIntStateImpl)
                                                                                        mutableIntState4)
                                                                                .getIntValue();
                                                                if (SubscriptionManager
                                                                        .isValidSubscriptionId(
                                                                                intValue5)) {
                                                                    TelephonyManager
                                                                            telephonyManager2 =
                                                                                    TelephonyRepositoryKt
                                                                                            .telephonyManager(
                                                                                                    context6,
                                                                                                    intValue5);
                                                                    Log.d(
                                                                            "TelephonyRepository",
                                                                            "["
                                                                                    + intValue5
                                                                                    + "] setMobileDataPolicyEnabled(3):"
                                                                                    + " "
                                                                                    + booleanValue);
                                                                    telephonyManager2
                                                                            .setMobileDataPolicyEnabled(
                                                                                    3,
                                                                                    booleanValue);
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        },
                                                        composerImpl3,
                                                        0);
                                    }
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    48);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$MobileDataSectionImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            NetworkCellularGroupProviderKt.MobileDataSectionImpl(
                                    MutableIntState.this,
                                    nonDds,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void PrimarySimImpl(
            final PrimarySimRepository.PrimarySimInfo primarySimInfo,
            final MutableIntState callsSelectedId,
            final MutableIntState textsSelectedId,
            final MutableIntState mobileDataSelectedId,
            WifiPickerTrackerHelper wifiPickerTrackerHelper,
            SubscriptionManager subscriptionManager,
            CoroutineScope coroutineScope,
            Context context,
            Function1 function1,
            Function1 function12,
            Function1 function13,
            Composer composer,
            final int i,
            final int i2,
            final int i3) {
        final SubscriptionManager subscriptionManager2;
        int i4;
        final CoroutineScope coroutineScope2;
        final Context context2;
        Function1 function14;
        Function1 function15;
        Function1 function16;
        int i5;
        Context context3;
        Function1 function17;
        CoroutineScope coroutineScope3;
        SubscriptionManager subscriptionManager3;
        Intrinsics.checkNotNullParameter(primarySimInfo, "primarySimInfo");
        Intrinsics.checkNotNullParameter(callsSelectedId, "callsSelectedId");
        Intrinsics.checkNotNullParameter(textsSelectedId, "textsSelectedId");
        Intrinsics.checkNotNullParameter(mobileDataSelectedId, "mobileDataSelectedId");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-781535718);
        final WifiPickerTrackerHelper wifiPickerTrackerHelper2 =
                (i3 & 16) != 0 ? null : wifiPickerTrackerHelper;
        if ((i3 & 32) != 0) {
            subscriptionManager2 =
                    (SubscriptionManager)
                            ((Context)
                                            composerImpl.consume(
                                                    AndroidCompositionLocals_androidKt
                                                            .LocalContext))
                                    .getSystemService(SubscriptionManager.class);
            i4 = i & (-458753);
        } else {
            subscriptionManager2 = subscriptionManager;
            i4 = i;
        }
        if ((i3 & 64) != 0) {
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                                EffectsKt.createCompositionCoroutineScope(
                                        EmptyCoroutineContext.INSTANCE, composerImpl),
                                composerImpl);
            }
            coroutineScope2 =
                    ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
            i4 &= -3670017;
        } else {
            coroutineScope2 = coroutineScope;
        }
        if ((i3 & 128) != 0) {
            context2 =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            i4 &= -29360129;
        } else {
            context2 = context;
        }
        if ((i3 & 256) != 0) {
            function14 =
                    new Function1() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$1

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            final /* synthetic */ int $it;
                            final /* synthetic */ SubscriptionManager $subscriptionManager;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    SubscriptionManager subscriptionManager,
                                    int i,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.$subscriptionManager = subscriptionManager;
                                this.$it = i;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(
                                        this.$subscriptionManager, this.$it, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2))
                                        .invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    SubscriptionManager subscriptionManager =
                                            this.$subscriptionManager;
                                    int i2 = this.$it;
                                    this.label = 1;
                                    if (NetworkCellularGroupProviderKt.setDefaultVoice(
                                                    subscriptionManager, i2, this)
                                            == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(
                                                "call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            int intValue = ((Number) obj).intValue();
                            ((SnapshotMutableIntStateImpl) MutableIntState.this)
                                    .setIntValue(intValue);
                            BuildersKt.launch$default(
                                    coroutineScope2,
                                    null,
                                    null,
                                    new AnonymousClass1(subscriptionManager2, intValue, null),
                                    3);
                            return Unit.INSTANCE;
                        }
                    };
            i4 &= -234881025;
        } else {
            function14 = function1;
        }
        if ((i3 & 512) != 0) {
            function15 =
                    new Function1() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$2

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$2$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            final /* synthetic */ int $it;
                            final /* synthetic */ SubscriptionManager $subscriptionManager;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    SubscriptionManager subscriptionManager,
                                    int i,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.$subscriptionManager = subscriptionManager;
                                this.$it = i;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(
                                        this.$subscriptionManager, this.$it, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2))
                                        .invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    SubscriptionManager subscriptionManager =
                                            this.$subscriptionManager;
                                    int i2 = this.$it;
                                    this.label = 1;
                                    if (NetworkCellularGroupProviderKt.setDefaultSms(
                                                    subscriptionManager, i2, this)
                                            == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(
                                                "call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            int intValue = ((Number) obj).intValue();
                            ((SnapshotMutableIntStateImpl) MutableIntState.this)
                                    .setIntValue(intValue);
                            BuildersKt.launch$default(
                                    coroutineScope2,
                                    null,
                                    null,
                                    new AnonymousClass1(subscriptionManager2, intValue, null),
                                    3);
                            return Unit.INSTANCE;
                        }
                    };
            i4 &= -1879048193;
        } else {
            function15 = function12;
        }
        if ((i3 & 1024) != 0) {
            function16 =
                    new Function1() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$3

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$3$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            final /* synthetic */ Context $context;
                            final /* synthetic */ int $it;
                            final /* synthetic */ SubscriptionManager $subscriptionManager;
                            final /* synthetic */ WifiPickerTrackerHelper $wifiPickerTrackerHelper;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    Context context,
                                    SubscriptionManager subscriptionManager,
                                    WifiPickerTrackerHelper wifiPickerTrackerHelper,
                                    int i,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.$context = context;
                                this.$subscriptionManager = subscriptionManager;
                                this.$wifiPickerTrackerHelper = wifiPickerTrackerHelper;
                                this.$it = i;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(
                                        this.$context,
                                        this.$subscriptionManager,
                                        this.$wifiPickerTrackerHelper,
                                        this.$it,
                                        continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2))
                                        .invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                Unit unit = Unit.INSTANCE;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    Context context = this.$context;
                                    SubscriptionManager subscriptionManager =
                                            this.$subscriptionManager;
                                    WifiPickerTrackerHelper wifiPickerTrackerHelper =
                                            this.$wifiPickerTrackerHelper;
                                    int i2 = this.$it;
                                    this.label = 1;
                                    Object withContext =
                                            BuildersKt.withContext(
                                                    Dispatchers.Default,
                                                    new NetworkCellularGroupProviderKt$setMobileData$2(
                                                            i2,
                                                            true,
                                                            subscriptionManager,
                                                            context,
                                                            wifiPickerTrackerHelper,
                                                            null),
                                                    this);
                                    if (withContext != coroutineSingletons) {
                                        withContext = unit;
                                    }
                                    if (withContext != coroutineSingletons) {
                                        withContext = unit;
                                    }
                                    if (withContext == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(
                                                "call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                return unit;
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            BuildersKt.launch$default(
                                    CoroutineScope.this,
                                    null,
                                    null,
                                    new AnonymousClass1(
                                            context2,
                                            subscriptionManager2,
                                            wifiPickerTrackerHelper2,
                                            ((Number) obj).intValue(),
                                            null),
                                    3);
                            return Unit.INSTANCE;
                        }
                    };
            i5 = i2 & (-15);
        } else {
            function16 = function13;
            i5 = i2;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SimOnboardingPrimarySimKt.CreatePrimarySimListPreference(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.primary_sim_calls_title),
                primarySimInfo.callsAndSmsList,
                callsSelectedId,
                VectorResources_androidKt.vectorResource(composerImpl, R.drawable.ic_phone),
                function14,
                composerImpl,
                ((i4 << 3) & 896) | 64 | ((i4 >> 12) & 57344));
        String stringResource =
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.primary_sim_texts_title);
        List list = primarySimInfo.callsAndSmsList;
        ImageVector imageVector = MessageKt._message;
        if (imageVector != null) {
            subscriptionManager3 = subscriptionManager2;
            coroutineScope3 = coroutineScope2;
            context3 = context2;
            function17 = function14;
        } else {
            ImageVector.Builder builder =
                    new ImageVector.Builder(
                            "AutoMirrored.Outlined.Message",
                            24.0f,
                            24.0f,
                            24.0f,
                            24.0f,
                            0L,
                            0,
                            true,
                            96);
            EmptyList emptyList = VectorKt.EmptyPath;
            context3 = context2;
            function17 = function14;
            SolidColor solidColor = new SolidColor(Color.Black);
            PathBuilder pathBuilder = new PathBuilder();
            pathBuilder.moveTo(4.0f, 4.0f);
            pathBuilder.horizontalLineToRelative(16.0f);
            pathBuilder.verticalLineToRelative(12.0f);
            pathBuilder.lineTo(5.17f, 16.0f);
            pathBuilder.lineTo(4.0f, 17.17f);
            pathBuilder.lineTo(4.0f, 4.0f);
            coroutineScope3 = coroutineScope2;
            subscriptionManager3 = subscriptionManager2;
            pathBuilder._nodes.add(new PathNode.RelativeMoveTo(0.0f, -2.0f));
            pathBuilder.curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f);
            pathBuilder.lineTo(2.0f, 22.0f);
            pathBuilder.lineToRelative(4.0f, -4.0f);
            pathBuilder.horizontalLineToRelative(14.0f);
            pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
            pathBuilder.lineTo(22.0f, 4.0f);
            pathBuilder.curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f);
            pathBuilder.lineTo(4.0f, 2.0f);
            pathBuilder.close();
            pathBuilder.moveTo(6.0f, 12.0f);
            pathBuilder.horizontalLineToRelative(12.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.lineTo(6.0f, 14.0f);
            pathBuilder.verticalLineToRelative(-2.0f);
            pathBuilder.close();
            pathBuilder.moveTo(6.0f, 9.0f);
            pathBuilder.horizontalLineToRelative(12.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.lineTo(6.0f, 11.0f);
            pathBuilder.lineTo(6.0f, 9.0f);
            pathBuilder.close();
            pathBuilder.moveTo(6.0f, 6.0f);
            pathBuilder.horizontalLineToRelative(12.0f);
            pathBuilder.verticalLineToRelative(2.0f);
            pathBuilder.lineTo(6.0f, 8.0f);
            pathBuilder.lineTo(6.0f, 6.0f);
            pathBuilder.close();
            ImageVector.Builder.m390addPathoIyEayM$default(builder, pathBuilder._nodes, solidColor);
            imageVector = builder.build();
            MessageKt._message = imageVector;
        }
        SimOnboardingPrimarySimKt.CreatePrimarySimListPreference(
                stringResource,
                list,
                textsSelectedId,
                imageVector,
                function15,
                composerImpl,
                (i4 & 896) | 64 | ((i4 >> 15) & 57344));
        String stringResource2 =
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.mobile_data_settings_title);
        List list2 = primarySimInfo.dataList;
        ImageVector imageVector2 = DataUsageKt._dataUsage;
        if (imageVector2 == null) {
            ImageVector.Builder builder2 =
                    new ImageVector.Builder(
                            "Outlined.DataUsage", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96);
            EmptyList emptyList2 = VectorKt.EmptyPath;
            SolidColor solidColor2 = new SolidColor(Color.Black);
            PathBuilder pathBuilder2 = new PathBuilder();
            pathBuilder2.moveTo(13.0f, 2.05f);
            pathBuilder2.verticalLineToRelative(3.03f);
            pathBuilder2.curveToRelative(3.39f, 0.49f, 6.0f, 3.39f, 6.0f, 6.92f);
            pathBuilder2.curveToRelative(0.0f, 0.9f, -0.18f, 1.75f, -0.48f, 2.54f);
            pathBuilder2.lineToRelative(2.6f, 1.53f);
            pathBuilder2.curveToRelative(0.56f, -1.24f, 0.88f, -2.62f, 0.88f, -4.07f);
            pathBuilder2.curveToRelative(0.0f, -5.18f, -3.95f, -9.45f, -9.0f, -9.95f);
            pathBuilder2.close();
            pathBuilder2.moveTo(12.0f, 19.0f);
            pathBuilder2.curveToRelative(-3.87f, 0.0f, -7.0f, -3.13f, -7.0f, -7.0f);
            pathBuilder2.curveToRelative(0.0f, -3.53f, 2.61f, -6.43f, 6.0f, -6.92f);
            pathBuilder2.verticalLineTo(2.05f);
            pathBuilder2.curveToRelative(-5.06f, 0.5f, -9.0f, 4.76f, -9.0f, 9.95f);
            pathBuilder2.curveToRelative(0.0f, 5.52f, 4.47f, 10.0f, 9.99f, 10.0f);
            pathBuilder2.curveToRelative(3.31f, 0.0f, 6.24f, -1.61f, 8.06f, -4.09f);
            pathBuilder2.lineToRelative(-2.6f, -1.53f);
            pathBuilder2.curveTo(16.17f, 17.98f, 14.21f, 19.0f, 12.0f, 19.0f);
            pathBuilder2.close();
            ImageVector.Builder.m390addPathoIyEayM$default(
                    builder2, pathBuilder2._nodes, solidColor2);
            imageVector2 = builder2.build();
            DataUsageKt._dataUsage = imageVector2;
        }
        SimOnboardingPrimarySimKt.CreatePrimarySimListPreference(
                stringResource2,
                list2,
                mobileDataSelectedId,
                imageVector2,
                function16,
                composerImpl,
                ((i4 >> 3) & 896) | 64 | ((i5 << 12) & 57344));
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final SubscriptionManager subscriptionManager4 = subscriptionManager3;
            final CoroutineScope coroutineScope4 = coroutineScope3;
            final Context context4 = context3;
            final Function1 function18 = function17;
            final Function1 function19 = function15;
            final Function1 function110 = function16;
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimImpl$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            NetworkCellularGroupProviderKt.PrimarySimImpl(
                                    PrimarySimRepository.PrimarySimInfo.this,
                                    callsSelectedId,
                                    textsSelectedId,
                                    mobileDataSelectedId,
                                    wifiPickerTrackerHelper2,
                                    subscriptionManager4,
                                    coroutineScope4,
                                    context4,
                                    function18,
                                    function19,
                                    function110,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    RecomposeScopeImplKt.updateChangedFlags(i2),
                                    i3);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$1, kotlin.jvm.internal.Lambda] */
    public static final void PrimarySimSectionImpl(
            final Flow subscriptionInfoListFlow,
            final MutableIntState callsSelectedId,
            final MutableIntState textsSelectedId,
            final MutableIntState mobileDataSelectedId,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(subscriptionInfoListFlow, "subscriptionInfoListFlow");
        Intrinsics.checkNotNullParameter(callsSelectedId, "callsSelectedId");
        Intrinsics.checkNotNullParameter(textsSelectedId, "textsSelectedId");
        Intrinsics.checkNotNullParameter(mobileDataSelectedId, "mobileDataSelectedId");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(257671789);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final WifiPickerTrackerHelper wifiPickerTrackerHelper =
                new WifiPickerTrackerHelper(
                        new LifecycleRegistry(
                                (LifecycleOwner)
                                        composerImpl.consume(
                                                LocalLifecycleOwnerKt.LocalLifecycleOwner)),
                        context,
                        null);
        composerImpl.startReplaceGroup(1604895133);
        boolean changed = composerImpl.changed(subscriptionInfoListFlow);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            final NetworkCellularGroupProviderKt$defaultSmsSubscriptionFlow$$inlined$map$1
                    networkCellularGroupProviderKt$defaultSmsSubscriptionFlow$$inlined$map$1 =
                            new NetworkCellularGroupProviderKt$defaultSmsSubscriptionFlow$$inlined$map$1(
                                    subscriptionInfoListFlow, 1);
            final PrimarySimRepository primarySimRepository = new PrimarySimRepository(context);
            rememberedValue =
                    FlowKt.flowOn(
                            new Flow() { // from class:
                                // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                /* renamed from: com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                    public final /* synthetic */ PrimarySimRepository
                                            receiver$inlined;

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    @Metadata(
                                            k = 3,
                                            mv = {1, 9, 0},
                                            xi = 48)
                                    /* renamed from: com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2$2$1, reason: invalid class name */
                                    public final class AnonymousClass1 extends ContinuationImpl {
                                        Object L$0;
                                        int label;
                                        /* synthetic */ Object result;

                                        public AnonymousClass1(Continuation continuation) {
                                            super(continuation);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            this.result = obj;
                                            this.label |= Integer.MIN_VALUE;
                                            return AnonymousClass2.this.emit(null, this);
                                        }
                                    }

                                    public AnonymousClass2(
                                            FlowCollector flowCollector,
                                            PrimarySimRepository primarySimRepository) {
                                        this.$this_unsafeFlow = flowCollector;
                                        this.receiver$inlined = primarySimRepository;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                    @Override // kotlinx.coroutines.flow.FlowCollector
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final java.lang.Object emit(
                                            java.lang.Object r5,
                                            kotlin.coroutines.Continuation r6) {
                                        /*
                                            r4 = this;
                                            boolean r0 = r6 instanceof com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2.AnonymousClass2.AnonymousClass1
                                            if (r0 == 0) goto L13
                                            r0 = r6
                                            com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2$2$1 r0 = (com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                                            int r1 = r0.label
                                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                            r3 = r1 & r2
                                            if (r3 == 0) goto L13
                                            int r1 = r1 - r2
                                            r0.label = r1
                                            goto L18
                                        L13:
                                            com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2$2$1 r0 = new com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2$2$1
                                            r0.<init>(r6)
                                        L18:
                                            java.lang.Object r6 = r0.result
                                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                            int r2 = r0.label
                                            r3 = 1
                                            if (r2 == 0) goto L2f
                                            if (r2 != r3) goto L27
                                            kotlin.ResultKt.throwOnFailure(r6)
                                            goto L45
                                        L27:
                                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                            r4.<init>(r5)
                                            throw r4
                                        L2f:
                                            kotlin.ResultKt.throwOnFailure(r6)
                                            java.util.List r5 = (java.util.List) r5
                                            com.android.settings.spa.network.PrimarySimRepository r6 = r4.receiver$inlined
                                            com.android.settings.spa.network.PrimarySimRepository$PrimarySimInfo r5 = r6.getPrimarySimInfo(r5)
                                            r0.label = r3
                                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                            java.lang.Object r4 = r4.emit(r5, r0)
                                            if (r4 != r1) goto L45
                                            return r1
                                        L45:
                                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                            return r4
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$lambda$2$$inlined$map$2.AnonymousClass2.emit(java.lang.Object,"
                                                    + " kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(
                                        FlowCollector flowCollector, Continuation continuation) {
                                    Object collect =
                                            networkCellularGroupProviderKt$defaultSmsSubscriptionFlow$$inlined$map$1
                                                    .collect(
                                                            new AnonymousClass2(
                                                                    flowCollector,
                                                                    primarySimRepository),
                                                            continuation);
                                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                            ? collect
                                            : Unit.INSTANCE;
                                }
                            },
                            Dispatchers.Default);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        final PrimarySimRepository.PrimarySimInfo primarySimInfo =
                (PrimarySimRepository.PrimarySimInfo)
                        FlowExtKt.collectAsStateWithLifecycle(
                                        (Flow) rememberedValue, null, composerImpl, 56)
                                .getValue();
        if (primarySimInfo == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$primarySimInfo$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                NetworkCellularGroupProviderKt.PrimarySimSectionImpl(
                                        Flow.this,
                                        callsSelectedId,
                                        textsSelectedId,
                                        mobileDataSelectedId,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        CategoryKt.Category(
                StringResources_androidKt.stringResource(composerImpl, R.string.primary_sim_title),
                ComposableLambdaKt.rememberComposableLambda(
                        1642168265,
                        new Function3() { // from class:
                                          // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                ColumnScope Category = (ColumnScope) obj;
                                Composer composer2 = (Composer) obj2;
                                int intValue = ((Number) obj3).intValue();
                                Intrinsics.checkNotNullParameter(Category, "$this$Category");
                                if ((intValue & 81) == 16) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                NetworkCellularGroupProviderKt.PrimarySimImpl(
                                        PrimarySimRepository.PrimarySimInfo.this,
                                        callsSelectedId,
                                        textsSelectedId,
                                        mobileDataSelectedId,
                                        wifiPickerTrackerHelper,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        composer2,
                                        32776,
                                        0,
                                        2016);
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                48);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.NetworkCellularGroupProviderKt$PrimarySimSectionImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            NetworkCellularGroupProviderKt.PrimarySimSectionImpl(
                                    Flow.this,
                                    callsSelectedId,
                                    textsSelectedId,
                                    mobileDataSelectedId,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final Object setDefaultData(
            Context context,
            SubscriptionManager subscriptionManager,
            WifiPickerTrackerHelper wifiPickerTrackerHelper,
            int i,
            Continuation continuation) {
        Object withContext =
                BuildersKt.withContext(
                        Dispatchers.Default,
                        new NetworkCellularGroupProviderKt$setMobileData$2(
                                i,
                                true,
                                subscriptionManager,
                                context,
                                wifiPickerTrackerHelper,
                                null),
                        continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        Unit unit = Unit.INSTANCE;
        if (withContext != coroutineSingletons) {
            withContext = unit;
        }
        return withContext == coroutineSingletons ? withContext : unit;
    }

    public static final Object setDefaultSms(
            SubscriptionManager subscriptionManager, int i, Continuation continuation) {
        Object withContext =
                BuildersKt.withContext(
                        Dispatchers.Default,
                        new NetworkCellularGroupProviderKt$setDefaultSms$2(
                                subscriptionManager, i, null),
                        continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public static final Object setDefaultVoice(
            SubscriptionManager subscriptionManager, int i, Continuation continuation) {
        Object withContext =
                BuildersKt.withContext(
                        Dispatchers.Default,
                        new NetworkCellularGroupProviderKt$setDefaultVoice$2(
                                subscriptionManager, i, null),
                        continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
