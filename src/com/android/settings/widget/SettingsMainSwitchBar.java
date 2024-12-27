package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.appcompat.util.SeslMisc;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;

import com.samsung.android.settings.widget.SecMainSwitchBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsMainSwitchBar extends SecMainSwitchBar {
    public boolean mDisabledByAdmin;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public int mMetricsCategory;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public OnBeforeCheckedChangeListener mOnBeforeListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnBeforeCheckedChangeListener {
        boolean onBeforeCheckedChanged(boolean z);
    }

    public SettingsMainSwitchBar(Context context) {
        this(context, null);
    }

    @Override // com.samsung.android.settings.widget.SecMainSwitchBar
    public final void onBackgroundClicked() {
        if (!this.mDisabledByAdmin) {
            super.onBackgroundClicked();
        } else {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getContext(), this.mEnforcedAdmin);
            this.mMetricsFeatureProvider.clicked(this.mMetricsCategory, "switch_bar|restricted");
        }
    }

    @Override // androidx.appcompat.widget.SeslSwitchBar, android.view.View
    public final boolean performClick() {
        if (!this.mDisabledByAdmin) {
            return ((SecMainSwitchBar) this).mSwitch.performClick();
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(getContext(), this.mEnforcedAdmin);
        this.mMetricsFeatureProvider.clicked(this.mMetricsCategory, "switch_bar|restricted");
        return true;
    }

    @Override // androidx.appcompat.widget.SeslSwitchBar
    public void setChecked(boolean z) {
        OnBeforeCheckedChangeListener onBeforeCheckedChangeListener = this.mOnBeforeListener;
        if (onBeforeCheckedChangeListener == null
                || !onBeforeCheckedChangeListener.onBeforeCheckedChanged(z)) {
            super.setChecked(z);
        }
    }

    public void setCheckedInternal(boolean z) {
        super.setChecked(z);
    }

    public void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        this.mEnforcedAdmin = enforcedAdmin;
        if (enforcedAdmin == null) {
            this.mDisabledByAdmin = false;
            ((SecMainSwitchBar) this).mSwitch.setVisibility(0);
            setEnabled(isEnabled());
            return;
        }
        super.setEnabled(true);
        this.mDisabledByAdmin = true;
        ((SecMainSwitchBar) this).mTextView.setEnabled(false);
        if (SeslMisc.isLightTheme(getContext()) && ((SecMainSwitchBar) this).mSwitch.isChecked()) {
            ((SecMainSwitchBar) this).mTextView.setAlpha(0.55f);
        } else {
            ((SecMainSwitchBar) this).mTextView.setAlpha(0.4f);
        }
        ((SecMainSwitchBar) this).mSwitch.setEnabled(false);
    }

    @Override // androidx.appcompat.widget.SeslSwitchBar, android.view.View
    public void setEnabled(boolean z) {
        if (z && this.mDisabledByAdmin) {
            setDisabledByAdmin(null);
        } else {
            super.setEnabled(z);
        }
    }

    public void setMetricsCategory(int i) {
        this.mMetricsCategory = i;
    }

    public void setOnBeforeCheckedChangeListener(
            OnBeforeCheckedChangeListener onBeforeCheckedChangeListener) {
        this.mOnBeforeListener = onBeforeCheckedChangeListener;
    }

    public SettingsMainSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seslSwitchBarStyle);
    }

    public SettingsMainSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SettingsMainSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
            addOnSwitchChangeListener(
                    new CompoundButton
                            .OnCheckedChangeListener() { // from class:
                                                         // com.android.settings.widget.SettingsMainSwitchBar$$ExternalSyntheticLambda0
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(
                                CompoundButton compoundButton, boolean z) {
                            SettingsMainSwitchBar settingsMainSwitchBar =
                                    SettingsMainSwitchBar.this;
                            settingsMainSwitchBar.mMetricsFeatureProvider.changed(
                                    settingsMainSwitchBar.mMetricsCategory,
                                    z ? 1 : 0,
                                    "switch_bar");
                        }
                    });
            return;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }
}
