package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeMediaPreferenceController extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public final ZenModeBackend mBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeMediaPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean allowMedia =
                    ((NotificationManager) context.getSystemService("notification"))
                            .getNotificationPolicy()
                            .allowMedia();
            String valueOf = String.valueOf(36309);
            String str = allowMedia ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    public ZenModeMediaPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "zen_mode_media", lifecycle);
        this.mBackend = ZenModeBackend.getInstance(context);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_media";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (ZenModeSettingsBase.DEBUG) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "onPrefChange allowMedia=", "PrefControllerMixin", booleanValue);
        }
        this.mBackend.saveSoundPolicy(64, booleanValue);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        int zenMode = getZenMode();
        if (zenMode == 2) {
            twoStatePreference.setEnabled(false);
            twoStatePreference.setChecked(false);
        } else if (zenMode != 3) {
            twoStatePreference.setEnabled(true);
            twoStatePreference.setChecked(this.mBackend.isPriorityCategoryEnabled(64));
        } else {
            twoStatePreference.setEnabled(false);
            twoStatePreference.setChecked(true);
        }
    }
}
