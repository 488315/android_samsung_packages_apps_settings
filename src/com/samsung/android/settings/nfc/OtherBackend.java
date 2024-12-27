package com.samsung.android.settings.nfc;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.UserHandle;

import androidx.fragment.app.FragmentActivity;

import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class OtherBackend {
    public static final boolean DBG = PaymentSettings.DBG;
    public final CardEmulation mCardEmuManager;
    public final Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OtherAppInfo {
        public CharSequence caption;
        public ComponentName componentName;
        public Drawable icon;
        public boolean isCurrentUser;
        public boolean isManagedProfile;
        public boolean isSelected;
        public UserHandle userHandle;
        public CharSequence userName;
    }

    public OtherBackend(FragmentActivity fragmentActivity) {
        this.mContext = fragmentActivity;
        this.mCardEmuManager =
                CardEmulation.getInstance(NfcAdapter.getDefaultAdapter(fragmentActivity));
    }

    public final void setServiceEnabledForCategoryOther(
            ComponentName componentName, boolean z, int i) {
        try {
            Class.forName(CardEmulation.class.getName())
                    .getDeclaredMethod(
                            "setServiceEnabledForCategoryOther",
                            ComponentName.class,
                            Boolean.TYPE,
                            Integer.TYPE)
                    .invoke(
                            this.mCardEmuManager,
                            componentName,
                            Boolean.valueOf(z),
                            Integer.valueOf(i));
        } catch (ClassNotFoundException
                | IllegalAccessException
                | NoSuchMethodException
                | RuntimeException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
