package com.android.settingslib.drawer;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class Tile implements Parcelable {
    public static final Parcelable.Creator<Tile> CREATOR = new AnonymousClass1();
    public static final Tile$$ExternalSyntheticLambda0 TILE_COMPARATOR =
            new Tile$$ExternalSyntheticLambda0();
    public final String fragment;
    public String mCategory;
    public ComponentInfo mComponentInfo;
    public final String mComponentName;
    public final String mComponentPackage;
    public String mGroupKey;
    public final Intent mIntent;
    long mLastUpdateTime;
    public Bundle mMetaData;
    public String mSaLoggingIdOverride;
    public CharSequence mSummaryOverride;
    public int mSummaryResIdOverride;
    public int mTitleResIdOverride;
    public final String role;
    public String support;
    public String tileId;
    public final ArrayList userHandle = new ArrayList();
    public final HashMap pendingIntentMap = new HashMap();
    public int mIconResIdOverride = 0;
    public int mOrderOverride = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.drawer.Tile$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            boolean readBoolean = parcel.readBoolean();
            parcel.setDataPosition(0);
            if (!readBoolean) {
                return new ActivityTile(parcel);
            }
            ProviderTile providerTile = new ProviderTile(parcel);
            providerTile.mAuthority = ((ProviderInfo) providerTile.mComponentInfo).authority;
            providerTile.mKey = providerTile.mMetaData.getString("com.android.settings.keyhint");
            return providerTile;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Tile[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResourceInfo {
        public final String mResourceId;

        public ResourceInfo(String str) {
            this.mResourceId = str;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type ACTION;
        public static final Type EXTERNAL_ACTION;
        public static final Type GROUP;
        public static final Type SWITCH;
        public static final Type SWITCH_WITH_ACTION;

        static {
            Type type = new Type("ACTION", 0);
            ACTION = type;
            Type type2 = new Type("EXTERNAL_ACTION", 1);
            EXTERNAL_ACTION = type2;
            Type type3 = new Type("SWITCH", 2);
            SWITCH = type3;
            Type type4 = new Type("SWITCH_WITH_ACTION", 3);
            SWITCH_WITH_ACTION = type4;
            Type type5 = new Type("GROUP", 4);
            GROUP = type5;
            $VALUES = new Type[] {type, type2, type3, type4, type5};
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }
    }

    public Tile(ComponentInfo componentInfo, String str, Bundle bundle) {
        this.mComponentInfo = componentInfo;
        String str2 = componentInfo.packageName;
        this.mComponentPackage = str2;
        String str3 = componentInfo.name;
        this.mComponentName = str3;
        this.mCategory = str;
        this.mMetaData = bundle;
        if (bundle != null) {
            this.mGroupKey = bundle.getString("com.android.settings.group_key");
        }
        Intent className = new Intent().setClassName(str2, str3);
        this.mIntent = className;
        if (isNewTask()) {
            className.addFlags(268435456);
        }
        this.mTitleResIdOverride = 0;
        this.mSummaryResIdOverride = 0;
    }

    public static boolean isPrimaryProfileOnly(Bundle bundle) {
        String string =
                bundle != null ? bundle.getString("com.android.settings.profile") : "all_profiles";
        return TextUtils.equals(string != null ? string : "all_profiles", "primary_profile_only");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void ensureMetadataNotStale(Context context) {
        try {
            long j =
                    context.getApplicationContext()
                            .getPackageManager()
                            .getPackageInfo(this.mComponentPackage, 128)
                            .lastUpdateTime;
            if (j == this.mLastUpdateTime) {
                return;
            }
            this.mComponentInfo = null;
            getComponentInfo(context);
            this.mLastUpdateTime = j;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("Tile", "Can't find package, probably uninstalled.");
        }
    }

    public abstract ComponentInfo getComponentInfo(Context context);

    public abstract CharSequence getComponentLabel(Context context);

    public abstract String getDescription();

    public final Icon getIcon(Context context) {
        int i = this.mIconResIdOverride;
        if (i == -1) {
            return null;
        }
        if (i != 0) {
            return Icon.createWithResource(context.getResources(), this.mIconResIdOverride);
        }
        if (context != null && this.mMetaData != null) {
            ensureMetadataNotStale(context);
            ComponentInfo componentInfo = getComponentInfo(context);
            if (componentInfo == null) {
                Log.w("Tile", "Cannot find ComponentInfo for " + getDescription());
                return null;
            }
            int i2 = this.mMetaData.getInt("com.android.settings.icon");
            if (i2 != 0 && i2 != 17170445) {
                Icon createWithResource = Icon.createWithResource(componentInfo.packageName, i2);
                ensureMetadataNotStale(context);
                Bundle bundle = this.mMetaData;
                if ((bundle == null || !bundle.containsKey("com.android.settings.icon_tintable"))
                        ? false
                        : this.mMetaData.getBoolean("com.android.settings.icon_tintable")) {
                    TypedArray obtainStyledAttributes =
                            context.obtainStyledAttributes(new int[] {R.attr.colorControlNormal});
                    int color = obtainStyledAttributes.getColor(0, 0);
                    obtainStyledAttributes.recycle();
                    createWithResource.setTint(color);
                }
                return createWithResource;
            }
        }
        return null;
    }

    public final String getKey(Context context) {
        ensureMetadataNotStale(context);
        Bundle bundle = this.mMetaData;
        if (bundle == null || !bundle.containsKey("com.android.settings.keyhint")) {
            return null;
        }
        return this.mMetaData.get("com.android.settings.keyhint") instanceof Integer
                ? context.getResources()
                        .getString(this.mMetaData.getInt("com.android.settings.keyhint"))
                : this.mMetaData.getString("com.android.settings.keyhint");
    }

    public final int getOrder() {
        int i = this.mOrderOverride;
        if (i != 0) {
            return i;
        }
        if (hasOrder()) {
            return this.mMetaData.getInt("com.android.settings.order");
        }
        return 0;
    }

    public final String getSaLoggingId() {
        String str = this.mSaLoggingIdOverride;
        if (str != null) {
            return str;
        }
        Bundle bundle = this.mMetaData;
        if (bundle != null && bundle.containsKey("com.android.settings.logging_id")) {
            return this.mMetaData.get("com.android.settings.logging_id") instanceof Integer
                    ? String.valueOf(this.mMetaData.getInt("com.android.settings.logging_id"))
                    : this.mMetaData.getString("com.android.settings.logging_id");
        }
        return null;
    }

    public final CharSequence getSummary(Context context) {
        CharSequence charSequence = this.mSummaryOverride;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.mSummaryResIdOverride != 0) {
            return context.getResources().getString(this.mSummaryResIdOverride);
        }
        ensureMetadataNotStale(context);
        PackageManager packageManager = context.getPackageManager();
        Bundle bundle = this.mMetaData;
        if (bundle == null
                || bundle.containsKey("com.android.settings.summary_uri")
                || !this.mMetaData.containsKey("com.android.settings.summary")) {
            return null;
        }
        if (!(this.mMetaData.get("com.android.settings.summary") instanceof Integer)) {
            return this.mMetaData.getString("com.android.settings.summary");
        }
        try {
            return packageManager
                    .getResourcesForApplication(this.mComponentPackage)
                    .getString(this.mMetaData.getInt("com.android.settings.summary"));
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            Log.d("Tile", "Couldn't find info", e);
            return null;
        }
    }

    public final CharSequence getTitle(Context context) {
        if (this.mTitleResIdOverride != 0) {
            return context.getResources().getString(this.mTitleResIdOverride);
        }
        ensureMetadataNotStale(context);
        PackageManager packageManager = context.getPackageManager();
        Bundle bundle = this.mMetaData;
        String str = null;
        if (bundle != null && bundle.containsKey("com.android.settings.title")) {
            if (this.mMetaData.containsKey("com.android.settings.title_uri")) {
                return null;
            }
            if (this.mMetaData.get("com.android.settings.title") instanceof Integer) {
                try {
                    str =
                            packageManager
                                    .getResourcesForApplication(this.mComponentPackage)
                                    .getString(this.mMetaData.getInt("com.android.settings.title"));
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
                    Log.w("Tile", "Couldn't find info", e);
                }
            } else {
                str = this.mMetaData.getString("com.android.settings.title");
            }
        }
        return str == null ? getComponentLabel(context) : str;
    }

    public final int getTitleRes(Context context) {
        int i = this.mTitleResIdOverride;
        if (i != 0) {
            return i;
        }
        ensureMetadataNotStale(context);
        Bundle bundle = this.mMetaData;
        if (bundle == null
                || !bundle.containsKey("com.android.settings.title")
                || this.mMetaData.containsKey("com.android.settings.title_uri")
                || !(this.mMetaData.get("com.android.settings.title") instanceof Integer)) {
            return -1;
        }
        return this.mMetaData.getInt("com.android.settings.title");
    }

    public final Type getType() {
        boolean z = !this.pendingIntentMap.isEmpty();
        boolean z2 = z || (this instanceof ActivityTile);
        Bundle bundle = this.mMetaData;
        boolean z3 = bundle != null && bundle.containsKey("com.android.settings.switch_uri");
        return (z3 && z2)
                ? Type.SWITCH_WITH_ACTION
                : z3 ? Type.SWITCH : z ? Type.EXTERNAL_ACTION : z2 ? Type.ACTION : Type.GROUP;
    }

    public final boolean hasOrder() {
        if (this.mOrderOverride != 0) {
            return true;
        }
        Bundle bundle = this.mMetaData;
        return bundle != null
                && bundle.containsKey("com.android.settings.order")
                && (this.mMetaData.get("com.android.settings.order") instanceof Integer);
    }

    public final boolean isNewTask() {
        Bundle bundle = this.mMetaData;
        if (bundle == null || !bundle.containsKey("com.android.settings.new_task")) {
            return false;
        }
        return this.mMetaData.getBoolean("com.android.settings.new_task");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this instanceof ProviderTile);
        parcel.writeString(this.mComponentPackage);
        parcel.writeString(this.mComponentName);
        int size = this.userHandle.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            ((UserHandle) this.userHandle.get(i2)).writeToParcel(parcel, i);
        }
        parcel.writeString(this.support);
        parcel.writeString(this.mCategory);
        parcel.writeBundle(this.mMetaData);
        parcel.writeString(this.mGroupKey);
        parcel.writeString(this.fragment);
        parcel.writeString(this.tileId);
        parcel.writeString(this.role);
    }

    public Tile(Parcel parcel) {
        String readString = parcel.readString();
        this.mComponentPackage = readString;
        String readString2 = parcel.readString();
        this.mComponentName = readString2;
        this.mIntent = new Intent().setClassName(readString, readString2);
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.userHandle.add((UserHandle) UserHandle.CREATOR.createFromParcel(parcel));
        }
        this.fragment = parcel.readString();
        this.tileId = parcel.readString();
        this.role = parcel.readString();
        this.mCategory = parcel.readString();
        this.mMetaData = parcel.readBundle();
        this.support = parcel.readString();
        if (isNewTask()) {
            this.mIntent.addFlags(268435456);
        }
        this.mGroupKey = parcel.readString();
    }
}
