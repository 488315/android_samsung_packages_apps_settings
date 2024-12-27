package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Pair;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.outlined.AddKt;
import androidx.compose.material.icons.outlined.LaunchKt;
import androidx.compose.material.icons.outlined.WarningAmberKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.button.ActionButton;
import com.android.settingslib.spa.widget.button.ActionButtonsKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.ContextScope;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ClonePageAppButtonsKt {
    public static final void ClonePageAppButtons(
            final PackageInfoPresenter packageInfoPresenter, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-395397977);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-1746007869);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new CloneAppButtonsPresenter(packageInfoPresenter);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        CloneAppButtonsPresenter cloneAppButtonsPresenter =
                (CloneAppButtonsPresenter) rememberedValue;
        composerImpl.end(false);
        cloneAppButtonsPresenter.getClass();
        composerImpl.startReplaceGroup(-1190041399);
        List list = null;
        PackageInfo packageInfo =
                (PackageInfo)
                        FlowExtKt.collectAsStateWithLifecycle(
                                        cloneAppButtonsPresenter.packageInfoPresenter.flow,
                                        null,
                                        composerImpl,
                                        56)
                                .getValue();
        if (packageInfo != null) {
            final ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            composerImpl.startReplaceGroup(-1480518027);
            FakeAppLaunchButton fakeAppLaunchButton = cloneAppButtonsPresenter.appLaunchButton;
            fakeAppLaunchButton.getClass();
            composerImpl.startReplaceGroup(1196751280);
            String string = fakeAppLaunchButton.context.getString(R.string.launch_instant_app);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            ActionButton actionButton =
                    new ActionButton(
                            string,
                            LaunchKt.getLaunch(),
                            false,
                            new Function0() { // from class:
                                              // com.android.settings.spa.app.appinfo.FakeAppLaunchButton$getActionButton$1
                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                                    return Unit.INSTANCE;
                                }
                            });
            composerImpl.end(false);
            final AppCreateButton appCreateButton = cloneAppButtonsPresenter.appCreateButton;
            appCreateButton.getClass();
            composerImpl.startReplaceGroup(-1210391875);
            composerImpl.startReplaceGroup(-1985071511);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 =
                        PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                                EffectsKt.createCompositionCoroutineScope(
                                        EmptyCoroutineContext.INSTANCE, composerImpl),
                                composerImpl);
            }
            CoroutineScope coroutineScope =
                    ((CompositionScopedCoroutineScopeCanceller) rememberedValue2).coroutineScope;
            final NavControllerWrapper navControllerWrapper =
                    (NavControllerWrapper)
                            composerImpl.consume(NavControllerWrapperKt.LocalNavController);
            String string2 = appCreateButton.context.getString(R.string.create);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            final ContextScope contextScope = (ContextScope) coroutineScope;
            ActionButton actionButton2 =
                    new ActionButton(
                            string2,
                            AddKt.getAdd(),
                            ((Boolean) appCreateButton.enabledState.getValue()).booleanValue(),
                            new Function0() { // from class:
                                // com.android.settings.spa.app.appinfo.AppCreateButton$createButton$1

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
                                /* renamed from: com.android.settings.spa.app.appinfo.AppCreateButton$createButton$1$1, reason: invalid class name */
                                final class AnonymousClass1 extends SuspendLambda
                                        implements Function2 {
                                    final /* synthetic */ ApplicationInfo $app;
                                    final /* synthetic */ CharSequence $appLabel;
                                    final /* synthetic */ CloneBackend $cloneBackend;
                                    final /* synthetic */ NavControllerWrapper $navController;
                                    int label;
                                    final /* synthetic */ AppCreateButton this$0;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public AnonymousClass1(
                                            AppCreateButton appCreateButton,
                                            ApplicationInfo applicationInfo,
                                            CloneBackend cloneBackend,
                                            CharSequence charSequence,
                                            NavControllerWrapper navControllerWrapper,
                                            Continuation continuation) {
                                        super(2, continuation);
                                        this.this$0 = appCreateButton;
                                        this.$app = applicationInfo;
                                        this.$cloneBackend = cloneBackend;
                                        this.$appLabel = charSequence;
                                        this.$navController = navControllerWrapper;
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Continuation create(
                                            Object obj, Continuation continuation) {
                                        return new AnonymousClass1(
                                                this.this$0,
                                                this.$app,
                                                this.$cloneBackend,
                                                this.$appLabel,
                                                this.$navController,
                                                continuation);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        return ((AnonymousClass1)
                                                        create(
                                                                (CoroutineScope) obj,
                                                                (Continuation) obj2))
                                                .invokeSuspend(Unit.INSTANCE);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        CoroutineSingletons coroutineSingletons =
                                                CoroutineSingletons.COROUTINE_SUSPENDED;
                                        int i = this.label;
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            this.this$0.enabledState.setValue(Boolean.FALSE);
                                            AppCreateButton appCreateButton = this.this$0;
                                            ApplicationInfo applicationInfo = this.$app;
                                            CloneBackend cloneBackend = this.$cloneBackend;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    cloneBackend, "$cloneBackend");
                                            this.label = 1;
                                            appCreateButton.getClass();
                                            obj =
                                                    BuildersKt.withContext(
                                                            Dispatchers.IO,
                                                            new AppCreateButton$installCloneApp$2(
                                                                    cloneBackend,
                                                                    applicationInfo,
                                                                    null),
                                                            this);
                                            if (obj == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException(
                                                        "call to 'resume' before 'invoke' with"
                                                            + " coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj);
                                        }
                                        if (((Number) obj).intValue() == 0) {
                                            Context context = this.this$0.context;
                                            Preference$$ExternalSyntheticOutline0.m(
                                                    context,
                                                    R.string.cloned_app_created_toast_summary,
                                                    new Object[] {this.$appLabel},
                                                    context,
                                                    0);
                                            NavControllerWrapper navControllerWrapper =
                                                    this.$navController;
                                            AppInfoSettingsProvider appInfoSettingsProvider =
                                                    AppInfoSettingsProvider.INSTANCE;
                                            String packageName = this.$app.packageName;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    packageName, "packageName");
                                            navControllerWrapper.navigate(
                                                    appInfoSettingsProvider.getRoute(
                                                            this.$cloneBackend.mCloneUserId,
                                                            packageName),
                                                    true);
                                        } else {
                                            this.this$0.enabledState.setValue(Boolean.TRUE);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    CloneBackend cloneBackend =
                                            CloneBackend.getInstance(AppCreateButton.this.context);
                                    FeatureFactoryImpl featureFactoryImpl =
                                            FeatureFactoryImpl._factory;
                                    if (featureFactoryImpl == null) {
                                        throw new UnsupportedOperationException(
                                                "No feature factory configured");
                                    }
                                    featureFactoryImpl
                                            .getMetricsFeatureProvider()
                                            .action(
                                                    AppCreateButton.this.context,
                                                    1807,
                                                    new Pair[0]);
                                    CharSequence loadLabel =
                                            applicationInfo.loadLabel(
                                                    AppCreateButton.this.context
                                                            .getPackageManager());
                                    Intrinsics.checkNotNullExpressionValue(
                                            loadLabel, "loadLabel(...)");
                                    Context context = AppCreateButton.this.context;
                                    Preference$$ExternalSyntheticOutline0.m(
                                            context,
                                            R.string.cloned_app_creation_toast_summary,
                                            new Object[] {loadLabel},
                                            context,
                                            0);
                                    BuildersKt.launch$default(
                                            contextScope,
                                            null,
                                            null,
                                            new AnonymousClass1(
                                                    AppCreateButton.this,
                                                    applicationInfo,
                                                    cloneBackend,
                                                    loadLabel,
                                                    navControllerWrapper,
                                                    null),
                                            3);
                                    return Unit.INSTANCE;
                                }
                            });
            composerImpl.end(false);
            composerImpl.end(false);
            String string3 =
                    cloneAppButtonsPresenter.appForceStopButton.context.getString(
                            R.string.force_stop);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            list =
                    ArraysKt___ArraysKt.filterNotNull(
                            new ActionButton[] {
                                actionButton,
                                actionButton2,
                                new ActionButton(
                                        string3,
                                        WarningAmberKt.getWarningAmber(),
                                        false,
                                        new Function0() { // from class:
                                                          // com.android.settings.spa.app.appinfo.FakeAppForceStopButton$getActionButton$1
                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final /* bridge */ /* synthetic */ Object
                                                    mo1068invoke() {
                                                return Unit.INSTANCE;
                                            }
                                        })
                            });
            composerImpl.end(false);
        }
        if (list == null) {
            list = EmptyList.INSTANCE;
        }
        composerImpl.end(false);
        ActionButtonsKt.ActionButtons(list, composerImpl, 8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.ClonePageAppButtonsKt$ClonePageAppButtons$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ClonePageAppButtonsKt.ClonePageAppButtons(
                                    PackageInfoPresenter.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
