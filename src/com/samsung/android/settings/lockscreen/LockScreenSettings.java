package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;
import androidx.preference.SecSwitchPreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.biometrics.combination.CombinedBiometricSearchIndexProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.biometrics.face.FaceFragment;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintFragment;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.controller.AODPreferenceController;
import com.samsung.android.settings.lockscreen.controller.DualDarDoScreenLockTypePreferenceController;
import com.samsung.android.settings.lockscreen.controller.LockScreenTrustAgentPreferenceController;
import com.samsung.android.settings.lockscreen.controller.OwnerInfoPreferenceController;
import com.samsung.android.settings.lockscreen.controller.ScreenLockTypePreferenceController;
import com.samsung.android.settings.lockscreen.controller.SecureLockPreferenceController;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockScreenSettings extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_lockscreen_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public FragmentActivity mContext;
    public PreferenceCategory mEditorCategory;
    public SecSwitchPreference mEditorHijriCalendar;
    public SecSwitchPreference mEditorLunarCalendar;
    public final boolean mIsDualDarDoEnabled;
    public LockPatternUtils mLockPatternUtils;
    public SecSwitchPreference mTouchAndHoldToEditPreference;
    public Intent mTrustAgentClickIntent;
    public SecRelativeLinkView mRelativeLinkView = null;
    public final int mUserId = UserHandle.myUserId();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
            if (!LockUtils.isSupportAodService()) {
                ((ArrayList) nonIndexableKeys).add("always_on_screen");
            }
            if (!LockUtils.isSupportHijriCalendar(context)) {
                ((ArrayList) nonIndexableKeys).add("editor_hijri_calendar");
            }
            if (!LockUtils.isSupportLunarCalendarMenu(context)) {
                ((ArrayList) nonIndexableKeys).add("editor_lunar_calendar");
            }
            if (Rune.isSamsungDexMode(context) || Utils.isMinimalBatteryUseEnabled(context)) {
                ((ArrayList) nonIndexableKeys).add("editor_touch_and_hold_to_edit");
            }
            if (lockPatternUtils.isLockScreenDisabled(UserHandle.myUserId())) {
                ((ArrayList) nonIndexableKeys).add("editor_touch_and_hold_to_edit");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            int i = Rune.SUPPORT_AOD ? R.string.lockscreen_and_aod : R.string.lockscreen;
            searchIndexableRaw.title = String.valueOf(i);
            searchIndexableRaw.screenTitle = resources.getString(i);
            ((SearchIndexableData) searchIndexableRaw).key = "lock_settings_screen";
            ((SearchIndexableData) searchIndexableRaw).iconResId =
                    R.drawable.sec_ic_settings_lockscreen;
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            searchIndexableRaw2.title =
                    resources.getString(R.string.sec_secured_lock_settings_title);
            searchIndexableRaw2.screenTitle = resources.getString(i);
            ((SearchIndexableData) searchIndexableRaw2).className =
                    LockScreenSettings.class.getName();
            ((SearchIndexableData) searchIndexableRaw2).key = "secured_lock_settings";
            ((SearchIndexableData) searchIndexableRaw2).intentTargetPackage =
                    context.getPackageName();
            ((SearchIndexableData) searchIndexableRaw2).intentTargetClass =
                    SecuredLockSettingsMenu.class.getName();
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (Utils.isDesktopDualMode(context) || Utils.isDesktopStandaloneMode(context)) {
                return false;
            }
            String str = LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY;
            if (Rune$$ExternalSyntheticOutline0.m(
                            "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_POLICY_LIST",
                            "disable_keyguard")
                    || LockUtils.isLockMenuDisabledByEdm(context)) {
                return false;
            }
            return !(this instanceof CombinedBiometricSearchIndexProvider);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            char c;
            int i;
            LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
            int myUserId = UserHandle.myUserId();
            ArrayList arrayList = new ArrayList();
            char c2 = 'a';
            try {
            } catch (SecurityException e) {
                Log.e("LockScreenSettings", "SecurityException : " + e.getMessage());
            }
            if (lockPatternUtils.isSecure(myUserId)) {
                byte b =
                        (SecurityUtils.isFingerprintDisabledByDPM(context, myUserId)
                                        || lockPatternUtils.getBiometricState(1, myUserId) == 0)
                                ? false
                                : true;
                byte b2 =
                        (SecurityUtils.isFaceDisabledByDPM(context, myUserId)
                                        || lockPatternUtils.getBiometricState(256, myUserId) == 0)
                                ? false
                                : true;
                int i2 =
                        (!b == true || b2 == true)
                                ? (b == true && b2 == true) ? 2 : (b == true || !b2 == true) ? 0 : 3
                                : 1;
                int keyguardStoredPasswordQuality =
                        lockPatternUtils.getKeyguardStoredPasswordQuality(myUserId);
                if (keyguardStoredPasswordQuality == 65536) {
                    i = i2 + 103;
                } else if (keyguardStoredPasswordQuality == 131072
                        || keyguardStoredPasswordQuality == 196608) {
                    i = i2 + 99;
                } else {
                    if (keyguardStoredPasswordQuality == 262144
                            || keyguardStoredPasswordQuality == 327680
                            || keyguardStoredPasswordQuality == 393216) {
                        i = i2 + 107;
                    }
                    c = 'z';
                }
                c = (char) i;
            } else {
                c = lockPatternUtils.isLockScreenDisabled(myUserId) ? 'a' : 'b';
            }
            String valueOf = String.valueOf((int) c);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf;
            statusData.mStatusKey = "LSS9002";
            arrayList.add(statusData);
            String valueOf2 =
                    String.valueOf(
                            (lockPatternUtils.getDeviceOwnerInfo() == null
                                            && TextUtils.isEmpty(
                                                    lockPatternUtils.getOwnerInfo(myUserId)))
                                    ? 0
                                    : 1);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf2;
            statusData2.mStatusKey = "LSS9018";
            arrayList.add(statusData2);
            if (Rune.isDomesticModel()) {
                String valueOf3 =
                        String.valueOf(
                                1
                                        ^ (TextUtils.isEmpty(
                                                        lockPatternUtils.getPasswordHint(myUserId))
                                                ? 1
                                                : 0));
                StatusData statusData3 = new StatusData();
                statusData3.mStatusValue = valueOf3;
                statusData3.mStatusKey = "LSS9012";
                arrayList.add(statusData3);
            }
            String valueOf4 =
                    String.valueOf(lockPatternUtils.getPowerButtonInstantlyLocks(myUserId) ? 1 : 0);
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = valueOf4;
            statusData4.mStatusKey = "LSS9030";
            arrayList.add(statusData4);
            String[] stringArray =
                    context.getResources().getStringArray(R.array.lock_after_timeout_values);
            long j =
                    Settings.Secure.getLong(
                            context.getContentResolver(), "lock_screen_lock_after_timeout", 5000L);
            for (int i3 = 0; i3 < stringArray.length; i3++) {
                if (Long.valueOf(stringArray[i3]).longValue() == j) {
                    c2 = (char) (c2 + i3);
                }
            }
            String ch = Character.toString(c2);
            StatusData statusData5 = new StatusData();
            statusData5.mStatusValue = ch;
            statusData5.mStatusKey = "LSS9029";
            arrayList.add(statusData5);
            String str =
                    lockPatternUtils.isVisiblePatternEnabled(myUserId)
                            ? "1"
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData6 = new StatusData();
            statusData6.mStatusValue = str;
            statusData6.mStatusKey = "LSS9028";
            arrayList.add(statusData6);
            return arrayList;
        }
    }

    public LockScreenSettings() {
        new Handler();
        String str = KnoxUtils.mDeviceType;
        this.mIsDualDarDoEnabled = DualDarManager.isOnDeviceOwnerEnabled();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AODPreferenceController(context, this));
        arrayList.add(new LockScreenTrustAgentPreferenceController(context, this));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LockScreenSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_lockscreen_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult : requestCode : ", " resultCode : ", i, i2, "LockScreenSettings");
        if (i == 123) {
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.lockscreen.LockScreenSettings$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LockScreenSettings lockScreenSettings = LockScreenSettings.this;
                            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                    LockScreenSettings.SEARCH_INDEX_DATA_PROVIDER;
                            ((LockScreenTrustAgentPreferenceController)
                                            lockScreenSettings.use(
                                                    LockScreenTrustAgentPreferenceController.class))
                                    .updatePreference();
                            ((SecureLockPreferenceController)
                                            lockScreenSettings.use(
                                                    SecureLockPreferenceController.class))
                                    .updatePreference();
                            ((OwnerInfoPreferenceController)
                                            lockScreenSettings.use(
                                                    OwnerInfoPreferenceController.class))
                                    .updatePreference();
                        }
                    });
            if (this.mIsDualDarDoEnabled) {
                ((DualDarDoScreenLockTypePreferenceController)
                                use(DualDarDoScreenLockTypePreferenceController.class))
                        .updatePreference();
                return;
            }
            return;
        }
        if (i == 177 && i2 == -1) {
            ((LockScreenTrustAgentPreferenceController)
                            use(LockScreenTrustAgentPreferenceController.class))
                    .startTrustAgent();
        } else if (i == 200 && i2 == -1) {
            ((OwnerInfoPreferenceController) use(OwnerInfoPreferenceController.class))
                    .updatePreference();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((ScreenLockTypePreferenceController) use(ScreenLockTypePreferenceController.class))
                .init(this, 123);
        if (this.mIsDualDarDoEnabled) {
            ((DualDarDoScreenLockTypePreferenceController)
                            use(DualDarDoScreenLockTypePreferenceController.class))
                    .init(this, 123);
        }
        ((SecureLockPreferenceController) use(SecureLockPreferenceController.class)).init(this);
        ((OwnerInfoPreferenceController) use(OwnerInfoPreferenceController.class)).init(this, 200);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        int i;
        FragmentActivity fragmentActivity;
        int i2;
        this.mContext = getActivity();
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        super.onCreate(bundle);
        if (bundle != null && bundle.containsKey("trust_agent_click_intent")) {
            this.mTrustAgentClickIntent = (Intent) bundle.getParcelable("trust_agent_click_intent");
        }
        if (this.mLockPatternUtils.isSecure(this.mUserId)) {
            int keyguardStoredPasswordQuality =
                    this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mUserId);
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    keyguardStoredPasswordQuality, "passwordQuality : ", "LockScreenSettings");
            i =
                    keyguardStoredPasswordQuality != 65536
                            ? (keyguardStoredPasswordQuality == 131072
                                            || keyguardStoredPasswordQuality == 196608)
                                    ? R.string.sec_unlock_set_unlock_pin_title
                                    : (keyguardStoredPasswordQuality == 262144
                                                    || keyguardStoredPasswordQuality == 327680
                                                    || keyguardStoredPasswordQuality == 393216)
                                            ? R.string.sec_unlock_set_unlock_password_title
                                            : keyguardStoredPasswordQuality != 458752
                                                    ? keyguardStoredPasswordQuality != 589824
                                                            ? 0
                                                            : R.string.b_unlock_title
                                                    : R.string.unlock_set_unlock_cac_pin_title
                            : R.string.sec_unlock_set_unlock_pattern_title;
        } else {
            i =
                    this.mLockPatternUtils.isLockScreenDisabled(this.mUserId)
                            ? R.string.sec_unlock_set_unlock_off_title
                            : R.string.sec_unlock_set_unlock_none_title;
        }
        Log.d("LockScreenSettings", "InitPreference : " + i);
        this.mEditorCategory = (PreferenceCategory) findPreference("lockscreen_editor_category");
        this.mTouchAndHoldToEditPreference =
                (SecSwitchPreference) findPreference("editor_touch_and_hold_to_edit");
        this.mEditorHijriCalendar = (SecSwitchPreference) findPreference("editor_hijri_calendar");
        this.mEditorLunarCalendar = (SecSwitchPreference) findPreference("editor_lunar_calendar");
        if (!LockUtils.isSupportAodService()) {
            removePreference("always_on_screen");
        }
        if (Rune.SUPPORT_AOD) {
            fragmentActivity = this.mContext;
            i2 = R.string.lockscreen_and_aod;
        } else {
            fragmentActivity = this.mContext;
            i2 = R.string.lockscreen;
        }
        String string = fragmentActivity.getString(i2);
        if (getActivity() != null) {
            getActivity().setTitle(string);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ((AODPreferenceController) use(AODPreferenceController.class)).unregisterContentObserver();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        boolean z;
        UserInfo userInfo;
        UserInfo userInfo2;
        if (this.mLockPatternUtils.isLockScreenDisabled(this.mUserId)
                && Settings.System.getInt(
                                getContentResolver(), "lockscreen_notifications_option", 0)
                        != 0) {
            Settings.Secure.putInt(getContentResolver(), "lock_screen_show_notifications", 0);
        }
        if (!LockUtils.isLockMenuDisabledByEdm(this.mContext)) {
            boolean isShopDemo = Rune.isShopDemo(this.mContext);
            boolean isLDUModel = Rune.isLDUModel();
            Bundle bundle = new Bundle();
            boolean isUCMKeyguardEnabled = UCMUtils.isUCMKeyguardEnabled();
            if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
                this.mRelativeLinkView = new SecRelativeLinkView(this.mContext);
                z = true;
            } else {
                z = false;
            }
            SecRelativeLinkView secRelativeLinkView = this.mRelativeLinkView;
            if (secRelativeLinkView != null) {
                secRelativeLinkView.mLinkContainer.removeAllViews();
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData.flowId = "LSE4465LF";
                settingsPreferenceFragmentLinkData.callerMetric = 4400;
                settingsPreferenceFragmentLinkData.titleRes = R.string.sec_edit_lockscreen_title;
                Intent intent = new Intent();
                intent.setAction("com.samsung.dressroom.intent.action.SHOW_LOCK_SETTINGS");
                intent.addFlags(335544352);
                settingsPreferenceFragmentLinkData.intent = intent;
                settingsPreferenceFragmentLinkData.clickInterval = 1000L;
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.flowId = "LSE4465LP";
                settingsPreferenceFragmentLinkData2.callerMetric = 4400;
                settingsPreferenceFragmentLinkData2.titleRes = R.string.notifications_on_lockscreen;
                Intent intent2 = new Intent();
                intent2.setClass(this.mContext, Settings.LockscreenNotificationActivity.class);
                settingsPreferenceFragmentLinkData2.intent = intent2;
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData3.flowId = "LSE4465LH";
                settingsPreferenceFragmentLinkData3.callerMetric = 4400;
                settingsPreferenceFragmentLinkData3.titleRes = R.string.bio_face_recognition_title;
                settingsPreferenceFragmentLinkData3.fragment = FaceFragment.class.getName();
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData4 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData4.flowId = "LSE4465LN";
                settingsPreferenceFragmentLinkData4.callerMetric = 4400;
                settingsPreferenceFragmentLinkData4.titleRes =
                        R.string.bio_fingerprint_sanner_title;
                settingsPreferenceFragmentLinkData4.fragment = FingerprintFragment.class.getName();
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData5 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData5.flowId = "LSE4465SA";
                settingsPreferenceFragmentLinkData5.callerMetric = 4400;
                settingsPreferenceFragmentLinkData5.titleRes =
                        R.string.sec_lock_screen_show_alterting_notifications_only;
                bundle.putString(":settings:fragment_args_key", "lockscreen_alert_notification");
                Intent intent3 = new Intent();
                intent3.setClass(this.mContext, Settings.LockscreenNotificationActivity.class);
                intent3.putExtra(":settings:show_fragment_args", bundle);
                intent3.putExtra("extra_from_search", true);
                settingsPreferenceFragmentLinkData5.intent = intent3;
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData6 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData6.flowId = "4616";
                settingsPreferenceFragmentLinkData6.callerMetric = 4400;
                Intent intent4 = new Intent();
                intent4.setClassName(
                        "com.samsung.android.applock",
                        "com.samsung.android.applock.settings.AppLockSettingsActivity");
                intent4.putExtra("is_from_suggestions", true);
                settingsPreferenceFragmentLinkData6.intent = intent4;
                settingsPreferenceFragmentLinkData6.topLevelKey = "top_level_advanced_features";
                settingsPreferenceFragmentLinkData6.titleRes = R.string.applock_app_name;
                boolean isMultifactorAuthEnforced =
                        KnoxUtils.isKnoxOrganizationOwnedDevice(this.mContext, this.mUserId)
                                ? KnoxUtils.isMultifactorAuthEnforced(this.mContext, this.mUserId)
                                : false;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
                if ((this.mUserId == 0
                                || (userInfo2 =
                                                UserManager.get(this.mContext)
                                                        .getUserInfo(this.mUserId))
                                        == null
                                || !userInfo2.isGuest())
                        && !isShopDemo
                        && !isLDUModel
                        && !isUCMKeyguardEnabled
                        && !isMultifactorAuthEnforced
                        && !SecurityUtils.isFaceDisabled(this.mContext, this.mUserId)) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData3);
                }
                if (SecurityUtils.hasFingerprintFeature(this.mContext)
                        && ((this.mUserId == 0
                                        || (userInfo =
                                                        UserManager.get(this.mContext)
                                                                .getUserInfo(this.mUserId))
                                                == null
                                        || !userInfo.isGuest())
                                && !isShopDemo
                                && !isLDUModel
                                && !SecurityUtils.isFingerprintDisabled(this.mContext, this.mUserId)
                                && !isUCMKeyguardEnabled)) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData4);
                }
                if (!this.mLockPatternUtils.isLockScreenDisabled(UserHandle.myUserId())) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData5);
                }
                if (Rune.SUPPORT_SMARTMANAGER_CN
                        && !Utils.isAfwProfile(this.mContext)
                        && !LockUtils.isInMultiWindow(getActivity())) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData6);
                }
                if (z) {
                    this.mRelativeLinkView.create(this);
                }
            }
        }
        if (this.mEditorCategory != null) {
            if (this.mLockPatternUtils.isLockScreenDisabled(this.mUserId)
                    || Utils.isMinimalBatteryUseEnabled(this.mContext)
                    || Utils.isNewDexMode(this.mContext)) {
                this.mEditorCategory.removePreference(this.mTouchAndHoldToEditPreference);
            } else {
                this.mEditorCategory.addPreference(this.mTouchAndHoldToEditPreference);
                this.mEditorCategory.mOrderingAsAdded = true;
            }
        }
        super.onResume();
        if (!LockUtils.isLockSettingsBlockonDexMode(getActivity())) {
            ((AODPreferenceController) use(AODPreferenceController.class))
                    .registerContentObserver();
        } else {
            Toast.makeText(
                            getActivity(),
                            getString(R.string.sec_lock_screen_disable_by_samsung_dex_waring_text),
                            0)
                    .show();
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Intent intent = this.mTrustAgentClickIntent;
        if (intent != null) {
            bundle.putParcelable("trust_agent_click_intent", intent);
        }
    }
}
