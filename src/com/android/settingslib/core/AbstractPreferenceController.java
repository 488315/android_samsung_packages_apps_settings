package com.android.settingslib.core;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.os.BuildCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractPreferenceController {
    private static final String TAG = "AbstractPrefController";
    protected final Context mContext;
    private final DevicePolicyManager mDevicePolicyManager;

    public AbstractPreferenceController(Context context) {
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference;
        String preferenceKey = getPreferenceKey();
        if (TextUtils.isEmpty(preferenceKey)) {
            Log.w(
                    TAG,
                    "Skipping displayPreference because key is empty:"
                            .concat(getClass().getName()));
            return;
        }
        if (!isAvailable()) {
            setVisible(preferenceScreen, preferenceKey, false);
            return;
        }
        setVisible(preferenceScreen, preferenceKey, true);
        if (!(this instanceof Preference.OnPreferenceChangeListener)
                || (findPreference = preferenceScreen.findPreference(preferenceKey)) == null) {
            return;
        }
        findPreference.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
    }

    public abstract String getPreferenceKey();

    public CharSequence getSummary() {
        return null;
    }

    public boolean getSummaryOnBackground() {
        return false;
    }

    public boolean handlePreferenceTreeClick(Preference preference) {
        return false;
    }

    public abstract boolean isAvailable();

    public void refreshSummary(Preference preference) {
        CharSequence summary;
        if (getSummaryOnBackground()) {
            ThreadUtils.postOnBackgroundThread(
                    new AbstractPreferenceController$$ExternalSyntheticLambda2(this, preference));
        } else {
            if (preference == null || (summary = getSummary()) == null) {
                return;
            }
            preference.setSummary(summary);
        }
    }

    public void replaceEnterpriseStringSummary(
            PreferenceScreen preferenceScreen, String str, String str2, int i) {
        int i2 = BuildCompat.$r8$clinit;
        if (this.mDevicePolicyManager == null) {
            return;
        }
        Preference findPreference = preferenceScreen.findPreference(str);
        if (findPreference == null) {
            Log.d(TAG, "Could not find enterprise preference ".concat(str));
        } else {
            findPreference.setSummary(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    str2,
                                    new AbstractPreferenceController$$ExternalSyntheticLambda0(
                                            this, i, 0)));
        }
    }

    public void replaceEnterpriseStringTitle(
            PreferenceScreen preferenceScreen, String str, String str2, int i) {
        int i2 = BuildCompat.$r8$clinit;
        if (this.mDevicePolicyManager == null) {
            return;
        }
        Preference findPreference = preferenceScreen.findPreference(str);
        if (findPreference == null) {
            Log.d(TAG, "Could not find enterprise preference ".concat(str));
        } else {
            findPreference.setTitle(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    str2,
                                    new AbstractPreferenceController$$ExternalSyntheticLambda0(
                                            this, i, 1)));
        }
    }

    public final void setVisible(PreferenceGroup preferenceGroup, String str, boolean z) {
        Preference findPreference = preferenceGroup.findPreference(str);
        if (findPreference != null) {
            findPreference.setVisible(z);
        }
    }

    public void updateState(Preference preference) {
        refreshSummary(preference);
    }

    public void onViewCreated(LifecycleOwner lifecycleOwner) {}
}
