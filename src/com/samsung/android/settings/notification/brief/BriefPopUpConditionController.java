package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecDisabledAppearanceSwitchPreference;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpConditionController extends TogglePreferenceController {
    private static final String CONDITION_DB = "edge_lighting_show_condition";
    private static final String DB_NAME = "edge_lighting";
    private static final String KEY_BRIEF_POPUP_CONDITION = "brief_popup_show_even_screen_off";
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private static final String TAG = "BriefPopUpConditionController";
    LockPatternUtils mLockPatternUtils;
    PreferenceScreen mParentScreen;
    private SecDisabledAppearanceSwitchPreference mPreference;
    private String mPreferenceKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.BriefPopUpConditionController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean z =
                    Settings.System.getInt(
                                    context.getContentResolver(),
                                    BriefPopUpConditionController.DB_NAME,
                                    !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                            == 1;
            String valueOf = String.valueOf(36104);
            String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    public BriefPopUpConditionController(Context context, String str) {
        super(context, KEY_BRIEF_POPUP_CONDITION);
        this.mPreferenceKey = str;
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
    }

    private boolean isLockscreenNotificationsDisabled() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "lock_screen_show_notifications", 0, -2)
                == 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDisabledAppearanceSwitchPreference secDisabledAppearanceSwitchPreference =
                (SecDisabledAppearanceSwitchPreference)
                        preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = secDisabledAppearanceSwitchPreference;
        secDisabledAppearanceSwitchPreference.mEnabledAppearance =
                !isLockscreenNotificationsDisabled()
                        || this.mLockPatternUtils.isLockScreenDisabled(UserHandle.myUserId());
        secDisabledAppearanceSwitchPreference.notifyChanged();
        this.mPreference.mMsg =
                (!isLockscreenNotificationsDisabled()
                                || this.mLockPatternUtils.isLockScreenDisabled(
                                        UserHandle.myUserId()))
                        ? null
                        : this.mContext.getString(R.string.keyword_turn_on_lock_screen_noti_first);
        this.mParentScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM",
                                ApnSettings.MVNO_NONE)
                        .contains("aodversion")
                ? 0
                : 3;
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 36104;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_display;
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
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), CONDITION_DB, 0, -2)
                == 0;
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
        LoggingHelper.insertEventLogging(getMetricsCategory(), 36104, z ? 0L : 1L);
        return Settings.System.putIntForUser(
                this.mContext.getContentResolver(), CONDITION_DB, !z ? 1 : 0, -2);
    }

    public void updateVisible() {
        if (SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", ApnSettings.MVNO_NONE)
                .contains("aodversion")) {
            setVisible(
                    this.mParentScreen,
                    getPreferenceKey(),
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    DB_NAME,
                                    !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                            == 1);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
