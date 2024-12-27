package com.android.settings.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.DeviceInfoUtils;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FeedbackPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final Intent intent;
    public final Fragment mHost;

    public FeedbackPreferenceController(Context context, Fragment fragment) {
        super(context);
        this.mHost = fragment;
        this.intent = new Intent("android.intent.action.BUG_REPORT");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "device_feedback";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "device_feedback") || !isAvailable()) {
            return false;
        }
        this.mHost.startActivityForResult(this.intent, 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !TextUtils.isEmpty(DeviceInfoUtils.getFeedbackReporterPackage(this.mContext));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        this.intent.setPackage(DeviceInfoUtils.getFeedbackReporterPackage(this.mContext));
        preference.setIntent(this.intent);
        if (isAvailable() && !preference.isVisible()) {
            preference.setVisible(true);
        } else {
            if (isAvailable() || !preference.isVisible()) {
                return;
            }
            preference.setVisible(false);
        }
    }
}
