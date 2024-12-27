package com.samsung.android.settings.knox;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImeAwareEditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.password.ConfirmDeviceCredentialBaseFragment;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.securefolder.KnoxLockScreenButtonController;
import com.samsung.android.settings.security.SecurityUtils;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecureFolderConfirmLockHelper extends KnoxConfirmLockHelper {
    public static final boolean DEBUG;
    public final AnonymousClass2 contentObserver;
    public Activity mActivity;
    public final KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback mCallback;
    public final Context mContext;
    public final boolean mDeviceHasSoftKeys;
    public final int mDeviceScreenHeight;
    public final int mEffectiveUserId;
    public final InputMethodManager mInputMethodManager;
    public final ArrayList mKnoxEventList;
    public final AnonymousClass1 mLockEventReceiver;
    public final LockPatternUtils mLockPatternUtils;
    public PasswordViewLayoutListener mPasswordViewLayoutListener;
    public final SemPersonaManager mPersonaManager;
    public KnoxLockScreenButtonController mResetOrUninstallButton;
    public final boolean mReturnCredentials;
    public final int mUserId;
    public final UserManager mUserManager;
    public final int quality;
    public KnoxUtils.InitLockState mSFLockState = KnoxUtils.InitLockState.NONE;
    public int mVisibleLayoutBottomMagin = 0;
    public boolean mIsInMultiWindowMode = false;
    public int mShouldShowForgetOrUninstallButton = 8;
    public AlertDialog mAlertDialog = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.knox.SecureFolderConfirmLockHelper$7, reason: invalid class name */
    public final class AnonymousClass7 implements DialogInterface.OnCancelListener {
        @Override // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            dialogInterface.dismiss();
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
            int dimensionPixelSize = (i2 - i) - SecureFolderConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_default_margin_bottom);
            if (SecureFolderConfirmLockHelper.this.isInLandscapeMode()) {
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
            if (this.activityRootView != null) {
                Rect rect = new Rect();
                this.scrollView.getWindowVisibleDisplayFrame(rect);
                int navigationBarSize = KnoxUtils.getNavigationBarSize(SecureFolderConfirmLockHelper.this.mContext);
                int height = navigationBarSize > 0 ? (this.scrollView.getRootView().getHeight() - rect.bottom) - navigationBarSize : this.scrollView.getRootView().getHeight() - rect.bottom;
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(height, "Keyboard height = ", "KKG::SecureFolderConfirmLockHelper");
                SecureFolderConfirmLockHelper secureFolderConfirmLockHelper = SecureFolderConfirmLockHelper.this;
                int i2 = secureFolderConfirmLockHelper.mVisibleLayoutBottomMagin;
                int i3 = rect.bottom;
                if (i2 != i3) {
                    secureFolderConfirmLockHelper.mVisibleLayoutBottomMagin = i3;
                    int i4 = 0;
                    if (!secureFolderConfirmLockHelper.mIsInMultiWindowMode && !Rune.isSamsungDexMode(secureFolderConfirmLockHelper.mContext)) {
                        if (!SecureFolderConfirmLockHelper.this.isInLandscapeMode()) {
                            this.pwdLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                            SecureFolderConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, computePaddingForPwdlayout(this.activityRootView, this.pwdLayout.getHeight(), SecureFolderConfirmLockHelper.this.mVisibleLayoutBottomMagin));
                            return;
                        }
                        if (height > 0) {
                            ((LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout)).setVisibility(8);
                            this.pwdLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                            SecureFolderConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, computePaddingForPwdlayout(this.activityRootView, this.pwdLayout.getHeight(), SecureFolderConfirmLockHelper.this.mVisibleLayoutBottomMagin));
                            return;
                        }
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, SecureFolderConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_height_landscape));
                        SecureFolderConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, 0);
                        layoutParams.gravity = 16;
                        this.pwdLayout.setLayoutParams(layoutParams);
                        LinearLayout linearLayout = (LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout);
                        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).topMargin = 0;
                        linearLayout.setVisibility(0);
                        return;
                    }
                    if (KnoxUtils.getInflatedLayoutType(SecureFolderConfirmLockHelper.this.mContext) != 1000) {
                        LinearLayout linearLayout2 = (LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout);
                        ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).topMargin = 0;
                        linearLayout2.setVisibility(0);
                        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, SecureFolderConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_height_landscape));
                        this.pwdLayout.setLayoutParams(layoutParams2);
                        SecureFolderConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, 0);
                        layoutParams2.gravity = 16;
                        this.pwdLayout.setLayoutParams(layoutParams2);
                        return;
                    }
                    if (this.scrollView.getRootView().getHeight() > rect.bottom) {
                        this.securedKnoxLogo.setVisibility(8);
                    } else {
                        this.securedKnoxLogo.setVisibility(0);
                    }
                    this.pwdLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                    LinearLayout linearLayout3 = (LinearLayout) this.activityRootView.findViewById(R.id.knoxLogoLayout);
                    if (linearLayout3 != null) {
                        ((LinearLayout.LayoutParams) linearLayout3.getLayoutParams()).topMargin = SecureFolderConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_knoxLogoLayout_top_margin_multiwindow);
                    }
                    View view = this.activityRootView;
                    int height2 = ((rect.bottom - rect.top) - this.pwdLayout.getHeight()) - SecureFolderConfirmLockHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_pwd_layout_default_margin_bottom_multiwindow);
                    if (KnoxUtils.getInflatedLayoutType(SecureFolderConfirmLockHelper.this.mContext) == 1000) {
                        LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.knoxLogoLayout);
                        i4 = linearLayout4.getHeight();
                        i = ((LinearLayout.LayoutParams) linearLayout4.getLayoutParams()).topMargin;
                    } else {
                        i = 0;
                    }
                    SecureFolderConfirmLockHelper.this.mCallback.adjustPasswordViewWithIme(this.activityRootView, (height2 - i4) - i);
                }
            }
        }

        public final void stopListener() {
            try {
                if (this.activityRootView != null) {
                    Log.d("KKG::SecureFolderConfirmLockHelper", "Stop layout listener");
                    this.activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            } catch (Exception e) {
                Log.d("KKG::SecureFolderConfirmLockHelper", "Exception while stopping view listener : " + e.getMessage());
            }
        }
    }

    static {
        String str = Build.TYPE;
        DEBUG = "eng".equals(str) || "userdebug".equals(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.database.ContentObserver, com.samsung.android.settings.knox.SecureFolderConfirmLockHelper$2] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.samsung.android.settings.knox.SecureFolderConfirmLockHelper$1] */
    public SecureFolderConfirmLockHelper(Context context, int i, int i2, boolean z, KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback callback) {
        this.mPersonaManager = null;
        this.mDeviceHasSoftKeys = false;
        ?? r4 = new BroadcastReceiver() { // from class: com.samsung.android.settings.knox.SecureFolderConfirmLockHelper.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId()));
                Log.i("KKG::SecureFolderConfirmLockHelper", "mLockEventReceiver.onReceive() {action:" + intent.getAction() + " userId:" + intExtra + "}");
                KeyguardManager keyguardManager = (KeyguardManager) context2.getSystemService("keyguard");
                if (SecureFolderConfirmLockHelper.this.mUserId != intExtra || keyguardManager.isDeviceLocked(intExtra) || SecureFolderConfirmLockHelper.this.mReturnCredentials) {
                    return;
                }
                Log.i("KKG::SecureFolderConfirmLockHelper", "mLockEventReceiver.onReceive() authenticationSucceededCallback");
                ((ConfirmDeviceCredentialBaseFragment) SecureFolderConfirmLockHelper.this.mCallback).authenticationSucceeded();
            }
        };
        this.mLockEventReceiver = r4;
        ?? r0 = new ContentObserver(new Handler()) { // from class: com.samsung.android.settings.knox.SecureFolderConfirmLockHelper.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2, Uri uri, int i3) {
                Log.d("KKG::SecureFolderConfirmLockHelper", "onChange " + z2 + " / " + uri + " / " + i3);
                if (uri.equals(Settings.Secure.getUriFor("hide_secure_folder_flag")) && i3 == 0) {
                    int intForUser = Settings.Secure.getIntForUser(SecureFolderConfirmLockHelper.this.mContext.getContentResolver(), "hide_secure_folder_flag", 0, 0);
                    Activity activity = SecureFolderConfirmLockHelper.this.mActivity;
                    if (activity == null || intForUser == 0) {
                        return;
                    }
                    activity.finish();
                }
            }
        };
        this.contentObserver = r0;
        this.mContext = context;
        this.mUserId = i;
        this.mEffectiveUserId = i2;
        this.mReturnCredentials = z;
        this.mCallback = callback;
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        this.mLockPatternUtils = lockPatternUtils;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPersonaManager = (SemPersonaManager) context.getSystemService("persona");
        this.mInputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i2);
        this.quality = keyguardStoredPasswordQuality;
        this.mDeviceHasSoftKeys = context.getResources().getBoolean(Resources.getSystem().getIdentifier("config_showNavigationBar", "bool", RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        this.mDeviceScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        context.registerReceiverAsUser(r4, UserHandle.ALL, new IntentFilter("android.intent.action.DEVICE_LOCKED_CHANGED"), null, null);
        Log.d("KKG::SecureFolderConfirmLockHelper", "registerContentObserver - 0");
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("hide_secure_folder_flag"), false, r0, 0);
        ArrayList arrayList = new ArrayList();
        this.mKnoxEventList = arrayList;
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentLockType());
        if (KnoxUtils.isFingerprintLockSetForUser(context, i2)) {
            sb.append(" + fingerprint");
        }
        arrayList.add(KnoxSamsungAnalyticsLogger.addStatus(100, sb.toString()));
        StringBuilder sb2 = new StringBuilder("Init. KeyguardStoredPasswordQuality = ");
        sb2.append(keyguardStoredPasswordQuality == 0 ? SignalSeverity.NONE : keyguardStoredPasswordQuality <= 65536 ? "pattern" : keyguardStoredPasswordQuality <= 196608 ? "pin" : "Password");
        sb2.append(", applySecureFolderBackgroundCase = ");
        sb2.append(applySecureFolderBackgroundCase());
        Log.d("KKG::SecureFolderConfirmLockHelper", sb2.toString());
    }

    public final boolean applySecureFolderBackgroundCase() {
        return !this.mReturnCredentials;
    }

    public final String getCurrentLockType() {
        int i = this.quality;
        if (i == 65536) {
            return "pattern";
        }
        if (i != 131072 && i != 196608) {
            if (i != 262144 && i != 327680 && i != 393216) {
                if (i != 458752) {
                    if (i != 524288) {
                        return ApnSettings.MVNO_NONE;
                    }
                }
            }
            return HostAuth.PASSWORD;
        }
        return "pin";
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00eb  */
    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDefaultDetails() {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.knox.SecureFolderConfirmLockHelper.getDefaultDetails():int");
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final String getDefaultHeader(String str, boolean z) {
        return z ? this.mContext.getString(R.string.secure_folder_header_text_password) : this.mContext.getString(R.string.secure_folder_header_text_pin);
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final int getErrorMessage() {
        int i = this.quality;
        return i != 65536 ? (i == 131072 || i == 196608) ? R.string.cryptkeeper_wrong_pin : (i == 262144 || i == 327680 || i == 393216) ? R.string.cryptkeeper_wrong_password : R.string.sec_lockpassword_need_to_unlock_wrong : R.string.cryptkeeper_wrong_pattern;
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
    public final void interceptUpdateErrorMessageIfNeeded(int i) {
        if (i >= 15) {
            if (KnoxUtils.isResetWithSamsungAccountEnable(this.mContext, this.mEffectiveUserId)) {
                showSFLockedView(false, true);
                return;
            }
            KnoxLockScreenButtonController knoxLockScreenButtonController = this.mResetOrUninstallButton;
            if (knoxLockScreenButtonController != null) {
                knoxLockScreenButtonController.setVisibility(0);
            }
        }
    }

    public final boolean isInLandscapeMode() {
        int displayRotation = KnoxUtils.getDisplayRotation(this.mContext);
        return displayRotation == 1 || displayRotation == 3;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void needToUpdateErrorMessage(int i, TextView textView) {
        if (!KnoxUtils.isResetWithSamsungAccountEnable(this.mContext, this.mEffectiveUserId) || i < 10) {
            return;
        }
        int i2 = 15 - i;
        int i3 = this.quality;
        if (i3 == 65536) {
            textView.setText(this.mContext.getResources().getQuantityString(R.plurals.sec_incorrect_pattern_attempts_left, i2, Integer.valueOf(i2)));
            return;
        }
        if (i3 != 131072 && i3 != 196608) {
            if (i3 != 262144 && i3 != 327680 && i3 != 393216) {
                if (i3 != 458752) {
                    if (i3 != 524288) {
                        return;
                    }
                }
            }
            textView.setText(this.mContext.getResources().getQuantityString(R.plurals.sec_incorrect_password_attempts_left, i2, Integer.valueOf(i2)));
            return;
        }
        textView.setText(this.mContext.getResources().getQuantityString(R.plurals.sec_incorrect_pin_attempts_left, i2, Integer.valueOf(i2)));
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.mShouldShowForgetOrUninstallButton = bundle.getInt("show_forget_or_uninstall_button");
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onCreateView(View view) {
        if (applySecureFolderBackgroundCase()) {
            view.semSetRoundedCorners(0);
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onDestroy() {
        AnonymousClass1 anonymousClass1 = this.mLockEventReceiver;
        if (anonymousClass1 != null) {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
        if (this.contentObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.contentObserver);
        }
        PasswordViewLayoutListener passwordViewLayoutListener = this.mPasswordViewLayoutListener;
        if (passwordViewLayoutListener != null) {
            passwordViewLayoutListener.stopListener();
        }
        if (applySecureFolderBackgroundCase()) {
            KnoxSamsungAnalyticsLogger.send(this.mContext, this.mKnoxEventList, this.mEffectiveUserId);
        }
        this.mKnoxEventList.clear();
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void onSavedInstanceState(Bundle bundle) {
        KnoxLockScreenButtonController knoxLockScreenButtonController = this.mResetOrUninstallButton;
        if (knoxLockScreenButtonController != null) {
            TextView textView = knoxLockScreenButtonController.mTextView;
            bundle.putInt("show_forget_or_uninstall_button", textView == null ? 8 : textView.getVisibility());
        }
        this.mCallback.onSaveInstanceStateCallback();
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
        Log.d("KKG::SecureFolderConfirmLockHelper", "reportFailedAttempt");
        KnoxLockScreenButtonController knoxLockScreenButtonController = this.mResetOrUninstallButton;
        if (knoxLockScreenButtonController == null) {
            return true;
        }
        knoxLockScreenButtonController.setVisibility(0);
        return true;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void reportSuccessfulAttempt() {
        Log.d("KKG::SecureFolderConfirmLockHelper", "reportSuccessfulAttempt");
        this.mLockPatternUtils.clearBiometricAttemptDeadline(this.mEffectiveUserId);
        this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(100, 1000, getCurrentLockType()));
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setBiometricLockoutDeadline(int i) {
        this.mLockPatternUtils.setBiometricAttemptDeadline(this.mEffectiveUserId, i);
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final boolean setCredentialCheckResultTrackerIfNeeded(boolean z) {
        return z;
    }

    public final void setKnoxLogo(View view) {
        TextView textView = (TextView) view.findViewById(R.id.knoxTitleText);
        textView.setText(this.mPersonaManager.getContainerName(this.mEffectiveUserId, this.mContext));
        if (Rune.isSamsungDexMode(this.mContext)) {
            textView.setTextSize(1, this.mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_knoxTitletext_text_height_dex));
        }
        ((ImageView) view.findViewById(R.id.knoxLogo)).setImageDrawable(KnoxUtils.getSecureFolderLogo(this.mContext, this.mUserId));
        if (Rune.isSamsungDexMode(this.mContext)) {
            Context context = this.mContext;
            if (KnoxUtils.getActivityScreenWidth(context) > KnoxUtils.getActivityScreenHeight(context)) {
                return;
            }
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.knoxLogoLayout);
        if (linearLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            if (!KnoxUtils.isTablet()) {
                if (isInLandscapeMode()) {
                    layoutParams.topMargin = 0;
                    return;
                } else {
                    layoutParams.topMargin = (int) (KnoxUtils.getActivityScreenHeight(this.mContext) * 0.09d);
                    return;
                }
            }
            boolean isInLandscapeMode = isInLandscapeMode();
            int i = this.mDeviceScreenHeight;
            if (isInLandscapeMode) {
                layoutParams.topMargin = (int) (i * 0.08d);
            } else {
                layoutParams.topMargin = (int) (i * 0.1d);
            }
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setPasswordEntry(ImeAwareEditText imeAwareEditText) {
        if (applySecureFolderBackgroundCase()) {
            imeAwareEditText.setGravity(8388627);
            imeAwareEditText.setPadding((int) this.mContext.getResources().getDimension(R.dimen.knox_pwd_entry_padding_left_right), imeAwareEditText.getPaddingTop(), (int) this.mContext.getResources().getDimension(R.dimen.knox_pwd_entry_padding_left_right), imeAwareEditText.getPaddingBottom());
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setPatternColor(LockPatternView lockPatternView) {
        if (applySecureFolderBackgroundCase()) {
            lockPatternView.setColors(this.mContext.getResources().getColor(R.color.work_profile_lock_screen_text_color), this.mContext.getResources().getColor(R.color.status_bar_color), -65536);
        }
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void setShowPwdImgColor(ImageButton imageButton, boolean z) {
        if (applySecureFolderBackgroundCase() && imageButton != null) {
            Drawable drawable = z ? this.mContext.getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_show_mtrl, null) : this.mContext.getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_hide_mtrl, null);
            if (drawable == null) {
                Log.d("KKG::SecureFolderConfirmLockHelper", "Password show drawable is null.");
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
        KnoxUtils.InitLockState initLockState;
        if (applySecureFolderBackgroundCase()) {
            this.mActivity = fragmentActivity;
            ((AppBarLayout) fragmentActivity.findViewById(R.id.app_bar)).setVisibility(8);
            if (this.mIsInMultiWindowMode) {
                ((LinearLayout) view.findViewById(R.id.knox_logo_layout)).setPadding(0, 0, 0, 0);
                ImageView imageView = (ImageView) view.findViewById(R.id.knox_secure_logo);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.knox_logo_height_multiwindow);
                imageView.setLayoutParams(layoutParams);
            } else {
                boolean isInLandscapeMode = isInLandscapeMode();
                boolean z = this.mDeviceHasSoftKeys;
                if (isInLandscapeMode) {
                    LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.knox_logo_layout);
                    if (z) {
                        int navigationBarSize = (Utils.isTablet() || (KnoxUtils.isFoldableProduct() && this.mContext.getResources().getConfiguration().semDisplayDeviceType == 0)) ? KnoxUtils.getNavigationBarSize(this.mContext) : 0;
                        int navigationBarSize2 = SecurityUtils.isWinnerProduct() ? KnoxUtils.getNavigationBarSize(this.mContext) : 0;
                        if (KnoxUtils.getDisplayRotation(this.mContext) == 1) {
                            linearLayout.setPadding(0, 0, KnoxUtils.getNavigationBarSize(this.mContext), navigationBarSize);
                        } else {
                            linearLayout.setPadding(KnoxUtils.getNavigationBarSize(this.mContext), 0, navigationBarSize2, navigationBarSize);
                        }
                    }
                } else if (z) {
                    ((LinearLayout) view.findViewById(R.id.knox_logo_layout)).setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this.mContext));
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
                    Log.d("KKG::SecureFolderConfirmLockHelper", "Debug mode enabled");
                    window.clearFlags(8192);
                }
            }
            ImageView imageView2 = (ImageView) view.findViewById(R.id.background_image);
            if (imageView2 != null) {
                imageView2.setImageDrawable(KnoxUtils.getInflatedLayoutType(this.mContext) == 1000 ? this.mContext.getResources().getDrawable(R.drawable.sec_knox_credential_bg) : this.mContext.getResources().getDrawable(R.drawable.sec_knox_credential_bg_land));
            }
            Log.d("KKG::SecureFolderConfirmLockHelper", "Display rotation : " + KnoxUtils.getDisplayRotation(this.mContext));
            Log.d("KKG::SecureFolderConfirmLockHelper", "actvity width : " + KnoxUtils.getActivityScreenWidth(this.mContext));
            Log.d("KKG::SecureFolderConfirmLockHelper", "activity height : " + KnoxUtils.getActivityScreenHeight(this.mContext));
            Log.d("KKG::SecureFolderConfirmLockHelper", "Device screen width : " + Resources.getSystem().getDisplayMetrics().widthPixels);
            Preference$$ExternalSyntheticOutline0.m(new StringBuilder("Device screen height : "), Resources.getSystem().getDisplayMetrics().heightPixels, "KKG::SecureFolderConfirmLockHelper");
            Context context = this.mContext;
            int i = this.mEffectiveUserId;
            DialogFragment$$ExternalSyntheticOutline0.m("Samsung account : ", Settings.System.getStringForUser(context.getContentResolver(), "samsungaccount", i), "KKG::SecureFolderConfirmLockHelper");
            int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(i);
            KnoxUtils.InitLockState initLockState2 = KnoxUtils.InitLockState.SA_REMOVED;
            boolean hasSamsungAccount = KnoxUtils.hasSamsungAccount(this.mContext);
            KnoxUtils.InitLockState initLockState3 = KnoxUtils.InitLockState.VERIFICATION_FAILED;
            if (hasSamsungAccount) {
                boolean isResetWithSamsungAccountEnable = KnoxUtils.isResetWithSamsungAccountEnable(this.mContext, i);
                initLockState = KnoxUtils.InitLockState.SA_CHANGED_LOCK;
                if (!isResetWithSamsungAccountEnable || currentFailedPasswordAttempts < 15) {
                    if (!KnoxUtils.getSAccountLock(this.mContext, i)) {
                        initLockState = KnoxUtils.InitLockState.VERIFIED;
                    }
                } else if (!KnoxUtils.getSAccountLock(this.mContext, i)) {
                    initLockState = initLockState3;
                }
            } else {
                initLockState = initLockState2;
            }
            this.mSFLockState = initLockState;
            Log.i("KKG::SecureFolderConfirmLockHelper", "initLockState : " + initLockState);
            KnoxUtils.InitLockState initLockState4 = this.mSFLockState;
            if (initLockState4 == initLockState2 || initLockState4 == initLockState3) {
                showSFLockedView(false, false);
            }
        }
    }

    public final void showSFLockedView(boolean z, boolean z2) {
        Intent intent = new Intent();
        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.knox.SecureFolderLockedView");
        intent.addFlags(268468224);
        intent.putExtra("fromResetBtn", z);
        intent.putExtra("wasLastAttempt", z2);
        intent.putExtra("userId", this.mEffectiveUserId);
        try {
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            Activity activity = this.mActivity;
            if (activity != null) {
                intent.putExtras(activity.getIntent());
                if (this.mActivity.getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                    makeBasic.setLaunchTaskId(this.mActivity.getTaskId());
                }
            }
            this.mContext.startActivityAsUser(intent, makeBasic.toBundle(), new UserHandle(0));
            Context context = this.mContext;
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception24 : "), "KKG::SecureFolderConfirmLockHelper");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x01e2, code lost:
    
        if (r6 != 524288) goto L93;
     */
    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View switchViewIfNeeded(android.view.LayoutInflater r6, android.view.View r7, androidx.fragment.app.FragmentActivity r8) {
        /*
            Method dump skipped, instructions count: 595
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.knox.SecureFolderConfirmLockHelper.switchViewIfNeeded(android.view.LayoutInflater, android.view.View, androidx.fragment.app.FragmentActivity):android.view.View");
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmLockHelper
    public final void updateStageNeedToUnlock(TextView textView) {
        if (textView != null) {
            textView.setText(R.string.secure_folder_header_text_pattern);
        }
    }
}
