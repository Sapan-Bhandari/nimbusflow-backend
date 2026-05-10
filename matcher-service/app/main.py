from fastapi import FastAPI
from app.model import MatchRequest
from app.service import calculate_match_score

app = FastAPI(
    title="NimbusFlow Matcher Service",
    version="1.0.0"
)

@app.get("/health")
def health():

    return {
        "status": "UP"
    }

@app.post("/match")
def match_resume(
        request: MatchRequest):

    result = calculate_match_score(
        request.resume_text,
        request.job_description
    )

    return result