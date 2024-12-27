package com.samsung.android.settings.datausage;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivitySettingsManager;
import android.net.NetworkPolicyManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.SeslAppPickerListView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.net.UidDetail;
import com.android.settingslib.net.UidDetailProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.core.SecSettingsBaseActivity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAllowedNetworksSettings extends SettingsPreferenceFragment
        implements ApplicationsState.Callbacks {
    public static final List mBlockList =
            new ArrayList<
                    String>() { // from class:
                                // com.samsung.android.settings.datausage.SecAllowedNetworksSettings.1
                {
                    add("Y29tLnNhbXN1bmcuYW5kcm9pZC5mYXN0");
                    add("Y29tLmFuZHJvaWQuc3lzdGVtdWk=");
                    add("Y29tLnNhbXN1bmcuYW5kcm9pZC5tZXNzYWdpbmc=");
                    add("Y29tLnNlYy5hbmRyb2lkLmFwcC5sYXVuY2hlcg==");
                }
            };
    public static final HashMap mBlockMap = new HashMap();
    public SeslAppPickerListView mAppPickerView;
    public List mApps;
    public View mEmptyView;
    public Handler mHandler;
    public View mLoadingContainer;
    public NetworkPolicyManager mNetworkPolicyManager;
    public PopupMenu mPopupMenu;
    public View mRootView;
    public ApplicationsState.Session mSession;
    public UidDetailProvider mUidDetailProvider;
    public boolean mIsRebuild = false;
    public boolean mWaitLoading = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AlphaComparator implements Comparator {
        public final Collator sCollator = Collator.getInstance();

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            String str;
            String str2;
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) obj;
            ApplicationsState.AppEntry appEntry2 = (ApplicationsState.AppEntry) obj2;
            if (appEntry == null || (str = appEntry.label) == null) {
                return -1;
            }
            if (appEntry2 == null || (str2 = appEntry2.label) == null) {
                return 1;
            }
            return this.sCollator.compare(str, str2);
        }
    }

    public final int findSelectItem(int i) {
        return !this.mNetworkPolicyManager.getFirewallRuleMobileData(i)
                ? R.id.wifi_only
                : ConnectivitySettingsManager.getMobileDataPreferredUids(getContext())
                                .contains(Integer.valueOf(i))
                        ? R.id.mobile_data_preferred
                        : R.id.both_on;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_datausage_mdo_title;
    }

    public final String getMenuName(int i) {
        return i == R.id.wifi_only
                ? getString(R.string.use_wifi_data_only)
                : i == R.id.mobile_data_preferred
                        ? getString(R.string.use_mobile_data_preferred)
                        : getString(R.string.use_both_data);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7151;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_connections";
    }

    public final void loadAppList() {
        if (this.mRootView != null) {
            this.mLoadingContainer.setVisibility(0);
            this.mAppPickerView.setVisibility(8);
            this.mEmptyView.setVisibility(8);
        }
        mBlockMap.clear();
        Iterator it = ((ArrayList) mBlockList).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                String str2 = new String(Base64.decode(str, 0));
                mBlockMap.put(
                        Integer.valueOf(
                                getContext().getPackageManager().getApplicationInfo(str2, 0).uid),
                        str2);
            } catch (PackageManager.NameNotFoundException unused) {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "Blocked : ", str, "AllowedNetworksforApps");
            }
        }
        ArrayList allApps = this.mSession.getAllApps();
        this.mApps = new ArrayList();
        if (this.mWaitLoading) {
            if (allApps.size() <= 0) {
                return;
            } else {
                this.mWaitLoading = false;
            }
        }
        for (int i = 0; i < allApps.size(); i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            if (!ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER.filterApp(appEntry)) {
                StringBuilder sb = new StringBuilder("Not Downloaded and launcher AppEntry: ");
                sb.append(appEntry.info.uid);
                sb.append(" , App Name : ");
                sb.append(appEntry.info.packageName);
                sb.append(" , AppName : ");
                Utils$$ExternalSyntheticOutline0.m(
                        sb, appEntry.info.name, "AllowedNetworksforApps");
            } else if (UserHandle.getAppId(appEntry.info.uid) < 10000) {
                StringBuilder sb2 = new StringBuilder("AppId under 10000 : ");
                sb2.append(appEntry.info.uid);
                sb2.append(" , App Name : ");
                Utils$$ExternalSyntheticOutline0.m(
                        sb2, appEntry.info.packageName, "AllowedNetworksforApps");
            } else {
                StringBuilder sb3 = new StringBuilder("Add App List Uid : ");
                sb3.append(appEntry.info.uid);
                sb3.append(" , App Name : ");
                Utils$$ExternalSyntheticOutline0.m(
                        sb3, appEntry.info.packageName, "AllowedNetworksforApps");
                ((ArrayList) this.mApps).add(appEntry);
            }
        }
        Collections.sort(this.mApps, new AlphaComparator());
        final ArrayList arrayList = new ArrayList();
        Iterator it2 = ((ArrayList) this.mApps).iterator();
        while (it2.hasNext()) {
            ApplicationsState.AppEntry appEntry2 = (ApplicationsState.AppEntry) it2.next();
            int i2 = appEntry2.info.uid;
            Drawable icon = AppUtils.getIcon(getContext(), appEntry2);
            String str3 = appEntry2.info.packageName;
            AppInfo.Companion companion = AppInfo.Companion;
            AppInfoData build =
                    new AppData.ListAppDataBuilder(
                                    AppInfo.Companion.obtain(i2, str3, ApnSettings.MVNO_NONE))
                            .build();
            build.setSubLabel(getMenuName(findSelectItem(i2)));
            build.setLabel(appEntry2.label);
            build.setIcon(icon);
            arrayList.add(build);
        }
        Handler handler = new Handler();
        this.mHandler = handler;
        handler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.datausage.SecAllowedNetworksSettings.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecAllowedNetworksSettings secAllowedNetworksSettings =
                                SecAllowedNetworksSettings.this;
                        if (secAllowedNetworksSettings.mRootView != null) {
                            secAllowedNetworksSettings.mLoadingContainer.setVisibility(8);
                            if (((ArrayList) SecAllowedNetworksSettings.this.mApps).size() == 1) {
                                SecAllowedNetworksSettings.this.mEmptyView.setVisibility(0);
                                SecAllowedNetworksSettings.this.mAppPickerView.setVisibility(8);
                            } else {
                                SecAllowedNetworksSettings.this.mAppPickerView
                                        .smoothScrollToPosition(0);
                                SecAllowedNetworksSettings.this.mAppPickerView.submitList(
                                        arrayList);
                                SecAllowedNetworksSettings.this.mAppPickerView.setVisibility(0);
                                SecAllowedNetworksSettings.this.mEmptyView.setVisibility(8);
                            }
                        }
                    }
                },
                300L);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSession =
                ApplicationsState.getInstance(
                                (Application) getPrefContext().getApplicationContext())
                        .newSession(this, getLifecycle());
        this.mIsRebuild = bundle != null;
        this.mNetworkPolicyManager = NetworkPolicyManager.from(getContext());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, R.string.refresh_app_list_option);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View findViewById;
        View inflate =
                layoutInflater.inflate(R.layout.sec_allowed_networks_settings, (ViewGroup) null);
        this.mRootView = inflate;
        View findViewById2 = inflate.findViewById(R.id.loading_container);
        this.mLoadingContainer = findViewById2;
        findViewById2.semSetRoundedCorners(15);
        this.mLoadingContainer.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mLoadingContainer.setVisibility(0);
        this.mEmptyView = this.mRootView.findViewById(R.id.empty_view);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.empty);
        textView.semSetRoundedCorners(15);
        textView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mUidDetailProvider = new UidDetailProvider(getContext());
        this.mAppPickerView =
                (SeslAppPickerListView) this.mRootView.findViewById(R.id.app_picker_view);
        this.mAppPickerView.setAppListOrder(1);
        this.mAppPickerView.seslSetGoToTopEnabled(true);
        this.mAppPickerView.seslSetFastScrollerEnabled(true);
        this.mAppPickerView.seslSetFillBottomEnabled(true);
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mAppPickerView.setItemAnimator(null);
        this.mAppPickerView.setNestedScrollingEnabled(true);
        this.mAppPickerView.setOnItemClickEventListener(
                new AppPickerEvent$OnItemClickEventListener() { // from class:
                                                                // com.samsung.android.settings.datausage.SecAllowedNetworksSettings.2
                    @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
                    public final boolean onClick(View view, final AppInfo appInfo) {
                        List list = SecAllowedNetworksSettings.mBlockList;
                        final SecAllowedNetworksSettings secAllowedNetworksSettings =
                                SecAllowedNetworksSettings.this;
                        secAllowedNetworksSettings.mPopupMenu =
                                new PopupMenu(
                                        secAllowedNetworksSettings.getContext(),
                                        view.findViewById(R.id.title_frame),
                                        0);
                        final AppCompatTextView appCompatTextView =
                                (AppCompatTextView) view.findViewById(R.id.summary);
                        if (Rune.isTmobileConcept()) {
                            secAllowedNetworksSettings.mPopupMenu.inflate(
                                    R.menu.sec_select_network_type_tmo);
                        } else {
                            secAllowedNetworksSettings.mPopupMenu.inflate(
                                    R.menu.sec_select_network_type);
                        }
                        secAllowedNetworksSettings
                                .mPopupMenu
                                .mMenu
                                .findItem(secAllowedNetworksSettings.findSelectItem(appInfo.user))
                                .setChecked(true);
                        PopupMenu popupMenu = secAllowedNetworksSettings.mPopupMenu;
                        popupMenu.mMenuItemClickListener =
                                new PopupMenu
                                        .OnMenuItemClickListener() { // from class:
                                                                     // com.samsung.android.settings.datausage.SecAllowedNetworksSettings.3
                                    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
                                    public final void onMenuItemClick(MenuItem menuItem) {
                                        SecAllowedNetworksSettings secAllowedNetworksSettings2 =
                                                SecAllowedNetworksSettings.this;
                                        Set mobileDataPreferredUids =
                                                ConnectivitySettingsManager
                                                        .getMobileDataPreferredUids(
                                                                secAllowedNetworksSettings2
                                                                        .getContext());
                                        int itemId = menuItem.getItemId();
                                        List list2 = SecAllowedNetworksSettings.mBlockList;
                                        AppInfo appInfo2 = appInfo;
                                        if (itemId == R.id.both_on) {
                                            secAllowedNetworksSettings2.mNetworkPolicyManager
                                                    .setFirewallRuleMobileData(appInfo2.user, true);
                                            mobileDataPreferredUids.remove(
                                                    Integer.valueOf(appInfo2.user));
                                            Log.i(
                                                    "AllowedNetworksforApps",
                                                    "Wifi and Mobile Data Both on Uid : "
                                                            + Integer.valueOf(appInfo2.user));
                                            secAllowedNetworksSettings2
                                                    .mPopupMenu
                                                    .mMenu
                                                    .findItem(R.id.both_on)
                                                    .setChecked(true);
                                        } else if (menuItem.getItemId()
                                                == R.id.mobile_data_preferred) {
                                            secAllowedNetworksSettings2.mNetworkPolicyManager
                                                    .setFirewallRuleMobileData(appInfo2.user, true);
                                            mobileDataPreferredUids.add(
                                                    Integer.valueOf(appInfo2.user));
                                            TooltipPopup$$ExternalSyntheticOutline0.m(
                                                    new StringBuilder("Mobile Data Only Uid : "),
                                                    appInfo2.user,
                                                    "AllowedNetworksforApps");
                                            secAllowedNetworksSettings2
                                                    .mPopupMenu
                                                    .mMenu
                                                    .findItem(R.id.mobile_data_preferred)
                                                    .setChecked(true);
                                        } else {
                                            secAllowedNetworksSettings2.mNetworkPolicyManager
                                                    .setFirewallRuleMobileData(
                                                            appInfo2.user, false);
                                            mobileDataPreferredUids.remove(
                                                    Integer.valueOf(appInfo2.user));
                                            TooltipPopup$$ExternalSyntheticOutline0.m(
                                                    new StringBuilder("Wifi Only Uid : "),
                                                    appInfo2.user,
                                                    "AllowedNetworksforApps");
                                            secAllowedNetworksSettings2
                                                    .mPopupMenu
                                                    .mMenu
                                                    .findItem(R.id.wifi_only)
                                                    .setChecked(true);
                                        }
                                        ConnectivitySettingsManager.setMobileDataPreferredUids(
                                                secAllowedNetworksSettings2.getContext(),
                                                mobileDataPreferredUids);
                                        appCompatTextView.setText(
                                                secAllowedNetworksSettings2.getMenuName(
                                                        secAllowedNetworksSettings2.findSelectItem(
                                                                appInfo2.user)));
                                        UidDetail uidDetail =
                                                secAllowedNetworksSettings2.mUidDetailProvider
                                                        .getUidDetail(appInfo2.user, true);
                                        String str = appInfo2.packageName;
                                        int i = appInfo2.user;
                                        AppInfo.Companion companion = AppInfo.Companion;
                                        AppInfoData build =
                                                new AppData.ListAppDataBuilder(
                                                                AppInfo.Companion.obtain(
                                                                        i,
                                                                        str,
                                                                        ApnSettings.MVNO_NONE))
                                                        .setIcon(uidDetail.icon)
                                                        .build();
                                        build.setSubLabel(
                                                secAllowedNetworksSettings2.getMenuName(
                                                        secAllowedNetworksSettings2.findSelectItem(
                                                                appInfo2.user)));
                                        secAllowedNetworksSettings2.mAppPickerView.updateItem(
                                                build);
                                    }
                                };
                        popupMenu.show();
                        return true;
                    }
                });
        this.mAppPickerView.addFooter(
                getLayoutInflater()
                        .inflate(R.layout.sec_allowed_networks_settings_footer, (ViewGroup) null));
        this.mAppPickerView.addHeader(
                getLayoutInflater()
                        .inflate(R.layout.sec_allowed_network_empty_header_view, (ViewGroup) null),
                0);
        if (Rune.isTmobileConcept()) {
            ((TextView) this.mRootView.findViewById(R.id.allowed_network_for_apps_description))
                    .setText(R.string.allowed_networks_for_apps_menu_description_for_tmo);
        }
        return this.mRootView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        UidDetailProvider uidDetailProvider = this.mUidDetailProvider;
        if (uidDetailProvider != null) {
            synchronized (uidDetailProvider.mUidDetailCache) {
                uidDetailProvider.mUidDetailCache.clear();
            }
        }
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {
        View view;
        if (this.mIsRebuild
                && (view = this.mLoadingContainer) != null
                && view.getVisibility() == 0) {
            loadAppList();
            this.mIsRebuild = false;
            Log.i("AllowedNetworksforApps", "rebuild page");
        } else if (this.mWaitLoading) {
            this.mWaitLoading = false;
            loadAppList();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 1) {
            loadAppList();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        View view = this.mLoadingContainer;
        if (view == null || view.getVisibility() != 0 || this.mWaitLoading) {
            return;
        }
        loadAppList();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLoadEntriesCompleted() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageListChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRebuildComplete(ArrayList arrayList) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRunningStateChanged(boolean z) {}
}
