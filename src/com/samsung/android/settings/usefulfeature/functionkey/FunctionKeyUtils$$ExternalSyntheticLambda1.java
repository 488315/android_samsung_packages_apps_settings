package com.samsung.android.settings.usefulfeature.functionkey;

import android.text.TextUtils;

import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class FunctionKeyUtils$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return TextUtils.equals(
                ((FunctionKeyItem) obj).key,
                UsefulfeatureUtils.FUNC_KEY_DOUBLE_SELECTED_ITEM_DEFAULT);
    }
}
