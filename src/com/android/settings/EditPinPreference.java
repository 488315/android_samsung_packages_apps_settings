package com.android.settings;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.android.settings.IccLockSettings.ChangeIccLockPin;
import com.android.settings.IccLockSettings.SetIccLockEnabled;
import com.android.settingslib.CustomEditTextPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
class EditPinPreference extends CustomEditTextPreferenceCompat {
    public IccLockSettings mPinListener;

    public EditPinPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = (EditText) view.findViewById(R.id.edit);
        if (editText != null) {
            editText.setInputType(18);
            editText.setTextAlignment(5);
        }
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public final void onDialogClosed(boolean z) {
        IccLockSettings iccLockSettings = this.mPinListener;
        if (iccLockSettings != null) {
            if (!z) {
                iccLockSettings.resetDialogState();
                return;
            }
            String str = this.mText;
            iccLockSettings.mPin = str;
            if (str == null || str.length() < 4 || str.length() > 8) {
                iccLockSettings.mError =
                        iccLockSettings.mRes.getString(R.string.sim_invalid_pin_hint);
                iccLockSettings.showPinDialog$1();
                return;
            }
            int i = iccLockSettings.mDialogState;
            if (i == 1) {
                iccLockSettings
                        .new SetIccLockEnabled(iccLockSettings.mToState, iccLockSettings.mPin)
                        .execute(new Void[0]);
                iccLockSettings.mPinToggle.setEnabled(false);
                return;
            }
            if (i == 2) {
                iccLockSettings.mOldPin = iccLockSettings.mPin;
                iccLockSettings.mDialogState = 3;
                iccLockSettings.mError = null;
                iccLockSettings.mPin = null;
                iccLockSettings.showPinDialog$1();
                return;
            }
            if (i == 3) {
                iccLockSettings.mNewPin = iccLockSettings.mPin;
                iccLockSettings.mDialogState = 4;
                iccLockSettings.mPin = null;
                iccLockSettings.showPinDialog$1();
                return;
            }
            if (i != 4) {
                return;
            }
            if (iccLockSettings.mPin.equals(iccLockSettings.mNewPin)) {
                iccLockSettings.mError = null;
                iccLockSettings
                        .new ChangeIccLockPin(iccLockSettings.mOldPin, iccLockSettings.mNewPin)
                        .execute(new Void[0]);
            } else {
                iccLockSettings.mError =
                        iccLockSettings.mRes.getString(R.string.sim_pins_dont_match);
                iccLockSettings.mDialogState = 3;
                iccLockSettings.mPin = null;
                iccLockSettings.showPinDialog$1();
            }
        }
    }

    public EditPinPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
