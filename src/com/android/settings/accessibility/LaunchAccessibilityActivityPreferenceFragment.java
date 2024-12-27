package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LaunchAccessibilityActivityPreferenceFragment extends ToggleFeaturePreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final AccessibilityShortcutInfo getAccessibilityShortcutInfo() {
        List installedAccessibilityShortcutListAsUser =
                AccessibilityManager.getInstance(getPrefContext())
                        .getInstalledAccessibilityShortcutListAsUser(
                                getPrefContext(), UserHandle.myUserId());
        int size = installedAccessibilityShortcutListAsUser.size();
        for (int i = 0; i < size; i++) {
            AccessibilityShortcutInfo accessibilityShortcutInfo =
                    (AccessibilityShortcutInfo) installedAccessibilityShortcutListAsUser.get(i);
            ActivityInfo activityInfo = accessibilityShortcutInfo.getActivityInfo();
            if (this.mComponentName.getPackageName().equals(activityInfo.packageName)
                    && this.mComponentName.getClassName().equals(activityInfo.name)) {
                return accessibilityShortcutInfo;
            }
        }
        return null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LaunchAccessibilityActivityPreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return getArguments().getInt("metrics_category");
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("top_intro");
        arrayList.add("animated_image");
        arrayList.add("launch_preference");
        arrayList.add("shortcut_preference");
        arrayList.add("app_info");
        arrayList.add("general_categories");
        arrayList.add("html_description");
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return 0;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Preference preference = new Preference(getPrefContext());
        preference.setKey("launch_preference");
        AccessibilityShortcutInfo accessibilityShortcutInfo = getAccessibilityShortcutInfo();
        preference.setTitle(
                accessibilityShortcutInfo == null
                        ? ApnSettings.MVNO_NONE
                        : getString(
                                R.string.accessibility_common_button_open_app,
                                accessibilityShortcutInfo
                                        .getActivityInfo()
                                        .loadLabel(getPackageManager())));
        preference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.accessibility.LaunchAccessibilityActivityPreferenceFragment$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference2) {
                        int i = LaunchAccessibilityActivityPreferenceFragment.$r8$clinit;
                        LaunchAccessibilityActivityPreferenceFragment
                                launchAccessibilityActivityPreferenceFragment =
                                        LaunchAccessibilityActivityPreferenceFragment.this;
                        AccessibilityStatsLogUtils.logAccessibilityServiceEnabled(
                                launchAccessibilityActivityPreferenceFragment.mComponentName, true);
                        int displayId =
                                launchAccessibilityActivityPreferenceFragment
                                        .getPrefContext()
                                        .getDisplayId();
                        ComponentName componentName =
                                launchAccessibilityActivityPreferenceFragment.mComponentName;
                        Intent intent = new Intent();
                        Bundle bundle2 =
                                ActivityOptions.makeBasic()
                                        .setLaunchDisplayId(displayId)
                                        .toBundle();
                        intent.setComponent(componentName);
                        intent.addFlags(268435456);
                        try {
                            launchAccessibilityActivityPreferenceFragment
                                    .getPrefContext()
                                    .startActivityAsUser(
                                            intent, bundle2, UserHandle.of(UserHandle.myUserId()));
                        } catch (ActivityNotFoundException unused) {
                            Log.w(
                                    "LaunchAccessibilityActivityPreferenceFragment",
                                    "Target activity not found.");
                        }
                        return true;
                    }
                });
        getPreferenceScreen().addPreference(preference);
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        removePreference("use_service");
        this.mToggleServiceSwitchBar.hide();
        return onCreateView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0094, code lost:

       if (getPackageManager().queryIntentActivities(r1, 0).isEmpty() != false) goto L10;
    */
    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onProcessArguments(android.os.Bundle r6) {
        /*
            r5 = this;
            super.onProcessArguments(r6)
            java.lang.String r0 = "component_name"
            android.os.Parcelable r0 = r6.getParcelable(r0)
            android.content.ComponentName r0 = (android.content.ComponentName) r0
            r5.mComponentName = r0
            android.accessibilityservice.AccessibilityShortcutInfo r0 = r5.getAccessibilityShortcutInfo()
            if (r0 == 0) goto L29
            android.accessibilityservice.AccessibilityShortcutInfo r0 = r5.getAccessibilityShortcutInfo()
            android.content.pm.ActivityInfo r0 = r0.getActivityInfo()
            android.content.pm.PackageManager r1 = r5.getPackageManager()
            java.lang.CharSequence r0 = r0.loadLabel(r1)
            java.lang.String r0 = r0.toString()
            r5.mPackageName = r0
        L29:
            java.lang.String r0 = "animated_image_res"
            int r0 = r6.getInt(r0)
            if (r0 <= 0) goto L54
            android.net.Uri$Builder r1 = new android.net.Uri$Builder
            r1.<init>()
            java.lang.String r2 = "android.resource"
            android.net.Uri$Builder r1 = r1.scheme(r2)
            android.content.ComponentName r2 = r5.mComponentName
            java.lang.String r2 = r2.getPackageName()
            android.net.Uri$Builder r1 = r1.authority(r2)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            android.net.Uri$Builder r0 = r1.appendPath(r0)
            android.net.Uri r0 = r0.build()
            r5.mImageUri = r0
        L54:
            java.lang.String r0 = "html_description"
            java.lang.CharSequence r0 = r6.getCharSequence(r0)
            r5.mHtmlDescription = r0
            java.lang.String r0 = "settings_title"
            java.lang.String r0 = r6.getString(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 == 0) goto L6b
        L69:
            r1 = r2
            goto L97
        L6b:
            java.lang.String r1 = "settings_component_name"
            java.lang.String r1 = r6.getString(r1)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L78
            goto L69
        L78:
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r4 = "android.intent.action.MAIN"
            r3.<init>(r4)
            android.content.ComponentName r1 = android.content.ComponentName.unflattenFromString(r1)
            android.content.Intent r1 = r3.setComponent(r1)
            android.content.pm.PackageManager r3 = r5.getPackageManager()
            r4 = 0
            java.util.List r3 = r3.queryIntentActivities(r1, r4)
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L97
            goto L69
        L97:
            r5.mSettingsIntent = r1
            if (r1 != 0) goto L9c
            r0 = r2
        L9c:
            r5.mSettingsTitle = r0
            java.lang.String r5 = "tile_service_component_name"
            boolean r0 = r6.containsKey(r5)
            if (r0 == 0) goto Lad
            java.lang.String r5 = r6.getString(r5)
            android.content.ComponentName.unflattenFromString(r5)
        Lad:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.accessibility.LaunchAccessibilityActivityPreferenceFragment.onProcessArguments(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {}

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {}
}
