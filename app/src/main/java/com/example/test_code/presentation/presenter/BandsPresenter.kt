package com.example.test_code.presentation.presenter
import com.example.test_code.application.service.ErrorMessageFormatter
import com.example.test_code.application.interactor.GetBands
import com.example.test_code.application.interactor.UseCase
import com.example.test_code.application.navigation.Navigator
import com.example.test_code.domain.Band
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.view.fragment.BandsFragment
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject
class BandsPresenter
@Inject constructor(private val getBands: GetBands,
                    private val navigator: Navigator,
                    private val errorFormatter: ErrorMessageFormatter
) {

    private lateinit var view : BandsFragment

    fun init(view: BandsFragment) {
        this.view = view

        getBands.execute(object : DisposableSingleObserver<List<Band>>() {
            override fun onSuccess(t: List<Band>) =
                view.render(t.map { band -> BandViewEntity.from(band) }.toTypedArray())

            override fun onError(e: Throwable) {
                view.notify(errorFormatter.getErrorMessage(e))
                view.close()
            }
        }, UseCase.None())
    }

    fun onBandClicked(band: BandViewEntity) {
        navigator.showBandDetails(view.activity!!, band)
    }
}