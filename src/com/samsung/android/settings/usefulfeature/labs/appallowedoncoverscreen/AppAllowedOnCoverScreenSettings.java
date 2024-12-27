package com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen;

import android.app.ActivityTaskManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.LoadingViewController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.languagepack.appsstub.RequestStubApi;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AppAllowedOnCoverScreenSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public SeslAppPickerView mAppPickerView;
    public Configuration mConfiguration;
    public ProgressBar mProgressBar;
    public SettingsMainSwitchBar mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            Map coverLauncherEnabledAppList;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (!Rune$$ExternalSyntheticOutline0.m(
                    "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN")) {
                return null;
            }
            String str2 = ApnSettings.MVNO_NONE;
            new HashMap();
            try {
                coverLauncherEnabledAppList =
                        ActivityTaskManager.getService()
                                .getCoverLauncherEnabledAppList(UserHandle.getCallingUserId());
                int dualAppProfileId = SemDualAppManager.getDualAppProfileId();
                if (SemDualAppManager.isDualAppId(dualAppProfileId)) {
                    coverLauncherEnabledAppList.putAll(
                            ActivityTaskManager.getService()
                                    .getCoverLauncherEnabledAppList(dualAppProfileId));
                }
            } catch (RemoteException e) {
                SemLog.e(
                        "AppsAllowedOnCoverScreenSettings",
                        "Can not retrieve enable app list : " + e.getMessage());
            }
            if (coverLauncherEnabledAppList == null) {
                return null;
            }
            str2 = coverLauncherEnabledAppList.size() + ";";
            Iterator it = coverLauncherEnabledAppList.entrySet().iterator();
            while (it.hasNext()) {
                String str3 = (String) ((Map.Entry) it.next()).getKey();
                if (str2.length() + str3.length() > 2048) {
                    break;
                }
                str2 = str2 + str3 + ";";
            }
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(68702);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LauncherAppListLoader extends AsyncTask {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class AppInfomation {
            public final int id;
            public final String packageName;

            public AppInfomation(String str, int i) {
                this.packageName = str;
                this.id = i;
            }
        }

        public LauncherAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            ArrayList arrayList;
            List coverLauncherAvailableAppList;
            synchronized (this) {
                try {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList = new ArrayList();
                    try {
                        List coverLauncherAvailableAppList2 =
                                ActivityTaskManager.getService()
                                        .getCoverLauncherAvailableAppList(
                                                UserHandle.getCallingUserId());
                        if (coverLauncherAvailableAppList2 != null
                                && coverLauncherAvailableAppList2.size() > 0) {
                            Iterator it = coverLauncherAvailableAppList2.iterator();
                            while (it.hasNext()) {
                                arrayList2.add(
                                        new AppInfomation(
                                                (String) it.next(), UserHandle.getCallingUserId()));
                            }
                            coverLauncherAvailableAppList2.clear();
                        }
                        int dualAppProfileId = SemDualAppManager.getDualAppProfileId();
                        if (SemDualAppManager.isDualAppId(dualAppProfileId)
                                && (coverLauncherAvailableAppList =
                                                ActivityTaskManager.getService()
                                                        .getCoverLauncherAvailableAppList(
                                                                dualAppProfileId))
                                        != null
                                && coverLauncherAvailableAppList.size() > 0) {
                            Iterator it2 = coverLauncherAvailableAppList.iterator();
                            while (it2.hasNext()) {
                                arrayList2.add(
                                        new AppInfomation((String) it2.next(), dualAppProfileId));
                            }
                        }
                        Iterator it3 = arrayList2.iterator();
                        while (it3.hasNext()) {
                            AppInfomation appInfomation = (AppInfomation) it3.next();
                            String str = appInfomation.packageName;
                            int i = appInfomation.id;
                            AppInfo.Companion companion = AppInfo.Companion;
                            arrayList.add(
                                    new AppData.ListSwitchAppDataBuilder(
                                                    AppInfo.Companion.obtain(
                                                            i, str, ApnSettings.MVNO_NONE))
                                            .setDimmed(
                                                    !(Settings.System.getInt(
                                                                    AppAllowedOnCoverScreenSettings
                                                                            .this
                                                                            .getContentResolver(),
                                                                    "large_cover_screen_apps",
                                                                    0)
                                                            != 0))
                                            .setSelected(
                                                    ActivityTaskManager.getService()
                                                            .isPackageSettingsEnabledForCoverLauncher(
                                                                    appInfomation.packageName,
                                                                    appInfomation.id,
                                                                    0))
                                            .build());
                        }
                    } catch (RemoteException e) {
                        SemLog.e(
                                "AppsAllowedOnCoverScreenSettings",
                                "Can not retrieve app list : " + e.getMessage());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            AppAllowedOnCoverScreenSettings.this.mProgressBar.setVisibility(8);
            AppAllowedOnCoverScreenSettings.this.mAppPickerView.submitList((List) obj);
            AppAllowedOnCoverScreenSettings.this.mAppPickerView.setVisibility(0);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            AppAllowedOnCoverScreenSettings.this.mProgressBar.setVisibility(0);
            AppAllowedOnCoverScreenSettings.this.mAppPickerView.setVisibility(8);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_labs_cover_screen_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return LabsSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68700;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    public final void initPreference$12() {
        addPreferencesFromResource(R.xml.sec_app_allowed_cover_screen);
        SeslAppPickerView seslAppPickerView =
                (SeslAppPickerView)
                        ((LayoutPreference) findPreference("app_allowed_cover_screen_apps_picker"))
                                .mRootView.findViewById(R.id.labs_apppicker_view);
        this.mAppPickerView = seslAppPickerView;
        seslAppPickerView.setNestedScrollingEnabled(false);
        this.mAppPickerView.setAppListOrder(1);
        this.mAppPickerView.mOnStateChangeListener = new AnonymousClass1();
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.sec_app_allowed_cover_screen_guide, (ViewGroup) null);
        inflate.findViewById(R.id.preview_image).semSetRoundedCorners(15);
        inflate.findViewById(R.id.preview_image)
                .semSetRoundedCornerColor(
                        15,
                        getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerView.addHeader(inflate, 15);
        final Intent intent = new Intent();
        intent.setClassName(
                "com.samsung.android.multistar",
                "com.samsung.android.coverstar.ui.apps.AppsLauncherSettingsHelper");
        intent.putExtra("from_settings", true);
        View inflate2 =
                getLayoutInflater()
                        .inflate(R.layout.sec_app_allowed_cover_screen_more_apps, (ViewGroup) null);
        inflate2.findViewById(R.id.more_apps_button)
                .setOnClickListener(
                        new View.OnClickListener() { // from class:
                            // com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AppAllowedOnCoverScreenSettings appAllowedOnCoverScreenSettings =
                                        AppAllowedOnCoverScreenSettings.this;
                                Intent intent2 = intent;
                                if (!Utils.isIntentAvailable(
                                        appAllowedOnCoverScreenSettings.getContext(), intent2)) {
                                    SemLog.d(
                                            "AppsAllowedOnCoverScreenSettings",
                                            "multi star's launcher widget isn't available.");
                                    intent2 = new Intent();
                                    intent2.setData(
                                            Uri.parse(
                                                    "samsungapps://ProductDetail/com.samsung.android.multistar/?fsOrigin=stubUpdateCheck&fsUpdateType=self"));
                                }
                                try {
                                    appAllowedOnCoverScreenSettings.startActivity(intent2);
                                } catch (ActivityNotFoundException e) {
                                    SemLog.e(
                                            "AppsAllowedOnCoverScreenSettings",
                                            "Activity NOT found.",
                                            e);
                                }
                            }
                        });
        if (Utils.isIntentAvailable(getContext(), intent)) {
            this.mAppPickerView.addFooter(inflate2);
        } else if (Utils.isNetworkAvailable(getPrefContext())) {
            this.mAppPickerView.addFooter(inflate2);
            final LoadingViewController loadingViewController =
                    new LoadingViewController(
                            inflate2.findViewById(R.id.loading_container),
                            inflate2.findViewById(R.id.more_apps_container),
                            new View(getContext()));
            loadingViewController.handleLoadingContainer(false, false, false);
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppAllowedOnCoverScreenSettings appAllowedOnCoverScreenSettings =
                                    AppAllowedOnCoverScreenSettings.this;
                            final LoadingViewController loadingViewController2 =
                                    loadingViewController;
                            if (RequestStubApi.getInstance(
                                                    appAllowedOnCoverScreenSettings.getContext())
                                            .getDownloadInfo("com.samsung.android.multistar")
                                    != null) {
                                SemLog.d(
                                        "AppsAllowedOnCoverScreenSettings",
                                        "MultiStar can be downloaded from galaxy store.");
                                final int i = 0;
                                ThreadUtils.postOnMainThread(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i2 = i;
                                                LoadingViewController loadingViewController3 =
                                                        loadingViewController2;
                                                switch (i2) {
                                                    case 0:
                                                        loadingViewController3.showContent(false);
                                                        break;
                                                    default:
                                                        loadingViewController3.showEmpty();
                                                        break;
                                                }
                                            }
                                        });
                            } else {
                                SemLog.d(
                                        "AppsAllowedOnCoverScreenSettings",
                                        "MultiStar can't be downloaded from galaxy store.");
                                final int i2 = 1;
                                ThreadUtils.postOnMainThread(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i22 = i2;
                                                LoadingViewController loadingViewController3 =
                                                        loadingViewController2;
                                                switch (i22) {
                                                    case 0:
                                                        loadingViewController3.showContent(false);
                                                        break;
                                                    default:
                                                        loadingViewController3.showEmpty();
                                                        break;
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }
        this.mAppPickerView.setItemAnimator(null);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mSwitchBar = settingsMainSwitchBar;
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.System.putInt(
                getContext().getContentResolver(), "large_cover_screen_apps", z ? 1 : 0);
        LoggingHelper.insertEventLogging(68700, 68703, z);
        ArrayList arrayList = new ArrayList();
        for (AppData appData : this.mAppPickerView.mViewDataController.appDataList) {
            if (appData instanceof AppInfoData) {
                arrayList.add(
                        new AppData.ListSwitchAppDataBuilder((AppInfoData) appData)
                                .setDimmed(!z)
                                .build());
            }
        }
        if (arrayList.size() > 0) {
            this.mAppPickerView.submitList(arrayList);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mConfiguration.orientation != configuration.orientation) {
            getPreferenceScreen().removeAll();
            getPreferenceScreen()
                    .addPreference(
                            new LayoutPreference(
                                    getContext(), R.layout.sec_front_screen_app_picker));
            initPreference$12();
            boolean z =
                    Settings.System.getInt(getContentResolver(), "large_cover_screen_apps", 0) != 0;
            SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.setChecked(z);
                this.mSwitchBar.show();
            }
            new LauncherAppListLoader()
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        }
        this.mConfiguration.updateFrom(configuration);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        configuration.updateFrom(getContext().getResources().getConfiguration());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        initPreference$12();
        ProgressBar progressBar = new ProgressBar(getContext());
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        this.mProgressBar.setScrollBarStyle(R.style.LoadingTheme);
        this.mProgressBar.setVisibility(8);
        ViewGroup viewGroup2 = (ViewGroup) onCreateView.findViewById(android.R.id.list_container);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        viewGroup2.addView(this.mProgressBar, layoutParams);
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mProgressBar.getVisibility() == 0) {
            this.mProgressBar.setVisibility(8);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean z = Settings.System.getInt(getContentResolver(), "large_cover_screen_apps", 0) != 0;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
            this.mSwitchBar.show();
        }
        if (this.mProgressBar.getVisibility() == 0) {
            this.mProgressBar.setVisibility(8);
        }
        new LauncherAppListLoader()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen.AppAllowedOnCoverScreenSettings$1, reason: invalid class name */
    public final class AnonymousClass1 implements AppPickerState$OnStateChangeListener {
        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public final void onStateChanged(AppInfo appInfo, boolean z) {
            try {
                if (z) {
                    ActivityTaskManager.getService()
                            .setCoverLauncherPackageEnabled(appInfo.packageName, appInfo.user);
                } else {
                    ActivityTaskManager.getService()
                            .setCoverLauncherPackageDisabled(appInfo.packageName, appInfo.user);
                }
            } catch (RemoteException e) {
                SemLog.e(
                        "AppsAllowedOnCoverScreenSettings",
                        "Can not set app enable/disable : " + e.getMessage());
            }
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public final void onStateAllChanged(boolean z) {}
    }
}
