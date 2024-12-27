package com.android.settingslib.location;

import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InjectedSetting {
    public final String className;
    public final int iconId;
    public final UserHandle mUserHandle;
    public final String packageName;
    public final String settingsActivity;
    public final String title;
    public final String userRestriction;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public String mClassName;
        public int mIconId;
        public String mPackageName;
        public String mSettingsActivity;
        public String mTitle;
        public UserHandle mUserHandle;
        public String mUserRestriction;

        public final InjectedSetting build() {
            if (this.mPackageName != null
                    && this.mClassName != null
                    && !TextUtils.isEmpty(this.mTitle)
                    && !TextUtils.isEmpty(this.mSettingsActivity)) {
                return new InjectedSetting(this);
            }
            if (!Log.isLoggable("SettingsInjector", 5)) {
                return null;
            }
            StringBuilder sb = new StringBuilder("Illegal setting specification: package=");
            sb.append(this.mPackageName);
            sb.append(", class=");
            sb.append(this.mClassName);
            sb.append(", title=");
            sb.append(this.mTitle);
            sb.append(", settingsActivity=");
            MainClear$$ExternalSyntheticOutline0.m$1(
                    sb, this.mSettingsActivity, "SettingsInjector");
            return null;
        }
    }

    public InjectedSetting(Builder builder) {
        this.packageName = builder.mPackageName;
        this.className = builder.mClassName;
        this.title = builder.mTitle;
        this.iconId = builder.mIconId;
        this.mUserHandle = builder.mUserHandle;
        this.settingsActivity = builder.mSettingsActivity;
        this.userRestriction = builder.mUserRestriction;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InjectedSetting)) {
            return false;
        }
        InjectedSetting injectedSetting = (InjectedSetting) obj;
        return Objects.equals(this.packageName, injectedSetting.packageName)
                && Objects.equals(this.className, injectedSetting.className)
                && Objects.equals(this.title, injectedSetting.title)
                && Integer.valueOf(this.iconId).equals(Integer.valueOf(injectedSetting.iconId))
                && Objects.equals(this.mUserHandle, injectedSetting.mUserHandle)
                && Objects.equals(this.settingsActivity, injectedSetting.settingsActivity)
                && Objects.equals(this.userRestriction, injectedSetting.userRestriction);
    }

    public final int hashCode() {
        int m =
                (TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                                        TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                .m(
                                                        this.packageName.hashCode() * 31,
                                                        31,
                                                        this.className),
                                        31,
                                        this.title)
                                + this.iconId)
                        * 31;
        UserHandle userHandle = this.mUserHandle;
        int m2 =
                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        (m + (userHandle == null ? 0 : userHandle.hashCode())) * 31,
                        31,
                        this.settingsActivity);
        String str = this.userRestriction;
        return m2 + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("InjectedSetting{mPackageName='");
        sb.append(this.packageName);
        sb.append("', mClassName='");
        sb.append(this.className);
        sb.append("', label=");
        sb.append(this.title);
        sb.append(", iconId=");
        sb.append(this.iconId);
        sb.append(", userId=");
        sb.append(this.mUserHandle.getIdentifier());
        sb.append(", settingsActivity='");
        sb.append(this.settingsActivity);
        sb.append("', userRestriction='");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.userRestriction, '}');
    }
}
