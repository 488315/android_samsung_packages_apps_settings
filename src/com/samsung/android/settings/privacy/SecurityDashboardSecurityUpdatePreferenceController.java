package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SemSystemProperties;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateUtils;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardSecurityUpdatePreferenceController
        extends SecurityDashboardUpdatesPreferenceController {
    private static final String TAG = "SecurityDashboardSecurityUpdatePreferenceController";

    public SecurityDashboardSecurityUpdatePreferenceController(Context context, String str) {
        super(context, str);
    }

    private void updateMenuStatus(SecExpandableMenuPreference secExpandableMenuPreference) {
        secExpandableMenuPreference.setStatus(
                (!SecurityDashboardUtils.isSecurityUpdateInstalled(this.mContext)
                                || ((SecurityDashboardUtils.getDaysCountSinceLastSecurityUpdate()
                                                        > 360L
                                                ? 1
                                                : (SecurityDashboardUtils
                                                                        .getDaysCountSinceLastSecurityUpdate()
                                                                == 360L
                                                        ? 0
                                                        : -1))
                                        > 0))
                        ? SecurityDashboardConstants$Status.WARNING
                        : SecurityDashboardConstants$Status.OK);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        updateMenuStatus(
                (SecExpandableMenuPreference) preferenceScreen.findPreference(getPreferenceKey()));
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardUpdatesPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsSecurityUpdateSupported ? 0 : 3;
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
        return SecurityDashboardUtils.getSecurityUpdateSummary();
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
        if ("key_security_update".equals(preference.getKey())) {
            Context context = this.mContext;
            boolean z = false;
            if (Utils.isPackageEnabled(context, "com.ws.dm")) {
                Intent intent = new Intent("com.android.settings.MANUFACTURER_APPLICATION_SETTING");
                intent.addFlags(67108864);
                intent.setClassName(
                        "com.ws.dm", "com.att.fotaagent.enabler.ui.userinit.UserInitActivity");
                try {
                    context.startActivityForResult(null, intent, 0, null);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                if (Utils.isPackageEnabled(context, "com.samsung.sdm.sdmviewer")) {
                    z = true;
                } else if (Utils.isPackageEnabled(context, "com.samsung.sdm")) {
                    z = SoftwareUpdateUtils.isVZWFotaNewEnabled(context);
                }
                if (z) {
                    Intent intent2 =
                            new Intent("com.android.settings.MANUFACTURER_APPLICATION_SETTING");
                    String salesCode = SemSystemProperties.getSalesCode();
                    if ("TFN".equals(salesCode)
                            || "TFV".equals(salesCode)
                            || "TFO".equals(salesCode)
                            || "TFA".equals(salesCode)) {
                        intent2.setClassName(
                                "com.samsung.sdm.sdmviewer",
                                "com.samsung.sdm.sdmviewer.TFNSoftwareUpdateLauncher");
                    } else if (Utils.isPackageEnabled(context, "com.samsung.sdm.sdmviewer")) {
                        intent2.setClassName(
                                "com.samsung.sdm.sdmviewer",
                                "com.samsung.sdm.sdmviewer.VZWSystemUpdatesLauncher");
                    } else {
                        intent2.setClassName(
                                "com.samsung.sdm", "com.samsung.sdm.VZWSystemUpdatesLauncher");
                    }
                    try {
                        context.startActivity(intent2);
                    } catch (ActivityNotFoundException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    SoftwareUpdateUtils.checkNewSoftwareUpdate(context);
                }
            }
            LoggingHelper.insertEventLogging(9032, 7807);
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
        if (this.mIsSecurityUpdateSupported) {
            return;
        }
        list.add("key_security_update");
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
