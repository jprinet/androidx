/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.wear.compose.foundation.lazy

import androidx.compose.ui.layout.MeasureResult

/** The result of the measure pass of the [LazyColumn]. */
internal class LazyColumnMeasureResult(
    /** MeasureResult defining the layout. */
    measureResult: MeasureResult,
    /** The index of the item that should be considered as an anchor during scrolling. */
    val anchorItemIndex: Int,
    /** The offset of the anchor item from the top of screen. */
    val anchorItemScrollOffset: Int,
    /** Layout information for the visible items. */
    val visibleItems: List<LazyColumnVisibleItemInfo>,
    /** see [LazyColumnLayoutInfo.totalItemsCount] */
    val totalItemsCount: Int,
) : MeasureResult by measureResult
