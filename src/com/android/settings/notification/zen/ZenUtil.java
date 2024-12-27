package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.notification.app.AppNotificationTypeInfo;
import com.samsung.android.settings.notification.zen.SecZenModeBypassingAppsController;

import java.io.InputStream;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenUtil {
    public static AppNotificationTypeInfo getPhotoAndTitle(Context context, String str, long j) {
        AppNotificationTypeInfo appNotificationTypeInfo = new AppNotificationTypeInfo();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor query =
                context.getContentResolver()
                        .query(ContentUris.withAppendedId(uri, j), null, null, null, null);
        if (query == null) {
            return null;
        }
        try {
            if (query.moveToFirst()) {
                appNotificationTypeInfo.title =
                        query.getString(query.getColumnIndex("display_name"));
            } else {
                appNotificationTypeInfo.title =
                        context.getString(R.string.dnd_contact_picker_deleted_contact);
            }
            InputStream openContactPhotoInputStream =
                    ContactsContract.Contacts.openContactPhotoInputStream(
                            context.getContentResolver(), ContentUris.withAppendedId(uri, j));
            appNotificationTypeInfo.contactPhoto =
                    openContactPhotoInputStream != null
                            ? getRoundedBitmap(
                                    BitmapFactory.decodeStream(openContactPhotoInputStream))
                            : null;
            appNotificationTypeInfo.contactId = Long.valueOf(j);
            appNotificationTypeInfo.contactNumber = str;
        } catch (Exception unused) {
        } catch (Throwable th) {
            query.close();
            throw th;
        }
        query.close();
        return appNotificationTypeInfo;
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (height != width) {
            int min = Math.min(height, width);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, min, min);
        }
        Bitmap createBitmap =
                Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawCircle(
                bitmap.getWidth() / 2.0f,
                bitmap.getHeight() / 2.0f,
                (bitmap.getHeight() / 2.0f) - 2,
                paint);
        Paint paint2 = new Paint();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, (Rect) null, rect, paint2);
        return createBitmap;
    }

    public static boolean isContactDeleted(Context context, String str) {
        try {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    ContactsContract.RawContacts.CONTENT_URI,
                                    new String[] {"_id"},
                                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "contact_id = '", str, "' AND deleted = '0'")
                                            .toString(),
                                    null,
                                    null);
            if (query != null) {
                try {
                    if (!query.isClosed()) {
                        if (query.getCount() != 0) {
                            query.close();
                            return false;
                        }
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception unused) {
        }
        return true;
    }

    public static void setExceptionContact(Context context, List list) {
        try {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NotificationManager.class);
            NotificationManager.Policy notificationPolicy =
                    notificationManager.getNotificationPolicy();
            notificationManager.setNotificationPolicy(
                    new NotificationManager.Policy(
                            notificationPolicy.priorityCategories,
                            notificationPolicy.priorityCallSenders,
                            notificationPolicy.priorityMessageSenders,
                            notificationPolicy.suppressedVisualEffects,
                            notificationPolicy.state,
                            notificationPolicy.priorityConversationSenders,
                            Settings.Global.getInt(
                                    context.getContentResolver(),
                                    "zen_selected_exception_contacts_allowed",
                                    0),
                            list,
                            Settings.Global.getInt(
                                    context.getContentResolver(),
                                    SecZenModeBypassingAppsController
                                            .ZEN_MODE_BYPASSING_APPS_DB_KEY,
                                    0),
                            notificationPolicy.getAppBypassDndList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
