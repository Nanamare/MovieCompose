package com.nanamare.data

import com.google.common.truth.Truth.assertThat
import com.nanamare.data.local.db.ListConverter
import org.junit.Test

class ConverterTest {

    @Test
    fun test_int_list_encode_to_string() {
        assertThat(ListConverter().encode(listOf(1, 2, 3))).isEqualTo("[1,2,3]")
    }

    @Test
    fun test_string_decode_to_int_list() {
        assertThat(ListConverter().decode("[1, 2, 3]")).isEqualTo(listOf(1, 2, 3))
    }

}