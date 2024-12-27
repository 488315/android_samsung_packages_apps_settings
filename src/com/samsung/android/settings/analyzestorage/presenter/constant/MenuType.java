package com.samsung.android.settings.analyzestorage.presenter.constant;

import kotlin.jvm.internal.Intrinsics;

import java.lang.reflect.Field;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class MenuType {
    public static final String getMenuName(int i) {
        String str;
        Field[] declaredFields = MenuType.class.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
        int length = declaredFields.length;
        int i2 = 0;
        while (true) {
            str = null;
            if (i2 >= length) {
                break;
            }
            Field field = declaredFields[i2];
            try {
                if (Intrinsics.areEqual(field.getType(), Integer.TYPE)) {
                    Object obj = field.get(null);
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                    if (((Integer) obj).intValue() == i) {
                        str = field.getName();
                        break;
                    }
                    continue;
                } else {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            i2++;
        }
        return str == null ? String.valueOf(i) : str;
    }
}
