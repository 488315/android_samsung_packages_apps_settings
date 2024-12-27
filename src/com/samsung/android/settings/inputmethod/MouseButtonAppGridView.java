package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.SeslAppPickerGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MouseButtonAppGridView extends SettingsPreferenceFragment
        implements ApplicationsState.Callbacks {
    public SeslAppPickerGridView mAppPickerGridView;
    public Context mContext;
    public ViewGroup mLoading;
    public int mMouseButtonType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AlphaComparator implements Comparator {
        public final Collator sCollator = Collator.getInstance();

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            AppInfoData appInfoData = (AppInfoData) obj;
            AppInfoData appInfoData2 = (AppInfoData) obj2;
            if (appInfoData == null || appInfoData.getLabel() == null) {
                return -1;
            }
            if (appInfoData2 == null || appInfoData2.getLabel() == null) {
                return 1;
            }
            return this.sCollator.compare(appInfoData.getLabel(), appInfoData2.getLabel());
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final RecyclerView getListViewWithSpacing() {
        return this.mAppPickerGridView;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mLoading.setVisibility(0);
        this.mAppPickerGridView.setVisibility(8);
        new Handler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.inputmethod.MouseButtonAppGridView.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MouseButtonAppGridView.this.mLoading.setVisibility(8);
                                MouseButtonAppGridView.this.mAppPickerGridView.setVisibility(0);
                                MouseButtonAppGridView mouseButtonAppGridView =
                                        MouseButtonAppGridView.this;
                                SeslAppPickerGridView seslAppPickerGridView =
                                        mouseButtonAppGridView.mAppPickerGridView;
                                HashMap hashMap = new HashMap();
                                String string =
                                        Settings.System.getString(
                                                mouseButtonAppGridView.mContext
                                                        .getContentResolver(),
                                                MouseFunctionKeyInfo.getMouseFunctionDBKey(
                                                        mouseButtonAppGridView.mMouseButtonType));
                                String str =
                                        string != null
                                                ? string.split("/")[0]
                                                : ApnSettings.MVNO_NONE;
                                for (LauncherActivityInfo launcherActivityInfo :
                                        ((LauncherApps)
                                                        mouseButtonAppGridView
                                                                .getContext()
                                                                .getSystemService("launcherapps"))
                                                .getActivityList(
                                                        null,
                                                        new UserHandle(
                                                                UserHandle.getCallingUserId()))) {
                                    String str2 =
                                            launcherActivityInfo.getApplicationInfo().packageName;
                                    String name = launcherActivityInfo.getName();
                                    int appId =
                                            UserHandle.getAppId(
                                                    launcherActivityInfo.getApplicationInfo().uid);
                                    AppInfo.Companion companion = AppInfo.Companion;
                                    AppInfoData build =
                                            new AppData.ListAppDataBuilder(
                                                            AppInfo.Companion.obtain(
                                                                    appId, str2, name))
                                                    .setIcon(launcherActivityInfo.getIcon(0))
                                                    .setLabel(
                                                            launcherActivityInfo
                                                                    .getLabel()
                                                                    .toString())
                                                    .build();
                                    if (str2.equals(str)) {
                                        build.setDimmed(true);
                                    }
                                    if (!hashMap.containsKey(build.getLabel())) {
                                        hashMap.put(build.getLabel(), build);
                                    }
                                }
                                ArrayList arrayList = new ArrayList();
                                Iterator it = hashMap.keySet().iterator();
                                while (it.hasNext()) {
                                    arrayList.add((AppInfoData) hashMap.get((String) it.next()));
                                }
                                arrayList.sort(new AlphaComparator());
                                seslAppPickerGridView.submitList(arrayList);
                                MouseButtonAppGridView.this.mAppPickerGridView.addFooter(
                                        MouseButtonAppGridView.this
                                                .getLayoutInflater()
                                                .inflate(
                                                        R.layout
                                                                .sec_allowed_networks_settings_footer,
                                                        (ViewGroup) null));
                                MouseButtonAppGridView.this.mAppPickerGridView.addHeader(
                                        MouseButtonAppGridView.this
                                                .getLayoutInflater()
                                                .inflate(
                                                        R.layout
                                                                .sec_allowed_network_empty_header_view,
                                                        (ViewGroup) null),
                                        0);
                                MouseButtonAppGridView.this.mAppPickerGridView.setItemAnimator(
                                        null);
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
        this.mContext = getContext();
        this.mMouseButtonType = getArguments().getInt("mouseButtonType");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_app_shortcut_view, viewGroup, false);
        this.mAppPickerGridView = new SeslAppPickerGridView(this.mContext);
        SeslAppPickerGridView seslAppPickerGridView =
                (SeslAppPickerGridView) inflate.findViewById(R.id.appshortcutview);
        this.mAppPickerGridView = seslAppPickerGridView;
        seslAppPickerGridView.setNestedScrollingEnabled(true);
        this.mAppPickerGridView.semSetRoundedCorners(15);
        this.mAppPickerGridView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerGridView.setVisibility(8);
        this.mAppPickerGridView.seslSetFastScrollerEnabled(true);
        this.mAppPickerGridView.seslSetGoToTopEnabled(true);
        this.mAppPickerGridView.seslSetFillBottomEnabled(false);
        this.mAppPickerGridView.setOnItemClickEventListener(
                new AppPickerEvent$OnItemClickEventListener() { // from class:
                                                                // com.samsung.android.settings.inputmethod.MouseButtonAppGridView$$ExternalSyntheticLambda0
                    @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
                    public final boolean onClick(View view, AppInfo appInfo) {
                        MouseButtonAppGridView mouseButtonAppGridView = MouseButtonAppGridView.this;
                        mouseButtonAppGridView.getClass();
                        String str = appInfo.packageName;
                        String str2 = appInfo.activityName;
                        Context context = mouseButtonAppGridView.mContext;
                        int i = mouseButtonAppGridView.mMouseButtonType;
                        String m =
                                AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                        str, "/", str2);
                        Settings.System.putString(
                                context.getContentResolver(),
                                MouseFunctionKeyInfo.getMouseFunctionDBKey(i),
                                m);
                        Log.d(
                                "MouseFunctionKeyInfo",
                                "[save] mouseButtonType : " + i + "  / app : " + m);
                        MouseFunctionKeyInfo.releaseKeyCustomizationInfo(i);
                        MouseFunctionKeyInfo.setKeyCustomizationInfo(i, str, str2);
                        mouseButtonAppGridView.getActivity().onBackPressed();
                        return true;
                    }
                });
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.loading_panel);
        this.mLoading = viewGroup2;
        viewGroup2.semSetRoundedCorners(3);
        this.mLoading.semSetRoundedCornerColor(
                3, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        return inflate;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        if (this.mLoading != null) {
            this.mLoading = null;
        }
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {}

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
