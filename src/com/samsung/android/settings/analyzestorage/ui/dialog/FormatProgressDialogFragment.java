package com.samsung.android.settings.analyzestorage.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.analyzestorage.presenter.utils.AsyncTask;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.text.NumberFormat;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/FormatProgressDialogFragment;",
            "Lcom/samsung/android/settings/analyzestorage/ui/dialog/AbsDialog;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class FormatProgressDialogFragment extends AbsDialog {
    public TextView percent;
    public ProgressBar progressBar;

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final AlertDialog createDialog$2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        View inflate =
                LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.as_format_progress_layout, (ViewGroup) null);
        this.percent = (TextView) inflate.findViewById(R.id.file_operation_dialog_percent);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.cur_file_progressbar);
        String string = getString(R.string.as_sd_card);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        builder.setView(inflate);
        builder.P.mTitle = getString(R.string.as_storage_formatting, string);
        AlertDialog create = builder.create();
        new AsyncTask(new FormatProgressDialogFragment$initProgress$task$1(this))
                .execute(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.dialog.FormatProgressDialogFragment$initProgress$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                final FormatProgressDialogFragment formatProgressDialogFragment =
                                        FormatProgressDialogFragment.this;
                                formatProgressDialogFragment.getClass();
                                final int i = 20;
                                ThreadExecutor.runOnMainThread(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.analyzestorage.ui.dialog.FormatProgressDialogFragment$updateProgress$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ProgressBar progressBar =
                                                        FormatProgressDialogFragment.this
                                                                .progressBar;
                                                if (progressBar != null) {
                                                    progressBar.setProgress(i);
                                                }
                                                TextView textView =
                                                        FormatProgressDialogFragment.this.percent;
                                                if (textView == null) {
                                                    return;
                                                }
                                                textView.setText(
                                                        NumberFormat.getPercentInstance()
                                                                .format(i / 100));
                                            }
                                        });
                                StorageOperationManager storageOperationManager =
                                        StorageOperationManager.StorageOperationManagerHolder
                                                .INSTANCE;
                                Context baseContext =
                                        FormatProgressDialogFragment.this.getBaseContext();
                                storageOperationManager.getClass();
                                List list = StorageVolumeManager.sStorageMountInfoList;
                                new StorageVolumeManager$$ExternalSyntheticLambda0(
                                                baseContext, 1, 2, storageOperationManager)
                                        .run();
                                final FormatProgressDialogFragment formatProgressDialogFragment2 =
                                        FormatProgressDialogFragment.this;
                                formatProgressDialogFragment2.getClass();
                                final int i2 = 60;
                                ThreadExecutor.runOnMainThread(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.analyzestorage.ui.dialog.FormatProgressDialogFragment$updateProgress$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ProgressBar progressBar =
                                                        FormatProgressDialogFragment.this
                                                                .progressBar;
                                                if (progressBar != null) {
                                                    progressBar.setProgress(i2);
                                                }
                                                TextView textView =
                                                        FormatProgressDialogFragment.this.percent;
                                                if (textView == null) {
                                                    return;
                                                }
                                                textView.setText(
                                                        NumberFormat.getPercentInstance()
                                                                .format(i2 / 100));
                                            }
                                        });
                                try {
                                    Thread.sleep(200L);
                                    final FormatProgressDialogFragment
                                            formatProgressDialogFragment3 =
                                                    FormatProgressDialogFragment.this;
                                    formatProgressDialogFragment3.getClass();
                                    final int i3 = 100;
                                    ThreadExecutor.runOnMainThread(
                                            new Runnable() { // from class:
                                                             // com.samsung.android.settings.analyzestorage.ui.dialog.FormatProgressDialogFragment$updateProgress$1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    ProgressBar progressBar =
                                                            FormatProgressDialogFragment.this
                                                                    .progressBar;
                                                    if (progressBar != null) {
                                                        progressBar.setProgress(i3);
                                                    }
                                                    TextView textView =
                                                            FormatProgressDialogFragment.this
                                                                    .percent;
                                                    if (textView == null) {
                                                        return;
                                                    }
                                                    textView.setText(
                                                            NumberFormat.getPercentInstance()
                                                                    .format(i3 / 100));
                                                }
                                            });
                                    Thread.sleep(200L);
                                } catch (Exception unused) {
                                }
                                return Boolean.TRUE;
                            }
                        });
        return create;
    }
}
