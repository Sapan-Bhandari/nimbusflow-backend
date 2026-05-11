NimbusFlow Backend

AI-Powered Resume Matching & ATS Optimization Platform

⸻

Overview

NimbusFlow is an enterprise-style cloud-native AI resume analysis platform designed to help job seekers optimize resumes against job descriptions using semantic AI, LLM-powered recommendations, and microservices architecture.

The platform combines:

* Java Spring Boot Microservices
* FastAPI AI Matching Engine
* Sentence Transformers
* LLM Integration (Ollama)
* PostgreSQL
* JWT Authentication
* API Gateway
* Dockerized Infrastructure
* Kubernetes Orchestration
* Prometheus & Grafana Observability
* Production-ready Containerization

The system performs:

* Resume upload and parsing
* Job description management
* AI semantic similarity scoring
* Missing skill detection
* ATS optimization suggestions
* LLM-based resume recommendations

⸻

Architecture

                         +----------------------+
                         |      Frontend        |
                         | React + NGINX        |
                         +----------+-----------+
                                    |
                                    v
                         +----------+-----------+
                         | Kubernetes Ingress   |
                         | NGINX Controller     |
                         +----------+-----------+
                                    |
                                    v
                         +----------+-----------+
                         |     API Gateway      |
                         | Spring Cloud Gateway |
                         +-----+----------+-----+
                               |          |
        --------------------------------------------------------
        |                 |                |                   |
        v                 v                v                   v
+----------------+ +----------------+ +----------------+ +----------------+
| Resume Service | |   Job Service  | |  Auth Service  | | Matcher Service|
| Spring Boot    | | Spring Boot    | | Spring Boot    | | FastAPI + AI   |
+--------+-------+ +--------+-------+ +--------+-------+ +--------+-------+
|                  |                  |                   |
-----------------------------------------------------------
|
v
+----------------+
| PostgreSQL DB  |
+----------------+
-----------------------------------------------------------
|
v
+-------------------------------+
| Prometheus + Grafana Stack    |
| Metrics + Observability       |
+-------------------------------+

⸻

Key Features

Resume Management

* Upload resumes
* Extract resume content
* Store parsed resume data
* Retrieve uploaded resumes

Job Management

* Create job descriptions
* Manage skills and requirements
* Retrieve jobs for matching

AI Resume Matching

* Semantic similarity matching
* Transformer-based embeddings
* Missing skill detection
* ATS optimization analysis
* LLM-powered recommendations

Security

* JWT Authentication
* Secure API Gateway
* Token validation
* OAuth-ready architecture

Cloud-Native Features

* Dockerized microservices
* Kubernetes deployments
* Ingress routing
* ConfigMaps & Secrets
* Production frontend with NGINX
* Health checks
* Environment-based configuration
* Production-ready containerization

⸻

Technology Stack

Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Cloud Gateway
* Eureka Discovery Server
* Hibernate / JPA
* PostgreSQL
* JWT Authentication

AI / ML

* Python 3.12
* FastAPI
* Sentence Transformers
* BAAI Embedding Models
* KeyBERT
* Ollama LLM Integration
* spaCy NLP
* Scikit-learn

Frontend

* React
* Tailwind CSS
* Axios
* JWT Integration
* NGINX

DevOps & Cloud-Native Stack

* Docker
* Docker Compose
* Kubernetes
* NGINX Ingress
* Prometheus
* Grafana
* Spring Boot Actuator
* Micrometer
* Multi-stage Docker Builds
* Service Discovery
* Containerized Infrastructure

⸻

Microservices

Service	Description
discovery-server	Eureka service discovery
api-gateway	Central API gateway and JWT validation
auth-service	Authentication and JWT token generation
resume-service	Resume upload and matching orchestration
job-service	Job description management
matcher-service	AI semantic matching engine
frontend	React frontend application

⸻

AI Matching Engine

The AI matcher service performs:

* Semantic similarity scoring
* Embedding-based comparison
* Skill extraction
* Missing skill identification
* Resume optimization recommendations

AI Models Used

* BAAI/bge-base-en-v1.5
* Sentence Transformers
* Ollama LLMs
* KeyBERT keyword extraction

⸻

Example Match Response

{
"match_score": 76.89,
"semantic_score": 74.13,
"alignment_score": 83.33,
"matched_skills": [
"java",
"spring boot",
"aws"
],
"missing_skills": [
"kubernetes",
"microservices"
],
"ai_recommendations": "Add Kubernetes and cloud-native project experience to improve ATS alignment."
}

