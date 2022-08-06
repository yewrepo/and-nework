package ru.netology.nework.app.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.netology.nework.app.loadUrl
import ru.netology.nework.databinding.ViewAttachmentBinding
import ru.netology.nework.model.attachment.Attachment
import ru.netology.nework.model.attachment.AttachmentType

class AttachmentView @JvmOverloads constructor(
    c: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(c, attrs, defStyleAttr, defStyleRes) {

    private var binding = ViewAttachmentBinding.inflate(LayoutInflater.from(c), this, true)

    fun setAttachment(attachment: Attachment?) {
        binding.root.isVisible = attachment != null
        attachment?.apply {
            when (attachment.type) {
                AttachmentType.IMAGE -> showImage(this)
                else -> showOthers(this)
            }
        }
    }

    private fun showImage(attachment: Attachment) {
        binding.link.isVisible = false
        binding.image.loadUrl(attachment.url)
    }

    private fun showOthers(attachment: Attachment) {
        binding.link.isVisible = true
        binding.link.text = attachment.url
    }
}