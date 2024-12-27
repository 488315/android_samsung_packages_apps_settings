package com.android.settings.applications.appinfo;

import android.app.LocaleManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.internal.app.LocaleHelper;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.AppLocaleUtil;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.io.File;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppLocaleDetails extends SettingsPreferenceFragment {
    public ApplicationInfo mApplicationInfo;
    public boolean mCreated = false;
    public String mPackageName;
    public LayoutPreference mPrefOfDescription;
    public Preference mPrefOfDisclaimer;

    public static CharSequence getSummary(Context context, ApplicationInfo applicationInfo) {
        LocaleList applicationLocales;
        Context createContextAsUser =
                context.createContextAsUser(UserHandle.getUserHandleForUid(applicationInfo.uid), 0);
        String str = applicationInfo.packageName;
        LocaleManager localeManager =
                (LocaleManager) createContextAsUser.getSystemService(LocaleManager.class);
        Locale locale = null;
        if (localeManager == null) {
            applicationLocales = null;
        } else {
            try {
                applicationLocales = localeManager.getApplicationLocales(str);
            } catch (IllegalArgumentException e) {
                Log.w("AppLocaleDetails", "package name : " + str + " is not correct. " + e);
            }
        }
        if (applicationLocales != null) {
            locale = applicationLocales.get(0);
        }
        return locale == null
                ? context.getString(R.string.preference_of_system_locale_summary)
                : LocaleHelper.getDisplayName(
                        new Locale.Builder()
                                .setLocale(locale.stripExtensions())
                                .setUnicodeLocaleKeyword("nu", locale.getUnicodeLocaleType("nu"))
                                .build(),
                        locale,
                        true);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8133;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mCreated) {
            Log.w("AppLocaleDetails", "onActivityCreated: ignoring duplicate call");
            return;
        }
        this.mCreated = true;
        if (this.mPackageName == null) {
            return;
        }
        ApplicationsState.AppEntry appEntry =
                new ApplicationsState.AppEntry(getContext(), this.mApplicationInfo, 0L);
        appEntry.apkFile = new File(this.mApplicationInfo.sourceDir);
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(getActivity(), this, null);
        entityHeaderController.setIcon(AppUtils.getIconWithoutCache(getContext(), appEntry));
        entityHeaderController.mLabel =
                this.mApplicationInfo.loadLabel(getContext().getPackageManager());
        entityHeaderController.mIsInstantApp = AppUtils.isInstant(this.mApplicationInfo);
        entityHeaderController.mPackageName = this.mPackageName;
        entityHeaderController.mUid = this.mApplicationInfo.uid;
        entityHeaderController.mHasAppInfoLink = true;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        entityHeaderController.mPrefOrder = 10;
        getPreferenceScreen().addPreference(entityHeaderController.done(getPrefContext()));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        ApplicationInfo applicationInfo;
        super.onCreate(bundle);
        setAutoAddFooterPreference(false);
        Bundle arguments = getArguments();
        String string = arguments.getString("package", ApnSettings.MVNO_NONE);
        this.mPackageName = string;
        if (string.isEmpty()) {
            Log.d("AppLocaleDetails", "There is no package name.");
            finish();
        }
        int i =
                arguments.getInt(
                        NetworkAnalyticsConstants.DataPoints.UID, getContext().getUserId());
        addPreferencesFromResource(R.xml.app_locale_details);
        this.mPrefOfDescription =
                (LayoutPreference) getPreferenceScreen().findPreference("app_locale_description");
        this.mPrefOfDisclaimer = getPreferenceScreen().findPreference("app_locale_disclaimer");
        String str = this.mPackageName;
        try {
            applicationInfo = getContext().getPackageManager().getApplicationInfoAsUser(str, 0, i);
        } catch (PackageManager.NameNotFoundException unused) {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "Application info not found for: ", str, "AppLocaleDetails");
            applicationInfo = null;
        }
        this.mApplicationInfo = applicationInfo;
        this.mPrefOfDisclaimer.setVisible(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.mPackageName.isEmpty()
                ? layoutInflater.inflate(
                        R.layout.manage_applications_apps_unsupported, (ViewGroup) null)
                : super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        LocaleList packageLocales =
                AppLocaleUtil.getPackageLocales(getContext(), this.mPackageName);
        int i =
                ((packageLocales == null || !packageLocales.isEmpty())
                                && !(packageLocales == null
                                        && AppLocaleUtil.getAssetLocales(
                                                                getContext(), this.mPackageName)
                                                        .length
                                                == 0))
                        ? -1
                        : R.string.desc_no_available_supported_locale;
        if (i == -1) {
            this.mPrefOfDescription.setVisible(false);
        } else {
            this.mPrefOfDescription.setVisible(true);
            ((TextView) this.mPrefOfDescription.mRootView.findViewById(R.id.description))
                    .setText(getContext().getString(i));
        }
    }
}
