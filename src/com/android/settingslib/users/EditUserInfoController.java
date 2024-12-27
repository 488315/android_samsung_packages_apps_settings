package com.android.settingslib.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.internal.util.UserIcons;
import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.CustomDialogHelper;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.File;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EditUserInfoController {
    public Dialog mEditUserInfoDialog;
    public EditUserPhotoController mEditUserPhotoController;
    public Toast mMaxToast;
    public CircleFramedDrawable mSavedDrawable;
    public Bitmap mSavedPhoto;
    public boolean mWaitingForActivityResult;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomLengthFilter extends InputFilter.LengthFilter {
        public final Activity mActivity;

        public CustomLengthFilter(FragmentActivity fragmentActivity) {
            super(32);
            this.mActivity = fragmentActivity;
        }

        @Override // android.text.InputFilter.LengthFilter, android.text.InputFilter
        public final CharSequence filter(
                CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            char charAt;
            CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
            if (filter != null) {
                Toast toast = EditUserInfoController.this.mMaxToast;
                if (toast != null
                        && (toast.getView() == null
                                || EditUserInfoController.this.mMaxToast.getView().isShown())) {
                    EditUserInfoController.this.mMaxToast.cancel();
                }
                EditUserInfoController editUserInfoController = EditUserInfoController.this;
                Activity activity = this.mActivity;
                editUserInfoController.mMaxToast =
                        Toast.makeText(
                                activity,
                                activity.getResources().getString(R.string.max_byte_error),
                                0);
                EditUserInfoController.this.mMaxToast.show();
                if (filter.length() > 0
                        && ((charAt = charSequence.charAt(filter.length() - 1)) == 9770
                                || charAt == 10013)) {
                    return ApnSettings.MVNO_NONE;
                }
            }
            return filter;
        }
    }

    public final void clear() {
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        if (editUserPhotoController != null) {
            new File(editUserPhotoController.mImagesDir, "NewUserPhoto.png").delete();
        }
        this.mEditUserInfoDialog = null;
        this.mSavedPhoto = null;
        this.mSavedDrawable = null;
    }

    public final Dialog createDialog(
            final FragmentActivity fragmentActivity,
            ActivityStarter activityStarter,
            final Drawable drawable,
            final String str,
            final BiConsumer biConsumer,
            final Runnable runnable) {
        View inflate =
                LayoutInflater.from(fragmentActivity)
                        .inflate(R.layout.edit_user_info_dialog_content, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R.id.user_name);
        editText.setText(str);
        if (editText.getText().toString().length() > 32) {
            editText.setText(editText.getText().toString().substring(0, 32));
        }
        editText.setFilters(new InputFilter[] {new CustomLengthFilter(fragmentActivity)});
        editText.setPrivateImeOptions("disableImage=true");
        editText.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.android.settingslib.users.EditUserInfoController.1
                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        if (ApnSettings.MVNO_NONE.equals(charSequence.toString().trim())) {
                            ((AlertDialog) EditUserInfoController.this.mEditUserInfoDialog)
                                    .getButton(-1)
                                    .setEnabled(false);
                        } else {
                            ((AlertDialog) EditUserInfoController.this.mEditUserInfoDialog)
                                    .getButton(-1)
                                    .setEnabled(true);
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {}

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                });
        ImageView imageView = (ImageView) inflate.findViewById(R.id.user_photo);
        Drawable defaultUserIcon =
                drawable != null
                        ? drawable
                        : UserIcons.getDefaultUserIcon(
                                fragmentActivity.getResources(), -10000, false);
        Bitmap bitmap = this.mSavedPhoto;
        if (bitmap != null) {
            CircleFramedDrawable circleFramedDrawable =
                    new CircleFramedDrawable(
                            bitmap,
                            fragmentActivity
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.user_photo_size_in_user_info_dialog));
            this.mSavedDrawable = circleFramedDrawable;
            defaultUserIcon = circleFramedDrawable;
        }
        imageView.setImageDrawable(defaultUserIcon);
        if (isChangePhotoRestrictedByBase(fragmentActivity)) {
            inflate.findViewById(R.id.add_a_photo_icon).setVisibility(8);
        } else {
            final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction =
                    getChangePhotoAdminRestriction(fragmentActivity);
            if (changePhotoAdminRestriction != null) {
                imageView.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                        fragmentActivity, changePhotoAdminRestriction);
                            }
                        });
            } else {
                this.mEditUserPhotoController =
                        createEditUserPhotoController(fragmentActivity, activityStarter, imageView);
            }
        }
        final CustomDialogHelper customDialogHelper = new CustomDialogHelper(fragmentActivity);
        customDialogHelper.setVisibility(0, false);
        customDialogHelper.setVisibility(1, true);
        customDialogHelper.setVisibility(2, false);
        customDialogHelper.setVisibility(9, true);
        customDialogHelper.setVisibility(8, false);
        customDialogHelper.setVisibility(7, true);
        customDialogHelper.setupDialogPaddings();
        customDialogHelper.setTitle(R.string.profile_info_settings_title);
        customDialogHelper.mCustomLayout.addView(inflate);
        customDialogHelper.setButton(
                6,
                R.string.okay,
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EditUserInfoController editUserInfoController = EditUserInfoController.this;
                        Drawable drawable2 = drawable;
                        EditText editText2 = editText;
                        String str2 = str;
                        BiConsumer biConsumer2 = biConsumer;
                        CustomDialogHelper customDialogHelper2 = customDialogHelper;
                        EditUserPhotoController editUserPhotoController =
                                editUserInfoController.mEditUserPhotoController;
                        Drawable drawable3 =
                                editUserPhotoController != null
                                        ? editUserPhotoController.mNewUserPhotoDrawable
                                        : null;
                        if (drawable3 != null) {
                            drawable2 = drawable3;
                        }
                        String trim = editText2.getText().toString().trim();
                        if (!trim.isEmpty()) {
                            str2 = trim;
                        }
                        editUserInfoController.clear();
                        if (biConsumer2 != null) {
                            biConsumer2.accept(str2, drawable2);
                        }
                        customDialogHelper2.mDialog.dismiss();
                    }
                });
        customDialogHelper.setButton(
                5,
                R.string.cancel,
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EditUserInfoController editUserInfoController = EditUserInfoController.this;
                        Runnable runnable2 = runnable;
                        CustomDialogHelper customDialogHelper2 = customDialogHelper;
                        editUserInfoController.clear();
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                        customDialogHelper2.mDialog.dismiss();
                    }
                });
        customDialogHelper.mDialog.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        EditUserInfoController editUserInfoController = EditUserInfoController.this;
                        Runnable runnable2 = runnable;
                        CustomDialogHelper customDialogHelper2 = customDialogHelper;
                        editUserInfoController.clear();
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                        customDialogHelper2.mDialog.dismiss();
                    }
                });
        Dialog dialog = customDialogHelper.mDialog;
        this.mEditUserInfoDialog = dialog;
        dialog.getWindow().setSoftInputMode(4);
        return this.mEditUserInfoDialog;
    }

    public EditUserPhotoController createEditUserPhotoController(
            Activity activity, ActivityStarter activityStarter, ImageView imageView) {
        return new EditUserPhotoController(
                activity, activityStarter, imageView, this.mSavedPhoto, this.mSavedDrawable, false);
    }

    public RestrictedLockUtils.EnforcedAdmin getChangePhotoAdminRestriction(Context context) {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                context, UserHandle.myUserId(), "no_set_user_icon");
    }

    public boolean isChangePhotoRestrictedByBase(Context context) {
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(
                context, UserHandle.myUserId(), "no_set_user_icon");
    }
}
