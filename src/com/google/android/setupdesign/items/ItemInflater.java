package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;

import com.android.settings.sim.ChooseSimActivity;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ItemInflater extends SimpleInflater {
    public static final Class[] CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    public static final HashMap constructorMap = new HashMap();
    public final Context context;
    public final String defaultPackage;
    public final Object[] tempConstructorArgs;

    public ItemInflater(Context context) {
        super(context.getResources());
        this.tempConstructorArgs = new Object[2];
        this.context = context;
        this.defaultPackage = ChooseSimActivity.DisableableItem.class.getPackage().getName() + ".";
    }

    @Override // com.google.android.setupdesign.items.SimpleInflater
    public final Object onCreateItem(String str, AttributeSet attributeSet) {
        String str2 = this.defaultPackage;
        Object[] objArr = this.tempConstructorArgs;
        String concat = (str2 == null || str.indexOf(46) != -1) ? str : str2.concat(str);
        HashMap hashMap = constructorMap;
        Constructor<?> constructor = (Constructor) hashMap.get(concat);
        if (constructor == null) {
            try {
                constructor =
                        this.context
                                .getClassLoader()
                                .loadClass(concat)
                                .getConstructor(CONSTRUCTOR_SIGNATURE);
                constructor.setAccessible(true);
                hashMap.put(str, constructor);
            } catch (Exception e) {
                throw new InflateException(
                        attributeSet.getPositionDescription() + ": Error inflating class " + concat,
                        e);
            }
        }
        objArr[0] = this.context;
        objArr[1] = attributeSet;
        Object newInstance = constructor.newInstance(objArr);
        objArr[0] = null;
        objArr[1] = null;
        return newInstance;
    }
}
