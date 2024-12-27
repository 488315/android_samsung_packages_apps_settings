package com.android.settings.applications.specialaccess.pictureinpicture;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.IconDrawableFactory;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.EmptyTextSettings;
import com.android.settings.widget.HighlightablePreferenceGroupAdapter;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.AppSwitchPreference;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PictureInPictureSettings extends EmptyTextSettings {
    static final List<String> IGNORE_PACKAGE_LIST;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public FragmentActivity mContext;
    public IconDrawableFactory mIconDrawableFactory;
    public PackageManager mPackageManager;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppComparator implements Comparator {
        public final Collator mCollator = Collator.getInstance();
        public final PackageManager mPm;

        public AppComparator(PackageManager packageManager) {
            this.mPm = packageManager;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Pair pair = (Pair) obj;
            Pair pair2 = (Pair) obj2;
            CharSequence loadLabel = ((ApplicationInfo) pair.first).loadLabel(this.mPm);
            if (loadLabel == null) {
                loadLabel = ((ApplicationInfo) pair.first).name;
            }
            CharSequence loadLabel2 = ((ApplicationInfo) pair2.first).loadLabel(this.mPm);
            if (loadLabel2 == null) {
                loadLabel2 = ((ApplicationInfo) pair2.first).name;
            }
            int compare = this.mCollator.compare(loadLabel.toString(), loadLabel2.toString());
            return compare != 0
                    ? compare
                    : ((Integer) pair.second).intValue() - ((Integer) pair2.second).intValue();
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        IGNORE_PACKAGE_LIST = arrayList;
        arrayList.add("com.android.systemui");
        SEARCH_INDEX_DATA_PROVIDER = new BaseSearchIndexProvider(R.xml.picture_in_picture_settings);
    }

    public static boolean checkPackageHasPictureInPictureActivities(
            String str, ActivityInfo[] activityInfoArr) {
        if (!IGNORE_PACKAGE_LIST.contains(str) && activityInfoArr != null) {
            for (int length = activityInfoArr.length - 1; length >= 0; length--) {
                if (activityInfoArr[length].supportsPictureInPicture()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 812;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.picture_in_picture_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mPackageManager = activity.getPackageManager();
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        this.mIconDrawableFactory = IconDrawableFactory.newInstance(this.mContext);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        Uri data = getIntent().getData();
        HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter =
                new HighlightablePreferenceGroupAdapter(
                        preferenceScreen,
                        (data == null || data.getSchemeSpecificPart() == null)
                                ? null
                                : data.getSchemeSpecificPart(),
                        true);
        this.mAdapter = highlightablePreferenceGroupAdapter;
        return highlightablePreferenceGroupAdapter;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        int myUserId = UserHandle.myUserId();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = this.mUserManager.getProfiles(myUserId).iterator();
        while (it.hasNext()) {
            arrayList2.add(Integer.valueOf(((UserInfo) it.next()).id));
        }
        if (SemPersonaManager.isSecureFolderId(myUserId)) {
            for (PackageInfo packageInfo :
                    this.mPackageManager.getInstalledPackagesAsUser(1, myUserId)) {
                if (checkPackageHasPictureInPictureActivities(
                        packageInfo.packageName, packageInfo.activities)) {
                    arrayList.add(new Pair(packageInfo.applicationInfo, Integer.valueOf(myUserId)));
                }
            }
        } else {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                Integer num = (Integer) it2.next();
                for (PackageInfo packageInfo2 :
                        this.mPackageManager.getInstalledPackagesAsUser(1, num.intValue())) {
                    if (checkPackageHasPictureInPictureActivities(
                            packageInfo2.packageName, packageInfo2.activities)) {
                        arrayList.add(new Pair(packageInfo2.applicationInfo, num));
                    }
                }
            }
        }
        Collections.sort(arrayList, new AppComparator(this.mPackageManager));
        final Context prefContext = getPrefContext();
        LayoutPreference layoutPreference =
                new LayoutPreference(prefContext, R.layout.sec_manage_applications_desc);
        ((TextView) layoutPreference.mRootView.findViewById(R.id.function_desc))
                .setText(R.string.picture_in_picture_app_detail_summary);
        preferenceScreen.addPreference(layoutPreference);
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(prefContext);
        secInsetCategoryPreference.setHeight(0);
        preferenceScreen.addPreference(secInsetCategoryPreference);
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            Pair pair = (Pair) it3.next();
            final ApplicationInfo applicationInfo = (ApplicationInfo) pair.first;
            int intValue = ((Integer) pair.second).intValue();
            UserHandle of = UserHandle.of(intValue);
            final String str = applicationInfo.packageName;
            CharSequence loadLabel = applicationInfo.loadLabel(this.mPackageManager);
            AppSwitchPreference appSwitchPreference = new AppSwitchPreference(prefContext);
            appSwitchPreference.setIcon(
                    this.mIconDrawableFactory.getBadgedIcon(applicationInfo, intValue));
            appSwitchPreference.setTitle(this.mPackageManager.getUserBadgedLabel(loadLabel, of));
            appSwitchPreference.setPersistent();
            appSwitchPreference.setKey(str);
            appSwitchPreference.setChecked(
                    PictureInPictureDetails.getEnterPipStateForPackage(
                            prefContext, applicationInfo.uid, str));
            appSwitchPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.android.settings.applications.specialaccess.pictureinpicture.PictureInPictureSettings$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            Context context = prefContext;
                            ApplicationInfo applicationInfo2 = applicationInfo;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    PictureInPictureSettings.SEARCH_INDEX_DATA_PROVIDER;
                            PictureInPictureSettings pictureInPictureSettings =
                                    PictureInPictureSettings.this;
                            pictureInPictureSettings.getClass();
                            Boolean bool = (Boolean) obj;
                            int i = bool.booleanValue() ? 813 : 814;
                            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                            if (featureFactoryImpl == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            SettingsMetricsFeatureProvider metricsFeatureProvider =
                                    featureFactoryImpl.getMetricsFeatureProvider();
                            FragmentActivity activity = pictureInPictureSettings.getActivity();
                            metricsFeatureProvider.getClass();
                            int attribution = MetricsFeatureProvider.getAttribution(activity);
                            String str2 = str;
                            metricsFeatureProvider.action(attribution, i, 812, 0, str2);
                            ((AppOpsManager) context.getSystemService(AppOpsManager.class))
                                    .setMode(
                                            67,
                                            applicationInfo2.uid,
                                            str2,
                                            bool.booleanValue() ? 0 : 2);
                            boolean booleanValue = bool.booleanValue();
                            HashMap hashMap = new HashMap();
                            hashMap.put("pkgname", str2);
                            hashMap.put("newValue", booleanValue ? "On" : "Off");
                            SALogging.insertSALog(
                                    Integer.toString(812), Integer.toString(3918), hashMap, 0);
                            return true;
                        }
                    });
            preferenceScreen.addPreference(appSwitchPreference);
        }
        highlightPreferenceIfNeeded(true);
    }

    @Override // com.android.settings.widget.EmptyTextSettings,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setEmptyText(R.string.picture_in_picture_empty_text);
    }
}
