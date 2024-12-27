package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.secutil.Log;

import com.android.settings.core.SecCustomPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.flipfont.FontListAdapter;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecFontStylePreferenceController extends SecCustomPreferenceController {
    private static final String TAG = "SecFontStylePreferenceController";

    public SecFontStylePreferenceController(Context context) {
        super(context, "sec_font_style");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UserHandle.myUserId() != 0) {
            return 4;
        }
        return this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1 ? 5 : 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        String str;
        String currentFontName = SecDisplayUtils.getCurrentFontName(this.mContext);
        FontListAdapter instanceFontListAdapter =
                FontListAdapter.getInstanceFontListAdapter(this.mContext);
        int i = 0;
        while (true) {
            if (i >= instanceFontListAdapter.mFontNames.size()) {
                str = ApnSettings.MVNO_NONE;
                break;
            }
            if (currentFontName.equals(instanceFontListAdapter.getFontName(i))) {
                str =
                        i == 0
                                ? "default"
                                : (String) instanceFontListAdapter.mFontPackageNames.get(i);
            } else {
                i++;
            }
        }
        if (!str.isEmpty()) {
            ControlValue.Builder builder =
                    new ControlValue.Builder("sec_font_style", getControlType());
            builder.setValue(str);
            builder.mStorePackage = str;
            builder.mSummary = currentFontName;
            return builder.build();
        }
        Log.d(TAG, "Cannot get fontPackageName for " + currentFontName + " fonts.");
        ControlValue.Builder builder2 =
                new ControlValue.Builder("sec_font_style", getControlType());
        builder2.setValue(null);
        return builder2.build();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        String value = controlValue.getValue();
        if (value.isEmpty()) {
            ControlResult.Builder builder = new ControlResult.Builder("sec_font_style");
            builder.mResultCode = ControlResult.ResultCode.FAIL;
            builder.mErrorCode = ControlResult.ErrorCode.INVALID_DATA;
            return builder.build();
        }
        SecDisplayUtils.applyFlipFonts(this.mContext, value);
        ControlResult.Builder builder2 = new ControlResult.Builder("sec_font_style");
        builder2.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder2.build();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecFontStylePreferenceController(Context context, String str) {
        super(context, str);
    }
}
