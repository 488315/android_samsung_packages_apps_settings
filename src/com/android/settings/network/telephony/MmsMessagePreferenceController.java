package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;

import com.android.settings.network.MobileDataEnabledFlowKt;
import com.android.settingslib.spa.framework.util.FlowsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000F\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010"
                + "\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\bH\u0016J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\bJ\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0002J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\r"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001b"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/MmsMessagePreferenceController;",
            "Lcom/android/settings/network/telephony/TelephonyTogglePreferenceController;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "getDefaultDataSubId",
            "Lkotlin/Function0;",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V",
            "preferenceScreen",
            "Landroidx/preference/PreferenceScreen;",
            "telephonyManager",
            "Landroid/telephony/TelephonyManager;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "getAvailabilityStatus",
            "subId",
            "init",
            "isChecked",
            ApnSettings.MVNO_NONE,
            "isFallbackDataEnabled",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "setChecked",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class MmsMessagePreferenceController extends TelephonyTogglePreferenceController {
    public static final int $stable = 8;
    private final Function0 getDefaultDataSubId;
    private PreferenceScreen preferenceScreen;
    private TelephonyManager telephonyManager;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MmsMessagePreferenceController(Context context, String key) {
        this(context, key, null, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    private final boolean isFallbackDataEnabled() {
        int intValue = ((Number) this.getDefaultDataSubId.mo1068invoke()).intValue();
        if (intValue != this.mSubId) {
            TelephonyManager telephonyManager = this.telephonyManager;
            if (telephonyManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
                throw null;
            }
            if (telephonyManager.createForSubscriptionId(intValue).isDataEnabled()) {
                TelephonyManager telephonyManager2 = this.telephonyManager;
                if (telephonyManager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
                    throw null;
                }
                if (telephonyManager2.isMobileDataPolicyEnabled(3)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.preferenceScreen = screen;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        TelephonyManager telephonyManager;
        if (subId == -1 || (telephonyManager = this.telephonyManager) == null) {
            return 2;
        }
        if (telephonyManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
            throw null;
        }
        if (telephonyManager.isDataEnabled()) {
            return 2;
        }
        TelephonyManager telephonyManager2 = this.telephonyManager;
        if (telephonyManager2 != null) {
            return (!telephonyManager2.isApnMetered(2) || isFallbackDataEnabled()) ? 2 : 0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
        throw null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public final void init(int subId) {
        this.mSubId = subId;
        Object systemService =
                this.mContext.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) systemService).createForSubscriptionId(subId);
        Intrinsics.checkNotNullExpressionValue(
                createForSubscriptionId, "createForSubscriptionId(...)");
        this.telephonyManager = createForSubscriptionId;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        TelephonyManager telephonyManager = this.telephonyManager;
        if (telephonyManager != null) {
            return telephonyManager.isMobileDataPolicyEnabled(2);
        }
        Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
        throw null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        Flow mobileDataEnabledFlow =
                MobileDataEnabledFlowKt.mobileDataEnabledFlow(mContext, this.mSubId, true);
        Context mContext2 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
        FlowsKt.collectLatestWithLifecycle(
                FlowKt.combine(
                        mobileDataEnabledFlow,
                        SubscriptionRepositoryKt.subscriptionsChangedFlow(mContext2),
                        new MmsMessagePreferenceController$onViewCreated$1(3, null)),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new MmsMessagePreferenceController$onViewCreated$2(this, null));
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean isChecked) {
        TelephonyManager telephonyManager = this.telephonyManager;
        if (telephonyManager != null) {
            telephonyManager.setMobileDataPolicyEnabled(2, isChecked);
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
        throw null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* synthetic */ MmsMessagePreferenceController(
            Context context,
            String str,
            Function0 function0,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                str,
                (i & 4) != 0
                        ? new Function0() { // from class:
                                            // com.android.settings.network.telephony.MmsMessagePreferenceController.1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return Integer.valueOf(
                                        SubscriptionManager.getDefaultDataSubscriptionId());
                            }
                        }
                        : function0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MmsMessagePreferenceController(
            Context context, String key, Function0 getDefaultDataSubId) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(getDefaultDataSubId, "getDefaultDataSubId");
        this.getDefaultDataSubId = getDefaultDataSubId;
    }
}
