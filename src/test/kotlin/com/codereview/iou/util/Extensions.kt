package com.codereview.iou.util

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

fun ResultActions.jsonExpect(isPrintResponse: Boolean = true) =
    this.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).apply {
        if (isPrintResponse) {
            this.andDo {
                println(it.response.contentAsString)
            }
        }
    }
