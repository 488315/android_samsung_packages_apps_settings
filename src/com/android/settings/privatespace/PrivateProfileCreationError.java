package com.android.settings.privatespace;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateProfileCreationError extends InstrumentedFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2060;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GlifLayout glifLayout =
                (GlifLayout)
                        layoutInflater.inflate(
                                R.layout.privatespace_creation_error, viewGroup, false);
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        final int i = 0;
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_tryagain_label),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.privatespace.PrivateProfileCreationError$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateProfileCreationError f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                PrivateProfileCreationError privateProfileCreationError = this.f$0;
                                switch (i2) {
                                    case 0:
                                        privateProfileCreationError.mMetricsFeatureProvider.action(
                                                privateProfileCreationError.getContext(),
                                                1887,
                                                new Pair[0]);
                                        Log.i(
                                                "PrivateSpaceCreationErr",
                                                "Retry private space creation");
                                        NavHostFragment.Companion.findNavController(
                                                        privateProfileCreationError)
                                                .navigate(R.id.action_retry_profile_creation);
                                        break;
                                    default:
                                        FragmentActivity activity =
                                                privateProfileCreationError.getActivity();
                                        if (activity != null) {
                                            privateProfileCreationError.mMetricsFeatureProvider
                                                    .action(
                                                            privateProfileCreationError
                                                                    .getContext(),
                                                            1888,
                                                            new Pair[0]);
                                            Log.i(
                                                    "PrivateSpaceCreationErr",
                                                    "private space setup cancelled");
                                            activity.finish();
                                            break;
                                        }
                                        break;
                                }
                            }
                        },
                        5,
                        2132083805));
        final int i2 = 1;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_cancel_label),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.privatespace.PrivateProfileCreationError$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateProfileCreationError f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                PrivateProfileCreationError privateProfileCreationError = this.f$0;
                                switch (i22) {
                                    case 0:
                                        privateProfileCreationError.mMetricsFeatureProvider.action(
                                                privateProfileCreationError.getContext(),
                                                1887,
                                                new Pair[0]);
                                        Log.i(
                                                "PrivateSpaceCreationErr",
                                                "Retry private space creation");
                                        NavHostFragment.Companion.findNavController(
                                                        privateProfileCreationError)
                                                .navigate(R.id.action_retry_profile_creation);
                                        break;
                                    default:
                                        FragmentActivity activity =
                                                privateProfileCreationError.getActivity();
                                        if (activity != null) {
                                            privateProfileCreationError.mMetricsFeatureProvider
                                                    .action(
                                                            privateProfileCreationError
                                                                    .getContext(),
                                                            1888,
                                                            new Pair[0]);
                                            Log.i(
                                                    "PrivateSpaceCreationErr",
                                                    "private space setup cancelled");
                                            activity.finish();
                                            break;
                                        }
                                        break;
                                }
                            }
                        },
                        2,
                        2132083806),
                false);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new AnonymousClass1(true));
        return glifLayout;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privatespace.PrivateProfileCreationError$1, reason: invalid class name */
    public final class AnonymousClass1 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
