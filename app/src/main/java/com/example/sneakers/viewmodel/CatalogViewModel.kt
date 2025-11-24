package com.example.sneakers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sneakers.data.entities.Product
import com.example.sneakers.data.remote.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    var selectedProduct: Product? = null
        private set

    init {
        fetchAndMergeProducts()
    }

    fun selectProduct(product: Product) {
        selectedProduct = product
    }

    private fun fetchAndMergeProducts() = viewModelScope.launch {
        val apiProducts = productRepository.getProducts()
        val localProductsWithImages = getSampleProducts()
        val imageMap = localProductsWithImages.associate { it.id to (it.image to it.sizes) }

        val mergedProducts = apiProducts.map { apiProduct ->
            val (imageUrl, sizes) = imageMap[apiProduct.id] ?: (null to null)
            apiProduct.copy(
                image = imageUrl ?: "",
                sizes = sizes ?: ""
            )
        }

        // URL del Banner actualizada
        val banner = Product(0, "Banner Promocional", "", 0.0, "https://www.shutterstock.com/image-vector/sneakers-logo-shoes-sign-600w-471891569.jpg", "")
        _products.value = listOf(banner) + mergedProducts
    }

    private fun getSampleProducts(): List<Product> {
        return listOf(
            Product(1, "Nike Air Max 90", "", 0.0, "https://nikeclprod.vtexassets.com/arquivos/ids/271740/DH8010_002_A_PREM.jpg", "40,41,42,43,44"),
            Product(2, "Adidas Ultraboost 22", "", 0.0, "https://www.jdsports.gr/2685617-product_horizontal/adidas-ultraboost-22-w.jpg", "41,42,43"),
            Product(3, "Puma Suede Classic", "", 0.0, "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa/global/374915/12/sv01/fnd/CHL/w/1000/h/1000/fmt/png", "39,40,41,42"),
            Product(4, "New Balance 574", "", 0.0, "https://www.shoelander.com/cdn/shop/products/ML574ACC-1.jpg", "40,41,42,43,44,45"),
            Product(5, "Converse Chuck 70", "", 0.0, "https://ferreira.vtexassets.com/arquivos/ids/429208-800-auto", "38,39,40,41"),
            Product(6, "Vans Old Skool", "", 0.0, "https://suburbiosskateboards.com.mx/wp-content/uploads/2023/05/Check.jpg.webp", "39,40,41,42,43"),
            Product(7, "Reebok Classic Leather", "", 0.0, "https://dpjye2wk9gi5z.cloudfront.net/wcsstore/ExtendedSitesCatalogAssetStore/images/catalog/zoom/1033773-0100V1.jpg", "40,41,42"),
            Product(8, "ASICS GEL-Kayano 28", "", 0.0, "https://home.ripley.com.pe/Attachment/WOP_5/2084292469545/2084292469545-2.jpg", "42,43,44"),
            Product(9, "Saucony Kinvara 13", "", 0.0, "https://www.marathon.cl/on/demandware.static/-/Sites-catalog-equinox/default/dw51db625f/images/marathon/195019015982_1-20250601120000-mrtChile.jpeg", "41,42,43,44"),
            Product(10, "Brooks Ghost 14", "", 0.0, "https://fartlecksport.shop/wp-content/uploads/2022/07/brooks-ghost-14-amarillo-2-1-600x645.jpg", "40,41,42,43")
        )
    }
}

class CatalogViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
