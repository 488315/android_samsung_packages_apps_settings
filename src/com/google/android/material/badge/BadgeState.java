package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BadgeState {
    public final float badgeHeight;
    public final float badgeRadius;
    public final float badgeWidth;
    public final float badgeWithTextHeight;
    public final float badgeWithTextRadius;
    public final float badgeWithTextWidth;
    public final State currentState;
    public final int horizontalInset;
    public final int horizontalInsetWithText;
    public final int offsetAlignmentMode;
    public final State overridingState;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class State implements Parcelable {
        public static final Parcelable.Creator<State> CREATOR = new AnonymousClass1();
        public Integer additionalHorizontalOffset;
        public Integer additionalVerticalOffset;
        public int alpha;
        public Boolean autoAdjustToWithinGrandparentBounds;
        public Integer backgroundColor;
        public Integer badgeGravity;
        public Integer badgeHorizontalPadding;
        public int badgeResId;
        public Integer badgeShapeAppearanceOverlayResId;
        public Integer badgeShapeAppearanceResId;
        public Integer badgeTextAppearanceResId;
        public Integer badgeTextColor;
        public Integer badgeVerticalPadding;
        public Integer badgeWithTextShapeAppearanceOverlayResId;
        public Integer badgeWithTextShapeAppearanceResId;
        public int contentDescriptionExceedsMaxBadgeNumberRes;
        public CharSequence contentDescriptionForText;
        public CharSequence contentDescriptionNumberless;
        public int contentDescriptionQuantityStrings;
        public Integer horizontalOffsetWithText;
        public Integer horizontalOffsetWithoutText;
        public Boolean isVisible;
        public Integer largeFontVerticalOffsetAdjustment;
        public int maxCharacterCount;
        public int maxNumber;
        public int number;
        public Locale numberLocale;
        public String text;
        public Integer verticalOffsetWithText;
        public Integer verticalOffsetWithoutText;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.android.material.badge.BadgeState$State$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                State state = new State();
                state.alpha = 255;
                state.number = -2;
                state.maxCharacterCount = -2;
                state.maxNumber = -2;
                state.isVisible = Boolean.TRUE;
                state.badgeResId = parcel.readInt();
                state.backgroundColor = (Integer) parcel.readSerializable();
                state.badgeTextColor = (Integer) parcel.readSerializable();
                state.badgeTextAppearanceResId = (Integer) parcel.readSerializable();
                state.badgeShapeAppearanceResId = (Integer) parcel.readSerializable();
                state.badgeShapeAppearanceOverlayResId = (Integer) parcel.readSerializable();
                state.badgeWithTextShapeAppearanceResId = (Integer) parcel.readSerializable();
                state.badgeWithTextShapeAppearanceOverlayResId =
                        (Integer) parcel.readSerializable();
                state.alpha = parcel.readInt();
                state.text = parcel.readString();
                state.number = parcel.readInt();
                state.maxCharacterCount = parcel.readInt();
                state.maxNumber = parcel.readInt();
                state.contentDescriptionForText = parcel.readString();
                state.contentDescriptionNumberless = parcel.readString();
                state.contentDescriptionQuantityStrings = parcel.readInt();
                state.badgeGravity = (Integer) parcel.readSerializable();
                state.badgeHorizontalPadding = (Integer) parcel.readSerializable();
                state.badgeVerticalPadding = (Integer) parcel.readSerializable();
                state.horizontalOffsetWithoutText = (Integer) parcel.readSerializable();
                state.verticalOffsetWithoutText = (Integer) parcel.readSerializable();
                state.horizontalOffsetWithText = (Integer) parcel.readSerializable();
                state.verticalOffsetWithText = (Integer) parcel.readSerializable();
                state.largeFontVerticalOffsetAdjustment = (Integer) parcel.readSerializable();
                state.additionalHorizontalOffset = (Integer) parcel.readSerializable();
                state.additionalVerticalOffset = (Integer) parcel.readSerializable();
                state.isVisible = (Boolean) parcel.readSerializable();
                state.numberLocale = (Locale) parcel.readSerializable();
                state.autoAdjustToWithinGrandparentBounds = (Boolean) parcel.readSerializable();
                return state;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new State[i];
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.badgeResId);
            parcel.writeSerializable(this.backgroundColor);
            parcel.writeSerializable(this.badgeTextColor);
            parcel.writeSerializable(this.badgeTextAppearanceResId);
            parcel.writeSerializable(this.badgeShapeAppearanceResId);
            parcel.writeSerializable(this.badgeShapeAppearanceOverlayResId);
            parcel.writeSerializable(this.badgeWithTextShapeAppearanceResId);
            parcel.writeSerializable(this.badgeWithTextShapeAppearanceOverlayResId);
            parcel.writeInt(this.alpha);
            parcel.writeString(this.text);
            parcel.writeInt(this.number);
            parcel.writeInt(this.maxCharacterCount);
            parcel.writeInt(this.maxNumber);
            CharSequence charSequence = this.contentDescriptionForText;
            parcel.writeString(charSequence != null ? charSequence.toString() : null);
            CharSequence charSequence2 = this.contentDescriptionNumberless;
            parcel.writeString(charSequence2 != null ? charSequence2.toString() : null);
            parcel.writeInt(this.contentDescriptionQuantityStrings);
            parcel.writeSerializable(this.badgeGravity);
            parcel.writeSerializable(this.badgeHorizontalPadding);
            parcel.writeSerializable(this.badgeVerticalPadding);
            parcel.writeSerializable(this.horizontalOffsetWithoutText);
            parcel.writeSerializable(this.verticalOffsetWithoutText);
            parcel.writeSerializable(this.horizontalOffsetWithText);
            parcel.writeSerializable(this.verticalOffsetWithText);
            parcel.writeSerializable(this.largeFontVerticalOffsetAdjustment);
            parcel.writeSerializable(this.additionalHorizontalOffset);
            parcel.writeSerializable(this.additionalVerticalOffset);
            parcel.writeSerializable(this.isVisible);
            parcel.writeSerializable(this.numberLocale);
            parcel.writeSerializable(this.autoAdjustToWithinGrandparentBounds);
        }
    }

    public BadgeState(Context context, State state) {
        AttributeSet attributeSet;
        int i;
        int next;
        State state2 = new State();
        state2.alpha = 255;
        state2.number = -2;
        state2.maxCharacterCount = -2;
        state2.maxNumber = -2;
        state2.isVisible = Boolean.TRUE;
        this.currentState = state2;
        int i2 = state.badgeResId;
        if (i2 != 0) {
            try {
                XmlResourceParser xml = context.getResources().getXml(i2);
                do {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!TextUtils.equals(xml.getName(), "badge")) {
                    throw new XmlPullParserException(
                            "Must have a <" + ((Object) "badge") + "> start tag");
                }
                AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
                i = asAttributeSet.getStyleAttribute();
                attributeSet = asAttributeSet;
            } catch (IOException | XmlPullParserException e) {
                Resources.NotFoundException notFoundException =
                        new Resources.NotFoundException(
                                "Can't load badge resource ID #0x" + Integer.toHexString(i2));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        } else {
            attributeSet = null;
            i = 0;
        }
        TypedArray obtainStyledAttributes =
                ThemeEnforcement.obtainStyledAttributes(
                        context,
                        attributeSet,
                        R$styleable.Badge,
                        R.attr.badgeStyle,
                        i == 0 ? 2132084862 : i,
                        new int[0]);
        Resources resources = context.getResources();
        this.badgeRadius = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        this.horizontalInset =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.mtrl_badge_horizontal_edge_offset);
        this.horizontalInsetWithText =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.mtrl_badge_text_horizontal_edge_offset);
        this.badgeWithTextRadius = obtainStyledAttributes.getDimensionPixelSize(14, -1);
        this.badgeWidth =
                obtainStyledAttributes.getDimension(
                        12, resources.getDimension(R.dimen.m3_badge_size));
        this.badgeWithTextWidth =
                obtainStyledAttributes.getDimension(
                        17, resources.getDimension(R.dimen.m3_badge_with_text_size));
        this.badgeHeight =
                obtainStyledAttributes.getDimension(
                        3, resources.getDimension(R.dimen.m3_badge_size));
        this.badgeWithTextHeight =
                obtainStyledAttributes.getDimension(
                        13, resources.getDimension(R.dimen.m3_badge_with_text_size));
        this.offsetAlignmentMode = obtainStyledAttributes.getInt(24, 1);
        State state3 = this.currentState;
        int i3 = state.alpha;
        state3.alpha = i3 == -2 ? 255 : i3;
        int i4 = state.number;
        if (i4 != -2) {
            state3.number = i4;
        } else if (obtainStyledAttributes.hasValue(23)) {
            this.currentState.number = obtainStyledAttributes.getInt(23, 0);
        } else {
            this.currentState.number = -1;
        }
        String str = state.text;
        if (str != null) {
            this.currentState.text = str;
        } else if (obtainStyledAttributes.hasValue(7)) {
            this.currentState.text = obtainStyledAttributes.getString(7);
        }
        State state4 = this.currentState;
        state4.contentDescriptionForText = state.contentDescriptionForText;
        CharSequence charSequence = state.contentDescriptionNumberless;
        state4.contentDescriptionNumberless =
                charSequence == null
                        ? context.getString(R.string.mtrl_badge_numberless_content_description)
                        : charSequence;
        State state5 = this.currentState;
        int i5 = state.contentDescriptionQuantityStrings;
        state5.contentDescriptionQuantityStrings =
                i5 == 0 ? R.plurals.mtrl_badge_content_description : i5;
        int i6 = state.contentDescriptionExceedsMaxBadgeNumberRes;
        state5.contentDescriptionExceedsMaxBadgeNumberRes =
                i6 == 0 ? R.string.mtrl_exceed_max_badge_number_content_description : i6;
        Boolean bool = state.isVisible;
        state5.isVisible = Boolean.valueOf(bool == null || bool.booleanValue());
        State state6 = this.currentState;
        int i7 = state.maxCharacterCount;
        state6.maxCharacterCount = i7 == -2 ? obtainStyledAttributes.getInt(21, -2) : i7;
        State state7 = this.currentState;
        int i8 = state.maxNumber;
        state7.maxNumber = i8 == -2 ? obtainStyledAttributes.getInt(22, -2) : i8;
        State state8 = this.currentState;
        Integer num = state.badgeShapeAppearanceResId;
        state8.badgeShapeAppearanceResId =
                Integer.valueOf(
                        num == null
                                ? obtainStyledAttributes.getResourceId(5, 2132083606)
                                : num.intValue());
        State state9 = this.currentState;
        Integer num2 = state.badgeShapeAppearanceOverlayResId;
        state9.badgeShapeAppearanceOverlayResId =
                Integer.valueOf(
                        num2 == null
                                ? obtainStyledAttributes.getResourceId(6, 0)
                                : num2.intValue());
        State state10 = this.currentState;
        Integer num3 = state.badgeWithTextShapeAppearanceResId;
        state10.badgeWithTextShapeAppearanceResId =
                Integer.valueOf(
                        num3 == null
                                ? obtainStyledAttributes.getResourceId(15, 2132083606)
                                : num3.intValue());
        State state11 = this.currentState;
        Integer num4 = state.badgeWithTextShapeAppearanceOverlayResId;
        state11.badgeWithTextShapeAppearanceOverlayResId =
                Integer.valueOf(
                        num4 == null
                                ? obtainStyledAttributes.getResourceId(16, 0)
                                : num4.intValue());
        State state12 = this.currentState;
        Integer num5 = state.backgroundColor;
        state12.backgroundColor =
                Integer.valueOf(
                        num5 == null
                                ? MaterialResources.getColorStateList(
                                                context, obtainStyledAttributes, 1)
                                        .getDefaultColor()
                                : num5.intValue());
        State state13 = this.currentState;
        Integer num6 = state.badgeTextAppearanceResId;
        state13.badgeTextAppearanceResId =
                Integer.valueOf(
                        num6 == null
                                ? obtainStyledAttributes.getResourceId(8, 2132084131)
                                : num6.intValue());
        Integer num7 = state.badgeTextColor;
        if (num7 != null) {
            this.currentState.badgeTextColor = num7;
        } else if (obtainStyledAttributes.hasValue(9)) {
            this.currentState.badgeTextColor =
                    Integer.valueOf(
                            MaterialResources.getColorStateList(context, obtainStyledAttributes, 9)
                                    .getDefaultColor());
        } else {
            int intValue = this.currentState.badgeTextAppearanceResId.intValue();
            TypedArray obtainStyledAttributes2 =
                    context.obtainStyledAttributes(
                            intValue, androidx.appcompat.R$styleable.TextAppearance);
            obtainStyledAttributes2.getDimension(0, 0.0f);
            ColorStateList colorStateList =
                    MaterialResources.getColorStateList(context, obtainStyledAttributes2, 3);
            MaterialResources.getColorStateList(context, obtainStyledAttributes2, 4);
            MaterialResources.getColorStateList(context, obtainStyledAttributes2, 5);
            obtainStyledAttributes2.getInt(2, 0);
            obtainStyledAttributes2.getInt(1, 1);
            int i9 = obtainStyledAttributes2.hasValue(12) ? 12 : 10;
            obtainStyledAttributes2.getResourceId(i9, 0);
            obtainStyledAttributes2.getString(i9);
            obtainStyledAttributes2.getBoolean(14, false);
            MaterialResources.getColorStateList(context, obtainStyledAttributes2, 6);
            obtainStyledAttributes2.getFloat(7, 0.0f);
            obtainStyledAttributes2.getFloat(8, 0.0f);
            obtainStyledAttributes2.getFloat(9, 0.0f);
            obtainStyledAttributes2.recycle();
            TypedArray obtainStyledAttributes3 =
                    context.obtainStyledAttributes(intValue, R$styleable.MaterialTextAppearance);
            obtainStyledAttributes3.hasValue(0);
            obtainStyledAttributes3.getFloat(0, 0.0f);
            obtainStyledAttributes3.recycle();
            this.currentState.badgeTextColor = Integer.valueOf(colorStateList.getDefaultColor());
        }
        State state14 = this.currentState;
        Integer num8 = state.badgeGravity;
        state14.badgeGravity =
                Integer.valueOf(
                        num8 == null ? obtainStyledAttributes.getInt(2, 8388661) : num8.intValue());
        State state15 = this.currentState;
        Integer num9 = state.badgeHorizontalPadding;
        state15.badgeHorizontalPadding =
                Integer.valueOf(
                        num9 == null
                                ? obtainStyledAttributes.getDimensionPixelSize(
                                        11,
                                        resources.getDimensionPixelSize(
                                                R.dimen.mtrl_badge_long_text_horizontal_padding))
                                : num9.intValue());
        State state16 = this.currentState;
        Integer num10 = state.badgeVerticalPadding;
        state16.badgeVerticalPadding =
                Integer.valueOf(
                        num10 == null
                                ? obtainStyledAttributes.getDimensionPixelSize(
                                        10,
                                        resources.getDimensionPixelSize(
                                                R.dimen.m3_badge_with_text_vertical_padding))
                                : num10.intValue());
        State state17 = this.currentState;
        Integer num11 = state.horizontalOffsetWithoutText;
        state17.horizontalOffsetWithoutText =
                Integer.valueOf(
                        num11 == null
                                ? obtainStyledAttributes.getDimensionPixelOffset(18, 0)
                                : num11.intValue());
        State state18 = this.currentState;
        Integer num12 = state.verticalOffsetWithoutText;
        state18.verticalOffsetWithoutText =
                Integer.valueOf(
                        num12 == null
                                ? obtainStyledAttributes.getDimensionPixelOffset(25, 0)
                                : num12.intValue());
        State state19 = this.currentState;
        Integer num13 = state.horizontalOffsetWithText;
        state19.horizontalOffsetWithText =
                Integer.valueOf(
                        num13 == null
                                ? obtainStyledAttributes.getDimensionPixelOffset(
                                        19, state19.horizontalOffsetWithoutText.intValue())
                                : num13.intValue());
        State state20 = this.currentState;
        Integer num14 = state.verticalOffsetWithText;
        state20.verticalOffsetWithText =
                Integer.valueOf(
                        num14 == null
                                ? obtainStyledAttributes.getDimensionPixelOffset(
                                        26, state20.verticalOffsetWithoutText.intValue())
                                : num14.intValue());
        State state21 = this.currentState;
        Integer num15 = state.largeFontVerticalOffsetAdjustment;
        state21.largeFontVerticalOffsetAdjustment =
                Integer.valueOf(
                        num15 == null
                                ? obtainStyledAttributes.getDimensionPixelOffset(20, 0)
                                : num15.intValue());
        State state22 = this.currentState;
        Integer num16 = state.additionalHorizontalOffset;
        state22.additionalHorizontalOffset = Integer.valueOf(num16 == null ? 0 : num16.intValue());
        State state23 = this.currentState;
        Integer num17 = state.additionalVerticalOffset;
        state23.additionalVerticalOffset = Integer.valueOf(num17 == null ? 0 : num17.intValue());
        State state24 = this.currentState;
        Boolean bool2 = state.autoAdjustToWithinGrandparentBounds;
        state24.autoAdjustToWithinGrandparentBounds =
                Boolean.valueOf(
                        bool2 == null
                                ? obtainStyledAttributes.getBoolean(0, false)
                                : bool2.booleanValue());
        obtainStyledAttributes.recycle();
        Locale locale = state.numberLocale;
        if (locale == null) {
            this.currentState.numberLocale = Locale.getDefault(Locale.Category.FORMAT);
        } else {
            this.currentState.numberLocale = locale;
        }
        this.overridingState = state;
    }
}
