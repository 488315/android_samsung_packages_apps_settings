package com.android.settings.applications;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.android.internal.app.procstats.ProcessStats;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ProcessStatsBase extends SettingsPreferenceFragment
        implements AdapterView.OnItemSelectedListener {
    public static final int[] sDurationLabels;
    public static final long[] sDurations;
    public int mDurationIndex;
    public ArrayAdapter mFilterAdapter;
    public Spinner mFilterSpinner;
    public ViewGroup mSpinnerHeader;
    public ProcStatsData mStatsManager;

    static {
        long j = ProcessStats.COMMIT_PERIOD / 2;
        sDurations = new long[] {10800000 - j, 21600000 - j, 43200000 - j, 86400000 - j};
        sDurationLabels =
                new int[] {
                    R.string.menu_duration_3h,
                    R.string.menu_duration_6h,
                    R.string.menu_duration_12h,
                    R.string.menu_duration_1d
                };
    }

    public static void launchMemoryDetail(
            SettingsActivity settingsActivity,
            ProcStatsData.MemInfo memInfo,
            ProcStatsPackageEntry procStatsPackageEntry) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("package_entry", procStatsPackageEntry);
        bundle.putDouble("weight_to_ram", memInfo.weightToRam);
        bundle.putLong("total_time", memInfo.memTotalTime);
        bundle.putDouble("max_memory_usage", memInfo.usedWeight * memInfo.weightToRam);
        bundle.putDouble("total_scale", memInfo.totalScale);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(settingsActivity);
        String name = ProcessStatsDetail.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.memory_usage, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.mStatsManager =
                new ProcStatsData(
                        getActivity(),
                        bundle != null
                                || (arguments != null
                                        && arguments.getBoolean("transfer_stats", false)));
        this.mDurationIndex =
                bundle != null
                        ? bundle.getInt("duration_index")
                        : arguments != null ? arguments.getInt("duration_index") : 0;
        this.mStatsManager.mDuration =
                bundle != null ? bundle.getLong("duration", sDurations[0]) : sDurations[0];
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (getActivity().isChangingConfigurations()) {
            ProcStatsData.sStatsXfer = this.mStatsManager.mStats;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        this.mDurationIndex = i;
        this.mStatsManager.mDuration = sDurations[i];
        refreshUi();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
        this.mFilterSpinner.setSelection(0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mStatsManager.refreshStats(false);
        refreshUi();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong("duration", this.mStatsManager.mDuration);
        bundle.putInt("duration_index", this.mDurationIndex);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ViewGroup viewGroup = (ViewGroup) setPinnedHeaderView(R.layout.sec_apps_filter_spinner);
        this.mSpinnerHeader = viewGroup;
        viewGroup.semSetRoundedCorners(15);
        this.mSpinnerHeader.semSetRoundedCornerColor(
                15, getContext().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mFilterSpinner = (Spinner) this.mSpinnerHeader.findViewById(R.id.filter_spinner);
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(
                        this.mFilterSpinner.getContext(), R.layout.sec_filter_spinner_custom_item);
        this.mFilterAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        for (int i = 0; i < 4; i++) {
            this.mFilterAdapter.add(getString(sDurationLabels[i]));
        }
        this.mFilterSpinner.setAdapter((SpinnerAdapter) this.mFilterAdapter);
        this.mFilterSpinner.setSelection(this.mDurationIndex);
        this.mFilterSpinner.setOnItemSelectedListener(this);
    }

    public abstract void refreshUi();
}
