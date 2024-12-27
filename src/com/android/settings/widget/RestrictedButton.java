package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedButton extends Button {
    public RestrictedButton(Context context) {
        super(context);
    }

    private RestrictedLockUtils.EnforcedAdmin getEnforcedAdmin() {
        return null;
    }

    @Override // android.view.View
    public final boolean performClick() {
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = getEnforcedAdmin();
        if (enforcedAdmin == null) {
            return super.performClick();
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                ((Button) this).mContext, enforcedAdmin);
        return false;
    }

    public RestrictedButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RestrictedButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RestrictedButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
