package com.enes.busproje

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enes.busproje.adapter.LinesAdapter
import com.enes.busproje.databinding.FragmentLinesBinding
import com.enes.busproje.model.LinesModel
import com.enes.busproje.model.Service
import com.enes.busproje.service.LinesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LinesFragment : Fragment(), LinesAdapter.Listener {

    private lateinit var binding: FragmentLinesBinding
    private var linesAdapter: LinesAdapter? = null
    private var servicesList = ArrayList<Service>()
    companion object {
        const val BASE_URL = "https://tfe-opendata.com/api/v1/"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinesBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        binding.recyclerView.layoutManager = layoutManager
        fetchData()
    }

    private fun fetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(LinesAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<LinesModel> {
            override fun onFailure(call: Call<LinesModel>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LinesModel>,
                response: Response<LinesModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { linesModel ->
                        linesModel.services?.forEach { service ->
                            servicesList.add(service)
                        }
                        linesAdapter = LinesAdapter(servicesList,this@LinesFragment)
                        binding.recyclerView.adapter = linesAdapter
                    }
                }
            }
        })
    }
    override fun onItemClick(services: Service) {
        val intent = Intent(activity, LinesToMap::class.java)
        Toast.makeText(activity, "hmkghfdfdhgfd", Toast.LENGTH_SHORT).show()
        intent.putExtra("line_name", services.description)
        intent.putExtra("Service", services)
        startActivity(intent)
    }
}