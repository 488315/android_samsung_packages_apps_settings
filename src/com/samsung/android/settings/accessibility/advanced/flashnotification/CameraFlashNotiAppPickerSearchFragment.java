package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.picker.model.AppInfo;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerListView;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.LoadingViewController;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CameraFlashNotiAppPickerSearchFragment extends SettingsPreferenceFragment
        implements MenuItem.OnActionExpandListener {
    public SeslAppPickerListView appPickerListView;
    public Context context;
    public final Set installedAppSet = new HashSet();
    public LoadingViewController loadingViewController;
    public SearchView searchView;

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
            findItem.expandActionView();
            SearchView searchView = (SearchView) findItem.getActionView();
            this.searchView = searchView;
            searchView.onSearchClicked();
            this.searchView.requestFocus();
            SearchView searchView2 = this.searchView;
            searchView2.mMaxWidth = Preference.DEFAULT_ORDER;
            searchView2.requestLayout();
            this.searchView.mOnQueryChangeListener = new AnonymousClass1();
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
                        R.layout.camera_flash_notification_app_picker, viewGroup, false);
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
        View requireViewById = onCreateView.requireViewById(R.id.loading_container);
        TextView textView = (TextView) onCreateView.requireViewById(android.R.id.empty);
        textView.setText(R.string.no_applications);
        this.loadingViewController = new LoadingViewController(requireViewById, inflate, textView);
        return onCreateView;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        getActivity().finish();
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.loadingViewController.handleLoadingContainer(false, false, false);
        new Thread(new CameraFlashNotiAppPickerSearchFragment$$ExternalSyntheticLambda0(this, 0))
                .start();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotiAppPickerSearchFragment$1, reason: invalid class name */
    public final class AnonymousClass1
            implements AppPickerState$OnStateChangeListener, SearchView.OnQueryTextListener {
        public /* synthetic */ AnonymousClass1() {}

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            CameraFlashNotiAppPickerSearchFragment cameraFlashNotiAppPickerSearchFragment =
                    CameraFlashNotiAppPickerSearchFragment.this;
            cameraFlashNotiAppPickerSearchFragment.appPickerListView.setSearchFilter(
                    str,
                    new CameraFlashNotiAppPickerSearchFragment$$ExternalSyntheticLambda2(
                            cameraFlashNotiAppPickerSearchFragment));
            return true;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            CameraFlashNotiAppPickerSearchFragment cameraFlashNotiAppPickerSearchFragment =
                    CameraFlashNotiAppPickerSearchFragment.this;
            cameraFlashNotiAppPickerSearchFragment.appPickerListView.setSearchFilter(
                    str,
                    new CameraFlashNotiAppPickerSearchFragment$$ExternalSyntheticLambda2(
                            cameraFlashNotiAppPickerSearchFragment));
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateChanged(AppInfo appInfo, boolean z) {
            CameraFlashNotiAppPickerSearchFragment cameraFlashNotiAppPickerSearchFragment =
                    CameraFlashNotiAppPickerSearchFragment.this;
            FlashNotificationUtil.putAppPreferenceWithAlias(
                    cameraFlashNotiAppPickerSearchFragment.context,
                    appInfo.packageName,
                    z,
                    cameraFlashNotiAppPickerSearchFragment.installedAppSet);
            if (z
                    || !FlashNotificationUtil.isOffAll(
                            cameraFlashNotiAppPickerSearchFragment.context,
                            cameraFlashNotiAppPickerSearchFragment.installedAppSet)) {
                return;
            }
            Settings.System.putInt(
                    cameraFlashNotiAppPickerSearchFragment.context.getContentResolver(),
                    "camera_flash_notification",
                    0);
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateAllChanged(boolean z) {}
    }
}
