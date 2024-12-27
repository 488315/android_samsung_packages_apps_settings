package com.android.settingslib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CustomDialogPreferenceCompat extends DialogPreference {
    public CustomPreferenceDialogFragment mFragment;
    public DialogInterface.OnShowListener mOnShowListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CustomPreferenceDialogFragment extends PreferenceDialogFragmentCompat {
        @Override // androidx.preference.PreferenceDialogFragmentCompat
        public final void onBindDialogView(View view) {
            super.onBindDialogView(view);
            ((CustomDialogPreferenceCompat) getPreference()).onBindDialogView(view);
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat,
                  // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            this.mWhichButtonClicked = i;
            ((CustomDialogPreferenceCompat) getPreference()).onClick(dialogInterface, i);
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat,
                  // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Dialog onCreateDialog = super.onCreateDialog(bundle);
            onCreateDialog.setOnShowListener(
                    ((CustomDialogPreferenceCompat) getPreference()).mOnShowListener);
            return onCreateDialog;
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat
        public final void onDialogClosed(boolean z) {
            ((CustomDialogPreferenceCompat) getPreference()).onDialogClosed(z);
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat
        public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            ((CustomDialogPreferenceCompat) getPreference()).mFragment = this;
            ((CustomDialogPreferenceCompat) getPreference()).onPrepareDialogBuilder(builder, this);
        }
    }

    public CustomDialogPreferenceCompat(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public final Dialog getDialog() {
        CustomPreferenceDialogFragment customPreferenceDialogFragment = this.mFragment;
        if (customPreferenceDialogFragment != null) {
            return customPreferenceDialogFragment.mDialog;
        }
        return null;
    }

    public CustomDialogPreferenceCompat(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomDialogPreferenceCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
    }

    public CustomDialogPreferenceCompat(Context context) {
        super(context, null);
    }

    public void onBindDialogView(View view) {}

    public void onDialogClosed(boolean z) {}

    public void onClick(DialogInterface dialogInterface, int i) {}

    public void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {}
}
