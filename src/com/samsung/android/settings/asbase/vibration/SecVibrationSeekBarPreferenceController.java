package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.Ringtone;
import android.os.Handler;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.provider.Settings;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SliderPreferenceController;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecVibrationSeekBarPreferenceController extends SliderPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver {
    private static final int MAX_VIBRATION_INTENSITY_DC_HAPTIC = 3;
    protected static final int MAX_VIBRATION_INTENSITY_DEFAULT = 5;
    protected static final int MAX_VIBRATION_INTENSITY_FORCE_TOUCH = 4;
    private ContentObserver mPrefObserver;
    protected SecVibrationSeekBarPreference mPreference;

    public SecVibrationSeekBarPreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getSimSlot() {
        return SimUtils.isEnabledSIM2Only() ? 1 : 0;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            SecVibrationSeekBarPreference secVibrationSeekBarPreference =
                    (SecVibrationSeekBarPreference)
                            preferenceScreen.findPreference(getPreferenceKey());
            this.mPreference = secVibrationSeekBarPreference;
            String string =
                    secVibrationSeekBarPreference.getTitle() == null
                            ? this.mContext.getResources().getString(getTitle())
                            : this.mPreference.getTitle().toString();
            this.mPreference.setTitle(string);
            SecVibrationSeekBarPreference secVibrationSeekBarPreference2 = this.mPreference;
            String systemDBName = getSystemDBName();
            int maxVibrationIntensity = getMaxVibrationIntensity();
            int sAEventId = getSAEventId();
            Ringtone ringtone = getRingtone(getSimSlot());
            VibrationAttributes vibrationAttributes = getVibrationAttributes();
            int stream = getStream();
            VibrationEffect effect = getEffect(getSimSlot());
            String syncDbName = getSyncDbName(getSimSlot());
            secVibrationSeekBarPreference2.mSystemDBName = systemDBName;
            secVibrationSeekBarPreference2.mSALogEventId = sAEventId;
            secVibrationSeekBarPreference2.mMaxIntensity = maxVibrationIntensity;
            secVibrationSeekBarPreference2.setMax(maxVibrationIntensity);
            secVibrationSeekBarPreference2.mTitle = string;
            secVibrationSeekBarPreference2.mRingtone = ringtone;
            secVibrationSeekBarPreference2.mVibrationAttributes = vibrationAttributes;
            secVibrationSeekBarPreference2.mStream = stream;
            secVibrationSeekBarPreference2.mVibrationEffect = effect;
            secVibrationSeekBarPreference2.mSyncDbName = syncDbName;
            this.mPrefObserver =
                    new ContentObserver(
                            new Handler()) { // from class:
                                             // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreferenceController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            SecVibrationSeekBarPreference secVibrationSeekBarPreference3 =
                                    SecVibrationSeekBarPreferenceController.this.mPreference;
                            if (secVibrationSeekBarPreference3 != null) {
                                secVibrationSeekBarPreference3.setCurrentProgress();
                            }
                        }
                    };
        }
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

    public abstract VibrationEffect getEffect(int i);

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
        SecVibrationSeekBarPreference secVibrationSeekBarPreference = this.mPreference;
        return secVibrationSeekBarPreference != null
                ? secVibrationSeekBarPreference.mMax
                : getMaxVibrationIntensity();
    }

    public int getMaxVibrationIntensity() {
        return VibUtils.isSupportDcHaptic(this.mContext) ? 3 : 5;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 0;
    }

    public abstract Ringtone getRingtone(int i);

    public abstract int getSAEventId();

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        SecVibrationSeekBarPreference secVibrationSeekBarPreference = this.mPreference;
        return secVibrationSeekBarPreference != null
                ? secVibrationSeekBarPreference.mSeekBarValue
                : Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        getSystemDBName(),
                        getMaxVibrationIntensity());
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public abstract int getStream();

    public abstract String getSyncDbName(int i);

    public abstract String getSystemDBName();

    public abstract int getTitle();

    public abstract VibrationAttributes getVibrationAttributes();

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
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (this.mPreference == null || this.mPrefObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mPrefObserver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (this.mPreference == null || this.mPrefObserver == null) {
            return;
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor(getSystemDBName()),
                        false,
                        this.mPrefObserver,
                        -2);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        SecVibrationSeekBarPreference secVibrationSeekBarPreference = this.mPreference;
        if (secVibrationSeekBarPreference != null) {
            secVibrationSeekBarPreference.setValueInternal(i, true);
        }
        return Settings.System.putInt(this.mContext.getContentResolver(), getSystemDBName(), i);
    }

    public abstract void updatePreferenceIcon(int i);

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z = preference instanceof RestrictedPreference;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
