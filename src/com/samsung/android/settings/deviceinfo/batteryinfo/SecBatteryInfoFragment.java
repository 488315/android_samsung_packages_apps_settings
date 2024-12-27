package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.SearchIndexableResource;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.Rune;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBatteryInfoFragment extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public FragmentActivity mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.batteryinfo.SecBatteryInfoFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = SecBatteryInfoFragment.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_battery_info_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecBatteryInfoFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4810;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_battery_info_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if ((Rune.FEATURE_BATTERY_INFO_REGULATORY
                        && ((BatteryManager) this.mContext.getSystemService(BatteryManager.class))
                                .semGetValueAsBoolean(106))
                || "SM-A236B".equalsIgnoreCase(SystemProperties.get("ro.product.model"))) {
            MenuItem add = menu.add(0, 1, 0, R.string.notification_menu_info);
            try {
                add.setShowAsAction(2);
                add.setIcon(R.drawable.sec_notification_menu_info_icon);
            } catch (NullPointerException unused) {
                Log.e("SecBatteryInfoFragment", "MenuItem or Drawable is null");
            }
        }
        Log.d(
                "SecBatteryInfoFragment",
                "SEC_FLOATING_FEATURE_BATTERY_SUPPORT_BSOH_SETTINGS = "
                        + Rune.FEATURE_BATTERY_INFO_REGULATORY
                        + "\nDevice has Battery IC Chip = "
                        + ((BatteryManager) this.mContext.getSystemService(BatteryManager.class))
                                .semGetValueAsBoolean(106)
                        + "\nDevice has Two Not detachable Batteries = "
                        + new File("/sys/class/power_supply/sec_auth_2nd/qr_code").exists()
                        + "\nDevice has Two Detachable Batteries = "
                        + new File("/sys/class/power_supply/sbp-fg-2/qr_code").exists());
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return super.onOptionsItemSelected(menuItem);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getPrefContext());
        builder.setTitle(R.string.device_info_battery_title);
        builder.setNeutralButton(R.string.common_ok, null);
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.sec_battery_info_option_menu_dialog, (ViewGroup) null);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("SM-A236B".equalsIgnoreCase(SystemProperties.get("ro.product.model"))) {
            ((LinearLayout) inflate.findViewById(R.id.first_date_container)).setVisibility(8);
        }
        builder.setView(inflate);
        builder.create().show();
        return true;
    }
}
