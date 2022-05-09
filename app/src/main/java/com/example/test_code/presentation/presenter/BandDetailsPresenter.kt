package com.example.test_code.presentation.presenter
import com.example.test_code.application.exception.YouTubeNotInstalled
import com.example.test_code.application.service.ErrorMessageFormatter
import com.example.test_code.application.interactor.GetBandDetails
import com.example.test_code.application.navigation.Navigator
import com.example.test_code.domain.BandDetails
import com.example.test_code.presentation.entity.AlbumViewEntity
import com.example.test_code.presentation.entity.BandDetailsViewEntity
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.view.fragment.BandDetailsFragment
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject
class BandDetailsPresenter
@Inject constructor(private val getBandDetails: GetBandDetails,
                    private val navigator: Navigator,
                    private val errorFormatter: ErrorMessageFormatter
) {

    private lateinit var view: BandDetailsFragment
    private lateinit var band: BandViewEntity

    fun init(view: BandDetailsFragment, band: BandViewEntity) {
        this.view = view
        this.band = band

        getBandDetails.execute(object : DisposableSingleObserver<BandDetails>() {
            override fun onSuccess(bandDetails: BandDetails) =
                view.render(BandDetailsViewEntity.from(bandDetails))

            override fun onError(e: Throwable) {
                view.notify(errorFormatter.getErrorMessage(e))
                view.close()
            }
        },
            band.id)
    }

    fun onAlbumClicked(album: AlbumViewEntity) {
        try {
            navigator.openYouTubeSearch(view.activity!!, "${band.name} ${album.title}")
        }
        catch(e: YouTubeNotInstalled) {
            view.notify(errorFormatter.getErrorMessage(e))
        }
    }
}