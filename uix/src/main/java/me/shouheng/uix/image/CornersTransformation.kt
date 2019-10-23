package me.shouheng.uix.image

import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.BOTTOM_LEFT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.BOTTOM_RIGHT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.DIAGONAL_FROM_TOP_LEFT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.DIAGONAL_FROM_TOP_RIGHT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.OTHER_BOTTOM_LEFT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.OTHER_BOTTOM_RIGHT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.OTHER_TOP_LEFT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.OTHER_TOP_RIGHT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.TOP_LEFT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.TOP_RIGHT
import android.graphics.*
import android.support.annotation.IntDef
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.ALL
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.BOTTOM
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.LEFT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.RIGHT
import me.shouheng.uix.image.CornersTransformation.CornerType.Companion.TOP
import java.security.MessageDigest

/**
 * Glide image corner transformation.
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019/10/1 10:14
 */
class CornersTransformation @JvmOverloads constructor(
    private val radius: Int,
    private val margin: Int,
    @param:CornerType @field:CornerType private val cornerType: Int = ALL
) : BitmapTransformation() {
    private val diameter: Int = this.radius * 2

    @IntDef(value = [ALL, TOP, TOP_LEFT, TOP_RIGHT, BOTTOM, BOTTOM_LEFT,
        BOTTOM_RIGHT, LEFT, RIGHT, OTHER_TOP_LEFT, OTHER_TOP_RIGHT,
        OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT, DIAGONAL_FROM_TOP_LEFT,
        DIAGONAL_FROM_TOP_RIGHT])
    @Retention
    @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    annotation class CornerType {
        companion object {
            const val ALL = 0
            const val TOP = 1
            const val TOP_LEFT = 2
            const val TOP_RIGHT = 3
            const val BOTTOM = 4
            const val BOTTOM_LEFT = 5
            const val BOTTOM_RIGHT = 6
            const val LEFT = 7
            const val RIGHT = 8
            const val OTHER_TOP_LEFT = 9
            const val OTHER_TOP_RIGHT = 10
            const val OTHER_BOTTOM_LEFT = 11
            const val OTHER_BOTTOM_RIGHT = 12
            const val DIAGONAL_FROM_TOP_LEFT = 13
            const val DIAGONAL_FROM_TOP_RIGHT = 14
        }
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setHasAlpha(true)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

    private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        val right = width - margin
        val bottom = height - margin

        when (cornerType) {
            ALL -> canvas.drawRoundRect(
                RectF(margin.toFloat(), margin.toFloat(), right, bottom),
                radius.toFloat(),
                radius.toFloat(),
                paint
            )
            TOP_LEFT -> drawTopLeftRoundRect(canvas, paint, right, bottom)
            TOP_RIGHT -> drawTopRightRoundRect(canvas, paint, right, bottom)
            BOTTOM_LEFT -> drawBottomLeftRoundRect(canvas, paint, right, bottom)
            BOTTOM_RIGHT -> drawBottomRightRoundRect(canvas, paint, right, bottom)
            TOP -> drawTopRoundRect(canvas, paint, right, bottom)
            BOTTOM -> drawBottomRoundRect(canvas, paint, right, bottom)
            LEFT -> drawLeftRoundRect(canvas, paint, right, bottom)
            RIGHT -> drawRightRoundRect(canvas, paint, right, bottom)
            OTHER_TOP_LEFT -> drawOtherTopLeftRoundRect(canvas, paint, right, bottom)
            OTHER_TOP_RIGHT -> drawOtherTopRightRoundRect(canvas, paint, right, bottom)
            OTHER_BOTTOM_LEFT -> drawOtherBottomLeftRoundRect(canvas, paint, right, bottom)
            OTHER_BOTTOM_RIGHT -> drawOtherBottomRightRoundRect(canvas, paint, right, bottom)
            DIAGONAL_FROM_TOP_LEFT -> drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom)
            DIAGONAL_FROM_TOP_RIGHT -> drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom)
            else -> canvas.drawRoundRect(
                RectF(margin.toFloat(), margin.toFloat(), right, bottom),
                radius.toFloat(),
                radius.toFloat(),
                paint
            )
        }
    }

    private fun drawTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(
                margin.toFloat(),
                margin.toFloat(),
                (margin + diameter).toFloat(),
                (margin + diameter).toFloat()
            ), radius.toFloat(), radius.toFloat(), paint
        )
        canvas.drawRect(
            RectF(margin.toFloat(), (margin + radius).toFloat(), (margin + radius).toFloat(), bottom),
            paint
        )
        canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom), paint)
    }

    private fun drawTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - diameter, margin.toFloat(), right, (margin + diameter).toFloat()),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom), paint)
        canvas.drawRect(RectF(right - radius, (margin + radius).toFloat(), right, bottom), paint)
    }

    private fun drawBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), bottom - diameter, (margin + diameter).toFloat(), bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(
            RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom - radius),
            paint
        )
        canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom), paint)
    }

    private fun drawBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - diameter, bottom - diameter, right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom), paint)
        canvas.drawRect(RectF(right - radius, margin.toFloat(), right, bottom - radius), paint)
    }

    private fun drawTopRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), margin.toFloat(), right, (margin + diameter).toFloat()),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), right, bottom), paint)
    }

    private fun drawBottomRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), bottom - diameter, right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom - radius), paint)
    }

    private fun drawLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom), paint)
    }

    private fun drawRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - diameter, margin.toFloat(), right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom), paint)
    }

    private fun drawOtherTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), bottom - diameter, right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRoundRect(
            RectF(right - diameter, margin.toFloat(), right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom - radius), paint)
    }

    private fun drawOtherTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRoundRect(
            RectF(margin.toFloat(), bottom - diameter, right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF((margin + radius).toFloat(), margin.toFloat(), right, bottom - radius), paint)
    }

    private fun drawOtherBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), margin.toFloat(), right, (margin + diameter).toFloat()),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRoundRect(
            RectF(right - diameter, margin.toFloat(), right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), right - radius, bottom), paint)
    }

    private fun drawOtherBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(margin.toFloat(), margin.toFloat(), right, (margin + diameter).toFloat()),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRoundRect(
            RectF(margin.toFloat(), margin.toFloat(), (margin + diameter).toFloat(), bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF((margin + radius).toFloat(), (margin + radius).toFloat(), right, bottom), paint)
    }

    private fun drawDiagonalFromTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(
                margin.toFloat(),
                margin.toFloat(),
                (margin + diameter).toFloat(),
                (margin + diameter).toFloat()
            ), radius.toFloat(), radius.toFloat(), paint
        )
        canvas.drawRoundRect(
            RectF(right - diameter, bottom - diameter, right, bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), (margin + radius).toFloat(), right - diameter, bottom), paint)
        canvas.drawRect(RectF((margin + diameter).toFloat(), margin.toFloat(), right, bottom - radius), paint)
    }

    private fun drawDiagonalFromTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - diameter, margin.toFloat(), right, (margin + diameter).toFloat()),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRoundRect(
            RectF(margin.toFloat(), bottom - diameter, (margin + diameter).toFloat(), bottom),
            radius.toFloat(),
            radius.toFloat(),
            paint
        )
        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), right - radius, bottom - radius), paint)
        canvas.drawRect(RectF((margin + radius).toFloat(), (margin + radius).toFloat(), right, bottom), paint)
    }
}

