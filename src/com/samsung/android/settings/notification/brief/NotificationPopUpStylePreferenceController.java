package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationPopUpStylePreferenceController
        extends SecSingleChoicePreferenceController {
    private static final String BRIEF = "brief";
    private static final String DB_NAME = "edge_lighting";
    private static final String DETAIL = "detailed";
    private static final String POPUP_BRIEF = "0";
    private static final String POPUP_DETAIL = "1";
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    private static final String TAG = "NotiPopUpStyle";
    private SecHorizontalRadioPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.NotificationPopUpStylePreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean z =
                    Settings.System.getInt(
                                    context.getContentResolver(),
                                    NotificationPopUpStylePreferenceController.DB_NAME,
                                    !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                            == 1;
            String valueOf = String.valueOf(36101);
            String str = z ? "1" : "0";
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    public NotificationPopUpStylePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecHorizontalRadioPreference secHorizontalRadioPreference =
                (SecHorizontalRadioPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secHorizontalRadioPreference;
        secHorizontalRadioPreference.mIsDividerEnabled = false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "minimal_battery_use", 0)
                        == 1
                ? 5
                : 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntries() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(this.mContext.getString(R.string.sec_notification_popup_brief));
        arrayList.add(this.mContext.getString(R.string.sec_notification_popup_detail));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("0");
        arrayList.add("1");
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getImageEntries() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(R.drawable.noti_popup_brief));
        arrayList.add(Integer.valueOf(R.drawable.noti_popup_detail));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public String getSelectedValue() {
        return Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                DB_NAME,
                                !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                        == 1
                ? "0"
                : "1";
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getSubEntries() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        boolean equals = "0".equals(str);
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(), DB_NAME, equals ? 1 : 0, -2);
        HashMap hashMap = new HashMap();
        hashMap.put("type", equals ? BRIEF : DETAIL);
        SALogging.insertSALog(Integer.toString(36013), "NSTE0001", hashMap, 0);
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        SecHorizontalRadioPreference secHorizontalRadioPreference =
                (SecHorizontalRadioPreference) preference;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "minimal_battery_use", 0)
                == 1) {
            secHorizontalRadioPreference.setEnabled(false);
            secHorizontalRadioPreference.setEntryEnabled("0", false);
            secHorizontalRadioPreference.setEntryEnabled("1", false);
        } else {
            secHorizontalRadioPreference.setEnabled(true);
            secHorizontalRadioPreference.setEntryEnabled("0", true);
            secHorizontalRadioPreference.setEntryEnabled("1", true);
        }
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
