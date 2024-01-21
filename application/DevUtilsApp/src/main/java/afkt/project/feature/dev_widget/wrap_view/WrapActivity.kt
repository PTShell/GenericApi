package afkt.project.feature.dev_widget.wrap_view

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityWrapBinding
import afkt.project.model.item.RouterPath
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.therouter.router.Route
import dev.base.widget.BaseTextView
import dev.utils.app.ResourceUtils
import dev.utils.app.ShapeUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 自动换行 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.WrapActivity_PATH)
class WrapActivity : BaseActivity<ActivityWrapBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_wrap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = QuickHelper.get(BaseTextView(this))
            .setText("刷新")
            .setBold()
            .setTextColors(ResourceUtils.getColor(R.color.red))
            .setTextSizeBySp(15.0F)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClick { initValue() }.getView<View>()
        toolbar?.addView(view)
    }

    override fun initValue() {
        super.initValue()
        binding.vidWrap
//            // 设置最大行数
//            .setMaxLine(RandomUtils.getRandom(10, 30))
//            // 设置每一行向上的边距 ( 行间隔 )
//            .setRowTopMargin(30)
//            // 设置每个 View 之间的 Left 边距
//            .setViewLeftMargin(30)
            // 快捷设置两个边距
            .setRowViewMargin(30, 30)
            .removeAllViews()

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // 设置点击效果
        val drawable = ShapeUtils.newShape(30F, ResourceUtils.getColor(R.color.color_88)).drawable
        for (i in 1..20) {
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(7)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(5))
            val randomText =
                i.toString() + "." + RandomUtils.getRandom(text.toCharArray(), text.length)
            binding.vidWrap.addView(createView(randomText, layoutParams, drawable))
        }
    }

    private fun createView(
        text: String,
        layoutParams: ViewGroup.LayoutParams,
        drawable: GradientDrawable
    ): BaseTextView {
        return QuickHelper.get(BaseTextView(this))
            .setLayoutParams(layoutParams)
            .setPadding(30, 15, 30, 15)
            .setBackground(drawable)
            .setMaxLines(1)
            .setEllipsize(TextUtils.TruncateAt.END)
            .setTextColors(Color.WHITE)
            .setTextSizeBySp(15F)
            .setBold(RandomUtils.nextBoolean())
            .setText(text)
            .getView()
    }
}