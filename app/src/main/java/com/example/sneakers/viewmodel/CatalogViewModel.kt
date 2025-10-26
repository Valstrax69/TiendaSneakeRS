package com.example.sneakers.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sneakers.data.entities.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    var selectedProduct: Product? by mutableStateOf(null)
        private set

    init {
        _products.value = getSampleProducts()
    }

    fun selectProduct(product: Product) {
        selectedProduct = product
    }

    private fun getSampleProducts(): List<Product> {
        return listOf(
            Product(0, "Banner Promocional", "", 0.0, "https://www.shutterstock.com/image-vector/sneakers-logo-shoes-sign-600w-471891569.jpg", ""),
            Product(1, "Nike Air Max 90", "Un clásico que nunca pasa de moda.", 160000.0, "https://nikeclprod.vtexassets.com/arquivos/ids/271740/DH8010_002_A_PREM.jpg?v=637771712646000000", "40,41,42,43,44"),
            Product(2, "Adidas Ultraboost 22", "Corre más lejos que nunca.", 180000.0, "https://www.jdsports.gr/2685617-product_horizontal/adidas-ultraboost-22-w.jpg", "41,42,43"),
            Product(3, "Puma Suede Classic", "El estilo icónico de Puma.", 65000.0, "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa/global/374915/12/sv01/fnd/CHL/w/1000/h/1000/fmt/png", "39,40,41,42"),
            Product(4, "New Balance 574", "La silueta más reconocible.", 85000.0, "https://www.shoelander.com/cdn/shop/products/ML574ACC-1.jpg?v=1489289892", "40,41,42,43,44,45"),
            Product(5, "Converse Chuck 70", "El clásico atemporal, rediseñado.", 80000.0, "https://ferreira.vtexassets.com/arquivos/ids/429208-800-auto?v=638465366638330000&width=800&height=auto&aspect=true", "38,39,40,41"),
            Product(6, "Vans Old Skool", "La zapatilla de skate icónica.", 60000.0, "https://suburbiosskateboards.com.mx/wp-content/uploads/2023/05/Check.jpg.webp", "39,40,41,42,43"),
            Product(7, "Reebok Classic Leather", "Un icono de los 80.", 75000.0, "https://dpjye2wk9gi5z.cloudfront.net/wcsstore/ExtendedSitesCatalogAssetStore/images/catalog/zoom/1033773-0100V1.jpg", "40,41,42"),
            Product(8, "ASICS GEL-Kayano 28", "Estabilidad y confort excepcionales.", 160000.0, "https://home.ripley.com.pe/Attachment/WOP_5/2084292469545/2084292469545-2.jpg", "42,43,44"),
            Product(9, "Saucony Kinvara 13", "Ligereza y flexibilidad.", 110000.0, "https://www.marathon.cl/on/demandware.static/-/Sites-catalog-equinox/default/dw51db625f/images/marathon/195019015982_1-20250601120000-mrtChile.jpeg", "41,42,43,44"),
            Product(10, "Brooks Ghost 14", "Amortiguación suave.", 130000.0, "https://fartlecksport.shop/wp-content/uploads/2022/07/brooks-ghost-14-amarillo-2-1-600x645.jpg", "40,41,42,43")
        )
    }
}
