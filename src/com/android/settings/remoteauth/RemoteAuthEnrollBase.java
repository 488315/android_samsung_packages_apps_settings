package com.android.settings.remoteauth;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settingslib.Utils;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b'\u0018\u00002\u00020\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settings/remoteauth/RemoteAuthEnrollBase;",
            "Landroidx/fragment/app/Fragment;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public abstract class RemoteAuthEnrollBase extends Fragment {
    public final int glifLayoutId;
    public final Lazy primaryFooterButton$delegate;
    public final Lazy secondaryFooterButton$delegate;

    public RemoteAuthEnrollBase(int i) {
        super(i);
        this.glifLayoutId = R.id.setup_wizard_layout;
        this.primaryFooterButton$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.RemoteAuthEnrollBase$primaryFooterButton$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return RemoteAuthEnrollBase.this.initializePrimaryFooterButton();
                            }
                        });
        this.secondaryFooterButton$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.RemoteAuthEnrollBase$secondaryFooterButton$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return RemoteAuthEnrollBase.this.initializeSecondaryFooterButton();
                            }
                        });
    }

    public final FooterButton getPrimaryFooterButton() {
        return (FooterButton) this.primaryFooterButton$delegate.getValue();
    }

    public abstract FooterButton initializePrimaryFooterButton();

    public abstract FooterButton initializeSecondaryFooterButton();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View onCreateView = super.onCreateView(inflater, viewGroup, bundle);
        Intrinsics.checkNotNull(onCreateView);
        GlifLayout glifLayout = (GlifLayout) onCreateView.findViewById(this.glifLayoutId);
        if (glifLayout == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        footerBarMixin.setPrimaryButton(getPrimaryFooterButton());
        FooterButton footerButton = (FooterButton) this.secondaryFooterButton$delegate.getValue();
        if (footerButton != null) {
            footerBarMixin.setSecondaryButton(footerButton, false);
        }
        LinearLayout buttonContainer = footerBarMixin.getButtonContainer();
        ColorStateList colorAttr =
                Utils.getColorAttr(getContext(), android.R.attr.windowBackground);
        buttonContainer.setBackgroundColor(colorAttr != null ? colorAttr.getDefaultColor() : 0);
        return onCreateView;
    }
}
