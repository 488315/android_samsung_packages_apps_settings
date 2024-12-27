package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MaterialAttributes {
    public static TypedValue resolve(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static boolean resolveBoolean(Context context, int i, boolean z) {
        TypedValue resolve = resolve(context, i);
        return (resolve == null || resolve.type != 18) ? z : resolve.data != 0;
    }

    public static TypedValue resolveTypedValueOrThrow(Context context, int i, String str) {
        TypedValue resolve = resolve(context, i);
        if (resolve != null) {
            return resolve;
        }
        throw new IllegalArgumentException(
                String.format(
                        "%1$s requires a value for the %2$s attribute to be set in your app theme."
                            + " You can either set the attribute in your theme or update your theme"
                            + " to inherit from Theme.MaterialComponents (or a descendant).",
                        str, context.getResources().getResourceName(i)));
    }
}
