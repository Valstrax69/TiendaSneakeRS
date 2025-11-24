package com.example.sneakers.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.sneakers.data.remote.ProductRepository
import com.example.sneakers.viewmodel.CatalogViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_showsFeaturedSection() {
        val mockRepo = mock(ProductRepository::class.java)
        val mockViewModel = CatalogViewModel(mockRepo)

        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeScreen(navController, mockViewModel)
        }

        composeTestRule.onNodeWithText("Destacados").assertIsDisplayed()
    }
}
