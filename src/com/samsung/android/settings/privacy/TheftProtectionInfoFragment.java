package com.samsung.android.settings.privacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TheftProtectionInfoFragment extends Fragment {
    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.fragment_theft_protection_info, viewGroup, false);
        if (getActivity() instanceof SettingsActivity) {
            ((SettingsActivity) getActivity()).hideAppBar();
        }
        inflate.findViewById(R.id.btn_ok)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.privacy.TheftProtectionInfoFragment$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                TheftProtectionInfoFragment.this
                                        .getActivity()
                                        .getOnBackPressedDispatcher()
                                        .onBackPressed();
                            }
                        });
        return inflate;
    }
}
