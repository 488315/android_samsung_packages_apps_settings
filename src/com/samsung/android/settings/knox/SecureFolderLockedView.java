package com.samsung.android.settings.knox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.trust.TrustManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureFolderLockedView extends Activity {
    public AlertDialog mAlertDialog;
    public SecureFolderLockedView mContext;
    public int mEffectiveUserId;
    public boolean mHasEnteredWrongLastAttempt;
    public boolean mIsFromResetButton;
    public ArrayList mKnoxEventList;
    public LockPatternUtils mLockPatternUtils;
    public Window mWindow;
    public KnoxUtils.InitLockState mSFLockState = KnoxUtils.InitLockState.NONE;
    public boolean mDeviceHasSoftKeys = false;
    public boolean mIsInMultiWindowMode = false;
    public SemPersonaManager mPersonaManager = null;

    public final void callSamsungAccountConfirmationPage(int i) {
        Intent intent = new Intent();
        intent.setClassName(
                "com.samsung.knox.securefolder",
                "com.samsung.knox.securefolder.keyguard.KnoxKeyguardSamsungAccountBridge");
        intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
        intent.putExtras(getIntent());
        intent.putExtra("isFromForgotButton", true);
        intent.putExtra("hasSAccount", KnoxUtils.hasSamsungAccount(this.mContext));
        intent.putExtra("sa_state", i);
        intent.putExtra("userId", this.mEffectiveUserId);
        try {
            startActivityForResult(intent, 10001);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception24 : "), "KKG::SecureFolderLockedView");
        }
    }

    public final void initLockedView() {
        int displayRotation;
        Window window = this.mWindow;
        if (window != null) {
            window.setNavigationBarColor(0);
            window.setStatusBarColor(0);
            window.setNavigationBarContrastEnforced(false);
            window.getDecorView().setSystemUiVisibility(768);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.samsungFlags |= 67108864;
            window.setAttributes(attributes);
        }
        ((RelativeLayout) findViewById(R.id.main_layout)).setVisibility(0);
        ImageView imageView = (ImageView) findViewById(R.id.background_image);
        if (imageView != null) {
            imageView.setImageDrawable(
                    KnoxUtils.getInflatedLayoutType(this.mContext) == 1000
                            ? this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.sec_knox_credential_bg)
                            : this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.sec_knox_credential_bg_land));
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
        int displayRotation2 = KnoxUtils.getDisplayRotation(this.mContext);
        if ((displayRotation2 == 1 || displayRotation2 == 3) && !this.mIsInMultiWindowMode) {
            ((LinearLayout) linearLayout.findViewById(R.id.knoxLogoLayout))
                    .setPadding(
                            KnoxUtils.getNavigationBarSize(this.mContext),
                            0,
                            KnoxUtils.getNavigationBarSize(this.mContext),
                            0);
        }
        boolean isSamsungDexMode = Rune.isSamsungDexMode(this.mContext);
        if (this.mIsInMultiWindowMode && !KnoxUtils.isTablet() && !isSamsungDexMode) {
            int i = Resources.getSystem().getDisplayMetrics().heightPixels;
            LinearLayout linearLayout2 =
                    (LinearLayout)
                            this.mContext
                                    .getWindow()
                                    .getDecorView()
                                    .findViewById(R.id.knoxLogoLayout);
            char c =
                    KnoxUtils.getActivityScreenWidth(this.mContext)
                                    > KnoxUtils.getActivityScreenHeight(this.mContext)
                            ? (char) 1001
                            : (char) 1000;
            Log.d(
                    "KKG::SecureFolderLockedView",
                    "Inflated layout is : ".concat(c == 1000 ? "PORTRAIT" : "LANDSCAPE"));
            if (c != 1001) {
                ((ViewGroup.MarginLayoutParams) linearLayout2.getLayoutParams()).topMargin =
                        (int) (i * 0.07d);
            }
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.knoxLogo);
        if (imageView2 != null) {
            if (isSamsungDexMode
                    || !((displayRotation = KnoxUtils.getDisplayRotation(this.mContext)) == 1
                            || displayRotation == 3)) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
        }
        ImageView imageView3 = (ImageView) findViewById(R.id.knoxLockedLogo);
        if (imageView3 != null) {
            imageView3.setVisibility(0);
        }
        TextView textView = (TextView) findViewById(R.id.knoxTitleText);
        if (textView != null) {
            textView.setText(ApnSettings.MVNO_NONE);
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) findViewById(R.id.knox_locked);
        TextView textView3 = (TextView) findViewById(R.id.knox_locked_notice);
        TextView textView4 = (TextView) findViewById(R.id.go_to_saccount);
        TextView textView5 = (TextView) findViewById(R.id.sf_locked_bottom_btn);
        TextView textView6 = (TextView) findViewById(R.id.sf_uninstall_bottom_btn);
        String stringForUser =
                Settings.System.getStringForUser(
                        this.mContext.getContentResolver(),
                        "samsungaccount",
                        this.mEffectiveUserId);
        String knoxName = KnoxUtils.getKnoxName(this, SemPersonaManager.getSecureFolderId(this));
        if (textView2 != null) {
            SecureFolderLockedView secureFolderLockedView = this.mContext;
            textView2.setText(
                    secureFolderLockedView.getString(
                            R.string.secure_folder_locked,
                            this.mPersonaManager.getContainerName(
                                    this.mEffectiveUserId, secureFolderLockedView)));
            textView2.setVisibility(0);
        }
        if (textView3 != null) {
            textView3.setVisibility(0);
            if (textView5 == null) {
                Log.w("KKG::SecureFolderLockedView", "lockedBtn should not be null!");
                return;
            }
            textView5.setVisibility(0);
            int ordinal = this.mSFLockState.ordinal();
            if (ordinal == 2) {
                if (textView4 == null) {
                    Log.w("KKG::SecureFolderLockedView", "accountBtn should not be null !");
                    return;
                }
                textView4.setVisibility(0);
                textView3.setText(
                        this.mContext.getString(
                                R.string.keyguard_locked_reset_changed_account_b2c, stringForUser));
                textView4.setText(this.mContext.getString(R.string.go_to_samsungAccount));
                textView4.setPaintFlags(textView4.getPaintFlags() | 8);
                final int i2 = 0;
                textView4.setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.knox.SecureFolderLockedView.1
                            public final /* synthetic */ SecureFolderLockedView this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView2 =
                                                    this.this$0;
                                            if (secureFolderLockedView2.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView2.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        181, 1811, null));
                                        this.this$0.callSamsungAccountConfirmationPage(2);
                                        break;
                                    case 1:
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        181, 1810, null));
                                        this.this$0.finish();
                                        break;
                                    case 2:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView3 =
                                                    this.this$0;
                                            if (secureFolderLockedView3.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView3.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        182, 1820, null));
                                        this.this$0.callSamsungAccountConfirmationPage(3);
                                        break;
                                    case 3:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView4 =
                                                    this.this$0;
                                            if (secureFolderLockedView4.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView4.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        183, 1830, null));
                                        this.this$0.callSamsungAccountConfirmationPage(1);
                                        break;
                                    default:
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        183, 1831, null));
                                        final SecureFolderLockedView secureFolderLockedView5 =
                                                this.this$0;
                                        secureFolderLockedView5.getClass();
                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(
                                                        secureFolderLockedView5.mContext);
                                        builder.setTitle(
                                                R.string.bnr_secure_folder_backup_alert_title);
                                        builder.setMessage(
                                                R.string.keyguard_locked_uninstall_popup_msg);
                                        builder.setCancelable(true);
                                        final int i3 = 0;
                                        builder.setPositiveButton(
                                                R.string.knox_uninstall_dialog_title,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i4) {
                                                        switch (i3) {
                                                            case 0:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderLockedView5
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                String str = KnoxUtils.mDeviceType;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        "android.intent.extra.user_handle",
                                                                        0);
                                                                ContainerProxy.sendCommand(
                                                                        "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                        bundle);
                                                                Preference$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "Sending"
                                                                                            + " uninst"
                                                                                            + " Event"
                                                                                            + " to sf"
                                                                                            + " userid:"),
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId,
                                                                                "KKG::SecureFolderLockedView");
                                                                if (SemPersonaManager
                                                                        .isSecureFolderId(
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId)) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderLockedView",
                                                                            "Sending uninstallation"
                                                                                + " event to sf");
                                                                    try {
                                                                        secureFolderLockedView5
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .call(
                                                                                        Uri.parse(
                                                                                                "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                        "RemoveContainer",
                                                                                        (String)
                                                                                                null,
                                                                                        (Bundle)
                                                                                                null);
                                                                        break;
                                                                    } catch (
                                                                            IllegalArgumentException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            default:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        final int i4 = 1;
                                        builder.setNegativeButton(
                                                R.string.cancel,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i42) {
                                                        switch (i4) {
                                                            case 0:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderLockedView5
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                String str = KnoxUtils.mDeviceType;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        "android.intent.extra.user_handle",
                                                                        0);
                                                                ContainerProxy.sendCommand(
                                                                        "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                        bundle);
                                                                Preference$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "Sending"
                                                                                            + " uninst"
                                                                                            + " Event"
                                                                                            + " to sf"
                                                                                            + " userid:"),
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId,
                                                                                "KKG::SecureFolderLockedView");
                                                                if (SemPersonaManager
                                                                        .isSecureFolderId(
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId)) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderLockedView",
                                                                            "Sending uninstallation"
                                                                                + " event to sf");
                                                                    try {
                                                                        secureFolderLockedView5
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .call(
                                                                                        Uri.parse(
                                                                                                "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                        "RemoveContainer",
                                                                                        (String)
                                                                                                null,
                                                                                        (Bundle)
                                                                                                null);
                                                                        break;
                                                                    } catch (
                                                                            IllegalArgumentException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            default:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        AlertDialog create = builder.create();
                                        secureFolderLockedView5.mAlertDialog = create;
                                        if (!(secureFolderLockedView5.mContext
                                                instanceof Activity)) {
                                            try {
                                                create.getWindow()
                                                        .setType(
                                                                VolteConstants.ErrorCode
                                                                        .MAKECALL_REG_FAILURE_REG_403);
                                            } catch (NullPointerException e) {
                                                Log.e(
                                                        "KKG::SecureFolderLockedView",
                                                        "Exception29 : " + e.getMessage());
                                            }
                                        }
                                        secureFolderLockedView5.mAlertDialog
                                                .setCanceledOnTouchOutside(true);
                                        secureFolderLockedView5.mAlertDialog.setOnCancelListener(
                                                new DialogInterface
                                                        .OnCancelListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.8
                                                    @Override // android.content.DialogInterface.OnCancelListener
                                                    public final void onCancel(
                                                            DialogInterface dialogInterface) {
                                                        SecureFolderLockedView.this.mAlertDialog
                                                                .dismiss();
                                                        SecureFolderLockedView.this.mAlertDialog =
                                                                null;
                                                    }
                                                });
                                        secureFolderLockedView5.mAlertDialog.show();
                                        break;
                                }
                            }
                        });
                textView5.setText(
                        getString(
                                R.string.keyguard_locked_reset_changed_account_sf_close,
                                new Object[] {
                                    KnoxUtils.getKnoxName(
                                            this, SemPersonaManager.getSecureFolderId(this))
                                }));
                final int i3 = 1;
                textView5.setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.knox.SecureFolderLockedView.1
                            public final /* synthetic */ SecureFolderLockedView this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i3) {
                                    case 0:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView2 =
                                                    this.this$0;
                                            if (secureFolderLockedView2.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView2.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        181, 1811, null));
                                        this.this$0.callSamsungAccountConfirmationPage(2);
                                        break;
                                    case 1:
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        181, 1810, null));
                                        this.this$0.finish();
                                        break;
                                    case 2:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView3 =
                                                    this.this$0;
                                            if (secureFolderLockedView3.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView3.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        182, 1820, null));
                                        this.this$0.callSamsungAccountConfirmationPage(3);
                                        break;
                                    case 3:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView4 =
                                                    this.this$0;
                                            if (secureFolderLockedView4.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView4.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        183, 1830, null));
                                        this.this$0.callSamsungAccountConfirmationPage(1);
                                        break;
                                    default:
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        183, 1831, null));
                                        final SecureFolderLockedView secureFolderLockedView5 =
                                                this.this$0;
                                        secureFolderLockedView5.getClass();
                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(
                                                        secureFolderLockedView5.mContext);
                                        builder.setTitle(
                                                R.string.bnr_secure_folder_backup_alert_title);
                                        builder.setMessage(
                                                R.string.keyguard_locked_uninstall_popup_msg);
                                        builder.setCancelable(true);
                                        final int i32 = 0;
                                        builder.setPositiveButton(
                                                R.string.knox_uninstall_dialog_title,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i42) {
                                                        switch (i32) {
                                                            case 0:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderLockedView5
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                String str = KnoxUtils.mDeviceType;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        "android.intent.extra.user_handle",
                                                                        0);
                                                                ContainerProxy.sendCommand(
                                                                        "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                        bundle);
                                                                Preference$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "Sending"
                                                                                            + " uninst"
                                                                                            + " Event"
                                                                                            + " to sf"
                                                                                            + " userid:"),
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId,
                                                                                "KKG::SecureFolderLockedView");
                                                                if (SemPersonaManager
                                                                        .isSecureFolderId(
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId)) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderLockedView",
                                                                            "Sending uninstallation"
                                                                                + " event to sf");
                                                                    try {
                                                                        secureFolderLockedView5
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .call(
                                                                                        Uri.parse(
                                                                                                "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                        "RemoveContainer",
                                                                                        (String)
                                                                                                null,
                                                                                        (Bundle)
                                                                                                null);
                                                                        break;
                                                                    } catch (
                                                                            IllegalArgumentException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            default:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        final int i4 = 1;
                                        builder.setNegativeButton(
                                                R.string.cancel,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i42) {
                                                        switch (i4) {
                                                            case 0:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderLockedView5
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                String str = KnoxUtils.mDeviceType;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        "android.intent.extra.user_handle",
                                                                        0);
                                                                ContainerProxy.sendCommand(
                                                                        "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                        bundle);
                                                                Preference$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "Sending"
                                                                                            + " uninst"
                                                                                            + " Event"
                                                                                            + " to sf"
                                                                                            + " userid:"),
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId,
                                                                                "KKG::SecureFolderLockedView");
                                                                if (SemPersonaManager
                                                                        .isSecureFolderId(
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId)) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderLockedView",
                                                                            "Sending uninstallation"
                                                                                + " event to sf");
                                                                    try {
                                                                        secureFolderLockedView5
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .call(
                                                                                        Uri.parse(
                                                                                                "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                        "RemoveContainer",
                                                                                        (String)
                                                                                                null,
                                                                                        (Bundle)
                                                                                                null);
                                                                        break;
                                                                    } catch (
                                                                            IllegalArgumentException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            default:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        AlertDialog create = builder.create();
                                        secureFolderLockedView5.mAlertDialog = create;
                                        if (!(secureFolderLockedView5.mContext
                                                instanceof Activity)) {
                                            try {
                                                create.getWindow()
                                                        .setType(
                                                                VolteConstants.ErrorCode
                                                                        .MAKECALL_REG_FAILURE_REG_403);
                                            } catch (NullPointerException e) {
                                                Log.e(
                                                        "KKG::SecureFolderLockedView",
                                                        "Exception29 : " + e.getMessage());
                                            }
                                        }
                                        secureFolderLockedView5.mAlertDialog
                                                .setCanceledOnTouchOutside(true);
                                        secureFolderLockedView5.mAlertDialog.setOnCancelListener(
                                                new DialogInterface
                                                        .OnCancelListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.8
                                                    @Override // android.content.DialogInterface.OnCancelListener
                                                    public final void onCancel(
                                                            DialogInterface dialogInterface) {
                                                        SecureFolderLockedView.this.mAlertDialog
                                                                .dismiss();
                                                        SecureFolderLockedView.this.mAlertDialog =
                                                                null;
                                                    }
                                                });
                                        secureFolderLockedView5.mAlertDialog.show();
                                        break;
                                }
                            }
                        });
                textView6.setVisibility(8);
                return;
            }
            if (ordinal == 4) {
                textView3.setText(
                        this.mContext.getString(
                                R.string.keyguard_locked_reset_unchanged_account_sf,
                                knoxName,
                                knoxName));
                textView4.setText(stringForUser);
                textView4.setTextColor(
                        this.mContext.getResources().getColor(R.color.themeDefaultTextColor, null));
                textView5.setText(R.string.keyguard_sign_in);
                final int i4 = 2;
                textView5.setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.knox.SecureFolderLockedView.1
                            public final /* synthetic */ SecureFolderLockedView this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i4) {
                                    case 0:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView2 =
                                                    this.this$0;
                                            if (secureFolderLockedView2.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView2.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        181, 1811, null));
                                        this.this$0.callSamsungAccountConfirmationPage(2);
                                        break;
                                    case 1:
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        181, 1810, null));
                                        this.this$0.finish();
                                        break;
                                    case 2:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView3 =
                                                    this.this$0;
                                            if (secureFolderLockedView3.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView3.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        182, 1820, null));
                                        this.this$0.callSamsungAccountConfirmationPage(3);
                                        break;
                                    case 3:
                                        if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                            SecureFolderLockedView secureFolderLockedView4 =
                                                    this.this$0;
                                            if (secureFolderLockedView4.mIsInMultiWindowMode) {
                                                Toast.makeText(
                                                                secureFolderLockedView4.mContext,
                                                                R.string
                                                                        .lock_screen_doesnt_support_multi_window_text,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        }
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        183, 1830, null));
                                        this.this$0.callSamsungAccountConfirmationPage(1);
                                        break;
                                    default:
                                        this.this$0.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        183, 1831, null));
                                        final SecureFolderLockedView secureFolderLockedView5 =
                                                this.this$0;
                                        secureFolderLockedView5.getClass();
                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(
                                                        secureFolderLockedView5.mContext);
                                        builder.setTitle(
                                                R.string.bnr_secure_folder_backup_alert_title);
                                        builder.setMessage(
                                                R.string.keyguard_locked_uninstall_popup_msg);
                                        builder.setCancelable(true);
                                        final int i32 = 0;
                                        builder.setPositiveButton(
                                                R.string.knox_uninstall_dialog_title,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i42) {
                                                        switch (i32) {
                                                            case 0:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderLockedView5
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                String str = KnoxUtils.mDeviceType;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        "android.intent.extra.user_handle",
                                                                        0);
                                                                ContainerProxy.sendCommand(
                                                                        "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                        bundle);
                                                                Preference$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "Sending"
                                                                                            + " uninst"
                                                                                            + " Event"
                                                                                            + " to sf"
                                                                                            + " userid:"),
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId,
                                                                                "KKG::SecureFolderLockedView");
                                                                if (SemPersonaManager
                                                                        .isSecureFolderId(
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId)) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderLockedView",
                                                                            "Sending uninstallation"
                                                                                + " event to sf");
                                                                    try {
                                                                        secureFolderLockedView5
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .call(
                                                                                        Uri.parse(
                                                                                                "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                        "RemoveContainer",
                                                                                        (String)
                                                                                                null,
                                                                                        (Bundle)
                                                                                                null);
                                                                        break;
                                                                    } catch (
                                                                            IllegalArgumentException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            default:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        final int i42 = 1;
                                        builder.setNegativeButton(
                                                R.string.cancel,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i422) {
                                                        switch (i42) {
                                                            case 0:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderLockedView5
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                String str = KnoxUtils.mDeviceType;
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(
                                                                        "android.intent.extra.user_handle",
                                                                        0);
                                                                ContainerProxy.sendCommand(
                                                                        "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                        bundle);
                                                                Preference$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "Sending"
                                                                                            + " uninst"
                                                                                            + " Event"
                                                                                            + " to sf"
                                                                                            + " userid:"),
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId,
                                                                                "KKG::SecureFolderLockedView");
                                                                if (SemPersonaManager
                                                                        .isSecureFolderId(
                                                                                secureFolderLockedView5
                                                                                        .mEffectiveUserId)) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderLockedView",
                                                                            "Sending uninstallation"
                                                                                + " event to sf");
                                                                    try {
                                                                        secureFolderLockedView5
                                                                                .mContext
                                                                                .getContentResolver()
                                                                                .call(
                                                                                        Uri.parse(
                                                                                                "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                        "RemoveContainer",
                                                                                        (String)
                                                                                                null,
                                                                                        (Bundle)
                                                                                                null);
                                                                        break;
                                                                    } catch (
                                                                            IllegalArgumentException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            default:
                                                                secureFolderLockedView5
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        AlertDialog create = builder.create();
                                        secureFolderLockedView5.mAlertDialog = create;
                                        if (!(secureFolderLockedView5.mContext
                                                instanceof Activity)) {
                                            try {
                                                create.getWindow()
                                                        .setType(
                                                                VolteConstants.ErrorCode
                                                                        .MAKECALL_REG_FAILURE_REG_403);
                                            } catch (NullPointerException e) {
                                                Log.e(
                                                        "KKG::SecureFolderLockedView",
                                                        "Exception29 : " + e.getMessage());
                                            }
                                        }
                                        secureFolderLockedView5.mAlertDialog
                                                .setCanceledOnTouchOutside(true);
                                        secureFolderLockedView5.mAlertDialog.setOnCancelListener(
                                                new DialogInterface
                                                        .OnCancelListener() { // from class:
                                                    // com.samsung.android.settings.knox.SecureFolderLockedView.8
                                                    @Override // android.content.DialogInterface.OnCancelListener
                                                    public final void onCancel(
                                                            DialogInterface dialogInterface) {
                                                        SecureFolderLockedView.this.mAlertDialog
                                                                .dismiss();
                                                        SecureFolderLockedView.this.mAlertDialog =
                                                                null;
                                                    }
                                                });
                                        secureFolderLockedView5.mAlertDialog.show();
                                        break;
                                }
                            }
                        });
                return;
            }
            if (this.mSFLockState != KnoxUtils.InitLockState.SA_REMOVED) {
                Log.d(
                        "KKG::SecureFolderLockedView",
                        "Something was terribly wrong. Give UNINSTALL button to user!");
            }
            if (textView6 == null) {
                Log.w("KKG::SecureFolderLockedView", "uninstallBtn should not be null !");
                return;
            }
            textView6.setVisibility(0);
            SecureFolderLockedView secureFolderLockedView2 = this.mContext;
            textView3.setText(
                    secureFolderLockedView2.getString(
                            R.string.keyguard_locked_reset_removed_account_msg,
                            this.mPersonaManager.getContainerName(
                                    this.mEffectiveUserId, secureFolderLockedView2),
                            stringForUser));
            textView5.setText(R.string.keyguard_sign_in);
            final int i5 = 3;
            textView5.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.knox.SecureFolderLockedView.1
                        public final /* synthetic */ SecureFolderLockedView this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i5) {
                                case 0:
                                    if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                        SecureFolderLockedView secureFolderLockedView22 =
                                                this.this$0;
                                        if (secureFolderLockedView22.mIsInMultiWindowMode) {
                                            Toast.makeText(
                                                            secureFolderLockedView22.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    }
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(181, 1811, null));
                                    this.this$0.callSamsungAccountConfirmationPage(2);
                                    break;
                                case 1:
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(181, 1810, null));
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                        SecureFolderLockedView secureFolderLockedView3 =
                                                this.this$0;
                                        if (secureFolderLockedView3.mIsInMultiWindowMode) {
                                            Toast.makeText(
                                                            secureFolderLockedView3.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    }
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(182, 1820, null));
                                    this.this$0.callSamsungAccountConfirmationPage(3);
                                    break;
                                case 3:
                                    if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                        SecureFolderLockedView secureFolderLockedView4 =
                                                this.this$0;
                                        if (secureFolderLockedView4.mIsInMultiWindowMode) {
                                            Toast.makeText(
                                                            secureFolderLockedView4.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    }
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(183, 1830, null));
                                    this.this$0.callSamsungAccountConfirmationPage(1);
                                    break;
                                default:
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(183, 1831, null));
                                    final SecureFolderLockedView secureFolderLockedView5 =
                                            this.this$0;
                                    secureFolderLockedView5.getClass();
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(
                                                    secureFolderLockedView5.mContext);
                                    builder.setTitle(R.string.bnr_secure_folder_backup_alert_title);
                                    builder.setMessage(
                                            R.string.keyguard_locked_uninstall_popup_msg);
                                    builder.setCancelable(true);
                                    final int i32 = 0;
                                    builder.setPositiveButton(
                                            R.string.knox_uninstall_dialog_title,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i422) {
                                                    switch (i32) {
                                                        case 0:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1833,
                                                                                            null));
                                                            secureFolderLockedView5
                                                                    .mAlertDialog
                                                                    .getButton(-1)
                                                                    .setEnabled(false);
                                                            String str = KnoxUtils.mDeviceType;
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                            ContainerProxy.sendCommand(
                                                                    "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                    bundle);
                                                            Preference$$ExternalSyntheticOutline0.m(
                                                                    new StringBuilder(
                                                                            "Sending uninst Event"
                                                                                + " to sf userid:"),
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId,
                                                                    "KKG::SecureFolderLockedView");
                                                            if (SemPersonaManager.isSecureFolderId(
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId)) {
                                                                Log.d(
                                                                        "KKG::SecureFolderLockedView",
                                                                        "Sending uninstallation"
                                                                            + " event to sf");
                                                                try {
                                                                    secureFolderLockedView5
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                    "RemoveContainer",
                                                                                    (String) null,
                                                                                    (Bundle) null);
                                                                    break;
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    e.printStackTrace();
                                                                    return;
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1832,
                                                                                            null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                    }
                                                }
                                            });
                                    final int i42 = 1;
                                    builder.setNegativeButton(
                                            R.string.cancel,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i422) {
                                                    switch (i42) {
                                                        case 0:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1833,
                                                                                            null));
                                                            secureFolderLockedView5
                                                                    .mAlertDialog
                                                                    .getButton(-1)
                                                                    .setEnabled(false);
                                                            String str = KnoxUtils.mDeviceType;
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                            ContainerProxy.sendCommand(
                                                                    "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                    bundle);
                                                            Preference$$ExternalSyntheticOutline0.m(
                                                                    new StringBuilder(
                                                                            "Sending uninst Event"
                                                                                + " to sf userid:"),
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId,
                                                                    "KKG::SecureFolderLockedView");
                                                            if (SemPersonaManager.isSecureFolderId(
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId)) {
                                                                Log.d(
                                                                        "KKG::SecureFolderLockedView",
                                                                        "Sending uninstallation"
                                                                            + " event to sf");
                                                                try {
                                                                    secureFolderLockedView5
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                    "RemoveContainer",
                                                                                    (String) null,
                                                                                    (Bundle) null);
                                                                    break;
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    e.printStackTrace();
                                                                    return;
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1832,
                                                                                            null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                    }
                                                }
                                            });
                                    AlertDialog create = builder.create();
                                    secureFolderLockedView5.mAlertDialog = create;
                                    if (!(secureFolderLockedView5.mContext instanceof Activity)) {
                                        try {
                                            create.getWindow()
                                                    .setType(
                                                            VolteConstants.ErrorCode
                                                                    .MAKECALL_REG_FAILURE_REG_403);
                                        } catch (NullPointerException e) {
                                            Log.e(
                                                    "KKG::SecureFolderLockedView",
                                                    "Exception29 : " + e.getMessage());
                                        }
                                    }
                                    secureFolderLockedView5.mAlertDialog.setCanceledOnTouchOutside(
                                            true);
                                    secureFolderLockedView5.mAlertDialog.setOnCancelListener(
                                            new DialogInterface.OnCancelListener() { // from class:
                                                // com.samsung.android.settings.knox.SecureFolderLockedView.8
                                                @Override // android.content.DialogInterface.OnCancelListener
                                                public final void onCancel(
                                                        DialogInterface dialogInterface) {
                                                    SecureFolderLockedView.this.mAlertDialog
                                                            .dismiss();
                                                    SecureFolderLockedView.this.mAlertDialog = null;
                                                }
                                            });
                                    secureFolderLockedView5.mAlertDialog.show();
                                    break;
                            }
                        }
                    });
            textView6.setText(R.string.uninstall);
            final int i6 = 4;
            textView6.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.knox.SecureFolderLockedView.1
                        public final /* synthetic */ SecureFolderLockedView this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i6) {
                                case 0:
                                    if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                        SecureFolderLockedView secureFolderLockedView22 =
                                                this.this$0;
                                        if (secureFolderLockedView22.mIsInMultiWindowMode) {
                                            Toast.makeText(
                                                            secureFolderLockedView22.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    }
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(181, 1811, null));
                                    this.this$0.callSamsungAccountConfirmationPage(2);
                                    break;
                                case 1:
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(181, 1810, null));
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                        SecureFolderLockedView secureFolderLockedView3 =
                                                this.this$0;
                                        if (secureFolderLockedView3.mIsInMultiWindowMode) {
                                            Toast.makeText(
                                                            secureFolderLockedView3.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    }
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(182, 1820, null));
                                    this.this$0.callSamsungAccountConfirmationPage(3);
                                    break;
                                case 3:
                                    if (!Rune.isSamsungDexMode(this.this$0.mContext)) {
                                        SecureFolderLockedView secureFolderLockedView4 =
                                                this.this$0;
                                        if (secureFolderLockedView4.mIsInMultiWindowMode) {
                                            Toast.makeText(
                                                            secureFolderLockedView4.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    }
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(183, 1830, null));
                                    this.this$0.callSamsungAccountConfirmationPage(1);
                                    break;
                                default:
                                    this.this$0.mKnoxEventList.add(
                                            KnoxSamsungAnalyticsLogger.addEvent(183, 1831, null));
                                    final SecureFolderLockedView secureFolderLockedView5 =
                                            this.this$0;
                                    secureFolderLockedView5.getClass();
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(
                                                    secureFolderLockedView5.mContext);
                                    builder.setTitle(R.string.bnr_secure_folder_backup_alert_title);
                                    builder.setMessage(
                                            R.string.keyguard_locked_uninstall_popup_msg);
                                    builder.setCancelable(true);
                                    final int i32 = 0;
                                    builder.setPositiveButton(
                                            R.string.knox_uninstall_dialog_title,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i422) {
                                                    switch (i32) {
                                                        case 0:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1833,
                                                                                            null));
                                                            secureFolderLockedView5
                                                                    .mAlertDialog
                                                                    .getButton(-1)
                                                                    .setEnabled(false);
                                                            String str = KnoxUtils.mDeviceType;
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                            ContainerProxy.sendCommand(
                                                                    "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                    bundle);
                                                            Preference$$ExternalSyntheticOutline0.m(
                                                                    new StringBuilder(
                                                                            "Sending uninst Event"
                                                                                + " to sf userid:"),
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId,
                                                                    "KKG::SecureFolderLockedView");
                                                            if (SemPersonaManager.isSecureFolderId(
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId)) {
                                                                Log.d(
                                                                        "KKG::SecureFolderLockedView",
                                                                        "Sending uninstallation"
                                                                            + " event to sf");
                                                                try {
                                                                    secureFolderLockedView5
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                    "RemoveContainer",
                                                                                    (String) null,
                                                                                    (Bundle) null);
                                                                    break;
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    e.printStackTrace();
                                                                    return;
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1832,
                                                                                            null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                    }
                                                }
                                            });
                                    final int i42 = 1;
                                    builder.setNegativeButton(
                                            R.string.cancel,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.samsung.android.settings.knox.SecureFolderLockedView.6
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i422) {
                                                    switch (i42) {
                                                        case 0:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1833,
                                                                                            null));
                                                            secureFolderLockedView5
                                                                    .mAlertDialog
                                                                    .getButton(-1)
                                                                    .setEnabled(false);
                                                            String str = KnoxUtils.mDeviceType;
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt(
                                                                    "android.intent.extra.user_handle",
                                                                    0);
                                                            ContainerProxy.sendCommand(
                                                                    "knox.container.proxy.COMMAND_SWITCH_PROFILE",
                                                                    bundle);
                                                            Preference$$ExternalSyntheticOutline0.m(
                                                                    new StringBuilder(
                                                                            "Sending uninst Event"
                                                                                + " to sf userid:"),
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId,
                                                                    "KKG::SecureFolderLockedView");
                                                            if (SemPersonaManager.isSecureFolderId(
                                                                    secureFolderLockedView5
                                                                            .mEffectiveUserId)) {
                                                                Log.d(
                                                                        "KKG::SecureFolderLockedView",
                                                                        "Sending uninstallation"
                                                                            + " event to sf");
                                                                try {
                                                                    secureFolderLockedView5
                                                                            .mContext
                                                                            .getContentResolver()
                                                                            .call(
                                                                                    Uri.parse(
                                                                                            "content://0@com.samsung.knox.securefolder.provider.removecontainer"),
                                                                                    "RemoveContainer",
                                                                                    (String) null,
                                                                                    (Bundle) null);
                                                                    break;
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e) {
                                                                    e.printStackTrace();
                                                                    return;
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            secureFolderLockedView5.mKnoxEventList
                                                                    .add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1832,
                                                                                            null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                    }
                                                }
                                            });
                                    AlertDialog create = builder.create();
                                    secureFolderLockedView5.mAlertDialog = create;
                                    if (!(secureFolderLockedView5.mContext instanceof Activity)) {
                                        try {
                                            create.getWindow()
                                                    .setType(
                                                            VolteConstants.ErrorCode
                                                                    .MAKECALL_REG_FAILURE_REG_403);
                                        } catch (NullPointerException e) {
                                            Log.e(
                                                    "KKG::SecureFolderLockedView",
                                                    "Exception29 : " + e.getMessage());
                                        }
                                    }
                                    secureFolderLockedView5.mAlertDialog.setCanceledOnTouchOutside(
                                            true);
                                    secureFolderLockedView5.mAlertDialog.setOnCancelListener(
                                            new DialogInterface.OnCancelListener() { // from class:
                                                // com.samsung.android.settings.knox.SecureFolderLockedView.8
                                                @Override // android.content.DialogInterface.OnCancelListener
                                                public final void onCancel(
                                                        DialogInterface dialogInterface) {
                                                    SecureFolderLockedView.this.mAlertDialog
                                                            .dismiss();
                                                    SecureFolderLockedView.this.mAlertDialog = null;
                                                }
                                            });
                                    secureFolderLockedView5.mAlertDialog.show();
                                    break;
                            }
                        }
                    });
        }
    }

    public final void initSFLockState() {
        int currentFailedPasswordAttempts =
                this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId);
        KnoxUtils.InitLockState initLockState = KnoxUtils.InitLockState.SA_REMOVED;
        if (KnoxUtils.hasSamsungAccount(this.mContext)) {
            boolean isResetWithSamsungAccountEnable =
                    KnoxUtils.isResetWithSamsungAccountEnable(this.mContext, this.mEffectiveUserId);
            KnoxUtils.InitLockState initLockState2 = KnoxUtils.InitLockState.SA_CHANGED_LOCK;
            if (!isResetWithSamsungAccountEnable || currentFailedPasswordAttempts < 15) {
                if (!KnoxUtils.getSAccountLock(this.mContext, this.mEffectiveUserId)) {
                    initLockState = KnoxUtils.InitLockState.VERIFIED;
                }
                initLockState = initLockState2;
            } else {
                if (!KnoxUtils.getSAccountLock(this.mContext, this.mEffectiveUserId)) {
                    initLockState = KnoxUtils.InitLockState.VERIFICATION_FAILED;
                }
                initLockState = initLockState2;
            }
        }
        this.mSFLockState = initLockState;
        Log.i("KKG::SecureFolderLockedView", "initLockState : " + initLockState);
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "ResultCode = ", ", RequestCode = ", i2, i, "KKG::SecureFolderLockedView");
        if (i != 10001) {
            finish();
            return;
        }
        initSFLockState();
        if (i2 != -1) {
            finish();
            return;
        }
        boolean z = this.mIsFromResetButton;
        KnoxUtils.InitLockState initLockState = KnoxUtils.InitLockState.SA_CHANGED_LOCK;
        KnoxUtils.InitLockState initLockState2 = KnoxUtils.InitLockState.VERIFIED;
        if (z || this.mHasEnteredWrongLastAttempt) {
            KnoxUtils.InitLockState initLockState3 = this.mSFLockState;
            if (initLockState3 == initLockState2) {
                finish();
                return;
            } else if (initLockState3 == initLockState) {
                initLockedView();
                return;
            } else {
                Log.d("KKG::SecureFolderLockedView", "This should not be executed");
                return;
            }
        }
        KnoxUtils.InitLockState initLockState4 = this.mSFLockState;
        if (initLockState4 != initLockState2 && initLockState4 != initLockState) {
            initLockedView();
            return;
        }
        IntentSender intentSender =
                (IntentSender) getIntent().getParcelableExtra("android.intent.extra.INTENT");
        try {
            if (intentSender != null) {
                finish();
                startIntentSenderForResult(intentSender, -1, null, 0, 0, 0);
            } else {
                Log.i("KKG::SecureFolderLockedView", "IntentSender is null : ");
                finish();
            }
        } catch (Exception e) {
            Log.d("KKG::SecureFolderLockedView", "Exception launching intent : " + e.getMessage());
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        super.onBackPressed();
        IntentSender intentSender =
                (IntentSender) getIntent().getParcelableExtra("android.intent.extra.INTENT");
        if (this.mSFLockState == KnoxUtils.InitLockState.VERIFICATION_FAILED
                && intentSender == null) {
            ((TrustManager) getSystemService(TrustManager.class))
                    .setDeviceLockedForUser(this.mEffectiveUserId, true);
            String str = KnoxUtils.mDeviceType;
            Bundle bundle = new Bundle();
            bundle.putInt("android.intent.extra.user_handle", 0);
            ContainerProxy.sendCommand("knox.container.proxy.COMMAND_SWITCH_PROFILE", bundle);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("KKG::SecureFolderLockedView", "onCreate");
        this.mContext = this;
        this.mPersonaManager = (SemPersonaManager) getSystemService("persona");
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mEffectiveUserId = getIntent().getIntExtra("userId", 0);
        this.mIsFromResetButton = getIntent().getBooleanExtra("fromResetBtn", false);
        this.mHasEnteredWrongLastAttempt = getIntent().getBooleanExtra("wasLastAttempt", false);
        StringBuilder sb = new StringBuilder("mEffectiveUserId : ");
        sb.append(this.mEffectiveUserId);
        sb.append(", fromResetBtn : ");
        sb.append(this.mIsFromResetButton);
        sb.append(", wasLastAttempt : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                sb, this.mHasEnteredWrongLastAttempt, "KKG::SecureFolderLockedView");
        this.mDeviceHasSoftKeys =
                this.mContext
                        .getResources()
                        .getBoolean(
                                Resources.getSystem()
                                        .getIdentifier(
                                                "config_showNavigationBar",
                                                "bool",
                                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        this.mKnoxEventList = new ArrayList();
        initSFLockState();
        setContentView(R.layout.secure_folder_locked_view);
        Window window = getWindow();
        this.mWindow = window;
        window.getDecorView()
                .setBackgroundColor(
                        this.mContext
                                .getResources()
                                .getColor(R.color.secure_folder_background_color, null));
        this.mIsInMultiWindowMode = isInMultiWindowMode();
        ((ImageView) findViewById(R.id.knoxLogo))
                .setImageDrawable(
                        KnoxUtils.getSecureFolderLogo(this.mContext, this.mEffectiveUserId));
        View decorView = this.mContext.getWindow().getDecorView();
        if (this.mIsInMultiWindowMode) {
            ((LinearLayout) decorView.findViewById(R.id.knox_logo_layout)).setPadding(0, 0, 0, 0);
            ImageView imageView = (ImageView) decorView.findViewById(R.id.knox_secure_logo);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.knox_logo_height_multiwindow);
            imageView.setLayoutParams(layoutParams);
        } else {
            int displayRotation = KnoxUtils.getDisplayRotation(this.mContext);
            if (displayRotation == 1 || displayRotation == 3) {
                LinearLayout linearLayout =
                        (LinearLayout) decorView.findViewById(R.id.knox_logo_layout);
                if (this.mDeviceHasSoftKeys) {
                    if (KnoxUtils.getDisplayRotation(this.mContext) == 1) {
                        linearLayout.setPadding(
                                0, 0, KnoxUtils.getNavigationBarSize(this.mContext), 0);
                    } else {
                        linearLayout.setPadding(
                                KnoxUtils.getNavigationBarSize(this.mContext), 0, 0, 0);
                    }
                }
            } else if (this.mDeviceHasSoftKeys) {
                ((LinearLayout) decorView.findViewById(R.id.knox_logo_layout))
                        .setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this.mContext));
            }
        }
        if (bundle == null && this.mIsFromResetButton) {
            callSamsungAccountConfirmationPage(0);
        } else {
            initLockedView();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        KnoxSamsungAnalyticsLogger.send(this.mContext, this.mKnoxEventList, this.mEffectiveUserId);
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mIsFromResetButton = bundle.getBoolean("fromResetBtn", false);
        this.mHasEnteredWrongLastAttempt = bundle.getBoolean("wasLastAttempt", false);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.i("KKG::SecureFolderLockedView", "onResume");
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("fromResetBtn", this.mIsFromResetButton);
        bundle.putBoolean("wasLastAttempt", this.mHasEnteredWrongLastAttempt);
    }
}
