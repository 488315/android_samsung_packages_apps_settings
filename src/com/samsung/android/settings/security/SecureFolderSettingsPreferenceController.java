package com.samsung.android.settings.security;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureFolderSettingsPreferenceController extends BasePreferenceController {
    private static final String CREATE_SECURE_FOLDER_ACTION =
            "com.sec.knox.securefolder.CREATE_SECURE_FOLDER";
    private static final String KEY_SECURE_FOLDER = "secure_folder";
    boolean isSecureFolderAction;
    SemPersonaManager personaManager;

    public SecureFolderSettingsPreferenceController(Context context) {
        this(context, KEY_SECURE_FOLDER);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UserHandle.myUserId() != 0) {
            return 4;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(CREATE_SECURE_FOLDER_ACTION);
        this.isSecureFolderAction = intent.resolveActivity(packageManager) != null;
        SemPersonaManager semPersonaManager =
                (SemPersonaManager) this.mContext.getSystemService("persona");
        this.personaManager = semPersonaManager;
        return (semPersonaManager == null
                        || (semPersonaManager.isUserManaged() && this.isSecureFolderAction))
                ? 0
                : 3;
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
        if (!preference.getKey().equals(getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(9031, 4491);
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager.getApplicationEnabledSetting("com.samsung.knox.securefolder") == 3) {
            packageManager.setApplicationEnabledSetting("com.samsung.knox.securefolder", 0, 1);
        }
        Intent intent = new Intent(CREATE_SECURE_FOLDER_ACTION);
        intent.setFlags(268435456);
        if (Rune.isChinaModel()) {
            intent.putExtra("skip_popup", false);
        }
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (UserHandle.myUserId() != 0) {
            list.add(getPreferenceKey());
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(CREATE_SECURE_FOLDER_ACTION);
        this.isSecureFolderAction = intent.resolveActivity(packageManager) != null;
        SemPersonaManager semPersonaManager =
                (SemPersonaManager) this.mContext.getSystemService("persona");
        this.personaManager = semPersonaManager;
        if (semPersonaManager != null) {
            if (semPersonaManager.isUserManaged() && this.isSecureFolderAction) {
                return;
            }
            list.add(getPreferenceKey());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecureFolderSettingsPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
