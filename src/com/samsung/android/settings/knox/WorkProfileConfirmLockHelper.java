package com.samsung.android.settings.knox;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImeAwareEditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ConfirmDeviceCredentialBaseFragment;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.knox.zt.internal.IKnoxZtInternalService;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WorkProfileConfirmLockHelper extends KnoxConfirmLockHelper {
    public static final boolean DEBUG;
    public static final int[][] DETAIL_TEXTS;
    public final KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback mCallback;
    public final Context mContext;
    public final boolean mDeviceHasSoftKeys;
    public final DevicePolicyManager mDevicePolicyManager;
    public final int mDeviceScreenHeight;
    public final int mEffectiveUserId;
    public final InputMethodManager mInputMethodManager;
    public final boolean mIsUCMView;
    public final AnonymousClass1 mLockEventReceiver;
    public final LockPatternUtils mLockPatternUtils;
    public ImageView mMultiFactorImage;
    public PasswordViewLayoutListener mPasswordViewLayoutListener;
    public final SemPersonaManager mPersonaManager;
    public final boolean mReturnCredentials;
    public final int mUserId;
    public final UserManager mUserManager;
    public final int quality;
    public TextView ucmPluginText;
    public int mVisibleLayoutBottomMagin = 0;
    public boolean mIsInMultiWindowMode = false;
    public final MediaRoute2Provider$$ExternalSyntheticLambda0 mClientExecutor = new MediaRoute2Provider$$ExternalSyntheticLambda0(new Handler(Looper.getMainLooper()));
    public TwoStepAuthenticationCallback mTwoStepAuthenticationCallback = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class LastTryDialog extends DialogFragment {
        public static final String TAG = LastTryDialog.class.getSimpleName();

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string = getArguments().getString(UniversalCredentialUtil.AGENT_TITLE);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage = getArguments().getString("message");
            builder.setPositiveButton(getArguments().getInt("button"), (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            return create;
        }

        @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            if (getActivity() == null || !getArguments().getBoolean("dismiss")) {
                return;
            }
            getActivity().finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PasswordViewLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        public final View activityRootView;
        public final LinearLayout pwdLayout;
        public final ScrollView scrollView;
        public final LinearLayout securedKnoxLogo;

        public PasswordViewLayoutListener(View view) {
            this.activityRootView = view;
            this.scrollView = (ScrollView) view.findViewById(R.id.pwdLayoutScrollView);
            this.pwdLayout = (LinearLayout) view.findViewById(R.id.pwdLayout);
            this.securedKnoxLogo = (LinearLayout) view.findViewById(R.id.knox_logo_layout);
        }

        public final int computePaddingForPwdlayout(View view, int i, int i2) {
            int i3;
            int i4;
            int dimensionPixelSize = (i2 - i) - WorkProfileConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_default_margin_bottom);
            if (WorkProfileConfirmLockHelper.this.isInLandscapeMode$1()) {
                i3 = 0;
                i4 = 0;
            } else {
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.knoxLogoLayout);
                i3 = linearLayout.getHeight();
                i4 = ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).topMargin;
            }
            return (dimensionPixelSize - i3) - i4;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            int i;
            int i2;
            if (this.activityRootView != null) {
                Rect rect = new Rect();
                this.scrollView.getWindowVisibleDisplayFrame(rect);
                int navigationBarSize = KnoxUtils.getNavigationBarSize(WorkProfileConfirmLockHelper.this.mContext);
                int height = navigationBarSize > 0 ? (this.scrollView.getRootView().getHeight() - rect.bottom) - navigationBarSize : this.scrollView.getRootView().getHeight() - rect.bottom;
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(height, "Keyboard height = ", "KKG::WorkProfileConfirmLockHelper");
                WorkProfileConfirmLockHelper workProfileConfirmLockHelper = WorkProfileConfirmLockHelper.this;
                int i3 = workProfileConfirmLockHelper.mVisibleLayoutBottomMagin;
                int i4 = rect.bottom;
                if (i3 != i4) {
                    workProfileConfirmLockHelper.mVisibleLayoutBottomMagin = i4;
                    int i5 = 0;
                    if (!workProfileConfirmLockHelper.mIsInMultiWindowMode && !Rune.isSamsungDexMode(workProfileConfirmLockHelper.mContext)) {
                        if (!WorkProfileConfirmLockHelper.this.isInLandscapeMode$1()) {
                            this.pwdLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                            WorkProfileConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, computePaddingForPwdlayout(this.activityRootView, this.pwdLayout.getHeight(), WorkProfileConfirmLockHelper.this.mVisibleLayoutBottomMagin));
                            return;
                        }
                        if (height > 0) {
                            ((LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout)).setVisibility(8);
                            this.pwdLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                            WorkProfileConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, computePaddingForPwdlayout(this.activityRootView, this.pwdLayout.getHeight(), WorkProfileConfirmLockHelper.this.mVisibleLayoutBottomMagin));
                            return;
                        }
                        LinearLayout linearLayout = (LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout);
                        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).topMargin = 0;
                        linearLayout.setVisibility(0);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, WorkProfileConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_height_landscape));
                        WorkProfileConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, 0);
                        layoutParams.gravity = 16;
                        this.pwdLayout.setLayoutParams(layoutParams);
                        return;
                    }
                    if (KnoxUtils.getInflatedLayoutType(WorkProfileConfirmLockHelper.this.mContext) != 1000) {
                        LinearLayout linearLayout2 = (LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout);
                        ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).topMargin = 0;
                        linearLayout2.setVisibility(0);
                        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, WorkProfileConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_height_landscape));
                        this.pwdLayout.setLayoutParams(layoutParams2);
                        WorkProfileConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, 0);
                        layoutParams2.gravity = 16;
                        this.pwdLayout.setLayoutParams(layoutParams2);
                        return;
                    }
                    if (this.scrollView.getRootView().getHeight() > rect.bottom) {
                        this.securedKnoxLogo.setVisibility(8);
                    } else {
                        this.securedKnoxLogo.setVisibility(0);
                    }
                    double d = WorkProfileConfirmLockHelper.this.isInLandscapeMode$1() ? 0.7d : 0.4d;
                    double d2 = rect.bottom - rect.top;
                    WorkProfileConfirmLockHelper workProfileConfirmLockHelper2 = WorkProfileConfirmLockHelper.this;
                    if (d2 / workProfileConfirmLockHelper2.mDeviceScreenHeight >= d || (workProfileConfirmLockHelper2.applyWorkChallengeBackgroundCase() && ((i2 = workProfileConfirmLockHelper2.quality) == 131072 || i2 == 196608))) {
                        ((LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout)).setVisibility(0);
                    } else {
                        WorkProfileConfirmLockHelper workProfileConfirmLockHelper3 = WorkProfileConfirmLockHelper.this;
                        View view = this.activityRootView;
                        workProfileConfirmLockHelper3.getClass();
                        LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.knoxLogoLayout);
                        if (linearLayout3 != null) {
                            Log.d("KKG::WorkProfileConfirmLockHelper", "Hiding Knox logo.");
                            linearLayout3.setVisibility(8);
                        } else {
                            Log.d("KKG::WorkProfileConfirmLockHelper", "Unable to hide knox logo.");
                        }
                    }
                    this.pwdLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                    LinearLayout linearLayout4 = (LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout);
                    if (linearLayout4 != null) {
                        ((LinearLayout.LayoutParams) linearLayout4.getLayoutParams()).topMargin = WorkProfileConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_knoxLogoLayout_top_margin_multiwindow);
                    }
                    View view2 = this.activityRootView;
                    int height2 = ((rect.bottom - rect.top) - this.pwdLayout.getHeight()) - WorkProfileConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_default_margin_bottom_multiwindow);
                    if (KnoxUtils.getInflatedLayoutType(WorkProfileConfirmLockHelper.this.mContext) == 1000) {
                        LinearLayout linearLayout5 = (LinearLayout) view2.findViewById(R.id.knoxLogoLayout);
                        i5 = linearLayout5.getHeight();
                        i = ((LinearLayout.LayoutParams) linearLayout5.getLayoutParams()).topMargin;
                    } else {
                        i = 0;
                    }
                    WorkProfileConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, (height2 - i5) - i);
                }
            }
        }

        public final void stopListener() {
            try {
                if (this.activityRootView != null) {
                    Log.d("KKG::WorkProfileConfirmLockHelper", "Stop layout listener");
                    this.activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            } catch (Exception e) {
                Log.d("KKG::WorkProfileConfirmLockHelper", "Exception while stopping view listener : " + e.getMessage());
            }
        }
    }

    static {
        String str = Build.TYPE;
        DEBUG = "eng".equals(str) || "userdebug".equals(str);
        DETAIL_TEXTS = new int[][]{new int[]{R.string.sec_draw_pattern_WC, R.string.sec_after_restarting_phone_pattern_WC, R.string.sec_knox_for_your_security_pattern}, new int[]{R.string.sec_enter_pin_WC, R.string.sec_after_restarting_phone_pin_WC, R.string.sec_knox_for_your_security_pin}, new int[]{R.string.sec_enter_password_WC, R.string.sec_after_restarting_phone_password_WC, R.string.sec_knox_for_your_security_password}};
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.content.BroadcastReceiver, com.samsung.android.settings.knox.WorkProfileConfirmLockHelper$1] */
    public WorkProfileConfirmLockHelper(Context context, int i, int i2, boolean z, KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback callback) {
        this.mPersonaManager = null;
        this.mDeviceHasSoftKeys = false;
        this.mIsUCMView = false;
        ?? r5 = new BroadcastReceiver() { // from class: com.samsung.android.settings.knox.WorkProfileConfirmLockHelper.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                WorkProfileConfirmLockHelper workProfileConfirmLockHelper = WorkProfileConfirmLockHelper.this;
                if (KnoxUtils.needUCMLockSetWithoutWC(workProfileConfirmLockHelper.mContext, workProfileConfirmLockHelper.mUserId, workProfileConfirmLockHelper.mReturnCredentials)) {
                    return;
                }
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId()));
                Log.i("KKG::WorkProfileConfirmLockHelper", "mLockEventReceiver.onReceive() {action:" + intent.getAction() + " userId:" + intExtra + "}");
                KeyguardManager keyguardManager = (KeyguardManager) context2.getSystemService("keyguard");
                if (WorkProfileConfirmLockHelper.this.mUserId != intExtra || keyguardManager.isDeviceLocked(intExtra) || WorkProfileConfirmLockHelper.this.mReturnCredentials) {
                    return;
                }
                Log.i("KKG::WorkProfileConfirmLockHelper", "mLockEventReceiver.onReceive() authenticationSucceededCallback");
                ((ConfirmDeviceCredentialBaseFragment) WorkProfileConfirmLockHelper.this.mCallback).authenticationSucceeded();
            }
        };
        this.mLockEventReceiver = r5;
        this.mContext = context;
        this.mUserId = i;
        this.mEffectiveUserId = i2;
        this.mReturnCredentials = z;
        this.mCallback = callback;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        this.mLockPatternUtils = lockPatternUtils;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPersonaManager = (SemPersonaManager) context.getSystemService("persona");
        this.mInputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.mDeviceScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i2);
        this.quality = keyguardStoredPasswordQuality;
        this.mDeviceHasSoftKeys = context.getResources().getBoolean(Resources.getSystem().getIdentifier("config_showNavigationBar", "bool", RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        this.mIsUCMView = keyguardStoredPasswordQuality == 458752;
        context.registerReceiverAsUser(r5, UserHandle.ALL, new IntentFilter("android.intent.action.DEVICE_LOCKED_CHANGED"), null, null);
        StringBuilder sb = new StringBuilder("Init. KeyguardStoredPasswordQuality = ");
        sb.append(keyguardStoredPasswordQuality == 0 ? SignalSeverity.NONE : keyguardStoredPasswordQuality <= 65536 ? "pattern" : keyguardStoredPasswordQuality <= 196608 ? "pin" : keyguardStoredPasswordQuality <= 393216 ? "Password" : "SmartCard");
        sb.append(", applyWorkChallengeBackgroundCase = ");
        sb.append(applyWorkChallengeBackgroundCase());
        Log.d("KKG::WorkProfileConfirmLockHelper", sb.toString());
    }

    public final boolean applyWorkChallengeBackgroundCase() {
        int i = this.mUserId;
        boolean z = this.mReturnCredentials;
        return (!z || KnoxUtils.isPwdChangeEnforced(this.mContext, i) || KnoxUtils.needSetupCredential(this.mContext, i)) && this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i) && !KnoxUtils.needUCMLockSetWithoutWC(this.mContext, i, z);
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void cancelBiometricIfNeeded() {
        if (this.mTwoStepAuthenticationCallback == null || !Rune.isSamsungDexMode(this.mContext)) {
            return;
        }
        TwoStepAuthenticationCallback twoStepAuthenticationCallback = this.mTwoStepAuthenticationCallback;
        CancellationSignal cancellationSignal = twoStepAuthenticationCallback.mCancellationSignal;
        if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
            twoStepAuthenticationCallback.mCancellationSignal.cancel();
        }
        twoStepAuthenticationCallback.mCancellationSignal = null;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void checkPasswordPolicy(FragmentActivity fragmentActivity) {
        int i = this.mUserId;
        if (SemPersonaManager.isDualDARCustomCrypto(i) && this.mContext.getPackageManager().isSafeMode()) {
            Log.w("KKG::WorkProfileConfirmLockHelper", "DualDAR safe mode with custom crypto case");
            String containerName = this.mPersonaManager.getContainerName(i, this.mContext);
            Context context = this.mContext;
            Toast.makeText(context, String.format(context.getString(R.string.keyguard_can_not_use_work_apps_in_safe_mode), containerName), 0).show();
            fragmentActivity.finish();
            return;
        }
        boolean isPwdChangeEnforced = KnoxUtils.isPwdChangeEnforced(this.mContext, i);
        boolean z = this.mReturnCredentials;
        if (isPwdChangeEnforced && !z) {
            Log.w("KKG::WorkProfileConfirmLockHelper", "Enforce password change case");
            Intent intent = new Intent(fragmentActivity, (Class<?>) ChooseLockGeneric.InternalActivity.class);
            intent.putExtras(fragmentActivity.getIntent());
            intent.putExtra("android.intent.extra.USER_ID", i);
            intent.putExtra("isEnforcedPwdChanged", true);
            fragmentActivity.startActivity(intent);
            fragmentActivity.finish();
            return;
        }
        if (KnoxUtils.needSetupCredential(this.mContext, i) && !z) {
            Log.w("KKG::WorkProfileConfirmLockHelper", "Legacy reset password case");
            Intent intent2 = new Intent(fragmentActivity, (Class<?>) ChooseLockGeneric.InternalActivity.class);
            intent2.putExtras(fragmentActivity.getIntent());
            intent2.putExtra("android.intent.extra.USER_ID", i);
            intent2.putExtra("confirm_credentials", false);
            intent2.putExtra("isEnforcedPwdChanged", true);
            fragmentActivity.startActivity(intent2);
            fragmentActivity.finish();
            return;
        }
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "enable_one_lock_ongoing", 0, 0) <= 0 || z) {
            return;
        }
        Log.w("KKG::WorkProfileConfirmLockHelper", "one lock uncompliant case");
        Intent intent3 = new Intent(fragmentActivity, (Class<?>) ChooseLockGeneric.InternalActivity.class);
        intent3.putExtras(fragmentActivity.getIntent());
        intent3.putExtra("android.intent.extra.USER_ID", 0);
        intent3.putExtra("isOneLockOngoing", true);
        fragmentActivity.startActivity(intent3);
        fragmentActivity.finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final boolean confirmBiometricIfNeeded(boolean z, FragmentActivity fragmentActivity, boolean z2) {
        int i;
        if (z) {
            Context context = this.mContext;
            int i2 = this.mUserId;
            if (KnoxUtils.isMultifactorEnabledForWork(context, i2) && KnoxUtils.isFingerprintEnabled(this.mContext, i2)) {
                if (this.mLockPatternUtils.getBiometricAttemptDeadline(i2) <= 0) {
                    KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback callback = this.mCallback;
                    callback.disableConfirmCredentialCallback();
                    DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
                    TwoStepAuthenticationCallback twoStepAuthenticationCallback = TwoStepAuthenticationCallback.singleInstance;
                    MediaRoute2Provider$$ExternalSyntheticLambda0 mediaRoute2Provider$$ExternalSyntheticLambda0 = this.mClientExecutor;
                    if (twoStepAuthenticationCallback == null) {
                        TwoStepAuthenticationCallback twoStepAuthenticationCallback2 = new TwoStepAuthenticationCallback();
                        twoStepAuthenticationCallback2.mCancellationSignal = null;
                        twoStepAuthenticationCallback2.mClientExecutor = mediaRoute2Provider$$ExternalSyntheticLambda0;
                        twoStepAuthenticationCallback2.mDevicePolicyManager = devicePolicyManager;
                        twoStepAuthenticationCallback2.mUserId = i2;
                        TwoStepAuthenticationCallback.singleInstance = twoStepAuthenticationCallback2;
                    }
                    TwoStepAuthenticationCallback twoStepAuthenticationCallback3 = TwoStepAuthenticationCallback.singleInstance;
                    this.mTwoStepAuthenticationCallback = twoStepAuthenticationCallback3;
                    twoStepAuthenticationCallback3.activity = fragmentActivity;
                    twoStepAuthenticationCallback3.callback = callback;
                    if (!z2) {
                        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.samsung.android.settings.knox.WorkProfileConfirmLockHelper$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                WorkProfileConfirmLockHelper workProfileConfirmLockHelper = WorkProfileConfirmLockHelper.this;
                                workProfileConfirmLockHelper.mTwoStepAuthenticationCallback.onAuthenticationError(13, workProfileConfirmLockHelper.mContext.getString(R.string.common_cancel));
                            }
                        };
                        BiometricPrompt.Builder receiveSystemEvents = new BiometricPrompt.Builder(this.mContext).setUseDefaultTitle().setDisallowBiometricsIfPolicyExists(true).setReceiveSystemEvents(true);
                        receiveSystemEvents.setAllowedAuthenticators(15);
                        receiveSystemEvents.setDeviceCredentialAllowed(false);
                        receiveSystemEvents.setNegativeButton(this.mContext.getString(R.string.common_cancel), mediaRoute2Provider$$ExternalSyntheticLambda0, onClickListener);
                        boolean isFingerprintEnabled = KnoxUtils.isFingerprintEnabled(this.mContext, i2);
                        int i3 = isFingerprintEnabled;
                        if (KnoxUtils.isFaceEnabled(this.mContext, i2)) {
                            i3 = (isFingerprintEnabled ? 1 : 0) | 2;
                        }
                        receiveSystemEvents.semSetBiometricType(i3);
                        if (i3 != 0) {
                            receiveSystemEvents.setConfirmationRequired(false);
                            i = 36;
                        } else {
                            i = 32;
                        }
                        receiveSystemEvents.semSetPrivilegedFlag(i | 192);
                        BiometricPrompt build = receiveSystemEvents.build();
                        TwoStepAuthenticationCallback twoStepAuthenticationCallback4 = this.mTwoStepAuthenticationCallback;
                        if (twoStepAuthenticationCallback4.mCancellationSignal == null) {
                            twoStepAuthenticationCallback4.mCancellationSignal = new CancellationSignal();
                        }
                        build.authenticateUser(twoStepAuthenticationCallback4.mCancellationSignal, mediaRoute2Provider$$ExternalSyntheticLambda0, this.mTwoStepAuthenticationCallback, i2);
                    }
                    return true;
                }
                Intent intent = new Intent(this.mContext, (Class<?>) BiometricLockTimerDialogActivity.class);
                intent.putExtra("mUserId", i2);
                this.mContext.startActivity(intent);
                fragmentActivity.finish();
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0085  */
    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDefaultDetails() {
        /*
            Method dump skipped, instructions count: 212
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.knox.WorkProfileConfirmLockHelper.getDefaultDetails():int");
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final String getDefaultHeader(String str, boolean z) {
        if (!this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mUserId)) {
            return str;
        }
        int i = this.mEffectiveUserId;
        Context context = this.mContext;
        Object[] objArr = {KnoxUtils.getKnoxName(context, i)};
        return z ? context.getString(R.string.sec_knox_confirm_password_title, objArr) : context.getString(R.string.sec_knox_confirm_pin_title, objArr);
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final int getErrorMessage() {
        if (!applyWorkChallengeBackgroundCase()) {
            return R.string.sec_lockpassword_need_to_unlock_wrong;
        }
        int i = this.quality;
        if (i == 65536) {
            return R.string.cryptkeeper_wrong_pattern;
        }
        if (i != 131072 && i != 196608) {
            if (i != 262144 && i != 327680 && i != 393216) {
                if (i != 458752) {
                    if (i != 524288) {
                        return R.string.sec_lockpassword_need_to_unlock_wrong;
                    }
                }
            }
            return R.string.cryptkeeper_wrong_password;
        }
        return R.string.cryptkeeper_wrong_pin;
    }

    public final String getWPCMessage(int i, int i2) {
        return i == 1 ? i2 == 1 ? KnoxUtils.isTablet() ? String.format(String.format(this.mContext.getString(R.string.wpc_tablet_incorrect_dialog_1), new Object[0]), new Object[0]) : String.format(this.mContext.getString(R.string.wpc_phone_incorrect_dialog_1), new Object[0]) : KnoxUtils.isTablet() ? this.mContext.getResources().getQuantityString(R.plurals.wpc_tablet_incorrect_dialog_2, i, Integer.valueOf(i2)) : this.mContext.getResources().getQuantityString(R.plurals.wpc_phone_incorrect_dialog_2, i, Integer.valueOf(i2)) : i2 == 1 ? KnoxUtils.isTablet() ? this.mContext.getResources().getQuantityString(R.plurals.wpc_tablet_incorrect_dialog_4, i, Integer.valueOf(i)) : this.mContext.getResources().getQuantityString(R.plurals.wpc_phone_incorrect_dialog_4, i, Integer.valueOf(i)) : KnoxUtils.isTablet() ? String.format(this.mContext.getString(R.string.wpc_tablet_incorrect_dialog_3), Integer.valueOf(i), Integer.valueOf(i2)) : String.format(this.mContext.getString(R.string.wpc_phone_incorrect_dialog_3), Integer.valueOf(i), Integer.valueOf(i2));
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void hideSoftInputIfNeeded(ImeAwareEditText imeAwareEditText) {
        if (imeAwareEditText != null) {
            this.mInputMethodManager.hideSoftInputFromWindow(imeAwareEditText.getWindowToken(), 0);
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final boolean interceptAuthenticationSucceededIfNeeded(boolean z, boolean z2) {
        return z || z2;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final boolean interceptOnResumeIfNeeded(FragmentActivity fragmentActivity) {
        if (Rune.isSamsungDexMode(fragmentActivity) || !fragmentActivity.isInMultiWindowMode() || SemPersonaManager.isKnoxId(this.mEffectiveUserId)) {
            return false;
        }
        Toast.makeText(fragmentActivity, this.mContext.getString(R.string.lock_screen_doesnt_support_multi_window_text), 0).show();
        fragmentActivity.finish();
        return true;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final boolean interceptUpdateEntryOrStateIfNeeded(boolean z) {
        if (!z) {
            return false;
        }
        Log.d("KKG::WorkProfileConfirmLockHelper", "WaitingForTwoFactorConfirmation state. So intercept update entry or state.");
        return true;
    }

    public final boolean isInLandscapeMode$1() {
        int displayRotation = KnoxUtils.getDisplayRotation(this.mContext);
        return displayRotation == 1 || displayRotation == 3;
    }

    public final boolean isMultifactorEnabled() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", 0, this.mEffectiveUserId) == 1;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onCreateView(View view) {
        if (applyWorkChallengeBackgroundCase()) {
            TextView textView = (TextView) view.findViewById(R.id.password_entry);
            if (textView != null) {
                UserManager userManager = this.mUserManager;
                int i = this.mEffectiveUserId;
                if (userManager.isUserUnlocked(UserHandle.of(i))) {
                    textView.setTextOperationUser(UserHandle.of(i));
                }
                textView.setImportantForAutofill(2);
            }
            view.semSetRoundedCorners(0);
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onDestroy() {
        AnonymousClass1 anonymousClass1 = this.mLockEventReceiver;
        if (anonymousClass1 != null) {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
        PasswordViewLayoutListener passwordViewLayoutListener = this.mPasswordViewLayoutListener;
        if (passwordViewLayoutListener != null) {
            passwordViewLayoutListener.stopListener();
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onWindowFocusChanged(boolean z) {
        KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback callback = this.mCallback;
        if (!z) {
            callback.getClass();
            return;
        }
        Context context = this.mContext;
        String str = KnoxUtils.mDeviceType;
        if (((InputMethodManager) context.getSystemService("input_method")).semIsInputMethodShown()) {
            return;
        }
        ((ConfirmDeviceCredentialBaseFragment) callback).refreshLockScreen();
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void removeGlobalLayoutListenerIfRequired() {
        PasswordViewLayoutListener passwordViewLayoutListener = this.mPasswordViewLayoutListener;
        if (passwordViewLayoutListener != null) {
            passwordViewLayoutListener.stopListener();
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final boolean reportFailedAttempt(int i, FragmentManager fragmentManager, FragmentActivity fragmentActivity) {
        int enterprisePolicyEnabledInt;
        int i2;
        Log.d("KKG::WorkProfileConfirmLockHelper", "reportFailedAttempt");
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i3 = this.mEffectiveUserId;
        int maximumFailedPasswordsForWipe = lockPatternUtils.getMaximumFailedPasswordsForWipe(i3);
        PasswordPolicy passwordPolicy = KnoxUtils.getPasswordPolicy(this.mContext, i3);
        if (passwordPolicy != null) {
            enterprisePolicyEnabledInt = passwordPolicy.getMaximumFailedPasswordsForDeviceDisable();
        } else {
            enterprisePolicyEnabledInt = Utils.getEnterprisePolicyEnabledInt(this.mContext, "getMaximumFailedPasswordsForDisable", new String[]{Integer.toString(KnoxUtils.isChangeRequested(this.mContext, i3) > 0 ? 100000 * i3 : i3 == 0 ? 0 : Process.myUid())});
        }
        int min = (maximumFailedPasswordsForWipe <= 0 || enterprisePolicyEnabledInt <= 0) ? maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe : enterprisePolicyEnabledInt > 0 ? enterprisePolicyEnabledInt : 0 : Math.min(maximumFailedPasswordsForWipe, enterprisePolicyEnabledInt);
        if (min <= 0 || i <= 0 || (i2 = min - i) > 10) {
            return false;
        }
        String format = String.format(i2 == 1 ? this.mContext.getString(R.string.keyguard_password_attempt_count_pin_code) : this.mContext.getString(R.string.keyguard_password_attempts_count_pin_code), Integer.valueOf(i2));
        ConfirmDeviceCredentialBaseFragment confirmDeviceCredentialBaseFragment = (ConfirmDeviceCredentialBaseFragment) this.mCallback;
        if (confirmDeviceCredentialBaseFragment.mErrorTextView != null) {
            confirmDeviceCredentialBaseFragment.showError(format, 0L);
        }
        if (i2 > 5) {
            return false;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m("Under than 5 attempts left :  Wipe : ", " Disable : ", maximumFailedPasswordsForWipe, enterprisePolicyEnabledInt, "KKG::WorkProfileConfirmLockHelper");
        boolean isKnoxOrganizationOwnedDevice = KnoxUtils.isKnoxOrganizationOwnedDevice(this.mContext, 0);
        String format2 = min == maximumFailedPasswordsForWipe ? i == 1 ? i2 == 1 ? !isKnoxOrganizationOwnedDevice ? String.format(this.mContext.getString(R.string.po_incorrect_dialog_wipe_1), new Object[0]) : getWPCMessage(i, i2) : !isKnoxOrganizationOwnedDevice ? String.format(this.mContext.getString(R.string.po_incorrect_dialog_wipe_2), Integer.valueOf(i2)) : getWPCMessage(i, i2) : i2 == 1 ? !isKnoxOrganizationOwnedDevice ? String.format(this.mContext.getString(R.string.po_incorrect_dialog_wipe_4), Integer.valueOf(i)) : getWPCMessage(i, i2) : !isKnoxOrganizationOwnedDevice ? String.format(this.mContext.getString(R.string.po_incorrect_dialog_wipe_3), Integer.valueOf(i), Integer.valueOf(i2)) : getWPCMessage(i, i2) : i == 1 ? min == 1 ? String.format(this.mContext.getString(R.string.common_incorrect_dialog_1), new Object[0]) : String.format(this.mContext.getString(R.string.common_incorrect_dialog_2), Integer.valueOf(i2)) : i2 == 1 ? String.format(this.mContext.getString(R.string.common_incorrect_dialog_4), Integer.valueOf(i)) : String.format(this.mContext.getString(R.string.common_incorrect_dialog_3), Integer.valueOf(i), Integer.valueOf(i2));
        if (i2 < 1) {
            if (min != maximumFailedPasswordsForWipe) {
                if (!KnoxUtils.isKnoxOrganizationOwnedDevice(this.mContext, 0) || passwordPolicy == null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("android.intent.extra.user_handle", i3);
                    ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
                } else {
                    passwordPolicy.lock();
                }
                try {
                    IKnoxZtInternalService asInterface = IKnoxZtInternalService.Stub.asInterface(ServiceManager.getService("knoxztinternal"));
                    if (asInterface != null) {
                        asInterface.notifyFrameworkEvent(3, 0, (Bundle) null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
            fragmentActivity.finish();
            return true;
        }
        boolean z = i2 < 1;
        String str = LastTryDialog.TAG;
        LastTryDialog lastTryDialog = (LastTryDialog) fragmentManager.findFragmentByTag(str);
        if (lastTryDialog == null || lastTryDialog.isRemoving()) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(UniversalCredentialUtil.AGENT_TITLE, null);
            bundle2.putString("message", format2);
            bundle2.putInt("button", android.R.string.ok);
            bundle2.putBoolean("dismiss", z);
            LastTryDialog lastTryDialog2 = new LastTryDialog();
            lastTryDialog2.setArguments(bundle2);
            lastTryDialog2.show(fragmentManager, str);
            fragmentManager.executePendingTransactions();
        }
        return true;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void reportSuccessfulAttempt() {
        Log.d("KKG::WorkProfileConfirmLockHelper", "reportSuccessfulAttempt");
        this.mLockPatternUtils.clearBiometricAttemptDeadline(this.mEffectiveUserId);
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setBiometricLockoutDeadline(int i) {
        this.mLockPatternUtils.setBiometricAttemptDeadline(this.mEffectiveUserId, i);
    }

    public final void setKnoxLogo$1(View view) {
        TextView textView;
        UserManager userManager = this.mUserManager;
        int i = this.mEffectiveUserId;
        userManager.getUserInfo(i);
        TextView textView2 = (TextView) view.findViewById(R.id.knoxTitleText);
        int color = this.mContext.getResources().getColor(R.color.work_profile_lock_screen_text_color);
        String string = this.mContext.getString(R.string.work_title);
        textView2.setTextColor(color);
        textView2.setText(string);
        TextView textView3 = (TextView) view.findViewById(R.id.knoxTitleSubText);
        CharSequence organizationNameForUser = this.mDevicePolicyManager.getOrganizationNameForUser(i);
        textView3.setTextColor(color);
        textView3.setText(organizationNameForUser);
        ImageView imageView = (ImageView) view.findViewById(R.id.knoxLogo);
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.knox_basic);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            imageView.setColorFilter(Color.parseColor("#fffafafa"));
        }
        if (Rune.isSamsungDexMode(this.mContext) && (textView = (TextView) view.findViewById(R.id.knoxTitleText)) != null) {
            textView.setTextSize(1, this.mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_knoxTitletext_text_height_dex));
        }
        if (Rune.isSamsungDexMode(this.mContext)) {
            Context context = this.mContext;
            if (KnoxUtils.getActivityScreenWidth(context) > KnoxUtils.getActivityScreenHeight(context)) {
                return;
            }
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.knoxLogoLayout);
        if (linearLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            boolean isTablet = KnoxUtils.isTablet();
            int i2 = this.mDeviceScreenHeight;
            if (!isTablet) {
                if (isInLandscapeMode$1()) {
                    return;
                }
                layoutParams.topMargin = (int) (i2 * 0.09d);
            } else if (isInLandscapeMode$1()) {
                layoutParams.topMargin = (int) (i2 * 0.08d);
            } else {
                layoutParams.topMargin = (int) (i2 * 0.1d);
            }
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setPasswordEntry(ImeAwareEditText imeAwareEditText) {
        if (applyWorkChallengeBackgroundCase()) {
            imeAwareEditText.setGravity(8388627);
            imeAwareEditText.setPadding((int) this.mContext.getResources().getDimension(R.dimen.knox_pwd_entry_padding_left_right), imeAwareEditText.getPaddingTop(), (int) this.mContext.getResources().getDimension(R.dimen.knox_pwd_entry_padding_left_right), imeAwareEditText.getPaddingBottom());
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setPatternColor(LockPatternView lockPatternView) {
        if (applyWorkChallengeBackgroundCase()) {
            lockPatternView.setColors(this.mContext.getResources().getColor(R.color.work_profile_lock_screen_text_color), this.mContext.getResources().getColor(R.color.status_bar_color), -65536);
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setShowPwdImgColor(ImageButton imageButton, boolean z) {
        if (applyWorkChallengeBackgroundCase() && imageButton != null) {
            Drawable drawable = z ? this.mContext.getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_show_mtrl, null) : this.mContext.getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_hide_mtrl, null);
            if (drawable == null) {
                Log.d("KKG::WorkProfileConfirmLockHelper", "Password show drawable is null.");
                return;
            }
            imageButton.setForegroundTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(R.color.work_profile_lock_screen_text_color)));
            imageButton.setForeground(drawable);
            if (Rune.isSamsungDexMode(this.mContext)) {
                imageButton.semSetPointerIcon(3, PointerIcon.getSystemIcon(this.mContext, 3));
            }
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setWorkChallengeBackgroundIfNeeded(View view, FragmentActivity fragmentActivity) {
        if (applyWorkChallengeBackgroundCase()) {
            ((AppBarLayout) fragmentActivity.findViewById(R.id.app_bar)).setVisibility(8);
            ImageView imageView = (ImageView) view.findViewById(R.id.background_image);
            if (imageView != null) {
                imageView.setImageDrawable(KnoxUtils.getInflatedLayoutType(this.mContext) == 1000 ? this.mContext.getResources().getDrawable(R.drawable.sec_knox_credential_bg) : this.mContext.getResources().getDrawable(R.drawable.sec_knox_credential_bg_land));
            }
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.knox_logo_layout);
            if (linearLayout == null) {
                Log.d("KKG::WorkProfileConfirmLockHelper", "SecuredLogo is NULL");
            } else if (this.mIsInMultiWindowMode) {
                linearLayout.setPadding(0, 0, 0, 0);
                ImageView imageView2 = (ImageView) view.findViewById(R.id.knox_secure_logo);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView2.getLayoutParams();
                layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.knox_logo_height_multiwindow);
                imageView2.setLayoutParams(layoutParams);
            } else {
                boolean isInLandscapeMode$1 = isInLandscapeMode$1();
                boolean z = this.mDeviceHasSoftKeys;
                if (isInLandscapeMode$1) {
                    if (z) {
                        int navigationBarSize = SecurityUtils.isWinnerProduct() ? KnoxUtils.getNavigationBarSize(this.mContext) : 0;
                        int navigationBarSize2 = SecurityUtils.isWinnerProduct() ? KnoxUtils.getNavigationBarSize(this.mContext) : 0;
                        if (KnoxUtils.getDisplayRotation(this.mContext) == 1) {
                            linearLayout.setPadding(0, 0, KnoxUtils.getNavigationBarSize(this.mContext), navigationBarSize);
                        } else {
                            linearLayout.setPadding(KnoxUtils.getNavigationBarSize(this.mContext), 0, navigationBarSize2, navigationBarSize);
                        }
                    }
                } else if (z) {
                    linearLayout.setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this.mContext));
                }
            }
            Window window = fragmentActivity.getWindow();
            if (window != null) {
                if (!this.mIsInMultiWindowMode) {
                    window.setNavigationBarColor(0);
                    window.setNavigationBarContrastEnforced(false);
                    window.setStatusBarColor(0);
                    window.getDecorView().setSystemUiVisibility(768);
                }
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.samsungFlags |= 67108864;
                window.setAttributes(attributes);
                if (DEBUG) {
                    Log.d("KKG::WorkProfileConfirmLockHelper", "Debug mode enabled");
                    window.clearFlags(8192);
                }
            }
            Log.d("KKG::WorkProfileConfirmLockHelper", "Display rotation : " + KnoxUtils.getDisplayRotation(this.mContext));
            Log.d("KKG::WorkProfileConfirmLockHelper", "actvity width : " + KnoxUtils.getActivityScreenWidth(this.mContext));
            Log.d("KKG::WorkProfileConfirmLockHelper", "activity height : " + KnoxUtils.getActivityScreenHeight(this.mContext));
            Log.d("KKG::WorkProfileConfirmLockHelper", "Device screen width : " + Resources.getSystem().getDisplayMetrics().widthPixels);
            Preference$$ExternalSyntheticOutline0.m(new StringBuilder("Device screen height : "), Resources.getSystem().getDisplayMetrics().heightPixels, "KKG::WorkProfileConfirmLockHelper");
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setupForgotButtonIfManagedProfile(Button button) {
        button.setPaintFlags(button.getPaintFlags() | 8);
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final View switchViewIfNeeded(LayoutInflater layoutInflater, View view, FragmentActivity fragmentActivity) {
        boolean z;
        if (!applyWorkChallengeBackgroundCase()) {
            return view;
        }
        this.mIsInMultiWindowMode = fragmentActivity.isInMultiWindowMode();
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("mIsInMultiWindowMode : "), this.mIsInMultiWindowMode, "KKG::WorkProfileConfirmLockHelper");
        boolean z2 = this.mReturnCredentials;
        int i = this.mEffectiveUserId;
        int i2 = this.quality;
        View view2 = null;
        if (i2 != 65536) {
            if (i2 == 131072 || i2 == 196608) {
                Log.d("KKG::WorkProfileConfirmLockHelper", "Password quality is numeric");
            } else if (i2 != 262144 && i2 != 327680 && i2 != 393216 && i2 != 458752 && i2 != 524288) {
                if (SemPersonaManager.appliedPasswordPolicy(i) && !z2) {
                    view2 = layoutInflater.inflate(R.layout.sec_confirm_lock_password_settings_work_profile, (ViewGroup) null);
                }
            }
            view2 = this.mIsInMultiWindowMode ? layoutInflater.inflate(R.layout.sec_confirm_lock_password_settings_work_profile_multiwindow, (ViewGroup) null) : layoutInflater.inflate(R.layout.sec_confirm_lock_password_settings_work_profile, (ViewGroup) null);
            if (i2 == 458752) {
                this.ucmPluginText = (TextView) view2.findViewById(R.id.ucmTitleText);
            }
            setKnoxLogo$1(view2);
            if (isInLandscapeMode$1() && !this.mIsInMultiWindowMode) {
                ((LinearLayout) view2.findViewById(R.id.topLayout)).setPadding(KnoxUtils.getNavigationBarSize(this.mContext), 0, KnoxUtils.getNavigationBarSize(this.mContext), 0);
            }
            if (this.ucmPluginText != null && (z = this.mIsUCMView) && z) {
                String uCMKeyguardStorageForUser = UCMUtils.getUCMKeyguardStorageForUser(i);
                if (uCMKeyguardStorageForUser != null) {
                    Log.d("com.samsung.android.settings.knox.UCMUtils", "UCM Keyguard : ".concat(uCMKeyguardStorageForUser));
                    int lastIndexOf = uCMKeyguardStorageForUser.lastIndexOf(":");
                    if (lastIndexOf != -1) {
                        uCMKeyguardStorageForUser = uCMKeyguardStorageForUser.substring(lastIndexOf + 1, uCMKeyguardStorageForUser.length());
                    }
                } else {
                    uCMKeyguardStorageForUser = ApnSettings.MVNO_NONE;
                }
                this.ucmPluginText.setText(uCMKeyguardStorageForUser);
                this.ucmPluginText.setVisibility(0);
            }
            PasswordViewLayoutListener passwordViewLayoutListener = new PasswordViewLayoutListener(view2);
            this.mPasswordViewLayoutListener = passwordViewLayoutListener;
            Log.d("KKG::WorkProfileConfirmLockHelper", "Start layout listener");
            view2.getViewTreeObserver().addOnGlobalLayoutListener(passwordViewLayoutListener);
            ImageButton imageButton = (ImageButton) view2.findViewById(R.id.password_show_btn);
            if (Rune.isSamsungDexMode(this.mContext) && imageButton != null) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageButton.getLayoutParams();
                layoutParams.width = (int) this.mContext.getResources().getDimension(R.dimen.show_password_width_height_dex);
                layoutParams.height = (int) this.mContext.getResources().getDimension(R.dimen.show_password_width_height_dex);
                imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            if (!isInLandscapeMode$1() && !this.mIsInMultiWindowMode) {
                if (KnoxUtils.isFoldableProduct() && this.mContext.getResources().getConfiguration().semDisplayDeviceType == 0) {
                    ((FrameLayout) view2.findViewById(R.id.parent_password_entry)).getLayoutParams().width = (int) (KnoxUtils.getActivityScreenWidth(this.mContext) * 0.7d);
                } else if (KnoxUtils.isTablet()) {
                    ((FrameLayout) view2.findViewById(R.id.parent_password_entry)).getLayoutParams().width = (int) (KnoxUtils.getActivityScreenWidth(this.mContext) * 0.4d);
                }
            }
        } else {
            view2 = this.mIsInMultiWindowMode ? layoutInflater.inflate(R.layout.sec_confirm_lock_pattern_settings_work_profile_multiwindow, (ViewGroup) null) : layoutInflater.inflate(R.layout.sec_confirm_lock_pattern_settings_work_profile, (ViewGroup) null);
            setKnoxLogo$1(view2);
            if (!this.mIsInMultiWindowMode && !isInLandscapeMode$1()) {
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) ((LinearLayout) view2.findViewById(R.id.topLayout)).getLayoutParams();
                layoutParams2.bottomMargin = KnoxUtils.getNavigationBarSize(this.mContext) + layoutParams2.bottomMargin;
            }
        }
        if (this.mUserId != UserHandle.myUserId() && KnoxUtils.isFingerprintEnabled(this.mContext, i) && (((isMultifactorEnabled() && !z2) || (isMultifactorEnabled() && z2 && KnoxUtils.isPwdChangeEnforced(this.mContext, i))) && view2 != null)) {
            ImageView imageView = (ImageView) view2.findViewById(R.id.knox_two_step_image);
            this.mMultiFactorImage = imageView;
            imageView.setImageResource(R.drawable.ws_ic_2step_02);
            this.mMultiFactorImage.setVisibility(0);
        }
        return view2;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void updateStageNeedToUnlock(TextView textView) {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i = this.mEffectiveUserId;
        if (lockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            Context context = this.mContext;
            textView.setText(context.getString(R.string.sec_knox_confirm_pattern_title, KnoxUtils.getKnoxName(context, i)));
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void updateState() {
        this.mCallback.updateState();
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void interceptUpdateErrorMessageIfNeeded(int i) {
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void needToUpdateErrorMessage(int i, TextView textView) {
    }
}
