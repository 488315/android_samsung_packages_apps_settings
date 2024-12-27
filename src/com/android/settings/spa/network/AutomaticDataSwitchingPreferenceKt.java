package com.android.settings.spa.network;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.lifecycle.viewmodel.compose.ViewModelKt;

import com.android.settings.R;
import com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AutomaticDataSwitchingPreferenceKt {
    public static final void AutomaticDataSwitchingPreference(
            final Function0 isAutoDataEnabled,
            final Function1 setAutoDataEnabled,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(isAutoDataEnabled, "isAutoDataEnabled");
        Intrinsics.checkNotNullParameter(setAutoDataEnabled, "setAutoDataEnabled");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(581651418);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(isAutoDataEnabled) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(setAutoDataEnabled) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final String stringResource =
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.primary_sim_automatic_data_msg);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                                EffectsKt.createCompositionCoroutineScope(
                                        EmptyCoroutineContext.INSTANCE, composerImpl),
                                composerImpl);
            }
            CoroutineScope coroutineScope =
                    ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
            composerImpl.startReplaceableGroup(1729797275);
            ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
            if (current == null) {
                throw new IllegalStateException(
                        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                                .toString());
            }
            ViewModel viewModel =
                    ViewModelKt.viewModel(
                            Reflection.factory.getOrCreateKotlinClass(
                                    CrossSimCallingViewModel.class),
                            current,
                            null,
                            current instanceof HasDefaultViewModelProviderFactory
                                    ? ((HasDefaultViewModelProviderFactory) current)
                                            .getDefaultViewModelCreationExtras()
                                    : CreationExtras.Empty.INSTANCE,
                            composerImpl);
            composerImpl.end(false);
            final CrossSimCallingViewModel crossSimCallingViewModel =
                    (CrossSimCallingViewModel) viewModel;
            final ContextScope contextScope = (ContextScope) coroutineScope;
            SwitchPreferenceKt.SwitchPreference(
                    new SwitchPreferenceModel(
                            composerImpl,
                            stringResource,
                            isAutoDataEnabled,
                            contextScope,
                            setAutoDataEnabled,
                            crossSimCallingViewModel) { // from class:
                        // com.android.settings.spa.network.AutomaticDataSwitchingPreferenceKt$AutomaticDataSwitchingPreference$1
                        public final Function0 checked;
                        public final Function1 onCheckedChange;
                        public final Function0 summary;
                        public final String title;

                        {
                            this.title =
                                    StringResources_androidKt.stringResource(
                                            composerImpl,
                                            R.string.primary_sim_automatic_data_title);
                            this.summary =
                                    new Function0() { // from class:
                                        // com.android.settings.spa.network.AutomaticDataSwitchingPreferenceKt$AutomaticDataSwitchingPreference$1$summary$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return stringResource;
                                        }
                                    };
                            this.checked =
                                    new Function0() { // from class:
                                        // com.android.settings.spa.network.AutomaticDataSwitchingPreferenceKt$AutomaticDataSwitchingPreference$1$checked$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return (Boolean) Function0.this.mo1068invoke();
                                        }
                                    };
                            this.onCheckedChange =
                                    new Function1() { // from class:
                                        // com.android.settings.spa.network.AutomaticDataSwitchingPreferenceKt$AutomaticDataSwitchingPreference$1$onCheckedChange$1

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
                                        /* renamed from: com.android.settings.spa.network.AutomaticDataSwitchingPreferenceKt$AutomaticDataSwitchingPreference$1$onCheckedChange$1$1, reason: invalid class name */
                                        final class AnonymousClass1 extends SuspendLambda
                                                implements Function2 {
                                            final /* synthetic */ CrossSimCallingViewModel
                                                    $crossSimCallingViewModel;
                                            final /* synthetic */ boolean $newEnabled;
                                            final /* synthetic */ Function1 $setAutoDataEnabled;
                                            int label;

                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            public AnonymousClass1(
                                                    Function1 function1,
                                                    boolean z,
                                                    CrossSimCallingViewModel
                                                            crossSimCallingViewModel,
                                                    Continuation continuation) {
                                                super(2, continuation);
                                                this.$setAutoDataEnabled = function1;
                                                this.$newEnabled = z;
                                                this.$crossSimCallingViewModel =
                                                        crossSimCallingViewModel;
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Continuation create(
                                                    Object obj, Continuation continuation) {
                                                return new AnonymousClass1(
                                                        this.$setAutoDataEnabled,
                                                        this.$newEnabled,
                                                        this.$crossSimCallingViewModel,
                                                        continuation);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj, Object obj2) {
                                                AnonymousClass1 anonymousClass1 =
                                                        (AnonymousClass1)
                                                                create(
                                                                        (CoroutineScope) obj,
                                                                        (Continuation) obj2);
                                                Unit unit = Unit.INSTANCE;
                                                anonymousClass1.invokeSuspend(unit);
                                                return unit;
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Object invokeSuspend(Object obj) {
                                                CoroutineSingletons coroutineSingletons =
                                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                                if (this.label != 0) {
                                                    throw new IllegalStateException(
                                                            "call to 'resume' before 'invoke' with"
                                                                + " coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                                this.$setAutoDataEnabled.invoke(
                                                        Boolean.valueOf(this.$newEnabled));
                                                CrossSimCallingViewModel crossSimCallingViewModel =
                                                        this.$crossSimCallingViewModel;
                                                Unit unit = Unit.INSTANCE;
                                                crossSimCallingViewModel.updateChannel
                                                        .mo1469trySendJP2dKIU(unit);
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
                                                    contextScope,
                                                    Dispatchers.Default,
                                                    null,
                                                    new AnonymousClass1(
                                                            setAutoDataEnabled,
                                                            ((Boolean) obj).booleanValue(),
                                                            crossSimCallingViewModel,
                                                            null),
                                                    2);
                                            return Unit.INSTANCE;
                                        }
                                    };
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function0 getChecked() {
                            return this.checked;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function1 getOnCheckedChange() {
                            return this.onCheckedChange;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function0 getSummary() {
                            return this.summary;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final String getTitle() {
                            return this.title;
                        }
                    },
                    composerImpl,
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.AutomaticDataSwitchingPreferenceKt$AutomaticDataSwitchingPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AutomaticDataSwitchingPreferenceKt.AutomaticDataSwitchingPreference(
                                    Function0.this,
                                    setAutoDataEnabled,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
