package com.android.settings.security;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.AmbientDisplayConfiguration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.display.AmbientDisplayAlwaysOnPreferenceController;
import com.android.settings.display.AmbientDisplayNotificationsPreferenceController;
import com.android.settings.gestures.DoubleTapScreenPreferenceController;
import com.android.settings.gestures.PickupGesturePreferenceController;
import com.android.settings.notification.LockScreenNotificationPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.screenlock.LockScreenPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LockscreenDashboardFragment extends DashboardFragment
        implements OwnerInfoPreferenceController.OwnerInfoCallback {
    static final String KEY_ADD_USER_FROM_LOCK_SCREEN = "security_lockscreen_add_users_when_locked";
    static final String KEY_LOCK_SCREEN_NOTIFICATON = "security_setting_lock_screen_notif";
    static final String KEY_LOCK_SCREEN_NOTIFICATON_WORK_PROFILE =
            "security_setting_lock_screen_notif_work";
    static final String KEY_LOCK_SCREEN_NOTIFICATON_WORK_PROFILE_HEADER =
            "security_setting_lock_screen_notif_work_header";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.security_lockscreen_settings);
    public AmbientDisplayConfiguration mConfig;
    ContentObserver mControlsContentObserver;
    public OwnerInfoPreferenceController mOwnerInfoPreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.LockscreenDashboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(
                    new LockScreenNotificationPreferenceController(context, null, null, null));
            arrayList.add(new OwnerInfoPreferenceController(context, null));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys)
                    .add(LockscreenDashboardFragment.KEY_ADD_USER_FROM_LOCK_SCREEN);
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return new LockScreenPreferenceController(context, "anykey").isAvailable();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        LockScreenNotificationPreferenceController lockScreenNotificationPreferenceController =
                new LockScreenNotificationPreferenceController(
                        context,
                        KEY_LOCK_SCREEN_NOTIFICATON,
                        KEY_LOCK_SCREEN_NOTIFICATON_WORK_PROFILE_HEADER,
                        KEY_LOCK_SCREEN_NOTIFICATON_WORK_PROFILE);
        settingsLifecycle.addObserver(lockScreenNotificationPreferenceController);
        arrayList.add(lockScreenNotificationPreferenceController);
        OwnerInfoPreferenceController ownerInfoPreferenceController =
                new OwnerInfoPreferenceController(context, this);
        this.mOwnerInfoPreferenceController = ownerInfoPreferenceController;
        arrayList.add(ownerInfoPreferenceController);
        return arrayList;
    }

    public final AmbientDisplayConfiguration getConfig$1(Context context) {
        if (this.mConfig == null) {
            this.mConfig = new AmbientDisplayConfiguration(context);
        }
        return this.mConfig;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LockscreenDashboardFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 882;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.security_lockscreen_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AmbientDisplayAlwaysOnPreferenceController)
                        use(AmbientDisplayAlwaysOnPreferenceController.class))
                .setConfig(getConfig$1(context));
        ((AmbientDisplayNotificationsPreferenceController)
                        use(AmbientDisplayNotificationsPreferenceController.class))
                .setConfig(getConfig$1(context));
        ((DoubleTapScreenPreferenceController) use(DoubleTapScreenPreferenceController.class))
                .setConfig(getConfig$1(context));
        ((PickupGesturePreferenceController) use(PickupGesturePreferenceController.class))
                .setConfig(getConfig$1(context));
        this.mControlsContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.security.LockscreenDashboardFragment.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        super.onChange(z, uri);
                        LockscreenDashboardFragment lockscreenDashboardFragment =
                                LockscreenDashboardFragment.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                LockscreenDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                        lockscreenDashboardFragment.updatePreferenceStates();
                    }
                };
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("lockscreen_show_controls"),
                        false,
                        this.mControlsContentObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                KEY_LOCK_SCREEN_NOTIFICATON_WORK_PROFILE,
                "Settings.WORK_PROFILE_LOCKED_NOTIFICATION_TITLE",
                R.string.locked_work_profile_notification_title);
        replaceEnterpriseStringTitle(
                KEY_LOCK_SCREEN_NOTIFICATON_WORK_PROFILE_HEADER,
                "Settings.WORK_PROFILE_NOTIFICATIONS_SECTION_HEADER",
                R.string.profile_section_header);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        if (this.mControlsContentObserver != null) {
            getContext()
                    .getContentResolver()
                    .unregisterContentObserver(this.mControlsContentObserver);
            this.mControlsContentObserver = null;
        }
        super.onDetach();
    }

    @Override // com.android.settings.security.OwnerInfoPreferenceController.OwnerInfoCallback
    public final void onOwnerInfoUpdated() {
        OwnerInfoPreferenceController ownerInfoPreferenceController =
                this.mOwnerInfoPreferenceController;
        if (ownerInfoPreferenceController != null) {
            ownerInfoPreferenceController.updateSummary();
        }
    }
}
