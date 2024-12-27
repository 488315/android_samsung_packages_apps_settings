package com.samsung.android.settings.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.internal.telephony.ISemTelephony;
import com.android.settings.R;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.Rune;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecIccLockManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public String mErrorMessage;
    public int mIccLockMode;
    public boolean mIccToState;
    public MenuControllerInterface mMenuController;
    public String mNewPin;
    public String mOldPin;
    public String mPin;
    public ISemTelephony mSemTelephony;
    public int mSlotIndex;
    public int mSubscriptionId;
    public TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ChangeIccLockPin extends AsyncTask {
        public final String mNewPin;
        public final String mOldPin;

        public ChangeIccLockPin(String str, String str2) {
            this.mOldPin = str;
            this.mNewPin = str2;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return SecIccLockManager.this.mTelephonyManager.changeIccLockPin(
                    this.mOldPin, this.mNewPin);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            PinResult pinResult = (PinResult) obj;
            SecIccLockManager.this.getClass();
            SecIccLockManager secIccLockManager = SecIccLockManager.this;
            boolean z = pinResult.getResult() == 0;
            pinResult.getAttemptsRemaining();
            secIccLockManager.getClass();
            Log.d("SecIccLockSettings.SecIccLockManager", "iccPinChanged: success = " + z);
            if (z) {
                if (secIccLockManager.isCTCSlot1()) {
                    Context context = secIccLockManager.mContext;
                    ApnPreference$$ExternalSyntheticOutline0.m(
                            context, R.string.ruim_change_succeeded, context, 0);
                } else {
                    Context context2 = secIccLockManager.mContext;
                    ApnPreference$$ExternalSyntheticOutline0.m(
                            context2, R.string.sim_change_succeeded, context2, 0);
                }
            } else if (secIccLockManager.isCTCSlot1()) {
                Context context3 = secIccLockManager.mContext;
                Toast.makeText(
                                context3,
                                context3.getString(R.string.sim_change_failed)
                                        .replace(
                                                UniversalCredentialManager.APPLET_FORM_FACTOR_SIM,
                                                "UIM"),
                                0)
                        .show();
            } else {
                Context context4 = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context4, R.string.sim_change_failed, context4, 0);
            }
            secIccLockManager.updateMenu();
            secIccLockManager.resetIccLockData();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ChangeSimPersoPin extends AsyncTask {
        public final String mNewPin;
        public final String mOldPin;
        public final int mSubId;

        public ChangeSimPersoPin(int i, String str, String str2) {
            this.mOldPin = str;
            this.mNewPin = str2;
            this.mSubId = i;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                return Boolean.valueOf(
                        SecIccLockManager.this.mSemTelephony.changeIccSimPersoPasswordForSubId(
                                this.mSubId, this.mOldPin, this.mNewPin));
            } catch (Exception unused) {
                int i = SecIccLockManager.$r8$clinit;
                Log.i(
                        "SecIccLockSettings.SecIccLockManager",
                        "changeIccSimPersoPassword fail. exception occured");
                return Boolean.FALSE;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            SecIccLockManager secIccLockManager = SecIccLockManager.this;
            boolean booleanValue = ((Boolean) obj).booleanValue();
            secIccLockManager.getClass();
            Log.d(
                    "SecIccLockSettings.SecIccLockManager",
                    "simPersoLockPasswdChanged : " + booleanValue);
            if (booleanValue) {
                Context context = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context,
                        R.string.lock_phone_to_current_sim_change_password_success_toast,
                        context,
                        0);
            } else {
                Context context2 = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context2,
                        R.string.lock_phone_to_current_sim_change_password_fail_toast,
                        context2,
                        0);
            }
            if (SecSimPersoDialog.isDialogOpen()) {
                SecSimPersoDialog.releaseDialog();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface MenuControllerInterface {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SetIccLockEnabled extends AsyncTask {
        public final String mPin;
        public final boolean mState;

        public SetIccLockEnabled(boolean z, String str) {
            this.mState = z;
            this.mPin = str;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return SecIccLockManager.this.mTelephonyManager.setIccLockEnabled(
                    this.mState, this.mPin);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            PinResult pinResult = (PinResult) obj;
            SecIccLockManager secIccLockManager = SecIccLockManager.this;
            boolean z = pinResult.getResult() == 0;
            pinResult.getAttemptsRemaining();
            boolean z2 = this.mState;
            secIccLockManager.getClass();
            StringBuilder sb = new StringBuilder("iccLockChanged: success = ");
            sb.append(z);
            sb.append(" : ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    sb, secIccLockManager.mIccToState, "SecIccLockSettings.SecIccLockManager");
            if (z) {
                Context context = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context,
                        z2 ? R.string.sim_lock_is_set : R.string.sim_lock_is_released,
                        context,
                        1);
            } else if (secIccLockManager.isCTCSlot1()) {
                Context context2 = secIccLockManager.mContext;
                Toast.makeText(
                                context2,
                                context2.getString(R.string.sim_lock_failed)
                                        .replace(
                                                UniversalCredentialManager.APPLET_FORM_FACTOR_SIM,
                                                "UIM"),
                                1)
                        .show();
            } else {
                Context context3 = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context3, R.string.sim_lock_failed, context3, 1);
            }
            secIccLockManager.updateMenu();
            secIccLockManager.resetIccLockData();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SetSimPersoEnabled extends AsyncTask {
        public final String mPin;
        public final boolean mState;
        public final int mSubId;

        public SetSimPersoEnabled(int i, boolean z, String str) {
            this.mState = z;
            this.mPin = str;
            this.mSubId = i;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                return Boolean.valueOf(
                        SecIccLockManager.this.mSemTelephony.setIccSimPersoEnabledForSubId(
                                this.mSubId, this.mState, this.mPin));
            } catch (Exception unused) {
                int i = SecIccLockManager.$r8$clinit;
                Log.i(
                        "SecIccLockSettings.SecIccLockManager",
                        "setIccSimPersoEnabled fail. exception occured");
                return Boolean.FALSE;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            SecIccLockManager secIccLockManager = SecIccLockManager.this;
            boolean booleanValue = ((Boolean) obj).booleanValue();
            secIccLockManager.getClass();
            Log.d("SecIccLockSettings.SecIccLockManager", "simPersoEnable : " + booleanValue);
            if (!booleanValue) {
                Context context = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context, R.string.unlock_phone_to_current_sim_fail_toast, context, 0);
            } else if (secIccLockManager.mIccToState) {
                SharedPreferences.Editor edit =
                        secIccLockManager
                                .mContext
                                .getSharedPreferences("sim_perso_prefs", 0)
                                .edit();
                edit.putBoolean("sim_perso_used", true);
                edit.apply();
                Context context2 = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context2, R.string.lock_phone_to_current_sim_success_toast, context2, 0);
            } else {
                Context context3 = secIccLockManager.mContext;
                ApnPreference$$ExternalSyntheticOutline0.m(
                        context3, R.string.unlock_phone_to_current_sim_success_toast, context3, 0);
            }
            if (SecSimPersoDialog.isDialogOpen()) {
                SecSimPersoDialog.releaseDialog();
            }
            secIccLockManager.updateMenu();
            secIccLockManager.resetIccLockData();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean iccMenuEnabled() {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.security.SecIccLockManager.iccMenuEnabled():boolean");
    }

    public final boolean isCTCSlot1() {
        return this.mSlotIndex == 0 && Rune.isChinaCTCModel();
    }

    public final boolean isEuicc() {
        for (UiccCardInfo uiccCardInfo : this.mTelephonyManager.getUiccCardsInfo()) {
            Iterator<UiccPortInfo> it = uiccCardInfo.getPorts().iterator();
            while (it.hasNext()) {
                if (it.next().getLogicalSlotIndex() == this.mSlotIndex && uiccCardInfo.isEuicc()) {
                    RecyclerView$$ExternalSyntheticOutline0.m(
                            new StringBuilder("isEuicc() :"),
                            this.mSlotIndex,
                            " : true",
                            "SecIccLockSettings.SecIccLockManager");
                    return true;
                }
            }
        }
        RecyclerView$$ExternalSyntheticOutline0.m(
                new StringBuilder("isEuicc() :"),
                this.mSlotIndex,
                " : false",
                "SecIccLockSettings.SecIccLockManager");
        return false;
    }

    public final void resetIccLockData() {
        this.mIccToState = false;
        this.mErrorMessage = null;
        this.mIccLockMode = 0;
        this.mOldPin = null;
        this.mNewPin = null;
        this.mPin = null;
        Log.i("SecIccLockSettings.SecIccLockManager", "resetIccLockData()");
    }

    public final void setIccLockMode(int i) {
        this.mIccLockMode = i;
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("ICC MODE : "),
                this.mIccLockMode,
                "SecIccLockSettings.SecIccLockManager");
    }

    public final void setSimInformation(int i) {
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                SubscriptionManager.from(this.mContext).getActiveSubscriptionInfoForSimSlotIndex(i);
        this.mSlotIndex = i;
        this.mSubscriptionId =
                activeSubscriptionInfoForSimSlotIndex != null
                        ? activeSubscriptionInfoForSimSlotIndex.getSubscriptionId()
                        : -1;
        this.mTelephonyManager =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mSubscriptionId);
        StringBuilder sb = new StringBuilder("set SIM Info : ");
        sb.append(this.mSlotIndex);
        sb.append(", sub Id : ");
        TooltipPopup$$ExternalSyntheticOutline0.m(
                sb, this.mSubscriptionId, "SecIccLockSettings.SecIccLockManager");
    }

    public final void updateMenu() {
        MenuControllerInterface menuControllerInterface = this.mMenuController;
        if (menuControllerInterface != null) {
            ((SecIccLockSettings) menuControllerInterface).updatePreferenceStates();
        }
    }
}
