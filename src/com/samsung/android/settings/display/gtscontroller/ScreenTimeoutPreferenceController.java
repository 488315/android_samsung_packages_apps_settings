package com.samsung.android.settings.display.gtscontroller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import com.android.settings.Utils;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenTimeoutPreferenceController extends SecSingleChoicePreferenceController {
    private static final String PREFERENCE_KEY = "screen_timeout";

    public ScreenTimeoutPreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
    }

    private long getCurrentTimeout() {
        return Settings.System.getLong(
                this.mContext.getContentResolver(), "screen_off_timeout", 30000L);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!Rune.isSamsungDexMode(this.mContext)
                        || Utils.isDesktopStandaloneMode(this.mContext))
                ? 0
                : 3;
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
        CharSequence[] screenTimeoutEntryandValue =
                SecDisplayUtils.getScreenTimeoutEntryandValue(
                        this.mContext, Long.valueOf(getCurrentTimeout()), 1);
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence : screenTimeoutEntryandValue) {
            arrayList.add(charSequence.toString());
        }
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        CharSequence[] screenTimeoutEntryandValue =
                SecDisplayUtils.getScreenTimeoutEntryandValue(
                        this.mContext, Long.valueOf(getCurrentTimeout()), 2);
        ArrayList<String> arrayList = new ArrayList<>();
        for (CharSequence charSequence : screenTimeoutEntryandValue) {
            arrayList.add(charSequence.toString());
        }
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getImageEntries() {
        return null;
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
        return String.valueOf(getCurrentTimeout());
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        CharSequence timeoutDescription;
        long j =
                Settings.System.getLong(
                        this.mContext.getContentResolver(), "screen_off_timeout", 30000L);
        CharSequence[] screenTimeoutEntryandValue =
                SecDisplayUtils.getScreenTimeoutEntryandValue(this.mContext, Long.valueOf(j), 1);
        return (j < 0
                        || screenTimeoutEntryandValue == null
                        || screenTimeoutEntryandValue.length == 0
                        || (timeoutDescription =
                                        SecDisplayUtils.getTimeoutDescription(
                                                j,
                                                screenTimeoutEntryandValue,
                                                SecDisplayUtils.getScreenTimeoutEntryandValue(
                                                        this.mContext, Long.valueOf(j), 2)))
                                == null)
                ? ApnSettings.MVNO_NONE
                : timeoutDescription.toString();
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
        int parseInt = Integer.parseInt(str);
        if (parseInt <= 0) {
            return false;
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", parseInt);
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
