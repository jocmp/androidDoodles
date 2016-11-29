package co.josiahcampbell.prefixedittext;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;


public class PrefixEditText extends EditText {

    private TextDrawable left;
    private Rect firstLineBounds;
    private int baseline;

    public PrefixEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        firstLineBounds = new Rect();

        left = new TextDrawable(getTextSize(), getCurrentTextColor());

        // Setup the left side
        setCompoundDrawables(left, null, null, null);
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        if (left != null) {
            left.setTypeface(typeface);
        }
        postInvalidate();
    }

    public void setPrefix(String s) {
        left.setText(s);
    }

    @Override
    public void onDraw(Canvas canvas) {
        baseline = getLineBounds(0, firstLineBounds);

        if (getEditableText().length() > 0) {
            setCompoundDrawables(left, null, null, null);
        } else {
            setCompoundDrawables(null, null, null, null);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    final class TextDrawable extends Drawable {

        private final TextPaint textPaint;
        private String text;
        private int height;

        TextDrawable(float fontHeight, int color) {
            textPaint = new TextPaint();

            textPaint.setColor(color);
            textPaint.setTextSize(fontHeight);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setAntiAlias(true);

            this.text = "";
            height = (int) fontHeight;
        }

        void setText(String s) {
            text = s;

            setBounds(0, 0, getIntrinsicWidth(), getIntrinsicHeight());

            invalidateSelf();
        }

        void setTypeface(Typeface typeface) {
            textPaint.setTypeface(typeface);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawText(text, 0, baseline + canvas.getClipBounds().top, textPaint);
        }

        @Override
        public void setAlpha(int alpha) {
            textPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            textPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getIntrinsicHeight() {
            return height;
        }

        @Override
        public int getIntrinsicWidth() {
            return (int) textPaint.measureText(text);
        }
    }

}