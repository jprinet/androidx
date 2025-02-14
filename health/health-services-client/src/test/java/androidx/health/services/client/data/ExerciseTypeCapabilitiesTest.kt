/*
 * Copyright 2023 The Android Open Source Project
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

package androidx.health.services.client.data

import androidx.health.services.client.data.ComparisonType.Companion.GREATER_THAN
import androidx.health.services.client.data.DataType.Companion.HEART_RATE_BPM
import androidx.health.services.client.data.DataType.Companion.HEART_RATE_BPM_STATS
import androidx.health.services.client.proto.DataProto
import com.google.common.collect.ImmutableMap
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExerciseTypeCapabilitiesTest {
    @Test
    fun unknownEventCapabilities_roundTrip_emptyEventCapabilities() {
        class TestEventCapabilities(override val isSupported: Boolean) :
            ExerciseEventCapabilities() {
            override fun toProto(): DataProto.ExerciseEventCapabilities =
                DataProto.ExerciseEventCapabilities.getDefaultInstance()
        }
        val testingCapabilities =
            ExerciseTypeCapabilities(
                supportedDataTypes = emptySet(),
                supportedGoals = emptyMap(),
                supportedMilestones = emptyMap(),
                supportsAutoPauseAndResume = true,
                exerciseEventCapabilities =
                    ImmutableMap.of(ExerciseEventType.UNKNOWN, TestEventCapabilities(false)),
            )

        val proto = testingCapabilities.proto
        val capabilitiesReturned = ExerciseTypeCapabilities(proto)

        assertThat(capabilitiesReturned.exerciseEventCapabilities).isEmpty()
        assertThat(capabilitiesReturned.supportedExerciseEvents).isEmpty()
    }

    @Test
    fun debouncedGoalCapabilities_roundTrip() {
        val testingCapabilities =
            ExerciseTypeCapabilities(
                supportedDataTypes = emptySet(),
                supportedGoals = emptyMap(),
                supportedMilestones = emptyMap(),
                supportsAutoPauseAndResume = true,
                exerciseEventCapabilities = emptyMap(),
                supportedDebouncedGoals =
                    mapOf(
                        HEART_RATE_BPM to setOf(GREATER_THAN),
                        HEART_RATE_BPM_STATS to setOf(GREATER_THAN),
                    ),
            )

        val proto = testingCapabilities.proto
        val capabilitiesReturned = ExerciseTypeCapabilities(proto)

        assertThat(capabilitiesReturned.supportedDebouncedGoals)
            .isEqualTo(
                mapOf(
                    HEART_RATE_BPM to setOf(GREATER_THAN),
                    HEART_RATE_BPM_STATS to setOf(GREATER_THAN),
                )
            )
    }
}
