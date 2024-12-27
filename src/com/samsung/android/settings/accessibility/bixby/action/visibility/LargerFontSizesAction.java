package com.samsung.android.settings.accessibility.bixby.action.visibility;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LargerFontSizesAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        Bundle doGetSupportFeature = super.doGetSupportFeature(context, parsedBundle);
        doGetSupportFeature.putString("result", "DeleteFeature");
        return doGetSupportFeature;
    }
}
