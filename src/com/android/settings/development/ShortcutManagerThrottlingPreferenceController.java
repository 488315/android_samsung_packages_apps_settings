package com.android.settings.development;

import android.content.Context;
import android.content.pm.IShortcutService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ShortcutManagerThrottlingPreferenceController
        extends DeveloperOptionsPreferenceController implements PreferenceControllerMixin {
    public final IShortcutService mShortcutService;

    public ShortcutManagerThrottlingPreferenceController(Context context) {
        super(context);
        IShortcutService iShortcutService;
        try {
            iShortcutService =
                    IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut"));
        } catch (VerifyError unused) {
            iShortcutService = null;
        }
        this.mShortcutService = iShortcutService;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "reset_shortcut_manager_throttling";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals("reset_shortcut_manager_throttling", preference.getKey())) {
            return false;
        }
        IShortcutService iShortcutService = this.mShortcutService;
        if (iShortcutService == null) {
            return true;
        }
        try {
            iShortcutService.resetThrottling();
            Toast.makeText(this.mContext, R.string.reset_shortcut_manager_throttling_complete, 0)
                    .show();
            return true;
        } catch (RemoteException e) {
            Log.e("ShortcutMgrPrefCtrl", "Failed to reset rate limiting", e);
            return true;
        }
    }
}
