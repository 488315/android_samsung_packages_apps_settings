package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopupListController extends BasePreferenceController {
    private static final String DB_NAME = "edge_lighting";
    public static final String EDGE_LIGHTING_ENABLE_APP_LIST = "edge_lighting_enable_app_list";
    private static final String KEY_BRIEF_POPUP_LIST = "apps_to_view_as_brief_popup";
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private static final String TAG = "BriefPopupListController";
    private final String IS_ALL_APPS_AVAILABLE;
    private Context mContext;
    PreferenceScreen mParentScreen;
    private SecPreferenceScreen mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.BriefPopupListController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            String num;
            ArrayList arrayList = new ArrayList();
            String[] strArr = {new NotificationBackend().getEdgeLightingEnabledList(context)};
            int i = 0;
            boolean equals = "AllAppsAvailable".equals(strArr[0]);
            String str = strArr[0];
            if (str == null) {
                num = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            } else if (equals) {
                num = "all";
            } else {
                num =
                        Integer.toString(
                                !ApnSettings.MVNO_NONE.equals(strArr[0])
                                        ? str.split(",").length
                                        : 0);
            }
            String valueOf = String.valueOf(36102);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = num;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf2 = String.valueOf(36103);
            String valueOf3 = String.valueOf(equals ? 1 : 0);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            try {
                i = NotificationBackend.sINM.getBlockedAppCount(UserHandle.myUserId());
            } catch (Exception e) {
                Log.w("NotificationBackend", "Error calling ", e);
            }
            String valueOf4 = String.valueOf(36201);
            String valueOf5 = String.valueOf(i);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = valueOf5;
            statusData3.mStatusKey = valueOf4;
            arrayList.add(statusData3);
            return arrayList;
        }
    }

    public BriefPopupListController(Context context, String str) {
        super(context, KEY_BRIEF_POPUP_LIST);
        this.IS_ALL_APPS_AVAILABLE = "AllAppsAvailable";
        this.mContext = context;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mParentScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                DB_NAME,
                                !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                        == 0
                ? 2
                : 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_BRIEF_POPUP_LIST;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.sesl_primary_dark_color_dark));
        }
        String[] strArr = {new NotificationBackend().getEdgeLightingEnabledList(this.mContext)};
        String str = strArr[0];
        if (str == null) {
            return ApnSettings.MVNO_NONE;
        }
        if ("AllAppsAvailable".equals(str)) {
            return this.mContext.getString(R.string.sec_edge_lighting_enable_app_list_all);
        }
        String[] split = strArr[0].split(",");
        int length = !ApnSettings.MVNO_NONE.equals(strArr[0]) ? split.length : 0;
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("edgelighting enabled applist : "), strArr[0], TAG);
        return length != 0
                ? length != 1
                        ? length != 2
                                ? this.mContext.getString(
                                        R.string.sec_edge_lighting_enable_app_list_other,
                                        split[0],
                                        split[1],
                                        Integer.valueOf(split.length - 2))
                                : this.mContext.getString(
                                        R.string.sec_edge_lighting_enable_app_list_two,
                                        split[0],
                                        split[1])
                        : this.mContext.getString(
                                R.string.sec_edge_lighting_enable_app_list_one, split[0])
                : this.mContext.getString(R.string.sec_edge_lighting_enable_app_list_zero);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean getSummaryOnBackground() {
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(KEY_BRIEF_POPUP_LIST)) {
            return false;
        }
        SALogging.insertSALog(String.valueOf(36013), "NSTE0002");
        return false;
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
    public /* bridge */ /* synthetic */ boolean isSliceable() {
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

    public void updateVisible() {
        setVisible(
                this.mParentScreen,
                getPreferenceKey(),
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                DB_NAME,
                                !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                        == 1);
        if (this.mPreference.isVisible()) {
            refreshSummary(this.mPreference);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
