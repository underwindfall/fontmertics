/**
 * Copyright (C) 2021 by Qifan YANG (@underwindfall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qifan.fontmetrics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * an example to introduce u how to use font metrics
 */
class FontMetricsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // pixel size
    private val DEFAULT_TEXT_SIZE = 200
    private val STROKE_SIZE = 5F

    private var mText: String = "Test text line"
    private var textSize: Float = DEFAULT_TEXT_SIZE.toFloat()
    private val mAscentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTopPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBaselinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDescentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mMeasuredWidthPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextBoundsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBounds = Rect()

    var mIsTopVisible = true
        private set
    var mIsAscentVisible = true
        private set
    var mIsBaselineVisible = true
        private set
    var mIsDescentVisible = true
        private set
    var mIsBottomVisible = true
        private set
    var mIsBoundsVisible = true
        private set
    var mIsWidthVisible = true
        private set

    init {
        mTextPaint.color = Color.BLACK
        mTextPaint.textSize = textSize

        mLinePaint.color = Color.RED
        mLinePaint.strokeWidth = STROKE_SIZE

        mAscentPaint.color = Color.CYAN
        mAscentPaint.strokeWidth = STROKE_SIZE

        mTopPaint.color = Color.GREEN
        mTopPaint.strokeWidth = STROKE_SIZE

        mBaselinePaint.color = Color.MAGENTA
        mBaselinePaint.strokeWidth = STROKE_SIZE

        mBottomPaint.color = Color.GRAY
        mBottomPaint.strokeWidth = STROKE_SIZE

        mDescentPaint.color = Color.YELLOW
        mDescentPaint.strokeWidth = STROKE_SIZE

        mMeasuredWidthPaint.color = Color.YELLOW
        mMeasuredWidthPaint.strokeWidth = STROKE_SIZE

        mTextBoundsPaint.color = Color.BLUE
        mTextBoundsPaint.strokeWidth = STROKE_SIZE
        mTextBoundsPaint.style = Paint.Style.STROKE

        mRectPaint.color = Color.BLACK
        mRectPaint.strokeWidth = STROKE_SIZE
        mRectPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // center the text baseline vertically
        val verticalAdjustment = this.height / 2f
        canvas.translate(0f, verticalAdjustment)

        var startX = paddingLeft.toFloat()
        var startY = 0f
        var stopX = measuredWidth.toFloat()
        var stopY = 0f

        // draw text
        canvas.drawText(mText, startX, startY, mTextPaint) // x=0, y=0

        // draw lines
        startX = 0f
        if (mIsTopVisible) {
            startY = mTextPaint.fontMetrics.top
            stopY = startY
            canvas.drawLine(startX, startY, stopX, stopY, mTopPaint)
        }

        if (mIsAscentVisible) {
            startY = mTextPaint.fontMetrics.ascent
            stopY = startY
            canvas.drawLine(startX, startY, stopX, stopY, mAscentPaint)
        }

        if (mIsBaselineVisible) {
            startY = 0F
            stopY = startY
            canvas.drawLine(startX, startY, stopX, stopY, mBaselinePaint)
        }

        if (mIsDescentVisible) {
            startY = mTextPaint.fontMetrics.descent
            stopY = startY
            canvas.drawLine(startX, startY, stopX, stopY, mDescentPaint)
        }

        if (mIsBottomVisible) {
            startY = mTextPaint.fontMetrics.bottom
            stopY = startY
            mLinePaint.color = Color.RED
            canvas.drawLine(startX, startY, stopX, stopY, mBaselinePaint)
        }

        if (mIsBoundsVisible) {

            mTextPaint.getTextBounds(mText, 0, mText.length, mBounds)
            val dx = paddingLeft.toFloat()
            canvas.drawRect(
                mBounds.left + dx,
                mBounds.top.toFloat(),
                mBounds.right + dx,
                mBounds.bottom.toFloat(),
                mTextBoundsPaint
            )
        }

        if (mIsWidthVisible) {
            // get measured width
            val width = mTextPaint.measureText(mText)
            // get bounding with so that we can compare them
            mTextPaint.getTextBounds(mText, 0, mText.length, mBounds)
            // draw vertical line just before the left bounds
            // TODO need to fix this ?
            startX = paddingLeft + mBounds.left - (width - mBounds.left) / 2
            stopX = startX
            startY = -verticalAdjustment
            stopY = startY + height
            canvas.drawLine(startX, startY, stopX, stopY, mMeasuredWidthPaint)
            // draw vertical line just after the right bounds
            startX += width
            stopX = startX
            canvas.drawLine(startX, startY, stopX, stopY, mMeasuredWidthPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = 200
        val height = 200
        val desiredWidth = resolveSize(width, widthMeasureSpec)
        val desiredHeight = resolveSize(height, heightMeasureSpec)
        setMeasuredDimension(desiredWidth, desiredHeight)
    }
}
