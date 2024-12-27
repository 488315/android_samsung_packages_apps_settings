package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.NetworkPolicy;
import android.net.NetworkTemplate;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.datausage.lib.INetworkCycleDataRepository;
import com.android.settings.datausage.lib.NetworkCycleChartData;
import com.android.settings.datausage.lib.NetworkCycleDataRepository;
import com.android.settings.datausage.lib.NetworkUsageData;
import com.android.settingslib.NetworkPolicyEditor;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000N\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r"
                + "\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\fJ\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001aR\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001b"
        },
        d2 = {
            "Lcom/android/settings/datausage/ChartDataUsagePreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "preference",
            "Lcom/android/settings/datausage/ChartDataUsagePreference;",
            "repository",
            "Lcom/android/settings/datausage/lib/INetworkCycleDataRepository;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "init",
            "template",
            "Landroid/net/NetworkTemplate;",
            "setBillingCycleModifiable",
            "isModifiable",
            ApnSettings.MVNO_NONE,
            "setChartColor",
            "update",
            "chartData",
            "Lcom/android/settings/datausage/lib/NetworkCycleChartData;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class ChartDataUsagePreferenceController extends BasePreferenceController {
    public static final int $stable = 8;
    private ChartDataUsagePreference preference;
    private INetworkCycleDataRepository repository;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChartDataUsagePreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (ChartDataUsagePreference) findPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(NetworkTemplate template) {
        Intrinsics.checkNotNullParameter(template, "template");
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        this.repository = new NetworkCycleDataRepository(mContext, template);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public final void setBillingCycleModifiable(boolean isModifiable) {
        ChartDataUsagePreference chartDataUsagePreference = this.preference;
        NetworkPolicy networkPolicy = null;
        if (chartDataUsagePreference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        if (isModifiable) {
            INetworkCycleDataRepository iNetworkCycleDataRepository = this.repository;
            if (iNetworkCycleDataRepository == null) {
                Intrinsics.throwUninitializedPropertyAccessException("repository");
                throw null;
            }
            NetworkCycleDataRepository networkCycleDataRepository =
                    (NetworkCycleDataRepository) iNetworkCycleDataRepository;
            NetworkPolicyEditor networkPolicyEditor =
                    new NetworkPolicyEditor(networkCycleDataRepository.policyManager);
            networkPolicyEditor.read();
            networkPolicy =
                    networkPolicyEditor.getPolicy(networkCycleDataRepository.networkTemplate);
        }
        chartDataUsagePreference.mPolicy = networkPolicy;
        chartDataUsagePreference.notifyChanged();
    }

    public final void setChartColor() {
        int color = this.mContext.getResources().getColor(R.color.sec_data_usage_blue_color);
        Color.argb(127, Color.red(color), Color.green(color), Color.blue(color));
        ChartDataUsagePreference chartDataUsagePreference = this.preference;
        if (chartDataUsagePreference != null) {
            chartDataUsagePreference.notifyChanged();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public final void update(NetworkCycleChartData chartData) {
        Intrinsics.checkNotNullParameter(chartData, "chartData");
        ChartDataUsagePreference chartDataUsagePreference = this.preference;
        if (chartDataUsagePreference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        NetworkUsageData networkUsageData = chartData.total;
        long j = networkUsageData.startTime;
        long j2 = networkUsageData.endTime;
        chartDataUsagePreference.mStart = j;
        chartDataUsagePreference.mEnd = j2;
        chartDataUsagePreference.notifyChanged();
        ChartDataUsagePreference chartDataUsagePreference2 = this.preference;
        if (chartDataUsagePreference2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        chartDataUsagePreference2.mNetworkCycleChartData = chartData;
        chartDataUsagePreference2.notifyChanged();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
