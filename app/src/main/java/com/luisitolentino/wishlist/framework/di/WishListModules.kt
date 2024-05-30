package com.luisitolentino.wishlist.framework.di

import androidx.room.Room
import com.luisitolentino.wishlist.data.repository.WishManagerRepositoryImpl
import com.luisitolentino.wishlist.domain.usecase.WishManagerUseCase
import com.luisitolentino.wishlist.framework.datasource.WishesManagerDatabase
import com.luisitolentino.wishlist.presentation.view.viewmodel.WishManagerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                WishesManagerDatabase::class.java,
                WishesManagerDatabase.WISHES_MANAGER_DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<WishesManagerDatabase>().wishesDao() }
    factory<WishManagerUseCase> { WishManagerRepositoryImpl(get()) }
    viewModel { WishManagerViewModel(get()) }
}