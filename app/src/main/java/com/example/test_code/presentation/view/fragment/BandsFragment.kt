package com.example.test_code.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_code.R
import com.example.test_code.infrastructure.extentions.loadFromUrl
import com.example.test_code.infrastructure.platform.BaseFragment
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.presenter.BandsPresenter

import com.example.test_code.databinding.FragmentBandsBinding
import com.example.test_code.databinding.RowAlbumBinding
import com.example.test_code.databinding.RowBandBinding

import javax.inject.Inject

class BandsFragment : BaseFragment() {

    @Inject
    lateinit var presenter: BandsPresenter

    override fun layoutId() = R.layout.fragment_bands

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    private lateinit var binding: FragmentBandsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBandsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(this)
    }

    override fun onDestroyView() {
        binding.bandsList.adapter = null
        super.onDestroyView()
    }

    fun render(bands: Array<BandViewEntity>) {
        binding.bandsList.layoutManager = LinearLayoutManager(context)
        binding.bandsList.adapter = BandsAdapter(bands) { presenter.onBandClicked(it) }
    }
}

class BandsAdapter
@Inject constructor(
    private val items: Array<BandViewEntity>,
    private val clickListener: (BandViewEntity) -> Unit
) : RecyclerView.Adapter<BandsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowBandBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind(items[position])
        val bandViewEntity=items[position]
        holder.binding.bandPicture.loadFromUrl(bandViewEntity.picture)
        holder.binding.bandName.text = bandViewEntity.name
        holder.itemView.setOnClickListener { clickListener(bandViewEntity) }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: RowBandBinding) : RecyclerView.ViewHolder(binding.root)
}