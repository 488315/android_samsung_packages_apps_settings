package com.google.gson.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import java.lang.reflect.AccessibleObject;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ReflectionAccessFilterHelper {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AccessChecker {
        public static final AccessChecker INSTANCE;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$2, reason: invalid class name */
        public final class AnonymousClass2 extends AccessChecker {
            @Override // com.google.gson.internal.ReflectionAccessFilterHelper.AccessChecker
            public final boolean canAccess(Object obj, AccessibleObject accessibleObject) {
                return true;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x001f  */
        static {
            /*
                int r0 = com.google.gson.internal.JavaVersion.majorJavaVersion
                r1 = 9
                if (r0 < r1) goto L1c
                java.lang.Class<java.lang.reflect.AccessibleObject> r0 = java.lang.reflect.AccessibleObject.class
                java.lang.String r1 = "canAccess"
                r2 = 1
                java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch: java.lang.NoSuchMethodException -> L1c
                java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
                r4 = 0
                r2[r4] = r3     // Catch: java.lang.NoSuchMethodException -> L1c
                java.lang.reflect.Method r0 = r0.getDeclaredMethod(r1, r2)     // Catch: java.lang.NoSuchMethodException -> L1c
                com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$1 r1 = new com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$1     // Catch: java.lang.NoSuchMethodException -> L1c
                r1.<init>()     // Catch: java.lang.NoSuchMethodException -> L1c
                goto L1d
            L1c:
                r1 = 0
            L1d:
                if (r1 != 0) goto L24
                com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$2 r1 = new com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$2
                r1.<init>()
            L24:
                com.google.gson.internal.ReflectionAccessFilterHelper.AccessChecker.INSTANCE = r1
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.google.gson.internal.ReflectionAccessFilterHelper.AccessChecker.<clinit>():void");
        }

        public abstract boolean canAccess(Object obj, AccessibleObject accessibleObject);
    }

    public static void getFilterResult(List list) {
        Iterator it = list.iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }
}
