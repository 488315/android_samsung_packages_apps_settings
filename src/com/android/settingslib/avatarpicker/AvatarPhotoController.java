package com.android.settingslib.avatarpicker;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.android.settings.R;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AvatarPhotoController {
    public final AnonymousClass2 mAvatarUi;
    public final ContextInjectorImpl mContextInjector;
    public final Uri mCropPictureUri;
    public final File mImagesDir;
    public final int mPhotoSize;
    public final Uri mPreCropPictureUri;
    public final Uri mTakePictureUri;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.avatarpicker.AvatarPhotoController$1, reason: invalid class name */
    public final class AnonymousClass1 implements FutureCallback {
        public final /* synthetic */ boolean val$delayBeforeCrop;

        public AnonymousClass1(boolean z) {
            this.val$delayBeforeCrop = z;
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public final void onFailure(Throwable th) {
            Log.e("AvatarPhotoController", "Error performing copy-and-crop", th);
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public final void onSuccess(Object obj) {
            if (((Uri) obj) == null) {
                return;
            }
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.android.settingslib.avatarpicker.AvatarPhotoController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AvatarPhotoController avatarPhotoController =
                                    AvatarPhotoController.this;
                            AvatarPickerActivity avatarPickerActivity =
                                    (AvatarPickerActivity) avatarPhotoController.mAvatarUi.this$0;
                            if (avatarPickerActivity.isFinishing()
                                    || avatarPickerActivity.isDestroyed()) {
                                return;
                            }
                            avatarPhotoController.cropPhoto(
                                    avatarPhotoController.mPreCropPictureUri);
                        }
                    };
            if (this.val$delayBeforeCrop) {
                AvatarPhotoController.this
                        .mContextInjector
                        .mContext
                        .getMainThreadHandler()
                        .postDelayed(runnable, 150L);
            } else {
                runnable.run();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.avatarpicker.AvatarPhotoController$2, reason: invalid class name */
    public final class AnonymousClass2 implements FutureCallback {
        public final Object this$0;

        public /* synthetic */ AnonymousClass2(Object obj) {
            this.this$0 = obj;
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public void onFailure(Throwable th) {
            Log.e("AvatarPhotoController", "Error performing internal crop", th);
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public void onSuccess(Object obj) {
            if (((Bitmap) obj) != null) {
                AvatarPhotoController avatarPhotoController = (AvatarPhotoController) this.this$0;
                AnonymousClass2 anonymousClass2 = avatarPhotoController.mAvatarUi;
                Uri uri = avatarPhotoController.mCropPictureUri;
                AvatarPickerActivity avatarPickerActivity =
                        (AvatarPickerActivity) anonymousClass2.this$0;
                avatarPickerActivity.getClass();
                Intent intent = new Intent();
                intent.setData(uri);
                avatarPickerActivity.setResult(-1, intent);
                avatarPickerActivity.finish();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ContextInjectorImpl {
        public final Context mContext;
        public final String mFileAuthority;

        public ContextInjectorImpl(Context context, String str) {
            this.mContext = context;
            this.mFileAuthority = str;
        }

        public final Uri createTempImageUri(File file, String str, boolean z) {
            File file2 = new File(file, str);
            if (z) {
                file2.delete();
            }
            return FileProvider.getUriForFile(this.mContext, file2, this.mFileAuthority);
        }
    }

    public AvatarPhotoController(
            AnonymousClass2 anonymousClass2, ContextInjectorImpl contextInjectorImpl, boolean z) {
        this.mAvatarUi = anonymousClass2;
        this.mContextInjector = contextInjectorImpl;
        File file = new File(contextInjectorImpl.mContext.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        boolean z2 = !z;
        this.mPreCropPictureUri =
                contextInjectorImpl.createTempImageUri(file, "PreCropEditUserPhoto.jpg", z2);
        this.mCropPictureUri =
                contextInjectorImpl.createTempImageUri(file, "CropEditUserPhoto.jpg", z2);
        this.mTakePictureUri =
                contextInjectorImpl.createTempImageUri(file, "TakeEditUserPhoto.jpg", z2);
        this.mPhotoSize =
                ((AvatarPickerActivity) anonymousClass2.this$0)
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_avatar_picker_photo_popup_min_width);
    }

    public final void copyAndCropPhoto(Uri uri, boolean z) {
        ListenableFuture submit =
                ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                        .submit(
                                (Callable)
                                        new AvatarPhotoController$$ExternalSyntheticLambda0(
                                                this, uri, 0));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(z);
        submit.addListener(
                new Futures.CallbackListener(submit, anonymousClass1),
                this.mContextInjector.mContext.getMainExecutor());
    }

    public final void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        Uri uri2 = this.mCropPictureUri;
        intent.putExtra("output", uri2);
        intent.addFlags(3);
        intent.setClipData(ClipData.newRawUri("output", uri2));
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        int i = this.mPhotoSize;
        intent.putExtra("outputX", i);
        intent.putExtra("outputY", i);
        try {
            StrictMode.disableDeathOnFileUriExposure();
            AvatarPickerActivity avatarPickerActivity =
                    (AvatarPickerActivity) this.mAvatarUi.this$0;
            List<ResolveInfo> queryIntentActivities =
                    avatarPickerActivity.getPackageManager().queryIntentActivities(intent, 1048576);
            boolean z = false;
            if (queryIntentActivities.isEmpty()) {
                Log.w(
                        "AvatarPhotoController",
                        "No system package activity could be found for code 1003");
            } else {
                intent.setPackage(queryIntentActivities.get(0).activityInfo.packageName);
                avatarPickerActivity.startActivityForResult(intent, 1003);
                z = true;
            }
            if (z) {
                return;
            }
            StrictMode.enableDeathOnFileUriExposure();
            ListenableFuture submit =
                    ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                            .submit(
                                    (Callable)
                                            new AvatarPhotoController$$ExternalSyntheticLambda0(
                                                    this, uri, 1));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            submit.addListener(
                    new Futures.CallbackListener(submit, anonymousClass2),
                    this.mContextInjector.mContext.getMainExecutor());
        } finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }
}
