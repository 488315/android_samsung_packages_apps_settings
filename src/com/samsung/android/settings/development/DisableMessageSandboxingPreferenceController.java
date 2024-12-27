package com.samsung.android.settings.development;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DisableMessageSandboxingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnResume,
                OnPause,
                PreferenceControllerMixin {
    public final AnonymousClass1 mRampartContentObserver;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.settings.development.DisableMessageSandboxingPreferenceController$1] */
    public DisableMessageSandboxingPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        this.mRampartContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.development.DisableMessageSandboxingPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        DisableMessageSandboxingPreferenceController
                                disableMessageSandboxingPreferenceController =
                                        DisableMessageSandboxingPreferenceController.this;
                        disableMessageSandboxingPreferenceController.updateState(
                                disableMessageSandboxingPreferenceController.mPreference);
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "disable_message_sandboxing";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        onDeveloperOptionsSwitchDisabled();
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRampartContentObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        try {
            SystemProperties.set("persist.kumiho.disabled", booleanValue ? "true" : "false");
            if (booleanValue) {
                SystemProperties.set("ctl.stop", "kumihodecoder");
            } else {
                SystemProperties.set("ctl.start", "kumihodecoder");
            }
            return true;
        } catch (IllegalArgumentException e) {
            Log.e("PrefControllerMixin", "Fail to set kumiho system property: " + e.getMessage());
            return true;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("rampart_enabled_message_guard"),
                        false,
                        this.mRampartContentObserver);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        try {
            preference.setEnabled(
                    !(Settings.Secure.getInt(
                                    this.mContext.getContentResolver(),
                                    "rampart_enabled_message_guard",
                                    0)
                            == 1));
            ((SwitchPreference) this.mPreference)
                    .setChecked(SystemProperties.getBoolean("persist.kumiho.disabled", false));
        } catch (IllegalArgumentException e) {
            Log.e("PrefControllerMixin", "Fail to get kumiho system property: " + e.getMessage());
        }
        if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "rampart_enabled_message_guard", 0)
                == 1) {
            preference.setSummary(R.string.restricted_by_auto_blocker);
        } else {
            preference.setSummary(R.string.disable_message_sandboxing_summary);
        }
    }
}
