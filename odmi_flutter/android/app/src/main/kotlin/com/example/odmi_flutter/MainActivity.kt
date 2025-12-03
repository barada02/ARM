package com.example.odmi_flutter

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import org.pytorch.executorch.EValue
import org.pytorch.executorch.Module
import org.pytorch.executorch.Tensor
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class MainActivity: FlutterActivity() {
    private val CHANNEL = "executorch/channel"
    private var model: Module? = null
    
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "infer" -> {
                    val prompt = call.argument<String>("prompt")
                    val output = runInference(prompt ?: "")
                    result.success(output)
                }
                "loadModel" -> {
                    loadModel()
                    result.success("Model load attempted")
                }
                else -> result.notImplemented()
            }
        }
        
        loadModel()
    }
    
    private fun loadModel() {
        try {
            // Copy model from assets to internal storage
            val modelFile = File(filesDir, "model.pte")
            if (!modelFile.exists()) {
                val inputStream = assets.open("models/model.pte")
                val outputStream = FileOutputStream(modelFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            }
            
            // Load the model using ExecutorTorch Module
            model = Module.load(modelFile.absolutePath)
            Log.d("ExecuTorch", "✅ Model loaded successfully from ${modelFile.absolutePath}")
        } catch (e: Exception) {
            Log.e("ExecuTorch", "❌ Model load failed: ${e.message}")
        }
    }
    
    private fun runInference(prompt: String): String {
        return try {
            model?.let { execModel ->
                // Create input tensor for the prompt (dummy implementation)
                val inputTensor = createInputTensor(prompt)
                val inputEValue = EValue.from(inputTensor)
                
                // Run forward pass
                val output = execModel.forward(inputEValue)
                
                // Process output
                processOutput(output)
            } ?: "Model not loaded"
        } catch (e: Exception) {
            "Inference error: ${e.message}"
        }
    }
    
    private fun createInputTensor(prompt: String): Tensor {
        // TODO: Implement proper tokenization
        // For now, create dummy input tensor
        // Adjust shape based on your model's input requirements
        val dummyData = FloatArray(128) { it.toFloat() } // Dummy token IDs
        return Tensor.fromBlob(dummyData, longArrayOf(1, 128)) // [batch_size, seq_len]
    }
    
    private fun processOutput(output: Array<EValue>): String {
        return try {
            // Get the first output tensor and convert to float array
            val outputTensor = output[0].toTensor()
            val scores = outputTensor.dataAsFloatArray
            
            // TODO: Implement proper detokenization
            // For now, return a simple response based on output
            "Model response (scores: ${scores.take(5).joinToString(", ")}...)"
        } catch (e: Exception) {
            "Output processing error: ${e.message}"
        }
    }
}
