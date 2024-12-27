package com.android.settings.wifi.calling;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settings.CustomListPreference;
import com.android.settings.R;
import com.android.settings.R$styleable;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ListWithEntrySummaryPreference extends CustomListPreference {
    public final Context mContext;
    public CharSequence[] mSummaries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SelectorAdapter extends ArrayAdapter {
        public final Context mContext;
        public final ListWithEntrySummaryPreference mSelector;

        public SelectorAdapter(
                Context context, ListWithEntrySummaryPreference listWithEntrySummaryPreference) {
            super(
                    context,
                    R.xml.single_choice_list_item_2,
                    listWithEntrySummaryPreference.mEntryValues);
            this.mContext = context;
            this.mSelector = listWithEntrySummaryPreference;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            CharSequence charSequence;
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.xml.single_choice_list_item_2, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.title)).setText(this.mSelector.mEntries[i]);
            TextView textView = (TextView) inflate.findViewById(R.id.summary);
            CharSequence[] charSequenceArr = this.mSelector.mSummaries;
            if (charSequenceArr == null) {
                Log.w("ListWithEntrySummaryPreference", "getEntrySummary : mSummaries is null");
                charSequence = ApnSettings.MVNO_NONE;
            } else {
                charSequence = charSequenceArr[i];
            }
            textView.setText(charSequence);
            RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.radio);
            ListWithEntrySummaryPreference listWithEntrySummaryPreference = this.mSelector;
            if (i
                    == listWithEntrySummaryPreference.findIndexOfValue(
                            listWithEntrySummaryPreference.mValue)) {
                radioButton.setChecked(true);
            }
            return inflate;
        }
    }

    public ListWithEntrySummaryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.ListWithEntrySummaryPreference, 0, 0);
        this.mSummaries = obtainStyledAttributes.getTextArray(0);
        obtainStyledAttributes.recycle();
    }

    @Override // com.android.settings.CustomListPreference
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        builder.setSingleChoiceItems(
                new SelectorAdapter(this.mContext, this),
                findIndexOfValue(this.mValue),
                onClickListener);
    }

    @Override // androidx.preference.ListPreference, androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mEntries = savedState.mEntries;
        this.mEntryValues = savedState.mEntryValues;
        this.mSummaries = savedState.mSummaries;
    }

    @Override // androidx.preference.ListPreference, androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mEntries = this.mEntries;
        savedState.mEntryValues = this.mEntryValues;
        savedState.mSummaries = this.mSummaries;
        return savedState;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public CharSequence[] mEntries;
        public CharSequence[] mEntryValues;
        public CharSequence[] mSummaries;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.wifi.calling.ListWithEntrySummaryPreference$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mEntries = parcel.readCharSequenceArray();
            this.mEntryValues = parcel.readCharSequenceArray();
            this.mSummaries = parcel.readCharSequenceArray();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeCharSequenceArray(this.mEntries);
            parcel.writeCharSequenceArray(this.mEntryValues);
            parcel.writeCharSequenceArray(this.mSummaries);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
