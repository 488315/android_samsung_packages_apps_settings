package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.core.SecCustomPreferenceController;
import com.android.settings.datausage.DataUsageUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDataWarningController extends SecCustomPreferenceController {
    private static final String TAG = "SecBillingCycleSettings.SecDataWarningController";
    private SecBillingCycleSettings mParent;

    public SecDataWarningController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        return (!secBillingCycleManager.getAvailableBillingCycleSetting()
                        || secBillingCycleManager.getPolicyWarningBytes() == -1)
                ? 2
                : 0;
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
        long policyWarningBytes =
                SecBillingCycleManager.getInstance(this.mContext).getPolicyWarningBytes();
        return policyWarningBytes != -1
                ? DataUsageUtils.formatDataUsage(this.mContext, policyWarningBytes)
                : ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(
                Long.valueOf(
                        SecBillingCycleManager.getInstance(this.mContext).getPolicyWarningBytes()));
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
        LoggingHelper.insertEventLogging(getMetricsCategory(), 7122);
        SecBytesEditorFragment.show(this.mParent, false, (SecPreference) preference, false);
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
        ControlResult.ResultCode resultCode = ControlResult.ResultCode.FAIL;
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        try {
            long parseLong = Long.parseLong(controlValue.getValue());
            if (parseLong < -1) {
                builder.mResultCode = resultCode;
                builder.mErrorCode = errorCode;
                return builder.build();
            }
            SecBillingCycleManager.getInstance(this.mContext).setPolicyWarningBytes(parseLong);
            builder.mResultCode = ControlResult.ResultCode.SUCCESS;
            return builder.build();
        } catch (NumberFormatException unused) {
            builder.mResultCode = resultCode;
            builder.mErrorCode = errorCode;
            return builder.build();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        Log.d(TAG, "updateState()");
        super.updateState(preference);
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(this.mContext);
        SecPreference secPreference = (SecPreference) preference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
        if (secBillingCycleManager.getPolicyWarningBytes() != -1) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
