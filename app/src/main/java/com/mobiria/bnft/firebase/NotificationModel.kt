package com.mobiria.bnft.firebase

class NotificationModel {
    private var createdAt: String? = ""
    private var description: String? = ""
    private var imageURL: String? = ""
    private var notificationType: String? = ""
    private var notificationId: String? = ""
    private var product_id: String? = ""
    private var read: String? = ""
    private var seen: String? = ""
    private var title: String? = ""
    private var url: String? = ""


    fun setNotificationId(notificationId: String?){
        this.notificationId = notificationId
    }
    fun getNotificationId(): String? {
        return notificationId
    }

    fun setProduct_id(product_id: String?){
        this.product_id = product_id
    }
    fun getProduct_id(): String? {
        return product_id
    }


    fun setCreatedAt(createdAt: String?){
        this.createdAt = createdAt
    }
    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setDescription(description: String?) {
        this.description = description
    }
    fun getDescription(): String? {
        return description
    }

    fun setImageURL(imageURL: String?){
        this.imageURL = imageURL
    }
    fun getImageURL(): String? {
        return imageURL
    }

    fun setNotificationType(notificationType: String?){
        this.notificationType = notificationType
    }
    fun getNotificationType(): String? {
        return notificationType
    }

    fun setRead(read: String?){
        this.read = read
    }
    fun getRead(): String? {
        return read
    }

    fun setSeen(seen: String?){
        this.seen = seen
    }
    fun getSeen(): String? {
        return seen
    }

    fun setTitle(title: String?){
        this.title = title
    }
    fun getTitle() : String? {
        return title
    }

    fun setUrl(url: String?){
        this.url = url
    }
    fun getUrl(): String? {
        return url
    }

}