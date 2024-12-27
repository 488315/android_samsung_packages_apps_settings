package com.samsung.android.settings.display.gtscontroller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BrightnessPreferenceController extends SliderPreferenceController {
    private static final String KEY_AUTOMATIC_MODE = "key_automatic_mode";
    private static final String KEY_BRIGHTNESS_VALUE = "key_brightness_value";
    private static int MAX_BRIGHTNESS = 0;
    private static int MIN_BRIGHTNESS = 0;
    private static final int OFF = 0;
    private static final int ON = 1;
    private static final String PREFERENCE_KEY = "secbrightness";
    private static final String TAG =
            "com.samsung.android.settings.display.gtscontroller.BrightnessPreferenceController";
    private boolean mAutomaticMode;
    private int mBrightnessValue;
    private PowerManager mPowerManager;
    private Preference mPreference;

    public BrightnessPreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mPowerManager = powerManager;
        MAX_BRIGHTNESS = powerManager.getMaximumScreenBrightnessSetting();
        MIN_BRIGHTNESS = this.mPowerManager.getMinimumScreenBrightnessSetting();
    }

    private int getBrightnessMode(int i) {
        return Settings.System.getInt(
                this.mContext.getContentResolver(), "screen_brightness_mode", i);
    }

    private void initBrightnessValues() {
        this.mAutomaticMode = getBrightnessMode(0) == 1;
        this.mBrightnessValue =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "screen_brightness", 100);
    }

    private void setBrightness() {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "screen_brightness", this.mBrightnessValue);
        Settings.System.getInt(
                this.mContext.getContentResolver(),
                "screen_brightness_mode",
                this.mAutomaticMode ? 1 : 0);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(PREFERENCE_KEY);
        this.mPreference = findPreference;
        if (findPreference != null) {
            findPreference.setTitle(this.mContext.getString(R.string.brightness_title));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return MAX_BRIGHTNESS;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return MIN_BRIGHTNESS;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return PREFERENCE_KEY;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        initBrightnessValues();
        return this.mBrightnessValue;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue value = super.getValue();
        initBrightnessValues();
        ControlValue.Builder builder = new ControlValue.Builder(value.mKey, value.mControlType);
        builder.addAttributeInt(this.mBrightnessValue, KEY_BRIGHTNESS_VALUE);
        builder.addAttributeInt(this.mAutomaticMode ? 1 : 0, KEY_AUTOMATIC_MODE);
        builder.mAvailabilityStatus = value.mAvailabilityStatus;
        builder.mForceChange = Boolean.TRUE;
        builder.mBundle = value.mBundle;
        builder.setControlId(value.mControlId);
        builder.mIsDefault = Boolean.valueOf(value.mIsDefault);
        builder.mStatusCode = value.mStatusCode;
        builder.setValue(value.getValue());
        return builder.build();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        this.mBrightnessValue = i;
        Settings.System.putInt(
                this.mContext.getContentResolver(), "screen_brightness", this.mBrightnessValue);
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        String message;
        try {
        } catch (Exception e) {
            message = e.getMessage();
            Log.e(TAG, "setting value failed: " + message);
        }
        if (!PREFERENCE_KEY.equals(controlValue.mKey) || getAvailabilityStatus() != 0) {
            message = "setting value failed";
            ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
            builder.mResultCode = ControlResult.ResultCode.FAIL;
            builder.setErrorMsg(message);
            return new ControlResult(builder);
        }
        this.mBrightnessValue = controlValue.getAttributeInt(KEY_BRIGHTNESS_VALUE);
        boolean z = true;
        if (controlValue.getAttributeInt(KEY_AUTOMATIC_MODE) != 1) {
            z = false;
        }
        this.mAutomaticMode = z;
        setBrightness();
        ControlResult.Builder builder2 = new ControlResult.Builder(getPreferenceKey());
        builder2.mResultCode = ControlResult.ResultCode.SUCCESS;
        return new ControlResult(builder2);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
