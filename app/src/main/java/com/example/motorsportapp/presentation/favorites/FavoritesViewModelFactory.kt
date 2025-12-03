import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.presentation.favorites.FavoritesViewModel

class FavoritesViewModelFactory(
    private val apiService: ApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
