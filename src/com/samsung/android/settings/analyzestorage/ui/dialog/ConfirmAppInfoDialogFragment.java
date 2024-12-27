package com.samsung.android.settings.analyzestorage.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import com.android.settings.R;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.presenter.controllers.DialogViewModel;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/samsung/android/settings/analyzestorage/ui/dialog/ConfirmAppInfoDialogFragment;", "Lcom/samsung/android/settings/analyzestorage/ui/dialog/AbsDialog;", "<init>", "()V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class ConfirmAppInfoDialogFragment extends AbsDialog {
    public List appList = EmptyList.INSTANCE;
    public ConfirmAppInfoInterface confirmApp;

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final void clearDataFromViewModel() {
        super.clearDataFromViewModel();
        DialogViewModel viewModel = getViewModel();
        viewModel.getClass();
        viewModel.dialogDataMap.remove("key_confirm_app_interface");
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final AlertDialog createDialog$2() {
        String str;
        String str2;
        ConfirmAppInfoInterface confirmAppInfoInterface = this.confirmApp;
        int positiveButtonText = confirmAppInfoInterface != null ? confirmAppInfoInterface.getPositiveButtonText() : R.string.common_done;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        ConfirmAppInfoInterface confirmAppInfoInterface2 = this.confirmApp;
        if (confirmAppInfoInterface2 != null) {
            int size = this.appList.size();
            if (size == 1) {
                AppInfo appInfo = (AppInfo) CollectionsKt___CollectionsKt.first(this.appList);
                Resources resources = getResources();
                int singularText = confirmAppInfoInterface2.getSingularText();
                Context baseContext = getBaseContext();
                String packageName = ((CommonAppInfo) appInfo).getPackageName();
                try {
                    PackageManager packageManager = baseContext.getPackageManager();
                    str2 = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 128));
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("PackageCheckUtils", "getApplicationLabel() ] Package " + packageName + " is an unknown app that can not be found by PackageManager.");
                    str2 = ApnSettings.MVNO_NONE;
                }
                str = resources.getString(singularText, str2);
                Intrinsics.checkNotNull(str);
            } else {
                str = getResources().getQuantityString(confirmAppInfoInterface2.getPluralText(), size, Integer.valueOf(size));
                Intrinsics.checkNotNull(str);
            }
        } else {
            str = null;
        }
        builder.P.mMessage = str;
        final int i = 0;
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoDialogFragment$createDialog$1
            public final /* synthetic */ ConfirmAppInfoDialogFragment this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                switch (i) {
                    case 0:
                        ConfirmAppInfoDialogFragment confirmAppInfoDialogFragment = this.this$0;
                        UserInteractionDialog$Callback userInteractionDialog$Callback = confirmAppInfoDialogFragment.callback;
                        if (userInteractionDialog$Callback != null) {
                            userInteractionDialog$Callback.onOk();
                            confirmAppInfoDialogFragment.clearDataFromViewModel();
                            break;
                        }
                        break;
                    default:
                        this.this$0.cancel();
                        break;
                }
            }
        });
        final int i2 = 1;
        builder.setNegativeButton(R.string.as_dialog_cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoDialogFragment$createDialog$1
            public final /* synthetic */ ConfirmAppInfoDialogFragment this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i22) {
                switch (i2) {
                    case 0:
                        ConfirmAppInfoDialogFragment confirmAppInfoDialogFragment = this.this$0;
                        UserInteractionDialog$Callback userInteractionDialog$Callback = confirmAppInfoDialogFragment.callback;
                        if (userInteractionDialog$Callback != null) {
                            userInteractionDialog$Callback.onOk();
                            confirmAppInfoDialogFragment.clearDataFromViewModel();
                            break;
                        }
                        break;
                    default:
                        this.this$0.cancel();
                        break;
                }
            }
        });
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        List list = this.appList;
        outState.putSerializable("asAppsInfoList", list instanceof Serializable ? (Serializable) list : null);
        DialogViewModel viewModel = getViewModel();
        ConfirmAppInfoInterface confirmAppInfoInterface = this.confirmApp;
        viewModel.getClass();
        viewModel.dialogDataMap.put("key_confirm_app_interface", confirmAppInfoInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        Button button;
        super.onStart();
        AlertDialog alertDialog = this.baseDialog;
        if (alertDialog == null || (button = alertDialog.getButton(-1)) == null) {
            return;
        }
        button.setTextColor(getBaseContext().getColor(R.color.as_basic_dialog_positive_button_color_red));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.util.List] */
    @Override // com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog
    public final void restoreStateOnCreate(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
        Serializable serializable = savedInstanceState.getSerializable("asAppsInfoList");
        EmptyList emptyList = EmptyList.INSTANCE;
        if (!(serializable instanceof List)) {
            serializable = null;
        }
        ?? r4 = (List) serializable;
        if (r4 != 0) {
            emptyList = r4;
        }
        this.appList = emptyList;
        DialogViewModel viewModel = getViewModel();
        viewModel.getClass();
        Object obj = ((LinkedHashMap) viewModel.dialogDataMap).get("key_confirm_app_interface");
        this.confirmApp = (ConfirmAppInfoInterface) (obj != null ? obj : null);
    }
}
