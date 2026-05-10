from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
from keybert import KeyBERT

import numpy as np
import requests
import re
import os

# -----------------------------
# Embedding Model
# -----------------------------

EMBEDDING_MODEL = "BAAI/bge-base-en-v1.5"

model = SentenceTransformer(
    EMBEDDING_MODEL
)

# -----------------------------
# KeyBERT
# -----------------------------

kw_model = KeyBERT(
    model=model
)

# -----------------------------
# Ollama Configuration
# -----------------------------

OLLAMA_URL = os.getenv(

    "OLLAMA_URL",

    "http://host.docker.internal:11434/api/generate"
)

LLM_MODEL = "phi3"

# -----------------------------
# Noise Words
# -----------------------------

NOISE_TERMS = {

    "developer",
    "engineer",
    "experience",
    "team",
    "project",
    "requirement",
    "candidate",
    "ability",
    "work",
    "knowledge",
    "skill",
    "good",
    "years",
    "working",
    "role",
    "hiring",
    "understanding",
    "strong",
    "excellent",
    "familiarity",
    "plus",
    "preferred"
}

# -----------------------------
# Technical Skill Ontology
# -----------------------------

TECH_SKILLS = [

    "java",
    "spring boot",
    "spring",
    "microservices",
    "aws",
    "docker",
    "kubernetes",
    "kafka",
    "hibernate",
    "mysql",
    "postgresql",
    "mongodb",
    "redis",
    "react",
    "angular",
    "python",
    "django",
    "fastapi",
    "tensorflow",
    "pytorch",
    "jenkins",
    "terraform",
    "ci/cd",
    "git",
    "linux",
    "graphql",
    "rest api",
    "oauth2",
    "jwt",
    "elasticsearch",
    "rabbitmq",
    "spark",
    "hadoop",
    "oracle",
    "gcp",
    "azure",
    "typescript",
    "javascript"
]

TECH_SKILL_EMBEDDINGS = model.encode(
    TECH_SKILLS
)

# -----------------------------
# Text Preprocessing
# -----------------------------

def preprocess_text(text):

    text = text.lower()

    text = re.sub(
        r"\s+",
        " ",
        text
    )

    return text.strip()

# -----------------------------
# Normalize Skills
# -----------------------------

def normalize_skill(term):

    term_embedding = model.encode(
        [term]
    )

    similarities = cosine_similarity(

        term_embedding,

        TECH_SKILL_EMBEDDINGS
    )[0]

    best_index = np.argmax(
        similarities
    )

    best_score = similarities[
        best_index
    ]

    if best_score >= 0.72:

        return TECH_SKILLS[
            best_index
        ]

    return None

# -----------------------------
# Keyword Extraction
# -----------------------------

def extract_keywords(text):

    text = preprocess_text(text)

    keywords = kw_model.extract_keywords(

        text,

        keyphrase_ngram_range=(1, 2),

        stop_words="english",

        top_n=40
    )

    normalized_keywords = set()

    for keyword, score in keywords:

        keyword = keyword.lower().strip()

        if (
                keyword in NOISE_TERMS
                or
                len(keyword) <= 2
        ):

            continue

        normalized = normalize_skill(
            keyword
        )

        if normalized:

            normalized_keywords.add(
                normalized
            )

    return list(normalized_keywords)

# -----------------------------
# Semantic Skill Matching
# -----------------------------

def semantic_match(

        resume_terms,

        jd_terms,

        threshold=0.72
):

    if (
            not resume_terms
            or
            not jd_terms
    ):

        return [], []

    matched = set()

    missing = set()

    resume_embeddings = model.encode(
        resume_terms
    )

    jd_embeddings = model.encode(
        jd_terms
    )

    similarity_matrix = cosine_similarity(

        jd_embeddings,

        resume_embeddings
    )

    for i, jd_term in enumerate(
            jd_terms):

        similarities = similarity_matrix[i]

        best_similarity = np.max(
            similarities
        )

        if best_similarity >= threshold:

            matched.add(jd_term)

        else:

            missing.add(jd_term)

    return sorted(list(matched)), sorted(list(missing))

# -----------------------------
# LLM Recommendations
# -----------------------------

def generate_llm_recommendations(

        matched_skills,

        missing_skills,

        match_score
):

    prompt = f"""
You are a senior ATS evaluator and technical recruiter.

IMPORTANT RULES:
- Focus ONLY on technical skills.
- Do NOT invent certifications, projects, or technologies.
- Keep response concise.
- Only reference technologies explicitly present in the resume or directly related to the job description.
- Use professional ATS language.
- Ignore generic terms.

MATCH SCORE:
{match_score}

MATCHED SKILLS:
{matched_skills}

MISSING SKILLS:
{missing_skills}

Return ONLY these sections:

1. Key Strengths
2. Missing Technical Areas
3. ATS Optimization Improvements
4. Resume Enhancement Suggestions
5. Final Recruiter Assessment

Each section should contain 1 concise sentence only.
"""

    try:

        response = requests.post(

            OLLAMA_URL,

            json={

                "model": LLM_MODEL,

                "prompt": prompt,

                "stream": False
            },

            timeout=25
        )

        data = response.json()

        return data.get(
            "response",
            "No AI recommendations generated."
        )

    except Exception as e:

        return f"LLM recommendation generation failed: {str(e)}"

# -----------------------------
# Match Score Calculation
# -----------------------------

def calculate_match_score(

        resume_text,

        job_description
):

    # ---------------------------------
    # Semantic Embedding Score
    # ---------------------------------

    resume_embedding = model.encode(
        resume_text
    )

    job_embedding = model.encode(
        job_description
    )

    semantic_similarity = cosine_similarity(

        [resume_embedding],

        [job_embedding]

    )[0][0]

    semantic_score = round(

        float(semantic_similarity * 100),

        2
    )

    # ---------------------------------
    # Extract Technical Skills
    # ---------------------------------

    resume_keywords = extract_keywords(
        resume_text
    )

    job_keywords = extract_keywords(
        job_description
    )

    # ---------------------------------
    # Match Skills
    # ---------------------------------

    matched_keywords, missing_keywords = semantic_match(

        resume_keywords,

        job_keywords
    )

    # ---------------------------------
    # Alignment Score
    # ---------------------------------

    if len(job_keywords) > 0:

        alignment_score = round(

            (
                    len(matched_keywords)
                    /
                    len(job_keywords)
            ) * 100,

            2
        )

    else:

        alignment_score = semantic_score

    # ---------------------------------
    # Final ATS Score
    # ---------------------------------

    final_score = round(

        (
                semantic_score * 0.65
                +
                alignment_score * 0.35
        ),

        2
    )

    final_score = min(
        final_score,
        100
    )

    # ---------------------------------
    # AI Recommendations
    # ---------------------------------

    ai_recommendations = generate_llm_recommendations(

        matched_keywords,

        missing_keywords,

        final_score
    )

    return {

        "match_score":
            final_score,

        "semantic_score":
            semantic_score,

        "alignment_score":
            alignment_score,

        "matched_skills":
            matched_keywords,

        "missing_skills":
            missing_keywords,

        "ai_recommendations":
            ai_recommendations
    }