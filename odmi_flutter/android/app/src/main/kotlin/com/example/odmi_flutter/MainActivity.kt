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
import java.io.IOException

class MainActivity: FlutterActivity() {
    private val CHANNEL = "executorch/channel"
    private var model: Module? = null
    
    // Define the sequence length expected by the model
    private val SEQUENCE_LENGTH = 128
    
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            Log.d("ExecuTorch", "📱 Method called: ${call.method}")
            when (call.method) {
                "infer" -> {
                    val prompt = call.argument<String>("prompt")
                    Log.d("ExecuTorch", "🔤 Prompt received: '$prompt'")
                    val output = runInference(prompt ?: "")
                    Log.d("ExecuTorch", "📤 Sending result: $output")
                    result.success(output)
                }
                "loadModel" -> {
                    loadModel()
                    result.success("Model load attempted")
                }
                "checkModel" -> {
                    val status = if (model != null) "Model is loaded" else "Model is NOT loaded"
                    Log.d("ExecuTorch", "🔍 Model status: $status")
                    result.success(status)
                }
                else -> {
                    Log.d("ExecuTorch", "❌ Unknown method: ${call.method}")
                    result.notImplemented()
                }
            }
        }
        
        loadModel()
    }
    
    private fun loadModel() {
        try {
            Log.d("ExecuTorch", "🔄 Starting model load process...")
            
            // Copy model from assets to internal storage
            val modelFile = File(filesDir, "model.pte")
            Log.d("ExecuTorch", "📂 Model file path: ${modelFile.absolutePath}")
            
            if (!modelFile.exists()) {
                Log.d("ExecuTorch", "📥 Copying model from assets...")
                // Ensure parent directory exists before attempting to write
                modelFile.parentFile?.mkdirs() 
                
                // Use asset path expected from pubspec.yaml: assets/models/model.pte
                applicationContext.assets.open("models/model.pte").use { inputStream ->
                    FileOutputStream(modelFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Log.d("ExecuTorch", "✅ Model copied to internal storage")
            } else {
                Log.d("ExecuTorch", "📄 Model file already exists")
            }
            
            Log.d("ExecuTorch", "🏗️ Loading model with ExecutorTorch...")
            // Load the model using ExecutorTorch Module
            model = Module.load(modelFile.absolutePath)
            Log.d("ExecuTorch", "✅ Model loaded successfully! Model object: ${model != null}")
        } catch (e: IOException) {
            Log.e("ExecuTorch", "❌ Model file error (check path/permissions): ${e.message}", e)
            model = null
        } catch (e: Exception) {
            Log.e("ExecuTorch", "❌ Model load failed: ${e.message}", e)
            model = null
        }
    }
    
    private fun runInference(prompt: String): String {
        return try {
            model?.let { execModel ->
                // Create input tensors with CORRECT 2D shape [1, SEQUENCE_LENGTH] and Long type
                val inputIds = createInputTensor(prompt)
                val attentionMask = createAttentionMask()
                
                // Convert to EValue array for multiple inputs
                val inputs = arrayOf(
                    EValue.from(inputIds),      // Input 0: input_ids (Long)
                    EValue.from(attentionMask)  // Input 1: attention_mask (Long)
                )
                
                // Run forward pass with multiple inputs
                val output = execModel.forward(*inputs)
                
                // Process output
                processOutput(output)
            } ?: "Model not loaded"
        } catch (e: Exception) {
            Log.e("ExecuTorch", "❌ Inference failed: ${e.message}", e)
            "Inference error: ${e.message}"
        }
    }
    
    private fun createInputTensor(prompt: String): Tensor {
        // TODO: Implement proper tokenization (e.g., using Hugging Face tokenizer)
        
        // --- FIX IMPLEMENTED HERE ---
        // Dummy token IDs as Long. Uses SEQUENCE_LENGTH.
        // The first input to a transformer model MUST be Long/Int64 type.
        val inputIds = LongArray(SEQUENCE_LENGTH) { 1L } 
        
        // Define the shape as [Batch Size, Sequence Length] -> [1, 128]
        // This resolves the common type/shape mismatch error.
        val shape = longArrayOf(1L, SEQUENCE_LENGTH.toLong()) 
        
        return Tensor.fromBlob(inputIds, shape)
    }
    
    private fun createAttentionMask(): Tensor {
        // Attention mask: 1 for real tokens, 0 for padding.
        // For dummy data, we'll set all to 1 (no padding).
        val attentionMask = LongArray(SEQUENCE_LENGTH) { 1L } 
        
        // Define the shape as [Batch Size, Sequence Length] -> [1, 128]
        val shape = longArrayOf(1L, SEQUENCE_LENGTH.toLong()) 
        
        return Tensor.fromBlob(attentionMask, shape)
    }
    
    private fun processOutput(output: Array<EValue>): String {
        return try {
            // Assuming the output is a single tensor (e.g., logits)
            val outputTensor = output[0].toTensor()
            val scores = outputTensor.dataAsFloatArray
            
            // TODO: Implement proper detokenization/output interpretation
            // For now, return a simple response based on output
            "Model inferred successfully! (scores: ${scores.take(5).joinToString(", ")}...)"
        } catch (e: Exception) {
             "Output processing error: ${e.message}"
        }
    }
}