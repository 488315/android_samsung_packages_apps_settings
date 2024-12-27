package com.samsung.android.settings.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.internal.accessibility.dialog.AccessibilityServiceWarning;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityDialogActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityDialogActivity mContext;
    public BasePreferenceController mController;
    public Dialog mDialog;
    public String mPrefKey;
    public int mType = -1;
    public ComponentName mComponentName = null;
    public String mExclusiveTask = null;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda3] */
    public final Dialog createDialog(int i) {
        Dialog dialog = null;
        if (i == 0) {
            AccessibilityServiceInfo accessibilityServiceInfo =
                    SecAccessibilityUtils.getAccessibilityServiceInfo(
                            this.mContext, this.mComponentName);
            if (accessibilityServiceInfo != null) {
                final int i2 = 0;
                final int i3 = 1;
                dialog =
                        AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                                this.mContext,
                                accessibilityServiceInfo,
                                new View.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ AccessibilityDialogActivity f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i4 = i2;
                                        AccessibilityDialogActivity accessibilityDialogActivity =
                                                this.f$0;
                                        switch (i4) {
                                            case 0:
                                                Dialog dialog2 =
                                                        accessibilityDialogActivity.mDialog;
                                                if (dialog2 != null && dialog2.isShowing()) {
                                                    accessibilityDialogActivity.mDialog.dismiss();
                                                }
                                                Dialog createDialog =
                                                        accessibilityDialogActivity.createDialog(2);
                                                if (createDialog == null) {
                                                    BasePreferenceController
                                                            basePreferenceController =
                                                                    accessibilityDialogActivity
                                                                            .mController;
                                                    ControlValue.Builder builder =
                                                            new ControlValue.Builder(
                                                                    accessibilityDialogActivity
                                                                            .mPrefKey,
                                                                    basePreferenceController
                                                                            .getControlType());
                                                    builder.setValue(Boolean.TRUE);
                                                    builder.setControlId("dialogActivity");
                                                    basePreferenceController.setValue(
                                                            builder.build());
                                                    accessibilityDialogActivity.finish();
                                                    break;
                                                } else {
                                                    createDialog.show();
                                                    break;
                                                }
                                            default:
                                                Dialog dialog3 =
                                                        accessibilityDialogActivity.mDialog;
                                                if (dialog3 != null && dialog3.isShowing()) {
                                                    accessibilityDialogActivity.mDialog.dismiss();
                                                }
                                                accessibilityDialogActivity.finish();
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ AccessibilityDialogActivity f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i4 = i3;
                                        AccessibilityDialogActivity accessibilityDialogActivity =
                                                this.f$0;
                                        switch (i4) {
                                            case 0:
                                                Dialog dialog2 =
                                                        accessibilityDialogActivity.mDialog;
                                                if (dialog2 != null && dialog2.isShowing()) {
                                                    accessibilityDialogActivity.mDialog.dismiss();
                                                }
                                                Dialog createDialog =
                                                        accessibilityDialogActivity.createDialog(2);
                                                if (createDialog == null) {
                                                    BasePreferenceController
                                                            basePreferenceController =
                                                                    accessibilityDialogActivity
                                                                            .mController;
                                                    ControlValue.Builder builder =
                                                            new ControlValue.Builder(
                                                                    accessibilityDialogActivity
                                                                            .mPrefKey,
                                                                    basePreferenceController
                                                                            .getControlType());
                                                    builder.setValue(Boolean.TRUE);
                                                    builder.setControlId("dialogActivity");
                                                    basePreferenceController.setValue(
                                                            builder.build());
                                                    accessibilityDialogActivity.finish();
                                                    break;
                                                } else {
                                                    createDialog.show();
                                                    break;
                                                }
                                            default:
                                                Dialog dialog3 =
                                                        accessibilityDialogActivity.mDialog;
                                                if (dialog3 != null && dialog3.isShowing()) {
                                                    accessibilityDialogActivity.mDialog.dismiss();
                                                }
                                                accessibilityDialogActivity.finish();
                                                break;
                                        }
                                    }
                                },
                                new AccessibilityDialogActivity$$ExternalSyntheticLambda2());
            }
        } else if (i == 1) {
            AccessibilityServiceInfo accessibilityServiceInfo2 =
                    SecAccessibilityUtils.getAccessibilityServiceInfo(
                            this.mContext, this.mComponentName);
            if (accessibilityServiceInfo2 != null) {
                AccessibilityDialogActivity accessibilityDialogActivity = this.mContext;
                final int i4 = 0;
                DialogInterface.OnClickListener onClickListener =
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda3
                            public final /* synthetic */ AccessibilityDialogActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i5) {
                                int i6 = i4;
                                AccessibilityDialogActivity accessibilityDialogActivity2 = this.f$0;
                                switch (i6) {
                                    case 0:
                                        if (i5 == -1) {
                                            BasePreferenceController basePreferenceController =
                                                    accessibilityDialogActivity2.mController;
                                            ControlValue.Builder builder =
                                                    new ControlValue.Builder(
                                                            accessibilityDialogActivity2.mPrefKey,
                                                            basePreferenceController
                                                                    .getControlType());
                                            builder.setValue(Boolean.FALSE);
                                            builder.setControlId("dialogActivity");
                                            basePreferenceController.setValue(builder.build());
                                        } else {
                                            int i7 = AccessibilityDialogActivity.$r8$clinit;
                                        }
                                        accessibilityDialogActivity2.finish();
                                        break;
                                    case 1:
                                        BasePreferenceController basePreferenceController2 =
                                                accessibilityDialogActivity2.mController;
                                        ControlValue.Builder builder2 =
                                                new ControlValue.Builder(
                                                        accessibilityDialogActivity2.mPrefKey,
                                                        basePreferenceController2.getControlType());
                                        builder2.setValue(Boolean.TRUE);
                                        builder2.setControlId("dialogActivity");
                                        basePreferenceController2.setValue(builder2.build());
                                        accessibilityDialogActivity2.finish();
                                        break;
                                    default:
                                        int i8 = AccessibilityDialogActivity.$r8$clinit;
                                        accessibilityDialogActivity2.finish();
                                        break;
                                }
                            }
                        };
                Locale locale =
                        accessibilityDialogActivity
                                .getResources()
                                .getConfiguration()
                                .getLocales()
                                .get(0);
                dialog =
                        new AlertDialog.Builder(accessibilityDialogActivity)
                                .setMessage(
                                        accessibilityDialogActivity.getString(
                                                R.string.sec_disable_service_message,
                                                BidiFormatter.getInstance(locale)
                                                        .unicodeWrap(
                                                                accessibilityServiceInfo2
                                                                        .getResolveInfo()
                                                                        .loadLabel(
                                                                                accessibilityDialogActivity
                                                                                        .getPackageManager()))))
                                .setCancelable(true)
                                .setPositiveButton(R.string.accessibility_turn_off, onClickListener)
                                .setNegativeButton(R.string.common_cancel, onClickListener)
                                .create();
            }
        } else if (i == 2) {
            AccessibilityDialogActivity accessibilityDialogActivity2 = this.mContext;
            String str = this.mExclusiveTask;
            Objects.requireNonNull(str);
            final int i5 = 1;
            final int i6 = 2;
            dialog =
                    AccessibilityDialogUtils.createExclusiveDialog(
                            accessibilityDialogActivity2,
                            str,
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda3
                                public final /* synthetic */ AccessibilityDialogActivity f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(
                                        DialogInterface dialogInterface, int i52) {
                                    int i62 = i5;
                                    AccessibilityDialogActivity accessibilityDialogActivity22 =
                                            this.f$0;
                                    switch (i62) {
                                        case 0:
                                            if (i52 == -1) {
                                                BasePreferenceController basePreferenceController =
                                                        accessibilityDialogActivity22.mController;
                                                ControlValue.Builder builder =
                                                        new ControlValue.Builder(
                                                                accessibilityDialogActivity22
                                                                        .mPrefKey,
                                                                basePreferenceController
                                                                        .getControlType());
                                                builder.setValue(Boolean.FALSE);
                                                builder.setControlId("dialogActivity");
                                                basePreferenceController.setValue(builder.build());
                                            } else {
                                                int i7 = AccessibilityDialogActivity.$r8$clinit;
                                            }
                                            accessibilityDialogActivity22.finish();
                                            break;
                                        case 1:
                                            BasePreferenceController basePreferenceController2 =
                                                    accessibilityDialogActivity22.mController;
                                            ControlValue.Builder builder2 =
                                                    new ControlValue.Builder(
                                                            accessibilityDialogActivity22.mPrefKey,
                                                            basePreferenceController2
                                                                    .getControlType());
                                            builder2.setValue(Boolean.TRUE);
                                            builder2.setControlId("dialogActivity");
                                            basePreferenceController2.setValue(builder2.build());
                                            accessibilityDialogActivity22.finish();
                                            break;
                                        default:
                                            int i8 = AccessibilityDialogActivity.$r8$clinit;
                                            accessibilityDialogActivity22.finish();
                                            break;
                                    }
                                }
                            },
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda3
                                public final /* synthetic */ AccessibilityDialogActivity f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(
                                        DialogInterface dialogInterface, int i52) {
                                    int i62 = i6;
                                    AccessibilityDialogActivity accessibilityDialogActivity22 =
                                            this.f$0;
                                    switch (i62) {
                                        case 0:
                                            if (i52 == -1) {
                                                BasePreferenceController basePreferenceController =
                                                        accessibilityDialogActivity22.mController;
                                                ControlValue.Builder builder =
                                                        new ControlValue.Builder(
                                                                accessibilityDialogActivity22
                                                                        .mPrefKey,
                                                                basePreferenceController
                                                                        .getControlType());
                                                builder.setValue(Boolean.FALSE);
                                                builder.setControlId("dialogActivity");
                                                basePreferenceController.setValue(builder.build());
                                            } else {
                                                int i7 = AccessibilityDialogActivity.$r8$clinit;
                                            }
                                            accessibilityDialogActivity22.finish();
                                            break;
                                        case 1:
                                            BasePreferenceController basePreferenceController2 =
                                                    accessibilityDialogActivity22.mController;
                                            ControlValue.Builder builder2 =
                                                    new ControlValue.Builder(
                                                            accessibilityDialogActivity22.mPrefKey,
                                                            basePreferenceController2
                                                                    .getControlType());
                                            builder2.setValue(Boolean.TRUE);
                                            builder2.setControlId("dialogActivity");
                                            basePreferenceController2.setValue(builder2.build());
                                            accessibilityDialogActivity22.finish();
                                            break;
                                        default:
                                            int i8 = AccessibilityDialogActivity.$r8$clinit;
                                            accessibilityDialogActivity22.finish();
                                            break;
                                    }
                                }
                            });
        }
        if (dialog != null) {
            dialog.setOnCancelListener(
                    new DialogInterface
                            .OnCancelListener() { // from class:
                                                  // com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda6
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            AccessibilityDialogActivity accessibilityDialogActivity3 =
                                    AccessibilityDialogActivity.this;
                            int i7 = AccessibilityDialogActivity.$r8$clinit;
                            accessibilityDialogActivity3.finish();
                        }
                    });
        }
        return dialog;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String taskName;
        super.onCreate(bundle);
        this.mContext = this;
        this.mType = getIntent().getIntExtra("type", this.mType);
        String stringExtra = getIntent().getStringExtra(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
        if (stringExtra == null) {
            finish();
        } else {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(stringExtra);
            this.mComponentName = unflattenFromString;
            if (this.mType == 2) {
                taskName = getIntent().getStringExtra(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
            } else {
                Objects.requireNonNull(unflattenFromString);
                taskName =
                        ((ExclusiveTaskInfo)
                                        AccessibilityExclusiveUtil.INFO_SET.stream()
                                                .filter(
                                                        new AccessibilityExclusiveUtil$$ExternalSyntheticLambda0(
                                                                unflattenFromString))
                                                .findAny()
                                                .orElseThrow())
                                .getTaskName();
            }
            this.mExclusiveTask = taskName;
            Set set = AccessibilityExclusiveUtil.INFO_SET;
            if (TextUtils.isEmpty(taskName)) {
                throw new IllegalArgumentException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "wrong taskName entered : ", taskName));
            }
            Set set2 = AccessibilityExclusiveUtil.INFO_SET;
            String controllerName =
                    ((ExclusiveTaskInfo)
                                    set2.stream()
                                            .filter(
                                                    new AccessibilityExclusiveUtil$$ExternalSyntheticLambda0(
                                                            taskName, 0))
                                            .findAny()
                                            .orElseThrow())
                            .getControllerName();
            String str = this.mExclusiveTask;
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "wrong taskName entered : ", str));
            }
            String preferenceKey =
                    ((ExclusiveTaskInfo)
                                    set2.stream()
                                            .filter(
                                                    new AccessibilityExclusiveUtil$$ExternalSyntheticLambda0(
                                                            str, 1))
                                            .findAny()
                                            .orElseThrow())
                            .getPreferenceKey();
            this.mPrefKey = preferenceKey;
            this.mController =
                    SecAccessibilityUtils.getPreferenceController(
                            this.mContext, preferenceKey, controllerName);
        }
        Dialog createDialog = createDialog(this.mType);
        this.mDialog = createDialog;
        if (createDialog != null) {
            createDialog.show();
        } else {
            finish();
        }
    }
}
