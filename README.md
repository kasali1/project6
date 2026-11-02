# Project 6 - *Product Explorer*

Submitted by: **Annah Kasali**

Time spent: **4** hours spent in total

## User Stories

The following **required** functionality is completed:

- [x] **Make an API call to an API of your choice using AsyncHTTPClient**
- [x] **Implement a RecyclerView to display a list of entries from the API**
- [x] **Display at least three (3) pieces of data for each RecyclerView item**
  - Displays product thumbnail image (loaded with Glide)
  - Displays product title
  - Displays product price
  - Displays product rating
  - Displays product category and brand
  - Displays product description snippet

The following **stretch** features are implemented:

- [x] **Add a UI element for the user to interact with API further** (Search bar to query products live by keyword, plus Category Filter Chips)
- [x] **Show a Toast or Snackbar when an item is clicked** (Displays a detailed Toast notification on item click)
- [x] **Add item dividers with DividerItemDecoration** (Vertical item decoration added to RecyclerView)

The following **additional** features are implemented:

- [x] Loading state (`ProgressBar`) while fetching data over network
- [x] Empty state message when no products match search query

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/your-gif-here.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

<!-- REPLACE GIF URL ABOVE WITH YOUR OWN GIF URL -->

## Notes

- Integrated `com.codepath.asynchttpclient.AsyncHttpClient` to fetch product data from `https://dummyjson.com/products`.
- Parsed JSON response arrays into strongly typed Kotlin `Product` objects.
- Styled `RecyclerView` card items with Glide image loading and Material Card views.

## License

    Copyright 2026 Annah Kasali

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
