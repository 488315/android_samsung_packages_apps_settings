package com.samsung.android.settings.eternal.constant;

import android.os.SemSystemProperties;
import android.os.UserHandle;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.eternal.log.EternalFileLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EternalConstants {
    public static final String PREFIX_LO;
    public static final String SETTINGS_DTD_FILE_NAME;

    static {
        String str;
        if (SemPersonaManager.isSecureFolderId(UserHandle.semGetMyUserId())) {
            EternalFileLog.i("EternalUtils", "getSecureFolderDtdFileName()");
            str =
                    SemSystemProperties.getInt("ro.build.version.oneui", 0) < 70000
                            ? ApnSettings.MVNO_NONE
                            : "SettingsBnRDTD_V_OS_SecureFolder.xml";
        } else {
            EternalFileLog.i("EternalUtils", "getDtdFileName()");
            str =
                    SemSystemProperties.getInt("ro.build.version.oneui", 0) < 70000
                            ? "SettingsBnRDTD_U_OS.xml"
                            : "SettingsBnRDTD_V_OS.xml";
        }
        SETTINGS_DTD_FILE_NAME = str;
        PREFIX_LO = "_elppa";
    }
}
