package com.example.sample.Model.response


import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("event_date")
    val eventDate: String,
    @SerializedName("event_dates")
    val eventDates: String,
    @SerializedName("event_image")
    val eventImage: List<EventImage>,
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("short_description")
    val shortDescription: String
) {
    data class EventImage(
        @SerializedName("image")
        val image: String
    )
}