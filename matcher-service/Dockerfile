FROM python:3.12-slim

# -----------------------------------
# Environment Variables
# -----------------------------------

ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# -----------------------------------
# Working Directory
# -----------------------------------

WORKDIR /app

# -----------------------------------
# System Dependencies
# -----------------------------------

RUN apt-get update && apt-get install -y \

    build-essential \
    curl \
    gcc \
    python3-dev \

    && rm -rf /var/lib/apt/lists/*

# -----------------------------------
# Copy Requirements
# -----------------------------------

COPY requirements.txt .

# -----------------------------------
# Install Python Dependencies
# -----------------------------------

RUN pip install --upgrade pip

RUN pip install --no-cache-dir -r requirements.txt

# -----------------------------------
# Download spaCy Model
# -----------------------------------

RUN python -m spacy download en_core_web_sm

# -----------------------------------
# Copy Application
# -----------------------------------

COPY . .

# -----------------------------------
# Create Non-Root User
# -----------------------------------

RUN useradd -m appuser

USER appuser

# -----------------------------------
# Expose Port
# -----------------------------------

EXPOSE 8000

# -----------------------------------
# Health Check
# -----------------------------------

HEALTHCHECK --interval=30s --timeout=10s --retries=5 \

CMD curl -f http://localhost:8000/health || exit 1

# -----------------------------------
# Start FastAPI App
# -----------------------------------

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]