⸻

Cloud-Native Infrastructure

NimbusFlow is fully containerized and deployed using Kubernetes with production-style infrastructure components.

Implemented Infrastructure

* Kubernetes Deployments
* Kubernetes Services
* ConfigMaps & Secrets
* NGINX Ingress Controller
* ClusterIP Networking
* Dockerized Microservices
* Production Frontend with NGINX
* Prometheus Monitoring
* Grafana Dashboards
* Spring Boot Actuator Metrics
* Micrometer Instrumentation
* Service Discovery with Eureka

⸻

Observability Stack

NimbusFlow includes enterprise-grade observability using Prometheus and Grafana.

Monitoring Features

* JVM Metrics Monitoring
* Spring Boot APM Metrics
* HTTP Request Metrics
* Kubernetes Pod Monitoring
* CPU & Memory Metrics
* Service Health Monitoring
* Prometheus Service Discovery
* Grafana Dashboards

Observability Stack

* Prometheus
* Grafana
* Spring Boot Actuator
* Micrometer
* Kubernetes ServiceMonitors

⸻

Kubernetes Deployment

NimbusFlow is deployed on Kubernetes using production-style cloud-native infrastructure.

Kubernetes Components

* Deployments
* Services
* ConfigMaps
* Secrets
* Ingress Controller
* Service Discovery
* ClusterIP Networking
* Health Probes
* Observability Integration

Ingress Access

Frontend:

http://nimbusflow.local

Monitoring

Grafana:

http://localhost:3001

Prometheus:

http://localhost:9090

⸻

Local Development Setup

Prerequisites

Install:

* Java 21
* Maven
* Python 3.12
* Docker
* Docker Compose
* PostgreSQL
* Node.js
* Ollama
* Kubernetes
* Helm

⸻

Clone Repository

git clone <your-backend-repository-url>

⸻

Environment Variables

Create:

.env

Example:

POSTGRES_DB=nimbusflow
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
JWT_SECRET=mysupersecretkey
MATCHER_SERVICE_URL=http://matcher-service:8000
OLLAMA_URL=http://host.docker.internal:11434/api/generate
SPRING_PROFILES_ACTIVE=docker

⸻

Running with Docker

Build

docker compose build

Start

docker compose up

Stop

docker compose down

⸻

API Endpoints

Component	URL
API Gateway	http://localhost:8080
FastAPI Docs	http://localhost:8000/docs
Eureka Dashboard	http://localhost:8761
Grafana	http://localhost:3001
Prometheus	http://localhost:9090

⸻

Health Check Endpoints

Service	Endpoint
API Gateway	http://localhost:8080/actuator/health
Resume Service	http://localhost:8081/actuator/health
Job Service	http://localhost:8082/actuator/health
Auth Service	http://localhost:8084/actuator/health
Discovery Server	http://localhost:8761/actuator/health
Matcher Service	http://localhost:8000/health

⸻

Security

NimbusFlow currently supports:

* JWT authentication
* API Gateway validation
* Secure endpoint protection
* Token-based authorization

Future Security Enhancements

* Google OAuth Login
* Refresh Tokens
* RBAC
* API rate limiting

⸻

Future Enhancements

AI Enhancements

* Vector database integration
* RAG pipelines
* Fine-tuned ATS models
* Multi-model orchestration

Scalability

* Horizontal Pod Autoscaling
* Kafka-based async processing
* Redis caching
* Distributed tracing with OpenTelemetry

CI/CD

* GitHub Actions pipeline
* Automated Kubernetes deployments
* Security scanning
* Automated testing pipeline

Logging & Tracing

* Loki centralized logging
* OpenTelemetry tracing
* Distributed tracing dashboards

⸻

Screenshots

Recommended screenshots to include:

* Grafana Spring Boot APM Dashboard
* Kubernetes Pods & Services
* Prometheus Targets
* Application UI
* Eureka Dashboard

⸻

Project Goals

NimbusFlow is designed as:

* A real-world enterprise microservices platform
* An AI-powered ATS optimization system
* A cloud-native learning platform
* A portfolio-grade engineering project

⸻

Author

Sapan Bhandari

Software Engineer | Java | Spring Boot | Microservices | AI | Cloud Engineering

⸻

License

This project is for educational and portfolio purposes.