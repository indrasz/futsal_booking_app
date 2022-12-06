package org.d3ifcool.Myfutsal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3ifcool.Myfutsal.R
import org.d3ifcool.Myfutsal.data.model.Around
import org.d3ifcool.Myfutsal.data.model.Gallery
import org.d3ifcool.Myfutsal.data.model.Ground

class HomeViewModel : ViewModel(){

    private val aroundData = MutableLiveData<ArrayList<Around>>()
    private val groundData = MutableLiveData<ArrayList<Ground>>()
    private val galleryData = MutableLiveData<ArrayList<Gallery>>()


    init {
        aroundData.value = initAroundData()
        groundData.value = initGroundData()
        galleryData.value = initGalleryData()
    }

    private fun initAroundData(): ArrayList<Around> {
        return arrayListOf(
            Around(R.drawable.img_ground, "Tempat 1"),
            Around(R.drawable.img_ground, "Tempat 2"),
            Around(R.drawable.img_ground, "Tempat 3")
        )
    }

    private fun initGroundData(): ArrayList<Ground> {
        return arrayListOf(
            Ground(R.drawable.img_ground, "Lapangan 1"),
            Ground(R.drawable.img_ground, "Lapangan 2"),
            Ground(R.drawable.img_ground, "Lapangan 3")
        )
    }

    private fun initGalleryData(): ArrayList<Gallery> {
        return arrayListOf(
            Gallery(R.drawable.img_ground, "Gallery 1"),
            Gallery(R.drawable.img_ground, "Gallery 2"),
            Gallery(R.drawable.img_ground, "Gallery 3")
        )
    }
    fun getAroundData(): LiveData<ArrayList<Around>> = aroundData
    fun getGroundData(): LiveData<ArrayList<Ground>> = groundData
    fun getGalleryData(): LiveData<ArrayList<Gallery>> = galleryData


}