package com.android.settings.development;

import android.R;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.os.RemoteException;
import android.os.ServiceManager;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TransparentNavigationBarPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final IOverlayManager mOverlayManager;

    public TransparentNavigationBarPreferenceController(Context context) {
        super(context);
        this.mOverlayManager =
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "transparent_navigation_bar";
    }

    @VisibleForTesting
    public boolean isEnabled() {
        return this.mContext
                .getResources()
                .getBoolean(R.bool.config_overrideRemoteViewsActivityTransition);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((TwoStatePreference) this.mPreference).setChecked(false);
        if (isEnabled()) {
            return;
        }
        setEnabled(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        setEnabled(((Boolean) obj).booleanValue());
        return true;
    }

    @VisibleForTesting
    public void setEnabled(boolean z) {
        try {
            this.mOverlayManager.setEnabled(
                    "com.android.internal.systemui.navbar.transparent", z, -2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference).setChecked(isEnabled());
    }
}
