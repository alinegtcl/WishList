package com.luisitolentino.wishlist.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.luisitolentino.wishlist.R
import com.luisitolentino.wishlist.databinding.FragmentDetailWishBinding

class DetailWishFragment : Fragment() {
    private var _binding: FragmentDetailWishBinding? = null
    private val binding get() = _binding!!

    private val args: DetailWishFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Detalhes do Desejo"

        _binding = FragmentDetailWishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.apply {
            textWishTitle.text = args.selectedWish.title
            textWishDetails.text = args.selectedWish.details
            textWishStatus.text = args.selectedWish.status

            if (args.selectedWish.image.isNullOrEmpty()) {
                imageWish.setImageResource(R.drawable.ic_placeholder)
            } else {
                Glide.with(this@DetailWishFragment).load(args.selectedWish.image)
                    .placeholder(R.drawable.ic_placeholder).into(imageWish)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}