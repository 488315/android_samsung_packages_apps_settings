package com.samsung.android.settings.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WirelessKeyboardShareDBUtil {
    public static final int[] SUPPORT_TYPE = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    public static WirelessKeyboardShareDBUtil mWirelessKeyboardShareDBUtil;
    public final ArrayList mCompareDataList;
    public final ContentResolver mContentResolver;
    public final Object mLock;
    public final ArrayList mSaveDataList;

    public WirelessKeyboardShareDBUtil(Context context) {
        Object obj = new Object();
        this.mLock = obj;
        ArrayList arrayList = new ArrayList();
        this.mSaveDataList = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCompareDataList = arrayList2;
        this.mContentResolver = context.getContentResolver();
        synchronized (obj) {
            try {
                arrayList.clear();
                arrayList2.clear();
                int[] iArr = SUPPORT_TYPE;
                for (int i = 0; i < 9; i++) {
                    int i2 = iArr[i];
                    String str =
                            i2 == 1
                                    ? ApnSettings.MVNO_NONE
                                    : i2 == 2 ? ",," : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                    this.mSaveDataList.add(i2, str);
                    this.mCompareDataList.add(i2, str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        parsing(true);
    }

    public final int getChangedType() {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        this.mContentResolver, "wireless_keyboard_setting_repository", 0);
        int i = -1;
        if (TextUtils.isEmpty(stringForUser)) {
            Log.v("WirelessKeyboardShareDBUtil", "value is null, " + stringForUser);
        } else {
            Log.v("WirelessKeyboardShareDBUtil", "value=" + stringForUser);
            synchronized (this.mLock) {
                try {
                    String[] split = stringForUser.split("/");
                    int i2 = 0;
                    while (true) {
                        if (i2 >= split.length) {
                            break;
                        }
                        if (!split[i2].equals(this.mCompareDataList.get(i2))) {
                            this.mCompareDataList.set(i2, (String) this.mSaveDataList.get(i2));
                            i = i2;
                            break;
                        }
                        i2++;
                    }
                } finally {
                }
            }
        }
        parsing(false);
        return i;
    }

    public final String[] getHostList() {
        String[] split;
        synchronized (this.mLock) {
            try {
                String str = (String) this.mSaveDataList.get(2);
                if (TextUtils.isEmpty(str)) {
                    str = ",,";
                }
                split = str.split(",", 5);
            } catch (Throwable th) {
                throw th;
            }
        }
        return split;
    }

    public final boolean loadByBoolean(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = true;
            if (Integer.parseInt((String) this.mSaveDataList.get(i)) != 1) {
                z = false;
            }
            Log.d("WirelessKeyboardShareDBUtil", "loadByBoolean [" + i + "], boolean is " + z);
        }
        return z;
    }

    public final String loadByString() {
        String str;
        synchronized (this.mLock) {
            str = (String) this.mSaveDataList.get(7);
            Log.d("WirelessKeyboardShareDBUtil", "loadByString [7], String is " + str);
        }
        return str;
    }

    public final void parsing(boolean z) {
        int i = 0;
        String stringForUser =
                Settings.Secure.getStringForUser(
                        this.mContentResolver, "wireless_keyboard_setting_repository", 0);
        if (TextUtils.isEmpty(stringForUser)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                String[] split = stringForUser.split("/");
                int length = split.length;
                int i2 = 0;
                while (i < length) {
                    String str = split[i];
                    if (z) {
                        this.mCompareDataList.set(i2, str);
                    }
                    this.mSaveDataList.set(i2, str);
                    i++;
                    i2++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void save(int i, boolean z) {
        save(i, z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }

    public final void save(int i, String str) {
        String sb;
        if (str == null) {
            str = i == 2 ? ",," : ApnSettings.MVNO_NONE;
        }
        synchronized (this.mLock) {
            this.mSaveDataList.set(i, str);
        }
        ContentResolver contentResolver = this.mContentResolver;
        synchronized (this.mLock) {
            try {
                StringBuilder sb2 = new StringBuilder();
                int[] iArr = SUPPORT_TYPE;
                for (int i2 = 0; i2 < 9; i2++) {
                    sb2.append((String) this.mSaveDataList.get(iArr[i2]));
                    sb2.append("/");
                }
                sb = sb2.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        Settings.Secure.putStringForUser(
                contentResolver, "wireless_keyboard_setting_repository", sb, 0);
        Log.d("WirelessKeyboardShareDBUtil", "save [" + i + "], value is " + str);
    }
}
