package com.luisitolentino.wishlist.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.luisitolentino.wishlist.R
import com.luisitolentino.wishlist.databinding.FragmentManageWishBinding
import com.luisitolentino.wishlist.domain.entities.Wish

class ManageWishFragment : Fragment() {

    private var _binding: FragmentManageWishBinding? = null
    private val binding get() = _binding!!

    private val args: ManageWishFragmentArgs by navArgs()

    private val viewModel: WishManagerViewModel by viewModels()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                Glide.with(this@ManageWishFragment)
                    .load(uri)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.imageWish)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageWishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wish = args.selectedWish
        setupView(wish)
        setupImageSelection()
        binding.buttonSaveWish.setOnClickListener {
            // Lógica para salvar ou atualizar o desejo
        }
    }

    private fun setupView(wish: Wish?) {
        binding.apply {
            if (wish != null) {
                textWishTitle.setText(wish.title)
                editTextWishDetails.setText(wish.details)

                wish.image?.let { imageUri ->
                    if (imageUri.isNotEmpty()) {
                        Glide.with(this@ManageWishFragment).load(imageUri)
                            .placeholder(R.drawable.ic_placeholder).into(imageWish)
                    }
                }

                buttonSaveWish.text = getString(R.string.label_edit_wish)
            } else {
                (activity as? AppCompatActivity)?.supportActionBar?.title =
                    getString(R.string.label_add_wish)
                textWishTitle.setText(getString(R.string.label_add_wish))
                buttonSaveWish.text = getString(R.string.label_add_wish)
            }

            val statusOptions = resources.getStringArray(R.array.wish_status_options)
            val adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_dropdown_item_1line, statusOptions
            )
            spinnerWishStatus.setAdapter(adapter)
        }
    }

    private fun setupImageSelection() {
        binding.imageWish.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        getContent.launch(arrayOf("image/*"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
