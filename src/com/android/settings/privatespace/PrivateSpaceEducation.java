package com.android.settings.privatespace;

import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceEducation extends InstrumentedFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2058;
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
                                R.layout.private_space_education_screen, viewGroup, false);
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        final int i = 1;
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_setup_button_label),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.privatespace.PrivateSpaceEducation$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateSpaceEducation f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                PrivateSpaceEducation privateSpaceEducation = this.f$0;
                                switch (i2) {
                                    case 0:
                                        FragmentActivity activity =
                                                privateSpaceEducation.getActivity();
                                        if (activity != null) {
                                            privateSpaceEducation.mMetricsFeatureProvider.action(
                                                    privateSpaceEducation.getContext(),
                                                    1885,
                                                    new Pair[0]);
                                            Log.i(
                                                    "PrivateSpaceEducation",
                                                    "private space setup cancelled");
                                            activity.finish();
                                            break;
                                        }
                                        break;
                                    default:
                                        privateSpaceEducation.mMetricsFeatureProvider.action(
                                                privateSpaceEducation.getContext(),
                                                1884,
                                                new Pair[0]);
                                        Log.i(
                                                "PrivateSpaceEducation",
                                                "Starting private space setup");
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceEducation)
                                                .navigate(R.id.action_education_to_create);
                                        break;
                                }
                            }
                        },
                        5,
                        2132083805));
        final int i2 = 0;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getContext().getString(R.string.private_space_cancel_label),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.privatespace.PrivateSpaceEducation$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrivateSpaceEducation f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                PrivateSpaceEducation privateSpaceEducation = this.f$0;
                                switch (i22) {
                                    case 0:
                                        FragmentActivity activity =
                                                privateSpaceEducation.getActivity();
                                        if (activity != null) {
                                            privateSpaceEducation.mMetricsFeatureProvider.action(
                                                    privateSpaceEducation.getContext(),
                                                    1885,
                                                    new Pair[0]);
                                            Log.i(
                                                    "PrivateSpaceEducation",
                                                    "private space setup cancelled");
                                            activity.finish();
                                            break;
                                        }
                                        break;
                                    default:
                                        privateSpaceEducation.mMetricsFeatureProvider.action(
                                                privateSpaceEducation.getContext(),
                                                1884,
                                                new Pair[0]);
                                        Log.i(
                                                "PrivateSpaceEducation",
                                                "Starting private space setup");
                                        NavHostFragment.Companion.findNavController(
                                                        privateSpaceEducation)
                                                .navigate(R.id.action_education_to_create);
                                        break;
                                }
                            }
                        },
                        2,
                        2132083806),
                false);
        LottieColorUtils.applyDynamicColors(
                getContext(), (LottieAnimationView) glifLayout.findViewById(R.id.lottie_animation));
        TextView textView = (TextView) glifLayout.findViewById(R.id.learn_more);
        Linkify.addLinks(
                textView,
                Pattern.compile(textView.getText().toString()),
                getContext().getString(R.string.private_space_learn_more_url));
        return glifLayout;
    }
}
