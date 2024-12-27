package com.android.settings.privatespace;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.fragment.NavHostFragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.sec.ims.ImsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceSetLockFragment extends InstrumentedFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2061;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (!Flags.allowPrivateProfile() || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            return null;
        }
        GlifLayout glifLayout =
                (GlifLayout)
                        layoutInflater.inflate(
                                R.layout.private_space_setlock_screen, viewGroup, false);
        glifLayout.getHeaderTextView().setBreakStrategy(0);
        glifLayout.getHeaderTextView().setMaxLines(4);
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        final int i = 1;
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_set_lock_label),
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.privatespace.PrivateSpaceSetLockFragment$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateSpaceSetLockFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                PrivateSpaceSetLockFragment privateSpaceSetLockFragment = this.f$0;
                                switch (i2) {
                                    case 0:
                                        privateSpaceSetLockFragment.mMetricsFeatureProvider.action(
                                                privateSpaceSetLockFragment.getContext(),
                                                1892,
                                                new Pair[0]);
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceSetLockFragment)
                                                .navigate(R.id.action_pre_finish_delay_fragment);
                                        break;
                                    default:
                                        privateSpaceSetLockFragment.mMetricsFeatureProvider.action(
                                                privateSpaceSetLockFragment.getContext(),
                                                1891,
                                                new Pair[0]);
                                        UserHandle privateProfileHandle =
                                                PrivateSpaceMaintainer.getInstance(
                                                                privateSpaceSetLockFragment
                                                                        .getActivity())
                                                        .getPrivateProfileHandle();
                                        if (privateProfileHandle == null) {
                                            Log.w(
                                                    "PrivateSpaceSetLockFrag",
                                                    "Private profile user handle is null");
                                            break;
                                        } else {
                                            Intent intent =
                                                    new Intent(
                                                            privateSpaceSetLockFragment
                                                                    .getContext(),
                                                            (Class<?>)
                                                                    PrivateProfileContextHelperActivity
                                                                            .class);
                                            intent.putExtra(
                                                    ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 1);
                                            Log.i(
                                                    "PrivateSpaceSetLockFrag",
                                                    "Start separate lock setup for private"
                                                        + " profile");
                                            privateSpaceSetLockFragment
                                                    .getActivity()
                                                    .startActivityForResultAsUser(
                                                            intent, 1, privateProfileHandle);
                                            break;
                                        }
                                }
                            }
                        },
                        5,
                        2132083805));
        final int i2 = 0;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_use_screenlock_label),
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.privatespace.PrivateSpaceSetLockFragment$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateSpaceSetLockFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                PrivateSpaceSetLockFragment privateSpaceSetLockFragment = this.f$0;
                                switch (i22) {
                                    case 0:
                                        privateSpaceSetLockFragment.mMetricsFeatureProvider.action(
                                                privateSpaceSetLockFragment.getContext(),
                                                1892,
                                                new Pair[0]);
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceSetLockFragment)
                                                .navigate(R.id.action_pre_finish_delay_fragment);
                                        break;
                                    default:
                                        privateSpaceSetLockFragment.mMetricsFeatureProvider.action(
                                                privateSpaceSetLockFragment.getContext(),
                                                1891,
                                                new Pair[0]);
                                        UserHandle privateProfileHandle =
                                                PrivateSpaceMaintainer.getInstance(
                                                                privateSpaceSetLockFragment
                                                                        .getActivity())
                                                        .getPrivateProfileHandle();
                                        if (privateProfileHandle == null) {
                                            Log.w(
                                                    "PrivateSpaceSetLockFrag",
                                                    "Private profile user handle is null");
                                            break;
                                        } else {
                                            Intent intent =
                                                    new Intent(
                                                            privateSpaceSetLockFragment
                                                                    .getContext(),
                                                            (Class<?>)
                                                                    PrivateProfileContextHelperActivity
                                                                            .class);
                                            intent.putExtra(
                                                    ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, 1);
                                            Log.i(
                                                    "PrivateSpaceSetLockFrag",
                                                    "Start separate lock setup for private"
                                                        + " profile");
                                            privateSpaceSetLockFragment
                                                    .getActivity()
                                                    .startActivityForResultAsUser(
                                                            intent, 1, privateProfileHandle);
                                            break;
                                        }
                                }
                            }
                        },
                        7,
                        2132083806),
                false);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new AnonymousClass1(true));
        LottieColorUtils.applyDynamicColors(
                getContext(), (LottieAnimationView) glifLayout.findViewById(R.id.lottie_animation));
        return glifLayout;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privatespace.PrivateSpaceSetLockFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
