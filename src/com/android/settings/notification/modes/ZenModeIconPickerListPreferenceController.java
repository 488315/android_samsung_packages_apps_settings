package com.android.settings.notification.modes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.modes.ZenModeIconPickerListPreferenceController;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.LayoutPreference;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeIconPickerListPreferenceController extends AbstractZenModePreferenceController {
    public IconAdapter mAdapter;
    public final DashboardFragment mFragment;
    public final IconOptionsProviderImpl mIconOptionsProvider;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoFitGridLayoutManager extends GridLayoutManager {
        public final float mColumnWidth;

        public AutoFitGridLayoutManager(Context context) {
            super(1);
            this.mColumnWidth = context.getResources().getDimensionPixelSize(R.dimen.zen_mode_icon_list_item_size);
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            setSpanCount(Math.max(1, (int) (((this.mWidth - getPaddingRight()) - getPaddingLeft()) / this.mColumnWidth)));
            super.onLayoutChildren(recycler, state);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconAdapter extends RecyclerView.Adapter {
        public final ImmutableList mIconResources;

        public IconAdapter(IconOptionsProviderImpl iconOptionsProviderImpl) {
            iconOptionsProviderImpl.getClass();
            ImmutableList.Itr itr = ImmutableList.EMPTY_ITR;
            CollectPreconditions.checkNonnegative(4, "initialCapacity");
            Object[] objArr = new Object[4];
            TypedArray obtainTypedArray = iconOptionsProviderImpl.mContext.getResources().obtainTypedArray(R.array.zen_mode_icon_options);
            try {
                String[] stringArray = iconOptionsProviderImpl.mContext.getResources().getStringArray(R.array.zen_mode_icon_options_descriptions);
                if (obtainTypedArray.length() != stringArray.length) {
                    Log.wtf("IconOptionsProviderImpl", "Size mismatch between zen_mode_icon_options (" + obtainTypedArray.length() + ") and zen_mode_icon_options_descriptions (" + stringArray.length + ")");
                }
                int i = 0;
                for (int i2 = 0; i2 < Math.min(obtainTypedArray.length(), stringArray.length); i2++) {
                    int resourceId = obtainTypedArray.getResourceId(i2, 0);
                    if (resourceId != 0) {
                        IconOptionsProvider$IconInfo iconOptionsProvider$IconInfo = new IconOptionsProvider$IconInfo(resourceId, stringArray[i2]);
                        int i3 = i + 1;
                        objArr = objArr.length < i3 ? Arrays.copyOf(objArr, ImmutableCollection.Builder.expandedCapacity(objArr.length, i3)) : objArr;
                        objArr[i] = iconOptionsProvider$IconInfo;
                        i = i3;
                    }
                }
                obtainTypedArray.close();
                this.mIconResources = ImmutableList.asImmutableList(i, objArr);
            } catch (Throwable th) {
                if (obtainTypedArray != null) {
                    try {
                        obtainTypedArray.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mIconResources.size();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            final IconHolder iconHolder = (IconHolder) viewHolder;
            final IconOptionsProvider$IconInfo iconOptionsProvider$IconInfo = (IconOptionsProvider$IconInfo) this.mIconResources.get(i);
            ImageView imageView = iconHolder.mImageView;
            Context context = iconHolder.itemView.getContext();
            Drawable drawable = context.getDrawable(iconOptionsProvider$IconInfo.resId);
            drawable.getClass();
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(Utils.getColorAttrDefaultColor(context, android.R.^attr-private.materialColorSurface));
            drawable.setTint(Utils.getColorAttrDefaultColor(context, android.R.^attr-private.materialColorOnSurface));
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable, drawable});
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.zen_mode_icon_list_circle_diameter);
            int dimensionPixelSize2 = (dimensionPixelSize - context.getResources().getDimensionPixelSize(R.dimen.zen_mode_icon_list_icon_size)) / 2;
            layerDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            layerDrawable.setLayerInset(1, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
            imageView.setImageDrawable(layerDrawable);
            iconHolder.itemView.setContentDescription(iconOptionsProvider$IconInfo.description);
            iconHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.notification.modes.ZenModeIconPickerListPreferenceController$IconHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ZenModeIconPickerListPreferenceController.IconHolder iconHolder2 = ZenModeIconPickerListPreferenceController.IconHolder.this;
                    IconOptionsProvider$IconInfo iconOptionsProvider$IconInfo2 = iconOptionsProvider$IconInfo;
                    iconHolder2.getClass();
                    ZenModeIconPickerListPreferenceController.this.onIconSelected(iconOptionsProvider$IconInfo2.resId);
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return ZenModeIconPickerListPreferenceController.this.new IconHolder(MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.modes_icon_list_item, viewGroup, false));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public IconHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.icon_image_view);
        }
    }

    public ZenModeIconPickerListPreferenceController(Context context, DashboardFragment dashboardFragment, IconOptionsProviderImpl iconOptionsProviderImpl, ZenModesBackend zenModesBackend) {
        super(context, "icon_list", zenModesBackend);
        this.mFragment = dashboardFragment;
        this.mIconOptionsProvider = iconOptionsProviderImpl;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference = (LayoutPreference) preferenceScreen.findPreference(this.mKey);
        if (layoutPreference == null) {
            return;
        }
        if (this.mAdapter == null) {
            this.mAdapter = new IconAdapter(this.mIconOptionsProvider);
        }
        RecyclerView recyclerView = (RecyclerView) layoutPreference.mRootView.findViewById(R.id.icon_list);
        recyclerView.setLayoutManager(new AutoFitGridLayoutManager(this.mContext));
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void onIconSelected(final int i) {
        saveMode(new Function() { // from class: com.android.settings.notification.modes.ZenModeIconPickerListPreferenceController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                zenMode.mRule.setIconResId(i);
                return zenMode;
            }
        });
        this.mFragment.finish();
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
    }
}
