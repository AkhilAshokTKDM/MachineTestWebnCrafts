package com.akhil.mt_webncrafts.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.akhil.mt_webncrafts.models.api.ApiConstants
import com.akhil.mt_webncrafts.ui.adapters.CategoryAdapter
import com.akhil.mt_webncrafts.DashboardActivity
import com.akhil.mt_webncrafts.R
import com.akhil.mt_webncrafts.databinding.FragmentHomeBinding
import com.akhil.mt_webncrafts.models.data.AllData
import com.akhil.mt_webncrafts.models.room_db.ApiResponseData
import com.akhil.mt_webncrafts.ui.adapters.ImageSliderAdapter
import com.akhil.mt_webncrafts.ui.adapters.ProductAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator
import java.lang.Exception


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private var dataList : List<AllData> =  listOf<AllData>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if ((activity as DashboardActivity).isNetworkAvailable((activity as DashboardActivity).applicationContext)) {
            homeViewModel.getHomeData()
        } else {
            homeViewModel.getApiResponseFromDb(ApiConstants.get_home_data).observe(viewLifecycleOwner, Observer {
                try {
                    dataList =  listOf<AllData>()
                    dataList = GsonBuilder().create().fromJson(it[0].apiResponse, Array<AllData>::class.java).toList()
                    binding.llContents.removeAllViews()
                    showUi(dataList)
                } catch (e: Exception){
                    Toast.makeText(
                        requireParentFragment().context,
                        "You are offline now.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }

        handleObservers()

    }

    private fun getAptView(allData: AllData, llContents: LinearLayout) {
        if (allData.type == "banner_slider" && allData.title == "Top banner" ) {
            val view = LayoutInflater.from(context).inflate(R.layout.image_slider, null)
            val viewPager : ViewPager = view.findViewById(R.id.imageSlider) as ViewPager
            val imageSliderAdapter : ImageSliderAdapter = ImageSliderAdapter(requireContext(), allData.contents)
            viewPager.adapter = imageSliderAdapter
            (view.findViewById(R.id.indicator) as CircleIndicator).setViewPager(viewPager)
            llContents.addView(view)
        }
        if (allData.type == "products" && allData.title == "Best Sellers" ) {
            val view = LayoutInflater.from(context).inflate(R.layout.products_view, null)
            val productsRecyclerView : RecyclerView = view.findViewById(R.id.rv_products) as RecyclerView
            val txtTitle : TextView = view.findViewById(R.id.txt_title) as TextView
            txtTitle.text = allData.title
            productsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ProductAdapter(allData.contents)
            }
            llContents.addView(view)
        }

        if (allData.type == "banner_single" && allData.title == "ad banner" ) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_single_image, null)
            val ivSingleBanner : SimpleDraweeView = view.findViewById(R.id.iv_single_banner) as SimpleDraweeView
            ivSingleBanner.setImageURI(allData.imageUrl)
            llContents.addView(view)
        }
        if (allData.type == "catagories" && allData.title == "Top categories" ) {
            val view = LayoutInflater.from(context).inflate(R.layout.products_view, null)
            val productsRecyclerView : RecyclerView = view.findViewById(R.id.rv_products) as RecyclerView
            val txtTitle : TextView = view.findViewById(R.id.txt_title) as TextView
            txtTitle.text = allData.title
            productsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = CategoryAdapter(allData.contents)
            }
            llContents.addView(view)
        }

        if (allData.type == "products" && allData.title == "Most Popular" ) {
            val view = LayoutInflater.from(context).inflate(R.layout.products_view, null)
            val productsRecyclerView : RecyclerView = view.findViewById(R.id.rv_products) as RecyclerView
            val txtTitle : TextView = view.findViewById(R.id.txt_title) as TextView
            txtTitle.text = allData.title
            productsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ProductAdapter(allData.contents)
            }
            llContents.addView(view)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleObservers() {
        homeViewModel.onErrorHandler()?.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireParentFragment().context,
                "Data fetching failed",
                Toast.LENGTH_LONG
            ).show()
        }
        homeViewModel.onSuccessHandler()
            .observe(viewLifecycleOwner, Observer<List<AllData>?> {

                Toast.makeText(
                    requireParentFragment().context,
                    "Data Fetched Successfully",
                    Toast.LENGTH_LONG
                ).show()
                if (it != null) {
                    dataList = it
                    binding.llContents.removeAllViews()
                    showUi(dataList)

                }

                lifecycleScope.launch(Dispatchers.IO) {
                    homeViewModel.insertHomeData(
                        ApiResponseData(
                            ApiConstants.get_home_data,
                            Gson().toJson(it)
                        )
                    )
                }

            })
    }

    private fun showUi(dataList: List<AllData>) {
        if(dataList.isNotEmpty()) {
            for (x in dataList) {
                getAptView(x, binding.llContents)
            }

        }
    }
}