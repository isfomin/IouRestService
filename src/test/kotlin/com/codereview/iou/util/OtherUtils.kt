package com.codereview.iou.util

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

fun postJson(urlTemplate: String, contentObject: Any): MockHttpServletRequestBuilder =
    MockMvcRequestBuilders.post(urlTemplate)
        .content(contentObject.asJsonString())
        .contentType(MediaType.APPLICATION_JSON)

fun deleteJson(urlTemplate: String, contentObject: Any): MockHttpServletRequestBuilder =
    MockMvcRequestBuilders.delete(urlTemplate)
        .content(contentObject.asJsonString())
        .contentType(MediaType.APPLICATION_JSON)
