package com.example.sneakers.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sneakers.data.entities.CartItem
import com.example.sneakers.data.entities.Product
import com.example.sneakers.data.entities.User
import com.example.sneakers.data.remote.CartRepository
import com.example.sneakers.data.remote.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SharedViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    var selectedProduct: Product? by mutableStateOf(null)
        private set

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    val cartItems: StateFlow<List<CartItem>> = cartRepository.allCartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        _products.value = getSampleProducts()
    }

    fun selectProduct(product: Product) {
        selectedProduct = product
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) = viewModelScope.launch {
        val user = userRepository.loginUser(email, password)
        _currentUser.value = user
        onResult(user != null)
    }

    fun registerUser(name: String, email: String, password: String, onResult: (Boolean) -> Unit) = viewModelScope.launch {
        if (userRepository.getUserByEmail(email) != null) {
            onResult(false)
            return@launch
        }
        val newUser = User(name = name, email = email, password = password)
        userRepository.registerUser(newUser)
        onResult(true)
    }

    fun logout() {
        _currentUser.value = null
    }

    fun addToCart(product: Product) = viewModelScope.launch {
        // Si el producto es el banner, no lo añadimos al carrito
        if (product.id == 0) return@launch
        
        val existingItem = cartRepository.getCartItemById(product.id)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartRepository.insert(updatedItem)
        } else {
            val newCartItem = CartItem(
                id = product.id,
                name = product.name,
                description = product.description,
                price = product.price,
                image = product.image,
                quantity = 1
            )
            cartRepository.insert(newCartItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) = viewModelScope.launch {
        cartRepository.delete(cartItem)
    }

    fun increaseQuantity(item: CartItem) = viewModelScope.launch {
        val updatedItem = item.copy(quantity = item.quantity + 1)
        cartRepository.insert(updatedItem)
    }

    fun decreaseQuantity(item: CartItem) = viewModelScope.launch {
        if (item.quantity > 1) {
            val updatedItem = item.copy(quantity = item.quantity - 1)
            cartRepository.insert(updatedItem)
        } else {
            removeFromCart(item)
        }
    }

    fun clearCart() = viewModelScope.launch {
        cartRepository.clearCart()
    }

    private fun getSampleProducts(): List<Product> {
        return listOf(
            Product(0, "Banner Promocional", "", 0.0, "https://www.shutterstock.com/image-vector/sneakers-logo-shoes-sign-600w-471891569.jpg"),
            Product(1, "Nike Air Max 90", "Un clásico que nunca pasa de moda.", 160000.0, "https://nikeclprod.vtexassets.com/arquivos/ids/271740/DH8010_002_A_PREM.jpg?v=637771712646000000"),
            Product(2, "Adidas Ultraboost 22", "Corre más lejos que nunca.", 180000.0, "https://www.jdsports.gr/2685617-product_horizontal/adidas-ultraboost-22-w.jpg"),
            Product(3, "Puma Suede Classic", "El estilo icónico de Puma.", 65000.0, "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa/global/374915/12/sv01/fnd/CHL/w/1000/h/1000/fmt/png"),
            Product(4, "New Balance 574", "La silueta más reconocible.", 85000.0, "https://www.realkicks.cl/cdn/shop/files/p-U574RAD-2.jpg?v=1733829745"),
            Product(5, "Converse Chuck 70", "El clásico atemporal, rediseñado.", 80000.0, "https://ferreira.vtexassets.com/arquivos/ids/429208-800-auto?v=638465366638330000&width=800&height=auto&aspect=true"),
            Product(6, "Vans Old Skool", "La zapatilla de skate icónica.", 60000.0, "https://suburbiosskateboards.com.mx/wp-content/uploads/2023/05/Check.jpg.webp"),
            Product(7, "Reebok Classic Leather", "Un icono de los 80.", 75000.0, "https://dpjye2wk9gi5z.cloudfront.net/wcsstore/ExtendedSitesCatalogAssetStore/images/catalog/zoom/1033773-0100V1.jpg"),
            Product(8, "ASICS GEL-Kayano 28", "Estabilidad y confort excepcionales.", 160000.0, "https://home.ripley.com.pe/Attachment/WOP_5/2084292469545/2084292469545-2.jpg"),
            Product(9, "Saucony Kinvara 13", "Ligereza y flexibilidad.", 110000.0, "https://www.marathon.cl/on/demandware.static/-/Sites-catalog-equinox/default/dw51db625f/images/marathon/195019015982_1-20250601120000-mrtChile.jpeg"),
            Product(10, "Brooks Ghost 14", "Amortiguación suave.", 130000.0, "https://fartlecksport.shop/wp-content/uploads/2022/07/brooks-ghost-14-amarillo-2-1-600x645.jpg")
        )
    }
}

class SharedViewModelFactory(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(cartRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
