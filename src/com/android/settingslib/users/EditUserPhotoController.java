package com.android.settingslib.users;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.internal.util.UserIcons;
import com.android.settings.R;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EditUserPhotoController {
    public final Activity mActivity;
    public final ActivityStarter mActivityStarter;
    public String mCachedDrawablePath;
    public final ListeningExecutorService mExecutorService;
    public final ImageView mImageView;
    public final File mImagesDir;
    public Bitmap mNewUserPhotoBitmap;
    public Drawable mNewUserPhotoDrawable;

    /* renamed from: -$$Nest$monPhotoProcessed, reason: not valid java name */
    public static void m1052$$Nest$monPhotoProcessed(
            final EditUserPhotoController editUserPhotoController, Bitmap bitmap) {
        if (bitmap == null) {
            editUserPhotoController.getClass();
            return;
        }
        editUserPhotoController.mNewUserPhotoBitmap = bitmap;
        ((MoreExecutors.ListeningDecorator) editUserPhotoController.mExecutorService)
                .submit(
                        new Runnable() { // from class:
                                         // com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                EditUserPhotoController editUserPhotoController2 =
                                        EditUserPhotoController.this;
                                editUserPhotoController2.mCachedDrawablePath =
                                        editUserPhotoController2.saveNewUserPhotoBitmap().getPath();
                            }
                        });
        CircleFramedDrawable circleFramedDrawable =
                new CircleFramedDrawable(
                        editUserPhotoController.mNewUserPhotoBitmap,
                        editUserPhotoController
                                .mImageView
                                .getContext()
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.user_photo_size_in_user_info_dialog));
        editUserPhotoController.mNewUserPhotoDrawable = circleFramedDrawable;
        editUserPhotoController.mImageView.setImageDrawable(circleFramedDrawable);
    }

    public EditUserPhotoController(
            Activity activity,
            ActivityStarter activityStarter,
            ImageView imageView,
            Bitmap bitmap,
            CircleFramedDrawable circleFramedDrawable,
            final boolean z) {
        this.mActivity = activity;
        this.mActivityStarter = activityStarter;
        File file = new File(activity.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mImageView = imageView;
        imageView.setOnClickListener(
                new View.OnClickListener(
                        z) { // from class:
                             // com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EditUserPhotoController editUserPhotoController =
                                EditUserPhotoController.this;
                        editUserPhotoController.getClass();
                        Intent intent = new Intent("com.android.avatarpicker.FULL_SCREEN_ACTIVITY");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setPackage(
                                editUserPhotoController
                                        .mImageView
                                        .getContext()
                                        .getApplicationContext()
                                        .getPackageName());
                        intent.putExtra("file_authority", "com.android.settings.files");
                        editUserPhotoController.mActivityStarter.startActivityForResult(intent);
                    }
                });
        this.mNewUserPhotoBitmap = bitmap;
        this.mNewUserPhotoDrawable = circleFramedDrawable;
        this.mExecutorService = ThreadUtils.getBackgroundExecutor();
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 1004) {
            boolean hasExtra = intent.hasExtra("default_icon_tint_color");
            ListeningExecutorService listeningExecutorService = this.mExecutorService;
            if (hasExtra) {
                final int intExtra = intent.getIntExtra("default_icon_tint_color", -1);
                ListenableFuture submit =
                        ((MoreExecutors.ListeningDecorator) listeningExecutorService)
                                .submit(
                                        new Callable() { // from class:
                                                         // com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2
                                            @Override // java.util.concurrent.Callable
                                            public final Object call() {
                                                Resources resources =
                                                        EditUserPhotoController.this.mActivity
                                                                .getResources();
                                                return UserIcons.convertToBitmapAtUserIconSize(
                                                        resources,
                                                        UserIcons.getDefaultUserIconInColor(
                                                                resources, intExtra));
                                            }
                                        });
                final int i3 = 0;
                FutureCallback futureCallback =
                        new FutureCallback(
                                this) { // from class:
                                        // com.android.settingslib.users.EditUserPhotoController.1
                            public final /* synthetic */ EditUserPhotoController this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.google.common.util.concurrent.FutureCallback
                            public final void onFailure(Throwable th) {
                                switch (i3) {
                                    case 0:
                                        Log.e(
                                                "EditUserPhotoController",
                                                "Error processing default icon",
                                                th);
                                        break;
                                }
                            }

                            @Override // com.google.common.util.concurrent.FutureCallback
                            public final void onSuccess(Object obj) {
                                switch (i3) {
                                    case 0:
                                        EditUserPhotoController.m1052$$Nest$monPhotoProcessed(
                                                this.this$0, (Bitmap) obj);
                                        break;
                                    default:
                                        EditUserPhotoController.m1052$$Nest$monPhotoProcessed(
                                                this.this$0, (Bitmap) obj);
                                        break;
                                }
                            }

                            private final void
                                    onFailure$com$android$settingslib$users$EditUserPhotoController$2(
                                            Throwable th) {}
                        };
                submit.addListener(
                        new Futures.CallbackListener(submit, futureCallback),
                        this.mImageView.getContext().getMainExecutor());
                return;
            }
            if (intent.getData() != null) {
                final Uri data = intent.getData();
                ListenableFuture submit2 =
                        ((MoreExecutors.ListeningDecorator) listeningExecutorService)
                                .submit(
                                        new Callable() { // from class:
                                            // com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1
                                            /* JADX WARN: Multi-variable type inference failed */
                                            /* JADX WARN: Removed duplicated region for block: B:25:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                            /* JADX WARN: Type inference failed for: r3v2, types: [java.io.InputStream] */
                                            /* JADX WARN: Type inference failed for: r3v3 */
                                            /* JADX WARN: Type inference failed for: r3v8 */
                                            /* JADX WARN: Type inference failed for: r5v10, types: [java.io.InputStream] */
                                            /* JADX WARN: Type inference failed for: r5v2, types: [java.io.IOException, java.lang.Throwable] */
                                            /* JADX WARN: Type inference failed for: r5v6 */
                                            /* JADX WARN: Type inference failed for: r5v7, types: [java.io.InputStream] */
                                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0021 -> B:9:0x0038). Please report as a decompilation issue!!! */
                                            @Override // java.util.concurrent.Callable
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                            */
                                            public final java.lang.Object call() {
                                                /*
                                                    r5 = this;
                                                    android.net.Uri r0 = r2
                                                    com.android.settingslib.users.EditUserPhotoController r5 = com.android.settingslib.users.EditUserPhotoController.this
                                                    r5.getClass()
                                                    java.lang.String r1 = "Cannot close image stream"
                                                    java.lang.String r2 = "EditUserPhotoController"
                                                    r3 = 0
                                                    android.app.Activity r5 = r5.mActivity     // Catch: java.lang.Throwable -> L2a java.io.FileNotFoundException -> L2c
                                                    android.content.ContentResolver r5 = r5.getContentResolver()     // Catch: java.lang.Throwable -> L2a java.io.FileNotFoundException -> L2c
                                                    java.io.InputStream r5 = r5.openInputStream(r0)     // Catch: java.lang.Throwable -> L2a java.io.FileNotFoundException -> L2c
                                                    android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r5)     // Catch: java.lang.Throwable -> L25 java.io.FileNotFoundException -> L28
                                                    if (r5 == 0) goto L38
                                                    r5.close()     // Catch: java.io.IOException -> L20
                                                    goto L38
                                                L20:
                                                    r5 = move-exception
                                                    android.util.Log.w(r2, r1, r5)
                                                    goto L38
                                                L25:
                                                    r0 = move-exception
                                                    r3 = r5
                                                    goto L39
                                                L28:
                                                    r0 = move-exception
                                                    goto L2e
                                                L2a:
                                                    r0 = move-exception
                                                    goto L39
                                                L2c:
                                                    r0 = move-exception
                                                    r5 = r3
                                                L2e:
                                                    java.lang.String r4 = "Cannot find image file"
                                                    android.util.Log.w(r2, r4, r0)     // Catch: java.lang.Throwable -> L25
                                                    if (r5 == 0) goto L38
                                                    r5.close()     // Catch: java.io.IOException -> L20
                                                L38:
                                                    return r3
                                                L39:
                                                    if (r3 == 0) goto L43
                                                    r3.close()     // Catch: java.io.IOException -> L3f
                                                    goto L43
                                                L3f:
                                                    r5 = move-exception
                                                    android.util.Log.w(r2, r1, r5)
                                                L43:
                                                    throw r0
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1.call():java.lang.Object");
                                            }
                                        });
                final int i4 = 1;
                FutureCallback futureCallback2 =
                        new FutureCallback(
                                this) { // from class:
                                        // com.android.settingslib.users.EditUserPhotoController.1
                            public final /* synthetic */ EditUserPhotoController this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // com.google.common.util.concurrent.FutureCallback
                            public final void onFailure(Throwable th) {
                                switch (i4) {
                                    case 0:
                                        Log.e(
                                                "EditUserPhotoController",
                                                "Error processing default icon",
                                                th);
                                        break;
                                }
                            }

                            @Override // com.google.common.util.concurrent.FutureCallback
                            public final void onSuccess(Object obj) {
                                switch (i4) {
                                    case 0:
                                        EditUserPhotoController.m1052$$Nest$monPhotoProcessed(
                                                this.this$0, (Bitmap) obj);
                                        break;
                                    default:
                                        EditUserPhotoController.m1052$$Nest$monPhotoProcessed(
                                                this.this$0, (Bitmap) obj);
                                        break;
                                }
                            }

                            private final void
                                    onFailure$com$android$settingslib$users$EditUserPhotoController$2(
                                            Throwable th) {}
                        };
                submit2.addListener(
                        new Futures.CallbackListener(submit2, futureCallback2),
                        this.mImageView.getContext().getMainExecutor());
            }
        }
    }

    public final File saveNewUserPhotoBitmap() {
        if (this.mNewUserPhotoBitmap == null) {
            return null;
        }
        try {
            File file = new File(this.mImagesDir, "NewUserPhoto.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.mNewUserPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (IOException e) {
            Log.e("EditUserPhotoController", "Cannot create temp file", e);
            return null;
        }
    }
}
