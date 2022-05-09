package com.example.test_code.application.interactor
import com.example.test_code.application.repository.BandRepository
import com.example.test_code.domain.Band
import com.example.test_code.infrastructure.platform.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject
class GetBands
@Inject constructor(private val bandRepository: BandRepository,
                    schedulersProvider: SchedulersProvider)
    : UseCaseSingle<List<Band>, UseCase.None>(schedulersProvider) {

    override fun buildUseCaseSingle(params: None): Single<List<Band>> =
        bandRepository.bands()
}