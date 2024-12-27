package com.android.settings.deletionhelper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.Utils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutomaticStorageManagerSettings extends DashboardFragment
        implements Preference.OnPreferenceChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public DropDownPreference mDaysToRetain;
    public SettingsMainSwitchBar mSwitchBar;
    public AutomaticStorageManagerSwitchBarController mSwitchController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deletionhelper.AutomaticStorageManagerSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    AutomaticStorageManagerSettings.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new AutomaticStorageManagerDescriptionPreferenceController(context));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return false;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AutomaticStorageManagerDescriptionPreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 458;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.automatic_storage_management_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        DropDownPreference dropDownPreference = (DropDownPreference) findPreference("days");
        this.mDaysToRetain = dropDownPreference;
        dropDownPreference.setOnPreferenceChangeListener(this);
        ContentResolver contentResolver = getContentResolver();
        try {
            i = getResources().getInteger(android.R.integer.device_idle_light_max_idle_to_ms);
        } catch (Resources.NotFoundException unused) {
            i = 90;
        }
        int i2 =
                Settings.Secure.getInt(
                        contentResolver, "automatic_storage_manager_days_to_retain", i);
        String[] stringArray =
                getResources().getStringArray(R.array.automatic_storage_management_days_values);
        DropDownPreference dropDownPreference2 = this.mDaysToRetain;
        int i3 = 0;
        while (true) {
            if (i3 >= stringArray.length) {
                i3 = stringArray.length - 1;
                break;
            }
            if (i2 == Integer.parseInt(stringArray[i3])) {
                break;
            }
            i3++;
        }
        dropDownPreference2.setValue(stringArray[i3]);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.setTitle(
                getContext().getString(R.string.automatic_storage_manager_primary_switch_title));
        this.mSwitchBar.show();
        Context context = getContext();
        SettingsMainSwitchBar settingsMainSwitchBar2 = this.mSwitchBar;
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        DropDownPreference dropDownPreference3 = this.mDaysToRetain;
        FragmentManager fragmentManager = getFragmentManager();
        AutomaticStorageManagerSwitchBarController automaticStorageManagerSwitchBarController =
                new AutomaticStorageManagerSwitchBarController();
        Context context2 = (Context) Preconditions.checkNotNull(context);
        automaticStorageManagerSwitchBarController.mContext = context2;
        SettingsMainSwitchBar settingsMainSwitchBar3 =
                (SettingsMainSwitchBar) Preconditions.checkNotNull(settingsMainSwitchBar2);
        automaticStorageManagerSwitchBarController.mSwitchBar = settingsMainSwitchBar3;
        automaticStorageManagerSwitchBarController.mMetrics =
                (MetricsFeatureProvider) Preconditions.checkNotNull(metricsFeatureProvider);
        automaticStorageManagerSwitchBarController.mDaysToRetainPreference =
                (Preference) Preconditions.checkNotNull(dropDownPreference3);
        automaticStorageManagerSwitchBarController.mFragmentManager =
                (FragmentManager) Preconditions.checkNotNull(fragmentManager);
        settingsMainSwitchBar3.setChecked(Utils.isStorageManagerEnabled(context2));
        settingsMainSwitchBar3.addOnSwitchChangeListener(
                automaticStorageManagerSwitchBarController);
        this.mSwitchController = automaticStorageManagerSwitchBarController;
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.hide();
        AutomaticStorageManagerSwitchBarController automaticStorageManagerSwitchBarController =
                this.mSwitchController;
        automaticStorageManagerSwitchBarController.mSwitchBar.removeOnSwitchChangeListener(
                automaticStorageManagerSwitchBarController);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!"days".equals(preference.getKey())) {
            return true;
        }
        Settings.Secure.putInt(
                getContentResolver(),
                "automatic_storage_manager_days_to_retain",
                Integer.parseInt((String) obj));
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mDaysToRetain.setEnabled(Utils.isStorageManagerEnabled(getContext()));
    }
}
