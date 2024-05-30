package com.luisitolentino.wishlist.domain.utils

sealed class WishResult<out S, out E> {
    data class Success<out S>(val data: S) : WishResult<S, Nothing>()
    data class Error<out E>(val failure: E) : WishResult<Nothing, E>()
}

inline fun <S, E, R> WishResult<S, E>.flow(
    success: (S) -> R,
    error: (E) -> R
): R {
    return when (this) {
        is WishResult.Success -> success(data)
        is WishResult.Error -> error(failure)
    }
}