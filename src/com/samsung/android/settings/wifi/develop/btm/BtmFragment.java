package com.samsung.android.settings.wifi.develop.btm;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.SettingsPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BtmFragment extends SettingsPreferenceFragment {
    public BtmAdapter mBtmAdapter;
    public RecyclerView mBtmRecyclerView;
    public TextView mBtmSubTitleView;
    public Context mContext;
    public TextView mNoIssueView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x006e, code lost:

       if (r8.isEmpty() == false) goto L8;
    */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onCreateView(
            android.view.LayoutInflater r6, android.view.ViewGroup r7, android.os.Bundle r8) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.btm.BtmFragment.onCreateView(android.view.LayoutInflater,"
                    + " android.view.ViewGroup, android.os.Bundle):android.view.View");
    }
}
