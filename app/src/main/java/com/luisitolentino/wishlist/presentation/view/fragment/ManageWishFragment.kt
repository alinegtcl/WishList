package com.luisitolentino.wishlist.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.luisitolentino.wishlist.R
import com.luisitolentino.wishlist.databinding.FragmentManageWishBinding
import com.luisitolentino.wishlist.domain.model.Wish
import com.luisitolentino.wishlist.domain.utils.Constants.EMPTY_STRING
import com.luisitolentino.wishlist.presentation.view.viewmodel.WishManagerState
import com.luisitolentino.wishlist.presentation.view.viewmodel.WishManagerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates


class ManageWishFragment : Fragment() {

    private var _binding: FragmentManageWishBinding? = null
    private val binding get() = _binding!!

    private val args: ManageWishFragmentArgs by navArgs()

    private val viewModel: WishManagerViewModel by viewModel()

    private var isEdit by Delegates.notNull<Boolean>()
    private val statusOptions = listOf("Pendente", "Em andamento", "Realizado")
    private lateinit var wish: Wish
    private var title = EMPTY_STRING
    private var details = EMPTY_STRING
    private var statusSelected = EMPTY_STRING
    private var image = EMPTY_STRING

    private val getContent =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                val bitmap = Glide.with(this@ManageWishFragment).asBitmap().load(uri).submit().get()

                image = bitmap.toString()

                binding.imageWish.setImageBitmap(bitmap)
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
        setupStatusSpinner()
        setupInputListeners()
        setupView(wish)
        setupImageSelection()
        setupSaveWish()
        setupViewModel()
    }

    private fun setupSaveWish() {
        binding.buttonSaveWish.setOnClickListener {
            binding.apply {
                wish = Wish(
                    title, details, statusSelected, image, args.selectedWish?.id ?: 0L
                )
            }
            if (isEdit) {
                // viewModel.update(movie)
            } else {
                viewModel.insert(wish)
            }
        }
    }

    private fun setupInputListeners() {
        binding.editTextWishTitle.addTextChangedListener { wishTitle ->
            if (wishTitle.toString() != EMPTY_STRING) {
                title = wishTitle.toString()
            } else {
                binding.editTextWishTitle.error = "Campo obrigatório"
            }
        }

        binding.editTextWishDetails.addTextChangedListener { wishDetails ->
            details = wishDetails.toString()
        }
    }

    private fun setupView(wish: Wish?) {
        binding.apply {
            if (wish != null) {
                isEdit = true
                editTextWishTitle.setText(wish.title)
                editTextWishDetails.setText(wish.details)

                wish.image?.let { imageUri ->
                    if (imageUri.isNotEmpty()) {
                        Glide.with(this@ManageWishFragment).load(imageUri)
                            .placeholder(R.drawable.ic_placeholder).into(imageWish)
                    }
                }

                buttonSaveWish.text = getString(R.string.label_edit_wish)

                spinnerWishStatus.setSelection(statusOptions.indexOf(wish.status))
            } else {
                isEdit = false
                (activity as? AppCompatActivity)?.supportActionBar?.title =
                    getString(R.string.label_add_wish)
                editTextWishTitle.isEnabled = true
                buttonSaveWish.text = getString(R.string.label_add_wish)
                spinnerWishStatus.setSelection(0)
            }
        }
    }

    private fun setupStatusSpinner() {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, statusOptions
        )
        binding.spinnerWishStatus.setAdapter(adapter)
        binding.spinnerWishStatus.itemSelected { genre ->
            statusOptions.forEach {
                if (it == genre) statusSelected = it
            }
        }
    }

    private fun Spinner.itemSelected(action: (progress: String) -> Unit) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                action(parent?.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

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

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.stateManagement.collect { state ->
                when (state) {
                    is WishManagerState.Failure -> {
                        Toast.makeText(
                            requireContext(), "$state.errorMessage", Toast.LENGTH_SHORT
                        ).show()
                    }

                    WishManagerState.HideLoading -> binding.loadingWishList.visibility = View.GONE
                    WishManagerState.InsertSuccess -> {
                        Toast.makeText(
                            requireContext(), "Desejo inserido com sucesso", Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    WishManagerState.ShowLoading -> binding.loadingWishList.visibility =
                        View.VISIBLE

                    WishManagerState.UpdateSuccess -> {
                        Toast.makeText(
                            requireContext(), "Desejo atualizado com sucesso", Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
