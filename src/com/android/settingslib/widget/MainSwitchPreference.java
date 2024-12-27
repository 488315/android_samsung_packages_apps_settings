package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MainSwitchPreference extends TwoStatePreference
        implements CompoundButton.OnCheckedChangeListener {
    public MainSwitchBar mMainSwitchBar;
    public final List mSwitchChangeListeners;

    public MainSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSwitchChangeListeners = new ArrayList();
        init$4(context, attributeSet);
    }

    public final void addOnSwitchChangeListener(
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (!((ArrayList) this.mSwitchChangeListeners).contains(onCheckedChangeListener)) {
            ((ArrayList) this.mSwitchChangeListeners).add(onCheckedChangeListener);
        }
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar == null
                || ((ArrayList) mainSwitchBar.mSwitchChangeListeners)
                        .contains(onCheckedChangeListener)) {
            return;
        }
        ((ArrayList) mainSwitchBar.mSwitchChangeListeners).add(onCheckedChangeListener);
    }

    public final void init$4(Context context, AttributeSet attributeSet) {
        setLayoutResource(R.layout.settingslib_main_switch_layout);
        this.mSwitchChangeListeners.add(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.Preference, 0, 0);
            setTitle(obtainStyledAttributes.getText(4));
            setIconSpaceReserved(obtainStyledAttributes.getBoolean(15, true));
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        MainSwitchBar mainSwitchBar =
                (MainSwitchBar) preferenceViewHolder.findViewById(R.id.settingslib_main_switch_bar);
        this.mMainSwitchBar = mainSwitchBar;
        mainSwitchBar.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.widget.MainSwitchPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MainSwitchPreference mainSwitchPreference = MainSwitchPreference.this;
                        mainSwitchPreference.callChangeListener(
                                Boolean.valueOf(mainSwitchPreference.mChecked));
                    }
                });
        setIconSpaceReserved(isIconSpaceReserved());
        updateStatus(this.mChecked);
        Iterator it = ((ArrayList) this.mSwitchChangeListeners).iterator();
        while (it.hasNext()) {
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener =
                    (CompoundButton.OnCheckedChangeListener) it.next();
            MainSwitchBar mainSwitchBar2 = this.mMainSwitchBar;
            if (!((ArrayList) mainSwitchBar2.mSwitchChangeListeners)
                    .contains(onCheckedChangeListener)) {
                ((ArrayList) mainSwitchBar2.mSwitchChangeListeners).add(onCheckedChangeListener);
            }
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        super.setChecked(z);
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        super.setChecked(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar == null || mainSwitchBar.mSwitch.isChecked() == z) {
            return;
        }
        this.mMainSwitchBar.setChecked(z);
    }

    @Override // androidx.preference.Preference
    public final void setIconSpaceReserved(boolean z) {
        super.setIconSpaceReserved(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null) {
            mainSwitchBar.setIconSpaceReserved(z);
        }
    }

    @Override // androidx.preference.Preference
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null) {
            mainSwitchBar.setTitle(charSequence);
        }
    }

    public final void updateStatus(boolean z) {
        setChecked(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null) {
            mainSwitchBar.setTitle(getTitle());
            MainSwitchBar mainSwitchBar2 = this.mMainSwitchBar;
            mainSwitchBar2.setVisibility(0);
            mainSwitchBar2.mSwitch.setOnCheckedChangeListener(mainSwitchBar2);
        }
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mSwitchChangeListeners = new ArrayList();
        init$4(context, attributeSet);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwitchChangeListeners = new ArrayList();
        init$4(context, attributeSet);
    }

    public MainSwitchPreference(Context context) {
        super(context, null);
        this.mSwitchChangeListeners = new ArrayList();
        init$4(context, null);
    }
}
