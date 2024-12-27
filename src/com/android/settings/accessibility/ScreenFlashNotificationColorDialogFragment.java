package com.android.settings.accessibility;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;

import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenFlashNotificationColorDialogFragment extends DialogFragment
        implements ColorSelectorLayout.OnCheckedChangeListener {
    public Consumer mConsumer;
    public int mCurrentColor = 0;
    public Timer mTimer = null;
    public Boolean mIsPreview = Boolean.FALSE;

    public static ScreenFlashNotificationColorDialogFragment getInstance(int i, Consumer consumer) {
        ScreenFlashNotificationColorDialogFragment screenFlashNotificationColorDialogFragment =
                new ScreenFlashNotificationColorDialogFragment();
        screenFlashNotificationColorDialogFragment.mCurrentColor = i;
        screenFlashNotificationColorDialogFragment.mConsumer = consumer;
        return screenFlashNotificationColorDialogFragment;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.layout_color_selector_dialog, (ViewGroup) null);
        final ColorSelectorLayout colorSelectorLayout =
                (ColorSelectorLayout) inflate.findViewById(R.id.color_selector_preference);
        if (colorSelectorLayout != null) {
            colorSelectorLayout.setOnCheckedChangeListener(this);
            colorSelectorLayout.setCheckedColor(this.mCurrentColor);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        builder.setTitle(R.string.screen_flash_notification_color_title);
        builder.setNeutralButton(
                R.string.color_selector_dialog_cancel,
                new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
        builder.setNegativeButton(
                R.string.flash_notifications_preview, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(
                R.string.color_selector_dialog_done,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ScreenFlashNotificationColorDialogFragment
                                screenFlashNotificationColorDialogFragment =
                                        ScreenFlashNotificationColorDialogFragment.this;
                        ColorSelectorLayout colorSelectorLayout2 = colorSelectorLayout;
                        screenFlashNotificationColorDialogFragment.getClass();
                        int checkedColor =
                                colorSelectorLayout2.getCheckedColor(
                                        FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR);
                        screenFlashNotificationColorDialogFragment.mCurrentColor = checkedColor;
                        screenFlashNotificationColorDialogFragment.mConsumer.accept(
                                Integer.valueOf(checkedColor));
                    }
                });
        final AlertDialog create = builder.create();
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        final ScreenFlashNotificationColorDialogFragment
                                screenFlashNotificationColorDialogFragment =
                                        ScreenFlashNotificationColorDialogFragment.this;
                        AlertDialog alertDialog = create;
                        screenFlashNotificationColorDialogFragment.getClass();
                        alertDialog
                                .getButton(-2)
                                .setOnClickListener(
                                        new View
                                                .OnClickListener() { // from class:
                                                                     // com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda3
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                ScreenFlashNotificationColorDialogFragment.this
                                                        .showColor();
                                            }
                                        });
                    }
                });
        return create;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        synchronized (this) {
            try {
                Timer timer = this.mTimer;
                if (timer != null) {
                    timer.cancel();
                }
                if (getContext() != null) {
                    getContext()
                            .sendBroadcastAsUser(
                                    new Intent(
                                            "com.android.internal.intent.action.FLASH_NOTIFICATION_STOP_PREVIEW"),
                                    UserHandle.SYSTEM);
                    this.mIsPreview = Boolean.FALSE;
                }
            } finally {
            }
        }
    }

    public final void showColor() {
        int i;
        synchronized (this) {
            try {
                Timer timer = this.mTimer;
                if (timer != null) {
                    timer.cancel();
                }
                this.mTimer = new Timer();
                if (this.mIsPreview.booleanValue()) {
                    final int i2 = 1;
                    this.mTimer.schedule(
                            new TimerTask(this) { // from class:
                                // com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment.1
                                public final /* synthetic */
                                ScreenFlashNotificationColorDialogFragment this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // java.util.TimerTask, java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            synchronized (this) {
                                                ScreenFlashNotificationColorDialogFragment
                                                        screenFlashNotificationColorDialogFragment =
                                                                this.this$0;
                                                if (screenFlashNotificationColorDialogFragment
                                                                .getContext()
                                                        != null) {
                                                    screenFlashNotificationColorDialogFragment
                                                                    .mIsPreview =
                                                            Boolean.TRUE;
                                                    Intent intent =
                                                            new Intent(
                                                                    "com.android.internal.intent.action.FLASH_NOTIFICATION_START_PREVIEW");
                                                    intent.putExtra(
                                                            "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_TYPE",
                                                            1);
                                                    intent.putExtra(
                                                            "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_COLOR",
                                                            screenFlashNotificationColorDialogFragment
                                                                    .mCurrentColor);
                                                    screenFlashNotificationColorDialogFragment
                                                            .getContext()
                                                            .sendBroadcastAsUser(
                                                                    intent, UserHandle.SYSTEM);
                                                }
                                            }
                                            return;
                                        default:
                                            synchronized (this) {
                                                ScreenFlashNotificationColorDialogFragment
                                                        screenFlashNotificationColorDialogFragment2 =
                                                                this.this$0;
                                                if (screenFlashNotificationColorDialogFragment2
                                                                .getContext()
                                                        != null) {
                                                    screenFlashNotificationColorDialogFragment2
                                                            .getContext()
                                                            .sendBroadcastAsUser(
                                                                    new Intent(
                                                                            "com.android.internal.intent.action.FLASH_NOTIFICATION_STOP_PREVIEW"),
                                                                    UserHandle.SYSTEM);
                                                    screenFlashNotificationColorDialogFragment2
                                                                    .mIsPreview =
                                                            Boolean.FALSE;
                                                }
                                            }
                                            return;
                                    }
                                }
                            },
                            0L);
                    i = IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
                } else {
                    i = 0;
                }
                final int i3 = 0;
                this.mTimer.schedule(
                        new TimerTask(this) { // from class:
                            // com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment.1
                            public final /* synthetic */ ScreenFlashNotificationColorDialogFragment
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // java.util.TimerTask, java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        synchronized (this) {
                                            ScreenFlashNotificationColorDialogFragment
                                                    screenFlashNotificationColorDialogFragment =
                                                            this.this$0;
                                            if (screenFlashNotificationColorDialogFragment
                                                            .getContext()
                                                    != null) {
                                                screenFlashNotificationColorDialogFragment
                                                                .mIsPreview =
                                                        Boolean.TRUE;
                                                Intent intent =
                                                        new Intent(
                                                                "com.android.internal.intent.action.FLASH_NOTIFICATION_START_PREVIEW");
                                                intent.putExtra(
                                                        "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_TYPE",
                                                        1);
                                                intent.putExtra(
                                                        "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_COLOR",
                                                        screenFlashNotificationColorDialogFragment
                                                                .mCurrentColor);
                                                screenFlashNotificationColorDialogFragment
                                                        .getContext()
                                                        .sendBroadcastAsUser(
                                                                intent, UserHandle.SYSTEM);
                                            }
                                        }
                                        return;
                                    default:
                                        synchronized (this) {
                                            ScreenFlashNotificationColorDialogFragment
                                                    screenFlashNotificationColorDialogFragment2 =
                                                            this.this$0;
                                            if (screenFlashNotificationColorDialogFragment2
                                                            .getContext()
                                                    != null) {
                                                screenFlashNotificationColorDialogFragment2
                                                        .getContext()
                                                        .sendBroadcastAsUser(
                                                                new Intent(
                                                                        "com.android.internal.intent.action.FLASH_NOTIFICATION_STOP_PREVIEW"),
                                                                UserHandle.SYSTEM);
                                                screenFlashNotificationColorDialogFragment2
                                                                .mIsPreview =
                                                        Boolean.FALSE;
                                            }
                                        }
                                        return;
                                }
                            }
                        },
                        i);
                final int i4 = 1;
                this.mTimer.schedule(
                        new TimerTask(this) { // from class:
                            // com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment.1
                            public final /* synthetic */ ScreenFlashNotificationColorDialogFragment
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // java.util.TimerTask, java.lang.Runnable
                            public final void run() {
                                switch (i4) {
                                    case 0:
                                        synchronized (this) {
                                            ScreenFlashNotificationColorDialogFragment
                                                    screenFlashNotificationColorDialogFragment =
                                                            this.this$0;
                                            if (screenFlashNotificationColorDialogFragment
                                                            .getContext()
                                                    != null) {
                                                screenFlashNotificationColorDialogFragment
                                                                .mIsPreview =
                                                        Boolean.TRUE;
                                                Intent intent =
                                                        new Intent(
                                                                "com.android.internal.intent.action.FLASH_NOTIFICATION_START_PREVIEW");
                                                intent.putExtra(
                                                        "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_TYPE",
                                                        1);
                                                intent.putExtra(
                                                        "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_COLOR",
                                                        screenFlashNotificationColorDialogFragment
                                                                .mCurrentColor);
                                                screenFlashNotificationColorDialogFragment
                                                        .getContext()
                                                        .sendBroadcastAsUser(
                                                                intent, UserHandle.SYSTEM);
                                            }
                                        }
                                        return;
                                    default:
                                        synchronized (this) {
                                            ScreenFlashNotificationColorDialogFragment
                                                    screenFlashNotificationColorDialogFragment2 =
                                                            this.this$0;
                                            if (screenFlashNotificationColorDialogFragment2
                                                            .getContext()
                                                    != null) {
                                                screenFlashNotificationColorDialogFragment2
                                                        .getContext()
                                                        .sendBroadcastAsUser(
                                                                new Intent(
                                                                        "com.android.internal.intent.action.FLASH_NOTIFICATION_STOP_PREVIEW"),
                                                                UserHandle.SYSTEM);
                                                screenFlashNotificationColorDialogFragment2
                                                                .mIsPreview =
                                                        Boolean.FALSE;
                                            }
                                        }
                                        return;
                                }
                            }
                        },
                        i + 5100);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
