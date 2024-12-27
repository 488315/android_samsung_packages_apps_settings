package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingIconDescriptionController extends BasePreferenceController
        implements FloatingIconDependentControllerFieldListener {
    private final Context mContext;
    private SecUnclickablePreference mPreference;

    public FloatingIconDescriptionController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecUnclickablePreference) preferenceScreen.findPreference(getPreferenceKey());
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
        super.updateState(preference);
        int intForUser =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "notification_bubbles", 1, -2);
        if (intForUser == -1) {
            intForUser = 1;
        }
        if (intForUser == 0) {
            this.mPreference.setVisible(false);
            return;
        }
        if (intForUser == 1) {
            this.mPreference.setVisible(true);
            this.mPreference.setTitle(
                    this.mContext
                            .getResources()
                            .getText(R.string.notifications_floating_bubble_description));
        } else if (intForUser != 2) {
            this.mPreference.setVisible(false);
        } else {
            this.mPreference.setVisible(true);
            this.mPreference.setTitle(
                    this.mContext
                            .getResources()
                            .getText(R.string.notifications_floating_popup_description));
        }
    }

    @Override // com.samsung.android.settings.notification.FloatingIconDependentControllerFieldListener
    public void updateValues(int i) {
        if (i == -1) {
            i = 1;
        }
        if (i == 0) {
            this.mPreference.setVisible(false);
            return;
        }
        if (i == 1) {
            this.mPreference.setVisible(true);
            this.mPreference.setTitle(
                    this.mContext
                            .getResources()
                            .getText(R.string.notifications_floating_bubble_description));
        } else if (i != 2) {
            this.mPreference.setVisible(false);
        } else {
            this.mPreference.setVisible(true);
            this.mPreference.setTitle(
                    this.mContext
                            .getResources()
                            .getText(R.string.notifications_floating_popup_description));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
