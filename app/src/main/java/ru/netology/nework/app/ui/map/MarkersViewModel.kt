package ru.netology.nework.app.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nework.app.model.CoordinatesData
import ru.netology.nework.domain.MarkerRepository

class MarkersViewModel(
    private val repository: MarkerRepository,
    app: Application
) : AndroidViewModel(app) {

    private val _editableMarker = MutableLiveData<MyMarker>()

    private val _userMarker = MutableLiveData<MyMarker>()
    val userMarker: LiveData<MyMarker>
        get() = _userMarker

    private val _markers = MutableLiveData<List<MyMarker>>()
    val markers: LiveData<List<MyMarker>>
        get() = _markers

    private val _cameraMarker = MutableLiveData<MyMarker>()
    val cameraMarker: LiveData<MyMarker>
        get() = _cameraMarker

    private val _uiState = MutableLiveData(MapMarkersUiState(None, repository.getMarkers()))
    val uiState: LiveData<MapMarkersUiState>
        get() = _uiState

    fun addMarkerClick() {
        postState(MapMarkersUiState(EditMarker, repository.getMarkers()))
    }

    fun addStartedMarker(coordinates: CoordinatesData) {
        repository.addMarker(coordinates.toMarker())
        postState(MapMarkersUiState(EditMarker, repository.getMarkers()))
    }

    fun cancelEdit() {
        _editableMarker.value?.apply {
            if (id >= 0) {
                repository.addMarker(this)
            }
            postState(MapMarkersUiState(None, repository.getMarkers()))
        }
    }

    fun saveMarker() {
        _userMarker.value?.apply {
            repository.addMarker(this)
            postState(MapMarkersUiState(None, repository.getMarkers()))
        }
    }

    fun editMarker(myMarker: MyMarker) {
        repository.removeMarker(myMarker)
        _editableMarker.postValue(myMarker)
        postState(MapMarkersUiState(EditMarker, repository.getMarkers()))
    }

    fun editMarker(markerId: Int) {
        repository.getMarkers().find {
            it.id == markerId
        }?.apply {
            editMarker(this)
        }
    }

    private fun postState(state: MapMarkersUiState) {
        if (_uiState.value != state) {
            _uiState.postValue(state)
        }
    }

    fun setUserMarker(empty: MyMarker) {
        _editableMarker.value?.apply {
            _userMarker.postValue(this.copy(latLng = empty.latLng))
        }
    }

    fun saveCaption(markerId: Int, caption: String) {
        repository.getMarkers().find {
            it.id == markerId
        }?.apply {
            repository.updateMarker(this.copy(caption = caption))
            _uiState.postValue(MapMarkersUiState(None, repository.getMarkers()))
        }
    }

    fun deleteMarker(markerId: Int) {
        repository.getMarkers().find {
            it.id == markerId
        }?.apply {
            repository.removeMarker(this)
            _uiState.postValue(MapMarkersUiState(None, repository.getMarkers()))
        }
    }

    fun loadList() {
        _markers.postValue(repository.getMarkers())
    }

    fun moveCamera(myMarker: MyMarker?) {
        myMarker?.apply {
            _cameraMarker.postValue(this)
        }
    }
}
