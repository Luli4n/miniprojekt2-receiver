import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters


class ProductNotificationService(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    val channelId = "product_notification_channel"

    override suspend fun doWork(): Result {
        val productName = inputData.getString("productName")
        val productPrice = inputData.getFloat("productPrice", 0.0f)
        val productQuantity = inputData.getInt("productQuantity", 0)
        val notificationText = "Dodano nowy produkt: $productName, Cena: $productPrice, Ilość: $productQuantity"

        createNotificationChannel()
        showNotification(notificationText)

        return Result.success()
    }

    private fun createNotificationChannel() {
        // Creating a notification channel for Android Oreo and newer versions
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return
        }
            val channelName = "Notyfikacje produktów"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Kanał dla notyfikacji produktów"
            }

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)
        }


    private fun showNotification(text: String) {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Nowy Produkt Dodany")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
}