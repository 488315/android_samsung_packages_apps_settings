package com.android.settings.development;

import android.os.Handler;
import android.os.Looper;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PictureColorModePreferenceController extends DeveloperOptionsPreferenceController
        implements LifecycleObserver, OnResume, OnPause, PreferenceControllerMixin {
    public ColorModePreference mPreference;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ColorModePreference colorModePreference =
                (ColorModePreference) preferenceScreen.findPreference("picture_color_mode");
        this.mPreference = colorModePreference;
        if (colorModePreference != null) {
            colorModePreference.updateCurrentAndSupported();
        }
    }

    public int getColorModeDescriptionsSize() {
        return ((ArrayList) ColorModePreference.getColorModeDescriptions(this.mContext)).size();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "picture_color_mode";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return getColorModeDescriptionsSize() > 1 && !isWideColorGamut();
    }

    public boolean isWideColorGamut() {
        return this.mContext.getResources().getConfiguration().isScreenWideColorGamut();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        ColorModePreference colorModePreference = this.mPreference;
        if (colorModePreference == null) {
            return;
        }
        colorModePreference.mDisplayManager.unregisterDisplayListener(colorModePreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        ColorModePreference colorModePreference = this.mPreference;
        if (colorModePreference == null) {
            return;
        }
        colorModePreference.mDisplayManager.registerDisplayListener(
                colorModePreference, new Handler(Looper.getMainLooper()));
        this.mPreference.updateCurrentAndSupported();
    }
}
