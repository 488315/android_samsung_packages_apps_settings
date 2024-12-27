package com.android.settingslib;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.EditTextPreferenceDialogFragmentCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CustomEditTextPreferenceCompat extends EditTextPreference {
    public CustomPreferenceDialogFragment mFragment;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CustomPreferenceDialogFragment
            extends EditTextPreferenceDialogFragmentCompat {
        @Override // androidx.preference.EditTextPreferenceDialogFragmentCompat,
                  // androidx.preference.PreferenceDialogFragmentCompat
        public final void onBindDialogView(View view) {
            super.onBindDialogView(view);
            ((CustomEditTextPreferenceCompat) getPreference()).onBindDialogView(view);
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat,
                  // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            this.mWhichButtonClicked = i;
            ((CustomEditTextPreferenceCompat) getPreference()).onClick(dialogInterface, i);
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat,
                  // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Dialog onCreateDialog = super.onCreateDialog(bundle);
            ((CustomEditTextPreferenceCompat) getPreference()).getClass();
            return onCreateDialog;
        }

        @Override // androidx.preference.EditTextPreferenceDialogFragmentCompat,
                  // androidx.preference.PreferenceDialogFragmentCompat
        public final void onDialogClosed(boolean z) {
            super.onDialogClosed(z);
            ((CustomEditTextPreferenceCompat) getPreference()).onDialogClosed(z);
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat
        public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            ((CustomEditTextPreferenceCompat) getPreference()).mFragment = this;
            ((CustomEditTextPreferenceCompat) getPreference())
                    .onPrepareDialogBuilder(builder, this);
        }
    }

    public CustomEditTextPreferenceCompat(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public final EditText getEditText() {
        Dialog dialog;
        CustomPreferenceDialogFragment customPreferenceDialogFragment = this.mFragment;
        if (customPreferenceDialogFragment == null
                || (dialog = customPreferenceDialogFragment.mDialog) == null) {
            return null;
        }
        return (EditText) dialog.findViewById(R.id.edit);
    }

    public void onBindDialogView(View view) {
        EditText editText = (EditText) view.findViewById(R.id.edit);
        if (editText != null) {
            editText.setInputType(16385);
            editText.requestFocus();
        }
    }

    public CustomEditTextPreferenceCompat(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomEditTextPreferenceCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
    }

    public void onDialogClosed(boolean z) {}

    public void onClick(DialogInterface dialogInterface, int i) {}

    public void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {}
}
