package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.SemUserInfo;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessories.AccessoriesUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LedBackCoverController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private static final String FEATURE_LED_FLIP_SUITCASE =
            "com.sec.feature.nfc_suitled_authentication_cover";
    private static final String KEY_LED_BACK_COVER = "led_back_cover";
    public static final String PACKAGE_NAME_LED_BACK_VIEW = "com.samsung.android.app.ledbackcover";
    private SecPreferenceScreen mPreference;

    public LedBackCoverController(Context context) {
        this(context, KEY_LED_BACK_COVER);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SemUserInfo semGetSemUserInfo;
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_LED_BACK_COVER);
        if (UserHandle.myUserId() != 0) {
            Context context = this.mContext;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (UserHandle.myUserId() == 0
                    || (semGetSemUserInfo =
                                    UserManager.get(context)
                                            .semGetSemUserInfo(UserHandle.myUserId()))
                            == null
                    || !semGetSemUserInfo.isSecondNumberMode()) {
                this.mPreference.setEnabled(false);
                return;
            }
        }
        this.mPreference.setEnabled(true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!UsefulfeatureUtils.isCoverVerified(this.mContext)
                        || Utils.isTablet()
                        || SemPersonaManager.isKnoxId(UserHandle.myUserId())
                        || UsefulfeatureUtils.getTypeOfCover(this.mContext) != 14
                        || this.mContext
                                .getPackageManager()
                                .hasSystemFeature(FEATURE_LED_FLIP_SUITCASE))
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
            if (Utils.hasPackage(this.mContext, PACKAGE_NAME_LED_BACK_VIEW)) {
                Intent intent = new Intent("com.samsung.android.app.ledbackcover.action.LAUNCH");
                intent.setFlags(268468224);
                if (Utils.isIntentAvailable(this.mContext, intent)) {
                    this.mContext.startActivity(intent);
                }
            } else {
                Context context = this.mContext;
                AccessoriesUtils.showDownloadLedAppDialog(
                        context,
                        context.getString(R.string.led_back_cover_title),
                        PACKAGE_NAME_LED_BACK_VIEW);
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

    public LedBackCoverController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
