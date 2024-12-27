package com.google.android.material.chip;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SeslExpansionButton extends ImageView {
    public boolean mAutoDisappear;
    public boolean mExpanded;
    public boolean mFloated;
    public final AnonymousClass1 mTimer;

    /* JADX WARN: Type inference failed for: r6v0, types: [com.google.android.material.chip.SeslExpansionButton$1] */
    public SeslExpansionButton(Context context) {
        super(context, null, -1);
        this.mAutoDisappear = false;
        setElevation(getResources().getDimension(R.dimen.expansion_button_elevation));
        long integer = context.getResources().getInteger(R.integer.expansion_button_duration);
        this.mTimer =
                new CountDownTimer(
                        integer,
                        integer) { // from class:
                                   // com.google.android.material.chip.SeslExpansionButton.1
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        SeslExpansionButton seslExpansionButton = SeslExpansionButton.this;
                        if (seslExpansionButton.mAutoDisappear
                                && seslExpansionButton.getVisibility() == 0) {
                            SeslExpansionButton.this.setVisibility(4);
                        }
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j) {}
                };
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (this.mExpanded) {
            ImageView.mergeDrawableStates(
                    onCreateDrawableState, new int[] {R.attr.state_expansion_button_expanded});
        }
        if (this.mFloated) {
            ImageView.mergeDrawableStates(
                    onCreateDrawableState, new int[] {R.attr.state_expansion_button_floated});
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.ImageView, android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            cancel();
            start();
        }
    }
}
