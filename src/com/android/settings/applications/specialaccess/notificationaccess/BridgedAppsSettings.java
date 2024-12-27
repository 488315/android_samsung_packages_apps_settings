package com.android.settings.applications.specialaccess.notificationaccess;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.applications.ApplicationsState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BridgedAppsSettings extends DashboardFragment {
    public ComponentName mComponentName;
    public ApplicationsState.AppFilter mFilter;
    public boolean mShowSystem;
    public int mUserId;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BridgedAppsSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1865;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.notification_access_bridged_apps_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mFilter =
                this.mShowSystem
                        ? ApplicationsState.FILTER_ALL_ENABLED
                        : ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER;
        Bundle arguments = getArguments();
        Intent intent =
                arguments == null ? getIntent() : (Intent) arguments.getParcelable("intent");
        String string =
                arguments.getString("android.provider.extra.NOTIFICATION_LISTENER_COMPONENT_NAME");
        if (string != null) {
            this.mComponentName = ComponentName.unflattenFromString(string);
        }
        if (intent == null || !intent.hasExtra("android.intent.extra.user_handle")) {
            this.mUserId = UserHandle.myUserId();
        } else {
            this.mUserId =
                    ((UserHandle) intent.getParcelableExtra("android.intent.extra.user_handle"))
                            .getIdentifier();
        }
        ((BridgedAppsPreferenceController) use(BridgedAppsPreferenceController.class))
                .setAppState(
                        ApplicationsState.getInstance(
                                (Application) context.getApplicationContext()))
                .setCn(this.mComponentName)
                .setUserId(this.mUserId)
                .setSession(getSettingsLifecycle())
                .setFilter(this.mFilter)
                .rebuild();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mShowSystem = bundle != null && bundle.getBoolean("show_system");
        ((BridgedAppsPreferenceController) use(BridgedAppsPreferenceController.class))
                .setNm(new NotificationBackend());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(
                0, 43, 0, this.mShowSystem ? R.string.menu_hide_system : R.string.menu_show_system);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 43) {
            boolean z = !this.mShowSystem;
            this.mShowSystem = z;
            menuItem.setTitle(z ? R.string.menu_hide_system : R.string.menu_show_system);
            this.mFilter =
                    this.mShowSystem
                            ? ApplicationsState.FILTER_NOT_HIDE
                            : ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER;
            ((BridgedAppsPreferenceController) use(BridgedAppsPreferenceController.class))
                    .setFilter(this.mFilter)
                    .rebuild();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("show_system", this.mShowSystem);
    }
}
