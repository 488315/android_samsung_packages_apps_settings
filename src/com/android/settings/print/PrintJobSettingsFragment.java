package com.android.settings.print;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintJob;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrintJobSettingsFragment extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PrintJobSettingsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 78;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.print_job_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((PrintJobPreferenceController) use(PrintJobPreferenceController.class)).init(this);
        ((PrintJobMessagePreferenceController) use(PrintJobMessagePreferenceController.class))
                .init(this);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        PrintJob printJob =
                ((PrintJobPreferenceController) use(PrintJobPreferenceController.class))
                        .getPrintJob();
        if (printJob == null) {
            return;
        }
        if (!printJob.getInfo().isCancelling()) {
            menu.add(0, 1, 0, getString(R.string.print_cancel)).setShowAsAction(1);
        }
        if (printJob.isFailed()) {
            menu.add(0, 2, 0, getString(R.string.print_restart)).setShowAsAction(1);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        PrintJob printJob =
                ((PrintJobPreferenceController) use(PrintJobPreferenceController.class))
                        .getPrintJob();
        if (printJob != null) {
            int itemId = menuItem.getItemId();
            if (itemId == 1) {
                printJob.cancel();
                finish();
                return true;
            }
            if (itemId == 2) {
                printJob.restart();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().setEnabled(false);
    }
}
