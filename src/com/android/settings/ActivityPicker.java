package com.android.settings;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ActivityPicker extends AlertActivity
        implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public PickAdapter mAdapter;
    public Intent mBaseIntent;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconResizer {
        public final Canvas mCanvas;
        public final int mIconHeight;
        public final int mIconWidth;
        public final DisplayMetrics mMetrics;
        public final Rect mOldBounds = new Rect();

        public IconResizer(int i, int i2, DisplayMetrics displayMetrics) {
            Canvas canvas = new Canvas();
            this.mCanvas = canvas;
            canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
            this.mMetrics = displayMetrics;
            this.mIconWidth = i;
            this.mIconHeight = i2;
        }

        public final Drawable createIconThumbnail(Drawable drawable) {
            int i;
            int i2;
            int i3 = this.mIconWidth;
            int i4 = this.mIconHeight;
            if (drawable == null) {
                return new EmptyDrawable(i3, i4);
            }
            try {
                if (drawable instanceof PaintDrawable) {
                    PaintDrawable paintDrawable = (PaintDrawable) drawable;
                    paintDrawable.setIntrinsicWidth(i3);
                    paintDrawable.setIntrinsicHeight(i4);
                } else if (drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    if (bitmapDrawable.getBitmap().getDensity() == 0) {
                        bitmapDrawable.setTargetDensity(this.mMetrics);
                    }
                }
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                    return drawable;
                }
                if (i3 >= intrinsicWidth && i4 >= intrinsicHeight) {
                    if (intrinsicWidth >= i3 || intrinsicHeight >= i4) {
                        return drawable;
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
                    Canvas canvas = this.mCanvas;
                    canvas.setBitmap(createBitmap);
                    this.mOldBounds.set(drawable.getBounds());
                    int i5 = (i3 - intrinsicWidth) / 2;
                    int i6 = (i4 - intrinsicHeight) / 2;
                    drawable.setBounds(i5, i6, intrinsicWidth + i5, intrinsicHeight + i6);
                    drawable.draw(canvas);
                    drawable.setBounds(this.mOldBounds);
                    BitmapDrawable bitmapDrawable2 = new BitmapDrawable(createBitmap);
                    bitmapDrawable2.setTargetDensity(this.mMetrics);
                    canvas.setBitmap(null);
                    return bitmapDrawable2;
                }
                float f = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i2 = (int) (i3 / f);
                    i = i3;
                } else {
                    i = intrinsicHeight > intrinsicWidth ? (int) (i4 * f) : i3;
                    i2 = i4;
                }
                try {
                    Bitmap createBitmap2 =
                            Bitmap.createBitmap(
                                    i3,
                                    i4,
                                    drawable.getOpacity() != -1
                                            ? Bitmap.Config.ARGB_8888
                                            : Bitmap.Config.RGB_565);
                    Canvas canvas2 = this.mCanvas;
                    canvas2.setBitmap(createBitmap2);
                    this.mOldBounds.set(drawable.getBounds());
                    int i7 = (i3 - i) / 2;
                    int i8 = (i4 - i2) / 2;
                    drawable.setBounds(i7, i8, i7 + i, i8 + i2);
                    drawable.draw(canvas2);
                    drawable.setBounds(this.mOldBounds);
                    BitmapDrawable bitmapDrawable3 = new BitmapDrawable(createBitmap2);
                    bitmapDrawable3.setTargetDensity(this.mMetrics);
                    canvas2.setBitmap(null);
                    return bitmapDrawable3;
                } catch (Throwable unused) {
                    i3 = i;
                    i4 = i2;
                    return new EmptyDrawable(i3, i4);
                }
            } catch (Throwable unused2) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PickAdapter extends BaseAdapter {
        public final LayoutInflater mInflater;
        public final List mItems;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Item {
            public static IconResizer sResizer;
            public String className;
            public Bundle extras;
            public Drawable icon;
            public CharSequence label;
            public String packageName;

            public Item(Context context, CharSequence charSequence, Drawable drawable) {
                this.label = charSequence;
                if (sResizer == null) {
                    Resources resources = context.getResources();
                    int dimension = (int) resources.getDimension(R.dimen.app_icon_size);
                    sResizer = new IconResizer(dimension, dimension, resources.getDisplayMetrics());
                }
                this.icon = sResizer.createIconThumbnail(drawable);
            }
        }

        public PickAdapter(Context context, List list) {
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mItems = list;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.mItems.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return this.mItems.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.pick_item, viewGroup, false);
            }
            Item item = (Item) this.mItems.get(i);
            TextView textView = (TextView) view;
            textView.setText(item.label);
            textView.setCompoundDrawablesWithIntrinsicBounds(
                    item.icon, (Drawable) null, (Drawable) null, (Drawable) null);
            return view;
        }
    }

    public final Intent getIntentForPosition(int i) {
        String str;
        PickAdapter.Item item = (PickAdapter.Item) this.mAdapter.mItems.get(i);
        Intent intent = this.mBaseIntent;
        item.getClass();
        Intent intent2 = new Intent(intent);
        String str2 = item.packageName;
        if (str2 == null || (str = item.className) == null) {
            intent2.setAction("android.intent.action.CREATE_SHORTCUT");
            intent2.putExtra("android.intent.extra.shortcut.NAME", item.label);
        } else {
            intent2.setClassName(str2, str);
            Bundle bundle = item.extras;
            if (bundle != null) {
                intent2.putExtras(bundle);
            }
        }
        return intent2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List getItems() {
        ActivityInfo activityInfo;
        PackageManager packageManager = getPackageManager();
        ArrayList arrayList = new ArrayList();
        Intent intent = getIntent();
        ArrayList<String> stringArrayListExtra =
                intent.getStringArrayListExtra("android.intent.extra.shortcut.NAME");
        ArrayList parcelableArrayListExtra =
                intent.getParcelableArrayListExtra("android.intent.extra.shortcut.ICON_RESOURCE");
        if (stringArrayListExtra != null
                && parcelableArrayListExtra != null
                && stringArrayListExtra.size() == parcelableArrayListExtra.size()) {
            for (int i = 0; i < stringArrayListExtra.size(); i++) {
                String str = stringArrayListExtra.get(i);
                Drawable drawable = null;
                try {
                    Intent.ShortcutIconResource shortcutIconResource =
                            (Intent.ShortcutIconResource) parcelableArrayListExtra.get(i);
                    Resources resourcesForApplication =
                            packageManager.getResourcesForApplication(
                                    shortcutIconResource.packageName);
                    drawable =
                            resourcesForApplication.getDrawable(
                                    resourcesForApplication.getIdentifier(
                                            shortcutIconResource.resourceName, null, null),
                                    null);
                } catch (PackageManager.NameNotFoundException unused) {
                }
                arrayList.add(new PickAdapter.Item(this, str, drawable));
            }
        }
        Intent intent2 = this.mBaseIntent;
        if (intent2 != null) {
            PackageManager packageManager2 = getPackageManager();
            List<ResolveInfo> queryIntentActivities =
                    packageManager2.queryIntentActivities(intent2, 0);
            Collections.sort(
                    queryIntentActivities, new ResolveInfo.DisplayNameComparator(packageManager2));
            int size = queryIntentActivities.size();
            for (int i2 = 0; i2 < size; i2++) {
                ResolveInfo resolveInfo = queryIntentActivities.get(i2);
                PickAdapter.Item item = new PickAdapter.Item();
                CharSequence loadLabel = resolveInfo.loadLabel(packageManager2);
                item.label = loadLabel;
                if (loadLabel == null && (activityInfo = resolveInfo.activityInfo) != null) {
                    item.label = activityInfo.name;
                }
                if (PickAdapter.Item.sResizer == null) {
                    Resources resources = getResources();
                    int dimension = (int) resources.getDimension(R.dimen.app_icon_size);
                    PickAdapter.Item.sResizer =
                            new IconResizer(dimension, dimension, resources.getDisplayMetrics());
                }
                item.icon =
                        PickAdapter.Item.sResizer.createIconThumbnail(
                                resolveInfo.loadIcon(packageManager2));
                ActivityInfo activityInfo2 = resolveInfo.activityInfo;
                item.packageName = activityInfo2.applicationInfo.packageName;
                item.className = activityInfo2.name;
                arrayList.add(item);
            }
        }
        return arrayList;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        setResult(0);
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        setResult(-1, getIntentForPosition(i));
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        Intent intent = getIntent();
        Parcelable parcelableExtra = intent.getParcelableExtra("android.intent.extra.INTENT");
        if (parcelableExtra instanceof Intent) {
            Intent intent2 = (Intent) parcelableExtra;
            this.mBaseIntent = intent2;
            intent2.setFlags(intent2.getFlags() & (-196));
        } else {
            Intent intent3 = new Intent("android.intent.action.MAIN", (Uri) null);
            this.mBaseIntent = intent3;
            intent3.addCategory("android.intent.category.DEFAULT");
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mOnClickListener = this;
        alertParams.mOnCancelListener = this;
        if (intent.hasExtra("android.intent.extra.TITLE")) {
            alertParams.mTitle = intent.getStringExtra("android.intent.extra.TITLE");
        } else {
            alertParams.mTitle = getTitle();
        }
        PickAdapter pickAdapter = new PickAdapter(this, getItems());
        this.mAdapter = pickAdapter;
        alertParams.mAdapter = pickAdapter;
        setupAlert();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EmptyDrawable extends Drawable {
        public final int mHeight;
        public final int mWidth;

        public EmptyDrawable(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return this.mHeight;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return this.mWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getMinimumHeight() {
            return this.mHeight;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getMinimumWidth() {
            return this.mWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {}

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {}

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {}
    }
}
