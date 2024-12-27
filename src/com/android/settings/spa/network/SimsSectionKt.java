package com.android.settings.spa.network;

import android.content.Context;
import android.content.Intent;
import android.telephony.SubscriptionInfo;
import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.material.icons.outlined.SimCardDownloadKt;
import androidx.compose.material.icons.outlined.SimCardKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.settings.R;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.SubscriptionActivationRepository;
import com.android.settings.network.telephony.SubscriptionRepository;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spa.widget.ui.IconKt;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.template.preference.RestrictedPreferenceKt;
import com.android.settingslib.spaprivileged.template.preference.RestrictedTwoTargetSwitchPreferenceKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimsSectionKt {
    public static final void AddSim(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(106245916);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(1782533905);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = Boolean.valueOf(MobileNetworkUtils.showEuiccSettings(context));
                composerImpl.updateRememberedValue(rememberedValue);
            }
            boolean booleanValue = ((Boolean) rememberedValue).booleanValue();
            composerImpl.end(false);
            if (booleanValue) {
                RestrictedPreferenceKt.RestrictedPreference(new PreferenceModel(composerImpl, context) { // from class: com.android.settings.spa.network.SimsSectionKt$AddSim$2
                    public final ComposableLambdaImpl icon = ComposableSingletons$SimsSectionKt.f57lambda1;
                    public final Function0 onClick;
                    public final String title;

                    {
                        this.title = StringResources_androidKt.stringResource(composerImpl, R.string.mobile_network_list_add_more);
                        this.onClick = new Function0() { // from class: com.android.settings.spa.network.SimsSectionKt$AddSim$2$onClick$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Context context2 = context;
                                Intent intent = new Intent("android.telephony.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION");
                                intent.setPackage("com.android.phone");
                                intent.putExtra("android.telephony.euicc.extra.FORCE_PROVISION", true);
                                context2.startActivity(intent);
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
                    public final String getTitle() {
                        return this.title;
                    }
                }, new Restrictions(0, CollectionsKt__CollectionsJVMKt.listOf("no_config_mobile_networks"), 5), composerImpl, 64);
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settings.spa.network.SimsSectionKt$AddSim$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SimsSectionKt.AddSim((Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SimPreference(final SubscriptionInfo subscriptionInfo, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(178033895);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        int subscriptionId = subscriptionInfo.getSubscriptionId();
        composerImpl.startReplaceGroup(-1234773237);
        boolean changed = composerImpl.changed(subscriptionId);
        Object rememberedValue = composerImpl.rememberedValue();
        Object obj = Composer.Companion.Empty;
        if (changed || rememberedValue == obj) {
            rememberedValue = new SubscriptionRepository(context).isSubscriptionEnabledFlow(subscriptionInfo.getSubscriptionId());
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        Boolean bool = Boolean.FALSE;
        final MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle((Flow) rememberedValue, bool, composerImpl, 56);
        final MutableState phoneNumber = phoneNumber(subscriptionInfo, composerImpl);
        composerImpl.startReplaceGroup(-1234772986);
        boolean changed2 = composerImpl.changed(subscriptionInfo);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed2 || rememberedValue2 == obj) {
            rememberedValue2 = new SafeFlow(new SimsSectionKt$SimPreference$isConvertedPsim$2$1(subscriptionInfo, null));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle((Flow) rememberedValue2, bool, composerImpl, 56);
        composerImpl.startReplaceGroup(-1234772770);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == obj) {
            rememberedValue3 = new SubscriptionActivationRepository(context);
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        final SubscriptionActivationRepository subscriptionActivationRepository = (SubscriptionActivationRepository) rememberedValue3;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-1234772681);
        Object rememberedValue4 = composerImpl.rememberedValue();
        if (rememberedValue4 == obj) {
            rememberedValue4 = subscriptionActivationRepository.isActivationChangeableFlow();
            composerImpl.updateRememberedValue(rememberedValue4);
        }
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle((Flow) rememberedValue4, bool, composerImpl, 56);
        Object rememberedValue5 = composerImpl.rememberedValue();
        if (rememberedValue5 == obj) {
            rememberedValue5 = PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl), composerImpl);
        }
        final ContextScope contextScope = (ContextScope) ((CompositionScopedCoroutineScopeCanceller) rememberedValue5).coroutineScope;
        SwitchPreferenceModel switchPreferenceModel = new SwitchPreferenceModel(subscriptionInfo, context, phoneNumber, collectAsStateWithLifecycle2, collectAsStateWithLifecycle3, collectAsStateWithLifecycle, contextScope, subscriptionActivationRepository) { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$1
            public final Function0 changeable;
            public final Function0 checked;
            public final ComposableLambdaImpl icon;
            public final Function1 onCheckedChange;
            public final Function0 summary;
            public final String title;

            {
                this.title = subscriptionInfo.getDisplayName().toString();
                this.summary = new Function0() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$1$summary$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        if (((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue()) {
                            return context.getString(R.string.sim_category_converted_sim);
                        }
                        String str = (String) phoneNumber.getValue();
                        return str == null ? ApnSettings.MVNO_NONE : str;
                    }
                };
                this.icon = new ComposableLambdaImpl(-31251656, true, new Function2() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$1$icon$1
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
                        SimsSectionKt.access$SimIcon(subscriptionInfo.isEmbedded(), composer2, 0);
                        return Unit.INSTANCE;
                    }
                });
                this.changeable = new Function0() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$1$changeable$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return Boolean.valueOf(((Boolean) collectAsStateWithLifecycle3.getValue()).booleanValue() && !((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue());
                    }
                };
                this.checked = new Function0() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$1$checked$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return (Boolean) collectAsStateWithLifecycle.getValue();
                    }
                };
                this.onCheckedChange = new Function1() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$1$onCheckedChange$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                    /* renamed from: com.android.settings.spa.network.SimsSectionKt$SimPreference$1$onCheckedChange$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ boolean $newChecked;
                        final /* synthetic */ SubscriptionInfo $subInfo;
                        final /* synthetic */ SubscriptionActivationRepository $subscriptionActivationRepository;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(SubscriptionActivationRepository subscriptionActivationRepository, SubscriptionInfo subscriptionInfo, boolean z, Continuation continuation) {
                            super(2, continuation);
                            this.$subscriptionActivationRepository = subscriptionActivationRepository;
                            this.$subInfo = subscriptionInfo;
                            this.$newChecked = z;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.$subscriptionActivationRepository, this.$subInfo, this.$newChecked, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                SubscriptionActivationRepository subscriptionActivationRepository = this.$subscriptionActivationRepository;
                                int subscriptionId = this.$subInfo.getSubscriptionId();
                                boolean z = this.$newChecked;
                                this.label = 1;
                                if (subscriptionActivationRepository.setActive(subscriptionId, z, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
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
                    public final Object invoke(Object obj2) {
                        BuildersKt.launch$default(contextScope, null, null, new AnonymousClass1(subscriptionActivationRepository, subscriptionInfo, ((Boolean) obj2).booleanValue(), null), 3);
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
            public final Function2 getIcon() {
                return this.icon;
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
        };
        Restrictions restrictions = new Restrictions(0, CollectionsKt__CollectionsJVMKt.listOf("no_config_mobile_networks"), 5);
        composerImpl.startReplaceGroup(-1234771492);
        boolean changed3 = composerImpl.changed(collectAsStateWithLifecycle2);
        Object rememberedValue6 = composerImpl.rememberedValue();
        if (changed3 || rememberedValue6 == obj) {
            rememberedValue6 = new Function0() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    return Boolean.valueOf(!((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue());
                }
            };
            composerImpl.updateRememberedValue(rememberedValue6);
        }
        composerImpl.end(false);
        RestrictedTwoTargetSwitchPreferenceKt.RestrictedTwoTargetSwitchPreference(switchPreferenceModel, restrictions, (Function0) rememberedValue6, new Function0() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                MobileNetworkUtils.launchMobileNetworkSettings(context, subscriptionInfo);
                return Unit.INSTANCE;
            }
        }, composerImpl, 64, 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settings.spa.network.SimsSectionKt$SimPreference$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    SimsSectionKt.SimPreference(subscriptionInfo, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SimsSection(final List subscriptionInfoList, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(subscriptionInfoList, "subscriptionInfoList");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1134196035);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
        Updater.m221setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m221setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        composerImpl.startReplaceGroup(-99538519);
        Iterator it = subscriptionInfoList.iterator();
        while (it.hasNext()) {
            SimPreference((SubscriptionInfo) it.next(), composerImpl, 8);
        }
        composerImpl.end(false);
        AddSim(composerImpl, 0);
        composerImpl.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settings.spa.network.SimsSectionKt$SimsSection$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SimsSectionKt.SimsSection(subscriptionInfoList, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$SimIcon(final boolean z, Composer composer, final int i) {
        int i2;
        ImageVector imageVector;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1174816006);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (z) {
                imageVector = SimCardDownloadKt._simCardDownload;
                if (imageVector == null) {
                    ImageVector.Builder builder = new ImageVector.Builder("Outlined.SimCardDownload", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96);
                    EmptyList emptyList = VectorKt.EmptyPath;
                    long j = Color.Black;
                    SolidColor solidColor = new SolidColor(j);
                    PathBuilder pathBuilder = new PathBuilder();
                    pathBuilder.moveTo(18.0f, 2.0f);
                    pathBuilder.horizontalLineToRelative(-8.0f);
                    pathBuilder.lineTo(4.0f, 8.0f);
                    pathBuilder.verticalLineToRelative(12.0f);
                    pathBuilder.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
                    pathBuilder.horizontalLineToRelative(12.0f);
                    pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                    pathBuilder.verticalLineTo(4.0f);
                    pathBuilder.curveTo(20.0f, 2.9f, 19.1f, 2.0f, 18.0f, 2.0f);
                    pathBuilder.close();
                    pathBuilder.moveTo(18.0f, 4.0f);
                    pathBuilder.verticalLineToRelative(16.0f);
                    pathBuilder.horizontalLineTo(6.0f);
                    pathBuilder.verticalLineTo(8.83f);
                    pathBuilder.lineTo(10.83f, 4.0f);
                    pathBuilder.horizontalLineTo(18.0f);
                    pathBuilder.close();
                    ImageVector.Builder.m390addPathoIyEayM$default(builder, pathBuilder._nodes, solidColor);
                    SolidColor solidColor2 = new SolidColor(j);
                    PathBuilder pathBuilder2 = new PathBuilder();
                    pathBuilder2.moveTo(16.0f, 13.0f);
                    pathBuilder2.lineToRelative(-4.0f, 4.0f);
                    pathBuilder2.lineToRelative(-4.0f, -4.0f);
                    pathBuilder2.lineToRelative(1.41f, -1.41f);
                    pathBuilder2.lineTo(11.0f, 13.17f);
                    pathBuilder2.verticalLineTo(9.02f);
                    pathBuilder2.lineTo(13.0f, 9.0f);
                    pathBuilder2.verticalLineToRelative(4.17f);
                    pathBuilder2.lineToRelative(1.59f, -1.59f);
                    pathBuilder2.lineTo(16.0f, 13.0f);
                    pathBuilder2.close();
                    ImageVector.Builder.m390addPathoIyEayM$default(builder, pathBuilder2._nodes, solidColor2);
                    imageVector = builder.build();
                    SimCardDownloadKt._simCardDownload = imageVector;
                }
            } else {
                ImageVector imageVector2 = SimCardKt._simCard;
                if (imageVector2 == null) {
                    ImageVector.Builder builder2 = new ImageVector.Builder("Outlined.SimCard", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96);
                    EmptyList emptyList2 = VectorKt.EmptyPath;
                    SolidColor solidColor3 = new SolidColor(Color.Black);
                    PathBuilder pathBuilder3 = new PathBuilder();
                    pathBuilder3.moveTo(18.0f, 2.0f);
                    pathBuilder3.horizontalLineToRelative(-8.0f);
                    pathBuilder3.lineTo(4.0f, 8.0f);
                    pathBuilder3.verticalLineToRelative(12.0f);
                    pathBuilder3.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
                    pathBuilder3.horizontalLineToRelative(12.0f);
                    pathBuilder3.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                    pathBuilder3.lineTo(20.0f, 4.0f);
                    pathBuilder3.curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(18.0f, 4.0f);
                    pathBuilder3.verticalLineToRelative(16.0f);
                    pathBuilder3.lineTo(6.0f, 20.0f);
                    pathBuilder3.lineTo(6.0f, 8.83f);
                    pathBuilder3.lineTo(10.83f, 4.0f);
                    pathBuilder3.lineTo(18.0f, 4.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(7.0f, 17.0f);
                    pathBuilder3.horizontalLineToRelative(2.0f);
                    pathBuilder3.verticalLineToRelative(2.0f);
                    pathBuilder3.lineTo(7.0f, 19.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(15.0f, 17.0f);
                    pathBuilder3.horizontalLineToRelative(2.0f);
                    pathBuilder3.verticalLineToRelative(2.0f);
                    pathBuilder3.horizontalLineToRelative(-2.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(7.0f, 11.0f);
                    pathBuilder3.horizontalLineToRelative(2.0f);
                    pathBuilder3.verticalLineToRelative(4.0f);
                    pathBuilder3.lineTo(7.0f, 15.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(11.0f, 15.0f);
                    pathBuilder3.horizontalLineToRelative(2.0f);
                    pathBuilder3.verticalLineToRelative(4.0f);
                    pathBuilder3.horizontalLineToRelative(-2.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(11.0f, 11.0f);
                    pathBuilder3.horizontalLineToRelative(2.0f);
                    pathBuilder3.verticalLineToRelative(2.0f);
                    pathBuilder3.horizontalLineToRelative(-2.0f);
                    pathBuilder3.close();
                    pathBuilder3.moveTo(15.0f, 11.0f);
                    pathBuilder3.horizontalLineToRelative(2.0f);
                    pathBuilder3.verticalLineToRelative(4.0f);
                    pathBuilder3.horizontalLineToRelative(-2.0f);
                    pathBuilder3.close();
                    ImageVector.Builder.m390addPathoIyEayM$default(builder2, pathBuilder3._nodes, solidColor3);
                    imageVector2 = builder2.build();
                    SimCardKt._simCard = imageVector2;
                }
                imageVector = imageVector2;
            }
            IconKt.SettingsIcon(imageVector, composerImpl, 0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settings.spa.network.SimsSectionKt$SimIcon$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SimsSectionKt.access$SimIcon(z, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1] */
    public static final MutableState phoneNumber(final SubscriptionInfo subInfo, Composer composer) {
        Intrinsics.checkNotNullParameter(subInfo, "subInfo");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-2041111341);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-403199783);
        boolean changed = composerImpl.changed(subInfo);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            Intrinsics.checkNotNullParameter(context, "<this>");
            final Flow subscriptionsChangedFlow = SubscriptionRepositoryKt.subscriptionsChangedFlow(context);
            final ?? r2 = new Flow() { // from class: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                /* renamed from: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ SubscriptionInfo $subscriptionInfo$inlined;
                    public final /* synthetic */ Context $this_phoneNumberFlow$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                    /* renamed from: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, Context context, SubscriptionInfo subscriptionInfo) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$this_phoneNumberFlow$inlined = context;
                        this.$subscriptionInfo$inlined = subscriptionInfo;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L47
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            kotlin.Unit r5 = (kotlin.Unit) r5
                            android.content.Context r5 = r4.$this_phoneNumberFlow$inlined
                            android.telephony.SubscriptionInfo r6 = r4.$subscriptionInfo$inlined
                            java.lang.String r5 = com.android.settings.network.SubscriptionUtil.getBidiFormattedPhoneNumber(r5, r6)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L47
                            return r1
                        L47:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, context, subInfo), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            rememberedValue = FlowKt.flowOn(new Flow() { // from class: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                /* renamed from: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                    /* renamed from: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        Object L$1;
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

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1$2$1 r0 = (com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1$2$1 r0 = new com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L49
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            r6 = r5
                            java.lang.String r6 = (java.lang.String) r6
                            if (r6 == 0) goto L49
                            int r6 = r6.length()
                            if (r6 != 0) goto L3e
                            goto L49
                        L3e:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L49
                            return r1
                        L49:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.SubscriptionRepositoryKt$phoneNumberFlow$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = r2.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, Dispatchers.Default);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle((Flow) rememberedValue, null, composerImpl, 56);
        composerImpl.end(false);
        return collectAsStateWithLifecycle;
    }
}
