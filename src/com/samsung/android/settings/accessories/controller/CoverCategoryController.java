package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CoverCategoryController extends BasePreferenceController {
    private static final String FEATURE_LED_FLIP_SUITCASE =
            "com.sec.feature.nfc_suitled_authentication_cover";
    private static final String KEY_COVER = "cover";
    private SecPreferenceCategory mPreference;

    public CoverCategoryController(Context context) {
        this(context, KEY_COVER);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) preferenceScreen.findPreference(KEY_COVER);
        this.mPreference = secPreferenceCategory;
        if (secPreferenceCategory != null) {
            if (Utils.isTablet()) {
                this.mPreference.setTitle(R.string.book_cover);
                return;
            }
            int typeOfCover = UsefulfeatureUtils.getTypeOfCover(this.mContext);
            if (typeOfCover == 0) {
                this.mPreference.setTitle(R.string.sview_category_flip_wallet);
                return;
            }
            if (typeOfCover == 7) {
                this.mPreference.setTitle(R.string.sview_category_led_cover);
                return;
            }
            if (typeOfCover == 8 || typeOfCover == 15) {
                this.mPreference.setTitle(R.string.sview_category_clear_cover);
                return;
            }
            if (typeOfCover == 11) {
                this.mPreference.setTitle(R.string.sview_category_led_neon_flip_cover);
                return;
            }
            if (typeOfCover == 14) {
                this.mPreference.setTitle(R.string.led_back_cover_title);
            } else if (typeOfCover == 16) {
                this.mPreference.setTitle(R.string.sview_category_wallet_cover);
            } else {
                this.mPreference.setTitle(R.string.cover);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UsefulfeatureUtils.isCoverVerified(this.mContext)
                && !SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
            return (UsefulfeatureUtils.getTypeOfCover(this.mContext) == 14
                            && this.mContext
                                    .getPackageManager()
                                    .hasSystemFeature(FEATURE_LED_FLIP_SUITCASE))
                    ? 3
                    : 1;
        }
        return 3;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public CoverCategoryController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
