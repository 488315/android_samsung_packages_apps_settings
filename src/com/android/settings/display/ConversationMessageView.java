package com.android.settings.display;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConversationMessageView extends FrameLayout {
    public TextView mContactIconView;
    public final int mIconBackgroundColor;
    public final CharSequence mIconText;
    public final int mIconTextColor;
    public final boolean mIncoming;
    public LinearLayout mMessageBubble;
    public final CharSequence mMessageText;
    public ViewGroup mMessageTextAndInfoView;
    public TextView mMessageTextView;
    public TextView mStatusTextView;
    public final CharSequence mTimestampText;

    public ConversationMessageView(Context context) {
        this(context, null);
    }

    public static Drawable getTintedDrawable(int i, Context context, Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            drawable = constantState.newDrawable(context.getResources()).mutate();
        }
        drawable.setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        this.mMessageBubble = (LinearLayout) findViewById(R.id.message_content);
        this.mMessageTextAndInfoView = (ViewGroup) findViewById(R.id.message_text_and_info);
        this.mMessageTextView = (TextView) findViewById(R.id.message_text);
        this.mStatusTextView = (TextView) findViewById(R.id.message_status);
        this.mContactIconView = (TextView) findViewById(R.id.conversation_icon);
        this.mMessageTextView.setText(this.mMessageText);
        this.mStatusTextView.setText(this.mTimestampText);
        this.mStatusTextView.setVisibility(TextUtils.isEmpty(this.mTimestampText) ? 8 : 0);
        this.mContactIconView.setText(this.mIconText);
        this.mContactIconView.setTextColor(this.mIconTextColor);
        LayerDrawable layerDrawable =
                new LayerDrawable(
                        new Drawable[] {
                            getTintedDrawable(
                                    this.mIconBackgroundColor,
                                    getContext(),
                                    getContext().getDrawable(R.drawable.conversation_message_icon)),
                            getTintedDrawable(
                                    getContext().getColor(R.color.message_icon_color),
                                    getContext(),
                                    getContext().getDrawable(R.drawable.ic_person))
                        });
        int dimensionPixelOffset =
                getResources().getDimensionPixelOffset(R.dimen.message_icon_inset);
        layerDrawable.setLayerInset(
                1,
                dimensionPixelOffset,
                dimensionPixelOffset,
                dimensionPixelOffset,
                dimensionPixelOffset);
        this.mContactIconView.setBackground(layerDrawable);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingRight;
        int paddingLeft;
        int i5;
        boolean z2 = 1 == getLayoutDirection();
        int measuredWidth = this.mContactIconView.getMeasuredWidth();
        int measuredHeight = this.mContactIconView.getMeasuredHeight();
        int paddingTop = getPaddingTop();
        int i6 = i3 - i;
        int paddingLeft2 = ((i6 - measuredWidth) - getPaddingLeft()) - getPaddingRight();
        int measuredHeight2 = this.mMessageBubble.getMeasuredHeight();
        if (this.mIncoming) {
            if (z2) {
                paddingRight = getPaddingRight();
                paddingLeft = (i6 - paddingRight) - measuredWidth;
                i5 = paddingLeft - paddingLeft2;
            } else {
                paddingLeft = getPaddingLeft();
                i5 = paddingLeft + measuredWidth;
            }
        } else if (z2) {
            paddingLeft = getPaddingLeft();
            i5 = paddingLeft + measuredWidth;
        } else {
            paddingRight = getPaddingRight();
            paddingLeft = (i6 - paddingRight) - measuredWidth;
            i5 = paddingLeft - paddingLeft2;
        }
        this.mContactIconView.layout(
                paddingLeft, paddingTop, measuredWidth + paddingLeft, measuredHeight + paddingTop);
        this.mMessageBubble.layout(i5, paddingTop, paddingLeft2 + i5, measuredHeight2 + paddingTop);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Resources resources = getResources();
        int dimensionPixelOffset =
                resources.getDimensionPixelOffset(R.dimen.message_bubble_left_right_padding);
        int dimensionPixelOffset2 =
                resources.getDimensionPixelOffset(R.dimen.message_text_left_right_padding);
        int dimensionPixelOffset3 =
                resources.getDimensionPixelOffset(R.dimen.message_text_top_padding);
        int dimensionPixelOffset4 =
                resources.getDimensionPixelOffset(R.dimen.message_text_bottom_padding);
        boolean z = this.mIncoming;
        int i3 = z ? dimensionPixelOffset : 0;
        if (z) {
            dimensionPixelOffset = 0;
        }
        int i4 = z ? 8388627 : 8388629;
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.message_padding_default);
        int dimensionPixelOffset5 =
                resources.getDimensionPixelOffset(R.dimen.message_metadata_top_padding);
        int i5 = this.mIncoming ? R.color.message_bubble_incoming : R.color.message_bubble_outgoing;
        Context context = getContext();
        this.mMessageTextAndInfoView.setBackground(
                getTintedDrawable(
                        context.getColor(i5),
                        context,
                        context.getDrawable(
                                R.drawable.conversation_message_text_info_view_background)));
        if (1 == getLayoutDirection()) {
            this.mMessageTextAndInfoView.setPadding(
                    dimensionPixelOffset2,
                    dimensionPixelOffset3 + dimensionPixelOffset5,
                    dimensionPixelOffset2,
                    dimensionPixelOffset4);
        } else {
            this.mMessageTextAndInfoView.setPadding(
                    dimensionPixelOffset2,
                    dimensionPixelOffset3 + dimensionPixelOffset5,
                    dimensionPixelOffset2,
                    dimensionPixelOffset4);
        }
        setPadding(getPaddingLeft(), dimensionPixelSize, getPaddingRight(), 0);
        this.mMessageBubble.setGravity(i4);
        this.mMessageBubble.setPaddingRelative(i3, 0, dimensionPixelOffset, 0);
        boolean z2 = this.mIncoming;
        int i6 = z2 ? R.color.message_text_incoming : R.color.message_text_outgoing;
        int i7 = z2 ? R.color.timestamp_text_incoming : R.color.timestamp_text_outgoing;
        int color = getContext().getColor(i6);
        this.mMessageTextView.setTextColor(color);
        this.mMessageTextView.setLinkTextColor(color);
        this.mStatusTextView.setTextColor(i7);
        int size = View.MeasureSpec.getSize(i);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContactIconView.measure(makeMeasureSpec2, makeMeasureSpec2);
        int makeMeasureSpec3 =
                View.MeasureSpec.makeMeasureSpec(
                        Math.max(
                                this.mContactIconView.getMeasuredWidth(),
                                this.mContactIconView.getMeasuredHeight()),
                        1073741824);
        this.mContactIconView.measure(makeMeasureSpec3, makeMeasureSpec3);
        this.mMessageBubble.measure(
                View.MeasureSpec.makeMeasureSpec(
                        (((size - (this.mContactIconView.getMeasuredWidth() * 2))
                                                - getResources()
                                                        .getDimensionPixelOffset(
                                                                R.dimen
                                                                        .message_bubble_left_right_padding))
                                        - getPaddingLeft())
                                - getPaddingRight(),
                        Integer.MIN_VALUE),
                makeMeasureSpec);
        setMeasuredDimension(
                size,
                getPaddingTop()
                        + getPaddingBottom()
                        + Math.max(
                                this.mContactIconView.getMeasuredHeight(),
                                this.mMessageBubble.getMeasuredHeight()));
    }

    public ConversationMessageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ConversationMessageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ConversationMessageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.ConversationMessageView);
        this.mIncoming = obtainStyledAttributes.getBoolean(3, true);
        this.mMessageText = obtainStyledAttributes.getString(4);
        this.mTimestampText = obtainStyledAttributes.getString(7);
        this.mIconText = obtainStyledAttributes.getString(1);
        this.mIconTextColor = obtainStyledAttributes.getColor(2, 0);
        this.mIconBackgroundColor = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(context).inflate(R.layout.conversation_message_icon, this);
        LayoutInflater.from(context).inflate(R.layout.conversation_message_content, this);
    }
}
