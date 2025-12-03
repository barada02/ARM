#!/usr/bin/env python3
"""
ExecuTorch Model Test Script
===========================

Simple test of the converted SmolLM2-135M-Instruct model in ExecuTorch format.
"""

from pathlib import Path
import sys

def main():
    """Simple test of ExecuTorch model."""
    print("🚀 ExecuTorch Model Test")
    print("=" * 40)
    
    try:
        # Import required packages
        from optimum.executorch import ExecuTorchModelForCausalLM
        from transformers import AutoTokenizer
        print("✅ Imports successful")
        
        # Configuration
        model_path = "./qwen3_0_6B"
        original_model_id = "Qwen/Qwen3-0.6B"
        
        # Check model exists
        current_dir = Path.cwd()
        model_full_path = current_dir / model_path
        pte_file = model_full_path / "model.pte"
        
        if not pte_file.exists():
            print(f"❌ Model not found: {pte_file}")
            sys.exit(1)
            
        print(f"✅ Model found: {pte_file.stat().st_size / (1024*1024):.1f} MB")
        
        # Load model and tokenizer
        print("🔄 Loading model...")
        model = ExecuTorchModelForCausalLM.from_pretrained(str(model_full_path))
        tokenizer = AutoTokenizer.from_pretrained(original_model_id)
        print("✅ Model loaded")
        
        # Test generation
        test_prompt = "The future of AI is"
        print(f"🎯 Testing with: '{test_prompt}'")
        
        result = model.text_generation(
            tokenizer=tokenizer,
            prompt=test_prompt,
            max_seq_len=50
        )
        
        print(f"🤖 Result: {result}")
        print("✅ Test completed successfully!")
        
    except ImportError as e:
        print(f"❌ Import error: {e}")
        print("Install required packages: pip install optimum-executorch transformers")
        sys.exit(1)
    except Exception as e:
        print(f"❌ Error: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()