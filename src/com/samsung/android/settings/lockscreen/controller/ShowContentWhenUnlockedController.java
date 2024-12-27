package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.face.FaceSettingsHelper;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShowContentWhenUnlockedController extends TogglePreferenceController
        implements SecConceptControllerBehavior {
    private static final String KEY = "show_content_when_unlocked";
    private static final String LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE =
            "lock_screen_allow_private_notifications_when_unsecure";
    static final int OFF = 0;
    static final int ON = 1;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    LockPatternUtils mLockPatternUtils;
    SecSwitchPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.controller.ShowContentWhenUnlockedController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean z =
                    Settings.Secure.getIntForUser(
                                    context.getContentResolver(),
                                    ShowContentWhenUnlockedController
                                            .LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE,
                                    Rune.NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED_DEFAULT_ON
                                            ? 1
                                            : 0,
                                    -2)
                            != 0;
            String valueOf = String.valueOf(9034);
            String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    public ShowContentWhenUnlockedController(Context context) {
        super(context, KEY);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private boolean isAllowPrivateWhenUnsecure() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE,
                        Rune.NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED_DEFAULT_ON ? 1 : 0,
                        -2)
                != 0;
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        if ("lock_screen_show_notifications".equals(str)) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            SecSwitchPreference secSwitchPreference = this.mPreference;
            if (secSwitchPreference != null) {
                secSwitchPreference.setEnabled(booleanValue);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        refreshSummary(secSwitchPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int semGetMyUserId = UserHandle.semGetMyUserId();
        boolean z =
                Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "face_screen_lock",
                                0,
                                semGetMyUserId)
                        == 1;
        boolean z2 = this.mLockPatternUtils.getBiometricState(1, semGetMyUserId) == 1;
        if ((z
                        && FaceSettingsHelper.getFaceStayOnLockScreenBooleanValue(
                                this.mContext, semGetMyUserId))
                || (z2
                        && FingerprintSettingsUtils.getStayOnLockScreen(
                                this.mContext, semGetMyUserId))) {
            return 0;
        }
        if (!Rune.NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED) {
            return 3;
        }
        this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId());
        return 2;
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(
                Utils.isTablet()
                        ? R.string
                                .sec_lockscreen_notifications_show_content_when_unlocked_tablet_description
                        : R.string
                                .sec_lockscreen_notifications_show_content_when_unlocked_phone_description);
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
        return isAllowPrivateWhenUnsecure();
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE,
                z ? 1 : 0,
                -2);
        SALogging.insertSALog(z ? 1L : 0L, String.valueOf(getMetricsCategory()), "WU4452");
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}
}
