package com.android.settingslib;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PrimarySwitchPreference$$ExternalSyntheticLambda1
        implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return motionEvent.getActionMasked() == 2;
    }
}
