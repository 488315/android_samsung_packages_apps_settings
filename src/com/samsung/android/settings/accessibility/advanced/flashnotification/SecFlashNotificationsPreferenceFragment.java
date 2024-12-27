package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.FlashNotificationsPreferenceFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecFlashNotificationsPreferenceFragment extends FlashNotificationsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_flash_notifications_settings);
    public SecSwitchPreferenceScreen cameraFlashPreference;
    public final AnonymousClass1 mCameraFlashObserver;
    public Context mContext;
    public final AnonymousClass1 mScreenFlashObserver;
    public SecSwitchPreference screenFlashPreference;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment$1] */
    public SecFlashNotificationsPreferenceFragment() {
        final int i = 0;
        this.mCameraFlashObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment.1
                    public final /* synthetic */ SecFlashNotificationsPreferenceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        switch (i) {
                            case 0:
                                SecFlashNotificationsPreferenceFragment
                                        secFlashNotificationsPreferenceFragment = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        SecFlashNotificationsPreferenceFragment
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                secFlashNotificationsPreferenceFragment
                                        .updateCameraFlashNotificationSwitch();
                                break;
                            default:
                                SecFlashNotificationsPreferenceFragment
                                        secFlashNotificationsPreferenceFragment2 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        SecFlashNotificationsPreferenceFragment
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                secFlashNotificationsPreferenceFragment2
                                        .updateScreenFlashNotificationSwitch();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mScreenFlashObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment.1
                    public final /* synthetic */ SecFlashNotificationsPreferenceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        switch (i2) {
                            case 0:
                                SecFlashNotificationsPreferenceFragment
                                        secFlashNotificationsPreferenceFragment = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        SecFlashNotificationsPreferenceFragment
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                secFlashNotificationsPreferenceFragment
                                        .updateCameraFlashNotificationSwitch();
                                break;
                            default:
                                SecFlashNotificationsPreferenceFragment
                                        secFlashNotificationsPreferenceFragment2 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        SecFlashNotificationsPreferenceFragment
                                                .SEARCH_INDEX_DATA_PROVIDER;
                                secFlashNotificationsPreferenceFragment2
                                        .updateScreenFlashNotificationSwitch();
                                break;
                        }
                    }
                };
    }

    @Override // com.android.settings.accessibility.FlashNotificationsPreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.CMC_SD_NOT_REGISTERED;
    }

    @Override // com.android.settings.accessibility.FlashNotificationsPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_flash_notifications_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        ((ScreenFlashNotificationAllAppsPreferenceController)
                        use(ScreenFlashNotificationAllAppsPreferenceController.class))
                .setParentFragment(this);
        enableAutoFlowLogging(false);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getA11ySettingsMetricsFeatureProvider();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        DescriptionPreference descriptionPreference;
        super.onCreatePreferences(bundle, str);
        this.cameraFlashPreference = (SecSwitchPreferenceScreen) findPreference("camera_flash");
        this.screenFlashPreference = (SecSwitchPreference) findPreference("screen_flash");
        FragmentActivity activity = getActivity();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (activity == null
                || preferenceScreen == null
                || (descriptionPreference =
                                (DescriptionPreference) findPreference("guide_preference"))
                        == null) {
            return;
        }
        activity.addOnConfigurationChangedListener(
                new SecAccessibilityControlTimeoutPreferenceFragment$$ExternalSyntheticLambda0(
                        descriptionPreference));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(
                    VolteConstants.ErrorCode.CMC_SD_NOT_REGISTERED, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mContext.getContentResolver().unregisterContentObserver(this.mCameraFlashObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mScreenFlashObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mMetricsFeatureProvider.visible(
                null, 0, VolteConstants.ErrorCode.CMC_SD_NOT_REGISTERED, 0);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("camera_flash_notification"),
                        true,
                        this.mCameraFlashObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("screen_flash_notification"),
                        true,
                        this.mScreenFlashObserver);
        updateCameraFlashNotificationSwitch();
        updateScreenFlashNotificationSwitch();
    }

    public final void updateCameraFlashNotificationSwitch() {
        if (Settings.System.getInt(
                        getContext().getContentResolver(), "camera_flash_notification", 0)
                != 0) {
            this.cameraFlashPreference.setChecked(true);
        }
        SecCameraFlashNotificationPreferenceController
                secCameraFlashNotificationPreferenceController =
                        (SecCameraFlashNotificationPreferenceController)
                                use(SecCameraFlashNotificationPreferenceController.class);
        if (secCameraFlashNotificationPreferenceController != null) {
            secCameraFlashNotificationPreferenceController.updateState(this.cameraFlashPreference);
        }
    }

    public final void updateScreenFlashNotificationSwitch() {
        boolean z =
                Settings.System.getInt(
                                getContext().getContentResolver(), "screen_flash_notification", 0)
                        != 0;
        if (z) {
            this.screenFlashPreference.setChecked(true);
        } else {
            this.screenFlashPreference.setChecked(false);
        }
        SecScreenFlashNotificationPreferenceController
                secScreenFlashNotificationPreferenceController =
                        (SecScreenFlashNotificationPreferenceController)
                                use(SecScreenFlashNotificationPreferenceController.class);
        if (secScreenFlashNotificationPreferenceController != null) {
            secScreenFlashNotificationPreferenceController.setChecked(z);
        }
    }
}
