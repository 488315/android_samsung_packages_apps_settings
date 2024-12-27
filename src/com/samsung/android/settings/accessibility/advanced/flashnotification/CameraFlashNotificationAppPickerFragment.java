package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.picker.widget.SeslAppPickerListView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.LoadingViewController;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CameraFlashNotificationAppPickerFragment extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, MenuItem.OnActionExpandListener {
    public SeslAppPickerListView appPickerListView;
    public Context context;
    public LoadingViewController loadingViewController;
    public SettingsMainSwitchBar switchBar;
    public final Set installedAppSet = new HashSet();
    public final AnonymousClass1 mFlashNotificationObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    CameraFlashNotificationAppPickerFragment.this.updateSwitchBarToggleSwitch$2();
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.END_BY_REGULAR_CALL_RELEASE;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (FlashNotificationUtil.isOffAll(this.context, this.installedAppSet) && z) {
            return;
        }
        Settings.System.putInt(
                this.context.getContentResolver(), "camera_flash_notification", z ? 1 : 0);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (getActivity() == null || menu == null) {
            return;
        }
        menuInflater.inflate(R.menu.accessibility_flashnoti_menu, menu);
        MenuItem findItem = menu.findItem(R.id.search_app_list_menu);
        if (findItem != null) {
            findItem.setShowAsAction(10);
            findItem.setVisible(true);
            findItem.setOnActionExpandListener(this);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        setHasOptionsMenu(true);
        onCreateView.requireViewById(R.id.recycler_view).setImportantForAccessibility(2);
        View inflate =
                layoutInflater.inflate(
                        R.layout.camera_flash_notification_app_picker_with_all, viewGroup, false);
        inflate.setVisibility(8);
        ((ViewGroup) onCreateView.requireViewById(android.R.id.list_container)).addView(inflate);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.context);
        SeslAppPickerListView seslAppPickerListView =
                (SeslAppPickerListView) inflate.requireViewById(R.id.app_picker_view);
        this.appPickerListView = seslAppPickerListView;
        seslAppPickerListView.setAppListOrder(2);
        this.appPickerListView.setItemAnimator(null);
        this.appPickerListView.seslSetGoToTopEnabled(true);
        this.appPickerListView.seslSetFastScrollerEnabled(true);
        this.appPickerListView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.loadingViewController =
                new LoadingViewController(
                        onCreateView.requireViewById(R.id.loading_container), inflate, null);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            this.switchBar = settingsActivity.mMainSwitch;
        }
        this.switchBar.setChecked(
                Settings.System.getInt(
                                this.context.getContentResolver(), "camera_flash_notification", 0)
                        != 0);
        this.switchBar.setSessionDescription(getString(R.string.flash_notification_camera_flash));
        this.switchBar.addOnSwitchChangeListener(this);
        this.switchBar.show();
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.switchBar.hide();
        }
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return false;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.context);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = VolteConstants.ErrorCode.END_BY_REGULAR_CALL_RELEASE;
        launchRequest.mDestinationName = CameraFlashNotiAppPickerSearchFragment.class.getName();
        subSettingLauncher.launch();
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl
                    .getA11ySettingsMetricsFeatureProvider()
                    .clicked(VolteConstants.ErrorCode.END_BY_REGULAR_CALL_RELEASE, "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.context
                .getContentResolver()
                .unregisterContentObserver(this.mFlashNotificationObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.loadingViewController.handleLoadingContainer(false, false, false);
        this.context
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("camera_flash_notification"),
                        true,
                        this.mFlashNotificationObserver);
        updateSwitchBarToggleSwitch$2();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .visible(this.context, 0, VolteConstants.ErrorCode.END_BY_REGULAR_CALL_RELEASE, 0);
        new Thread(new CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0(this, 0))
                .start();
    }

    public final void updateSwitchBarToggleSwitch$2() {
        boolean z =
                Settings.System.getInt(
                                this.context.getContentResolver(), "camera_flash_notification", 0)
                        != 0;
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar == null
                || ((SeslSwitchBar) settingsMainSwitchBar).mSwitch.isChecked() == z) {
            return;
        }
        this.switchBar.setCheckedInternal(z);
    }
}
