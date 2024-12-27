package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.Objects;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zabs {
    public final ApiKey zaa;
    public final Feature zab;

    public /* synthetic */ zabs(ApiKey apiKey, Feature feature) {
        this.zaa = apiKey;
        this.zab = feature;
    }

    public final boolean equals(Object obj) {
        if (obj != null && (obj instanceof zabs)) {
            zabs zabsVar = (zabs) obj;
            if (Objects.equal(this.zaa, zabsVar.zaa) && Objects.equal(this.zab, zabsVar.zab)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[] {this.zaa, this.zab});
    }

    public final String toString() {
        Objects.ToStringHelper toStringHelper = new Objects.ToStringHelper(this);
        toStringHelper.add(this.zaa, GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
        toStringHelper.add(this.zab, "feature");
        return toStringHelper.toString();
    }
}
