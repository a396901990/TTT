package com.dean.travltotibet.ui;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class XRTextView extends TextView {

    private final String namespace = "rong.android.TextView";
    private String text;
    private float textSize;
    private float paddingLeft;
    private float paddingRight;
    private float marginLeft;
    private float marginRight;
    private int textColor;
    private Paint paint1 = new Paint();
    private float textShowWidth;
    private float Spacing = 0;
    private float LineSpacing = 1.5f;//行与行的间距

    public XRTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        text = attrs.getAttributeValue(
                "http://schemas.android.com/apk/res/android", "text");
        textSize = attrs.getAttributeIntValue(namespace, "textSize", 25);//字体大小
        textColor = attrs.getAttributeIntValue(namespace, "textColor", Color.BLUE);//字体颜色
        paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 10);
        paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 10);
        marginLeft = attrs.getAttributeIntValue(namespace, "marginLeft", 5);
        marginRight = attrs.getAttributeIntValue(namespace, "marginRight", 5);
        paint1.setTextSize(getTextSize());
        paint1.setColor(getCurrentTextColor());
        paint1.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
        View view = (View) this.getParent();
        textShowWidth = view.getMeasuredWidth() - paddingLeft - paddingRight - marginLeft - marginRight;
        int lineCount = 0;

        text = this.getText().toString();//.replaceAll("\n", "\r\n");
        if (text == null) return;
        char[] textCharArray = text.toCharArray();
        // 已绘的宽度
        float drawedWidth = 0;
        float charWidth;
        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);

            if (textCharArray[i] == '\n') {
                lineCount++;
                drawedWidth = 0;
                continue;
            }
            if (textShowWidth - drawedWidth < charWidth) {
                lineCount++;
                drawedWidth = 0;
            }

            canvas.drawText(textCharArray, i, 1,
                    paddingLeft + drawedWidth, (lineCount + 1) * textSize * LineSpacing + paddingRight, paint1);
            if (textCharArray[i] > 127 && textCharArray[i] != '、'
                    && textCharArray[i] != '，' && textCharArray[i] != '。'
                    && textCharArray[i] != '：' && textCharArray[i] != '！') {
                drawedWidth += charWidth + Spacing;

            } else {
                drawedWidth += charWidth;
            }
        }
        setHeight((int) ((lineCount + 1) * (int) textSize * LineSpacing + 30));
    }

}
