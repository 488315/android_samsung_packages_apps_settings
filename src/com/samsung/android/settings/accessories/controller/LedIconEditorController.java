package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.accessories.AccessoriesUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LedIconEditorController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private static final String KEY_LED_ICON_EDITOR = "led_icon_editor";
    public static final String PACKAGE_NAME_LED_VIEW = "com.samsung.android.app.ledcoverdream";
    private SecPreferenceScreen mPreference;

    public LedIconEditorController(Context context) {
        this(context, KEY_LED_ICON_EDITOR);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_LED_ICON_EDITOR);
        if (UserHandle.myUserId() == 0) {
            this.mPreference.setEnabled(true);
        } else {
            this.mPreference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!UsefulfeatureUtils.isCoverVerified(this.mContext)
                        || !AccessoriesUtils.hasCoverSettingLEDCover(this.mContext)
                        || SemPersonaManager.isKnoxId(UserHandle.myUserId())
                        || Utils.isTablet())
                ? 3
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mPreference)) {
            if (Utils.hasPackage(this.mContext, PACKAGE_NAME_LED_VIEW)) {
                Intent intent = new Intent();
                if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0)
                        < 31) {
                    intent.setAction("com.samsung.android.app.ledcover.action.LAUNCH");
                } else {
                    intent.setClassName(
                            PACKAGE_NAME_LED_VIEW,
                            "com.samsung.android.app.ledcover.app.editor.LedIconEditorActivity");
                }
                intent.setFlags(268435456);
                if (Utils.isIntentAvailable(this.mContext, intent)) {
                    this.mContext.startActivity(intent);
                }
            } else {
                Context context = this.mContext;
                AccessoriesUtils.showDownloadLedAppDialog(
                        context,
                        context.getString(R.string.sview_led_cover_icon_editor),
                        PACKAGE_NAME_LED_VIEW);
            }
        }
        return super.handlePreferenceTreeClick(preference);
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public LedIconEditorController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
