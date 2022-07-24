package ru.netology.nework.data

import ru.netology.nework.data.remote.AttachmentRemote
import ru.netology.nework.data.remote.PostRemote
import ru.netology.nework.data.remote.TokenRemote
import ru.netology.nework.data.remote.UserRemote
import ru.netology.nework.domain.Mapper
import ru.netology.nework.model.attachment.Attachment
import ru.netology.nework.model.attachment.AttachmentType
import ru.netology.nework.model.post.Post
import ru.netology.nework.model.user.Token
import ru.netology.nework.model.user.User

class TokenMapper : Mapper<TokenRemote, Token> {
    override fun transform(data: TokenRemote) = Token(
        id = data.id,
        token = data.token
    )
}

class UserMapper : Mapper<UserRemote, User> {
    override fun transform(data: UserRemote) = User(
        id = data.id,
        login = data.login,
        name = data.name,
        avatar = data.avatar.orEmpty(),
    )
}

class AttachmentToDtoMapper : Mapper<AttachmentRemote?, Attachment?> {
    override fun transform(data: AttachmentRemote?): Attachment? {
        return data?.let {
            Attachment(
                url = it.url,
                type = AttachmentType.valueOf(it.type.uppercase())
            )
        }
    }
}

class PostToDtoMapper : Mapper<PostRemote, Post> {
    override fun transform(data: PostRemote) = Post(
        id = data.id,
        authorId = data.authorId,
        authorAvatar = data.authorAvatar.orEmpty(),
        content = data.content,
        published = data.published,
        coords = data.coords,
        link = data.link,
        likeOwnerIds = data.likeOwnerIds,
        mentionIds = data.mentionIds,
        mentionedMe = data.mentionedMe,
        likedByMe = data.likedByMe,
        attachment = AttachmentToDtoMapper().transform(data.attachment),
        ownedByMe = data.ownedByMe
    )
}

