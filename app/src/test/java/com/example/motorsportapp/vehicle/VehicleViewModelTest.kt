import app.cash.turbine.test
import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.domain.model.TopSpeed
import com.example.motorsportapp.domain.model.VehicleImages
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

@ExperimentalCoroutinesApi
class VehicleViewModelTest {

    private lateinit var repository: VehicleRepository
    private lateinit var viewModel: VehicleViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createDummyVehicle(id: String, manufacturer: String, model: String) = Vehicle(
        id = id,
        manufacturer = manufacturer,
        model = model,
        seats = 4,
        price = 20000,
        topSpeed = TopSpeed(
            mph = 140,
            kmh = 220
        ),
        images = VehicleImages(
            front = "https://example.com/front.png",
            rear = "https://example.com/rear.png",
            side = "https://example.com/side.png",
            rearQuarter = "https://example.com/rearQuarter.png",
            frontQuarter = "https://example.com/frontQuarter.png"
        )
    )

    @Test
    fun `loadVehicles updates vehicles list`() = runTest {
        val vehicles = listOf(
            createDummyVehicle("1", "Toyota", "Corolla"),
            createDummyVehicle("2", "Ford", "Mustang")
        )
        whenever(repository.getVehicles()).thenReturn(vehicles)

        viewModel = VehicleViewModel(repository)

        // Esperamos a que loadVehicles() termine
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(vehicles, viewModel.vehicles.value)
    }

    @Test
    fun `loadVehicles handles error`() = runTest {
        whenever(repository.getVehicles()).thenThrow(RuntimeException("Network error"))

        viewModel = VehicleViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Debe estar vacío si ocurre error
        assertEquals(emptyList<Vehicle>(), viewModel.vehicles.value)
    }



    @Test
    fun `toggleFavorite adds and removes correctly`() = runTest {
        val vehicleId = "1"
        viewModel = VehicleViewModel(repository)

        // Agregar a favoritos
        viewModel.toggleFavorite(vehicleId)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(repository).addToFavorites(vehicleId)
        assertTrue(viewModel.favoriteIds.value.contains(vehicleId))

        // Quitar de favoritos
        viewModel.toggleFavorite(vehicleId)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(repository).removeFromFavorites(vehicleId)
        assertFalse(viewModel.favoriteIds.value.contains(vehicleId))
    }

    @Test
    fun `toggleCart adds and removes correctly`() = runTest {
        val vehicle = createDummyVehicle("1", "Toyota", "Corolla")
        viewModel = VehicleViewModel(mock())

        viewModel.toggleCart(vehicle)
        assertTrue(viewModel.cartIds.value.contains(vehicle.id))

        viewModel.toggleCart(vehicle)
        assertFalse(viewModel.cartIds.value.contains(vehicle.id))
    }
}
