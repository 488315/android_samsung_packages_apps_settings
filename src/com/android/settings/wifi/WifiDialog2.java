package com.android.settings.wifi;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.custom.IKnoxCustomManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiDialog2 extends AlertDialog
        implements WifiConfigUiBase2, DialogInterface.OnClickListener {
    public WifiConfigController2 controller;
    public final boolean hideMeteredAndPrivacy;
    public final boolean hideSubmitButton;
    public final boolean isSysUiCaller;
    public final WifiDialog2Listener listener;
    public final int mode;
    public View view;
    public final WifiEntry wifiEntry;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface WifiDialog2Listener {
        default void onForget(WifiDialog2 dialog) {
            Intrinsics.checkNotNullParameter(dialog, "dialog");
        }

        default void onScan(WifiDialog2 dialog, String ssid) {
            Intrinsics.checkNotNullParameter(dialog, "dialog");
            Intrinsics.checkNotNullParameter(ssid, "ssid");
        }

        void onSubmit(WifiDialog2 wifiDialog2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WifiDialog2(Context context, WifiDialog2Listener listener, WifiEntry wifiEntry, int i) {
        this(
                context,
                listener,
                wifiEntry,
                i,
                0,
                false,
                IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void dispatchSubmit() {
        this.listener.onSubmit(this);
        dismiss();
    }

    public final WifiConfigController2 getController() {
        WifiConfigController2 wifiConfigController2 = this.controller;
        if (wifiConfigController2 != null) {
            return wifiConfigController2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("controller");
        throw null;
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final Button getForgetButton() {
        return getButton(-3);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final Button getSubmitButton() {
        return getButton(-1);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(dialogInterface, "dialogInterface");
        if (i != -3) {
            if (i != -1) {
                return;
            }
            this.listener.onSubmit(this);
            return;
        }
        Context context = getContext();
        WifiEntry wifiEntry = this.wifiEntry;
        Intrinsics.checkNotNull(wifiEntry);
        if (WifiUtils.isNetworkLockedDown(context, wifiEntry.getWifiConfiguration())) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getContext(), RestrictedLockUtilsInternal.getDeviceOwner(getContext()));
        } else {
            this.listener.onForget(this);
        }
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        Window window;
        if (this.isSysUiCaller && (window = getWindow()) != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            window.setType(2009);
            window.setAttributes(attributes);
        }
        View inflate = getLayoutInflater().inflate(R.layout.wifi_dialog, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
        this.view = inflate;
        setView$1(inflate);
        View view = this.view;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("view");
            throw null;
        }
        this.controller =
                new WifiConfigController2(
                        this, view, this.wifiEntry, this.mode, this.hideMeteredAndPrivacy);
        super.onCreate(bundle);
        if (this.hideSubmitButton) {
            WifiConfigController2 wifiConfigController2 = this.controller;
            if (wifiConfigController2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("controller");
                throw null;
            }
            Button submitButton = wifiConfigController2.mConfigUi.getSubmitButton();
            if (submitButton != null) {
                submitButton.setVisibility(8);
            }
        } else {
            WifiConfigController2 wifiConfigController22 = this.controller;
            if (wifiConfigController22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("controller");
                throw null;
            }
            wifiConfigController22.enableSubmitIfAppropriate();
        }
        if (this.wifiEntry == null) {
            WifiConfigController2 wifiConfigController23 = this.controller;
            if (wifiConfigController23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("controller");
                throw null;
            }
            Button forgetButton = wifiConfigController23.mConfigUi.getForgetButton();
            if (forgetButton == null) {
                return;
            }
            forgetButton.setVisibility(8);
        }
    }

    @Override // android.app.Dialog
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        WifiConfigController2 wifiConfigController2 = this.controller;
        if (wifiConfigController2 != null) {
            wifiConfigController2.updatePassword();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("controller");
            throw null;
        }
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onStart() {
        super.onStart();
        View requireViewById = requireViewById(R.id.ssid_scanner_button);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        ImageButton imageButton = (ImageButton) requireViewById;
        if (this.hideSubmitButton) {
            imageButton.setVisibility(8);
        } else {
            imageButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wifi.WifiDialog2$onStart$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            View requireViewById2 = WifiDialog2.this.requireViewById(R.id.ssid);
                            Intrinsics.checkNotNullExpressionValue(
                                    requireViewById2, "requireViewById(...)");
                            String obj = ((TextView) requireViewById2).getText().toString();
                            WifiDialog2 wifiDialog2 = WifiDialog2.this;
                            wifiDialog2.listener.onScan(wifiDialog2, obj);
                        }
                    });
        }
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setCancelButton(CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        setButton(-2, text, this);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setForgetButton(CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        setButton(-3, text, this);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setSubmitButton(CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        setButton(-1, text, this);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ WifiDialog2(
            android.content.Context r12,
            com.android.settings.wifi.WifiDialog2.WifiDialog2Listener r13,
            com.android.wifitrackerlib.WifiEntry r14,
            int r15,
            int r16,
            boolean r17,
            int r18) {
        /*
            r11 = this;
            r0 = r18 & 16
            r1 = 0
            if (r0 == 0) goto L7
            r7 = r1
            goto L9
        L7:
            r7 = r16
        L9:
            r0 = r18 & 32
            if (r0 == 0) goto L14
            if (r15 != 0) goto L11
            r0 = 1
            goto L12
        L11:
            r0 = r1
        L12:
            r8 = r0
            goto L15
        L14:
            r8 = r1
        L15:
            r0 = r18 & 64
            if (r0 == 0) goto L1b
            r9 = r1
            goto L1d
        L1b:
            r9 = r17
        L1d:
            r10 = 0
            r2 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiDialog2.<init>(android.content.Context,"
                    + " com.android.settings.wifi.WifiDialog2$WifiDialog2Listener,"
                    + " com.android.wifitrackerlib.WifiEntry, int, int, boolean, int):void");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiDialog2(
            Context context,
            WifiDialog2Listener listener,
            WifiEntry wifiEntry,
            int i,
            int i2,
            boolean z,
            boolean z2,
            boolean z3) {
        super(context, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
        this.wifiEntry = wifiEntry;
        this.mode = i;
        this.hideSubmitButton = z;
        this.hideMeteredAndPrivacy = z2;
        this.isSysUiCaller = z3;
    }
}
