package com.android.settings;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class RestrictedCheckBox extends CheckBox {
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;

    public RestrictedCheckBox(Context context) {
        this(context, null);
    }

    @Override // android.widget.CompoundButton, android.view.View
    public final boolean performClick() {
        if (!this.mDisabledByAdmin) {
            return super.performClick();
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, this.mEnforcedAdmin);
        return true;
    }

    public void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        boolean z = enforcedAdmin != null;
        this.mEnforcedAdmin = enforcedAdmin;
        if (this.mDisabledByAdmin != z) {
            this.mDisabledByAdmin = z;
            RestrictedLockUtilsInternal.setTextViewAsDisabledByAdmin(this.mContext, this, z);
            if (this.mDisabledByAdmin) {
                getButtonDrawable()
                        .setColorFilter(
                                this.mContext.getColor(R.color.disabled_text_color),
                                PorterDuff.Mode.MULTIPLY);
            } else {
                getButtonDrawable().clearColorFilter();
            }
        }
    }

    public RestrictedCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }
}
