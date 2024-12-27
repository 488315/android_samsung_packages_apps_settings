package com.android.settings.privatespace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.fragment.NavHostFragment;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.sec.ims.ImsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceAccountLoginError extends InstrumentedFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2062;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GlifLayout glifLayout =
                (GlifLayout)
                        layoutInflater.inflate(
                                R.layout.privatespace_account_login_error, viewGroup, false);
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        final int i = 1;
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_continue_login_label),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.privatespace.PrivateSpaceAccountLoginError$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateSpaceAccountLoginError f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                PrivateSpaceAccountLoginError privateSpaceAccountLoginError =
                                        this.f$0;
                                switch (i2) {
                                    case 0:
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1902,
                                                        new Pair[0]);
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1889,
                                                        false);
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceAccountLoginError)
                                                .navigate(R.id.action_skip_account_login);
                                        break;
                                    default:
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1890,
                                                        new Pair[0]);
                                        Intent intent =
                                                new Intent(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        (Class<?>)
                                                                PrivateProfileContextHelperActivity
                                                                        .class);
                                        intent.putExtra(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 2);
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1894,
                                                        new Pair[0]);
                                        privateSpaceAccountLoginError
                                                .getActivity()
                                                .startActivityForResult(intent, 2);
                                        break;
                                }
                            }
                        },
                        5,
                        2132083805));
        final int i2 = 0;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_skip_login_label),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.privatespace.PrivateSpaceAccountLoginError$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateSpaceAccountLoginError f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                PrivateSpaceAccountLoginError privateSpaceAccountLoginError =
                                        this.f$0;
                                switch (i22) {
                                    case 0:
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1902,
                                                        new Pair[0]);
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1889,
                                                        false);
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceAccountLoginError)
                                                .navigate(R.id.action_skip_account_login);
                                        break;
                                    default:
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1890,
                                                        new Pair[0]);
                                        Intent intent =
                                                new Intent(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        (Class<?>)
                                                                PrivateProfileContextHelperActivity
                                                                        .class);
                                        intent.putExtra(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 2);
                                        privateSpaceAccountLoginError.mMetricsFeatureProvider
                                                .action(
                                                        privateSpaceAccountLoginError.getContext(),
                                                        1894,
                                                        new Pair[0]);
                                        privateSpaceAccountLoginError
                                                .getActivity()
                                                .startActivityForResult(intent, 2);
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
    /* renamed from: com.android.settings.privatespace.PrivateSpaceAccountLoginError$1, reason: invalid class name */
    public final class AnonymousClass1 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
