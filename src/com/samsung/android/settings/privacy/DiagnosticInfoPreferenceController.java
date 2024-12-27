package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.Utils;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DiagnosticInfoPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, Preference.OnPreferenceChangeListener {
    private static final String DIAGMON_AGENT_PKG_NAME = "com.sec.android.diagmonagent";
    private static final String DIAGNOSTIC_INFO_ACTION =
            "com.samsung.settings.DIAGNOSTIC_INFO_CHANGED";
    private static final String DQ_AGENT_PKG_NAME = "com.samsung.android.dqagent";
    private static final String TAG = "DiagnosticInfoPreferenceController";
    private SecSwitchPreference mDiagnosticInfo;
    private BroadcastReceiver mIntentReceiver;

    public DiagnosticInfoPreferenceController(Context context, String str) {
        super(context, str);
    }

    private BroadcastReceiver getDiagnosticInfoReceiver() {
        if (this.mIntentReceiver == null) {
            this.mIntentReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.settings.privacy.DiagnosticInfoPreferenceController.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                                    "onReceive() : ",
                                    intent.getAction(),
                                    DiagnosticInfoPreferenceController.TAG,
                                    DiagnosticInfoPreferenceController.DIAGNOSTIC_INFO_ACTION)) {
                                DiagnosticInfoPreferenceController.this.mDiagnosticInfo.setChecked(
                                        intent.getIntExtra("diagnostic_info_changed", 0) != 0);
                            }
                        }
                    };
        }
        return this.mIntentReceiver;
    }

    private void showReportDiagnosticinfoDialog() {
        try {
            this.mContext.startActivity(
                    new Intent().setAction("com.samsung.settings.ReportDiagnosticinfo"));
        } catch (ActivityNotFoundException e) {
            Log.e(
                    TAG,
                    "showReportDiagnosticInfoDialog() failed to launch an activity : "
                            + e.getMessage());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mDiagnosticInfo =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Utils.hasPackage(this.mContext, DIAGMON_AGENT_PKG_NAME)
                || Utils.hasPackage(this.mContext, DQ_AGENT_PKG_NAME)) {
            return Utils.isGuestUser(this.mContext) ? 4 : 0;
        }
        return 3;
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

    public boolean isChecked() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), "samsung_errorlog_agree", 0)
                != 0;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (preference.getKey().equals(getPreferenceKey())) {
            if (Rune.SUPPORT_DIAGNOSTIC_INFO_CHINA_DELTA && !booleanValue) {
                SecurityUtils.setDiagnostic(this.mContext, false, false);
                LoggingHelper.insertEventLogging(56041, 8010, false);
                return true;
            }
            showReportDiagnosticinfoDialog();
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        Log.d(TAG, "onStart()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DIAGNOSTIC_INFO_ACTION);
        this.mContext.registerReceiver(getDiagnosticInfoReceiver(), intentFilter, 4);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        Log.d(TAG, "onStop()");
        BroadcastReceiver broadcastReceiver = this.mIntentReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
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
        ((SecSwitchPreference) preference).setChecked(isChecked());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
