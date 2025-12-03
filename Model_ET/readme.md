# ExecuTorch Model Conversion

This project demonstrates converting Hugging Face transformer models to ExecuTorch format (.pte) for edge deployment.

## Setup

### 1. Create Environment
```bash
conda create -n optimum-executorch python=3.11
conda activate optimum-executorch
```
### Addtional packages
```
pip install huggingface_hub[hf_xet]
```

### 2. Install Dependencies
```bash
git clone https://github.com/huggingface/optimum-executorch.git
cd optimum-executorch
pip install '.[dev]'
python install_dev.py

cd ../
```


## Model Conversion

### Export Model to ExecuTorch
```bash
optimum-cli export executorch \
    --model "HuggingFaceTB/SmolLM2-135M-Instruct" \
    --task "text-generation" \
    --recipe "xnnpack" \
    --output_dir="./smollm2_exported"
```
```powershell
optimum-cli export executorch `
    --model "HuggingFaceTB/SmolLM2-135M-Instruct" `
    --task "text-generation" `
    --recipe "xnnpack" `
    --output_dir="./smollm2_exported"
```

### Qwen3 0.6B
```
optimum-cli export executorch `    
     --model "Qwen/Qwen3-0.6B" `
     --task "text-generation" `
     --recipe "xnnpack" `
     --use_custom_sdpa `
     --use_custom_kv_cache `
     --qlinear 8da4w `
     --qembedding 4w `
     --output_dir="./qwen3_0_6B"

```

## Usage

### Run Tests
```bash
# Simple test
python test_model.py

# Interactive version
python main.py
```

## Files
- `main.py` - Interactive model testing
- `test_model.py` - Simple automated test
- `smollm2_exported/model.pte` - Converted ExecuTorch model (622MB)

## Performance
- **Model Size**: ~622MB
- **Speed**: ~2.7 tokens/second (CPU)
- **Backend**: XNNPACK optimization

## Cleanup
After installation via conda, the `optimum-executorch/` directory can be safely deleted.
