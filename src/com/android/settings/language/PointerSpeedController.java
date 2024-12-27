package com.android.settings.language;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.input.InputSettings;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.inputmethod.SecPointerSpeedPreference;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PointerSpeedController extends BasePreferenceController implements LifecycleObserver {
    static final String KEY_POINTER_SPEED = "pointer_speed";
    private SecPointerSpeedPreference mPointerSeekbar;
    private final PointerSpeedPreferenceSamsungCallback mPointerSpeedCallback;
    private ContentObserver mSpeedObserver;
    private UserManager um;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PointerSpeedPreferenceSamsungCallback {}

    public PointerSpeedController(Context context) {
        super(context, KEY_POINTER_SPEED);
        this.mPointerSpeedCallback = new PointerSpeedPreferenceSamsungCallback();
        this.mSpeedObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.language.PointerSpeedController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        PointerSpeedController.this.onSpeedChanged();
                    }
                };
        this.um = (UserManager) context.getSystemService("user");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSpeedChanged() {
        int pointerSpeed = InputSettings.getPointerSpeed(this.mContext);
        this.mPointerSeekbar.setProgress$1(pointerSpeed + 7, true);
        LoggingHelper.insertEventLogging(57, 4705, pointerSpeed);
        this.mPointerSeekbar.setSeekBarContentDescription(Integer.toString(pointerSpeed));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        int pointerSpeed = InputSettings.getPointerSpeed(this.mContext);
        SecPointerSpeedPreference secPointerSpeedPreference =
                (SecPointerSpeedPreference) preferenceScreen.findPreference(KEY_POINTER_SPEED);
        this.mPointerSeekbar = secPointerSpeedPreference;
        if (secPointerSpeedPreference != null) {
            Log.e("SecPointerSpeedPreference", "setCallback called");
            SecPointerSpeedPreference secPointerSpeedPreference2 = this.mPointerSeekbar;
            if (14 != secPointerSpeedPreference2.mMax) {
                secPointerSpeedPreference2.mMax = 14;
                secPointerSpeedPreference2.notifyChanged();
            }
            this.mPointerSeekbar.setProgress$1(pointerSpeed + 7, true);
            this.mPointerSeekbar.setSeekBarContentDescription(Integer.toString(pointerSpeed));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        UserManager userManager = this.um;
        StringBuilder sb = Utils.sBuilder;
        if (userManager.getUserInfo(userManager.getUserHandle()).isSecureFolder()) {
            return 2;
        }
        return this.mContext.getResources().getBoolean(R.bool.config_show_pointer_speed) ? 0 : 3;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Lifecycle lifecycle) {
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor(KEY_POINTER_SPEED), true, this.mSpeedObserver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSpeedObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
