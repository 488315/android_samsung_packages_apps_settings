package com.android.settings.widget;

import android.content.Context;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreferenceHelper;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsMainSwitchPreference extends TwoStatePreference
        implements CompoundButton.OnCheckedChangeListener {
    public final List mBeforeCheckedChangeListeners;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public SettingsMainSwitchBar mMainSwitchBar;
    public int mOffTextId;
    public int mOnTextId;
    public RestrictedPreferenceHelper mRestrictedHelper;
    public String mSessionName;
    public final List mSwitchChangeListeners;

    public SettingsMainSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mBeforeCheckedChangeListeners = new ArrayList();
        this.mSwitchChangeListeners = new ArrayList();
        this.mOnTextId = R.string.switch_on_text;
        this.mOffTextId = R.string.switch_off_text;
        init$3(context, attributeSet);
    }

    public final void addOnSwitchChangeListener(
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (!((ArrayList) this.mSwitchChangeListeners).contains(onCheckedChangeListener)) {
            ((ArrayList) this.mSwitchChangeListeners).add(onCheckedChangeListener);
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(onCheckedChangeListener);
        }
    }

    public final void init$3(Context context, AttributeSet attributeSet) {
        setLayoutResource(R.layout.preference_widget_main_switch);
        this.mSwitchChangeListeners.add(this);
        if (attributeSet != null) {
            this.mRestrictedHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        RestrictedPreferenceHelper restrictedPreferenceHelper;
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        if (this.mEnforcedAdmin == null
                && (restrictedPreferenceHelper = this.mRestrictedHelper) != null) {
            String str = restrictedPreferenceHelper.mAttrUserRestriction;
            this.mEnforcedAdmin =
                    str == null
                            ? null
                            : RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                    restrictedPreferenceHelper.mContext,
                                    UserHandle.myUserId(),
                                    str);
        }
        SettingsMainSwitchBar settingsMainSwitchBar =
                (SettingsMainSwitchBar) preferenceViewHolder.findViewById(R.id.main_switch_bar);
        this.mMainSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setSwitchBarText(this.mOnTextId, this.mOffTextId);
            String str2 = this.mSessionName;
            if (str2 != null) {
                this.mMainSwitchBar.setSessionDescription(str2);
            }
            this.mMainSwitchBar.setDisabledByAdmin(this.mEnforcedAdmin);
        }
        if (!isVisible()) {
            this.mMainSwitchBar.hide();
            return;
        }
        this.mMainSwitchBar.show();
        boolean isChecked = ((SeslSwitchBar) this.mMainSwitchBar).mSwitch.isChecked();
        boolean z = this.mChecked;
        if (isChecked != z) {
            setChecked(z);
        }
        Iterator it = ((ArrayList) this.mBeforeCheckedChangeListeners).iterator();
        while (it.hasNext()) {
            this.mMainSwitchBar.setOnBeforeCheckedChangeListener(
                    (SettingsMainSwitchBar.OnBeforeCheckedChangeListener) it.next());
        }
        Iterator it2 = ((ArrayList) this.mSwitchChangeListeners).iterator();
        while (it2.hasNext()) {
            this.mMainSwitchBar.addOnSwitchChangeListener(
                    (CompoundButton.OnCheckedChangeListener) it2.next());
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        super.setChecked(z);
        SettingsJankMonitor.detectToggleJank(compoundButton, getKey());
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        super.setChecked(z);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
        }
    }

    public final void setSwitchBarEnabled$1(boolean z) {
        setEnabled(z);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setEnabled(z);
        }
    }

    @Override // androidx.preference.Preference
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setTitle(charSequence);
        }
    }

    public SettingsMainSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBeforeCheckedChangeListeners = new ArrayList();
        this.mSwitchChangeListeners = new ArrayList();
        this.mOnTextId = R.string.switch_on_text;
        this.mOffTextId = R.string.switch_off_text;
        init$3(context, attributeSet);
    }

    public SettingsMainSwitchPreference(Context context) {
        super(context, null);
        this.mBeforeCheckedChangeListeners = new ArrayList();
        this.mSwitchChangeListeners = new ArrayList();
        this.mOnTextId = R.string.switch_on_text;
        this.mOffTextId = R.string.switch_off_text;
        init$3(context, null);
    }

    public SettingsMainSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBeforeCheckedChangeListeners = new ArrayList();
        this.mSwitchChangeListeners = new ArrayList();
        this.mOnTextId = R.string.switch_on_text;
        this.mOffTextId = R.string.switch_off_text;
        init$3(context, attributeSet);
    }
}
