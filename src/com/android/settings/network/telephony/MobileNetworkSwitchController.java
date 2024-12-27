package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.spa.widget.preference.MainSwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser;

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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.internal.ContextScope;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000:\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B+\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020"
                + "\t¢\u0006\u0002\u0010\n"
                + "J\r"
                + "\u0010\r"
                + "\u001a\u00020\u000eH\u0017¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\fH\u0016J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fJ\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0003H\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0014²\u0006\f\u0010\u0015\u001a\u0004\u0018\u00010\u0013X\u008a\u0084\u0002²\u0006\n"
                + "\u0010\u0016\u001a\u00020\u0013X\u008a\u0084\u0002"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/MobileNetworkSwitchController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "subscriptionRepository",
            "Lcom/android/settings/network/telephony/SubscriptionRepository;",
            "subscriptionActivationRepository",
            "Lcom/android/settings/network/telephony/SubscriptionActivationRepository;",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/android/settings/network/telephony/SubscriptionRepository;Lcom/android/settings/network/telephony/SubscriptionActivationRepository;)V",
            "subId",
            ApnSettings.MVNO_NONE,
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "getAvailabilityStatus",
            "init",
            GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_VISIBLE,
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core",
            "checked",
            "changeable"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class MobileNetworkSwitchController extends ComposePreferenceController {
    public static final int $stable = 8;
    private int subId;
    private final SubscriptionActivationRepository subscriptionActivationRepository;
    private final SubscriptionRepository subscriptionRepository;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MobileNetworkSwitchController(Context context, String preferenceKey) {
        this(context, preferenceKey, null, null, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Boolean Content$lambda$2(State state) {
        return (Boolean) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean Content$lambda$4(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    private final boolean isVisible(Context context) {
        Object obj;
        Iterator it =
                SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(
                                this.subscriptionRepository.context)
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((SubscriptionInfo) obj).getSubscriptionId() == this.subId) {
                break;
            }
        }
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        if (subscriptionInfo == null) {
            return false;
        }
        return subscriptionInfo.isEmbedded()
                || SubscriptionRepositoryKt.requireSubscriptionManager(context)
                        .canDisablePhysicalSubscription();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1395618430);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(1850554690);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = Boolean.valueOf(!isVisible(context));
            composerImpl.updateRememberedValue(rememberedValue);
        }
        boolean booleanValue = ((Boolean) rememberedValue).booleanValue();
        composerImpl.end(false);
        if (booleanValue) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.network.telephony.MobileNetworkSwitchController$Content$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                MobileNetworkSwitchController.this.Content(
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        composerImpl.startReplaceGroup(1850554755);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = this.subscriptionRepository.isSubscriptionEnabledFlow(this.subId);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        (Flow) rememberedValue2, null, composerImpl, 56);
        composerImpl.startReplaceGroup(1850554919);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == composer$Companion$Empty$1) {
            rememberedValue3 = this.subscriptionActivationRepository.isActivationChangeableFlow();
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle2 =
                FlowExtKt.collectAsStateWithLifecycle(
                        (Flow) rememberedValue3, Boolean.TRUE, composerImpl, 56);
        Object rememberedValue4 = composerImpl.rememberedValue();
        if (rememberedValue4 == composer$Companion$Empty$1) {
            rememberedValue4 =
                    PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                            EffectsKt.createCompositionCoroutineScope(
                                    EmptyCoroutineContext.INSTANCE, composerImpl),
                            composerImpl);
        }
        final ContextScope contextScope =
                (ContextScope)
                        ((CompositionScopedCoroutineScopeCanceller) rememberedValue4)
                                .coroutineScope;
        MainSwitchPreferenceKt.MainSwitchPreference(
                new SwitchPreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle2,
                        collectAsStateWithLifecycle,
                        contextScope,
                        this) { // from class:
                    // com.android.settings.network.telephony.MobileNetworkSwitchController$Content$3
                    public final Function0 changeable;
                    public final Function0 checked;
                    public final Function1 onCheckedChange;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.mobile_network_use_sim_on);
                        this.changeable =
                                new Function0() { // from class:
                                    // com.android.settings.network.telephony.MobileNetworkSwitchController$Content$3$changeable$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        boolean Content$lambda$4;
                                        Content$lambda$4 =
                                                MobileNetworkSwitchController.Content$lambda$4(
                                                        collectAsStateWithLifecycle2);
                                        return Boolean.valueOf(Content$lambda$4);
                                    }
                                };
                        this.checked =
                                new Function0() { // from class:
                                    // com.android.settings.network.telephony.MobileNetworkSwitchController$Content$3$checked$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        Boolean Content$lambda$2;
                                        Content$lambda$2 =
                                                MobileNetworkSwitchController.Content$lambda$2(
                                                        collectAsStateWithLifecycle);
                                        return Content$lambda$2;
                                    }
                                };
                        this.onCheckedChange =
                                new Function1() { // from class:
                                    // com.android.settings.network.telephony.MobileNetworkSwitchController$Content$3$onCheckedChange$1

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
                                    /* renamed from: com.android.settings.network.telephony.MobileNetworkSwitchController$Content$3$onCheckedChange$1$1, reason: invalid class name */
                                    final class AnonymousClass1 extends SuspendLambda
                                            implements Function2 {
                                        final /* synthetic */ boolean $newChecked;
                                        int label;
                                        final /* synthetic */ MobileNetworkSwitchController this$0;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass1(
                                                MobileNetworkSwitchController
                                                        mobileNetworkSwitchController,
                                                boolean z,
                                                Continuation continuation) {
                                            super(2, continuation);
                                            this.this$0 = mobileNetworkSwitchController;
                                            this.$newChecked = z;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(
                                                Object obj, Continuation continuation) {
                                            return new AnonymousClass1(
                                                    this.this$0, this.$newChecked, continuation);
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
                                            SubscriptionActivationRepository
                                                    subscriptionActivationRepository;
                                            int i;
                                            CoroutineSingletons coroutineSingletons =
                                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i2 = this.label;
                                            if (i2 == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                subscriptionActivationRepository =
                                                        this.this$0
                                                                .subscriptionActivationRepository;
                                                i = this.this$0.subId;
                                                boolean z = this.$newChecked;
                                                this.label = 1;
                                                if (subscriptionActivationRepository.setActive(
                                                                i, z, this)
                                                        == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            } else {
                                                if (i2 != 1) {
                                                    throw new IllegalStateException(
                                                            "call to 'resume' before 'invoke' with"
                                                                + " coroutine");
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
                                        BuildersKt.launch$default(
                                                contextScope,
                                                null,
                                                null,
                                                new AnonymousClass1(
                                                        this, ((Boolean) obj).booleanValue(), null),
                                                3);
                                        return Unit.INSTANCE;
                                    }
                                };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function0 getChangeable() {
                        return this.changeable;
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
                    public final String getTitle() {
                        return this.title;
                    }
                },
                composerImpl,
                0);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.telephony.MobileNetworkSwitchController$Content$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            MobileNetworkSwitchController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public final void init(int subId) {
        this.subId = subId;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MobileNetworkSwitchController(
            Context context, String preferenceKey, SubscriptionRepository subscriptionRepository) {
        this(context, preferenceKey, subscriptionRepository, null, 8, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Intrinsics.checkNotNullParameter(subscriptionRepository, "subscriptionRepository");
    }

    public /* synthetic */ MobileNetworkSwitchController(
            Context context,
            String str,
            SubscriptionRepository subscriptionRepository,
            SubscriptionActivationRepository subscriptionActivationRepository,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                str,
                (i & 4) != 0 ? new SubscriptionRepository(context) : subscriptionRepository,
                (i & 8) != 0
                        ? new SubscriptionActivationRepository(context)
                        : subscriptionActivationRepository);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkSwitchController(
            Context context,
            String preferenceKey,
            SubscriptionRepository subscriptionRepository,
            SubscriptionActivationRepository subscriptionActivationRepository) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Intrinsics.checkNotNullParameter(subscriptionRepository, "subscriptionRepository");
        Intrinsics.checkNotNullParameter(
                subscriptionActivationRepository, "subscriptionActivationRepository");
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionActivationRepository = subscriptionActivationRepository;
        this.subId = -1;
    }
}
