package com.android.settings.development;

import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CameraLaserSensorPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String BUILD_TYPE = "ro.build.type";
    static final int DISABLED = 2;
    static final int ENABLED = 0;
    static final String ENG_BUILD = "eng";
    static final String PROPERTY_CAMERA_LASER_SENSOR = "persist.camera.stats.disablehaf";
    static final String USERDEBUG_BUILD = "userdebug";
    static final String USER_BUILD = "user";

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "camera_laser_sensor_switch";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_camera_laser_sensor);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                PROPERTY_CAMERA_LASER_SENSOR,
                Integer.toString(((Boolean) obj).booleanValue() ? 0 : 2));
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        TextUtils.equals(
                                Integer.toString(0),
                                SystemProperties.get(
                                        PROPERTY_CAMERA_LASER_SENSOR, Integer.toString(0))));
    }
}
