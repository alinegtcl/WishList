package com.luisitolentino.wishlist.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.luisitolentino.wishlist.databinding.FragmentWishListBinding
import com.luisitolentino.wishlist.domain.model.Wish
import com.luisitolentino.wishlist.presentation.view.adapter.WishAdapter
import com.luisitolentino.wishlist.presentation.view.viewmodel.WishManagerViewModel
import com.luisitolentino.wishlist.presentation.view.viewmodel.WishState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishListFragment : Fragment() {

    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WishManagerViewModel by viewModel()

    private lateinit var wishAdapter: WishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllWishes()
        setupViewModel()
        setupAddButton()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.stateList.collect {
                when (it) {
                    WishState.EmptyState -> binding.textEmptyState.visibility = View.VISIBLE
                    is WishState.Failure -> {} // Lógica para tratamento de falhas
                    WishState.HideLoading -> binding.loadingWishList.visibility = View.GONE
                    WishState.ShowLoading -> binding.loadingWishList.visibility = View.VISIBLE
                    is WishState.SearchAllSuccess -> setupRecyclerView(it.wishes)
                }
            }
        }
    }

    private fun setupRecyclerView(wishes: List<Wish>) {
        wishAdapter = WishAdapter(::onWishClick, ::onMenuItemClick).apply { submitList(wishes) }
        binding.recyclerWishList.adapter = wishAdapter
    }

    private fun onWishClick(wish: Wish) {
        val action = WishListFragmentDirections.actionWishListFragmentToDetailWishFragment(wish)
        findNavController().navigate(action)
    }

    private fun onMenuItemClick(wish: Wish) {
        val action = WishListFragmentDirections.actionWishListFragmentToManageWishFragment(wish)
        findNavController().navigate(action)
    }

    private fun setupAddButton() {
        binding.buttonAddNewWish.setOnClickListener {
            val action = WishListFragmentDirections.actionWishListFragmentToManageWishFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
