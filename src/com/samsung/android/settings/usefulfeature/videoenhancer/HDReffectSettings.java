package com.samsung.android.settings.usefulfeature.videoenhancer;

import android.R;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecSwitchPreference;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecIconResizer;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HDReffectSettings extends DashboardFragment implements Preference.OnPreferenceChangeListener {
    public SecPreferenceCategory mAppListPreference;
    public AnonymousClass1 mDesktopModeListener;
    public SemDesktopModeManager mDesktopModeManager;
    public SemDisplaySolutionManager mDisplaySolutionManager;
    public Handler mHandler;
    public SecUnclickablePreference mPreviewPreference;
    public ProgressBar mProgressBar;
    public RecyclerView mRecyclerView;
    public SecVideoBrightnessHorizontalRadioPreference mSecBrightenUpVideoPreference;
    public SettingsObserver mSettingsObserver;
    public String[] mList = new String[0];
    public boolean mFragmentVisible = false;
    public List mAppList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CreatePreferenceNewTask extends AsyncTask {
        public CreatePreferenceNewTask() {
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            HDReffectSettings.this.mAppList.clear();
            if (HDReffectSettings.this.getActivity() == null) {
                return HDReffectSettings.this.mAppList;
            }
            HDReffectSettings hDReffectSettings = HDReffectSettings.this;
            String[] stringArray = hDReffectSettings.getResources().getStringArray(R.array.config_integrityRuleProviderPackages);
            String[] stringArray2 = hDReffectSettings.getResources().getStringArray(R.array.config_ephemeralResolverPackage);
            ArrayList arrayList = new ArrayList();
            String[] strArr = new String[stringArray.length + stringArray2.length];
            hDReffectSettings.mList = strArr;
            System.arraycopy(stringArray2, 0, strArr, 0, stringArray2.length);
            System.arraycopy(stringArray, 0, hDReffectSettings.mList, stringArray2.length, stringArray.length);
            PackageManager packageManager = hDReffectSettings.getPackageManager();
            SecIconResizer secIconResizer = new SecIconResizer(hDReffectSettings.getContext());
            int dimension = (int) secIconResizer.mContext.getResources().getDimension(com.android.settings.R.dimen.sec_widget_list_app_icon_size);
            secIconResizer.mIconHeight = dimension;
            secIconResizer.mIconWidth = dimension;
            int i = 0;
            int i2 = 0;
            while (true) {
                String[] strArr2 = hDReffectSettings.mList;
                if (i >= strArr2.length) {
                    hDReffectSettings.mAppList = arrayList;
                    return HDReffectSettings.this.mAppList;
                }
                String str = strArr2[i];
                int videoEnhancerSettingState = hDReffectSettings.mDisplaySolutionManager.getVideoEnhancerSettingState(str);
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
                    if (applicationInfo != null) {
                        Drawable loadIcon = applicationInfo.loadIcon(packageManager, true, 1);
                        String charSequence = applicationInfo.loadLabel(packageManager).toString();
                        if (Utils.isRTL(hDReffectSettings.getActivity())) {
                            charSequence = BidiFormatter.getInstance().unicodeWrap(charSequence, TextDirectionHeuristics.RTL);
                        }
                        if (loadIcon == null) {
                            loadIcon = packageManager.semGetDrawableForIconTray(packageManager.getDefaultActivityIcon(), 1);
                        }
                        i2++;
                        SecSwitchPreference secSwitchPreference = new SecSwitchPreference(hDReffectSettings.getContext());
                        secSwitchPreference.setKey(str);
                        secSwitchPreference.setOrder((i2 * 10) + hDReffectSettings.mAppListPreference.getOrder());
                        secSwitchPreference.setTitle(charSequence);
                        secSwitchPreference.setPersistent();
                        if (loadIcon != null) {
                            secSwitchPreference.setIcon(secIconResizer.createIconThumbnail(loadIcon));
                        }
                        secSwitchPreference.setSummary(ApnSettings.MVNO_NONE);
                        secSwitchPreference.setChecked(videoEnhancerSettingState == 1);
                        secSwitchPreference.setOnPreferenceChangeListener(hDReffectSettings);
                        secSwitchPreference.setEnabled(true);
                        arrayList.add(secSwitchPreference);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
                i++;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            boolean z;
            List list = (List) obj;
            HDReffectSettings hDReffectSettings = HDReffectSettings.this;
            synchronized (hDReffectSettings) {
                z = hDReffectSettings.mFragmentVisible;
            }
            if (z) {
                HDReffectSettings.this.setAppListUp(list);
                if (HDReffectSettings.this.mProgressBar.getVisibility() == 0) {
                    HDReffectSettings.this.mProgressBar.setVisibility(8);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BRIGHTEN_UP_VIDEO;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.BRIGHTEN_UP_VIDEO = Settings.System.getUriFor("hdr_effect");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            HDReffectSettings.this.loadAppsList(Settings.System.getInt(HDReffectSettings.this.getContentResolver(), "hdr_effect", 0) == 1);
        }

        public final void setListening(boolean z) {
            if (z) {
                HDReffectSettings.this.getContext().getContentResolver().registerContentObserver(this.BRIGHTEN_UP_VIDEO, false, this);
            } else {
                HDReffectSettings.this.getContext().getContentResolver().unregisterContentObserver(this);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "HDReffectSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4376;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.sec_hdreffect_settings;
    }

    public final void loadAppsList(boolean z) {
        if (!z) {
            this.mPreviewPreference.setTitle(com.android.settings.R.string.hdr_setting_text_1);
            SecPreferenceCategory secPreferenceCategory = this.mAppListPreference;
            if (secPreferenceCategory != null) {
                secPreferenceCategory.setVisible(false);
                return;
            }
            return;
        }
        this.mPreviewPreference.setTitle(com.android.settings.R.string.hdr_setting_text_2);
        if (!this.mAppList.isEmpty()) {
            setAppListUp(this.mAppList);
            return;
        }
        this.mProgressBar.setVisibility(0);
        new FrameLayout.LayoutParams(-1, -2).gravity = 17;
        new CreatePreferenceNewTask().execute(new Object[0]);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (UsefulfeatureUtils.isVideoBrightnessMenuDisabled(getContext())) {
            this.mAppListPreference.setEnabled(false);
            this.mPreviewPreference.setEnabled(false);
            this.mSecBrightenUpVideoPreference.setEnabled(false);
        } else {
            this.mAppListPreference.setEnabled(true);
            this.mPreviewPreference.setEnabled(true);
            this.mSecBrightenUpVideoPreference.setEnabled(true);
        }
        setImageMinHeight();
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDisplaySolutionManager = (SemDisplaySolutionManager) getContext().getSystemService("DisplaySolution");
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        this.mAppListPreference = (SecPreferenceCategory) findPreference("hdr_effect_app_list_preference");
        this.mPreviewPreference = (SecUnclickablePreference) findPreference("sec_hdr_effect_preview");
        this.mSecBrightenUpVideoPreference = (SecVideoBrightnessHorizontalRadioPreference) findPreference("sec_brighten_up_video");
        setImageMinHeight();
        SecPreferenceCategory secPreferenceCategory = this.mAppListPreference;
        if (secPreferenceCategory != null) {
            secPreferenceCategory.removeAll();
            this.mAppListPreference.setVisible(false);
        }
        this.mAppList.clear();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mRecyclerView = getListView();
        setDivider(null);
        this.mRecyclerView.addItemDecoration(new SecDividerItemDecorator((int) (getResources().getDimension(com.android.settings.R.dimen.sec_app_list_item_icon_min_width) + getResources().getDimension(com.android.settings.R.dimen.sec_widget_list_item_padding_start)), getContext(), getResources().getDrawable(com.android.settings.R.drawable.sec_top_level_list_divider)));
        ViewGroup viewGroup2 = (ViewGroup) onCreateView.findViewById(R.id.list_container);
        ProgressBar progressBar = new ProgressBar(getContext());
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        this.mProgressBar.setScrollBarStyle(com.android.settings.R.style.LoadingTheme);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        viewGroup2.addView(this.mProgressBar, layoutParams);
        this.mProgressBar.setVisibility(8);
        return onCreateView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        synchronized (this) {
            this.mFragmentVisible = false;
        }
        if (this.mProgressBar.getVisibility() == 0) {
            this.mProgressBar.setVisibility(8);
        }
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof SecSwitchPreference)) {
            return false;
        }
        this.mDisplaySolutionManager.setVideoEnhancerSettingState(preference.getKey(), ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [com.samsung.android.desktopmode.SemDesktopModeManager$DesktopModeListener, com.samsung.android.settings.usefulfeature.videoenhancer.HDReffectSettings$1] */
    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (Rune.supportDesktopMode() && Rune.isSamsungDexMode(getActivity()) && !Utils.isNewDexMode(getContext())) {
            getActivity().finish();
        }
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) getActivity().getSystemService("desktopmode");
        this.mDesktopModeManager = semDesktopModeManager;
        if (semDesktopModeManager != null) {
            ?? r1 = new SemDesktopModeManager.DesktopModeListener() { // from class: com.samsung.android.settings.usefulfeature.videoenhancer.HDReffectSettings.1
                public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    if (semDesktopModeState.enabled != 4 || Utils.isNewDexMode(HDReffectSettings.this.getContext())) {
                        return;
                    }
                    HDReffectSettings.this.getActivity().runOnUiThread(new Runnable() { // from class: com.samsung.android.settings.usefulfeature.videoenhancer.HDReffectSettings.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.i("HDReffectSettings", "DesktopMode Enabled, Finish Video Enhancer settings");
                            HDReffectSettings.this.getActivity().finish();
                        }
                    });
                }
            };
            this.mDesktopModeListener = r1;
            semDesktopModeManager.registerListener((SemDesktopModeManager.DesktopModeListener) r1);
        }
        synchronized (this) {
            this.mFragmentVisible = true;
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(true);
        }
        loadAppsList(Settings.System.getInt(getContentResolver(), "hdr_effect", 0) == 1);
        if (UsefulfeatureUtils.isVideoBrightnessMenuDisabled(getContext())) {
            this.mAppListPreference.setEnabled(false);
            this.mPreviewPreference.setEnabled(false);
            this.mSecBrightenUpVideoPreference.setEnabled(false);
        } else {
            this.mAppListPreference.setEnabled(true);
            this.mPreviewPreference.setEnabled(true);
            this.mSecBrightenUpVideoPreference.setEnabled(true);
        }
        setImageMinHeight();
    }

    public final void setAppListUp(List list) {
        if (list != null) {
            this.mAppListPreference.setVisible(true);
            int size = list.size();
            if (size > 0) {
                this.mAppListPreference.removeAll();
            }
            for (int i = 0; i < size; i++) {
                this.mAppListPreference.addPreference((Preference) list.get(i));
                if (i == size - 1) {
                    ((SecSwitchPreference) list.get(i)).seslSetRoundedBg(12);
                }
            }
        }
    }

    public final void setImageMinHeight() {
        int dimension = (int) getContext().getResources().getDimension(com.android.settings.R.dimen.sec_widget_Video_Brightness_horizontal_radio_preference_icon_min_height);
        SecVideoBrightnessHorizontalRadioPreference secVideoBrightnessHorizontalRadioPreference = this.mSecBrightenUpVideoPreference;
        if (secVideoBrightnessHorizontalRadioPreference != null) {
            if (dimension < 0) {
                secVideoBrightnessHorizontalRadioPreference.getClass();
            } else {
                secVideoBrightnessHorizontalRadioPreference.mMinHeight = dimension;
                secVideoBrightnessHorizontalRadioPreference.notifyChanged();
            }
        }
    }
}
