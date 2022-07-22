package ru.netology.nework.data

import ru.netology.nework.data.remote.AttachmentRemote
import ru.netology.nework.domain.Mapper
import ru.netology.nework.model.attachment.Attachment
import ru.netology.nework.model.attachment.AttachmentType

class AttachmentMapper : Mapper<AttachmentRemote, Attachment> {
    override fun transform(data: AttachmentRemote) = Attachment(
        url = data.url,
        type = AttachmentType.valueOf(data.type.uppercase())
    )
}