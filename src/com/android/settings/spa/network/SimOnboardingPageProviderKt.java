package com.android.settings.spa.network;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavHostController;
import androidx.navigation.compose.NavGraphBuilderKt;
import androidx.navigation.compose.NavHostKt;

import com.android.settings.network.SimOnboardingActivity$Companion$CallbackType;
import com.android.settings.network.SimOnboardingService;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.ui.TextKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimOnboardingPageProviderKt {
    /* JADX WARN: Type inference failed for: r3v0, types: [T, com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$finishOnboarding$1] */
    public static final void PageImpl(
            final SimOnboardingService onboardingService,
            final NavHostController navHostController,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(onboardingService, "onboardingService");
        Intrinsics.checkNotNullParameter(navHostController, "navHostController");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(614563256);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element =
                new Function0() { // from class:
                                  // com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$finishOnboarding$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Activity activity = SimOnboardingPageProviderKt.getActivity(context);
                        if (activity != null) {
                            activity.finish();
                        }
                        onboardingService.callback.invoke(
                                SimOnboardingActivity$Companion$CallbackType.CALLBACK_FINISH);
                        return Unit.INSTANCE;
                    }
                };
        NavHostKt.NavHost(
                navHostController,
                "LabelSim",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new Function1() { // from class:
                    // com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        NavGraphBuilder NavHost = (NavGraphBuilder) obj;
                        Intrinsics.checkNotNullParameter(NavHost, "$this$NavHost");
                        final SimOnboardingService simOnboardingService = SimOnboardingService.this;
                        final Ref$ObjectRef<Function0> ref$ObjectRef2 = ref$ObjectRef;
                        final NavHostController navHostController2 = navHostController;
                        NavGraphBuilderKt.composable$default(
                                NavHost,
                                "LabelSim",
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                new ComposableLambdaImpl(
                                        -906494181,
                                        true,
                                        new Function4() { // from class:
                                            // com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(4);
                                            }

                                            /* JADX WARN: Code restructure failed: missing block: B:25:0x0052, code lost:

                                               if (r3.activeSubInfoList.size() == r0) goto L17;
                                            */
                                            @Override // kotlin.jvm.functions.Function4
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                            */
                                            public final java.lang.Object invoke(
                                                    java.lang.Object r3,
                                                    java.lang.Object r4,
                                                    java.lang.Object r5,
                                                    java.lang.Object r6) {
                                                /*
                                                    r2 = this;
                                                    androidx.compose.animation.AnimatedContentScope r3 = (androidx.compose.animation.AnimatedContentScope) r3
                                                    androidx.navigation.NavBackStackEntry r4 = (androidx.navigation.NavBackStackEntry) r4
                                                    androidx.compose.runtime.Composer r5 = (androidx.compose.runtime.Composer) r5
                                                    java.lang.Number r6 = (java.lang.Number) r6
                                                    r6.intValue()
                                                    java.lang.String r6 = "$this$composable"
                                                    kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r6)
                                                    java.lang.String r3 = "it"
                                                    kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r3)
                                                    androidx.compose.runtime.OpaqueKey r3 = androidx.compose.runtime.ComposerKt.invocation
                                                    androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
                                                    r3 = -2056574389(0xffffffff856b2a4b, float:-1.1057415E-35)
                                                    r5.startReplaceGroup(r3)
                                                    com.android.settings.network.SimOnboardingService r3 = r2
                                                    boolean r3 = r3.isMultipleEnabledProfilesSupported()
                                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                    r6 = 0
                                                    if (r3 == 0) goto L6a
                                                    com.android.settings.network.SimOnboardingService r3 = r2
                                                    android.telephony.TelephonyManager r0 = r3.telephonyManager
                                                    if (r0 == 0) goto L35
                                                    int r0 = r0.getActiveModemCount()
                                                    goto L36
                                                L35:
                                                    r0 = r6
                                                L36:
                                                    if (r0 != 0) goto L40
                                                    java.lang.String r3 = "SimOnboardingService"
                                                    java.lang.String r0 = "isAllOfSlotAssigned: getActiveModemCount is 0"
                                                    android.util.Log.e(r3, r0)
                                                    goto L54
                                                L40:
                                                    android.telephony.TelephonyManager r1 = r3.telephonyManager
                                                    if (r1 == 0) goto L49
                                                    int r1 = r1.getActiveModemCount()
                                                    goto L4a
                                                L49:
                                                    r1 = r6
                                                L4a:
                                                    if (r1 == 0) goto L6a
                                                    java.util.List r3 = r3.activeSubInfoList
                                                    int r3 = r3.size()
                                                    if (r3 != r0) goto L6a
                                                L54:
                                                    com.android.settings.network.SimOnboardingService r3 = r2
                                                    android.telephony.SubscriptionInfo r3 = r3.targetSubInfo
                                                    if (r3 == 0) goto L5f
                                                    int r3 = r3.getSimSlotIndex()
                                                    goto L60
                                                L5f:
                                                    r3 = -1
                                                L60:
                                                    if (r3 < 0) goto L64
                                                    r3 = 1
                                                    goto L65
                                                L64:
                                                    r3 = r6
                                                L65:
                                                    if (r3 != 0) goto L6a
                                                    java.lang.String r3 = "SelectSim"
                                                    goto L77
                                                L6a:
                                                    com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1 r3 = new com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1$1$nextPage$1
                                                    com.android.settings.network.SimOnboardingService r0 = r2
                                                    r1 = 0
                                                    r3.<init>(r0, r1)
                                                    androidx.compose.runtime.EffectsKt.LaunchedEffect(r5, r4, r3)
                                                    java.lang.String r3 = "PrimarySim"
                                                L77:
                                                    r5.end(r6)
                                                    com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1$1$1 r6 = new com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1$1$1
                                                    androidx.navigation.NavHostController r0 = r1
                                                    r6.<init>()
                                                    kotlin.jvm.internal.Ref$ObjectRef<kotlin.jvm.functions.Function0> r3 = r3
                                                    T r3 = r3.element
                                                    kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                                                    com.android.settings.network.SimOnboardingService r2 = r2
                                                    r0 = 512(0x200, float:7.175E-43)
                                                    com.android.settings.spa.network.SimOnboardingLabelSimKt.SimOnboardingLabelSimImpl(r6, r3, r2, r5, r0)
                                                    return r4
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1.AnonymousClass1.invoke(java.lang.Object,"
                                                            + " java.lang.Object, java.lang.Object,"
                                                            + " java.lang.Object):java.lang.Object");
                                            }
                                        }),
                                254);
                        final Ref$ObjectRef<Function0> ref$ObjectRef3 = ref$ObjectRef;
                        final SimOnboardingService simOnboardingService2 =
                                SimOnboardingService.this;
                        final Context context2 = context;
                        NavGraphBuilderKt.composable$default(
                                NavHost,
                                "PrimarySim",
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                new ComposableLambdaImpl(
                                        -1842151982,
                                        true,
                                        new Function4() { // from class:
                                            // com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(4);
                                            }

                                            @Override // kotlin.jvm.functions.Function4
                                            public final Object invoke(
                                                    Object obj2,
                                                    Object obj3,
                                                    Object obj4,
                                                    Object obj5) {
                                                AnimatedContentScope composable =
                                                        (AnimatedContentScope) obj2;
                                                NavBackStackEntry it = (NavBackStackEntry) obj3;
                                                ((Number) obj5).intValue();
                                                Intrinsics.checkNotNullParameter(
                                                        composable, "$this$composable");
                                                Intrinsics.checkNotNullParameter(it, "it");
                                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                final SimOnboardingService simOnboardingService3 =
                                                        simOnboardingService2;
                                                final Context context3 = context2;
                                                SimOnboardingPrimarySimKt
                                                        .SimOnboardingPrimarySimImpl(
                                                                new Function0() { // from class:
                                                                    // com.android.settings.spa.network.SimOnboardingPageProviderKt.PageImpl.1.2.1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(0);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function0
                                                                    /* renamed from: invoke */
                                                                    public final Object
                                                                            mo1068invoke() {
                                                                        simOnboardingService3
                                                                                .callback.invoke(
                                                                                SimOnboardingActivity$Companion$CallbackType
                                                                                        .CALLBACK_ONBOARDING_COMPLETE);
                                                                        Activity activity =
                                                                                SimOnboardingPageProviderKt
                                                                                        .getActivity(
                                                                                                context3);
                                                                        if (activity != null) {
                                                                            activity.finish();
                                                                        }
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                },
                                                                Ref$ObjectRef.this.element,
                                                                simOnboardingService3,
                                                                (Composer) obj4,
                                                                512);
                                                return Unit.INSTANCE;
                                            }
                                        }),
                                254);
                        final Ref$ObjectRef<Function0> ref$ObjectRef4 = ref$ObjectRef;
                        final SimOnboardingService simOnboardingService3 =
                                SimOnboardingService.this;
                        final NavHostController navHostController3 = navHostController;
                        NavGraphBuilderKt.composable$default(
                                NavHost,
                                "SelectSim",
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                new ComposableLambdaImpl(
                                        756791955,
                                        true,
                                        new Function4() { // from class:
                                            // com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$1.3
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(4);
                                            }

                                            @Override // kotlin.jvm.functions.Function4
                                            public final Object invoke(
                                                    Object obj2,
                                                    Object obj3,
                                                    Object obj4,
                                                    Object obj5) {
                                                AnimatedContentScope composable =
                                                        (AnimatedContentScope) obj2;
                                                NavBackStackEntry it = (NavBackStackEntry) obj3;
                                                ((Number) obj5).intValue();
                                                Intrinsics.checkNotNullParameter(
                                                        composable, "$this$composable");
                                                Intrinsics.checkNotNullParameter(it, "it");
                                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                final NavHostController navHostController4 =
                                                        navHostController3;
                                                SimOnboardingSelectSimKt.SimOnboardingSelectSimImpl(
                                                        new Function0() { // from class:
                                                            // com.android.settings.spa.network.SimOnboardingPageProviderKt.PageImpl.1.3.1
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                NavController.navigate$default(
                                                                        NavHostController.this,
                                                                        "PrimarySim",
                                                                        null,
                                                                        6);
                                                                return Unit.INSTANCE;
                                                            }
                                                        },
                                                        ref$ObjectRef4.element,
                                                        simOnboardingService3,
                                                        (Composer) obj4,
                                                        512);
                                                return Unit.INSTANCE;
                                            }
                                        }),
                                254);
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                8,
                0,
                1020);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingPageProviderKt$PageImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingPageProviderKt.PageImpl(
                                    SimOnboardingService.this,
                                    navHostController,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void SimOnboardingMessage(
            final String text, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(text, "text");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(208774821);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(text) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier m89paddingqDBjuR0$default =
                    PaddingKt.m89paddingqDBjuR0$default(
                            Modifier.Companion.$$INSTANCE,
                            SettingsDimension.itemPaddingStart,
                            0.0f,
                            SettingsDimension.itemPaddingEnd,
                            SettingsDimension.paddingExtraLarge,
                            2);
            ColumnMeasurePolicy columnMeasurePolicy =
                    ColumnKt.columnMeasurePolicy(
                            Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, m89paddingqDBjuR0$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m221setimpl(
                    composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
            }
            Updater.m221setimpl(
                    composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            TextKt.SettingsBody(text, null, 0, composerImpl, i2 & 14, 6);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.SimOnboardingPageProviderKt$SimOnboardingMessage$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SimOnboardingPageProviderKt.SimOnboardingMessage(
                                    text,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final Activity getActivity(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (!(context instanceof ContextWrapper)) {
            return null;
        }
        Context baseContext = ((ContextWrapper) context).getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
        return getActivity(baseContext);
    }
}
