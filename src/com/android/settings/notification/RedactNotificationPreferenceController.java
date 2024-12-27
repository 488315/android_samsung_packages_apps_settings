package com.android.settings.notification;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RedactNotificationPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    static final String KEY_LOCKSCREEN_REDACT = "lock_screen_redact";
    static final String KEY_LOCKSCREEN_WORK_PROFILE_REDACT = "lock_screen_work_redact";
    private static final String TAG = "LockScreenNotifPref";
    private ContentObserver mContentObserver;
    private KeyguardManager mKm;
    private RestrictedSwitchPreference mPreference;
    int mProfileUserId;
    private UserManager mUm;

    public RedactNotificationPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.notification.RedactNotificationPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (RedactNotificationPreferenceController.this.mPreference != null) {
                            RedactNotificationPreferenceController.this.mPreference.setEnabled(
                                    RedactNotificationPreferenceController.this
                                                    .getAvailabilityStatus()
                                            != 5);
                        }
                    }
                };
        this.mUm = (UserManager) context.getSystemService(UserManager.class);
        this.mKm = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mProfileUserId = UserHandle.myUserId();
        List profiles = this.mUm.getProfiles(UserHandle.myUserId());
        int size = profiles.size();
        for (int i = 0; i < size; i++) {
            UserInfo userInfo = (UserInfo) profiles.get(i);
            if (userInfo.isManagedProfile()
                    && userInfo.getUserHandle().getIdentifier() != UserHandle.myUserId()) {
                this.mProfileUserId = userInfo.getUserHandle().getIdentifier();
            }
        }
    }

    private boolean getAllowPrivateNotifications(int i) {
        return Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "lock_screen_allow_private_notifications",
                                1,
                                i)
                        != 0
                && getEnforcedAdmin(i) == null;
    }

    private RestrictedLockUtils.EnforcedAdmin getEnforcedAdmin(int i) {
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(this.mContext, 4, i);
        return checkIfKeyguardFeaturesDisabled != null
                ? checkIfKeyguardFeaturesDisabled
                : RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(this.mContext, 8, i);
    }

    private boolean getLockscreenNotificationsEnabled(int i) {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "lock_screen_show_notifications", 1, i)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        int myUserId =
                KEY_LOCKSCREEN_REDACT.equals(getPreferenceKey())
                        ? UserHandle.myUserId()
                        : this.mProfileUserId;
        if (myUserId != -10000) {
            this.mPreference.setDisabledByAdmin(getEnforcedAdmin(myUserId));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (KEY_LOCKSCREEN_WORK_PROFILE_REDACT.equals(getPreferenceKey())
                && this.mProfileUserId == UserHandle.myUserId()) {
            return 2;
        }
        int myUserId =
                KEY_LOCKSCREEN_REDACT.equals(getPreferenceKey())
                        ? UserHandle.myUserId()
                        : this.mProfileUserId;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (!featureFactoryImpl
                .getSecurityFeatureProvider()
                .getLockPatternUtils(this.mContext)
                .isSecure(myUserId)) {
            return 2;
        }
        if (getLockscreenNotificationsEnabled(myUserId)) {
            return (KEY_LOCKSCREEN_WORK_PROFILE_REDACT.equals(getPreferenceKey())
                            && this.mKm.isDeviceLocked(this.mProfileUserId))
                    ? 5
                    : 0;
        }
        return 5;
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
        return R.string.menu_key_notifications;
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
        return getAllowPrivateNotifications(
                KEY_LOCKSCREEN_REDACT.equals(getPreferenceKey())
                        ? UserHandle.myUserId()
                        : this.mProfileUserId);
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
                        Settings.Secure.getUriFor("lock_screen_show_notifications"),
                        false,
                        this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "lock_screen_allow_private_notifications",
                z ? 1 : 0,
                KEY_LOCKSCREEN_REDACT.equals(getPreferenceKey())
                        ? UserHandle.myUserId()
                        : this.mProfileUserId);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
