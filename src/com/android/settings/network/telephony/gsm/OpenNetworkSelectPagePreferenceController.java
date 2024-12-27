package com.android.settings.network.telephony.gsm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Settings;
import com.android.settings.network.telephony.AllowedNetworkTypesFlowKt;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.ServiceStateFlowKt;
import com.android.settings.network.telephony.TelephonyBasePreferenceController;
import com.android.settingslib.spa.framework.util.FlowsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000P\n"
                + "\u0002\u0018\u0002\n"
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
                + "\u0002\b\b\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002BQ\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u001a\b\u0002\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\n"
                + "0\t0\u0007\u0012\u001a\b\u0002\u0010\r"
                + "\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\f0"
                + "\t0\u0007¢\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\b¢\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016¢\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001aH\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\bH\u0016¢\u0006\u0004\b\u001f\u0010"
                + " R&\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\n"
                + "0\t0\u00078\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u000b\u0010!R&\u0010\r"
                + "\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\f0\t0\u00078\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\r"
                + "\u0010!R\u0018\u0010#\u001a\u0004\u0018\u00010\"8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n"
                + "\u0004\b#\u0010$¨\u0006%"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/gsm/OpenNetworkSelectPagePreferenceController;",
            "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;",
            "Lcom/android/settings/network/telephony/gsm/AutoSelectPreferenceController$OnNetworkSelectModeListener;",
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
            "<init>",
            "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V",
            "subId",
            "init",
            "(I)Lcom/android/settings/network/telephony/gsm/OpenNetworkSelectPagePreferenceController;",
            "getAvailabilityStatus",
            "(I)I",
            "Landroidx/preference/PreferenceScreen;",
            "screen",
            ApnSettings.MVNO_NONE,
            "displayPreference",
            "(Landroidx/preference/PreferenceScreen;)V",
            "Landroidx/lifecycle/LifecycleOwner;",
            "viewLifecycleOwner",
            "onViewCreated",
            "(Landroidx/lifecycle/LifecycleOwner;)V",
            "mode",
            "onNetworkSelectModeUpdated",
            "(I)V",
            "Lkotlin/jvm/functions/Function1;",
            "Landroidx/preference/Preference;",
            "preference",
            "Landroidx/preference/Preference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class OpenNetworkSelectPagePreferenceController
        extends TelephonyBasePreferenceController
        implements AutoSelectPreferenceController.OnNetworkSelectModeListener {
    public static final int $stable = 8;
    private final Function1 allowedNetworkTypesFlowFactory;
    private Preference preference;
    private final Function1 serviceStateFlowFactory;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.gsm.OpenNetworkSelectPagePreferenceController$1, reason: invalid class name */
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
    /* renamed from: com.android.settings.network.telephony.gsm.OpenNetworkSelectPagePreferenceController$2, reason: invalid class name */
    public /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl
            implements Function1 {
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return ServiceStateFlowKt.serviceStateFlow(
                    (Context) this.receiver, ((Number) obj).intValue());
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public OpenNetworkSelectPagePreferenceController(Context context, String key) {
        this(context, key, null, null, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        this.preference = findPreference;
        if (findPreference == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this.mContext, Settings.NetworkSelectActivity.class);
        intent.putExtra("android.provider.extra.SUB_ID", this.mSubId);
        findPreference.setIntent(intent);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        return MobileNetworkUtils.shouldDisplayNetworkSelectOptions(this.mContext, subId) ? 0 : 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public final OpenNetworkSelectPagePreferenceController init(int subId) {
        this.mSubId = subId;
        return this;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.telephony.gsm.AutoSelectPreferenceController.OnNetworkSelectModeListener
    public void onNetworkSelectModeUpdated(int mode) {
        Preference preference = this.preference;
        if (preference == null) {
            return;
        }
        preference.setEnabled(mode != 1);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        FlowsKt.collectLatestWithLifecycle(
                (Flow) this.allowedNetworkTypesFlowFactory.invoke(Integer.valueOf(this.mSubId)),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new OpenNetworkSelectPagePreferenceController$onViewCreated$1(this, null));
        FlowsKt.collectLatestWithLifecycle(
                (Flow) this.serviceStateFlowFactory.invoke(Integer.valueOf(this.mSubId)),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new OpenNetworkSelectPagePreferenceController$onViewCreated$2(this, null));
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public OpenNetworkSelectPagePreferenceController(
            Context context, String key, Function1 allowedNetworkTypesFlowFactory) {
        this(context, key, allowedNetworkTypesFlowFactory, null, 8, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(
                allowedNetworkTypesFlowFactory, "allowedNetworkTypesFlowFactory");
    }

    public OpenNetworkSelectPagePreferenceController(
            Context context,
            String str,
            Function1 function1,
            Function1 function12,
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
                        : function12);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OpenNetworkSelectPagePreferenceController(
            Context context,
            String key,
            Function1 allowedNetworkTypesFlowFactory,
            Function1 serviceStateFlowFactory) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(
                allowedNetworkTypesFlowFactory, "allowedNetworkTypesFlowFactory");
        Intrinsics.checkNotNullParameter(serviceStateFlowFactory, "serviceStateFlowFactory");
        this.allowedNetworkTypesFlowFactory = allowedNetworkTypesFlowFactory;
        this.serviceStateFlowFactory = serviceStateFlowFactory;
    }
}
