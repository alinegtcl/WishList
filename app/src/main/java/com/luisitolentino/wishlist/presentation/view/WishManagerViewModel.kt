package com.luisitolentino.wishlist.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.wishlist.domain.entities.Wish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishManagerViewModel : ViewModel() {

    private val _stateList = MutableStateFlow<WishState>(WishState.EmptyState)
    val stateList: StateFlow<WishState> = _stateList

    val sampleWishes = listOf(
        Wish(
            id = "1",
            title = "Comprar uma casa",
            details = "Uma casa grande com um jardim bonito.",
            status = "Pendente",
            image = "https://example.com/images/house.jpg"
        ), Wish(
            id = "2",
            title = "Viajar para o Japão",
            details = "Explorar Tóquio, Quioto e Hiroshima.",
            status = "Em andamento",
            image = "https://example.com/images/japan.jpg"
        ), Wish(
            id = "3",
            title = "Aprender a tocar piano",
            details = "Ter aulas semanais e praticar diariamente.",
            status = "Pendente",
            image = "https://example.com/images/piano.jpg"
        ), Wish(
            id = "4",
            title = "Ler 50 livros em um ano",
            details = "Focar em leituras de ficção e não-ficção.",
            status = "Realizado",
            image = "https://example.com/images/books.jpg"
        ), Wish(
            id = "5",
            title = "Fazer um curso de culinária",
            details = "Especializar-se em culinária italiana.",
            status = "Em andamento",
            image = "https://example.com/images/cooking.jpg"
        ), Wish(
            id = "6",
            title = "Correr uma maratona",
            details = "Treinar diariamente para completar uma maratona.",
            status = "Pendente",
            image = "https://example.com/images/marathon.jpg"
        ), Wish(
            id = "7",
            title = "Aprender uma nova língua",
            details = "Estudar espanhol até atingir a fluência.",
            status = "Em andamento",
            image = "https://example.com/images/spanish.jpg"
        ), Wish(
            id = "8",
            title = "Fazer um mochilão pela Europa",
            details = "Visitar vários países da Europa em um mochilão.",
            status = "Pendente",
            image = "https://example.com/images/europe.jpg"
        ), Wish(
            id = "9",
            title = "Meditar diariamente",
            details = "Estabelecer uma rotina de meditação diária.",
            status = "Realizado",
            image = "https://example.com/images/meditation.jpg"
        ), Wish(
            id = "10",
            title = "Escrever um livro",
            details = "Completar a escrita de um romance.",
            status = "Em andamento",
            image = "https://example.com/images/writing.jpg"
        )
    )

    fun getAllWishes() {
        viewModelScope.launch {
            _stateList.value = WishState.ShowLoading
            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
            _stateList.value = WishState.HideLoading
            _stateList.value = WishState.SearchAllSuccess(sampleWishes)
        }
    }

}
