package ru.netology.nework.data

import ru.netology.nework.data.remote.UserRemote
import ru.netology.nework.domain.Mapper
import ru.netology.nework.model.user.User

class UserMapper : Mapper<UserRemote, User> {
    override fun transform(data: UserRemote) = User(
        id = data.id,
        login = data.login,
        name = data.name,
        avatar = data.avatar.orEmpty(),
    )
}