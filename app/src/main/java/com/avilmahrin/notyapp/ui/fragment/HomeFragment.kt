package com.avilmahrin.notyapp.ui.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.renderscript.Allocation
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur
import com.avilmahrin.notyapp.R
import com.avilmahrin.notyapp.adapter.NoteAdapter
import com.avilmahrin.notyapp.databinding.FragmentHomeBinding
import com.avilmahrin.notyapp.viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            binding.recAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recAllNotes.adapter = NoteAdapter(requireContext(), notesList)
        })

        binding.btnAdd.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNoteFragment)
        }

        // Set OnClickListener untuk tombol back
        binding.toolbar.root.findViewById<FrameLayout>(R.id.backbutton).setOnClickListener {
            findNavController().navigateUp()
        }

        // Tambahkan kode ini untuk membuat efek blur
        // binding.imageView2.post {
        // val bitmap = (binding.imageView2.drawable as BitmapDrawable).bitmap
        //    val blurredBitmap = blurBitmapRepeatedly(bitmap, 25f, 3) // Apply blur 3 times
        //    binding.imageView2.setImageBitmap(blurredBitmap)
        // }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun blurBitmap(bitmap: Bitmap, radius: Float): Bitmap {
        val outputBitmap = Bitmap.createBitmap(bitmap)
        val renderScript = RenderScript.create(requireContext())
        val blurInput = Allocation.createFromBitmap(renderScript, bitmap)
        val blurOutput = Allocation.createFromBitmap(renderScript, outputBitmap)

        val blur = ScriptIntrinsicBlur.create(renderScript, blurInput.element)
        blur.setInput(blurInput)
        blur.setRadius(radius)
        blur.forEach(blurOutput)
        blurOutput.copyTo(outputBitmap)

        return outputBitmap
    }

    private fun blurBitmapRepeatedly(bitmap: Bitmap, radius: Float, iterations: Int): Bitmap {
        var blurredBitmap = bitmap
        for (i in 0 until iterations) {
            blurredBitmap = blurBitmap(blurredBitmap, radius)
        }
        return blurredBitmap
    }
}
