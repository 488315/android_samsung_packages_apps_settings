package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LedIndicatorPreferenceController extends TogglePreferenceController {
    private static final String KEY_LED_INDICATOR = "key_simple_led_indicator_settings";
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.LedIndicatorPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        public Context statusContext;

        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            this.statusContext = context;
            ArrayList arrayList = new ArrayList();
            boolean z =
                    Settings.System.getInt(
                                            this.statusContext.getContentResolver(),
                                            "led_indicator_charing",
                                            1)
                                    == 1
                            && Settings.System.getInt(
                                            this.statusContext.getContentResolver(),
                                            "led_indicator_low_battery",
                                            1)
                                    == 1
                            && Settings.System.getInt(
                                            this.statusContext.getContentResolver(),
                                            "led_indicator_missed_event",
                                            1)
                                    == 1
                            && Settings.System.getInt(
                                            this.statusContext.getContentResolver(),
                                            "led_indicator_voice_recording",
                                            1)
                                    == 1;
            String valueOf = String.valueOf(36410);
            String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        anonymousClass1.statusContext = null;
        STATUS_LOGGING_PROVIDER = anonymousClass1;
    }

    public LedIndicatorPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean getLedIndicator() {
        return Settings.System.getInt(
                                this.mContext.getContentResolver(), "led_indicator_charing", 1)
                        == 1
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), "led_indicator_low_battery", 1)
                        == 1
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), "led_indicator_missed_event", 1)
                        == 1
                && Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "led_indicator_voice_recording",
                                1)
                        == 1;
    }

    private void setLedIndicator(boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "led_indicator_charing", z ? 1 : 0);
        Settings.System.putInt(
                this.mContext.getContentResolver(), "led_indicator_low_battery", z ? 1 : 0);
        Settings.System.putInt(
                this.mContext.getContentResolver(), "led_indicator_missed_event", z ? 1 : 0);
        Settings.System.putInt(
                this.mContext.getContentResolver(), "led_indicator_voice_recording", z ? 1 : 0);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!Rune.supportLedIndicator() || SemPersonaManager.isKnoxId(UserHandle.myUserId()))
                ? 3
                : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 4050;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_LED_INDICATOR;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return getLedIndicator();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        setLedIndicator(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
