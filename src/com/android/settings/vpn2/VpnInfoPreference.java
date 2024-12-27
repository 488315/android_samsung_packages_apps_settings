package com.android.settings.vpn2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VpnInfoPreference extends RestrictedPreference implements View.OnClickListener {
    public final String mHelpUrl;
    public boolean mIsInsecureVpn;

    public VpnInfoPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsInsecureVpn = false;
        this.mHelpUrl = context.getString(R.string.help_url_insecure_vpn);
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_warning;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.warning_button);
        if (!this.mIsInsecureVpn || TextUtils.isEmpty(this.mHelpUrl)) {
            findViewById.setVisibility(8);
            findViewById.setOnClickListener(this);
            findViewById.setEnabled(false);
        } else {
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(this);
            findViewById.setEnabled(true);
        }
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(8);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Intent helpIntent;
        if (view.getId() != R.id.warning_button
                || (helpIntent =
                                HelpUtils.getHelpIntent(
                                        getContext(), this.mHelpUrl, getClass().getName()))
                        == null) {
            return;
        }
        ((Activity) getContext()).startActivityForResult(helpIntent, 0);
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return false;
    }
}
