package im.kirillt.yandexmoneyclient.events.download

import android.content.Context
import android.util.Log
import com.yandex.money.api.methods.OperationHistory.FilterType
import com.yandex.money.api.methods.OperationHistory.Request
import com.yandex.money.api.model.Operation.Type
import de.greenrobot.event.EventBus
import im.kirillt.yandexmoneyclient.YMCApplication
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent
import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns
import im.kirillt.yandexmoneyclient.provider.operation.OperationSelection
import im.kirillt.yandexmoneyclient.utils.ResponseToContentValues
import org.joda.time.DateTime
import java.util.*

class DownloadHistoryEvent(private val context: Context) : DownloadEvent {

    val DEFAULT_RECORDS_DOWNLOAD = 30;

    override fun download() {
        try {
            YMCApplication.historyDownloadingStart()
            download(getLatestSavedOperationDate())
            EventBus.getDefault().post(SuccessHistoryEvent())
        } catch (e: Exception) {
            e.printStackTrace()
            EventBus.getDefault().post(AnyErrorEvent(e))
        } finally {
            YMCApplication.historyDownloadingFinish()
        }
    }

    private fun download(from: DateTime?, startRecord: Int? = null, count: Int = DEFAULT_RECORDS_DOWNLOAD) {
        var nextRecord = startRecord
        do {
            val response = YMCApplication.client.execute(createRequest(from, nextRecord, count))
            Log.i("download: ", "nextRecord: ${response.nextRecord ?: 0} response size: ${response.operations.size}")
            response.operations
                    .filter {
                        listOf(Type.OUTGOING_TRANSFER, Type.INCOMING_TRANSFER, Type.INCOMING_TRANSFER_PROTECTED)
                                .contains(it.type)
                    }
                    .forEach { ResponseToContentValues.operation(it).insert(context.contentResolver) }
            try {
                nextRecord = response.nextRecord?.toInt()
            } catch (e: NumberFormatException) {
                nextRecord = null
            }
        } while (nextRecord != null)
    }

    private fun createRequest(from: DateTime?, startRecord: Int?, count: Int): Request {
        val builder = Request.Builder()
                .setTypes(hashSetOf(FilterType.DEPOSITION, FilterType.PAYMENT))
                .setDetails(true)
        if (from != null) {
            builder.setFrom(from.plusMillis(100))
        }
        if (startRecord != null) {
            builder.setStartRecord(startRecord.toString())
        }
        builder.setRecords(count)
        return builder.create()
    }

    private fun getLatestSavedOperationDate(): DateTime? {
        val cursor = OperationSelection().operationidNot("").query(context.contentResolver, null, OperationColumns.DATETIME + " DESC") ?: return null
        if (cursor.moveToNext()) {
            return DateTime(Date(cursor.datetime))
        }
        return null
    }
}