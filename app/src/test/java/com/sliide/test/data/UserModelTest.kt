package com.sliide.test.data

import com.sliide.test.data.model.UserModel
import com.sliide.test.data.model.toUser
import com.sliide.test.domain.entity.UserEntity
import org.junit.Assert
import org.junit.Test


class UserModelTest {
    private val tEmail = "tahamalas@live.com"
    private val tName = "Taha Malas"
    private val tGender = "Male"
    private val tStatus = "active"
    private val tUserID = 1
    private val tUserModel = UserModel(tEmail, tGender, tUserID, tName, tStatus)
    private val tUserEntity = UserEntity(tEmail, tUserID, tName)

    @Test
    fun `user model to user entity conversion`() {
        val user = tUserModel.toUser()
        Assert.assertEquals(tUserEntity, user)
    }

}