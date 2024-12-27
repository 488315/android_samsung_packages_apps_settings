package com.samsung.android.knox.lockscreen;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EmergencyPhoneWidget extends LinearLayout
        implements View.OnClickListener, View.OnTouchListener {
    public static final int BG_COLOR = -16777216;
    public static final int BG_COLOR_ONFOCUS = Color.argb(200, 82, 219, 226);
    public static final String TAG = "LSO";
    public static final int TXT_COLOR = -1;
    public Button mBtn;
    public LinearLayout mContainer;
    public final Context mContext;
    public Drawable mDrawable;
    public int mImageMaxSize;
    public ImageView mImgView;
    public String mPhoneAction;

    public EmergencyPhoneWidget(Context context) {
        super(context);
        this.mContext = context;
        initialize();
    }

    public final void callEmergencyNumber() {
        try {
            Intent intent = new Intent("android.intent.action.CALL");
            intent.setFlags(268435456);
            intent.setData(Uri.parse("tel:" + this.mPhoneAction));
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("LSO", "Failed to place Emergency call", e);
        } catch (Exception e2) {
            Log.e("LSO", "Failed to place Emergency call", e2);
        }
    }

    public final void initialize() {
        this.mImageMaxSize = LSOUtils.convertDipToPixel(this.mContext, 50);
        setGravity(17);
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        this.mContainer = linearLayout;
        linearLayout.setPadding(5, 5, 5, 5);
        this.mContainer.setGravity(17);
        addView(this.mContainer, new LinearLayout.LayoutParams(-2, -2));
        this.mContainer.setOnClickListener(this);
        this.mContainer.setFocusable(true);
        this.mContainer.setOnTouchListener(this);
        this.mContainer.setBackgroundColor(BG_COLOR);
        this.mImgView = new ImageView(this.mContext);
        BitmapDrawable bitmapDrawable =
                (BitmapDrawable)
                        LSOUtils.getResourceDrawable(this.mContext, R.drawable.pointer_wait_29);
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            int i = this.mImageMaxSize;
            this.mImgView.setImageDrawable(
                    new BitmapDrawable(
                            this.mContext.getResources(), LSOUtils.scaledBitmap(bitmap, i, i)));
            this.mImgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.mContainer.addView(this.mImgView, new LinearLayout.LayoutParams(-2, -2));
            this.mImgView.setFocusable(false);
            this.mImgView.setClickable(false);
        }
        Button button = new Button(this.mContext);
        this.mBtn = button;
        button.setText(LSOUtils.getResourceString(this.mContext, R.string.webpage_unresponsive));
        this.mBtn.setBackgroundDrawable(null);
        this.mBtn.setTextColor(-1);
        this.mContainer.addView(this.mBtn, new LinearLayout.LayoutParams(-2, -2));
        this.mBtn.setFocusable(false);
        this.mBtn.setClickable(false);
        this.mDrawable = this.mContainer.getBackground();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Toast.makeText(this.mContext, "Calling " + this.mPhoneAction, 0).show();
        callEmergencyNumber();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mContainer.setBackgroundDrawable(null);
            this.mContainer.setBackgroundColor(BG_COLOR_ONFOCUS);
            return false;
        }
        if (motionEvent.getAction() != 1) {
            return false;
        }
        this.mContainer.setBackgroundDrawable(this.mDrawable);
        return false;
    }

    public void setAttribute(String str, Object obj) {
        Bitmap bitmap;
        if (str == null) {
            return;
        }
        try {
            Log.d("LSO", "SetAttribute(" + str + ", " + obj.toString() + ")");
            if (str.equalsIgnoreCase(LSOAttrConst.EPW_ATTR_PHONE_NUMBER)) {
                this.mPhoneAction = obj.toString();
            } else if (str.equalsIgnoreCase(LSOAttrConst.ATTR_TEXT)) {
                this.mBtn.setText(obj.toString());
            } else if (str.equalsIgnoreCase(LSOAttrConst.EPW_ATTR_SHOW_BG)) {
                if (!((Boolean) obj).booleanValue()) {
                    this.mContainer.setBackgroundDrawable(null);
                    this.mDrawable = null;
                }
            } else if (str.equalsIgnoreCase(LSOAttrConst.EPW_ATTR_SHOW_DEFAULT_TEXT)) {
                if (((Boolean) obj).booleanValue()) {
                    this.mBtn.setVisibility(0);
                } else {
                    this.mBtn.setVisibility(8);
                }
            } else if (str.equalsIgnoreCase(LSOAttrConst.ATTR_MAX_LINES)) {
                this.mBtn.setMaxLines(((Integer) obj).intValue());
            } else if (str.equalsIgnoreCase(LSOAttrConst.ATTR_GRAVITY)) {
                setGravity(((Integer) obj).intValue());
            } else if (str.equalsIgnoreCase(LSOAttrConst.ATTR_ORIENTATION)) {
                if (((Boolean) obj).booleanValue()) {
                    this.mContainer.setOrientation(1);
                }
            } else if (str.equalsIgnoreCase(LSOAttrConst.ATTR_IMAGE_SRC)
                    && (bitmap = LSOUtils.getBitmap((String) obj, this.mImageMaxSize)) != null) {
                BitmapDrawable bitmapDrawable =
                        new BitmapDrawable(this.mContext.getResources(), bitmap);
                ImageView imageView = this.mImgView;
                if (imageView != null) {
                    imageView.setImageDrawable(bitmapDrawable);
                }
            }
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m("Exception: ", e, "LSO");
        }
    }

    public EmergencyPhoneWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initialize();
    }
}
