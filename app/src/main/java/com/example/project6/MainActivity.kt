package com.example.project6

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmptyState: TextView
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: MaterialButton
    private lateinit var btnClear: MaterialButton
    private lateinit var chipGroup: ChipGroup

    private val productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private val client = AsyncHttpClient()

    private var currentCategory = "All"
    private var currentQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI Elements
        rvProducts = findViewById(R.id.rvProducts)
        progressBar = findViewById(R.id.progressBar)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnClear = findViewById(R.id.btnClear)
        chipGroup = findViewById(R.id.chipGroup)

        // Setup RecyclerView & Adapter
        adapter = ProductAdapter(this, productList) { product ->
            onProductClicked(product)
        }
        rvProducts.adapter = adapter
        rvProducts.layoutManager = LinearLayoutManager(this)

        // Stretch Feature: Add Item Dividers
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        rvProducts.addItemDecoration(dividerItemDecoration)

        // Setup Search Actions
        btnSearch.setOnClickListener {
            performSearch()
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        btnClear.setOnClickListener {
            etSearch.text?.clear()
            currentQuery = ""
            btnClear.visibility = View.GONE
            fetchProducts()
        }

        // Setup Category Chip Selection
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = findViewById<Chip>(checkedIds[0])
                currentCategory = chip?.text?.toString() ?: "All"
            } else {
                currentCategory = "All"
            }
            fetchProducts()
        }

        // Fetch Initial Products
        fetchProducts()
    }

    private fun performSearch() {
        val query = etSearch.text.toString().trim()
        currentQuery = query
        btnClear.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
        hideKeyboard()
        fetchProducts()
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun fetchProducts() {
        progressBar.visibility = View.VISIBLE
        tvEmptyState.visibility = View.GONE
        rvProducts.visibility = View.GONE

        val url = when {
            currentQuery.isNotEmpty() -> "https://dummyjson.com/products/search?q=$currentQuery"
            currentCategory != "All" -> "https://dummyjson.com/products/category/${currentCategory.lowercase()}"
            else -> "https://dummyjson.com/products?limit=50"
        }

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                progressBar.visibility = View.GONE
                try {
                    val productsArray = json.jsonObject.getJSONArray("products")
                    val fetchedProducts = Product.fromJSONArray(productsArray)

                    productList.clear()
                    productList.addAll(fetchedProducts)
                    adapter.notifyDataSetChanged()

                    if (productList.isEmpty()) {
                        tvEmptyState.visibility = View.VISIBLE
                        rvProducts.visibility = View.GONE
                    } else {
                        tvEmptyState.visibility = View.GONE
                        rvProducts.visibility = View.VISIBLE
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    showErrorSnackbar("Error parsing products data")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                progressBar.visibility = View.GONE
                tvEmptyState.visibility = View.VISIBLE
                rvProducts.visibility = View.GONE
                showErrorSnackbar("Failed to fetch products. Status: $statusCode")
            }
        })
    }

    private fun onProductClicked(product: Product) {
        // Stretch Feature: Show Toast or Snackbar when an item is clicked
        val message = "🛍️ ${product.title}\nBrand: ${product.brand} • Price: $${product.price} • Rating: ⭐ ${product.rating}"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(rvProducts, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") { fetchProducts() }
            .show()
    }
}
