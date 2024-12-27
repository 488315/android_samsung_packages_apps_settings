package com.android.settingslib.avatarpicker;

import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.util.UserIcons;
import com.android.settings.R;

import com.google.android.setupdesign.util.ThemeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AvatarPickerActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AvatarAdapter mAdapter;
    public AvatarPhotoController mAvatarPhotoController;
    public Button mSaveButton;
    public boolean mWaitingForActivityResult;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AutoFitGridLayoutManager extends GridLayoutManager {
        public final int mColumnWidth;
        public final int mSpanCount;
        public int mTotalSpace;

        public AutoFitGridLayoutManager(AvatarPickerActivity avatarPickerActivity, int i) {
            super(i);
            this.mTotalSpace = 0;
            this.mColumnWidth =
                    avatarPickerActivity
                            .getResources()
                            .getDimensionPixelSize(R.dimen.sec_avatar_full_size_in_picker);
            this.mSpanCount = i;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager,
                  // androidx.recyclerview.widget.LinearLayoutManager,
                  // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutChildren(
                RecyclerView.Recycler recycler, RecyclerView.State state) {
            int paddingRight = (this.mWidth - getPaddingRight()) - getPaddingLeft();
            if (this.mTotalSpace < paddingRight) {
                this.mTotalSpace = paddingRight;
            }
            int max = Math.max(1, this.mTotalSpace / this.mColumnWidth);
            int i = this.mSpanCount;
            if (max > i) {
                max = i;
            }
            setSpanCount(max);
            super.onLayoutChildren(recycler, state);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AvatarAdapter extends RecyclerView.Adapter {
        public final List mImageDescriptions;
        public final List mImageDrawables;
        public final TypedArray mPreselectedImages;
        public int mSelectedPosition = -1;
        public final int[] mUserIconColors;

        public AvatarAdapter() {
            this.mPreselectedImages =
                    AvatarPickerActivity.this
                            .getResources()
                            .obtainTypedArray(R.array.avatar_images);
            this.mUserIconColors =
                    UserIcons.getUserIconColors(AvatarPickerActivity.this.getResources());
            ArrayList arrayList = new ArrayList();
            int i = 0;
            int i2 = 0;
            while (true) {
                int length = this.mPreselectedImages.length();
                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                if (i2 >= length) {
                    if (arrayList.isEmpty()) {
                        while (true) {
                            int[] iArr = this.mUserIconColors;
                            if (i >= iArr.length) {
                                break;
                            }
                            arrayList.add(
                                    UserIcons.getDefaultUserIconInColor(
                                            avatarPickerActivity.getResources(), iArr[i]));
                            i++;
                        }
                    }
                    this.mImageDrawables = arrayList;
                    this.mImageDescriptions =
                            this.mPreselectedImages.length() > 0
                                    ? Arrays.asList(
                                            AvatarPickerActivity.this
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array.avatar_image_descriptions))
                                    : null;
                    return;
                }
                Drawable drawable = this.mPreselectedImages.getDrawable(i2);
                if (!(drawable instanceof BitmapDrawable)) {
                    throw new IllegalStateException("Avatar drawables must be bitmaps");
                }
                RoundedBitmapDrawable21 roundedBitmapDrawable21 =
                        new RoundedBitmapDrawable21(
                                avatarPickerActivity.getResources(),
                                ((BitmapDrawable) drawable).getBitmap());
                roundedBitmapDrawable21.setCircular();
                arrayList.add(roundedBitmapDrawable21);
                i2++;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return ((ArrayList) this.mImageDrawables).size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            AvatarViewHolder avatarViewHolder = (AvatarViewHolder) viewHolder;
            if (i >= 0) {
                avatarViewHolder.mImageView.setSelected(i == this.mSelectedPosition);
                avatarViewHolder.mImageView.setImageDrawable(
                        (Drawable) ((ArrayList) this.mImageDrawables).get(i));
                List list = this.mImageDescriptions;
                if (list == null || i >= list.size()) {
                    avatarViewHolder.mImageView.setContentDescription(
                            AvatarPickerActivity.this.getString(
                                    R.string.default_user_icon_description));
                } else {
                    avatarViewHolder.mImageView.setContentDescription(
                            (String) this.mImageDescriptions.get(i));
                }
            }
            avatarViewHolder.mImageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settingslib.avatarpicker.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AvatarPickerActivity.AvatarAdapter avatarAdapter =
                                    AvatarPickerActivity.AvatarAdapter.this;
                            int i2 = i;
                            int i3 = avatarAdapter.mSelectedPosition;
                            AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                            if (i3 == i2) {
                                avatarAdapter.mSelectedPosition = -1;
                                avatarAdapter.notifyItemChanged(i2);
                                int i4 = AvatarPickerActivity.$r8$clinit;
                                avatarPickerActivity.saveButtonSetEnabled(false);
                                return;
                            }
                            avatarAdapter.mSelectedPosition = i2;
                            avatarAdapter.notifyItemChanged(i2);
                            if (i3 != -1) {
                                avatarAdapter.notifyItemChanged(i3);
                            } else {
                                int i5 = AvatarPickerActivity.$r8$clinit;
                                avatarPickerActivity.saveButtonSetEnabled(true);
                            }
                        }
                    });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AvatarViewHolder(
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.avatar_item, viewGroup, false));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AvatarViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public AvatarViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.avatar_image);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GridItemDecoration extends RecyclerView.ItemDecoration {
        public final boolean mIncludeEdge = true;
        public final boolean mRtl;
        public final int mSpacing;
        public final int mSpacingTop;

        public GridItemDecoration(Context context) {
            this.mSpacing =
                    context.getResources()
                            .getDimensionPixelSize(R.dimen.sec_avatar_item_side_padding);
            this.mSpacingTop =
                    context.getResources()
                            .getDimensionPixelSize(R.dimen.sec_avatar_item_top_bottom_padding);
            this.mRtl = context.getResources().getConfiguration().getLayoutDirection() == 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                int i = ((GridLayoutManager) layoutManager).mSpanCount;
                int childAdapterPosition = RecyclerView.getChildAdapterPosition(view) % i;
                boolean z = this.mIncludeEdge;
                int i2 = this.mSpacing;
                if (!z) {
                    rect.left = (childAdapterPosition * i2) / i;
                    rect.right = i2 - (((childAdapterPosition + 1) * i2) / i);
                    return;
                }
                int i3 = i2 / 2;
                rect.left = i3;
                rect.right = i3;
                int i4 = this.mSpacingTop;
                rect.top = i4;
                rect.bottom = i4;
                if (this.mRtl) {
                    rect.left = i3;
                    rect.right = i3;
                }
            }
        }
    }

    public final void cancel$1$1() {
        setResult(0);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mWaitingForActivityResult = false;
        AvatarPhotoController avatarPhotoController = this.mAvatarPhotoController;
        avatarPhotoController.getClass();
        if (i2 != -1) {
            return;
        }
        Uri data =
                (intent == null || intent.getData() == null)
                        ? avatarPhotoController.mTakePictureUri
                        : intent.getData();
        if (!"content".equals(data.getScheme())) {
            Log.e("AvatarPhotoController", "Invalid pictureUri scheme: " + data.getScheme());
            EventLog.writeEvent(1397638484, "172939189", -1, data.getPath());
            return;
        }
        switch (i) {
            case 1001:
                avatarPhotoController.copyAndCropPhoto(data, true);
                break;
            case 1002:
                if (!avatarPhotoController.mTakePictureUri.equals(data)) {
                    avatarPhotoController.copyAndCropPhoto(data, false);
                    break;
                } else {
                    avatarPhotoController.cropPhoto(data);
                    break;
                }
            case 1003:
                AvatarPickerActivity avatarPickerActivity =
                        (AvatarPickerActivity) avatarPhotoController.mAvatarUi.this$0;
                avatarPickerActivity.getClass();
                Intent intent2 = new Intent();
                intent2.setData(data);
                avatarPickerActivity.setResult(-1, intent2);
                avatarPickerActivity.finish();
                break;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        new Handler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AvatarPickerActivity avatarPickerActivity =
                                        AvatarPickerActivity.this;
                                int i = AvatarPickerActivity.$r8$clinit;
                                RecyclerView recyclerView =
                                        (RecyclerView)
                                                avatarPickerActivity.findViewById(R.id.avatar_grid);
                                recyclerView.removeItemDecorationAt(0);
                                recyclerView.setLayoutManager(
                                        new AvatarPickerActivity.AutoFitGridLayoutManager(
                                                avatarPickerActivity,
                                                avatarPickerActivity
                                                        .getResources()
                                                        .getInteger(
                                                                R.integer.avatar_picker_columns)));
                                recyclerView.addItemDecoration(
                                        new AvatarPickerActivity.GridItemDecoration(
                                                avatarPickerActivity));
                            }
                        },
                        100L);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        KeyguardManager keyguardManager;
        super.onCreate(bundle);
        setTheme(2132084351);
        ThemeHelper.trySetDynamicColor(this);
        setContentView(R.layout.sec_avatar_picker);
        Button button = (Button) findViewById(R.id.save_button);
        this.mSaveButton = button;
        if (button != null) {
            saveButtonSetEnabled(false);
            final int i = 2;
            this.mSaveButton.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                        public final /* synthetic */ AvatarPickerActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i2 = i;
                            AvatarPickerActivity avatarPickerActivity = this.f$0;
                            switch (i2) {
                                case 0:
                                    AvatarPhotoController avatarPhotoController =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController.getClass();
                                    Intent intent =
                                            new Intent(
                                                    "android.intent.action.GET_CONTENT",
                                                    (Uri) null);
                                    intent.setPackage("com.sec.android.gallery3d");
                                    intent.setType("image/*");
                                    ((AvatarPickerActivity) avatarPhotoController.mAvatarUi.this$0)
                                            .startActivityForResult(intent, 1001);
                                    return;
                                case 1:
                                    AvatarPhotoController avatarPhotoController2 =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController2.getClass();
                                    Intent intent2 =
                                            new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                    Uri uri = avatarPhotoController2.mTakePictureUri;
                                    intent2.putExtra("output", uri);
                                    intent2.addFlags(3);
                                    intent2.setClipData(ClipData.newRawUri("output", uri));
                                    ((AvatarPickerActivity) avatarPhotoController2.mAvatarUi.this$0)
                                            .startActivityForResult(intent2, 1002);
                                    return;
                                case 2:
                                    AvatarPickerActivity.AvatarAdapter avatarAdapter =
                                            avatarPickerActivity.mAdapter;
                                    int i3 = avatarAdapter.mSelectedPosition;
                                    int length = avatarAdapter.mPreselectedImages.length();
                                    AvatarPickerActivity avatarPickerActivity2 =
                                            AvatarPickerActivity.this;
                                    if (length <= 0) {
                                        int i4 = avatarAdapter.mUserIconColors[i3];
                                        avatarPickerActivity2.getClass();
                                        Intent intent3 = new Intent();
                                        intent3.putExtra("default_icon_tint_color", i4);
                                        avatarPickerActivity2.setResult(-1, intent3);
                                        avatarPickerActivity2.finish();
                                        return;
                                    }
                                    int resourceId =
                                            avatarAdapter.mPreselectedImages.getResourceId(i3, -1);
                                    if (resourceId == -1) {
                                        throw new IllegalStateException(
                                                "Preselected avatar images must be resources.");
                                    }
                                    Uri build =
                                            new Uri.Builder()
                                                    .scheme("android.resource")
                                                    .authority(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourcePackageName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceTypeName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceEntryName(
                                                                            resourceId))
                                                    .build();
                                    Intent intent4 = new Intent();
                                    intent4.setData(build);
                                    avatarPickerActivity2.setResult(-1, intent4);
                                    avatarPickerActivity2.finish();
                                    return;
                                default:
                                    int i5 = AvatarPickerActivity.$r8$clinit;
                                    avatarPickerActivity.cancel$1$1();
                                    return;
                            }
                        }
                    });
        }
        Button button2 = (Button) findViewById(R.id.cancel_button);
        if (button2 != null) {
            final int i2 = 3;
            button2.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                        public final /* synthetic */ AvatarPickerActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i2;
                            AvatarPickerActivity avatarPickerActivity = this.f$0;
                            switch (i22) {
                                case 0:
                                    AvatarPhotoController avatarPhotoController =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController.getClass();
                                    Intent intent =
                                            new Intent(
                                                    "android.intent.action.GET_CONTENT",
                                                    (Uri) null);
                                    intent.setPackage("com.sec.android.gallery3d");
                                    intent.setType("image/*");
                                    ((AvatarPickerActivity) avatarPhotoController.mAvatarUi.this$0)
                                            .startActivityForResult(intent, 1001);
                                    return;
                                case 1:
                                    AvatarPhotoController avatarPhotoController2 =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController2.getClass();
                                    Intent intent2 =
                                            new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                    Uri uri = avatarPhotoController2.mTakePictureUri;
                                    intent2.putExtra("output", uri);
                                    intent2.addFlags(3);
                                    intent2.setClipData(ClipData.newRawUri("output", uri));
                                    ((AvatarPickerActivity) avatarPhotoController2.mAvatarUi.this$0)
                                            .startActivityForResult(intent2, 1002);
                                    return;
                                case 2:
                                    AvatarPickerActivity.AvatarAdapter avatarAdapter =
                                            avatarPickerActivity.mAdapter;
                                    int i3 = avatarAdapter.mSelectedPosition;
                                    int length = avatarAdapter.mPreselectedImages.length();
                                    AvatarPickerActivity avatarPickerActivity2 =
                                            AvatarPickerActivity.this;
                                    if (length <= 0) {
                                        int i4 = avatarAdapter.mUserIconColors[i3];
                                        avatarPickerActivity2.getClass();
                                        Intent intent3 = new Intent();
                                        intent3.putExtra("default_icon_tint_color", i4);
                                        avatarPickerActivity2.setResult(-1, intent3);
                                        avatarPickerActivity2.finish();
                                        return;
                                    }
                                    int resourceId =
                                            avatarAdapter.mPreselectedImages.getResourceId(i3, -1);
                                    if (resourceId == -1) {
                                        throw new IllegalStateException(
                                                "Preselected avatar images must be resources.");
                                    }
                                    Uri build =
                                            new Uri.Builder()
                                                    .scheme("android.resource")
                                                    .authority(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourcePackageName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceTypeName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceEntryName(
                                                                            resourceId))
                                                    .build();
                                    Intent intent4 = new Intent();
                                    intent4.setData(build);
                                    avatarPickerActivity2.setResult(-1, intent4);
                                    avatarPickerActivity2.finish();
                                    return;
                                default:
                                    int i5 = AvatarPickerActivity.$r8$clinit;
                                    avatarPickerActivity.cancel$1$1();
                                    return;
                            }
                        }
                    });
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.avatar_grid);
        AvatarAdapter avatarAdapter = new AvatarAdapter();
        this.mAdapter = avatarAdapter;
        recyclerView.setAdapter(avatarAdapter);
        recyclerView.setLayoutManager(
                new AutoFitGridLayoutManager(
                        this, getResources().getInteger(R.integer.avatar_picker_columns)));
        recyclerView.addItemDecoration(new GridItemDecoration(this));
        recyclerView.setHasFixedSize(true);
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setTitle(R.string.avatar_picker_title);
        }
        boolean z =
                getPackageManager()
                                .queryIntentActivities(
                                        new Intent("android.media.action.IMAGE_CAPTURE"), 65536)
                                .size()
                        > 0;
        Intent intent = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
        intent.setPackage("com.sec.android.gallery3d");
        intent.setType("image/*");
        boolean z2 =
                (getPackageManager().queryIntentActivities(intent, 0).size() <= 0
                                || (keyguardManager =
                                                (KeyguardManager)
                                                        getSystemService(KeyguardManager.class))
                                        == null
                                || keyguardManager.isDeviceLocked())
                        ? false
                        : true;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.gallery_btn);
        if (linearLayout != null && z2) {
            linearLayout.setVisibility(0);
            final int i3 = 0;
            linearLayout.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                        public final /* synthetic */ AvatarPickerActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i3;
                            AvatarPickerActivity avatarPickerActivity = this.f$0;
                            switch (i22) {
                                case 0:
                                    AvatarPhotoController avatarPhotoController =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController.getClass();
                                    Intent intent2 =
                                            new Intent(
                                                    "android.intent.action.GET_CONTENT",
                                                    (Uri) null);
                                    intent2.setPackage("com.sec.android.gallery3d");
                                    intent2.setType("image/*");
                                    ((AvatarPickerActivity) avatarPhotoController.mAvatarUi.this$0)
                                            .startActivityForResult(intent2, 1001);
                                    return;
                                case 1:
                                    AvatarPhotoController avatarPhotoController2 =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController2.getClass();
                                    Intent intent22 =
                                            new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                    Uri uri = avatarPhotoController2.mTakePictureUri;
                                    intent22.putExtra("output", uri);
                                    intent22.addFlags(3);
                                    intent22.setClipData(ClipData.newRawUri("output", uri));
                                    ((AvatarPickerActivity) avatarPhotoController2.mAvatarUi.this$0)
                                            .startActivityForResult(intent22, 1002);
                                    return;
                                case 2:
                                    AvatarPickerActivity.AvatarAdapter avatarAdapter2 =
                                            avatarPickerActivity.mAdapter;
                                    int i32 = avatarAdapter2.mSelectedPosition;
                                    int length = avatarAdapter2.mPreselectedImages.length();
                                    AvatarPickerActivity avatarPickerActivity2 =
                                            AvatarPickerActivity.this;
                                    if (length <= 0) {
                                        int i4 = avatarAdapter2.mUserIconColors[i32];
                                        avatarPickerActivity2.getClass();
                                        Intent intent3 = new Intent();
                                        intent3.putExtra("default_icon_tint_color", i4);
                                        avatarPickerActivity2.setResult(-1, intent3);
                                        avatarPickerActivity2.finish();
                                        return;
                                    }
                                    int resourceId =
                                            avatarAdapter2.mPreselectedImages.getResourceId(
                                                    i32, -1);
                                    if (resourceId == -1) {
                                        throw new IllegalStateException(
                                                "Preselected avatar images must be resources.");
                                    }
                                    Uri build =
                                            new Uri.Builder()
                                                    .scheme("android.resource")
                                                    .authority(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourcePackageName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceTypeName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceEntryName(
                                                                            resourceId))
                                                    .build();
                                    Intent intent4 = new Intent();
                                    intent4.setData(build);
                                    avatarPickerActivity2.setResult(-1, intent4);
                                    avatarPickerActivity2.finish();
                                    return;
                                default:
                                    int i5 = AvatarPickerActivity.$r8$clinit;
                                    avatarPickerActivity.cancel$1$1();
                                    return;
                            }
                        }
                    });
        }
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.camera_btn);
        if (linearLayout2 != null && z) {
            linearLayout2.setVisibility(0);
            final int i4 = 1;
            linearLayout2.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                        public final /* synthetic */ AvatarPickerActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i4;
                            AvatarPickerActivity avatarPickerActivity = this.f$0;
                            switch (i22) {
                                case 0:
                                    AvatarPhotoController avatarPhotoController =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController.getClass();
                                    Intent intent2 =
                                            new Intent(
                                                    "android.intent.action.GET_CONTENT",
                                                    (Uri) null);
                                    intent2.setPackage("com.sec.android.gallery3d");
                                    intent2.setType("image/*");
                                    ((AvatarPickerActivity) avatarPhotoController.mAvatarUi.this$0)
                                            .startActivityForResult(intent2, 1001);
                                    return;
                                case 1:
                                    AvatarPhotoController avatarPhotoController2 =
                                            avatarPickerActivity.mAvatarPhotoController;
                                    avatarPhotoController2.getClass();
                                    Intent intent22 =
                                            new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                    Uri uri = avatarPhotoController2.mTakePictureUri;
                                    intent22.putExtra("output", uri);
                                    intent22.addFlags(3);
                                    intent22.setClipData(ClipData.newRawUri("output", uri));
                                    ((AvatarPickerActivity) avatarPhotoController2.mAvatarUi.this$0)
                                            .startActivityForResult(intent22, 1002);
                                    return;
                                case 2:
                                    AvatarPickerActivity.AvatarAdapter avatarAdapter2 =
                                            avatarPickerActivity.mAdapter;
                                    int i32 = avatarAdapter2.mSelectedPosition;
                                    int length = avatarAdapter2.mPreselectedImages.length();
                                    AvatarPickerActivity avatarPickerActivity2 =
                                            AvatarPickerActivity.this;
                                    if (length <= 0) {
                                        int i42 = avatarAdapter2.mUserIconColors[i32];
                                        avatarPickerActivity2.getClass();
                                        Intent intent3 = new Intent();
                                        intent3.putExtra("default_icon_tint_color", i42);
                                        avatarPickerActivity2.setResult(-1, intent3);
                                        avatarPickerActivity2.finish();
                                        return;
                                    }
                                    int resourceId =
                                            avatarAdapter2.mPreselectedImages.getResourceId(
                                                    i32, -1);
                                    if (resourceId == -1) {
                                        throw new IllegalStateException(
                                                "Preselected avatar images must be resources.");
                                    }
                                    Uri build =
                                            new Uri.Builder()
                                                    .scheme("android.resource")
                                                    .authority(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourcePackageName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceTypeName(
                                                                            resourceId))
                                                    .appendPath(
                                                            avatarPickerActivity2
                                                                    .getResources()
                                                                    .getResourceEntryName(
                                                                            resourceId))
                                                    .build();
                                    Intent intent4 = new Intent();
                                    intent4.setData(build);
                                    avatarPickerActivity2.setResult(-1, intent4);
                                    avatarPickerActivity2.finish();
                                    return;
                                default:
                                    int i5 = AvatarPickerActivity.$r8$clinit;
                                    avatarPickerActivity.cancel$1$1();
                                    return;
                            }
                        }
                    });
        }
        if (bundle != null) {
            this.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
            this.mAdapter.mSelectedPosition = bundle.getInt("selected_position", -1);
            saveButtonSetEnabled(this.mAdapter.mSelectedPosition != -1);
        }
        AvatarPhotoController.AnonymousClass2 anonymousClass2 =
                new AvatarPhotoController.AnonymousClass2(this);
        String stringExtra = getIntent().getStringExtra("file_authority");
        if (stringExtra == null) {
            Log.e(getClass().getName(), "File authority must be provided");
            finish();
        }
        this.mAvatarPhotoController =
                new AvatarPhotoController(
                        anonymousClass2,
                        new AvatarPhotoController.ContextInjectorImpl(this, stringExtra),
                        this.mWaitingForActivityResult);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            cancel$1$1();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mAdapter.getClass();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
        bundle.putInt("selected_position", this.mAdapter.mSelectedPosition);
        super.onSaveInstanceState(bundle);
    }

    public final void saveButtonSetEnabled(boolean z) {
        Button button = this.mSaveButton;
        if (button != null) {
            button.setEnabled(z);
            this.mSaveButton.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i) {
        this.mWaitingForActivityResult = true;
        super.startActivityForResult(intent, i);
    }
}
