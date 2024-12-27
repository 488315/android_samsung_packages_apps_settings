package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.secutil.Log;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BadgeAppIconSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, Preference.OnPreferenceChangeListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public RadioPreference mAppIconDot;
    public RadioPreference mAppIconNumber;
    public FragmentActivity mContext;
    public SecSwitchPreference mHomescreenNotiPreivew;
    public SettingsMainSwitchBar mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.BadgeAppIconSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            hashMap.put("homescreen_noti_preview", GtsGroup.GROUP_KEY_NOTIFICATIONS.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (context.getPackageManager()
                    .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite")) {
                ((ArrayList) nonIndexableKeys).add("homescreen_noti_preview");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = BadgeAppIconSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_badge_app_icon_settings;
            arrayList.add(searchIndexableResource);
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.BadgeAppIconSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        /* JADX WARN: Removed duplicated region for block: B:12:0x0063  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0066  */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getStatusLoggingData(android.content.Context r4) {
            /*
                r3 = this;
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                int r0 = com.samsung.android.settings.notification.NotiUtils.getBadgeAppIconType(r4)
                android.content.ContentResolver r4 = r4.getContentResolver()
                java.lang.String r1 = "home_show_notification_enabled"
                r2 = 0
                int r4 = android.provider.Settings.Secure.getInt(r4, r1, r2)
                r1 = 1
                if (r4 != r1) goto L18
                r2 = r1
            L18:
                r4 = 36408(0x8e38, float:5.1018E-41)
                if (r0 == 0) goto L46
                if (r0 == r1) goto L36
                r1 = 2
                if (r0 == r1) goto L24
                r4 = 0
                goto L57
            L24:
                java.lang.String r4 = java.lang.String.valueOf(r4)
                com.samsung.android.settings.logging.status.StatusData r0 = new com.samsung.android.settings.logging.status.StatusData
                r0.<init>()
                java.lang.String r1 = "off"
                r0.mStatusValue = r1
                r0.mStatusKey = r4
            L34:
                r4 = r0
                goto L57
            L36:
                java.lang.String r4 = java.lang.String.valueOf(r4)
                com.samsung.android.settings.logging.status.StatusData r0 = new com.samsung.android.settings.logging.status.StatusData
                r0.<init>()
                java.lang.String r1 = "dotON"
                r0.mStatusValue = r1
                r0.mStatusKey = r4
                goto L34
            L46:
                java.lang.String r4 = java.lang.String.valueOf(r4)
                com.samsung.android.settings.logging.status.StatusData r0 = new com.samsung.android.settings.logging.status.StatusData
                r0.<init>()
                java.lang.String r1 = "numberOn"
                r0.mStatusValue = r1
                r0.mStatusKey = r4
                goto L34
            L57:
                r3.add(r4)
                r4 = 36409(0x8e39, float:5.102E-41)
                java.lang.String r4 = java.lang.String.valueOf(r4)
                if (r2 == 0) goto L66
                java.lang.String r0 = "1"
                goto L68
            L66:
                java.lang.String r0 = "0"
            L68:
                com.samsung.android.settings.logging.status.StatusData r1 = new com.samsung.android.settings.logging.status.StatusData
                r1.<init>()
                r1.mStatusValue = r0
                r1.mStatusKey = r4
                r3.add(r1)
                return r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.notification.BadgeAppIconSettings.AnonymousClass2.getStatusLoggingData(android.content.Context):java.util.List");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 36045;
    }

    public final void initUI$8() {
        addPreferencesFromResource(R.xml.sec_badge_app_icon_settings);
        ((SecPreferenceCategory) findPreference("no_stroke")).setEmptyHeight();
        RadioPreference radioPreference = (RadioPreference) findPreference("app_icon_number");
        this.mAppIconNumber = radioPreference;
        radioPreference.setOnPreferenceChangeListener(this);
        RadioPreference radioPreference2 = (RadioPreference) findPreference("app_icon_dot");
        this.mAppIconDot = radioPreference2;
        radioPreference2.setOnPreferenceChangeListener(this);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("homescreen_noti_preview");
        this.mHomescreenNotiPreivew = secSwitchPreference;
        secSwitchPreference.setOnPreferenceChangeListener(this);
        Bitmap[] bitmapArr = new Bitmap[2];
        if (this.mContext
                        .getPackageManager()
                        .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite")
                || !isSupportHomescreenNotiPreivew()) {
            removePreference("notification_homescreen_category");
            removePreference("homescreen_noti_preview");
        }
    }

    public final boolean isSupportHomescreenNotiPreivew() {
        Bundle bundle;
        if (Rune.isSamsungDexMode(this.mContext)) {
            return false;
        }
        if (SemPersonaManager.isSecureFolderId(UserHandle.getCallingUserId())) {
            try {
                ApplicationInfo applicationInfo =
                        getActivity()
                                .getPackageManager()
                                .getApplicationInfo("com.samsung.knox.securefolder", 128);
                if (applicationInfo == null || (bundle = applicationInfo.metaData) == null) {
                    return false;
                }
                if (!bundle.getBoolean("knox_folder_noti_preview")) {
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        super.onActivityCreated(bundle);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(), "notification_badging", z ? 1 : 0, -2);
        Log.secD("NotiUtils", "setEnableBadgeAppIcon : " + z);
        this.mAppIconNumber.setEnabled(z);
        this.mAppIconDot.setEnabled(z);
        if (isSupportHomescreenNotiPreivew()) {
            this.mHomescreenNotiPreivew.setEnabled(z);
        }
        if (z) {
            return;
        }
        LoggingHelper.insertEventLogging(36045, 36408, "off");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setPreferenceScreen(null);
        initUI$8();
        updateUI$6();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        initUI$8();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.hide();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        if ("homescreen_noti_preview".equals(key)) {
            Settings.Secure.putInt(
                    getContentResolver(), "home_show_notification_enabled", booleanValue ? 1 : 0);
            LoggingHelper.insertEventLogging(36045, 36409, booleanValue ? 1L : 0L);
        } else {
            if ("app_icon_number".equals(key)) {
                if (!booleanValue) {
                    return false;
                }
                updateBadgeTypePref(0, true);
                LoggingHelper.insertEventLogging(36045, 36408, "numberOn");
                return true;
            }
            if ("app_icon_dot".equals(key)) {
                bool.getClass();
                if (!booleanValue) {
                    return false;
                }
                updateBadgeTypePref(1, true);
                LoggingHelper.insertEventLogging(36045, 36408, "dotON");
            }
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        updateUI$6();
    }

    public final void updateBadgeTypePref(int i, boolean z) {
        if (i == 0) {
            this.mAppIconDot.setChecked(false);
            if (z) {
                NotiUtils.setBadgeAppIconType(this.mContext, i);
                return;
            } else {
                this.mAppIconNumber.setChecked(true);
                return;
            }
        }
        this.mAppIconNumber.setChecked(false);
        if (z) {
            NotiUtils.setBadgeAppIconType(this.mContext, i);
        } else {
            this.mAppIconDot.setChecked(true);
        }
    }

    public final void updateUI$6() {
        boolean z =
                Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(), "notification_badging", 1, -2)
                        == 1;
        Log.secD("NotiUtils", "isEnabledBadgeAppIcon : " + z);
        this.mSwitchBar.setChecked(z);
        this.mAppIconNumber.setEnabled(z);
        this.mAppIconDot.setEnabled(z);
        if (isSupportHomescreenNotiPreivew()) {
            this.mHomescreenNotiPreivew.setEnabled(z);
        }
        updateBadgeTypePref(NotiUtils.getBadgeAppIconType(this.mContext), false);
        this.mHomescreenNotiPreivew.setChecked(
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "home_show_notification_enabled",
                                0)
                        == 1);
        if (isSupportHomescreenNotiPreivew()) {
            return;
        }
        this.mHomescreenNotiPreivew.setEnabled(false);
    }
}
