package com.samsung.android.settings.navigationbar;

import android.view.KeyEvent;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class NavigationBarGesturePreference$$ExternalSyntheticLambda1
        implements View.OnKeyListener {
    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == 66 || i == 160 || i == 23) {
            int action = keyEvent.getAction();
            if (action == 0) {
                view.setAlpha(0.6f);
                return true;
            }
            if (action == 1) {
                view.setAlpha(1.0f);
                view.callOnClick();
            }
        }
        return false;
    }
}
