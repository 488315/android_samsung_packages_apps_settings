package com.android.settings.notification.modes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenModeFragmentBase extends ZenModesFragmentBase {
    public ZenMode mZenMode;

    @Override // com.android.settings.notification.modes.ZenModesFragmentBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("MODE_ID")) {
            Log.e("ZenModeSettings", "Mode id required to set mode config settings");
            toastAndFinish();
            return;
        }
        String string = arguments.getString("MODE_ID");
        ZenMode mode = this.mBackend.getMode(string);
        this.mZenMode = mode;
        if (mode == null) {
            Log.e("ZenModeSettings", "Mode id " + string + " not found");
            toastAndFinish();
            return;
        }
        if (mode != null) {
            Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
            while (it.hasNext()) {
                try {
                    Iterator<AbstractPreferenceController> it2 = it.next().iterator();
                    while (it2.hasNext()) {
                        ((AbstractZenModePreferenceController) it2.next()).mZenMode = this.mZenMode;
                    }
                } catch (ClassCastException unused) {
                }
            }
        }
    }

    public final void toastAndFinish() {
        Toast.makeText(
                        ((ZenModesFragmentBase) this).mContext,
                        R.string.zen_mode_rule_not_found_text,
                        0)
                .show();
        finish();
    }

    @Override // com.android.settings.notification.modes.ZenModesFragmentBase
    public final void updateZenModeState() {
        ZenMode zenMode = this.mZenMode;
        if (zenMode == null) {
            toastAndFinish();
            return;
        }
        ZenModesBackend zenModesBackend = this.mBackend;
        String str = zenMode.mId;
        ZenMode mode = zenModesBackend.getMode(str);
        this.mZenMode = mode;
        if (!(mode != null)) {
            Log.d("ZenModeSettings", "Mode id=" + str + " not found");
            toastAndFinish();
            return;
        }
        if (getPreferenceControllers() == null || this.mZenMode == null) {
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            Log.d("ZenModeSettings", "PreferenceScreen not found");
            return;
        }
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                if (abstractPreferenceController.isAvailable()) {
                    try {
                        String preferenceKey = abstractPreferenceController.getPreferenceKey();
                        Preference findPreference = preferenceScreen.findPreference(preferenceKey);
                        if (findPreference != null) {
                            AbstractZenModePreferenceController
                                    abstractZenModePreferenceController =
                                            (AbstractZenModePreferenceController)
                                                    abstractPreferenceController;
                            abstractZenModePreferenceController.mZenMode = this.mZenMode;
                            abstractZenModePreferenceController.updateState(findPreference);
                        } else {
                            Log.d(
                                    "ZenModeSettings",
                                    "Cannot find preference with key "
                                            + preferenceKey
                                            + " in Controller "
                                            + abstractPreferenceController
                                                    .getClass()
                                                    .getSimpleName());
                        }
                    } catch (ClassCastException unused) {
                        Log.d(
                                "ZenModeSettings",
                                "Could not cast: "
                                        .concat(
                                                abstractPreferenceController
                                                        .getClass()
                                                        .getSimpleName()));
                    }
                }
            }
        }
    }
}
