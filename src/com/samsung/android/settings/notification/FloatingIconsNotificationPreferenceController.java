package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingIconsNotificationPreferenceController extends BasePreferenceController {
    private static final int BUBBLE = 1;
    private static final int OFF = 0;
    private static final int POPUP = 2;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private final Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.FloatingIconsNotificationPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            int intForUser =
                    Settings.Secure.getIntForUser(
                            context.getContentResolver(), "notification_bubbles", 1, -2);
            if (intForUser == -1) {
                intForUser = 1;
            }
            if (intForUser == 0) {
                String valueOf = String.valueOf(36406);
                statusData = new StatusData();
                statusData.mStatusValue = "off";
                statusData.mStatusKey = valueOf;
            } else if (intForUser == 1) {
                String valueOf2 = String.valueOf(36406);
                statusData = new StatusData();
                statusData.mStatusValue = "bubbles";
                statusData.mStatusKey = valueOf2;
            } else {
                if (intForUser != 2) {
                    statusData2 = null;
                    arrayList.add(statusData2);
                    return arrayList;
                }
                String valueOf3 = String.valueOf(36406);
                statusData = new StatusData();
                statusData.mStatusValue = "smart popup view";
                statusData.mStatusKey = valueOf3;
            }
            statusData2 = statusData;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    public FloatingIconsNotificationPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Utils.isDesktopModeEnabled(this.mContext) ? 5 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        int intForUser =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "notification_bubbles", 1, -2);
        if (intForUser == -1) {
            intForUser = 1;
        }
        if (intForUser == 0) {
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getText(R.string.notification_floating_icons_off_summary));
        } else if (intForUser == 1) {
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getText(R.string.notification_floating_icons_bubble_summary));
        } else if (intForUser != 2) {
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getText(R.string.notification_floating_icons_off_summary));
        } else {
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getText(R.string.notification_floating_icons_popup_summary));
        }
        SecPreference secPreference = (SecPreference) preference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
