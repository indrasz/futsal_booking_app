package org.d3ifcool.Myfutsal.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.Around
import org.d3ifcool.Myfutsal.data.model.Gallery
import org.d3ifcool.Myfutsal.data.model.Ground
import org.d3ifcool.Myfutsal.databinding.FragmentHomeBinding
import org.d3ifcool.Myfutsal.ui.home.adapter.AroundAdapter
import org.d3ifcool.Myfutsal.ui.home.adapter.GalleryAdapter
import org.d3ifcool.Myfutsal.ui.home.adapter.GroundAdapter

class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding

    //adapter lapangan
    private lateinit var groundAdapter: GroundAdapter
    private val arrayOfGround = arrayListOf<Ground>()

    //adapter tempat sekitar
    private lateinit var aroundAdapter: AroundAdapter
    private val arrayOfAround = arrayListOf<Around>()

    //adapter gallery
    private lateinit var galleryAdapter: GalleryAdapter
    private val arrayOfGallery = arrayListOf<Gallery>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayListGround()
        displayListAround()
        displayListGallery()
    }

    @SuppressLint("Recycle")
    private fun displayListGround(){
        val imageArray = resources.obtainTypedArray(R.array.ground_image_array)
        val nameArray = resources.getStringArray(R.array.ground_name_array)

        for (i in nameArray.indices) arrayOfGround.add(
            Ground(
                image = imageArray.getResourceId(i, 0),
                name = nameArray[i],
            )
        )
        groundAdapter = GroundAdapter(arrayOfGround) {
            Snackbar.make(
                binding.root,
                "${it.name}",
                Snackbar.LENGTH_SHORT,

                ).show()
        }
        with(binding.rvGround) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = groundAdapter
        }
    }

    @SuppressLint("Recycle")
    private fun displayListAround(){
        val imageArray = resources.obtainTypedArray(R.array.around_image_array)
        val nameArray = resources.getStringArray(R.array.around_name_array)

        for (i in nameArray.indices) arrayOfAround.add(
            Around(
                image = imageArray.getResourceId(i, 0),
                name = nameArray[i],
            )
        )
        aroundAdapter = AroundAdapter(arrayOfAround) {
            Snackbar.make(
                binding.root,
                "${it.name}",
                Snackbar.LENGTH_SHORT,

                ).show()
        }
        with(binding.rvAround) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = aroundAdapter
        }
    }

    @SuppressLint("Recycle")
    private fun displayListGallery(){
        val imageArray = resources.obtainTypedArray(R.array.gallery_image_array)
        val nameArray = resources.getStringArray(R.array.gallery_name_array)

        for (i in nameArray.indices) arrayOfGallery.add(
            Gallery(
                image = imageArray.getResourceId(i, 0),
                name = nameArray[i],
            )
        )
        galleryAdapter = GalleryAdapter(arrayOfGallery) {
            Snackbar.make(
                binding.root,
                "${it.name}",
                Snackbar.LENGTH_SHORT,

                ).show()
        }
        with(binding.rvGallery) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = galleryAdapter
        }
    }

}