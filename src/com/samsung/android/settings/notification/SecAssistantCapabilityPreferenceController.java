package com.samsung.android.settings.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAssistantCapabilityPreferenceController extends TogglePreferenceController {
    static final int OFF = 0;
    static final int ON = 1;
    static final String PRIORITIZER_KEY = "asst_capability_prioritizer";
    static final String RANKING_KEY = "asst_capability_ranking";
    static final String SMART_KEY = "asst_capabilities_actions_replies";
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private NotificationBackend mBackend;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.SecAssistantCapabilityPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            List list;
            ArrayList arrayList = new ArrayList();
            try {
                list =
                        NotificationBackend.sINM.getAllowedAssistantAdjustments(
                                context.getPackageName());
            } catch (Exception e) {
                Log.w("AssistantCapabilityPreferenceController", "Error calling NoMan", e);
                list = null;
            }
            if (list != null) {
                boolean z =
                        list.contains("key_contextual_actions")
                                && list.contains("key_text_replies");
                String valueOf = String.valueOf(36407);
                String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                StatusData statusData = new StatusData();
                statusData.mStatusValue = str;
                statusData.mStatusKey = valueOf;
                arrayList.add(statusData);
            }
            return arrayList;
        }
    }

    public SecAssistantCapabilityPreferenceController(Context context, String str) {
        super(context, str);
        this.mBackend = new NotificationBackend();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Utils.isDesktopModeEnabled(this.mContext) ? 5 : 0;
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
        return 36041;
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
        this.mBackend.getClass();
        ComponentName allowedNotificationAssistant =
                NotificationBackend.getAllowedNotificationAssistant();
        this.mBackend.getClass();
        return allowedNotificationAssistant != null
                && allowedNotificationAssistant.equals(
                        NotificationBackend.getDefaultNotificationAssistant());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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

    public void setBackend(NotificationBackend notificationBackend) {
        this.mBackend = notificationBackend;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        ComponentName componentName;
        LoggingHelper.insertEventLogging(getMetricsCategory(), 36407, z ? 1L : 0L);
        if (z) {
            this.mBackend.getClass();
            componentName = NotificationBackend.getDefaultNotificationAssistant();
        } else {
            componentName = null;
        }
        if (z) {
            this.mBackend.getClass();
            NotificationBackend.setNotificationAssistantGranted(componentName);
            return true;
        }
        this.mBackend.getClass();
        NotificationBackend.setNotificationAssistantGranted(null);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
