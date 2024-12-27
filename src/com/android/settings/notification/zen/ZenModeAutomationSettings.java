package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.utils.ManagedServiceSettings;
import com.android.settings.utils.ZenServiceListing;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeAutomationSettings extends ZenModeSettingsBase
        implements ZenRulePreference.OnPreferenceLongClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.zen_mode_automation_settings);
    public final ManagedServiceSettings.Config CONFIG =
            new ManagedServiceSettings.Config(
                    "ZenModeSettings",
                    null,
                    "android.service.notification.ConditionProviderService",
                    "android.app.action.AUTOMATIC_ZEN_RULE",
                    "android.permission.BIND_CONDITION_PROVIDER_SERVICE",
                    "condition provider",
                    0,
                    0,
                    0);
    public View mPinnedView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeAutomationSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    ZenModeAutomationSettings.SEARCH_INDEX_DATA_PROVIDER;
            new ZenModeBackend(context);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ZenModeAutomaticRulesPreferenceController(context, null, null, null));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("zen_mode_automatic_rules");
            return nonIndexableKeys;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        new ZenServiceListing(getContext(), this.CONFIG).reloadApprovedServices();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModeAutomaticRulesPreferenceController(
                        context, this, getSettingsLifecycle(), this));
        return arrayList;
    }

    public final int getAutomaticRulesCount() {
        Map<String, AutomaticZenRule> automaticZenRules =
                NotificationManager.from(((ZenModeSettingsBase) this).mContext)
                        .getAutomaticZenRules();
        int i = 0;
        if (automaticZenRules != null) {
            Iterator<Map.Entry<String, AutomaticZenRule>> it =
                    automaticZenRules.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getValue() != null) {
                    i++;
                }
            }
        }
        return i;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 142;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_automation_settings;
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("DELETE_RULE")) {
            return;
        }
        this.mBackend.removeZenRule(arguments.getString("DELETE_RULE"));
        arguments.remove("DELETE_RULE");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (getAutomaticRulesCount() != 0) {
            menu.add(0, 1, 0, R.string.common_remove);
            MenuItem add = menu.add(0, 2, 0, R.string.zen_mode_add_rule);
            try {
                add.setShowAsAction(2);
                add.setIcon(R.drawable.tw_ic_ab_add_mtrl);
                add.getIcon()
                        .setColorFilter(
                                ((ZenModeSettingsBase) this)
                                        .mContext
                                        .getResources()
                                        .getColor(R.color.sec_dnd_schedule_add, null),
                                PorterDuff.Mode.SRC_ATOP);
            } catch (NullPointerException unused) {
                Log.e("ZenModeSettings", "MenuItem or Drawable is null");
            }
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mPinnedView = setPinnedHeaderView(R.layout.sec_add_schedule_preference);
        getListView().mDrawLastRoundedCorner = false;
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            startLongPressActivity(null);
            return true;
        }
        if (itemId != 2) {
            return super.onOptionsItemSelected(menuItem);
        }
        Intent addFlags = new Intent().addFlags(67108864);
        addFlags.setAction("android.settings.ZEN_MODE_SCHEDULE_RULE_SETTINGS");
        startActivity(addFlags);
        return true;
    }

    @Override // com.android.settings.notification.zen.ZenRulePreference.OnPreferenceLongClickListener
    public final void onPreferenceLongClick(ZenRulePreference zenRulePreference) {
        startLongPressActivity(zenRulePreference.getKey());
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        if (getAutomaticRulesCount() != 0) {
            this.mPinnedView.setVisibility(8);
        } else {
            this.mPinnedView.setVisibility(0);
            ((LinearLayout) this.mPinnedView.findViewById(R.id.add_preference))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.notification.zen.ZenModeAutomationSettings.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    ZenModeAutomationSettings zenModeAutomationSettings =
                                            ZenModeAutomationSettings.this;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            ZenModeAutomationSettings.SEARCH_INDEX_DATA_PROVIDER;
                                    zenModeAutomationSettings.getClass();
                                    Intent addFlags = new Intent().addFlags(67108864);
                                    addFlags.setAction(
                                            "android.settings.ZEN_MODE_SCHEDULE_RULE_SETTINGS");
                                    zenModeAutomationSettings.startActivity(addFlags);
                                }
                            });
        }
    }

    public final void startLongPressActivity(String str) {
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("selected_id", str);
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$SecZenModeDeleteRulesSettingsActivity");
        intent.addFlags(67108864);
        intent.putExtra(":settings:show_fragment_args", m);
        startActivity(intent);
    }
}
