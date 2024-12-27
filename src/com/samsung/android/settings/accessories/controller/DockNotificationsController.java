package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DockNotificationsController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnStart, OnStop {
    private static final String KEY_COVER_NOTIFICATION = "cover_show_sec_notifications";
    private ContentObserver mAodModeObserver;
    private LockPatternUtils mLockPatternUtils;
    private SecPreferenceScreen mPreference;

    public DockNotificationsController(Context context, String str) {
        super(context, str);
        this.mAodModeObserver = null;
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
    }

    private ContentObserver getAodModeObserver() {
        if (this.mAodModeObserver == null) {
            this.mAodModeObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.accessories.controller.DockNotificationsController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            DockNotificationsController.this.updatePreference();
                        }
                    };
        }
        return this.mAodModeObserver;
    }

    public static Intent getDockNotificationsIntent() {
        return DisplaySettings$$ExternalSyntheticOutline0.m(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$LockscreenNotificationActivity");
    }

    private boolean isDockNotificationEnabled() {
        Boolean bool = Boolean.TRUE;
        boolean isLockScreenDisabled =
                this.mLockPatternUtils.isLockScreenDisabled(UserHandle.myUserId());
        boolean z =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(),
                                "aod_mode",
                                0,
                                UserHandle.myUserId())
                        == 1;
        boolean isUsingContentHomeButtonInAOD =
                LockUtils.isUsingContentHomeButtonInAOD(this.mContext);
        if (isLockScreenDisabled && (!z || isUsingContentHomeButtonInAOD)) {
            bool = Boolean.FALSE;
        }
        return bool.booleanValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (UsefulfeatureUtils.isCoverVerified(this.mContext)
                        && !Utils.isTablet()
                        && !SemPersonaManager.isKnoxId(UserHandle.myUserId())
                        && UsefulfeatureUtils.getTypeOfCover(this.mContext) == 8
                        && Utils.isIntentAvailable(this.mContext, getDockNotificationsIntent()))
                ? 0
                : 3;
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
        if (!TextUtils.equals(preference.getKey(), KEY_COVER_NOTIFICATION)) {
            return false;
        }
        LoggingHelper.insertEventLogging(getMetricsCategory(), 1063);
        this.mContext.startActivity(getDockNotificationsIntent());
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

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        updatePreference();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("aod_mode"), true, getAodModeObserver());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(getAodModeObserver());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreference() {
        if (this.mPreference != null) {
            if (Rune.isSamsungDexMode(this.mContext)) {
                this.mPreference.setEnabled(false);
            } else if (isDockNotificationEnabled()) {
                this.mPreference.setEnabled(true);
                this.mPreference.setSummary(R.string.clearview_notifications_summary);
            } else {
                this.mPreference.setEnabled(false);
                this.mPreference.setSummary(R.string.clearview_notifications_summary_disable);
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
