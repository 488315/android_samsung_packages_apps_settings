package com.android.settings.development;

import android.content.Context;
import android.debug.PairDevice;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbDeviceDetailsHeaderController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    static final String KEY_HEADER = "adb_device_header";
    public final Fragment mFragment;
    public final PairDevice mPairedDevice;

    public AdbDeviceDetailsHeaderController(
            PairDevice pairDevice, Context context, Fragment fragment) {
        super(context);
        this.mPairedDevice = pairDevice;
        this.mFragment = fragment;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(KEY_HEADER);
        Fragment fragment = this.mFragment;
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(
                        fragment.getActivity(),
                        fragment,
                        layoutPreference.mRootView.findViewById(R.id.entity_header));
        entityHeaderController.setIcon(
                this.mContext.getDrawable(android.R.drawable.ic_clear_normal));
        entityHeaderController.mLabel = this.mPairedDevice.name;
        entityHeaderController.done(true);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_HEADER;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }
}
