package com.android.settings.network.apn;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.SecEditTextPreference;
import androidx.preference.SecListPreference;
import androidx.preference.SwitchPreference;

import com.android.internal.util.ArrayUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.connection.SecSimFeatureProvider;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.csc.CscParser;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.settings.ImsProfile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ApnEditor extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener, View.OnKeyListener {
    static final int APN_INDEX = 2;
    public static final String[] APN_TYPES;
    static final int CARRIER_ENABLED_INDEX = 17;
    static final int MCC_INDEX = 9;
    static final int MENU_DELETE = 1;
    public static final boolean MHSDBG;
    static final int MNC_INDEX = 10;
    static final int NAME_INDEX = 1;
    static final int PROTOCOL_INDEX = 16;
    static final int ROAMING_PROTOCOL_INDEX = 20;
    static final int TYPE_INDEX = 15;
    static String sNotSet;
    public static final String[] sProjection;
    SecEditTextPreference mApn;
    ApnData mApnData;
    public ApnSettingsObserver mApnSettingsObserver;
    SecEditTextPreference mApnType;
    SecListPreference mAuthType;
    MultiSelectListPreference mBearerMulti;
    SwitchPreference mCarrierEnabled;
    public Uri mCarrierUri;
    public String mCurMcc;
    public String mCurMnc;
    String mDefaultApnProtocol;
    String mDefaultApnRoamingProtocol;
    String[] mDefaultApnTypes;
    public boolean mEditable;
    public IntentFilter mHotSwapStateFilter;
    public boolean mIsCarrierIdApn;
    public boolean mIsEditableMode;
    SecEditTextPreference mMcc;
    SecEditTextPreference mMmsPort;
    SecEditTextPreference mMmsProxy;
    SecEditTextPreference mMmsc;
    SecEditTextPreference mMnc;
    SecEditTextPreference mMvnoMatchData;
    public String mMvnoMatchDataStr;
    SecListPreference mMvnoType;
    public String mMvnoTypeStr;
    SecEditTextPreference mName;
    public boolean mNewApn;
    SecEditTextPreference mPassword;
    SecEditTextPreference mPort;
    SecListPreference mProtocol;
    SecEditTextPreference mProxy;
    ProxySubscriptionManager mProxySubscriptionMgr;
    public boolean mReadOnlyApn;
    public String[] mReadOnlyApnFields;
    String[] mReadOnlyApnTypes;
    SecListPreference mRoamingProtocol;
    public SecSimFeatureProvider mSecSimFeatureProvider;
    SecEditTextPreference mServer;
    public int mSubId;
    SecEditTextPreference mUser;
    public final String KEY_APN = "apn_apn";
    public boolean mIsNoWarning = false;
    public int mSimSlot = 0;
    public int mBearerInitialVal = 0;
    public boolean mHasMdmEditedApn = false;
    public boolean mHasUserEditedApn = false;
    public final AnonymousClass1 mAirplaneModeEnabledObserver =
            new ContentObserver(
                    new Handler()) { // from class: com.android.settings.network.apn.ApnEditor.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    ApnEditor apnEditor = ApnEditor.this;
                    boolean z2 = ApnEditor.MHSDBG;
                    if (Settings.Global.getInt(
                                    apnEditor.getContentResolver(), "airplane_mode_on", 0)
                            == 1) {
                        ApnEditor.this.finish();
                    }
                }
            };
    public AnonymousClass2 mHotSwapReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.network.apn.ApnEditor.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("com.samsung.intent.action.SIMHOTSWAP".equals(intent.getAction())) {
                        boolean z = ApnEditor.MHSDBG;
                        Log.d("ApnEditor", "FINISH : HOTSWAP");
                        ApnEditor.this.finish();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ApnSettingsObserver extends ContentObserver {
        public ApnSettingsObserver(Handler handler) {
            super(handler);
            boolean z = ApnEditor.MHSDBG;
            ContentResolver contentResolver = ApnEditor.this.getContentResolver();
            Uri uri = ApnEditor.this.mApnData.mUri;
            contentResolver.registerContentObserver(
                    uri == null ? ApnEditor.this.mCarrierUri : uri, false, this);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            if (z) {
                return;
            }
            ApnEditor.this.mHasMdmEditedApn = true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ErrorDialog extends InstrumentedDialogFragment {
        public static int mDialogId;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return FileType.GLTF;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            String validateApnData = ((ApnEditor) getTargetFragment()).validateApnData();
            int i = mDialogId;
            if (i == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.error_title);
                builder.setPositiveButton(R.string.dlg_ok, (DialogInterface.OnClickListener) null);
                builder.P.mMessage = validateApnData;
                builder.setNegativeButton(
                        R.string.profile_exit,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.network.apn.ApnEditor.ErrorDialog.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                ((ApnEditor) ErrorDialog.this.getTargetFragment()).finish();
                            }
                        });
                return builder.create();
            }
            if (i != 10) {
                return null;
            }
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
            builder2.setTitle(R.string.wifi_error);
            builder2.setPositiveButton(R.string.dlg_ok, (DialogInterface.OnClickListener) null);
            builder2.setMessage(R.string.error_unavailable_apn);
            return builder2.create();
        }
    }

    static {
        MHSDBG = ApnSettings.DEBUG || Debug.semIsProductDev();
        "eng".equals(Build.TYPE);
        APN_TYPES =
                new String[] {
                    "default",
                    "mms",
                    "supl",
                    "dun",
                    "hipri",
                    "fota",
                    ImsProfile.PDN_IMS,
                    "cbs",
                    "ia",
                    ImsProfile.PDN_EMERGENCY,
                    "mcx",
                    ImsProfile.PDN_XCAP
                };
        sProjection =
                new String[] {
                    "_id",
                    "name",
                    "apn",
                    "proxy",
                    HostAuth.PORT,
                    "user",
                    "server",
                    HostAuth.PASSWORD,
                    "mmsc",
                    "mcc",
                    "mnc",
                    "numeric",
                    "mmsproxy",
                    "mmsport",
                    "authtype",
                    "type",
                    "protocol",
                    "carrier_enabled",
                    "bearer",
                    "bearer_bitmask",
                    "roaming_protocol",
                    "mvno_type",
                    "mvno_match_data",
                    "edited",
                    "user_editable",
                    "carrier_id"
                };
    }

    public static boolean apnTypesMatch(String str, String[] strArr) {
        if (ArrayUtils.isEmpty(strArr)) {
            return false;
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = strArr[i].toLowerCase();
        }
        if (ArrayUtils.isEmpty(strArr2)) {
            if (!TextUtils.isEmpty(str)) {
                List asList = Arrays.asList(strArr2);
                for (String str2 : str.split(",")) {
                    if (asList.contains(str2.trim().toLowerCase())) {
                        Log.d(
                                "ApnEditor",
                                "apnTypesMatch: true because match found for " + str2.trim());
                        return true;
                    }
                }
                Log.d("ApnEditor", "apnTypesMatch: false");
                return false;
            }
        } else if (Arrays.asList(strArr2).contains("*")) {
            Log.d("ApnEditor", "hasAllApns: true because apnList.contains(APN_TYPE_ALL)");
        } else {
            Log.d("ApnEditor", "hasAllApns: true");
        }
        return true;
    }

    public static String checkNotSet(String str) {
        if (sNotSet.equals(str)) {
            return null;
        }
        return str;
    }

    public static String checkNull(String str) {
        return TextUtils.isEmpty(str) ? sNotSet : str;
    }

    public static String checkNullArgument(String str) {
        return TextUtils.isEmpty(str)
                ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE
                : str;
    }

    public static String formatInteger(String str) {
        try {
            return String.format(
                    str.length() == 2 ? "%02d" : "%03d", Integer.valueOf(Integer.parseInt(str)));
        } catch (NumberFormatException unused) {
            return str;
        }
    }

    public static String starify(String str) {
        if (str == null || str.length() == 0) {
            return sNotSet;
        }
        int length = str.length();
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = '*';
        }
        return new String(cArr);
    }

    public final String bearerMultiDescription(Set set) {
        int i;
        String[] stringArray = getResources().getStringArray(R.array.bearer_entries);
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        boolean z = true;
        while (it.hasNext()) {
            String str = (String) it.next();
            MultiSelectListPreference multiSelectListPreference = this.mBearerMulti;
            if (str != null) {
                CharSequence[] charSequenceArr = multiSelectListPreference.mEntryValues;
                if (charSequenceArr != null) {
                    i = charSequenceArr.length - 1;
                    while (i >= 0) {
                        if (TextUtils.equals(
                                multiSelectListPreference.mEntryValues[i].toString(), str)) {
                            break;
                        }
                        i--;
                    }
                }
            } else {
                multiSelectListPreference.getClass();
            }
            i = -1;
            if (z) {
                try {
                    sb.append(stringArray[i]);
                    z = false;
                } catch (ArrayIndexOutOfBoundsException unused) {
                }
            } else {
                sb.append(", " + stringArray[i]);
            }
        }
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            return null;
        }
        return sb2;
    }

    public final void disableAllFields() {
        Log.i("ApnEditor", "disableAllFields");
        this.mName.setEnabled(false);
        this.mApn.setEnabled(false);
        this.mProxy.setEnabled(false);
        this.mPort.setEnabled(false);
        this.mUser.setEnabled(false);
        this.mServer.setEnabled(false);
        this.mPassword.setEnabled(false);
        this.mMmsProxy.setEnabled(false);
        this.mMmsPort.setEnabled(false);
        this.mMmsc.setEnabled(false);
        this.mMcc.setEnabled(false);
        this.mMnc.setEnabled(false);
        this.mApnType.setEnabled(false);
        this.mAuthType.setEnabled(false);
        this.mProtocol.setEnabled(false);
        this.mRoamingProtocol.setEnabled(false);
        this.mCarrierEnabled.setEnabled(false);
        this.mBearerMulti.setEnabled(false);
        this.mMvnoType.setEnabled(false);
        this.mMvnoMatchData.setEnabled(false);
    }

    public final void enableAllFields() {
        Log.i("ApnEditor", "enableAllFields");
        this.mName.setEnabled(true);
        this.mApn.setEnabled(true);
        this.mProxy.setEnabled(true);
        this.mPort.setEnabled(true);
        this.mUser.setEnabled(true);
        this.mServer.setEnabled(true);
        this.mPassword.setEnabled(true);
        this.mMmsProxy.setEnabled(true);
        this.mMmsPort.setEnabled(true);
        this.mMmsc.setEnabled(true);
        this.mMcc.setEnabled(true);
        this.mMnc.setEnabled(true);
        this.mApnType.setEnabled(true);
        this.mAuthType.setEnabled(true);
        this.mProtocol.setEnabled(true);
        this.mRoamingProtocol.setEnabled(true);
        this.mCarrierEnabled.setEnabled(true);
        this.mBearerMulti.setEnabled(true);
        this.mMvnoType.setEnabled(true);
        this.mMvnoMatchData.setEnabled(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:160:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillUI(boolean r20) {
        /*
            Method dump skipped, instructions count: 1772
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.fillUI(boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.settings.network.apn.ApnEditor.ApnData getApnDataFromUri(
            android.net.Uri r7) {
        /*
            r6 = this;
            android.content.ContentResolver r0 = r6.getContentResolver()
            java.lang.String[] r2 = com.android.settings.network.apn.ApnEditor.sProjection
            r4 = 0
            r5 = 0
            r3 = 0
            r1 = r7
            android.database.Cursor r6 = r0.query(r1, r2, r3, r4, r5)
            if (r6 == 0) goto L26
            boolean r0 = r6.moveToFirst()     // Catch: java.lang.Throwable -> L1c
            if (r0 == 0) goto L26
            com.android.settings.network.apn.ApnEditor$ApnData r0 = new com.android.settings.network.apn.ApnEditor$ApnData     // Catch: java.lang.Throwable -> L1c
            r0.<init>(r7, r6)     // Catch: java.lang.Throwable -> L1c
            goto L27
        L1c:
            r7 = move-exception
            r6.close()     // Catch: java.lang.Throwable -> L21
            goto L25
        L21:
            r6 = move-exception
            r7.addSuppressed(r6)
        L25:
            throw r7
        L26:
            r0 = 0
        L27:
            if (r6 == 0) goto L2c
            r6.close()
        L2c:
            java.lang.String r6 = "ApnEditor"
            if (r0 != 0) goto L42
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Can't get apnData from Uri "
            r1.<init>(r2)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            android.util.Log.d(r6, r7)
            goto L49
        L42:
            java.lang.String r7 = r0.toString()
            android.util.Log.i(r6, r7)
        L49:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.getApnDataFromUri(android.net.Uri):com.android.settings.network.apn.ApnEditor$ApnData");
    }

    public void getCarrierCustomizedConfig(Context context) {
        PersistableBundle configForSubId;
        this.mReadOnlyApn = false;
        this.mReadOnlyApnTypes = null;
        this.mReadOnlyApnFields = null;
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) context.getSystemService("carrier_config");
        if (carrierConfigManager == null
                || (configForSubId = carrierConfigManager.getConfigForSubId(this.mSubId)) == null) {
            return;
        }
        String[] stringArray = configForSubId.getStringArray("read_only_apn_types_string_array");
        this.mReadOnlyApnTypes = stringArray;
        if (!ArrayUtils.isEmpty(stringArray)) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onCreate: read only APN type: "),
                    Arrays.toString(this.mReadOnlyApnTypes),
                    "ApnEditor");
        }
        this.mReadOnlyApnFields =
                configForSubId.getStringArray("read_only_apn_fields_string_array");
        String[] stringArray2 =
                configForSubId.getStringArray("apn_settings_default_apn_types_string_array");
        this.mDefaultApnTypes = stringArray2;
        if (!ArrayUtils.isEmpty(stringArray2)) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onCreate: default apn types: "),
                    Arrays.toString(this.mDefaultApnTypes),
                    "ApnEditor");
        }
        String string = configForSubId.getString("apn.settings_default_protocol_string");
        this.mDefaultApnProtocol = string;
        if (!TextUtils.isEmpty(string)) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onCreate: default apn protocol: "),
                    this.mDefaultApnProtocol,
                    "ApnEditor");
        }
        String string2 = configForSubId.getString("apn.settings_default_roaming_protocol_string");
        this.mDefaultApnRoamingProtocol = string2;
        if (!TextUtils.isEmpty(string2)) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onCreate: default apn roaming protocol: "),
                    this.mDefaultApnRoamingProtocol,
                    "ApnEditor");
        }
        if (configForSubId.getBoolean("allow_adding_apns_bool")) {
            return;
        }
        Log.d("ApnEditor", "onCreate: not allow to add new APN");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mNewApn ? 3600 : 13;
    }

    public String getUserEnteredApnType() {
        String str = this.mApnType.mText;
        if (str != null) {
            str = str.trim();
        }
        if (TextUtils.isEmpty(str) || "*".equals(str)) {
            String[] strArr = APN_TYPES;
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (String str2 : strArr) {
                if (!str2.equals("dun")
                        && !str2.equals("ia")
                        && !str2.equals(ImsProfile.PDN_EMERGENCY)
                        && !str2.equals("mcx")
                        && !str2.equals(ImsProfile.PDN_IMS)) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",");
                    }
                    sb.append(str2);
                }
            }
            str = sb.toString();
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "getUserEnteredApnType: changed apn type to editable apn types: ",
                str,
                "ApnEditor");
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x012e, code lost:

       if (r5 != null) goto L38;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0130, code lost:

       r5.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0149, code lost:

       android.util.Log.d("ApnEditor", "isDefinedApn : " + r25 + " result : " + r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0162, code lost:

       return r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0146, code lost:

       if (r5 == null) goto L42;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isDefinedApn(java.lang.String r25) {
        /*
            Method dump skipped, instructions count: 361
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.isDefinedApn(java.lang.String):boolean");
    }

    public boolean isUserRestricted() {
        UserManager userManager = (UserManager) getContext().getSystemService(UserManager.class);
        if (userManager == null) {
            return false;
        }
        if (!userManager.isAdminUser()) {
            Log.e("ApnEditor", "User is not an admin");
            return true;
        }
        if (!userManager.hasUserRestriction("no_config_mobile_networks")) {
            return false;
        }
        Log.e("ApnEditor", "User is not allowed to configure mobile network");
        return true;
    }

    public final String mvnoDescription(String str) {
        String[] strArr;
        int findIndexOfValue = this.mMvnoType.findIndexOfValue(str);
        String str2 = this.mMvnoType.mValue;
        if (findIndexOfValue == -1) {
            return null;
        }
        String[] stringArray = getResources().getStringArray(R.array.mvno_type_entries);
        this.mMvnoMatchData.setEnabled(
                ((this.mReadOnlyApn
                                        || ((strArr = this.mReadOnlyApnFields) != null
                                                && Arrays.asList(strArr)
                                                        .contains("mvno_match_data")))
                                || findIndexOfValue == 0)
                        ? false
                        : true);
        if (str != null && !str.equals(str2)) {
            if (stringArray[findIndexOfValue].equals("SPN")) {
                TelephonyManager telephonyManager =
                        (TelephonyManager) getContext().getSystemService(TelephonyManager.class);
                TelephonyManager createForSubscriptionId =
                        telephonyManager.createForSubscriptionId(this.mSubId);
                if (createForSubscriptionId != null) {
                    telephonyManager = createForSubscriptionId;
                }
                this.mMvnoMatchData.setText(telephonyManager.getSimOperatorName());
                this.mMvnoMatchData.setSummary(telephonyManager.getSimOperatorName());
            } else {
                boolean equals = stringArray[findIndexOfValue].equals("IMSI");
                String str3 = com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE;
                if (equals) {
                    SubscriptionInfo accessibleSubscriptionInfo =
                            this.mProxySubscriptionMgr.getAccessibleSubscriptionInfo(this.mSubId);
                    String objects =
                            accessibleSubscriptionInfo == null
                                    ? com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE
                                    : Objects.toString(
                                            accessibleSubscriptionInfo.getMccString(),
                                            com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE);
                    if (accessibleSubscriptionInfo != null) {
                        str3 =
                                Objects.toString(
                                        accessibleSubscriptionInfo.getMncString(),
                                        com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE);
                    }
                    this.mMvnoMatchData.setText(objects + str3 + "x");
                    this.mMvnoMatchData.setSummary(objects + str3 + "x");
                } else if (stringArray[findIndexOfValue].equals("GID")) {
                    TelephonyManager telephonyManager2 =
                            (TelephonyManager)
                                    getContext().getSystemService(TelephonyManager.class);
                    TelephonyManager createForSubscriptionId2 =
                            telephonyManager2.createForSubscriptionId(this.mSubId);
                    if (createForSubscriptionId2 != null) {
                        telephonyManager2 = createForSubscriptionId2;
                    }
                    this.mMvnoMatchData.setText(telephonyManager2.getGroupIdLevel1());
                    this.mMvnoMatchData.setSummary(telephonyManager2.getGroupIdLevel1());
                } else {
                    this.mMvnoMatchData.setText(
                            com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE);
                }
            }
        }
        Log.i("ApnEditor", "mMvnoMatchData : " + this.mMvnoMatchData);
        try {
            return stringArray[findIndexOfValue];
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x042c, code lost:

       if (r6.equals("type") != false) goto L113;
    */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x042f, code lost:

       r6 = r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0438, code lost:

       if (r6.equals(com.samsung.android.knox.accounts.HostAuth.PORT) != false) goto L117;
    */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x043b, code lost:

       r6 = 14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0445, code lost:

       if (r6.equals("name") != false) goto L121;
    */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0448, code lost:

       r6 = '\r';
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0452, code lost:

       if (r6.equals("mmsc") != false) goto L125;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0455, code lost:

       r6 = '\f';
    */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x045f, code lost:

       if (r6.equals("mnc") != false) goto L129;
    */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0462, code lost:

       r6 = 11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x046c, code lost:

       if (r6.equals("mcc") != false) goto L133;
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0470, code lost:

       r6 = '\n';
    */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x047a, code lost:

       if (r6.equals("apn") != false) goto L137;
    */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x047e, code lost:

       r6 = '\t';
    */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0486, code lost:

       if (r6.equals("mvno_match_data") != false) goto L141;
    */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x048a, code lost:

       r6 = '\b';
    */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0494, code lost:

       if (r6.equals("server") != false) goto L145;
    */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0498, code lost:

       r6 = 7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x04a0, code lost:

       if (r6.equals("protocol") != false) goto L149;
    */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x04a4, code lost:

       r6 = 6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x04ac, code lost:

       if (r6.equals("roaming_protocol") != false) goto L153;
    */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x04b0, code lost:

       r6 = 5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x04b8, code lost:

       if (r6.equals("bearer_bitmask") != false) goto L157;
    */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x04bc, code lost:

       r6 = 4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x04c4, code lost:

       if (r6.equals("bearer") != false) goto L161;
    */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x04c8, code lost:

       r6 = 3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x04ce, code lost:

       if (r6.equals("carrier_enabled") != false) goto L165;
    */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x04d2, code lost:

       r6 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x04da, code lost:

       if (r6.equals("mmsproxy") != false) goto L169;
    */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x04de, code lost:

       r6 = 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x04e4, code lost:

       if (r6.equals("mvno_type") != false) goto L173;
    */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x04e8, code lost:

       r6 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0531, code lost:

       r0 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x053a, code lost:

       if (r0 >= getPreferenceScreen().getPreferenceCount()) goto L223;
    */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x053c, code lost:

       getPreferenceScreen().getPreference(r0).setOnPreferenceChangeListener(r22);
       r0 = r0 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0549, code lost:

       if (r23 != null) goto L205;
    */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x054b, code lost:

       r7 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x054c, code lost:

       fillUI(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x054f, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x03a9, code lost:

       android.util.Log.d("ApnEditor", "onCreate: apnTypesMatch; read-only APN");
       r22.mReadOnlyApn = true;
       disableAllFields();
    */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x03b5, code lost:

       r4 = 15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x034b, code lost:

       if (r3 == null) goto L75;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x032a, code lost:

       if (r3 == null) goto L75;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x032c, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x034e, code lost:

       android.util.Log.e("ApnEditor", "isEditable  : " + r5);
       r22.mEditable = r5;
       android.util.Log.d("ApnEditor", "mEditable :" + r22.mEditable);
       android.util.Log.d("ApnEditor", "onCreate: EDITED " + r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0385, code lost:

       if (r4 != false) goto L82;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0397, code lost:

       if (r22.mApnData.getInteger(24, 1).intValue() == 0) goto L81;
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0399, code lost:

       r4 = 15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x03a7, code lost:

       if (apnTypesMatch(r22.mApnData.getString(15), r22.mReadOnlyApnTypes) == false) goto L83;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x03bd, code lost:

       if (com.android.internal.util.ArrayUtils.isEmpty(r22.mReadOnlyApnFields) != false) goto L199;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x03bf, code lost:

       r0 = r22.mReadOnlyApnFields;
       r3 = r0.length;
       r5 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x03c3, code lost:

       if (r5 >= r3) goto L220;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x03c5, code lost:

       r6 = r0[r5];
       android.util.Log.i("ApnEditor", "disable apnFields: " + r6);
       r6.getClass();
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x03df, code lost:

       switch(r6.hashCode()) {
           case -2135515857: goto L170;
           case -1954254981: goto L166;
           case -1640523526: goto L162;
           case -1393032351: goto L158;
           case -1230508389: goto L154;
           case -1039601666: goto L150;
           case -989163880: goto L146;
           case -905826493: goto L142;
           case -520149991: goto L138;
           case 96799: goto L134;
           case 107917: goto L130;
           case 108258: goto L126;
           case 3355632: goto L122;
           case 3373707: goto L118;
           case 3446913: goto L114;
           case 3575610: goto L110;
           case 3599307: goto L106;
           case 106941038: goto L102;
           case 1183882708: goto L98;
           case 1216985755: goto L94;
           case 1433229538: goto L90;
           default: goto L89;
       };
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x03e2, code lost:

       r6 = 65535;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x04e9, code lost:

       switch(r6) {
           case 0: goto L195;
           case 1: goto L194;
           case 2: goto L193;
           case 3: goto L192;
           case 4: goto L192;
           case 5: goto L191;
           case 6: goto L190;
           case 7: goto L189;
           case 8: goto L188;
           case 9: goto L187;
           case 10: goto L186;
           case 11: goto L185;
           case 12: goto L184;
           case 13: goto L183;
           case 14: goto L182;
           case 15: goto L181;
           case 16: goto L180;
           case 17: goto L179;
           case 18: goto L178;
           case 19: goto L177;
           case 20: goto L176;
           default: goto L175;
       };
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x04ec, code lost:

       r6 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0529, code lost:

       if (r6 == null) goto L222;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x052b, code lost:

       r6.setEnabled(false);
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x052e, code lost:

       r5 = r5 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x04ee, code lost:

       r6 = r22.mAuthType;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x04f1, code lost:

       r6 = r22.mPassword;
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x04f4, code lost:

       r6 = r22.mMmsPort;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x04f7, code lost:

       r6 = r22.mProxy;
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x04fa, code lost:

       r6 = r22.mUser;
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x04fd, code lost:

       r6 = r22.mApnType;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0500, code lost:

       r6 = r22.mPort;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0503, code lost:

       r6 = r22.mName;
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0506, code lost:

       r6 = r22.mMmsc;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0509, code lost:

       r6 = r22.mMnc;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x050c, code lost:

       r6 = r22.mMcc;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x050f, code lost:

       r6 = r22.mApn;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0512, code lost:

       r6 = r22.mMvnoMatchData;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0515, code lost:

       r6 = r22.mServer;
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0518, code lost:

       r6 = r22.mProtocol;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x051b, code lost:

       r6 = r22.mRoamingProtocol;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x051e, code lost:

       r6 = r22.mBearerMulti;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0521, code lost:

       r6 = r22.mCarrierEnabled;
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0524, code lost:

       r6 = r22.mMmsProxy;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0527, code lost:

       r6 = r22.mMvnoType;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x03eb, code lost:

       if (r6.equals("authtype") != false) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x03ee, code lost:

       r6 = 20;
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x03f8, code lost:

       if (r6.equals(com.samsung.android.knox.accounts.HostAuth.PASSWORD) != false) goto L97;
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x03fb, code lost:

       r6 = 19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0405, code lost:

       if (r6.equals("mmsport") != false) goto L101;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0408, code lost:

       r6 = 18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0412, code lost:

       if (r6.equals("proxy") != false) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0415, code lost:

       r6 = 17;
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x041f, code lost:

       if (r6.equals("user") != false) goto L109;
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0422, code lost:

       r6 = 16;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0552  */
    /* JADX WARN: Type inference failed for: r3v74, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v75 */
    /* JADX WARN: Type inference failed for: r3v77, types: [android.database.Cursor] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r23) {
        /*
            Method dump skipped, instructions count: 1510
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.onCreate(android.os.Bundle):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:

       if (r6.isSPRSimInserted(r10) == false) goto L53;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0079, code lost:

       if (com.android.settings.Utils.MHSDBG != false) goto L53;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0094, code lost:

       if ("LTE INTERNET".equals(r3) == false) goto L53;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00aa, code lost:

       if ("LTE INTERNET".equals(r3) == false) goto L53;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b7, code lost:

       if (r6.isSPRSimInserted(r10) == false) goto L53;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d2, code lost:

       if (r2 != false) goto L53;
    */
    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreateOptionsMenu(android.view.Menu r9, android.view.MenuInflater r10) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.onCreateOptionsMenu(android.view.Menu,"
                    + " android.view.MenuInflater):void");
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (!validateAndSaveApnData()) {
            return true;
        }
        finish();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            if (this.mApnData.mUri != null) {
                if (getContentResolver().delete(this.mApnData.mUri, null, null) > 0) {
                    AuditLog.log(
                            5, 5, true, Process.myPid(), "ApnEditor", "Deleting APN succeeded");
                } else {
                    AuditLog.log(5, 5, false, Process.myPid(), "ApnEditor", "Deleting APN failed");
                }
                this.mApnData = new ApnData(sProjection.length);
            }
            finish();
            return true;
        }
        if (itemId != 2) {
            if (itemId == 3) {
                LoggingHelper.insertEventLogging(getMetricsCategory(), 3619);
                finish();
                return true;
            }
            if (itemId != 16908332) {
                return super.onOptionsItemSelected(menuItem);
            }
        }
        LoggingHelper.insertEventLogging(getMetricsCategory(), 3601);
        if (validateAndSaveApnData()) {
            finish();
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        getContentResolver().unregisterContentObserver(this.mAirplaneModeEnabledObserver);
        if (this.mApnSettingsObserver != null) {
            getContentResolver().unregisterContentObserver(this.mApnSettingsObserver);
            this.mApnSettingsObserver = null;
        }
        if (this.mHotSwapReceiver != null) {
            getActivity().unregisterReceiver(this.mHotSwapReceiver);
            this.mHotSwapReceiver = null;
        }
        super.onPause();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x007c, code lost:

       if ("endo".equalsIgnoreCase(r1) == false) goto L68;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00fe, code lost:

       if ("tethering".equals(r1) != false) goto L28;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x010e, code lost:

       if (r1.contains(".au-net.ne.jp") != false) goto L28;
    */
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onPreferenceChange(
            androidx.preference.Preference r9, java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 576
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.onPreferenceChange(androidx.preference.Preference,"
                    + " java.lang.Object):boolean");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if ("apn_name".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3602);
        } else if ("apn_apn".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3603);
        } else if ("apn_http_proxy".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3604);
        } else if ("apn_http_port".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3605);
        } else if ("apn_user".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3606);
        } else if ("apn_password".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3607);
        } else if ("apn_server".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3608);
        } else if ("apn_mmsc".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3609);
        } else if ("apn_mms_proxy".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3610);
        } else if ("apn_mms_port".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3611);
        } else if ("apn_mcc".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3612);
        } else if ("apn_mnc".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3613);
        } else if ("apn_type".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3615);
        } else if (UniversalCredentialManager.BUNDLE_EXTRA_AUTH_TYPE.equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3614);
        } else if ("apn_protocol".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3616);
        } else if ("apn_roaming_protocol".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3617);
        } else if ("carrier_enabled".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3618);
        } else if ("bearer_multi".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3620);
        } else if ("mvno_type".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3621);
        } else if ("mvno_match_data".equals(key)) {
            LoggingHelper.insertEventLogging(getMetricsCategory(), 3622);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        EditText editText;
        super.onResume();
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("airplane_mode_on"),
                        true,
                        this.mAirplaneModeEnabledObserver);
        if (this.mHasMdmEditedApn) {
            fillUI(true);
            this.mHasMdmEditedApn = false;
        }
        this.mApnSettingsObserver = new ApnSettingsObserver(new Handler());
        this.mHotSwapStateFilter = new IntentFilter("com.samsung.intent.action.SIMHOTSWAP");
        getActivity().registerReceiver(this.mHotSwapReceiver, this.mHotSwapStateFilter, 4);
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback() { // from class:
                                                      // com.android.settings.network.apn.ApnEditor.3
                            @Override // androidx.activity.OnBackPressedCallback
                            public final void handleOnBackPressed() {
                                ApnEditor apnEditor = ApnEditor.this;
                                if (apnEditor.validateAndSaveApnData()) {
                                    apnEditor.finish();
                                }
                            }
                        });
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if ((preference instanceof SecEditTextPreference)
                    && (editText = ((SecEditTextPreference) preference).getEditText()) != null) {
                editText.setInputType(16385);
                editText.requestFocus();
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            bundle.putBoolean("user_edited_apn", this.mHasUserEditedApn);
            bundle.putBoolean("mdm_edited_apn", this.mHasMdmEditedApn);
            bundle.putString("current_mnc", this.mCurMnc);
            bundle.putString("current_mcc", this.mCurMcc);
        }
    }

    public final String protocolDescription(String str, SecListPreference secListPreference) {
        String upperCase = checkNull(str).toUpperCase();
        if (upperCase.equals("IPV4")) {
            upperCase = com.samsung.android.knox.net.apn.ApnSettings.PROTOCOL_IPV4;
        }
        int findIndexOfValue = secListPreference.findIndexOfValue(upperCase);
        if (findIndexOfValue == -1) {
            return null;
        }
        try {
            return getResources().getStringArray(R.array.apn_protocol_entries)[findIndexOfValue];
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    public final boolean setIntValueAndCheckIfDiff(
            ContentValues contentValues, String str, int i, boolean z, int i2) {
        boolean z2 = z || i != ((Integer) this.mApnData.mData[i2]).intValue();
        if (z2) {
            contentValues.put(str, Integer.valueOf(i));
        }
        return z2;
    }

    public final boolean setStringValueAndCheckIfDiff(
            ContentValues contentValues, String str, String str2, boolean z, int i) {
        String string = this.mApnData.getString(i);
        boolean z2 =
                z
                        || (!(TextUtils.isEmpty(str2) && TextUtils.isEmpty(string))
                                && (str2 == null || !str2.equals(string)));
        if (z2 && str2 != null) {
            contentValues.put(str, str2);
        }
        return z2;
    }

    public void showError() {
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.setTargetFragment(this, 0);
        errorDialog.show(getFragmentManager(), "error");
    }

    public boolean validateAndSaveApnData() {
        String checkNotSet;
        int i;
        boolean z;
        int i2;
        if (!this.mEditable) {
            Log.d("ApnEditor", "Read Only Apn Skip");
            return true;
        }
        boolean z2 = this.mHasUserEditedApn;
        if (!z2) {
            Log.d("ApnEditor", "mHasUserEditedApn");
            return true;
        }
        if (this.mHasMdmEditedApn && !z2) {
            Log.d("ApnEditor", "mHasMdmEditedApn / mHasUserEditedApn");
            return true;
        }
        if (Rune.isSprModel()
                && !this.mIsEditableMode
                && this.mApnData.getInteger(23, -1).intValue() == 4) {
            Log.d("ApnEditor", "carrier edit 4, do not update apn!");
            return true;
        }
        String checkNotSet2 = checkNotSet(this.mName.mText);
        String checkNotSet3 = checkNotSet(this.mApn.mText);
        String checkNotSet4 = checkNotSet(this.mMcc.mText);
        String checkNotSet5 = checkNotSet(this.mMnc.mText);
        if (validateApnData() != null) {
            showError();
            AuditLog.log(5, 5, false, Process.myPid(), "ApnEditor", "Saving APN failed");
            return false;
        }
        String simOperator = ConnectionsUtils.getSimOperator(getContext(), this.mSimSlot);
        if ((CscParser.getOmcPath().contains("AIO")
                        || CscParser.getOmcPath().contains("APP")
                        || CscParser.getOmcPath().contains("ATT")
                        || CscParser.getOmcPath().contains("XAR")
                        || Rune.isDomesticKTTModel()
                        || "45003".equals(simOperator)
                        || "45005".equals(simOperator))
                && (checkNotSet = checkNotSet(this.mApnType.mText)) != null
                && checkNotSet.toLowerCase().contains("dun")) {
            Log.d("ApnEditor", "dun Error!");
            ErrorDialog.mDialogId = 10;
            ErrorDialog errorDialog = new ErrorDialog();
            errorDialog.setTargetFragment(this, 0);
            errorDialog.show(getFragmentManager(), "error");
            return false;
        }
        final ContentValues contentValues = new ContentValues();
        boolean stringValueAndCheckIfDiff =
                setStringValueAndCheckIfDiff(
                        contentValues,
                        "mmsc",
                        checkNotSet(this.mMmsc.mText),
                        setStringValueAndCheckIfDiff(
                                contentValues,
                                HostAuth.PASSWORD,
                                checkNotSet(this.mPassword.mText),
                                setStringValueAndCheckIfDiff(
                                        contentValues,
                                        "server",
                                        checkNotSet(this.mServer.mText),
                                        setStringValueAndCheckIfDiff(
                                                contentValues,
                                                "user",
                                                checkNotSet(this.mUser.mText),
                                                setStringValueAndCheckIfDiff(
                                                        contentValues,
                                                        "mmsport",
                                                        checkNotSet(this.mMmsPort.mText),
                                                        setStringValueAndCheckIfDiff(
                                                                contentValues,
                                                                "mmsproxy",
                                                                checkNotSet(this.mMmsProxy.mText),
                                                                setStringValueAndCheckIfDiff(
                                                                        contentValues,
                                                                        HostAuth.PORT,
                                                                        checkNotSet(
                                                                                this.mPort.mText),
                                                                        setStringValueAndCheckIfDiff(
                                                                                contentValues,
                                                                                "proxy",
                                                                                checkNotSet(
                                                                                        this.mProxy
                                                                                                .mText),
                                                                                setStringValueAndCheckIfDiff(
                                                                                        contentValues,
                                                                                        "apn",
                                                                                        checkNotSet3,
                                                                                        setStringValueAndCheckIfDiff(
                                                                                                contentValues,
                                                                                                "name",
                                                                                                checkNotSet2,
                                                                                                this
                                                                                                        .mNewApn,
                                                                                                1),
                                                                                        2),
                                                                                3),
                                                                        4),
                                                                12),
                                                        13),
                                                5),
                                        6),
                                7),
                        8);
        String str = this.mAuthType.mValue;
        if (str != null) {
            stringValueAndCheckIfDiff =
                    setIntValueAndCheckIfDiff(
                            contentValues,
                            "authtype",
                            Integer.parseInt(str),
                            stringValueAndCheckIfDiff,
                            14);
        }
        boolean stringValueAndCheckIfDiff2 =
                setStringValueAndCheckIfDiff(
                        contentValues,
                        "roaming_protocol",
                        checkNotSet(this.mRoamingProtocol.mValue),
                        setStringValueAndCheckIfDiff(
                                contentValues,
                                "protocol",
                                checkNotSet(this.mProtocol.mValue),
                                stringValueAndCheckIfDiff,
                                16),
                        20);
        boolean stringValueAndCheckIfDiff3 =
                setStringValueAndCheckIfDiff(
                        contentValues,
                        "mnc",
                        checkNotSet5,
                        setStringValueAndCheckIfDiff(
                                contentValues,
                                "mcc",
                                checkNotSet4,
                                (Rune.isDomesticKTTModel()
                                                && TextUtils.isEmpty(this.mApnType.mText))
                                        ? setStringValueAndCheckIfDiff(
                                                contentValues,
                                                "type",
                                                "default,mms,supl",
                                                stringValueAndCheckIfDiff2,
                                                15)
                                        : setStringValueAndCheckIfDiff(
                                                contentValues,
                                                "type",
                                                checkNotSet(getUserEnteredApnType()),
                                                stringValueAndCheckIfDiff2,
                                                15),
                                9),
                        10);
        contentValues.put("numeric", checkNotSet4 + checkNotSet5);
        String str2 = this.mCurMnc;
        if (str2 != null
                && this.mCurMcc != null
                && str2.equals(checkNotSet5)
                && this.mCurMcc.equals(checkNotSet4)) {
            if (((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider).isMultiSimModel()
                    && this.mSimSlot == 1) {
                contentValues.put("current1", (Integer) 1);
                contentValues.put("sim_slot", (Integer) 1);
            } else {
                contentValues.put("current", (Integer) 1);
                contentValues.put("sim_slot", (Integer) 0);
            }
        }
        Iterator it = ((HashSet) this.mBearerMulti.mValues).iterator();
        int i3 = 0;
        while (true) {
            if (!it.hasNext()) {
                i = i3;
                break;
            }
            String str3 = (String) it.next();
            if (Integer.parseInt(str3) == 0) {
                i = 0;
                break;
            }
            int parseInt = Integer.parseInt(str3);
            i3 |= parseInt >= 1 ? 1 << (parseInt - 1) : 0;
        }
        boolean stringValueAndCheckIfDiff4 =
                setStringValueAndCheckIfDiff(
                        contentValues,
                        "mvno_match_data",
                        checkNotSet(this.mMvnoMatchData.mText),
                        setStringValueAndCheckIfDiff(
                                contentValues,
                                "mvno_type",
                                checkNotSet(this.mMvnoType.mValue),
                                setIntValueAndCheckIfDiff(
                                        contentValues,
                                        "bearer",
                                        (i == 0
                                                        || (i2 = this.mBearerInitialVal) == 0
                                                        || (i != 0
                                                                && (i2 < 1
                                                                        || ((1 << (i2 + (-1))) & i)
                                                                                == 0)))
                                                ? 0
                                                : i2,
                                        setIntValueAndCheckIfDiff(
                                                contentValues,
                                                "bearer_bitmask",
                                                i,
                                                stringValueAndCheckIfDiff3,
                                                19),
                                        18),
                                21),
                        22);
        if (Rune.isChinaModel()) {
            int intValue = this.mApnData.getInteger(23, -1).intValue();
            MainClearConfirm$$ExternalSyntheticOutline0.m(intValue, "edited : ", "ApnEditor");
            if (intValue == 0) {
                contentValues.put("edited", (Integer) 0);
            }
        }
        boolean intValueAndCheckIfDiff =
                setIntValueAndCheckIfDiff(
                        contentValues,
                        "carrier_enabled",
                        this.mCarrierEnabled.mChecked ? 1 : 0,
                        stringValueAndCheckIfDiff4,
                        17);
        contentValues.put("edited", (Integer) 1);
        if (this.mNewApn) {
            contentValues.put("roaming", (Integer) 2);
        }
        if (isDefinedApn("CSC")
                && ((this.mNewApn && isDefinedApn("Telephony"))
                        || (!this.mIsNoWarning && isDefinedApn("Telephony")))) {
            if (validateApnData() != null) {
                z = false;
                ErrorDialog.mDialogId = 0;
                ErrorDialog errorDialog2 = new ErrorDialog();
                errorDialog2.setTargetFragment(this, 0);
                errorDialog2.show(getFragmentManager(), "error");
            } else {
                z = false;
            }
            Log.i("ApnEditor", "validateAndSaveApnData() show ERROR_DIALOG_ID");
            return z;
        }
        if (intValueAndCheckIfDiff) {
            final Uri uri = this.mApnData.mUri;
            if (uri == null) {
                uri = this.mCarrierUri;
            }
            Log.i("ApnEditor", "updateApnDataToDatabase values : " + checkNotSet2);
            Log.i("ApnEditor", "updateApnDataToDatabase uri : " + uri);
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.network.apn.ApnEditor$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ApnEditor apnEditor = ApnEditor.this;
                            Uri uri2 = uri;
                            ContentValues contentValues2 = contentValues;
                            if (!uri2.equals(apnEditor.mCarrierUri)) {
                                apnEditor
                                        .getContentResolver()
                                        .update(uri2, contentValues2, null, null);
                            } else if (apnEditor
                                            .getContentResolver()
                                            .insert(apnEditor.mCarrierUri, contentValues2)
                                    == null) {
                                Log.e(
                                        "ApnEditor",
                                        "Can't add a new apn to database " + apnEditor.mCarrierUri);
                            }
                        }
                    });
        } else {
            Log.i("ApnEditor", "validateAndSaveApnData: not calling update()");
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String validateApnData() {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnEditor.validateApnData():java.lang.String");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ApnData {
        public final Object[] mData;
        public final Uri mUri;

        public ApnData(int i) {
            this.mData = new Object[i];
        }

        public final Integer getInteger(int i, Integer num) {
            Integer num2 = (Integer) this.mData[i];
            return num2 == null ? num : num2;
        }

        public final String getString(int i) {
            return (String) this.mData[i];
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                Object[] objArr = this.mData;
                if (i >= objArr.length) {
                    return sb.toString();
                }
                sb.append(ApnEditor.sProjection[i] + " : " + objArr[i].toString());
                sb.append(" ");
                i++;
            }
        }

        public ApnData(Uri uri, Cursor cursor) {
            this.mUri = uri;
            this.mData = new Object[cursor.getColumnCount()];
            for (int i = 0; i < this.mData.length; i++) {
                int type = cursor.getType(i);
                if (type == 1) {
                    this.mData[i] = Integer.valueOf(cursor.getInt(i));
                } else if (type == 2) {
                    this.mData[i] = Float.valueOf(cursor.getFloat(i));
                } else if (type == 3) {
                    this.mData[i] = cursor.getString(i);
                } else if (type != 4) {
                    this.mData[i] = null;
                } else {
                    this.mData[i] = cursor.getBlob(i);
                }
            }
        }
    }
}
