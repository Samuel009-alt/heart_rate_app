package com.example.heart_rate_app.data.cloudinaryConfig

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object CloudinaryConfig {
    const val CLOUD_NAME = "disyregac"
    const val API_KEY = "669913392216152"
    const val API_SECRET = "your_api_secret_here"
    const val UPLOAD_PRESET = "mobile_uploads"
}

// Main upload helper class
class ImageUploadHelper {
    suspend fun uploadToCloudinary(imageUri: Uri, context: Context): String? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("ImageUpload", "Starting image upload process...")

                // Read image bytes
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()

                if (bytes == null) {
                    Log.e("ImageUpload", "Failed to read image bytes from URI")
                    return@withContext null
                }

                Log.d("ImageUpload", "Image bytes read successfully, size: ${bytes.size}")

                // Try multipart upload first (more reliable)
                val multipartResult = uploadMultipart(bytes, context)
                if (multipartResult != null) {
                    Log.d("ImageUpload", "Multipart upload successful")
                    return@withContext multipartResult
                }

                Log.d("ImageUpload", "Multipart failed, trying base64...")

                // Fallback to base64 if multipart fails
                val base64Result = uploadBase64(bytes)
                if (base64Result != null) {
                    Log.d("ImageUpload", "Base64 upload successful")
                    return@withContext base64Result
                }

                Log.e("ImageUpload", "Both upload methods failed")
                return@withContext null

            } catch (e: Exception) {
                Log.e("ImageUpload", "Exception in uploadToCloudinary: ${e.message}", e)
                return@withContext null
            }
        }
    }

    private fun uploadMultipart(imageBytes: ByteArray, context: Context): String? {
        return try {
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            val timestamp = System.currentTimeMillis() / 1000
            val publicId = "profile_${timestamp}"

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    "image.jpg",
                    imageBytes.toRequestBody("image/jpeg".toMediaType())
                )
                .addFormDataPart("upload_preset", CloudinaryConfig.UPLOAD_PRESET)
                .addFormDataPart("public_id", publicId)
                .addFormDataPart("folder", "profile_images")
                .build()

            val request = Request.Builder()
                .url("https://api.cloudinary.com/v1_1/${CloudinaryConfig.CLOUD_NAME}/image/upload")
                .post(requestBody)
                .build()

            Log.d("Cloudinary", "Multipart upload URL: ${request.url}")

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            Log.d("Cloudinary", "Multipart response code: ${response.code}")
            Log.d("Cloudinary", "Multipart response: $responseBody")

            if (response.isSuccessful && responseBody != null) {
                val jsonObject = JSONObject(responseBody)
                jsonObject.getString("secure_url")
            } else {
                Log.e("Cloudinary", "Multipart upload failed: ${response.code} - $responseBody")
                null
            }
        } catch (e: Exception) {
            Log.e("Cloudinary", "Multipart upload exception: ${e.message}", e)
            null
        }
    }

    private fun uploadBase64(imageBytes: ByteArray): String? {
        return try {
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            val base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
            val timestamp = System.currentTimeMillis() / 1000
            val publicId = "profile_${timestamp}"

            val requestBody = FormBody.Builder()
                .add("file", "data:image/jpeg;base64,$base64Image")
                .add("upload_preset", CloudinaryConfig.UPLOAD_PRESET)
                .add("public_id", publicId)
                .add("folder", "profile_images")
                .build()

            val request = Request.Builder()
                .url("https://api.cloudinary.com/v1_1/${CloudinaryConfig.CLOUD_NAME}/image/upload")
                .post(requestBody)
                .addHeader("User-Agent", "AndroidApp/1.0")
                .build()

            Log.d("Cloudinary", "Base64 upload URL: ${request.url}")

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            Log.d("Cloudinary", "Base64 response code: ${response.code}")
            Log.d("Cloudinary", "Base64 response: $responseBody")

            if (response.isSuccessful && responseBody != null) {
                val jsonObject = JSONObject(responseBody)
                jsonObject.getString("secure_url")
            } else {
                Log.e("Cloudinary", "Base64 upload failed: ${response.code} - $responseBody")
                null
            }
        } catch (e: Exception) {
            Log.e("Cloudinary", "Base64 upload exception: ${e.message}", e)
            null
        }
    }
}