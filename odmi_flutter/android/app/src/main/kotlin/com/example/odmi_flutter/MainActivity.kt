package com.example.odmi_flutter

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import org.pytorch.executorch.*
import java.nio.ByteBuffer

class MainActivity: FlutterActivity() {
    private val CHANNEL = "executorch/channel"
    private var model: EdgeModel? = null
    
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "infer" -> {
                    val prompt = call.argument<String>("prompt")
                    val output = runInference(prompt ?: "")
                    result.success(output)
                }
                else -> result.notImplemented()
            }
        }
        
        loadModel()
    }
    
    private fun loadModel() {
        try {
            val inputStream = assets.open("models/model.pte")
            val modelBytes = inputStream.readBytes()
            val options = ExecutionContextOptions.builder().build()
            model = ExportedModel.load(modelBytes, ExecutionContext(options))
            android.util.Log.d("ExecuTorch", "✅ Model loaded successfully")
        } catch (e: Exception) {
            android.util.Log.e("ExecuTorch", "❌ Model load failed: ${e.message}")
        }
    }
    
    private fun runInference(prompt: String): String {
        return try {
            "Model loaded! Input: $prompt (Tokenizer next step)"
        } catch (e: Exception) {
            "Inference error: ${e.message}"
        }
    }
}
