package com.youarelaunched.challenge.fakes

import com.youarelaunched.challenge.data.network.api.ApiVendors
import com.youarelaunched.challenge.data.network.models.NetworkMediaAttachment
import com.youarelaunched.challenge.data.network.models.NetworkVendor
import javax.inject.Inject

class FakeApiVendors @Inject constructor(): ApiVendors {

    override suspend fun getVendors(): List<NetworkVendor> {
        return mockVendors
    }

    companion object {
        val mockVendors = listOf(
            NetworkVendor(
                areaServed = "pretium",
                categories = listOf(),
                companyName = "Myra Cox",
                coverPhoto = NetworkMediaAttachment(
                    id = 2335,
                    mediaType = "instructior",
                    mediaUrl = "https://www.google.com/#q=impetus"
                ),
                profilePhoto = null,
                distance = null,
                favorite = false,
                id = 1633,
                shopType = "sadipscing",
                tags = listOf(),
                chatId = null,
                type = null
            ),
            NetworkVendor(
                areaServed = "vituperatoribus",
                categories = listOf(),
                companyName = "Esteban Boyd",
                coverPhoto = NetworkMediaAttachment(
                    id = 4098,
                    mediaType = "conclusionemque",
                    mediaUrl = "https://www.google.com/#q=fusce"
                ),
                profilePhoto = null,
                distance = null,
                favorite = false,
                id = 7777,
                shopType = "vidisse",
                tags = listOf(),
                chatId = null,
                type = null
            ),
            NetworkVendor(
                areaServed = "instructior",
                categories = listOf(),
                companyName = "Jerome McFarland",
                coverPhoto = NetworkMediaAttachment(
                    id = 1591,
                    mediaType = "phasellus",
                    mediaUrl = "https://www.google.com/#q=sale"
                ),
                profilePhoto = null,
                distance = null,
                favorite = false,
                id = 4156,
                shopType = "quam",
                tags = listOf(),
                chatId = null,
                type = null
            ),
        )
    }
}