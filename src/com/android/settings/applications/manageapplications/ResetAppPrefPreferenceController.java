package com.android.settings.applications.manageapplications;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ResetAppPrefPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnCreate, OnSaveInstanceState {
    public ResetAppsHelper mResetAppsHelper;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "reset_app_prefs";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "reset_app_prefs")) {
            return false;
        }
        this.mResetAppsHelper.buildResetDialog();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public final void onCreate(Bundle bundle) {
        ResetAppsHelper resetAppsHelper = this.mResetAppsHelper;
        resetAppsHelper.getClass();
        if (bundle == null || !bundle.getBoolean("resetDialog")) {
            return;
        }
        resetAppsHelper.buildResetDialog();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public final void onSaveInstanceState(Bundle bundle) {
        if (this.mResetAppsHelper.mResetDialog != null) {
            bundle.putBoolean("resetDialog", true);
        }
    }
}
