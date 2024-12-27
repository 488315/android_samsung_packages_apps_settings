package com.samsung.android.settings.wifi.advanced.controlhistory;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.samsung.android.settings.wifi.intelligent.TurnOnWifiAutomaticallySettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiControlHistory extends SettingsPreferenceFragment
        implements Preference.OnPreferenceClickListener {
    public WifiControlPackageInfo mInfo;
    public PreferenceScreen mPreferenceGroup;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XLSB;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.getInt("wifi_tips_notification", 0) == 1) {
            SALogging.insertSALog("WIFI_250", "1253");
        }
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.sec_wifi_control_history);
        this.mPreferenceGroup = getPreferenceScreen();
        Context context = getContext();
        WifiControlPackageInfo wifiControlPackageInfo = new WifiControlPackageInfo();
        wifiControlPackageInfo.mContext = context;
        wifiControlPackageInfo.mResolver = context.getContentResolver();
        this.mInfo = wifiControlPackageInfo;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        String key = getPreferenceScreen().findPreference(preference.getKey()).getKey();
        if (!TextUtils.isEmpty(key) && "samsung.wifi.autowifi".equals(key)) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
            subSettingLauncher.setTitleRes(R.string.wifi_autowifi_title, null);
            String name = TurnOnWifiAutomaticallySettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = FileType.XLSB;
            subSettingLauncher.launch();
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x005c, code lost:

       if (r7 != null) goto L7;
    */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 480
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.advanced.controlhistory.WifiControlHistory.onResume():void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                getContext(),
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                R.id.icon_frame,
                                android.R.id.icon));
        getListView().setImportantForAccessibility(2);
    }
}
