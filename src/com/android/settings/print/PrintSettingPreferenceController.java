package com.android.settings.print;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.print.PrintJob;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrintManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrintSettingPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, PrintManager.PrintJobStateChangeListener {
    private static final String KEY_PRINTING_SETTINGS = "connected_device_printing";
    private final PackageManager mPackageManager;
    private Preference mPreference;
    private final PrintManager mPrintManager;

    public PrintSettingPreferenceController(Context context) {
        super(context, KEY_PRINTING_SETTINGS);
        this.mPackageManager = context.getPackageManager();
        this.mPrintManager =
                ((PrintManager) context.getSystemService("print"))
                        .getGlobalPrintManagerForUser(context.getUserId());
    }

    public static boolean shouldShowToUser(PrintJobInfo printJobInfo) {
        int state = printJobInfo.getState();
        return state == 2 || state == 3 || state == 4 || state == 6;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!this.mPackageManager.hasSystemFeature("android.software.print")
                        || this.mPrintManager == null)
                ? 3
                : 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        List<PrintJob> printJobs = this.mPrintManager.getPrintJobs();
        int i = 0;
        if (printJobs != null) {
            Iterator<PrintJob> it = printJobs.iterator();
            while (it.hasNext()) {
                if (shouldShowToUser(it.next().getInfo())) {
                    i++;
                }
            }
        }
        if (i > 0) {
            return StringUtil.getIcuPluralsString(this.mContext, i, R.string.print_jobs_summary);
        }
        List printServices = this.mPrintManager.getPrintServices(1);
        return (printServices == null || printServices.isEmpty())
                ? this.mContext.getText(R.string.print_settings_summary_no_service)
                : StringUtil.getIcuPluralsString(
                        this.mContext, printServices.size(), R.string.print_settings_summary);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    public void onPrintJobStateChanged(PrintJobId printJobId) {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        PrintManager printManager = this.mPrintManager;
        if (printManager != null) {
            printManager.addPrintJobStateChangeListener(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        PrintManager printManager = this.mPrintManager;
        if (printManager != null) {
            printManager.removePrintJobStateChangeListener(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        ((RestrictedPreference) preference).checkRestrictionAndSetDisabled("no_printing");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
