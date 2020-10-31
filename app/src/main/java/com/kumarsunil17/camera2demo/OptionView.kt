package com.kumarsunil17.camera2demo

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat


/**
 * Created by Sunil Kumar on 31-10-2020 07:52 AM.
 */
class OptionView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var textView : TextView
    private var spaceView : View
    private var imageView : ImageView
    private var pressOpacity:Float;

    init {
        inflate(getContext(), R.layout.option_view, this)
        textView = findViewById(R.id.optionText)
        imageView = findViewById(R.id.optionIcon)
        spaceView = findViewById(R.id.optionSpace)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.OptionView)
        try {
            val text = ta.getString(R.styleable.OptionView_text)
            textView.text = text
            val drawableId = ta.getResourceId(R.styleable.OptionView_icon, 0)
            pressOpacity = ta.getFloat(R.styleable.OptionView_pressOpacity, 0.4f)
            if (drawableId != 0) {
                val drawable = AppCompatResources.getDrawable(context, drawableId)
                imageView.setImageDrawable(drawable)
            }
            val space = ta.getInt(R.styleable.OptionView_textSpacing, 8)
            spaceView.layoutParams.width=space
            spaceView.requestLayout();

        } finally {
            ta.recycle()
        }

        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val customClick = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        AccessibilityNodeInfo.ACTION_CLICK, textView.text)
                info.addAction(customClick)
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action: Int = event!!.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                textView.alpha=pressOpacity
                imageView.alpha=pressOpacity
            }
            MotionEvent.ACTION_UP -> {
                textView.alpha = 1.0f
                imageView.alpha=1.0f
            }
            MotionEvent.ACTION_CANCEL -> {

                textView.alpha = 1.0f
                imageView.alpha=1.0f            }
        }
        return true
    }
    fun setText(text:String){
        textView.text = text
    }
    fun setIcon(drawableId:Int){
        if (drawableId != 0) {
            val drawable = AppCompatResources.getDrawable(context, drawableId)
            imageView.setImageDrawable(drawable)
        }
    }
}