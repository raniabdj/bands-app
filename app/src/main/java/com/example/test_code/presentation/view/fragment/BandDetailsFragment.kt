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
import com.example.test_code.presentation.entity.AlbumViewEntity
import com.example.test_code.presentation.entity.BandDetailsViewEntity
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.presenter.BandDetailsPresenter
import javax.inject.Inject

import com.example.test_code.databinding.FragmentBandDetailsBinding
import com.example.test_code.databinding.RowAlbumBinding


class BandDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_BAND = "param_band"

        fun forBand(band: BandViewEntity): BandDetailsFragment {
            val fragment = BandDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_BAND, band)
            fragment.arguments = arguments
            return fragment
        }
    }
    private lateinit var binding:FragmentBandDetailsBinding

    @Inject lateinit var presenter: BandDetailsPresenter

    override fun layoutId(): Int = R.layout.fragment_band_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(this, this.arguments!!.get(PARAM_BAND) as BandViewEntity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBandDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    //private BaseFragmentBinding binding;

    override fun onDestroyView() {
        binding.albumsList.adapter = null
        super.onDestroyView()
    }

    fun render(entity: BandDetailsViewEntity) {
       // activity?.toolbar?.title = entity.name
        binding.bandDescription.text = entity.description
        binding.bandGenre.text = entity.genre
        binding.bandFoundationYear.text = entity.foundationYear
        binding.bandPicture.loadFromUrl(entity.picture.toString())

        binding.albumsList.layoutManager = LinearLayoutManager(context)
        binding.albumsList.adapter = AlbumsAdapter(entity.albums) { presenter.onAlbumClicked(it) }
    }
}

class AlbumsAdapter
@Inject constructor(private val items: List<AlbumViewEntity>,
                    private val clickListener: (AlbumViewEntity) -> Unit)
    : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowAlbumBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
   //    ViewHolder(
     //       LayoutInflater.from(parent.context)
       //         .inflate(R.layout.row_album, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       val album=items[position]
        holder.bindng.albumPicture.loadFromUrl(album.picture.toString())
        holder.bindng.albumName.text = album.title
        holder.bindng.albumDate.text = album.date
        holder.itemView.setOnClickListener { clickListener(album) }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val bindng: RowAlbumBinding) : RecyclerView.ViewHolder(bindng.root)


}