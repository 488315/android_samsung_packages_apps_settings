package com.android.settings;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreferenceDialogFragmentCompat;
import androidx.preference.SecListPreference;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class CustomListPreference extends SecListPreference {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmDialogFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return FileType.JSON;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.P.mMessage = getArguments().getCharSequence("android.intent.extra.TEXT");
            builder.setPositiveButton(
                    R.string.ok,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.CustomListPreference.ConfirmDialogFragment.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            Fragment targetFragment =
                                    ConfirmDialogFragment.this.getTargetFragment();
                            if (targetFragment != null) {
                                CustomListPreferenceDialogFragment
                                        customListPreferenceDialogFragment =
                                                (CustomListPreferenceDialogFragment) targetFragment;
                                customListPreferenceDialogFragment.onClick(
                                        customListPreferenceDialogFragment.mDialog, -1);
                                customListPreferenceDialogFragment.mDialog.dismiss();
                            }
                        }
                    });
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CustomListPreferenceDialogFragment
            extends ListPreferenceDialogFragmentCompat {
        public int mClickedDialogEntryIndex;

        public DialogInterface.OnClickListener getOnItemClickListener() {
            return new DialogInterface
                    .OnClickListener() { // from class:
                                         // com.android.settings.CustomListPreference.CustomListPreferenceDialogFragment.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CharSequence[] charSequenceArr;
                    CustomListPreferenceDialogFragment.this.setClickedDialogEntryIndex(i);
                    ((CustomListPreference) CustomListPreferenceDialogFragment.this.getPreference())
                            .getClass();
                    CustomListPreferenceDialogFragment customListPreferenceDialogFragment =
                            CustomListPreferenceDialogFragment.this;
                    CustomListPreference customListPreference =
                            (CustomListPreference)
                                    customListPreferenceDialogFragment.getPreference();
                    CustomListPreference customListPreference2 =
                            (CustomListPreference)
                                    customListPreferenceDialogFragment.getPreference();
                    int i2 = customListPreferenceDialogFragment.mClickedDialogEntryIndex;
                    if (i2 >= 0 && (charSequenceArr = customListPreference2.mEntryValues) != null) {
                        charSequenceArr[i2].toString();
                    }
                    customListPreference.getClass();
                    customListPreferenceDialogFragment.onClick(
                            customListPreferenceDialogFragment.mDialog, -1);
                    customListPreferenceDialogFragment.mDialog.dismiss();
                }
            };
        }

        @Override // androidx.fragment.app.Fragment
        public final void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            ((CustomListPreference) getPreference()).getClass();
        }

        @Override // androidx.preference.PreferenceDialogFragmentCompat,
                  // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Dialog onCreateDialog = super.onCreateDialog(bundle);
            if (bundle != null) {
                this.mClickedDialogEntryIndex =
                        bundle.getInt(
                                "settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX",
                                this.mClickedDialogEntryIndex);
            }
            ((CustomListPreference) getPreference()).onDialogCreated((AlertDialog) onCreateDialog);
            return onCreateDialog;
        }

        @Override // androidx.preference.ListPreferenceDialogFragmentCompat,
                  // androidx.preference.PreferenceDialogFragmentCompat
        public final void onDialogClosed(boolean z) {
            CharSequence[] charSequenceArr;
            ((CustomListPreference) getPreference()).getClass();
            CustomListPreference customListPreference = (CustomListPreference) getPreference();
            CustomListPreference customListPreference2 = (CustomListPreference) getPreference();
            int i = this.mClickedDialogEntryIndex;
            String charSequence =
                    (i < 0 || (charSequenceArr = customListPreference2.mEntryValues) == null)
                            ? null
                            : charSequenceArr[i].toString();
            if (z
                    && charSequence != null
                    && customListPreference.callChangeListener(charSequence)) {
                customListPreference.setValue(charSequence);
            }
        }

        @Override // androidx.preference.ListPreferenceDialogFragmentCompat,
                  // androidx.preference.PreferenceDialogFragmentCompat
        public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            super.onPrepareDialogBuilder(builder);
            this.mClickedDialogEntryIndex =
                    ((CustomListPreference) getPreference())
                            .findIndexOfValue(((CustomListPreference) getPreference()).mValue);
            ((CustomListPreference) getPreference())
                    .onPrepareDialogBuilder(builder, getOnItemClickListener());
            ((CustomListPreference) getPreference()).getClass();
        }

        @Override // androidx.preference.ListPreferenceDialogFragmentCompat,
                  // androidx.preference.PreferenceDialogFragmentCompat,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putInt(
                    "settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX",
                    this.mClickedDialogEntryIndex);
        }

        public void setClickedDialogEntryIndex(int i) {
            this.mClickedDialogEntryIndex = i;
        }
    }

    public CustomListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public CustomListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void onDialogCreated(AlertDialog alertDialog) {}

    public void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {}
}
