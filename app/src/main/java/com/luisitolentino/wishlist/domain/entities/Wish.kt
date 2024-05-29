package com.luisitolentino.wishlist.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wish(
    val id: String, // Um identificador único para o desejo
    val title: String, // O título do desejo
    val details: String, // Detalhes adicionais sobre o desejo
    val status: String, // O status atual do desejo (por exemplo, pendente, realizado, em andamento, etc.)
    val image: String? // Uma URL ou URI da imagem associada ao desejo (opcional)
) : Parcelable