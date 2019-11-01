package com.example.safe.moudule

import android.content.Context
import androidx.room.Room
import com.example.safe.contract.MainContract
import com.example.safe.db.entity.UserDataBase
import com.example.safe.db.repository.UserRepo
import com.example.safe.db.repository.UserRepository
import com.example.safe.model.MainModel
import com.example.safe.utils.ActivityScope
import dagger.Module
import dagger.Provides


/**
 * Created by hy on 2019/10/30
 */
@Module
class MainModule(private val view: MainContract.View,private val context: Context) {

    @ActivityScope
    @Provides
    internal fun provideModel(model: MainModel): MainContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideCommentView(): MainContract.View {
        return this.view
    }

    @Provides
    @ActivityScope
    internal fun provideContext(): Context = context

    @Provides
    @ActivityScope
    internal fun provideAppDatabase(context: Context): UserDataBase =
        Room.databaseBuilder(context, UserDataBase::class.java, "safe.class.db").build()

    @Provides
    @ActivityScope
    internal fun provideQuestionRepoHelper(appDatabase: UserDataBase): UserRepo = UserRepository(appDatabase.searchUserDao())

}