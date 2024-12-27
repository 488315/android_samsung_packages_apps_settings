package com.android.settings.print;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.print.PrintJob;
import android.print.PrintJobId;
import android.print.PrintManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PrintJobPreferenceControllerBase extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, PrintManager.PrintJobStateChangeListener {
    private static final String EXTRA_PRINT_JOB_ID = "EXTRA_PRINT_JOB_ID";
    private static final String TAG = "PrintJobPrefCtrlBase";
    protected PrintJobSettingsFragment mFragment;
    protected Preference mPreference;
    protected PrintJobId mPrintJobId;
    private final PrintManager mPrintManager;

    public PrintJobPreferenceControllerBase(Context context, String str) {
        super(context, str);
        this.mPrintManager =
                ((PrintManager) this.mContext.getSystemService("print"))
                        .getGlobalPrintManagerForUser(this.mContext.getUserId());
    }

    private void processArguments() {
        String string = this.mFragment.getArguments().getString(EXTRA_PRINT_JOB_ID);
        if (string != null
                || (string =
                                this.mFragment
                                        .getActivity()
                                        .getIntent()
                                        .getStringExtra(EXTRA_PRINT_JOB_ID))
                        != null) {
            this.mPrintJobId = PrintJobId.unflattenFromString(string);
        } else {
            Log.w(TAG, "EXTRA_PRINT_JOB_ID not set");
            this.mFragment.finish();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public PrintJob getPrintJob() {
        return this.mPrintManager.getPrintJob(this.mPrintJobId);
    }

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

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(PrintJobSettingsFragment printJobSettingsFragment) {
        this.mFragment = printJobSettingsFragment;
        processArguments();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void onPrintJobStateChanged(PrintJobId printJobId) {
        updateUi();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mPrintManager.addPrintJobStateChangeListener(this);
        updateUi();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mPrintManager.removePrintJobStateChangeListener(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public abstract void updateUi();

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
