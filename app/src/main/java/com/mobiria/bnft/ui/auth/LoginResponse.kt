package com.mobiria.bnft.ui.auth

data class LoginResponse(
	val oData: ODataLogin? = null,
	val message: String? = null,
	val accessToken: String? = null,
	val errors: Errors? = null,
	val status: String? = null,
	val social_medias: SocialMedias? = null
)

data class SocialMedias(
	val any: Any? = null
)

data class ODataLogin(
	val user_device_type: String? = null,
	val updated_on: String? = null,
	val user_device_token: String? = null,
	val user_package: Int? = null,
	val alt_secret_key: String? = null,
	val temp_mobile: Any? = null,
	val temp_dial_code: Any? = null,
	val id: Int? = null,
	val state_id: Int? = null,
	val state: String? = null,
	val password_reset_time: Any? = null,
	val user_verified: Int? = null,
	val business_name: String? = null,
	val alt_api_key: String? = null,
	val user_image: String? = null,
	val group_name: String? = null,
	val selfi_with_photo_id: Any? = null,
	val created_uid: Int? = null,
	val trade_licence_file: Any? = null,
	val active: Int? = null,
	val display_name: String? = null,
	val public_wallet_address: String? = null,
	val created_on: String? = null,
	val dob: String? = null,
	val is_verify_email_sent: Int? = null,
	val country_id: Int? = null,
	val city_id: Int? = null,
	val updated_uid: Int? = null,
	val photo_id: Any? = null,
	val city: String? = null,
	val selfi_with_account_number: Any? = null,
	val firebase_user_key: String? = null,
	val mobile_verified: Int? = null,
	val dial_code: String? = null,
	val user_code: String? = null,
	val fcm_token: String? = null,
	val user_access_token: String? = null,
	val passport_file: Any? = null,
	val first_name: String? = null,
	val email: String? = null,
	val email_verified: Int? = null,
	val alt_merchant_id: String? = null,
	val mobile: String? = null,
	val last_name: String? = null,
	val banner: String? = null,
	val wallet_balance: Any? = null,
	val ip_address: String? = null,
	val middle_name: Any? = null,
	val user_phone_otp: Any? = null,
	val oodle_token: String? = null,
	val deleted: Int? = null,
	val password_reset_code: Any? = null,
	val user_name: String? = null
)

data class Errors(
	val any: Any? = null
)

