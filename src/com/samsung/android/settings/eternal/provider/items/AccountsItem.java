package com.samsung.android.settings.eternal.provider.items;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;

import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController$$ExternalSyntheticOutline0;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AccountsItem implements Recoverable {
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str) {
        char c;
        AccountManager accountManager = AccountManager.get(context);
        Scene.Builder builder = new Scene.Builder(str);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            boolean z = true;
            switch (str.hashCode()) {
                case -712705117:
                    if (str.equals("/Settings/Accounts/GoogleAccountID")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 632766048:
                    if (str.equals("/Settings/Accounts/AutoSyncData")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1931678105:
                    if (str.equals("/Settings/Accounts/GoogleCoreControl")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2093720026:
                    if (str.equals("/Settings/Accounts/SamsungAccountID")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                Account[] accountsByType =
                        accountManager.getAccountsByType(RestrictionPolicy.GOOGLE_ACCOUNT_TYPE);
                if (accountsByType.length > 0) {
                    builder.setValue(accountsByType[0].name, false);
                }
            } else if (c == 1) {
                Account[] accountsByType2 = accountManager.getAccountsByType("com.osp.app.signin");
                if (accountsByType2.length > 0) {
                    builder.setValue(accountsByType2[0].name, false);
                }
            } else if (c != 2) {
                if (c != 3) {
                    SemLog.d("AccountsItem", "unknown key : ".concat(str));
                } else {
                    builder.setValue(
                            Integer.valueOf(
                                    ContentResolver.getMasterSyncAutomaticallyAsUser(
                                                    UserHandle.myUserId())
                                            ? 1
                                            : 0),
                            false);
                }
            } else if (Rune.isChinaModel()) {
                int i = Settings.System.getInt(contentResolver, "google_core_control", 0);
                builder.setValue(Integer.valueOf(i), false);
                if (i != 0) {
                    z = false;
                }
                builder.setDefault(z);
            }
        } catch (Exception e) {
            SemLog.e("AccountsItem", e.getStackTrace()[0].toString());
        }
        return builder;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(
            Context context, String str, Scene scene, SourceInfo sourceInfo) {
        ContentResolver contentResolver = context.getContentResolver();
        String value = scene.getValue(null, false);
        SceneResult.Builder builder = new SceneResult.Builder(str);
        builder.mResultType = SceneResult.ResultType.RESULT_OK;
        str.getClass();
        if (str.equals("/Settings/Accounts/AutoSyncData")) {
            ContentResolver.setMasterSyncAutomaticallyAsUser(
                    value.equals("1"), UserHandle.myUserId());
        } else if (str.equals("/Settings/Accounts/GoogleCoreControl") && Rune.isChinaModel()) {
            SecSoundDeviceVibrationController$$ExternalSyntheticOutline0.m(
                    contentResolver, value, "google_core_control");
        }
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
