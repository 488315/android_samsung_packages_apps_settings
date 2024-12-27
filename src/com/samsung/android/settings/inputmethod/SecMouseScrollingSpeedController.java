package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.SliderPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMouseScrollingSpeedController extends SliderPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    static final String KEY_MOUSE_SCROLLING_SPEED = "mouse_scrolling_speed";
    private final SecMouseScrollingSpeedPreferenceCallback mScrollingSpeedCallback;
    private ContentObserver mScrollingSpeedObserver;
    private SecMouseScrollingSpeedPreference mScrollingSpeedSeekbar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SecMouseScrollingSpeedPreferenceCallback {}

    public SecMouseScrollingSpeedController(Context context, String str) {
        super(context, KEY_MOUSE_SCROLLING_SPEED);
        this.mScrollingSpeedCallback = new SecMouseScrollingSpeedPreferenceCallback();
        this.mScrollingSpeedObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.inputmethod.SecMouseScrollingSpeedController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SecMouseScrollingSpeedController.this.onScrollingSpeedChanged();
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollingSpeedChanged() {
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), KEY_MOUSE_SCROLLING_SPEED, 1);
        int i2 = i - 1;
        this.mScrollingSpeedSeekbar.setProgress$1(i2, true);
        SALogging.insertSALog(i, "770100", String.valueOf(77012), (String) null);
        this.mScrollingSpeedSeekbar.setSeekBarContentDescription(Integer.toString(i2));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), KEY_MOUSE_SCROLLING_SPEED, 1);
        SecMouseScrollingSpeedPreference secMouseScrollingSpeedPreference =
                (SecMouseScrollingSpeedPreference)
                        preferenceScreen.findPreference(KEY_MOUSE_SCROLLING_SPEED);
        this.mScrollingSpeedSeekbar = secMouseScrollingSpeedPreference;
        if (secMouseScrollingSpeedPreference != null) {
            Log.e("SecMouseScrollingSpeedPreference", "setCallback called");
            SecMouseScrollingSpeedPreference secMouseScrollingSpeedPreference2 =
                    this.mScrollingSpeedSeekbar;
            if (99 != secMouseScrollingSpeedPreference2.mMax) {
                secMouseScrollingSpeedPreference2.mMax = 99;
                secMouseScrollingSpeedPreference2.notifyChanged();
            }
            int i2 = i - 1;
            this.mScrollingSpeedSeekbar.setProgress$1(i2, true);
            this.mScrollingSpeedSeekbar.setSeekBarContentDescription(Integer.toString(i2));
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
        return 100;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 1;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        return Settings.System.getInt(
                this.mContext.getContentResolver(), KEY_MOUSE_SCROLLING_SPEED, 1);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor(KEY_MOUSE_SCROLLING_SPEED),
                        true,
                        this.mScrollingSpeedObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mScrollingSpeedObserver);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        return Settings.System.putInt(
                this.mContext.getContentResolver(), KEY_MOUSE_SCROLLING_SPEED, i);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
