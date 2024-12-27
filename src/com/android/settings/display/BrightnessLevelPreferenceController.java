package com.android.settings.display;

import android.annotation.Nullable;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.MathUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BrightnessLevelPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnStart, OnStop {
    private static final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");
    private static final String TAG = "BrightnessPrefCtrl";
    private ContentObserver mBrightnessObserver;
    private final ContentResolver mContentResolver;
    private final DisplayManager.DisplayListener mDisplayListener;
    private final DisplayManager mDisplayManager;
    private final Handler mHandler;
    private boolean mInSetupWizard;

    @Nullable private Preference mPreference;

    public BrightnessLevelPreferenceController(Context context, Lifecycle lifecycle) {
        this(context, context.getString(R.string.preference_key_brightness_level), lifecycle);
    }

    private double getCurrentBrightness() {
        int i;
        BrightnessInfo brightnessInfo = this.mContext.getDisplay().getBrightnessInfo();
        if (brightnessInfo != null) {
            float norm =
                    MathUtils.norm(
                                    brightnessInfo.brightnessMinimum,
                                    brightnessInfo.brightnessMaximum,
                                    brightnessInfo.brightness)
                            * 12.0f;
            i =
                    Math.round(
                            MathUtils.lerp(
                                    0,
                                    CustomDeviceManager.QUICK_PANEL_ALL,
                                    norm <= 1.0f
                                            ? MathUtils.sqrt(norm) * 0.5f
                                            : (MathUtils.log(norm - 0.28466892f) * 0.17883277f)
                                                    + 0.5599107f));
        } else {
            i = 0;
        }
        return getPercentage(i, 0, CustomDeviceManager.QUICK_PANEL_ALL);
    }

    private double getPercentage(double d, int i, int i2) {
        if (d > i2) {
            return 1.0d;
        }
        double d2 = i;
        if (d < d2) {
            return 0.0d;
        }
        return (d - d2) / (i2 - i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatedSummary(Preference preference) {
        if (preference != null) {
            preference.setSummary(NumberFormat.getPercentInstance().format(getCurrentBrightness()));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mInSetupWizard ? 2 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        Intent intent = new Intent("com.android.intent.action.SHOW_BRIGHTNESS_DIALOG");
        intent.setPackage("com.android.systemui");
        intent.putExtra("page_transition_type", -1);
        intent.putExtra("android.intent.extra.BRIGHTNESS_DIALOG_IS_FULL_WIDTH", true);
        this.mContext.startActivityForResult(
                preference.getKey(),
                intent,
                0,
                ActivityOptions.makeCustomAnimation(
                                this.mContext, android.R.anim.fade_in, android.R.anim.fade_out)
                        .toBundle());
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContentResolver.registerContentObserver(
                BRIGHTNESS_ADJ_URI, false, this.mBrightnessObserver);
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler, 8L);
        updatedSummary(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContentResolver.unregisterContentObserver(this.mBrightnessObserver);
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setInSetupWizard(boolean z) {
        this.mInSetupWizard = z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference.isEnabled()
                && UserManager.get(this.mContext)
                        .hasBaseUserRestriction("no_config_brightness", Process.myUserHandle())) {
            preference.setEnabled(false);
        }
        updatedSummary(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private BrightnessLevelPreferenceController(Context context, String str, Lifecycle lifecycle) {
        super(context, str);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mBrightnessObserver =
                new ContentObserver(
                        handler) { // from class:
                                   // com.android.settings.display.BrightnessLevelPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        BrightnessLevelPreferenceController brightnessLevelPreferenceController =
                                BrightnessLevelPreferenceController.this;
                        brightnessLevelPreferenceController.updatedSummary(
                                brightnessLevelPreferenceController.mPreference);
                    }
                };
        this.mDisplayListener =
                new DisplayManager
                        .DisplayListener() { // from class:
                                             // com.android.settings.display.BrightnessLevelPreferenceController.2
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayChanged(int i) {
                        BrightnessLevelPreferenceController brightnessLevelPreferenceController =
                                BrightnessLevelPreferenceController.this;
                        brightnessLevelPreferenceController.updatedSummary(
                                brightnessLevelPreferenceController.mPreference);
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayAdded(int i) {}

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayRemoved(int i) {}
                };
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mContentResolver = this.mContext.getContentResolver();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
