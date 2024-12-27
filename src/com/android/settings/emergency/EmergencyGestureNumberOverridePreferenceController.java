package com.android.settings.emergency;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.text.Spannable;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.emergencynumber.EmergencyNumberUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyGestureNumberOverridePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    EmergencyNumberUtils mEmergencyNumberUtils;
    private final Handler mHandler;
    private Preference mPreference;
    private final ContentObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EmergencyGestureNumberOverrideSettingsObserver extends ContentObserver {
        public EmergencyGestureNumberOverrideSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (EmergencyGestureNumberOverridePreferenceController.this.mPreference != null) {
                EmergencyGestureNumberOverridePreferenceController
                        emergencyGestureNumberOverridePreferenceController =
                                EmergencyGestureNumberOverridePreferenceController.this;
                emergencyGestureNumberOverridePreferenceController.updateState(
                        emergencyGestureNumberOverridePreferenceController.mPreference);
            }
        }
    }

    public EmergencyGestureNumberOverridePreferenceController(Context context, String str) {
        super(context, str);
        this.mEmergencyNumberUtils = new EmergencyNumberUtils(context);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mSettingsObserver = new EmergencyGestureNumberOverrideSettingsObserver(handler);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_show_emergency_gesture_settings)
                ? 0
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String policeNumber = this.mEmergencyNumberUtils.getPoliceNumber();
        String string =
                this.mContext.getString(
                        R.string.emergency_gesture_call_for_help_summary, policeNumber);
        int indexOf = string.indexOf(policeNumber);
        if (indexOf < 0) {
            return string;
        }
        Spannable newSpannable = Spannable.Factory.getInstance().newSpannable(string);
        PhoneNumberUtils.addTtsSpan(newSpannable, indexOf, policeNumber.length() + indexOf);
        return newSpannable;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        EmergencyNumberUtils.EMERGENCY_NUMBER_OVERRIDE_AUTHORITY,
                        false,
                        this.mSettingsObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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
