package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.CustomEditTextPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ValidatedEditTextPreference extends CustomEditTextPreferenceCompat {
    public boolean mIsPassword;
    public boolean mIsSummaryPassword;
    public final EditTextWatcher mTextWatcher;
    public Validator mValidator;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Validator {
        boolean isTextValid(String str);
    }

    public ValidatedEditTextPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTextWatcher = new EditTextWatcher();
    }

    public boolean isPassword() {
        return this.mIsPassword;
    }

    @Override // com.android.settingslib.CustomEditTextPreferenceCompat
    public void onBindDialogView(View view) {
        super.onBindDialogView(view);
        EditText editText = (EditText) view.findViewById(R.id.edit);
        if (editText != null && !TextUtils.isEmpty(editText.getText())) {
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText);
        }
        if (this.mValidator == null || editText == null) {
            return;
        }
        editText.removeTextChangedListener(this.mTextWatcher);
        if (this.mIsPassword) {
            editText.setInputType(145);
            editText.setMaxLines(1);
        }
        editText.addTextChangedListener(this.mTextWatcher);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        if (textView == null) {
            return;
        }
        if (this.mIsSummaryPassword) {
            textView.setInputType(129);
        } else {
            textView.setInputType(524289);
        }
    }

    public ValidatedEditTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextWatcher = new EditTextWatcher();
    }

    public ValidatedEditTextPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextWatcher = new EditTextWatcher();
    }

    public ValidatedEditTextPreference(Context context) {
        super(context, null);
        this.mTextWatcher = new EditTextWatcher();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EditTextWatcher implements TextWatcher {
        public EditTextWatcher() {}

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            EditText editText = ValidatedEditTextPreference.this.getEditText();
            ValidatedEditTextPreference validatedEditTextPreference =
                    ValidatedEditTextPreference.this;
            Validator validator = validatedEditTextPreference.mValidator;
            if (validator == null || editText == null) {
                return;
            }
            CustomEditTextPreferenceCompat.CustomPreferenceDialogFragment
                    customPreferenceDialogFragment =
                            ((CustomEditTextPreferenceCompat) validatedEditTextPreference)
                                    .mFragment;
            ((AlertDialog)
                            (customPreferenceDialogFragment != null
                                    ? customPreferenceDialogFragment.mDialog
                                    : null))
                    .getButton(-1)
                    .setEnabled(validator.isTextValid(editText.getText().toString()));
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}
