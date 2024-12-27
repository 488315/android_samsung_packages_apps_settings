package com.google.gson.internal.reflect;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.google.gson.JsonIOException;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ReflectionHelper {
    public static final RecordHelper RECORD_HELPER;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class RecordHelper {
        public abstract Method getAccessor(Class cls, Field field);

        public abstract Constructor getCanonicalRecordConstructor(Class cls);

        public abstract String[] getRecordComponentNames(Class cls);

        public abstract boolean isRecord(Class cls);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RecordNotSupportedHelper extends RecordHelper {
        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final Method getAccessor(Class cls, Field field) {
            throw new UnsupportedOperationException(
                    "Records are not supported on this JVM, this method should not be called");
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final Constructor getCanonicalRecordConstructor(Class cls) {
            throw new UnsupportedOperationException(
                    "Records are not supported on this JVM, this method should not be called");
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final String[] getRecordComponentNames(Class cls) {
            throw new UnsupportedOperationException(
                    "Records are not supported on this JVM, this method should not be called");
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final boolean isRecord(Class cls) {
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RecordSupportedHelper extends RecordHelper {
        public final Method getName;
        public final Method getRecordComponents;
        public final Method getType;
        public final Method isRecord = Class.class.getMethod("isRecord", null);

        public RecordSupportedHelper() {
            Class[] clsArr = new Class[0];
            Class[] clsArr2 = new Class[0];
            Method method = Class.class.getMethod("getRecordComponents", null);
            this.getRecordComponents = method;
            Class<?> componentType = method.getReturnType().getComponentType();
            Class[] clsArr3 = new Class[0];
            this.getName = componentType.getMethod("getName", null);
            Class[] clsArr4 = new Class[0];
            this.getType = componentType.getMethod("getType", null);
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final Method getAccessor(Class cls, Field field) {
            try {
                String name = field.getName();
                Class[] clsArr = new Class[0];
                return cls.getMethod(name, null);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(
                        "Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support"
                            + " Java records, reflection is utilized to read out information about"
                            + " records. All these invocations happens after it is established that"
                            + " records exist in the JVM. This exception is unexpected behavior.",
                        e);
            }
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final Constructor getCanonicalRecordConstructor(Class cls) {
            try {
                Object[] objArr = (Object[]) this.getRecordComponents.invoke(cls, null);
                Class<?>[] clsArr = new Class[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    clsArr[i] = (Class) this.getType.invoke(objArr[i], null);
                }
                return cls.getDeclaredConstructor(clsArr);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(
                        "Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support"
                            + " Java records, reflection is utilized to read out information about"
                            + " records. All these invocations happens after it is established that"
                            + " records exist in the JVM. This exception is unexpected behavior.",
                        e);
            }
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final String[] getRecordComponentNames(Class cls) {
            try {
                Object[] objArr = (Object[]) this.getRecordComponents.invoke(cls, null);
                String[] strArr = new String[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    strArr[i] = (String) this.getName.invoke(objArr[i], null);
                }
                return strArr;
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(
                        "Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support"
                            + " Java records, reflection is utilized to read out information about"
                            + " records. All these invocations happens after it is established that"
                            + " records exist in the JVM. This exception is unexpected behavior.",
                        e);
            }
        }

        @Override // com.google.gson.internal.reflect.ReflectionHelper.RecordHelper
        public final boolean isRecord(Class cls) {
            try {
                return ((Boolean) this.isRecord.invoke(cls, null)).booleanValue();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(
                        "Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support"
                            + " Java records, reflection is utilized to read out information about"
                            + " records. All these invocations happens after it is established that"
                            + " records exist in the JVM. This exception is unexpected behavior.",
                        e);
            }
        }
    }

    static {
        RecordHelper recordNotSupportedHelper;
        try {
            recordNotSupportedHelper = new RecordSupportedHelper();
        } catch (NoSuchMethodException unused) {
            recordNotSupportedHelper = new RecordNotSupportedHelper();
        }
        RECORD_HELPER = recordNotSupportedHelper;
    }

    public static void appendExecutableParameters(
            AccessibleObject accessibleObject, StringBuilder sb) {
        sb.append('(');
        Class<?>[] parameterTypes =
                accessibleObject instanceof Method
                        ? ((Method) accessibleObject).getParameterTypes()
                        : ((Constructor) accessibleObject).getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(parameterTypes[i].getSimpleName());
        }
        sb.append(')');
    }

    public static String constructorToString(Constructor constructor) {
        StringBuilder sb = new StringBuilder(constructor.getDeclaringClass().getName());
        appendExecutableParameters(constructor, sb);
        return sb.toString();
    }

    public static String fieldToString(Field field) {
        return field.getDeclaringClass().getName() + "#" + field.getName();
    }

    public static String getAccessibleObjectDescription(
            AccessibleObject accessibleObject, boolean z) {
        String str;
        if (accessibleObject instanceof Field) {
            str = "field '" + fieldToString((Field) accessibleObject) + "'";
        } else if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            StringBuilder sb = new StringBuilder(method.getName());
            appendExecutableParameters(method, sb);
            str = "method '" + method.getDeclaringClass().getName() + "#" + sb.toString() + "'";
        } else if (accessibleObject instanceof Constructor) {
            str = "constructor '" + constructorToString((Constructor) accessibleObject) + "'";
        } else {
            str = "<unknown AccessibleObject> " + accessibleObject.toString();
        }
        if (!z || !Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static void makeAccessible(AccessibleObject accessibleObject) {
        try {
            accessibleObject.setAccessible(true);
        } catch (Exception e) {
            throw new JsonIOException(
                    ComposerKt$$ExternalSyntheticOutline0.m(
                            "Failed making ",
                            getAccessibleObjectDescription(accessibleObject, false),
                            " accessible; either increase its visibility or write a custom"
                                + " TypeAdapter for its declaring type."),
                    e);
        }
    }
}
