package com.android.settings.nfc;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.ApduServiceInfo;
import android.nfc.cardemulation.CardEmulation;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import com.android.internal.content.PackageMonitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PaymentBackend {
    public ArrayList mAppInfos;
    public final CardEmulation mCardEmuManager;
    public final Context mContext;
    public PaymentAppInfo mDefaultAppInfo;
    public final SettingsPackageMonitor mSettingsPackageMonitor = new SettingsPackageMonitor();
    public final ArrayList mCallbacks = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onPaymentAppsChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaymentAppInfo {
        public ComponentName componentName;
        public Drawable icon;
        public boolean isDefault;
        public CharSequence label;
        public ComponentName settingsComponent;
        public UserHandle userHandle;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaymentInfo {
        public ComponentName componentName;
        public int userId;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsPackageMonitor extends PackageMonitor {
        public AnonymousClass1 mHandler;

        public SettingsPackageMonitor() {}

        public final void onPackageAdded(String str, int i) {
            obtainMessage().sendToTarget();
        }

        public final void onPackageAppeared(String str, int i) {
            obtainMessage().sendToTarget();
        }

        public final void onPackageDisappeared(String str, int i) {
            obtainMessage().sendToTarget();
        }

        public final void onPackageRemoved(String str, int i) {
            obtainMessage().sendToTarget();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.nfc.PaymentBackend$SettingsPackageMonitor$1] */
        public final void register(
                Context context, Looper looper, UserHandle userHandle, boolean z) {
            if (this.mHandler == null) {
                this.mHandler =
                        new Handler(
                                looper) { // from class:
                                          // com.android.settings.nfc.PaymentBackend.SettingsPackageMonitor.1
                            @Override // android.os.Handler
                            public final void dispatchMessage(Message message) {
                                PaymentBackend.this.refresh();
                            }
                        };
            }
            super.register(context, looper, userHandle, z);
        }
    }

    public PaymentBackend(Context context) {
        this.mContext = context;
        this.mCardEmuManager = CardEmulation.getInstance(NfcAdapter.getDefaultAdapter(context));
        refresh();
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

    public final void refresh() {
        PaymentInfo paymentInfo;
        PackageManager packageManager = this.mContext.getPackageManager();
        ArrayList arrayList = new ArrayList();
        List<UserHandle> enabledProfiles =
                ((UserManager)
                                this.mContext
                                        .createContextAsUser(
                                                UserHandle.of(ActivityManager.getCurrentUser()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles();
        Iterator it =
                ((UserManager)
                                this.mContext
                                        .createContextAsUser(
                                                UserHandle.of(ActivityManager.getCurrentUser()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles()
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                paymentInfo = null;
                break;
            }
            UserHandle userHandle = (UserHandle) it.next();
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            this.mContext.getContentResolver(),
                            "nfc_payment_default_component",
                            userHandle.getIdentifier());
            ComponentName unflattenFromString =
                    stringForUser != null ? ComponentName.unflattenFromString(stringForUser) : null;
            if (unflattenFromString != null) {
                paymentInfo = new PaymentInfo();
                paymentInfo.userId = userHandle.getIdentifier();
                paymentInfo.componentName = unflattenFromString;
                break;
            }
        }
        PaymentAppInfo paymentAppInfo = null;
        for (UserHandle userHandle2 : enabledProfiles) {
            List<ApduServiceInfo> services =
                    this.mCardEmuManager.getServices("payment", userHandle2.getIdentifier());
            if (services != null) {
                ArrayList arrayList2 = new ArrayList();
                for (ApduServiceInfo apduServiceInfo : services) {
                    PaymentAppInfo paymentAppInfo2 = new PaymentAppInfo();
                    paymentAppInfo2.userHandle = userHandle2;
                    CharSequence loadLabel = apduServiceInfo.loadLabel(packageManager);
                    paymentAppInfo2.label = loadLabel;
                    if (loadLabel == null) {
                        paymentAppInfo2.label = apduServiceInfo.loadAppLabel(packageManager);
                    }
                    if (paymentInfo == null) {
                        paymentAppInfo2.isDefault = false;
                    } else {
                        paymentAppInfo2.isDefault =
                                apduServiceInfo.getComponent().equals(paymentInfo.componentName)
                                        && paymentInfo.userId == userHandle2.getIdentifier();
                    }
                    if (paymentAppInfo2.isDefault) {
                        paymentAppInfo = paymentAppInfo2;
                    }
                    paymentAppInfo2.componentName = apduServiceInfo.getComponent();
                    String settingsActivityName = apduServiceInfo.getSettingsActivityName();
                    if (settingsActivityName != null) {
                        paymentAppInfo2.settingsComponent =
                                new ComponentName(
                                        paymentAppInfo2.componentName.getPackageName(),
                                        settingsActivityName);
                    } else {
                        paymentAppInfo2.settingsComponent = null;
                    }
                    apduServiceInfo.getDescription();
                    paymentAppInfo2.icon =
                            packageManager.getUserBadgedIcon(
                                    apduServiceInfo.loadBanner(packageManager) != null
                                            ? apduServiceInfo.loadBanner(packageManager)
                                            : apduServiceInfo.loadIcon(packageManager),
                                    paymentAppInfo2.userHandle);
                    arrayList2.add(paymentAppInfo2);
                }
                arrayList.addAll(arrayList2);
            }
        }
        this.mAppInfos = arrayList;
        this.mDefaultAppInfo = paymentAppInfo;
        Iterator it2 = this.mCallbacks.iterator();
        while (it2.hasNext()) {
            ((Callback) it2.next()).onPaymentAppsChanged();
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
        refresh();
    }
}
