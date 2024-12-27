package com.samsung.android.settings.connection.moreconnection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.print.PrintJobId;
import android.print.PrintManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecPrintSettingPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, PrintManager.PrintJobStateChangeListener {
    private static final String TAG = "SecPrintSettingPreferenceController";
    private final PackageManager mPackageManager;
    private Preference mPreference;
    private final PrintManager mPrintManager;

    public SecPrintSettingPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
        this.mPrintManager =
                ((PrintManager) context.getSystemService("print"))
                        .getGlobalPrintManagerForUser(context.getUserId());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean hasSystemFeature = this.mPackageManager.hasSystemFeature("android.software.print");
        String str = TAG;
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "getAvailabilityStatus() : ", " : ", hasSystemFeature);
        m.append(this.mPackageManager);
        Log.d(str, m.toString());
        return (!hasSystemFeature || this.mPrintManager == null) ? 3 : 0;
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
