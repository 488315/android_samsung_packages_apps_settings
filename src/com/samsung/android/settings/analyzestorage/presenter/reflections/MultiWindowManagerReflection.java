package com.samsung.android.settings.analyzestorage.presenter.reflections;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MultiWindowManagerReflection {
    public static Object mMultiWindowManagerInstance;
    public Class mBaseClass;
    public ArrayList mNameList;
    public ArrayList mReflectionList;

    public final void addReflectionInstance(Object obj, String str) {
        synchronized (this.mNameList) {
            this.mNameList.add(str);
            this.mReflectionList.add(obj);
        }
    }

    public final Object invokeNormalMethod(Object obj) {
        Object obj2;
        Method method;
        if (obj == null) {
            Log.d(
                    "com.samsung.android.multiwindow.MultiWindowManager",
                    "Cannot invoke getVisibleTasks");
            return null;
        }
        Object[] objArr = new Object[0];
        synchronized (this.mNameList) {
            try {
                int size = this.mNameList.size();
                int i = 0;
                loop0:
                while (true) {
                    if (i >= size) {
                        obj2 = null;
                        break;
                    }
                    String str = (String) this.mNameList.get(i);
                    int length = str.length();
                    if (length == 15) {
                        int i2 = length - 1;
                        char[] charArray = str.toCharArray();
                        char[] charArray2 = "getVisibleTasks".toCharArray();
                        for (int i3 = 0; i3 < length; i3++) {
                            char c = charArray[i3];
                            if ((charArray2[i3] & c) != c) {
                                break;
                            }
                            if (i3 == i2) {
                                obj2 = this.mReflectionList.get(i);
                            }
                        }
                    }
                    i++;
                }
            } finally {
            }
        }
        if (obj2 != null) {
            method = (Method) obj2;
        } else {
            Class cls = this.mBaseClass;
            if (cls != null) {
                Class[] clsArr = new Class[0];
                try {
                    try {
                        method = cls.getMethod("getVisibleTasks", null);
                        addReflectionInstance(method, "getVisibleTasks");
                    } catch (NoSuchMethodException unused) {
                        method = this.mBaseClass.getDeclaredMethod("getVisibleTasks", null);
                        method.setAccessible(true);
                        addReflectionInstance(method, "getVisibleTasks");
                    }
                } catch (NoSuchMethodException e) {
                    Log.e(
                            "AbstractBaseReflection",
                            "com.samsung.android.multiwindow.MultiWindowManager No method " + e);
                }
            }
            method = null;
        }
        if (method == null) {
            Log.d(
                    "com.samsung.android.multiwindow.MultiWindowManager",
                    "Cannot invoke there's no method reflection : ".concat("getVisibleTasks"));
            return null;
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            Log.e(
                    "AbstractBaseReflection",
                    "com.samsung.android.multiwindow.MultiWindowManager IllegalAccessException"
                        + " encountered invoking getVisibleTasks"
                            + e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e(
                    "AbstractBaseReflection",
                    "com.samsung.android.multiwindow.MultiWindowManager InvocationTargetException"
                        + " encountered invoking getVisibleTasks"
                            + e3);
            return null;
        }
    }

    public final void loadReflection() {
        Class<?> cls;
        try {
            cls = Class.forName("com.samsung.android.multiwindow.MultiWindowManager");
        } catch (ClassNotFoundException e) {
            Log.e(
                    "AbstractBaseReflection",
                    "com.samsung.android.multiwindow.MultiWindowManager Unable to load class " + e);
            cls = null;
        }
        this.mBaseClass = cls;
        if (cls == null) {
            Log.d("AbstractBaseReflection", "There's no class.");
        }
    }
}
