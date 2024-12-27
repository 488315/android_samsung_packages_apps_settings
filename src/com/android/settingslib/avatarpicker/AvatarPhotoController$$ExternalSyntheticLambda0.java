package com.android.settingslib.avatarpicker;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import libcore.io.Streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AvatarPhotoController$$ExternalSyntheticLambda0
        implements Callable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AvatarPhotoController f$0;
    public final /* synthetic */ Uri f$1;

    public /* synthetic */ AvatarPhotoController$$ExternalSyntheticLambda0(
            AvatarPhotoController avatarPhotoController, Uri uri, int i) {
        this.$r8$classId = i;
        this.f$0 = avatarPhotoController;
        this.f$1 = uri;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        switch (this.$r8$classId) {
            case 0:
                Uri uri = this.f$1;
                AvatarPhotoController avatarPhotoController = this.f$0;
                ContentResolver contentResolver =
                        avatarPhotoController.mContextInjector.mContext.getContentResolver();
                try {
                    Streams.copy(
                            contentResolver.openInputStream(uri),
                            contentResolver.openOutputStream(
                                    avatarPhotoController.mPreCropPictureUri));
                    return avatarPhotoController.mPreCropPictureUri;
                } catch (IOException e) {
                    Log.w("AvatarPhotoController", "Failed to copy photo", e);
                    return null;
                }
            default:
                Uri uri2 = this.f$1;
                AvatarPhotoController avatarPhotoController2 = this.f$0;
                avatarPhotoController2.getClass();
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                int i = avatarPhotoController2.mPhotoSize;
                Bitmap createBitmap = Bitmap.createBitmap(i, i, config);
                Canvas canvas = new Canvas(createBitmap);
                AvatarPhotoController.ContextInjectorImpl contextInjectorImpl =
                        avatarPhotoController2.mContextInjector;
                InputStream openInputStream =
                        contextInjectorImpl.mContext.getContentResolver().openInputStream(uri2);
                try {
                    Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    if (decodeStream == null) {
                        Log.e("AvatarPhotoController", "Image data could not be decoded");
                        return null;
                    }
                    int i2 = -1;
                    try {
                        i2 =
                                new ExifInterface(
                                                contextInjectorImpl
                                                        .mContext
                                                        .getContentResolver()
                                                        .openInputStream(uri2))
                                        .getAttributeInt("Orientation", -1);
                    } catch (IOException e2) {
                        Log.e("AvatarPhotoController", "Error while getting rotation", e2);
                    }
                    int i3 = i2 != 3 ? i2 != 6 ? i2 != 8 ? 0 : 270 : 90 : 180;
                    int min = Math.min(decodeStream.getWidth(), decodeStream.getHeight());
                    int width = (decodeStream.getWidth() - min) / 2;
                    int height = (decodeStream.getHeight() - min) / 2;
                    Matrix matrix = new Matrix();
                    float f = i;
                    matrix.setRectToRect(
                            new RectF(width, height, width + min, height + min),
                            new RectF(0.0f, 0.0f, f, f),
                            Matrix.ScaleToFit.CENTER);
                    float f2 = f / 2.0f;
                    matrix.postRotate(i3, f2, f2);
                    canvas.drawBitmap(decodeStream, matrix, new Paint());
                    try {
                        FileOutputStream fileOutputStream =
                                new FileOutputStream(
                                        new File(
                                                avatarPhotoController2.mImagesDir,
                                                "CropEditUserPhoto.jpg"));
                        createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return createBitmap;
                    } catch (IOException e3) {
                        Log.e("AvatarPhotoController", "Cannot create temp file", e3);
                        return createBitmap;
                    }
                } catch (Throwable th) {
                    if (openInputStream != null) {
                        try {
                            openInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
        }
    }
}
