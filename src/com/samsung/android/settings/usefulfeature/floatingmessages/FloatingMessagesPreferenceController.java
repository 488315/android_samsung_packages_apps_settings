package com.samsung.android.settings.usefulfeature.floatingmessages;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FloatingMessagesPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String KEY_DISPLAY_AIRMESSAGE = "display_airmessage";
    private static final String TAG = "FloatingMessagesPreferenceController";
    private SecSwitchPreferenceScreen mPreference;

    public FloatingMessagesPreferenceController(Context context) {
        this(context, KEY_DISPLAY_AIRMESSAGE);
    }

    private void updatePreferenceSummary(boolean z) {
        if (this.mPreference != null) {
            if (getThreadEnabled()) {
                this.mPreference.setSummary((CharSequence) null);
            } else {
                this.mPreference.setSummary(R.string.airmessage_settings_summary);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_DISPLAY_AIRMESSAGE);
        updatePreferenceSummary(getThreadEnabled());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableAirMessage")
                ? 3
                : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        boolean z;
        if (preference.equals(this.mPreference)) {
            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setComponent(
                        new ComponentName(
                                "com.bst.airmessage",
                                "com.bst.airmessage.settings.AirMessageSettings"));
                if (UsefulfeatureUtils.isCoverVerified(this.mContext)
                        && UsefulfeatureUtils.getTypeOfCover(this.mContext) != 2) {
                    z = true;
                    intent.putExtra("S View cover attached", z);
                    this.mContext.startActivity(intent);
                    return true;
                }
                z = false;
                intent.putExtra("S View cover attached", z);
                this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                Log.secD(TAG, "ActivityNotFoundException in mDisplayAirmessage");
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "airmessage_on", 1) != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference != null) {
            boolean threadEnabled = getThreadEnabled();
            SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
            if (threadEnabled != secSwitchPreferenceScreen.mChecked) {
                secSwitchPreferenceScreen.setChecked(getThreadEnabled());
            }
            updatePreferenceSummary(getThreadEnabled());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), "airmessage_on", z ? 1 : 0);
        updatePreferenceSummary(getThreadEnabled());
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FloatingMessagesPreferenceController(Context context, String str) {
        super(context, str);
    }
}
