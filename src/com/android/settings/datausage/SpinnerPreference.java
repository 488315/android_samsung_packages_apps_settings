package com.android.settings.datausage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SpinnerPreference extends Preference implements CycleAdapter.SpinnerInterface {
    public CycleAdapter mAdapter;
    public Object mCurrentObject;
    public View mItemView;
    public boolean mItemViewVisible;
    public AdapterView.OnItemSelectedListener mListener;
    public final AnonymousClass1 mOnSelectedListener;
    public int mPosition;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.datausage.SpinnerPreference$1] */
    public SpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItemViewVisible = false;
        this.mOnSelectedListener =
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.android.settings.datausage.SpinnerPreference.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view, int i, long j) {
                        SpinnerPreference spinnerPreference = SpinnerPreference.this;
                        spinnerPreference.mPosition = i;
                        spinnerPreference.mCurrentObject = spinnerPreference.mAdapter.getItem(i);
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                SpinnerPreference.this.mListener;
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelected(adapterView, view, i, j);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                SpinnerPreference.this.mListener;
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onNothingSelected(adapterView);
                        }
                    }
                };
        setLayoutResource(R.layout.sec_data_usage_cycles);
    }

    @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
    public final Object getSelectedItem() {
        return this.mCurrentObject;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        this.mItemView = view;
        view.setVisibility(this.mItemViewVisible ? 0 : 4);
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(R.id.cycles_spinner);
        spinner.setAdapter((SpinnerAdapter) this.mAdapter);
        spinner.setSelection(this.mPosition);
        spinner.setOnItemSelectedListener(this.mOnSelectedListener);
    }

    @Override // androidx.preference.Preference
    public final void performClick(View view) {
        view.findViewById(R.id.cycles_spinner).performClick();
    }

    @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
    public final void setAdapter(CycleAdapter cycleAdapter) {
        this.mAdapter = cycleAdapter;
        notifyChanged();
    }

    @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
    public final void setOnItemSelectedListener(
            AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mListener = onItemSelectedListener;
    }

    @Override // com.android.settings.datausage.CycleAdapter.SpinnerInterface
    public final void setSelection(int i) {
        this.mPosition = i;
        this.mCurrentObject = this.mAdapter.getItem(i);
        notifyChanged();
    }
}
