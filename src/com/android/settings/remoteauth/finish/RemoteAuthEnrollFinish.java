package com.android.settings.remoteauth.finish;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.fragment.NavHostFragment;

import com.android.settings.R;
import com.android.settings.remoteauth.RemoteAuthEnrollBase;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterButton;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/remoteauth/finish/RemoteAuthEnrollFinish;",
            "Lcom/android/settings/remoteauth/RemoteAuthEnrollBase;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RemoteAuthEnrollFinish extends RemoteAuthEnrollBase {
    public RemoteAuthEnrollFinish() {
        super(R.layout.remote_auth_enroll_finish);
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase
    public final FooterButton initializePrimaryFooterButton() {
        return new FooterButton(
                requireContext()
                        .getString(R.string.security_settings_remoteauth_enroll_finish_btn_next),
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.remoteauth.finish.RemoteAuthEnrollFinish$initializePrimaryFooterButton$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View p0) {
                        Intrinsics.checkNotNullParameter(p0, "p0");
                        RemoteAuthEnrollFinish remoteAuthEnrollFinish = RemoteAuthEnrollFinish.this;
                        remoteAuthEnrollFinish.getClass();
                        NavHostFragment.Companion.findNavController(remoteAuthEnrollFinish)
                                .navigate(R.id.action_finish_to_settings);
                    }
                },
                5,
                2132083805);
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase
    public final FooterButton initializeSecondaryFooterButton() {
        return null;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        LottieColorUtils.applyDynamicColors(
                requireContext(),
                (LottieAnimationView) view.findViewById(R.id.enroll_finish_animation));
    }
}
