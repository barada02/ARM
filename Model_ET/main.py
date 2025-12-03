#!/usr/bin/env python3
"""
ExecuTorch Model Inference Script
=================================

This script demonstrates how to use the converted SmolLM2-135M-Instruct model
in ExecuTorch format for text generation.

Usage:
    python main.py
"""

import os
import sys
from pathlib import Path
from typing import Optional

def setup_environment():
    """Setup the environment and check dependencies."""
    try:
        from optimum.executorch import ExecuTorchModelForCausalLM
        from transformers import AutoTokenizer
        print("✅ All required packages are available")
        return True
    except ImportError as e:
        print(f"❌ Missing dependencies: {e}")
        print("Please install the required packages:")
        print("  pip install optimum-executorch transformers")
        return False

def load_model_and_tokenizer(model_path: str, original_model_id: str):
    """Load the ExecuTorch model and tokenizer."""
    try:
        print(f"🔄 Loading ExecuTorch model from: {model_path}")
        
        # Import here to handle missing dependencies gracefully
        from optimum.executorch import ExecuTorchModelForCausalLM
        from transformers import AutoTokenizer
        
        # Load the converted ExecuTorch model
        model = ExecuTorchModelForCausalLM.from_pretrained(model_path)
        print("✅ ExecuTorch model loaded successfully")
        
        # Load the tokenizer from the original model
        tokenizer = AutoTokenizer.from_pretrained(original_model_id)
        print("✅ Tokenizer loaded successfully")
        
        return model, tokenizer
        
    except Exception as e:
        print(f"❌ Error loading model: {e}")
        return None, None

def generate_text(model, tokenizer, prompt: str, max_length: int = 128):
    """Generate text using the ExecuTorch model."""
    try:
        print(f"🎯 Generating text for prompt: '{prompt}'")
        print("-" * 50)
        
        # Generate text using the ExecuTorch model
        generated_text = model.text_generation(
            tokenizer=tokenizer,
            prompt=prompt,
            max_seq_len=max_length
        )
        
        return generated_text
        
    except Exception as e:
        print(f"❌ Error during text generation: {e}")
        return None

def interactive_mode(model, tokenizer):
    """Run interactive text generation mode."""
    print("\n🎮 Interactive Mode")
    print("=" * 50)
    print("Enter your prompts (type 'quit' to exit, 'help' for commands)")
    print("-" * 50)
    
    while True:
        try:
            prompt = input("\n💬 Enter prompt: ").strip()
            
            if prompt.lower() in ['quit', 'exit', 'q']:
                print("👋 Goodbye!")
                break
                
            elif prompt.lower() == 'help':
                print("\nAvailable commands:")
                print("  - Type any text to generate a continuation")
                print("  - 'quit' or 'exit' to stop")
                print("  - 'help' to show this message")
                continue
                
            elif not prompt:
                print("⚠️ Please enter a prompt or 'quit' to exit")
                continue
            
            # Generate text
            result = generate_text(model, tokenizer, prompt)
            
            if result:
                print(f"\n🤖 Generated text:\n{result}")
            else:
                print("❌ Failed to generate text")
                
        except KeyboardInterrupt:
            print("\n\n👋 Interrupted by user. Goodbye!")
            break
        except Exception as e:
            print(f"❌ Error: {e}")

def run_demo_prompts(model, tokenizer):
    """Run predefined demo prompts."""
    demo_prompts = [
        "Once upon a time",
        "The future of artificial intelligence",
        "In a world where technology",
        "The most important thing in life is",
        "Science fiction writers often imagine"
    ]
    
    print("\n🎭 Demo Mode - Running predefined prompts")
    print("=" * 50)
    
    for i, prompt in enumerate(demo_prompts, 1):
        print(f"\n📝 Demo {i}/{len(demo_prompts)}")
        result = generate_text(model, tokenizer, prompt, max_length=100)
        
        if result:
            print(f"🤖 Generated: {result}")
        else:
            print("❌ Generation failed")
        
        print("-" * 30)

def main():
    """Main execution function."""
    print("🚀 ExecuTorch Model Inference")
    print("=" * 50)
    
    # Configuration
    model_path = "./smollm2_exported"
    original_model_id = "HuggingFaceTB/SmolLM2-135M-Instruct"
    
    # Check if we're in the right directory
    current_dir = Path.cwd()
    model_full_path = current_dir / model_path
    
    print(f"📁 Current directory: {current_dir}")
    print(f"🔍 Looking for model at: {model_full_path}")
    
    # Check if model exists
    if not model_full_path.exists():
        print(f"❌ Model directory not found: {model_full_path}")
        print("\nPlease ensure:")
        print("1. You're running this script from the Model_ET directory")
        print("2. The model directory ./smollm2_exported exists in this folder")
        sys.exit(1)
    
    # Check model.pte file
    pte_file = model_full_path / "model.pte"
    if not pte_file.exists():
        print(f"❌ Model file not found: {pte_file}")
        sys.exit(1)
    
    print(f"✅ Found model file: {pte_file}")
    print(f"📊 Model file size: {pte_file.stat().st_size / (1024*1024):.1f} MB")
    
    # Setup environment
    if not setup_environment():
        sys.exit(1)
    
    # Load model and tokenizer
    model, tokenizer = load_model_and_tokenizer(str(model_full_path), original_model_id)
    
    if model is None or tokenizer is None:
        print("❌ Failed to load model or tokenizer")
        sys.exit(1)
    
    # Ask user what they want to do
    print("\n🎯 What would you like to do?")
    print("1. Run demo prompts")
    print("2. Interactive mode")
    print("3. Both")
    
    try:
        choice = input("\nEnter your choice (1/2/3) [default: 3]: ").strip()
        
        if choice == "1":
            run_demo_prompts(model, tokenizer)
        elif choice == "2":
            interactive_mode(model, tokenizer)
        else:  # Default to both
            run_demo_prompts(model, tokenizer)
            interactive_mode(model, tokenizer)
            
    except KeyboardInterrupt:
        print("\n\n👋 Interrupted by user. Goodbye!")
    except Exception as e:
        print(f"❌ Unexpected error: {e}")

if __name__ == "__main__":
    main()
