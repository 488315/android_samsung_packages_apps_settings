package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListListener$getMouseEventCallBack$1;

import java.util.ArrayList;
import java.util.EnumMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KeyboardMouseManager {
    public static final SparseArray sEventContextMap = new SparseArray();
    public static KeyMouseCommand sKeyMouseCommand;
    public static final SparseArray sKeyMouseCommandMap;
    public static AppListListener$getMouseEventCallBack$1 sMouseEventCallBack;
    public static final SparseBooleanArray sPressedKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class KeyboardMouseManagerHolder {
        public static final KeyboardMouseManager INSTANCE;

        static {
            KeyboardMouseManager keyboardMouseManager = new KeyboardMouseManager();
            new ArrayList();
            INSTANCE = keyboardMouseManager;
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        sKeyMouseCommandMap = sparseArray;
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        sPressedKey = sparseBooleanArray;
        sparseArray.append(1, new NormalKeyMouseCommand());
        sparseArray.append(2, new CtrlKeyMouseCommand());
        sparseArray.append(4, new ShiftKeyMouseCommand());
        sparseArray.append(6, new CtrlShiftKeyMouseCommand());
        sparseArray.append(8, new AltKeyMouseCommand());
        sparseBooleanArray.append(2, false);
        sparseBooleanArray.append(4, false);
        sparseBooleanArray.append(8, false);
    }

    public static EventContext getEventContext() {
        EventContext.EventContextPosition eventContextPosition =
                EventContext.EventContextPosition.CONTENTS_PANEL;
        EnumMap enumMap = (EnumMap) sEventContextMap.get(1);
        if (enumMap != null) {
            return (EventContext) enumMap.get(eventContextPosition);
        }
        return null;
    }
}
