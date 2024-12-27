package com.samsung.android.settings.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TopLevelLayoutFactory implements LayoutInflater.Factory2 {
    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(
            View view, String str, Context context, AttributeSet attributeSet) {
        try {
            return ((LayoutInflater) context.getSystemService("layout_inflater"))
                    .createView(str, null, attributeSet);
        } catch (Exception e) {
            Log.e("TopLevelLayoutFactory", e.getMessage());
            return null;
        }
    }
}
