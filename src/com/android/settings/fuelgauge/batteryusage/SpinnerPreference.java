package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settingslib.widget.SettingsSpinnerAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SpinnerPreference extends Preference {

    @VisibleForTesting String[] mItems;
    public AdapterView.OnItemSelectedListener mOnItemSelectedListener;

    @VisibleForTesting int mSavedSpinnerPosition;

    @VisibleForTesting Spinner mSpinner;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    public static class SavedState extends Preference.BaseSavedState {
        public final int mSpinnerPosition;

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.mSpinnerPosition = i;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SpinnerAdapter extends SettingsSpinnerAdapter {
        public final String[] mItems;

        public SpinnerAdapter(Context context, String[] strArr) {
            super(context);
            this.mItems = strArr;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final int getCount() {
            return this.mItems.length;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final Object getItem(int i) {
            return this.mItems[i];
        }
    }

    public SpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.preference_spinner);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        if (this.mSpinner != null) {
            return;
        }
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(R.id.spinner);
        this.mSpinner = spinner;
        spinner.setAdapter(
                (android.widget.SpinnerAdapter) new SpinnerAdapter(getContext(), this.mItems));
        this.mSpinner.setSelection(this.mSavedSpinnerPosition);
        this.mSpinner.setLongClickable(false);
        AdapterView.OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
        if (onItemSelectedListener != null) {
            this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (parcelable == absSavedState) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(absSavedState);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.mSpinnerPosition;
        this.mSavedSpinnerPosition = i;
        AdapterView.OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(null, null, i, 0L);
        }
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onRestoreInstanceState() spinnerPosition="),
                savedState.mSpinnerPosition,
                "SpinnerPreference");
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        if (this.mSpinner == null) {
            return super.onSaveInstanceState();
        }
        Log.d(
                "SpinnerPreference",
                "onSaveInstanceState() spinnerPosition=" + this.mSpinner.getSelectedItemPosition());
        return new SavedState(super.onSaveInstanceState(), this.mSpinner.getSelectedItemPosition());
    }
}
