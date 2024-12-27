package com.android.settings.development.featureflags;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.preference.SwitchPreferenceCompat;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FeatureFlagPreference extends SwitchPreferenceCompat {
    public final boolean mIsPersistent;
    public final String mKey;

    public FeatureFlagPreference(Context context, String str) {
        super(context);
        boolean isEnabled;
        this.mKey = str;
        setKey(str);
        setTitle(str);
        boolean contains = FeatureFlagPersistent.PERSISTENT_FLAGS.contains(str);
        this.mIsPersistent = contains;
        if (contains) {
            String str2 = SystemProperties.get("persist.sys.fflag.override." + str);
            isEnabled =
                    !TextUtils.isEmpty(str2)
                            ? Boolean.parseBoolean(str2)
                            : FeatureFlagUtils.isEnabled(context, str);
        } else {
            isEnabled = FeatureFlagUtils.isEnabled(context, str);
        }
        super.setChecked(isEnabled);
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        super.setChecked(z);
        if (!this.mIsPersistent) {
            FeatureFlagUtils.setEnabled(getContext(), this.mKey, z);
            return;
        }
        Context context = getContext();
        String str = this.mKey;
        HashSet hashSet = FeatureFlagPersistent.PERSISTENT_FLAGS;
        SystemProperties.set(
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        "persist.sys.fflag.override.", str),
                z ? "true" : "false");
        FeatureFlagUtils.setEnabled(context, str, z);
    }
}
