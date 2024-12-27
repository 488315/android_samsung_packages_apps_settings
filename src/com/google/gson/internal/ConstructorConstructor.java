package com.google.gson.internal;

import com.google.gson.internal.reflect.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConstructorConstructor {
    public final Map instanceCreators;
    public final List reflectionFilters;
    public final boolean useJdkUnsafe;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.ConstructorConstructor$9, reason: invalid class name */
    public final class AnonymousClass9 implements ObjectConstructor {
        public final /* synthetic */ int $r8$classId = 1;
        public final /* synthetic */ Object val$constructor;

        public AnonymousClass9(Constructor constructor) {
            this.val$constructor = constructor;
        }

        @Override // com.google.gson.internal.ObjectConstructor
        public final Object construct() {
            Object obj = this.val$constructor;
            switch (this.$r8$classId) {
                case 0:
                    try {
                        return ((Constructor) obj).newInstance(null);
                    } catch (IllegalAccessException e) {
                        ReflectionHelper.RecordHelper recordHelper = ReflectionHelper.RECORD_HELPER;
                        throw new RuntimeException(
                                "Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain"
                                    + " ReflectionAccessFilter features require Java >= 9 to work"
                                    + " correctly. If you are not using ReflectionAccessFilter,"
                                    + " report this to the Gson maintainers.",
                                e);
                    } catch (InstantiationException e2) {
                        throw new RuntimeException(
                                "Failed to invoke constructor '"
                                        + ReflectionHelper.constructorToString((Constructor) obj)
                                        + "' with no args",
                                e2);
                    } catch (InvocationTargetException e3) {
                        throw new RuntimeException(
                                "Failed to invoke constructor '"
                                        + ReflectionHelper.constructorToString((Constructor) obj)
                                        + "' with no args",
                                e3.getCause());
                    }
                default:
                    try {
                        return UnsafeAllocator.INSTANCE.newInstance((Class) obj);
                    } catch (Exception e4) {
                        throw new RuntimeException(
                                "Unable to create instance of "
                                        + ((Class) obj)
                                        + ". Registering an InstanceCreator or a TypeAdapter for"
                                        + " this type, or adding a no-args constructor may fix this"
                                        + " problem.",
                                e4);
                    }
            }
        }

        public AnonymousClass9(Class cls) {
            this.val$constructor = cls;
        }
    }

    public ConstructorConstructor(List list, Map map, boolean z) {
        this.instanceCreators = map;
        this.useJdkUnsafe = z;
        this.reflectionFilters = list;
    }

    public static String checkInstantiable(Class cls) {
        int modifiers = cls.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            return "Interfaces can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Interface name: "
                    .concat(cls.getName());
        }
        if (Modifier.isAbstract(modifiers)) {
            return "Abstract classes can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Class name: "
                    .concat(cls.getName());
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0087 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.gson.internal.ObjectConstructor get(
            com.google.gson.reflect.TypeToken r10) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.ConstructorConstructor.get(com.google.gson.reflect.TypeToken):com.google.gson.internal.ObjectConstructor");
    }

    public final String toString() {
        return this.instanceCreators.toString();
    }
}
