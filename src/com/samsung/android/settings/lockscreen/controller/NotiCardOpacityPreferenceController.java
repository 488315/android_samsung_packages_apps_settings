package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.NotificationCardSeekbarPreference;
import com.samsung.android.settings.lockscreen.SecConceptBehavior;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotiCardOpacityPreferenceController extends BasePreferenceController
        implements SecConceptControllerBehavior, LifecycleObserver {
    static final String KEY = "noti_card_seekbar";
    SecConceptBehavior mContextDashBoard;
    private final ContentObserver mLockNotiCardOpacity;
    LockPatternUtils mLockPatternUtils;
    PreferenceScreen mParentScreen;
    NotificationCardSeekbarPreference mPreference;

    public NotiCardOpacityPreferenceController(
            Context context, Lifecycle lifecycle, SecConceptBehavior secConceptBehavior) {
        super(context, KEY);
        this.mLockNotiCardOpacity =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.lockscreen.controller.NotiCardOpacityPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        NotiCardOpacityPreferenceController.this.updateNotiCardOpacity();
                    }
                };
        this.mContextDashBoard = secConceptBehavior;
        this.mLockPatternUtils = new LockPatternUtils(context);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private void registerContentObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("lock_noticard_opacity"),
                        true,
                        this.mLockNotiCardOpacity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNotiCardOpacity() {
        if (this.mContext != null) {
            LockScreenNotificationSettings lockScreenNotificationSettings =
                    (LockScreenNotificationSettings) this.mContextDashBoard;
            lockScreenNotificationSettings.notifyChange(
                    Float.valueOf(
                            Settings.System.getInt(
                                            r0.getContentResolver(),
                                            "lock_noticard_opacity",
                                            this.mContext
                                                    .getResources()
                                                    .getInteger(
                                                            R.integer
                                                                    .config_default_lock_noticard_opacity))
                                    / 100.0f),
                    getPreferenceKey());
        }
    }

    private void updateVisibility(int i) {
        if (i == 0) {
            setVisible(this.mParentScreen, getPreferenceKey(), true);
        } else {
            setVisible(this.mParentScreen, getPreferenceKey(), false);
        }
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        str.getClass();
        switch (str) {
            case "lock_screen_show_notifications":
                boolean booleanValue = ((Boolean) obj).booleanValue();
                NotificationCardSeekbarPreference notificationCardSeekbarPreference =
                        this.mPreference;
                if (notificationCardSeekbarPreference != null) {
                    notificationCardSeekbarPreference.setEnabled(booleanValue);
                    break;
                }
                break;
            case "noti_inverse_text":
                NotificationCardSeekbarPreference notificationCardSeekbarPreference2 =
                        this.mPreference;
                if (notificationCardSeekbarPreference2 != null) {
                    notificationCardSeekbarPreference2.updateSeekBarDescVisible();
                    break;
                }
                break;
            case "notification_details":
                updateVisibility(0);
                break;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (NotificationCardSeekbarPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        this.mParentScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId())
                        || ((LockScreenNotificationSettings) this.mContextDashBoard).mNeedRedaction)
                ? 0
                : 2;
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (this.mPreference != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mLockNotiCardOpacity);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (this.mPreference != null) {
            registerContentObserver();
        }
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

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {}
}
