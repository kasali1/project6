package com.example.project6

import org.json.JSONArray
import org.json.JSONObject

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val brand: String,
    val thumbnailUrl: String
) {
    companion object {
        fun fromJSONObject(jsonObject: JSONObject): Product {
            val id = jsonObject.optInt("id", 0)
            val title = jsonObject.optString("title", "Unknown Product")
            val description = jsonObject.optString("description", "")
            val category = jsonObject.optString("category", "General")
            val price = jsonObject.optDouble("price", 0.0)
            val rating = jsonObject.optDouble("rating", 0.0)
            val rawBrand = jsonObject.optString("brand", "")
            val brand = if (rawBrand.isEmpty() || rawBrand == "null") category.replaceFirstChar { it.uppercase() } else rawBrand
            val thumbnail = jsonObject.optString("thumbnail", "")

            return Product(
                id = id,
                title = title,
                description = description,
                category = category,
                price = price,
                rating = rating,
                brand = brand,
                thumbnailUrl = thumbnail
            )
        }

        fun fromJSONArray(jsonArray: JSONArray): List<Product> {
            val products = mutableListOf<Product>()
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.optJSONObject(i) ?: continue
                products.add(fromJSONObject(item))
            }
            return products
        }
    }
}
