package com.luisitolentino.wishlist.framework.di

import com.luisitolentino.wishlist.presentation.view.WishManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { WishManagerViewModel() }
}