package com.android.settingslib.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.DialogTitle;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.util.UserIcons;
import com.android.settings.R;
import com.android.settings.users.UserSettings;
import com.android.settings.users.UserSettings$$ExternalSyntheticLambda10;
import com.android.settings.users.UserSettings$$ExternalSyntheticLambda15;
import com.android.settings.users.UserSettings$$ExternalSyntheticLambda4;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.File;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CreateUserDialogController {
    public Activity mActivity;
    public ActivityStarter mActivityStarter;
    public String mCachedDrawablePath;
    public Runnable mCancelCallback;
    public int mCurrentState;
    public CustomDialogHelper mCustomDialogHelper;
    public View mEditUserInfoView;
    public EditUserPhotoController mEditUserPhotoController;
    public View mGrantAdminView;
    public Boolean mIsAdmin;
    public Toast mMaxToast;
    public Drawable mNewUserIcon;
    public CircleFramedDrawable mSavedDrawable;
    public String mSavedName;
    public Bitmap mSavedPhoto;
    public UserSettings$$ExternalSyntheticLambda15 mSuccessCallback;
    public Dialog mUserCreationDialog;
    public String mUserName;
    public EditText mUserNameView;
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
                Toast toast = CreateUserDialogController.this.mMaxToast;
                if (toast != null
                        && (toast.getView() == null
                                || CreateUserDialogController.this.mMaxToast.getView().isShown())) {
                    CreateUserDialogController.this.mMaxToast.cancel();
                }
                CreateUserDialogController createUserDialogController =
                        CreateUserDialogController.this;
                Activity activity = this.mActivity;
                createUserDialogController.mMaxToast =
                        Toast.makeText(
                                activity,
                                activity.getResources().getString(R.string.max_byte_error),
                                0);
                CreateUserDialogController.this.mMaxToast.show();
                if (filter.length() > 0
                        && ((charAt = charSequence.charAt(filter.length() - 1)) == 9770
                                || charAt == 10013)) {
                    return ApnSettings.MVNO_NONE;
                }
            }
            return filter;
        }
    }

    public final Dialog createDialog(
            FragmentActivity fragmentActivity,
            UserSettings$$ExternalSyntheticLambda10 userSettings$$ExternalSyntheticLambda10,
            final boolean z,
            UserSettings$$ExternalSyntheticLambda15 userSettings$$ExternalSyntheticLambda15,
            UserSettings$$ExternalSyntheticLambda4 userSettings$$ExternalSyntheticLambda4) {
        this.mActivity = fragmentActivity;
        this.mCustomDialogHelper = new CustomDialogHelper(fragmentActivity);
        this.mSuccessCallback = userSettings$$ExternalSyntheticLambda15;
        this.mCancelCallback = userSettings$$ExternalSyntheticLambda4;
        this.mActivityStarter = userSettings$$ExternalSyntheticLambda10;
        View inflate = View.inflate(this.mActivity, R.layout.grant_admin_dialog_content, null);
        this.mGrantAdminView = inflate;
        this.mCustomDialogHelper.mCustomLayout.addView(inflate);
        RadioGroup radioGroup = (RadioGroup) this.mGrantAdminView.findViewById(R.id.choose_admin);
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() { // from class:
                                                     // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda5
                    @Override // android.widget.RadioGroup.OnCheckedChangeListener
                    public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                        CreateUserDialogController createUserDialogController =
                                CreateUserDialogController.this;
                        createUserDialogController.mCustomDialogHelper.mPositiveButton.setEnabled(
                                true);
                        createUserDialogController.mIsAdmin =
                                Boolean.valueOf(i == R.id.grant_admin_yes);
                    }
                });
        if (Boolean.TRUE.equals(this.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R.id.grant_admin_yes)).setChecked(true);
        } else if (Boolean.FALSE.equals(this.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R.id.grant_admin_no)).setChecked(true);
        }
        View inflate2 = View.inflate(this.mActivity, R.layout.edit_user_info_dialog_content, null);
        this.mEditUserInfoView = inflate2;
        this.mCustomDialogHelper.mCustomLayout.addView(inflate2);
        EditText editText = (EditText) this.mEditUserInfoView.findViewById(R.id.user_name);
        this.mUserNameView = editText;
        String str = this.mSavedName;
        if (str == null) {
            editText.setText(R.string.user_new_user_name);
        } else {
            editText.setText(str);
        }
        final EditText editText2 = (EditText) this.mEditUserInfoView.findViewById(R.id.user_name);
        if (editText2.getText().toString().length() > 32) {
            editText2.setText(editText2.getText().toString().substring(0, 32));
        }
        editText2.setFilters(
                new InputFilter[] {new CustomLengthFilter((FragmentActivity) this.mActivity)});
        editText2.setPrivateImeOptions("disableImage=true");
        editText2.setOnFocusChangeListener(
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z2) {
                        CreateUserDialogController createUserDialogController =
                                CreateUserDialogController.this;
                        EditText editText3 = editText2;
                        if (z2) {
                            createUserDialogController.getClass();
                            return;
                        }
                        InputMethodManager inputMethodManager =
                                (InputMethodManager)
                                        createUserDialogController.mActivity.getSystemService(
                                                "input_method");
                        if (inputMethodManager == null || !inputMethodManager.isActive()) {
                            return;
                        }
                        inputMethodManager.hideSoftInputFromWindow(editText3.getWindowToken(), 0);
                    }
                });
        editText2.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.android.settingslib.users.CreateUserDialogController.2
                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        if (ApnSettings.MVNO_NONE.equals(charSequence.toString().trim())) {
                            ((AlertDialog) CreateUserDialogController.this.mUserCreationDialog)
                                    .getButton(-1)
                                    .setEnabled(false);
                        } else {
                            ((AlertDialog) CreateUserDialogController.this.mUserCreationDialog)
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
        final ImageView imageView =
                (ImageView) this.mEditUserInfoView.findViewById(R.id.user_photo);
        Drawable defaultUserIcon =
                UserIcons.getDefaultUserIcon(this.mActivity.getResources(), -10000, false);
        if (this.mCachedDrawablePath != null) {
            ListenableFuture submit =
                    ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                            .submit(
                                    new Callable() { // from class:
                                                     // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda6
                                        @Override // java.util.concurrent.Callable
                                        public final Object call() {
                                            CreateUserDialogController createUserDialogController =
                                                    CreateUserDialogController.this;
                                            createUserDialogController.getClass();
                                            Bitmap decodeFile =
                                                    BitmapFactory.decodeFile(
                                                            new File(
                                                                            createUserDialogController
                                                                                    .mCachedDrawablePath)
                                                                    .getAbsolutePath());
                                            createUserDialogController.mSavedPhoto = decodeFile;
                                            CircleFramedDrawable circleFramedDrawable =
                                                    new CircleFramedDrawable(
                                                            decodeFile,
                                                            createUserDialogController
                                                                    .mActivity
                                                                    .getResources()
                                                                    .getDimensionPixelSize(
                                                                            R.dimen
                                                                                    .user_photo_size_in_user_info_dialog));
                                            createUserDialogController.mSavedDrawable =
                                                    circleFramedDrawable;
                                            return circleFramedDrawable;
                                        }
                                    });
            submit.addListener(
                    new Futures.CallbackListener(
                            submit,
                            new FutureCallback() { // from class:
                                                   // com.android.settingslib.users.CreateUserDialogController.1
                                @Override // com.google.common.util.concurrent.FutureCallback
                                public final void onSuccess(Object obj) {
                                    imageView.setImageDrawable((Drawable) obj);
                                }

                                @Override // com.google.common.util.concurrent.FutureCallback
                                public final void onFailure(Throwable th) {}
                            }),
                    this.mActivity.getMainExecutor());
        } else {
            imageView.setImageDrawable(defaultUserIcon);
        }
        if (isChangePhotoRestrictedByBase(this.mActivity)) {
            this.mEditUserInfoView.findViewById(R.id.add_a_photo_icon).setVisibility(8);
        } else {
            final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction =
                    getChangePhotoAdminRestriction(this.mActivity);
            if (changePhotoAdminRestriction != null) {
                imageView.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda4
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                CreateUserDialogController createUserDialogController =
                                        CreateUserDialogController.this;
                                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                        createUserDialogController.mActivity,
                                        changePhotoAdminRestriction);
                            }
                        });
            } else {
                this.mEditUserPhotoController = createEditUserPhotoController(imageView);
            }
        }
        final int i = 0;
        this.mCustomDialogHelper.setButton(
                6,
                R.string.next,
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
                    public final /* synthetic */ CreateUserDialogController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                CreateUserDialogController createUserDialogController = this.f$0;
                                boolean z2 = z;
                                int i2 = createUserDialogController.mCurrentState;
                                int i3 = i2 + 1;
                                createUserDialogController.mCurrentState = i3;
                                if (i3 == 1 && !z2) {
                                    createUserDialogController.mCurrentState = i2 + 2;
                                }
                                createUserDialogController.updateLayout();
                                break;
                            default:
                                CreateUserDialogController createUserDialogController2 = this.f$0;
                                boolean z3 = z;
                                int i4 = createUserDialogController2.mCurrentState;
                                int i5 = i4 - 1;
                                createUserDialogController2.mCurrentState = i5;
                                if (i5 == 1 && !z3) {
                                    createUserDialogController2.mCurrentState = i4 - 2;
                                }
                                createUserDialogController2.updateLayout();
                                break;
                        }
                    }
                });
        final int i2 = 1;
        this.mCustomDialogHelper.setButton(
                5,
                R.string.back,
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
                    public final /* synthetic */ CreateUserDialogController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                CreateUserDialogController createUserDialogController = this.f$0;
                                boolean z2 = z;
                                int i22 = createUserDialogController.mCurrentState;
                                int i3 = i22 + 1;
                                createUserDialogController.mCurrentState = i3;
                                if (i3 == 1 && !z2) {
                                    createUserDialogController.mCurrentState = i22 + 2;
                                }
                                createUserDialogController.updateLayout();
                                break;
                            default:
                                CreateUserDialogController createUserDialogController2 = this.f$0;
                                boolean z3 = z;
                                int i4 = createUserDialogController2.mCurrentState;
                                int i5 = i4 - 1;
                                createUserDialogController2.mCurrentState = i5;
                                if (i5 == 1 && !z3) {
                                    createUserDialogController2.mCurrentState = i4 - 2;
                                }
                                createUserDialogController2.updateLayout();
                                break;
                        }
                    }
                });
        this.mUserCreationDialog = this.mCustomDialogHelper.mDialog;
        updateLayout();
        this.mUserCreationDialog.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        CreateUserDialogController.this.finish();
                    }
                });
        this.mCustomDialogHelper.mDialogMessage.setPadding(10, 10, 10, 10);
        this.mUserCreationDialog.setCanceledOnTouchOutside(true);
        return this.mUserCreationDialog;
    }

    public EditUserPhotoController createEditUserPhotoController(ImageView imageView) {
        return new EditUserPhotoController(
                this.mActivity,
                this.mActivityStarter,
                imageView,
                this.mSavedPhoto,
                this.mSavedDrawable,
                true);
    }

    public final void finish() {
        if (this.mCurrentState == 3) {
            UserSettings$$ExternalSyntheticLambda15 userSettings$$ExternalSyntheticLambda15 =
                    this.mSuccessCallback;
            if (userSettings$$ExternalSyntheticLambda15 != null) {
                String str = this.mUserName;
                Drawable drawable = this.mNewUserIcon;
                boolean equals = Boolean.TRUE.equals(this.mIsAdmin);
                UserSettings userSettings = userSettings$$ExternalSyntheticLambda15.f$0;
                userSettings.mPendingUserIcon = drawable;
                userSettings.mPendingUserName = str;
                userSettings.mPendingUserIsAdmin = equals;
                userSettings.addUserNow(userSettings$$ExternalSyntheticLambda15.f$1);
            }
        } else {
            Runnable runnable = this.mCancelCallback;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.mUserCreationDialog = null;
        this.mCustomDialogHelper = null;
        this.mEditUserPhotoController = null;
        this.mSavedPhoto = null;
        this.mSavedName = null;
        this.mSavedDrawable = null;
        this.mIsAdmin = null;
        this.mActivity = null;
        this.mActivityStarter = null;
        this.mGrantAdminView = null;
        this.mEditUserInfoView = null;
        this.mUserNameView = null;
        this.mSuccessCallback = null;
        this.mCancelCallback = null;
        this.mCachedDrawablePath = null;
        this.mCurrentState = 0;
    }

    public RestrictedLockUtils.EnforcedAdmin getChangePhotoAdminRestriction(Context context) {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                context, UserHandle.myUserId(), "no_set_user_icon");
    }

    public boolean isChangePhotoRestrictedByBase(Context context) {
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(
                context, UserHandle.myUserId(), "no_set_user_icon");
    }

    public final void updateLayout() {
        Drawable drawable;
        int i = this.mCurrentState;
        if (i == -1) {
            this.mCustomDialogHelper.mDialog.dismiss();
            return;
        }
        if (i == 0) {
            this.mEditUserInfoView.setVisibility(8);
            this.mGrantAdminView.setVisibility(8);
            SharedPreferences preferences = this.mActivity.getPreferences(0);
            boolean z = preferences.getBoolean("key_add_user_long_message_displayed", false);
            int i2 = z ? R.string.user_add_user_message_short : R.string.user_add_user_message_long;
            if (!z) {
                preferences.edit().putBoolean("key_add_user_long_message_displayed", true).apply();
            }
            Drawable drawable2 = this.mActivity.getDrawable(R.drawable.ic_person_add);
            CustomDialogHelper customDialogHelper = this.mCustomDialogHelper;
            customDialogHelper.setVisibility(0, false);
            customDialogHelper.setVisibility(1, true);
            customDialogHelper.setVisibility(2, true);
            customDialogHelper.setVisibility(9, true);
            customDialogHelper.setVisibility(8, true);
            customDialogHelper.setVisibility(7, false);
            customDialogHelper.setupDialogPaddings();
            customDialogHelper.mDialogIcon.setImageDrawable(drawable2);
            customDialogHelper.mPositiveButton.setEnabled(true);
            customDialogHelper.setTitle(R.string.user_add_user_title);
            customDialogHelper.setMessage(i2);
            customDialogHelper.mNegativeButton.setText(R.string.cancel);
            customDialogHelper.mPositiveButton.setText(R.string.next);
            DialogTitle dialogTitle = this.mCustomDialogHelper.mDialogTitle;
            dialogTitle.requestFocus();
            dialogTitle.sendAccessibilityEvent(8);
            return;
        }
        if (i == 1) {
            this.mEditUserInfoView.setVisibility(8);
            this.mGrantAdminView.setVisibility(0);
            CustomDialogHelper customDialogHelper2 = this.mCustomDialogHelper;
            customDialogHelper2.setVisibility(0, false);
            customDialogHelper2.setVisibility(1, true);
            customDialogHelper2.setVisibility(2, true);
            customDialogHelper2.setVisibility(9, true);
            customDialogHelper2.setVisibility(8, true);
            customDialogHelper2.setVisibility(7, true);
            customDialogHelper2.setupDialogPaddings();
            customDialogHelper2.mDialogIcon.setImageDrawable(
                    this.mActivity.getDrawable(R.drawable.ic_admin_panel_settings));
            customDialogHelper2.setTitle(R.string.user_grant_admin_title);
            customDialogHelper2.setMessage(R.string.user_grant_admin_message);
            customDialogHelper2.mNegativeButton.setText(R.string.back);
            customDialogHelper2.mPositiveButton.setText(R.string.next);
            DialogTitle dialogTitle2 = this.mCustomDialogHelper.mDialogTitle;
            dialogTitle2.requestFocus();
            dialogTitle2.sendAccessibilityEvent(8);
            if (this.mIsAdmin == null) {
                this.mCustomDialogHelper.mPositiveButton.setEnabled(false);
                return;
            }
            return;
        }
        if (i == 2) {
            CustomDialogHelper customDialogHelper3 = this.mCustomDialogHelper;
            customDialogHelper3.setVisibility(0, false);
            customDialogHelper3.setVisibility(1, true);
            customDialogHelper3.setVisibility(2, false);
            customDialogHelper3.setVisibility(9, true);
            customDialogHelper3.setVisibility(8, false);
            customDialogHelper3.setVisibility(7, true);
            customDialogHelper3.setupDialogPaddings();
            customDialogHelper3.setTitle(R.string.user_info_settings_title);
            customDialogHelper3.mNegativeButton.setText(R.string.back);
            customDialogHelper3.mPositiveButton.setText(R.string.done);
            DialogTitle dialogTitle3 = this.mCustomDialogHelper.mDialogTitle;
            dialogTitle3.requestFocus();
            dialogTitle3.sendAccessibilityEvent(8);
            this.mEditUserInfoView.setVisibility(0);
            this.mGrantAdminView.setVisibility(8);
            return;
        }
        if (i != 3) {
            if (i < -1) {
                this.mCurrentState = -1;
                updateLayout();
                return;
            } else {
                this.mCurrentState = 3;
                updateLayout();
                return;
            }
        }
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        if (editUserPhotoController == null
                || (drawable = editUserPhotoController.mNewUserPhotoDrawable) == null) {
            drawable = this.mSavedDrawable;
        }
        this.mNewUserIcon = drawable;
        String trim = this.mUserNameView.getText().toString().trim();
        String string = this.mActivity.getString(R.string.user_new_user_name);
        if (trim.isEmpty()) {
            trim = string;
        }
        this.mUserName = trim;
        this.mCustomDialogHelper.mDialog.dismiss();
    }
}
