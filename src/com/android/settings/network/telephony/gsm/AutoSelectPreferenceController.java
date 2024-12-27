package com.android.settings.network.telephony.gsm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.TelephonyManager;

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
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.telephony.AllowedNetworkTypesFlowKt;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.ServiceStateFlowKt;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.spa.framework.compose.OverridableFlow;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.sec.ims.settings.ImsProfile;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.internal.ContextScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000t\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\b\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010!\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\f\b\u0007\u0018\u0000"
                + " A2\u00020\u0001:\u0002ABBg\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u001a\b\u0002\u0010\n"
                + "\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020"
                + "\t0\b0\u0006\u0012\u001a\b\u0002\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\u000b0\b0\u0006\u0012\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r"
                + "0\u0006¢\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0007¢\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\u0016H\u0017¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u0019H\u0016¢\u0006\u0004\b\u001b\u0010\u001cJ\u0015\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001d¢\u0006\u0004\b\u001f\u0010"
                + " J\u0018\u0010\"\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u000bH\u0082@¢\u0006\u0004\b\"\u0010#J\u000f\u0010%\u001a\u00020$H\u0002¢\u0006\u0004\b%\u0010&J\u001e\u0010)\u001a\u00020\u00162\f\u0010(\u001a\b\u0012\u0004\u0012\u00020$0'H\u0082@¢\u0006\u0004\b)\u0010*J\u0017\u0010,\u001a\u00020\u00162\u0006\u0010+\u001a\u00020$H\u0002¢\u0006\u0004\b,\u0010-J\u000f\u0010.\u001a\u00020\u0016H\u0002¢\u0006\u0004\b.\u0010/J\u000f\u00100\u001a\u00020\u0016H\u0002¢\u0006\u0004\b0\u0010/R&\u0010\n"
                + "\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00068\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\n"
                + "\u00101R&\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\u000b0\b0\u00068\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\f\u00101R"
                + " \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r"
                + "0\u00068\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u000e\u00101R\u0016\u00103\u001a\u0002028\u0002@\u0002X\u0082.¢\u0006\u0006\n"
                + "\u0004\b3\u00104R\u001a\u00106\u001a\b\u0012\u0004\u0012\u00020\u001d058\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b6\u00107R*\u00109\u001a\u0004\u0018\u0001088\u0006@\u0006X\u0087\u000e¢\u0006\u0018\n"
                + "\u0004\b9\u0010:\u0012\u0004\b?\u0010/\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u0016\u0010\u0011\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n"
                + "\u0004\b\u0011\u0010@¨\u0006D²\u0006\u000e\u0010+\u001a\u0004\u0018\u00010$8\n"
                + "X\u008a\u0084\u0002²\u0006\f\u0010C\u001a\u00020\u00048\n"
                + "X\u008a\u0084\u0002"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/gsm/AutoSelectPreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "Landroid/content/Context;",
            "context",
            ApnSettings.MVNO_NONE,
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            "Lkotlin/Function1;",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/Flow;",
            ApnSettings.MVNO_NONE,
            "allowedNetworkTypesFlowFactory",
            "Landroid/telephony/ServiceState;",
            "serviceStateFlowFactory",
            "Landroid/os/PersistableBundle;",
            "getConfigForSubId",
            "<init>",
            "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V",
            "subId",
            "init",
            "(I)Lcom/android/settings/network/telephony/gsm/AutoSelectPreferenceController;",
            "getAvailabilityStatus",
            "()I",
            ApnSettings.MVNO_NONE,
            "Content",
            "(Landroidx/compose/runtime/Composer;I)V",
            "Landroidx/lifecycle/LifecycleOwner;",
            "viewLifecycleOwner",
            "onViewCreated",
            "(Landroidx/lifecycle/LifecycleOwner;)V",
            "Lcom/android/settings/network/telephony/gsm/AutoSelectPreferenceController$OnNetworkSelectModeListener;",
            "listener",
            "addListener",
            "(Lcom/android/settings/network/telephony/gsm/AutoSelectPreferenceController$OnNetworkSelectModeListener;)Lcom/android/settings/network/telephony/gsm/AutoSelectPreferenceController;",
            "serviceState",
            "getDisallowedSummary",
            "(Landroid/telephony/ServiceState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            ApnSettings.MVNO_NONE,
            "onlyAutoSelectInHome",
            "()Z",
            "Lcom/android/settingslib/spa/framework/compose/OverridableFlow;",
            "overrideChannel",
            "setAutomaticSelectionMode",
            "(Lcom/android/settingslib/spa/framework/compose/OverridableFlow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "isAuto",
            "updateListenerValue",
            "(Z)V",
            "showAutoSelectProgressBar",
            "()V",
            "dismissProgressBar",
            "Lkotlin/jvm/functions/Function1;",
            "Landroid/telephony/TelephonyManager;",
            "telephonyManager",
            "Landroid/telephony/TelephonyManager;",
            ApnSettings.MVNO_NONE,
            "listeners",
            "Ljava/util/List;",
            "Landroid/app/ProgressDialog;",
            "progressDialog",
            "Landroid/app/ProgressDialog;",
            "getProgressDialog",
            "()Landroid/app/ProgressDialog;",
            "setProgressDialog",
            "(Landroid/app/ProgressDialog;)V",
            "getProgressDialog$annotations",
            ImsProfile.TIMER_NAME_I,
            "Companion",
            "OnNetworkSelectModeListener",
            "disallowedSummary",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class AutoSelectPreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;
    private static final long MINIMUM_DIALOG_TIME;
    private final Function1 allowedNetworkTypesFlowFactory;
    private final Function1 getConfigForSubId;
    private final List<OnNetworkSelectModeListener> listeners;
    private ProgressDialog progressDialog;
    private final Function1 serviceStateFlowFactory;
    private int subId;
    private TelephonyManager telephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$1, reason: invalid class name */
    public /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl
            implements Function1 {
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return AllowedNetworkTypesFlowKt.allowedNetworkTypesFlow(
                    (Context) this.receiver, ((Number) obj).intValue());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$2, reason: invalid class name */
    public /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl
            implements Function1 {
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return ServiceStateFlowKt.serviceStateFlow(
                    (Context) this.receiver, ((Number) obj).intValue());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnNetworkSelectModeListener {
        void onNetworkSelectModeUpdated(int i);
    }

    static {
        int i = Duration.$r8$clinit;
        MINIMUM_DIALOG_TIME = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AutoSelectPreferenceController(Context context, String key) {
        this(context, key, null, null, null, 28, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Boolean Content$lambda$3(State state) {
        return (Boolean) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String Content$lambda$4(State state) {
        return (String) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object Content$updateListenerValue(
            AutoSelectPreferenceController autoSelectPreferenceController,
            boolean z,
            Continuation continuation) {
        autoSelectPreferenceController.updateListenerValue(z);
        return Unit.INSTANCE;
    }

    private final void dismissProgressBar() {
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        try {
            ProgressDialog progressDialog2 = this.progressDialog;
            if (progressDialog2 != null) {
                progressDialog2.dismiss();
            }
        } catch (IllegalArgumentException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getDisallowedSummary(
            android.telephony.ServiceState r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$1 r0 = (com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$1 r0 = new com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L27:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.scheduling.DefaultScheduler r7 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$2 r2 = new com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$getDisallowedSummary$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L43
            return r1
        L43:
            java.lang.String r5 = "withContext(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r5)
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.gsm.AutoSelectPreferenceController.getDisallowedSummary(android.telephony.ServiceState,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean onlyAutoSelectInHome() {
        return ((PersistableBundle) this.getConfigForSubId.invoke(Integer.valueOf(this.subId)))
                .getBoolean("only_auto_select_in_home_network");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object setAutomaticSelectionMode(
            com.android.settingslib.spa.framework.compose.OverridableFlow r6,
            kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$1 r0 = (com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$1 r0 = new com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.settingslib.spa.framework.compose.OverridableFlow r6 = (com.android.settingslib.spa.framework.compose.OverridableFlow) r6
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController r5 = (com.android.settings.network.telephony.gsm.AutoSelectPreferenceController) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L53
        L30:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L38:
            kotlin.ResultKt.throwOnFailure(r7)
            r5.showAutoSelectProgressBar()
            kotlinx.coroutines.scheduling.DefaultScheduler r7 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$2 r2 = new com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$setAutomaticSelectionMode$2
            r4 = 0
            r2.<init>(r5, r4)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L53
            return r1
        L53:
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            kotlinx.coroutines.channels.BufferedChannel r6 = r6.overrideChannel
            r6.mo1469trySendJP2dKIU(r7)
            r5.dismissProgressBar()
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.gsm.AutoSelectPreferenceController.setAutomaticSelectionMode(com.android.settingslib.spa.framework.compose.OverridableFlow,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void showAutoSelectProgressBar() {
        if (this.progressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this.mContext);
            progressDialog.setMessage(
                    this.mContext.getResources().getString(R.string.register_automatically));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            this.progressDialog = progressDialog;
        }
        ProgressDialog progressDialog2 = this.progressDialog;
        if (progressDialog2 != null) {
            progressDialog2.show();
        }
    }

    private final void updateListenerValue(boolean isAuto) {
        Iterator<OnNetworkSelectModeListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onNetworkSelectModeUpdated(isAuto ? 1 : 2);
        }
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1355013747);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue =
                    PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                            EffectsKt.createCompositionCoroutineScope(
                                    EmptyCoroutineContext.INSTANCE, composerImpl),
                            composerImpl);
        }
        CoroutineScope coroutineScope =
                ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        composerImpl.startReplaceGroup(-1614894151);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    FlowKt.filterNotNull(
                            FlowKt.stateIn(
                                    (Flow)
                                            this.serviceStateFlowFactory.invoke(
                                                    Integer.valueOf(this.subId)),
                                    coroutineScope,
                                    SharingStarted.Companion.Lazily,
                                    null));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        final Flow flow = (Flow) rememberedValue2;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-1614893948);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == composer$Companion$Empty$1) {
            rememberedValue3 =
                    new OverridableFlow(
                            new Flow() { // from class:
                                // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    @Metadata(
                                            k = 3,
                                            mv = {1, 9, 0},
                                            xi = 48)
                                    /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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
                                    public final java.lang.Object emit(
                                            java.lang.Object r5,
                                            kotlin.coroutines.Continuation r6) {
                                        /*
                                            r4 = this;
                                            boolean r0 = r6 instanceof com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                            if (r0 == 0) goto L13
                                            r0 = r6
                                            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                            int r1 = r0.label
                                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                            r3 = r1 & r2
                                            if (r3 == 0) goto L13
                                            int r1 = r1 - r2
                                            r0.label = r1
                                            goto L18
                                        L13:
                                            com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1$2$1
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
                                            android.telephony.ServiceState r5 = (android.telephony.ServiceState) r5
                                            boolean r5 = r5.getIsManualSelection()
                                            r5 = r5 ^ r3
                                            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
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
                                                    + " com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                    + " kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(
                                        FlowCollector flowCollector, Continuation continuation) {
                                    Object collect =
                                            Flow.this.collect(
                                                    new AnonymousClass2(flowCollector),
                                                    continuation);
                                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                            ? collect
                                            : Unit.INSTANCE;
                                }
                            });
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        final OverridableFlow overridableFlow = (OverridableFlow) rememberedValue3;
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        FlowKt.onEach(
                                overridableFlow.flow,
                                new AutoSelectPreferenceController$Content$isAuto$2(
                                        2,
                                        this,
                                        AutoSelectPreferenceController.class,
                                        "updateListenerValue",
                                        "updateListenerValue(Z)V",
                                        4)),
                        null,
                        composerImpl,
                        56);
        final MutableState collectAsStateWithLifecycle2 =
                FlowExtKt.collectAsStateWithLifecycle(
                        new Flow() { // from class:
                            // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ AutoSelectPreferenceController
                                        $receiver$inlined;
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1$2$1, reason: invalid class name */
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
                                        AutoSelectPreferenceController
                                                autoSelectPreferenceController) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.$receiver$inlined = autoSelectPreferenceController;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:19:0x005c A[RETURN] */
                                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                                    /*
                                        r6 = this;
                                        boolean r0 = r8 instanceof com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r8
                                        com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1$2$1
                                        r0.<init>(r8)
                                    L18:
                                        java.lang.Object r8 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 2
                                        r4 = 1
                                        if (r2 == 0) goto L3a
                                        if (r2 == r4) goto L32
                                        if (r2 != r3) goto L2a
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        goto L5d
                                    L2a:
                                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                        r6.<init>(r7)
                                        throw r6
                                    L32:
                                        java.lang.Object r6 = r0.L$0
                                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        goto L51
                                    L3a:
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        android.telephony.ServiceState r7 = (android.telephony.ServiceState) r7
                                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                                        r0.L$0 = r8
                                        r0.label = r4
                                        com.android.settings.network.telephony.gsm.AutoSelectPreferenceController r6 = r6.$receiver$inlined
                                        java.lang.Object r6 = com.android.settings.network.telephony.gsm.AutoSelectPreferenceController.access$getDisallowedSummary(r6, r7, r0)
                                        if (r6 != r1) goto L4e
                                        return r1
                                    L4e:
                                        r5 = r8
                                        r8 = r6
                                        r6 = r5
                                    L51:
                                        r7 = 0
                                        r0.L$0 = r7
                                        r0.label = r3
                                        java.lang.Object r6 = r6.emit(r8, r0)
                                        if (r6 != r1) goto L5d
                                        return r1
                                    L5d:
                                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                        return r6
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                Object collect =
                                        Flow.this.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        },
                        ApnSettings.MVNO_NONE,
                        composerImpl,
                        56);
        final ContextScope contextScope = (ContextScope) coroutineScope;
        SwitchPreferenceKt.SwitchPreference(
                new SwitchPreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle2,
                        collectAsStateWithLifecycle,
                        contextScope,
                        this,
                        overridableFlow) { // from class:
                    // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$1
                    public final Function0 changeable;
                    public final Function0 checked;
                    public final Function1 onCheckedChange;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.select_automatically);
                        this.summary =
                                new Function0() { // from class:
                                    // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$1$summary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        String Content$lambda$4;
                                        Content$lambda$4 =
                                                AutoSelectPreferenceController.Content$lambda$4(
                                                        collectAsStateWithLifecycle2);
                                        return Content$lambda$4;
                                    }
                                };
                        this.changeable =
                                new Function0() { // from class:
                                    // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$1$changeable$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        String Content$lambda$4;
                                        Content$lambda$4 =
                                                AutoSelectPreferenceController.Content$lambda$4(
                                                        collectAsStateWithLifecycle2);
                                        return Boolean.valueOf(Content$lambda$4.length() == 0);
                                    }
                                };
                        this.checked =
                                new Function0() { // from class:
                                    // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$1$checked$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        Boolean Content$lambda$3;
                                        Content$lambda$3 =
                                                AutoSelectPreferenceController.Content$lambda$3(
                                                        collectAsStateWithLifecycle);
                                        return Content$lambda$3;
                                    }
                                };
                        this.onCheckedChange =
                                new Function1() { // from class:
                                    // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$1$onCheckedChange$1

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
                                    /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$1$onCheckedChange$1$1, reason: invalid class name */
                                    final class AnonymousClass1 extends SuspendLambda
                                            implements Function2 {
                                        final /* synthetic */ OverridableFlow
                                                $isAutoOverridableFlow;
                                        int label;
                                        final /* synthetic */ AutoSelectPreferenceController this$0;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass1(
                                                AutoSelectPreferenceController
                                                        autoSelectPreferenceController,
                                                OverridableFlow overridableFlow,
                                                Continuation continuation) {
                                            super(2, continuation);
                                            this.this$0 = autoSelectPreferenceController;
                                            this.$isAutoOverridableFlow = overridableFlow;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(
                                                Object obj, Continuation continuation) {
                                            return new AnonymousClass1(
                                                    this.this$0,
                                                    this.$isAutoOverridableFlow,
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
                                            Object automaticSelectionMode;
                                            CoroutineSingletons coroutineSingletons =
                                                    CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                AutoSelectPreferenceController
                                                        autoSelectPreferenceController =
                                                                this.this$0;
                                                OverridableFlow overridableFlow =
                                                        this.$isAutoOverridableFlow;
                                                this.label = 1;
                                                automaticSelectionMode =
                                                        autoSelectPreferenceController
                                                                .setAutomaticSelectionMode(
                                                                        overridableFlow, this);
                                                if (automaticSelectionMode == coroutineSingletons) {
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
                                            return Unit.INSTANCE;
                                        }
                                    }

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Context context;
                                        Context context2;
                                        int i2;
                                        if (((Boolean) obj).booleanValue()) {
                                            BuildersKt.launch$default(
                                                    contextScope,
                                                    null,
                                                    null,
                                                    new AnonymousClass1(
                                                            this, overridableFlow, null),
                                                    3);
                                        } else {
                                            context =
                                                    ((AbstractPreferenceController) this).mContext;
                                            Intent intent = new Intent();
                                            AutoSelectPreferenceController
                                                    autoSelectPreferenceController = this;
                                            context2 =
                                                    ((AbstractPreferenceController)
                                                                    autoSelectPreferenceController)
                                                            .mContext;
                                            intent.setClass(
                                                    context2, Settings.NetworkSelectActivity.class);
                                            i2 = autoSelectPreferenceController.subId;
                                            intent.putExtra("android.provider.extra.SUB_ID", i2);
                                            context.startActivity(intent);
                                        }
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$Content$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AutoSelectPreferenceController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final AutoSelectPreferenceController addListener(OnNetworkSelectModeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.add(listener);
        return this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return MobileNetworkUtils.shouldDisplayNetworkSelectOptions(this.mContext, this.subId)
                ? 0
                : 2;
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

    public final ProgressDialog getProgressDialog() {
        return this.progressDialog;
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

    public final AutoSelectPreferenceController init(int subId) {
        this.subId = subId;
        Object systemService =
                this.mContext.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) systemService).createForSubscriptionId(subId);
        Intrinsics.checkNotNullExpressionValue(
                createForSubscriptionId, "createForSubscriptionId(...)");
        this.telephonyManager = createForSubscriptionId;
        return this;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        FlowsKt.collectLatestWithLifecycle(
                (Flow) this.allowedNetworkTypesFlowFactory.invoke(Integer.valueOf(this.subId)),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new AutoSelectPreferenceController$onViewCreated$1(this, null));
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public final void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
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
    public AutoSelectPreferenceController(
            Context context, String key, Function1 allowedNetworkTypesFlowFactory) {
        this(context, key, allowedNetworkTypesFlowFactory, null, null, 24, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(
                allowedNetworkTypesFlowFactory, "allowedNetworkTypesFlowFactory");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AutoSelectPreferenceController(
            Context context,
            String key,
            Function1 allowedNetworkTypesFlowFactory,
            Function1 serviceStateFlowFactory) {
        this(context, key, allowedNetworkTypesFlowFactory, serviceStateFlowFactory, null, 16, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(
                allowedNetworkTypesFlowFactory, "allowedNetworkTypesFlowFactory");
        Intrinsics.checkNotNullParameter(serviceStateFlowFactory, "serviceStateFlowFactory");
    }

    public AutoSelectPreferenceController(
            final Context context,
            String str,
            Function1 function1,
            Function1 function12,
            Function1 function13,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                str,
                (i & 4) != 0
                        ? new AnonymousClass1(
                                1,
                                context,
                                AllowedNetworkTypesFlowKt.class,
                                "allowedNetworkTypesFlow",
                                "allowedNetworkTypesFlow(Landroid/content/Context;I)Lkotlinx/coroutines/flow/Flow;",
                                1)
                        : function1,
                (i & 8) != 0
                        ? new AnonymousClass2(
                                1,
                                context,
                                ServiceStateFlowKt.class,
                                "serviceStateFlow",
                                "serviceStateFlow(Landroid/content/Context;I)Lkotlinx/coroutines/flow/Flow;",
                                1)
                        : function12,
                (i & 16) != 0
                        ? new Function1() { // from class:
                            // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                int intValue = ((Number) obj).intValue();
                                CarrierConfigCache.getInstance(context).getClass();
                                PersistableBundle configForSubId =
                                        CarrierConfigCache.getConfigForSubId(intValue);
                                Intrinsics.checkNotNullExpressionValue(
                                        configForSubId, "getConfigForSubId(...)");
                                return configForSubId;
                            }
                        }
                        : function13);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoSelectPreferenceController(
            Context context,
            String key,
            Function1 allowedNetworkTypesFlowFactory,
            Function1 serviceStateFlowFactory,
            Function1 getConfigForSubId) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(
                allowedNetworkTypesFlowFactory, "allowedNetworkTypesFlowFactory");
        Intrinsics.checkNotNullParameter(serviceStateFlowFactory, "serviceStateFlowFactory");
        Intrinsics.checkNotNullParameter(getConfigForSubId, "getConfigForSubId");
        this.allowedNetworkTypesFlowFactory = allowedNetworkTypesFlowFactory;
        this.serviceStateFlowFactory = serviceStateFlowFactory;
        this.getConfigForSubId = getConfigForSubId;
        this.listeners = new ArrayList();
        this.subId = -1;
    }

    public static /* synthetic */ void getProgressDialog$annotations() {}
}
