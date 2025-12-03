import app.cash.turbine.test
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.dto.OrderDto
import com.example.motorsportapp.presentation.order.OrdersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
class OrdersViewModelTest {

    private lateinit var apiService: ApiService
    private lateinit var viewModel: OrdersViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        apiService = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createDummyOrder(id: Long) = OrderDto(
        id = id,
        userId = 1,
        fechaPedido = "2025-12-02",
        estado = "PENDING",
        direccionEnvio = "Calle Falsa 123",
        items = emptyList()
    )


    @Test
    fun `loadOrders http error sets error message`() = runTest {
        whenever(apiService.getOrdersByUserId(1)).thenReturn(
            Response.error(404, ResponseBody.create("application/json".toMediaTypeOrNull(), "Not Found"))
        )

        viewModel = OrdersViewModel(apiService)

        viewModel.loadOrders(1)
        advanceUntilIdle()

        assertEquals("Error al cargar órdenes: 404", viewModel.error.value)
        assertEquals(emptyList<OrderDto>(), viewModel.orders.value)
    }

    @Test
    fun `loadOrders network exception sets error message`() = runTest {
        whenever(apiService.getOrdersByUserId(1)).thenThrow(RuntimeException("No internet"))

        viewModel = OrdersViewModel(apiService)

        viewModel.loadOrders(1)
        advanceUntilIdle()

        assertNotNull(viewModel.error.value)
        assertEquals("Error de red: No internet", viewModel.error.value)
        assertEquals(emptyList<OrderDto>(), viewModel.orders.value)
    }
}
