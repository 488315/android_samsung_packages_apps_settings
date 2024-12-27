package com.android.settings.network.apn;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CustomizedConfig {
    public final String defaultApnProtocol;
    public final String defaultApnRoamingProtocol;
    public final List defaultApnTypes;
    public final boolean isAddApnAllowed;
    public final boolean readOnlyApn;
    public final List readOnlyApnFields;
    public final List readOnlyApnTypes;

    public CustomizedConfig(
            boolean z, List list, List list2, List list3, String str, String str2, int i) {
        this(
                false,
                (i & 2) != 0 ? true : z,
                (i & 4) != 0 ? EmptyList.INSTANCE : list,
                (i & 8) != 0 ? EmptyList.INSTANCE : list2,
                (i & 16) != 0 ? null : list3,
                (i & 32) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str,
                (i & 64) != 0 ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE : str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomizedConfig)) {
            return false;
        }
        CustomizedConfig customizedConfig = (CustomizedConfig) obj;
        return this.readOnlyApn == customizedConfig.readOnlyApn
                && this.isAddApnAllowed == customizedConfig.isAddApnAllowed
                && Intrinsics.areEqual(this.readOnlyApnTypes, customizedConfig.readOnlyApnTypes)
                && Intrinsics.areEqual(this.readOnlyApnFields, customizedConfig.readOnlyApnFields)
                && Intrinsics.areEqual(this.defaultApnTypes, customizedConfig.defaultApnTypes)
                && Intrinsics.areEqual(this.defaultApnProtocol, customizedConfig.defaultApnProtocol)
                && Intrinsics.areEqual(
                        this.defaultApnRoamingProtocol, customizedConfig.defaultApnRoamingProtocol);
    }

    public final int hashCode() {
        int hashCode =
                (this.readOnlyApnFields.hashCode()
                                + ((this.readOnlyApnTypes.hashCode()
                                                + TransitionData$$ExternalSyntheticOutline0.m(
                                                        Boolean.hashCode(this.readOnlyApn) * 31,
                                                        31,
                                                        this.isAddApnAllowed))
                                        * 31))
                        * 31;
        List list = this.defaultApnTypes;
        return this.defaultApnRoamingProtocol.hashCode()
                + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        (hashCode + (list == null ? 0 : list.hashCode())) * 31,
                        31,
                        this.defaultApnProtocol);
    }

    public final String toString() {
        List list = this.readOnlyApnTypes;
        List list2 = this.readOnlyApnFields;
        List list3 = this.defaultApnTypes;
        StringBuilder sb = new StringBuilder("CustomizedConfig(readOnlyApn=");
        sb.append(this.readOnlyApn);
        sb.append(", isAddApnAllowed=");
        sb.append(this.isAddApnAllowed);
        sb.append(", readOnlyApnTypes=");
        sb.append(list);
        sb.append(", readOnlyApnFields=");
        sb.append(list2);
        sb.append(", defaultApnTypes=");
        sb.append(list3);
        sb.append(", defaultApnProtocol=");
        sb.append(this.defaultApnProtocol);
        sb.append(", defaultApnRoamingProtocol=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                sb, this.defaultApnRoamingProtocol, ")");
    }

    public CustomizedConfig(
            boolean z,
            boolean z2,
            List readOnlyApnTypes,
            List readOnlyApnFields,
            List list,
            String defaultApnProtocol,
            String defaultApnRoamingProtocol) {
        Intrinsics.checkNotNullParameter(readOnlyApnTypes, "readOnlyApnTypes");
        Intrinsics.checkNotNullParameter(readOnlyApnFields, "readOnlyApnFields");
        Intrinsics.checkNotNullParameter(defaultApnProtocol, "defaultApnProtocol");
        Intrinsics.checkNotNullParameter(defaultApnRoamingProtocol, "defaultApnRoamingProtocol");
        this.readOnlyApn = z;
        this.isAddApnAllowed = z2;
        this.readOnlyApnTypes = readOnlyApnTypes;
        this.readOnlyApnFields = readOnlyApnFields;
        this.defaultApnTypes = list;
        this.defaultApnProtocol = defaultApnProtocol;
        this.defaultApnRoamingProtocol = defaultApnRoamingProtocol;
    }
}
