package com.android.settings;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.euicc.EuiccManager;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.enterprise.ActionDisabledByAdminDialogHelper;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.password.ConfirmDeviceCredentialActivity;
import com.android.settings.system.ResetDashboardFragment;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.general.GeneralUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.configuration.DATA;

import java.text.Collator;
import java.util.Comparator;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class MainClear extends SettingsPreferenceFragment
        implements ViewTreeObserver.OnGlobalLayoutListener {
    static final int CREDENTIAL_CONFIRM_REQUEST = 56;
    static final int KEYGUARD_REQUEST = 55;
    public static final AnonymousClass10 mAppLabelComparator = new AnonymousClass10();
    public View mContentView;
    public TextView mDescriptionView;
    CheckBox mEsimStorage;
    View mEsimStorageContainer;
    CheckBox mExternalStorage;
    public View mExternalStorageContainer;
    Button mInitiateButton;
    public boolean mPinConfirmed;
    public View mRecoveryCodeContainer;
    public Intent mRecoveryCodeIntent;
    RecoveryCodeTask mRecoveryCodeTask;
    NestedScrollView mScrollView;
    public boolean mCanEraseExternalOnFuseSystem = false;
    public String mStrSdVolumeId = null;
    public AlertDialog mResetBatteryLevelCheckDialog = null;
    public int mBatteryLevel = 0;
    public boolean mEsimCheckDefaultVal = false;
    protected final View.OnClickListener mInitiateListener = new AnonymousClass7(this, 3);
    public final AnonymousClass14 mBatteryInfoReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.MainClear.14
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                        MainClear.this.mBatteryLevel =
                                com.android.settingslib.Utils.getBatteryLevel(intent);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.MainClear$10, reason: invalid class name */
    public final class AnonymousClass10 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Collator.getInstance()
                    .compare(
                            ((TextView) ((View) obj).findViewById(R.id.title)).getText().toString(),
                            ((TextView) ((View) obj2).findViewById(R.id.title))
                                    .getText()
                                    .toString());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.MainClear$13, reason: invalid class name */
    public final class AnonymousClass13 implements DialogInterface.OnCancelListener {
        @Override // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            dialogInterface.dismiss();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.MainClear$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ MainClear this$0;
        public final /* synthetic */ Object val$input;

        public /* synthetic */ AnonymousClass2(MainClear mainClear, Object obj, int i) {
            this.$r8$classId = i;
            this.this$0 = mainClear;
            this.val$input = obj;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    if (-2113671796
                            == ((EditText) this.val$input).getText().toString().hashCode()) {
                        this.this$0.showFinalConfirmation();
                        break;
                    }
                    break;
                case 1:
                    Log.i("MainClear", "SamsungWalletDialog:click go samsung wallet");
                    try {
                        this.this$0.startActivity(
                                new Intent(
                                        "android.intent.action.VIEW",
                                        Uri.parse((String) this.val$input)));
                        break;
                    } catch (ActivityNotFoundException e) {
                        Log.e("MainClear", "SamsungWalletDialog:go to SamsungWallet fail", e);
                        return;
                    }
                default:
                    Log.i("MainClear", "SamsungWalletDialog:click skip");
                    MainClear.m648$$Nest$mcheckOtherCondition(
                            this.this$0, (Context) this.val$input);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.MainClear$7, reason: invalid class name */
    public final class AnonymousClass7 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ MainClear this$0;

        public /* synthetic */ AnonymousClass7(MainClear mainClear, int i) {
            this.$r8$classId = i;
            this.this$0 = mainClear;
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x013a  */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onClick(android.view.View r13) {
            /*
                Method dump skipped, instructions count: 368
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.MainClear.AnonymousClass7.onClick(android.view.View):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RecoveryCodeTask extends AsyncTask {
        public final Context mContext;

        public RecoveryCodeTask(FragmentActivity fragmentActivity) {
            this.mContext = fragmentActivity;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Bundle bundle;
            Intent intent = null;
            try {
                bundle =
                        this.mContext
                                .getContentResolver()
                                .call(
                                        Uri.parse(
                                                "content://com.samsung.android.scpm.e2ee.provider.e2eeprovider"),
                                        "e2ee_support",
                                        (String) null,
                                        (Bundle) null);
            } catch (Exception unused) {
                bundle = null;
            }
            boolean z = false;
            if (bundle != null) {
                z = bundle.getBoolean("e2eeSupportResult", false);
                String string = bundle.getString("deepLinkPath");
                if (!TextUtils.isEmpty(string)) {
                    intent = new Intent();
                    intent.setData(Uri.parse(string));
                }
            }
            return Pair.create(Boolean.valueOf(z), intent);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Pair pair = (Pair) obj;
            if (!((Boolean) pair.first).booleanValue() || pair.second == null) {
                return;
            }
            MainClear.this.mRecoveryCodeContainer.setVisibility(0);
            MainClear.this.mRecoveryCodeIntent = (Intent) pair.second;
        }
    }

    /* renamed from: -$$Nest$mcheckOtherCondition, reason: not valid java name */
    public static void m648$$Nest$mcheckOtherCondition(MainClear mainClear, Context context) {
        mainClear.getClass();
        if (Utils.isDemoUser(context)) {
            ComponentName deviceOwnerComponentOnAnyUser =
                    ((DevicePolicyManager) context.getSystemService("device_policy"))
                            .getDeviceOwnerComponentOnAnyUser();
            if (deviceOwnerComponentOnAnyUser != null) {
                context.startActivity(
                        new Intent()
                                .setPackage(deviceOwnerComponentOnAnyUser.getPackageName())
                                .setAction("android.intent.action.FACTORY_RESET"));
                return;
            }
            return;
        }
        if (mainClear.mBatteryLevel < 10) {
            AlertDialog alertDialog = mainClear.mResetBatteryLevelCheckDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                mainClear.mResetBatteryLevelCheckDialog = null;
                final int i = 1;
                DialogInterface.OnClickListener onClickListener =
                        new DialogInterface
                                .OnClickListener() { // from class: com.android.settings.MainClear.3
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                switch (i) {
                                    case 0:
                                        dialogInterface.cancel();
                                        break;
                                    default:
                                        dialogInterface.dismiss();
                                        break;
                                }
                            }
                        };
                AnonymousClass13 anonymousClass13 = new AnonymousClass13();
                AlertDialog.Builder builder = new AlertDialog.Builder(mainClear.getActivity());
                AlertController.AlertParams alertParams = builder.P;
                alertParams.mCancelable = true;
                alertParams.mTitle =
                        mainClear.getString(R.string.sec_icon_glossary_battery_battery_low);
                alertParams.mMessage =
                        String.format(
                                mainClear.getString(R.string.master_clear_not_enough_battery), 10);
                builder.setPositiveButton(
                        mainClear.getString(R.string.sec_common_ok).toUpperCase(), onClickListener);
                alertParams.mOnCancelListener = anonymousClass13;
                AlertDialog create = builder.create();
                mainClear.mResetBatteryLevelCheckDialog = create;
                create.show();
                return;
            }
            return;
        }
        Resources resources = mainClear.getActivity().getResources();
        ChooseLockSettingsHelper.Builder builder2 =
                new ChooseLockSettingsHelper.Builder(mainClear.getActivity(), mainClear);
        builder2.mRequestCode = 55;
        builder2.mTitle = resources.getText(R.string.main_clear_short_title);
        if (builder2.show()) {
            return;
        }
        if (!Rune.isVzwDemoMode(mainClear.getActivity())) {
            Intent accountConfirmationIntent = mainClear.getAccountConfirmationIntent();
            if (accountConfirmationIntent != null) {
                mainClear.showAccountCredentialConfirmation(accountConfirmationIntent);
                return;
            } else {
                mainClear.showFinalConfirmation();
                return;
            }
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(mainClear.getActivity());
        builder3.setTitle(R.string.store_mode_reset_title);
        builder3.setMessage(R.string.store_mode_reset_text);
        EditText editText = new EditText(mainClear.getActivity());
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        builder3.setView(editText);
        builder3.setPositiveButton(R.string.dlg_ok, new AnonymousClass2(mainClear, editText, 0));
        final int i2 = 0;
        builder3.setNegativeButton(
                R.string.dlg_cancel,
                new DialogInterface
                        .OnClickListener() { // from class: com.android.settings.MainClear.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        switch (i2) {
                            case 0:
                                dialogInterface.cancel();
                                break;
                            default:
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                });
        builder3.show();
    }

    public static void getContentDescription(View view, StringBuffer stringBuffer) {
        if (view.getVisibility() != 0) {
            return;
        }
        if (!(view instanceof ViewGroup)) {
            if (view instanceof TextView) {
                stringBuffer.append(((TextView) view).getText());
                stringBuffer.append(",");
                return;
            }
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            getContentDescription(viewGroup.getChildAt(i), stringBuffer);
        }
    }

    public static boolean isSystemAppExcludedList(PackageManager packageManager, String str) {
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_GMS_CONFIG_PERMISSION_PREGRANT_POLICY_LIST");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        for (String str2 : string.split(";")) {
            try {
                if (str.equals(str2)) {
                    return packageManager.getPackageInfo(str, 0).versionCode > 1;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d("MainClear", "Can't find package, probably uninstalled.");
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0655  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x071d  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0712 A[Catch: IOException -> 0x06c9, TRY_ENTER, TRY_LEAVE, TryCatch #13 {IOException -> 0x06c9, blocks: (B:161:0x06c5, B:237:0x06f8, B:226:0x0712), top: B:145:0x067b }] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0707 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:237:0x06f8 A[Catch: IOException -> 0x06c9, TRY_ENTER, TRY_LEAVE, TryCatch #13 {IOException -> 0x06c9, blocks: (B:161:0x06c5, B:237:0x06f8, B:226:0x0712), top: B:145:0x067b }] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x06ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x04c8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x04fa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v2, types: [android.view.LayoutInflater] */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:247:0x06ca -> B:150:0x0715). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void establishInitialState() {
        /*
            Method dump skipped, instructions count: 2156
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.MainClear.establishInitialState():void");
    }

    public Intent getAccountConfirmationIntent() {
        ActivityInfo activityInfo;
        FragmentActivity activity = getActivity();
        String string = activity.getString(R.string.account_type);
        String string2 = activity.getString(R.string.account_confirmation_package);
        String string3 = activity.getString(R.string.account_confirmation_class);
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
            Log.i("MainClear", "Resources not set for account confirmation.");
            return null;
        }
        Account[] accountsByType = AccountManager.get(activity).getAccountsByType(string);
        if (accountsByType == null || accountsByType.length <= 0) {
            Log.d("MainClear", "No " + string + " accounts installed!");
        } else {
            Intent component =
                    new Intent()
                            .setPackage(string2)
                            .setComponent(new ComponentName(string2, string3));
            ResolveInfo resolveActivity =
                    activity.getPackageManager().resolveActivity(component, 0);
            if (resolveActivity != null
                    && (activityInfo = resolveActivity.activityInfo) != null
                    && string2.equals(activityInfo.packageName)) {
                return component;
            }
            Log.i("MainClear", "Unable to resolve Activity: " + string2 + "/" + string3);
        }
        return null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ResetDashboardFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 66;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_general";
    }

    public boolean hasReachedBottom(NestedScrollView nestedScrollView) {
        if (nestedScrollView.getChildCount() < 1) {
            return true;
        }
        return nestedScrollView.getChildAt(0).getBottom()
                        - (nestedScrollView.getScrollY() + nestedScrollView.getHeight())
                <= 0;
    }

    public boolean isEuiccEnabled(Context context) {
        EuiccManager euiccManager = (EuiccManager) context.getSystemService("euicc");
        return euiccManager != null && euiccManager.isEnabled();
    }

    public boolean isValidRequestCode(int i) {
        return i == 55 || i == 56 || i == 58 || i == 57;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        onActivityResultInternal(i, i2, intent);
    }

    public void onActivityResultInternal(int i, int i2, Intent intent) {
        Intent accountConfirmationIntent;
        if (!isValidRequestCode(i) && i == 56) {
            if (i2 == -1) {
                this.mPinConfirmed = true;
            }
            Log.i("MainClear", MainClear.class.getName().concat(" blocked by isValidRequestCode "));
            return;
        }
        if (i != 55) {
            if (i == 57) {
                if (i2 == -1) {
                    showFinalConfirmation();
                    return;
                } else {
                    finish();
                    return;
                }
            }
            if (i != 58) {
                if (56 == i
                        || (accountConfirmationIntent = getAccountConfirmationIntent()) == null) {
                    showFinalConfirmation();
                    return;
                } else {
                    showAccountCredentialConfirmation(accountConfirmationIntent);
                    return;
                }
            }
        }
        if (i2 != -1) {
            establishInitialState();
            return;
        }
        int userId = getActivity().getUserId();
        FragmentActivity activity = getActivity();
        StringBuilder sb = Utils.sBuilder;
        BiometricManager biometricManager =
                (BiometricManager) activity.getSystemService(BiometricManager.class);
        Utils.BiometricStatus biometricStatus = Utils.BiometricStatus.NOT_ACTIVE;
        Utils.BiometricStatus biometricStatus2 = Utils.BiometricStatus.OK;
        if (biometricManager == null) {
            Log.e("Settings", "Biometric Manager is null.");
        } else if (Flags.mandatoryBiometrics()) {
            UserManager userManager = (UserManager) activity.getSystemService(UserManager.class);
            int canAuthenticate =
                    biometricManager.canAuthenticate(
                            userManager != null
                                    ? userManager.getCredentialOwnerProfile(userId)
                                    : userId,
                            65536);
            if (canAuthenticate == 0) {
                biometricStatus = biometricStatus2;
            } else if (canAuthenticate == 7) {
                biometricStatus = Utils.BiometricStatus.LOCKOUT;
            } else if (canAuthenticate != 20 && canAuthenticate != 21) {
                biometricStatus = Utils.BiometricStatus.ERROR;
            }
        }
        if (biometricStatus != biometricStatus2) {
            showFinalConfirmation();
            return;
        }
        UserManager userManager2 = (UserManager) getContext().getSystemService(UserManager.class);
        Resources resources = getResources();
        if (userManager2 != null) {
            userId = userManager2.getCredentialOwnerProfile(userId);
        }
        Intent intent2 = new Intent();
        if (Flags.mandatoryBiometrics()) {
            intent2.putExtra("biometric_prompt_authenticators", 65536);
        }
        intent2.putExtra(
                "biometric_prompt_negative_button_text", resources.getString(R.string.cancel));
        intent2.putExtra(
                "android.app.extra.DESCRIPTION",
                resources.getString(R.string.mandatory_biometrics_prompt_description));
        intent2.putExtra("allow_any_user", true);
        intent2.putExtra("android.intent.extra.USER_ID", userId);
        intent2.putExtra("biometric_prompt_hide_background", false);
        intent2.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                ConfirmDeviceCredentialActivity.InternalActivity.class.getName());
        startActivityForResult(intent2, 57);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View findViewById;
        Context context = getContext();
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, UserHandle.myUserId(), "no_factory_reset");
        if ((!UserManager.get(context).isAdminUser()
                        || RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                context, UserHandle.myUserId(), "no_factory_reset"))
                && !Utils.isDemoUser(context)) {
            return layoutInflater.inflate(R.layout.main_clear_disallowed_screen, (ViewGroup) null);
        }
        if (checkIfRestrictionEnforced != null) {
            AlertDialog.Builder prepareDialogBuilder =
                    new ActionDisabledByAdminDialogHelper(getActivity(), null)
                            .prepareDialogBuilder("no_factory_reset", checkIfRestrictionEnforced);
            prepareDialogBuilder.P.mOnDismissListener =
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.android.settings.MainClear$$ExternalSyntheticLambda5
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            MainClear mainClear = MainClear.this;
                            MainClear.AnonymousClass10 anonymousClass10 =
                                    MainClear.mAppLabelComparator;
                            mainClear.getActivity().finish();
                        }
                    };
            prepareDialogBuilder.show();
            return new View(getContext());
        }
        this.mEsimCheckDefaultVal = Rune.FEATURE_ESIM_CHECKBOX_DEFAULT_VALUE;
        View inflate = layoutInflater.inflate(R.layout.sec_main_clear, (ViewGroup) null);
        this.mContentView = inflate;
        this.mDescriptionView = (TextView) inflate.findViewById(R.id.tips_description);
        establishInitialState();
        Log.i("MainClear", MainClear.class.getName().concat("onCreateView "));
        ClickableSpan clickableSpan =
                new ClickableSpan() { // from class: com.android.settings.MainClear.11
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        MainClear.this.getClass();
                        LoggingHelper.insertEventLogging(66, 8022);
                        MainClear mainClear = MainClear.this;
                        mainClear.getClass();
                        try {
                            Intent launchIntentForPackage =
                                    mainClear
                                            .getContext()
                                            .getPackageManager()
                                            .getLaunchIntentForPackage("com.sec.android.easyMover");
                            if (launchIntentForPackage == null) {
                                launchIntentForPackage =
                                        new Intent("com.sec.android.easyMover.LAUNCH_SMART_SWITCH");
                                launchIntentForPackage.addFlags(268435456);
                                launchIntentForPackage.putExtra("EXTERNAL_BNR", true);
                            } else {
                                launchIntentForPackage.putExtra("EXTERNAL_BNR", true);
                            }
                            mainClear.startActivity(launchIntentForPackage);
                        } catch (ActivityNotFoundException unused) {
                            Log.e("MainClear", "not found activity");
                            Intent intent = null;
                            try {
                                Intent intent2 = new Intent();
                                try {
                                    intent2.setComponent(
                                            new ComponentName(
                                                    "com.sec.android.easyMover.Agent",
                                                    "com.sec.android.easyMover.Agent.ServiceActivity"));
                                    intent2.setAction(
                                            "com.sec.android.easyMover.Agent.action.AUTO_DOWNLOAD");
                                    intent2.putExtra("EXTERNAL_BNR", true);
                                    if (MainClear.this
                                                    .getContext()
                                                    .getPackageManager()
                                                    .resolveActivity(intent2, 0)
                                            != null) {
                                        Log.d(
                                                "MainClear",
                                                "easyMover resolveActivity is not null, start"
                                                    + " easyMover Agent, uri : "
                                                        + intent2.toString());
                                        MainClear.this.startActivity(intent2);
                                    } else {
                                        Intent intent3 =
                                                new Intent(
                                                        "android.intent.action.VIEW",
                                                        Uri.parse(
                                                                "samsungapps://ProductDetail/com.sec.android.easyMover"));
                                        try {
                                            intent3.addFlags(268435456);
                                            intent3.addFlags(
                                                    NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                            if (MainClear.this
                                                            .getContext()
                                                            .getPackageManager()
                                                            .resolveActivity(intent3, 0)
                                                    != null) {
                                                Log.d(
                                                        "MainClear",
                                                        "startMarket resolveActivity is not null,"
                                                            + " start market service, uri : "
                                                                + intent3.toString());
                                                MainClear.this.startActivity(intent3);
                                            } else {
                                                Log.d(
                                                        "MainClear",
                                                        "null resolveActivity.try again via google"
                                                            + " play");
                                                intent =
                                                        new Intent(
                                                                "android.intent.action.VIEW",
                                                                Uri.parse(
                                                                        "market://details?id=com.sec.android.easyMover"));
                                                intent.addFlags(268435456);
                                                intent.addFlags(
                                                        NetworkAnalyticsConstants.DataPoints
                                                                .FLAG_UID);
                                                MainClear.this.startActivity(intent);
                                            }
                                        } catch (ActivityNotFoundException e) {
                                            e = e;
                                            intent = intent3;
                                            Log.d(
                                                    "MainClear",
                                                    "linkToMarket got an error, uri : "
                                                            + intent.toString());
                                            Log.e(
                                                    "MainClear",
                                                    "Can not link to market, Exception e: "
                                                            + e.getMessage().toString());
                                        }
                                    }
                                } catch (ActivityNotFoundException e2) {
                                    e = e2;
                                    intent = intent2;
                                }
                            } catch (ActivityNotFoundException e3) {
                                e = e3;
                            }
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(true);
                        textPaint.setFakeBoldText(true);
                        textPaint.setColor(
                                MainClear.this
                                        .getResources()
                                        .getColor(R.color.sec_tips_description_link_text_color));
                    }
                };
        String string = getActivity().getString(R.string.smart_switch_name_mobile);
        String string2 =
                "1"
                                .equals(
                                        SystemProperties.get(
                                                "storage.support.sdcard",
                                                DATA.DM_FIELD_INDEX.PCSCF_DOMAIN))
                        ? getString(R.string.master_clear_smartswitch_tip_description, string)
                        : getString(R.string.master_clear_smartswitch_tip_description_no_sdcard);
        try {
            SpannableString spannableString = new SpannableString(string2);
            int indexOf = string2.indexOf(string);
            spannableString.setSpan(clickableSpan, indexOf, string.length() + indexOf, 0);
            this.mDescriptionView.setText(spannableString);
            this.mDescriptionView.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (IndexOutOfBoundsException unused) {
            this.mDescriptionView.setText(string2);
            Log.e("MainClear", "Out of BountdsException Occurs!!");
        }
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        return this.mContentView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mResetBatteryLevelCheckDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        RecoveryCodeTask recoveryCodeTask = this.mRecoveryCodeTask;
        if (recoveryCodeTask != null) {
            recoveryCodeTask.cancel(true);
            this.mRecoveryCodeTask = null;
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        this.mInitiateButton.setEnabled(hasReachedBottom(this.mScrollView));
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        try {
            getActivity().unregisterReceiver(this.mBatteryInfoReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MainClear", "no BatteryReceiver");
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity()
                .registerReceiver(
                        this.mBatteryInfoReceiver,
                        new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        Settings.System.putInt(getActivity().getContentResolver(), "lock_my_mobile", 0);
        if (this.mPinConfirmed) {
            this.mPinConfirmed = false;
            Resources resources = getActivity().getResources();
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(getActivity(), this);
            builder.mRequestCode = 55;
            builder.mTitle = resources.getText(R.string.main_clear_short_title);
            if (!builder.show()) {
                showFinalConfirmation();
            }
        }
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isFactoryResetAllowed");
        Button button = this.mInitiateButton;
        if (button == null || enterprisePolicyEnabled == -1) {
            return;
        }
        button.setEnabled(enterprisePolicyEnabled == 1);
    }

    public void showAccountCredentialConfirmation(Intent intent) {
        startActivityForResult(intent, 56);
    }

    public boolean showAnySubscriptionInfo(Context context) {
        if (context != null) {
            Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
            if (context.getResources().getBoolean(R.bool.config_show_sim_info)) {
                return true;
            }
        }
        return false;
    }

    public void showFinalConfirmation() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("erase_sd", this.mExternalStorage.isChecked());
        if (Rune.FEATURE_NA_ESIM_RESET) {
            bundle.putBoolean("erase_esim", GeneralUtils.isEsimEmbedded(getContext()));
        } else {
            bundle.putBoolean(
                    "erase_esim",
                    this.mEsimStorage.isChecked()
                            && this.mEsimStorageContainer.getVisibility() == 0);
        }
        bundle.putString("erase_sd_id", this.mStrSdVolumeId);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("hiddenmenu");
            bundle.putString("hiddenmenu", stringExtra);
            Log.d("MainClear", "Intent is not null : " + stringExtra);
        }
        bundle.putBoolean("can_erase_sd_on_fuse", this.mCanEraseExternalOnFuseSystem);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = MainClearConfirm.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_master_clear_title, null);
        launchRequest.mSourceMetricsCategory = 66;
        subSettingLauncher.launch();
    }

    public boolean showWipeEuicc() {
        Context context = getContext();
        if (showAnySubscriptionInfo(context) && isEuiccEnabled(context)) {
            return Settings.Global.getInt(context.getContentResolver(), "euicc_provisioned", 0) != 0
                    || DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context);
        }
        return false;
    }

    public boolean showWipeEuiccCheckbox() {
        return SystemProperties.getBoolean(
                "masterclear.allow_retain_esim_profiles_after_fdr", false);
    }
}
