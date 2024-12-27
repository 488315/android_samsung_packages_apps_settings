package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.system.ResetDashboardFragment;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EraseEuiccDataController extends BasePreferenceController {
    private ResetDashboardFragment mHostFragment;
    private final UserManager mUm;

    public EraseEuiccDataController(Context context, String str) {
        super(context, str);
        this.mUm = (UserManager) context.getSystemService(UserManager.class);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (SubscriptionUtil.isSimHardwareVisible(this.mContext)
                        && ((this.mUm.isAdminUser() || Utils.isDemoUser(this.mContext))
                                && !MobileNetworkUtils.isMobileNetworkUserRestricted(this.mContext))
                        && this.mContext
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.telephony.euicc"))
                ? 1
                : 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        if (SubscriptionUtil.shouldShowRacDialogWhenErasingAllEsims(this.mContext)) {
            ResetDashboardFragment resetDashboardFragment = this.mHostFragment;
            if (resetDashboardFragment.getActivity() == null) {
                return true;
            }
            EuiccRacConnectivityDialogFragment euiccRacConnectivityDialogFragment =
                    new EuiccRacConnectivityDialogFragment();
            euiccRacConnectivityDialogFragment.setTargetFragment(resetDashboardFragment, 0);
            euiccRacConnectivityDialogFragment.show(
                    resetDashboardFragment.getActivity().getSupportFragmentManager(),
                    "EuiccRacConnectivityDlg");
            return true;
        }
        ResetDashboardFragment resetDashboardFragment2 = this.mHostFragment;
        if (resetDashboardFragment2.getActivity() == null) {
            return true;
        }
        EraseEuiccDataDialogFragment eraseEuiccDataDialogFragment =
                new EraseEuiccDataDialogFragment();
        eraseEuiccDataDialogFragment.setTargetFragment(resetDashboardFragment2, 0);
        eraseEuiccDataDialogFragment.show(
                resetDashboardFragment2.getActivity().getSupportFragmentManager(),
                "EraseEuiccDataDlg");
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
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

    public void setFragment(ResetDashboardFragment resetDashboardFragment) {
        this.mHostFragment = resetDashboardFragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
