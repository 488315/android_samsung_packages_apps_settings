package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.compose.runtime.State;

import com.android.settings.spa.preference.ComposePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00008\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\r"
                + "\u0010\r"
                + "\u001a\u00020\u000eH\u0017¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\fH\u0016J\u001a\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010"
                + "\t\u001a\u00020\n"
                + "H\u0007R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\n"
                + "X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0012²\u0006\n"
                + "\u0010\u0013\u001a\u00020\u0014X\u008a\u0084\u0002²\u0006\n"
                + "\u0010\u0015\u001a\u00020\u0014X\u008a\u0084\u0002"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/NrAdvancedCallingPreferenceController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "callStateRepository",
            "Lcom/android/settings/network/telephony/CallStateRepository;",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/android/settings/network/telephony/CallStateRepository;)V",
            "repository",
            "Lcom/android/settings/network/telephony/VoNrRepository;",
            "subId",
            ApnSettings.MVNO_NONE,
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "getAvailabilityStatus",
            "init",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core",
            "isInCall",
            ApnSettings.MVNO_NONE,
            "isEnabled"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class NrAdvancedCallingPreferenceController extends ComposePreferenceController {
    public static final int $stable = 8;
    private final CallStateRepository callStateRepository;
    private VoNrRepository repository;
    private int subId;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NrAdvancedCallingPreferenceController(Context context, String key) {
        this(context, key, null, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean Content$lambda$1(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean Content$lambda$3(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    public static /* synthetic */ void init$default(
            NrAdvancedCallingPreferenceController nrAdvancedCallingPreferenceController,
            int i,
            VoNrRepository voNrRepository,
            int i2,
            Object obj) {
        if ((i2 & 2) != 0) {
            Context mContext = nrAdvancedCallingPreferenceController.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            voNrRepository = new VoNrRepository(mContext, i);
        }
        nrAdvancedCallingPreferenceController.init(i, voNrRepository);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0066, code lost:

       if (r0 == null) goto L11;
    */
    @Override // com.android.settings.spa.preference.ComposePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void Content(androidx.compose.runtime.Composer r11, final int r12) {
        /*
            r10 = this;
            androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
            r0 = -1672793652(0xffffffff9c4b31cc, float:-6.7231427E-22)
            r11.startRestartGroup(r0)
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            r0 = 2132024071(0x7f141b07, float:1.9686608E38)
            java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r11, r0)
            r0 = 1647207071(0x622e629f, float:8.0421E20)
            r11.startReplaceGroup(r0)
            java.lang.Object r0 = r11.rememberedValue()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r1) goto L28
            com.android.settings.network.telephony.CallStateRepository r0 = r10.callStateRepository
            kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r0 = r0.isInCallFlow()
            r11.updateRememberedValue(r0)
        L28:
            kotlinx.coroutines.flow.Flow r0 = (kotlinx.coroutines.flow.Flow) r0
            r7 = 0
            r11.end(r7)
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            r4 = 56
            androidx.compose.runtime.MutableState r5 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r0, r3, r11, r4)
            r0 = 1647207207(0x622e6327, float:8.0421954E20)
            r11.startReplaceGroup(r0)
            java.lang.Object r0 = r11.rememberedValue()
            if (r0 != r1) goto L70
            com.android.settings.network.telephony.VoNrRepository r0 = r10.repository
            if (r0 == 0) goto L68
            android.content.Context r6 = r0.context
            kotlinx.coroutines.flow.Flow r6 = com.android.settings.network.telephony.SubscriptionRepositoryKt.subscriptionsChangedFlow(r6)
            com.android.settings.network.telephony.VoNrRepository$isVoNrEnabledFlow$$inlined$map$1 r8 = new com.android.settings.network.telephony.VoNrRepository$isVoNrEnabledFlow$$inlined$map$1
            r8.<init>()
            r6 = -1
            kotlinx.coroutines.flow.Flow r6 = kotlinx.coroutines.flow.FlowKt.buffer$default(r8, r6)
            com.android.settings.network.telephony.VoNrRepository$isVoNrEnabledFlow$2 r8 = new com.android.settings.network.telephony.VoNrRepository$isVoNrEnabledFlow$2
            r9 = 0
            r8.<init>(r0, r9)
            kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r0 = kotlinx.coroutines.flow.FlowKt.onEach(r6, r8)
            kotlinx.coroutines.scheduling.DefaultScheduler r6 = kotlinx.coroutines.Dispatchers.Default
            kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.flowOn(r0, r6)
            if (r0 != 0) goto L6d
        L68:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r0 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r0.<init>(r3)
        L6d:
            r11.updateRememberedValue(r0)
        L70:
            kotlinx.coroutines.flow.Flow r0 = (kotlinx.coroutines.flow.Flow) r0
            r11.end(r7)
            androidx.compose.runtime.MutableState r4 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r0, r3, r11, r4)
            java.lang.Object r0 = r11.rememberedValue()
            if (r0 != r1) goto L89
            kotlin.coroutines.EmptyCoroutineContext r0 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            kotlinx.coroutines.internal.ContextScope r0 = androidx.compose.runtime.EffectsKt.createCompositionCoroutineScope(r0, r11)
            androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller r0 = androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(r0, r11)
        L89:
            androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller r0 = (androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller) r0
            kotlinx.coroutines.CoroutineScope r0 = r0.coroutineScope
            com.android.settings.network.telephony.NrAdvancedCallingPreferenceController$Content$1 r8 = new com.android.settings.network.telephony.NrAdvancedCallingPreferenceController$Content$1
            r6 = r0
            kotlinx.coroutines.internal.ContextScope r6 = (kotlinx.coroutines.internal.ContextScope) r6
            r0 = r8
            r1 = r11
            r3 = r5
            r5 = r6
            r6 = r10
            r0.<init>(r1, r2, r3, r4, r5, r6)
            com.android.settingslib.spa.widget.preference.SwitchPreferenceKt.SwitchPreference(r8, r11, r7)
            androidx.compose.runtime.RecomposeScopeImpl r11 = r11.endRestartGroup()
            if (r11 == 0) goto Laa
            com.android.settings.network.telephony.NrAdvancedCallingPreferenceController$Content$2 r0 = new com.android.settings.network.telephony.NrAdvancedCallingPreferenceController$Content$2
            r0.<init>()
            r11.block = r0
        Laa:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.NrAdvancedCallingPreferenceController.Content(androidx.compose.runtime.Composer,"
                    + " int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0073, code lost:

       if (r8 != true) goto L30;
    */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r8 = this;
            com.android.settings.network.telephony.VoNrRepository r8 = r8.repository
            r0 = 0
            if (r8 == 0) goto L76
            int r1 = r8.subId
            boolean r2 = android.telephony.SubscriptionManager.isValidSubscriptionId(r1)
            r3 = 1
            if (r2 == 0) goto L72
            android.telephony.TelephonyManager r2 = r8.telephonyManager
            long r4 = r2.getSupportedRadioAccessFamily()
            r6 = 524288(0x80000, double:2.590327E-318)
            long r4 = r4 & r6
            r6 = 0
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L20
            r2 = r3
            goto L21
        L20:
            r2 = r0
        L21:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "["
            r4.<init>(r5)
            r4.append(r1)
            java.lang.String r5 = "] has5gCapability: "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "VoNrRepository"
            android.util.Log.d(r5, r4)
            if (r2 != 0) goto L3f
            goto L72
        L3f:
            android.telephony.CarrierConfigManager r8 = r8.carrierConfigManager
            java.lang.String r2 = "vonr_enabled_bool"
            java.lang.String r4 = "vonr_setting_visibility_bool"
            java.lang.String r5 = "carrier_nr_availabilities_int_array"
            java.lang.String[] r6 = new java.lang.String[]{r2, r4, r5}
            java.util.List r6 = kotlin.collections.CollectionsKt__CollectionsKt.listOf(r6)
            android.os.PersistableBundle r8 = com.android.settings.network.telephony.CarrierConfigManagerExtKt.safeGetConfig(r8, r6, r1)
            boolean r1 = r8.getBoolean(r2)
            if (r1 == 0) goto L72
            boolean r1 = r8.getBoolean(r4)
            if (r1 == 0) goto L72
            int[] r8 = r8.getIntArray(r5)
            if (r8 == 0) goto L6d
            int r8 = r8.length
            if (r8 != 0) goto L6a
            r8 = r3
            goto L6b
        L6a:
            r8 = r0
        L6b:
            r8 = r8 ^ r3
            goto L6e
        L6d:
            r8 = r0
        L6e:
            if (r8 == 0) goto L72
            r8 = r3
            goto L73
        L72:
            r8 = r0
        L73:
            if (r8 != r3) goto L76
            goto L77
        L76:
            r3 = r0
        L77:
            if (r3 == 0) goto L7a
            goto L7b
        L7a:
            r0 = 2
        L7b:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.NrAdvancedCallingPreferenceController.getAvailabilityStatus():int");
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

    public final void init(int i) {
        init$default(this, i, null, 2, null);
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

    public /* synthetic */ NrAdvancedCallingPreferenceController(
            Context context,
            String str,
            CallStateRepository callStateRepository,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i & 4) != 0 ? new CallStateRepository(context) : callStateRepository);
    }

    public final void init(int subId, VoNrRepository repository) {
        Intrinsics.checkNotNullParameter(repository, "repository");
        this.subId = subId;
        this.repository = repository;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NrAdvancedCallingPreferenceController(
            Context context, String key, CallStateRepository callStateRepository) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callStateRepository, "callStateRepository");
        this.callStateRepository = callStateRepository;
        this.subId = -1;
    }
}
