package com.android.settings.applications;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.RunningProcessesView.ServiceListAdapter;
import com.android.settings.development.DeveloperOptionAwareMixin;
import com.android.settings.widget.LoadingViewController;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RunningServices extends SettingsPreferenceFragment
        implements DeveloperOptionAwareMixin {
    public View mLoadingContainer;
    public LoadingViewController mLoadingViewController;
    public Menu mOptionsMenu;
    public final AnonymousClass1 mRunningProcessesAvail =
            new Runnable() { // from class: com.android.settings.applications.RunningServices.1
                @Override // java.lang.Runnable
                public final void run() {
                    RunningServices.this.mLoadingViewController.showContent(true);
                }
            };
    public RunningProcessesView mRunningProcessesView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 404;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.runningservices_settings_title);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.mOptionsMenu = menu;
        menu.add(0, 1, 1, R.string.show_running_services).setShowAsAction(0);
        menu.add(0, 2, 2, R.string.show_background_processes).setShowAsAction(0);
        updateOptionsMenu$2();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.manage_applications_running, (ViewGroup) null);
        RunningProcessesView runningProcessesView =
                (RunningProcessesView) inflate.findViewById(R.id.running_processes);
        this.mRunningProcessesView = runningProcessesView;
        runningProcessesView.mAm =
                (ActivityManager) runningProcessesView.getContext().getSystemService("activity");
        runningProcessesView.mState = RunningState.getInstance(runningProcessesView.getContext());
        LayoutInflater layoutInflater2 =
                (LayoutInflater)
                        runningProcessesView.getContext().getSystemService("layout_inflater");
        layoutInflater2.inflate(R.layout.running_processes_view, runningProcessesView);
        runningProcessesView.mListView =
                (ListView) runningProcessesView.findViewById(android.R.id.list);
        View findViewById = runningProcessesView.findViewById(android.R.id.empty);
        if (findViewById != null) {
            runningProcessesView.mListView.setEmptyView(findViewById);
        }
        runningProcessesView.mListView.setOnItemClickListener(runningProcessesView);
        runningProcessesView.mListView.setRecyclerListener(runningProcessesView);
        RunningProcessesView.ServiceListAdapter serviceListAdapter =
                runningProcessesView.new ServiceListAdapter(runningProcessesView.mState);
        runningProcessesView.mAdapter = serviceListAdapter;
        runningProcessesView.mListView.setAdapter((ListAdapter) serviceListAdapter);
        View inflate2 =
                layoutInflater2.inflate(R.layout.running_processes_header, (ViewGroup) null);
        runningProcessesView.mHeader = inflate2;
        runningProcessesView.mListView.addHeaderView(inflate2, null, false);
        runningProcessesView.mColorBar =
                (ProgressBar) runningProcessesView.mHeader.findViewById(R.id.color_bar);
        Context context = runningProcessesView.getContext();
        runningProcessesView.mColorBar.setProgressTintList(
                ColorStateList.valueOf(context.getColor(R.color.running_processes_system_ram)));
        runningProcessesView.mColorBar.setSecondaryProgressTintList(
                Utils.getColorAttr(context, android.R.attr.colorAccent));
        ProgressBar progressBar = runningProcessesView.mColorBar;
        PorterDuff.Mode mode = PorterDuff.Mode.SRC;
        progressBar.setSecondaryProgressTintMode(mode);
        runningProcessesView.mColorBar.setProgressBackgroundTintList(
                ColorStateList.valueOf(context.getColor(R.color.running_processes_free_ram)));
        runningProcessesView.mColorBar.setProgressBackgroundTintMode(mode);
        runningProcessesView.mAppsProcessPrefix =
                (TextView) runningProcessesView.mHeader.findViewById(R.id.appsSizePrefix);
        runningProcessesView.mForegroundProcessPrefix =
                (TextView) runningProcessesView.mHeader.findViewById(R.id.systemSizePrefix);
        runningProcessesView.mBackgroundProcessText =
                (TextView) runningProcessesView.mHeader.findViewById(R.id.freeSize);
        runningProcessesView.mAppsProcessText =
                (TextView) runningProcessesView.mHeader.findViewById(R.id.appsSize);
        runningProcessesView.mForegroundProcessText =
                (TextView) runningProcessesView.mHeader.findViewById(R.id.systemSize);
        runningProcessesView.mAm.getMemoryInfo(new ActivityManager.MemoryInfo());
        View findViewById2 = inflate.findViewById(R.id.loading_container);
        this.mLoadingContainer = findViewById2;
        this.mLoadingViewController =
                new LoadingViewController(findViewById2, this.mRunningProcessesView, null);
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            this.mRunningProcessesView.mAdapter.setShowBackground(false);
        } else {
            if (itemId != 2) {
                return false;
            }
            this.mRunningProcessesView.mAdapter.setShowBackground(true);
        }
        updateOptionsMenu$2();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        RunningProcessesView runningProcessesView = this.mRunningProcessesView;
        runningProcessesView.mState.pause();
        runningProcessesView.mDataAvail = null;
        runningProcessesView.mOwner = null;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        updateOptionsMenu$2();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        boolean z;
        super.onResume();
        RunningProcessesView runningProcessesView = this.mRunningProcessesView;
        AnonymousClass1 anonymousClass1 = this.mRunningProcessesAvail;
        runningProcessesView.mOwner = this;
        runningProcessesView.mState.resume(runningProcessesView);
        RunningState runningState = runningProcessesView.mState;
        synchronized (runningState.mLock) {
            z = runningState.mHaveData;
        }
        if (z) {
            runningProcessesView.refreshUi(true);
            this.mLoadingViewController.showContent(false);
        } else {
            runningProcessesView.mDataAvail = anonymousClass1;
            this.mLoadingViewController.handleLoadingContainer(false, false, false);
        }
    }

    public final void updateOptionsMenu$2() {
        boolean z = this.mRunningProcessesView.mAdapter.mShowBackground;
        this.mOptionsMenu.findItem(1).setVisible(z);
        this.mOptionsMenu.findItem(2).setVisible(!z);
    }
}
