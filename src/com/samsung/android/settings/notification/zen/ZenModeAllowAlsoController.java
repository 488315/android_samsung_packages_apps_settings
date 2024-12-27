package com.samsung.android.settings.notification.zen;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ZenModeAllowAlsoController extends BasePreferenceController {
    private static final int ALLOW_ONLY_SELECTED_CONTACTS = 0;
    public static final String ZEN_MODE_PEOPLE_SETTINGS_DB_KEY =
            "zen_selected_exception_contacts_allowed";
    private Context mContext;
    private int mCurrentAllowState;
    private boolean mIsFromDnd;
    private PreferenceCategory mPreference;
    private String mPreferenceKey;
    private final BixbyRoutineActionHandler mRSHandler;

    public ZenModeAllowAlsoController(Context context, String str, boolean z) {
        super(context, str);
        this.mCurrentAllowState = 0;
        this.mContext = context;
        this.mPreferenceKey = str;
        this.mIsFromDnd = z;
        this.mRSHandler = BixbyRoutineActionHandler.getInstance();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (preferenceScreen != null) {
            this.mPreference =
                    (PreferenceCategory) preferenceScreen.findPreference(this.mPreferenceKey);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mIsFromDnd) {
            this.mCurrentAllowState =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(),
                            "zen_selected_exception_contacts_allowed",
                            0);
        } else {
            this.mCurrentAllowState = (int) BixbyRoutineActionHandler.getContactFlag();
        }
        if (this.mCurrentAllowState == 0) {
            preference.setVisible(true);
        } else {
            preference.setVisible(false);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
