package com.android.settings.remoteauth.introduction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.android.settings.R;
import com.android.settings.remoteauth.RemoteAuthEnrollBase;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.template.RequireScrollMixin;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
            "Lcom/android/settings/remoteauth/introduction/RemoteAuthEnrollIntroduction;",
            "Lcom/android/settings/remoteauth/RemoteAuthEnrollBase;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RemoteAuthEnrollIntroduction extends RemoteAuthEnrollBase {
    public final Lazy navController$delegate;

    public RemoteAuthEnrollIntroduction() {
        super(R.layout.remote_auth_enroll_introduction);
        this.navController$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.RemoteAuthEnrollIntroduction$navController$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return NavHostFragment.Companion.findNavController(
                                        RemoteAuthEnrollIntroduction.this);
                            }
                        });
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase
    public final FooterButton initializePrimaryFooterButton() {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return new FooterButton(
                context.getString(R.string.security_settings_remoteauth_enroll_introduction_agree),
                new RemoteAuthEnrollIntroduction$initializeRequireScrollMixin$1(this, 1),
                6,
                2132083805);
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase
    public final FooterButton initializeSecondaryFooterButton() {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return new FooterButton(
                context.getString(
                        R.string.security_settings_remoteauth_enroll_introduction_disagree),
                new RemoteAuthEnrollIntroduction$initializeRequireScrollMixin$1(this, 2),
                5,
                2132083805);
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase,
              // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View onCreateView = super.onCreateView(inflater, viewGroup, bundle);
        GlifLayout glifLayout = (GlifLayout) onCreateView.findViewById(this.glifLayoutId);
        if (glifLayout == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        FooterButton footerButton = (FooterButton) this.secondaryFooterButton$delegate.getValue();
        if (footerButton != null) {
            footerButton.setVisibility(4);
        }
        RequireScrollMixin requireScrollMixin =
                (RequireScrollMixin) glifLayout.getMixin(RequireScrollMixin.class);
        requireScrollMixin.requireScrollWithButton(
                requireContext(),
                getPrimaryFooterButton(),
                R.string.security_settings_remoteauth_enroll_introduction_more,
                new RemoteAuthEnrollIntroduction$initializeRequireScrollMixin$1(this, 0));
        requireScrollMixin.listener =
                new RequireScrollMixin
                        .OnRequireScrollStateChangedListener() { // from class:
                                                                 // com.android.settings.remoteauth.introduction.RemoteAuthEnrollIntroduction$initializeRequireScrollMixin$2
                    @Override // com.google.android.setupdesign.template.RequireScrollMixin.OnRequireScrollStateChangedListener
                    public final void onRequireScrollStateChanged(boolean z) {
                        RemoteAuthEnrollIntroduction remoteAuthEnrollIntroduction =
                                RemoteAuthEnrollIntroduction.this;
                        if (z) {
                            remoteAuthEnrollIntroduction
                                    .getPrimaryFooterButton()
                                    .setText(
                                            remoteAuthEnrollIntroduction.requireContext(),
                                            R.string
                                                    .security_settings_remoteauth_enroll_introduction_more);
                            return;
                        }
                        remoteAuthEnrollIntroduction
                                .getPrimaryFooterButton()
                                .setText(
                                        remoteAuthEnrollIntroduction.requireContext(),
                                        R.string
                                                .security_settings_remoteauth_enroll_introduction_agree);
                        FooterButton footerButton2 =
                                (FooterButton)
                                        remoteAuthEnrollIntroduction.secondaryFooterButton$delegate
                                                .getValue();
                        if (footerButton2 == null) {
                            return;
                        }
                        footerButton2.setVisibility(0);
                    }
                };
        return onCreateView;
    }
}
