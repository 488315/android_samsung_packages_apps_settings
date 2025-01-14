package com.android.settings;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.utils.ColorUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class RestrictedRadioButton extends RadioButton {
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;

    public RestrictedRadioButton(Context context) {
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
                        .setAlpha((int) (ColorUtil.getDisabledAlpha(this.mContext) * 255.0f));
            } else {
                getButtonDrawable().setAlpha(0);
            }
        }
    }

    public RestrictedRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.radioButtonStyle);
    }

    public RestrictedRadioButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedRadioButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
    }
}
