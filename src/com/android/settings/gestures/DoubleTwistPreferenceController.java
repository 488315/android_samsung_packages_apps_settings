package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DoubleTwistPreferenceController extends GesturePreferenceController {
    private static final String PREF_KEY_VIDEO = "gesture_double_twist_video";
    private final int OFF;
    private final int ON;
    private final String mDoubleTwistPrefKey;
    private final UserManager mUserManager;

    public DoubleTwistPreferenceController(Context context, String str) {
        super(context, str);
        this.ON = 1;
        this.OFF = 0;
        this.mDoubleTwistPrefKey = str;
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    public static int getManagedProfileId(UserManager userManager) {
        return Utils.getManagedProfileId(userManager, UserHandle.myUserId());
    }

    public static boolean isGestureAvailable(Context context) {
        Resources resources = context.getResources();
        String string = resources.getString(R.string.gesture_double_twist_sensor_type);
        String string2 = resources.getString(R.string.gesture_double_twist_sensor_vendor);
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
            return false;
        }
        for (Sensor sensor :
                ((SensorManager) context.getSystemService("sensor")).getSensorList(-1)) {
            if (string.equals(sensor.getStringType()) && string2.equals(sensor.getVendor())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSuggestionComplete(
            Context context, SharedPreferences sharedPreferences) {
        return !isGestureAvailable(context)
                || sharedPreferences.getBoolean("pref_double_twist_suggestion_complete", false);
    }

    public static void setDoubleTwistPreference(Context context, UserManager userManager, int i) {
        Settings.Secure.putInt(
                context.getContentResolver(), "camera_double_twist_to_flip_enabled", i);
        int managedProfileId = getManagedProfileId(userManager);
        if (managedProfileId != -10000) {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(),
                    "camera_double_twist_to_flip_enabled",
                    i,
                    managedProfileId);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isGestureAvailable(this.mContext) ? 0 : 3;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return this.mDoubleTwistPrefKey;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.gestures.GesturePreferenceController
    public String getVideoPrefKey() {
        return PREF_KEY_VIDEO;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "camera_double_twist_to_flip_enabled",
                        1)
                != 0;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), "gesture_double_twist");
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        setDoubleTwistPreference(this.mContext, this.mUserManager, z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
