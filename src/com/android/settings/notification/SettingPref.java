package com.android.settings.notification;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.DropDownPreference;
import androidx.preference.TwoStatePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingPref {
    public final int mDefault;
    public DropDownPreference mDropDown;
    public final String mKey;
    public final String mSetting;
    public TwoStatePreference mTwoState;
    public final int mType;
    public final Uri mUri;
    public final int[] mValues;

    public SettingPref(int i, String str, String str2, int i2, int... iArr) {
        Uri uriFor;
        this.mType = i;
        this.mKey = str;
        this.mSetting = str2;
        this.mDefault = i2;
        this.mValues = iArr;
        if (i == 1) {
            uriFor = Settings.Global.getUriFor(str2);
        } else if (i == 2) {
            uriFor = Settings.System.getUriFor(str2);
        } else {
            if (i != 3) {
                throw new IllegalArgumentException();
            }
            uriFor = Settings.Secure.getUriFor(str2);
        }
        this.mUri = uriFor;
    }

    public String getCaption(Resources resources, int i) {
        throw new UnsupportedOperationException();
    }

    public boolean isApplicable(Context context) {
        return true;
    }

    public boolean setSetting(Context context, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = this.mType;
        String str = this.mSetting;
        if (i2 == 1) {
            return Settings.Global.putInt(contentResolver, str, i);
        }
        if (i2 == 2) {
            return Settings.System.putInt(contentResolver, str, i);
        }
        if (i2 == 3) {
            return Settings.Secure.putInt(contentResolver, str, i);
        }
        throw new IllegalArgumentException();
    }

    public final void update(Context context) {
        int i;
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = this.mType;
        String str = this.mSetting;
        int i3 = this.mDefault;
        if (i2 == 1) {
            i = Settings.Global.getInt(contentResolver, str, i3);
        } else if (i2 == 2) {
            i = Settings.System.getInt(contentResolver, str, i3);
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException();
            }
            i = Settings.Secure.getInt(contentResolver, str, i3);
        }
        TwoStatePreference twoStatePreference = this.mTwoState;
        if (twoStatePreference != null) {
            twoStatePreference.setChecked(i != 0);
            return;
        }
        DropDownPreference dropDownPreference = this.mDropDown;
        if (dropDownPreference != null) {
            dropDownPreference.setValue(Integer.toString(i));
        }
    }
}
