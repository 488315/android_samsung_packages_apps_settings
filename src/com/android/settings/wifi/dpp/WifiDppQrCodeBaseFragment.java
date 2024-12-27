package com.android.settings.wifi.dpp;

import android.R;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.core.InstrumentedFragment;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiDppQrCodeBaseFragment extends InstrumentedFragment {
    public GlifLayout mGlifLayout;
    public FooterButton mLeftButton;
    public FooterButton mRightButton;
    public TextView mSummary;

    public abstract boolean isFooterAvailable();

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        this.mGlifLayout = (GlifLayout) view;
        this.mSummary = (TextView) view.findViewById(R.id.summary);
        if (isFooterAvailable()) {
            getContext();
            this.mLeftButton = new FooterButton(ApnSettings.MVNO_NONE, null, 2, 2132083806);
            ((FooterBarMixin) this.mGlifLayout.getMixin(FooterBarMixin.class))
                    .setSecondaryButton(this.mLeftButton, false);
            getContext();
            this.mRightButton = new FooterButton(ApnSettings.MVNO_NONE, null, 5, 2132083805);
            ((FooterBarMixin) this.mGlifLayout.getMixin(FooterBarMixin.class))
                    .setPrimaryButton(this.mRightButton);
        }
        this.mGlifLayout.getHeaderTextView().setAccessibilityLiveRegion(1);
    }

    public final void setHeaderIconImageResource(int i) {
        Drawable drawable;
        GlifLayout glifLayout = this.mGlifLayout;
        try {
            drawable = getContext().getDrawable(i);
        } catch (Resources.NotFoundException unused) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Resource does not exist: ", "WifiDppQrCodeBaseFragment");
            drawable = null;
        }
        glifLayout.setIcon(drawable);
    }

    public final void setHeaderTitle(int i, Object... objArr) {
        this.mGlifLayout.setHeaderText(getString(i, objArr));
    }

    public final void setProgressBarShown(boolean z) {
        this.mGlifLayout.setProgressBarShown(z);
    }
}
