package com.android.settings.print;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;

import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.core.os.BundleKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.compose.DrawablePainterKt;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spa.widget.ui.CategoryKt;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrintSettingsPageProvider implements SettingsPageProvider {
    public static final PrintSettingsPageProvider INSTANCE = new PrintSettingsPageProvider();

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.settings.print.PrintSettingsPageProvider$PrintServices$1, kotlin.jvm.internal.Lambda] */
    public static final void access$PrintServices(
            final PrintSettingsPageProvider printSettingsPageProvider,
            final PrintRepository printRepository,
            Composer composer,
            final int i) {
        final int i2 = 0;
        printSettingsPageProvider.getClass();
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1571500986);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(834644095);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            PrintManager printManager = printRepository.printManager;
            Intrinsics.checkNotNullParameter(printManager, "<this>");
            Flow buffer$default =
                    FlowKt.buffer$default(
                            FlowKt.callbackFlow(
                                    new PrintRepository$Companion$printServicesChangeFlow$1(
                                            printManager, null)),
                            -1);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            final Flow flowOn = FlowKt.flowOn(buffer$default, defaultScheduler);
            final Flow flowOn2 =
                    FlowKt.flowOn(
                            FlowKt.buffer$default(
                                    new Flow() { // from class:
                                        // com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        /* renamed from: com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2, reason: invalid class name */
                                        public final class AnonymousClass2
                                                implements FlowCollector {
                                            public final /* synthetic */ FlowCollector
                                                    $this_unsafeFlow;
                                            public final /* synthetic */ PrintRepository this$0;

                                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                            @Metadata(
                                                    k = 3,
                                                    mv = {1, 9, 0},
                                                    xi = 48)
                                            /* renamed from: com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1, reason: invalid class name */
                                            public final class AnonymousClass1
                                                    extends ContinuationImpl {
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
                                                    PrintRepository printRepository) {
                                                this.$this_unsafeFlow = flowCollector;
                                                this.this$0 = printRepository;
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
                                                    boolean r0 = r6 instanceof com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                    if (r0 == 0) goto L13
                                                    r0 = r6
                                                    com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1 r0 = (com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                    int r1 = r0.label
                                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                    r3 = r1 & r2
                                                    if (r3 == 0) goto L13
                                                    int r1 = r1 - r2
                                                    r0.label = r1
                                                    goto L18
                                                L13:
                                                    com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1 r0 = new com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1
                                                    r0.<init>(r6)
                                                L18:
                                                    java.lang.Object r6 = r0.result
                                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                    int r2 = r0.label
                                                    r3 = 1
                                                    if (r2 == 0) goto L2f
                                                    if (r2 != r3) goto L27
                                                    kotlin.ResultKt.throwOnFailure(r6)
                                                    goto L48
                                                L27:
                                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                    r4.<init>(r5)
                                                    throw r4
                                                L2f:
                                                    kotlin.ResultKt.throwOnFailure(r6)
                                                    kotlin.Unit r5 = (kotlin.Unit) r5
                                                    com.android.settings.print.PrintRepository r5 = r4.this$0
                                                    android.print.PrintManager r5 = r5.printManager
                                                    r6 = 3
                                                    java.util.List r5 = r5.getPrintServices(r6)
                                                    r0.label = r3
                                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                                    java.lang.Object r4 = r4.emit(r5, r0)
                                                    if (r4 != r1) goto L48
                                                    return r1
                                                L48:
                                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                    return r4
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                            + " kotlin.coroutines.Continuation):java.lang.Object");
                                            }
                                        }

                                        @Override // kotlinx.coroutines.flow.Flow
                                        public final Object collect(
                                                FlowCollector flowCollector,
                                                Continuation continuation) {
                                            switch (i2) {
                                                case 0:
                                                    Object collect =
                                                            flowOn.collect(
                                                                    new AnonymousClass2(
                                                                            flowCollector,
                                                                            printRepository),
                                                                    continuation);
                                                    if (collect
                                                            != CoroutineSingletons
                                                                    .COROUTINE_SUSPENDED) {
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    Object collect2 =
                                                            flowOn.collect(
                                                                    new PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2(
                                                                            flowCollector,
                                                                            printRepository),
                                                                    continuation);
                                                    if (collect2
                                                            != CoroutineSingletons
                                                                    .COROUTINE_SUSPENDED) {
                                                        break;
                                                    }
                                                    break;
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    -1),
                            defaultScheduler);
            final int i3 = 1;
            rememberedValue =
                    FlowKt.flowOn(
                            FlowKt.buffer$default(
                                    new Flow() { // from class:
                                        // com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        /* renamed from: com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2, reason: invalid class name */
                                        public final class AnonymousClass2
                                                implements FlowCollector {
                                            public final /* synthetic */ FlowCollector
                                                    $this_unsafeFlow;
                                            public final /* synthetic */ PrintRepository this$0;

                                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                            @Metadata(
                                                    k = 3,
                                                    mv = {1, 9, 0},
                                                    xi = 48)
                                            /* renamed from: com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1, reason: invalid class name */
                                            public final class AnonymousClass1
                                                    extends ContinuationImpl {
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
                                                    PrintRepository printRepository) {
                                                this.$this_unsafeFlow = flowCollector;
                                                this.this$0 = printRepository;
                                            }

                                            @Override // kotlinx.coroutines.flow.FlowCollector
                                            public final Object emit(
                                                    Object obj, Continuation continuation) {
                                                /*
                                                    this = this;
                                                    boolean r0 = r6 instanceof com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                    if (r0 == 0) goto L13
                                                    r0 = r6
                                                    com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1 r0 = (com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                    int r1 = r0.label
                                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                    r3 = r1 & r2
                                                    if (r3 == 0) goto L13
                                                    int r1 = r1 - r2
                                                    r0.label = r1
                                                    goto L18
                                                L13:
                                                    com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1 r0 = new com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1$2$1
                                                    r0.<init>(r6)
                                                L18:
                                                    java.lang.Object r6 = r0.result
                                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                    int r2 = r0.label
                                                    r3 = 1
                                                    if (r2 == 0) goto L2f
                                                    if (r2 != r3) goto L27
                                                    kotlin.ResultKt.throwOnFailure(r6)
                                                    goto L48
                                                L27:
                                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                    r4.<init>(r5)
                                                    throw r4
                                                L2f:
                                                    kotlin.ResultKt.throwOnFailure(r6)
                                                    kotlin.Unit r5 = (kotlin.Unit) r5
                                                    com.android.settings.print.PrintRepository r5 = r4.this$0
                                                    android.print.PrintManager r5 = r5.printManager
                                                    r6 = 3
                                                    java.util.List r5 = r5.getPrintServices(r6)
                                                    r0.label = r3
                                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                                    java.lang.Object r4 = r4.emit(r5, r0)
                                                    if (r4 != r1) goto L48
                                                    return r1
                                                L48:
                                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                    return r4
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.android.settings.print.PrintRepository$printServicesFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                            + " kotlin.coroutines.Continuation):java.lang.Object");
                                            }
                                        }

                                        @Override // kotlinx.coroutines.flow.Flow
                                        public final Object collect(
                                                FlowCollector flowCollector,
                                                Continuation continuation) {
                                            switch (i3) {
                                                case 0:
                                                    Object collect =
                                                            flowOn2.collect(
                                                                    new AnonymousClass2(
                                                                            flowCollector,
                                                                            printRepository),
                                                                    continuation);
                                                    if (collect
                                                            != CoroutineSingletons
                                                                    .COROUTINE_SUSPENDED) {
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    Object collect2 =
                                                            flowOn2.collect(
                                                                    new PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2(
                                                                            flowCollector,
                                                                            printRepository),
                                                                    continuation);
                                                    if (collect2
                                                            != CoroutineSingletons
                                                                    .COROUTINE_SUSPENDED) {
                                                        break;
                                                    }
                                                    break;
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    -1),
                            defaultScheduler);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        (Flow) rememberedValue, EmptyList.INSTANCE, composerImpl, 56);
        CategoryKt.Category(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.print_settings_title),
                ComposableLambdaKt.rememberComposableLambda(
                        -2118558186,
                        new Function3() { // from class:
                                          // com.android.settings.print.PrintSettingsPageProvider$PrintServices$1
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
                                Iterator it =
                                        ((List) collectAsStateWithLifecycle.getValue()).iterator();
                                while (it.hasNext()) {
                                    PrintSettingsPageProvider.INSTANCE.PrintService(
                                            (PrintRepository.PrintServiceDisplayInfo) it.next(),
                                            composer2,
                                            56);
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                48);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.print.PrintSettingsPageProvider$PrintServices$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PrintSettingsPageProvider.access$PrintServices(
                                    PrintSettingsPageProvider.this,
                                    printRepository,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2004530149);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            RegularScaffoldKt.RegularScaffold(
                    StringResources_androidKt.stringResource(composerImpl, R.string.print_settings),
                    null,
                    ComposableSingletons$PrintSettingsPageProviderKt.f27lambda1,
                    composerImpl,
                    384,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.print.PrintSettingsPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PrintSettingsPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void PrintService(
            final PrintRepository.PrintServiceDisplayInfo displayInfo,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(displayInfo, "displayInfo");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1542866244);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        PreferenceKt.Preference(
                new PreferenceModel(
                        context,
                        displayInfo) { // from class:
                                       // com.android.settings.print.PrintSettingsPageProvider$PrintService$1
                    public final ComposableLambdaImpl icon;
                    public final Function0 onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title = displayInfo.title;
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.print.PrintSettingsPageProvider$PrintService$1$summary$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return PrintRepository.PrintServiceDisplayInfo.this.summary;
                                    }
                                };
                        this.icon =
                                new ComposableLambdaImpl(
                                        -2082721115,
                                        true,
                                        new Function2() { // from class:
                                                          // com.android.settings.print.PrintSettingsPageProvider$PrintService$1$icon$1
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj, Object obj2) {
                                                Composer composer2 = (Composer) obj;
                                                if ((((Number) obj2).intValue() & 11) == 2) {
                                                    ComposerImpl composerImpl2 =
                                                            (ComposerImpl) composer2;
                                                    if (composerImpl2.getSkipping()) {
                                                        composerImpl2.skipToGroupEnd();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                ImageKt.Image(
                                                        DrawablePainterKt.rememberDrawablePainter(
                                                                PrintRepository
                                                                        .PrintServiceDisplayInfo
                                                                        .this
                                                                        .icon,
                                                                composer2),
                                                        null,
                                                        SizeKt.m96size3ABfNKs(
                                                                Modifier.Companion.$$INSTANCE,
                                                                SettingsDimension.appIconItemSize),
                                                        null,
                                                        null,
                                                        0.0f,
                                                        null,
                                                        composer2,
                                                        56,
                                                        120);
                                                return Unit.INSTANCE;
                                            }
                                        });
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.print.PrintSettingsPageProvider$PrintService$1$onClick$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        SubSettingLauncher subSettingLauncher =
                                                new SubSettingLauncher(context);
                                        PrintRepository.PrintServiceDisplayInfo
                                                printServiceDisplayInfo = displayInfo;
                                        String qualifiedName =
                                                Reflection.factory
                                                        .getOrCreateKotlinClass(
                                                                PrintServiceSettingsFragment.class)
                                                        .getQualifiedName();
                                        SubSettingLauncher.LaunchRequest launchRequest =
                                                subSettingLauncher.mLaunchRequest;
                                        launchRequest.mDestinationName = qualifiedName;
                                        launchRequest.mArguments =
                                                BundleKt.bundleOf(
                                                        new Pair(
                                                                "EXTRA_CHECKED",
                                                                Boolean.valueOf(
                                                                        printServiceDisplayInfo
                                                                                .isEnabled)),
                                                        new Pair(
                                                                "EXTRA_TITLE",
                                                                printServiceDisplayInfo.title),
                                                        new Pair(
                                                                "EXTRA_SERVICE_COMPONENT_NAME",
                                                                printServiceDisplayInfo
                                                                        .componentName));
                                        launchRequest.mSourceMetricsCategory = 80;
                                        subSettingLauncher.launch();
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function2 getIcon() {
                        return this.icon;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.print.PrintSettingsPageProvider$PrintService$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            PrintSettingsPageProvider.this.PrintService(
                                    displayInfo,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "PrintSettings";
    }
}
