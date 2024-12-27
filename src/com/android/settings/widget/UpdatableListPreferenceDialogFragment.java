package com.android.settings.widget;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

import com.android.internal.R;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UpdatableListPreferenceDialogFragment extends PreferenceDialogFragmentCompat
        implements Instrumentable {
    public ArrayAdapter mAdapter;
    public int mClickedDialogEntryIndex;
    public ArrayList mEntries;
    public CharSequence[] mEntryValues;
    public int mMetricsCategory = 0;

    public static UpdatableListPreferenceDialogFragment newInstance(int i, String str) {
        UpdatableListPreferenceDialogFragment updatableListPreferenceDialogFragment =
                new UpdatableListPreferenceDialogFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, str);
        bundle.putInt("metrics_category_key", i);
        updatableListPreferenceDialogFragment.setArguments(bundle);
        return updatableListPreferenceDialogFragment;
    }

    public ArrayAdapter getAdapter() {
        return this.mAdapter;
    }

    public ListPreference getListPreference() {
        return (ListPreference) getPreference();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mMetricsCategory;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mMetricsCategory = getArguments().getInt("metrics_category_key", 0);
        if (bundle == null) {
            this.mEntries = new ArrayList();
            setPreferenceData(getListPreference());
        } else {
            this.mClickedDialogEntryIndex =
                    bundle.getInt("UpdatableListPreferenceDialogFragment.index", 0);
            this.mEntries =
                    bundle.getCharSequenceArrayList(
                            "UpdatableListPreferenceDialogFragment.entries");
            this.mEntryValues =
                    bundle.getCharSequenceArray(
                            "UpdatableListPreferenceDialogFragment.entryValues");
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onDialogClosed(boolean z) {
        if (!z || this.mClickedDialogEntryIndex < 0) {
            return;
        }
        ListPreference listPreference = getListPreference();
        String charSequence = this.mEntryValues[this.mClickedDialogEntryIndex].toString();
        if (listPreference.callChangeListener(charSequence)) {
            listPreference.setValue(charSequence);
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(
                                null, R.styleable.AlertDialog, android.R.attr.alertDialogStyle, 0);
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(
                        getContext(),
                        obtainStyledAttributes.getResourceId(
                                21, android.R.layout.select_dialog_singlechoice),
                        this.mEntries);
        this.mAdapter = arrayAdapter;
        builder.setSingleChoiceItems(
                arrayAdapter,
                this.mClickedDialogEntryIndex,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.widget.UpdatableListPreferenceDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        UpdatableListPreferenceDialogFragment
                                updatableListPreferenceDialogFragment =
                                        UpdatableListPreferenceDialogFragment.this;
                        updatableListPreferenceDialogFragment.mClickedDialogEntryIndex = i;
                        updatableListPreferenceDialogFragment.onClick(dialogInterface, -1);
                        dialogInterface.dismiss();
                    }
                });
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("UpdatableListPreferenceDialogFragment.index", this.mClickedDialogEntryIndex);
        bundle.putCharSequenceArrayList(
                "UpdatableListPreferenceDialogFragment.entries", this.mEntries);
        bundle.putCharSequenceArray(
                "UpdatableListPreferenceDialogFragment.entryValues", this.mEntryValues);
    }

    public void setAdapter(ArrayAdapter arrayAdapter) {
        this.mAdapter = arrayAdapter;
    }

    public void setEntries(ArrayList<CharSequence> arrayList) {
        this.mEntries = arrayList;
    }

    public void setMetricsCategory(Bundle bundle) {
        this.mMetricsCategory = bundle.getInt("metrics_category_key", 0);
    }

    public final void setPreferenceData(ListPreference listPreference) {
        this.mEntries.clear();
        this.mClickedDialogEntryIndex = listPreference.findIndexOfValue(listPreference.mValue);
        for (CharSequence charSequence : listPreference.mEntries) {
            this.mEntries.add(charSequence);
        }
        this.mEntryValues = listPreference.mEntryValues;
    }
}
