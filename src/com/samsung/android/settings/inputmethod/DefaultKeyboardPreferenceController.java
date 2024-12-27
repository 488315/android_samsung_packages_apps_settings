package com.samsung.android.settings.inputmethod;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DefaultKeyboardPreferenceController extends BasePreferenceController {
    private static final String PKG_HONEY_BOARD =
            "com.samsung.android.honeyboard/.service.HoneyBoardService";
    private DevicePolicyManager mDpm;
    private InputMethodManager mImm;
    private PreferenceCategory mPreferenceCategory;
    private UserManager um;

    public DefaultKeyboardPreferenceController(Context context, String str) {
        super(context, str);
        this.um = (UserManager) context.getSystemService("user");
        this.mImm = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        UserManager userManager = this.um;
        StringBuilder sb = Utils.sBuilder;
        return userManager.getUserInfo(userManager.getUserHandle()).isKnoxWorkspace() ? 4 : 0;
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

    public void updateInputMethodPreferenceViews() {
        if (this.mPreferenceCategory == null) {
            return;
        }
        List permittedInputMethodsForCurrentUser =
                this.mDpm.getPermittedInputMethodsForCurrentUser();
        List<InputMethodInfo> enabledInputMethodList = this.mImm.getEnabledInputMethodList();
        int size = enabledInputMethodList == null ? 0 : enabledInputMethodList.size();
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(), "default_input_method");
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i);
            boolean z =
                    permittedInputMethodsForCurrentUser == null
                            || permittedInputMethodsForCurrentUser.contains(
                                    inputMethodInfo.getPackageName());
            inputMethodInfo.loadIcon(this.mContext.getPackageManager());
            SecInputMethodPreference secInputMethodPreference =
                    new SecInputMethodPreference(
                            this.mContext, inputMethodInfo, z, null, UserHandle.myUserId());
            if (TextUtils.equals(inputMethodInfo.getId(), string)) {
                this.mPreferenceCategory.removeAll();
                this.mPreferenceCategory.addPreference(secInputMethodPreference);
                if (string.equals(PKG_HONEY_BOARD)) {
                    secInputMethodPreference.setTitle(R.string.sec_samsung_keyboard_settings_title);
                } else {
                    secInputMethodPreference.setTitle(
                            this.mContext
                                    .getResources()
                                    .getString(
                                            R.string.default_keyboard_settings_title,
                                            inputMethodInfo.loadLabel(
                                                    this.mContext.getPackageManager())));
                }
                secInputMethodPreference.updatePreferenceViews();
                SecPreferenceUtils.applySummaryColor(secInputMethodPreference, true);
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
