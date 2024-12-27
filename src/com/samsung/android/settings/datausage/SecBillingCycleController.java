package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkPolicy;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settingslib.NetworkPolicyEditor;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBillingCycleController extends SecCustomPreferenceController {
    private static final String TAG = "SecBillingCycleSettings.SecBillingCycleController";
    private SecBillingCycleSettings mParent;

    public SecBillingCycleController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SecBillingCycleManager.getInstance(this.mContext).getAvailableBillingCycleSetting()
                ? 0
                : 3;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        int cycleDay = secBillingCycleManager.getCycleDay(secBillingCycleManager.mNetworkTemplate);
        if (cycleDay != -1) {
            return this.mContext.getResources()
                    .getStringArray(R.array.data_usage_billing_cycle_summary)[cycleDay - 1];
        }
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        builder.setValue(
                Integer.valueOf(
                        secBillingCycleManager.getCycleDay(
                                secBillingCycleManager.mNetworkTemplate)));
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(getMetricsCategory(), 7121);
        SecBillingCycleSettings secBillingCycleSettings = this.mParent;
        SecPreference secPreference = (SecPreference) preference;
        int i = SecCycleEditorFragment.$r8$clinit;
        if (!secBillingCycleSettings.isAdded()) {
            return true;
        }
        Bundle bundle = new Bundle();
        SecCycleEditorFragment secCycleEditorFragment = new SecCycleEditorFragment();
        secCycleEditorFragment.mAnchorView = secPreference;
        secCycleEditorFragment.setArguments(bundle);
        secCycleEditorFragment.setTargetFragment(secBillingCycleSettings, 0);
        secCycleEditorFragment.show(secBillingCycleSettings.getFragmentManager(), "cycleEditor");
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setParentFragment(SecBillingCycleSettings secBillingCycleSettings) {
        this.mParent = secBillingCycleSettings;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        ControlResult.ErrorCode errorCode = ControlResult.ErrorCode.INVALID_DATA;
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        boolean availableBillingCycleSetting =
                SecBillingCycleManager.getInstance(this.mContext).getAvailableBillingCycleSetting();
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        if (!availableBillingCycleSetting) {
            builder.mResultCode = resultCode;
            builder.mErrorCode = ControlResult.ErrorCode.NOT_SUPPORT_DEVICE;
            return new ControlResult(builder);
        }
        try {
            int parseInt = Integer.parseInt(controlValue.getValue());
            if (parseInt < 1 || parseInt > 31) {
                builder.mResultCode = resultCode;
                builder.mErrorCode = errorCode;
                return new ControlResult(builder);
            }
            SecBillingCycleManager secBillingCycleManager =
                    SecBillingCycleManager.getInstance(this.mContext);
            NetworkTemplate networkTemplate = secBillingCycleManager.mNetworkTemplate;
            String id = TimeZone.getDefault().getID();
            NetworkPolicyEditor networkPolicyEditor =
                    secBillingCycleManager.mServices.mPolicyEditor;
            NetworkPolicy orCreatePolicy = networkPolicyEditor.getOrCreatePolicy(networkTemplate);
            orCreatePolicy.cycleRule = NetworkPolicy.buildRule(parseInt, ZoneId.of(id));
            orCreatePolicy.inferred = false;
            orCreatePolicy.clearSnooze();
            networkPolicyEditor.writeAsync();
            secBillingCycleManager.updateScreen();
            builder.mResultCode = ControlResult.ResultCode.SUCCESS;
            return new ControlResult(builder);
        } catch (NumberFormatException unused) {
            builder.mResultCode = resultCode;
            builder.mErrorCode = errorCode;
            return new ControlResult(builder);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        Log.d(TAG, "updateState()");
        super.updateState(preference);
        SecPreference secPreference = (SecPreference) preference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
