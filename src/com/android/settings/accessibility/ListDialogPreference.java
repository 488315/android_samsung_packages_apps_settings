package com.android.settings.accessibility;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settingslib.CustomDialogPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ListDialogPreference extends CustomDialogPreferenceCompat {
    public CharSequence[] mEntryTitles;
    public int[] mEntryValues;
    public int mListItemLayout;
    public OnValueChangedListener mOnValueChangedListener;
    public int mValue;
    public int mValueIndex;
    public boolean mValueSet;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ListPreferenceAdapter extends BaseAdapter {
        public LayoutInflater mInflater;

        public ListPreferenceAdapter() {}

        @Override // android.widget.Adapter
        public final int getCount() {
            return ListDialogPreference.this.mEntryValues.length;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return Integer.valueOf(ListDialogPreference.this.mEntryValues[i]);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return ListDialogPreference.this.mEntryValues[i];
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                if (this.mInflater == null) {
                    this.mInflater = LayoutInflater.from(viewGroup.getContext());
                }
                view =
                        this.mInflater.inflate(
                                ListDialogPreference.this.mListItemLayout, viewGroup, false);
            }
            ListDialogPreference.this.onBindListItem(view, i);
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnValueChangedListener {
        void onValueChanged(ListDialogPreference listDialogPreference, int i);
    }

    public ListDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final int getIndexForValue(int i) {
        int[] iArr = this.mEntryValues;
        if (iArr == null) {
            return -1;
        }
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    @Override // androidx.preference.Preference
    public CharSequence getSummary() {
        int i = this.mValueIndex;
        if (i >= 0) {
            return getTitleAt(i);
        }
        return null;
    }

    public CharSequence getTitleAt(int i) {
        CharSequence[] charSequenceArr = this.mEntryTitles;
        if (charSequenceArr == null || charSequenceArr.length <= i) {
            return null;
        }
        return charSequenceArr[i];
    }

    public abstract void onBindListItem(View view, int i);

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        Context context = getContext();
        View inflate =
                LayoutInflater.from(context).inflate(this.mDialogLayoutResId, (ViewGroup) null);
        ListPreferenceAdapter listPreferenceAdapter = new ListPreferenceAdapter();
        AbsListView absListView = (AbsListView) inflate.findViewById(R.id.list);
        absListView.setAdapter((ListAdapter) listPreferenceAdapter);
        absListView.setOnItemClickListener(
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.android.settings.accessibility.ListDialogPreference.1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i, long j) {
                        int i2 = (int) j;
                        if (ListDialogPreference.this.callChangeListener(Integer.valueOf(i2))) {
                            ListDialogPreference.this.setValue(i2);
                        }
                        Dialog dialog = ListDialogPreference.this.getDialog();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
        int indexForValue = getIndexForValue(this.mValue);
        if (indexForValue != -1) {
            absListView.setSelection(indexForValue);
        }
        builder.setView(inflate);
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null);
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValue(savedState.value);
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.value = this.mValue;
        return savedState;
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj, boolean z) {
        setValue(z ? getPersistedInt(this.mValue) : ((Integer) obj).intValue());
    }

    public final void setValue(int i) {
        boolean z = this.mValue != i;
        if (z || !this.mValueSet) {
            this.mValue = i;
            this.mValueIndex = getIndexForValue(i);
            this.mValueSet = true;
            persistInt(i);
            if (z) {
                notifyDependencyChange(shouldDisableDependents$1());
                notifyChanged();
            }
            OnValueChangedListener onValueChangedListener = this.mOnValueChangedListener;
            if (onValueChangedListener != null) {
                onValueChangedListener.onValueChanged(this, i);
            }
        }
    }

    public final void setValues(int[] iArr) {
        this.mEntryValues = iArr;
        if (this.mValueSet && this.mValueIndex == -1) {
            this.mValueIndex = getIndexForValue(this.mValue);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public int value;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.accessibility.ListDialogPreference$SavedState$1, reason: invalid class name */
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
            this.value = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.value);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
