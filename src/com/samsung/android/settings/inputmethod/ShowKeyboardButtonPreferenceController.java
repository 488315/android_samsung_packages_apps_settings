package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.taskbar.TaskBarPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShowKeyboardButtonPreferenceController extends TogglePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnStart, OnStop {
    private static final String KEY_SHOW_KEYBOARD_BUTTON = "key_show_keyboard_button";
    private static final String TAG = "ShowKeyboardButtonPreferenceController";
    private SecSwitchPreferenceScreen mPreference;
    private ContentObserver mShowKeyboardButtonObserver;

    public ShowKeyboardButtonPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, KEY_SHOW_KEYBOARD_BUTTON);
        this.mShowKeyboardButtonObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.inputmethod.ShowKeyboardButtonPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        Log.d(ShowKeyboardButtonPreferenceController.TAG, "onChange called");
                        boolean z2 =
                                Settings.Secure.getInt(
                                                ((AbstractPreferenceController)
                                                                ShowKeyboardButtonPreferenceController
                                                                        .this)
                                                        .mContext.getContentResolver(),
                                                "show_keyboard_button",
                                                0)
                                        != 0;
                        ShowKeyboardButtonPreferenceController.this.setChecked(z2);
                        ShowKeyboardButtonPreferenceController.this.setSummary(z2);
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private boolean getShowKeyboardButtonPreferenceState() {
        return !(Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "navigation_bar_gesture_while_hidden",
                                0)
                        != 0)
                || (Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "navigation_bar_button_to_hide_keyboard",
                                1)
                        != 0);
    }

    private boolean isTaskBarEnabled() {
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        TaskBarPreferenceController.KEY_TASK_BAR_SETTING,
                        1)
                == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSummary(boolean z) {
        if (!z) {
            this.mPreference.setSummary(R.string.show_keyboard_button_summary);
        } else if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "show_keyboard_button_position", 0)
                == 0) {
            this.mPreference.setSummary(R.string.navigationbar_show_on_left_side);
        } else {
            this.mPreference.setSummary(R.string.navigationbar_show_on_right_side);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mContext.getResources().getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data)
                || isTaskBarEnabled()) {
            return (Rune.supportDesktopMode() && Rune.isSamsungDexMode(this.mContext)) ? 3 : 0;
        }
        return 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "show_keyboard_button", 1)
                > 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
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
                        Settings.Secure.getUriFor("show_keyboard_button"),
                        false,
                        this.mShowKeyboardButtonObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(this.mShowKeyboardButtonObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        this.mPreference.setChecked(z);
        return Settings.Secure.putInt(
                this.mContext.getContentResolver(), "show_keyboard_button", z ? 1 : 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        this.mPreference.setVisible(isAvailable());
        setSummary(getThreadEnabled());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
