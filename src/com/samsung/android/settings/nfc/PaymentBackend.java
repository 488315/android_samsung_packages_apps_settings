package com.samsung.android.settings.nfc;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PaymentBackend {
    public final CardEmulation mCardEmuManager;
    public final Context mContext;
    public PaymentAppInfo mDefaultAppInfo;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaymentAppInfo {
        public ComponentName componentName;
        public CharSequence description;
        public Drawable icon;
        public boolean isCurrentUser;
        public boolean isDefault;
        public boolean isManagedProfile;
        public CharSequence label;
        public UserHandle userHandle;
        public CharSequence userName;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaymentInfo {
        public ComponentName componentName;
        public int userId;
    }

    static {
        boolean z = PaymentSettings.DBG;
    }

    public PaymentBackend(Context context) {
        this.mContext = context;
        this.mCardEmuManager = CardEmulation.getInstance(NfcAdapter.getDefaultAdapter(context));
    }

    public final PaymentInfo getDefaultPaymentApp() {
        ComponentName componentName;
        UserHandle userHandle;
        Iterator it =
                ((UserManager)
                                this.mContext
                                        .createContextAsUser(
                                                UserHandle.of(ActivityManager.getCurrentUser()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles()
                        .iterator();
        do {
            componentName = null;
            if (!it.hasNext()) {
                return null;
            }
            userHandle = (UserHandle) it.next();
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            this.mContext.getContentResolver(),
                            "nfc_payment_default_component",
                            userHandle.getIdentifier());
            if (stringForUser != null) {
                componentName = ComponentName.unflattenFromString(stringForUser);
            }
        } while (componentName == null);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.userId = userHandle.getIdentifier();
        paymentInfo.componentName = componentName;
        return paymentInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c9  */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v3, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getPaymentAppInfos() {
        /*
            Method dump skipped, instructions count: 359
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.nfc.PaymentBackend.getPaymentAppInfos():java.util.List");
    }

    public final boolean isForegroundMode() {
        try {
            return Settings.Secure.getIntForUser(
                            this.mContext.getContentResolver(),
                            "nfc_payment_foreground",
                            UserHandle.myUserId())
                    != 0;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public final void setDefaultPaymentApp(ComponentName componentName, int i) {
        for (UserHandle userHandle :
                ((UserManager)
                                this.mContext
                                        .createContextAsUser(
                                                UserHandle.of(ActivityManager.getCurrentUser()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles()) {
            if (userHandle.getIdentifier() == i) {
                Settings.Secure.putStringForUser(
                        this.mContext.getContentResolver(),
                        "nfc_payment_default_component",
                        componentName != null ? componentName.flattenToString() : null,
                        userHandle.getIdentifier());
            } else {
                Settings.Secure.putStringForUser(
                        this.mContext.getContentResolver(),
                        "nfc_payment_default_component",
                        null,
                        userHandle.getIdentifier());
            }
        }
    }

    public final void setForegroundMode(boolean z) {
        for (UserHandle userHandle :
                ((UserManager)
                                this.mContext
                                        .createContextAsUser(
                                                UserHandle.of(UserHandle.myUserId()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles()) {
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(),
                    "nfc_payment_foreground",
                    z ? 1 : 0,
                    userHandle.getIdentifier());
        }
    }
}
