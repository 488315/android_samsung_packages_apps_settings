package com.android.settingslib.widget;

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
public class SettingsSpinnerPreference extends Preference
        implements Preference.OnPreferenceClickListener {
    public SettingsSpinnerAdapter mAdapter;
    public AdapterView.OnItemSelectedListener mListener;
    public final AnonymousClass1 mOnSelectedListener;
    public int mPosition;
    public boolean mShouldPerformClick;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.widget.SettingsSpinnerPreference$1] */
    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnSelectedListener =
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.android.settingslib.widget.SettingsSpinnerPreference.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view, int i2, long j) {
                        SettingsSpinnerPreference settingsSpinnerPreference =
                                SettingsSpinnerPreference.this;
                        if (settingsSpinnerPreference.mPosition == i2) {
                            return;
                        }
                        settingsSpinnerPreference.mPosition = i2;
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                settingsSpinnerPreference.mListener;
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelected(adapterView, view, i2, j);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                SettingsSpinnerPreference.this.mListener;
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onNothingSelected(adapterView);
                        }
                    }
                };
        setLayoutResource(R.layout.settings_spinner_preference);
        setOnPreferenceClickListener(this);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(R.id.spinner);
        spinner.setAdapter((SpinnerAdapter) this.mAdapter);
        spinner.setSelection(this.mPosition);
        spinner.setOnItemSelectedListener(this.mOnSelectedListener);
        if (this.mShouldPerformClick) {
            this.mShouldPerformClick = false;
            spinner.performClick();
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        this.mShouldPerformClick = true;
        notifyChanged();
        return true;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.widget.SettingsSpinnerPreference$1] */
    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnSelectedListener =
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.android.settingslib.widget.SettingsSpinnerPreference.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view, int i2, long j) {
                        SettingsSpinnerPreference settingsSpinnerPreference =
                                SettingsSpinnerPreference.this;
                        if (settingsSpinnerPreference.mPosition == i2) {
                            return;
                        }
                        settingsSpinnerPreference.mPosition = i2;
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                settingsSpinnerPreference.mListener;
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelected(adapterView, view, i2, j);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                SettingsSpinnerPreference.this.mListener;
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onNothingSelected(adapterView);
                        }
                    }
                };
        setLayoutResource(R.layout.settings_spinner_preference);
        setOnPreferenceClickListener(this);
    }

    public SettingsSpinnerPreference(Context context) {
        this(context, null);
    }
}
