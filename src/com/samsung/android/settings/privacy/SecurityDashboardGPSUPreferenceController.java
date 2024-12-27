package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardGPSUPreferenceController
        extends SecurityDashboardUpdatesPreferenceController {
    private static final String TAG = "SecurityDashboardGPSUPreferenceController";

    public SecurityDashboardGPSUPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void launchGpsuIntent() {
        Intent intent = new Intent("android.settings.MODULE_UPDATE_SETTINGS");
        if (intent.resolveActivity(this.mContext.getPackageManager()) != null) {
            try {
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                SemLog.e(TAG, "Google Play System Update Not Found" + e.getMessage());
            }
        }
    }

    private void updateMenuStatus(SecExpandableMenuPreference secExpandableMenuPreference) {
        secExpandableMenuPreference.setStatus(
                SecurityDashboardUtils.getDaysCountSinceLastGPSystemUpdate(this.mContext) <= 360
                        ? SecurityDashboardConstants$Status.OK
                        : SecurityDashboardConstants$Status.WARNING);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsGPSuSupported ? 0 : 3;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return SecurityDashboardUtils.getGPSystemUpdateSummary(this.mContext);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_google_play_system_update".equals(preference.getKey())) {
            launchGpsuIntent();
            LoggingHelper.insertEventLogging(9032, 56039);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (this.mIsGPSuSupported) {
            return;
        }
        list.add("key_google_play_system_update");
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus((SecExpandableMenuPreference) preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
