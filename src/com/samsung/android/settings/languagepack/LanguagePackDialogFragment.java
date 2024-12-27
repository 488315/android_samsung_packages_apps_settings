package com.samsung.android.settings.languagepack;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslProgressBar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;
import com.samsung.android.settings.languagepack.utils.LanguagePackUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackDialogFragment extends DialogFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mDownloadDialog;
    public View mFooterDivider;
    public SeslProgressBar mIndeterminateBar;
    public boolean mIsMultiLocales;
    public List mLanguageInfo;
    public LanguagePackManager mLanguagePackManager;
    public View mPositiveButton;
    public SeslProgressBar mProgressBar;
    public LinearLayout mProgressBarContainer;
    public ProgressBar mProgressButton;
    public TextView mProgressLeftText;
    public TextView mProgressRightText;
    public TextView mTitleText;
    public boolean mIsDownloadClicked = false;
    public final AnonymousClass1 mServiceConnection = new AnonymousClass1();
    public final AnonymousClass4 mLangPackReceiver = new AnonymousClass4();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackDialogFragment$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i = LanguagePackDialogFragment.$r8$clinit;
            Log.d("LanguagePackDialogFragment", "onServiceConnected() : " + iBinder);
            LanguagePackDownloadService.mLanguagePackService =
                    (LanguagePackDownloadService.LanguagePackServiceBinder) iBinder;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            int i = LanguagePackDialogFragment.$r8$clinit;
            Log.d("LanguagePackDialogFragment", "onServiceDisconnected() : " + componentName);
            LanguagePackDownloadService.mLanguagePackService = null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackDialogFragment$2, reason: invalid class name */
    public final class AnonymousClass2 implements View.OnClickListener {
        public final /* synthetic */ String val$callingPackage;

        public AnonymousClass2(String str) {
            this.val$callingPackage = str;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            LanguagePackDialogFragment languagePackDialogFragment = LanguagePackDialogFragment.this;
            languagePackDialogFragment.mIsDownloadClicked = true;
            languagePackDialogFragment.mDownloadDialog.setCanceledOnTouchOutside(false);
            if (LanguagePackUtils.isNotActiveNetworksWarning(
                    LanguagePackDialogFragment.this.getContext())) {
                LanguagePackDialogFragment.this.getActivity().finish();
                return;
            }
            Stream stream = LanguagePackDialogFragment.this.mLanguageInfo.stream();
            final String str = this.val$callingPackage;
            stream.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.languagepack.LanguagePackDialogFragment$2$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            LanguagePackDialogFragment.AnonymousClass2 anonymousClass2 =
                                    LanguagePackDialogFragment.AnonymousClass2.this;
                            String str2 = str;
                            LanguageInfo languageInfo = (LanguageInfo) obj;
                            Context context = LanguagePackDialogFragment.this.getContext();
                            int i = LanguagePackDialogFragment.$r8$clinit;
                            if (languageInfo.isAllPackageInstalled(context)) {
                                Preference$$ExternalSyntheticOutline0.m(
                                        context,
                                        R.string
                                                .sec_offline_lang_pack_dialog_already_installed_toast,
                                        new Object[] {languageInfo.getDisplayName()},
                                        context,
                                        0);
                                return;
                            }
                            LanguagePackDownloadService.LanguagePackServiceBinder
                                    languagePackServiceBinder =
                                            LanguagePackDownloadService.mLanguagePackService;
                            if (languagePackServiceBinder != null) {
                                LanguagePackDownloadService.this.enqueueDownloadTask(languageInfo);
                                if (TextUtils.isEmpty(str2)) {
                                    return;
                                }
                                LanguagePackDownloadService.this.mResultBroadcastPackage = str2;
                            }
                        }
                    });
            LanguagePackDialogFragment languagePackDialogFragment2 =
                    LanguagePackDialogFragment.this;
            Context context = languagePackDialogFragment2.getContext();
            Iterator it = ((ArrayList) languagePackDialogFragment2.mLanguageInfo).iterator();
            while (it.hasNext()) {
                if (!((LanguageInfo) it.next()).isAllPackageInstalled(context)) {
                    if (!LanguagePackDialogFragment.this.mLanguageInfo.stream()
                            .anyMatch(
                                    new LanguagePackDialogFragment$$ExternalSyntheticLambda0(0))) {
                        LanguagePackDialogFragment.m1244$$Nest$mshowWaitingUI(
                                LanguagePackDialogFragment.this);
                    } else {
                        LanguagePackDialogFragment.m1243$$Nest$mshowDownloadingUI(
                                LanguagePackDialogFragment.this);
                    }
                    LoggingHelper.insertEventLogging(8140, 8151);
                    return;
                }
            }
            LanguagePackDialogFragment.this.getActivity().finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackDialogFragment$4, reason: invalid class name */
    public final class AnonymousClass4 extends BroadcastReceiver {
        public AnonymousClass4() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            final String stringExtra = intent.getStringExtra("extra_lang_code");
            int i = LanguagePackDialogFragment.$r8$clinit;
            Log.i(
                    "LanguagePackDialogFragment",
                    "onReceive() ignored. action : " + action + ", langCode : " + stringExtra);
            LanguagePackDialogFragment languagePackDialogFragment = LanguagePackDialogFragment.this;
            if (!languagePackDialogFragment.mIsDownloadClicked) {
                Log.i("LanguagePackDialogFragment", "onReceive() Download btn not clicked");
                return;
            }
            if (!languagePackDialogFragment.mLanguageInfo.stream()
                    .anyMatch(
                            new LanguagePackDialogFragment$$ExternalSyntheticLambda1(
                                    stringExtra, 1))) {
                Log.i("LanguagePackDialogFragment", "onReceive() ignored");
                LanguagePackDialogFragment languagePackDialogFragment2 =
                        LanguagePackDialogFragment.this;
                if (languagePackDialogFragment2.mProgressBarContainer.getVisibility() == 0
                        || languagePackDialogFragment2.mIndeterminateBar.getVisibility() == 0) {
                    LanguagePackDialogFragment.m1244$$Nest$mshowWaitingUI(
                            LanguagePackDialogFragment.this);
                    return;
                }
                return;
            }
            LanguagePackDialogFragment languagePackDialogFragment3 =
                    LanguagePackDialogFragment.this;
            if (languagePackDialogFragment3.mProgressBarContainer.getVisibility() != 0
                    && languagePackDialogFragment3.mIndeterminateBar.getVisibility() != 0) {
                LanguagePackDialogFragment.m1243$$Nest$mshowDownloadingUI(
                        LanguagePackDialogFragment.this);
            }
            action.getClass();
            if (action.equals("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_RESULT")) {
                new Handler()
                        .post(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.languagepack.LanguagePackDialogFragment$4$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LanguagePackDialogFragment.AnonymousClass4 anonymousClass4 =
                                                LanguagePackDialogFragment.AnonymousClass4.this;
                                        String str = stringExtra;
                                        LanguagePackDialogFragment languagePackDialogFragment4 =
                                                LanguagePackDialogFragment.this;
                                        int i2 = LanguagePackDialogFragment.$r8$clinit;
                                        languagePackDialogFragment4.updateStatus$1(str);
                                    }
                                });
                return;
            }
            if (action.equals(
                    "com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_PROGRESS")) {
                long longExtra = intent.getLongExtra("extra_lang_progress", 0L);
                double longExtra2 = intent.getLongExtra("extra_total_size", 0L);
                String format =
                        String.format("%.1f", Float.valueOf((float) (longExtra2 / 1048576.0d)));
                double d = longExtra;
                String format2 = String.format("%.1f", Float.valueOf((float) (d / 1048576.0d)));
                int round = (int) Math.round((d / longExtra2) * 100.0d);
                LanguagePackDialogFragment.this.mProgressBar.setProgress(round);
                LanguagePackDialogFragment.this.mProgressLeftText.setText(
                        context.getString(
                                R.string.sec_offline_lang_pack_dialog_progress_bar_progress,
                                format2,
                                format));
                LanguagePackDialogFragment.this.mProgressRightText.setText(
                        context.getString(
                                R.string.sec_offline_lang_pack_dialog_progress_bar_percentage,
                                Integer.valueOf(round)));
            }
        }
    }

    /* renamed from: -$$Nest$mshowDownloadingUI, reason: not valid java name */
    public static void m1243$$Nest$mshowDownloadingUI(
            LanguagePackDialogFragment languagePackDialogFragment) {
        languagePackDialogFragment.getClass();
        Log.i("LanguagePackDialogFragment", "showDownloadingUI()");
        languagePackDialogFragment.mLanguageInfo.stream()
                .filter(new LanguagePackDialogFragment$$ExternalSyntheticLambda0(2))
                .findFirst()
                .ifPresent(
                        new LanguagePackDialogFragment$$ExternalSyntheticLambda3(
                                languagePackDialogFragment, 1));
        View view = languagePackDialogFragment.mFooterDivider;
        if (view != null) {
            view.setVisibility(8);
        }
        languagePackDialogFragment.mProgressBarContainer.setVisibility(0);
        ProgressBar progressBar = languagePackDialogFragment.mProgressButton;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
        languagePackDialogFragment.mPositiveButton.setVisibility(8);
    }

    /* renamed from: -$$Nest$mshowWaitingUI, reason: not valid java name */
    public static void m1244$$Nest$mshowWaitingUI(
            LanguagePackDialogFragment languagePackDialogFragment) {
        languagePackDialogFragment.getClass();
        Log.i("LanguagePackDialogFragment", "showWaitingUI()");
        LanguagePackDownloadService.LanguagePackServiceBinder languagePackServiceBinder =
                LanguagePackDownloadService.mLanguagePackService;
        if (languagePackServiceBinder != null) {
            LanguagePackDownloadService.DownloadTask downloadTask =
                    LanguagePackDownloadService.this.mCurrentTask;
            if (languagePackDialogFragment.mLanguageInfo.stream()
                    .noneMatch(
                            new LanguagePackDialogFragment$$ExternalSyntheticLambda1(
                                    downloadTask != null
                                            ? downloadTask.mLanguageInfo.mLanguageCode
                                            : ApnSettings.MVNO_NONE,
                                    0))) {
                languagePackDialogFragment.mLanguageInfo.stream()
                        .filter(new LanguagePackDialogFragment$$ExternalSyntheticLambda0(1))
                        .findFirst()
                        .ifPresent(
                                new LanguagePackDialogFragment$$ExternalSyntheticLambda3(
                                        languagePackDialogFragment, 0));
            }
        }
        View view = languagePackDialogFragment.mFooterDivider;
        if (view != null) {
            view.setVisibility(0);
        }
        languagePackDialogFragment.mProgressBarContainer.setVisibility(8);
        languagePackDialogFragment.mPositiveButton.setVisibility(8);
        if (languagePackDialogFragment.mProgressButton == null) {
            ProgressBar progressBar =
                    new ProgressBar(
                            languagePackDialogFragment.getContext(),
                            null,
                            android.R.attr.progressBarStyle);
            languagePackDialogFragment.mProgressButton = progressBar;
            progressBar.setIndeterminate(true);
            languagePackDialogFragment.mProgressButton.setVisibility(8);
            ((LinearLayout) languagePackDialogFragment.mPositiveButton.getParent())
                    .addView(languagePackDialogFragment.mProgressButton);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams)
                            languagePackDialogFragment.mProgressButton.getLayoutParams();
            layoutParams.width = languagePackDialogFragment.mPositiveButton.getWidth();
            layoutParams.height = languagePackDialogFragment.mPositiveButton.getHeight();
            languagePackDialogFragment.mProgressButton.setLayoutParams(layoutParams);
        }
        languagePackDialogFragment.mProgressButton.setVisibility(0);
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        getActivity().finish();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext()
                .startService(
                        new Intent(getContext(), (Class<?>) LanguagePackDownloadService.class));
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        super.onCreateDialog(bundle);
        Bundle arguments = getArguments();
        String string = arguments.getString("function");
        String string2 = arguments.getString("lang_code");
        String string3 = arguments.getString("calling_package");
        String replaceAll = string2.replaceAll(" ", ApnSettings.MVNO_NONE);
        String[] split = replaceAll.split(",");
        if (split.length > 2) {
            Log.d(
                    "LanguagePackDialogFragment",
                    "language code count exceeded. Count = " + split.length);
            getActivity().finish();
            return new AlertDialog.Builder(getActivity()).create();
        }
        this.mLanguageInfo = new ArrayList();
        LanguagePackManager languagePackManager = LanguagePackManager.getInstance(getContext());
        this.mLanguagePackManager = languagePackManager;
        try {
            languagePackManager.makeLanguageList();
            for (String str : split) {
                if (TextUtils.isEmpty(str)) {
                    Log.d(
                            "LanguagePackDialogFragment",
                            "Invalid language Code : ".concat(replaceAll));
                    getActivity().finish();
                    return new AlertDialog.Builder(getActivity()).create();
                }
                if (this.mLanguagePackManager.getLanguageInfo(str) != null) {
                    ((ArrayList) this.mLanguageInfo)
                            .add(this.mLanguagePackManager.getLanguageInfo(str));
                }
            }
            if (((ArrayList) this.mLanguageInfo).isEmpty()) {
                Log.d(
                        "LanguagePackDialogFragment",
                        "Download candidate is empty : ".concat(replaceAll));
                getActivity().finish();
                return new AlertDialog.Builder(getActivity()).create();
            }
            if (((ArrayList) this.mLanguageInfo).size() > 1) {
                this.mIsMultiLocales = true;
            }
            StringBuilder m =
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            "langCode : ",
                            replaceAll,
                            ", callingPackage : ",
                            string3,
                            ", multi : ");
            m.append(this.mIsMultiLocales);
            Log.d("LanguagePackDialogFragment", m.toString());
            View inflate =
                    getLayoutInflater()
                            .inflate(R.layout.sec_offline_download_dialog, (ViewGroup) null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(inflate);
            builder.setPositiveButton(
                    R.string.sec_offline_lang_pack_dialog_positive_btn_text,
                    (DialogInterface.OnClickListener) null);
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            this.mTitleText = (TextView) inflate.findViewById(R.id.sec_download_dialog_title);
            this.mProgressLeftText = (TextView) inflate.findViewById(R.id.left_text);
            this.mProgressRightText = (TextView) inflate.findViewById(R.id.right_text);
            this.mProgressBar =
                    (SeslProgressBar) inflate.findViewById(R.id.dialog_progress_seekbar);
            this.mProgressBarContainer =
                    (LinearLayout) inflate.findViewById(R.id.custom_progress_bar);
            this.mIndeterminateBar =
                    (SeslProgressBar) inflate.findViewById(R.id.indeterminate_progressBar);
            if (this.mIsMultiLocales) {
                this.mTitleText.setText(
                        String.format(
                                getContext()
                                        .getString(
                                                R.string
                                                        .sec_offline_lang_pack_dialog_header_text_multi),
                                string,
                                ((LanguageInfo) ((ArrayList) this.mLanguageInfo).get(0))
                                        .getDisplayName(),
                                ((LanguageInfo) ((ArrayList) this.mLanguageInfo).get(1))
                                        .getDisplayName()));
            } else {
                this.mTitleText.setText(
                        getContext()
                                .getString(
                                        R.string.sec_offline_lang_pack_dialog_header_text,
                                        string,
                                        ((LanguageInfo) ((ArrayList) this.mLanguageInfo).get(0))
                                                .getDisplayName()));
            }
            if (LanguagePackUtils.showMobileDataWarningMessage(getContext())) {
                this.mTitleText.setText(
                        this.mTitleText.getText()
                                + " "
                                + getContext()
                                        .getResources()
                                        .getString(
                                                R.string.sec_offline_lang_pack_download_warning));
            }
            AlertDialog create = builder.create();
            this.mDownloadDialog = create;
            create.setCanceledOnTouchOutside(true);
            this.mDownloadDialog.show();
            Button button = this.mDownloadDialog.getButton(-1);
            this.mPositiveButton = button;
            button.setOnClickListener(new AnonymousClass2(string3));
            this.mDownloadDialog
                    .getButton(-2)
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.languagepack.LanguagePackDialogFragment.3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    LanguagePackDialogFragment languagePackDialogFragment =
                                            LanguagePackDialogFragment.this;
                                    if (!languagePackDialogFragment.mIsDownloadClicked) {
                                        languagePackDialogFragment.getActivity().finish();
                                        return;
                                    }
                                    if (LanguagePackDownloadService.mLanguagePackService != null) {
                                        languagePackDialogFragment.mLanguageInfo.stream()
                                                .forEach(
                                                        new LanguagePackDialogFragment$3$$ExternalSyntheticLambda0());
                                    }
                                    LanguagePackDialogFragment.this.getActivity().finish();
                                }
                            });
            this.mFooterDivider = this.mDownloadDialog.findViewById(R.id.sem_divider1);
            return this.mDownloadDialog;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        getActivity().finish();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        getActivity().unbindService(this.mServiceConnection);
        LanguagePackDownloadService.mLanguagePackService = null;
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        AlertDialog alertDialog;
        super.onResume();
        getActivity()
                .bindService(
                        new Intent(getActivity(), (Class<?>) LanguagePackDownloadService.class),
                        this.mServiceConnection,
                        1);
        if (this.mIsDownloadClicked
                && (alertDialog = this.mDownloadDialog) != null
                && alertDialog.isShowing()) {
            Iterator it = ((ArrayList) this.mLanguageInfo).iterator();
            boolean z = false;
            while (it.hasNext()) {
                LanguagePackDownloadService.Status status = ((LanguageInfo) it.next()).mStatus;
                if (status == LanguagePackDownloadService.Status.STATUS_WAITING
                        || status == LanguagePackDownloadService.Status.STATUS_DOWNLOADING
                        || status == LanguagePackDownloadService.Status.STATUS_INSTALLING) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            this.mDownloadDialog.dismiss();
            getActivity().finish();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getActivity()
                .registerReceiver(
                        this.mLangPackReceiver,
                        ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                "com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_PROGRESS",
                                "com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_RESULT"),
                        2);
        if (this.mLanguageInfo == null) {
            getActivity().finish();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(this.mLangPackReceiver);
    }

    public final void updateStatus$1(String str) {
        LanguageInfo languageInfo = this.mLanguagePackManager.getLanguageInfo(str);
        if (languageInfo == null || getActivity() == null) {
            return;
        }
        Log.d(
                "LanguagePackDialogFragment",
                "updateStatus : "
                        + languageInfo.mStatus
                        + ", Language code = "
                        + languageInfo.mLanguageCode
                        + ", mIsMultiLocales : "
                        + this.mIsMultiLocales);
        int i =
                ((int)
                                        this.mLanguageInfo.stream()
                                                .filter(
                                                        new LanguagePackDialogFragment$$ExternalSyntheticLambda0(
                                                                3))
                                                .count())
                                == 0
                        ? 2
                        : 1;
        LanguagePackDownloadService.Status status =
                LanguagePackDownloadService.Status.STATUS_INSTALLING;
        LanguagePackDownloadService.Status status2 = languageInfo.mStatus;
        if (status == status2) {
            this.mProgressBarContainer.setVisibility(8);
            this.mIndeterminateBar.setVisibility(0);
            if (!this.mIsMultiLocales) {
                this.mTitleText.setText(
                        getContext()
                                .getString(
                                        R.string.sec_offline_lang_pack_dialog_install_text,
                                        languageInfo.getDisplayName()));
                return;
            }
            this.mTitleText.setText(
                    getContext()
                                    .getString(
                                            R.string.sec_offline_lang_pack_dialog_install_text,
                                            languageInfo.getDisplayName())
                            + String.format(
                                    "(%d/%d)",
                                    Integer.valueOf(i),
                                    Integer.valueOf(((ArrayList) this.mLanguageInfo).size())));
            return;
        }
        if (LanguagePackDownloadService.Status.STATUS_DOWNLOADING != status2) {
            if (!this.mIsMultiLocales || i == 2) {
                this.mDownloadDialog.dismiss();
                getActivity().finish();
                return;
            }
            return;
        }
        this.mProgressBarContainer.setVisibility(0);
        this.mProgressBar.setProgress(0);
        this.mIndeterminateBar.setVisibility(8);
        if (!this.mIsMultiLocales) {
            this.mTitleText.setText(
                    getContext()
                            .getString(
                                    R.string.sec_offline_lang_pack_dialog_download_text,
                                    languageInfo.getDisplayName()));
            return;
        }
        this.mTitleText.setText(
                getContext()
                                .getString(
                                        R.string.sec_offline_lang_pack_dialog_download_text,
                                        languageInfo.getDisplayName())
                        + String.format(
                                "(%d/%d)",
                                Integer.valueOf(i),
                                Integer.valueOf(((ArrayList) this.mLanguageInfo).size())));
    }
}
