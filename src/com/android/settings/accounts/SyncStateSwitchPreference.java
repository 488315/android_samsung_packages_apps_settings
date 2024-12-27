package com.android.settings.accounts;

import android.accounts.Account;
import android.app.ActivityManager;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settingslib.widget.AnimatedImageView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SyncStateSwitchPreference extends SecSwitchPreference {
    public Account mAccount;
    public String mAuthority;
    public boolean mOneTimeSyncMode;
    public String mPackageName;
    public int mUid;

    public SyncStateSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, R.style.SyncSwitchPreference);
        this.mOneTimeSyncMode = false;
        this.mAccount = null;
        this.mAuthority = null;
        this.mPackageName = null;
        this.mUid = 0;
    }

    @Override // androidx.preference.SecSwitchPreference,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        AnimatedImageView animatedImageView =
                (AnimatedImageView) preferenceViewHolder.findViewById(R.id.sync_active);
        View findViewById = preferenceViewHolder.findViewById(R.id.sync_failed);
        preferenceViewHolder.mDividerAllowedBelow = true;
        preferenceViewHolder.mDividerAllowedAbove = true;
        animatedImageView.setVisibility(8);
        findViewById.setVisibility(8);
        View findViewById2 = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        if (!this.mOneTimeSyncMode) {
            findViewById2.setVisibility(0);
        } else {
            findViewById2.setVisibility(8);
            ((TextView) preferenceViewHolder.findViewById(android.R.id.summary))
                    .setText(getContext().getString(R.string.sync_one_time_sync, getSummary()));
        }
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        if (this.mOneTimeSyncMode) {
            return;
        }
        if (ActivityManager.isUserAMonkey()) {
            Log.d("SyncState", "ignoring monkey's attempt to flip sync state");
        } else {
            super.onClick();
        }
    }
}
