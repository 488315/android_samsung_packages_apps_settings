package com.samsung.android.settings.display;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecDarkModeSetupWizardActivity$ViewHolder$$ExternalSyntheticLambda1
        implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            view.setAlpha(0.6f);
            return false;
        }
        if (action == 1) {
            view.setAlpha(1.0f);
            view.performClick();
            return false;
        }
        if (action != 3) {
            return false;
        }
        view.setAlpha(1.0f);
        return false;
    }
}
